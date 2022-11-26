package monfox.toolkit.snmp.util;

import monfox.log.Logger;

class g extends Thread {
   private Queue a;
   private Logger b = Logger.getInstance(a("\u000724iJ"), a("\u001653h"), a("\u0014\u000e\bO\u007f15\u0012V\u007f\"\u0005"));
   private static final String c = "$Id: WorkerThread.java,v 1.7 2010/02/24 22:53:01 sking Exp $";

   public g(ThreadGroup var1, String var2, Queue var3, int var4) {
      super(var1, var2);
      this.a = var3;
      this.setDaemon(true);
      this.setPriority(var4);
   }

   public void run() {
      int var3 = WorkItem.d;

      try {
         while(true) {
            if (this.a.isActive()) {
               WorkItem var1 = this.a.get();

               label26: {
                  try {
                     var1.perform();
                  } catch (Exception var4) {
                     this.b.error(a("&\u0019\u0019Aj7\b\u0015J:*\u000fZMn&\f@\u0004") + var1, var4);
                     break label26;
                  } catch (Error var5) {
                     this.b.error(a("&\u0013\bKhc\b\u0014\u0004s7\u0004\u0017\u001e:") + var1, var5);
                     break label26;
                  }

                  if (var3 != 0) {
                     break;
                  }
               }

               if (var3 == 0) {
                  continue;
               }
            }

            this.b.debug(a("4\u000e\bO\u007f1:") + this.getName() + a("\u001e[Z\u0004\u007f;\b\u000eMt$OT\n:"));
            break;
         }
      } catch (InterruptedException var6) {
         this.b.debug(a("4\u000e\bO\u007f1:") + this.getName() + a("\u001e[ZMt7\u0004\bVo3\u0015\u001f@6c\u0004\u0002Mn*\u000f\u001d\n4mA"));
      }

   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 67;
               break;
            case 1:
               var10003 = 97;
               break;
            case 2:
               var10003 = 122;
               break;
            case 3:
               var10003 = 36;
               break;
            default:
               var10003 = 26;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
