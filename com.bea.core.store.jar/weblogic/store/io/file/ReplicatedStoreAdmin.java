package weblogic.store.io.file;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import weblogic.store.io.file.direct.ReplicatedIONativeImpl;

public class ReplicatedStoreAdmin {
   private HashMap daemons;
   private HashMap regions;
   private static ReplicatedStoreAdmin singleton = new ReplicatedStoreAdmin();
   private SimpleDateFormat dtfmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
   private DaemonInfo recoveryDaemonInfo;
   String libraryVersion = "not populated from library yet";

   private ReplicatedStoreAdmin() {
      ReplicatedIONativeImpl.loadReplicatedLib();
   }

   static ReplicatedStoreAdmin getReplicatedStoreAdminSingleton() throws IOException {
      ReplicatedIONativeImpl.getDirectIOManagerSingletonIOException();
      return singleton;
   }

   public static ReplicatedStoreAdmin getRecoveryInstance() throws IOException {
      ReplicatedIONativeImpl.getDirectIOManagerSingletonIOException();
      return new ReplicatedStoreAdmin();
   }

   public native int attach(String var1, int var2) throws IOException;

   public native void detach() throws IOException;

   public native boolean isAttached() throws IOException;

   public native int shutdownDaemon(int var1, boolean var2, boolean var3) throws IOException;

   public native int listRegion(String var1) throws IOException;

   private native int listLocalRegions() throws IOException;

   private native int listGlobalRegions() throws IOException;

   private native int listRecoveryRegionsAndDaemon(int var1, String var2, String var3) throws IOException;

   public native int deleteRegion(String var1, boolean var2) throws IOException;

   private native int listDaemons() throws IOException;

   public native int attachToDaemon(int var1) throws IOException;

   public native int getAttachedDaemonIndex() throws IOException;

   public void addRegionInfo(String name, int size, int used, int bs, long ctime, long mtime, long atime, boolean open, boolean local, int primary, int pid, String ip, int minredundancy, int redundancy, long nodes) {
      RegionInfo localCopy = new RegionInfo();
      localCopy.setName(name);
      localCopy.setSize(size);
      localCopy.setUsed(used);
      localCopy.setBlockSize(bs);
      localCopy.setCreationTime(ctime);
      localCopy.setModificationTime(mtime);
      localCopy.setAccessTime(atime);
      localCopy.setOpen(open);
      localCopy.setLocal(local);
      localCopy.setPrimary(primary);
      localCopy.setPid(pid);
      localCopy.setIp(ip);
      localCopy.setMinRedundancy(minredundancy);
      localCopy.setMaxRedundancy(redundancy);
      localCopy.setNodes(nodes);
      this.regions.put(localCopy.getName(), localCopy);
   }

   public void addDaemonInfo(int index, String status, int reachable, String ip, short port, int shmKey, long startTime, long currentTime, long totalMemory, long usedMemory, int numberOfStores, int numberOfOpens, int numberOfLocalOpens, int numberOfResilvers, int totalNumberOfDaemons) {
      DaemonInfo localCopy;
      if (this.recoveryDaemonInfo == null) {
         localCopy = new DaemonInfo();
      } else {
         localCopy = this.recoveryDaemonInfo;
      }

      localCopy.setIndex(index);
      localCopy.setStatus(status == null ? "Unknown" : status);
      localCopy.setReachable(reachable == 0 ? "FALSE" : "TRUE");
      localCopy.setIp(ip == null ? "0.0.0.0" : ip);
      localCopy.setPort(port);
      localCopy.setShmKey(shmKey);
      localCopy.setStartTime(startTime);
      localCopy.setCurrentTime(currentTime);
      localCopy.setTotalMemory(totalMemory);
      localCopy.setUsedMemory(usedMemory);
      localCopy.setNumberOfStores(numberOfStores);
      localCopy.setNumberOfOpens(numberOfOpens);
      localCopy.setNumberOfLocalOpens(numberOfLocalOpens);
      localCopy.setNumberOfResilvers(numberOfResilvers);
      localCopy.setNumberOfDaemons(totalNumberOfDaemons);
      if (this.recoveryDaemonInfo == null) {
         this.daemons.put(localCopy.getId(), localCopy);
      }

   }

   HashMap populateGlobalRegions() throws IOException {
      HashMap local = new HashMap();
      synchronized(this) {
         this.regions = local;

         HashMap var3;
         try {
            this.listGlobalRegions();
            var3 = local;
         } finally {
            this.regions = null;
         }

         return var3;
      }
   }

