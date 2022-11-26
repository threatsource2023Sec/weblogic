package weblogic.store.io.file;

import com.bea.security.utils.random.SecureRandomData;
import java.io.File;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import weblogic.kernel.KernelStatus;
import weblogic.store.PersistentStoreException;
import weblogic.store.StoreLogger;
import weblogic.store.StoreWritePolicy;
import weblogic.store.common.StoreDebug;
import weblogic.store.internal.StoreStatisticsImpl;
import weblogic.store.io.file.checksum.Checksummer;
import weblogic.store.io.file.checksum.ChecksummerFactory;
import weblogic.store.io.file.direct.DirectIOManager;

public final class Heap {
   private static boolean HANDLE_TRACKING;
   private static final int MAGIC_GEN_RETRY_MAX = 10;
   public static final boolean LARGE_DEFAULTS = Integer.getInteger("sun.arch.data.model", 32) >= 64 || Boolean.getBoolean("weblogic.store.LargeDefaults");
   private static final String WLS_STORE_CACHE = "WLStoreCache";
   public static final String OS_TMP_DIR;
   public static final String DEFAULT_FILE_SUFFIX = "dat";
   public static final String DEFAULT_REPLICATED_SUFFIX;
   public static final String DEFAULT_REGION_DIR;
   private static final Integer DEFAULT_BLOCK_SIZE;
   private static final int DEFAULT_IO_SIZE;
   private static final int DEFAULT_MIN_MAP_SIZE;
   private static final int DEFAULT_MAX_MAP_SIZE;
   private static final String WL_CLIENT = "WLClient";
   private static final String GLOBAL_DIRECT_IO_MODE;
   private static final String GLOBAL_AVOID_DIRECT_IO;
   private static final int MAX_INC_FILE_SIZE = 10485760;
   public static final short HEAP_VERSION_3 = 3;
   public static final short HEAP_VERSION_2 = 2;
   public static final short HEAP_VERSION_1 = 1;
   public static final short HEAP_VERSION_CURRENT = 3;
   private static final byte RECORD_VERSION_CURRENT = 5;
   private static final byte RECORD_VERSION_PREVIOUS = 4;
   private static final byte RECORD_VERSION_OLD = 2;
   private static final boolean ENABLE_FILESTORE_CLEAN_ON_BOOT;
   private static final boolean SKIP_SPACE_UPDATES;
   static final String DIRECT_MODE_DUAL_READ_BUFFERED = "read-buffered";
   static final String DIRECT_MODE_SINGLE_BUFFERED = "single-handle-buffered";
   static final String DIRECT_MODE_SINGLE_UNBUFFERED = "single-handle-unbuffered";
   static final String DIRECT_MODE_SINGLE_NONE = "single-handle-non-direct";
   private final LinkedList readLog;
   private final String dirName;
   private String storeName;
   private String regionName;
   private final String suffix;
   private int hashCode;
   private String localBlockSizePropertyName;
   private Integer localBlockSizeProperty;
   private StoreDir dir;
   private Checksummer writeChecksummer;
   private Checksummer readChecksummer;
   private final DiskScheduler scheduler;
   private final Set flushList;
   private final boolean autoCreateDirs;
   private int directAlignment;
   private final BaseStoreIO baseStoreIO;
   private final DirectIOManager directIOManager;
   private ByteBuffer directZeroBuffer;
   private DirectBufferPool bufferPool;
   private int blockSize;
   private StoreHeap allocator;
   private short lastFileWritten;
   private short heapVersion;
   private long maxFileSize;
   private int extentBlocks;
   private int maxExtentBlocks;
   private String cacheDir;
   private String tempDirPrefix;
   String uuidStr;
   private Map config;
   private StoreWritePolicy writePolicy;
   private boolean supportOSDirectIO;
   private boolean singleHandleDirectIO;
   private int ioSize;
   private int minMapSize;
   private int maxMapSize;
   private boolean locking;
   boolean isReplicatedStore;
   private StoreStatisticsImpl stats;
   boolean enforceExplicitIO;
   private short recoveryFileNum;
   private int recoveryBlock;
   private long recoveryFilePos;
   private int recoveryFileBlocks;
   private boolean recoveryComplete;
   private long uuidLo;
   private long uuidHi;
   private int readLogRemaining;
   private boolean isOpen;
   private Map ht;
   private static final long HEAP_RECORD_MAGIC = 1370321247807281150L;
   private static final int STATE_SALT = -123456789;
   private long heapRecordMagic;
   private static int OLD_HEADER_LENGTH;
   private static int HEADER_LENGTH;
   private static final int TAIL_LENGTH = 8;

   public Heap(String storeName, String dirName) throws PersistentStoreException {
      this(storeName, dirName, "dat", true);
   }

   public Heap(String storeName, String dirName, boolean autoCreateDirs) throws PersistentStoreException {
      this(storeName, dirName, "dat", autoCreateDirs);
   }

   public Heap(String storeName, String dirName, String suffix) throws PersistentStoreException {
      this(storeName, dirName, suffix, true);
   }

   public Heap(String storeName, String dirName, String suffix, boolean autoCreateDirs) throws PersistentStoreException {
      this((BaseStoreIO)null, (DirectIOManager)null, storeName, dirName, suffix, autoCreateDirs, false);
   }

   public Heap(BaseStoreIO baseStoreIO, DirectIOManager directIOManager, String storeName, String dirName, String suffix, boolean autoCreateDirs, boolean isReplicatedStore) throws PersistentStoreException {
      this.readLog = new LinkedList();
      this.writeChecksummer = ChecksummerFactory.getNewInstance();
      this.readChecksummer = ChecksummerFactory.getNewInstance();
      this.scheduler = new DiskScheduler();
      this.flushList = new HashSet();
      this.maxFileSize = 1342177280L;
      this.config = new HashMap();
      this.isReplicatedStore = false;
      this.isOpen = false;
      this.isReplicatedStore = isReplicatedStore;
      this.storeName = storeName;
      this.regionName = this.computeRegionName((Map)null);
      this.baseStoreIO = baseStoreIO;
      this.autoCreateDirs = autoCreateDirs;
      if (isReplicatedStore) {
         this.supportOSDirectIO = true;
         this.singleHandleDirectIO = true;
         this.suffix = DEFAULT_REPLICATED_SUFFIX;
         this.dirName = dirName + DEFAULT_REGION_DIR;
      } else {
         this.suffix = suffix;
         this.dirName = dirName;
         this.hashCode = this.storeName.hashCode();
         this.localBlockSizePropertyName = "weblogic.store." + storeName + ".BlockSize";
         this.localBlockSizeProperty = getBlockSizeFromProperty(this.localBlockSizePropertyName);
         this.dir = new StoreDir(this, dirName, storeName, suffix);
      }

      if (directIOManager == null) {
         this.directIOManager = DirectIOManager.getFileManager();
      } else {
         this.directIOManager = directIOManager;
      }

      if (!$assertionsDisabled) {
         HANDLE_TRACKING = true;
         if (false) {
            throw new AssertionError();
         }
      }

   }

   String getDirName() {
      return this.dirName;
   }

   String getSuffix() {
      return this.suffix;
   }

   public String getName() {
      return this.storeName;
   }

   public String getRegionName() {
      return this.regionName;
   }

   public void setConfig(HashMap config) {
      if (config == null) {
         config = new HashMap();
      }

      if (this.baseStoreIO != null) {
         config = this.baseStoreIO.adjustConfig(config);
      }

      this.config = config;
   }

   public HashMap getConfig() {
      return (HashMap)this.config;
   }

   public void setStats(StoreStatisticsImpl statistics) {
      this.stats = statistics;
      this.stats.init(this.getName(), this.getConfig());
   }

   public StoreStatisticsImpl getStats() {
      return this.stats;
   }

   public void setSynchronousWritePolicy(StoreWritePolicy policy) {
      this.writePolicy = policy;
   }

   public void setWriteChecksummer(Checksummer writeChecksummer) {
      this.writeChecksummer = writeChecksummer;
   }

   public void setReadChecksummer(Checksummer readChecksummer) {
      this.readChecksummer = readChecksummer;
   }

   public void setChecksummer(Checksummer checksummer) {
      this.readChecksummer = checksummer;
      this.writeChecksummer = checksummer;
   }

   boolean isOpen() {
      return this.isOpen;
   }

   String computeRegionName(Map config) throws PersistentStoreException {
      StringBuilder theRegionName = new StringBuilder();
      if (config != null) {
         String domainName = (String)config.get("DomainName");
         if (domainName == null) {
            throw new PersistentStoreException("Domain name missing.");
         }

         theRegionName.append(domainName.toUpperCase()).append("_").append(this.storeName.toUpperCase()).append("_");
      } else {
         theRegionName.append(this.storeName.toUpperCase());
      }

      return theRegionName.toString();
   }

   public void open() throws PersistentStoreException {
      if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
         StoreDebug.storeIOPhysicalVerbose.debug("open begin");
      }

      int tmpAlignment;
      if (this.isReplicatedStore) {
         if (this.config.get("RegionSize") == null) {
            throw new AssertionError();
         }

         this.regionName = this.computeRegionName(this.config);
         this.hashCode = this.regionName.hashCode();
         this.dir = new StoreDir(this, this.dirName, this.regionName, this.suffix);
         this.localBlockSizePropertyName = "weblogic.store." + this.storeName + ".BlockSize";
         this.localBlockSizeProperty = getBlockSizeFromProperty(this.localBlockSizePropertyName);

         try {
            tmpAlignment = this.dirName.length() - DEFAULT_REGION_DIR.length();
            this.dir.checkOK(this.autoCreateDirs, tmpAlignment);
         } catch (IOException var18) {
            throw new PersistentStoreException(var18);
         } catch (SecurityException var19) {
            throw new PersistentStoreException(var19);
         }
      }

