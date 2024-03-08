package p01;

public class SistemaLanzador {
	
    public static void main(String[] args) {
    	
    	IParque parque = AdaptadorParqueSinc.getInstancia();
        
        // Define el número de puertas que deseas
        int numeroDePuertas = 4;

        // Crea un array para almacenar las instancias de ActividadEntradaPuerta y los hilos
        ActividadEntradaPuerta[] actividadesPuertas = new ActividadEntradaPuerta[numeroDePuertas];
        Thread[] hilosPuertas = new Thread[numeroDePuertas];

        // Instancia las ActividadEntradaPuerta y los hilos correspondientes
        for (int i = 0; i < numeroDePuertas; i++) {
            actividadesPuertas[i] = new ActividadEntradaPuerta(parque, String.valueOf((char)('A' + i)));
            hilosPuertas[i] = new Thread(actividadesPuertas[i]);
        }

        // Inicia los hilos
        for (Thread hilo : hilosPuertas) {
            hilo.start();
        }

    }
}

