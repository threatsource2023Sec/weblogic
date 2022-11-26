package weblogic.jms.extensions;

import javax.jms.ExceptionListener;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Topic;
import org.w3c.dom.Document;
import weblogic.jms.common.JMSConstants;

public interface WLJMSContext extends JMSContext {
   String RECONNECT_POLICY_NONE = JMSConstants.RECONNECT_POLICY_NONE;
   String RECONNECT_POLICY_PRODUCER = JMSConstants.RECONNECT_POLICY_PRODUCER;
   String RECONNECT_POLICY_ALL = JMSConstants.RECONNECT_POLICY_ALL;
   String CLIENT_ID_POLICY_RESTRICTED = JMSConstants.CLIENT_ID_POLICY_RESTRICTED_STRING;
   String CLIENT_ID_POLICY_UNRESTRICTED = JMSConstants.CLIENT_ID_POLICY_UNRESTRICTED_STRING;
   String SUBSCRIPTION_EXCLUSIVE = JMSConstants.SUBSCRIPTION_EXCLUSIVE;
   String SUBSCRIPTION_SHARABLE = JMSConstants.SUBSCRIPTION_SHARABLE;
   int NO_ACKNOWLEDGE = 4;
   int MULTICAST_NO_ACKNOWLEDGE = 128;
   int KEEP_OLD = 0;
   int KEEP_NEW = 1;

   void setReconnectPolicy(String var1) throws IllegalArgumentException;

   String getReconnectPolicy();

   void setReconnectBlockingMillis(long var1) throws IllegalArgumentException;

   long getReconnectBlockingMillis();

   void setTotalReconnectPeriodMillis(long var1) throws IllegalArgumentException;

   long getTotalReconnectPeriodMillis();

   void setClientID(String var1, String var2) throws IllegalArgumentException;

   String getClientIDPolicy();

   String getSubscriptionSharingPolicy();

   void setSubscriptionSharingPolicy(String var1) throws IllegalArgumentException;

   XMLMessage createXMLMessage();

   XMLMessage createXMLMessage(String var1);

   XMLMessage createXMLMessage(Document var1);

   void setSessionExceptionListener(ExceptionListener var1);

   int getMessagesMaximum();

   void setMessagesMaximum(int var1);

   int getOverrunPolicy();

   void setOverrunPolicy(int var1);

   long getRedeliveryDelay();

   void setRedeliveryDelay(long var1);

   void acknowledge(Message var1);

   void unsubscribe(Topic var1, String var2);
}
