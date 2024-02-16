package p01;

public class CasoEstudio1 {
	public static void main(String[] args) {
		
		Thread hilox = new Thread(new HiloX());
		Thread hilo_ = new Thread(new Hilo_());
		Thread hiloO = new Thread(new HiloO());
		
		hilox.start();
		hilo_.start();
		hiloO.start();

	}
}
