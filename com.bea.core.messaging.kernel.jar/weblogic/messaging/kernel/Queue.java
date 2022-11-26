package weblogic.messaging.kernel;

public interface Queue extends Destination, Source {
   Cursor createCursor(boolean var1, Expression var2, int var3) throws KernelException;

   void empty(KernelRequest var1) throws KernelException;

   long getLastMessagesReceivedTime();
}
