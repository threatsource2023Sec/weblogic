package monfox.toolkit.snmp.mgr.usm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import monfox.toolkit.snmp.util.ByteFormatter;
import monfox.toolkit.snmp.util.Commandline;
import monfox.toolkit.snmp.util.CryptoUtil;
import monfox.toolkit.snmp.util.FormatUtil;

public class UsmDHKickstartTool {
   public static void main(String[] var0) {
      boolean var23 = Usm.M;
      Commandline var1 = null;

      String var2;
      try {
         var2 = a("POo");
         String[] var3 = new String[]{a("W\u0015\u007f"), a("P\u0006b\r!M\u0019s\u001a")};
         String var4 = a("Y\u0011\u007f\u0012");
         String[] var5 = new String[]{a("Y\u0019k\u001a4^\u0003b"), a("R\u0011\u007f")};
         var1 = new Commandline(var0, var2, var4, var3, var5);
      } catch (Throwable var26) {
         a();
         System.err.println(a("5z'_vZ\u0002u\u0010$\u0005Pn\u0011 ^\u001cn\u001bvP\u0000s\u00169QP ") + var26.getMessage() + a("\u0018z\r"));
         System.exit(1);
      }

      if (var1.params.length == 0) {
         a();
         System.exit(1);
      }

      var2 = var1.getOption(a("YJa\u0016:Z\u0012f\f3"), a("T\u0019d\u0014%K\u0011u\u000b"));
      boolean var28 = var1.hasFlag(a("PJh\t3M\u0007u\u0016\"Z"));
      boolean var29 = var1.hasFlag(a("WJo\u001a."));
      BigInteger var30 = null;
      if (var1.getOption(a("RJj\u001e."), (String)null) != null) {
         var30 = new BigInteger(var1.getOption(a("RJj\u001e."), (String)null));
      }

      String var6 = var1.getOption("a", a("r42"));
      String var7 = var1.getOption("x", a("{5T"));

      try {
         String var8 = var2 + a("`\u001d`\rxK\bs");
         FileOutputStream var9 = null;
         boolean var10 = false;

         try {
            File var11 = new File(var8);
            boolean var12 = var11.exists();
            var9 = new FileOutputStream(var8, !var28);
            if (!var12) {
               StringBuffer var13 = new StringBuffer();
               var13.append(a("\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS\r"));
               var13.append(a("\u001cPJ>\u0018~7B-v{8'4\u001f|;T+\u0017m$'/\u0017m1J:\u0002z\"Tu"));
               var13.append(a("\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS\r"));
               var13.append(FormatUtil.pad(a("\u001cPT:\u0015\u0012%T:\u0004"), 15, 'l')).append(" ");
               var13.append(FormatUtil.pad(a("~%S7"), 6, 'l')).append(" ");
               var13.append(FormatUtil.pad(a("o\"N)"), 6, 'l')).append(" ");
               var13.append(a("r1I>\u0011z\"*-\u0017q4H2"));
               var13.append("\n");
               var13.append(a("\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS\r"));
               var9.write(var13.toString().getBytes());
            }
         } catch (IOException var25) {
            System.err.println(a("5z'_\u0013m\"H-l\u001f\u0013f\u00118P\u0004'\u0010&Z\u001e(\u001c$Z\u0011s\u001avY\u0019k\u001av\u0018") + var8);
            System.exit(1);
         }

         String var31 = var2 + a("`\u0011`\u001a8K^s\u0007\"");
         FileOutputStream var32 = null;

         try {
            File var33 = new File(var31);
            boolean var14 = var33.exists();
            var32 = new FileOutputStream(var31, !var28);
            if (!var14) {
               StringBuffer var15 = new StringBuffer();
               var15.append(a("\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS\r"));
               var15.append(a("\u001cPF8\u0013q$';\u001e\u001f;N<\u001dl$F-\u0002\u001f F-\u0017r5S:\u0004lz"));
               var15.append(a("\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS\r"));
               var15.append(FormatUtil.pad(a("\u001cPT:\u0015\u0012%T:\u0004"), 15, 'l')).append(" ");
               var15.append(FormatUtil.pad(a("~%S7"), 6, 'l')).append(" ");
               var15.append(FormatUtil.pad(a("o\"N)"), 6, 'l')).append(" ");
               var15.append(a("r1I>\u0011z\"*/\u0003}<N<"));
               var15.append("\n");
               var15.append(a("\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS$\\u\u001cS\r"));
               var32.write(var15.toString().getBytes());
            }
         } catch (IOException var24) {
            System.err.println(a("5z'_\u0013m\"H-l\u001f\u0013f\u00118P\u0004'\u0010&Z\u001e(\u001c$Z\u0011s\u001avY\u0019k\u001av\u0018") + var31);
            System.exit(1);
         }

         BigInteger var34 = UsmAdmin.d;
         BigInteger var35 = UsmAdmin.e;
         int var36 = UsmAdmin.f;
         SecureRandom var16 = new SecureRandom();
         int var17 = 0;

         label97:
         while(var17 < var1.params.length) {
            if (var23) {
               return;
            }

            BigInteger var18 = null;

            while(true) {
               var18 = new BigInteger(var36, var16);
               BigInteger var10000 = var18;

               while(var10000.compareTo(var34) < 0) {
                  var10000 = var30;
                  if (!var23) {
                     if (var30 != null) {
                        var18 = var18.mod(var30);
                     }

                     String var20;
                     StringBuffer var21;
                     label86: {
                        BigInteger var19 = var35.modPow(var18, var34);
                        var20 = var1.params[var17];
                        var21 = new StringBuffer();
                        var21.append(FormatUtil.pad(var20, 15, 'l')).append(" ");
                        var21.append(FormatUtil.pad(var6, 6, 'l')).append(" ");
                        var21.append(FormatUtil.pad(var7, 6, 'l')).append(" ");
                        if (var29) {
                           var21.append(a("\u000f\b")).append(ByteFormatter.toHexString(CryptoUtil.encodeUnsigned(var19), true));
                           if (!var23) {
                              break label86;
                           }
                        }

                        var21.append(var19);
                     }

                     StringBuffer var22;
                     label81: {
                        var21.append("\n");
                        var22 = new StringBuffer();
                        var22.append(FormatUtil.pad(var20, 15, 'l')).append(" ");
                        var22.append(FormatUtil.pad(var6, 6, 'l')).append(" ");
                        var22.append(FormatUtil.pad(var7, 6, 'l')).append(" ");
                        if (var29) {
                           var22.append(a("\u000f\b")).append(ByteFormatter.toHexString(CryptoUtil.encodeUnsigned(var18), true));
                           if (!var23) {
                              break label81;
                           }
                        }

                        var22.append(var18);
                     }

                     var22.append("\n");
                     var32.write(var21.toString().getBytes());
                     var9.write(var22.toString().getBytes());
                     ++var17;
                     if (var23) {
                        break label97;
                     }
                     continue label97;
                  }
               }
            }
         }

         var32.close();
         var9.close();
      } catch (IOException var27) {
         System.err.println(a("5z'_\u0013m\"H-l\u001f\u0016n\u00133\u001f\u0007u\u0016\"ZPb\r$P\u0002'W") + var27.getMessage() + ")");
         System.exit(1);
      }

   }

