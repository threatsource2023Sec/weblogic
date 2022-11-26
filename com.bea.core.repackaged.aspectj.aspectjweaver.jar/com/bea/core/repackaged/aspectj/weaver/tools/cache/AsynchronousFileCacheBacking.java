package com.bea.core.repackaged.aspectj.weaver.tools.cache;

import com.bea.core.repackaged.aspectj.util.FileUtil;
import com.bea.core.repackaged.aspectj.util.LangUtil;
import com.bea.core.repackaged.aspectj.weaver.tools.Trace;
import com.bea.core.repackaged.aspectj.weaver.tools.TraceFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class AsynchronousFileCacheBacking extends AbstractIndexedFileCacheBacking {
   private static final BlockingQueue commandsQ = new LinkedBlockingQueue();
   private static final ExecutorService execService = Executors.newSingleThreadExecutor();
   private static Future commandsRunner;
   protected final Map index;
   protected final Map exposedIndex;
   protected final Map bytesMap;
   protected final Map exposedBytes;

   protected AsynchronousFileCacheBacking(File cacheDir) {
      super(cacheDir);
      this.index = this.readIndex(cacheDir, this.getIndexFile());
      this.exposedIndex = Collections.unmodifiableMap(this.index);
      this.bytesMap = this.readClassBytes(this.index, cacheDir);
      this.exposedBytes = Collections.unmodifiableMap(this.bytesMap);
   }

   protected Map getIndex() {
      return this.index;
   }

   public CachedClassEntry get(CachedClassReference ref, byte[] originalBytes) {
      String key = ref.getKey();
      AbstractIndexedFileCacheBacking.IndexEntry indexEntry;
      synchronized(this.index) {
         if ((indexEntry = (AbstractIndexedFileCacheBacking.IndexEntry)this.index.get(key)) == null) {
            return null;
         }
      }

      if (crc(originalBytes) != indexEntry.crcClass) {
         if (this.logger != null && this.logger.isTraceEnabled()) {
            this.logger.debug("get(" + this.getCacheDirectory() + ") mismatched original class bytes CRC for " + key);
         }

         this.remove(key);
         return null;
      } else if (indexEntry.ignored) {
         return new CachedClassEntry(ref, WeavedClassCache.ZERO_BYTES, CachedClassEntry.EntryType.IGNORED);
      } else {
         byte[] bytes;
         synchronized(this.bytesMap) {
            if ((bytes = (byte[])this.bytesMap.remove(key)) == null) {
               return null;
            }
         }

         return indexEntry.generated ? new CachedClassEntry(ref, bytes, CachedClassEntry.EntryType.GENERATED) : new CachedClassEntry(ref, bytes, CachedClassEntry.EntryType.WEAVED);
      }
   }

   public void put(CachedClassEntry entry, byte[] originalBytes) {
      String key = entry.getKey();
      byte[] bytes = entry.isIgnored() ? null : entry.getBytes();
      synchronized(this.index) {
         AbstractIndexedFileCacheBacking.IndexEntry indexEntry = (AbstractIndexedFileCacheBacking.IndexEntry)this.index.get(key);
         if (indexEntry != null) {
            return;
         }

         indexEntry = createIndexEntry(entry, originalBytes);
         this.index.put(key, indexEntry);
      }

      if (!postCacheCommand(new InsertCommand(this, key, bytes)) && this.logger != null && this.logger.isTraceEnabled()) {
         this.logger.error("put(" + this.getCacheDirectory() + ") Failed to post insert command for " + key);
      }

      if (this.logger != null && this.logger.isTraceEnabled()) {
         this.logger.debug("put(" + this.getCacheDirectory() + ")[" + key + "] inserted");
      }

   }

   public void remove(CachedClassReference ref) {
      this.remove(ref.getKey());
   }

   protected AbstractIndexedFileCacheBacking.IndexEntry remove(String key) {
      AbstractIndexedFileCacheBacking.IndexEntry entry;
      synchronized(this.index) {
         entry = (AbstractIndexedFileCacheBacking.IndexEntry)this.index.remove(key);
      }

      synchronized(this.bytesMap) {
         this.bytesMap.remove(key);
      }

      if (!postCacheCommand(new RemoveCommand(this, key)) && this.logger != null && this.logger.isTraceEnabled()) {
         this.logger.error("remove(" + this.getCacheDirectory() + ") Failed to post remove command for " + key);
      }

      if (entry != null) {
         if (!key.equals(entry.key)) {
            if (this.logger != null && this.logger.isTraceEnabled()) {
               this.logger.error("remove(" + this.getCacheDirectory() + ") Mismatched keys: " + key + " / " + entry.key);
            }
         } else if (this.logger != null && this.logger.isTraceEnabled()) {
            this.logger.debug("remove(" + this.getCacheDirectory() + ")[" + key + "] removed");
         }
      }

      return entry;
   }

   public List getIndexEntries() {
      synchronized(this.index) {
         return (List)(this.index.isEmpty() ? Collections.emptyList() : new ArrayList(this.index.values()));
      }
   }

   public Map getIndexMap() {
      return this.exposedIndex;
   }

   public Map getBytesMap() {
      return this.exposedBytes;
   }

   public void clear() {
      synchronized(this.index) {
         this.index.clear();
      }

      if (!postCacheCommand(new ClearCommand(this)) && this.logger != null && this.logger.isTraceEnabled()) {
         this.logger.error("Failed to post clear command for " + this.getIndexFile());
      }

   }

   protected void executeCommand(AsyncCommand cmd) throws Exception {
      if (cmd instanceof ClearCommand) {
         this.executeClearCommand();
      } else if (cmd instanceof UpdateIndexCommand) {
         this.executeUpdateIndexCommand();
      } else if (cmd instanceof InsertCommand) {
         this.executeInsertCommand((InsertCommand)cmd);
      } else {
         if (!(cmd instanceof RemoveCommand)) {
            throw new UnsupportedOperationException("Unknown command: " + cmd);
         }

         this.executeRemoveCommand((RemoveCommand)cmd);
      }

   }

   protected void executeClearCommand() throws Exception {
      FileUtil.deleteContents(this.getIndexFile());
      FileUtil.deleteContents(this.getCacheDirectory());
   }

   protected void executeUpdateIndexCommand() throws Exception {
      this.writeIndex(this.getIndexFile(), this.getIndexEntries());
   }

   protected void executeInsertCommand(InsertCommand cmd) throws Exception {
      this.writeIndex(this.getIndexFile(), this.getIndexEntries());
      byte[] bytes = cmd.getClassBytes();
      if (bytes != null) {
         this.writeClassBytes(cmd.getKey(), bytes);
      }

   }

   protected void executeRemoveCommand(RemoveCommand cmd) throws Exception {
      Exception err = null;

      try {
         this.removeClassBytes(cmd.getKey());
      } catch (Exception var4) {
         err = var4;
      }

      this.writeIndex(this.getIndexFile(), this.getIndexEntries());
      if (err != null) {
         throw err;
      }
   }

   protected abstract void removeClassBytes(String var1) throws Exception;

   protected abstract Map readClassBytes(Map var1, File var2);

   public String toString() {
      return this.getClass().getSimpleName() + "[" + this.getCacheDirectory() + "]";
   }

   protected static final AsynchronousFileCacheBacking createBacking(File cacheDir, AsynchronousFileCacheBackingCreator creator) {
      final Trace trace = TraceFactory.getTraceFactory().getTrace(AsynchronousFileCacheBacking.class);
      if (!cacheDir.exists() && !cacheDir.mkdirs()) {
         if (trace != null && trace.isTraceEnabled()) {
            trace.error("Unable to create cache directory at " + cacheDir.getAbsolutePath());
         }

         return null;
      } else if (!cacheDir.canWrite()) {
         if (trace != null && trace.isTraceEnabled()) {
            trace.error("Cache directory is not writable at " + cacheDir.getAbsolutePath());
         }

         return null;
      } else {
         AsynchronousFileCacheBacking backing = creator.create(cacheDir);
         synchronized(execService) {
            if (commandsRunner == null) {
               commandsRunner = execService.submit(new Runnable() {
                  public void run() {
                     while(true) {
                        try {
                           AsyncCommand cmd = (AsyncCommand)AsynchronousFileCacheBacking.commandsQ.take();

                           try {
                              AsynchronousFileCacheBacking cache = cmd.getCache();
                              cache.executeCommand(cmd);
                           } catch (Exception var3) {
                              if (trace != null && trace.isTraceEnabled()) {
                                 trace.error("Failed (" + var3.getClass().getSimpleName() + ")" + " to execute " + cmd + ": " + var3.getMessage(), var3);
                              }
                           }
                        } catch (InterruptedException var4) {
                           if (trace != null && trace.isTraceEnabled()) {
                              trace.warn("Interrupted");
                           }

                           Thread.currentThread().interrupt();
                           return;
                        }
                     }
                  }
               });
            }
         }

         if (!postCacheCommand(new UpdateIndexCommand(backing)) && trace != null && trace.isTraceEnabled()) {
            trace.warn("Failed to offer update index command to " + cacheDir.getAbsolutePath());
         }

         return backing;
      }
   }

   public static final boolean postCacheCommand(AsyncCommand cmd) {
      return commandsQ.offer(cmd);
   }

   public static class InsertCommand extends KeyedCommand {
      private final byte[] bytes;

      public InsertCommand(AsynchronousFileCacheBacking cache, String keyValue, byte[] classBytes) {
         super(cache, keyValue);
         this.bytes = classBytes;
      }

      public final byte[] getClassBytes() {
         return this.bytes;
      }
   }

   public static class RemoveCommand extends KeyedCommand {
      public RemoveCommand(AsynchronousFileCacheBacking cache, String keyValue) {
         super(cache, keyValue);
      }
   }

   public abstract static class KeyedCommand extends AbstractCommand {
      private final String key;

      protected KeyedCommand(AsynchronousFileCacheBacking cache, String keyValue) {
         super(cache);
         if (LangUtil.isEmpty(keyValue)) {
            throw new IllegalStateException("No key value");
         } else {
            this.key = keyValue;
         }
      }

      public final String getKey() {
         return this.key;
      }

      public String toString() {
         return super.toString() + "[" + this.getKey() + "]";
      }
   }

   public static class UpdateIndexCommand extends AbstractCommand {
      public UpdateIndexCommand(AsynchronousFileCacheBacking cache) {
         super(cache);
      }
   }

   public static class ClearCommand extends AbstractCommand {
      public ClearCommand(AsynchronousFileCacheBacking cache) {
         super(cache);
      }
   }

   public abstract static class AbstractCommand implements AsyncCommand {
      private final AsynchronousFileCacheBacking cache;

      protected AbstractCommand(AsynchronousFileCacheBacking backing) {
         if ((this.cache = backing) == null) {
            throw new IllegalStateException("No backing cache specified");
         }
      }

      public final AsynchronousFileCacheBacking getCache() {
         return this.cache;
      }

      public String toString() {
         return this.getClass().getSimpleName() + "[" + this.getCache() + "]";
      }
   }

   public interface AsyncCommand {
      AsynchronousFileCacheBacking getCache();
   }

   public interface AsynchronousFileCacheBackingCreator {
      AsynchronousFileCacheBacking create(File var1);
   }
}
