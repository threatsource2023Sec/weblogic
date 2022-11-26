package weblogic.cluster.replication;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.rmi.spi.HostID;

final class WrappedRO {
   public static final byte PRIMARY_STATUS = 0;
   public static final byte SECONDARY_STATUS = 1;
   static final int INITIAL_VERSION_NUMBER = 0;
   static final int RO_NOT_FOUND = -1;
   private final Map map = new HashMap(3);
   private final Map versionMap = new HashMap(3);
   private byte status;
   private HostID otherHost;
   private int version;
   private final ROInfo roInfo;
   private final ROID id;
   final int channelIndex;
   private ResourceGroupKey resourceGroupKey;
   private transient boolean isMigrated = false;

   WrappedRO(Replicatable ro, ROID id, byte status, int version, int channelIndex, ResourceGroupKey resourceGroupKey) {
      this.roInfo = new ROInfo(id);
      this.status = status;
      this.version = version;
      this.otherHost = null;
      this.id = id;
      this.resourceGroupKey = resourceGroupKey;
      if (status == 0) {
         ro.becomePrimary(id);
      } else {
         this.roInfo.setSecondaryROInfo(ro.becomeSecondary(id));
      }

      this.addMapEntry(ro, version);
      this.channelIndex = channelIndex;
   }

   private void addMapEntry(Replicatable ro, int version) {
      this.map.put(ro.getKey(), ro);
      this.versionMap.put(ro.getKey(), version);
   }

   synchronized void addRO(Replicatable ro) {
      this.addMapEntry(ro, 1);
      if (this.status == 0) {
         ro.becomePrimary(this.id);
      } else {
         this.roInfo.setSecondaryROInfo(ro.becomeSecondary(this.id));
      }

   }

   synchronized void addRO(Replicatable ro, int version) {
      this.addMapEntry(ro, version);
      if (this.status == 0) {
         ro.becomePrimary(this.id);
      } else {
         this.roInfo.setSecondaryROInfo(ro.becomeSecondary(this.id));
      }

   }

   synchronized void removeAll() {
      this.map.clear();
      this.versionMap.clear();
   }

   boolean removeRO(Object key) {
      synchronized(this) {
         this.map.remove(key);
         this.versionMap.remove(key);
      }

      return this.map.size() == 0;
   }

   Replicatable getRO(Object key) {
      return (Replicatable)this.map.get(key);
   }

   ROID getID() {
      return this.roInfo.getROID();
   }

   ROInfo getROInfo() {
      return this.roInfo;
   }

   HostID getOtherHost() {
      return this.otherHost;
   }

   byte getStatus() {
      return this.status;
   }

   int getVersion() {
      return this.version;
   }

   HostID setOtherHost(HostID otherHost) {
      return this.otherHost = otherHost;
   }

   int getVersion(Object key) {
      Integer version = (Integer)this.versionMap.get(key);
      return version != null ? version : -1;
   }

   Object getSecondaryROInfo() {
      return this.roInfo.getSecondaryROInfo();
   }

   ROInfo getROInfoForSecondary(Object key) {
      Integer v;
      synchronized(this.versionMap) {
         v = (Integer)this.versionMap.get(key);
      }

      if (v == null) {
         this.roInfo.setSecondaryROVersion(-1);
      } else {
         this.roInfo.setSecondaryROVersion(v);
      }

      return this.roInfo;
   }

   void setOtherHostInfo(Object otherHostInfo) {
      this.roInfo.setSecondaryROInfo(otherHostInfo);
   }

   final int incrementVersion(Object key) {
      ++this.version;
      int newVersion = this.getVersion(key) + 1;
      synchronized(this) {
         this.versionMap.put(key, newVersion);
         return newVersion;
      }
   }

   final void decrementVersion(Object key) {
      --this.version;
      synchronized(this) {
         this.versionMap.put(key, this.getVersion(key) - 1);
      }
   }

   final int getNumROS() {
      return this.map.size();
   }

   final synchronized void ensureStatus(byte status) {
      if (this.status != status) {
         this.status = status;
         if (status == 0) {
            this.roInfo.setSecondaryROInfo((Object)null);
            this.otherHost = null;
            this.changeStatus(true);
         } else {
            this.changeStatus(false);
         }
      }

   }

   private void changeStatus(boolean toPrimary) {
      Iterator iter = this.map.values().iterator();

      while(iter.hasNext()) {
         Replicatable ro = (Replicatable)iter.next();
         if (toPrimary) {
            ro.becomePrimary(this.roInfo.getROID());
         } else {
            this.roInfo.setSecondaryROInfo(ro.becomeSecondary(this.roInfo.getROID()));
         }
      }

   }

   synchronized Map getMap() {
      return (Map)((HashMap)this.map).clone();
   }

   synchronized Map getVersionMap() {
      return (Map)((HashMap)this.versionMap).clone();
   }

   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (object instanceof WrappedRO) {
         WrappedRO other = (WrappedRO)object;
         return other.id == this.id;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.id.hashCode();
   }

   public ResourceGroupKey getResourceGroupKey() {
      return this.resourceGroupKey;
   }

   boolean isMigrated() {
      return this.isMigrated;
   }

   void setIsMigrated(boolean migrated) {
      this.isMigrated = migrated;
   }
}
