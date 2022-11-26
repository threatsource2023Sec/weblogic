package weblogic.server.channels;

import weblogic.kernel.AuditableThread;

public class ServerThread extends AuditableThread {
   private static int nexttid = 0;
   private final int tid = getNextTid();

   private static synchronized int getNextTid() {
      return ++nexttid;
   }

   public ServerThread(ThreadGroup tg, Runnable task, String name) {
      super(tg, task, name);
   }

   ServerThread(Runnable task, String name) {
      super(task, name);
   }

   public String getThreadID() {
      return String.valueOf(this.tid);
   }

   public int hashCode() {
      return this.tid;
   }
}
