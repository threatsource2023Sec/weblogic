package com.bea.httppubsub.descriptor;

public interface ChannelResourceCollectionBean {
   String getChannelResourceName();

   void setChannelResourceName(String var1);

   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String[] getChannelPatterns();

   void addChannelPattern(String var1);

   void removeChannelPattern(String var1);

   void setChannelPatterns(String[] var1);

   String[] getChannelOperations();

   void addChannelOperation(String var1);

   void removeChannelOperation(String var1);

   void setChannelOperations(String[] var1);
}
