package weblogic.store.io.file;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import weblogic.logging.Loggable;
import weblogic.store.StoreLogger;
import weblogic.store.common.StoreDebug;
import weblogic.store.common.StoreRCMUtils;

public final class StoreDir implements FilenameFilter {
   private final File dir;
   private long bytesUsed;
   final String filePrefix;
   private final String fileSuffix;
   private final String dirName;
   private DirectBufferPool bufferPool;
   private final List files;
   private final Heap heap;

   public StoreDir(String dirName, String storeName, String fileSuffix) {
      this((Heap)null, dirName, storeName, fileSuffix);
   }

   StoreDir(Heap heap, String dirName, String storeName, String fileSuffix) {
      this.files = new ArrayList(1);
      this.heap = heap;
      this.dir = new File(dirName);
      this.filePrefix = storeName.toUpperCase();
      this.fileSuffix = fileSuffix.toUpperCase();
      this.dirName = dirName;
   }

   List open(DirectBufferPool bufferPool, boolean autoCreateDir) throws IOException {
      this.checkOK(autoCreateDir);
      this.bufferPool = bufferPool;

      try {
         this.findFiles();
      } catch (IOException var4) {
         throwIOException(StoreLogger.logCantAccessDirectoryLoggable(this.getDirAbsolutePath()), var4);
      }

      for(short i = 0; i < this.files.size(); ++i) {
         if (this.files.get(i) == null) {
            if (this.heap.isReplicatedStore) {
               throwIOException(StoreLogger.logMissingRegionLoggable(this.heap.getName(), this.fileNumToFileName(this.filePrefix, i)));
            } else {
               throwIOException(StoreLogger.logMissingFileLoggable(this.fileNumToFileName(this.filePrefix, i), this.getDirAbsolutePath()));
            }
         }
      }

      return this.files;
   }

   public void changePrefix(String nuPrefix) throws IOException {
      final String newPrefix = nuPrefix.toUpperCase();
      this.files.clear();
      this.findFiles();

      try {
         class ChangePrefixRunnable implements Runnable {
            public void run() {
               Iterator var1 = StoreDir.this.files.iterator();

               while(var1.hasNext()) {
                  StoreFile file = (StoreFile)var1.next();
                  String newName = StoreDir.this.fileNumToFileName(newPrefix, file.getFileNum());
                  file.rename(new File(StoreDir.this.dir, newName));
               }

            }
         }

         StoreRCMUtils.accountAsGlobal((Runnable)(new ChangePrefixRunnable()));
      } catch (Exception var4) {
         StoreRCMUtils.throwIOorRuntimeException(var4);
      }

   }

   public void deleteFiles() throws IOException {
      final File[] fa = this.dir.listFiles(this);

      try {
         class DeleteFilesRunnable implements Runnable {
            public void run() {
               try {
                  if (fa != null) {
                     for(int i = 0; i < fa.length; ++i) {
                        fa[i].delete();
                     }

                  }
               } catch (SecurityException var2) {
                  throw new RuntimeException(new IOException(var2.toString(), var2));
               }
            }
         }

         StoreRCMUtils.accountAsGlobal((Runnable)(new DeleteFilesRunnable()));
      } catch (Exception var3) {
         StoreRCMUtils.throwIOorRuntimeException(var3);
      }

   }

   public void close() throws IOException {
      IOException firstException = null;

      for(Iterator iter = this.files.listIterator(); iter.hasNext(); iter.remove()) {
         StoreFile sf = (StoreFile)iter.next();

         try {
            sf.close();
         } catch (IOException var5) {
            if (firstException == null) {
               firstException = var5;
            }
         }
      }

      if (firstException != null) {
         throw firstException;
      }
   }

   public int numFiles() {
      return this.files.size();
   }

   public StoreFile get(int index) {
      return (StoreFile)this.files.get(index);
   }

   public List getFiles() {
      return this.files;
   }

   public void addNewFile(StoreFile file) {
      this.files.add(file);
   }

   public String getDirName() {
      return this.dirName;
   }

   String getDirAbsolutePath() {
      return this.dir.getAbsolutePath();
   }

   private static void throwIOException(Loggable l) throws IOException {
      throw new IOException(l.getMessage());
   }

