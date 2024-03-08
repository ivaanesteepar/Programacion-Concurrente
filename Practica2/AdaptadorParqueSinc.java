package p01;

public class AdaptadorParqueSinc implements IParque{

	private IParque parque;
	
	private static AdaptadorParqueSinc instancia = new AdaptadorParqueSinc();
	
	private AdaptadorParqueSinc() {
		parque = new Parque();
	}
	
	@Override
	public synchronized void entrarParque(String puerta) {
		parque.entrarParque(puerta);
		
	}
	
	public static AdaptadorParqueSinc getInstancia() {
		return instancia;
	}

}
