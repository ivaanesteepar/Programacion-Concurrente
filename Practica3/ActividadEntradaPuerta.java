package p01;

public class ActividadEntradaPuerta implements Runnable{
	
	private final IParque parque;
    private final String puerta;
    private static final int MAX_ENTRADAS_PUERTA = 20;
    
	
	public ActividadEntradaPuerta(IParque parque, String puerta) {
		this.parque = parque;
		this.puerta = puerta;
	}

	
	@Override
	public void run() {
		
		// Actividad que tenga que hacer
		for (int i = 1; i <= MAX_ENTRADAS_PUERTA; i++) {
			
			// Se entra al parque por la puerta correspondiente
            parque.entrarParque(puerta);
		
			// Sleep random 
			int aleatorio = (int) Math.floor(Math.random() * (0 - 5 + 1)); 
			try { 
				java.util.concurrent.TimeUnit.SECONDS.sleep(aleatorio); 
			} catch (InterruptedException e) {
				e.printStackTrace(); 
			}
		}
	}
	
}
