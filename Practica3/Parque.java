package p01;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Parque implements IParque {
	
	// Numero total de personas
	private int totalPersonas;
	private double tiempoMedio;
	private double tiempoInicial;
	private static Random aleatorios = new Random();
	
	// Numero personas por puerta (usar hashtable)
	private Hashtable<String, Integer> personasPuerta;
	
	
	public Parque() {
		totalPersonas = 0;
		personasPuerta = new Hashtable<String, Integer>();
		tiempoInicial = System.currentTimeMillis();
		tiempoMedio = 0;
	}
	

	@Override
	public synchronized void entrarParque(String puerta) {
		
		/* COMO EL AUMENTO DEL CONTADOR DEL TOTAL DE PERSONAS Y EL NUMERO DE PERSONAS POR PUERTA DEBERÍAN DE SER
		 * EJECUTARSE DE FORMA ATÓMICA, SE UTILIZA "SYNCHRONIZED" PARA QUE SE EJECUTEN A LA VEZ EN EL TIEMPO.
		 * ESTO HACE QUE NO SALTE EL INVARIANTE (ASSERT).
		 * BLOQUEA EL ACCESO AL RESTO DE HILOS PARA QUE SOLAMENTE ENTREN DE UNO EN UNO.
		 */
		
		while (totalPersonas == 50) { // Solo puede haber en el parque hasta maximo 50 personas
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		totalPersonas++; // como ha entrado una persona al parque, aumentamos el contador total de personas
	
		// Si la persona que entra al parque no tiene una puerta en el hashtable, se crea con el contador a 0
		if (personasPuerta.get(puerta) == null) {
			personasPuerta.put(puerta, 0);
		}
	
		int personaActual = personasPuerta.get(puerta); // obtenemos la puerta de la persona que entra al parque
		personaActual++; // aumentamos el contador de la puerta antes de meterlo en el hashtable
		personasPuerta.put(puerta, personaActual); // añadimos el par de valores al hashtable
		
		long tActual = System.currentTimeMillis();
		tiempoMedio = (tiempoMedio + (tActual - tiempoInicial)) / 2.0;

		
		// Imprimir informacion del parque
		System.out.println("Entrada al parque por puerta " + puerta);
        System.out.println("--> Personas en el parque " + totalPersonas + " tiempo medio de estancia: " + tiempoMedio);
        
        for (String p : personasPuerta.keySet()) {
        	System.out.println("----> Por puerta " + p + " " + personasPuerta.get(p));
        }
        
        
        int suma = 0; 
        Collection <Integer> valores = personasPuerta.values(); // obtengo los contadores del hashtable
        
        // Realizamos una suma de todas las personas por puerta
        for (int valor : valores) {
        	suma += valor;
        	assert valor <= 20;
        }
        
        // El invariante dice que el numero total de personas debe de ser igual a la suma de las personas por puerta
        assert suma == totalPersonas : "El contador total de personas no es igual al contador de personas por puerta";
        assert totalPersonas >= 0;
        
        notifyAll();
		
	}


	@Override
	public synchronized void salirParque(String puerta) {	
		
		while (totalPersonas == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		totalPersonas--; // como sale una persona, se disminuye el contador total
		
		if (personasPuerta.get(puerta) == null) {
			personasPuerta.put(puerta, 0);
		}
		
		int personaActual = personasPuerta.get(puerta); // obtenemos la puerta de la persona que sale del parque
		personaActual--; // disminuimos el contador de la puerta antes de meterlo en el hashtable
		personasPuerta.put(puerta, personaActual); // añadimos el par de valores al hashtable
		
		long tActual2 = System.currentTimeMillis();
		tiempoMedio = (tiempoMedio + (tActual2 - tiempoInicial)) / 2.0;
		
		// Imprimir informacion del parque
		System.out.println("Salida del parque por puerta " + puerta);
		System.out.println("--> Personas en el parque " + totalPersonas + " tiempo medio de estancia: " + tiempoMedio);
		        
		for (String p : personasPuerta.keySet()) {
			System.out.println("----> Por puerta " + p + " " + personasPuerta.get(p));
		}
		
		int suma = 0; 
		Collection <Integer> valores = personasPuerta.values(); // obtengo los contadores del hashtable
	        
		// Realizamos una suma de todas las personas por puerta
		for (int valor : valores) {
			suma += valor;
			assert valor <= 20 && valor >= 0;
		}
		
		// El invariante dice que el numero total de personas debe de ser igual a la suma de las personas por puerta
        assert suma == totalPersonas : "El contador total de personas no es igual al contador de personas por puerta";
        assert totalPersonas >= 0;
        
        notifyAll(); // notifica que ha salido alguien del parque
	}
	
	
	

}