      try {
         this.dir.checkOK(this.isReplicatedStore ? true : this.autoCreateDirs);
      } catch (IOException var16) {
         throw new PersistentStoreException(var16);
      } catch (SecurityException var17) {
         throw new PersistentStoreException(var17);
      }

      try {
         File tempFile = File.createTempFile("wls", ".dat", new File(this.dirName));
         tmpAlignment = this.directIOManager.checkAlignment(tempFile);
         tempFile.delete();
      } catch (IOException var15) {
         tmpAlignment = -1;
      }

      this.directAlignment = tmpAlignment;
      this.locking = this.getLocking();
      if (this.isReplicatedStore) {
         StoreLogger.logOpeningReplicatedStore(this.storeName, this.regionName, this.getDirectoryName(), this.getDriver());
      } else {
         StoreLogger.logOpeningPersistentStore(this.storeName, this.getDirectoryName(), this.writePolicy.toString(), this.locking, this.getDriver());
      }

      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StoreDebug.storeIOPhysical.debug("Config for " + (this.isReplicatedStore ? this.storeName + "(Region Name=" + this.regionName + ")" : this.storeName) + "\nwritePolicy=" + this.config.get("SynchronousWritePolicy") + "\nfileLocking=" + this.config.get("FileLockingEnabled") + "\nioSize=" + this.config.get("IoBufferSize") + "\nblockSize=" + this.config.get("BlockSize") + "\nminMapSize=" + this.config.get("MinWindowBufferSize") + "\nmaxMapSize=" + this.config.get("MaxWindowBufferSize") + "\nmaxFileSize=" + this.config.get("MaxFileSize"));
      }

      Integer ioSizeConfig = (Integer)this.config.get("IoBufferSize");
      if (ioSizeConfig != null && ioSizeConfig >= 0) {
         this.ioSize = adjustedIOSize("IOBufferSize", ioSizeConfig);
      } else {
         this.ioSize = DEFAULT_IO_SIZE;
      }

      Integer minMapSizeConfig = (Integer)this.config.get("MinWindowBufferSize");
      if (minMapSizeConfig != null && minMapSizeConfig >= 0) {
         this.minMapSize = power2("MinWindowBufferSize", minMapSizeConfig);
      } else {
         this.minMapSize = DEFAULT_MIN_MAP_SIZE;
      }

      Integer maxMapSizeConfig = (Integer)this.config.get("MaxWindowBufferSize");
      if (maxMapSizeConfig != null && maxMapSizeConfig >= 0) {
         this.maxMapSize = power2("MaxWindowBufferSize", maxMapSizeConfig);
      } else {
         this.maxMapSize = DEFAULT_MAX_MAP_SIZE;
      }

      this.cacheDir = (String)this.config.get("CacheDirectory");
      if (this.cacheDir == null) {
         String domain = (String)this.config.get("DomainName");
         this.tempDirPrefix = OS_TMP_DIR + File.separator + (domain == null ? "WLClient" : domain);
      } else {
         this.cacheDir = this.cacheDir + File.separator + "WLStoreCache";
      }

      this.bufferPool = new DirectBufferPool(this.ioSize, this.stats);
      if (!this.isReplicatedStore && this.writePolicy.schedulerNeeded()) {
         this.scheduler.calibrate(this.dir.getDirName());
      } else {
         this.scheduler.disable();
      }

      this.allocator = new StoreHeap(this.scheduler.isEnabled());

      List files;
      try {
         files = this.dir.open(this.bufferPool, this.autoCreateDirs);
      } catch (IOException var14) {
         throw new PersistentStoreException(var14);
      }

      if (!this.directIOManager.nativeFileCodeAvailable() && !this.writePolicy.unforced() && KernelStatus.isServer()) {
         StoreLogger.logNativeDriverLoadFailure(this.storeName);
      }

      if (this.writePolicy == StoreWritePolicy.DIRECT_WRITE_WITH_CACHE) {
         if (this.directAlignment < 0) {
            this.writePolicy = StoreWritePolicy.DIRECT_WRITE;
         } else {
            this.supportOSDirectIO = true;
            this.singleHandleDirectIO = true;
         }
      }

      if (!this.isReplicatedStore && this.writePolicy == StoreWritePolicy.DIRECT_WRITE) {
         if (this.directAlignment > 0) {
            this.evaluateDirectIOModeProperties();
         } else if (DirectIOManager.getManager().nativeFileCodeAvailable() && KernelStatus.isServer()) {
            StoreLogger.logWritePolicyDowngraded(this.storeName);
         }
      }

      String failedOpen = null;

      try {
         Iterator var7 = files.iterator();

         while(var7.hasNext()) {
            StoreFile f = (StoreFile)var7.next();
            failedOpen = f.getName();
            this.openStoreFile(f);
         }
      } catch (IOException var21) {
         Iterator var8 = files.iterator();

         while(var8.hasNext()) {
            StoreFile f = (StoreFile)var8.next();

            try {
               f.close();
            } catch (IOException var13) {
            }
         }

         if (this.isReplicatedStore) {
            throw new PersistentStoreException(StoreLogger.logRegionOpenErrorLoggable(this.storeName, failedOpen.toString()), var21);
         }

         throw new PersistentStoreException(StoreLogger.logFileOpenErrorLoggable(this.storeName, failedOpen.toString()), var21);
      }

      if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
         StoreDebug.storeIOPhysicalVerbose.debug("Opening dir=" + this.dir.getDirName() + " total file size = " + this.dir.getBytesUsed() + " bytes ");
      }

      if (this.dir.numFiles() == 0) {
         this.blockSize = -1;
         this.heapVersion = 3;
         this.heapRecordMagic = generateHeaderMagic();
         this.establishBlockSize(0);
         this.establishMaxFileSize();
         this.establishUUID();
         Long configInitialStoreBytes = (Long)this.config.get("InitialSize");
         if (configInitialStoreBytes != null && configInitialStoreBytes > 0L) {
            long initStoreBlocks = configInitialStoreBytes / (long)this.blockSize;
            long maxFileBlocks = this.maxFileSize / (long)this.blockSize;

            try {
               while(initStoreBlocks > 0L) {
                  int nextBlocks = (int)Math.min(initStoreBlocks, maxFileBlocks);
                  this.reserveSpace(nextBlocks);
                  initStoreBlocks -= (long)nextBlocks;
               }
            } catch (IOException var20) {
               if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
                  StoreDebug.storeIOPhysical.debug("Heap.open: Error trying to reserve more file space.", var20);
               }

               throw new PersistentStoreException(var20);
            }
         }

         this.completeRecovery();
      } else {
         this.recoveryComplete = false;
         this.recoveryFileNum = 0;
         this.blockSize = -1;
         this.getNextRecoveryFile();
      }

      this.isOpen = true;
   }

   public void removeStoreFiles() throws PersistentStoreException {
      if (this.dir != null) {
         if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
            StoreDebug.storeIOPhysicalVerbose.debug("Purging store files in directory " + this.dirName);
         }

         try {
            this.dir.deleteFiles();
         } catch (Exception var2) {
            throw new PersistentStoreException("Cannot purge store", var2);
         }
      }
   }

   private void openStoreFile(StoreFile file) throws IOException {
      file.ioSize = this.ioSize;
      file.minMapSize = this.minMapSize;
      file.maxMapSize = this.maxMapSize;
      file.locking = this.locking;
      file.stats = this.stats;
      file.enforceExplicitIO = this.enforceExplicitIO;
      if (this.supportOSDirectIO) {
         if (this.singleHandleDirectIO) {
            file.openSingleHandleDirect(this.writePolicy);
         } else {
            file.openDirect(this.writePolicy);
         }
      } else {
         file.open(this.writePolicy);
      }

   }

   FileChannel fileChannelFactory(File file, String mode, boolean exclusive) throws IOException {
      return this.baseStoreIO == null ? FileStoreIO.staticOpen(this, file, mode, exclusive) : this.baseStoreIO.fileChannelFactory(this.config, file, mode, exclusive);
   }

   private void openCacheFile(StoreFile file) throws IOException {
      if (this.cacheDir == null) {
         this.cacheDir = this.tempDirPrefix + File.separator + this.uuidStr;
      }

      file.openCacheFile(this.cacheDir, this.isReplicatedStore ? this.regionName : this.storeName);
   }

   public int getBlockSize() {
      return this.blockSize - HEADER_LENGTH;
   }

   public final int getInternalBlockSize() {
      return this.blockSize;
   }

   long getMaxFileSize() {
      return this.maxFileSize;
   }

   public short getHeapVersion() {
      return this.heapVersion;
   }

   private final int getNextExtentSize() {
      int ret = this.extentBlocks;
      this.extentBlocks = Math.min(this.extentBlocks * 2, this.maxExtentBlocks);
      return ret;
   }

   private void reserveSpace(int blocks) throws IOException, PersistentStoreException {
      if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
         StoreDebug.storeIOPhysicalVerbose.debug("reserveSpace begin");
      }

      blocks = Math.max(blocks, this.getNextExtentSize());
      long bytes = (long)(blocks * this.blockSize);
      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StoreDebug.storeIOPhysical.debug("Heap: Trying to reserve " + bytes + " bytes more file space.");
      }

      if (bytes > this.maxFileSize) {
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StoreDebug.storeIOPhysical.debug("Heap: bytes " + bytes + " exceeds maxFileSize " + this.maxFileSize);
         }

         throw new PersistentStoreException(StoreLogger.logRecordTooLongLoggable(this.maxFileSize));
      } else {
         if (this.dir.numFiles() > 0) {
            StoreFile lastFile = this.dir.get(this.dir.numFiles() - 1);
            if (lastFile.checkFileBytesQuota(bytes, this.maxFileSize)) {
               try {
                  int startBlock = (int)(lastFile.size() / (long)this.blockSize);
                  lastFile.expand(bytes);
                  this.allocator.expand(lastFile.getFileNum(), startBlock, blocks);
                  this.updateStats(lastFile);
                  if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
                     StoreDebug.storeIOPhysical.debug("Heap: Expanded file " + lastFile.getFileNum() + " by " + bytes + " bytes to " + lastFile.size() + " bytes.");
                  }

                  return;
               } catch (IOException var14) {
                  if (StoreHeap.DEBUG_SPACE_UPDATES && this.isReplicatedStore && StoreDebug.storeIOPhysical.isDebugEnabled()) {
                     StoreDebug.storeIOPhysical.debug("RS: " + this.storeName + " got exception when expand the current file, will create a new file. ");
                  }
               }
            }
         }

         if (this.dir.numFiles() >= 32767) {
            if (this.isReplicatedStore) {
               throw new PersistentStoreException(StoreLogger.logTooManyRegionsCreatedLoggable(32767));
            } else {
               throw new PersistentStoreException(StoreLogger.logTooManyFilesCreatedLoggable(32767));
            }
         } else {
            short newFileNum = (short)this.dir.numFiles();
            File newFile = this.dir.createFile(newFileNum);
            StoreFile newStoreFile = new StoreFile(this, this.dir, newFile, newFileNum, this.bufferPool);

            try {
               boolean firstAttempt = true;

               while(true) {
                  this.openStoreFile(newStoreFile);

                  try {
                     if (newStoreFile.hasCacheFile()) {
                        this.openCacheFile(newStoreFile);
                     }

                     newStoreFile.expand(bytes + (long)this.blockSize);
                     break;
                  } catch (IOException var12) {
                     this.checkLockedMapException(newStoreFile, firstAttempt, var12);
                     firstAttempt = false;
                  }
               }

               HeapFileHeader fileHeader = new HeapFileHeader(this.heapVersion, this.blockSize, this.uuidLo, this.uuidHi, this.heapRecordMagic);
               ByteBuffer fileHeaderBuf;
               if (newStoreFile.mapped()) {
                  fileHeaderBuf = newStoreFile.getDirectMappedBuffer(0L, (long)(0 + this.blockSize));
                  fileHeader.serialize(fileHeaderBuf);
               } else {
                  fileHeaderBuf = fileHeader.getBuffer();
               }

               if (this.writeExplicit()) {
                  newStoreFile.write(0L, fileHeaderBuf);
               }

               newStoreFile.flush0();
               this.allocator.expand(newFileNum, 1, blocks);
               this.dir.addNewFile(newStoreFile);
               this.updateStats(newStoreFile);
               if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
                  StoreDebug.storeIOPhysical.debug("Heap: Created new file " + newFileNum + " in dir " + this.dir.getDirName() + " with " + bytes + " bytes.");
               }

            } catch (IOException var13) {
               try {
                  newStoreFile.close();
               } catch (IOException var10) {
               }

               try {
                  newFile.delete();
               } catch (SecurityException var11) {
                  if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
                     StoreDebug.storeIOPhysical.debug("Heap: SecurityException: " + var11.toString(), var11);
                  }
               }

               if (var13 != null) {
                  throw var13;
               } else {
                  throw new AssertionError("Internal error expanding file store");
               }
            }
         }
      }
   }

   private void checkLockedMapException(StoreFile f, boolean firstAttempt, IOException ioEx) throws IOException {
      if (firstAttempt && this.locking && f.getFileNum() == 0 && f.mapped()) {
         if (f.hasCacheFile()) {
            File cf = f.getCacheFile();
            if (cf != null) {
               StoreLogger.logFileMappingError(this.isReplicatedStore ? this.storeName + "(Region Name=" + this.regionName + ")" : this.storeName, cf.toString(), ioEx);
            }
         } else if (StoreDebug.cacheDebug.isDebugEnabled()) {
            StoreDebug.cacheDebug.debug("Fall back on conventional IO for a locked file: " + f, ioEx);
         }

         try {
            f.close();
         } catch (IOException var5) {
         }

         if (this.writePolicy.unforced()) {
            this.enforceExplicitIO = true;
         } else {
            this.writePolicy = StoreWritePolicy.DIRECT_WRITE;
            this.singleHandleDirectIO = true;
            this.supportOSDirectIO = false;
         }

      } else {
         throw ioEx;
      }
   }

   /** @deprecated */
   @Deprecated
   public long[] multiWrite(List records, boolean autoFlush) throws PersistentStoreException {
      return this.multiWrite(records);
   }

   public long[] multiWrite(List records) throws PersistentStoreException {
      if (!this.recoveryComplete) {
         throw new PersistentStoreException(StoreLogger.logRecoveryNotCompleteLoggable());
      } else {
         int numRecords = records.size();
         int[] numBlocks = new int[numRecords];
         HeapHeader[] headers = new HeapHeader[numRecords];
         short[] paddings = new short[numRecords];
         int totalNumBlocks = 0;
         Iterator iter = records.iterator();

         int ht;
         int index;
         for(int i = 0; i < numRecords; ++i) {
            List record = (List)iter.next();
            HeapHeader heapHeader = new HeapHeader(record);
            ht = getPadding(heapHeader.totalLength, (short)this.blockSize);
            index = (heapHeader.totalLength + ht) / this.blockSize;
            headers[i] = heapHeader;
            paddings[i] = (short)ht;
            numBlocks[i] = index;
            totalNumBlocks += index;
         }

         long[] handles;
         StoreFile writeFile;
         synchronized(this) {
            handles = this.allocator.alloc(this.lastFileWritten, this.scheduler.getNextBlock(), numBlocks);
            if (handles == null) {
               try {
                  this.reserveSpace(totalNumBlocks);
               } catch (IOException var27) {
                  if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
                     StoreDebug.storeIOPhysical.debug("Heap.multiWrite: Error trying to reserve more file space.", var27);
                  }

                  throw new PersistentStoreException(var27);
               } catch (RuntimeException var28) {
                  if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
                     StoreDebug.storeIOPhysical.debug("Heap.multiWrite: Error trying to reserve more file space.", var28);
                  }

                  throw new PersistentStoreException(var28);
               } catch (Error var29) {
                  if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
                     StoreDebug.storeIOPhysical.debug("Heap.multiWrite: Error trying to reserve more file space.", var29);
                  }

                  throw new PersistentStoreException(var29);
               }

               handles = this.allocator.alloc(this.lastFileWritten, this.scheduler.getNextBlock(), numBlocks);
               if (handles == null) {
                  throw new AssertionError("Internal error: No room in the store");
               }
            }

            if (HANDLE_TRACKING) {
               for(ht = 0; ht < handles.length; ++ht) {
                  this.addHandle(handles[ht]);
               }
            }

            this.lastFileWritten = StoreHeap.handleToFileNum(handles[0]);
            writeFile = this.dir.get(this.lastFileWritten);
            if (writeFile.getWritePolicy() != this.writePolicy) {
               try {
                  writeFile.close();
                  this.openStoreFile(writeFile);
               } catch (IOException var26) {
                  throw new PersistentStoreException(StoreLogger.logErrorWritingToStoreLoggable(), var26);
               }
            }
         }

         int writeBlock = StoreHeap.handleToFileBlock(handles[0]);
         StoreFileCursor fileCursor = new StoreFileCursor(writeFile, writeBlock, totalNumBlocks);
         this.scheduler.start();

         try {
            index = 0;

            for(Iterator var13 = records.iterator(); var13.hasNext(); ++index) {
               List record = (List)var13.next();
               fileCursor.writeDataWithChecksumInTail(headers[index], record, paddings[index]);
            }

            fileCursor.finalWrite();
            if (this.writePolicy == StoreWritePolicy.CACHE_FLUSH) {
               synchronized(this) {
                  this.flushList.add(writeFile);
               }

               this.flush();
            }

            this.scheduler.stop(writeBlock + totalNumBlocks);
            this.updateStats(writeFile);
         } catch (IOException var30) {
            throw new PersistentStoreException(StoreLogger.logErrorWritingToStoreLoggable(), var30);
         } finally {
            fileCursor.realeaseBuffer();
         }

         return handles;
      }
   }

   private boolean writeExplicit() {
      return this.writePolicy.writeExplicit() || this.enforceExplicitIO;
   }

   public void delete(long handle) throws PersistentStoreException {
      if (!this.recoveryComplete) {
         throw new PersistentStoreException(StoreLogger.logRecoveryNotCompleteLoggable());
      } else {
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StoreDebug.storeIOPhysical.debug("Heap: Deleting record " + handleToString(handle));
         }

         StoreFile deleteFile;
         synchronized(this) {
            deleteFile = this.dir.get(StoreHeap.handleToFileNum(handle));
         }

         boolean needsFlush;
         try {
            needsFlush = this.zeroOutMagic(deleteFile, StoreHeap.handleToFileBlock(handle), false);
         } catch (IOException var8) {
            throw new PersistentStoreException(StoreLogger.logErrorWritingToStoreLoggable(), var8);
         }

         synchronized(this) {
            if (HANDLE_TRACKING) {
               this.checkHandle(handle);
            }

            if (needsFlush) {
               this.flushList.add(deleteFile);
            }

            this.allocator.free(handle);
            if (HANDLE_TRACKING) {
               this.removeHandle(handle);
            }
         }

         try {
            this.updateStats(deleteFile);
         } catch (IOException var7) {
            throw new PersistentStoreException(StoreLogger.logErrorWritingToStoreLoggable(), var7);
         }
      }
   }

   public void forget(long handle) {
      if (handle != -1L) {
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StoreDebug.storeIOPhysical.debug("Forgetting record " + handleToString(handle));
         }

         synchronized(this) {
            if (HANDLE_TRACKING) {
               this.checkHandle(handle);
            }

            this.allocator.free(handle);
            if (HANDLE_TRACKING) {
               this.removeHandle(handle);
            }
         }

         try {
            this.updateStats(this.dir.get(StoreHeap.handleToFileNum(handle)));
         } catch (IOException var5) {
         }

      }
   }

   private boolean zeroOutMagic(StoreFile file, int fileBlock, boolean flushNow) throws IOException {
      this.directZeroBuffer.clear();
      long filePos = (long)(fileBlock * this.blockSize);
      if (file.mapped()) {
         ByteBuffer mappedBlock = file.getDirectMappedBuffer(filePos, filePos + (long)this.blockSize);
         DirectIOManager.getFileMemoryManager().zeroBuffer(mappedBlock);
      }

      if (this.writeExplicit()) {
         file.write(filePos, this.directZeroBuffer);
      }

      if (this.writePolicy == StoreWritePolicy.CACHE_FLUSH) {
         if (flushNow) {
            file.flush();
         }

         return !flushNow;
      } else {
         return false;
      }
   }

   public void flush() throws PersistentStoreException {
      ArrayList tmpList;
      synchronized(this) {
         if (this.flushList.isEmpty()) {
            return;
         }

         tmpList = new ArrayList(this.flushList);
         this.flushList.clear();
      }

      IOException savedException = null;
      Iterator flushees = tmpList.iterator();

      while(flushees.hasNext()) {
         StoreFile curFile = (StoreFile)flushees.next();

         try {
            curFile.flush();
         } catch (IOException var6) {
            savedException = var6;
         }

         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StoreDebug.storeIOPhysical.debug("Heap: Flushed fileNum " + curFile.getFileNum());
         }
      }

      if (savedException != null) {
         if (this.isReplicatedStore) {
            throw new PersistentStoreException(StoreLogger.logErrorFlushingRegionLoggable(), savedException);
         } else {
            throw new PersistentStoreException(StoreLogger.logErrorFlushingFileLoggable(), savedException);
         }
      }
   }

   public HeapRecord read(long handle) throws PersistentStoreException {
      if (!this.recoveryComplete) {
         throw new PersistentStoreException(StoreLogger.logRecoveryNotCompleteLoggable());
      } else {
         if (HANDLE_TRACKING) {
            this.checkHandle(handle);
         }

         if (handle != 0L && handle != -1L) {
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               StoreDebug.storeIOPhysical.debug("Heap: Reading record " + handleToString(handle));
            }

            short fileNum = StoreHeap.handleToFileNum(handle);
            int fileBlock = StoreHeap.handleToFileBlock(handle);
            int numBlocks = StoreHeap.handleToNumBlocks(handle);
            if (fileNum >= 0 && fileBlock >= 0 && numBlocks > 0) {
               StoreFile file;
               synchronized(this) {
                  file = this.dir.get(fileNum);
               }

               int heapRecordSize = numBlocks * this.blockSize;
               int filePos = fileBlock * this.blockSize;
               ByteBuffer buf;
               if (file.mapped()) {
                  buf = file.mappedRead((long)filePos, heapRecordSize);
               } else {
                  buf = ByteBuffer.allocate(heapRecordSize);

                  try {
                     file.read((long)filePos, buf);
                  } catch (IOException var12) {
                     throw new PersistentStoreException(StoreLogger.logErrorReadingFromStoreLoggable(), var12);
                  }
               }

               if (!this.verifyMagic(buf)) {
                  throw new PersistentStoreException(StoreLogger.logStoreRecordNotFoundLoggable(handle));
               } else {
                  HeapHeader headerRec = new HeapHeader(buf);
                  HeapRecord heapRecord = this.readRecord(headerRec, buf);
                  heapRecord.setHandle(handle);
                  return heapRecord;
               }
            } else {
               throw new PersistentStoreException(StoreLogger.logInvalidRecordHandleLoggable(handle));
            }
         } else {
            throw new PersistentStoreException(StoreLogger.logInvalidRecordHandleLoggable(handle));
         }
      }
   }

   public synchronized HeapRecord recover() throws PersistentStoreException {
      if (this.recoveryComplete) {
         return null;
      } else {
         boolean storeIOPhysicalDebugEnabled = StoreDebug.storeIOPhysical.isDebugEnabled();

         StoreFile file;
         int recBlocks;
         HeapRecord ret;
         label100:
         while(true) {
            while(true) {
               if (this.recoveryBlock < this.recoveryFileBlocks && this.writePolicy != StoreWritePolicy.NON_DURABLE) {
                  file = this.dir.get(this.recoveryFileNum);
               } else {
                  if (this.writePolicy.configurable()) {
                     this.dir.get(this.recoveryFileNum).commitScan(this.heapVersion, this.blockSize, this.uuidLo, this.uuidHi, this.heapRecordMagic);
                  }

                  ++this.recoveryFileNum;
                  if (this.recoveryFileNum >= this.dir.numFiles()) {
                     this.completeRecovery();
                     return null;
                  }

                  if (storeIOPhysicalDebugEnabled) {
                     StoreDebug.storeIOPhysical.debug("Heap: Switching recovery to file " + this.recoveryFileNum);
                  }

                  file = this.getNextRecoveryFile();
               }

               ByteBuffer headerBlock = file.mapped() ? file.mappedRecoveryRead((long)(this.recoveryBlock * this.blockSize), this.blockSize) : this.readStoreFile(file, this.blockSize);
               if (this.verifyMagic(headerBlock)) {
                  HeapHeader headerRec = new HeapHeader(headerBlock);
                  recBlocks = headerRec.getNumBlocks(this.blockSize);
                  ByteBuffer recBuf;
                  if (recBlocks > 1) {
                     int multiBlockRecordSize = recBlocks * this.blockSize;
                     recBuf = file.mapped() ? file.mappedRecoveryRead((long)(this.recoveryBlock * this.blockSize), multiBlockRecordSize) : this.readStoreFile(file, multiBlockRecordSize);
                     headerRec = new HeapHeader(recBuf);
                  } else {
                     recBuf = headerBlock;
                  }

                  try {
                     ret = this.readRecord(headerRec, recBuf);
                     break label100;
                  } catch (PersistentStoreException var11) {
                     if (storeIOPhysicalDebugEnabled) {
                        StoreDebug.storeIOPhysical.debug("Invalid record at block " + this.recoveryBlock + ": " + var11);
                     }

                     try {
                        if (ENABLE_FILESTORE_CLEAN_ON_BOOT) {
                           this.zeroOutMagic(file, this.recoveryBlock, true);
                        }
                     } catch (IOException var10) {
                     }

                     if (!file.mapped()) {
                        this.rollbackReadMoveNextBlock();
                     }

                     ++this.recoveryBlock;
                  }
               } else {
                  if (!file.mapped()) {
                     this.rollbackReadMoveNextBlock();
                  }

                  ++this.recoveryBlock;
               }
            }
         }

         long handle = this.allocator.allocForce(this.recoveryFileNum, this.recoveryBlock, recBlocks);

         assert StoreHeap.handleToFileBlock(handle) == this.recoveryBlock;

         ret.setHandle(handle);
         if (HANDLE_TRACKING) {
            this.addHandle(handle);
         }

         if (storeIOPhysicalDebugEnabled) {
            StoreDebug.storeIOPhysical.debug("Recovered record with handle " + handleToString(handle));
         }

         if (!file.mapped()) {
            this.commitRead(recBlocks);
         }

         this.recoveryBlock += recBlocks;
         return ret;
      }
   }

   private ByteBuffer readStoreFile(StoreFile file, int size) throws PersistentStoreException {
      int targetRemaining = size;

      ByteBuffer copyTarget;
      while(this.readLogRemaining < size) {
         copyTarget = this.bufferPool.get();

         try {
            int count = file.readBulk(this.recoveryFilePos, copyTarget, size - this.readLogRemaining);
            this.readLogRemaining += count;
            this.recoveryFilePos += (long)count;
            this.readLog.add(new ReadLogNode(copyTarget));
         } catch (IOException var12) {
            throw new PersistentStoreException(StoreLogger.logErrorReadingFromStoreLoggable(), var12);
         }
      }

      copyTarget = null;

      int chunk;
      for(Iterator var13 = this.readLog.iterator(); var13.hasNext(); targetRemaining -= chunk) {
         ReadLogNode rln = (ReadLogNode)var13.next();
         ByteBuffer source = rln.buf;
         int sourceLimit = source.limit();
         int sourceRemaining = source.remaining();
         chunk = Math.min(targetRemaining, sourceRemaining);
         source.limit(source.position() + chunk);
         if (copyTarget == null) {
            if (chunk == size) {
               ByteBuffer slice = source.slice();
               source.limit(sourceLimit);
               return slice;
            }

            copyTarget = ByteBuffer.allocate(size);
         }

         copyTarget.put(source);
         source.position(source.limit() - chunk);
         source.limit(sourceLimit);
         if (!copyTarget.hasRemaining()) {
            break;
         }
      }

      copyTarget.clear();
      return copyTarget;
   }

   private StoreFile getNextRecoveryFile() throws PersistentStoreException {
      this.cleanupReadLog();
      this.recoveryFilePos = 0L;
      this.recoveryBlock = 0;
      StoreFile file = this.dir.get(this.recoveryFileNum);
      HeapFileHeader header = null;
      boolean firstAttempt = true;

      while(true) {
         try {
            if (!firstAttempt) {
               this.openStoreFile(file);
            }

            ByteBuffer primaryHeaderBuf;
            if (file.mapped()) {
               if (file.hasCacheFile()) {
                  primaryHeaderBuf = this.bufferPool.get();

                  try {
                     primaryHeaderBuf.limit(8192);
                     file.read(0L, primaryHeaderBuf);
                     primaryHeaderBuf.position(0);
                     header = new HeapFileHeader(primaryHeaderBuf);
                     if (header.version == 1) {
                        throw new PersistentStoreException(this.writePolicy + "(" + file.getIOMode() + ") is not supported with 9.0- Stores.");
                     }

                     if (this.recoveryFileNum == 0) {
                        this.establishUUID(header.uuidLo, header.uuidHi);
                     }

                     this.openCacheFile(file);
                  } finally {
                     this.bufferPool.put(primaryHeaderBuf);
                  }
               } else {
                  header = file.mappedRecoveryInit(this.isReplicatedStore ? this.regionName : this.storeName);
                  if (this.recoveryFileNum == 0) {
                     this.establishUUID(header.uuidLo, header.uuidHi);
                  }
               }
            } else {
               primaryHeaderBuf = this.readStoreFile(file, 512);
               header = new HeapFileHeader(primaryHeaderBuf);
               if (this.recoveryFileNum == 0) {
                  this.establishUUID(header.uuidLo, header.uuidHi);
               }
            }

            if (this.recoveryFileNum == 0) {
               this.heapRecordMagic = header.heapHeaderMagic;
            }
            break;
         } catch (IOException var13) {
            IOException e = var13;

            try {
               this.checkLockedMapException(file, firstAttempt, e);
            } catch (IOException var11) {
               throw new PersistentStoreException(var11);
            }

            firstAttempt = false;
         }
      }

      if (this.recoveryFileNum == 0) {
         this.establishBlockSize(header.blockSize);
         this.establishMaxFileSize();
         this.heapVersion = header.version;
      } else {
         if (header.blockSize != this.blockSize) {
            throw new AssertionError("Mismatched file block sizes");
         }

         if (header.version != this.heapVersion) {
            throw new AssertionError("Mismatched heap file versions");
         }

         if ((header.uuidLo != this.uuidLo || header.uuidHi != this.uuidHi) && header.uuidLo != 0L && header.uuidHi != 0L) {
            throw new AssertionError("Mismatched uuid");
         }
      }

      if (file.hasCacheFile()) {
         try {
            file.verifyCacheFile(header, this.isReplicatedStore ? this.regionName : this.storeName);
         } catch (IOException var10) {
            throw new PersistentStoreException(var10);
         }
      }

      this.recoveryFileBlocks = (int)file.size() / this.blockSize;
      if (header.version != 2 && header.version != 3) {
         if (header.version != 1) {
            throw new AssertionError("Unknown heap file version " + header.version);
         }

         this.allocator.expand(this.recoveryFileNum, 0, this.recoveryFileBlocks);
         this.recoveryBlock = 0;
      } else {
         this.allocator.expand(this.recoveryFileNum, 1, this.recoveryFileBlocks - 1);
         this.recoveryBlock = 1;
         if (!file.mapped()) {
            this.rollbackReadMoveNextBlock();
         }
      }

      return file;
   }

   private void completeRecovery() throws PersistentStoreException {
      if (!this.recoveryComplete) {
         StoreDebug.storeIOPhysical.debug("Heap: Reached end of recovery scan");

         try {
            Iterator var1 = this.dir.getFiles().iterator();

            while(var1.hasNext()) {
               StoreFile f = (StoreFile)var1.next();
               f.adjustFileSize(this.blockSize);
               this.updateStats(f);
            }
         } catch (IOException var3) {
            throw new PersistentStoreException(var3);
         }

         this.cleanupReadLog();
         this.recoveryComplete = true;
         if (StoreHeap.DEBUG_SPACE_UPDATES && this.isReplicatedStore && StoreDebug.storeIOPhysical.isDebugEnabled() && this.allocator != null) {
            StoreDebug.storeIOPhysical.debug("RS recovery: allocatedBlocks = " + this.allocator.getAllocatedBlocks());
         }

      }
   }

   private void cleanupReadLog() throws PersistentStoreException {
      if (this.readLog.size() > 1) {
         throw new PersistentStoreException("At most one outstanding recovery buffer expected.");
      } else {
         if (!this.readLog.isEmpty()) {
            ReadLogNode rest = (ReadLogNode)this.readLog.removeFirst();
            this.bufferPool.put(rest.buf);
         }

         this.bufferPool.close();
      }
   }

   private void establishBlockSize(int newBlockSize) throws PersistentStoreException {
      Integer configBlockSize = (Integer)this.config.get("BlockSize");
      DirectIOManager ioManager = DirectIOManager.getFileMemoryManager();
      if (newBlockSize <= 0) {
         if (this.localBlockSizeProperty != null) {
            this.blockSize = this.localBlockSizeProperty;
         } else if (DEFAULT_BLOCK_SIZE != null) {
            this.blockSize = DEFAULT_BLOCK_SIZE;
         } else if (configBlockSize != null && configBlockSize > 0) {
            this.blockSize = adjustedBlockSize("BlockSize in config.xml", configBlockSize);
         } else if (this.directAlignment > 0 && this.directAlignment <= 8192) {
            this.blockSize = this.directAlignment;
         } else {
            this.blockSize = 512;
         }
      } else {
         this.blockSize = newBlockSize;
         if (this.isReplicatedStore) {
            this.blockSize = power2("ReplicatedStore " + this.getName() + ", block-size in config.xml", configBlockSize);
         }

         if (this.localBlockSizeProperty != null) {
            if (this.blockSize != this.localBlockSizeProperty) {
               if (this.isReplicatedStore) {
                  StoreLogger.logReplicatedStoreBlockSizeIgnored(this.localBlockSizePropertyName, this.storeName, this.regionName);
               } else {
                  StoreLogger.logBlockSizeIgnored(this.localBlockSizePropertyName, this.storeName);
               }
            }
         } else if (DEFAULT_BLOCK_SIZE != null) {
            if (this.blockSize != DEFAULT_BLOCK_SIZE) {
               if (this.isReplicatedStore) {
                  StoreLogger.logReplicatedStoreBlockSizeIgnored("weblogic.store.BlockSize", this.storeName, this.regionName);
               } else {
                  StoreLogger.logBlockSizeIgnored("weblogic.store.BlockSize", this.storeName);
               }
            }
         } else if (configBlockSize != null && configBlockSize > 0 && this.blockSize != configBlockSize) {
            if (this.isReplicatedStore) {
               StoreLogger.logReplicatedStoreBlockSizeIgnored("in config.xml", this.storeName, this.regionName);
            } else {
               StoreLogger.logBlockSizeIgnored("in config.xml", this.storeName);
            }
         }
      }

      if (this.stats != null) {
         this.stats.setBlockSize(this.blockSize);
      }

      boolean supportOSDirectIOFlopped = this.supportOSDirectIO;
      this.supportOSDirectIO = this.supportOSDirectIO && this.blockSize % this.directAlignment == 0;
      supportOSDirectIOFlopped = supportOSDirectIOFlopped && !this.supportOSDirectIO;
      this.directZeroBuffer = ioManager.getZeroBuffer(this.blockSize);
      Integer initialExtentOverride = Integer.getInteger("weblogic.store.InitialExtentSize");
      if (initialExtentOverride == null) {
         this.extentBlocks = 1048576 / this.blockSize;
      } else {
         this.extentBlocks = Math.max(1, initialExtentOverride / this.blockSize);
      }

      this.maxExtentBlocks = 10485760 / this.blockSize;
      if (supportOSDirectIOFlopped) {
         StoreLogger.logIncompatibleDirectIOAlignment(this.isReplicatedStore ? this.storeName + "(Region Name=" + this.regionName + ")" : this.storeName, this.directAlignment, this.blockSize);
         if (this.dir.numFiles() > 0) {
            StoreFile file = this.dir.get(this.recoveryFileNum);
            String prevMode = file.getIOMode();
            List files = this.dir.getFiles();
            Iterator var9 = files.iterator();

            while(var9.hasNext()) {
               StoreFile toOpen = (StoreFile)var9.next();

               try {
                  toOpen.close();
               } catch (IOException var16) {
               }

               try {
                  this.openStoreFile(toOpen);
               } catch (IOException var17) {
                  Iterator var12 = files.iterator();

                  while(var12.hasNext()) {
                     StoreFile toClose = (StoreFile)var12.next();

                     try {
                        toClose.close();
                     } catch (IOException var15) {
                     }
                  }

                  throw new PersistentStoreException(var17);
               }
            }

            if (prevMode == "single-handle-unbuffered") {
               this.cleanupReadLog();
               this.writePolicy = StoreWritePolicy.DIRECT_WRITE;
               this.readStoreFile(file, 512);
            }
         }
      }

   }

   private void establishUUID() {
      UUID uuid = UUID.randomUUID();
      this.uuidLo = uuid.getLeastSignificantBits();
      this.uuidHi = uuid.getMostSignificantBits();
      this.uuidStr = uuid.toString();
   }

   private void establishUUID(long headerUuidLo, long headerUuidHi) {
      UUID uuid = new UUID(headerUuidHi, headerUuidLo);
      this.uuidLo = uuid.getLeastSignificantBits();
      this.uuidHi = uuid.getMostSignificantBits();
      this.uuidStr = uuid.toString();
   }

   private void evaluateDirectIOModeProperties() {
      String avoidDirectIO = "weblogic.store." + this.storeName + ".DirectIOMode";
      String localAvoidDirectIO = System.getProperty(avoidDirectIO);
      String directMode;
      String directPropName;
      if (localAvoidDirectIO == null) {
         directPropName = "weblogic.store.DirectIOMode";
         directMode = GLOBAL_DIRECT_IO_MODE;
      } else {
         directPropName = avoidDirectIO;
         directMode = localAvoidDirectIO;
      }

      if (this.isReplicatedStore) {
         this.supportOSDirectIO = true;
         this.singleHandleDirectIO = true;
      } else if ("read-buffered".equalsIgnoreCase(directMode)) {
         this.supportOSDirectIO = true;
         this.singleHandleDirectIO = false;
      } else if ("single-handle-unbuffered".equalsIgnoreCase(directMode)) {
         this.supportOSDirectIO = true;
         this.singleHandleDirectIO = true;
      } else if ("single-handle-buffered".equalsIgnoreCase(directMode)) {
         this.supportOSDirectIO = false;
      } else if (directMode != null) {
         this.supportOSDirectIO = true;
         this.singleHandleDirectIO = true;
         StoreLogger.logInvalidDirectModeIgnored(directPropName, directMode, this.storeName, "single-handle-unbuffered");
      } else {
         this.singleHandleDirectIO = false;
         localAvoidDirectIO = System.getProperty("weblogic.store." + this.storeName + ".AvoidDirectIO");
         avoidDirectIO = localAvoidDirectIO == null ? GLOBAL_AVOID_DIRECT_IO : localAvoidDirectIO;
         this.supportOSDirectIO = avoidDirectIO == null || avoidDirectIO.length() == 0 || !String.valueOf(true).equalsIgnoreCase(avoidDirectIO);
      }

   }

   private void establishMaxFileSize() {
      if (this.isReplicatedStore) {
         Integer maxFileSizeProp = (Integer)this.config.get("RegionSize");
         if (maxFileSizeProp == null) {
            throw new AssertionError("No region size set.");
         } else if (this.blockSize == 0) {
            throw new AssertionError("Block size not set.");
         } else {
            this.maxFileSize = (long)Math.max(maxFileSizeProp, 10485760);
            this.maxFileSize -= this.maxFileSize % 10485760L;
            if (this.maxFileSize < 16777216L) {
               throw new AssertionError("Too Small " + this.maxFileSize);
            } else {
               if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
                  StoreDebug.storeIOPhysical.debug("Heap: Rep Region Size " + this.maxFileSize);
               }

            }
         }
      } else {
         try {
            Long maxFileSizeProp = Long.getLong("weblogic.store.MaxFileSize", (Long)((Long)this.config.get("MaxFileSize")));
            if (maxFileSizeProp == null) {
               return;
            }

            if (this.blockSize == 0) {
               this.establishBlockSize(0);
            }

            this.maxFileSize = maxFileSizeProp;
            this.maxFileSize = Math.max(this.maxFileSize, 10485760L);
            if (this.maxFileSize % 10485760L > 0L) {
               this.maxFileSize = (this.maxFileSize / 10485760L + 1L) * 10485760L;
            }

            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               StoreDebug.storeIOPhysical.debug("Heap: Max File Size " + this.maxFileSize);
            }
         } catch (Exception var2) {
         }

      }
   }

   public synchronized void empty() throws PersistentStoreException {
      this.establishBlockSize(0);

      for(short fileNum = 0; fileNum < this.dir.numFiles(); ++fileNum) {
         StoreFile file = this.dir.get(fileNum);
         int fileSize = (int)file.size();
         this.allocator.expand(fileNum, 1, fileSize / this.blockSize - 1);

         try {
            this.updateStats(file);
         } catch (IOException var5) {
         }
      }

      this.completeRecovery();
   }

   public synchronized void close() throws PersistentStoreException {
      PersistentStoreException firstException = null;

      try {
         this.flush();
      } catch (PersistentStoreException var6) {
         firstException = var6;
      }

      Iterator var2 = this.dir.getFiles().iterator();

      while(var2.hasNext()) {
         StoreFile file = (StoreFile)var2.next();

         try {
            if (!file.hasCacheFile()) {
               break;
            }

            file.commitClose(this.blockSize);
         } catch (IOException var7) {
            if (firstException == null) {
               firstException = new PersistentStoreException(var7);
            }
         }
      }

      try {
         this.dir.close();
      } catch (IOException var5) {
         firstException = new PersistentStoreException(var5);
      }

      this.closeInternal();
      if (firstException != null) {
         throw firstException;
      }
   }

   private void closeInternal() {
      if (this.allocator != null) {
         this.allocator.clear();
      }

      this.flushList.clear();
      this.cleanupHandleTracking();
      if (this.bufferPool != null) {
         this.bufferPool.close();
      }

      this.reinitFields();
   }

   private void reinitFields() {
      this.allocator = null;
      this.blockSize = 0;
      this.bufferPool = null;
      this.config = new HashMap();
      this.directZeroBuffer = null;
      this.extentBlocks = 0;
      this.heapVersion = 0;
      this.ht = null;
      this.ioSize = 0;
      this.locking = false;
      this.maxExtentBlocks = 0;
      this.maxFileSize = 1342177280L;
      this.maxMapSize = 0;
      this.minMapSize = 0;
      this.readLogRemaining = 0;
      this.recoveryBlock = 0;
      this.recoveryComplete = false;
      this.recoveryFileBlocks = 0;
      this.recoveryFileNum = 0;
      this.recoveryFilePos = 0L;
      this.singleHandleDirectIO = false;
      this.stats = null;
      this.supportOSDirectIO = false;
      this.uuidHi = 0L;
      this.uuidLo = 0L;
      this.uuidStr = null;
      this.writePolicy = null;
      this.tempDirPrefix = null;
      this.enforceExplicitIO = false;
   }

   private HeapRecord readRecord(HeapHeader heapHeader, ByteBuffer buf) throws PersistentStoreException {
      if (heapHeader.version != 5) {
         StoreDebug.storeIOPhysical.debug(">>>>> Reading record of version " + heapHeader.version);
      }

      int state;
      byte baseErrorNumber;
      if (heapHeader.version != 5 && heapHeader.version != 4) {
         if (heapHeader.version != 2) {
            throw new PersistentStoreException("Attempting to recover from an unsupported store version '" + heapHeader.version + "'. The supported store version is '" + 5 + "'.");
         }

         state = heapHeader.state;
         baseErrorNumber = 5;
      } else {
         state = 0;
         baseErrorNumber = 2;
      }

      assert heapHeader.bodyChecksum != -1L;

      try {
         if (heapHeader.bodyLength > 0) {
            ByteBuffer body = buf.slice();
            body.limit(heapHeader.bodyLength);
            long calculatedChecksum = this.readChecksummer.calculateChecksum(body);
            if (heapHeader.bodyChecksum != calculatedChecksum) {
               throw new PersistentStoreException(StoreLogger.logInvalidStoreRecordLoggable(baseErrorNumber));
            } else {
               return new HeapRecord(body, state);
            }
         } else if (heapHeader.bodyChecksum != 1L && heapHeader.bodyChecksum != 0L) {
            throw new PersistentStoreException(StoreLogger.logInvalidStoreRecordLoggable(baseErrorNumber + 1));
         } else {
            return new HeapRecord((ByteBuffer)null, state);
         }
      } catch (BufferUnderflowException var8) {
         throw new PersistentStoreException(StoreLogger.logInvalidStoreRecordLoggable(baseErrorNumber + 2));
      }
   }

   private String getStackTrace() {
      StringBuilder builder = new StringBuilder();
      StackTraceElement[] var2 = Thread.currentThread().getStackTrace();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         StackTraceElement element = var2[var4];
         builder.append(element.toString()).append('\n');
      }

      return builder.toString();
   }

   public String toString() {
      return this.allocator != null ? this.allocator.toString() : "null";
   }

   public int hashCode() {
      return this.hashCode;
   }

   private static short getPadding(int size, short alignment) {
      short padding = (short)(alignment - size % alignment);
      return padding == alignment ? 0 : padding;
   }

   static String handleToString(long handle) {
      StringBuffer buf = new StringBuffer(32);
      buf.append('[');
      buf.append(StoreHeap.handleToFileNum(handle));
      buf.append('.');
      buf.append(StoreHeap.handleToFileBlock(handle));
      buf.append('.');
      buf.append(StoreHeap.handleToNumBlocks(handle));
      buf.append(']');
      return buf.toString();
   }

   private synchronized void cleanupHandleTracking() {
      if (this.ht != null) {
         this.ht.clear();
      }

   }

   private synchronized void addHandle(long handle) {
      if (this.ht == null) {
         this.ht = new HashMap();
      }

      if (this.ht.get(handle) != null) {
         throw new AssertionError("Assertion: Duplicate handle " + handleToString(handle));
      } else {
         this.ht.put(handle, this.ht);
      }
   }

   private synchronized void removeHandle(long handle) {
      if (this.ht == null) {
         this.ht = new HashMap();
      }

      if (this.ht.get(handle) == null) {
         throw new AssertionError("Assertion: Unknown handle " + handleToString(handle));
      } else {
         this.ht.remove(handle);
      }
   }

   private synchronized void checkHandle(long handle) {
      if (this.ht == null) {
         this.ht = new HashMap();
      }

      if (this.ht.get(handle) == null) {
         throw new AssertionError("Assertion: Unknown handle " + handleToString(handle));
      }
   }

   public String getDirectoryName() {
      return this.dir.getDirName();
   }

   public boolean getSupportOSDirectIO() {
      return this.supportOSDirectIO;
   }

   private void rollbackReadMoveNextBlock() {
      ReadLogNode rln = (ReadLogNode)this.readLog.getFirst();
      int pos = rln.buf.position();

      assert pos + this.blockSize <= rln.buf.limit();

      rln.buf.position(pos + this.blockSize);
      if (!rln.buf.hasRemaining()) {
         this.readLog.removeFirst();
         this.bufferPool.put(rln.buf);
      }

      this.readLogRemaining -= this.blockSize;
   }

   private void commitRead(int blocks) {
      int targetRemaining = blocks * this.blockSize;
      this.readLogRemaining -= targetRemaining;
      Iterator iter = this.readLog.iterator();

      while(iter.hasNext() && targetRemaining > 0) {
         ReadLogNode rln = (ReadLogNode)iter.next();
         int chunk = Math.min(targetRemaining, rln.buf.remaining());
         targetRemaining -= chunk;
         int pos = rln.buf.position();
         rln.buf.position(pos + chunk);
         if (!rln.buf.hasRemaining()) {
            iter.remove();
            this.bufferPool.put(rln.buf);
         }
      }

   }

   private boolean verifyMagic(ByteBuffer buf) {
      return buf.getLong(0) == this.heapRecordMagic;
   }

   private static boolean byteBufferEqual(ByteBuffer a, ByteBuffer b) {
      if (a == b) {
         return true;
      } else if (a != null && b != null && a.remaining() == b.remaining()) {
         a = a.slice();
         b = b.slice();

         do {
            if (!a.hasRemaining()) {
               return true;
            }
         } while(a.get() == b.get());

         return false;
      } else {
         return false;
      }
   }

   private static int power2(String name, int val) {
      int p = Integer.highestOneBit(val);
      if (val != p) {
         StoreLogger.logSizeNotPowerOfTwo(name, val, p);
         return p;
      } else {
         return val;
      }
   }

   private static int adjustedBlockSize(String name, int val) {
      if (val >= 512 && val <= 8192) {
         return power2(name, val);
      } else {
         StoreLogger.logOutOfBlockSizeRange(name, val, 512, 8192, 512);
         return 512;
      }
   }

   private static int adjustedIOSize(String name, int val) {
      return val < 8192 ? 8192 : power2(name, val);
   }

   private static Integer getBlockSizeFromProperty(String name) {
      String blockSizeStr = System.getProperty(name);
      if (blockSizeStr != null) {
         try {
            return adjustedBlockSize(name, Integer.decode(blockSizeStr));
         } catch (NumberFormatException var3) {
            StoreLogger.logInvalidIntegerProperty(name, blockSizeStr, 512);
            return 512;
         }
      } else {
         return null;
      }
   }

   String getIOMode() {
      String ioMode;
      if (this.dir.numFiles() > 0) {
         ioMode = this.dir.get(0).getIOMode();
      } else if (this.writePolicy.synchronous()) {
         if (this.supportOSDirectIO) {
            if (this.singleHandleDirectIO) {
               ioMode = "single-handle-unbuffered";
            } else {
               ioMode = "read-buffered";
            }
         } else {
            ioMode = "single-handle-buffered";
         }
      } else {
         ioMode = "single-handle-non-direct";
      }

      return this.writePolicy + "(" + ioMode + ")";
   }

   String getDriver() {
      return this.baseStoreIO == null ? DirectIOManager.getFileMemoryManager().getDriver() : this.baseStoreIO.getDriver();
   }

   boolean getLocking() {
      Boolean lockingConfig = (Boolean)this.config.get("FileLockingEnabled");
      return lockingConfig == null ? true : lockingConfig;
   }

   private void updateStats(StoreFile file) throws IOException {
      if (this.stats != null && this.blockSize != 0 && this.isReplicatedStore) {
         if (!SKIP_SPACE_UPDATES) {
            int maximumMessageSizePercent = (Integer)this.config.get("MaximumMessageSizePercent");
            long max = this.maxFileSize * (long)maximumMessageSizePercent / 100L;
            this.stats.setMaximumWriteSize(max > 2147483647L ? Integer.MAX_VALUE : (int)max);
            file.updateStats(this.stats, this.maxFileSize);
            long extraBytes = 0L;
            if (this.dir.numFiles() > 0) {
               StoreFile lastFile = this.dir.get(this.dir.numFiles() - 1);
               extraBytes = this.maxFileSize - lastFile.size();
               if (extraBytes < 10485760L) {
                  extraBytes = 0L;
               }
            }

            long extraBlocks = extraBytes / (long)this.blockSize;
            long deleteRecordOnlyBlocks = 0L;
            if (this.baseStoreIO != null) {
               deleteRecordOnlyBlocks = this.baseStoreIO.getDeleteRecordOnlyBlocks();
            }

            this.allocator.updateStats(this.stats, extraBlocks, deleteRecordOnlyBlocks, this.blockSize);
         }
      }
   }

   void pollDevice() throws IOException {
      if (StoreHeap.DEBUG_SPACE_UPDATES && this.isReplicatedStore && StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StoreDebug.storeIOPhysical.debug("RS: " + this.storeName + " Heap: " + this.getName() + " pollDevice(): stats= " + this.stats + " blockSize = " + this.blockSize + " isReplicatedStore = " + this.isReplicatedStore + " SKIP_STORE_UPDATES = " + SKIP_SPACE_UPDATES);
      }

      if (this.stats != null && this.blockSize != 0 && this.isReplicatedStore && this.dir.numFiles() != 0) {
         if (!SKIP_SPACE_UPDATES) {
            StoreFile lastFile = this.dir.get(this.dir.numFiles() - 1);
            lastFile.updateStats(this.stats, this.maxFileSize);
         }
      }
   }

   BaseStoreIO getBaseStoreIO() {
      return this.baseStoreIO;
   }

   String getStringConfig(String key) {
      return (String)this.config.get(key);
   }

   void dumpStoreHeap() {
      this.allocator.dump();
   }

   private static long generateHeaderMagic() throws PersistentStoreException {
      Exception rtException = null;
      long hdrMagic = 0L;
      int retryCount = 0;

      try {
         do {
            hdrMagic = SecureRandomData.getInstance().getRandomLong();
         } while(hdrMagic == 0L && retryCount++ < 10);
      } catch (RuntimeException var5) {
         rtException = var5;
      }

      if (rtException == null && hdrMagic != 0L) {
         return hdrMagic;
      } else {
         if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder("header initialization error");
            if (hdrMagic == 0L) {
               sb.append("; retry max reached");
            }

            StoreDebug.storeIOPhysicalVerbose.debug(sb.toString(), rtException);
         }

         throw new PersistentStoreException(StoreLogger.logErrorInitializingStore(), rtException);
      }
   }

   static {
      OS_TMP_DIR = System.getProperty("java.io.tmpdir") + File.separator + "WLStoreCache";
      DEFAULT_REPLICATED_SUFFIX = "rgn".toUpperCase();
      DEFAULT_REGION_DIR = File.separator + "regions";
      DEFAULT_BLOCK_SIZE = getBlockSizeFromProperty("weblogic.store.BlockSize");
      DEFAULT_IO_SIZE = LARGE_DEFAULTS ? 8388608 : 1048576;
      DEFAULT_MIN_MAP_SIZE = LARGE_DEFAULTS ? 262144 : 65536;
      DEFAULT_MAX_MAP_SIZE = LARGE_DEFAULTS ? 268435456 : 4194304;
      GLOBAL_DIRECT_IO_MODE = System.getProperty("weblogic.store.DirectIOMode");
      GLOBAL_AVOID_DIRECT_IO = System.getProperty("weblogic.store.AvoidDirectIO");
      String enableZeroOutMagicOnRecover = System.getProperty("weblogic.store.EnableFileStoreCleanOnBoot");
      ENABLE_FILESTORE_CLEAN_ON_BOOT = enableZeroOutMagicOnRecover == null ? false : enableZeroOutMagicOnRecover.equalsIgnoreCase("true");
      SKIP_SPACE_UPDATES = Boolean.getBoolean("weblogic.store.SkipSpaceUpdates");
      if (SKIP_SPACE_UPDATES && StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StoreDebug.storeIOPhysical.debug(" *** skipping store space stat updates because -Dweblogic.store.SkipSpaceUpdates=true ***");
      }

      OLD_HEADER_LENGTH = 50;
      HEADER_LENGTH = 42;
   }

   private static class Utility {
      static byte[] longToBytes(long value) {
         byte[] bytes = new byte[]{(byte)((int)(value >>> 56)), (byte)((int)(value >>> 48)), (byte)((int)(value >>> 40)), (byte)((int)(value >>> 32)), (byte)((int)(value >>> 24)), (byte)((int)(value >>> 16)), (byte)((int)(value >>> 8)), (byte)((int)(value >>> 0))};
         return bytes;
      }
   }

   private class StoreFileCursor {
      private StoreFile storeFile;
      private long filePos;
      private long limit;
      private ByteBuffer directBuffer;
      private int lastHeaderOffset;

      StoreFileCursor(StoreFile storeFile, int startBlock, int numBlocks) throws PersistentStoreException {
         this.storeFile = storeFile;
         this.filePos = (long)(startBlock * Heap.this.blockSize);
         this.limit = this.filePos + (long)(numBlocks * Heap.this.blockSize);

         try {
            if (storeFile.mapped()) {
               this.directBuffer = this.getMappedBuffer(this.filePos, this.limit);
            } else {
               this.directBuffer = Heap.this.bufferPool.get();
            }
         } catch (IOException var6) {
            throw new PersistentStoreException(StoreLogger.logErrorWritingToStoreLoggable(), var6);
         }

         if (this.directBuffer == null) {
            throw new PersistentStoreException(StoreLogger.logCreateFailedLoggable());
         }
      }

      ByteBuffer getMappedBuffer(long start, long end) throws IOException {
         return start == end ? null : this.storeFile.getDirectMappedBuffer(start, Math.min(end, start + (long)Heap.this.ioSize));
      }

      void writeDataWithChecksumInTail(HeapHeader header, List record, short padding) throws IOException {
         this.appendHeader(header);
         long checksum = this.appendRecord(record, Heap.this.writeChecksummer);
         this.appendChecksum(checksum);
         this.blockAlignRecord(padding);
      }

      private void appendHeader(HeapHeader header) throws IOException {
         if (this.directBuffer.remaining() < Heap.HEADER_LENGTH) {
            assert this.directBuffer.remaining() == 0;

            this.writeToStore();
         }

         header.write(this.directBuffer);
         this.lastHeaderOffset = this.directBuffer.position();
      }

      private long appendRecord(List record, Checksummer checksummer) throws IOException {
         checksummer.reset();
         int startPos = this.directBuffer.position();
         Iterator var4 = record.iterator();

         while(var4.hasNext()) {
            ByteBuffer data = (ByteBuffer)var4.next();
            int dataLimit = data.limit();

            while(data.position() < dataLimit) {
               int chunkSize = Math.min(dataLimit - data.position(), this.directBuffer.remaining());
               data.limit(data.position() + chunkSize);
               this.directBuffer.put(data);
               if (!this.directBuffer.hasRemaining()) {
                  checksummer.update(this.directBuffer, startPos, this.directBuffer.position() - startPos);
                  this.writeToStore();
                  startPos = this.directBuffer.position();
               }
            }
         }

         if (this.directBuffer.position() != startPos) {
            checksummer.update(this.directBuffer, startPos, this.directBuffer.position() - startPos);
         }

         return checksummer.getValue();
      }

      private void appendChecksum(long checksum) throws IOException {
         int remainingBytes = this.directBuffer.remaining();
         if (remainingBytes < 8) {
            byte[] bytes = Heap.Utility.longToBytes(checksum);
            this.directBuffer.put(bytes, 0, remainingBytes);
            this.writeToStore();
            this.directBuffer.put(bytes, remainingBytes, 8 - remainingBytes);
         } else {
            this.directBuffer.putLong(checksum);
         }

      }

      private void blockAlignRecord(int paddingWidth) throws IOException {
         if (this.directBuffer.remaining() < paddingWidth) {
            this.writeToStore();
         }

         DirectIOManager.getFileMemoryManager().zeroBuffer(this.directBuffer, paddingWidth);
      }

      private void writeToStore() throws IOException {
         long newFilePos = this.filePos + (long)this.directBuffer.position();
         if (Heap.this.writeExplicit()) {
            this.directBuffer.flip();
            this.storeFile.write(this.filePos, this.directBuffer);
         }

         this.filePos = newFilePos;
         if (this.storeFile.mapped()) {
            this.directBuffer = this.getMappedBuffer(this.filePos, this.limit);
         } else {
            this.directBuffer.clear();
         }

      }

      private void finalWrite() throws IOException {
         if (Heap.this.writeExplicit() && this.dataLeftToBeWritten()) {
            this.directBuffer.flip();
            int numBytesToWrite = this.directBuffer.remaining();
            this.storeFile.write(this.filePos, this.directBuffer);
            this.filePos += (long)numBytesToWrite;
         }

      }

      private void realeaseBuffer() {
         if (!this.storeFile.mapped() && this.directBuffer != null) {
            Heap.this.bufferPool.put(this.directBuffer);
         }

      }

      private boolean dataLeftToBeWritten() {
         return this.filePos < this.limit;
      }
   }

   static final class HeapFileHeader {
      private static final long MAGIC = -4611194893197503948L;
      final boolean magicVerified;
      final short version;
      final int blockSize;
      final long uuidLo;
      final long uuidHi;
      final ByteBuffer signature;
      final long heapHeaderMagic;

      HeapFileHeader(short version, int blockSize, long uuidLo, long uuidHi, long heapHeaderMagic) {
         this.version = version;
         this.blockSize = blockSize;
         this.uuidLo = uuidLo;
         this.uuidHi = uuidHi;
         this.magicVerified = true;
         this.signature = null;
         this.heapHeaderMagic = heapHeaderMagic;
      }

      HeapFileHeader(ByteBuffer buf) {
         this.magicVerified = buf.getLong() == -4611194893197503948L;
         if (this.magicVerified) {
            this.version = buf.getShort();
            this.blockSize = buf.getInt();
            this.uuidLo = buf.getLong();
            this.uuidHi = buf.getLong();
            int oldLimit = buf.limit();
            buf.limit(buf.position() + 64);
            this.signature = buf.slice();
            if (this.version == 3) {
               buf.limit(oldLimit);
               buf.position(94);
               this.heapHeaderMagic = buf.getLong();
            } else {
               this.heapHeaderMagic = 1370321247807281150L;
            }
         } else {
            this.version = 1;
            this.blockSize = 256;
            this.uuidLo = 0L;
            this.uuidHi = 0L;
            this.signature = null;
            this.heapHeaderMagic = 0L;
         }

      }

      ByteBuffer getBuffer() {
         ByteBuffer buf = ByteBuffer.allocate(this.blockSize);
         this.serialize(buf);
         return buf;
      }

      void serialize(ByteBuffer dest) {
         dest.putLong(-4611194893197503948L);
         dest.putShort(this.version);
         dest.putInt(this.blockSize);
         dest.putLong(this.uuidLo);
         dest.putLong(this.uuidHi);
         if (dest.isDirect()) {
            DirectIOManager.getFileMemoryManager().zeroBuffer(dest);
         }

         if (this.version == 3) {
            dest.position(94);
            dest.putLong(this.heapHeaderMagic);
         }

         dest.limit(this.blockSize);
         dest.position(0);
      }

      boolean signatureZero() {
         if (this.signature == null) {
            return true;
         } else {
            for(int i = 0; i < 64; ++i) {
               if (this.signature.get(i) != 0) {
                  return false;
               }
            }

            return true;
         }
      }

      boolean equalsTo(HeapFileHeader other) {
         return this.magicVerified == other.magicVerified && this.version == other.version && this.blockSize == other.blockSize && this.uuidLo == other.uuidLo && this.uuidHi == other.uuidHi && Heap.byteBufferEqual(this.signature, other.signature) && (this.version != 3 || this.heapHeaderMagic == other.heapHeaderMagic);
      }

      public String toString() {
         return this.getClass().getName() + "[version=" + this.version + " blockSize=" + this.blockSize + " uuidLo=" + this.uuidLo + " uuidHi=" + this.uuidHi + " zeroSignature=" + this.signatureZero() + "]";
      }
   }

   final class HeapHeader {
      private int state;
      private long headerChecksum;
      private final byte version;
      private int totalLength;
      private long bodyChecksum;
      private int bodyLength;

      HeapHeader(ByteBuffer buf) throws PersistentStoreException {
         int startPos = buf.position();
         buf.position(startPos + 12);
         this.headerChecksum = buf.getLong();
         this.version = buf.get();
         this.totalLength = buf.getInt();
         this.bodyChecksum = buf.getLong();
         this.bodyLength = this.totalLength - Heap.OLD_HEADER_LENGTH;
         if (this.isChecksumInTail()) {
            if (startPos + Heap.HEADER_LENGTH + this.bodyLength + 8 <= buf.limit()) {
               this.bodyChecksum = buf.getLong(startPos + Heap.HEADER_LENGTH + this.bodyLength);

               assert buf.position() - startPos <= Heap.HEADER_LENGTH;

               buf.position(startPos + Heap.HEADER_LENGTH);
               if (this.headerChecksum != (long)(this.version + this.totalLength)) {
                  throw new PersistentStoreException(StoreLogger.logInvalidStoreRecordLoggable(30));
               }
            }
         } else {
            assert buf.position() - startPos <= Heap.OLD_HEADER_LENGTH;

            buf.position(startPos + Heap.OLD_HEADER_LENGTH);
            if (this.headerChecksum != (long)(this.version + this.totalLength) + this.bodyChecksum) {
               throw new PersistentStoreException(StoreLogger.logInvalidStoreRecordLoggable(30));
            }
         }

         if (this.version == 2) {
            this.state = buf.getInt(8) - -123456789;
         } else if (this.version != 5 && this.version != 4) {
            throw new PersistentStoreException(StoreLogger.logInvalidStoreRecordVersionLoggable(this.version));
         }

      }

      boolean isChecksumInTail() {
         return this.version == 5;
      }

      HeapHeader(List record) {
         this.totalLength = Heap.HEADER_LENGTH;

         ByteBuffer data;
         for(Iterator var3 = record.iterator(); var3.hasNext(); this.bodyLength += data.remaining()) {
            data = (ByteBuffer)var3.next();
         }

         this.totalLength += this.bodyLength;
         this.totalLength += 8;
         this.version = 5;
         this.headerChecksum = (long)(this.version + this.totalLength);
         this.bodyChecksum = -1L;
      }

      void write(ByteBuffer destBuffer) {
         int startPosition = destBuffer.position();
         destBuffer.putLong(Heap.this.heapRecordMagic);
         destBuffer.putInt(0);
         destBuffer.putLong(this.headerChecksum);
         destBuffer.put(this.version);
         destBuffer.putInt(this.totalLength);
         destBuffer.putLong(-1L);
         DirectIOManager.getFileMemoryManager().zeroBuffer(destBuffer, Heap.HEADER_LENGTH - (destBuffer.position() - startPosition));
      }

      int getNumBlocks(int blockSize) {
         int ret = this.totalLength / blockSize;
         if (this.totalLength % blockSize != 0) {
            ++ret;
         }

         return ret;
      }

      public String toString() {
         StringBuffer buf = new StringBuffer();
         buf.append("HeapHeader [ version = ");
         buf.append(this.version);
         buf.append(" total length = ");
         buf.append(this.totalLength);
         buf.append(" body checksum = ");
         buf.append(this.bodyChecksum);
         buf.append(" header checksum = ");
         buf.append(this.headerChecksum);
         buf.append(" ]");
         return buf.toString();
      }
   }

   public static final class HeapRecord {
      private long handle;
      private final ByteBuffer body;
      private final int oldState;

      private HeapRecord(ByteBuffer body, int oldState) {
         this.body = body;
         this.oldState = oldState;
      }

      void setHandle(long handle) {
         this.handle = handle;
      }

      public long getHandle() {
         return this.handle;
      }

      public ByteBuffer getBody() {
         return this.body;
      }

      public int getOldState() {
         return this.oldState;
      }

      // $FF: synthetic method
      HeapRecord(ByteBuffer x0, int x1, Object x2) {
         this(x0, x1);
      }
   }

   private static final class ReadLogNode {
      private final ByteBuffer buf;

      private ReadLogNode(ByteBuffer buf) {
         this.buf = buf;
      }

      // $FF: synthetic method
      ReadLogNode(ByteBuffer x0, Object x1) {
         this(x0);
      }
   }
}