   private static void throwIOException(Throwable linked) throws IOException {
      IOException ioe = new IOException(linked.toString());
      ioe.initCause(linked);
      throw ioe;
   }

   private static void throwIOException(Loggable l, Throwable linked) throws IOException {
      IOException ioe = new IOException(l.getMessage());
      ioe.initCause(linked);
      throw ioe;
   }

   boolean checkOK(boolean autoCreateOK) throws IOException {
      return this.checkOK(autoCreateOK, this.dir.getPath().length());
   }

   boolean checkOK(boolean autoCreateOK, int length) throws IOException {
      createDirectory(this.dir, length, autoCreateOK);
      final File storeFile = new File(this.dir, this.fileNumToFileName(this.filePrefix, (short)0));
      boolean itExists = false;

      try {
         class CheckOkCallable implements Callable {
            public Boolean call() {
               return storeFile.exists();
            }
         }

         itExists = (Boolean)StoreRCMUtils.accountAsGlobal((Callable)(new CheckOkCallable()));
      } catch (Exception var6) {
         StoreRCMUtils.throwIOorRuntimeException(var6);
      }

      return itExists;
   }

   public static void createDirectory(File dir, boolean autoCreateOK) throws IOException {
      createDirectory(dir, dir.getPath().length(), autoCreateOK);
   }

   public static void createDirectory(File dir, final int length, final boolean autoCreateOK) throws IOException {
      final File theDir = dir;

      try {
         class CreateDirectoryCallable implements Callable {
            public File call() throws IOException {
               boolean autoCreateDir = false;
               boolean isSuccess = false;
               File tmpDir = new File(theDir.getPath().substring(0, length));

               try {
                  if (!tmpDir.exists()) {
                     if (!autoCreateOK) {
                        StoreDir.throwIOException(StoreLogger.logDirectoryNotFoundLoggable(tmpDir.getPath()));
                     } else {
                        try {
                           tmpDir.mkdirs();
                           autoCreateDir = true;
                        } catch (SecurityException var14) {
                           StoreDir.throwIOException((Throwable)var14);
                        }
                     }
                  }

                  StoreDir.checkDirectory(tmpDir);
                  isSuccess = true;
               } catch (SecurityException var15) {
                  StoreDir.throwIOException((Throwable)var15);
               } finally {
                  if (autoCreateDir && !isSuccess) {
                     try {
                        tmpDir.delete();
                     } catch (SecurityException var13) {
                     }
                  }

               }

               return tmpDir;
            }
         }

         StoreRCMUtils.accountAsGlobal((Callable)(new CreateDirectoryCallable()));
      } catch (Exception var5) {
         StoreRCMUtils.throwIOorRuntimeException(var5);
      }

   }

