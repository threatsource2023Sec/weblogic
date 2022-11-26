package org.python.core.io;

import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import org.python.core.Py;

public abstract class RawIOBase extends IOBase {
   public ByteBuffer read(int size) {
      if (size < 0) {
         return this.readall();
      } else {
         ByteBuffer buf = ByteBuffer.allocate(size);
         this.readinto(buf);
         buf.flip();
         return buf;
      }
   }

   public ByteBuffer readall() {
      ByteBuffer all = ByteBuffer.allocate(8192);
      ByteBuffer readBuffer = ByteBuffer.allocate(8192);
      int readCount = false;

      int readCount;
      while((readCount = this.readinto(readBuffer)) > 0) {
         if (all.remaining() < readCount) {
            long newSize = (long)all.position() + (long)readCount;
            if (newSize > 2147483647L) {
               throw Py.OverflowError("requested number of bytes is more than a Python string can hold");
            }

            ByteBuffer old = all;
            all = ByteBuffer.allocate(Math.max(all.capacity() * 2, (int)newSize));
            old.flip();
            all.put(old);
         }

         readBuffer.flip();
         all.put(readBuffer);
         readBuffer.clear();
      }

      all.flip();
      return all;
   }

   public int readinto(ByteBuffer buf) {
      this.unsupported("readinto");
      return -1;
   }

   public long readinto(ByteBuffer[] bufs) {
      long count = 0L;
      ByteBuffer[] var4 = bufs;
      int var5 = bufs.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ByteBuffer buf = var4[var6];
         if (buf.hasRemaining()) {
            int bufCount;
            if ((bufCount = this.readinto(buf)) == 0) {
               break;
            }

            count += (long)bufCount;
         }
      }

      return count;
   }

   public int write(ByteBuffer buf) {
      this.unsupported("write");
      return -1;
   }

   public long write(ByteBuffer[] bufs) {
      long count = 0L;
      ByteBuffer[] var4 = bufs;
      int var5 = bufs.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ByteBuffer buf = var4[var6];
         if (buf.hasRemaining()) {
            int bufCount;
            if ((bufCount = this.write(buf)) == 0) {
               break;
            }

            count += (long)bufCount;
         }
      }

      return count;
   }

   public RawIOBase fileno() {
      this.checkClosed();
      return this;
   }

   public abstract Channel getChannel();
}
