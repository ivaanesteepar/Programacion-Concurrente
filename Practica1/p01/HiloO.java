package p01;

public class HiloO implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 100; i++) {
			System.out.print("o");
		}
		System.out.println();
	}

}
