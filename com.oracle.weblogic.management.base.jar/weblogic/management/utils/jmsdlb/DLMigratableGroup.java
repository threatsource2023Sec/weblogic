package weblogic.management.utils.jmsdlb;

import java.util.HashMap;
import java.util.Map;

public class DLMigratableGroup {
   private final String name;
   private DLStoreConfig config;
   private DLServerStatus currentServer;
   private DLServerStatus lastServer;
   private DLId id;
   private int restartCnt;
   private long lastRestartTime;
   private String targetServer;
   private STATUS status;
   private DLLoadValue load;
   private Map assignedServers;

   public String getStateInfo() {
      return this.name + " - " + this.status + " - " + this.currentServer + "-" + this.config;
   }

   public void setPending() {
      this.status = DLMigratableGroup.STATUS.PENDING;
   }

   public boolean isPending() {
      return this.status == DLMigratableGroup.STATUS.PENDING;
   }

   public DLMigratableGroup(DLContext context, String name, DLStoreConfig config, String server) {
      this.name = name;
      this.config = config;
      this.status = DLMigratableGroup.STATUS.DOWN;
      this.id = context.getGroupID(name);
      this.assignedServers = new HashMap();
      if (config.getDistribution() == DLStoreConfig.DISTRIBUTION.DISTRIBUTED) {
         this.targetServer = server;
      }

   }

   DLId getGroupID() {
      return this.id;
   }

   public int lastVersionOfServer(String serverName) {
      Integer i = (Integer)this.assignedServers.get(serverName);
      return i == null ? -1 : i;
   }

   public String getName() {
      return this.name;
   }

   public DLStoreConfig getConfig() {
      return this.config;
   }

   public DLServerStatus getCurrentServer() {
      return this.currentServer;
   }

   public DLServerStatus getLastServer() {
      return this.lastServer;
   }

   public void setCurrentServer(DLServerStatus currentServer) {
      this.currentServer = currentServer;
      if (currentServer != null) {
         this.assignedServers.put(currentServer.getName(), currentServer.getVersion());
         this.lastServer = currentServer;
      }

   }

   public void resetRestart() {
      this.restartCnt = 0;
      this.lastRestartTime = 0L;
   }

   public void incrementRestart() {
      ++this.restartCnt;
      this.lastRestartTime = System.currentTimeMillis();
   }

   public int getRestartCnt() {
      return this.restartCnt;
   }

   public long getLastRestartTime() {
      return this.lastRestartTime;
   }

   public void setStatus(STATUS status) {
      this.status = status;
   }

   public String toString() {
      return "DMMigrationInstance[name=" + this.name + ",status=" + this.status + ",server=" + this.currentServer + ",type=" + (this.config == null ? null : this.config.getDistribution()) + ", targetServer=" + this.targetServer + "]";
   }

   public STATUS getStatus() {
      return this.status;
   }

   public boolean isRunning() {
      return this.status == DLMigratableGroup.STATUS.RUNNING;
   }

   public boolean isAdminDown() {
      return this.status == DLMigratableGroup.STATUS.ADMIN_DOWN;
   }

   public String getTargetServer() {
      return this.targetServer;
   }

   public DLLoadValue getLoad() {
      return this.load;
   }

   public void setLoad(DLLoadValue load) {
      this.load = load;
   }

   public static enum STATUS {
      RUNNING,
      PENDING,
      DOWN,
      ADMIN_DOWN,
      FAILED_CRITICAL,
      FAILED;
   }
}
