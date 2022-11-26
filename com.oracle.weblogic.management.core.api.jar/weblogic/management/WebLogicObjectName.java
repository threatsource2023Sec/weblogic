package weblogic.management;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import weblogic.common.internal.InteropWriteReplaceable;
import weblogic.common.internal.PeerInfo;
import weblogic.management.interop.JMXInteropHelper;

/** @deprecated */
@Deprecated
public final class WebLogicObjectName extends ObjectName implements InteropWriteReplaceable {
   private static HashMap parents = null;
   public static final String WEBLOGIC = "weblogic";
   public static final String NAME = "Name";
   public static final String TYPE = "Type";
   public static final String LOCATION = "Location";
   private static final long serialVersionUID = 7351961731978894257L;
   private static final String[] ABSTRACT_TYPES;
   private boolean isAdmin;
   private boolean isConfig;
   private boolean isRuntime;
   private WebLogicObjectName parent;
   private int hashCode;
   private static final HashSet keys;

   /** @deprecated */
   @Deprecated
   public WebLogicObjectName(String fullObjectName) throws MalformedObjectNameException {
      super(extractDomain(fullObjectName), extractProperties(fullObjectName));
      this.validate();
      this.populate();
   }

   public WebLogicObjectName(String name, String type, String domain) throws MalformedObjectNameException {
      this(domain + ":" + "Name" + "=" + name + "," + "Type" + "=" + type);
   }

   public WebLogicObjectName(String name, String type, String domain, String location) throws MalformedObjectNameException {
      this(domain + ":" + "Name" + "=" + name + "," + "Type" + "=" + type + "," + "Location" + "=" + location);
   }

   public WebLogicObjectName(String name, String type, String domain, WebLogicObjectName parent) throws MalformedObjectNameException {
      this(name, type, domain, (String)null, parent);
   }

   /** @deprecated */
   @Deprecated
   public WebLogicObjectName(WebLogicObjectName objectNameArg, WebLogicObjectName parentArg) throws MalformedObjectNameException {
      this(objectNameArg.getName(), objectNameArg.getType(), objectNameArg.getDomain(), objectNameArg.getLocation(), parentArg);
   }

   public WebLogicObjectName(String nameArg, String typeArg, String domainArg, String locationArg, WebLogicObjectName parentArg) throws MalformedObjectNameException {
      super(domainArg, makePropertyTable(nameArg, typeArg, locationArg, parentArg));
      this.parent = parentArg;
      this.validate();
   }

   /** @deprecated */
   @Deprecated
   public WebLogicObjectName(String nameArg, String typeArg, WebLogicObjectName parentArg) throws MalformedObjectNameException {
      super(parentArg.getDomain(), makePropertyTable(nameArg, typeArg, (String)null, parentArg));
      this.parent = parentArg;
      this.validate();
   }

   /** @deprecated */
   @Deprecated
   public WebLogicObjectName(String domain, Hashtable properties) throws MalformedObjectNameException {
      super(domain, properties);
      this.validate();
   }

   /** @deprecated */
   @Deprecated
   public WebLogicObjectName(ObjectName objectName, String locationName) throws MalformedObjectNameException {
      this(objectName.getDomain(), localize(objectName, locationName));
      this.validate();
   }

   public static boolean isAdmin(ObjectName objectName) {
      String location = objectName.getKeyProperty("Location");
      return location == null;
   }

   public static boolean isConfig(ObjectName objectName) {
      String type = objectName.getKeyProperty("Type");
      return type != null && type.endsWith("Config");
   }

   public static boolean isRuntime(ObjectName objectName) {
      String type = objectName.getKeyProperty("Type");
      return type != null && type.endsWith("Runtime");
   }

   public static String extractDomain(String fullWebLogicObjectName) throws MalformedObjectNameException {
      int idx = fullWebLogicObjectName.indexOf(":");
      if (idx >= 1) {
         String result = fullWebLogicObjectName.substring(0, idx);
         if (!result.equals("null")) {
            return result;
         }
      }

      throw new MalformedObjectNameException("Domain name either missing or null");
   }

