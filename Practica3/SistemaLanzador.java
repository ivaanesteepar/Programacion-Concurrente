package p01;

public class SistemaLanzador {
	
    public static void main(String[] args) {
    	
    	int numPuertas = 4;
    	
    	IParque parque = new Parque();
    	// Crear hilos y lanzarlos
    	Thread hilo_entradas;
    	Thread hilo_salidas;
    	
    	for (int i = 0; i < numPuertas; i++) {
	    	hilo_entradas = new Thread(new ActividadEntradaPuerta(parque, String.valueOf((char) (65 + i))));
	    	hilo_salidas = new Thread(new ActividadSalidaPuerta(parque, String.valueOf((char) (65 + i))));
	    	
	    	hilo_entradas.start();
	    	hilo_salidas.start();
    	}

    }

}

