package p01;

public class Hilo_ implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 100; i++) {
			System.out.print("-");
		}
		System.out.println();
	}
	
}
