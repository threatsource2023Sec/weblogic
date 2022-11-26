package weblogic.nodemanager.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import weblogic.diagnostics.debug.DebugLogger;

public class ProgressData {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugNodeManagerProgressTracker");
   public static final String PROGRESS_SERVER_DISPOSITION_FILL = "__**FILL**__";
   private final RandomAccessFile randomAccess;
   private final FileChannel channel;
   private final File baseFile;
   private FileLock heldLock;

   public ProgressData(File baseFile) throws IOException {
      this.randomAccess = new RandomAccessFile(baseFile, "rw");
      this.channel = this.randomAccess.getChannel();
      this.baseFile = baseFile;
   }

   public void close() {
      try {
         this.channel.close();
         this.randomAccess.close();
      } catch (IOException var2) {
      }

   }

   private void acquireLock() throws IOException {
      this.heldLock = this.channel.lock();
   }

   private void releaseLock() {
      FileLock localLock = this.heldLock;
      this.heldLock = null;
      if (localLock != null) {
         try {
            localLock.release();
         } catch (IOException var3) {
         }

      }
   }

   public void write(String currentProgress) throws IOException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Writing this progress to progress tracker file " + this.baseFile.getAbsolutePath() + "\n" + currentProgress);
      }

      this.acquireLock();

      try {
         long oldSize = this.channel.size();
         byte[] bytes = currentProgress.getBytes();
         long newSize = 8L + (long)bytes.length;
         ByteBuffer bb = ByteBuffer.allocate((int)newSize);
         bb.putInt(0);
         bb.putInt(bytes.length);
         bb.put(bytes);
         bb.rewind();
         this.channel.write(bb, 0L);
         if (newSize < oldSize) {
            this.channel.truncate(newSize);
         }

         this.channel.force(true);
      } finally {
         this.releaseLock();
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Finished writing progress to tracker file " + this.baseFile.getAbsolutePath());
      }

   }

   public String read() throws IOException {
      this.acquireLock();

      String var5;
      try {
         int fSize = (int)this.channel.size();
         ByteBuffer bb = ByteBuffer.allocate(fSize);
         this.channel.read(bb, 0L);
         bb.rewind();
         bb.getInt();
         int length = bb.getInt();
         byte[] bytes = new byte[length];
         bb.get(bytes);
         var5 = new String(bytes);
      } finally {
         this.releaseLock();
      }

      return var5;
   }

   public String toString() {
      return "ProgressData(" + this.randomAccess + "," + System.identityHashCode(this) + ")";
   }
}
