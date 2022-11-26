package weblogic.messaging.kernel;

public interface ListenRequest {
   void incrementCount(int var1) throws KernelException;

   int getCount();

   void stop();

   void stopAndWait();

   Queue getQueue();
}
