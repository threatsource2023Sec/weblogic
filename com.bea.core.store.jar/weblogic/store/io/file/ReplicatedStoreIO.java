package weblogic.store.io.file;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.store.PersistentStore;
import weblogic.store.PersistentStoreException;
import weblogic.store.StoreLogger;
import weblogic.store.StoreWritePolicy;
import weblogic.store.common.StoreDebug;
import weblogic.store.io.IOListener;
import weblogic.store.io.file.direct.ReplicatedIONativeImpl;

public class ReplicatedStoreIO extends BaseStoreIO {
   private int localIndex;

   public ReplicatedStoreIO(String filePrefix, String dirName) throws PersistentStoreException {
      this(filePrefix, dirName, true);
   }

   public ReplicatedStoreIO(String filePrefix, String dirName, boolean autoCreateDir) throws PersistentStoreException {
      super(ReplicatedIONativeImpl.getDirectIOManagerSingletonPersistentStoreException(), filePrefix, dirName, autoCreateDir, true);
   }

   public static int getIntConfiguration(HashMap config, String configName, String myStoreName, String localPropName, int defaultValue) {
      if (config != null) {
         Object cp = config.get(configName);
         if (cp != null && cp instanceof Integer) {
            defaultValue = (Integer)cp;
         }
      }

      return Integer.getInteger("weblogic.store.replicated." + myStoreName + localPropName, Integer.getInteger("weblogic.store.replicated" + localPropName, defaultValue));
   }

   HashMap adjustConfig(HashMap config) {
      if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
         StoreDebug.storeIOPhysicalVerbose.debug("adjustConfig begin");
      }

      this.writePolicy = StoreWritePolicy.DIRECT_WRITE;
      Object tmp = config.get("SynchronousWritePolicy");
      if (this.writePolicy != tmp) {
         config.put("SynchronousWritePolicy", this.writePolicy);
      }

      int blockSize = 8192;
      Integer configuredBlockSize = (Integer)config.get("BlockSize");
      if (configuredBlockSize < 512) {
         if (configuredBlockSize != -1 && StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StoreDebug.storeIOPhysical.debug("ReplicatedStoreIO: " + this.heap.getName() + ", Invalid block-size value configured:" + configuredBlockSize + ", overridding with: " + blockSize);
         }

         config.put("BlockSize", Integer.valueOf(blockSize));
      }

      Boolean lockingConfig = (Boolean)config.get("FileLockingEnabled");
      if (lockingConfig != null && !lockingConfig) {
         config.put("FileLockingEnabled", Boolean.TRUE);
      }

