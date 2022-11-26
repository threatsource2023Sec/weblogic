package weblogic.diagnostics.snmp.agent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.snmp.agent.monfox.MonfoxToolkit;
import weblogic.diagnostics.snmp.i18n.SNMPLogger;

public class SNMPAgent {
   protected static final String DEFAULT_MIB_BASEPATH = "/weblogic/diagnostics/snmp/mib";
   protected static final String DEFAULT_MIB_MODULE = "BEA-WEBLOGIC-MIB";
   protected static final int DEFAULT_UDP_PORT = 161;
   protected static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugSNMPAgent");
   protected SNMPAgentToolkit snmpAgentToolkit;
   protected String mibBasePath;
   protected String mibModules;
   protected String udpListenAddress;
   protected int udpListenPort;
   protected String community;
   protected String rootOidNode;
   protected List trapDestinations;
   protected int snmpTrapVersion;
   protected String tagName;
   protected String notifyGroup;
   protected boolean snmpAgentInitialized;
   protected boolean automaticTrapsEnabled;
   protected boolean informEnabled;
   protected int informTimeout;
   protected int informRetryCount;
   protected int maxPortRetryCount;

   public SNMPAgent() {
      this(new MonfoxToolkit());
   }

   public SNMPAgent(SNMPAgentToolkit toolkit) {
      this.mibBasePath = "/weblogic/diagnostics/snmp/mib";
      this.mibModules = "BEA-WEBLOGIC-MIB";
      this.udpListenPort = 161;
      this.community = "public";
      this.rootOidNode = "wls";
      this.trapDestinations = new ArrayList();
      this.snmpTrapVersion = 1;
      this.tagName = "wls";
      this.notifyGroup = "wls-mgrs";
      this.informTimeout = 1000;
      this.informRetryCount = 1;
      this.maxPortRetryCount = 10;
      this.snmpAgentToolkit = toolkit;
   }

   public String getMibBasePath() {
      return this.mibBasePath;
   }

   public void setMibBasePath(String mibBasePath) {
      this.mibBasePath = mibBasePath;
   }

   public String getMibModules() {
      return this.mibModules;
   }

   public void setMibModules(String mibResources) {
      this.mibModules = mibResources;
   }

   public int getUdpListenPort() {
      return this.udpListenPort;
   }

   public void setUdpListenPort(int udpListenPort) {
      this.udpListenPort = udpListenPort;
   }

   public void initialize() throws SNMPAgentToolkitException {
      this.snmpAgentToolkit.initializeSNMPAgentToolkit(this.mibBasePath, this.mibModules);
      this.snmpAgentToolkit.startSNMPAgent(this.udpListenPort);
      this.snmpAgentToolkit.setSNMPCommunity(this.community, this.rootOidNode);
      this.initializeTrapDestinations();
      if (this.automaticTrapsEnabled) {
         this.sendColdStartTrap();
      }

      this.snmpAgentInitialized = true;
   }

   public void shutdown() throws SNMPAgentToolkitException {
      this.snmpAgentToolkit.stopSNMPAgent();
   }

   protected void sendColdStartTrap() throws SNMPAgentToolkitException {
      List varBindings = new ArrayList();
      SNMPNotificationManager notifier = this.snmpAgentToolkit.getSNMPNotificationManager();
      notifier.sendNotification(this.notifyGroup, "coldStart", varBindings);
      SNMPLogger.logAgentColdStartSent();
   }

   protected void initializeTrapDestinations() throws SNMPAgentToolkitException {
      SNMPTargetManager tm = this.snmpAgentToolkit.getTargetManager();
      Iterator i = this.trapDestinations.iterator();

      while(i.hasNext()) {
         SNMPTrapDestination td = (SNMPTrapDestination)i.next();
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Adding SNMPTrapDestination " + td.getName());
         }

         try {
            switch (this.snmpTrapVersion) {
               case 1:
                  tm.addV1TrapDestination(td.getName(), td.getHost(), td.getPort(), this.informTimeout, this.informRetryCount, this.tagName, td.getCommunity());
                  break;
               case 2:
                  tm.addV2TrapDestination(td.getName(), td.getHost(), td.getPort(), this.informTimeout, this.informRetryCount, this.tagName, td.getCommunity());
                  break;
               case 3:
                  tm.addV3TrapDestination(td.getName(), td.getHost(), td.getPort(), this.informTimeout, this.informRetryCount, this.tagName, td.getSecurityName(), td.getSecurityLevel());
                  break;
               default:
                  throw new IllegalArgumentException();
            }
         } catch (Exception var5) {
            SNMPLogger.logInvalidTrapDestination(td.getName(), td.getHost(), td.getPort());
         }
      }

