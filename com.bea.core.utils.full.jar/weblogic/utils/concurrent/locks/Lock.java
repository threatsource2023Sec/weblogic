package weblogic.utils.concurrent.locks;

public interface Lock {
   void lock();

   boolean tryLock();

   void unlock();
}
