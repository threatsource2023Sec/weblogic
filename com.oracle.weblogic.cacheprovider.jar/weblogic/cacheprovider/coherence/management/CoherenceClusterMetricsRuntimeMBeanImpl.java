package weblogic.cacheprovider.coherence.management;

import com.tangosol.discovery.NSLookup;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.QueryExp;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.InvalidKeyException;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import weblogic.cacheprovider.CacheProviderLogger;
import weblogic.cacheprovider.coherence.CoherenceClusterManager;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.CoherenceClusterSystemResourceMBean;
import weblogic.management.configuration.CoherenceManagementAddressProviderMBean;
import weblogic.management.configuration.CoherenceManagementClusterMBean;
import weblogic.management.configuration.CoherenceMemberConfigMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.CoherenceClusterMetricsRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class CoherenceClusterMetricsRuntimeMBeanImpl extends RuntimeMBeanDelegate implements CoherenceClusterMetricsRuntimeMBean {
   private boolean m_fExternalCluster;
   private ObjectName m_reporter;
   private CoherenceManagementClusterMBean m_cmcb;
   private CoherenceClusterSystemResourceMBean m_ccsr;
   private MBeanServerConnection m_mbsc;
   private String m_sGroupFileName;
   private Map m_mapXmlReports;
   private TabularData m_tabularDataCached;
   private long m_lastUpdated;
   public static final int DEFAULT_REFRESH_INTERVAL = 90000;
   private static final int MAX_RETRY = 2;
   private static final String TAG_SOURCE_NODEID = "SourceNodeId";
   private static final String RUN_GROUP_REPORT_METHOD_NAME = "runTabularGroupReport";
   private static final String[] RUN_GROUP_REPORT_SIGNATURE = new String[]{"java.lang.String", "java.util.Map"};
   private static final String CLUSTER_PATTERN_CONTAINER = "Coherence:type=Cluster,Location=?,*";
   private static final String CLUSTER_PATTERN = "Coherence:type=Cluster,*";
   private static final String REPORTER_PATTERN_CONTAINER = "Coherence:type=Reporter,Location=?,member=?,*";
   private static final String REPORTER_PATTERN = "Coherence:type=Reporter,nodeId=?,*";
   private static final String MEMBER_ID_ATTRIBUTE = "LocalMemberId";
   private static final String MEMBER_NAME_KEY = "MemberName";
   private static final String NODE_ID_KEY = "nodeId";
   private static final String LOCATION_KEY = "Location";
   private static final String MGMT_NODE_ID = "ManagementNode";
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public CoherenceClusterMetricsRuntimeMBeanImpl(CoherenceManagementClusterMBean cmcb) throws ManagementException {
      this(cmcb.getName(), cmcb.getReportGroupFile(), cmcb, (CoherenceClusterSystemResourceMBean)null);
   }

   public CoherenceClusterMetricsRuntimeMBeanImpl(CoherenceClusterSystemResourceMBean ccsr) throws ManagementException {
      this(ccsr.getName(), ccsr.getReportGroupFile(), (CoherenceManagementClusterMBean)null, ccsr);
   }

   public CoherenceClusterMetricsRuntimeMBeanImpl(String sName, String sGroupFileName, CoherenceManagementClusterMBean cmcb, CoherenceClusterSystemResourceMBean ccsr) throws ManagementException {
      super(sName);
      this.m_sGroupFileName = sGroupFileName;
      String sHomeDir = ManagementService.getRuntimeAccess(KERNEL_ID).getServerRuntime().getMiddlewareHome();
      File groupFile = new File(sHomeDir + File.separator + sGroupFileName);
      if (groupFile.exists() && groupFile.canRead()) {
         try {
            this.m_mapXmlReports = CoherenceClusterManager.getInstance().loadReporterGroupFileConfig(CoherenceClusterMetricsRuntimeMBeanImpl.class.getClassLoader(), groupFile.getAbsolutePath());
         } catch (Exception var8) {
            var8.printStackTrace();
         }
      }

      this.m_ccsr = ccsr;
      this.m_cmcb = cmcb;
      this.m_fExternalCluster = this.m_cmcb != null;
   }

   public String getType() {
      return "CoherenceClusterRuntimeMetrics";
   }

   public CoherenceManagementClusterMBean getCoherenceManagementCluster() {
      return this.m_cmcb;
   }

   public CoherenceClusterSystemResourceMBean getCoherenceClusterSystemResource() {
      return this.m_ccsr;
   }

   public String getReportGroupFile() {
      return this.m_sGroupFileName;
   }

   public List getNameServiceAddresses() {
      return this.m_fExternalCluster ? this.getAddresses(this.m_cmcb) : this.getAddresses(this.m_ccsr);
   }

   public String[] getInstances() {
      if (this.m_ccsr == null && this.m_cmcb == null) {
         return new String[0];
      } else {
         MBeanServerConnection mbsc = this.ensureMBeanServer();
         return mbsc == null ? new String[0] : new String[]{"ManagementNode"};
      }
   }

   public TabularType[] getSchema(String[] asTables, String[] asNodeNames, Properties properties) {
      TabularData tabularData = this.fetchTabularData(properties);
      if (asTables == null || asTables.length == 0) {
         asTables = this.getTableNames(tabularData);
      }

      return this.getTabularType(asTables, tabularData);
   }

   public TabularData[] getMetrics(String[] asTables, String[] asNodeNames, Properties properties) {
      TabularData tabularData = this.fetchTabularData(properties);
      if (tabularData != null && !tabularData.isEmpty()) {
         if (asTables == null || asTables.length == 0) {
            asTables = this.getTableNames(tabularData);
         }

         int cTables = asTables.length;
         TabularData[] aTabMetrics = new TabularData[cTables];
         TabularType[] aOpenTypeOfTables = this.getTabularType(asTables, tabularData);

         label66:
         for(int iTable = 0; iTable < cTables; ++iTable) {
            TabularType fetchedTableType = aOpenTypeOfTables[iTable];
            if (fetchedTableType != null) {
               List listIndexNames = fetchedTableType.getIndexNames();
               int nColumns = listIndexNames.size() + 1;
               String[] asColumnNames = new String[nColumns];
               String[] asColumnDesc = new String[nColumns];
               OpenType[] aColumnOpenTypes = new OpenType[nColumns];
               asColumnNames[0] = "SourceNodeId";
               asColumnDesc[0] = "SourceNodeId";
               aColumnOpenTypes[0] = SimpleType.STRING;
               int iCol = 1;
               CompositeType prevRowType = fetchedTableType.getRowType();

               for(Iterator iter = listIndexNames.iterator(); iter.hasNext(); ++iCol) {
                  String sIndexName = (String)iter.next();
                  asColumnNames[iCol] = sIndexName;
                  asColumnDesc[iCol] = prevRowType.getDescription(sIndexName);
                  aColumnOpenTypes[iCol] = prevRowType.getType(sIndexName);
               }

               CompositeType rowType;
               try {
                  rowType = new CompositeType(asTables[iTable], "The same table from all nodes", asColumnNames, asColumnDesc, aColumnOpenTypes);
                  TabularType tableType = new TabularType(asTables[iTable], "Metrics for " + asTables[iTable], rowType, asColumnNames);
                  aTabMetrics[iTable] = new TabularDataSupport(tableType);
               } catch (OpenDataException var24) {
                  throw new RuntimeException("Could not add column to pre-existing CompositeType or TabularType", var24);
               }

               TabularData tabTable = (TabularData)((CompositeData)tabularData.values().iterator().next()).get(asTables[iTable]);
               if (tabTable != null) {
                  Iterator iterRows = tabTable.values().iterator();

                  while(true) {
                     HashMap mapRowData;
                     CompositeData rowData;
                     do {
                        if (!iterRows.hasNext()) {
                           continue label66;
                        }

                        mapRowData = new HashMap();
                        mapRowData.put("SourceNodeId", "ManagementNode");
                        rowData = (CompositeData)iterRows.next();
                     } while(rowData == null);

                     for(iCol = 1; iCol < nColumns; ++iCol) {
                        String sColName = asColumnNames[iCol];
                        mapRowData.put(sColName, rowData.get(sColName));
                     }

                     try {
                        aTabMetrics[iTable].put(new CompositeDataSupport(rowType, mapRowData));
                     } catch (Exception var23) {
                        throw new RuntimeException("Could not add a column to a rowfrom table " + asTables[iTable] + " from nodeId=" + "ManagementNode", var23);
                     }
                  }
               }
            }
         }

         return aTabMetrics;
      } else {
         return new TabularData[0];
      }
   }

   protected TabularData fetchTabularData(Properties properties) {
      String sfCache = properties != null ? properties.getProperty("dms.use.cache") : null;
      boolean fCache = sfCache != null && sfCache.equalsIgnoreCase("true");
      TabularData tabularData = null;
      if (fCache && System.currentTimeMillis() - this.m_lastUpdated <= 90000L) {
         tabularData = this.m_tabularDataCached;
      }

      return tabularData == null ? this.runReport() : tabularData;
   }

   protected String[] getTableNames(TabularData tabularData) {
      if (tabularData != null && tabularData.size() != 0) {
         Set setTableNames = new HashSet();
         Object[] aTemp = ((CompositeDataSupport)tabularData.values().iterator().next()).getCompositeType().keySet().toArray();
         Object[] var4 = aTemp;
         int var5 = aTemp.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Object obj = var4[var6];
            setTableNames.add((String)obj);
         }

         return (String[])((String[])setTableNames.toArray(new String[0]));
      } else {
         return new String[0];
      }
   }

   protected TabularType[] getTabularType(String[] asTables, TabularData tabularData) {
      TabularType[] aTabType = new TabularType[asTables.length];
      if (tabularData != null) {
         CompositeData tableTabulars = (CompositeData)tabularData.values().iterator().next();
         int i = 0;
         String[] var6 = asTables;
         int var7 = asTables.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            String sTable = var6[var8];

            try {
               TabularData tabular = (TabularData)tableTabulars.get(sTable);
               if (tabular != null) {
                  aTabType[i] = tabular.getTabularType();
               }
            } catch (InvalidKeyException var11) {
            }

            ++i;
         }
      }

      return aTabType;
   }

   protected TabularData runReport() {
      TabularData tempTab = null;
      int loop = 0;

      while(true) {
         try {
            ObjectName oName = null;
            MBeanServerConnection mbsc = this.ensureMBeanServer();
            if (mbsc != null && (oName = this.getReporterMBean()) != null) {
               if (this.m_mapXmlReports != null) {
                  Object[] aObjParams = new Object[]{this.m_sGroupFileName, this.m_mapXmlReports};
                  tempTab = (TabularData)mbsc.invoke(oName, "runTabularGroupReport", aObjParams, RUN_GROUP_REPORT_SIGNATURE);
                  this.m_tabularDataCached = tempTab;
                  this.m_lastUpdated = System.currentTimeMillis();
               }

               return tempTab;
            }

            return tempTab;
         } catch (Exception var6) {
            ++loop;
            if (loop == 2) {
               CacheProviderLogger.logFailedToInvoke("runTabularGroupReport", this.getName(), var6);
               return tempTab;
            }

            this.m_mbsc = null;
            this.m_reporter = null;
         }
      }
   }

   protected ObjectName getReporterMBean() {
      ObjectName oName = this.m_reporter;
      if (oName == null) {
         if (!ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().isGlobalRuntime()) {
            return this.getDomainPartitionReporterMBean();
         }

         try {
            MBeanServerConnection mbsc = this.ensureMBeanServer();
            ObjectName oClusterName = this.getClusterMBean();
            if (oClusterName != null) {
               String sNodeId = String.valueOf(mbsc.getAttribute(oClusterName, "LocalMemberId"));
               String sLocation = oClusterName.getKeyProperty("Location");
               String sNodePattern = sLocation == null ? "Coherence:type=Reporter,nodeId=?,*".replace("?", sNodeId) : "Coherence:type=Reporter,Location=?,member=?,*".replace("Location=?", "Location=" + sLocation).replace("member=?", "member=" + sLocation);
               Set setReporterMBeans = mbsc.queryNames(new ObjectName(sNodePattern), (QueryExp)null);
               oName = (ObjectName)setReporterMBeans.iterator().next();
               if (oName != null) {
                  this.m_reporter = oName;
               }
            }
         } catch (MalformedObjectNameException var8) {
            throw new RuntimeException("Pre-configured pattern \"Coherence:type=Reporter,nodeId=?,*\" used to query for reporter mbeans was rejected", var8);
         } catch (Exception var9) {
            this.m_mbsc = null;
         }
      }

      return oName;
   }

   protected ObjectName getDomainPartitionReporterMBean() {
      Set setReporterMBeans = null;

      try {
         MBeanServerConnection mbsc = this.ensureMBeanServer();
         Set cohSvcMBeans = mbsc.queryNames(new ObjectName("Coherence:type=Service,*"), (QueryExp)null);
         if (cohSvcMBeans != null) {
            ObjectName oSvcName = (ObjectName)cohSvcMBeans.iterator().next();
            if (oSvcName != null) {
               String sLocation = oSvcName.getKeyProperty("Location");
               String sNodePattern = "Coherence:type=Reporter,Location=?,member=?,*".replace("Location=?", "Location=" + sLocation).replace("member=?", "member=" + sLocation);
               setReporterMBeans = mbsc.queryNames(new ObjectName(sNodePattern), (QueryExp)null);
               ObjectName oName = (ObjectName)setReporterMBeans.iterator().next();
               if (oName != null) {
                  this.m_reporter = oName;
                  return oName;
               }
            }
         }
      } catch (MalformedObjectNameException var8) {
         throw new RuntimeException("Pre-configured pattern \"Coherence:type=Reporter,Location=?,member=?,*\" used to query for reporter mbeans was rejected", var8);
      } catch (Exception var9) {
         this.m_mbsc = null;
      }

      return null;
   }

   protected ObjectName getClusterMBean() {
      Set setClusterMBean = null;

      try {
         MBeanServerConnection mbsc = this.ensureMBeanServer();
         if (this.m_fExternalCluster) {
            setClusterMBean = mbsc.queryNames(new ObjectName("Coherence:type=Cluster,*"), (QueryExp)null);
         } else {
            Set setServers = this.getServers(this.m_ccsr);
            Iterator var4 = setServers.iterator();

            while(var4.hasNext()) {
               ServerMBean server = (ServerMBean)var4.next();
               setClusterMBean = mbsc.queryNames(new ObjectName("Coherence:type=Cluster,Location=?,*".replace("?", server.getName())), (QueryExp)null);
               if (setClusterMBean != null && !setClusterMBean.isEmpty()) {
                  break;
               }
            }
         }
      } catch (MalformedObjectNameException var6) {
         throw new RuntimeException("Pre-configured pattern \"Coherence:type=Cluster,*\" used to query for reporter mbeans was rejected", var6);
      } catch (Exception var7) {
         this.m_mbsc = null;
      }

      return setClusterMBean != null ? (ObjectName)setClusterMBean.iterator().next() : null;
   }

   protected MBeanServerConnection ensureMBeanServer() {
      if (this.m_mbsc == null) {
         this.resetMBeanServerConnection();
      }

      return this.m_mbsc;
   }

   public void resetMBeanServerConnection() {
      MBeanServerConnection mbeanSvrConn = null;
      if (this.m_fExternalCluster) {
         List listAddresses = this.getNameServiceAddresses();
         Iterator var3 = listAddresses.iterator();

         while(var3.hasNext()) {
            SocketAddress socketAddr = (SocketAddress)var3.next();
            JMXServiceURL jmxServiceURL = null;

            try {
               jmxServiceURL = NSLookup.lookupJMXServiceURL(socketAddr);
               if (jmxServiceURL != null) {
                  Map mapEnv = new HashMap();
                  if (this.m_cmcb != null && this.m_cmcb.getUsername() != null && this.m_cmcb.getPassword() != null) {
                     String[] creds = new String[]{this.m_cmcb.getUsername(), this.m_cmcb.getPassword()};
                     mapEnv.put("jmx.remote.credentials", creds);
                  }

                  JMXConnector jmxConnector = JMXConnectorFactory.connect(jmxServiceURL, mapEnv);
                  mbeanSvrConn = jmxConnector.getMBeanServerConnection();
                  break;
               }
            } catch (IOException var8) {
               String sAddr = jmxServiceURL == null ? socketAddr.toString() : jmxServiceURL.getProtocol() + "://" + jmxServiceURL.getHost() + ":" + jmxServiceURL.getPort();
               CacheProviderLogger.logServerNotAvailable(sAddr);
            }
         }

         if (mbeanSvrConn != null) {
            this.m_mbsc = mbeanSvrConn;
         }
      } else {
         this.m_mbsc = ManagementService.getDomainRuntimeMBeanServer(KERNEL_ID);
      }

   }

   protected List getAddresses(CoherenceManagementClusterMBean cmcb) {
      List listAddresses = new ArrayList();
      CoherenceManagementAddressProviderMBean[] aCohMetricsAddrProviders = cmcb.getCoherenceManagementAddressProviders();

      for(int i = 0; i < aCohMetricsAddrProviders.length; ++i) {
         CoherenceManagementAddressProviderMBean cohMgmtAddrMB = aCohMetricsAddrProviders[i];
         int nPort = cohMgmtAddrMB.getPort();

         try {
            InetAddress[] aAddresses = InetAddress.getAllByName(cohMgmtAddrMB.getAddress());
            InetAddress[] var8 = aAddresses;
            int var9 = aAddresses.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               InetAddress address = var8[var10];
               InetSocketAddress socketAddr = new InetSocketAddress(address, nPort);
               listAddresses.add(socketAddr);
            }
         } catch (IOException var13) {
            listAddresses.add(new InetSocketAddress(cohMgmtAddrMB.getAddress(), cohMgmtAddrMB.getPort()));
         }
      }

      return listAddresses;
   }

   protected List getAddresses(CoherenceClusterSystemResourceMBean ccsr) {
      List listAddresses = new ArrayList();
      Set setServers = this.getServers(ccsr);
      Iterator var4 = setServers.iterator();

      while(var4.hasNext()) {
         ServerMBean server = (ServerMBean)var4.next();
         CoherenceMemberConfigMBean cmcb = server.getCoherenceMemberConfig();
         String sIP = cmcb.getUnicastListenAddress();
         if (cmcb.isManagementProxy() && sIP != null) {
            listAddresses.add(new InetSocketAddress(sIP, cmcb.getUnicastListenPort()));
         }
      }

      return listAddresses;
   }

   protected Set getServers(CoherenceClusterSystemResourceMBean ccsr) {
      TargetMBean[] aTargets = ccsr.getTargets();
      Set setServers = new HashSet();
      DomainMBean domainMBean = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain();
      ServerMBean[] var5 = domainMBean.getServers();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         ServerMBean server = var5[var7];
         ClusterMBean cluster = server.getCluster();
         CoherenceClusterSystemResourceMBean foundCCSR = cluster != null && cluster.getCoherenceClusterSystemResource() != null ? cluster.getCoherenceClusterSystemResource() : server.getCoherenceClusterSystemResource();
         if (foundCCSR != null && foundCCSR.getName().equals(ccsr.getName())) {
            setServers.add(server);
         }
      }

      return setServers;
   }
}
