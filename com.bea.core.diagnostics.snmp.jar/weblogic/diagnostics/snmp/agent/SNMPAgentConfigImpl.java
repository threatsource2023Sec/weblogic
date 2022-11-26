package weblogic.diagnostics.snmp.agent;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import weblogic.utils.PropertyHelper;

public class SNMPAgentConfigImpl implements SNMPAgentConfig, Serializable {
   private static final long serialVersionUID = -2930366666336096787L;
   private static final long DEFAULT_INFORM_TIMEOUT_MS = 10000L;
   private static final boolean DEBUG = false;
   private String mibBasePath = PropertyHelper.getProperty("weblogic.diagnostics.snmp.mib.mibBasePath", "/weblogic/diagnostics/snmp/mib");
   private String mibModules = PropertyHelper.getProperty("weblogic.diagnostics.snmp.mib.mibModules", "BEA-WEBLOGIC-MIB");
   private String udpListenAddress;
   private int udpListenPort = 161;
   private String community = "public";
   private boolean automaticTrapsEnabled = true;
   private boolean informEnabled = false;
   private int snmpTrapVersion = 1;
   private List snmpTrapDestinations = new LinkedList();
   private List snmpProxyAgents = new LinkedList();
   private int informRetryCount = 1;
   private long informTimeout = 10000L;
   private int maxPortRetryCount = 10;
   private String rootOidNode = "bea";
   private transient SNMPAgent snmpAgent = new SNMPAgent();
   private String name;

   public SNMPAgent getSNMPAgent() {
      return this.snmpAgent;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getCommunity() {
      return this.community;
   }

   public void setCommunity(String community) {
      this.community = community;
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

   public void setMibModules(String mibModules) {
      this.mibModules = mibModules;
   }

   public String getUdpListenAddress() {
      return this.udpListenAddress;
   }

   public void setUdpListenAddress(String udpListenAddress) {
      this.udpListenAddress = udpListenAddress;
   }

   public int getUdpListenPort() {
      return this.udpListenPort;
   }

   public void setUdpListenPort(int udpListenPort) {
      this.udpListenPort = udpListenPort;
   }

   public boolean isAutomaticTrapsEnabled() {
      return this.automaticTrapsEnabled;
   }

   public void setAutomaticTrapsEnabled(boolean b) {
      this.automaticTrapsEnabled = b;
   }

   public boolean isInformEnabled() {
      return this.informEnabled;
   }

   public void setInformEnabled(boolean informEnabled) {
      this.informEnabled = informEnabled;
   }

   public int getSNMPTrapVersion() {
      return this.snmpTrapVersion;
   }

   public void setSNMPTrapVersion(int snmpTrapVersion) {
      this.snmpTrapVersion = snmpTrapVersion;
   }

   public int getInformRetryCount() {
      return this.informRetryCount;
   }

   public void setInformRetryCount(int informRetryCount) {
      this.informRetryCount = informRetryCount;
   }

   public long getInformTimeout() {
      return this.informTimeout;
   }

   public void setInformTimeout(long informTimeout) {
      this.informTimeout = informTimeout;
   }

   public int getMaxPortRetryCount() {
      return this.maxPortRetryCount;
   }

   public void setMaxPortRetryCount(int maxPortRetryCount) {
      this.maxPortRetryCount = maxPortRetryCount;
   }

   public String getRootOidNode() {
      return this.rootOidNode;
   }

   public void setRootOidNode(String rootOidNode) {
      this.rootOidNode = rootOidNode;
   }

   public void prepare() {
   }

   public void activate() throws SNMPAgentToolkitException {
      this.snmpAgent.shutdown();
      this.snmpAgent.setAutomaticTrapsEnabled(this.automaticTrapsEnabled);
      this.snmpAgent.setCommunity(this.community);
      this.snmpAgent.setInformEnabled(this.informEnabled);
      this.snmpAgent.setInformRetryCount(this.informRetryCount);
      this.snmpAgent.setInformTimeout((int)(this.informTimeout / 10L));
      this.snmpAgent.setMaxPortRetryCount(this.maxPortRetryCount);
      this.snmpAgent.setMibBasePath(this.mibBasePath);
      this.snmpAgent.setMibModules(this.mibModules);
      this.snmpAgent.setRootOidNode(this.rootOidNode);
      this.snmpAgent.setSNMPTrapVersion(this.snmpTrapVersion);
      this.snmpAgent.setTrapDestinations(this.snmpTrapDestinations);
      this.snmpAgent.setUdpListenAddress(this.udpListenAddress);
      this.snmpAgent.setUdpListenPort(this.udpListenPort);
      this.initializeTrapDestinations();
      this.snmpAgent.initialize();
      this.initializeProxies();
   }

   private void initializeProxies() throws SNMPAgentToolkitException {
      if (this.snmpProxyAgents != null) {
         Iterator i = this.snmpProxyAgents.iterator();
         SNMPProxyManager pm = this.snmpAgent.getSNMPAgentToolkit().getSNMPProxyManager();

         while(i.hasNext()) {
            SNMPProxyConfig p = (SNMPProxyConfig)i.next();
            pm.addProxyAgent(p.getProxyName(), p.getAddress(), p.getPort(), p.getOidRoot(), p.getCommunity(), p.getSecurityName(), p.getSecurityLevel(), p.getTimeoutMillis());
         }

      }
   }

   private void initializeTrapDestinations() {
      if (this.snmpTrapDestinations != null) {
         this.snmpAgent.setTrapDestinations(this.snmpTrapDestinations);
      }

   }

   public void prepareUpdate() {
   }

   public void activateUpdate() {
   }

   public void rollbackUpdate() {
   }

   public List getSNMPProxyConfigs() {
      return this.snmpProxyAgents;
   }

   public void setSNMPProxyConfigs(List snmpProxyAgents) {
      this.snmpProxyAgents = snmpProxyAgents;
   }

   public List getSNMPTrapDestinationConfigs() {
      return this.snmpTrapDestinations;
   }

   public void setSNMPTrapDestinationConfigs(List snmpTrapDestinations) {
      this.snmpTrapDestinations = snmpTrapDestinations;
   }
}
