package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface ClientParamsBean extends SettableBean {
   String getClientId();

   void setClientId(String var1) throws IllegalArgumentException;

   String getClientIdPolicy();

   void setClientIdPolicy(String var1) throws IllegalArgumentException;

   String getSubscriptionSharingPolicy();

   void setSubscriptionSharingPolicy(String var1) throws IllegalArgumentException;

   String getAcknowledgePolicy();

   void setAcknowledgePolicy(String var1) throws IllegalArgumentException;

   boolean isAllowCloseInOnMessage();

   void setAllowCloseInOnMessage(boolean var1) throws IllegalArgumentException;

   int getMessagesMaximum();

   void setMessagesMaximum(int var1) throws IllegalArgumentException;

   String getMulticastOverrunPolicy();

   void setMulticastOverrunPolicy(String var1) throws IllegalArgumentException;

   void setSynchronousPrefetchMode(String var1) throws IllegalArgumentException;

   String getSynchronousPrefetchMode();

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
}
