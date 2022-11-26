package weblogic.management.commo;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;

public class CommoProcess implements Runnable {
   private boolean isErr;
   private String subname = "";
   private InputStream isem;
   private String proName;
   private String log = null;
   private boolean doDrain;
   private static HashMap processes = new HashMap();

   CommoProcess(boolean isError, InputStream is, String processName, boolean drain) {
      this.isErr = isError;
      if (this.isErr) {
         this.subname = processName + " - STDERROR";
      } else {
         this.subname = processName + " - STDOut";
      }

      this.isem = is;
      this.proName = processName;
      this.doDrain = drain;
   }

   CommoProcess(boolean isError, InputStream is, String processName, boolean drain, String theLog) {
      this.isErr = isError;
      if (this.isErr) {
         this.subname = processName + " - STDERROR";
      } else {
         this.subname = processName + " - STDOut";
      }

      this.isem = is;
      this.proName = processName;
      this.doDrain = drain;
      this.log = theLog;
   }

   public void run() {
      try {
         BufferedReader br = new BufferedReader(new InputStreamReader(this.isem));
         FileOutputStream fos = null;
         BufferedOutputStream bos = null;
         String prefix = "";
         PrintStream out;
         if (this.log != null) {
            fos = new FileOutputStream(new File(this.log));
            bos = new BufferedOutputStream(fos);
            out = new PrintStream(bos, true);
         } else {
            out = System.out;
            prefix = this.proName + ": ";
         }

         try {
            String line;
            try {
               while((line = br.readLine()) != null) {
                  if (this.doDrain) {
                     out.println(prefix + line);
                  }
               }
            } catch (IOException var29) {
               if (this.doDrain) {
                  out.println(prefix + "IOException when running CommoProcess");
                  var29.printStackTrace();
               }
            }
         } finally {
            if (this.doDrain) {
               out.println(prefix + "Stopped draining " + this.proName);
            }

            if (this.log != null) {
               out.flush();
               out.close();

               try {
                  bos.flush();
               } catch (IOException var28) {
               }

               try {
                  bos.close();
               } catch (IOException var27) {
               }

               try {
                  fos.flush();
               } catch (IOException var26) {
               }

               try {
                  fos.close();
               } catch (IOException var25) {
               }
            }

         }
      } catch (Exception var31) {
         System.out.println("Error running the Commo process " + var31);
      }

   }

   static void destroyProcesses(String processName) {
      Iterator iter;
      Process pro;
      if (processName == null) {
         iter = processes.keySet().iterator();

         while(iter.hasNext()) {
            pro = (Process)iter.next();
            pro.destroy();
         }
      } else {
         iter = processes.keySet().iterator();
         pro = null;

         while(iter.hasNext()) {
            pro = (Process)iter.next();
            String pName = (String)processes.get(pro);
            if (pName.equals(processName)) {
               System.out.println("Killing process " + pName);
               pro.destroy();
               break;
            }
         }

         if (pro != null) {
            processes.remove(pro);
         }
      }

   }

   static Process getProcess(String processName) {
      try {
         Iterator p = processes.keySet().iterator();
         Process pro = null;

         while(p.hasNext()) {
            pro = (Process)p.next();
            String pName = (String)processes.get(pro);
            if (pName.equals(processName)) {
               return pro;
            }
         }
      } catch (Exception var4) {
         System.out.println("Error getting the process " + var4);
      }

      return null;
   }

   public static void startIOThreads(Process process, String processName, boolean drain, String log) {
      Thread outthread = new Thread(new CommoProcess(false, process.getInputStream(), processName, drain, log));
      outthread.setDaemon(true);
      outthread.start();
      Thread errthread = new Thread(new CommoProcess(true, process.getErrorStream(), processName, drain, log));
      errthread.setDaemon(true);
      errthread.start();
      processes.put(process, processName);
   }

   public static void startIOThreads(Process process, String processName, boolean drain) {
      Thread outthread = new Thread(new CommoProcess(false, process.getInputStream(), processName, drain));
      outthread.setDaemon(true);
      outthread.start();
      Thread errthread = new Thread(new CommoProcess(true, process.getErrorStream(), processName, drain));
      errthread.setDaemon(true);
      errthread.start();
      processes.put(process, processName);
   }
}
