package p04;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CasoEstudio4 {

    public static void main(String[] args) {

    	Thread hilox = new Thread(new Hilo('x', 1, 4));
        Thread hilo_ = new Thread(new Hilo('-', 3, 2));
        Thread hiloO = new Thread(new Hilo('o', 6, 2));
        
        hiloO.setPriority(Thread.MAX_PRIORITY);
        

//        try {
//            hilox.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        
        // Creamos y definimos el ejecutor
        ExecutorService executor = Executors.newCachedThreadPool();
        
        
        // Ejecutar con diferentes tipos de ejecutores
    	executor.execute(hilox);
        executor.execute(hilo_);
        executor.execute(hiloO);

    }

}
