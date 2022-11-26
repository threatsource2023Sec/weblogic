package weblogic.connector.configuration;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.stream.XMLStreamException;
import weblogic.application.PermissionsDescriptorLoader;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.connector.common.Debug;
import weblogic.connector.deploy.RarArchive;
import weblogic.connector.exception.RAConfigurationException;
import weblogic.connector.exception.WLRAConfigurationException;
import weblogic.connector.external.ConnectorUtils;
import weblogic.connector.external.RAInfo;
import weblogic.connector.utils.ConnectorAPContext;
import weblogic.descriptor.DescriptorManager;
import weblogic.j2ee.descriptor.AdminObjectBean;
import weblogic.j2ee.descriptor.ConnectionDefinitionBean;
import weblogic.j2ee.descriptor.ConnectorBean;
import weblogic.j2ee.descriptor.OutboundResourceAdapterBean;
import weblogic.j2ee.descriptor.PermissionsBean;
import weblogic.j2ee.descriptor.ResourceAdapterBean;
import weblogic.j2ee.descriptor.wl.AdminObjectGroupBean;
import weblogic.j2ee.descriptor.wl.AdminObjectInstanceBean;
import weblogic.j2ee.descriptor.wl.AdminObjectsBean;
import weblogic.j2ee.descriptor.wl.ConnectionDefinitionPropertiesBean;
import weblogic.j2ee.descriptor.wl.ConnectionInstanceBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.PoolParamsBean;
import weblogic.j2ee.descriptor.wl.WeblogicConnectorBean;
import weblogic.j2ee.descriptor.wl.WeblogicConnectorExtensionBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.utils.jars.VirtualJarFile;

public class DDUtil {
   private static final String CLASS_NAME = "weblogic.connector.configuration.DDUtil";

   public static RAInfo getRAInfo(RarArchive rar, File altDD, File altWlsDD, String moduleName, AppDeploymentMBean dmb, DeploymentPlanBean plan, ClassLoader classLoader, boolean insideEar, AdditionalAnnotatedClassesProvider provider) throws RAConfigurationException, WLRAConfigurationException {
      return getRAInfo(rar, altDD, altWlsDD, moduleName, dmb, plan, classLoader, insideEar, provider, (PermissionsBean)null, false);
   }

   public static RAInfo getRAInfo(RarArchive rar, File altDD, File altWlsDD, String moduleName, AppDeploymentMBean dmb, DeploymentPlanBean plan, ClassLoader classLoader, boolean insideEar, AdditionalAnnotatedClassesProvider provider, PermissionsBean permissionsBean, boolean customizedClassloading) throws RAConfigurationException, WLRAConfigurationException {
      RAInfo var19;
      try {
         Debug.enter("weblogic.connector.configuration.DDUtil", "getRAInfo()");
         Debug.parsing("Validating the RAR and the alternate descriptor");
         Debug.println((Object)"weblogic.connector.configuration.DDUtil", (String)".getRAInfo() Get the config directory");
         File configDir = null;
         String appId;
         if (dmb == null) {
            appId = moduleName;
         } else {
            appId = dmb.getApplicationIdentifier();
         }

         if (dmb != null && dmb.getPlanDir() != null) {
            configDir = new File(dmb.getLocalPlanDir());
         }

         Debug.parsing("Constructing the ConnectorDescriptor");
         ClassLoader cc1 = Thread.currentThread().getContextClassLoader();
         ConnectorBean connBean = null;
         WeblogicConnectorBean wlConnBean = null;
         ConnectorAPContext apcontext = null;

         try {
            Thread.currentThread().setContextClassLoader(DDUtil.class.getClassLoader());
            ConnectorDescriptor connDescriptor = ConnectorDescriptor.buildDescriptor(altDD, altWlsDD, rar, configDir, plan, getNameForMerging(rar.getOriginalRarFilename()), classLoader, insideEar, provider);
            apcontext = connDescriptor.getAnnotationProcessingContext();
            Debug.println((Object)"weblogic.connector.configuration.DDUtil", (String)".getRAInfo() Get the connector bean");
            connBean = connDescriptor.getConnectorBean();
            Debug.println((Object)"weblogic.connector.configuration.DDUtil", (String)".getRAInfo() Get the weblogic connector bean");
            wlConnBean = connDescriptor.getWeblogicConnectorBean();
         } finally {
            Thread.currentThread().setContextClassLoader(cc1);
         }

         if (connBean == null) {
            String exMsg;
            if (!(wlConnBean instanceof WeblogicConnectorExtensionBean)) {
               exMsg = Debug.getExceptionNeedsRAXML();
               throw new WLRAConfigurationException(exMsg);
            }

            if (!DDValidator.isLinkRef((WeblogicConnectorExtensionBean)wlConnBean)) {
               exMsg = Debug.getExceptionMustBeLinkRef();
               throw new WLRAConfigurationException(exMsg);
            }
         }

         if (wlConnBean == null) {
            wlConnBean = createDefaultWLConnBean(connBean, getDefaultBaseJndiName(appId, moduleName));
         }

         Debug.println((Object)"weblogic.connector.configuration.DDUtil", (String)".getRAInfo()Get the url");
         URL raURL = getRAURL(rar.getVirtualJarFile().getName());
         RAInfo raInfo = ConnectorUtils.raInfo.createRAInfo(connBean, wlConnBean, permissionsBean, raURL, moduleName, apcontext, customizedClassloading);
         var19 = raInfo;
      } finally {
         Debug.exit("weblogic.connector.configuration.DDUtil", "getRAInfo()");
      }

      return var19;
   }

