package weblogic.nodemanager.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class ConcurrentLockedFile extends ConcurrentFile {
   public ConcurrentLockedFile(String path) {
      super(path);
   }

   public void write(ByteBuffer bb) throws IOException {
      FileChannel fc = (new RandomAccessFile(this, "rws")).getChannel();

      try {
         FileLock fl = fc.lock();

         try {
            fc.truncate(0L);
            fc.write(bb);
            fc.force(false);
         } finally {
            fl.release();
         }
      } finally {
         fc.close();
      }

   }

   public int read(ByteBuffer bb) throws IOException {
      if (!this.exists()) {
         throw new FileNotFoundException("File not found: " + this.getPath());
      } else {
         FileChannel fc = (new RandomAccessFile(this, "rws")).getChannel();

         try {
            FileLock fl = fc.lock();

            try {
               int count = 0;

               while(true) {
                  int len;
                  if (bb.hasRemaining()) {
                     len = fc.read(bb);
                     if (len != -1) {
                        count += len;
                        continue;
                     }
                  }

                  len = count;
                  return len;
               }
            } finally {
               fl.release();
            }
         } finally {
            fc.close();
         }
      }
   }
}
