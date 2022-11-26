package weblogic.management.filelock.internal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import weblogic.management.filelock.FileLockHandle;

public class FileLockHandleImpl implements FileLockHandle {
   private boolean isClosed = false;
   private final File originalFile;
   private final FileOutputStream stream;
   private final FileChannel channel;
   private final FileLock lock;

   public FileLockHandleImpl(File originalFile, FileOutputStream stream, FileChannel channel, FileLock lock) {
      this.originalFile = originalFile;
      this.stream = stream;
      this.channel = channel;
      this.lock = lock;
   }

   public synchronized void close() throws IOException {
      if (!this.isClosed) {
         this.isClosed = true;
         IOException channelException = null;

         try {
            this.lock.release();
            this.lock.close();
         } catch (IOException var4) {
            channelException = var4;
         }

         try {
            this.channel.close();
         } catch (IOException var3) {
            channelException = var3;
         }

         this.stream.close();
         if (channelException != null) {
            throw channelException;
         }
      }
   }

   public String toString() {
      return "FileLockHandleImpl(" + this.originalFile.getAbsolutePath() + "," + this.stream + "," + this.channel + "," + this.lock + "," + System.identityHashCode(this) + ")";
   }

   public void finalize() {
      try {
         this.close();
      } catch (IOException var2) {
      }

   }
}
