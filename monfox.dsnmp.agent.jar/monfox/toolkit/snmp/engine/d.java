package monfox.toolkit.snmp.engine;

import java.io.IOException;
import monfox.log.Logger;
import monfox.toolkit.snmp.v3.SnmpSecurityCoderException;

class d implements Runnable {
   private static Logger a = null;
   private SnmpEngine b = null;
   private SnmpMessageProcessor c = null;
   private TransportProvider d = null;
   private byte[] e = new byte[131072];
   private static final String f = "$Id: SnmpEngine.java,v 1.21 2011/01/26 21:26:38 sking Exp $";

   public d(SnmpEngine var1, TransportProvider var2) {
      this.d = var2;
      this.b = var1;
      this.c = var1.getMessageProcessor();
      if (a == null) {
         a = Logger.getInstance(a("cZYzQBUZyu_F@]jB_Qx"));
      }

   }

   public void run() {
      boolean var2 = SnmpPDU.i;

      try {
         while(this.d.isActive()) {
            try {
               this.a();
            } catch (SnmpTransportException var3) {
               a.debug(a("TF[zuYZS*gQP\u0014ZAe"), var3);
               ++this.b.f;
               if (var2) {
                  break;
               }
               continue;
            }

            if (var2 || var2) {
               break;
            }
         }
      } catch (IOException var4) {
         a.debug(a("Y[\u0014o}SQD~l_Z"), var4);
      }

   }

   private void a() throws IOException, SnmpTransportException {
      synchronized(this.e) {
         SnmpBuffer var2 = new SnmpBuffer(this.e, 0, this.e.length);
         TransportEntity var3 = this.d.receive(var2, true);
         long var4 = System.currentTimeMillis();
         ++this.b.c;

         try {
            SnmpMessage var6 = this.c.decodeMessage(var2);
            var6.setTimestamp(var4);
            if (a.isDebugEnabled()) {
               a.debug(a("tqwEAup\u0014YK}d\u0014GVw\u000e\u0014") + var6);
            }

            if (this.b.t != null) {
               this.b.t.incomingMessage(var6, var2, var3, (String)null);
            }

            this.b.dispatchMessage(var3, var6);
         } catch (SnmpBadVersionException var9) {
            if (this.b.t != null) {
               this.b.t.incomingMessage((SnmpMessage)null, var2, var3, a("RUP*sUFGcj^"));
            }

            if (a.isDebugEnabled()) {
               a.debug(a("rup*SufgCJ~\u0014}D%czyZ%}gs"), var9);
            }

            ++this.b.e;
         } catch (SnmpSecurityCoderException var10) {
            if (this.b.t != null) {
               this.b.t.incomingMessage((SnmpMessage)null, var2, var3, a("CQW\u007fwY@M*`BF[x"));
            }

            if (a.isDebugEnabled()) {
               a.debug(a("cqw_Wy`m*@bf{X%yz\u0014YK}d\u0014GVw"), var10);
            }

            this.b.dispatchSecurityError(var2, var3, var10.getSpecificError(), var10.getMsgId(), var10);
         } catch (SnmpCoderException var11) {
            if (this.b.t != null) {
               this.b.t.incomingMessage((SnmpMessage)null, var2, var3, a("RUP*`^W[nl^S"));
            }

            if (a.isDebugEnabled()) {
               a.debug(a("s{pCKw\u0014qXW\u007ff\u0014CK\u0010gzGU\u0010ygM"), var11);
            }

            ++this.b.d;
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
               var10003 = 48;
               break;
            case 1:
               var10003 = 52;
               break;
            case 2:
               var10003 = 52;
               break;
            case 3:
               var10003 = 10;
               break;
            default:
               var10003 = 5;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
