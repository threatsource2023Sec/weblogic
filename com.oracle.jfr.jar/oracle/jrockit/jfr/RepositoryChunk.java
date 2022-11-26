package oracle.jrockit.jfr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.Date;
import oracle.jrockit.log.Logger;

public class RepositoryChunk {
   private final Logger logger;
   private final Repository repository;
   private Date startTime;
   private Date endTime;
   private final File tempFile;
   private final File destFile;
   private volatile int refCount = 1;
   private long size;
   private FileChannel output;
   static boolean rename_overwrites;
   static boolean checked_overwrites;

   public RepositoryChunk(Repository repository, Logger logger) throws IOException {
      this.logger = logger;
      this.repository = repository;
      File dir = repository.getPath();
      String base = repository.filenameBase();
      String name = base;
      int i = 0;

      File t;
      File d;
      try {
         repository.lock();

         while(true) {
            d = new File(dir, name + ".jfr");
            if (d.createNewFile()) {
               t = new File(dir, name + ".jfr.part");
               if (!t.createNewFile()) {
                  d.delete();
                  throw new IOException("Could not create temporary file " + t);
               }
               break;
            }

            name = base + "_" + i++;
         }
      } finally {
         repository.unlock();
      }

      this.destFile = d;
      this.tempFile = t;
      this.startTime = new Date();
   }

   public RepositoryChunk(Repository repository, Logger logger, File tmpFile) {
      this.repository = repository;
      this.logger = logger;
      this.tempFile = tmpFile;
      String path = tmpFile.getPath();
      this.destFile = new File(path.substring(0, path.length() - ".part".length()));
      this.startTime = null;

      assert this.tempFile.exists();

      assert this.destFile.exists();

   }

   public File getOutputFile() throws IOException {
      if (this.isFinished()) {
         throw new IOException("Chunk finished");
      } else {
         return this.tempFile;
      }
   }

   public String getOutputPath() throws IOException {
      return this.getOutputFile().getPath();
   }

   public FileChannel getOutputChannel() throws IOException {
      if (this.output == null) {
         this.output = (new FileOutputStream(this.getOutputFile())).getChannel();
      }

      return this.output;
   }

   public void finish(long startTime, long endTime) throws IOException {
      this.finish(new Date(startTime), new Date(endTime));
   }

   public void finish(Date startTime, Date endTime) throws IOException {
      if (this.output != null) {
         this.output.force(true);
         this.output.close();
      }

      if (!checked_overwrites) {
         File tmp1 = File.createTempFile("test1", "tst");
         File tmp2 = File.createTempFile("test2", "tst");
         if (tmp1.renameTo(tmp2)) {
            rename_overwrites = true;
         }

         tmp1.delete();
         tmp2.delete();
         checked_overwrites = true;
      }

      assert this.tempFile != null;

      this.repository.lock();

      try {
         if (!rename_overwrites) {
            this.destFile.delete();
         }

         if (!this.tempFile.renameTo(this.destFile)) {
            throw new IOException("Could not rename " + this.tempFile + " to " + this.destFile);
         }
      } finally {
         this.repository.unlock();
      }

      this.endTime = endTime;
      this.startTime = startTime;
      this.size = this.destFile.length();
      this.logger.debug("Chunk finished: " + this.destFile);
   }

   public Date getStartTime() {
      return this.startTime;
   }

   public Date getEndTime() {
      return this.endTime;
   }

   private void delete(File f) {
      if (f.exists()) {
         if (!f.delete() && f.exists()) {
            f.deleteOnExit();
            this.logger.error("Repository chunk " + f + " could not be deleted");
         } else {
            this.logger.debug("Repository chunk " + f + " deleted");
         }
      }
   }

   private void destroy() {
      if (!this.isFinished()) {
         try {
            this.finish(0L, 0L);
         } catch (IOException var2) {
         }
      }

      this.delete(this.tempFile);
      this.delete(this.destFile);
   }

   public synchronized void use() {
      ++this.refCount;
   }

   public synchronized void release() {
      if (--this.refCount == 0) {
         this.destroy();
      }

   }

   public void finalize() {
      while(this.refCount > 0) {
         this.destroy();
      }

   }

   public long getSize() {
      return this.size;
   }

   public boolean isFinished() {
      return this.endTime != null;
   }

   public String toString() {
      return this.isFinished() ? this.destFile.getPath() : this.tempFile.getPath();
   }

   InputStream newInputStream() throws IOException {
      if (!this.isFinished()) {
         throw new IOException("Chunk not finished");
      } else {
         return new FileInputStream(this.destFile);
      }
   }
}
