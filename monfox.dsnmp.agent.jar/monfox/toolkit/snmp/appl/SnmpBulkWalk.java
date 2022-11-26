package monfox.toolkit.snmp.appl;

import java.util.Enumeration;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.mgr.SnmpErrorException;
import monfox.toolkit.snmp.mgr.SnmpPendingRequest;
import monfox.toolkit.snmp.mgr.SnmpResponseListener;
import monfox.toolkit.snmp.mgr.SnmpSession;
import monfox.toolkit.snmp.mgr.SnmpTimeoutException;

public class SnmpBulkWalk extends a implements SnmpResponseListener {
   private static final String[] a = new String[0];
   private static final String[] b = new String[]{a("~>"), a("~>J\tVN6[\u0014\u000fU'B\u001e\u0015O")};
   private SnmpOid c = null;
   private static final String f = "$Id: SnmpBulkWalk.java,v 1.3 2007/03/23 04:12:00 sking Exp $";

   public static void main(String[] var0) {
      try {
         SnmpBulkWalk var1 = new SnmpBulkWalk(var0);
         var1.g();
      } catch (Throwable var2) {
         h();
         System.out.println("\n");
         System.out.println(a("\u001cs\u000b4)n\u001cyK[") + var2.getMessage());
         System.out.println("\n");
      }

   }

   SnmpBulkWalk(String[] var1) throws Throwable {
      super(var1, "", a, "", b);
   }

   void g() {
      try {
         if (this.d.hasFlag("?") || this.e.length == 0) {
            h();
            System.exit(1);
         }

         this.e();
         int var1 = this.d.getIntOption(a("~>\u00113\u0016]+Y\u0014\u000bY'B\u0005\u0012S=X"), 20);
         SnmpSession var2 = this.f();
         if (this.h == 0) {
            System.out.println(a("y\u0001y>)\u0006sx\u001f\u0016Ls}@[R<_Q\r]?B\u0015[Z<YQ\u0019I?@Q\f]?@"));
            System.out.println("");
            System.out.println(a("\u001cs\u000bQ[\u001csf\u0004\bHs^\u0002\u001e\u001c~]C _\u000e\u000b\u001e\t\u001c~]B[Z<YQ\u0019I?@Q\f]?@"));
            System.out.println("");
            System.exit(1);
         }

         SnmpVarBindList var3 = new SnmpVarBindList(this.e[0]);
         String var4 = this.e.length == 1 ? this.e[0] : this.e[1];
         this.c = new SnmpOid(var4);
         var2.performBulkWalk(this, var3, this.c, var1, true);
      } catch (SnmpTimeoutException var6) {
         System.out.println(a("y\u0001y>)\u0006s\u007f\u0018\u0016Y<^\u0005"));
      } catch (SnmpErrorException var7) {
      } catch (Exception var8) {
         System.out.println(a("y\u000bh4+h\u001ad?A\u001c") + var8);
      }

      System.out.println(a("x\u001ce4"));
   }

   public void handleResponse(SnmpPendingRequest var1, int var2, int var3, SnmpVarBindList var4) {
      boolean var8 = SnmpTrapLogger.i;
      if (!var1.isCompleted()) {
         if (var2 != 0) {
            System.out.println(a("o=F\u0001>N!D\u0003Ag") + Snmp.errorStatusToString(var2) + a("\u0010sN\u0003\tS!b\u001f\u001fY+\u0016") + var3 + "]");
            return;
         }

         Enumeration var5 = var4.getVarBinds();

         while(var5.hasMoreElements()) {
            SnmpVarBind var6 = (SnmpVarBind)var5.nextElement();
            SnmpOid var7 = var6.getOid();
            if (var7.compareTo(this.c) < 0 || this.c.contains(var7) && var7.compareTo(this.c) != 0) {
               label40: {
                  if (var6.getValue().getTag() == 130) {
                     System.out.println(a("y\u001doQ4zsf89"));
                     if (!var8) {
                        break label40;
                     }
                  }

                  System.out.print(var6.getOid());
                  System.out.print("=");
                  System.out.println(var6.getValueString());
               }
            }

            if (var8) {
               break;
            }
         }
      }

   }

   public void handleReport(SnmpPendingRequest var1, int var2, int var3, SnmpVarBindList var4) {
      if (var2 != 0) {
         System.out.println("[" + Snmp.errorStatusToString(var2) + a("\u0010sN\u0003\tS!b\u001f\u001fY+\u0016") + var3 + "]");
      }

      int var5 = 0;

      while(var5 < var4.size()) {
         SnmpVarBind var6 = var4.get(var5);
         System.out.print(var6.getOid());
         System.out.print("=");
         System.out.println(var6.getValueString());
         ++var5;
         if (SnmpTrapLogger.i) {
            break;
         }
      }

   }

   static void h() {
      System.out.println(a("6s\u000b$(}\u0014n{q\u001cs\u000bQ[o=F\u00019I?@&\u001aP8\u000b*V\u0003/D\u0001\u000fU<E\u0002&\u001coD\u0013\u0011Y0_8?\u0002sp\u0005\u001eN>B\u001f\u001aH:D\u001f4u\u0017v{q\u001cso4(\u007f\u0001b!/u\u001ce{q\u001cs\u000bQ[l6Y\u0017\u0014N>XQ\u001a\u001c\u0014N\u0005V~&G\u001a[^2X\u0014\u001f\u001c\u0000E\u001c\u000b\u001c\u001eb3[K2G\u001a[S#N\u0003\u001aH:D\u001f[O'J\u0003\u000fU=LQ\fU'CQq\u001cs\u000bQ[H;NQ\u001cU%N\u001f[q\u001aiQ\r]!B\u0010\u0019P6\u000b\u0010\u0015Xs_\u0014\tQ:E\u0010\u000fU=LQ\u0014Rs_\u0019\u001e\u001c [\u0014\u0018U5B\u0014\u001f\u001c'N\u0003\u0016U=J\u0005\u0012S=\u0005Qq\u001cs\u000bQ[s\u001ao_[u5\u000b\u001f\u0014\u001c'N\u0003\u0016U=J\u0005\u0012S=\u000b>2xsB\u0002[L!D\u0007\u0012X6O][H;NQ\u000bN<L\u0003\u001aQs\\\u0018\u0017Ps\\\u0010\u0017WsJ\u001d\u00176s\u000bQ[\u001c\u001eb3[R<O\u0014\b\u001c0D\u001f\u000f]:E\u0014\u001f\u001c1RQ\u000fT6\u000b\u001e\u0015YsD\u0013\u0011Y0_8?\u001c#Y\u001e\rU7N\u0015U6Y\u000bQ9i\u001f`Q4l\u0007b>5oY!Q[\u001cs\u00063\u0016g2S\\\tY#N\u0005\u0012H:D\u001f\bas\r\u001d\u000f\u0007p\r\u0016\u000f\u0007s\u0011Q9I?@Q\u0016]+\u0006\u0003\u001eL6_\u0018\u000fU<E\u0002[\u001cs\u000bQ[\u001cs\u000b*I\f\u000e!{[\u001c\u001c{%2s\u001dx{q") + b() + "\n" + "\n" + a("\u001csx?6l%\u0018Q4l\u0007b>5oY") + "\n" + c() + "\n" + "\n");
   }

   public void handleTimeout(SnmpPendingRequest var1) {
   }

   public void handleException(SnmpPendingRequest var1, Exception var2) {
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 60;
               break;
            case 1:
               var10003 = 83;
               break;
            case 2:
               var10003 = 43;
               break;
            case 3:
               var10003 = 113;
               break;
            default:
               var10003 = 123;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
