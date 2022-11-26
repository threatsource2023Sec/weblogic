package weblogic.jms.common;

import javax.jms.Message;

public interface AsyncSendResponseInfo {
   Message getMessage();

   void setMessage(Message var1);

   long getAsyncFlowControlTime();

   void setAsyncFlowControlTime(long var1);

   int getDeliveryMode();

   int getPriority();

   int getRedeliveryLimit();

   boolean isDispatchOneWay();
}
