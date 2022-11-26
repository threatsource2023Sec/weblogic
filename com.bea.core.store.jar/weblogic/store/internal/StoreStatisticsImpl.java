package weblogic.store.internal;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import weblogic.store.StoreLogger;
import weblogic.store.StoreStatistics;
import weblogic.store.common.StoreDebug;
import weblogic.store.io.file.ReplicatedStoreIO;

public class StoreStatisticsImpl extends OperationStatisticsImpl implements StoreStatistics {
   private long physicalWriteCount;
   private long physicalReadCount;
   private long ioBufferBytes;
   private long mappedBytes;
   private volatile int blockSize;
   private volatile int deviceUsedPercent;
   private volatile int localUsedPercent;
   private volatile int maximumWriteSize = Integer.MAX_VALUE;
   private volatile int largestFreeChunkBlocks;
   private volatile int oneRegionPercent;
   private volatile int maxBatchSize = 1;
   private volatile int deleteRecordOnlyPercent;
   private int lastLoggedDeviceUsedPercent = 0;
   private int lastLoggedLocalUsedPercent = 0;
   private int spaceUsageLogThreshold = 70;
   private int spaceUsageWarningThreshold = 80;
   private int spaceUsageErrorThreshold = 90;
   private int spaceUsageLogDelta = 10;
   private int spaceUsageBatchFactor = 1;
   private static int memoryUsageTestPattern;
   private int count = 0;
   private Random generator = new Random();
   private int factor;
   private int pin;
   private int op;
   private static final boolean DEBUG_SPACE_UPDATES = Boolean.getBoolean("weblogic.store.DebugSpaceUpdates");

   public StoreStatisticsImpl(String name) {
      super(name);
      this.factor = this.generator.nextInt();
      if (this.factor < 0) {
         this.factor *= -1;
      }

      this.pin = this.generator.nextInt();
      if (this.pin < 0) {
         this.pin *= -1;
      }

   }

   public void setBlockSize(int size) {
      this.blockSize = size;
   }

   public void recordBatchSize(int size) {
      if (size > this.maxBatchSize) {
         this.maxBatchSize = size;
      }

   }

   public void init(String name, HashMap config) {
      int logThreshold = Math.abs(ReplicatedStoreIO.getIntConfiguration(config, "SpaceLoggingStartPercent", name, ".SpaceLoggingStartPercent", 70));
      if (logThreshold > 100) {
         logThreshold = 100;
      }

      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StoreDebug.storeIOPhysical.debug("StoreStatisticsImpl: " + name + ".SpaceLoggingStartPercent:" + logThreshold);
      }