   static void a() {
      System.out.println(a("5z'_vj#F8\u00135z'_v\u001fP'*%R4O4?\\\u001bt\u000b7M\u0004S\u00109SP'$\u0019o$N0\u0018l-'C%Z\u0013r\r?K\t*\n%Z\u0002*\u00117R\u00159_x\u0011^\ruv\u001fPC:\u0005|\"N/\u0002v?Iu\\\u001fP'_v\u001f$o\u001avj\u0003j;\u001et\u0019d\u0014%K\u0011u\u000b\u0002P\u001fk_?LPr\f3[Ps\u0010vX\u0015i\u001a$^\u0004b_#L\u001dC7\u001dV\u0013l\f\"^\u0002s+7]\u001cb_\\\u001fP'_v\u001f\u0002b\u00137K\u0015c_?Q\u0019s\u00167S\u0019}\u001e\"V\u001fi_0V\u001cb\fx\u001f9s_!V\u001ck_1Z\u001eb\r7K\u0015'\u001evR\u0011s\u001c>V\u001e`_&^\u0019u_\\\u001fP'_v\u001f\u001fa_7X\u0015i\u000bv^\u001ec_;^\u001ef\u00183MPa\u0016:Z\u0003)_\u0002W\u0015'\u001e1Z\u001es_0V\u001cb_5P\u001es\u001e?Q\u0003'\u000b>ZPj\u001e8^\u0017b\rv5P'_v\u001fPw\n4S\u0019d_ ^\u001cr\u001avY\u001fu_\"W\u0015'\f&Z\u0013n\u0019?Z\u0014'\n%Z\u0002t_!W\u0019k\u001avK\u0018b_;^\u001ef\u00183MPa\u0016:ZP\r_v\u001fP'_5P\u001es\u001e?Q\u0003'\u000b>ZPj\u001e8^\u0017b\rvM\u0011i\u001b9RPq\u001e:J\u0015t_0P\u0002'\u000b>ZPt\u000f3\\\u0019a\u00163[Pr\f3M\u0003)u\\\u001fP'_v\u001f2~_2Z\u0016f\n:KPs\u00173\u001f\u0004h\u0010:\u001f\u0007n\u0013:\u001f\u0011w\u000f3Q\u0014'\u00113HPb\u0011\"M\u0019b\fvK\u001f'\u001e8FPb\u0007?L\u0004n\u00111\u001fz'_v\u001fP'\u0014?\\\u001bt\u000b7M\u0004'\u0019?S\u0015tu\\\u001fP'/\u0017m1J:\u0002z\"Tu\\\u001fP'_v\u001fLt\u001a5J\u0002n\u000b/\u0012\u0005t\u001a$\u0012\u001ef\u00123\u0001P'_v\u0005Ps\u00173\u001f\u001bn\u001c=L\u0004f\r\"\u001f\u0015i\u000b$FPt\u001a5J\u0002n\u000b/\u001f\u0005t\u001a$\u001f\u001ef\u001235z\r_v\u001f?W+\u001fp>Tu\\\u001fP'_v\u001f]o$3G-'_v\u001fP'_v\u001fP'_v\u001fP'_v\u0005Pp\r?K\u0015'\t7S\u0005b\fv^\u0003'\u00173GPi\n;]\u0015u\fv\u001fP'_v\u001f+c\u001a5V\u001df\u0013\u000b5P'_v\u001fP*\u0010\rI\u0015u\b$V\u0004b\"v\u001fP'_v\u001fP'_v\u001fJ'\u0010 Z\u0002p\r?K\u0015'\u001a.V\u0003s\u00168XPa\u0016:Z\u0003'_v\u001fP'_v\u001fP\\\u001e&O\u0015i\u001b\u000b5P'_v\u001fP*\u0019\rV\u001cb\u001d7L\u0015Z_j]\u0011t\u001a8^\u001dbAv\u001fJ'\u000b>ZPe\u001e%Z\u001ef\u00123\u001f\u0016h\rvX\u0015i\u001a$^\u0004b\u001bvY\u0019k\u001a%\u001fP\\\u0014?\\\u001bt\u000b7M\u0004Zuv\u001fP'_v\u0012\u0011'C7J\u0004oR&M\u001fs\u0010h\u001fP'_v\u001fP'_l\u001f\u0011r\u000b>\u001f\u0000u\u0010\"P\u0013h\u0013vQ\u0011j\u001av\u0017=CJ*l8FVv\u001fP'_vd=CJ\u000b5P'_v\u001fP*\u0007v\u0003\u0000u\u0016 \u0012\u0000u\u0010\"PN'_v\u001fP'_v\u001fJ'\u000f$V\u0006'\u000f$P\u0004h\u001c9SPi\u001e;ZP'_v\u001fP'_v\u001fP'_v\u001fP\\;\u0013l-\r_v\u001fP'_v\u001fP'_v\u001fP'_v\u001fP'_v\u001fP'_v\u001fP'_v\u001fP'We{5T\u0003\u0012z#{>\u0013lA5G*~5TNo\r\fF:\u0005\rE1V\\\u001fP'_v\u001f]j$7G-'C;^\b*\r7Q\u0014h\u0012h\u001fP'_v\u0005Ps\u00173\u001f\u001df\u0007{M\u0011i\u001b9RPq\u001e:J\u0015'\u000b9\u001f\u0005t\u001av\u001fP'_v\u001f+i\u00108Z-\ru"));
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 63;
               break;
            case 1:
               var10003 = 112;
               break;
            case 2:
               var10003 = 7;
               break;
            case 3:
               var10003 = 127;
               break;
            default:
               var10003 = 86;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
