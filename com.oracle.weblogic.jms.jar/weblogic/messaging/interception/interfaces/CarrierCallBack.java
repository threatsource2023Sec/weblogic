package weblogic.messaging.interception.interfaces;

import weblogic.messaging.interception.exceptions.InterceptionProcessorException;

public interface CarrierCallBack {
   void onCallBack(boolean var1);

   void onException(InterceptionProcessorException var1);
}
