package commonj.work;

public interface Work extends Runnable {
   void release();

   boolean isDaemon();
}