   private static Hashtable extractProperties(String name) throws MalformedObjectNameException {
      Hashtable result = new Hashtable();
      int idx = name.indexOf(":");
      if (idx > -1) {
         name = name.substring(idx + 1, name.length());
      }

      StringTokenizer tokenizer = new StringTokenizer(name, ",");

      while(tokenizer.hasMoreTokens()) {
         String token = tokenizer.nextToken();
         int eq = token.indexOf("=");
         if (eq < 1) {
            throw new MalformedObjectNameException("bad object name property: " + token);
         }

         String key = token.substring(0, eq);
         String value = token.substring(eq + 1, token.length());
         if (result.containsKey(key)) {
            throw new MalformedObjectNameException("bad object name,property " + key + " has multiple values in " + name);
         }

         result.put(key, value);
      }

      return result;
   }

   private static Hashtable makePropertyTable(String name, String type, String location, WebLogicObjectName parent) throws MalformedObjectNameException {
      if (type == null) {
         throw new MalformedObjectNameException("type cannot be null");
      } else {
         Hashtable result = null;
         if (parent == null) {
            result = new Hashtable(2);
         } else {
            result = (Hashtable)parent.getKeyPropertyList().clone();
            if (!parent.getType().equals("Domain") && !parent.getType().equals("DomainConfig")) {
               result.put(parent.getType(), parent.getName());
            }
         }

         result.put("Name", name);
         result.put("Type", type);
         if (location != null) {
            result.put("Location", location);
         }

         return result;
      }
   }

   private static Hashtable localize(ObjectName objectName, String locationName) {
      Hashtable result = new Hashtable();
      Hashtable props = objectName.getKeyPropertyList();
      Iterator it = props.keySet().iterator();

      while(it.hasNext()) {
         String key = (String)it.next();
         String value = (String)props.get(key);
         if (key.equals("Name")) {
            result.put(key, value);
         } else if (key.equals("Type")) {
            result.put(key, value + "Config");
         } else {
            result.put(key + "Config", value);
         }
      }

      result.put("Location", locationName);
      return result;
   }

   public String getName() {
      return this.getKeyProperty("Name");
   }

   public String getType() {
      return this.getKeyProperty("Type");
   }

   public String getLocation() {
      return this.getKeyProperty("Location");
   }

   public String getNormalizedToStringName() {
      StringBuffer result = new StringBuffer(this.getDomain() + ":");
      Hashtable propertyList = this.getKeyPropertyList();
      String nameProp = (String)propertyList.remove("Name");
      String typeProp = (String)propertyList.remove("Type");
      String locationProp = (String)propertyList.remove("Location");
      if (nameProp != null) {
         result.append("Name=" + nameProp + ",");
      }

      if (typeProp != null) {
         result.append("Type=" + typeProp + ",");
      }

      if (locationProp != null) {
         result.append("Location=" + locationProp + ",");
      }

      Iterator i = propertyList.entrySet().iterator();

      while(i.hasNext()) {
         Map.Entry entry = (Map.Entry)i.next();
         result.append((String)entry.getKey() + "=" + (String)entry.getValue() + ",");
      }

      result.deleteCharAt(result.length() - 1);
      if (nameProp != null) {
         propertyList.put("Name", nameProp);
      }

      if (typeProp != null) {
         propertyList.put("Type", typeProp);
      }

      if (locationProp != null) {
         propertyList.put("Location", locationProp);
      }

      return result.toString();
   }

   public boolean isAdmin() {
      return this.isAdmin;
   }

   public boolean isConfig() {
      return this.isConfig;
   }

   public boolean isRuntime() {
      return this.isRuntime;
   }

   public int hashCode() {
      if (this.hashCode == 0) {
         this.hashCode = super.hashCode();
      }

      return this.hashCode;
   }

   public static final boolean isAbstract(String baseType) {
      for(int i = 0; i < ABSTRACT_TYPES.length; ++i) {
         if (ABSTRACT_TYPES[i].equals(baseType)) {
            return true;
         }
      }

      return false;
   }

