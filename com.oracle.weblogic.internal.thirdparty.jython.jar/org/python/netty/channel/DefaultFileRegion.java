package org.python.netty.channel;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import org.python.netty.util.AbstractReferenceCounted;
import org.python.netty.util.IllegalReferenceCountException;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public class DefaultFileRegion extends AbstractReferenceCounted implements FileRegion {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultFileRegion.class);
   private final File f;
   private final long position;
   private final long count;
   private long transferred;
   private FileChannel file;

   public DefaultFileRegion(FileChannel file, long position, long count) {
      if (file == null) {
         throw new NullPointerException("file");
      } else if (position < 0L) {
         throw new IllegalArgumentException("position must be >= 0 but was " + position);
      } else if (count < 0L) {
         throw new IllegalArgumentException("count must be >= 0 but was " + count);
      } else {
         this.file = file;
         this.position = position;
         this.count = count;
         this.f = null;
      }
   }

   public DefaultFileRegion(File f, long position, long count) {
      if (f == null) {
         throw new NullPointerException("f");
      } else if (position < 0L) {
         throw new IllegalArgumentException("position must be >= 0 but was " + position);
      } else if (count < 0L) {
         throw new IllegalArgumentException("count must be >= 0 but was " + count);
      } else {
         this.position = position;
         this.count = count;
         this.f = f;
      }
   }

   public boolean isOpen() {
      return this.file != null;
   }

   public void open() throws IOException {
      if (!this.isOpen() && this.refCnt() > 0) {
         this.file = (new RandomAccessFile(this.f, "r")).getChannel();
      }

   }

   public long position() {
      return this.position;
   }

   public long count() {
      return this.count;
   }

   /** @deprecated */
   @Deprecated
   public long transfered() {
      return this.transferred;
   }

   public long transferred() {
      return this.transferred;
   }

   public long transferTo(WritableByteChannel target, long position) throws IOException {
      long count = this.count - position;
      if (count >= 0L && position >= 0L) {
         if (count == 0L) {
            return 0L;
         } else if (this.refCnt() == 0) {
            throw new IllegalReferenceCountException(0);
         } else {
            this.open();
            long written = this.file.transferTo(this.position + position, count, target);
            if (written > 0L) {
               this.transferred += written;
            }

            return written;
         }
      } else {
         throw new IllegalArgumentException("position out of range: " + position + " (expected: 0 - " + (this.count - 1L) + ')');
      }
   }

   protected void deallocate() {
      FileChannel file = this.file;
      if (file != null) {
         this.file = null;

         try {
            file.close();
         } catch (IOException var3) {
            if (logger.isWarnEnabled()) {
               logger.warn("Failed to close a file.", (Throwable)var3);
            }
         }

      }
   }

   public FileRegion retain() {
      super.retain();
      return this;
   }

   public FileRegion retain(int increment) {
      super.retain(increment);
      return this;
   }

   public FileRegion touch() {
      return this;
   }

   public FileRegion touch(Object hint) {
      return this;
   }
}
