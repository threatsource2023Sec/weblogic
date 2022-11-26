package weblogic.diagnostics.snmp.agent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import weblogic.diagnostics.snmp.agent.monfox.MonfoxV3Toolkit;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class SNMPV3Agent extends SNMPAgent implements SNMPConstants {
   private WorkManager snmpWorkManager;
   private String engineId;
   private int engineBoots;
   private boolean communityBasedAccessEnabled = true;
   private int securityLevel;
   private int authProtocol;
   private int privProtocol;
   private long localizedKeyCacheInvalidationInterval;
   private long snmpEngineBoots;
   private String snmpDataFileName;
   private String tcpListenAddress;
   private int tcpListenPort;
   private SNMPV3AgentToolkit snmpV3AgentToolkit;

   public SNMPV3Agent() {
      super(new MonfoxV3Toolkit());
      this.snmpV3AgentToolkit = (SNMPV3AgentToolkit)super.snmpAgentToolkit;
   }

   public void initialize() throws SNMPAgentToolkitException {
      this.snmpWorkManager = WorkManagerFactory.getInstance().findOrCreate("SnmpWorkManager", 2, -1);
      this.snmpV3AgentToolkit.initializeSNMPAgentToolkit(this.mibBasePath, this.mibModules);
      this.snmpV3AgentToolkit.setMaxPortRetryCount(this.maxPortRetryCount);
      this.snmpV3AgentToolkit.startSNMPAgent(this.engineId, this.tcpListenAddress, this.tcpListenPort, this.udpListenAddress, this.udpListenPort, this.engineBoots);
      this.snmpV3AgentToolkit.createSNMPMibTables(this.mibModules);
      this.snmpV3AgentToolkit.setSNMPCommunity(this.community, this.rootOidNode);
      this.snmpV3AgentToolkit.setCommunityBasedAccessEnabled(this.communityBasedAccessEnabled);
      this.snmpV3AgentToolkit.setSecurityParams(this.securityLevel, this.authProtocol, this.privProtocol, this.localizedKeyCacheInvalidationInterval);
      this.initializeTrapDestinations();
      this.snmpEngineBoots = this.getCurrentEngineBootsValue();
      this.updateSNMPEngineBoots();
      if (this.automaticTrapsEnabled) {
         this.sendColdStartTrap();
      }

      this.snmpAgentInitialized = true;
   }

   public int getEngineBoots() {
      return this.engineBoots;
   }

   public void setEngineBoots(int engineBoots) {
      this.engineBoots = engineBoots;
   }

   public String getEngineId() {
      return this.engineId;
   }

   public void setEngineId(String engineId) {
      this.engineId = engineId;
   }

   public boolean isCommunityBasedAccessEnabled() {
      return this.communityBasedAccessEnabled;
   }

   public void setCommunityBasedAccessEnabled(boolean communityBasedAccessEnabled) {
      this.communityBasedAccessEnabled = communityBasedAccessEnabled;
   }

   public int getAuthProtocol() {
      return this.authProtocol;
   }

   public void setAuthProtocol(int authProtocol) {
      this.authProtocol = authProtocol;
   }

   public int getPrivProtocol() {
      return this.privProtocol;
   }

   public void setPrivProtocol(int privProtocol) {
      this.privProtocol = privProtocol;
   }

   public int getSecurityLevel() {
      return this.securityLevel;
   }

   public void setSecurityLevel(int securityLevel) {
      this.securityLevel = securityLevel;
   }

   private void updateSNMPEngineBoots() {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Updating engine boots");
      }

      ++this.snmpEngineBoots;
      String dataFileName = this.getSNMPDataFileName();
      File f = new File(dataFileName);
      File parent = f.getParentFile();
      if (parent != null) {
         parent.mkdirs();
      }

      SNMPData data = new SNMPData();
      data.setEngineBoots(this.snmpEngineBoots);
      FileOutputStream fos = null;

      try {
         fos = new FileOutputStream(f);
         ObjectOutputStream oos = new ObjectOutputStream(fos);
         oos.writeObject(data);
         fos.flush();
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Updated engine boots to " + this.snmpEngineBoots);
         }
      } catch (Throwable var15) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Error updating engine boots ", var15);
         }
      } finally {
         if (fos != null) {
            try {
               fos.close();
            } catch (IOException var14) {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("Error closing file ", var14);
               }
            }
         }

      }

   }

   private long getCurrentEngineBootsValue() {
      long engineBoots = 0L;
      String snmpDataFileName = this.getSNMPDataFileName();
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("SNMP data file = " + snmpDataFileName);
      }

      File snmpDataFile = new File(snmpDataFileName);
      FileInputStream fis = null;
      if (snmpDataFile.exists()) {
         long var7;
         try {
            fis = new FileInputStream(snmpDataFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            SNMPData data = (SNMPData)ois.readObject();
            engineBoots = data.getEngineBoots();
            return engineBoots;
         } catch (Throwable var18) {
            var7 = 0L;
         } finally {
            try {
               if (fis != null) {
                  fis.close();
               }
            } catch (IOException var17) {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("Error closing data file", var17);
               }
            }

         }

         return var7;
      } else {
         return engineBoots;
      }
   }

   public String getSNMPDataFileName() {
      return this.snmpDataFileName;
   }

   public void setSNMPDataFileName(String snmpDataFileName) {
      this.snmpDataFileName = snmpDataFileName;
   }

   public long getLocalizedKeyCacheInvalidationInterval() {
      return this.localizedKeyCacheInvalidationInterval;
   }

   public void setLocalizedKeyCacheInvalidationInterval(long period) {
      this.localizedKeyCacheInvalidationInterval = period;
   }

   public WorkManager getSnmpWorkManagerInstance() {
      return this.snmpWorkManager;
   }

   public String getTcpListenAddress() {
      return this.tcpListenAddress;
   }

   public void setTcpListenAddress(String tcpListenAddress) {
      this.tcpListenAddress = tcpListenAddress;
   }

   public int getTcpListenPort() {
      return this.tcpListenPort;
   }

   public void setTcpListenPort(int tcpListenPort) {
      this.tcpListenPort = tcpListenPort;
   }

   private static class SNMPData implements Serializable {
      private static final long serialVersionUID = 4541537220945911770L;
      private long engineBoots;

      private SNMPData() {
         this.engineBoots = 0L;
      }

      public long getEngineBoots() {
         return this.engineBoots;
      }

      public void setEngineBoots(long snmpEngineBoots) {
         this.engineBoots = snmpEngineBoots;
      }

      // $FF: synthetic method
      SNMPData(Object x0) {
         this();
      }
   }
}
