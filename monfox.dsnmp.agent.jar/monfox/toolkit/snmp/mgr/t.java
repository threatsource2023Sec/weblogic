package monfox.toolkit.snmp.mgr;

import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.engine.SnmpBulkPDU;
import monfox.toolkit.snmp.engine.SnmpPDU;
import monfox.toolkit.snmp.engine.SnmpRequestPDU;
import monfox.toolkit.snmp.util.TimerItem;

class t extends TimerItem {
   private static Logger a;
   private SnmpPendingRequest b;
   private static final String c = "$Id: SnmpSessionImpl.java,v 1.79 2013/09/06 22:17:00 sking Exp $";

   public t(SnmpPendingRequest var1) {
      this(var1, (long)(var1.getPollingInterval() * 1000));
   }

   public t(SnmpPendingRequest var1, long var2) {
      super(var2 + System.currentTimeMillis());
      this.b = null;
      this.b = var1;
      if (a == null) {
         a = Logger.getInstance(a("\u000f3a\u0007]1;D\u001fQ2"));
      }

   }

   public void perform() {
      if (a.isDebugEnabled()) {
         a.debug(a("\u00195\u007f\u0002Z8|]\u0004X35c\f}+9`Q\u0014\u0004") + this.fireTime + ":" + System.currentTimeMillis() + ":" + this.b + "]");
      }

      if (this.b.isCancelled()) {
         a.debug(a("/3a\u0007]1;-\bU1?h\u0007X:8"));
      } else {
         SnmpResponseListener var1 = this.b.getResponseListener();

         TimerItem var2;
         try {
            var2 = null;
            synchronized(this.b.w()) {
               var2 = (TimerItem)this.b.k();
               if (var2 != null && !var2.cancelled) {
                  a.debug(a("/3a\u0007]1; \u0002@:17KD:.k\u0004F25c\f\u0014+5`\u000e[*("));

                  try {
                     SnmpPeer var4 = this.b.getPeer();
                     SnmpSession var5 = this.b.getSession();
                     if (var4.isCollectingStats()) {
                        var4.getStats().a();
                        var4.getStats().f();
                     }

                     if (var5.isCollectingStats()) {
                        var5.getStats().a();
                        var5.getStats().f();
                     }
                  } catch (Exception var7) {
                     a.error(a(":.\u007f\u0004F\u007f5cKG+=y\u0018\u0014<3a\u0007Q<(d\u0004Z"), var7);
                  }

                  this.b.t();
                  if (this.b.v() != null) {
                     this.b.v().t();
                  }

                  if (var1 != null) {
                     var1.handleTimeout(this.b);
                  }

                  this.b.c();
                  if (this.b.v() != null) {
                     this.b.v().c();
                  }
               }
            }
         } catch (Exception var10) {
            if (var1 != null) {
               var1.handleException(this.b, var10);
            }
         }

         var2 = null;
         Object var11;
         if (this.b.getType() == 165) {
            SnmpBulkPDU var3 = new SnmpBulkPDU();
            var3.setNonRepeaters(this.b.getNonRepeaters());
            var3.setMaxRepetitions(this.b.getMaxRepetitions());
            var11 = var3;
         } else {
            var11 = new SnmpRequestPDU();
         }

         try {
            this.b.setResponseVarBindList((SnmpVarBindList)null);
            this.b.f();
            this.b.getSession().sendPollPDU(this.b, (SnmpPDU)var11, this.b.getRequestVarBindList(), this.b.getParameters());
         } catch (SnmpException var9) {
            if (this.b.getResponseListener() != null) {
               this.b.getResponseListener().handleException(this.b, var9);
            }
         }

      }
   }

   static {
      n.getInstance();
      a = null;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 95;
               break;
            case 1:
               var10003 = 92;
               break;
            case 2:
               var10003 = 13;
               break;
            case 3:
               var10003 = 107;
               break;
            default:
               var10003 = 52;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
