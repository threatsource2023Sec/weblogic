package com.octetstring.vde.util;

import java.io.BufferedWriter;
import java.io.PrintWriter;

public class LogFlusher extends Thread {
   PrintWriter consoleWriter = null;
   BufferedWriter accessWriter = null;
   PrintWriter logWriter = null;

   public LogFlusher() {
      this.setPriority(2);
   }

   public void run() {
      while(true) {
         Logger.getInstance().flush();

         try {
            sleep(5000L);
         } catch (InterruptedException var2) {
         }
      }
   }
}