      this.heap.setSynchronousWritePolicy(this.writePolicy);
      this.localIndex = getIntConfiguration(config, "LocalIndex", this.heap.getName(), ".LocalIndex", 0);
      if (this.localIndex < 0) {
         StoreLogger.logInvalidIntegerProperty("LocalIndex", String.valueOf(this.localIndex), 0);
         this.localIndex = 0;
      }

      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StoreDebug.storeIOPhysical.debug("ReplicatedStoreIO: " + this.heap.getName() + ".LocalIndex:" + this.localIndex);
      }

      config.put("LocalIndex", this.localIndex);
      int maximumMessageSizePercent = Math.abs(getIntConfiguration(config, "MaximumMessageSizePercent", this.heap.getName(), ".MaximumMessageSizePercent", 1));
      if (maximumMessageSizePercent > 100) {
         maximumMessageSizePercent = 100;
      }

      if (maximumMessageSizePercent != 1) {
         System.out.println(" *** -D.MaximumMessageSizePercentis set to " + maximumMessageSizePercent);
      }

      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StoreDebug.storeIOPhysical.debug("ReplicatedStoreIO: " + this.heap.getName() + ".MaximumMessageSizePercent:" + maximumMessageSizePercent);
      }

      config.put("MaximumMessageSizePercent", maximumMessageSizePercent);
      if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
         StoreDebug.storeIOPhysicalVerbose.debug("adjustConfig end. config map=" + config.toString());
      }

      return config;
   }

   public int open(HashMap config) throws PersistentStoreException {
      config = this.adjustConfig(config);
      return this.openInternal(config);
   }

   static String convertToString(File file, Map config, String key) throws IOException {
      Object result = config.get(key);

      try {
         String s = (String)result;
         s.charAt(1);
         return s;
      } catch (Throwable var5) {
         throw new IOException("expected Replicated Store configuration " + key + " as positive number, have " + result + " for PersistentStore " + file.getCanonicalPath(), var5);
      }
   }

   static long convertToLong(File file, Map config, String key) throws IOException {
      Object result = config.get(key);

      try {
         return ((Number)result).longValue();
      } catch (Throwable var5) {
         throw new IOException("expected Replicated Store configuration " + key + " as positive number, have " + result + " for PersistentStore " + file.getCanonicalPath(), var5);
      }
   }

   static String validatePositiveConfig(File file, Map config, String key) throws IOException {
      long val = convertToLong(file, config, key);
      if (val < 1L) {
         throw new IOException("expected value greater than 0 for " + key + " for PersistentStore " + file.getCanonicalPath());
      } else {
         return Long.toString(val);
      }
   }

   static String validateRealConfig(File file, Map config, String key) throws IOException {
      long val = convertToLong(file, config, key);
      if (val < 0L) {
         throw new IOException("expected value at least 0 for " + key + " for PersistentStore " + file.getCanonicalPath());
      } else {
         return Long.toString(val);
      }
   }

   FileChannel fileChannelFactory(Map config, File file, String mode, boolean exclusive) throws IOException {
      if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
         StoreDebug.storeIOPhysicalVerbose.debug("fileChannelFactory begin");
      }

      if (!exclusive) {
         throw new IOException("must always be exclusive access");
      } else if (!"rwd".equalsIgnoreCase(mode)) {
         throw new IOException("mode must be 'rwd' but is " + mode);
      } else {
         Object requestedPolicy = config.get("SynchronousWritePolicy");
         StoreWritePolicy targetPolicy = StoreWritePolicy.DIRECT_WRITE;
         if (requestedPolicy != null && requestedPolicy.equals(targetPolicy)) {
            HashMap localConfig = new HashMap();
            HashMap remaining = new HashMap(config);
            int handle = ReplicatedIONativeImpl.allocateNativeHandle(this.heap.getRegionName());
            remaining.put("CandidateHandle", (new Integer(handle)).toString());
            remaining.put("LocalIndex", (new Integer(this.localIndex)).toString());
            remaining.remove("SynchronousWritePolicy");
            remaining.remove("DomainName");
            Iterator var10 = PersistentStore.VALID_REPLICATED_IO_KEYS.iterator();

            while(var10.hasNext()) {
               String k = (String)var10.next();
               Object v = remaining.remove(k);
               if (null == v) {
                  String d = "ReplicatedStoreIO: " + this.heap.getName() + ". has unpopulated config " + k;
                  if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
                     StoreDebug.storeIOPhysical.debug(d);
                  }
               } else {
                  localConfig.put(k, v.toString());
               }
            }

            if (!remaining.isEmpty()) {
               String d = "skip configurable " + remaining.toString();
               if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
                  StoreDebug.storeIOPhysical.debug(d);
               }
            }

            int size = localConfig.size();
            String[] keys = new String[size];
            String[] values = new String[size];
            int valueIndex = 0;
            Iterator var14 = PersistentStore.VALID_REPLICATED_IO_KEYS.iterator();

            while(var14.hasNext()) {
               String k = (String)var14.next();
               String v = (String)localConfig.get(k);
               if (null != v) {
                  values[valueIndex] = v;
                  keys[valueIndex++] = k;
               }
            }

            boolean problem = true;

            FileChannel var27;
            try {
               FileChannel fc = this.directIOManager.openEnhanced(file, mode, exclusive, keys, values);
               problem = false;
               var27 = fc;
            } finally {
               if (problem) {
                  ReplicatedIONativeImpl.freeNativeHandle((long)handle);
               }

            }

            return var27;
         } else {
            throw new IOException("policy must be " + targetPolicy + " but is " + requestedPolicy);
         }
      }
   }

   public void flush(IOListener listener) throws PersistentStoreException {
      throw new UnsupportedOperationException("the asynchrounous flush should not be called on ReplicatedStoreIO");
   }

   public void poll() {
      try {
         this.heap.pollDevice();
      } catch (IOException var2) {
         if (StoreHeap.DEBUG_SPACE_UPDATES) {
            System.out.println("Failed to poll a daemon from a replicated store for status");
         }
      }

   }

   File[] listRegionsOrFiles(Heap heap, File configuredDirectory, FilenameFilter filenameFilter) throws IOException {
      String daemonConfigFile = heap.getStringConfig("ConfigFileName");
      String regionprefix = heap.getRegionName();
      if (regionprefix != null && daemonConfigFile != null) {
         ReplicatedStoreAdmin rsa = ReplicatedStoreAdmin.getRecoveryInstance();
         HashMap regionInfoMap = new HashMap();
         ReplicatedStoreAdmin.DaemonInfo localDaemon = rsa.populateRecoveryInfo(regionInfoMap, this.localIndex, daemonConfigFile, regionprefix);
         if (localDaemon == null) {
            throw new IOException("unexpected local daemon");
         } else if (localDaemon.getIndex() < 0) {
            throw new IOException("unexpected local daemon index is " + localDaemon.getIndex());
         } else if (localDaemon.getTotalMemory() < 0L) {
            throw new IOException("unexpected local getTotalMemory is " + localDaemon.getTotalMemory());
         } else if (localDaemon.getUsedMemory() < 0L) {
            throw new IOException("unexpected local getUsedMemory is " + localDaemon.getUsedMemory());
         } else {
            long daemonBitMask = (long)(1 << localDaemon.getIndex());
            long daemonTotalMemory = localDaemon.getTotalMemory();
            long daemonUsedMemory = localDaemon.getUsedMemory();
            ArrayList files = new ArrayList();
            long moreLocalDaemonMemory = 0L;
            ReplicatedStoreAdmin.RegionInfo lastRegion = null;
            int prefixSize = regionprefix.length();
            int digits = true;
            Iterator var21 = regionInfoMap.values().iterator();

            while(true) {
               while(var21.hasNext()) {
                  ReplicatedStoreAdmin.RegionInfo regionInfo = (ReplicatedStoreAdmin.RegionInfo)var21.next();
                  String name = regionInfo.getName();
                  if (name != null && name.length() == prefixSize + 6 && name.startsWith(regionprefix)) {
                     String suffix = name.substring(prefixSize, prefixSize + 6);
                     boolean skip = false;

                     for(int offset = 0; offset < 6; ++offset) {
                        char one = suffix.charAt(offset);
                        if (one < '0' || '9' < one) {
                           skip = true;
                           if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
                              StoreDebug.storeIOPhysicalVerbose.debug("ReplicatedStoreIO.listRegionsOrFiles skip " + name + " has non numeric character " + one);
                           }
                           break;
                        }
                     }

                     if (!skip) {
                        lastRegion = regionInfo;
                        if ((regionInfo.getNodes() & daemonBitMask) == 0L) {
                           moreLocalDaemonMemory += (long)(regionInfo.getSize() + 1024);
                           if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
                              StoreDebug.storeIOPhysicalVerbose.debug("ReplicatedStoreIO.listRegionsOrFiles remote size=" + regionInfo.getSize() + "/" + regionInfo.getUsed() + " " + name);
                           }
                        } else if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
                           StoreDebug.storeIOPhysicalVerbose.debug("ReplicatedStoreIO.listRegionsOrFiles LOCAL! size=" + regionInfo.getSize() + "/" + regionInfo.getUsed() + " " + name);
                        }

                        File oneFile = new File(configuredDirectory, name + "." + Heap.DEFAULT_REPLICATED_SUFFIX);
                        files.add(oneFile);
                        if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
                           StoreDebug.storeIOPhysicalVerbose.debug("ReplicatedStoreIO.listRegionsOrFiles name=" + oneFile.getName() + " as " + oneFile.getCanonicalPath());
                        }
                     }
                  } else if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
                     StoreDebug.storeIOPhysicalVerbose.debug("ReplicatedStoreIO.listRegionsOrFiles skip " + name + " does not start with " + regionprefix);
                  }
               }

               if (lastRegion == null) {
                  int maxRegionSize = (Integer)heap.getConfig().get("RegionSize");
                  if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
                     StoreDebug.storeIOPhysicalVerbose.debug("ReplicatedStoreIO.listRegionsOrFiles create file maxRegionSize=" + maxRegionSize);
                  }

                  moreLocalDaemonMemory += (long)(maxRegionSize + 1024);
               }

               long percentUsed = 100L * (daemonUsedMemory + moreLocalDaemonMemory) / daemonTotalMemory;
               if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
                  StoreDebug.storeIOPhysicalVerbose.debug("ReplicatedStoreIO.listRegionsOrFiles percentUsed=" + percentUsed + " daemonUsedMemory=" + daemonUsedMemory + " moreLocalDaemonMemory=" + moreLocalDaemonMemory + " daemonTotalMemory=" + daemonTotalMemory);
               }

               if (moreLocalDaemonMemory > 0L) {
                  int redPercent = (Integer)heap.getConfig().get("SpaceOverloadRedPercent");
                  if (percentUsed >= (long)redPercent) {
                     throw new IOException("opening replicated store will increase memory on local daemon by " + moreLocalDaemonMemory + ". Memory will be (" + percentUsed + "%) exceeding allowed (" + redPercent + "%) with local daemon index=" + localDaemon.getIndex() + " for replicated store " + regionprefix);
                  }
               }

               return (File[])files.toArray(new File[files.size()]);
            }
         }
      } else {
         throw new IOException("unexpected missing value: prefix=" + regionprefix + " configFile=" + daemonConfigFile);
      }
   }

   public void dump(XMLStreamWriter xsw) throws XMLStreamException {
      xsw.writeStartElement("ReplicatedStore");
      this.dumpInternal(xsw);
   }
}
