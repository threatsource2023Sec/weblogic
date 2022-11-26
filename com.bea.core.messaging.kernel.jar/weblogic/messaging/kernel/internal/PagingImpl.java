package weblogic.messaging.kernel.internal;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.messaging.Message;
import weblogic.messaging.MessagingLogger;
import weblogic.messaging.kernel.KernelException;
import weblogic.store.ObjectHandler;
import weblogic.store.PersistentStoreException;
import weblogic.store.StoreWritePolicy;
import weblogic.store.internal.StoreStatisticsImpl;
import weblogic.store.io.file.Heap;
import weblogic.utils.collections.EmbeddedList;
import weblogic.utils.concurrent.atomic.AtomicFactory;
import weblogic.utils.concurrent.atomic.AtomicInteger;
import weblogic.utils.concurrent.atomic.AtomicLong;
import weblogic.utils.io.ByteBufferObjectInputStream;

final class PagingImpl implements Runnable {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugMessagingKernel");
   private Heap pagingFile;
   private String pagingDirectory;
   private ObjectHandler userObjectHandler;
   private final HashMap config = new HashMap();
   StoreStatisticsImpl storeStats;
   private final KernelImpl kernel;
   private final List unpagedList = new EmbeddedList();
   private boolean running;
   private boolean closed;
   private final boolean alwaysUsePagingStore;
   private long threshold;
   private long unpagedThreshold;
   private long lowThreshold;
   private final long pagedMessageThreshold;
   private final AtomicLong currentUsage = AtomicFactory.createAtomicLong();
   private final int batchSize;
   private int numWaiters;
   private final AtomicInteger messagesPagedOut = AtomicFactory.createAtomicInteger();
   private final AtomicInteger messagesPagedIn = AtomicFactory.createAtomicInteger();
   private final AtomicLong bytesPagedOut = AtomicFactory.createAtomicLong();
   private final AtomicLong bytesPagedIn = AtomicFactory.createAtomicLong();
   private static final int DEFAULT_BATCH_SIZE = 128;
   private static final long DEFAULT_THRESHOLD_SIZE = 65536L;
   private static final long MINIMUM_BUFFER_SIZE = 8388608L;
   private static final long MAXIMUM_BUFFER_SIZE = 536870912L;
   private static final String PAGING_FILE_SUFFIX = "tmp";

   PagingImpl(KernelImpl kernel, String pagingDirectory, ObjectHandler userObjectHandler, long bufferSize, boolean supportsFastReads) {
      this.kernel = kernel;
      this.pagingDirectory = pagingDirectory;
      this.userObjectHandler = userObjectHandler;
      this.updateThreshold(bufferSize);
      this.alwaysUsePagingStore = kernel.getBooleanProperty("weblogic.messaging.kernel.paging.AlwaysUsePagingStore", !supportsFastReads);
      this.pagedMessageThreshold = kernel.getLongProperty("weblogic.messaging.kernel.paging.PagedMessageThreshold", 65536L);
      this.batchSize = kernel.getIntProperty("weblogic.messaging.kernel.paging.BatchSize", 128);
   }

   public void open() throws KernelException {
      try {
         this.openPagingFile();

         try {
            while(true) {
               if (this.pagingFile.recover() != null) {
                  continue;
               }
            }
         } catch (Exception var2) {
            this.pagingFile.close();
            this.pagingFile.removeStoreFiles();
            this.openPagingFile();
         }

      } catch (PersistentStoreException var3) {
         throw new KernelException("Cannot open paging store", var3);
      }
   }

   private void openPagingFile() throws KernelException {
      try {
         if (logger.isDebugEnabled()) {
            logger.debug("Opening paging store in directory " + this.pagingDirectory + ", pagingFileLockingEnabled set to " + this.config.get("FileLockingEnabled"));
         }

         this.pagingFile = new Heap(this.kernel.getName(), this.pagingDirectory, "tmp");
         this.pagingFile.setSynchronousWritePolicy(StoreWritePolicy.NON_DURABLE);
         this.pagingFile.setConfig(this.config);
         this.storeStats = new StoreStatisticsImpl(this.kernel.getName());
         this.pagingFile.setStats(this.storeStats);
         this.pagingFile.open();
      } catch (PersistentStoreException var2) {
         throw new KernelException("Cannot open paging store", var2);
      }
   }

