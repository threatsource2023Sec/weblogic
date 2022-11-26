package weblogic.servlet.utils.fileupload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

class DeferredFileOutputStream extends ThresholdingOutputStream {
   private ByteArrayOutputStream memoryOutputStream;
   private OutputStream currentOutputStream;
   private File outputFile;
   private String prefix;
   private String suffix;
   private File directory;
   private boolean closed;

   public DeferredFileOutputStream(int threshold, File outputFile) {
      super(threshold);
      this.closed = false;
      this.outputFile = outputFile;
      this.memoryOutputStream = new ByteArrayOutputStream();
      this.currentOutputStream = this.memoryOutputStream;
   }

   public DeferredFileOutputStream(int threshold, String prefix, String suffix, File directory) {
      this(threshold, (File)null);
      if (prefix == null) {
         throw new IllegalArgumentException("Temporary file prefix is missing");
      } else {
         this.prefix = prefix;
         this.suffix = suffix;
         this.directory = directory;
      }
   }

   protected OutputStream getStream() throws IOException {
      return this.currentOutputStream;
   }

   protected void thresholdReached() throws IOException {
      if (this.prefix != null) {
         this.outputFile = File.createTempFile(this.prefix, this.suffix, this.directory);
      }

      FileOutputStream fos = new FileOutputStream(this.outputFile);
      this.memoryOutputStream.writeTo(fos);
      this.currentOutputStream = fos;
      this.memoryOutputStream = null;
   }

   public boolean isInMemory() {
      return !this.isThresholdExceeded();
   }

   public byte[] getData() {
      return this.memoryOutputStream != null ? this.memoryOutputStream.toByteArray() : null;
   }

   public File getFile() {
      return this.outputFile;
   }

   public void close() throws IOException {
      super.close();
      this.closed = true;
   }

   public void writeTo(OutputStream out) throws IOException {
      if (!this.closed) {
         throw new IOException("Stream not closed");
      } else {
         if (this.isInMemory()) {
            this.memoryOutputStream.writeTo(out);
         } else {
            FileInputStream fis = new FileInputStream(this.outputFile);
            Streams.copy(fis, out, false);
         }

      }
   }
}
