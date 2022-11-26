package weblogic.diagnostics.snmp.agent.monfox;

import java.lang.annotation.Annotation;
import java.net.InetAddress;
import java.security.AccessController;
import java.util.Map;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import monfox.toolkit.snmp.SnmpIpAddress;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpAgent;
import monfox.toolkit.snmp.agent.ext.acm.AppAcm;
import monfox.toolkit.snmp.agent.ext.table.SnmpMibTableAdaptor;
import monfox.toolkit.snmp.engine.SnmpEngineID;
import monfox.toolkit.snmp.engine.SnmpTransportException;
import monfox.toolkit.snmp.engine.TransportProvider;
import weblogic.diagnostics.snmp.agent.SNMPAgentToolkitException;
import weblogic.diagnostics.snmp.agent.SNMPSecurityManager;
import weblogic.diagnostics.snmp.agent.SNMPSubAgentX;
import weblogic.diagnostics.snmp.agent.SNMPTransportProvider;
import weblogic.diagnostics.snmp.agent.SNMPV3AgentToolkit;
import weblogic.diagnostics.snmp.i18n.SNMPLogger;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.protocol.ServerURL;
import weblogic.protocol.URLManagerService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;

public class MonfoxV3Toolkit extends MonfoxToolkit implements SNMPV3AgentToolkit {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private SnmpEngineID snmpEngineId;
   private WLSTcpTransportProvider tcpProvider;
   private SNMPSecurityManager snmpSecurityManager;

