package monfox.toolkit.snmp.appl;

import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.mgr.SnmpErrorException;
import monfox.toolkit.snmp.mgr.SnmpSession;
import monfox.toolkit.snmp.mgr.SnmpTimeoutException;
import monfox.toolkit.snmp.mgr.usm.UsmAdmin;

public class SnmpUsm extends a {
   private static final String a = "$Id: SnmpUsm.java,v 1.4 2007/03/23 04:12:00 sking Exp $";

   public static void main(String[] var0) {
      try {
         SnmpUsm var1 = new SnmpUsm(var0);
         var1.g();
      } catch (Throwable var2) {
         h();
         System.out.println("\n");
         System.out.println(a("'\u000ee\u007f.Ua\u0017\u0000\\") + var2.getMessage());
         var2.printStackTrace();
         System.out.println("\n");
      }

   }

   SnmpUsm(String[] var1) throws Throwable {
      super(var1);
   }

   void g() {
      boolean var9 = SnmpTrapLogger.i;
      Object var1 = null;

      try {
         if (this.d.hasFlag("?") || this.e.length == 0) {
            h();
            System.exit(1);
         }

         this.e();
         SnmpSession var2 = this.f();

         try {
            SnmpFramework.loadMibs(a("T`\bjQR}\u0000hQEo\u0016\u007f8*}\b\u00171Nl"));
         } catch (Exception var10) {
            System.out.println(a("B|\u0017u.=\u000e&[\u0012iA1\u001a\u0010hO!\u001a)Tcew5E]"));
            var10.printStackTrace();
            System.exit(1);
         }

         String[] var3 = this.e;
         String var4 = this.e[0];
         String var5;
         String var6;
         if (a("d\\ [\bb").equalsIgnoreCase(var4)) {
            if (this.e.length != 3) {
               h();
               System.exit(1);
            }

            var5 = this.e[1];
            var6 = this.e[2];
            UsmAdmin var7 = var2.getUsm().getAdmin();
            var7.createUser(this.b, var5, var6);
            System.out.println(a("\r\u000ee\u001a\u000frM&_\u000ft\u0014e\u001d") + var5 + a(" \u000e&H\u0019fZ ^R\r"));
            if (!var9) {
               return;
            }
         }

         UsmAdmin var14;
         if (a("cK)_\bb").equalsIgnoreCase(var4)) {
            if (this.e.length != 2) {
               h();
               System.exit(1);
            }

            var5 = this.e[1];
            var14 = var2.getUsm().getAdmin();
            var14.deleteUser(this.b, var5);
            System.out.println(a("\r\u000ee\u001a\u000frM&_\u000ft\u0014e\u001d") + var5 + a(" \u000e!_\u0010bZ ^R\r"));
            if (!var9) {
               return;
            }
         }

         UsmAdmin var8;
         String var15;
         if (a("dF$T\u001bbo0N\u0014LK<").equalsIgnoreCase(var4)) {
            if (this.e.length != 4) {
               h();
               System.exit(1);
            }

            var5 = this.e[1];
            var6 = this.e[2];
            var15 = this.e[3];
            var8 = var2.getUsm().getAdmin();
            var8.changeAuthKey(this.b, var5, var6, var15);
            System.out.println(a("\r\u000ee\u001a\u000frM&_\u000ft\u0014e\u001d") + var5 + a(" \u000e$O\bo\u000e._\u0005'M-[\u0012`K!\u0014v"));
            if (!var9) {
               return;
            }
         }

         if (a("dF$T\u001bb~7S\nLK<").equalsIgnoreCase(var4)) {
            if (this.e.length != 4) {
               h();
               System.exit(1);
            }

            var5 = this.e[1];
            var6 = this.e[2];
            var15 = this.e[3];
            var8 = var2.getUsm().getAdmin();
            var8.changePrivKey(this.b, var5, var6, var15);
            System.out.println(a("\r\u000ee\u001a\u000frM&_\u000ft\u0014e\u001d") + var5 + a(" \u000e5H\u0015q\u000e._\u0005'M-[\u0012`K!\u0014v"));
            if (!var9) {
               return;
            }
         }

         if (a("dF$T\u001bba2T=rZ-q\u0019~").equalsIgnoreCase(var4)) {
            if (this.e.length != 2) {
               h();
               System.exit(1);
            }

            var5 = this.e[1];
            var14 = var2.getUsm().getAdmin();
            var14.changeOwnAuthKey(this.b, var5);
            System.out.println(a("\r\u000ee\u001a\u000frM&_\u000ft\u0014eY\u0014f@\"_\\hY+\u001a\u001drZ-\u001a\u0017bWk0"));
            if (!var9) {
               return;
            }
         }

         if (a("dF$T\u001bba2T,uG3q\u0019~").equalsIgnoreCase(var4)) {
            if (this.e.length != 2) {
               h();
               System.exit(1);
            }

            var5 = this.e[1];
            var14 = var2.getUsm().getAdmin();
            var14.changeOwnPrivKey(this.b, var5);
            System.out.println(a("\r\u000ee\u001a\u000frM&_\u000ft\u0014eY\u0014f@\"_\\hY+\u001a\fuG3\u001a\u0017bWk0"));
            if (!var9) {
               return;
            }
         }

         System.out.println(a("\r\u000ee\u001a9U|\nhF'[+Q\u0012hY+\u001a\u0013wK7[\bnA+\u0000\\") + var4 + "\n");
      } catch (SnmpTimeoutException var11) {
         System.out.println(a("B\\7U\u000e=\u000e\u0011S\u0011bA0N"));
      } catch (SnmpErrorException var12) {
         this.a(var12, (SnmpVarBindList)null);
      } catch (Exception var13) {
         System.out.println(a("B\\7U\u000e=\u000e") + var13.getMessage());
         var13.printStackTrace();
      }

   }

