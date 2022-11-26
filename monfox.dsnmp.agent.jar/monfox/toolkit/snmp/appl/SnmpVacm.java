package monfox.toolkit.snmp.appl;

import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.mgr.SnmpErrorException;
import monfox.toolkit.snmp.mgr.SnmpSession;
import monfox.toolkit.snmp.mgr.SnmpTimeoutException;
import monfox.toolkit.snmp.mgr.vacm.VacmAdmin;

public class SnmpVacm extends a {
   public static void main(String[] var0) {
      try {
         SnmpVacm var1 = new SnmpVacm(var0);
         var1.g();
      } catch (Throwable var2) {
         h();
         System.out.println("\n");
         System.out.println(a("v`nm<$/<\u0012n") + var2.getMessage());
         System.out.println("\n");
      }

   }

   SnmpVacm(String[] var1) throws Throwable {
      super(var1);
   }

   void g() {
      boolean var13 = SnmpTrapLogger.i;

      try {
         if (this.d.hasFlag("?") || this.e.length == 0) {
            h();
            System.exit(1);
         }

         this.e();
         SnmpSession var1 = this.f();

         try {
            SnmpFramework.loadMibs(a("\u0005\u000e\u0003xc\u0000\t\u000b\u007fc\u0014\u0001\u001dm\n{\u0001\rec\u001b\t\f\u0012\u001d\u0018\r\u001e\u0005\u001b\u0005\u0005\u001c\u0005\f\u0017\u0013\u000blc\u0005\rce\u0007\u0014"));
         } catch (Exception var14) {
            System.out.println(a("\u00132<G<l`-I 8/:\b\"9!*\b\u0018\u0017\u0003\u0003\b\u0003\u001f\u0002="));
            var14.printStackTrace();
            System.exit(1);
         }

         String[] var2 = this.e;
         String var3 = this.e[0];
         String var4;
         String var5;
         String var6;
         String var7;
         if (a("52+I:3\u0001-K+%3").equalsIgnoreCase(var3)) {
            if (this.e.length != 9) {
               h();
               System.exit(1);
            }

            var4 = this.e[1];
            var5 = this.e[2];
            var6 = this.e[3];
            var7 = this.e[4];
            String var8 = this.e[5];
            String var9 = this.e[6];
            String var10 = this.e[7];
            String var11 = this.e[8];
            VacmAdmin var12 = new VacmAdmin(var1);
            var12.createAccess(this.b, var4, var5, this.b(var6), this.c(var7), this.d(var8), var9, var10, var11);
            System.out.println(a("\\`n\b\u001d##-M=%zn\b-$%/\\+2`/K-33=\b+84<Qnq") + var4 + a("qni") + var5 + a("qn") + this.a(this.b(var6)) + "." + this.a(this.c(var7)) + "\n");
            if (!var13) {
               return;
            }
         }

         VacmAdmin var20;
         if (a("2%\"M:3\u0001-K+%3").equalsIgnoreCase(var3)) {
            if (this.e.length != 5) {
               h();
               System.exit(1);
            }

            var4 = this.e[1];
            var5 = this.e[2];
            var6 = this.e[3];
            var7 = this.e[4];
            var20 = new VacmAdmin(var1);
            var20.deleteAccess(this.b, var4, var5, this.b(var6), this.c(var7));
            System.out.println(a("\\`n\b\u001d##-M=%zn\b-$%/\\+2`/K-33=\b+84<Qnq") + var4 + a("qni") + var5 + a("qn") + this.a(this.b(var6)) + "." + this.a(this.c(var7)) + "\n");
            if (!var13) {
               return;
            }
         }

         if (a("52+I:3\u0013+K|\u00112!]>").equalsIgnoreCase(var3)) {
            if (this.e.length != 4) {
               h();
               System.exit(1);
            }

            var4 = this.e[1];
            var5 = this.e[2];
            var6 = this.e[3];
            VacmAdmin var19 = new VacmAdmin(var1);
            var19.createSec2Group(this.b, this.b(var4), var5, var6);
            System.out.println(a("\\`n\b\u001d##-M=%zn\b-$%/\\+2`=M-#2'\\7\u0002/\tZ!#0nM \"27\b") + this.a(this.b(var4)) + a("xg") + var5 + a("qnD"));
            if (!var13) {
               return;
            }
         }

         VacmAdmin var18;
         if (a("2%\"M:3\u0013+K|\u00112!]>").equalsIgnoreCase(var3)) {
            if (this.e.length != 3) {
               h();
               System.exit(1);
            }

            var4 = this.e[1];
            var5 = this.e[2];
            var18 = new VacmAdmin(var1);
            var18.deleteSec2Group(this.b, this.b(var4), var5);
            System.out.println(a("\\`n\b\u001d##-M=%zn\b*3,+\\+2`=M-#2'\\7\u0002/\tZ!#0nM \"27\b") + this.a(this.b(var4)) + a("xg") + var5 + a("qnD"));
            if (!var13) {
               return;
            }
         }

         if (a("52+I:3\u0016'M9").equalsIgnoreCase(var3)) {
            if (this.e.length != 5) {
               h();
               System.exit(1);
            }

            var4 = this.e[1];
            var5 = this.e[2];
            var6 = this.e[3];
            var7 = this.e[4];
            var20 = new VacmAdmin(var1);
            var20.createView(this.b, var4, var5, var6, this.e(var7));
            System.out.println(a("\\`n\b\u001d##-M=%zn\b-$%/\\+2`8A+!`i") + var4 + a("qni") + var5 + a("qnD"));
            if (!var13) {
               return;
            }
         }

         if (a("2%\"M:3\u0016'M9").equalsIgnoreCase(var3)) {
            if (this.e.length != 3) {
               h();
               System.exit(1);
            }

            var4 = this.e[1];
            var5 = this.e[2];
            var18 = new VacmAdmin(var1);
            var18.deleteView(this.b, var4, var5);
            System.out.println(a("\\`n\b\u001d##-M=%zn\b*3,+\\+2`8A+!`i") + var4 + a("qni") + var5 + a("qnD"));
            if (!var13) {
               return;
            }
         }

         System.out.println(a("\\`n\b\u000b$2!Ztv5 C 97 \b!&%<I:?/ \u0012n") + var3 + "\n");
      } catch (SnmpTimeoutException var15) {
         System.out.println(a("\u00132<G<l`\u001aA#3/;\\"));
      } catch (SnmpErrorException var16) {
         this.a(var16, (SnmpVarBindList)null);
      } catch (Exception var17) {
         System.out.println(a("\u00132<G<h~n") + var17.getMessage());
      }

   }

