package monfox.toolkit.snmp.appl;

import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.mgr.SnmpErrorException;
import monfox.toolkit.snmp.mgr.SnmpSession;
import monfox.toolkit.snmp.mgr.SnmpTimeoutException;

public class SnmpGetAll extends a {
   private static final String a = "$Id: SnmpGetAll.java,v 1.14 2007/03/23 04:12:00 sking Exp $";

   public static void main(String[] var0) {
      try {
         SnmpGetAll var1 = new SnmpGetAll(var0);
         var1.g();
      } catch (Throwable var2) {
         h();
         System.out.println("\n");
         System.out.println(a("9>\u001cC\u0014KQn<f") + var2.getMessage());
         System.out.println("\n");
      }

   }

   SnmpGetAll(String[] var1) throws Throwable {
      super(var1);
   }

   void g() {
      boolean var5 = SnmpTrapLogger.i;

      try {
         if (this.d.hasFlag("?") || this.e.length == 0) {
            h();
            System.exit(1);
         }

         this.e();
         SnmpSession var1 = this.f();
         SnmpVarBindList var2 = new SnmpVarBindList(this.e);
         SnmpVarBindList[] var3 = var1.performGetAllInstances(this.b, var2);
         int var4 = 0;

         while(var4 < var3.length) {
            System.out.println(a("43\u0011+k43\u0011+k43\u0011+k43\u0011+k43\u0011+k43\u0011+k43\u0011+k43\u0011+k43\u0011+k4"));
            this.a(var3[var4]);
            System.out.println(a("43\u0011+k43\u0011+k43\u0011+k43\u0011+k43\u0011+k43\u0011+k43\u0011+k43\u0011+k43\u0011+k4"));
            ++var4;
            if (var5) {
               return;
            }

            if (var5) {
               break;
            }
         }
      } catch (SnmpTimeoutException var6) {
         System.out.println(a("\\LnI\u0014#>ho+|qIr"));
      } catch (SnmpErrorException var7) {
      } catch (Exception var8) {
         System.out.println(a("\\F\u007fC\u0016MWsH|9") + var8);
      }

      System.out.println(a("]QrC"));
   }

   static void h() {
      System.out.println(a("\u0013>\u001cS\u0015XYy\fL9>\u001c&,xh]&\u0015wsLA#m_PjfB3\u0003z)ijUi(jC\u001c:%vrIk(VWx8fB\"_i*lsRI\u000f] a,f\u0013\u0014\u001c&\u0002\\M\u007fT\u000fIJuI\b\u0013\u0014\u001c&f9>lc4\u007fqNk59\u007fR&\u0015WSl&\u000bP\\\u001cq'uu\u001ci 9jTcfm\u007f^j#9}Sj3tpO&5i{_o p{X&'wz\u001c\ff9>\u001c&!kqIv59jTcfzqPs+w>Jg*l{O& vl\u001cc'zv\u001ct)n>Hi!|jTc49wR&2q{\u001ci3mnIrh\u0013>\u001c&f9_PjfzqPs+wm\u001cv4vhUb#}>On)lrX&$|>Zt)t>Hn#9m]k#9j]d*|06\ff9QlR\u000fVPo\fL") + b() + "\n" + "\n" + a("9>oH\u000bIh\u000f&\tIJuI\bJ\u0014") + c() + "\n" + "\n");
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 25;
               break;
            case 1:
               var10003 = 30;
               break;
            case 2:
               var10003 = 60;
               break;
            case 3:
               var10003 = 6;
               break;
            default:
               var10003 = 70;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
