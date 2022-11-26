package weblogic.messaging.kernel;

import weblogic.messaging.Message;

public interface Sink {
   Kernel getKernel();

   Quota getQuota();

   boolean isDurable();

   KernelRequest send(Message var1, SendOptions var2) throws KernelException;
}
