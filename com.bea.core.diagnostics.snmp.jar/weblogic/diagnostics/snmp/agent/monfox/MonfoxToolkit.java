package weblogic.diagnostics.snmp.agent.monfox;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.SnmpInt;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpString;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpAccessPolicy;
import monfox.toolkit.snmp.agent.SnmpAgent;
import monfox.toolkit.snmp.agent.SnmpMib;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibTableRow;
import monfox.toolkit.snmp.agent.V2cacm;
import monfox.toolkit.snmp.agent.ext.table.SnmpMibTableAdaptor;
import monfox.toolkit.snmp.agent.x.master.MasterAgentX;
import monfox.toolkit.snmp.engine.SnmpTransportException;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpModule;
import monfox.toolkit.snmp.metadata.SnmpModuleIdentityInfo;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;
import monfox.toolkit.snmp.metadata.SnmpOidInfo;
import monfox.toolkit.snmp.metadata.SnmpTableInfo;
import monfox.toolkit.snmp.metadata.SnmpTableInfo.Editor;
import monfox.toolkit.snmp.metadata.builder.MibApi;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.snmp.agent.SNMPAgentToolkit;
import weblogic.diagnostics.snmp.agent.SNMPAgentToolkitException;
import weblogic.diagnostics.snmp.agent.SNMPNotificationManager;
import weblogic.diagnostics.snmp.agent.SNMPProxyManager;
import weblogic.diagnostics.snmp.agent.SNMPSubAgentX;
import weblogic.diagnostics.snmp.agent.SNMPTargetManager;
import weblogic.diagnostics.snmp.agent.SNMPUtil;
import weblogic.diagnostics.snmp.i18n.SNMPLogger;

public class MonfoxToolkit implements SNMPAgentToolkit {
   protected static final String VENDOR_NAME = "Monfox";
   protected static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugSNMPAgent");
   protected static final int AGENTX_MAX_RETRY = 3;
   protected SnmpMetadata snmpMetadata;
   protected SnmpAgent snmpAgent;
   protected SnmpMib snmpMib;
   protected Map snmpTables = new HashMap();
   protected Map subAgents = new HashMap();
   protected TargetManager targetManager;
   protected NotificationManager notificationManager;
   protected String snmpAgentListenAddress;
   protected int snmpAgentUDPPort;
   protected MasterAgentX masterAgentX;
   protected String masterAgentXHost;
   protected int masterAgentXPort;
   protected ProxyManager proxyManager;
   protected int maxPortRetryCount = 10;
   private MibApi mibApi;
   private Map tableEditors = new HashMap();

   public String getVendorName() {
      return "Monfox";
   }

   public void initializeSNMPAgentToolkit(String mibBasePath, String mibResources) throws SNMPAgentToolkitException {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Initializing toolkit from " + mibBasePath + " with resources = " + mibResources);
      }

