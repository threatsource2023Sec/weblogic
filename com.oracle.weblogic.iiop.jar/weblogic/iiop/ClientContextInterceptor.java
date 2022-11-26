package weblogic.iiop;

import javax.validation.constraints.NotNull;
import weblogic.iiop.contexts.ServiceContext;
import weblogic.iiop.contexts.ServiceContextList;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.messages.RequestMessage;
import weblogic.iiop.protocol.CorbaInputStream;

abstract class ClientContextInterceptor {
   private int flag;
   private int serviceContextId;

   ClientContextInterceptor(int flag, int serviceContextId) {
      this.flag = flag;
      this.serviceContextId = serviceContextId;
   }

   int getFlag() {
      return this.flag;
   }

   void addServiceContexts(@NotNull RequestMessage requestMessage, @NotNull EndPoint endPoint, IOR ior, int contextsNeeded) {
      if (!this.dontNeedContext(contextsNeeded)) {
         if (this.mayAddContext(ior)) {
            requestMessage.addServiceContext(this.getContext(endPoint));
         }
      }
   }

   private boolean dontNeedContext(int contextsNeeded) {
      return (contextsNeeded & this.flag) == 0;
   }

   protected boolean mayAddContext(@NotNull IOR ior) {
      return true;
   }

   protected abstract ServiceContext getContext(@NotNull EndPoint var1);

   int getServiceContextFlagToClear(@NotNull RequestMessage requestMessage) {
      return this.isServiceContextPresent(requestMessage) ? this.flag : 0;
   }

   private boolean isServiceContextPresent(@NotNull RequestMessage requestMessage) {
      return requestMessage.getServiceContext(this.serviceContextId) != null;
   }

   public void handleReceivedReply(@NotNull ServiceContextList serviceContexts, @NotNull EndPoint endPoint, CorbaInputStream inputStream) {
   }
}
