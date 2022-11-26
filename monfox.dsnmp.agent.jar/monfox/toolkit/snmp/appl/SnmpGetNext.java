package monfox.toolkit.snmp.appl;

import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.mgr.SnmpErrorException;
import monfox.toolkit.snmp.mgr.SnmpSession;
import monfox.toolkit.snmp.mgr.SnmpTimeoutException;

public class SnmpGetNext extends a {
   private static final String a = "$Id: SnmpGetNext.java,v 1.24 2007/03/23 04:12:00 sking Exp $";

   public static void main(String[] var0) {
      try {
         SnmpGetNext var1 = new SnmpGetNext(var0);
         var1.g();
      } catch (Throwable var2) {
         h();
         System.out.println("\n");
         System.out.println(a("4\u0018\u0018PzFwj/\b") + var2.getMessage());
         System.out.println("\n");
      }

   }

   SnmpGetNext(String[] var1) throws Throwable {
      super(var1);
   }

   void g() {
      SnmpVarBindList var1 = null;

      try {
         if (this.d.hasFlag("?") || this.e.length == 0) {
            h();
            System.exit(1);
         }

         this.e();
         SnmpSession var2 = this.f();
         var1 = new SnmpVarBindList(this.e);
         SnmpVarBindList var3 = var2.performGetNext(this.b, var1);
         this.a(var3);
      } catch (SnmpTimeoutException var4) {
         System.out.println(a("QjjZz.\u0018l|EqWMa"));
      } catch (SnmpErrorException var5) {
         this.a(var5, var1);
      } catch (Exception var6) {
         System.out.println(a("QjjZz.\u0018") + var6);
      }

   }

   static void h() {
      System.out.println(a("\u001e\u0018\u0018@{U\u007f}\u001f\"4\u0018\u00185\b~YNt\bGVUeoqLvpP`\u0018c8\u0017hWHaA{VKH\b(WZ\u007fMwLqQ\u0016?\u00182\u001f\b4|}FkFqhAa[v2\u001f\b4\u0018\u00185xqJ^zZyK\u0018t\bgLY{LuJ\\5{zUHRM`v]m\\4WHpZuLQzF4OQa@4LPp\bsQNpF42\u00185\b4\u0018WwBq[L\\lg\u0016\u0018\u001f\"4\u0018wE|]wvF\"\u001e") + b() + "\n" + "\n" + a("4\u0018k[eDN\u000b5gDlqZfG2") + "\n" + c() + "\n" + "\n");
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 20;
               break;
            case 1:
               var10003 = 56;
               break;
            case 2:
               var10003 = 56;
               break;
            case 3:
               var10003 = 21;
               break;
            default:
               var10003 = 40;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