   public void close() throws KernelException {
      synchronized(this) {
         this.closed = true;

         while(true) {
            if (!this.running) {
               if (logger.isDebugEnabled()) {
                  logger.debug("Closing PagingStore, clearing unpagedList, size: " + this.unpagedList.size());
               }

               this.unpagedList.clear();
               break;
            }

            ++this.numWaiters;

            try {
               this.wait();
            } catch (InterruptedException var10) {
            } finally {
               --this.numWaiters;
            }
         }
      }

      try {
         this.pagingFile.close();
      } catch (PersistentStoreException var9) {
         throw new KernelException("Error closing store", var9);
      }
   }

   synchronized int getNumMessages() {
      return this.unpagedList.size();
   }

   long getNumBytes() {
      return this.currentUsage.get();
   }

   int getMessagesPagedOut() {
      return this.messagesPagedOut.get();
   }

   int getMessagesPagedIn() {
      return this.messagesPagedIn.get();
   }

   long getBytesPagedOut() {
      return this.bytesPagedOut.get();
   }

   long getBytesPagedIn() {
      return this.bytesPagedIn.get();
   }

   private void updateThreshold(long newSize) {
      if (newSize < 0L || newSize == Long.MAX_VALUE) {
         newSize = Runtime.getRuntime().maxMemory() / 3L;
         newSize = Math.max(newSize, 8388608L);
         newSize = Math.min(newSize, 536870912L);
      }

      this.threshold = newSize;
      this.lowThreshold = Math.max(newSize - newSize / 10L, 0L);
      this.unpagedThreshold = Math.max(newSize / 4L, 0L);
      MessagingLogger.logMessageBufferSize(this.kernel.getName(), newSize);
   }

   public void setPagingConfig(HashMap config) {
      this.config.putAll(config);
   }

   void setBufferSize(long newSize) {
      boolean startWork;
      synchronized(this) {
         this.updateThreshold(newSize);
         startWork = this.checkThreshold(this.currentUsage.get());
      }

      if (startWork) {
         this.kernel.getWorkManager().schedule(this);
      }

   }

   synchronized void waitForSpace() {
      while(this.currentUsage.get() > this.threshold) {
         ++this.numWaiters;

         try {
            this.wait();
         } catch (InterruptedException var5) {
         } finally {
            --this.numWaiters;
         }
      }

   }

   void makePageable(MessageHandle handle) {
      long newSize = this.currentUsage.addAndGet(handle.size());
      if (newSize >= this.unpagedThreshold) {
         boolean startWork;
         synchronized(this) {
            this.unpagedList.add(handle);
            startWork = this.checkThreshold(newSize);
         }

         if (startWork) {
            this.kernel.getWorkManager().schedule(this);
         }

      }
   }

   private boolean checkThreshold(long newSize) {
      assert Thread.holdsLock(this);

      if (!this.running && !this.closed && newSize > this.threshold) {
         this.running = true;
         return true;
      } else {
         return false;
      }
   }

   void makeUnpageable(MessageHandle handle) {
      if (handle.getList() == this.unpagedList) {
         synchronized(this) {
            this.unpagedList.remove(handle);
            if (this.numWaiters > 0) {
               this.notifyAll();
            }
         }
      }

      if (handle.getMessage() != null) {
         this.currentUsage.addAndGet(-handle.size());
      }

   }

   void pageIn(MessageHandle handle) throws KernelException {
      Message msg;
      synchronized(this.pagingFile) {
         Heap.HeapRecord heapRec;
         try {
            heapRec = this.pagingFile.read(handle.getPagingHandle());
         } catch (PersistentStoreException var9) {
            throw new KernelException("Error paging in message body", var9);
         }

         try {
            ByteBufferObjectInputStream in = new ByteBufferObjectInputStream(new ByteBuffer[]{heapRec.getBody()});
            if (this.userObjectHandler == null) {
               msg = (Message)in.readObject();
            } else {
               msg = (Message)this.userObjectHandler.readObject(in);
            }
         } catch (IOException var7) {
            throw new KernelException("Error reading paged-out message body", var7);
         } catch (ClassNotFoundException var8) {
            throw new KernelException("Error reading paged-out message body", var8);
         }
      }

      handle.setMessage(msg);
      if (logger.isDebugEnabled()) {
         logger.debug("Paging in message " + handle + " msg " + handle.getMessage() + " group " + handle.getGroupName());
      }

      this.messagesPagedIn.incrementAndGet();
      this.bytesPagedIn.addAndGet(handle.size());
   }

   void removePagedState(MessageHandle handle) {
      synchronized(this.pagingFile) {
         this.pagingFile.forget(handle.getPagingHandle());
      }

      handle.setPagingHandle(0L);
   }

