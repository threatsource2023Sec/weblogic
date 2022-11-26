package weblogic.store.io.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreFatalException;
import weblogic.store.PersistentStoreRuntimeException;
import weblogic.store.PersistentStoreTestException;
import weblogic.store.StoreLogger;
import weblogic.store.StoreWritePolicy;
import weblogic.store.common.StoreDebug;
import weblogic.store.internal.StoreStatisticsImpl;
import weblogic.store.io.IOListener;
import weblogic.store.io.IORecord;
import weblogic.store.io.PersistentStoreIO;
import weblogic.store.io.file.direct.DirectIOManager;
import weblogic.utils.Hex;
import weblogic.utils.collections.CircularQueue;

public abstract class BaseStoreIO implements PersistentStoreIO {
   private static final int STORED_TEST_EXCEPTION_TYPE = Integer.MIN_VALUE;
   protected static final int DROP_BATCH_SIZE = 20;
   protected boolean opened;
   protected boolean fatalError;
   protected final CircularQueue deferredFrees = new CircularQueue();
   final Heap heap;
   int blockSize;
   StoreWritePolicy writePolicy;
   protected final HashMap typeTable = new HashMap();
   protected final CircularQueue pendingOperations = new CircularQueue();
   protected final DirectIOManager directIOManager;
   protected long currentGeneration = -1L;
   protected PersistentStoreException testStoreException = null;
   private long deleteRecordOnlyBlocks;
   protected static final int FLUSH_HEADER_LENGTH = 10;

   protected BaseStoreIO(DirectIOManager directIOManager, String filePrefix, String dirName, boolean autoCreateDir, boolean isReplicatedStore) throws PersistentStoreException {
      this.directIOManager = directIOManager;
      this.heap = new Heap(this, directIOManager, filePrefix, dirName, "dat", autoCreateDir, isReplicatedStore);
   }

   public boolean exists(Map config) throws PersistentStoreException {
      boolean itExists = this.opened;
      if (!itExists) {
         if (config == null) {
            throw new PersistentStoreException("Missing config");
         }

         StoreDir tStoreDir;
         if (this.heap.isReplicatedStore) {
            boolean enableReplicatedStoreExistenceCheck = Boolean.parseBoolean(System.getProperty("weblogic.store.EnableReplicatedStoreExistenceCheck", "false"));
            if (!enableReplicatedStoreExistenceCheck) {
               throw new PersistentStoreException("Checking for the existence of ReplicatedStores is not supported.");
            }

            tStoreDir = new StoreDir(this.heap, this.heap.getDirName(), this.heap.computeRegionName(config), this.heap.getSuffix());
         } else {
            tStoreDir = new StoreDir(this.heap, this.heap.getDirName(), this.heap.getName(), this.heap.getSuffix());
         }

         try {
            File[] f = this.listRegionsOrFiles(this.heap, new File(this.heap.getDirName()), tStoreDir);
            itExists = f != null && f.length > 0;
         } catch (IOException var5) {
            throw new PersistentStoreException(var5);
         }
      }

      return itExists;
   }

   public boolean supportsFastReads() {
      return true;
   }

   public boolean supportsAsyncIO() {
      return false;
   }

   public boolean isIdle() {
      return true;
   }

   public int getPreferredFlushLoadSize() {
      return Integer.MAX_VALUE;
   }

   public int getWorkerCount() {
      return 1;
   }

   /** @deprecated */
   @Deprecated
   public int open(StoreWritePolicy wp, int ignored) throws PersistentStoreException {
      HashMap config = new HashMap();
      config.put("SynchronousWritePolicy", wp);
      return this.open(config);
   }

   int openInternal(HashMap config) throws PersistentStoreException {
      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StoreDebug.storeIOPhysical.debug("BaseStoreIO.openInternal(): store: " + this.heap.getName());
      }

      this.fatalError = false;
      this.writePolicy = (StoreWritePolicy)config.get("SynchronousWritePolicy");
      this.heap.setSynchronousWritePolicy(this.writePolicy);
      this.heap.setConfig(config);
      PersistentStoreException pse = null;
      boolean open = false;

      int result;
      try {
         this.heap.open();
         open = true;
         result = this.heap.getHeapVersion();
         if (result != 2 && result != 3) {
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               StoreDebug.storeIOPhysical.debug("BaseStoreIO.openInternal(): incorrect version number: " + result);
            }

            if (this.heap.isReplicatedStore) {
               pse = new PersistentStoreFatalException(StoreLogger.logInvalidRegionVersionLoggable(this.heap.getName(), this.heap.getRegionName(), this.heap.getHeapVersion(), 3));
            } else {
               pse = new PersistentStoreFatalException(StoreLogger.logInvalidFileVersionLoggable(this.heap.getName(), this.heap.getHeapVersion(), 3));
            }
         } else {
            this.recover();
         }

