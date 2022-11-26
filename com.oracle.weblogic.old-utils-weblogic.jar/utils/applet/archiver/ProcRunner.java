package utils.applet.archiver;

import java.io.InputStream;
import java.io.OutputStream;

class ProcRunner implements Runnable {
   InputStream i;
   OutputStream o;

   ProcRunner(InputStream i, OutputStream o) {
      this.i = i;
      this.o = o;
   }

   public void run() {
      try {
         byte[] buf = new byte[1024];

         int r;
         while((r = this.i.read(buf)) != -1) {
            this.o.write(buf, 0, r);
         }
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }
}