   public File createFile(final short fileNum) throws IOException {
      try {
         class CreateFileCallable implements Callable {
            public File call() throws IOException {
               File file = new File(StoreDir.this.dir, StoreDir.this.fileNumToFileName(StoreDir.this.filePrefix, fileNum));
               if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
                  StoreDebug.storeIOPhysicalVerbose.debug("About to create new file " + file.getPath());
               }

               try {
                  if (!file.createNewFile()) {
                     if (StoreDir.this.heap.isReplicatedStore) {
                        if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
                           StoreDebug.storeIOPhysicalVerbose.debug("StoreDir Replicated Store could not create " + file.getCanonicalPath());
                        }

                        if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
                           StoreDebug.storeIOPhysicalVerbose.debug("StoreDir Replicated Store could not create " + file.getCanonicalPath());
                        }
                     } else {
                        StoreDir.throwIOException(StoreLogger.logErrorCreatingFileLoggable(file.getPath()));
                     }
                  }
               } catch (SecurityException var3) {
                  StoreDir.throwIOException((Throwable)var3);
               }

               return file;
            }
         }

         File createdFile = (File)StoreRCMUtils.accountAsGlobal((Callable)(new CreateFileCallable()));
         return createdFile;
      } catch (IOException var4) {
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StoreDebug.storeIOPhysical.debug("StoreDir.createFile: ", var4);
         }

         throw var4;
      } catch (RuntimeException var5) {
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StoreDebug.storeIOPhysical.debug("StoreDir.createFile: ", var5);
         }

         throw var5;
      } catch (Exception var6) {
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StoreDebug.storeIOPhysical.debug("StoreDir.createFile: ", var6);
         }

         throw new RuntimeException(var6);
      } catch (Error var7) {
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StoreDebug.storeIOPhysical.debug("StoreDir.createFile: ", var7);
         }

         throw var7;
      }
   }

   private void findFiles() throws IOException {
      BaseStoreIO baseStoreIO = this.heap.getBaseStoreIO();
      File[] f = new File[0];
      if (baseStoreIO == null) {
         final FilenameFilter filter = this;

         try {
            class FindFilesCallable implements Callable {
               public File[] call() throws IOException, SecurityException {
                  return StoreDir.this.dir.listFiles(filter);
               }
            }

            f = (File[])StoreRCMUtils.accountAsGlobal((Callable)(new FindFilesCallable()));
         } catch (Exception var6) {
            throwIOException((Throwable)var6);
         }
      } else {
         f = baseStoreIO.listRegionsOrFiles(this.heap, this.dir, this);
      }

      try {
         for(int i = 0; i < f.length; ++i) {
            String fileName = f[i].getName();
            short fileNum = this.fileNameToFileNum(this.filePrefix, fileName);
            if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
               StoreDebug.storeIOPhysicalVerbose.debug("StoreDir.findFiles fileNum=" + fileNum + " for " + fileName);
            }

            while(fileNum >= this.files.size()) {
               this.files.add((Object)null);
            }

            this.files.set(fileNum, new StoreFile(this.heap, this, f[i], fileNum, this.bufferPool));
         }
      } catch (SecurityException var7) {
         throwIOException((Throwable)var7);
      }

   }

   public boolean accept(File dir, String name) {
      return this.fileNameToFileNum(this.filePrefix, name) >= 0;
   }

   public void incBytesUsed(long amt) {
      this.bytesUsed += amt;
   }

   public long getBytesUsed() {
      return this.bytesUsed;
   }

   private String fileNumToFileName(String prefix, short n) {
      String str = Integer.toString(n);
      int len = str.length();
      return prefix + (new StringBuffer("000000")).replace(6 - len, 6, str).toString() + "." + this.fileSuffix;
   }

   private short fileNameToFileNum(String prefix, String fileName) {
      fileName = fileName.toUpperCase();
      if (prefix.length() + this.fileSuffix.length() + 6 + 1 == fileName.length() && fileName.startsWith(prefix) && fileName.endsWith(this.fileSuffix.toUpperCase())) {
         try {
            return Short.parseShort(fileName.substring(prefix.length(), prefix.length() + 6));
         } catch (NumberFormatException var4) {
            return -1;
         }
      } else {
         return -1;
      }
   }

   public static void checkFile(final File file) throws IOException {
      try {
         class CheckFileRunnable implements Runnable {
            public void run() {
               try {
                  if (file.isDirectory()) {
                     StoreDir.throwIOException(StoreLogger.logFileIsADirectoryLoggable(file.getPath()));
                  }

                  if (!file.canRead() || !file.canWrite()) {
                     StoreDir.throwIOException(StoreLogger.logNoAccessToFileLoggable(file.getPath()));
                  }

               } catch (IOException var2) {
                  throw new RuntimeException(var2);
               }
            }
         }

         StoreRCMUtils.accountAsGlobal((Runnable)(new CheckFileRunnable()));
      } catch (Exception var2) {
         StoreRCMUtils.throwIOorRuntimeException(var2);
      }

   }

   public static void checkDirectory(final File dir) throws IOException {
      try {
         class CheckDirectoryRunnable implements Runnable {
            public void run() {
               try {
                  if (!dir.isDirectory()) {
                     StoreDir.throwIOException(StoreLogger.logDirectoryNotADirectoryLoggable(dir.getPath()));
                  }

                  if (!dir.canRead() || !dir.canWrite()) {
                     StoreDir.throwIOException(StoreLogger.logNoAccessToDirectoryLoggable(dir.getPath()));
                  }

               } catch (IOException var2) {
                  throw new RuntimeException(var2);
               }
            }
         }

         StoreRCMUtils.accountAsGlobal((Runnable)(new CheckDirectoryRunnable()));
      } catch (Exception var2) {
         StoreRCMUtils.throwIOorRuntimeException(var2);
      }

   }
}
