package weblogic.nodemanager.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ConcurrentUnixFile extends ConcurrentFile {
   private static final String PREFIX = "nodemgr";
   private static final String SUFFIX = "tmp";

   public ConcurrentUnixFile(String path) {
      super(path);
   }

   public void write(ByteBuffer bb) throws IOException {
      File tmpFile = File.createTempFile("nodemgr", "tmp", this.getParentFile());

      try {
         FileChannel fc = (new FileOutputStream(tmpFile)).getChannel();

         try {
            fc.truncate(0L);
            fc.write(bb);
            fc.force(false);
         } finally {
            fc.close();
         }

         if (!tmpFile.renameTo(this)) {
            throw new IOException("Could not overwrite file: " + this);
         }
      } finally {
         tmpFile.delete();
      }

   }

   public int read(ByteBuffer bb) throws IOException {
      FileChannel fc = (new FileInputStream(this)).getChannel();

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
         fc.close();
      }
   }
}
