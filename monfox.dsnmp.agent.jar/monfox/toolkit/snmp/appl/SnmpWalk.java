package monfox.toolkit.snmp.appl;

import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.mgr.SnmpErrorException;
import monfox.toolkit.snmp.mgr.SnmpPendingRequest;
import monfox.toolkit.snmp.mgr.SnmpResponseListener;
import monfox.toolkit.snmp.mgr.SnmpSession;
import monfox.toolkit.snmp.mgr.SnmpTimeoutException;

public class SnmpWalk extends a implements SnmpResponseListener {
   private SnmpOid a = null;
   private static final String b = "$Id: SnmpWalk.java,v 1.25 2007/03/23 04:12:00 sking Exp $";

   public static void main(String[] var0) {
      try {
         SnmpWalk var1 = new SnmpWalk(var0);
         var1.g();
      } catch (Throwable var2) {
         h();
         System.out.println("\n");
         System.out.println(a("$6\u001b\u0015PVYij\"") + var2.getMessage());
         System.out.println("\n");
      }

   }

   SnmpWalk(String[] var1) throws Throwable {
      super(var1);
   }

   void g() {
      try {
         if (this.d.hasFlag("?") || this.e.length == 0) {
            h();
            System.exit(1);
         }

         this.e();
         SnmpSession var1 = this.f();
         SnmpVarBindList var2 = new SnmpVarBindList(this.e[0]);
         String var3 = this.e.length == 1 ? this.e[0] : this.e[1];
         this.a = new SnmpOid(var3);
         var1.performWalk(this, var2, this.a, true);
      } catch (SnmpTimeoutException var5) {
         System.out.println(a("ADi\u001fP>6o9oayN$"));
      } catch (SnmpErrorException var6) {
      } catch (Exception var7) {
         System.out.println(a("ANx\u0015RP_t\u001e8$") + var7);
      }

      System.out.println(a("@Yu\u0015"));
   }

   public void handleResponse(SnmpPendingRequest var1, int var2, int var3, SnmpVarBindList var4) {
      if (var4.size() == 1) {
         if (var2 != 0) {
            System.out.println(a("WxV GvdT\"8_") + Snmp.errorStatusToString(var2) + a("(6^\"pkdr>fan\u0006") + var3 + "]");
            return;
         }

         SnmpVarBind var5 = var4.get(0);
         SnmpOid var6 = var5.getOid();
         if (var6.compareTo(this.a) < 0 || this.a.contains(var6) && var6.compareTo(this.a) != 0) {
            if (var5.getValue().getTag() == 130) {
               System.out.println(a("AX\u007fpMB6v\u0019@"));
               if (!SnmpTrapLogger.i) {
                  return;
               }
            }

            System.out.print(var5.getOid());
            System.out.print("=");
            System.out.println(var5.getValueString());
         }
      }

   }

   public void handleReport(SnmpPendingRequest var1, int var2, int var3, SnmpVarBindList var4) {
      if (var2 != 0) {
         System.out.println("[" + Snmp.errorStatusToString(var2) + a("(6^\"pkdr>fan\u0006") + var3 + "]");
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
      System.out.println(a("\u000e6\u001b\u0005QEQ~Z\b$6\u001bp\"WxV UezPpY))G?rp\u007fT>qY6\u0007?`nsX$K@(\u001b\u000bvadV9lebR?lK_\u007f\r\b\u000e6\u001b\u0014GWUi\u0019RP_t\u001e\b\u000e6\u001bp\"$F^\"dkdV#\"e6Y1qmu\u001b\u0003lif\u001b\u001dKF6L1no6T gvwO9mj6H$cvbR>e$aR$j$bS5\"c\u007fM5l\u000e6\u001bp\"$[r\u0012\"rwI9cfz^pcjr\u001b$gv{R>cp\u007fU7\"kx\u001b$ja6H gg\u007f]9g`6O5pi\u007fU1vmyUpMMR\u0015p\b$6\u001bp\"Mp\u001b>m$b^\"omxZ$kkx\u001b\u001fK@6R#\"tdT&k`s_|\"p~^prvy\\\"ci6L9nh6L1no6Z<n\u000e6\u001bp\"$[r\u0012\"jy_5q$uT>ve\u007fU5f$tBpvls\u001b?la6T2hauO\u0019F$fI?tmr^4,\u000e\u001c\u001bpMTBr\u001fLW\u001c1") + b() + "\n" + "\n" + a("$6h\u001eOT`\bpMTBr\u001fLW\u001c") + "\n" + c() + "\n" + "\n");
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
               var10003 = 4;
               break;
            case 1:
               var10003 = 22;
               break;
            case 2:
               var10003 = 59;
               break;
            case 3:
               var10003 = 80;
               break;
            default:
               var10003 = 2;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