      this.spaceUsageLogThreshold = logThreshold;
      int warningThreshold = Math.abs(ReplicatedStoreIO.getIntConfiguration(config, "SpaceOverloadYellowPercent", name, ".SpaceOverloadYellowPercent", 80));
      if (warningThreshold > 100) {
         warningThreshold = 100;
      }

      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StoreDebug.storeIOPhysical.debug("StoreStatisticsImpl: " + name + ".SpaceOverloadYellowPercent:" + warningThreshold);
      }

      this.spaceUsageWarningThreshold = warningThreshold;
      int errorThreshold = Math.abs(ReplicatedStoreIO.getIntConfiguration(config, "SpaceOverloadRedPercent", name, ".SpaceOverloadRedPercent", 90));
      if (errorThreshold > 100) {
         errorThreshold = 100;
      }

      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StoreDebug.storeIOPhysical.debug("StoreStatisticsImpl: " + name + ".SpaceOverloadRedPercent:" + errorThreshold);
      }

      this.spaceUsageErrorThreshold = errorThreshold;
      int muDelta = Math.abs(ReplicatedStoreIO.getIntConfiguration(config, "SpaceLoggingDeltaPercent", name, ".SpaceLoggingDeltaPercent", 10));
      if (muDelta == 0) {
         muDelta = 1;
      }

      if (muDelta > 100) {
         muDelta = 100;
      }

      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StoreDebug.storeIOPhysical.debug("StoreStatisticsImpl: " + name + ".SpaceLoggingDeltaPercent:" + muDelta);
      }

      this.spaceUsageLogDelta = muDelta;
      this.validateSpaceSettings(name, logThreshold, warningThreshold, errorThreshold);
      int bf = ReplicatedStoreIO.getIntConfiguration(config, "SpaceOverloadBatchFactor", name, ".SpaceOverloadBatchFactor", 1);
      if (bf == 0) {
         bf = 1;
      }

      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StoreDebug.storeIOPhysical.debug("StoreStatisticsImpl: " + name + ".SpaceOverloadBatchFactor:" + bf);
      }

      this.spaceUsageBatchFactor = bf;
   }

   public void validateSpaceSettings(String name, int start, int yellow, int red) {
      if (start >= 0 && (start > yellow || start > red)) {
         this.logInvalidSpaceSettings(name);
      }

      if (yellow >= 0 && (yellow < start || yellow > red)) {
         this.logInvalidSpaceSettings(name);
      }

      if (red >= 0 && (red < start || red < yellow)) {
         this.logInvalidSpaceSettings(name);
      }

   }

   public void logInvalidSpaceSettings(String name) {
      System.out.println("ERROR: The space usage related -D parameter values of the Replicated Store " + name + " must be corrected such that the " + "SpaceLoggingStartPercent" + ", " + "SpaceOverloadYellowPercent" + " and " + "SpaceOverloadRedPercent" + " values are appropriately set. In particular, " + "SpaceLoggingStartPercent" + " needs to be lower than " + "SpaceOverloadYellowPercent" + ", which in turn needs to be lower than " + "SpaceOverloadRedPercent" + ".");
   }

   public long getPhysicalWriteCount() {
      return this.physicalWriteCount;
   }

   public void incrementPhysicalWriteCount() {
      ++this.physicalWriteCount;
   }

   public long getPhysicalReadCount() {
      return this.physicalReadCount;
   }

   public void incrementPhysicalReadCount() {
      ++this.physicalReadCount;
   }

   public long getMappedBufferBytes() {
      return this.mappedBytes;
   }

   public long getIOBufferBytes() {
      return this.ioBufferBytes;
   }

   public void addIOBufferBytes(int bytes) {
      this.ioBufferBytes += (long)bytes;
   }

   public void addMappedBytes(int bytes) {
      this.mappedBytes += (long)bytes;
   }

   public void setDeviceUsedPercent(int usage) {
      if (usage >= 0 && usage <= 100) {
         boolean needLog = false;
         this.deviceUsedPercent = usage;
         if (usage >= this.spaceUsageLogThreshold && Math.abs(usage - this.lastLoggedDeviceUsedPercent) >= this.spaceUsageLogDelta) {
            needLog = true;
            this.lastLoggedDeviceUsedPercent = usage;
         }

         if (needLog) {
            if (usage >= this.spaceUsageErrorThreshold) {
               StoreLogger.logReplicatedStoreDaemonMemoryUsageError(this.name, usage);
            } else if (usage >= this.spaceUsageWarningThreshold) {
               StoreLogger.logReplicatedStoreDaemonMemoryUsageWarning(this.name, usage);
            } else {
               StoreLogger.logReplicatedStoreDaemonMemoryUsageInfo(this.name, usage);
            }
         }

      } else {
         throw new AssertionError();
      }
   }

   public int getDeviceUsedPercent() {
      if (memoryUsageTestPattern > 0) {
         ++this.count;
         if (this.count > 10000) {
            this.count = 0;
         }

         if (this.count % 5 == 0) {
            this.change();
         }
      }

      return this.deviceUsedPercent;
   }

   public void setLocalUsedPercent(int usage) {
      if (usage >= 0 && usage <= 100) {
         boolean needLog = false;
         this.localUsedPercent = usage;
         if (usage >= this.spaceUsageLogThreshold && this.deviceUsedPercent >= this.spaceUsageWarningThreshold && Math.abs(usage - this.lastLoggedLocalUsedPercent) > this.spaceUsageLogDelta) {
            needLog = true;
            this.lastLoggedLocalUsedPercent = usage;
         }

         if (needLog) {
            if (usage >= this.spaceUsageWarningThreshold) {
               StoreLogger.logReplicatedStoreMemoryUsageWarning(this.name, usage);
            } else {
               StoreLogger.logReplicatedStoreMemoryUsageInfo(this.name, usage);
            }
         }

      } else {
         throw new AssertionError();
      }
   }

   public int getLocalUsedPercent() {
      return this.localUsedPercent;
   }

   public int getMaximumWriteSize() {
      return this.maximumWriteSize;
   }

   public void setMaximumWriteSize(int i) {
      if (i < 0) {
         throw new AssertionError();
      } else {
         this.maximumWriteSize = i;
      }
   }

   public long getLargestFreeChunkBlocks() {
      return (long)this.largestFreeChunkBlocks;
   }

   public void setLargestFreeChunkBlocks(int blocks) {
      if (blocks < 0) {
         throw new AssertionError();
      } else {
         this.largestFreeChunkBlocks = blocks;
      }
   }

   public boolean isPotentiallyOverloaded(long bytes) {
      if (!this.isDeviceNearWarningOverloaded()) {
         return false;
      } else {
         int maxBatchSize = this.maxBatchSize;
         int count = 1;
         if (this.getDeviceUsedPercent() >= this.spaceUsageWarningThreshold && this.getLocalUsedPercent() > this.spaceUsageWarningThreshold - 10) {
            count = (maxBatchSize + 1) / 2;
         }

         if (this.isDeviceNearErrorOverloaded() && (this.getLocalUsedPercent() > this.spaceUsageWarningThreshold - 20 || this.isMostlyDeleteRecord())) {
            count = Math.max(maxBatchSize, this.spaceUsageBatchFactor);
         }

         long blocks = (bytes * 14L / 10L + (long)this.blockSize) / (long)this.blockSize;
         if (DEBUG_SPACE_UPDATES) {
            System.out.println("IsPotentiallyOverloaded: oneRegionPercent = " + this.oneRegionPercent + " blockSize =" + this.blockSize + " blocks = " + blocks + " localUsedpercent = " + this.getLocalUsedPercent() + " deiveUsedPercent = " + this.getDeviceUsedPercent() + " isMostDeleteRecord= " + this.isMostlyDeleteRecord() + " largestFreeChunkBlocks = " + this.largestFreeChunkBlocks + " count = " + count);
         }

         return blocks * (long)count > (long)this.largestFreeChunkBlocks;
      }
   }

   public boolean isWarningOverloaded() {
      return this.getDeviceUsedPercent() >= this.spaceUsageWarningThreshold && this.getLocalUsedPercent() >= this.spaceUsageWarningThreshold;
   }

   public boolean isDeviceErrorOverloaded() {
      return this.getDeviceUsedPercent() >= this.spaceUsageErrorThreshold;
   }

   private boolean isDeviceNearErrorOverloaded() {
      return this.getDeviceUsedPercent() + 3 * this.oneRegionPercent >= this.spaceUsageErrorThreshold;
   }

   private boolean isDeviceNearWarningOverloaded() {
      return this.getDeviceUsedPercent() + 2 * this.oneRegionPercent > this.spaceUsageWarningThreshold;
   }

   private boolean isMostlyDeleteRecord() {
      return this.deleteRecordOnlyPercent > 90;
   }

   public void setDeleteRecordOnlyPercent(int percent) {
      this.deleteRecordOnlyPercent = percent;
   }

   public void setOneRegionPercent(int percent) {
      this.oneRegionPercent = percent;
   }

   public int getSpaceUsageErrorThreshold() {
      return this.spaceUsageErrorThreshold;
   }

   private synchronized void change() {
      int local = -1;
      int device = -1;
      this.pin = (this.pin + 1) % 3;
      this.factor = 2;
      this.op = this.generator.nextInt() % 2;
      if (this.pin == 0 || this.pin == 2) {
         if (this.op == 0 || memoryUsageTestPattern == 1) {
            device = this.deviceUsedPercent + 10 * this.factor;
         }

         if (this.op == 1 && memoryUsageTestPattern > 1) {
            device = this.deviceUsedPercent - 10 * this.factor;
            if (device < 0) {
               device = 100;
            }
         }

         if (device > 100) {
            device %= 100;
         }
      }

      if (device != -1) {
         this.setDeviceUsedPercent(device);
      }

      if (this.pin == 1 || this.pin == 2) {
         if (this.op == 0 || memoryUsageTestPattern == 1) {
            local = this.localUsedPercent + 10 * this.factor;
         }

         if (this.op == 1 && memoryUsageTestPattern > 1) {
            local = this.localUsedPercent - 10 * this.factor;
            if (local < 0) {
               local = 100;
            }
         }

         if (local > 100) {
            local %= 100;
         }
      }

      if (local != -1) {
         this.setLocalUsedPercent(local);
      }

      System.out.println("UsedPercent: " + (new Date(System.currentTimeMillis())).toString() + " pattern = " + memoryUsageTestPattern + " device: " + this.deviceUsedPercent + " local: " + this.localUsedPercent);
   }

   static {
      int pattern = 0;

      try {
         String tc = System.getProperty("weblogic.store.MemoryUsageTestPattern", "0");
         pattern = Integer.parseInt(tc);
      } catch (Throwable var3) {
      }

      memoryUsageTestPattern = pattern;
   }
}
