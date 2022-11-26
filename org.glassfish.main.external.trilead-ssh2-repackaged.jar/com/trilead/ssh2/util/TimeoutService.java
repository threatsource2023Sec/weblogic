package com.trilead.ssh2.util;

import com.trilead.ssh2.log.Logger;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.LinkedList;

public class TimeoutService {
   private static final Logger log;
   private static final LinkedList todolist;
   private static Thread timeoutThread;
   // $FF: synthetic field
   static Class class$com$trilead$ssh2$util$TimeoutService;

   public static final TimeoutToken addTimeoutHandler(long runTime, Runnable handler) {
      TimeoutToken token = new TimeoutToken(runTime, handler);
      synchronized(todolist) {
         todolist.add(token);
         Collections.sort(todolist);
         if (timeoutThread != null) {
            timeoutThread.interrupt();
         } else {
            timeoutThread = new TimeoutThread();
            timeoutThread.setDaemon(true);
            timeoutThread.start();
         }

         return token;
      }
   }

   public static final void cancelTimeoutHandler(TimeoutToken token) {
      synchronized(todolist) {
         todolist.remove(token);
         if (timeoutThread != null) {
            timeoutThread.interrupt();
         }

      }
   }

   // $FF: synthetic method
   static Class class$(String x0) {
      try {
         return Class.forName(x0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   static {
      log = Logger.getLogger(class$com$trilead$ssh2$util$TimeoutService == null ? (class$com$trilead$ssh2$util$TimeoutService = class$("com.trilead.ssh2.util.TimeoutService")) : class$com$trilead$ssh2$util$TimeoutService);
      todolist = new LinkedList();
      timeoutThread = null;
   }

   private static class TimeoutThread extends Thread {
      private TimeoutThread() {
      }

      public void run() {
         synchronized(TimeoutService.todolist) {
            while(TimeoutService.todolist.size() != 0) {
               long now = System.currentTimeMillis();
               TimeoutToken tt = (TimeoutToken)TimeoutService.todolist.getFirst();
               if (tt.runTime > now) {
                  try {
                     TimeoutService.todolist.wait(tt.runTime - now);
                  } catch (InterruptedException var8) {
                  }
               } else {
                  TimeoutService.todolist.removeFirst();

                  try {
                     tt.handler.run();
                  } catch (Exception var9) {
                     StringWriter sw = new StringWriter();
                     var9.printStackTrace(new PrintWriter(sw));
                     TimeoutService.log.log(20, "Exeception in Timeout handler:" + var9.getMessage() + "(" + sw.toString() + ")");
                  }
               }
            }

            TimeoutService.timeoutThread = null;
         }
      }

      // $FF: synthetic method
      TimeoutThread(Object x0) {
         this();
      }
   }

   public static class TimeoutToken implements Comparable {
      private long runTime;
      private Runnable handler;

      private TimeoutToken(long runTime, Runnable handler) {
         this.runTime = runTime;
         this.handler = handler;
      }

      public int compareTo(Object o) {
         TimeoutToken t = (TimeoutToken)o;
         if (this.runTime > t.runTime) {
            return 1;
         } else {
            return this.runTime == t.runTime ? 0 : -1;
         }
      }

      // $FF: synthetic method
      TimeoutToken(long x0, Runnable x1, Object x2) {
         this(x0, x1);
      }
   }
}