   public void initializeSNMPAgentToolkit(String mibBasePath, String mibResources) throws SNMPAgentToolkitException {
      try {
         super.initializeSNMPAgentToolkit(mibBasePath, mibResources);
         TransportProvider.addTransportProvider(2, WLSTcpTransportProvider.class);
      } catch (Exception var4) {
         throw new SNMPAgentToolkitException(var4);
      }
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   public void startSNMPAgent(String engineId, String tcpListenAddress, int tcpPort, String udpListenAddress, int udpListenPort, int engineBootsParam) throws SNMPAgentToolkitException {
      try {
         this.snmpEngineId = new SnmpEngineID(engineId, true);
      } catch (SnmpValueException var13) {
         throw new SNMPAgentToolkitException(var13);
      }

      for(int port = udpListenPort; port <= udpListenPort + this.maxPortRetryCount; ++port) {
         if (port != 162) {
            try {
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("Trying UDP port " + port);
               }

               this.snmpAgentListenAddress = udpListenAddress;
               this.snmpAgentUDPPort = port;
               SnmpAgent.V3Config config = new SnmpAgent.V3Config(this.snmpAgentUDPPort, this.snmpEngineId);
               InetAddress addr = null;
               if (this.snmpAgentListenAddress != null && this.snmpAgentListenAddress.length() > 0) {
                  addr = InetAddress.getByName(this.snmpAgentListenAddress);
                  config.setInetAddress(addr);
               }

               config.setUsmUserSecurityExtension(WLSSecurityExtension.getInstance());
               config.setAccessControlModel(new AppAcm(WLSAccessController.getInstance()));
               config.setAuditTrailLogger(WLSAuditTrailLogger.getInstance());
               this.snmpAgent = new SnmpAgent(this.snmpMib, config);
               if (addr == null) {
                  try {
                     RuntimeAccess rt = ManagementService.getRuntimeAccess(KERNEL_ID);
                     ServerURL serverURL = new ServerURL(getURLManagerService().findAdministrationURL(rt.getServerName()));
                     addr = InetAddress.getByName(serverURL.getHost());
                  } catch (Exception var14) {
                     if (DEBUG_LOGGER.isDebugEnabled()) {
                        DEBUG_LOGGER.debug("Error computing agent address", var14);
                     }
                  }
               }

               if (addr != null) {
                  this.snmpAgent.setAgentAddress(new SnmpIpAddress(addr));
               }

               if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("Claimed UDP port " + port);
               }

               SNMPLogger.logStartedSNMPagent(udpListenPort);
               break;
            } catch (SnmpTransportException var15) {
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("UDP Port seems to be taken" + port);
               }

               this.snmpAgent = null;
               if (port == udpListenPort + this.maxPortRetryCount) {
                  throw new SNMPAgentToolkitException(var15);
               }
            } catch (Throwable var16) {
               throw new SNMPAgentToolkitException(var16);
            }
         }
      }

      try {
         this.snmpAgent.getEngine().setEngineBoots(engineBootsParam);
         this.snmpAgent.setRequestExecManager(new MonfoxExecManager());
         this.snmpAgent.isCommunityAtContextFormSupported(true);
         this.targetManager = new TargetManager(this.snmpAgent.getTarget());
         this.notificationManager = new NotificationManager(this.snmpMetadata, this.snmpAgent.getNotifier());
         this.proxyManager = new ProxyManager(this.snmpAgent);
         this.tcpProvider = (WLSTcpTransportProvider)TransportProvider.newInstance(2, (InetAddress)null, 0);
         this.snmpAgent.getEngine().addTransportProvider(this.tcpProvider);
         this.snmpSecurityManager = new WLSSnmpSecurityManager(this.snmpAgent);
      } catch (Throwable var12) {
         throw new SNMPAgentToolkitException(var12);
      }
   }

   public void stopSNMPAgent() throws SNMPAgentToolkitException {
      if (this.tcpProvider != null) {
         this.snmpAgent.getEngine().removeTransportProvider(this.tcpProvider);
      }

      super.stopSNMPAgent();
   }

   public void setSecurityParams(int securityLevel, int authProtocol, int privacyProtocol, long localizedKeyCacheInvalidationInterval) {
      WLSSecurityExtension securityExtension = WLSSecurityExtension.getInstance();
      securityExtension.setSecurityLevel(securityLevel);
      securityExtension.setAuthProtocol(authProtocol);
      securityExtension.setPrivProtocol(privacyProtocol);
      securityExtension.setLocalizedKeyCacheInvalidationInterval(localizedKeyCacheInvalidationInterval);
      AppAcm acm = (AppAcm)this.snmpAgent.getAccessControlModel();
      WLSAccessController wlsAccessController = (WLSAccessController)acm.getAccessController();
      wlsAccessController.setSnmpEngineId(this.snmpEngineId);
   }

   public SNMPTransportProvider getTransportProvider(int transportType) {
      switch (transportType) {
         case 1:
            return this.tcpProvider;
         default:
            return null;
      }
   }

   public SNMPSecurityManager getSNMPSecurityManager() {
      return this.snmpSecurityManager;
   }

   public void addSNMPTableRowForMBeanInstance(String snmpTableName, MBeanServerConnection mbeanServerConnection, ObjectName on, Map colAttrMap) throws SNMPAgentToolkitException {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Adding table row " + snmpTableName + " for " + on);
      }

      try {
         SnmpMibTableAdaptor tableAdaptor = (SnmpMibTableAdaptor)this.snmpTables.get(snmpTableName);
         if (tableAdaptor != null) {
            MBeanInstanceTableRow row = new MBeanInstanceTableRow(mbeanServerConnection, on, colAttrMap);
            tableAdaptor.addRow(row);
         } else if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Did not find SnmpMibTableAdaptor for " + snmpTableName + " and MBean " + on);
         }

      } catch (Exception var7) {
         throw new SNMPAgentToolkitException(var7);
      }
   }

   public void deleteSNMPTableRowForMBeanInstance(String snmpTableName, ObjectName objectName) throws SNMPAgentToolkitException {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Deleting row " + snmpTableName + " for objectName " + objectName);
      }

      try {
         SnmpMibTableAdaptor tableAdaptor = (SnmpMibTableAdaptor)this.snmpTables.get(snmpTableName);
         if (tableAdaptor != null) {
            String index = MBeanInstanceTableRow.computeIndex(objectName.toString());
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("Deleting row for index " + index);
            }

            tableAdaptor.removeRow(new String[]{index});
         }

      } catch (Exception var5) {
         throw new SNMPAgentToolkitException(var5);
      }
   }

   public void setCommunityBasedAccessEnabled(boolean value) throws SNMPAgentToolkitException {
      AppAcm acm = (AppAcm)this.snmpAgent.getAccessControlModel();
      WLSAccessController wlsAccessController = (WLSAccessController)acm.getAccessController();
      wlsAccessController.setCommunityBasedAccessEnabled(value);
   }

   public void setSNMPCommunity(String community, String oid) throws SNMPAgentToolkitException {
      AppAcm acm = (AppAcm)this.snmpAgent.getAccessControlModel();
      WLSAccessController wlsAccessController = (WLSAccessController)acm.getAccessController();
      wlsAccessController.setCommunity(community);
   }

   public SNMPSubAgentX createSNMPSubAgentX(String subAgentId, String oidSubTree) throws SNMPAgentToolkitException {
      MBeanServerSubAgentXImpl subAgent = new MBeanServerSubAgentXImpl(this.masterAgentXHost, this.masterAgentXPort, subAgentId, oidSubTree);
      this.subAgents.put(subAgentId, subAgent);
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Created subagent " + subAgentId);
      }

      return subAgent;
   }
}