   private void validate() throws MalformedObjectNameException {
      String type = this.getType();
      if (isAbstract(type)) {
         throw new MalformedObjectNameException("Cannot create abstract MBean, name: " + this.toString());
      } else {
         this.isAdmin = this.getLocation() == null;
         if (type != null) {
            this.isConfig = type.endsWith("Config");
            this.isRuntime = type.endsWith("Runtime");
         }

      }
   }

   public WebLogicObjectName(ObjectName object) throws MalformedObjectNameException {
      this(object.getCanonicalName());
   }

   private void populate() throws MalformedObjectNameException {
      Hashtable table = (Hashtable)this.getKeyPropertyList().clone();
      String location = this.getLocation();
      String type = this.getType();
      String domain = this.getDomain();
      if (type != null) {
         if (table != null) {
            Iterator it = keys.iterator();
            String key = null;

            while(it.hasNext()) {
               key = (String)it.next();
               if (table.containsKey(key)) {
                  table.remove(key);
               }
            }

            if (table.isEmpty()) {
               if (this.getDomain().equals("weblogic") || this.getType().equals("Domain") || this.getType().equals("DomainConfig") || this.getType().equals("ServerRuntime") || this.getType().equals("DomainRuntime")) {
                  return;
               }

               String domainName = this.getDomain();
               if (location != null) {
                  this.parent = new WebLogicObjectName(domainName, "DomainConfig", domainName, location);
               } else {
                  this.parent = new WebLogicObjectName(domainName, "Domain", domainName);
               }
            }
         }

      }
   }

   public static void addTypeAndParents(String type, String[] parent) {
      parents.put(type, parent);
   }

   public Object interopWriteReplace(PeerInfo info) throws IOException {
      return info.compareTo(PeerInfo.VERSION_DIABLO) < 0 && !JMXInteropHelper.isSunInteropPropertySpecified() ? new weblogic.management.interop.WebLogicObjectName(this.getCanonicalName()) : this;
   }

