package weblogic.diagnostics.snmp.mib;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.diagnostics.snmp.agent.SNMPAgent;
import weblogic.diagnostics.snmp.agent.SNMPAgentToolkitException;
import weblogic.i18n.logging.NonCatalogLogger;

public class WLSMibMetadataBuilder implements MibConstants {
   private static final NonCatalogLogger LOGGER = new NonCatalogLogger("WLSMibMetadataBuilder");
   private static final int DEFAULT_MIB_METADATA_BUILDER_PORT = 1260;

   public static void main(String[] args) throws SNMPAgentToolkitException, WLSMibMetadataException {
      SNMPAgent agent = new SNMPAgent();
      int argCount = args.length;
      String outFile = "WLSMibMetadata.dat";
      if (argCount > 0) {
         outFile = args[0];
      }

      if (argCount > 1) {
         agent.setMibBasePath(args[1]);
      }

      if (argCount > 2) {
         agent.setMibModules(args[2]);
      }

      if (argCount > 3) {
         agent.setRootOidNode(args[3]);
      }

      SNMPExtensionProviderBuildPlugin buildPlugin = null;
      if (argCount > 4) {
         String buildPluginClass = args[4];

         try {
            Class clz = Class.forName(buildPluginClass);
            buildPlugin = (SNMPExtensionProviderBuildPlugin)clz.newInstance();
         } catch (Exception var12) {
            LOGGER.error("Invalid build plugin class: " + buildPluginClass, var12);
         }
      }

      int port = 1260;
      if (argCount > 5) {
         port = Integer.parseInt(args[5]);
      }

      if (port > 0) {
         agent.setUdpListenPort(port);
      }

      agent.initialize();
      Map snmpTables = agent.getSNMPTablesMetadata();
      WLSMibMetadata wlsMibMetadata = new WLSMibMetadata();
      Iterator iter = snmpTables.keySet().iterator();

      while(iter.hasNext()) {
         String snmpTableName = (String)iter.next();
         String[] snmpColumnNames = (String[])((String[])snmpTables.get(snmpTableName));
         if (!WLSMibMetadataBuilder.Initializer.REMOVED_TABLES.contains(snmpTableName)) {
            buildTypeInfo(buildPlugin, wlsMibMetadata, snmpTableName, snmpColumnNames);
         }
      }

      try {
         saveToDisk(wlsMibMetadata, outFile);
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Writing to disk " + wlsMibMetadata);
         }

      } catch (Throwable var11) {
         throw new WLSMibMetadataException(var11);
      }
   }

   private static void buildTypeInfo(SNMPExtensionProviderBuildPlugin buildPlugin, WLSMibMetadata wlsMibMetadata, String snmpTableName, String[] snmpColumnNames) throws WLSMibMetadataException {
      int entryIndex = snmpTableName.lastIndexOf("Table");
      String wlsTypeName = null;
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Processing snmp table " + snmpTableName);
      }

      String typeName = snmpTableName.substring(0, entryIndex);
      if (WLSMibMetadataBuilder.Initializer.OUTLIERS.containsKey(typeName)) {
         wlsTypeName = (String)WLSMibMetadataBuilder.Initializer.OUTLIERS.get(typeName);
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Found WLS type " + wlsTypeName + " for " + snmpTableName);
         }
      } else {
         List prefixes = new ArrayList();
         Collections.addAll(prefixes, (Object[])SUBSYSTEM_PREFIXES);
         if (typeName.startsWith("wlssnmp")) {
            typeName = typeName.substring(3);
         }

         String prefix = findSubsystemPrefix(prefixes, typeName);
         int len = prefix.length();
         String lowerCasePrefix = prefix;
         String upperCaseprefix = prefix.toUpperCase();
         if (typeName.length() > len) {
            char lower = typeName.charAt(len);
            if (Character.isLowerCase(lower)) {
               lowerCasePrefix = prefix + lower;
               char upper = Character.toUpperCase(lower);
               upperCaseprefix = upperCaseprefix + upper;
            }
         }

         String processedTypeName = typeName.replaceFirst(lowerCasePrefix, upperCaseprefix);
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Processed cheese = " + processedTypeName);
         }

         wlsTypeName = getFullyQualifiedClassName(processedTypeName + "MBean");
         if (wlsTypeName == null && buildPlugin != null) {
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("Using build plugin to fine type for " + snmpTableName);
            }

            wlsTypeName = buildPlugin.getFullyQualifiedClassName(processedTypeName);
         }

         if (wlsTypeName == null) {
            LOGGER.warning("Missing type info for " + snmpTableName);
            return;
         }

         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Found type " + wlsTypeName + " for " + snmpTableName);
         }
      }

      WLSMibTableColumnsMetadata colsMetadata = getColumnsMetadata(typeName, snmpColumnNames);
      validateAttributes(wlsTypeName, colsMetadata.getAttributeNames());
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Validated WLS type " + wlsTypeName + " for " + snmpTableName);
      }

      wlsMibMetadata.snmpTableNameToWLSTypeName.put(snmpTableName, wlsTypeName);
      wlsMibMetadata.wlsTypeNameToSNMPTableName.put(wlsTypeName, snmpTableName);
      wlsMibMetadata.snmpTableNameToColumns.put(snmpTableName, colsMetadata);
      wlsMibMetadata.wlsTypeNameToColumns.put(wlsTypeName, colsMetadata);
   }

   private static void saveToDisk(WLSMibMetadata wlsMibMetadata, String outFile) throws IOException {
      FileOutputStream fos = new FileOutputStream(outFile);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(wlsMibMetadata);
      LOGGER.notice("Generated MIB metadata to " + outFile);
   }

   private static void validateAttributes(String wlsTypeName, Iterator attributeNames) throws IllegalArgumentException {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Validating type " + wlsTypeName);
      }

      Class clz = null;

      try {
         clz = Class.forName(wlsTypeName);
      } catch (Exception var8) {
         throw new IllegalArgumentException(var8);
      }

      while(attributeNames.hasNext()) {
         String attributeName = (String)attributeNames.next();
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Validating attribute " + wlsTypeName + "." + attributeName);
         }

         Method getter = null;

         try {
            getter = clz.getMethod("get" + attributeName);
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("Found getter " + getter.getName() + " on type " + wlsTypeName);
            }

            if (getter != null) {
               Class retType = getter.getReturnType();
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("Attribute " + wlsTypeName + "." + attributeName + " return type = " + retType);
               }
            }
         } catch (Exception var7) {
            try {
               getter = clz.getMethod("is" + attributeName);
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("Found getter " + getter.getName() + " on type " + wlsTypeName);
               }
            } catch (Exception var6) {
               System.err.println("No attribute " + attributeName + " defined on " + wlsTypeName);
            }
         }
      }

   }

   private static WLSMibTableColumnsMetadata getColumnsMetadata(String typeName, String[] snmpColumnNames) {
      List tuples = new ArrayList();

      for(int i = 0; i < snmpColumnNames.length; ++i) {
         String snmpColName = snmpColumnNames[i];
         if (WLSMibMetadataBuilder.Initializer.OVERRIDES_REVERSE.containsKey(snmpColName)) {
            snmpColName = (String)WLSMibMetadataBuilder.Initializer.OVERRIDES_REVERSE.get(snmpColumnNames[i]);
         }

         int len = typeName.length();
         if (typeName.startsWith("snmp") && snmpColumnNames[i].startsWith("wlssnmp")) {
            len += 3;
         }

         String attributeName = snmpColName.substring(len);
         tuples.add(new String[]{snmpColumnNames[i], attributeName});
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Discovered attribute " + attributeName + " for type " + typeName + " , snmp column name = " + snmpColumnNames[i]);
         }
      }

      return new WLSMibTableColumnsMetadata(tuples);
   }

   private static String getFullyQualifiedClassName(String typeName) {
      String config;
      try {
         config = "weblogic.management.runtime." + typeName;
         Class.forName(config);
         return config;
      } catch (Exception var3) {
         try {
            config = "weblogic.management.configuration." + typeName;
            Class.forName(config);
            return config;
         } catch (Exception var2) {
            return null;
         }
      }
   }

   private static String findSubsystemPrefix(List prefixes, String typeName) {
      String result = "";
      Iterator i = prefixes.iterator();

      String prefix;
      do {
         if (!i.hasNext()) {
            return result;
         }

         prefix = (String)i.next();
      } while(!typeName.startsWith(prefix));

      return prefix;
   }

   public static boolean isColumnNameOverridden(String columnName) {
      boolean result = WLSMibMetadataBuilder.Initializer.OVERRIDES.containsKey(columnName);
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("isColumnNameOverriden for column " + columnName + "::returning " + result);
      }

      return result;
   }

   public static String getColumnNameOverride(String columnName) {
      String value = (String)WLSMibMetadataBuilder.Initializer.OVERRIDES.get(columnName);
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("getColumnNameOverride() for column " + columnName + "::returning " + value);
      }

      return value;
   }

   public static boolean isTableNameRemoved(String tableName) {
      return WLSMibMetadataBuilder.Initializer.REMOVED_TABLES.contains(tableName);
   }

   private static final class Initializer {
      private static final Map OUTLIERS = new HashMap() {
         {
            this.put("snmpAgent", "weblogic.management.configuration.SNMPAgentMBean");
            this.put("wsReliableDeliveryPolicy", "weblogic.management.configuration.WSReliableDeliveryPolicyMBean");
            this.put("wtctBridgeGlobal", "weblogic.management.configuration.WTCtBridgeGlobalMBean");
            this.put("wtctBridgeRedirect", "weblogic.management.configuration.WTCtBridgeRedirectMBean");
            this.put("jrockitRuntime", "weblogic.management.runtime.JRockitRuntimeMBean");
            this.put("xmlEntitySpecRegistry", "weblogic.management.configuration.XMLEntitySpecRegistryEntryMBean");
            this.put("xmlParserSelectRegistry", "weblogic.management.configuration.XMLParserSelectRegistryEntryMBean");
            this.put("componentRuntime", "weblogic.management.runtime.ComponentRuntimeMBean");
            this.put("commonLog", "weblogic.management.configuration.CommonLogMBean");
            this.put("scalingTaskRuntime", "weblogic.management.runtime.ScalingTaskRuntimeMBean");
            this.put("managedThreadFactory", "weblogic.management.configuration.ManagedThreadFactoryMBean");
            this.put("managedThreadFactoryTemplate", "weblogic.management.configuration.ManagedThreadFactoryTemplateMBean");
            this.put("managedThreadFactoryRuntime", "weblogic.management.runtime.ManagedThreadFactoryRuntimeMBean");
            this.put("managedExecutorService", "weblogic.management.configuration.ManagedExecutorServiceMBean");
            this.put("managedExecutorServiceTemplate", "weblogic.management.configuration.ManagedExecutorServiceTemplateMBean");
            this.put("managedScheduledExecutorService", "weblogic.management.configuration.ManagedScheduledExecutorServiceMBean");
            this.put("managedScheduledExecutorServiceTemplate", "weblogic.management.configuration.ManagedScheduledExecutorServiceTemplateMBean");
            this.put("managedScheduledExecutorServiceRuntime", "weblogic.management.runtime.ManagedScheduledExecutorServiceRuntimeMBean");
         }
      };
      private static final Map OVERRIDES = new HashMap() {
         {
            this.put("connectorConnectionPoolRuntimeConnectionsDestroyedByErrorTotalCount", "connectorConnectionPoolRuntimeConnectionsDestroyedByErrorTotalCt");
            this.put("connectorConnectionPoolRuntimeConnectionsDestroyedByShrinkingTotalCount", "connectorConnectionPoolRuntimeConsDestroyedByShrinkingTotalCount");
            this.put("singleSignOnServicesWantTransportLayerSecurityClientAuthentication", "singleSignOnServicesWantTransportLayerSecurityClientAtn");
            this.put("springTransactionManagerRuntimeGlobalRollbackOnParticipationFailure", "springTransactionManagerRuntimeGlobalRollbkOnParticipationFailure");
            this.put("transactionNameRuntimeTransactionReadOnlyOnePhaseCommittedTotalCount", "txNameRuntimeTxReadOnlyOnePhaseCommittedTotalCount");
            this.put("transactionNameRuntimeTransactionOneResourceOnePhaseCommittedTotalCount", "txNameRuntimeTxOneResourceOnePhaseCommittedTotalCount");
            this.put("managedScheduledExecutorServiceTemplateMaxConcurrentLongRunningRequests", "managedScheduledExecutorSvcTmplMaxConcurrentLongRunningRequests");
            this.put("managedScheduledExecutorServiceRuntimeSubmitedShortRunningRequests", "managedScheduledExecutorSvcRuntimeSubmitedShortRunningRequests");
            this.put("managedScheduledExecutorServiceRuntimeRejectedLongRunningRequests", "managedScheduledExecutorSvcRuntimeRejectedLongRunningRequests");
            this.put("managedScheduledExecutorServiceRuntimeCompletedShortRunningRequests", "managedScheduledExecutorSvcRuntimeCompletedShortRunningRequests");
            this.put("managedScheduledExecutorServiceRuntimeRejectedShortRunningRequests", "managedScheduledExecutorSvcRuntimeRejectedShortRunningRequests");
            this.put("managedScheduledExecutorServiceRuntimeCompletedLongRunningRequests", "managedScheduledExecutorSvcRuntimeCompletedLongRunningRequests");
            this.put("managedScheduledExecutorServiceRuntimeSubmittedLongRunningRequests", "managedScheduledExecutorSvcRuntimeSubmittedLongRunningRequests");
            this.put("jtaPartitionRuntimeTransactionTwoPhaseCommittedNotLoggedTotalCount", "jtaPartitionRuntimeTxTwoPhaseCommittedNotLoggedTotalCount");
            this.put("jtaPartitionRuntimeTransactionReadOnlyOnePhaseCommittedTotalCount", "jtaPartitionRuntimeTxReadOnlyOnePhaseCommittedTotalCount");
            this.put("jtaPartitionRuntimeTransactionOneResourceOnePhaseCommittedTotalCount", "jtaPartitionRuntimeTxOneResourceOnePhaseCommittedTotalCount");
            this.put("transactionNameRuntimeTransactionTwoPhaseCommittedNotLoggedTotalCount", "txNameRuntimeTransactionTwoPhaseCommittedNotLoggedTotalCount");
            this.put("transactionNameRuntimeTransactionTwoPhaseCommittedLoggedTotalCount", "txNameRuntimeTransactionTwoPhaseCommittedLoggedTotalCount");
            this.put("coherenceClusterSystemResourceUsingCustomClusterConfigurationFile", "coherenceClusterSystemResourceUsingCustomClusterConfigFile");
            this.put("jdbcReplayStatisticsRuntimeTotalCallsAffectedByOutagesDuringReplay", "jdbcReplayStatsRuntimeTotalCallsAffectedByOutagesDuringReplay");
            this.put("wseePortConfigurationRuntimePolicySubjectAbsolutePortableExpression", "wseePortConfigRuntimePolicySubjectAbsolutePortableExpression");
         }
      };
      private static final Map OVERRIDES_REVERSE = new HashMap() {
         {
            Iterator i = WLSMibMetadataBuilder.Initializer.OVERRIDES.entrySet().iterator();

            while(i.hasNext()) {
               Map.Entry entry = (Map.Entry)i.next();
               this.put(entry.getValue(), entry.getKey());
            }

         }
      };
      static final Set REMOVED_TABLES = new HashSet() {
         {
            this.add("webServiceRuntimeTable");
            this.add("coherenceManagementJMXAddressTable");
            this.add("coherenceMetricsRuntimeTable");
            this.add("securityTable");
            this.add("ldapAuthenticatorRuntimeTable");
            this.add("wlecConnectionPoolRuntimeTable");
         }
      };
   }
}
