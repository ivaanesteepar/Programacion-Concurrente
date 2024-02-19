package p04;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import p03.Hilo;

public class CasoEstudio4 {

    public static void main(String[] args) {

    	Thread hilox = new Thread(new Hilo('x', 1, 4));
        Thread hilo_ = new Thread(new Hilo('-', 3, 2));
        Thread hiloO = new Thread(new Hilo('o', 6, 2));
                
        hilox.start();
        hilo_.start();
        hiloO.start();
        
        hiloO.setPriority(Thread.MAX_PRIORITY);

//        try {
//            hilox.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        
        // Ejecutar con diferentes tipos de ejecutores
    	ejecutarConExecutor(Executors.newCachedThreadPool(), hilox);
        ejecutarConExecutor(Executors.newFixedThreadPool(1), hiloO);
        ejecutarConExecutor(Executors.newSingleThreadExecutor(), hilo_);
        //ejecutarConExecutor(Executors.newFixedThreadPool(2), hiloParametrizado);
        //ejecutarConExecutor(Executors.newFixedThreadPool(3), hiloParametrizado);
    }

    private static void ejecutarConExecutor(ExecutorService executor, Runnable hilo) {
        executor.execute(hilo);
        executor.shutdown();
    }
}
