package jnr.posix.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import jnr.posix.POSIXHandler;

public class ExecIt {
   protected final POSIXHandler handler;

   public ExecIt(POSIXHandler handler) {
      this.handler = handler;
   }

   public int runAndWait(String... args) throws IOException, InterruptedException {
      return this.runAndWait(this.handler.getOutputStream(), args);
   }

   public int runAndWait(OutputStream output, String... args) throws IOException, InterruptedException {
      return this.runAndWait(output, this.handler.getErrorStream(), args);
   }

   public int runAndWait(OutputStream output, OutputStream error, String... args) throws IOException, InterruptedException {
      Process process = this.run(args);
      this.handleStreams(process, this.handler.getInputStream(), output, error);
      return process.waitFor();
   }

   public Process run(String... args) throws IOException {
      File cwd = this.handler.getCurrentWorkingDirectory();
      return Runtime.getRuntime().exec(args, this.handler.getEnv(), cwd);
   }

   private void handleStreams(Process p, InputStream in, OutputStream out, OutputStream err) throws IOException {
      InputStream pOut = p.getInputStream();
      InputStream pErr = p.getErrorStream();
      OutputStream pIn = p.getOutputStream();
      StreamPumper t1 = new StreamPumper(pOut, out, false);
      StreamPumper t2 = new StreamPumper(pErr, err, false);
      StreamPumper t3 = new StreamPumper(in, pIn, true);
      t1.start();
      t2.start();
      t3.start();

      try {
         t1.join();
      } catch (InterruptedException var18) {
      }

      try {
         t2.join();
      } catch (InterruptedException var17) {
      }

      t3.quit();

      try {
         err.flush();
      } catch (IOException var16) {
      }

      try {
         out.flush();
      } catch (IOException var15) {
      }

      try {
         pIn.close();
      } catch (IOException var14) {
      }

      try {
         pOut.close();
      } catch (IOException var13) {
      }

      try {
         pErr.close();
      } catch (IOException var12) {
      }

   }

   private static class StreamPumper extends Thread {
      private InputStream in;
      private OutputStream out;
      private boolean onlyIfAvailable;
      private volatile boolean quit;
      private final Object waitLock = new Object();

      StreamPumper(InputStream in, OutputStream out, boolean avail) {
         this.in = in;
         this.out = out;
         this.onlyIfAvailable = avail;
      }

      public void run() {
         byte[] buf = new byte[1024];
         boolean hasReadSomething = false;

         try {
            while(!this.quit) {
               if (this.onlyIfAvailable && !hasReadSomething) {
                  if (this.in.available() == 0) {
                     synchronized(this.waitLock) {
                        this.waitLock.wait(10L);
                        continue;
                     }
                  }

                  hasReadSomething = true;
               }

               int numRead;
               if ((numRead = this.in.read(buf)) == -1) {
                  break;
               }

               this.out.write(buf, 0, numRead);
            }
         } catch (Exception var16) {
         } finally {
            if (this.onlyIfAvailable) {
               try {
                  this.out.close();
               } catch (IOException var14) {
               }
            }

         }

      }

      public void quit() {
         this.quit = true;
         synchronized(this.waitLock) {
            this.waitLock.notify();
         }
      }
   }
}
