package p01;

public class HiloX implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 100; i++) {
			System.out.print("x");
			
			if (i == 2) {
				Thread.yield();
			}
		}
		System.out.println();
	}

}
