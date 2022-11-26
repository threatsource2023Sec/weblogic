package weblogic.nodemanager.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import weblogic.nodemanager.server.NMServer;

public class Drainer extends Thread {
   private InputStream in;
   private BufferedOutputStream out;

   public Drainer(InputStream is, OutputStream out) {
      this.in = is;
      this.out = new BufferedOutputStream(out);
   }

   public void run() {
      int bRead = false;

      while(true) {
         try {
            int bRead = this.in.read();
            if (bRead == -1) {
               NMServer.nmLog.log(Level.FINE, "End of stream in process output drainer");
               break;
            }

            this.out.write(bRead);
         } catch (IOException var6) {
            NMServer.nmLog.log(Level.WARNING, "Exception redirecting process output", var6);
            break;
         }
      }

      try {
         this.out.flush();
      } catch (IOException var5) {
      }

      try {
         this.out.close();
      } catch (IOException var4) {
      }

      try {
         this.in.close();
      } catch (IOException var3) {
      }

   }
}
