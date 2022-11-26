package weblogic.connector.common;

import com.bea.connector.diagnostic.AdapterType;
import com.bea.connector.diagnostic.ConnectorDiagnosticImageDocument;
import com.bea.connector.diagnostic.ConnectorDiagnosticImageType;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import javax.resource.spi.ResourceAdapter;
import weblogic.connector.exception.RAException;
import weblogic.connector.external.RAInfo;
import weblogic.connector.monitoring.ServiceRuntimeMBeanImpl;
import weblogic.connector.utils.PartitionUtils;
import weblogic.management.ManagementException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class RACollectionManager {
   private static final String CLASS_NAME = "weblogic.connector.common.RACollectionManager";
   private static List raList = new Vector(10);
   private static int connectionPoolsTotalCount = 0;

   public static void register(RAInstanceManager aRA) throws ManagementException {
      raList.add(aRA);
      ServiceRuntimeMBeanImpl connectorServiceRuntime = ConnectorService.getConnectorServiceRuntimeMBean(aRA.getPartitionName());
      if (connectorServiceRuntime != null) {
         connectorServiceRuntime.addConnectorRuntime(aRA.getConnectorComponentRuntimeMBean());
      }

   }

   public static void unregister(RAInstanceManager aRA) throws ManagementException {
      raList.remove(aRA);
      ServiceRuntimeMBeanImpl connectorServiceRuntime = ConnectorService.getConnectorServiceRuntimeMBean(aRA.getPartitionName());
      if (connectorServiceRuntime != null) {
         connectorServiceRuntime.removeConnectorRuntime(aRA.getConnectorComponentRuntimeMBean());
      }

   }

   public static void stop() throws RAException {
      Debug.service("stop() called on RACollectionManager; will stop each RA instance.");
      Iterator raIterator = raList.iterator();

      while(raIterator.hasNext()) {
         RAInstanceManager aRA = (RAInstanceManager)raIterator.next();
         aRA.stop();
      }

   }

   public static void halt() throws RAException {
      Debug.service("halt() called on RACollectionManager; will halt each RA instance.");
      Iterator raIterator = raList.iterator();

      while(raIterator.hasNext()) {
         RAInstanceManager aRA = (RAInstanceManager)raIterator.next();
         aRA.halt();
      }

   }

   public static void start() throws RAException {
      Debug.service("start() called on RACollectionManager; no actions to perform.");
   }

   public static void updateCounts(RAInstanceManager aRA) {
      connectionPoolsTotalCount += aRA.getAvailableConnectionPoolsCount();
   }

   public static List getRAs() {
      return raList;
   }

   public static int getConnectionPoolsTotalCount() {
      return connectionPoolsTotalCount;
   }

   public static RAInstanceManager getRAInstanceManager(ResourceAdapter ra) {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      Iterator raIMs = raList.iterator();

      RAInstanceManager raIM;
      ResourceAdapter nextRA;
      do {
         if (!raIMs.hasNext()) {
            return null;
         }

         raIM = (RAInstanceManager)raIMs.next();
         nextRA = raIM.getResourceAdapter();
      } while(nextRA == null || !raIM.getAdapterLayer().equals(nextRA, ra, kernelId));

      return raIM;
   }

   public static RAInstanceManager getRAInstanceManager(String jndiName) {
      Debug.println((Object)"weblogic.connector.common.RACollectionManager", (String)(".getRAInstanceManager( jndiName = '" + jndiName + "' )"));
      if (jndiName == null || jndiName.trim().equals("")) {
         Debug.throwAssertionError("jndiName is null or empty");
      }

      String partitionName = PartitionUtils.getPartitionName();
      RAInstanceManager raIM = null;
      Iterator raIMs = raList.iterator();

      do {
         if (!raIMs.hasNext()) {
            Debug.println((Object)"weblogic.connector.common.RACollectionManager", (String)(".getRAInstanceManager() found no deployed RA with JNDI name = '" + jndiName + "'"));
            return null;
         }

         raIM = (RAInstanceManager)raIMs.next();
      } while(!jndiName.equals(raIM.getJndiName()) || !Objects.equals(partitionName, raIM.getPartitionName()));

      Debug.println((Object)"weblogic.connector.common.RACollectionManager", (String)(".getRAInstanceManager( " + jndiName + " ) returning RA with moduleName = " + raIM.getModuleName()));
      return raIM;
   }

   public static List getAllRAInstanceManagers() {
      return raList;
   }

   public static ConnectorDiagnosticImageDocument getXMLBean(ConnectorDiagnosticImageSource src) {
      ConnectorDiagnosticImageDocument doc = ConnectorDiagnosticImageDocument.Factory.newInstance();
      ConnectorDiagnosticImageType diagImage = ConnectorDiagnosticImageType.Factory.newInstance();
      boolean timedout = src != null ? src.timedout() : false;
      if (timedout) {
         return doc;
      } else {
         AdapterType[] adapterXBeans = new AdapterType[raList.size()];
         Iterator iter = raList.iterator();

         for(int idx = 0; iter.hasNext(); ++idx) {
            RAInstanceManager raIM = (RAInstanceManager)iter.next();
            adapterXBeans[idx] = raIM.getXMLBean(src);
         }

         diagImage.setAdapterArray(adapterXBeans);
         doc.setConnectorDiagnosticImage(diagImage);
         return doc;
      }
   }

   public static ConnectorDiagnosticImageDocument getXMLBean(String partitionName, ConnectorDiagnosticImageSource src) {
      partitionName = PartitionUtils.isDomainScope(partitionName) ? PartitionUtils.DOMAIN_SCOPE : partitionName;
      ConnectorDiagnosticImageDocument doc = ConnectorDiagnosticImageDocument.Factory.newInstance();
      ConnectorDiagnosticImageType diagImage = ConnectorDiagnosticImageType.Factory.newInstance();
      boolean timedout = src != null ? src.timedout() : false;
      if (timedout) {
         return doc;
      } else {
         List adapterXBeans = new ArrayList();
         Iterator var6 = raList.iterator();

         while(var6.hasNext()) {
            RAInstanceManager raIM = (RAInstanceManager)var6.next();
            if (partitionName.equals(raIM.getPartitionName())) {
               adapterXBeans.add(raIM.getXMLBean(src));
            }
         }

         diagImage.setAdapterArray((AdapterType[])adapterXBeans.toArray(new AdapterType[adapterXBeans.size()]));
         doc.setConnectorDiagnosticImage(diagImage);
         return doc;
      }
   }

   public static RAInstanceManager getRAInstanceManagerByAppName(String appName, String versionId) {
      Debug.println((Object)"weblogic.connector.common.RACollectionManager", (String)(".getRAInstanceManagerByAppName( appName = '" + appName + "' )"));
      if (appName == null || appName.trim().equals("")) {
         Debug.throwAssertionError("jndiName is null or empty");
      }

      String partitionName = PartitionUtils.getPartitionName();
      RAInstanceManager raIM = null;
      Iterator raIMs = raList.iterator();
      if (versionId != null && versionId.length() > 0) {
         while(raIMs.hasNext()) {
            raIM = (RAInstanceManager)raIMs.next();
            if (appName.equals(raIM.getApplicationName()) && versionId.equals(raIM.getVersionId()) && Objects.equals(partitionName, raIM.getPartitionName())) {
               Debug.println((Object)"weblogic.connector.common.RACollectionManager", (String)(".getRAInstanceManager( " + appName + " ) returning RA with moduleName = " + raIM.getModuleName()));
               return raIM;
            }
         }
      }

      Debug.println((Object)"weblogic.connector.common.RACollectionManager", (String)(".getRAInstanceManagerByAppName found no deployed RA with appName name = '" + appName + "'"));
      return null;
   }

   public static RAInstanceManager getRAInstanceManagerByAppName(String appName) {
      Debug.println((Object)"weblogic.connector.common.RACollectionManager", (String)(".getRAInstanceManagerByAppName( appName = '" + appName + "' )"));
      String partitionName = PartitionUtils.getPartitionName();
      Iterator var2 = raList.iterator();

      RAInstanceManager raIM;
      do {
         if (!var2.hasNext()) {
            Debug.println((Object)"weblogic.connector.common.RACollectionManager", (String)(".getRAInstanceManagerByAppName found no deployed RA with appName name = '" + appName + "'"));
            return null;
         }

         raIM = (RAInstanceManager)var2.next();
      } while(!appName.equals(raIM.getApplicationName()) || !Objects.equals(partitionName, raIM.getPartitionName()));

      Debug.println((Object)"weblogic.connector.common.RACollectionManager", (String)(".getRAInstanceManagerByAppName( " + appName + " ) returning RA with moduleName = " + raIM.getModuleName()));
      return raIM;
   }

   public static RAInstanceManager getRAInstanceManagerByAppNameAndModuleName(String appName, String moduleName) {
      Debug.println((Object)"weblogic.connector.common.RACollectionManager", (String)(".getRAInstanceManagerByAppNameAndModuleName( appName = '" + appName + "', moduleName = '" + moduleName + "' )"));
      String moduleNameWithRAR = moduleName.endsWith(".rar") ? moduleName : moduleName + ".rar";
      String partitionName = PartitionUtils.getPartitionName();
      Iterator var4 = raList.iterator();

      RAInstanceManager raIM;
      do {
         if (!var4.hasNext()) {
            Debug.println((Object)"weblogic.connector.common.RACollectionManager", (String)(".getRAInstanceManagerByAppNameAndModuleName found no deployed RA with appName = '" + appName + "', and moduleName = '" + moduleName + "'"));
            return null;
         }

         raIM = (RAInstanceManager)var4.next();
      } while(!appName.equals(raIM.getApplicationName()) || !moduleNameWithRAR.equals(raIM.getModuleName()) || !Objects.equals(partitionName, raIM.getPartitionName()));

      Debug.println((Object)"weblogic.connector.common.RACollectionManager", (String)(".getRAInstanceManagerByAppNameAndModuleName( " + appName + ", " + moduleName + " ) returning RA with moduleName = " + raIM.getModuleName()));
      return raIM;
   }

   public static RAInstanceManager getRAInstanceManagerByAppIdAndModuleName(String appId, String moduleName) {
      Debug.println((Object)"weblogic.connector.common.RACollectionManager", (String)(".getRAInstanceManagerByAppIdAndModuleName( appId = '" + appId + "', moduleName = '" + moduleName + "' )"));
      if (appId == null || appId.trim().equals("")) {
         Debug.throwAssertionError("appId is null or empty");
      }

      if (moduleName == null || moduleName.trim().equals("")) {
         Debug.throwAssertionError("moduleName is null or empty");
      }

      String moduleNameWithRAR = moduleName.endsWith(".rar") ? moduleName : moduleName + ".rar";
      String partitionName = PartitionUtils.getPartitionName();
      RAInstanceManager raIM = null;
      Iterator raIMs = raList.iterator();

      do {
         if (!raIMs.hasNext()) {
            Debug.println((Object)"weblogic.connector.common.RACollectionManager", (String)(".getRAInstanceManagerByAppIdAndModuleName found no deployed RA with appId = '" + appId + "', and moduleName = '" + moduleName + "'"));
            return null;
         }

         raIM = (RAInstanceManager)raIMs.next();
      } while(!appId.equals(raIM.getAppContext().getApplicationId()) || !moduleNameWithRAR.equals(raIM.getModuleName()) || !Objects.equals(partitionName, raIM.getPartitionName()));

      Debug.println((Object)"weblogic.connector.common.RACollectionManager", (String)(".getRAInstanceManagerByAppIdAndModuleName( " + appId + ", " + moduleName + " ) returning RA with moduleName = " + raIM.getModuleName()));
      return raIM;
   }

   public static List getRAJndiNamesByMessageListenerType(String messageListenerType) {
      List jndiNames = new ArrayList();
      String partitionName = PartitionUtils.getPartitionName();
      Debug.deployment("getRAJndiNamesByMessageListenerType: Lookup resource adapter by message listener type " + messageListenerType);
      Iterator var3 = raList.iterator();

      while(var3.hasNext()) {
         RAInstanceManager ra = (RAInstanceManager)var3.next();
         RAInfo raInfo = ra.getRAInfo();
         if (raInfo.isEnableGlobalAccessToClasses() && partitionName.equals(ra.getPartitionName()) && raInfo.supportMessageListenerType(messageListenerType)) {
            Debug.deployment("getRAJndiNamesByMessageListenerType: find resource adapter: " + ra.getApplicationName());
            jndiNames.add(raInfo.getJndiName());
         }
      }

      return jndiNames;
   }

   public static List getRAJndiNamesByMessageListenerType(String messageListenerType, String appName, String version) {
      Debug.deployment("getRAJndiNamesByMessageListenerType: Lookup resource adapter by message listener type " + messageListenerType + " and application " + appName + ", version " + version);
      List jndiNames = new ArrayList();
      String partitionName = PartitionUtils.getPartitionName();
      Iterator var5 = raList.iterator();

      while(var5.hasNext()) {
         RAInstanceManager ra = (RAInstanceManager)var5.next();
         if (appName.equals(ra.getApplicationName()) && Objects.equals(version, ra.getVersionId()) && Objects.equals(partitionName, ra.getPartitionName()) && ra.getRAInfo().supportMessageListenerType(messageListenerType)) {
            Debug.deployment("getRAJndiNamesByMessageListenerType: find resource adapter: " + ra.getApplicationName() + "#" + ra.getModuleName());
            jndiNames.add(ra.getRAInfo().getJndiName());
         }
      }

      return jndiNames;
   }
}
