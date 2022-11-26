package weblogic.messaging.kernel;

public interface Topic extends Destination {
   void subscribe(Queue var1, Object var2, KernelRequest var3) throws KernelException;

   void unsubscribe(Queue var1, KernelRequest var2) throws KernelException;
}
