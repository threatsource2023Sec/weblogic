package weblogic.diagnostics.archive;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileUtils {
   public static byte[] readFile(File file, int offset, int length) throws IOException {
      byte[] buf = new byte[length];
      return readFile(file, offset, length, buf);
   }

   public static byte[] readFile(File file, int offset, int length, byte[] buf) throws IOException {
      RandomAccessFile in = null;
      if (!file.exists()) {
         throw new FileNotFoundException(file.toString());
      } else if (offset < 0) {
         throw new IOException("Attemp to access data beyond file size");
      } else {
         try {
            in = new RandomAccessFile(file, "r");
            in.seek((long)offset);
            int n = 0;

            while(true) {
               if (n < length) {
                  int cnt = in.read(buf, n, length - n);
                  if (cnt >= 0) {
                     n += cnt;
                     continue;
                  }
               }

               byte[] tmp;
               if (n < length) {
                  tmp = new byte[n];
                  System.arraycopy(buf, 0, tmp, 0, n);
                  buf = tmp;
               }

               tmp = buf;
               return tmp;
            }
         } finally {
            if (in != null) {
               in.close();
            }

         }
      }
   }
}