      SNMPLogger.logTrapVersionInfo(this.snmpTrapVersion);
      if (this.informEnabled) {
         this.snmpAgentToolkit.getSNMPNotificationManager().addInformGroup(this.notifyGroup, this.tagName);
      } else {
         this.snmpAgentToolkit.getSNMPNotificationManager().addTrapGroup(this.notifyGroup, this.tagName);
      }

   }

   public Map getSNMPTablesMetadata() throws SNMPAgentToolkitException {
      return this.snmpAgentToolkit.getSNMPTablesMetadata(this.mibModules);
   }

   public SNMPAgentToolkit getSNMPAgentToolkit() {
      return this.snmpAgentToolkit;
   }

   public String getCommunity() {
      return this.community;
   }

   public void setCommunity(String community) {
      this.community = community;
   }

   public String getRootOidNode() {
      return this.rootOidNode;
   }

   public void setRootOidNode(String rootOidNode) {
      this.rootOidNode = rootOidNode;
   }

   public SNMPTrapDestination createSNMPTrapDestination(String name) {
      SNMPTrapDestination td = this.findSNMPTrapDestination(name);
      if (td == null) {
         td = new SNMPTrapDestination(name);
         this.trapDestinations.add(td);
      }

      return td;
   }

   public void destroySNMPTrapDestination(String name) {
      SNMPTrapDestination td = this.findSNMPTrapDestination(name);
      if (td != null) {
         this.trapDestinations.remove(td);
      }

   }

   private SNMPTrapDestination findSNMPTrapDestination(String name) {
      Iterator i = this.trapDestinations.iterator();

      SNMPTrapDestination td;
      do {
         if (!i.hasNext()) {
            return null;
         }

         td = (SNMPTrapDestination)i.next();
      } while(!td.getName().equals("name"));

      return td;
   }

   public int getSNMPTrapVersion() {
      return this.snmpTrapVersion;
   }

   public void setSNMPTrapVersion(int snmpTrapVersion) {
      this.snmpTrapVersion = snmpTrapVersion;
   }

   public String getNotifyGroup() {
      return this.notifyGroup;
   }

   public boolean isSNMPAgentInitialized() {
      return this.snmpAgentInitialized;
   }

   public void setAutomaticTrapsEnabled(boolean b) {
      this.automaticTrapsEnabled = b;
   }

   public boolean isAutomaticTrapsEnabled() {
      return this.automaticTrapsEnabled;
   }

   public boolean isInformEnabled() {
      return this.informEnabled;
   }

   public void setInformEnabled(boolean informEnabled) {
      this.informEnabled = informEnabled;
   }

   public int getInformRetryCount() {
      return this.informRetryCount;
   }

   public void setInformRetryCount(int informRetryCount) {
      this.informRetryCount = informRetryCount;
   }

   public int getInformTimeout() {
      return this.informTimeout;
   }

   public void setInformTimeout(int informTimeout) {
      this.informTimeout = informTimeout;
   }

   public String getUdpListenAddress() {
      return this.udpListenAddress;
   }

   public void setUdpListenAddress(String udpListenAddress) {
      this.udpListenAddress = udpListenAddress;
   }

   public void initializeMasterAgentX(String host, int port) throws SNMPAgentToolkitException {
      this.snmpAgentToolkit.initializeMasterAgentX(host, port, this.notifyGroup);
   }

   public SNMPSubAgentX createSubAgentX(String subAgentId, String subTreeOid) throws SNMPAgentToolkitException {
      return this.getSNMPAgentToolkit().createSNMPSubAgentX(subAgentId, subTreeOid);
   }

   public int getMaxPortRetryCount() {
      return this.maxPortRetryCount;
   }

   public void setMaxPortRetryCount(int maxPortRetryCount) {
      this.maxPortRetryCount = maxPortRetryCount;
   }

   public List getTrapDestinations() {
      return this.trapDestinations;
   }

   public void setTrapDestinations(List trapDestinations) {
      this.trapDestinations = trapDestinations;
   }
}
