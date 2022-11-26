package weblogic.jms.extensions;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.TopicConnection;
import weblogic.jms.common.JMSConstants;

public interface WLConnection extends QueueConnection, TopicConnection, Connection {
   String RECONNECT_POLICY_NONE = JMSConstants.RECONNECT_POLICY_NONE;
   String RECONNECT_POLICY_PRODUCER = JMSConstants.RECONNECT_POLICY_PRODUCER;
   String RECONNECT_POLICY_ALL = JMSConstants.RECONNECT_POLICY_ALL;
   int ACKNOWLEDGE_ALL = 1;
   int ACKNOWLEDGE_PREVIOUS = 2;
   String CLIENT_ID_POLICY_RESTRICTED = JMSConstants.CLIENT_ID_POLICY_RESTRICTED_STRING;
   String CLIENT_ID_POLICY_UNRESTRICTED = JMSConstants.CLIENT_ID_POLICY_UNRESTRICTED_STRING;
   String SUBSCRIPTION_EXCLUSIVE = JMSConstants.SUBSCRIPTION_EXCLUSIVE;
   String SUBSCRIPTION_SHARABLE = JMSConstants.SUBSCRIPTION_SHARABLE;

   /** @deprecated */
   @Deprecated
   void setReconnectPolicy(String var1) throws IllegalArgumentException;

   /** @deprecated */
   @Deprecated
   String getReconnectPolicy();

   /** @deprecated */
   @Deprecated
   void setReconnectBlockingMillis(long var1) throws IllegalArgumentException;

   /** @deprecated */
   @Deprecated
   long getReconnectBlockingMillis();

   /** @deprecated */
   @Deprecated
   void setTotalReconnectPeriodMillis(long var1) throws IllegalArgumentException;

   /** @deprecated */
   @Deprecated
   long getTotalReconnectPeriodMillis();

   void setAcknowledgePolicy(int var1);

   int getAcknowledgePolicy();

   void setDispatchPolicy(String var1) throws IllegalArgumentException;

   void setClientID(String var1, String var2) throws JMSException, IllegalArgumentException;

   String getClientIDPolicy();

   String getSubscriptionSharingPolicy();

   void setSubscriptionSharingPolicy(String var1) throws JMSException, IllegalArgumentException;

   void setInboundDest(Destination var1) throws JMSException;
}
