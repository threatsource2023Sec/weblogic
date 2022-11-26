package com.bea.httppubsub.descriptor;

public interface ChannelBean {
   String getChannelPattern();

   void setChannelPattern(String var1);

   ChannelPersistenceBean getChannelPersistence();

   ChannelPersistenceBean createChannelPersistence();

   String getJmsHandlerName();

   void setJmsHandlerName(String var1);

   String[] getMessageFilters();

   void addMessageFilter(String var1);

   void removeMessageFilter(String var1);
}
