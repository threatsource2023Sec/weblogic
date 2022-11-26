package com.bea.common.ldap.properties;

import com.bea.common.security.ProvidersLogger;
import com.bea.common.store.service.StoreInitializationException;
import com.bea.common.store.service.config.StoreServicePropertiesConfigurator;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import org.apache.openjpa.conf.OpenJPAVersion;

public class LDAPStoreServicePropertiesConfigurator implements StoreServicePropertiesConfigurator {
   public static final String USERNAME = "Username";
   public static final String PASSWORD = "Password";
   public static final String CONNECTION_URL = "ConnectionURL";

   public Properties convertStoreProperties(Properties storeProperties, Properties connectionProperties, Properties notificationProperties) throws StoreInitializationException {
      Properties jdoProperties = new Properties();
      jdoProperties.put("kodo.BrokerFactory", "abstractstore");
      jdoProperties.put("org.apache.openjpa.abstractstore.AbstractStoreManager", "com.bea.common.ldap.LDAPStoreManager");
      jdoProperties.put("openjpa.abstractstore.AbstractStoreManager", "com.bea.common.ldap.LDAPStoreManager");
      jdoProperties.setProperty("kodo.Optimistic", "false");
      if (storeProperties == null) {
         throw new StoreInitializationException(ProvidersLogger.getStoreServicePropertiesIsNull());
      } else {
         String property = storeProperties.getProperty("Username");
         if (property == null) {
            throw new StoreInitializationException(ProvidersLogger.getStoreServicePropertiesHasNullField("Username"));
         } else {
            jdoProperties.setProperty("kodo.ConnectionUserName", property);
            property = storeProperties.getProperty("Password");
            if (property != null) {
               jdoProperties.setProperty("kodo.ConnectionPassword", property);
            }

            property = storeProperties.getProperty("ConnectionURL");
            if (property == null) {
               throw new StoreInitializationException(ProvidersLogger.getStoreServicePropertiesHasNullField("ConnectionURL"));
            } else {
               if (Boolean.valueOf(connectionProperties.getProperty("embedded"))) {
                  try {
                     URI uri = new URI(property);
                     if (uri.getHost() == null || uri.getHost().trim().length() == 0 || uri.getPort() < 1 || uri.getPort() > 65535) {
                        throw new StoreInitializationException(ProvidersLogger.getStoreServiceInvalidURL(property));
                     }
                  } catch (URISyntaxException var9) {
                     throw new StoreInitializationException(ProvidersLogger.getStoreServiceInvalidURL(property));
                  }
               }

               jdoProperties.setProperty("kodo.ConnectionURL", property);
               if (connectionProperties != null) {
                  StringBuilder cp = new StringBuilder();
                  Iterator it = connectionProperties.entrySet().iterator();

                  while(it.hasNext()) {
                     Map.Entry e = (Map.Entry)it.next();
                     cp.append(e.getKey().toString());
                     cp.append('=');
                     cp.append(e.getValue().toString());
                     if (it.hasNext()) {
                        cp.append(", ");
                     }
                  }

                  jdoProperties.setProperty("kodo.ConnectionProperties", cp.toString());
                  if (OpenJPAVersion.MAJOR_RELEASE >= 1 && OpenJPAVersion.MINOR_RELEASE >= 1) {
                     StringBuilder metaCache = new StringBuilder();
                     metaCache.append("default(Id=org.apache.openjpa.conf.MetaDataCacheMaintenance,");
                     metaCache.append("InputResource=com/bea/common/security/store/data/openjpa-ldap-metadata.ser, ConsumeSerializationErrors=true,");
                     metaCache.append("ValidationPolicy=org.apache.openjpa.conf.OpenJPAVersionAndConfigurationTypeValidationPolicy)");
                     jdoProperties.setProperty("openjpa.CacheMarshallers", metaCache.toString());
                  }
               }

               return jdoProperties;
            }
         }
      }
   }
}