   HashMap populateLocalRegions() throws IOException {
      HashMap local = new HashMap();
      synchronized(this) {
         this.regions = local;

         HashMap var3;
         try {
            this.listLocalRegions();
            var3 = local;
         } finally {
            this.regions = null;
         }

         return var3;
      }
   }

   HashMap populateRegion(String name) throws IOException {
      HashMap local = new HashMap();
      synchronized(this) {
         this.regions = local;

         HashMap var4;
         try {
            this.listRegion(name);
            var4 = local;
         } finally {
            this.regions = null;
         }

         return var4;
      }
   }

   HashMap populateDaemons() throws IOException {
      HashMap local = new HashMap();
      synchronized(this) {
         this.daemons = local;

         HashMap var3;
         try {
            this.listDaemons();
            var3 = local;
         } finally {
            this.daemons = null;
         }

         return var3;
      }
   }

   public DaemonInfo populateRecoveryInfo(HashMap localRegions, int localindex, String cfgFileName, String prefix) throws IOException {
      DaemonInfo localDaemonInfo = new DaemonInfo();
      synchronized(this) {
         this.regions = localRegions;
         this.recoveryDaemonInfo = localDaemonInfo;

         DaemonInfo var7;
         try {
            this.listRecoveryRegionsAndDaemon(localindex, cfgFileName, prefix);
            var7 = this.recoveryDaemonInfo;
         } finally {
            this.regions = null;
            this.recoveryDaemonInfo = null;
         }

         return var7;
      }
   }

   public String getLibraryVersion() {
      return this.libraryVersion;
   }

   public class DaemonInfo {
      String did;
      int index;
      String status;
      String reachable;
      String ip;
      short port;
      int shmKey;
      long startTime;
      long currentTime;
      long totalMemory;
      long usedMemory;
      int numberOfStores;
      int numberOfOpens;
      int numberOfLocalOpens;
      int numberOfResilvers;
      int numberOfDaemons;

      public int getIndex() {
         return this.index;
      }

      public void setIndex(int index) {
         this.index = index;
         this.did = String.format("%03d", index);
      }

      public String getId() {
         return this.did;
      }

      public String getStatus() {
         return this.status;
      }

      public void setStatus(String status) {
         this.status = status;
      }

      public String getReachable() {
         return this.reachable;
      }

      public void setReachable(String reachable) {
         this.reachable = reachable;
      }

      public String getIp() {
         return this.ip;
      }

      public void setIp(String ip) {
         this.ip = ip;
      }

      public short getPort() {
         return this.port;
      }

      public void setPort(short port) {
         this.port = port;
      }

      public int getShmKey() {
         return this.shmKey;
      }

      public void setShmKey(int shmKey) {
         this.shmKey = shmKey;
      }

      public long getStartTime() {
         return this.startTime;
      }

      public void setStartTime(long startTime) {
         this.startTime = startTime;
      }

      public long getCurrentTime() {
         return this.currentTime;
      }

      public void setCurrentTime(long currentTime) {
         this.currentTime = currentTime;
      }

      public long getTotalMemory() {
         return this.totalMemory;
      }

      public void setTotalMemory(long totalMemory) {
         this.totalMemory = totalMemory;
      }

      public long getUsedMemory() {
         return this.usedMemory;
      }

      public void setUsedMemory(long usedMemory) {
         this.usedMemory = usedMemory;
      }

      public int getNumberOfStores() {
         return this.numberOfStores;
      }

      public void setNumberOfStores(int numberOfStores) {
         this.numberOfStores = numberOfStores;
      }

      public int getNumberOfOpens() {
         return this.numberOfOpens;
      }

      public void setNumberOfOpens(int numberOfOpens) {
         this.numberOfOpens = numberOfOpens;
      }

      public int getNumberOfLocalOpens() {
         return this.numberOfLocalOpens;
      }

      public void setNumberOfLocalOpens(int numberOfLocalOpens) {
         this.numberOfLocalOpens = numberOfLocalOpens;
      }

      public int getNumberOfResilvers() {
         return this.numberOfResilvers;
      }

      public void setNumberOfResilvers(int numberOfResilvers) {
         this.numberOfResilvers = numberOfResilvers;
      }

      public int getNumberOfDaemons() {
         return this.numberOfDaemons;
      }

