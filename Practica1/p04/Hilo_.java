package p04;

public class Hilo_ implements Runnable{
	
	private char caracter;
    private int tiempoEntreEscrituras;
    private int repeticiones;

    // constructor con el caracter que se quiere imprimir, el tiempo y el numero de repeticiones
    public Hilo_(char caracter, int tiempoEntreEscrituras, int repeticiones) {
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
        System.out.println();
    }
}
