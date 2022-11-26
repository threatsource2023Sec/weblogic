package monfox.toolkit.snmp.appl;

import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.mgr.SnmpErrorException;
import monfox.toolkit.snmp.mgr.SnmpSession;
import monfox.toolkit.snmp.mgr.SnmpTimeoutException;

public class SnmpGetBulk extends a {
   private static final String[] a = new String[0];
   private static final String[] c = new String[]{a("<+"), a("<("), a("<+9Lh\f &G$\n $Q"), a("<(7Zh\f &G1\u00171?M+\r")};
   private static final String f = "$Id: SnmpGetBulk.java,v 1.24 2007/03/23 04:12:00 sking Exp $";

   public static void main(String[] var0) {
      try {
         SnmpGetBulk var1 = new SnmpGetBulk(var0);
         var1.g();
      } catch (Throwable var2) {
         h();
         System.out.println("\n");
         System.out.println(a("^evg\u0017,\n\u0004\u0018e") + var2.getMessage());
         System.out.println("\n");
      }

   }

   SnmpGetBulk(String[] var1) throws Throwable {
      super(var1, "", a, "", c);
   }

   void g() {
      SnmpVarBindList var1 = null;

      try {
         if (this.d.hasFlag("?") || this.e.length == 0) {
            h();
            System.exit(1);
         }

         int var2 = this.d.getIntOption(a("<+l`+\u0011+$G5\u001b$\"G7\r"), 0);
         int var3 = this.d.getIntOption(a("<(l`(\u001f=$G5\u001b1?V,\u0011+%"), 100);
         this.e();
         if (this.h == 0) {
            System.out.println(a(";\u0017\u0004m\u0017De\u0018M1^37N,\u001ae0M7^\u0016\u0018o\u0015^\u00133P6\u0017*8\u0002t"));
            System.exit(1);
         }

         SnmpSession var4 = this.f();
         var1 = new SnmpVarBindList(this.e);
         SnmpVarBindList var5 = var4.performGetBulk(this.b, var1, var2, var3);
         this.a(var5);
      } catch (SnmpTimeoutException var6) {
         System.out.println(a(";\u0017\u0004m\u0017De\u0002K(\u001b*#V"));
      } catch (SnmpErrorException var7) {
         this.a(var7, var1);
      } catch (Exception var8) {
         System.out.println(a(";\u0017\u0004m\u0017De") + var8);
      }

   }

   static void h() {
      System.out.println(a("tevw\u0016?\u0002\u0013(O^ev\u0002e\u0014$ Ce-+;R\u0002\u001b1\u0014W)\u0015e\r\u000fz\u0002*&V,\u0011+%\u007feB*4H \u001d1\u001ff{UO\\\u0002e:\u0000\u0005a\u00177\u0015\u0002k\n0O\\\u0002e^evr \f#9P(\re7\u00026\n$8F$\f!vq+\u00135\u0011G1<0:Ie\u001153P$\n,9Le\t,\"Je\n-3\u0002\"\u001733Letev\u0002e^*4H \u001d1\u001ff6Pe\\(e^\u0007\u0003n\u000e^\n\u0006v\f1\u000b\u0005(e^ev\u0002h<+\rM+S73R \u001f13P6#ev\u0002y]{v\u0018e<0:Ie\u0010*8\u000f7\u001b53C1\u001b7%\u0002e^ev\u0002e^\u001ef\u007fO^ev\u0002eS\u0007;y$\u0006h$G5\u001b1?V,\u0011+%\u007feBfh\u0002\u007f^\u0007#N.^(7Zh\f &G1\u00171?M+\rev\u0002e^e\r\u0013uN\u0018\\(e^\n\u0006v\f1\u000b\u0005(O") + b() + "\n" + "\n" + a("^e\u0005l\b.3e\u0002\n.\u0011\u001fm\u000b-O") + "\n" + c() + "\n" + "\n");
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 126;
               break;
            case 1:
               var10003 = 69;
               break;
            case 2:
               var10003 = 86;
               break;
            case 3:
               var10003 = 34;
               break;
            default:
               var10003 = 69;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
