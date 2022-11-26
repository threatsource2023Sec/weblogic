package weblogic.connector.external.impl;

import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import weblogic.connector.common.Debug;
import weblogic.connector.utils.TypeUtils;
import weblogic.descriptor.DescriptorManager;
import weblogic.j2ee.descriptor.AuthenticationMechanismBean;
import weblogic.j2ee.descriptor.ConnectionDefinitionBean;
import weblogic.j2ee.descriptor.ConnectionFactoryResourceBean;
import weblogic.j2ee.descriptor.ConnectorBean;
import weblogic.j2ee.descriptor.JavaEEPropertyBean;
import weblogic.j2ee.descriptor.wl.ConfigPropertiesBean;
import weblogic.j2ee.descriptor.wl.ConfigPropertyBean;
import weblogic.j2ee.descriptor.wl.ConnectionDefinitionPropertiesBean;
import weblogic.j2ee.descriptor.wl.ConnectionInstanceBean;
import weblogic.j2ee.descriptor.wl.LoggingBean;
import weblogic.j2ee.descriptor.wl.PoolParamsBean;
import weblogic.j2ee.descriptor.wl.WeblogicConnectorBean;

public class ConnectionDefinitionHelper {
   private static final String WEBLOGIC_POOL_PARAS = "weblogic.pool-parms.";
   private static final String WEBLOGIC_LOGGING = "weblogic.logging.";
   private static final String WEBLOGIC_AUTHMECH = "weblogic.authentication-mechanism.";
   private static final String WEBLOGIC_REAUTH_SUPPORT = "weblogic.reauthentication-support";
   private static final String WEBLOGIC_RES_AUTH = "weblogic.res-auth";
   private final ConnectorBean connBean;
   private final WeblogicConnectorBean wlConnBean;
   private static SoftReference dmRef = null;
   private static final Map nameToMethod = new HashMap();

   public ConnectionDefinitionHelper(ConnectorBean connBean, WeblogicConnectorBean wlConnBean) {
      this.connBean = connBean;
      this.wlConnBean = wlConnBean;
   }

   private static DescriptorManager getDescriptorManager() {
      if (dmRef == null || dmRef.get() == null) {
         dmRef = new SoftReference(new DescriptorManager());
      }

      return (DescriptorManager)dmRef.get();
   }

   ConnectionDefinitionBean getConnectionDefinitionBean(String interfaceName) {
      ConnectionDefinitionBean[] connTypeBeans = this.connBean.getResourceAdapter().getOutboundResourceAdapter().getConnectionDefinitions();
      ConnectionDefinitionBean[] var3 = connTypeBeans;
      int var4 = connTypeBeans.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ConnectionDefinitionBean bean = var3[var5];
         if (interfaceName.equals(bean.getConnectionFactoryInterface())) {
            return bean;
         }
      }