   private boolean mustWriteToStore(MessageHandle handle) {
      if (handle.getPagingHandle() != 0L) {
         return false;
      } else {
         return this.alwaysUsePagingStore || handle.getPersistentHandle() == null || handle.size() < this.pagedMessageThreshold;
      }
   }

   public void run() {
      List pendingList = new ArrayList();

      do {
         synchronized(this) {
            if (logger.isDebugEnabled()) {
               logger.debug("Messaging kernel starting paging. current usage = " + this.currentUsage + " threshold = " + this.threshold);
            }

            long bytesToPage = this.currentUsage.get() - this.lowThreshold;
            int ioCount = 0;
            long bytesPaged = 0L;
            Iterator iterator = this.unpagedList.iterator();

            while(ioCount < this.batchSize && bytesPaged < bytesToPage && iterator.hasNext()) {
               MessageHandle handle = (MessageHandle)iterator.next();
               synchronized(handle) {
                  if (handle.isPagingInProgress()) {
                     continue;
                  }

                  iterator.remove();
                  if (this.mustWriteToStore(handle)) {
                     handle.setPagingInProgress(true);
                     pendingList.add(handle);
                     ++ioCount;
                  } else {
                     handle.setPagedOut();
                     this.currentUsage.addAndGet(-handle.size());
                  }

                  bytesPaged += handle.size();
               }

               if (logger.isDebugEnabled()) {
                  logger.debug("Paging out message " + handle + " msg " + handle.getMessage() + " group " + handle.getGroupName());
               }
            }
         }

         long bytesPaged = 0L;
         int msgsPaged = 0;
         if (!pendingList.isEmpty()) {
            try {
               bytesPaged = this.performPagingIO(pendingList);
               msgsPaged = pendingList.size();
            } catch (KernelException var14) {
               MessagingLogger.logPagingIOFailure(var14);
               this.restoreUnpagedList(pendingList);
            } catch (Exception var15) {
               MessagingLogger.logPagingIOFailure(var15);
               this.restoreUnpagedList(pendingList);
            } catch (Throwable var16) {
               MessagingLogger.logPagingIOFailure(var16);
               this.restoreUnpagedList(pendingList);
            }

            this.messagesPagedOut.addAndGet(msgsPaged);
            this.bytesPagedOut.addAndGet(bytesPaged);
            pendingList.clear();
         }

         synchronized(this) {
            this.currentUsage.addAndGet(-bytesPaged);
            if (this.numWaiters > 0) {
               this.notifyAll();
            }

            if (this.closed || this.currentUsage.get() <= this.lowThreshold) {
               this.running = false;
               return;
            }
         }
      } while(!this.kernel.getWorkManager().scheduleIfBusy(this));

   }

   private synchronized void restoreUnpagedList(List pendingList) {
      Iterator iterator = pendingList.iterator();

      while(iterator.hasNext()) {
         MessageHandle handle = (MessageHandle)iterator.next();
         handle.setPagingInProgress(false);
         this.unpagedList.add(handle);
      }

   }

   private long performPagingIO(List pendingList) throws KernelException {
      long bytesPagedOut = 0L;
      ArrayList recList = new ArrayList(pendingList.size());
      Iterator iterator = pendingList.iterator();

      PagingByteBufferObjectOutputStreamImpl out;
      try {
         for(; iterator.hasNext(); recList.add(Arrays.asList(out.getBuffers()))) {
            MessageHandle handle = (MessageHandle)iterator.next();
            out = new PagingByteBufferObjectOutputStreamImpl();
            if (this.userObjectHandler == null) {
               out.writeObject(handle.getMessage());
            } else {
               this.userObjectHandler.writeObject(out, handle.getMessage());
            }
         }
      } catch (IOException var14) {
         throw new KernelException("Error serializing messages for paging", var14);
      }

      long[] handles;
      try {
         synchronized(this.pagingFile) {
            handles = this.pagingFile.multiWrite(recList);
         }

         this.storeStats.incrementPhysicalWriteCount();
      } catch (PersistentStoreException var13) {
         throw new KernelException("Error writing messages for paging", var13);
      }

      int inc = 0;

      for(iterator = pendingList.iterator(); iterator.hasNext(); ++inc) {
         MessageHandle handle = (MessageHandle)iterator.next();
         synchronized(handle) {
            handle.setPagingHandle(handles[inc]);
            handle.setPagedOut();
            handle.setPagingInProgress(false);
            bytesPagedOut += handle.size();
         }
      }

      return bytesPagedOut;
   }
}
