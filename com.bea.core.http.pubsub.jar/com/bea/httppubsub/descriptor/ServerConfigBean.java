package com.bea.httppubsub.descriptor;

public interface ServerConfigBean {
   String getName();

   void setName(String var1);

   String getWorkManager();

   void setWorkManager(String var1);

   boolean isPublishWithoutConnectAllowed();

   void setPublishWithoutConnectAllowed(boolean var1);

   int getConnectionIdleTimeoutSecs();

   void setConnectionIdleTimeoutSecs(int var1);

   int getClientTimeoutSecs();

   void setClientTimeoutSecs(int var1);

   int getIntervalMillisecs();

   void setIntervalMillisecs(int var1);

   int getMultiFrameIntervalMillisecs();

   void setMultiFrameIntervalMillisecs(int var1);

   int getPersistentClientTimeoutSecs();

   void setPersistentClientTimeoutSecs(int var1);

   SupportedTransportBean getSupportedTransport();

   SupportedTransportBean createSupportedTransport();

   String getCookiePath();

   void setCookiePath(String var1);

   boolean isCookiePathSet();
}