      return null;
   }

   weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean getWLConnectionDefinitionGroup(String connectionFactoryInterface) {
      if (!this.wlConnBean.isOutboundResourceAdapterSet()) {
         return null;
      } else {
         weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean[] var2 = this.wlConnBean.getOutboundResourceAdapter().getConnectionDefinitionGroups();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean bean = var2[var4];
            if (connectionFactoryInterface.equals(bean.getConnectionFactoryInterface())) {
               return bean;
            }
         }

         return null;
      }
   }

   weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean getOrCreateWLConnectionDefinition(String interfaceName) {
      weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean groupBean = this.getWLConnectionDefinitionGroup(interfaceName);
      if (groupBean == null) {
         groupBean = (weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean)getDescriptorManager().createDescriptorRoot(weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean.class).getRootBean();
         groupBean.setConnectionFactoryInterface(interfaceName);
      }

      return groupBean;
   }

   ConnectionInstanceBean createDummyConnectionInstanceBean(ConnectionFactoryResourceBean cfBean) {
      ConnectionInstanceBean wlConnInstance = (ConnectionInstanceBean)getDescriptorManager().createDescriptorRoot(ConnectionInstanceBean.class).getRootBean();
      wlConnInstance.setJNDIName(cfBean.getName());
      wlConnInstance.setDescription(cfBean.getDescription());
      if (cfBean.getMaxPoolSize() >= 0) {
         wlConnInstance.getConnectionProperties().getPoolParams().setMaxCapacity(cfBean.getMaxPoolSize());
      }

      if (cfBean.getMinPoolSize() >= 0) {
         wlConnInstance.getConnectionProperties().getPoolParams().setInitialCapacity(cfBean.getMinPoolSize());
      }

      if (cfBean.getTransactionSupport().trim().length() != 0) {
         wlConnInstance.getConnectionProperties().setTransactionSupport(cfBean.getTransactionSupport());
      }

      convertProperties(cfBean.getProperties(), wlConnInstance.getConnectionProperties());
      return wlConnInstance;
   }

   private static AuthenticationMechanismBean getFirstAuthenticationMechanismBean(ConnectionDefinitionPropertiesBean propertyBean) {
      return propertyBean.getAuthenticationMechanisms() != null && propertyBean.getAuthenticationMechanisms().length != 0 ? propertyBean.getAuthenticationMechanisms()[0] : propertyBean.createAuthenticationMechanism();
   }

   protected static void convertProperties(JavaEEPropertyBean[] beans, ConnectionDefinitionPropertiesBean propertyBean) {
      if (beans != null && beans.length != 0) {
         ConfigPropertiesBean wlBeans = propertyBean.getProperties();
         JavaEEPropertyBean[] var3 = beans;
         int var4 = beans.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            JavaEEPropertyBean bean = var3[var5];
            String name = bean.getName().trim();
            Method method = (Method)nameToMethod.get(name);
            if (method == null) {
               ConfigPropertyBean wlBean = wlBeans.createProperty();
               wlBean.setName(bean.getName().trim());
               wlBean.setValue(bean.getValue().trim());
            } else if (name.startsWith("weblogic.pool-parms.")) {
               setProperty(propertyBean.getPoolParams(), method, bean.getValue());
            } else if (name.startsWith("weblogic.logging.")) {
               setProperty(propertyBean.getLogging(), method, bean.getValue());
            } else if (name.startsWith("weblogic.authentication-mechanism.")) {
               setProperty(getFirstAuthenticationMechanismBean(propertyBean), method, bean.getValue());
            } else if (name.equals("weblogic.reauthentication-support") || name.equals("weblogic.res-auth")) {
               setProperty(propertyBean, method, bean.getValue());
            }
         }

      }
   }

   protected static void setProperty(Object aimObject, Method m, String value) {
      Class paraType = m.getParameterTypes()[0];
      if (!TypeUtils.isSupportedType(paraType.getName())) {
         Debug.throwAssertionError("Unsupported type:" + paraType.getName());
      }

      String realValue = value == null ? null : value.trim();
      Object para = TypeUtils.getValueByType(realValue, paraType.getName());

      try {
         m.invoke(aimObject, para);
      } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var7) {
         Debug.throwAssertionError("failed to invoke setter", var7);
      }

   }

   static {
      try {
         nameToMethod.put("weblogic.pool-parms.match-connections-supported", PoolParamsBean.class.getMethod("setMatchConnectionsSupported", Boolean.TYPE));
         nameToMethod.put("weblogic.pool-parms.match-connections-supported", PoolParamsBean.class.getMethod("setMatchConnectionsSupported", Boolean.TYPE));
         nameToMethod.put("weblogic.pool-parms.use-first-available", PoolParamsBean.class.getMethod("setUseFirstAvailable", Boolean.TYPE));
         nameToMethod.put("weblogic.pool-parms.capacity-increment", PoolParamsBean.class.getMethod("setCapacityIncrement", Integer.TYPE));
         nameToMethod.put("weblogic.pool-parms.shrinking-enabled", PoolParamsBean.class.getMethod("setShrinkingEnabled", Boolean.TYPE));
         nameToMethod.put("weblogic.pool-parms.shrink-frequency-seconds", PoolParamsBean.class.getMethod("setShrinkFrequencySeconds", Integer.TYPE));
         nameToMethod.put("weblogic.pool-parms.highest-num-waiters", PoolParamsBean.class.getMethod("setHighestNumWaiters", Integer.TYPE));
         nameToMethod.put("weblogic.pool-parms.highest-num-unavailable", PoolParamsBean.class.getMethod("setHighestNumUnavailable", Integer.TYPE));
         nameToMethod.put("weblogic.pool-parms.connection-creation-retry-frequency-seconds", PoolParamsBean.class.getMethod("setConnectionCreationRetryFrequencySeconds", Integer.TYPE));
         nameToMethod.put("weblogic.pool-parms.connection-reserve-timeout-seconds", PoolParamsBean.class.getMethod("setConnectionReserveTimeoutSeconds", Integer.TYPE));
         nameToMethod.put("weblogic.pool-parms.test-frequency-seconds", PoolParamsBean.class.getMethod("setTestFrequencySeconds", Integer.TYPE));
         nameToMethod.put("weblogic.pool-parms.test-connections-on-create", PoolParamsBean.class.getMethod("setTestConnectionsOnCreate", Boolean.TYPE));
         nameToMethod.put("weblogic.pool-parms.test-connections-on-release", PoolParamsBean.class.getMethod("setTestConnectionsOnRelease", Boolean.TYPE));
         nameToMethod.put("weblogic.pool-parms.test-connections-on-reserve", PoolParamsBean.class.getMethod("setTestConnectionsOnReserve", Boolean.TYPE));
         nameToMethod.put("weblogic.pool-parms.profile-harvest-frequency-seconds", PoolParamsBean.class.getMethod("setProfileHarvestFrequencySeconds", Integer.TYPE));
         nameToMethod.put("weblogic.pool-parms.ignore-in-use-connections-enabled", PoolParamsBean.class.getMethod("setIgnoreInUseConnectionsEnabled", Boolean.TYPE));
         nameToMethod.put("weblogic.logging.log-filename", LoggingBean.class.getMethod("setLogFilename", String.class));
         nameToMethod.put("weblogic.logging.logging-enabled", LoggingBean.class.getMethod("setLoggingEnabled", Boolean.TYPE));
         nameToMethod.put("weblogic.logging.rotation-type", LoggingBean.class.getMethod("setRotationType", String.class));
         nameToMethod.put("weblogic.logging.number-of-files-limited", LoggingBean.class.getMethod("setNumberOfFilesLimited", Boolean.TYPE));
         nameToMethod.put("weblogic.logging.file-count", LoggingBean.class.getMethod("setFileCount", Integer.TYPE));
         nameToMethod.put("weblogic.logging.file-size-limit", LoggingBean.class.getMethod("setFileSizeLimit", Integer.TYPE));
         nameToMethod.put("weblogic.logging.rotate-log-on-startup", LoggingBean.class.getMethod("setRotateLogOnStartup", Boolean.TYPE));
         nameToMethod.put("weblogic.logging.log-file-rotation-dir", LoggingBean.class.getMethod("setLogFileRotationDir", String.class));
         nameToMethod.put("weblogic.logging.rotation-time", LoggingBean.class.getMethod("setRotationTime", String.class));
         nameToMethod.put("weblogic.logging.file-time-span", LoggingBean.class.getMethod("setFileTimeSpan", Integer.TYPE));
         nameToMethod.put("weblogic.logging.date-format-pattern", LoggingBean.class.getMethod("setDateFormatPattern", String.class));
         nameToMethod.put("weblogic.authentication-mechanism.authentication-mechanism-type", AuthenticationMechanismBean.class.getMethod("setAuthenticationMechanismType", String.class));
         nameToMethod.put("weblogic.authentication-mechanism.credential-interface", AuthenticationMechanismBean.class.getMethod("setCredentialInterface", String.class));
         nameToMethod.put("weblogic.reauthentication-support", ConnectionDefinitionPropertiesBean.class.getMethod("setReauthenticationSupport", Boolean.TYPE));
         nameToMethod.put("weblogic.res-auth", ConnectionDefinitionPropertiesBean.class.getMethod("setResAuth", String.class));
      } catch (Exception var1) {
         Debug.throwAssertionError("failed to initialize map for setter", var1);
      }

   }
}