      public void setNumberOfDaemons(int numberOfDaemons) {
         this.numberOfDaemons = numberOfDaemons;
      }

      public void printAsString() {
         System.out.println("DEBUG: DaemonInfo: \nIndex                              => " + this.index + "\nStatus                             => " + this.status + "\nReachable                          => " + this.reachable + "\nIP                                 => " + this.ip + "\nPort                               => " + this.port + "\nShared Memory Key                  => " + this.shmKey + "\nStart Time                         => " + this.startTime + "\nCurrent Time                       => " + this.currentTime + "\nTotal Memory                       => " + this.totalMemory + "\nUsed Memory                        => " + this.usedMemory + "\nNumber Of Stores Managed           => " + this.numberOfStores + "\nNumber Of Stores Currently Opened  => " + this.numberOfOpens + "\nNumber Of Stores Opened by Local Client => " + this.numberOfLocalOpens + "\nNumber Of Resilvers in Progress    => " + this.numberOfResilvers + "\nNumber Of Daemons in the Cluster   => " + this.numberOfDaemons + "\n");
      }
   }

   public class RegionInfo {
      private String name;
      private int size;
      private int used;
      private int blockSize;
      private long ctime;
      private long mtime;
      private long atime;
      private int primary;
      private int pid;
      private String ip;
      private boolean open;
      private boolean local;
      private int minredundancy;
      private int redundancy;
      private long nodes;

      public String getName() {
         return this.name;
      }

      public void setName(String name) {
         this.name = name;
      }

      public int getSize() {
         return this.size;
      }

      public void setSize(int size) {
         this.size = size;
      }

      public int getUsed() {
         return this.used;
      }

      public void setUsed(int used) {
         this.used = used;
      }

      public int getBlockSize() {
         return this.blockSize;
      }

      public void setBlockSize(int blockSize) {
         this.blockSize = blockSize;
      }

      public long getCreationTime() {
         return this.ctime;
      }

      public void setCreationTime(long ctime) {
         this.ctime = ctime;
      }

      public long getModificationTime() {
         return this.mtime;
      }

      public void setModificationTime(long mtime) {
         this.mtime = mtime;
      }

      public long getAccessTime() {
         return this.atime;
      }

      public void setAccessTime(long atime) {
         this.atime = atime;
      }

      public boolean isOpen() {
         return this.open;
      }

      public void setOpen(boolean open) {
         this.open = open;
      }

      public boolean isLocal() {
         return this.local;
      }

      public void setLocal(boolean local) {
         this.local = local;
      }

      public int getPrimary() {
         return this.primary;
      }

      public void setPrimary(int primary) {
         this.primary = primary;
      }

      public int getPid() {
         return this.pid;
      }

      public void setPid(int pid) {
         this.pid = pid;
      }

      public String getIp() {
         return this.ip;
      }

      public void setIp(String ip) {
         this.ip = ip;
      }

      public int getMinRedundancy() {
         return this.minredundancy;
      }

      public void setMinRedundancy(int minredundancy) {
         this.minredundancy = minredundancy;
      }

      public int getMaxRedundancy() {
         return this.redundancy;
      }

      public void setMaxRedundancy(int redundancy) {
         this.redundancy = redundancy;
      }

      public long getNodes() {
         return this.nodes;
      }

      public void setNodes(long nodes) {
         this.nodes = nodes;
      }

      public void printAsString() {
         System.out.println("DEBUG: RegionInfo: \nName              => " + this.name + "\nSize              => " + this.size + "\nUsed              => " + this.used + "\nBlock Size        => " + this.blockSize + "\nCreation Time     => " + ReplicatedStoreAdmin.this.dtfmt.format(new Date(this.ctime / 1000L)) + "\nModification Time => " + ReplicatedStoreAdmin.this.dtfmt.format(new Date(this.mtime / 1000L)) + "\nLast Access Time  => " + ReplicatedStoreAdmin.this.dtfmt.format(new Date(this.atime / 1000L)) + "\nPrimary Daemon    => " + this.primary + "\nCurrently Open    => " + this.open + "\nOpened Local      => " + this.local + "\nProcess Id        => " + this.pid + "\nIP Address        => " + this.ip + "\nMin Redundancy    => " + this.minredundancy + "\nMax Redundancy    => " + this.redundancy + "\nNodes             => " + this.nodes);
      }
   }
}
