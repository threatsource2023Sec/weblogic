package weblogic.messaging.kernel;

public abstract class ReceiveRequest extends KernelRequest {
   public abstract void stop();

   public abstract void start() throws KernelException;

   public abstract void cancel();
}