   static {
      parents = new HashMap(40);
      parents.put("ApplicationRuntime", new String[]{"ServerRuntime"});
      parents.put("DeployerRuntime", new String[]{"DomainRuntime"});
      parents.put("DeploymentTaskRuntime", new String[]{"DeployerRuntime"});
      parents.put("ServerLifeCycleRuntime", new String[]{"DomainRuntime"});
      parents.put("ServerLifeCycleTaskRuntime", new String[]{"ServerLifeCycleRuntime"});
      parents.put("AppClientComponentRuntime", new String[]{"ApplicationRuntime"});
      parents.put("ConnectorComponentRuntime", new String[]{"ApplicationRuntime"});
      parents.put("ConnectorConnectionRuntime", new String[]{"ConnectorConnectionPoolRuntime"});
      parents.put("ConnectorConnectionPoolRuntime", new String[]{"ConnectorComponentRuntime"});
      parents.put("ConnectorInboundRuntime", new String[]{"ConnectorComponentRuntime"});
      parents.put("ConnectorWorkManagerRuntime", new String[]{"ConnectorComponentRuntime"});
      parents.put("EJBCacheRuntime", new String[]{"EJBComponentRuntime"});
      parents.put("EJBComponentRuntime", new String[]{"ApplicationRuntime"});
      parents.put("EJBLockingRuntime", new String[]{"EJBComponentRuntime"});
      parents.put("EJBPoolRuntime", new String[]{"EJBComponentRuntime"});
      parents.put("EJBRuntime", new String[]{"EJBComponentRuntime"});
      parents.put("EJBTimerRuntime", new String[]{"EJBComponentRuntime"});
      parents.put("EJBTransactionRuntime", new String[]{"EJBComponentRuntime"});
      parents.put("EntityEJBRuntime", new String[]{"EJBComponentRuntime"});
      parents.put("QueryCacheRuntime", new String[]{"EntityEJBRuntime, ApplicationRuntime", "EJBComponentRuntime"});
      parents.put("InterceptionComponentRuntime", new String[]{"ApplicationRuntime"});
      parents.put("JDBCConnectionPoolRuntime", new String[]{"ApplicationRuntime", "ServerRuntime"});
      parents.put("JDBCDataSourceRuntime", new String[]{"ApplicationRuntime", "ServerRuntime"});
      parents.put("JDBCDataSourceTaskRuntime", new String[]{"JDBCDataSourceRuntime"});
      parents.put("JDBCOracleDataSourceRuntime", new String[]{"ApplicationRuntime", "ServerRuntime"});
      parents.put("JDBCOracleDataSourceInstanceRuntime", new String[]{"JDBCOracleDataSourceRuntime"});
      parents.put("ONSClientRuntime", new String[]{"JDBCOracleDataSourceRuntime"});
      parents.put("ONSDaemonRuntime", new String[]{"ONSClientRuntime"});
      parents.put("JMSConnectionRuntime", new String[]{"JMSRuntime"});
      parents.put("JMSConsumerRuntime", new String[]{"JMSSessionRuntime"});
      parents.put("JMSComponentRuntime", new String[]{"ApplicationRuntime"});
      parents.put("JMSDestinationRuntime", new String[]{"JMSServerRuntime"});
      parents.put("JMSDurableSubscriberRuntime", new String[]{"JMSDestinationRuntime"});
      parents.put("JMSProducerRuntime", new String[]{"JMSSessionRuntime"});
      parents.put("JMSSessionPoolRuntime", new String[]{"JMSServerRuntime"});
      parents.put("JMSSessionRuntime", new String[]{"JMSConnectionRuntime"});
      parents.put("MessageDrivenEJBRuntime", new String[]{"EJBComponentRuntime"});
      parents.put("PSAssemblyRuntime", new String[]{"PathServiceRuntime"});
      parents.put("PSEntryCursorRuntime", new String[]{"PSAssemblyRuntime"});
      parents.put("ServletRuntime", new String[]{"WebAppComponentRuntime"});
      parents.put("JaxRsApplicationRuntime", new String[]{"WebAppComponentRuntime"});
      parents.put("JaxRsResourceRuntime", new String[]{"JaxRsApplicationRuntime"});
      parents.put("JaxRsSubResourceLocatorRuntime", new String[]{"JaxRsResourceRuntime"});
      parents.put("JaxRsResourceMethodRuntime", new String[]{"JaxRsResourceRuntime"});
      parents.put("JaxRsResourceConfigTypeRuntime", new String[]{"JaxRsApplicationRuntime"});
      parents.put("ServletSessionRuntime", new String[]{"WebAppComponentRuntime"});
      parents.put("StatefulEJBRuntime", new String[]{"EJBComponentRuntime"});
      parents.put("StatelessEJBRuntime", new String[]{"EJBComponentRuntime"});
      parents.put("WebAppComponentRuntime", new String[]{"ApplicationRuntime"});
      parents.put("WebServerLog", new String[]{"WebServer"});
      parents.put("WebServerLogConfig", new String[]{"WebServerConfig"});
      parents.put("WorkManagerRuntime", new String[]{"WebAppComponentRuntime", "EJBComponentRuntime", "ConnectorComponentRuntime", "ApplicationRuntime"});
      parents.put("MaxThreadsConstraintRuntime", new String[]{"WorkManagerRuntime", "ApplicationRuntime"});
      parents.put("MinThreadsConstraintRuntime", new String[]{"WorkManagerRuntime", "ApplicationRuntime"});
      parents.put("RequestClassRuntime", new String[]{"WorkManagerRuntime", "ApplicationRuntime"});
      parents.put("StuckThreadAction", new String[]{"OverloadProtection"});
      parents.put("StuckThreadActionConfig", new String[]{"OverloadProtectionConfig"});
      parents.put("ContextCase", new String[]{"ContextRequestClass"});
      parents.put("ContextCaseConfig", new String[]{"ContextRequestClassConfig"});
      parents.put("WebServiceComponentRuntime", new String[]{"ApplicationRuntime"});
      parents.put("WebServiceRuntime", new String[]{"WebServiceComponentRuntime"});
      parents.put("WebServiceOperationRuntime", new String[]{"WebServiceRuntime"});
      parents.put("WebServiceHandlerRuntime", new String[]{"WebServiceOperationRuntime", "WebServiceRuntime"});
      parents.put("WebServiceBuffering", new String[]{"WebService"});
      parents.put("WebServiceRequestBufferingQueue", new String[]{"WebServiceBuffering"});
      parents.put("WebServiceResponseBufferingQueue", new String[]{"WebServiceBuffering"});
      parents.put("WebServicePersistence", new String[]{"WebService"});
      parents.put("WebServiceLogicalStore", new String[]{"WebServicePersistence"});
      parents.put("WebServicePhysicalStore", new String[]{"WebServicePersistence"});
      parents.put("WebServiceReliability", new String[]{"WebService"});
      parents.put("WseePolicyRuntime", new String[]{"WseeRuntime", "WseeV2Runtime"});
      parents.put("WseeRuntime", new String[]{"ApplicationRuntime"});
      parents.put("WseeV2Runtime", new String[]{"WebAppComponentRuntime", "EJBComponentRuntime"});
      parents.put("WseeClientRuntime", new String[]{"WebAppComponentRuntime", "EJBComponentRuntime"});
      parents.put("WseeClientConfigurationRuntime", new String[]{"WebAppComponentRuntime", "EJBComponentRuntime"});
      parents.put("WebservicePolicyRuntime", new String[]{"WseeRuntime", "WseeV2Runtime"});
      parents.put("WseePortRuntime", new String[]{"WseeRuntime", "WseeV2Runtime"});
      parents.put("WseeOperationRuntime", new String[]{"WseePortRuntime"});
      parents.put("WseeAggregatableBaseOperationRuntime", new String[]{"WseePortRuntime", "WseeClientPortRuntime"});
      parents.put("WseeClientPortRuntime", new String[]{"WseeClientRuntime"});
      parents.put("WseePortConfigurationRuntime", new String[]{"WseeClientConfigurationRuntime"});
      parents.put("WseeOperationConfigurationRuntime", new String[]{"WseePortConfigurationRuntime"});
      parents.put("WseeClientOperationRuntime", new String[]{"WseeClientPortRuntime"});
      parents.put("WseeClusterFrontEndRuntime", new String[]{"ServerRuntime"});
      parents.put("WseeClusterRoutingRuntime", new String[]{"WseePortRuntime", "WseeClientPortRuntime", "WseeClusterFrontEndRuntime"});
      parents.put("WseeWsrmRuntime", new String[]{"WseePortRuntime", "WseeClientPortRuntime"});
      parents.put("WseeHandlerRuntime", new String[]{"WseePortRuntime", "WseeClientPortRuntime"});
      parents.put("WseePortPolicyRuntime", new String[]{"WseePortRuntime", "WseeClientPortRuntime"});
      parents.put("WseeMcRuntime", new String[]{"WseePortRuntime", "WseeClientPortRuntime"});
      parents.put("OwsmSecurityPolicyRuntime", new String[]{"WseeRuntime", "WseeV2Runtime"});
      parents.put("WseeWsrmRuntime", new String[]{"WseePortRuntime", "WseeClientPortRuntime"});
      parents.put("LogFilter", new String[]{"Log"});
      parents.put("LogFilterConfig", new String[]{"LogConfig"});
      parents.put("DebugScope", new String[]{"ServerDebug"});
      parents.put("DebugScopeConfig", new String[]{"ServerDebugConfig"});
      parents.put("JMSConnectionConsumer", new String[]{"JMSSessionPool"});
      parents.put("JMSConnectionConsumerConfig", new String[]{"JMSSessionPoolConfig"});
      parents.put("SAFAgentRuntime", new String[]{"SAFRuntime"});
      parents.put("SAFRemoteAgentRuntime", new String[]{"SAFAgentRuntime"});
      parents.put("SAFRemoteEndpointRuntime", new String[]{"SAFAgentRuntime"});
      parents.put("SAFMessageCursorRuntime", new String[]{"SAFRemoteEndpointRuntime"});
      parents.put("SAFConversationRuntime", new String[]{"SAFAgentRuntime", "SAFRemoteEndpointRuntime"});
      parents.put("WLDFAccessRuntime", new String[]{"WLDFRuntime"});
      parents.put("WLDFDataAccessRuntime", new String[]{"WLDFAccessRuntime"});
      parents.put("WLDFHarvesterRuntime", new String[]{"WLDFRuntime"});
      parents.put("WLDFImageRuntime", new String[]{"WLDFRuntime"});
      parents.put("WLDFWatchNotificationRuntime", new String[]{"WLDFRuntime"});
      parents.put("WLDFWatchJMXNotificationRuntime", new String[]{"WLDFWatchNotificationRuntime"});
      parents.put("WLDFInstrumentationRuntime", new String[]{"ApplicationRuntime", "WLDFRuntime"});
      parents.put("WLDFArchiveRuntime", new String[]{"WLDFRuntime"});
      parents.put("WLDFDbstoreArchiveRuntime", new String[]{"WLDFRuntime"});
      parents.put("WLDFFileArchiveRuntime", new String[]{"WLDFRuntime"});
      parents.put("WLDFWlstoreArchiveRuntime", new String[]{"WLDFRuntime"});
      parents.put("WLDFDataRetirementByAge", new String[]{"WLDFServerDiagnostic"});
      parents.put("WLDFHarvestedType", new String[]{"WLDFHarvester"});
      parents.put("WLDFHarvestedTypeConfig", new String[]{"WLDFHarvesterConfig"});
      parents.put("WLDFWatch", new String[]{"WLDFWatchNotification"});
      parents.put("WLDFWatchConfig", new String[]{"WLDFWatchNotificationConfig"});
      parents.put("WLDFJMXNotification", new String[]{"WLDFWatchNotification", "WLDFWatch"});
      parents.put("WLDFJMXNotificationConfig", new String[]{"WLDFWatchNotificationConfig", "WLDFWatchConfig"});
      parents.put("WLDFJMSNotification", new String[]{"WLDFWatchNotification", "WLDFWatch"});
      parents.put("WLDFJMSNotificationConfig", new String[]{"WLDFWatchNotificationConfig", "WLDFWatchConfig"});
      parents.put("WLDFSNMPNotification", new String[]{"WLDFWatchNotification", "WLDFWatch"});
      parents.put("WLDFSNMPNotificationConfig", new String[]{"WLDFWatchNotificationConfig", "WLDFWatchConfig"});
      parents.put("WLDFSMTPNotification", new String[]{"WLDFWatchNotification", "WLDFWatch"});
      parents.put("WLDFSMTPNotificationConfig", new String[]{"WLDFWatchNotificationConfig", "WLDFWatchConfig"});
      parents.put("WLDFImageNotification", new String[]{"WLDFWatchNotification", "WLDFWatch"});
      parents.put("WLDFImageNotificationConfig", new String[]{"WLDFWatchNotificationConfig", "WLDFWatchConfig"});
      parents.put("JTARecoveryRuntime", new String[]{"JTARuntime"});
      parents.put("TransactionResourceRuntime", new String[]{"JTARuntime"});
      parents.put("TransactionNameRuntime", new String[]{"JTARuntime"});
      parents.put("NonXAResourceRuntime", new String[]{"JTARuntime"});
      parents.put("SubDeployment", new String[]{"SubDeployment", "Deployable"});
      parents.put("SubDeploymentConfig", new String[]{"SubDeploymentConfig", "DeployableConfig"});
      parents.put("PersistentStoreConnectionRuntime", new String[]{"PersistentStoreRuntime"});
      parents.put("ServerMigrationRuntime", new String[]{"ClusterRuntime"});
      parents.put("MigrationDataRuntime", new String[]{"ServerMigrationRuntime"});
      parents.put("JobSchedulerRuntime", new String[]{"ClusterRuntime"});
      parents.put("JobRuntime", new String[]{"JobSchedulerRuntime"});
      parents.put("UnicastMessagingRuntime", new String[]{"ClusterRuntime"});
      ABSTRACT_TYPES = new String[]{"Configuration", "Deployment", "WebDeployment", "WebLogic"};
      keys = new HashSet(Arrays.asList((Object[])(new String[]{"Type", "Name", "Location"})));
   }
}
