package weblogic.messaging.kernel;

import javax.transaction.xa.Xid;
import weblogic.messaging.Message;
import weblogic.utils.expressions.ExpressionMap;

public interface MessageElement extends ExpressionMap {
   Message getMessage();

   Queue getQueue();

   int getState();

   int getDeliveryCount();

   Group getGroup();

   Xid getXid();

   void setUserData(Object var1);

   Object getUserData();

   Sequence getSequence();

   long getSequenceNum();

   void setUserSequenceNum(long var1);

   long getUserSequenceNum();

   String getConsumerID();

   long getInternalSequenceNumber();
}
