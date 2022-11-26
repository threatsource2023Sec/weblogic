package com.bea.httppubsub.descriptor;

public interface JmsHandlerMappingBean {
   String getJmsHandlerName();

   void setJmsHandlerName(String var1);

   JmsHandlerBean getJmsHandler();

   JmsHandlerBean createJmsHandler();
}