         this.blockSize = this.heap.getBlockSize() - 10;
         this.opened = true;
         this.checkSavedStoreException();
      } catch (Throwable var6) {
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StoreDebug.storeIOPhysical.debug(var6.getLocalizedMessage(), var6);
         }

         if (var6 instanceof PersistentStoreException) {
            pse = (PersistentStoreException)var6;
         } else {
            pse = new PersistentStoreException(var6);
         }
      }

      if (pse != null) {
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StoreDebug.storeIOPhysical.debug("BaseStoreIO.openInternal(): Setting fatalError: " + ((PersistentStoreException)pse).getLocalizedMessage(), (Throwable)pse);
         }

         this.fatalError = true;

         try {
            if (open) {
               this.close();
            }
         } catch (PersistentStoreException var5) {
            StoreLogger.logStoreShutdownFailed(this.heap.getName(), "Close failed after recovery or version checking failure. Check the next store message in the log", var5);
         }

         throw pse;
      } else {
         result = this.getNumObjects(-1, true);
         if (this.heap.isReplicatedStore) {
            StoreLogger.logReplicatedStoreOpened(this.heap.getName(), this.heap.getRegionName(), this.heap.uuidStr, this.heap.getInternalBlockSize(), result);
         } else {
            StoreLogger.logPersistentStoreOpened(this.heap.getName(), this.heap.uuidStr, this.heap.getInternalBlockSize(), this.heap.getIOMode(), this.heap.enforceExplicitIO, result);
         }

         return result;
      }
   }

   private void checkSavedStoreException() throws PersistentStoreException {
      PersistentStoreTestException testException = this.readStoreTestException();
      PersistentStoreTestException testExceptionToBeSaved = null;
      if (testException != null) {
         try {
            testException = this.readStoreTestException();
            Date failUntilDate = testException.getBootFailureUntil();
            int failCount = testException.getBootFailureCount();
            if (failUntilDate != null) {
               Date now = new Date();
               if (now.before(failUntilDate)) {
                  testExceptionToBeSaved = new PersistentStoreTestException(testException.getMessage());
                  testExceptionToBeSaved.clearFailOnFlush();
                  testExceptionToBeSaved.setBootFailureUntil(failUntilDate);
                  if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
                     StoreDebug.storeIOPhysical.debug("BaseStoreIO.checkSavedStoreException(): throwing based upon date; testException: " + testException);
                  }

                  throw new PersistentStoreFatalException(testException);
               }
            }

            if (failCount > 0) {
               --failCount;
               if (failCount > 0) {
                  testExceptionToBeSaved = new PersistentStoreTestException(testException.getMessage());
                  testExceptionToBeSaved.clearFailOnFlush();
                  testExceptionToBeSaved.setBootFailureCount(failCount);
               }

               if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
                  StoreDebug.storeIOPhysical.debug("BaseStoreIO.checkSavedStoreException(): throwing based upon count; testException: " + testException);
               }

               throw new PersistentStoreFatalException(testException);
            }
         } finally {
            if (testExceptionToBeSaved != null) {
               this.saveStoreTestException(testExceptionToBeSaved);
            } else {
               this.deleteStoreTestException();
            }

         }
      }

   }

   public abstract int open(HashMap var1) throws PersistentStoreException;

   abstract HashMap adjustConfig(HashMap var1);

   abstract FileChannel fileChannelFactory(Map var1, File var2, String var3, boolean var4) throws IOException;

   protected TypeRecord findTypeRecord(int typeCode) {
      synchronized(this.typeTable) {
         return (TypeRecord)this.typeTable.get(typeCode);
      }
   }

   TypeRecord getTypeRecord(int typeCode) throws PersistentStoreException {
      TypeRecord ret = this.findTypeRecord(typeCode);
      if (ret == null) {
         throw new PersistentStoreException(StoreLogger.logInvalidRecordHandleLoggable((long)typeCode));
      } else {
         return ret;
      }
   }

   protected TypeRecord findOrCreateTypeRecord(int typeCode) {
      synchronized(this.typeTable) {
         TypeRecord typeRec = (TypeRecord)this.typeTable.get(typeCode);
         if (typeRec == null) {
            typeRec = new TypeRecord(typeCode);
            this.typeTable.put(typeCode, typeRec);
         }

         return typeRec;
      }
   }

   protected Collection getAllTypeRecords() {
      synchronized(this.typeTable) {
         ArrayList ret = new ArrayList(this.typeTable.values());
         return ret;
      }
   }

   public void create(int handle, int typeCode, ByteBuffer[] data, int flags) throws PersistentStoreException {
      this.checkOpened();
      TypeRecord typeRec = this.getTypeRecord(typeCode);
      Record oldRec = typeRec.getSlot(handle, false);
      if (oldRec == null) {
         throw new PersistentStoreException(StoreLogger.logInvalidRecordHandleLoggable((long)handle));
      } else {
         if (oldRec.handle != null) {
            this.freeHandleLater(oldRec.handle);
         }

         CreateRecord rec = new CreateRecord((Handle)null, handle);
         typeRec.setSlot(handle, rec);
         Operation op = new Operation(handle, typeCode, data);
         op.record = rec;
         this.pendingOperations.add(op);
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StoreDebug.storeIOPhysical.debug("Create: typeCode = " + typeCode + " handle = " + handle + " operation = " + op);
         }

      }
   }

   public IORecord read(int handle, int typeCode) throws PersistentStoreException {
      this.checkOpened();
      TypeRecord typeRec = this.getTypeRecord(typeCode);
      return this.readInternal(handle, typeRec, false);
   }

   protected IORecord readInternal(int handle, TypeRecord typeRec, boolean allowSkips) throws PersistentStoreException {
      Record rec = null;

      try {
         rec = typeRec.getSlot(handle, true);
      } catch (PersistentStoreException var8) {
         if (!allowSkips) {
            throw var8;
         }
      }

      if (rec != null && rec.handle != null) {
         Heap.HeapRecord heapRec = this.heap.read(rec.handle.handle);
         heapRec.getBody().position(10);
         Operation create = BaseStoreIO.Operation.read(heapRec.getBody(), handle, typeRec.getTypeCode());

         assert create.typeCode == typeRec.getTypeCode();

         ByteBuffer data = create.data != null ? create.data[0] : null;
         return new IORecord(handle, create.typeCode, data);
      } else {
         throw new PersistentStoreException(StoreLogger.logStoreRecordNotFoundLoggable((long)handle), new PersistentStoreRuntimeException(StoreLogger.logStoreRecordNotFoundLoggable((long)handle)));
      }
   }

   public void update(int handle, int typeCode, ByteBuffer[] data, int flags) throws PersistentStoreException {
      this.create(handle, typeCode, data, flags);
   }

   public void delete(int handle, int typeCode, int flags) throws PersistentStoreException {
      this.checkOpened();
      TypeRecord typeRec = this.getTypeRecord(typeCode);
      this.deleteInternal(handle, typeRec);
   }

   public int drop(int typeCode) throws PersistentStoreException {
      this.checkOpened();
      TypeRecord typeRec = this.findTypeRecord(typeCode);
      if (typeRec == null) {
         return 0;
      } else {
         Collection recs = typeRec.getRecords();
         Iterator i = recs.iterator();

         int dropCount;
         for(dropCount = 0; i.hasNext() && dropCount < 20; ++dropCount) {
            CreateRecord rec = (CreateRecord)i.next();
            this.deleteInternal(rec.getSlotNum(), typeRec);
         }

         return dropCount;
      }
   }

   public int allocateHandle(int typeCode) {
      TypeRecord typeRec = this.findOrCreateTypeRecord(typeCode);
      int ret = typeRec.allocateSlot();
      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StoreDebug.storeIOPhysical.debug("Allocate typeCode = " + typeCode + " handle = " + ret);
      }

      return ret;
   }

   public void ensureHandleAllocated(int typeCode, int handle) {
      TypeRecord typeRec = this.findOrCreateTypeRecord(typeCode);
      typeRec.setSlot(handle, new DeleteRecord((Handle)null, handle));
   }

   public void releaseHandle(int typeCode, int handle) {
      TypeRecord typeRec = this.findTypeRecord(typeCode);
      if (typeRec != null) {
         typeRec.undoAllocateSlot(handle);
      }

   }

   public boolean isHandleReadable(int typeCode, int handle) {
      TypeRecord typeRec = this.findTypeRecord(typeCode);
      return typeRec != null && typeRec.containsCreateRecord(handle);
   }

   public int getNumObjects(int typeCode) throws PersistentStoreException {
      return this.getNumObjects(typeCode, false);
   }

   protected int getNumObjects(int typeCode, boolean countAll) throws PersistentStoreException {
      this.checkOpened();
      if (!countAll) {
         TypeRecord typeRec = this.findTypeRecord(typeCode);
         return typeRec == null ? 0 : typeRec.getSize();
      } else {
         Collection typeRecs = this.getAllTypeRecords();
         int count = 0;

         TypeRecord typeRec;
         for(Iterator i = typeRecs.iterator(); i.hasNext(); count += typeRec.getSize()) {
            typeRec = (TypeRecord)i.next();
         }

         return count;
      }
   }

   protected void deleteInternal(int handle, TypeRecord typeRec) throws PersistentStoreException {
      Record oldRec = typeRec.getSlot(handle, true);
      if (oldRec.handle != null) {
         this.freeHandleLater(oldRec.handle);
      }

      DeleteRecord deleteRecord = new DeleteRecord((Handle)null, handle);
      typeRec.freeSlot(handle, deleteRecord);
      if (this.writePolicy != StoreWritePolicy.NON_DURABLE) {
         Operation op = new Operation(handle, typeRec.getTypeCode());
         op.record = deleteRecord;
         this.pendingOperations.add(op);
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StoreDebug.storeIOPhysical.debug("Delete typeCode = " + typeRec.getTypeCode() + " handle = " + handle + " operation = " + op);
         }
      }

   }

   protected boolean flushListHasDuplicates(Operation[] ops) {
      Arrays.sort(ops, BaseStoreIO.OperationHandleComparator.THE_ONE);
      Operation lastOp = ops[0];

      for(int inc = 1; inc < ops.length; ++inc) {
         if (lastOp.typeCode == ops[inc].typeCode && lastOp.slotNum == ops[inc].slotNum) {
            throw new AssertionError("Duplicate operations: " + lastOp + " == " + ops[inc]);
         }

         lastOp = ops[inc];
      }

      return false;
   }

   public void flush() throws PersistentStoreException {
      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StoreDebug.storeIOPhysical.debug("BaseStoreIO.flush(): store: " + this.heap.getName());
      }

      this.checkOpened();
      if (this.testStoreException != null) {
         boolean isFatalFlushException = false;
         PersistentStoreException flushException = this.testStoreException;
         this.testStoreException = null;
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StoreDebug.storeIOPhysical.debug("BaseStoreIO.flush(): processing exception; flushException: " + flushException);
         }

         if (flushException instanceof PersistentStoreTestException) {
            PersistentStoreTestException testException = (PersistentStoreTestException)flushException;
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               StoreDebug.storeIOPhysical.debug("BaseStoreIO.flush(): processing exception; testException: " + testException);
            }

            if (testException.shouldFailOnBoot()) {
               this.pendingOperations.clear();
               this.saveStoreTestException(testException);
            }

            if (!testException.shouldFailOnFlush()) {
               flushException = null;
            } else {
               isFatalFlushException = testException.isFatalFailure();
            }
         } else {
            isFatalFlushException = flushException instanceof PersistentStoreFatalException;
         }

         if (flushException != null) {
            String logMessage;
            if (isFatalFlushException) {
               this.fatalError = true;
               logMessage = "test fatal exception";
            } else {
               logMessage = "test non-fatal exception";
            }

            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               StoreDebug.storeIOPhysical.debug(logMessage, flushException);
            }

            this.pendingOperations.clear();
            throw flushException;
         }
      }

      if (this.pendingOperations.isEmpty()) {
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StoreDebug.storeIOPhysical.debug("BaseStoreIO.flush(): store: " + this.heap.getName() + ": No pending operations; returning");
         }

      } else {
         long generation = this.nextGenerationNumber();
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StoreDebug.storeIOPhysical.debug("Flushing " + this.pendingOperations.size() + " operations for generation " + generation);
         }

         Operation[] opList = new Operation[this.pendingOperations.size()];

         for(int inc = 0; inc < opList.length; ++inc) {
            opList[inc] = (Operation)this.pendingOperations.remove();
         }

         assert this.pendingOperations.isEmpty();

         assert !this.flushListHasDuplicates(opList);

         Arrays.sort(opList, BaseStoreIO.OperationSizeComparator.THE_ONE);
         ArrayList recList = new ArrayList(opList.length);
         ArrayList bufList = null;
         int remainingBytes = this.blockSize;
         int lastDeleteOpIndex = -1;
         HashSet deleteRecordOnlyHandleIndicies = new HashSet(opList.length);
         int numCreateOps = 0;

         for(int i = 0; i < opList.length; ++i) {
            boolean prependFlushHeader = false;
            if (bufList == null) {
               bufList = new ArrayList();
               prependFlushHeader = true;
            } else if (opList[i].length() > remainingBytes) {
               recList.add(bufList);
               bufList = new ArrayList();
               prependFlushHeader = true;
               remainingBytes = this.blockSize;
            }

            opList[i].write(bufList, prependFlushHeader, generation, (short)opList.length);
            opList[i].handleIndex = recList.size();
            remainingBytes -= opList[i].length();
            if (lastDeleteOpIndex != -1 && opList[i].handleIndex != opList[lastDeleteOpIndex].handleIndex) {
               this.incrementDeleteRecordOnlyBlocks();
               deleteRecordOnlyHandleIndicies.add(opList[lastDeleteOpIndex].handleIndex);
               lastDeleteOpIndex = -1;
            }

            if (opList[i].opType == 2) {
               if (prependFlushHeader) {
                  lastDeleteOpIndex = i;
               }
            } else {
               lastDeleteOpIndex = -1;
            }

            if (opList[i].opType == 1) {
               ++numCreateOps;
            }
         }

         if (this.heap.getStats() != null) {
            this.heap.getStats().recordBatchSize(numCreateOps);
         }

         if (lastDeleteOpIndex != -1) {
            this.incrementDeleteRecordOnlyBlocks();
            deleteRecordOnlyHandleIndicies.add(opList[lastDeleteOpIndex].handleIndex);
            int lastDeleteOpIndex = true;
         }

         recList.add(bufList);

         long[] handles;
         try {
            handles = this.heap.multiWrite(recList);
         } catch (PersistentStoreException var14) {
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               StoreDebug.storeIOPhysical.debug("BaseStoreIO.flush(): store: " + this.heap.getName() + " Setting fatalError: " + var14.getMessage(), var14);
            }

            this.fatalError = true;
            throw new PersistentStoreFatalException(var14);
         }

         Handle[] handleObjs = new Handle[opList.length];

         int i;
         for(i = 0; i < handles.length; ++i) {
            handleObjs[i] = new Handle(handles[i]);
            if (deleteRecordOnlyHandleIndicies.contains(i)) {
               handleObjs[i].setDeleteOnly();
            }
         }

         for(i = 0; i < opList.length; ++i) {
            Operation o = opList[i];
            o.record.updateHandle(handleObjs[o.handleIndex]);
            handleObjs[o.handleIndex].incrementRefCount();
         }

         this.freeDeferredHandles();
      }
   }

   private void saveStoreTestException(PersistentStoreTestException testException) throws PersistentStoreException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StoreDebug.storeIOPhysical.debug("BaseStoreIO.saveStoreTestException(): saving exceptiont; testException: " + testException);
      }

      try {
         ObjectOutputStream oos = new ObjectOutputStream(baos);
         oos.writeObject(testException);
         oos.flush();
         oos.close();
         baos.flush();
         byte[] baosBytes = baos.toByteArray();
         ByteBuffer[] bba = new ByteBuffer[]{ByteBuffer.allocate(baosBytes.length)};
         bba[0].put(baosBytes);
         bba[0].flip();
         TypeRecord expTypeRec = this.findOrCreateTypeRecord(Integer.MIN_VALUE);
         Record rec = expTypeRec.getSlot(0, false);
         if (rec == null) {
            this.ensureHandleAllocated(Integer.MIN_VALUE, 0);
         }

         this.create(0, Integer.MIN_VALUE, bba, 0);
         this.flush();
      } catch (Throwable var8) {
         throw new PersistentStoreException("saveStoreTestException failed", var8);
      }
   }

   private PersistentStoreTestException readStoreTestException() throws PersistentStoreException {
      PersistentStoreTestException testException = null;

      try {
         TypeRecord expTypeRec = this.findOrCreateTypeRecord(Integer.MIN_VALUE);
         Record slot = expTypeRec.getSlot(0, false);
         if (slot != null && !(slot instanceof DeleteRecord) && slot.handle != null) {
            IORecord rec = this.readInternal(0, expTypeRec, true);
            ByteBuffer bb = rec.getData();
            if (bb != null && bb.limit() > 0) {
               byte[] ba = new byte[bb.limit()];
               bb.get(ba, 0, ba.length);
               ByteArrayInputStream bais = new ByteArrayInputStream(ba);
               ObjectInputStream ois = new ObjectInputStream(bais);
               testException = (PersistentStoreTestException)ois.readObject();
            }
         }
      } catch (PersistentStoreException var9) {
         throw var9;
      } catch (Throwable var10) {
         throw new PersistentStoreException("readStoreTestException failed", var10);
      }

      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StoreDebug.storeIOPhysical.debug("BaseStoreIO.readStoreTestException(): testException: " + testException);
      }

      return testException;
   }

   private void deleteStoreTestException() throws PersistentStoreException {
      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StoreDebug.storeIOPhysical.debug("BaseStoreIO.deleteStoreTestException(): removing any saved test exception");
      }

      TypeRecord expTypeRec = this.findOrCreateTypeRecord(Integer.MIN_VALUE);
      this.deleteInternal(0, expTypeRec);
      this.flush();
   }

   public abstract void flush(IOListener var1) throws PersistentStoreException;

   public PersistentStoreIO.Cursor createCursor(int typeCode, int flags) throws PersistentStoreException {
      this.checkOpened();
      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StoreDebug.storeIOPhysical.debug("Creating cursor for " + typeCode);
      }

      if (typeCode >= 0) {
         ArrayList codes = new ArrayList(1);
         TypeRecord typeRec = this.findTypeRecord(typeCode);
         if (typeRec != null) {
            codes.add(typeRec);
         }

         return new Cursor(codes, flags);
      } else {
         Collection codes = this.getAllTypeRecords();
         return new Cursor(codes, flags);
      }
   }

   public void setStats(StoreStatisticsImpl statistics) {
      this.heap.setStats(statistics);
   }

   public void prepareToClose() {
   }

   synchronized long getDeleteRecordOnlyBlocks() {
      return this.deleteRecordOnlyBlocks;
   }

   synchronized void incrementDeleteRecordOnlyBlocks() {
      ++this.deleteRecordOnlyBlocks;
   }

   synchronized void decrementDeleteRecordOnlyBlocks() {
      --this.deleteRecordOnlyBlocks;
      if (this.deleteRecordOnlyBlocks < 0L) {
         this.deleteRecordOnlyBlocks = 0L;
      }

   }

   protected void recover() throws PersistentStoreException {
      CircularQueue heapRecords = new CircularQueue();
      boolean storeIOPhysicalDebugEnabled = StoreDebug.storeIOPhysical.isDebugEnabled();
      HashMap handleRecords = new HashMap();

      Heap.HeapRecord heapRec;
      do {
         heapRec = this.heap.recover();
         if (heapRec != null) {
            ByteBuffer body = heapRec.getBody();
            if (body.remaining() < 10) {
               throw new PersistentStoreException(StoreLogger.logInvalidStoreRecordLoggable(20));
            }

            long generation = body.getLong();
            short numOps = body.getShort();
            Handle handle = new Handle(heapRec.getHandle());
            if (storeIOPhysicalDebugEnabled) {
               StoreDebug.storeIOPhysical.debug("Read heap record " + heapRec.getHandle());
            }

            Operation newOp;
            while(body.remaining() > 0) {
               newOp = BaseStoreIO.Operation.read(body, false);
               newOp.generationNumber = generation;
               newOp.numOps = numOps;
               newOp.handle = handle;
               handle.incrementRefCount();
               if (storeIOPhysicalDebugEnabled) {
                  StoreDebug.storeIOPhysical.debug("Reading " + newOp);
               }

               if (generation > this.currentGeneration) {
                  this.currentGeneration = generation;
                  this.recoverPendingOperations();
               }

               if (generation == this.currentGeneration) {
                  this.pendingOperations.add(newOp);
               } else {
                  heapRecords.add(newOp);
               }
            }

            while((newOp = (Operation)heapRecords.remove()) != null) {
               this.recoverOperation(newOp);
            }
         }
      } while(heapRec != null);

      this.verifyLastTransaction();
      ++this.currentGeneration;
      Collection typeRecs = this.getAllTypeRecords();
      Iterator i = typeRecs.iterator();

      while(i.hasNext()) {
         TypeRecord typeRec = (TypeRecord)i.next();
         typeRec.freeRecoverySlots();
         typeRec.rebuildDeletedSlots();
         if (this.heap.isReplicatedStore) {
            typeRec.checkDeleteRecordOnlyHandles(handleRecords);
         }
      }

      if (this.heap.isReplicatedStore) {
         if (StoreHeap.DEBUG_SPACE_UPDATES) {
            System.out.println("Got totally " + handleRecords.size() + " handles on recovery");
         }

         this.processDeleteRecordOnlyHandles(handleRecords);
         if (StoreHeap.DEBUG_SPACE_UPDATES) {
            this.heap.dumpStoreHeap();
         }
      }

      handleRecords = null;
   }

   protected void recoverPendingOperations() throws PersistentStoreException {
      Operation op;
      while((op = (Operation)this.pendingOperations.remove()) != null) {
         this.recoverOperation(op);
      }

   }

   protected void zeroOutOperations() {
      Operation op;
      while((op = (Operation)this.pendingOperations.remove()) != null) {
         try {
            if (op.handle.decrementRefCount() <= 0) {
               this.heap.delete(op.handle.handle);
               if (op.handle.isDeleteOnly()) {
                  this.decrementDeleteRecordOnlyBlocks();
               }
            }
         } catch (PersistentStoreException var3) {
         }
      }

      try {
         this.heap.flush();
      } catch (PersistentStoreException var4) {
      }

   }

   protected void recoverOperation(Operation o) throws PersistentStoreException {
      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StoreDebug.storeIOPhysical.debug("Recovering operation " + o);
      }

      TypeRecord typeRec = this.findOrCreateTypeRecord(o.typeCode);
      typeRec.ensureRecoveryCapacity(o.slotNum);
      long recoveryGeneration = typeRec.getRecoveryGeneration(o.slotNum);
      Record old = typeRec.getSlot(o.slotNum, false);
      switch (o.opType) {
         case 1:
            if (o.generationNumber > recoveryGeneration) {
               if (old != null) {
                  this.freeHandle(old.handle);
               }

               typeRec.setRecoveryGeneration(o.slotNum, o.generationNumber);
               typeRec.setSlot(o.slotNum, new CreateRecord(o.handle, o.slotNum));
            } else {
               this.freeHandle(o.handle);
            }
            break;
         case 2:
            if (o.generationNumber > recoveryGeneration) {
               if (old != null) {
                  this.freeHandle(old.handle);
               }

               typeRec.setRecoveryGeneration(o.slotNum, o.generationNumber);
               typeRec.setSlot(o.slotNum, new DeleteRecord(o.handle, o.slotNum));
            } else {
               this.freeHandle(o.handle);
            }
      }

   }

   protected void verifyLastTransaction() throws PersistentStoreException {
      Iterator i = this.pendingOperations.iterator();
      if (i.hasNext()) {
         Operation o = (Operation)i.next();
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StoreDebug.storeIOPhysical.debug("Verifying last transaction. generation = " + this.currentGeneration + " operation size= " + o.numOps + " pending ops = " + this.pendingOperations.size());
         }

         if (o.numOps != this.pendingOperations.size()) {
            System.err.println("last transaction incomplete, discarded");
            this.zeroOutOperations();
            return;
         }

         this.recoverPendingOperations();
      }

   }

   private void processDeleteRecordOnlyHandles(HashMap handleRecords) {
      Iterator itr = handleRecords.values().iterator();

      while(itr.hasNext()) {
         RecoveredHandle hrec = (RecoveredHandle)itr.next();
         if (hrec.isDeleteOnly()) {
            Handle handle = hrec.getHandle();
            handle.setDeleteOnly();
            this.incrementDeleteRecordOnlyBlocks();
         }
      }

      if (StoreHeap.DEBUG_SPACE_UPDATES) {
         System.out.println("Recovered " + this.deleteRecordOnlyBlocks + " deleteRecordOnlyBlocks");
      }

   }

   public void close() throws PersistentStoreException {
      if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
         StoreDebug.storeIOPhysicalVerbose.debug("BaseStoreIO.close(): closing the store " + this.heap.getName());
      }

      this.typeTable.clear();
      this.pendingOperations.clear();
      this.deferredFrees.clear();
      this.currentGeneration = -1L;
      this.testStoreException = null;
      this.fatalError = false;
      this.opened = false;
      this.heap.close();
   }

   protected long nextGenerationNumber() {
      return (long)(this.currentGeneration++);
   }

   protected void freeHandleLater(Handle handle) {
      this.deferredFrees.add(handle);
   }

   protected void freeHandle(Handle handle) {
      if (handle.decrementRefCount() <= 0) {
         if (StoreHeap.DEBUG_SPACE_UPDATES) {
            System.out.println("RS: " + (new Date(System.currentTimeMillis())).toString() + " freeeHandle: handle = " + handle.handle + " isDeleteOnly = " + handle.isDeleteOnly());
         }

         this.heap.forget(handle.handle);
         if (handle.isDeleteOnly()) {
            this.decrementDeleteRecordOnlyBlocks();
            handle.resetRefCount();
         }
      }

   }

   protected void freeDeferredHandles() {
      Handle handle;
      while((handle = (Handle)this.deferredFrees.remove()) != null) {
         this.freeHandle(handle);
      }

   }

   protected void checkOpened() throws PersistentStoreException {
      PersistentStoreException pse = null;
      if (!this.opened) {
         pse = new PersistentStoreException(StoreLogger.logStoreNotOpenLoggable(this.heap.getName()));
      } else if (this.fatalError) {
         pse = new PersistentStoreFatalException(StoreLogger.logStoreFatalErrorLoggable());
      }

      if (pse != null) {
         if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
            StoreDebug.storeIOPhysicalVerbose.debug("BaseStoreIO.checkOpened: store: " + this.heap.getName() + " opened: " + this.opened + " fatalError: " + this.fatalError, (Throwable)pse);
         }

         throw pse;
      }
   }

   public void dumpInternal(XMLStreamWriter xsw) throws XMLStreamException {
      xsw.writeAttribute("Directory", this.heap.getDirectoryName());
      xsw.writeAttribute("WritePolicy", this.writePolicy.toString());
      xsw.writeAttribute("BlockSize", "" + this.blockSize);
      xsw.writeAttribute("SupportOSDirectIO", "" + this.heap.getSupportOSDirectIO());
      xsw.writeAttribute("HeapVersion", "" + this.heap.getHeapVersion());
      xsw.writeEndElement();
   }

   public void dump(XMLStreamWriter xsw, int typeCode) throws XMLStreamException {
      this.dump(xsw, typeCode, false);
   }

   public void dump(XMLStreamWriter xsw, int typeCode, boolean dumpContents) throws XMLStreamException {
      Collection records;
      try {
         TypeRecord typeRec = this.getTypeRecord(typeCode);
         records = typeRec.getRecords();
      } catch (PersistentStoreException var9) {
         return;
      }

      for(Iterator i = records.iterator(); i.hasNext(); xsw.writeEndElement()) {
         Record cr = (Record)i.next();
         xsw.writeStartElement("Record");
         long handle = cr.handle.handle;
         xsw.writeAttribute("TypeCode", "" + typeCode);
         xsw.writeAttribute("SlotNum", "" + cr.slotNum);
         xsw.writeAttribute("HandleNum", "" + handle);
         xsw.writeAttribute("FileNum", "" + StoreHeap.handleToFileNum(handle));
         xsw.writeAttribute("BlockNum", "" + StoreHeap.handleToFileBlock(handle));
         xsw.writeAttribute("NumBlocks", "" + StoreHeap.handleToNumBlocks(handle));
         if (dumpContents) {
            this.dumpRecordContents(xsw, cr.slotNum, typeCode);
         }
      }

   }

   protected void dumpRecordContents(XMLStreamWriter xsw, int handle, int typeCode) throws XMLStreamException {
      xsw.writeStartElement("RecordContents");

      try {
         IORecord ioRec = this.read(handle, typeCode);
         ByteBuffer bb = ioRec.getData();
         if (bb == null || bb.limit() == 0) {
            xsw.writeEndElement();
            return;
         }

         byte[] ba = new byte[bb.limit()];
         bb.get(ba, 0, ba.length);
         xsw.writeCharacters(Hex.dump(ba, 0, ba.length));
      } catch (PersistentStoreException var7) {
         xsw.writeCharacters("ERROR: while reading record contents");
      }

      xsw.writeEndElement();
   }

   public void setTestException(PersistentStoreException exception) {
      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StoreDebug.storeIOPhysical.debug("BaseStoreIO.setTestException(): setting: " + exception.getMessage(), new Exception("Called from"));
      }

      this.testStoreException = exception;
   }

   abstract File[] listRegionsOrFiles(Heap var1, File var2, FilenameFilter var3) throws IOException;

   public String getDriver() {
      return this.directIOManager.getDriver();
   }

   private static final class RecoveredHandle {
      Handle handle;
      boolean deleteOnly = true;

      RecoveredHandle(Handle handle) {
         this.handle = handle;
      }

      Handle getHandle() {
         return this.handle;
      }

      boolean isDeleteOnly() {
         return this.deleteOnly;
      }

      void setDeleteOnly(boolean deleteOnly) {
         this.deleteOnly = deleteOnly;
      }
   }

   protected static final class OperationHandleComparator implements Comparator {
      static final OperationHandleComparator THE_ONE = new OperationHandleComparator();

      public int compare(Object o1, Object o2) {
         Operation op1 = (Operation)o1;
         Operation op2 = (Operation)o2;
         if (op1.typeCode < op2.typeCode) {
            return -1;
         } else if (op1.typeCode > op2.typeCode) {
            return 1;
         } else if (op1.slotNum < op2.slotNum) {
            return -1;
         } else {
            return op1.slotNum > op2.slotNum ? 1 : 0;
         }
      }
   }

   protected static final class OperationSizeComparator implements Comparator {
      static final OperationSizeComparator THE_ONE = new OperationSizeComparator();

      public int compare(Object o1, Object o2) {
         try {
            int len1 = ((Operation)o1).length();
            int len2 = ((Operation)o2).length();
            if (len1 < len2) {
               return -1;
            } else {
               return len1 > len2 ? 1 : 0;
            }
         } catch (ClassCastException var5) {
            return -1;
         }
      }

      public boolean equals(Object obj) {
         return obj instanceof OperationSizeComparator;
      }
   }

   static final class Operation {
      static final byte CREATE = 1;
      static final byte DELETE = 2;
      protected static final int HEADER_LENGTH = 5;
      protected static final int CREATE_HEADER_LENGTH = 8;
      protected static final int MAX_HEADER_LENGTH = 8;
      long generationNumber;
      short numOps;
      int slotNum;
      byte opType;
      int bodyLength;
      int typeCode;
      ByteBuffer[] data;
      Handle handle;
      int handleIndex;
      Record record;

      protected Operation() {
      }

      Operation(int slotNum, int typeCode, ByteBuffer[] data) {
         this.opType = 1;
         this.slotNum = slotNum;
         this.bodyLength = 8 + calculateLength(data);
         this.typeCode = typeCode;
         this.data = data;
      }

      Operation(int slotNum, int typeCode) {
         this.opType = 2;
         this.slotNum = slotNum;
         this.typeCode = typeCode;
      }

      protected void write(ArrayList bufList, boolean writeFlushHeader, long generation, short numOps) {
         ByteBuffer bb = ByteBuffer.allocate(23);
         if (writeFlushHeader) {
            bb.putLong(generation);
            bb.putShort(numOps);
         }

         bb.putInt(this.typeCode);
         bb.putInt(this.slotNum);
         bb.put(this.opType);
         switch (this.opType) {
            case 1:
               bb.putInt(this.bodyLength);
               bb.flip();
               bufList.add(bb);
               if (this.data != null) {
                  for(int i = 0; i < this.data.length; ++i) {
                     bufList.add(this.data[i]);
                  }
               }
               break;
            case 2:
               bb.flip();
               bufList.add(bb);
               break;
            default:
               throw new AssertionError("Unknown opType: " + this.opType);
         }

      }

      protected static Operation read(ByteBuffer bb, int slotNum, int typeCode) throws PersistentStoreException {
         while(true) {
            if (bb.remaining() > 0) {
               Operation ret = read(bb, true);
               if (ret.slotNum != slotNum || ret.typeCode != typeCode) {
                  continue;
               }

               return ret;
            }

            throw new PersistentStoreException(StoreLogger.logStoreRecordNotFoundLoggable((long)slotNum));
         }
      }

      static Operation read(ByteBuffer bb, boolean readBody) throws PersistentStoreException {
         if (bb.remaining() < 5) {
            throw new PersistentStoreException(StoreLogger.logInvalidStoreRecordLoggable(12));
         } else {
            Operation o = new Operation();
            o.typeCode = bb.getInt();
            o.slotNum = bb.getInt();
            o.opType = bb.get();
            switch (o.opType) {
               case 1:
                  if (bb.remaining() < 8) {
                     throw new PersistentStoreException(StoreLogger.logInvalidStoreRecordLoggable(13));
                  } else {
                     o.bodyLength = bb.getInt();
                     int dataLen = o.bodyLength - 8;
                     if (bb.remaining() < dataLen) {
                        throw new PersistentStoreException(StoreLogger.logInvalidStoreRecordLoggable(14));
                     } else if (readBody) {
                        if (dataLen != 0) {
                           o.data = new ByteBuffer[1];
                           o.data[0] = bb.slice();
                           o.data[0].limit(dataLen);
                           bb.position(bb.position() + dataLen);
                        }
                     } else {
                        bb.position(bb.position() + dataLen);
                     }
                  }
               case 2:
                  return o;
               default:
                  throw new PersistentStoreException(StoreLogger.logInvalidStoreRecordLoggable(12));
            }
         }
      }

      int length() {
         return 5 + this.bodyLength;
      }

      static int calculateLength(ByteBuffer[] bbs) {
         if (bbs == null) {
            return 0;
         } else {
            int ret = 0;

            for(int i = 0; i < bbs.length; ++i) {
               ret += bbs[i].remaining();
            }

            return ret;
         }
      }

      public String toString() {
         StringBuffer sb = new StringBuffer("Operation (");
         sb.append("type=");
         switch (this.opType) {
            case 1:
               sb.append("CREATE");
               break;
            case 2:
               sb.append("DELETE");
               break;
            default:
               sb.append("UNKNOWN");
         }

         sb.append(" generation=").append(this.generationNumber);
         sb.append(" numOps=").append(this.numOps);
         sb.append(" slot=").append(this.slotNum);
         sb.append(" typeCode=").append(this.typeCode);
         sb.append(")");
         return sb.toString();
      }
   }

   static final class TypeRecord {
      protected int typeCode;
      protected final ArrayList slots = new ArrayList();
      protected ArrayList recoverySlots;
      protected DeleteRecord freeSlots;

      protected TypeRecord(int typeCode) {
         this.typeCode = typeCode;
      }

      int getTypeCode() {
         return this.typeCode;
      }

      synchronized void ensureRecoveryCapacity(int slot) {
         if (this.recoverySlots == null) {
            this.recoverySlots = new ArrayList();
         }

         while(this.recoverySlots.size() < slot + 1) {
            this.recoverySlots.add(-1L);
         }

      }

      synchronized void freeRecoverySlots() {
         this.recoverySlots = null;
      }

      synchronized int getSize() {
         int ret = 0;
         Iterator i = this.slots.iterator();

         while(i.hasNext()) {
            if (i.next() instanceof CreateRecord) {
               ++ret;
            }
         }

         return ret;
      }

      synchronized void setSlot(int slot, Record record) {
         for(int inc = this.slots.size(); inc < slot + 1; ++inc) {
            this.slots.add((Object)null);
         }

         this.slots.set(slot, record);
      }

      synchronized Record getSlot(int slot, boolean check) throws PersistentStoreException {
         Record ret;
         try {
            ret = (Record)this.slots.get(slot);
         } catch (IndexOutOfBoundsException var5) {
            if (!check) {
               return null;
            }

            throw new PersistentStoreException(StoreLogger.logInvalidRecordHandleLoggable((long)slot), var5);
         }

         if (!check || ret != null && !(ret instanceof DeleteRecord)) {
            return ret;
         } else {
            throw new PersistentStoreException(StoreLogger.logStoreRecordNotFoundLoggable((long)slot));
         }
      }

      synchronized boolean containsCreateRecord(int slot) {
         return slot >= 0 && slot < this.slots.size() && this.slots.get(slot) instanceof CreateRecord;
      }

      synchronized long getRecoveryGeneration(int slot) {
         return (Long)this.recoverySlots.get(slot);
      }

      synchronized void setRecoveryGeneration(int slot, long newGeneration) {
         this.recoverySlots.set(slot, newGeneration);
      }

      synchronized int allocateSlot() {
         if (this.freeSlots != null) {
            DeleteRecord freedRec = this.freeSlots;
            this.freeSlots = freedRec.next;
            freedRec.next = null;
            return freedRec.getSlotNum();
         } else {
            int ret = this.slots.size();
            this.slots.add(new DeleteRecord((Handle)null, ret));
            return ret;
         }
      }

      synchronized void undoAllocateSlot(int slot) {
         DeleteRecord unFreedSlot;
         try {
            unFreedSlot = (DeleteRecord)this.slots.get(slot);
         } catch (IndexOutOfBoundsException var4) {
            return;
         } catch (ClassCastException var5) {
            return;
         }

         unFreedSlot.next = this.freeSlots;
         this.freeSlots = unFreedSlot;
      }

      synchronized void freeSlot(int slot, DeleteRecord deleteRec) throws PersistentStoreException {
         try {
            Record oldRec = (Record)this.slots.get(slot);
            if (oldRec instanceof DeleteRecord) {
               throw new PersistentStoreException(StoreLogger.logStoreRecordNotFoundLoggable((long)slot));
            }

            this.slots.set(slot, deleteRec);
         } catch (IndexOutOfBoundsException var4) {
            throw new PersistentStoreException(StoreLogger.logInvalidRecordHandleLoggable((long)slot));
         }

         deleteRec.next = this.freeSlots;
         this.freeSlots = deleteRec;
      }

      synchronized Collection getRecords() {
         ArrayList ret = new ArrayList(this.slots.size());
         Iterator i = this.slots.iterator();

         while(i.hasNext()) {
            Object next = i.next();
            if (next instanceof CreateRecord) {
               ret.add(next);
            }
         }

         return ret;
      }

      synchronized void rebuildDeletedSlots() {
         Iterator i = this.slots.iterator();

         while(i.hasNext()) {
            Object next = i.next();
            if (next instanceof DeleteRecord) {
               DeleteRecord rec = (DeleteRecord)next;
               rec.next = this.freeSlots;
               this.freeSlots = rec;
            }
         }

      }

      synchronized void checkDeleteRecordOnlyHandles(HashMap handleRecords) {
         Iterator i = this.slots.iterator();

         while(true) {
            Record rec;
            do {
               if (!i.hasNext()) {
                  return;
               }

               rec = (Record)i.next();
            } while(!(rec instanceof DeleteRecord) && !(rec instanceof CreateRecord));

            Handle handle = rec.getHandle();
            if (handle != null) {
               RecoveredHandle hrec = (RecoveredHandle)handleRecords.get(handle);
               if (hrec == null) {
                  hrec = new RecoveredHandle(handle);
                  handleRecords.put(handle, hrec);
               }

               if (rec instanceof CreateRecord) {
                  hrec.setDeleteOnly(false);
               }
            }
         }
      }
   }

   protected static final class DeleteRecord extends Record {
      DeleteRecord next;

      DeleteRecord(Handle handle, int slotNum) {
         super(handle, slotNum);
      }
   }

   protected static final class CreateRecord extends Record {
      CreateRecord(Handle handle, int slotNum) {
         super(handle, slotNum);
      }
   }

   protected abstract static class Record {
      Handle handle;
      protected final int slotNum;

      protected Record(Handle handle, int slotNum) {
         this.handle = handle;
         this.slotNum = slotNum;
      }

      final Handle getHandle() {
         return this.handle;
      }

      final int getSlotNum() {
         return this.slotNum;
      }

      final void updateHandle(Handle newHandle) {
         this.handle = newHandle;
      }
   }

   static final class Handle {
      final long handle;
      short refCount;

      Handle(long handle) {
         this.handle = handle;
      }

      void incrementRefCount() {
         boolean isDelete = this.isDeleteOnly();
         this.refCount = this.getRefCount();
         ++this.refCount;

         assert this.refCount < 32767;

         if (StoreHeap.DEBUG_SPACE_UPDATES && isDelete) {
            System.out.println("RS: incrementRefCount for a deleteRecordOnly handle " + this.handle + " refCount = " + this.refCount);
         }

         if (isDelete) {
            this.setDeleteOnly();
         }

      }

      short decrementRefCount() {
         boolean isDelete = this.isDeleteOnly();
         this.refCount = this.getRefCount();
         --this.refCount;
         if (this.refCount < 0) {
            this.refCount = 0;
         }

         if (StoreHeap.DEBUG_SPACE_UPDATES && isDelete) {
            System.out.println("RS: decrementRefCount for a deleteRecordOnly handle " + this.handle + "refCount = " + this.refCount);
         }

         if (isDelete) {
            this.setDeleteOnly();
         }

         return this.getRefCount();
      }

      short getRefCount() {
         return (short)(this.refCount & 32767);
      }

      void setDeleteOnly() {
         this.refCount |= Short.MIN_VALUE;
      }

      boolean isDeleteOnly() {
         return (this.refCount & Short.MIN_VALUE) != 0;
      }

      void resetRefCount() {
         this.refCount = 0;
      }
   }

   protected final class Cursor implements PersistentStoreIO.Cursor {
      Iterator recIterator = null;
      Iterator codeIterator;
      TypeRecord typeRec;
      boolean allowSkips;

      Cursor(Collection typeRecs, int flags) {
         this.codeIterator = typeRecs.iterator();
         this.allowSkips = (flags & 32) == 32;
      }

      public IORecord next() throws PersistentStoreException {
         int i = 0;

         while(i < 20) {
            try {
               return this.nextInternal();
            } catch (PersistentStoreException var3) {
               if (!(var3.getCause() instanceof PersistentStoreRuntimeException)) {
                  throw var3;
               }

               ++i;
            }
         }

         throw new PersistentStoreException(StoreLogger.logStoreRecordNotFoundLoggable(0L));
      }

      protected IORecord nextInternal() throws PersistentStoreException {
         while(this.recIterator == null || !this.recIterator.hasNext()) {
            if (!this.codeIterator.hasNext()) {
               return null;
            }

            this.typeRec = (TypeRecord)this.codeIterator.next();
            this.recIterator = this.typeRec.getRecords().iterator();
         }

         int slotNum = ((Record)this.recIterator.next()).getSlotNum();
         return BaseStoreIO.this.readInternal(slotNum, this.typeRec, this.allowSkips);
      }
   }
}
