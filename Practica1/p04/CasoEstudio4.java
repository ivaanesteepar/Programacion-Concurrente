package p04;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CasoEstudio4 {

    public static void main(String[] args) {
        // Solicitar entrada del usuario para las variables del hilo
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Ingrese el carácter: ");
        char caracter = scanner.next().charAt(0);

        System.out.print("Ingrese el tiempo entre escrituras (en milisegundos): ");
        int tiempoEntreEscrituras = scanner.nextInt();

        System.out.print("Ingrese el número de repeticiones: ");
        int repeticiones = scanner.nextInt();

        // Crear un único tipo de hilo con tres parámetros
        Hilo_ hiloParametrizado = new Hilo_(caracter, tiempoEntreEscrituras, repeticiones);

        // Ejecutar con diferentes tipos de ejecutores
        ejecutarConExecutor(Executors.newCachedThreadPool(), hiloParametrizado);
        ejecutarConExecutor(Executors.newFixedThreadPool(1), hiloParametrizado);
        ejecutarConExecutor(Executors.newSingleThreadExecutor(), hiloParametrizado);
        ejecutarConExecutor(Executors.newFixedThreadPool(2), hiloParametrizado);
        ejecutarConExecutor(Executors.newFixedThreadPool(3), hiloParametrizado);
    }

    private static void ejecutarConExecutor(ExecutorService executor, Runnable hilo) {
        executor.execute(hilo);
        executor.shutdown();
    }
}
