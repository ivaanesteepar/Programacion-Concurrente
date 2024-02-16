package p03;
import java.util.Scanner;

public class CasoEstudio3 {

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
        Thread hiloParametrizado = new Thread(new Hilo_(caracter, tiempoEntreEscrituras, repeticiones));

        hiloParametrizado.setPriority(Thread.MAX_PRIORITY);
        
        hiloParametrizado.start();

        try {
            hiloParametrizado.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.print("Fin hilo parametrizado");
    }

}

class HiloParametrizado implements Runnable {

    private char caracter;
    private int tiempoEntreEscrituras;
    private int repeticiones;

    public HiloParametrizado(char caracter, int tiempoEntreEscrituras, int repeticiones) {
        this.caracter = caracter;
        this.tiempoEntreEscrituras = tiempoEntreEscrituras;
        this.repeticiones = repeticiones;
    }

    @Override
    public void run() {
        for (int i = 0; i < repeticiones; i++) {
            System.out.print(caracter);

            try {
                Thread.sleep(tiempoEntreEscrituras);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
