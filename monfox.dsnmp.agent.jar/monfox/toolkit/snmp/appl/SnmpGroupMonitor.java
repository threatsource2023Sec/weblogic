package monfox.toolkit.snmp.appl;

import monfox.toolkit.snmp.NoSuchObjectException;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.ext.SnmpError;
import monfox.toolkit.snmp.ext.SnmpObjectGroup;
import monfox.toolkit.snmp.ext.SnmpObjectSet;
import monfox.toolkit.snmp.ext.SnmpObjectSetListener;
import monfox.toolkit.snmp.ext.SnmpPollingEngine;
import monfox.toolkit.snmp.mgr.SnmpErrorException;
import monfox.toolkit.snmp.mgr.SnmpSession;

public class SnmpGroupMonitor extends a implements SnmpObjectSetListener {
   private static final String[] a = new String[0];
   private static final String[] c = new String[]{a("t5&_[`")};
   private SnmpPollingEngine f = null;
   private static final String g = "$Id: SnmpGroupMonitor.java,v 1.22 2007/03/23 04:12:00 sking Exp $";

   public static void main(String[] var0) {
      try {
         SnmpGroupMonitor var1 = new SnmpGroupMonitor(var0);
         var1.g();
         System.out.println(a("V\u0015\u0015rm>p\u0018Y[o9:Q\u0014b?&\u0016Wl1:QQw~"));

         while(true) {
            Thread.sleep(10000L);
         }
      } catch (Throwable var2) {
         h();
         System.out.println("\n");
         System.out.println(a("$ptsfV\u001f\u0006\f\u0014") + var2.getMessage());
         System.out.println("\n");
      }
   }

   SnmpGroupMonitor(String[] var1) throws Throwable {
      super(var1, "", a, "", c);
   }

   void g() {
      boolean var6 = SnmpTrapLogger.i;

      try {
         if (this.d.hasFlag("?") || this.e.length == 0) {
            h();
            System.exit(1);
         }

         int var1 = this.d.getIntOption(a("t5&_[`"), 5);
         this.e();
         SnmpSession var2 = this.f();
         this.f = new SnmpPollingEngine(var2);
         SnmpObjectGroup var3 = new SnmpObjectGroup(this.b);
         var3.setName(a("C\";CD"));
         String[] var4 = this.e;
         var3.addObjectSetListener(this);
         int var5 = 0;

         while(var5 < var4.length) {
            var3.monitor(var4[var5]);
            ++var5;
            if (var6) {
               return;
            }

            if (var6) {
               break;
            }
         }

         this.f.add(var3, var1);
      } catch (SnmpErrorException var7) {
         this.a(var7);
      } catch (Exception var8) {
         System.out.println(a("A\u0002\u0006yf>p") + var8);
         System.exit(1);
      }

   }

   public void handleUpdated(SnmpObjectSet var1, int[] var2) {
      if (var2 != null) {
         System.out.println(a("G\u0018\u0015xsA\u0014\u000f") + var1.getName() + a("Yj"));
         int var3 = 0;

         while(var2 != null && var3 < var2.length) {
            try {
               SnmpVarBind var4 = var1.getVarBind(var2[var3]);
               System.out.println(a("$pt\u001e") + var4.getOid() + a("(p") + var4.getValueString() + ")");
            } catch (NoSuchObjectException var5) {
               System.out.println(a("A\u0002\u0006yf>p") + var5);
               System.exit(1);
            }

            ++var3;
            if (SnmpTrapLogger.i) {
               break;
            }
         }

      }
   }

   public void handleError(SnmpObjectSet var1, SnmpError var2) {
      System.out.println(a("A\u0002\u0006yf>p\u000f") + var1.getName() + a("Yjt\u001e") + var2 + ")");
   }

   static void h() {
      System.out.println(a("\u000eptcgE\u0017\u0011<>$pt\u0016\u0014n1\"W\u0014W>9Fsv?!Fyk>=B[vp\u000f\u001b\u000bx?$B]k>'k\u00148?6\\Qg$\u001dr\n/Z^\u0016\u0014@\u0015\u0007ufM\u0000\u0000\u007f{JZ^\u0016\u0014$pt{[j9 YF$1tQFk%$\u0016[bp\u0019\u007fv$?6\\Qg$'\u0016Rk\"t@Uh%1\u0016Wl1:QQwp5XP$4=EDh1-\u0016>$pt\u0016\u0014g85XSa#tWG$$<SM$1&S\u0014`9'U[r5&SP*p\u0000^]wp5FDh97W@m?:\u0016Aw5'\u0016Da\"=YPm3^\u0016\u0014$ptF[h<=XS$9:\u0016[v41D\u0014p?tCD`1 S\u0014p81\u0016Be<!SG$1:R\u0014h?;]\u0014b?&\u0016Wl1:QQw~^\u0016\u0014$ptb\\aphYVn57B}@nt[U}p6S\u0014ep'UUh1&\u0016[vp\u001bt~A\u0013\u0000\u0016sV\u001f\u0001f\u0014m41X@m6=SF*Z^\u0016\u0014I\u001f\u001a\u007f`K\u0002tydP\u0019\u001bxg\u000eZt\u0016\u0014$pyFQv9;R\u0014$pt\u0016\u00148#1U[j4'\b\u0014$jtF[h<=XS$ 1D]k4tP[vp ^Q$?6\\Qg$'\u0016\u0014$p\u000f\u0003i\u000eZt\u0016wK\u001d\u0019yz$\u001f\u0004b}K\u001e\u0007<>") + b() + "\n" + "\n" + a("$p\u0007xyT&g\u0016{T\u0004\u001dyzWZ") + "\n" + c() + "\n" + "\n");
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
               var10003 = 80;
               break;
            case 2:
               var10003 = 84;
               break;
            case 3:
               var10003 = 54;
               break;
            default:
               var10003 = 52;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