   public static PermissionsBean getPermissionsBean(AppDeploymentMBean dmb, DeploymentPlanBean plan, RarArchive explodedRar, String moduleName) throws RAConfigurationException {
      if (System.getSecurityManager() == null) {
         return null;
      } else {
         VirtualJarFile virtualJarFile = explodedRar.getVirtualJarFile();
         if (virtualJarFile == null) {
            return null;
         } else {
            File configDir = null;
            if (dmb != null && dmb.getPlanDir() != null) {
               configDir = new File(dmb.getLocalPlanDir());
            }

            PermissionsDescriptorLoader loader = new PermissionsDescriptorLoader(virtualJarFile, configDir, plan, moduleName);

            try {
               return (PermissionsBean)loader.loadDescriptorBean();
            } catch (IOException var8) {
               throw new RAConfigurationException(var8);
            } catch (XMLStreamException var9) {
               throw new RAConfigurationException(var9);
            }
         }
      }
   }

   private static URL getRAURL(String raURLString) throws RAConfigurationException {
      URL var1;
      try {
         Debug.enter("weblogic.connector.configuration.DDUtil", "getRAURL()");
         var1 = new URL("file", (String)null, raURLString);
      } catch (MalformedURLException var5) {
         throw new RAConfigurationException(var5);
      } finally {
         Debug.exit("weblogic.connector.configuration.DDUtil", "getRAURL()");
      }

      return var1;
   }

   private static WeblogicConnectorBean createDefaultWLConnBean(ConnectorBean connBean, String defaultBaseJndiName) {
      Debug.parsing("Resource Adapter being deployed does not have weblogic-ra.xml -- a default is being created.");
      WeblogicConnectorBean wlConnBean = (WeblogicConnectorBean)(new DescriptorManager()).createDescriptorRoot(WeblogicConnectorBean.class).getRootBean();
      wlConnBean.setNativeLibdir("/temp/nativelibs/");
      if (connBean.getResourceAdapter().getResourceAdapterClass() != null) {
         Debug.parsing("Setting adapter jndi name to '" + getDefaultJndiNameForRABean(defaultBaseJndiName) + "'");
         wlConnBean.setJNDIName(getDefaultJndiNameForRABean(defaultBaseJndiName));
      }

      setDefaultOutboundRAs(connBean, wlConnBean, defaultBaseJndiName);
      setDefaultAdminObjects(connBean, wlConnBean, defaultBaseJndiName);
      return wlConnBean;
   }

   private static void setDefaultOutboundRAs(ConnectorBean connBean, WeblogicConnectorBean wlConnectorBean, String defaultBaseJndiName) {
      ResourceAdapterBean raBean = connBean.getResourceAdapter();
      OutboundResourceAdapterBean outRABean = raBean.getOutboundResourceAdapter();
      if (outRABean != null) {
         ConnectionDefinitionBean[] connDefnBeans = outRABean.getConnectionDefinitions();
         int numConns = connDefnBeans.length;
         if (numConns > 0) {
            weblogic.j2ee.descriptor.wl.OutboundResourceAdapterBean wlOutRABean = wlConnectorBean.getOutboundResourceAdapter();
            setDefaultMaxCapacity(wlOutRABean);

            for(int i = 0; i < connDefnBeans.length; ++i) {
               weblogic.j2ee.descriptor.wl.ConnectionDefinitionBean connectionDefinitionGroup = wlOutRABean.createConnectionDefinitionGroup();
               String connectionFactoryInterface = connDefnBeans[i].getConnectionFactoryInterface();
               if (Debug.isParsingEnabled()) {
                  Debug.parsing("Setting ConnectionFactoryInterface of conn defn group[" + i + "] to '" + connectionFactoryInterface + "'");
               }

               connectionDefinitionGroup.setConnectionFactoryInterface(connectionFactoryInterface);
               ConnectionInstanceBean connectionInstance = connectionDefinitionGroup.createConnectionInstance();
               String ciJNDIName = getDefaultJndiName(defaultBaseJndiName, connectionFactoryInterface.replace('.', '_'));
               if (Debug.isParsingEnabled()) {
                  Debug.parsing("Setting JNDI name of conn instance[" + i + "] to '" + ciJNDIName + "'");
               }

               connectionInstance.setJNDIName(ciJNDIName);
            }
         }
      }

   }