      try {
         Logger.setProvider(new MonfoxDebugLogger.ProviderImpl());
         Logger.getProvider().enableAll();
         SnmpFramework.isCachingOidInfo(false);
         SnmpFramework.setMetadata((SnmpMetadata)null);
         this.snmpMetadata = SnmpFramework.loadMibs(mibResources, mibBasePath);
         SnmpFramework.getCharacterSetSupport().setDefaultCharSet("UTF-8");
         this.snmpMib = new SnmpMib(this.snmpMetadata);
         this.snmpMib.isPrepareForAccessSupported(true);
         if (DEBUG_LOGGER.isDebugEnabled()) {
            Enumeration e = this.snmpMetadata.getModuleIdentities();

            while(e.hasMoreElements()) {
               DEBUG_LOGGER.debug("Module identity found " + e.nextElement());
            }
         }

         this.mibApi = new MibApi(this.snmpMetadata);
      } catch (Exception var4) {
         throw new SNMPAgentToolkitException(var4);
      }
   }

   public void startSNMPAgent(int udpListenPort) throws SNMPAgentToolkitException {
      try {
         for(int port = udpListenPort; port <= udpListenPort + this.maxPortRetryCount; ++port) {
            if (port != 162) {
               try {
                  if (DEBUG_LOGGER.isDebugEnabled()) {
                     DEBUG_LOGGER.debug("Trying UDP port " + port);
                  }

                  this.snmpAgentUDPPort = port;
                  this.snmpAgent = new SnmpAgent(this.snmpAgentUDPPort, this.snmpMib);
                  if (DEBUG_LOGGER.isDebugEnabled()) {
                     DEBUG_LOGGER.debug("Claimed UDP port " + port);
                  }
                  break;
               } catch (SnmpTransportException var4) {
                  if (DEBUG_LOGGER.isDebugEnabled()) {
                     DEBUG_LOGGER.debug("UDP Port seems to be taken" + port);
                  }

                  this.snmpAgent = null;
                  if (port == udpListenPort + this.maxPortRetryCount) {
                     throw new SNMPAgentToolkitException(var4);
                  }
               }
            }
         }

         this.targetManager = new TargetManager(this.snmpAgent.getTarget());
         this.notificationManager = new NotificationManager(this.snmpMetadata, this.snmpAgent.getNotifier());
         this.proxyManager = new ProxyManager(this.snmpAgent);
         SNMPLogger.logStartedSNMPagent(this.snmpAgentUDPPort);
      } catch (Throwable var5) {
         throw new SNMPAgentToolkitException(var5);
      }
   }

   public void initializeMasterAgentX(String listenAddress, int listenPort, String notifyName) throws SNMPAgentToolkitException {
      for(int port = listenPort; port <= listenPort + this.maxPortRetryCount; ++port) {
         if (port != 162 && port != 161) {
            try {
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("Trying TCP port " + port);
               }

               this.masterAgentXPort = port;
               this.masterAgentX = new MasterAgentX(this.snmpAgent, InetAddress.getByName(listenAddress), this.masterAgentXPort, 3);
               this.masterAgentXHost = listenAddress;
               this.masterAgentX.addNotifyName(notifyName);
               this.masterAgentX.startup();
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("Claimed TCP port " + port);
               }
               break;
            } catch (IOException var6) {
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("TCP Port seems to be taken" + port);
               }

               this.masterAgentX = null;
               if (port == listenPort + this.maxPortRetryCount) {
                  throw new SNMPAgentToolkitException(var6);
               }
            } catch (Throwable var7) {
               throw new SNMPAgentToolkitException(var7);
            }
         }
      }

   }

   private int getTCPPortAvailable(int listenPort) {
      ServerSocket socket = null;
      int i = 0;

      while(i < this.maxPortRetryCount) {
         int port = listenPort + i;

         try {
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("Trying TCP port " + port);
            }

            socket = new ServerSocket(port);
            socket.close();
            return port;
         } catch (IOException var6) {
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("Port seems to be taken" + port);
            }

            ++i;
         }
      }

      return listenPort;
   }

   public void stopSNMPAgent() throws SNMPAgentToolkitException {
      try {
         this.proxyManager.cleanup();
         this.notificationManager.cleanup();
         this.targetManager.cleanup();
      } catch (Throwable var6) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Exception cleaning up", var6);
         }
      }

      try {
         if (this.snmpAgent != null) {
            this.snmpAgent.shutdown();
         }
      } catch (SnmpTransportException var4) {
         throw new SNMPAgentToolkitException(var4);
      }

      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Shutdown SNMP Agent");
      }

      try {
         Iterator i = this.snmpTables.keySet().iterator();

         while(i.hasNext()) {
            Object key = i.next();
            SnmpMibTableAdaptor table = (SnmpMibTableAdaptor)this.snmpTables.get(key);
            table.clearAllNodes();
            table.removeAllRows();
            this.snmpMib.remove(table);
         }

         this.snmpTables.clear();
         this.cleanUpSubAgents();
         this.subAgents.clear();
         if (this.masterAgentX != null) {
            this.masterAgentX.shutdown();
         }

         this.masterAgentX = null;
         this.snmpAgent = null;
         this.mibApi = null;
         this.snmpMetadata = null;
         this.targetManager = null;
         this.notificationManager = null;
         SnmpFramework.setMetadata((SnmpMetadata)null);
      } catch (Throwable var5) {
         throw new SNMPAgentToolkitException(var5);
      }
   }

   private void cleanUpSubAgents() {
      Iterator i = this.subAgents.keySet().iterator();

      while(i.hasNext()) {
         SNMPSubAgentX subAgent = (SNMPSubAgentX)this.subAgents.get(i.next());
         subAgent.deleteAllSNMPTableRows();
      }

   }

   public void setSNMPCommunity(String community, String oid) throws SNMPAgentToolkitException {
      try {
         V2cacm acm = (V2cacm)this.snmpAgent.getAccessControlModel();
         SnmpAccessPolicy ro = acm.addAccessPolicy(community, false);
         ro.getView().includeSubTree(new SnmpOid(this.snmpMetadata, oid));
      } catch (Throwable var5) {
         throw new SNMPAgentToolkitException(var5);
      }
   }

   public void createSNMPMibTables(String moduleNames) throws SNMPAgentToolkitException {
      StringTokenizer st = new StringTokenizer(moduleNames, ":");
      Set modules = new HashSet();

      while(st.hasMoreTokens()) {
         String module = st.nextToken();
         if (module != null && module.length() != 0) {
            modules.add(module);
         }
      }

      Enumeration e = this.snmpMetadata.getTables();

      while(e.hasMoreElements()) {
         SnmpTableInfo tableInfo = (SnmpTableInfo)e.nextElement();
         if (modules.contains(tableInfo.getModule().getName())) {
            String tableName = tableInfo.getName();
            if (!this.snmpTables.containsKey(tableName)) {
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("Creating MIB table " + tableName);
               }

               this.createSNMPMibTable(tableName);
            }
         }
      }

   }

   private void createSNMPMibTable(String snmpTableName) throws SNMPAgentToolkitException {
      try {
         SnmpMibTableAdaptor tableAdaptor = new SnmpMibTableAdaptor(this.snmpMetadata, snmpTableName);
         tableAdaptor.isLazyLoadingEnabled(false);
         this.snmpMib.add(tableAdaptor);
         this.snmpTables.put(snmpTableName, tableAdaptor);
      } catch (SnmpMibException var3) {
         throw new SNMPAgentToolkitException(var3);
      } catch (SnmpValueException var4) {
         throw new SNMPAgentToolkitException(var4);
      }
   }

   public void addSNMPTableRow(String tableName, Map attributeValues) throws SNMPAgentToolkitException {
      SnmpMibTableAdaptor tableAdaptor = (SnmpMibTableAdaptor)this.snmpTables.get(tableName);
      if (tableAdaptor != null) {
         TableRow row = new TableRow(attributeValues);

         try {
            tableAdaptor.addRow(row);
         } catch (Exception var6) {
            throw new SNMPAgentToolkitException(var6);
         }
      }

   }

   public void addSNMPTableRow(String tableName, String[] indexColVals, Map attributeValues) throws SNMPAgentToolkitException {
      SnmpMibTableAdaptor table = (SnmpMibTableAdaptor)this.snmpTables.get(tableName);

      try {
         SnmpMibTableRow row = table.addRow(indexColVals);
         String key;
         Object snmpValue;
         if (attributeValues != null) {
            for(Iterator var6 = attributeValues.entrySet().iterator(); var6.hasNext(); row.setLeafValue(key, (SnmpValue)snmpValue)) {
               Object obj = var6.next();
               Map.Entry entry = (Map.Entry)obj;
               key = (String)entry.getKey();
               Object val = entry.getValue();
               if (val == null) {
                  val = "";
               }

               if (val instanceof Integer) {
                  snmpValue = new SnmpInt((Integer)val);
               } else {
                  snmpValue = new SnmpString(val.toString());
               }
            }
         }

      } catch (Exception var12) {
         throw new SNMPAgentToolkitException(var12);
      }
   }

   public void removeSNMPTableRow(String tableName, String[] indexColVals) throws SNMPAgentToolkitException {
      SnmpMibTableAdaptor table = (SnmpMibTableAdaptor)this.snmpTables.get(tableName);

      try {
         table.removeRow(indexColVals);
      } catch (Throwable var5) {
         throw new SNMPAgentToolkitException(var5);
      }
   }

   public Map getSNMPTablesMetadata(String moduleName) throws SNMPAgentToolkitException {
      Map result = new HashMap();
      Enumeration e = this.snmpMetadata.getTables();

      while(e.hasMoreElements()) {
         SnmpTableInfo tableInfo = (SnmpTableInfo)e.nextElement();
         if (tableInfo.getModule().getName().equals(moduleName)) {
            String tableName = tableInfo.getName();
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("Adding table " + tableName);
            }

            SnmpObjectInfo[] cols = tableInfo.getColumns();
            result.put(tableName, this.getColumnNames(cols));
         }
      }

      return result;
   }

   public void updateSNMPTable(String moduleName, String tableName, String tableDesc) throws SNMPAgentToolkitException {
      Enumeration e = this.snmpMetadata.getTables();

      while(e.hasMoreElements()) {
         SnmpTableInfo tableInfo = (SnmpTableInfo)e.nextElement();
         if (tableInfo.getModule().getName().equals(moduleName)) {
            String name = tableInfo.getName();
            if (name.equals(tableName)) {
               tableInfo.setDescription(tableDesc);
            }
         }
      }

   }

   private String[] getColumnNames(SnmpObjectInfo[] cols) {
      List colNames = new ArrayList();

      for(int i = 0; i < cols.length; ++i) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Adding column " + cols[i].getName());
         }

         colNames.add(cols[i].getName());
      }

      String[] result = new String[colNames.size()];
      colNames.toArray(result);
      return result;
   }

   public SNMPTargetManager getTargetManager() {
      return this.targetManager;
   }

   public SNMPNotificationManager getSNMPNotificationManager() {
      return this.notificationManager;
   }

   public SNMPSubAgentX createSNMPSubAgentX(String subAgentId, String oidSubTree) throws SNMPAgentToolkitException {
      SNMPSubAgentXImpl subAgent = new SNMPSubAgentXImpl(this.masterAgentXHost, this.masterAgentXPort, subAgentId, oidSubTree);
      this.subAgents.put(subAgentId, subAgent);
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Created subagent " + subAgentId);
      }

      return subAgent;
   }

   public SNMPSubAgentX findSNMPSubAgentX(String subAgentId) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Subagent id " + subAgentId + " exists=" + this.subAgents.containsKey(subAgentId));
      }

      return (SNMPSubAgentX)this.subAgents.get(subAgentId);
   }

   public String getSNMPAgentListenAddress() {
      return this.snmpAgentListenAddress;
   }

   public int getSNMPAgentUDPPort() {
      return this.snmpAgentUDPPort;
   }

   public String getMasterAgentXListenAddress() {
      return this.masterAgentXHost;
   }

   public int getMasterAgentXPort() {
      return this.masterAgentXPort;
   }

   public SNMPProxyManager getSNMPProxyManager() {
      return this.proxyManager;
   }

   public int getMaxPortRetryCount() {
      return this.maxPortRetryCount;
   }

   public void setMaxPortRetryCount(int maxPortRetryCount) {
      if (maxPortRetryCount > 10) {
         this.maxPortRetryCount = maxPortRetryCount;
      }

   }

   public void addModuleIdentityInfo(String modificationDate, String organization, String contactInfo, String description, String moduleName, String rootOid, String symbolicName) {
      SnmpModuleIdentityInfo mod_ident = new SnmpModuleIdentityInfo(modificationDate, organization, contactInfo, description);
      this.snmpMetadata.add(moduleName, rootOid, symbolicName, mod_ident);
   }

   public void createSNMPTable(String moduleName, String tableName, String tableDesc, String tableOid, List props) throws SNMPAgentToolkitException {
      try {
         SnmpOid oid = new SnmpOid(tableOid);
         MibApi.Table table = this.mibApi.createTable(moduleName, tableName, oid, tableDesc);
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Creating table " + tableName + " with oid " + tableOid + " and description " + tableDesc);
         }

         String columnPrefix = tableName;
         if (tableName.endsWith("Table")) {
            columnPrefix = tableName.substring(0, tableName.length() - "Table".length());
         }

         String indexColumn = columnPrefix + "Index";
         table.addColumn(indexColumn, "SNMPv2-TC.DisplayString", "na", "Index column", true);
         String objectNameColumn = columnPrefix + "ObjectName";
         table.addColumn(objectNameColumn, "SNMPv2-TC.DisplayString", "ro", "ObjectName column", false);
         Map columnMetaData = new HashMap();
         columnMetaData.put(indexColumn, "Index");
         columnMetaData.put(objectNameColumn, "ObjectName");
         if (props != null) {
            for(int i = 0; i < props.size(); ++i) {
               PropertyDescriptor pd = (PropertyDescriptor)props.get(i);
               String attributeName = pd.getName();
               Class attributeType = pd.getPropertyType();
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("Discovered attribute " + attributeName + " of type " + attributeType);
               }

               String snmpColumnName = columnPrefix + attributeName;
               String snmpTypeName = this.getSNMPTypeName(attributeType);
               String colDesc = (String)pd.getValue("description");
               colDesc = SNMPUtil.stripHtmlTags(colDesc);
               table.addColumn(snmpColumnName, snmpTypeName, "ro", colDesc, false);
               columnMetaData.put(snmpColumnName, attributeName);
            }
         }

         table.exportToMetadata();
      } catch (Exception var19) {
         throw new SNMPAgentToolkitException(var19);
      }
   }

   public void createSNMPColumn(String tableName, String columnName, Class javaType, String description) throws SNMPAgentToolkitException {
      try {
         SnmpTableInfo tableInfo = this.snmpMetadata.getTable(tableName);
         if (tableInfo != null) {
            SnmpObjectInfo[] columns = tableInfo.getColumns();
            if (columns != null) {
               long maxOid = 0L;

               for(int i = 0; i < columns.length; ++i) {
                  long lastOid = columns[i].getOid().getLast();
                  if (lastOid > maxOid) {
                     maxOid = lastOid;
                  }
               }

               SnmpTableInfo.Editor editor = this.getTableEditor(tableName);
               if (editor != null) {
                  String typeName = this.getSNMPTypeName(javaType);
                  if (DEBUG_LOGGER.isDebugEnabled()) {
                     DEBUG_LOGGER.debug("Adding col" + columnName + "(" + description + ")");
                  }

                  editor.addColumn(columnName, (int)(maxOid + 1L), typeName, "ro", description);
               }

            }
         }
      } catch (Exception var12) {
         throw new SNMPAgentToolkitException(var12);
      }
   }

   public void updateSNMPColumn(String tableName, String columnName, String description) throws SNMPAgentToolkitException {
      try {
         SnmpTableInfo tableInfo = this.snmpMetadata.getTable(tableName);
         if (tableInfo != null) {
            SnmpObjectInfo[] columns = tableInfo.getColumns();
            if (columns != null) {
               for(int i = 0; i < columns.length; ++i) {
                  if (columns[i].getName().equals(columnName)) {
                     columns[i].setDescription(description);
                  }
               }

            }
         }
      } catch (Exception var7) {
         throw new SNMPAgentToolkitException(var7);
      }
   }

   private SnmpTableInfo.Editor getTableEditor(String tableName) throws SnmpValueException {
      SnmpTableInfo.Editor editor = (SnmpTableInfo.Editor)this.tableEditors.get(tableName);
      if (editor == null) {
         SnmpTableInfo tableInfo = this.snmpMetadata.getTable(tableName);
         if (tableInfo == null) {
            return null;
         }

         editor = Editor.newInstance(tableInfo);
         this.tableEditors.put(tableName, editor);
      }

      return editor;
   }

   private String getSNMPTypeName(Class attributeType) {
      String snmpTypeName = "SNMPv2-TC.DisplayString";
      if (attributeType != Long.class && attributeType != Long.TYPE) {
         if (attributeType == Integer.class || attributeType == Integer.TYPE) {
            snmpTypeName = "INTEGER";
         }
      } else {
         snmpTypeName = "Counter64";
      }

      return snmpTypeName;
   }

   public String outputMIBModule(String moduleName) throws SNMPAgentToolkitException {
      return MonfoxUtil.outputMIBModule(this.snmpMetadata, moduleName);
   }

   public void removeSNMPColumn(String tableName, String snmpColName) throws SNMPAgentToolkitException {
      try {
         SnmpTableInfo.Editor editor = this.getTableEditor(tableName);
         if (editor != null) {
            editor.removeColumn(snmpColName);
         }

      } catch (Exception var4) {
         throw new SNMPAgentToolkitException(var4);
      }
   }

   public void completeTableEdit(String tableName) throws SNMPAgentToolkitException {
      try {
         SnmpTableInfo.Editor editor = this.getTableEditor(tableName);
         if (editor != null) {
            editor.complete();
         }

         this.tableEditors.remove(tableName);
      } catch (SnmpValueException var4) {
         throw new SNMPAgentToolkitException(var4);
      }
   }

   public long getMaxLastOid(String moduleName, String suffix) throws SNMPAgentToolkitException {
      try {
         SortedMap oidIndex = new TreeMap();
         SnmpModule module = this.snmpMetadata.getModule(moduleName);
         SnmpOidInfo[] var5 = module.getOidInfo();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            SnmpOidInfo oidInfo = var5[var7];
            String oidName = oidInfo.getName().trim();
            if (oidName.endsWith(suffix)) {
               oidIndex.put(oidInfo.getOid().getLast(), oidInfo);
            }
         }

         return (Long)oidIndex.lastKey();
      } catch (SnmpValueException var10) {
         throw new SNMPAgentToolkitException(var10);
      }
   }
}
