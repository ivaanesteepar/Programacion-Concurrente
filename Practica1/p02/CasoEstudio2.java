package p02;

public class CasoEstudio2 {
	
	public static void main(String[] args) {
		
		Thread hilox = new Thread(new HiloX());
		Thread hilo_ = new Thread(new Hilo_());
		Thread hiloO = new Thread(new HiloO());
		
		hilox.start(); //Se ejecuta el hilo de las x

		try {
			hilox.join(); //Hasta que no acabe el hilo de las x, no se ejecutará lo siguiente
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Una vez acabado el hilo de las x, se ejecuta esto
		System.out.print("Fin hilo x");
		hilo_.start();
		hiloO.start();
		
	}
}
