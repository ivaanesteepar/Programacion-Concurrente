package p03;

public class Hilo implements Runnable{
	
    private char caracter;
    private int tiempoEntreEscrituras;
    private int repeticiones;

    public Hilo(char caracter, int tiempoEntreEscrituras, int repeticiones) {
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
