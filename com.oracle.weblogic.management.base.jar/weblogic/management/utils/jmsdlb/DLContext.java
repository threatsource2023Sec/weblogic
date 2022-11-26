package weblogic.management.utils.jmsdlb;

import java.util.Date;

public class DLContext {
   private final String name;
   private final String clusterName;
   private final DLUtil util;
   private final DLUUID serverID;
   private final DLUUID groupID;
   private final DLUUID storeID;
   private boolean isActive;
   private long activateTime;
   private long INITIALIZE_TIME = 120L;
   private static final boolean SupportSuspect = Boolean.getBoolean("weblogic.management.utild.dlb.suspect.support");
   private static final boolean ApplyFirstDelay = Boolean.getBoolean("weblogic.management.utild.dlb.applyFirstDelay");
   private static final int MaxConcurrentPlacement = Integer.getInteger("weblogic.management.utild.dlb.maxPlacement", -1);
   private long createTime;

   public boolean getSupportSuspect() {
      return SupportSuspect;
   }

   public boolean isApplyFirstDelay() {
      return ApplyFirstDelay;
   }

   public int getMaxConcurrentPlacement() {
      return MaxConcurrentPlacement;
   }

   public DLId getGroupID(String name) {
      return this.groupID.getID(name);
   }

   public DLId getStoreConfigID(String name) {
      return this.storeID.getID(name);
   }

   public DLId getServerID(String name) {
      return this.serverID.getID(name);
   }

   public int getServerIDSize() {
      return this.serverID.getCurrentMaxSize();
   }

   public int getGroupIDSize() {
      return this.groupID.getCurrentMaxSize();
   }

   public String getClusterName() {
      return this.clusterName;
   }

   public String toString() {
      return "DLContext{name='" + this.name + '\'' + ", isActive=" + this.isActive + ", activateTime=" + new Date(this.activateTime) + ", createTime=" + new Date(this.createTime) + '}';
   }

   public DLContext(String name, String clusterName) {
      this.name = name;
      this.clusterName = clusterName;
      this.serverID = new DLUUID(name, "DLServerID");
      this.groupID = new DLUUID(name, "DLGroupID");
      this.storeID = new DLUUID(name, "DLStoreID");
      this.setCreateTime(System.currentTimeMillis());
      this.util = new DLUtil();
   }

   public DLUtil getDMUtil() {
      return this.getUtil();
   }

   public String getName() {
      return this.name;
   }

   public DLUtil getUtil() {
      return this.util;
   }

   public boolean isActive() {
      return this.isActive;
   }

   public void setActive(boolean isActive) {
      this.isActive = isActive;
      if (isActive) {
         this.setActivateTime(System.currentTimeMillis());
      } else {
         this.setActivateTime(0L);
      }

   }

   public long getActivateTime() {
      return this.activateTime;
   }

   private void setActivateTime(long activateTime) {
      this.activateTime = activateTime;
   }

   public long getCreateTime() {
      return this.createTime;
   }

   public void setCreateTime(long createTime) {
      this.createTime = createTime;
   }

   public boolean isTakeover() {
      if (this.activateTime == 0L) {
         return false;
      } else {
         return this.activateTime - this.createTime > this.INITIALIZE_TIME * 1000L;
      }
   }

   private static class DLUUID {
      final DLId.DLIdFactory factory;

      public DLUUID(String dlbName, String idName) {
         this.factory = new DLId.DLIdFactory(dlbName + ":" + idName);
      }

      public DLId getID(String name) {
         return this.factory.getNewID(name);
      }

      public DLId getCurrentMaxId() {
         return this.factory.getCurrentMaxId();
      }

      public int getCurrentMaxSize() {
         return this.getCurrentMaxId().getIndex() + 1;
      }
   }
}
