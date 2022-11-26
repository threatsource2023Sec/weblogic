package weblogic.messaging.interception.internal;

import javax.xml.rpc.handler.MessageContext;
import weblogic.messaging.interception.exceptions.InterceptionProcessorException;
import weblogic.messaging.interception.interfaces.CarrierCallBack;
import weblogic.messaging.interception.interfaces.InterceptionCallBack;

public class InterceptionCallBackImpl implements InterceptionCallBack {
   private CarrierCallBack callBack;
   private Association association;
   private boolean processOnly;
   private MessageContext context;

   InterceptionCallBackImpl(CarrierCallBack callBack, Association association, boolean processOnly, MessageContext context) {
      this.callBack = callBack;
      this.association = association;
      this.processOnly = processOnly;
      this.context = context;
   }

   public final void onCallBack(boolean continueOn) {
      if (this.processOnly) {
         this.association.updateAsyncMeessagesCount(true);
         this.callBack.onCallBack(true);
      } else {
         this.association.updateAsyncMeessagesCount(continueOn);
         this.callBack.onCallBack(continueOn);
      }

   }

   public final void onException(InterceptionProcessorException exception) {
      this.association.updateAsyncMeessagesCount(false);
      this.callBack.onException(exception);
   }
}
