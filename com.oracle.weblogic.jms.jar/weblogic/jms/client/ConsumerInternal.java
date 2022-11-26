package weblogic.jms.client;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.QueueReceiver;
import javax.jms.TopicSubscriber;
import weblogic.jms.common.JMSID;
import weblogic.messaging.ID;
import weblogic.utils.expressions.ExpressionEvaluator;

public interface ConsumerInternal extends MessageConsumer, TopicSubscriber, QueueReceiver, ClientRuntimeInfo {
   int RETRIEVE = 0;
   int CREATE = 1;
   int REMOVE_SUBSCRIPTION = 2;
   long TIMEOUT_NEVER = Long.MAX_VALUE;
   long TIMEOUT_NO_WAIT = 9223372036854775806L;
   long TIMEOUT_NO_VALUE = 9223372036854775805L;
   long TIMEOUT_RESERVED = 9223372036854775805L;

   JMSID getJMSID();

   Destination getDestination();

   void setWindowCurrent(int var1);

   int getWindowCurrent();

   int getWindowMaximum();

   void setExpectedSequenceNumber(long var1);

   void setExpectedSequenceNumber(long var1, boolean var3);

   JMSSession getSession();

   boolean privateGetNoLocal();

   ExpressionEvaluator getExpressionEvaluator();

   boolean isDurable();

   void removeDurableConsumer();

   void setClosed(boolean var1);

   void setId(JMSID var1);

   ID getId();

   void decrementWindowCurrent(boolean var1) throws JMSException;

   long getExpectedSequenceNumber();

   boolean isClosed();

   JMSMessageContext getMessageListenerContext();

   void setRuntimeMBeanName(String var1);

   String getPartitionName();
}