   private static void setDefaultAdminObjects(ConnectorBean connBean, WeblogicConnectorBean wlConnectorBean, String defaultBaseJndiName) {
      ResourceAdapterBean raBean = connBean.getResourceAdapter();
      AdminObjectBean[] adminObjects = raBean.getAdminObjects();
      if (adminObjects.length > 0) {
         AdminObjectsBean wlAdminObjects = wlConnectorBean.getAdminObjects();

         for(int i = 0; i < adminObjects.length; ++i) {
            String adminObjectInterface = adminObjects[i].getAdminObjectInterface();
            AdminObjectGroupBean adminObjectGroup = wlAdminObjects.createAdminObjectGroup();
            if (Debug.isParsingEnabled()) {
               Debug.parsing("Setting AdminObjectInterface of admin obj group[" + i + "] to '" + adminObjectInterface + "'");
            }

            adminObjectGroup.setAdminObjectInterface(adminObjectInterface);
            AdminObjectInstanceBean adminObjectInstance = adminObjectGroup.createAdminObjectInstance();
            String adminJNDIName = getDefaultJndiName(defaultBaseJndiName, adminObjectInterface.replace('.', '_'));
            if (Debug.isParsingEnabled()) {
               Debug.parsing("Setting JNDI name of Admin Obj instance[" + i + "] to '" + adminJNDIName + "'");
            }

            adminObjectInstance.setJNDIName(adminJNDIName);
         }
      }

   }

   private static void setDefaultMaxCapacity(weblogic.j2ee.descriptor.wl.OutboundResourceAdapterBean outboundResourceAdapter) {
      ConnectionDefinitionPropertiesBean defaultConnectionProperties = outboundResourceAdapter.getDefaultConnectionProperties();
      PoolParamsBean poolParams = defaultConnectionProperties.getPoolParams();
      poolParams.setMaxCapacity(Integer.MAX_VALUE);
   }

   private static String getNameForMerging(String fileName) {
      String name = fileName;
      String separator = File.separator;
      if (separator.equals("\\")) {
         separator = "\\\\";
      }

      String[] splitArray = fileName.split(separator);
      if (splitArray != null) {
         name = splitArray[splitArray.length - 1];
      }

      return name;
   }

   private static String getAppName(String appId) {
      String appname = null;
      appId = ApplicationVersionUtils.replaceDelimiter(appId, '_');
      if (appId != null && appId.trim().length() != 0) {
         if (!appId.endsWith(".rar") && !appId.endsWith(".ear")) {
            appname = appId;
         } else {
            appname = appId.substring(0, appId.length() - 4);
         }
      } else {
         Debug.throwAssertionError("AppId is null or empty : " + appId);
      }

      return appname;
   }

   public static String getModuleName(String moduleName) {
      if (moduleName != null && moduleName.trim().length() != 0) {
         if (moduleName.endsWith(".rar")) {
            moduleName = moduleName.substring(0, moduleName.length() - 4);
         }
      } else {
         Debug.throwAssertionError("Module name is null or empty : " + moduleName);
      }

      return moduleName;
   }

   private static String getDefaultBaseJndiName(String appId, String modName) {
      String jndiName = null;
      String appName = getAppName(appId);
      String moduleName = getModuleName(modName);
      if (appName.equals(moduleName)) {
         jndiName = "eis/" + appName;
      } else {
         jndiName = "eis/" + appName + "_" + moduleName;
      }

      return jndiName;
   }

   private static String getDefaultJndiNameForRABean(String baseJNDIName) {
      return baseJNDIName + "_RABean";
   }

   private static String getDefaultJndiName(String baseJNDIName, String interfaceName) {
      return baseJNDIName + "_" + interfaceName;
   }
}
