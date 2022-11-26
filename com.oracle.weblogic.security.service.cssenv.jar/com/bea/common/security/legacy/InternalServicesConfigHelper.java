package com.bea.common.security.legacy;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceEngineConfig;
import java.util.Properties;
import javax.net.ssl.SSLContext;

public interface InternalServicesConfigHelper {
   String getLDAPSSLSocketFactoryLookupServiceName();

   String getJAXPFactoryServiceName();

   String getNamedSQLConnectionLookupServiceName();

   String getSAMLKeyServiceName();

   String getStoreServiceName();

   String getBootStrapServiceName();

   String getSessionServiceName();

   LDAPSSLSocketFactoryLookupServiceConfigCustomizer getLDAPSSLSocketFactoryLookupServiceCustomizer();

   JAXPFactoryServiceConfigCustomizer getJAXPFactoryServiceCustomizer();

   NamedSQLConnectionLookupServiceConfigCustomizer getNamedSQLConnectionLookupServiceCustomizer();

   SAMLKeyServiceConfigCustomizer getSAMLKeyServiceCustomizer();

   StoreServiceConfigCustomizer getStoreServiceCustomizer();

   BootStrapServiceConfigCustomizer getBootStrapServiceCustomizer();

   ServiceConfigCustomizer getSessionServiceCustomizer();

   void addToConfig(ServiceEngineConfig var1, String var2);

   public interface BootStrapServiceConfigCustomizer extends ServiceConfigCustomizer {
      void setBootStrapProperties(Properties var1);
   }

   public interface StoreServiceConfigCustomizer extends ServiceConfigCustomizer {
      void setStoreProperties(Properties var1);

      void setConnectionProperties(Properties var1);

      void setNotificationProperties(Properties var1);
   }

   public interface SAMLKeyServiceConfigCustomizer extends ServiceConfigCustomizer {
      void setKeyStoreFile(String var1);

      void setKeyStoreType(String var1);

      void setKeyStorePassPhrase(char[] var1);

      void setStoreValidationPollInterval(int var1);

      void setDefaultKeyAlias(String var1);

      void setDefaultKeyPassphrase(char[] var1);
   }

   public interface NamedSQLConnectionLookupServiceConfigCustomizer extends ServiceConfigCustomizer {
      void addNamedSQLConnectionPool(String var1, String var2, int var3, int var4, String var5, Properties var6, String var7, String var8) throws ServiceConfigurationException;

      void addNamedSQLConnectionPool(String var1, String var2, int var3, int var4, boolean var5, int var6, String var7, Properties var8, String var9, String var10, String var11, Properties var12, String var13, String var14) throws ServiceConfigurationException;
   }

   public interface JAXPFactoryServiceConfigCustomizer extends ServiceConfigCustomizer {
      void setDocumentBuilderFactoryClassName(String var1);

      void setTransformerFactoryClassName(String var1);
   }

   public interface LDAPSSLSocketFactoryLookupServiceConfigCustomizer extends ServiceConfigCustomizer {
      void setSSLContext(SSLContext var1);
   }
}
