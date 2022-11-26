package com.bea.httppubsub.descriptor;

public interface ChannelPersistenceBean {
   int getMaxPersistentMessageDurationSecs();

   void setMaxPersistentMessageDurationSecs(int var1);

   String getPersistentStore();

   void setPersistentStore(String var1);
}
