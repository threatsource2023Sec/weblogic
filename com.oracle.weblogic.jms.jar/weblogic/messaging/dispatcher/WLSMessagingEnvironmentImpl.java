package weblogic.messaging.dispatcher;

import weblogic.kernel.KernelStatus;

public class WLSMessagingEnvironmentImpl extends MessagingEnvironment {
   public boolean isServer() {
      return KernelStatus.isServer();
   }

   public Class getUtilClass() {
      if (this.isServer()) {
         return ServerCrossDomainUtil.class;
      } else {
         try {
            return Class.forName("weblogic.messaging.dispatcher.T3ClientCrossDomainUtil");
         } catch (ClassNotFoundException var4) {
            try {
               return Class.forName("weblogic.messaging.dispatcher.IIOPClientCrossDomainUtil");
            } catch (ClassNotFoundException var3) {
               throw new AssertionError(var3);
            }
         }
      }
   }
}
