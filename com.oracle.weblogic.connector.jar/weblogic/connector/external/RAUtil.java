package weblogic.connector.external;

import java.security.AccessController;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.naming.Reference;
import javax.resource.ResourceException;
import javax.resource.spi.ResourceAdapter;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.naming.AdministeredObjectUtilityService;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.common.RACollectionManager;
import weblogic.connector.common.RAInstanceManager;
import weblogic.connector.common.UniversalResourceKey;
import weblogic.connector.security.SecurityHelperFactory;
import weblogic.connector.utils.PartitionUtils;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.j2ee.descriptor.AdministeredObjectBean;
import weblogic.j2ee.descriptor.ConnectionFactoryResourceBean;
import weblogic.logging.LogFileConfigUtil;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ConnectorConnectionPoolRuntimeMBean;
import weblogic.management.runtime.ConnectorServiceRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class RAUtil {
   public static final String CONNECTOR_LOG = "ConnectorLog";
   private static final AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static RAInfo getRAInfo(ResourceAdapter ra) {
      RAInfo raInfo = null;
      if (ra != null) {
         RAInstanceManager raIM = RACollectionManager.getRAInstanceManager(ra);
         if (raIM != null) {
            raInfo = raIM.getRAInfo();
         }
      }

      return raInfo;
   }

   public static RAInfo getRAInfo(String jndiName) {
      RAInfo raInfo = null;
      Iterator var2 = RACollectionManager.getRAs().iterator();

      while(var2.hasNext()) {
         RAInstanceManager ra = (RAInstanceManager)var2.next();
         if (jndiName.equals(ra.getRAInfo().getJndiName())) {
            raInfo = ra.getRAInfo();
            break;
         }

         Iterator var4 = ra.getRAInfo().getOutboundInfos().iterator();

         while(var4.hasNext()) {
            OutboundInfo outboundInfo = (OutboundInfo)var4.next();
            if (outboundInfo.getJndiName().equals(jndiName)) {
               raInfo = ra.getRAInfo();
               break;
            }
         }
      }

      return raInfo;
   }

   public static Set getAvailableConnectorLogNames() {
      Set results = new HashSet();
      Iterator var1 = RACollectionManager.getRAs().iterator();

      while(var1.hasNext()) {
         RAInstanceManager raInstanceMgr = (RAInstanceManager)var1.next();
         Iterator var3 = raInstanceMgr.getRAInfo().getOutboundInfos().iterator();

         while(var3.hasNext()) {
            OutboundInfo outboundInfo = (OutboundInfo)var3.next();
            String logFilename = outboundInfo.getLogFilename();
            if (logFilename != null && logFilename.length() > 0) {
               String logKey = PartitionUtils.appendPartitionName(outboundInfo.getJndiName(), raInstanceMgr.getPartitionName());
               results.add("ConnectorLog/" + logKey);
            }
         }
      }

      return results;
   }

   public static Set getAvailableConnectorLogNames(String partitionName) {
      if (PartitionUtils.isDomainScope(partitionName)) {
         partitionName = PartitionUtils.DOMAIN_SCOPE;
      }

      Set results = new HashSet();
      Iterator var2 = RACollectionManager.getRAs().iterator();

      while(true) {
         RAInstanceManager raInstanceMgr;
         do {
            if (!var2.hasNext()) {
               return results;
            }

            raInstanceMgr = (RAInstanceManager)var2.next();
         } while(!partitionName.equals(raInstanceMgr.getPartitionName()));

         Iterator var4 = raInstanceMgr.getRAInfo().getOutboundInfos().iterator();

         while(var4.hasNext()) {
            OutboundInfo outboundInfo = (OutboundInfo)var4.next();
            String logFilename = outboundInfo.getLogFilename();
            if (logFilename != null && logFilename.length() > 0) {
               String logKey = PartitionUtils.appendPartitionName(outboundInfo.getJndiName(), raInstanceMgr.getPartitionName());
               results.add("ConnectorLog/" + logKey);
            }
         }
      }
   }

   public static String getLogFileName(String key) {
      String logFilename = null;
      OutboundInfo outboundInfo = getOutboundInfo(key);
      if (outboundInfo != null) {
         logFilename = outboundInfo.getLogFilename();
      }

      return logFilename != null && logFilename.length() != 0 ? LogFileConfigUtil.computePathRelativeServersLogsDir(logFilename) : null;
   }

   public static String getLogFileRotationDir(String key) {
      OutboundInfo outboundInfo = getOutboundInfo(key);
      String logFileRotationDir;
      if (outboundInfo != null) {
         logFileRotationDir = outboundInfo.getLogFileRotationDir();
      } else {
         logFileRotationDir = null;
      }

      return logFileRotationDir != null && logFileRotationDir.length() != 0 ? LogFileConfigUtil.computePathRelativeServersLogsDir(logFileRotationDir) : null;
   }

   private static OutboundInfo getOutboundInfo(String key) {
      String jndi = PartitionUtils.getJNDINameFromLoggerKey(key);
      String partitionName = PartitionUtils.getPartitionNameFromLoggerKey(key);
      Iterator var3 = RACollectionManager.getRAs().iterator();

      while(var3.hasNext()) {
         RAInstanceManager raInstanceMgr = (RAInstanceManager)var3.next();
         if (partitionName.equals(raInstanceMgr.getPartitionName())) {
            OutboundInfo outboundInfo = raInstanceMgr.getRAInfo().getOutboundInfo(jndi);
            if (outboundInfo != null) {
               return outboundInfo;
            }
         }
      }

      return null;
   }

   static RAInstanceManager getValidRAManagerForAppDefinedResource(String raName, String appId) {
      RAInstanceManager raInstanceMgr = null;
      String appName = ApplicationVersionUtils.getApplicationName(appId);
      if (raName.startsWith("#")) {
         raInstanceMgr = RACollectionManager.getRAInstanceManagerByAppIdAndModuleName(appId, raName.substring(1));
      } else {
         String[] array = raName.split("#");
         if (array.length == 1) {
            raInstanceMgr = RACollectionManager.getRAInstanceManagerByAppName(raName);
         } else if (array.length == 2) {
            if (appName.equals(array[0])) {
               raInstanceMgr = RACollectionManager.getRAInstanceManagerByAppIdAndModuleName(appId, array[1]);
            } else {
               raInstanceMgr = RACollectionManager.getRAInstanceManagerByAppNameAndModuleName(array[0], array[1]);
            }
         }
      }

      return raInstanceMgr;
   }

   public static Reference createAdministeredObject(AdministeredObjectBean aoBean, String moduleName, String compName, String appId) throws ResourceException {
      String appName = ApplicationVersionUtils.getApplicationName(appId);
      RAInstanceManager raInstanceMgr = getValidRAManagerForAppDefinedResource(aoBean.getResourceAdapter(), appId);
      if (raInstanceMgr == null) {
         throw new ResourceException(ConnectorLogger.getExceptionResourceAdapterNotFound(aoBean.getResourceAdapter(), appName, moduleName, compName, aoBean.getName()));
      } else {
         Reference ref = raInstanceMgr.createAppDefinedAdminObject(aoBean, appId, moduleName, compName);
         return ref;
      }
   }

   public static Object revokeAdministeredObject(String jndiName, String raName, String moduleName, String compName, String appId) throws ResourceException {
      String appName = ApplicationVersionUtils.getApplicationName(appId);
      RAInstanceManager raInstanceMgr = getValidRAManagerForAppDefinedResource(raName, appId);
      if (raInstanceMgr == null) {
         throw new ResourceException(ConnectorLogger.getExceptionResourceAdapterNotFound(raName, appName, moduleName, compName, jndiName));
      } else {
         UniversalResourceKey key = new UniversalResourceKey(appName, moduleName, compName, jndiName, raInstanceMgr.getVersionId());
         return raInstanceMgr.revokeAppDefinedAdminObject(key);
      }
   }

   public static void destroyAdministeredObject(Object handle, String jndiName, String raName, String moduleName, String compName, String appId) throws ResourceException {
      String appName = ApplicationVersionUtils.getApplicationName(appId);
      RAInstanceManager raInstanceMgr = getValidRAManagerForAppDefinedResource(raName, appId);
      if (raInstanceMgr == null) {
         throw new ResourceException(ConnectorLogger.getExceptionResourceAdapterNotFound(raName, appName, moduleName, compName, jndiName));
      } else {
         raInstanceMgr.destroyAppDefinedAdminObject(handle);
      }
   }

   public static Reference createConnectionFactory(ConnectionFactoryResourceBean bean, String moduleName, String compName, String appId) throws ResourceException {
      String appName = ApplicationVersionUtils.getApplicationName(appId);
      RAInstanceManager raInstanceMgr = getValidRAManagerForAppDefinedResource(bean.getResourceAdapter(), appId);
      if (raInstanceMgr == null) {
         throw new ResourceException(ConnectorLogger.getExceptionResourceAdapterNotFound(bean.getResourceAdapter(), appName, moduleName, compName, bean.getName()));
      } else {
         return raInstanceMgr.createAppDefinedConnectionFactory(bean, appId, moduleName, compName);
      }
   }

   public static Object revokeConnectionFactory(String jndiName, String raName, String moduleName, String compName, String appId) throws ResourceException {
      String appName = ApplicationVersionUtils.getApplicationName(appId);
      RAInstanceManager raInstanceMgr = getValidRAManagerForAppDefinedResource(raName, appId);
      if (raInstanceMgr == null) {
         throw new ResourceException(ConnectorLogger.getExceptionResourceAdapterNotFound(raName, appName, moduleName, compName, jndiName));
      } else {
         UniversalResourceKey key = new UniversalResourceKey(appName, moduleName, compName, jndiName, raInstanceMgr.getVersionId());
         return raInstanceMgr.revokeAppdefinedConnectionFactory(key);
      }
   }

   public static void destroyConnectionFactory(Object handle, String jndiName, String raName, String moduleName, String compName, String appId) throws ResourceException {
      String appName = ApplicationVersionUtils.getApplicationName(appId);
      RAInstanceManager raInstanceMgr = getValidRAManagerForAppDefinedResource(raName, appId);
      if (raInstanceMgr == null) {
         throw new ResourceException(ConnectorLogger.getExceptionResourceAdapterNotFound(raName, appName, moduleName, compName, jndiName));
      } else {
         raInstanceMgr.destroyAppDefinedConnectionFactory(handle);
      }
   }

   public static void testLogUtils(AuthenticatedSubject kernelId) {
      if (!SecurityHelperFactory.getInstance().isKernelIdentity(kernelId)) {
         throw new SecurityException("KernelId is required to call RAUtils.testLogUtils, Subject '" + (kernelId == null ? "<null>" : kernelId.toString()) + "' is not the kernel identity");
      } else {
         System.out.println("^^^^^ TESTLOGUTILS STARTING ^^^^^^^");
         Set connectorLogs = getAvailableConnectorLogNames();
         int index = "ConnectorLog".length() + 1;
         Iterator iter = connectorLogs.iterator();
         int count = 0;

         while(iter.hasNext()) {
            ++count;
            String logicalName = (String)iter.next();
            String keyName = logicalName.substring(index);
            String logFilename = getLogFileName(keyName);
            String logFileRotationDir = getLogFileRotationDir(keyName);
            System.out.println("CONNECTOR LOG #" + count);
            System.out.println("================================================");
            System.out.println("  LogicalName:         " + logicalName);
            System.out.println("  KeyName:             " + keyName);
            System.out.println("  LogFilename:         " + logFilename);
            System.out.println("  LogFileRotationDir:  " + logFileRotationDir);
         }

         System.out.println("^^^^^ TESTLOGUTILS COMPLETE ^^^^^^^");
      }
   }

   public static boolean isConnectionFactory(String jndiName, ComponentInvocationContext cic) {
      String applicationName = cic.getApplicationName();
      String moduleName = cic.getModuleName();
      String componentName = cic.getComponentName();
      String key = UniversalResourceKey.toKeyString(jndiName, applicationName, moduleName, componentName);
      ServerRuntimeMBean serverRuntime = ManagementService.getRuntimeAccess(kernelID).getServerRuntime();
      ConnectorServiceRuntimeMBean connectorServiceRuntime;
      if (cic.isGlobalRuntime()) {
         connectorServiceRuntime = serverRuntime.getConnectorServiceRuntime();
      } else {
         PartitionRuntimeMBean partitionRuntime = serverRuntime.lookupPartitionRuntime(cic.getPartitionName());
         connectorServiceRuntime = partitionRuntime.getConnectorServiceRuntime();
      }

      ConnectorConnectionPoolRuntimeMBean connectionPool = connectorServiceRuntime.getConnectionPool(key);
      return connectionPool != null;
   }

   @Service
   private static class AdministeredObjectUtilityServiceImpl implements AdministeredObjectUtilityService {
      public Reference createConnectionFactory(ConnectionFactoryResourceBean bean, String moduleName, String compName, String appId) throws ResourceException {
         return RAUtil.createConnectionFactory(bean, moduleName, compName, appId);
      }

      public Object revokeConnectionFactory(String jndiName, String raName, String moduleName, String compName, String appId) throws ResourceException {
         return RAUtil.revokeConnectionFactory(jndiName, raName, moduleName, compName, appId);
      }

      public void destroyConnectionFactory(Object handle, String jndiName, String raName, String moduleName, String compName, String appId) throws ResourceException {
         RAUtil.destroyConnectionFactory(handle, jndiName, raName, moduleName, compName, appId);
      }

      public Reference createAdministeredObject(AdministeredObjectBean aoBean, String moduleName, String compName, String appId) throws ResourceException {
         return RAUtil.createAdministeredObject(aoBean, moduleName, compName, appId);
      }

      public Object revokeAdministeredObject(String jndiName, String raName, String moduleName, String compName, String appId) throws ResourceException {
         return RAUtil.revokeAdministeredObject(jndiName, raName, moduleName, compName, appId);
      }

      public void destroyAdministeredObject(Object handle, String jndiName, String raName, String moduleName, String compName, String appId) throws ResourceException {
         RAUtil.destroyAdministeredObject(handle, jndiName, raName, moduleName, compName, appId);
      }
   }
}
