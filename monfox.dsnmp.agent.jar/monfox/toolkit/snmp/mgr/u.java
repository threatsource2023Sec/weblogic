package monfox.toolkit.snmp.mgr;

import monfox.log.Logger;
import monfox.toolkit.snmp.util.TimerItem;

class u extends TimerItem {
   private static Logger a = null;
   private SnmpPendingRequest b = null;

   public u(SnmpPendingRequest var1, long var2) {
      super(var2 + System.currentTimeMillis());
      this.b = var1;
      if (a == null) {
         a = Logger.getInstance(a("Vh),8wu\r=2o"));
      }

   }

   public void perform() {
      boolean var5 = SnmpSession.B;
      if (a.isDebugEnabled()) {
         a.debug(a("Dh6 9e!\u0010 :gn1=\u001evd)swY") + this.fireTime + ":" + System.currentTimeMillis() + ":" + this.b + "]");
      }

      TimerItem var1 = (TimerItem)this.b.l();
      if (var1 != null) {
         synchronized(this.b.w()) {
            TimerItem var3 = (TimerItem)this.b.k();
            if (var3 == null || var3.cancelled) {
               a.debug(a("lnd=>od+<#\"h0,:\"g+;wRShi9mud9%mb!:$ko#"));
               return;
            }
         }
      }

      SnmpResponseListener var2 = this.b.getResponseListener();

      try {
         if (this.b.getRetryCount() >= this.b.getPeer().getMaxRetries()) {
            if (a.isDebugEnabled()) {
               a.debug(a("dh*(;/u-$2mt0ewko2&<ko#i#kl!&\"v!,(9fm!;m") + this.b);
            }

            try {
               SnmpPeer var9 = this.b.getPeer();
               SnmpSession var4 = this.b.getSession();
               if (var9.isCollectingStats()) {
                  var9.getStats().a();
                  var9.getStats().f();
               }

               if (var4.isCollectingStats()) {
                  var4.getStats().a();
                  var4.getStats().f();
               }
            } catch (Exception var6) {
               a.error(a("gs6&%\"h*i$v`0:wan(%2au-&9"), var6);
            }

            this.b.t();
            if (this.b.v() != null) {
               this.b.v().t();
            }

            label64: {
               if (var2 != null) {
                  var2.handleTimeout(this.b);
                  if (!var5) {
                     break label64;
                  }
               }

               if (this.b.v() != null) {
                  SnmpResponseListener var10 = this.b.v().getResponseListener();
                  if (var10 != null) {
                     var10.handleTimeout(this.b);
                  }
               }
            }

            this.b.c();
            if (this.b.v() == null) {
               return;
            }

            this.b.v().c();
            if (!var5) {
               return;
            }
         }

         this.b.getSession().retryPDU(this.b);
      } catch (Exception var7) {
         if (var2 != null) {
            var2.handleException(this.b, var7);
         }
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
               var10003 = 2;
               break;
            case 1:
               var10003 = 1;
               break;
            case 2:
               var10003 = 68;
               break;
            case 3:
               var10003 = 73;
               break;
            default:
               var10003 = 87;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