   private String a(VacmAdmin.SecLevel var1) throws Exception {
      if (var1 == VacmAdmin.LEVEL_NO_AUTH_NO_PRIV) {
         return a("8/\u000f]:>\u000e!x<?6");
      } else if (var1 == VacmAdmin.LEVEL_AUTH_NO_PRIV) {
         return a("75:@\u00009\u0010<A8");
      } else {
         return var1 == VacmAdmin.LEVEL_AUTH_PRIV ? a("75:@\u001e$)8") : a("i\u007fq");
      }
   }

   private String a(VacmAdmin.SecModel var1) throws Exception {
      if (var1 == VacmAdmin.MODEL_V1) {
         return a(" q");
      } else if (var1 == VacmAdmin.MODEL_V2C) {
         return a(" r-");
      } else {
         return var1 == VacmAdmin.MODEL_USM ? a("#3#") : a("7.7");
      }
   }

   private VacmAdmin.SecModel b(String var1) throws Exception {
      String var2 = var1.toUpperCase();
      if (var2.indexOf(a("\u0000q")) >= 0) {
         return VacmAdmin.MODEL_V1;
      } else if (var2.indexOf(a("\u0000r")) >= 0) {
         return VacmAdmin.MODEL_V2C;
      } else if (var2.indexOf(a("\u0003\u0013\u0003")) >= 0) {
         return VacmAdmin.MODEL_USM;
      } else if (var2.indexOf(a("\u0017\u000e\u0017")) >= 0) {
         return VacmAdmin.MODEL_ANY;
      } else {
         throw new Exception(a("4!*\b=3#;Z'\"9nE!2%\"\bi") + var1 + "'");
      }
   }

   private VacmAdmin.SecLevel c(String var1) throws Exception {
      String var2 = var1.toUpperCase();
      if (var2.indexOf(a("\u0018\u000f\u000f}\u001a\u001e")) >= 0) {
         return VacmAdmin.LEVEL_NO_AUTH_NO_PRIV;
      } else if (var2.indexOf(a("\u0018\u000f\u001ez\u0007\u0000")) >= 0) {
         return VacmAdmin.LEVEL_AUTH_NO_PRIV;
      } else if (var2.indexOf(a("\u0017\u0015\u001a`\u001e\u0004\t\u0018")) >= 0) {
         return VacmAdmin.LEVEL_AUTH_PRIV;
      } else {
         throw new Exception(a("4!*\b=3#;Z'\"9nD+ %\"\bi") + var1 + "'");
      }
   }

   private VacmAdmin.CtxMatch d(String var1) throws Exception {
      String var2 = var1.toUpperCase();
      if (var2.indexOf(a("\u0013\u0018\u000fk\u001a")) >= 0) {
         return VacmAdmin.MATCH_EXACT;
      } else if (var2.indexOf(a("\u0006\u0012\u000bn\u0007\u000e")) >= 0) {
         return VacmAdmin.MATCH_PREFIX;
      } else {
         throw new Exception(a("4!*\b-9.:M6\"`>Z+0)6\bi") + var1 + "'");
      }
   }

