package weblogic.store.io.file.direct;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.concurrent.Callable;
import weblogic.store.PersistentStoreException;
import weblogic.store.common.StoreDebug;
import weblogic.store.common.StoreRCMUtils;

public final class DirectIOManager {
   private static final String FILE_STORE_LIB_NAME = "wlfileio3";
   private static final String JDK_IO_NAME = "NIO";
   public String libraryName;
   private ByteBuffer zeroPage;
   private final IONative ioNative;
   private final boolean nativeRequired;
   private static final DirectIOManager fileSingleton = new DirectIOManager(false, "wlfileio3", DirectIONativeImpl.getSingleton());
   private final boolean nativeFileAvailable;
   private final UnsatisfiedLinkError unsatisfiedLinkError;

   DirectIOManager(boolean nativeRequired, String library, IONative ioNative) {
      this.ioNative = ioNative;
      this.libraryName = library;
      this.nativeRequired = nativeRequired;
      boolean debug = StoreDebug.cacheDebug.isDebugEnabled();
      boolean tempNative = false;
      UnsatisfiedLinkError tempULE = null;

      try {
         System.loadLibrary(this.libraryName);
         tempNative = true;
      } catch (UnsatisfiedLinkError var8) {
         tempULE = var8;
         if (debug) {
            StoreDebug.cacheDebug.debug(this.libraryName, var8);
         }
      }

      this.nativeFileAvailable = tempNative;
      this.unsatisfiedLinkError = tempULE;
   }

   DirectIOManager checkNativePersistentStoreException() throws PersistentStoreException {
      if (this.nativeRequired && !this.nativeFileAvailable) {
         throw new PersistentStoreException("failed to load " + this.libraryName, this.unsatisfiedLinkError);
      } else {
         return this;
      }
   }

   DirectIOManager checkNativeIOException() throws IOException {
      if (this.nativeRequired && !this.nativeFileAvailable) {
         throw new IOException("failed to load " + this.libraryName, this.unsatisfiedLinkError);
      } else {
         return this;
      }
   }

   public static DirectIOManager getManager() {
      return fileSingleton;
   }

   public static DirectIOManager getMemMapManager() {
      return fileSingleton;
   }

   public static DirectIOManager getFileManager() {
      return fileSingleton;
   }

   public static IONative getFileMappingNative() {
      return DirectIONativeImpl.getSingleton();
   }

   public static DirectIOManager getOpenFileManager() {
      return fileSingleton;
   }

   public static DirectIOManager getFileMemoryManager() {
      return fileSingleton;
   }

   public boolean nativeFileCodeAvailable() {
      return this.nativeFileAvailable;
   }

   public int checkAlignment(File testFile) {
      if (!this.nativeFileAvailable) {
         return -1;
      } else {
         try {
            return this.ioNative.checkAlignment(testFile.getCanonicalPath());
         } catch (IOException var3) {
            return -1;
         }
      }
   }

   public ByteBuffer allocateDirectBuffer(int size) {
      return this.nativeFileAvailable ? this.ioNative.allocate(size) : ByteBuffer.allocateDirect(size);
   }

   public void zeroBuffer(ByteBuffer buf) {
      this.zeroBuffer(buf, buf.remaining());
   }

   public void zeroBuffer(ByteBuffer buf, int length) {
      if (this.nativeFileAvailable) {
         this.ioNative.fillBuffer(buf, buf.position(), length, (byte)0);
         buf.position(buf.position() + length);
      } else {
         ByteBuffer zeroBuf = this.getZeroBuffer(8192);
         int remaining = length;

         while(remaining > 0) {
            int chunk = Math.min(8192, remaining);
            remaining -= chunk;
            zeroBuf.limit(chunk);
            zeroBuf.position(0);
            buf.put(zeroBuf);
         }
      }

   }

   public void freeDirectBuffer(ByteBuffer buf) {
      if (this.nativeFileAvailable) {
         this.ioNative.free(buf);
      }

   }

   public FileChannel openBasic(final File file, final String mode, boolean exclusive) throws IOException {
      if (this.nativeFileAvailable) {
         return new DirectFileChannel(this.ioNative, file, mode, exclusive);
      } else if (this.nativeRequired) {
         throw new IOException("can not open " + file.getCanonicalPath() + " failed to load " + this.libraryName, this.unsatisfiedLinkError);
      } else if (mode.equals("rwD")) {
         throw new IOException("Direct I/O is not supported on this platform");
      } else {
         final FileChannel channel;
         try {
            class OpenBasicCallable implements Callable {
               public FileChannel call() throws FileNotFoundException {
                  return (new RandomAccessFile(file, mode)).getChannel();
               }
            }

            channel = (FileChannel)StoreRCMUtils.accountAsGlobal((Callable)(new OpenBasicCallable()));
         } catch (RuntimeException var8) {
            Throwable t = var8.getCause();
            if (t instanceof IOException) {
               throw (IOException)t;
            }

            throw var8;
         } catch (Exception var9) {
            if (var9 instanceof IOException) {
               throw (IOException)var9;
            }

            throw new RuntimeException(var9);
         }

         if (exclusive) {
            FileLock lock = channel.tryLock();
            if (lock == null) {
               try {
                  class OpenBasicCloseRunable implements Runnable {
                     public void run() {
                        try {
                           channel.close();
                        } catch (Exception var2) {
                           throw new RuntimeException(var2);
                        }
                     }
                  }

                  StoreRCMUtils.accountAsGlobal((Runnable)(new OpenBasicCloseRunable()));
               } catch (Exception var7) {
                  StoreRCMUtils.throwIOorRuntimeException(var7);
               }

               throw new IOException("Could not acquire an NIO lock for " + file + ". A file store running in another process may be holding the lock.");
            }
         }

         return channel;
      }
   }

   public FileChannel openEnhanced(final File file, final String mode, final boolean exclusive, final String[] configurationKeys, final String[] configurationValues) throws IOException {
      if (!this.nativeFileAvailable) {
         if (this.nativeRequired) {
            throw new IOException("can not open " + file.getCanonicalPath() + " failed to load " + this.libraryName, this.unsatisfiedLinkError);
         } else {
            throw new IOException("Direct I/O is not supported on this platform");
         }
      } else {
         FileChannel channel = null;

         try {
            class OpenEnhancedCallable implements Callable {
               public FileChannel call() throws IOException {
                  return new DirectFileChannel(DirectIOManager.this.ioNative, file, mode, exclusive, configurationKeys, configurationValues);
               }
            }

            channel = (FileChannel)StoreRCMUtils.accountAsGlobal((Callable)(new OpenEnhancedCallable()));
         } catch (Exception var8) {
            StoreRCMUtils.throwIOorRuntimeException(var8);
         }

         return channel;
      }
   }

   public String getDriver() {
      return this.nativeFileAvailable ? this.libraryName : "NIO";
   }

   public String toString() {
      return this.nativeFileAvailable ? this.libraryName + " nativeRequired=" + this.nativeRequired + (this.ioNative == null ? " null ioNative!" : this.ioNative.getClass().getName()) : "NIO nativeRequired=" + this.nativeRequired + (this.ioNative == null ? " null ioNative!" : this.ioNative.getClass().getName());
   }

   public synchronized ByteBuffer getZeroBuffer(int blockSize) {
      if (this.zeroPage == null) {
         this.zeroPage = this.allocateDirectBuffer(8192);
      }

      this.zeroPage.limit(blockSize);
      this.zeroPage.position(0);
      return this.zeroPage.slice();
   }
}
