package com.bea.common.security.servicecfg;

import java.util.Properties;

public interface StoreServiceConfig {
   Properties getStoreProperties();

   void setStoreProperties(Properties var1);

   Properties getConnectionProperties();

   void setConnectionProperties(Properties var1);

   Properties getNotificationProperties();

   void setNotificationProperties(Properties var1);
}