   static void h() {
      System.out.println(a("\r\u000eeo/Fi\u00000v'\u000ee\u001a\\mO3[\\T@(J)tCeaQ8R*J\bnA+I!'\u0012*J\u0019uO1S\u0013i\u0010e\u0006\ff\\$W\u000f9\u000ek\u0014R'$O\u001a\\Ck\u0016y.N~\u0011s3I$O\u001a\\'\u000eej\u0019uH*H\u0011t\u000e1R\u0019'M*W\u0011h@eo/J\u000e0I\u0019u\u000e$^\u0011n@,I\buO1S\u0013i\u000e#O\u0012dZ,U\u0012t\u0000O0\\'~\u0004h=Jk\u0011\u007f.T$O\u001a\\'\u000ee\u0006\u0013wK7[\bnA+\u0004\\=\u000e1R\u0019'A5_\u000efZ,U\u0012'@$W\u0019\r\u000ee\u001a\\'\u00125[\u000efC6\u0004\\'\u000ee\u0000\\sF \u001a\u0013wK7[\bnA+\u001a\u000fwK&S\u001anMeJ\u001duO(_\bb\\60v'\u000ee\u001a\\SF \u001a\nfB,^\\h^ H\u001dsG*T\u000f'O+^\\wO7[\u0011t\u000e$H\u0019'O6\u001a\u001ahB)U\u000bt\u0014O0\\'\u000ee\u001a\\'\u000e&H\u0019fZ o\u000fb\\e\u001a\\'\u000eyT\u0019p\u00030I\u0019u@$W\u00199\u000eyY\u0010h@ \u0017\u001auA(\u0017\ttK7T\u001djK{0\\'\u000ee\u001a\\'\u000e!_\u0010bZ o\u000fb\\e\u001a\\'\u000eyO\u000fb\\+[\u0011b\u00031UQcK)_\bb\u0010O0\\'\u000ee\u001a\\'\u000e&R\u001diI {\tsF\u000e_\u0005'\u000eyO\u000fb\\+[\u0011b\u0010e\u0006\u0013kJh[\tsFhJ\u001dt]2^B'\u0012+_\u000b*O0N\u0014*^$I\u000fpJ{0\\'\u000ee\u001a\\'\u000e&R\u001diI j\u000enX\u000e_\u0005'\u000eyO\u000fb\\+[\u0011b\u0010e\u0006\u0013kJhJ\u000enXhJ\u001dt]2^B'\u0012+_\u000b*^7S\n*^$I\u000fpJ{0v'\u000ee\u001a\\'\u000eeY\u0014f@\"_3p@\u0004O\boe C\\;@ MQf[1RQwO6I\u000bc\u0010O\u001a\\'\u000ee\u001a\\'M-[\u0012`K\nM\u0012W\\,L7bWe\u0006\u0012bYhJ\u000enXhJ\u001dt]2^B\r$e\u001a\\'\u000e\u000bU\bb\u0014en\u0014b\u000e&R\u001diI u\u000biv\u001db$LK<\u001a\u0013wK7[\bnA+I\\r] \u001a\boKeo/J\u000e0I\u0019u$e\u001a\\'\u000ee\u001a\\'\u000eeS\u0012aA7W\u001dsG*T\\w\\,L\u0015cK!\u001a\nnOeN\u0014b\u000ehOP'\u0003\u0004\u0016\\f@!\u001aQ_\u000e*J\bnA+IR\r$e\u001a3Wz\fu2T$O") + b() + "\n" + "\n" + a("'\u000e\u0016t1WXv\u001a3Wz\fu2T$") + "\n" + c() + "\n" + "\n");
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 7;
               break;
            case 1:
               var10003 = 46;
               break;
            case 2:
               var10003 = 69;
               break;
            case 3:
               var10003 = 58;
               break;
            default:
               var10003 = 124;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
