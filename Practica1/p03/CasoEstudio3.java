package p03;

public class CasoEstudio3 {

    public static void main(String[] args) {
        
        Thread hilox = new Thread(new Hilo('x', 1, 4));
        Thread hilo_ = new Thread(new Hilo('-', 2, 2));
        Thread hiloO = new Thread(new Hilo('o', 3, 2));
                
        hilox.start();
        hilo_.start();
        hiloO.start();
        
        hiloO.setPriority(Thread.MAX_PRIORITY);

        try {
            hilox.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