   private VacmAdmin.ViewType e(String var1) throws Exception {
      String var2 = var1.toUpperCase();
      if (var2.indexOf(a("\u001f\u000e\rd\u001b\u0012\u0005")) >= 0) {
         return VacmAdmin.SUBTREE_INCLUDED;
      } else if (var2.indexOf(a("\u0013\u0018\rd\u001b\u0012\u0005")) >= 0) {
         return VacmAdmin.SUBTREE_EXCLUDED;
      } else {
         throw new Exception(a("4!*\b8?%9\b:/0+\u000f") + var1 + a("q`f^/:)*\b87,;M=k`'F-:5*M**%6K\"#$+L"));
      }
   }

   static void h() {
      System.out.println(a("\\`n}\u001d\u0017\u0007\u000b\"Dv`n\bn<!8In\u0005.#X\u00187##\b\u0015{\u007f2G>\")!F=\u000b`rG>32/\\'9.p\br&!<I#%~n\u0006`x`D\"nv\u0004\u000b{\r\u0004\t\u001e|\u0007\u0019\u000eD\"nv`n\b\u001e32(G<;3n\\&3`-G#;/ \b\u0018\u0017\u0003\u0003\b/2-'F'%4<I:?/ \b(#.-\\'9.=\u0006D\\`nx\u000f\u0004\u0001\u0003m\u001a\u0013\u0012\u001d\"Dv`n\bnj/>M<74'G h`t\b:>%nG>32/\\'9.nF/;%D\bnv`n\u0014>72/E=h`n\bnl`:@+v/>M<74'G v3>M-?&'Kn&!<I#34+Z=\\Jn\bnv`\u001a@+v6/D'2`!X+$!:A!83nI 2`>I<7-=\b/$%nI=v&!D\"97=\u0012D\\`n\bnv`n\b-$%/\\+\u0017#-M=%`rO<95>F/;%p\br5/ \\+.4cX<3&'Ppv|=M-{-!L+:~n\u0014=3#cD+ %\"\u0016n\n`D\bnv`n\bnv`n\bnv`n\bnv`n\br5/ \\+.4cE/\"#&\u0016nj2+I*{6'M9h`r_<?4+\u00058?%9\u0016nj.!\\'09c^'37p\bD\\`n\bnv`n\b*3,+\\+\u0017#-M=%`rO<95>F/;%p\br5/ \\+.4cX<3&'Ppv|=M-{-!L+:~n\u0014=3#cD+ %\"\u0016D\\`n\bnv`n\b-$%/\\+\u0005%-\u001a\t$/;Xnj3+Kc;/*M\"h`r[+5m I#3~n\u0014)$/;Xc8!#Mp\\Jn\bnv`n\bn2%\"M:3\u0013+K|\u00112!]>v|=M-{-!L+:~n\u0014=3#cF/;%p\"Dv`n\bnv`nK<3!:M\u0018?%9\br )+_c8!#Mpv|=],\"2+Mpv|#I==~n\u00148?%9\u0005:/0+\u0016D\\`n\bnv`n\b*3,+\\+\u0000)+_nj6'M9{./E+h`r[;44<M+hJD\bnv`c\b\u001e72/E+\"%<\b\u001d/.:I633D\"nv`n\bnv`r[+5m#G*3,p\u0012nv`n\bnvhn^\u007fv<n^|5`2\b;%-nTn7.7\bg\\`n\bnv`n\br%%-\u0005\"36+Dpl`n\bnv`n\u0000n8/\u000f]:>\u000e!x<?6nTn75:@\u00009\u0010<A8v<nI;\"(\u001eZ' `g\"nv`n\bnv`rK!84+P:{-/\\->~t\bnvhnM67#:\b2v0<M(?8g\"nv`n\bnv`r^'37c\\7&%p\u0012nv`n\bnvhnA 5,;L+v<nM65,;L+viD\bnv`n\bnv|=],\"2+Mpl`n\bnv`n\bn\u0019\t\n\b=#\":Z+3`fL!\"m(G<;` G:74'G \u007fJn\bnv`n\bnj-/[%hzn\bnv`n\bnv`n\b,?4n[:$) On~%6\u0012ntg(N(0g&\ngvJD\bnv`n\bnvjni\":`!\\&32n^/:5+[n72+\b->!<I-\"%<\b=\"2'F)%nD\"nv\u000f\u001e|\u0007\u0019\u000e\u001d\"D") + b() + "\n" + "\n" + a("v`\u001df\u0003\u00066}\b\u0001\u0006\u0014\u0007g\u0000\u0005J") + "\n" + c() + "\n" + "\n");
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 86;
               break;
            case 1:
               var10003 = 64;
               break;
            case 2:
               var10003 = 78;
               break;
            case 3:
               var10003 = 40;
               break;
            default:
               var10003 = 78;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
