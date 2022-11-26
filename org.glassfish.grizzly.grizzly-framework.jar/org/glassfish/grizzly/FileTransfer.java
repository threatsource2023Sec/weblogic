package org.glassfish.grizzly;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import org.glassfish.grizzly.asyncqueue.WritableMessage;

public class FileTransfer implements WritableMessage {
   private FileChannel fileChannel;
   private long len;
   private long pos;

   public FileTransfer(File f) {
      this(f, 0L, f.length());
   }

   public FileTransfer(File f, long pos, long len) {
      if (f == null) {
         throw new IllegalArgumentException("f cannot be null.");
      } else if (!f.exists()) {
         throw new IllegalArgumentException("File " + f.getAbsolutePath() + " does not exist.");
      } else if (!f.canRead()) {
         throw new IllegalArgumentException("File " + f.getAbsolutePath() + " is not readable.");
      } else if (f.isDirectory()) {
         throw new IllegalArgumentException("File " + f.getAbsolutePath() + " is a directory.");
      } else if (pos < 0L) {
         throw new IllegalArgumentException("The pos argument cannot be negative.");
      } else if (len < 0L) {
         throw new IllegalArgumentException("The len argument cannot be negative.");
      } else if (pos > f.length()) {
         throw new IllegalArgumentException("Illegal offset");
      } else if (f.length() - pos < len) {
         throw new IllegalArgumentException("Specified length exceeds available bytes to transfer.");
      } else {
         this.pos = pos;
         this.len = len;

         try {
            this.fileChannel = (new FileInputStream(f)).getChannel();
         } catch (FileNotFoundException var7) {
            throw new IllegalStateException(var7);
         }
      }
   }

   public long writeTo(WritableByteChannel c) throws IOException {
      long written = this.fileChannel.transferTo(this.pos, this.len, c);
      this.pos += written;
      this.len -= written;
      return written;
   }

   public boolean hasRemaining() {
      return this.len != 0L;
   }

   public int remaining() {
      return this.len > 2147483647L ? Integer.MAX_VALUE : (int)this.len;
   }

   public boolean release() {
      try {
         this.fileChannel.close();
      } catch (IOException var5) {
      } finally {
         this.fileChannel = null;
      }

      return true;
   }

   public boolean isExternal() {
      return true;
   }
}
