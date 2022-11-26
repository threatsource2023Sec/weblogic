package com.bea.httppubsub.descriptor;

public interface JmsHandlerBean {
   String getJmsProviderUrl();

   void setJmsProviderUrl(String var1);

   String getConnectionFactoryJndiName();

   void setConnectionFactoryJndiName(String var1);

   String getTopicJndiName();

   void setTopicJndiName(String var1);
}
