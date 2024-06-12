#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <time.h>
#include <math.h>
#include "FreeRTOS.h"
#include "task.h"
#include "queue.h"
#include "semphr.h"
#include "console.h"

#define FILENAME_LENGTH 20
#define NUM_DATA_POINTS 200
#define NUM_T3_TASKS 9

QueueHandle_t fileQueue;
QueueHandle_t resultQueue;
SemaphoreHandle_t semaphore;

void generateRandomNumbersToFile(const char *filename);
void T1_Task(void *pvParameters);
void T2_Task(void *pvParameters);
void T3_Task(void *pvParameters);
void T4_Task(void *pvParameters);

void main_base(void)
{
    fileQueue = xQueueCreate(1, FILENAME_LENGTH);
    resultQueue = xQueueCreate(NUM_T3_TASKS, sizeof(bool));
    semaphore = xSemaphoreCreateBinary();

    xTaskCreate(T1_Task, "T1", configMINIMAL_STACK_SIZE, NULL, 2, NULL);
    xTaskCreate(T2_Task, "T2", configMINIMAL_STACK_SIZE, NULL, 1, NULL);
    xTaskCreate(T4_Task, "T4", configMINIMAL_STACK_SIZE, NULL, 1, NULL);

    vTaskStartScheduler();

    for (;;)
    {
    }
}

void T1_Task(void *pvParameters)
{
    TickType_t xLastWakeTime = xTaskGetTickCount();
    const TickType_t xPeriod = pdMS_TO_TICKS(1000); // plazo de ejecución de 0,3 segundos

    while (1)
    {
        char filename[FILENAME_LENGTH];
        sprintf(filename, "file_%03d.txt", rand() % 100);

        generateRandomNumbersToFile(filename);

        xQueueSend(fileQueue, &filename, portMAX_DELAY); // envia el nombre de archivo a T2

        vTaskDelayUntil(&xLastWakeTime, xPeriod);
    }
}

double randn(double mu, double sigma)
{
    double U1, U2, W, mult;
    static double X1, X2;
    static int call = 0;

    if (call == 1)
    {
        call = !call;
        return (mu + sigma * (double)X2);
    }

    do
    {
        U1 = -1 + ((double)rand() / RAND_MAX) * 2;
        U2 = -1 + ((double)rand() / RAND_MAX) * 2;
        W = pow(U1, 2) + pow(U2, 2);
    } while (W >= 1 || W == 0);

    mult = sqrt((-2 * log(W)) / W);
    X1 = U1 * mult;
    X2 = U2 * mult;

    call = !call;

    return (mu + sigma * (double)X1);
}

void generateRandomNumbersToFile(const char *filename)
{
    FILE *file = fopen(filename, "w");
    if (file == NULL)
    {
        printf("Error al abrir el fichero\n");
        return;
    }

    srand(time(NULL));

    for (int i = 0; i < NUM_DATA_POINTS; ++i)
    {
        fprintf(file, "%f\n", randn(0, 1)); // Genera números aleatorios menor que 1
    }

    fclose(file);
}

void T2_Task(void *pvParameters)
{
    char filename[FILENAME_LENGTH];

    while (1)
    {
        if (xQueueReceive(fileQueue, &filename, pdMS_TO_TICKS(1000)) == pdTRUE)
        {

            for (int i = 1; i <= NUM_T3_TASKS; i++)
            {
                char taskName[10];
                sprintf(taskName, "T3.%d", i);
                xTaskCreate(T3_Task, taskName, configMINIMAL_STACK_SIZE, filename, 2, NULL);
            }

            int trueCount = 0;
            for (int i = 0; i < NUM_T3_TASKS; i++)
            {
                bool result;
                xQueueReceive(resultQueue, &result, pdMS_TO_TICKS(1000));
                if (result)
                    trueCount++;
            }

            printf("Consenso alcanzado entre %d de %d tareas. ", trueCount, NUM_T3_TASKS);
            printf("\nNumero de true counts: %d. Valor de consenso: %s\n", trueCount, trueCount > NUM_T3_TASKS / 2 ? "true" : "false");

        }
        else
        {
            // Si no se recibió ningún nombre de archivo en 1 segundo
            printf("No se recibieron archivos en 1 segundo.\n");
        }
    }
}

void T3_Task(void *pvParameters)
{
    char *filename = (char *)pvParameters;
    int trueCount = 0;
    double sum = 0;

    FILE *file = fopen(filename, "r");
    if (file == NULL)
    {
        printf("\nError al abrir el fichero\n");
        vTaskDelete(NULL);
        return;
    }

    float buffer; // Suponemos que cada línea del archivo tiene una longitud máxima de 50 caracteres
    while (fscanf(file, "%f", &buffer) != EOF)
    {
        if (fabs(buffer) > 2)
        {
            trueCount++;
            if (trueCount >= 10)
            {
                break; // Si trueCount es igual o mayor a 10, salir del bucle
            }
        }
    }

    fclose(file);

    // Determinar si el resultado es correcto o su complemento
    bool result;
    if (rand() % 100 < 80)
    { // 80% de las veces el resultado es correcto
        result = trueCount >= 10 ? true : false;
    }
    else
    { // 20% de las veces se envía el valor lógico negado o complementario
        result = !(trueCount >= 10 ? true : false);
    }

    // Enviar resultado a T2
    xQueueSend(resultQueue, &result, portMAX_DELAY);

    vTaskDelete(NULL);
}

void T4_Task(void *pvParameters)
{
    TickType_t xLastWakeTime;
    const TickType_t xPeriod = pdMS_TO_TICKS(2000);       // se ejecuta con un periodo de 2 segundos
    const TickType_t xExecutionTime = pdMS_TO_TICKS(500); // consume 0,5 segundos del procesador

    while (1)
    {
        xLastWakeTime = xTaskGetTickCount();
        // codigo que gaste ciclos de ejecucion
        vTaskDelayUntil(&xLastWakeTime, xPeriod);
    }
}
