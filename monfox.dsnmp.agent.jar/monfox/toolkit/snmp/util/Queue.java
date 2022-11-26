package monfox.toolkit.snmp.util;

public class Queue implements ExecManager {
   protected String name;
   private ThreadGroup a;
   private int b;
   private int c;
   private int d;
   protected boolean isActive;
   protected WorkItem head;
   protected WorkItem tail;
   private static final String e = "$Id: Queue.java,v 1.12 2010/01/28 21:40:41 sking Exp $";

   public Queue(String var1, int var2, int var3) {
      int var6 = WorkItem.d;
      super();
      this.b = 0;
      this.c = 0;
      this.d = 0;
      this.isActive = true;
      this.head = null;
      this.tail = null;
      this.name = var1;
      this.a = new ThreadGroup(var1);
      this.a.setDaemon(true);
      int var4 = 0;

      while(true) {
         if (var4 < var2) {
            g var5 = new g(this.a, var1 + var4, this, var3);
            var5.start();
            ++var4;
            if (var6 != 0) {
               break;
            }

            if (var6 == 0) {
               continue;
            }
         }

         this.b = var2;
         break;
      }

   }

   public boolean isActive() {
      return this.isActive;
   }

   public synchronized void shutdown() {
      this.isActive = false;
      this.notifyAll();
   }

   public synchronized boolean isEmpty() {
      return this.head == null;
   }

   public synchronized boolean isIdle() {
      return this.c == this.b && this.isEmpty();
   }

   public int getNumWorkers() {
      return this.b;
   }

   public int getNumAvailableWorkers() {
      return this.c;
   }

   public int getNumQueuedWorkItems() {
      return this.d;
   }

   public synchronized WorkItem get() throws InterruptedException {
      int var2 = WorkItem.d;
      ++this.c;

      WorkItem var10000;
      while(true) {
         if (this.isActive) {
            var10000 = this.head;
            if (var2 != 0) {
               break;
            }

            if (var10000 == null) {
               this.wait(2000L);
               if (var2 == 0) {
                  continue;
               }
            }
         }

         --this.c;
         if (!this.isActive) {
            throw new InterruptedException(a("?D8\u0006?:\u000b#\u0003.-Y8\u0018*<N."));
         }

         var10000 = this.head;
         break;
      }

      WorkItem var1;
      label21: {
         var1 = var10000;
         this.head = this.head.a;
         if (this.head == null) {
            this.tail = null;
            if (var2 == 0) {
               break label21;
            }
         }

         this.head.b = null;
      }

      --this.d;
      return var1;
   }

   public void schedule(Runnable var1) {
      this.put(new RunnableWorkItem(var1));
   }

   public synchronized void put(WorkItem var1) {
      label11: {
         var1.a = null;
         var1.b = this.tail;
         if (this.head == null) {
            this.head = var1;
            this.tail = var1;
            if (WorkItem.d == 0) {
               break label11;
            }
         }

         this.tail.a = var1;
         this.tail = var1;
      }

      ++this.d;
      this.notify();
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 72;
               break;
            case 1:
               var10003 = 43;
               break;
            case 2:
               var10003 = 74;
               break;
            case 3:
               var10003 = 109;
               break;
            default:
               var10003 = 90;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   private class RunnableWorkItem extends WorkItem {
      private Runnable a;

      public RunnableWorkItem(Runnable var2) {
         this.a = var2;
      }

      public void perform() {
         if (this.a != null) {
            this.a.run();
         }

      }
   }
}
