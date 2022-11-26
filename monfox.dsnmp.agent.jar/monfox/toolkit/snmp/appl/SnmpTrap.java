package monfox.toolkit.snmp.appl;

import java.net.InetAddress;
import java.util.StringTokenizer;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.SnmpIpAddress;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.engine.SnmpEngineID;
import monfox.toolkit.snmp.engine.TransportProvider;
import monfox.toolkit.snmp.mgr.SnmpErrorException;
import monfox.toolkit.snmp.mgr.SnmpParameters;
import monfox.toolkit.snmp.mgr.SnmpPeer;
import monfox.toolkit.snmp.mgr.SnmpSession;
import monfox.toolkit.snmp.mgr.SnmpTimeoutException;
import monfox.toolkit.snmp.mgr.SnmpTrapSource;

public class SnmpTrap extends a {
   private static final SnmpOid a = new SnmpOid(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 1L, 1L, 4L, 1L, 0L});
   private static final SnmpOid b = new SnmpOid(new long[]{1L, 3L, 6L, 1L, 2L, 1L, 1L, 3L, 0L});
   SnmpTrapSource c = null;
   SnmpPeer f = null;
   private static final String h = "$Id: SnmpTrap.java,v 1.12 2007/03/23 04:12:00 sking Exp $";

   public static void main(String[] var0) {
      try {
         SnmpTrap var1 = new SnmpTrap(var0);
         var1.g();
      } catch (Throwable var2) {
         h();
         System.out.println("\n");
         System.out.println(a("im*.\u0004\u001b\u0002XQv") + var2);
         var2.printStackTrace();
      }

   }

   SnmpTrap(String[] var1) throws Throwable {
      super(var1, "", new String[0], "", new String[]{a(";(z\u000e7="), a("-(f\n/")});
   }

   void g() {
      boolean var7 = SnmpTrapLogger.i;
      Object var1 = null;

      try {
         if (this.d.hasFlag("?") || this.e.length < 2) {
            h();
            System.exit(1);
         }

         int var2 = this.d.getIntOption(a(";(z\u000e7="), 1);
         int var3 = this.d.getIntOption(a("-(f\n/"), -1);
         this.e();
         SnmpParameters var4 = this.a(true);
         SnmpSession var5 = this.a(var4);
         int var6 = 0;

         while(var6 < var2) {
            this.a(var5, var4);
            if (var7) {
               break;
            }

            if (var3 > 0) {
               Thread.sleep((long)var3);
            }

            ++var6;
            if (var7) {
               break;
            }
         }
      } catch (SnmpTimeoutException var8) {
         System.out.println(a("\f\u001fX$\u0004sm^\u0002;,\"\u007f\u001f"));
      } catch (SnmpErrorException var9) {
         this.a(var9, (SnmpVarBindList)var1);
      } catch (Exception var10) {
         System.out.println(a("\f\u001fX$\u0004sm") + var10);
      }

   }

   private void a(SnmpSession var1, SnmpParameters var2) throws SnmpTimeoutException, SnmpErrorException, Exception {
      boolean var11 = SnmpTrapLogger.i;
      SnmpVarBindList var3 = new SnmpVarBindList();
      int var4 = var2.getInformProfile().getSnmpVersion();
      if (var4 != 0) {
         try {
            int var5 = Integer.parseInt(this.e[0]);
            var3.addTimeTicks(b, (long)var5);
         } catch (NumberFormatException var18) {
            System.out.println(a("\f\u001fX$\u0004smh\n2iq\u007f\u001b\"  oUlij") + this.e[0] + "'");
            System.out.println(a("i`'K\r") + var18.getMessage() + a("\u0014m'F"));
            System.exit(1);
         }

         try {
            var3.addOid(a, this.e[1]);
         } catch (Exception var17) {
            System.out.println(a("\f\u001fX$\u0004smh\n2iq~\u001979`e\u00022ww*L") + this.e[1] + "'");
            System.out.println(a("i`'K\r") + var17.getMessage() + a("\u0014m'F"));
            System.exit(1);
         }

         this.a(this.e, 2, var3);
         if (var3.size() <= 0) {
            System.out.println(a("\f\u001fX$\u0004smd\u0004v?,f\u001e3:m~\u0004v #i\u0007#-(\u0000"));
            System.exit(1);
         }

         if (this.c == null) {
            label84: {
               TransportProvider var19 = TransportProvider.newInstance(this.E, (InetAddress)null, -1, false);
               this.J.config(a("\u001a#g\u001b\u0013'*c\u00053\u0000\t0K") + this.x);
               if (this.x != null) {
                  this.c = new SnmpTrapSource(SnmpFramework.getMetadata(), this.x, var19);
                  if (!var11) {
                     break label84;
                  }
               }

               this.c = new SnmpTrapSource(SnmpFramework.getMetadata(), (SnmpEngineID)null, var19);
            }

            if (this.y >= 0) {
               this.c.getSnmpEngine().setEngineBoots(this.y);
            }

            if (this.z >= 0) {
               this.c.getSnmpEngine().setEngineTime(this.z);
            }

            this.f = new SnmpPeer(this.g, this.C, this.E);
         }

         this.f.setParameters(var2);
         this.c.sendTrapV2(var3, this.f);
         if (!var11) {
            return;
         }
      }

      if (this.e.length < 5) {
         h();
         System.exit(1);
      }

      SnmpOid var20 = null;
      SnmpIpAddress var6 = null;
      int var7 = 0;
      int var8 = 1;
      int var9 = 0;

      try {
         var20 = new SnmpOid(this.e[0]);
      } catch (SnmpException var16) {
         System.out.println(a("\f\u001fX$\u0004smh\n2iqo\u0005\",?z\u0019?:('\u0004?-s0Kq") + this.e[0] + "'");
         System.out.println(a("i`'K\r") + var16.getMessage() + a("\u0014m'F"));
         System.exit(1);
      }

      try {
         InetAddress var10 = InetAddress.getByName(this.e[1]);
         var6 = new SnmpIpAddress(var10.getHostAddress());
      } catch (Exception var15) {
         System.out.println(a("\f\u001fX$\u0004smh\n2iqk\f3'94Qvn") + this.e[1] + "'");
         System.out.println(a("i`'K\r") + var15.getMessage() + a("\u0014m'F"));
         System.exit(1);
      }

      try {
         var7 = Integer.parseInt(this.e[2]);
      } catch (NumberFormatException var14) {
         System.out.println(a("\f\u001fX$\u0004smh\n2iqm\u000e8,?c\b{=?k\u001bhsm-") + this.e[2] + "'");
         System.out.println(a("i`'K\r") + var14.getMessage() + a("\u0014m'F"));
         System.exit(1);
      }

      try {
         var8 = Integer.parseInt(this.e[3]);
      } catch (NumberFormatException var13) {
         System.out.println(a("\f\u001fX$\u0004smh\n2iqy\u001b3*$l\u00025d9x\n&ww*L") + this.e[3] + "'");
         System.out.println(a("i`'K\r") + var13.getMessage() + a("\u0014m'F"));
         System.exit(1);
      }

      try {
         var9 = Integer.parseInt(this.e[4]);
      } catch (NumberFormatException var12) {
         System.out.println(a("\f\u001fX$\u0004smh\n2iq~\u0002;,>~\n;9s0Kq") + this.e[4] + "'");
         System.out.println(a("i`'K\r") + var12.getMessage() + a("\u0014m'F"));
         System.exit(1);
      }

      this.a(this.e, 5, var3);
      if (this.c == null) {
         TransportProvider var21 = TransportProvider.newInstance(this.E, (InetAddress)null, -1, false);
         this.c = new SnmpTrapSource(SnmpFramework.getMetadata(), (SnmpEngineID)null, var21);
         this.f = new SnmpPeer(this.g, this.C, this.E);
      }

      this.f.setParameters(var2);
      this.c.sendTrapV1(var20, var6, var7, var8, var9, var3, this.f);
   }

   private void a(String[] var1, int var2, SnmpVarBindList var3) {
      boolean var10 = SnmpTrapLogger.i;
      int var4 = var2;

      while(var4 < var1.length) {
         label36: {
            StringTokenizer var5 = new StringTokenizer(var1[var4], a("tw"), false);
            if (var5.countTokens() < 2) {
               System.out.println(a("\f\u001fX$\u0004smH\n2i\fx\f#$(d\u001fv") + var1[var4]);
               if (!var10) {
                  break label36;
               }
            }

            try {
               label44: {
                  String var6 = var5.nextToken();
                  String var7 = null;
                  String var8 = var5.nextToken();
                  if (!var5.hasMoreTokens()) {
                     var3.add(var6, var8);
                     if (!var10) {
                        break label44;
                     }
                  }

                  var7 = var8.toUpperCase();
                  var8 = var5.nextToken("\u0001").trim();
                  if (var8.startsWith(":")) {
                     var8 = var8.substring(1);
                  }

                  SnmpValue var9 = SnmpValue.getInstance(var7, var8);
                  var3.add(var6, var9);
               }
            } catch (SnmpException var11) {
               System.out.println(a("\f\u001fX$\u0004smH\n2i\u000ee\u0006&&#o\u0005\"sm") + var1[var4]);
               System.out.println(a("\f\u001fX$\u0004smC\u00050&w*") + var11.getMessage());
            } catch (NumberFormatException var12) {
               System.out.println(a("\f\u001fX$\u0004smH\n2i\u0003\u007f\u00064,?*L") + var12.getMessage() + a("nml\u00199$m*L") + var1[var4] + a("nc"));
            } catch (Exception var13) {
               System.out.println(a("\f\u001fX$\u0004smH\n2i\u001bk\u0019\u0014 #nQv") + var1[var4]);
               System.out.println(a("\f\u001fX$\u0004smC\u00050&w*") + var13.getMessage());
            }
         }

         ++var4;
         if (var10) {
            break;
         }
      }

   }

   static void h() {
      System.out.println(a("Cm*>\u0005\b\nOa\\im*Kv#,|\nv\u001a#g\u001b\u0002;,zK{?|*0{v1e\u001b\":\u0010*W3'9o\u0019&;$y\u000ehiqk\f3'94K\nCm*Kvim*Kvim*Kvim*Kvim*Kvim*Kvim*Kj.(d\u000e$ .'\u001f$(=4Kj:=o\b?/$iF\";,zUv\u0015G*Kvim*Kvim*Kvim*Kvim*Kvim*Kvim*Kvu8z\u001f?$(4Kj&/`\u000e5=\u0004NUk\u0012q~\u0012&,s06j?,f\u001e3wm$ExiG\u0000Kvim*\u00017?,*88$=^\u001979m'\u001dd5;9K\rdrv\u0004&=>WKj<=~\u0002;,s*W\";,zF9 )4K\nCm*Kvim*Kvim*Kvim*Kvim*Kvim*Kvim*Kvim6\u00044#(i\u001f\u001f\rs70j=4z\u000ehs\u00106\u001d7%8oUvgc$K\\Cm*/\u0013\u001a\u000eX\"\u0006\u001d\u0004E%\\Cm*Kvi\u001do\u00190&?g\u0018v(#*8\u0018\u0004\u001d*?$(=*\u0004&,?k\u001f?&#*\u00048i9b\u000ev:=o\b?/$o\u000fv\u0004\u0004Havim*K (?c\n4%(yE\\Cm*=gi\u0019X*\u0006i\u001dK9\u0017\u0004\b^.\u0004\u001aG\u0000Kvim*W3'9o\u0019&;$y\u000ehim*Kli\u0019b\u000ev,#~\u000e$9?c\u00183i\u0002C/\\im*Kvu,m\u000e8=s*Kvim*Kviw*?>,mk\f3'9*\n2-?o\u0018%i9eK?'.f\u001e2,mc\u0005v=%oK\";,zavim*Kj.(d\u000e$ .'\u001f$(=4Kvsm^\u00033i*o\u00053;$iK\";,zK?-G*Kvim6\u0018&,.c\r?*`~\u001979s*Qv\u001d%oK%9(i\u00020 .*\u001f$(=*\u00022Cm*Kviq\u007f\u001b\"  oUvim*Kvim0K\u0002!(*\u001f$(=*\u001e&=$g\u000ev?,f\u001e3CG*K\u0000{m^9\u0017\u0019mZ*\u0004\b\u0000O?\u0013\u001b\u001e\u0000avim*Kj&/`\u000e5=\u0004NUvsmG\"\u0014i;k\u0019?(/f\u000ev\u0006\u0004Na\\im*Kvu9s\u001b3wm*Kviw*\u0002\r'9o\f3;\u0010&\u0018\r=?c\u00051\u0014ae0?-\u0010&avim*Kvim*Kvim*Kvimc\u001b\r()n\u00193:>WG1\u0012,\u007f\f3\u0014ai09<#~\u000e$\u0014\u00169Y\u000bCm*Kvim*Kvim*Kvim*K5\u0012\"\u007f\u0005\",?W]bem~0?$(~\u00025\">WGv&=Q\n'<(Wa\\im*Kvim*Kvim*Kvim*Av\u0007\"~\u000ev=%k\u001fvu9s\u001b3wml\u00023%)*\u0002%i\"z\u001f?&#k\u0007xi\u0004lK\"!(\u0000Kvim*Kvim*Kvim*Kvim*&\u001f\u000bmi\u00048=,c\u0005?'**\u001f>,me\t<,.~K?:mf\u00047-(nGv=%o\u0005v=%oavim*Kvim*Kvim*Kvim*Kj=4z\u000ehi$yK8&9*\u00053,)o\u000fxCG*Kvim6\u001d7%8oUvim*Qv?,f\u001e3i\"lK\"!(*&\u001f\u000bm|\n$ ,h\u00073i9eK?'.f\u001e2,G\u0000avi\u0002Z?\u001f\u0006\u0003Ya\\im*Kvd?o\u001b3(9*Wu 9o\u00197=$e\u0005%wm*Kli#\u007f\u00064,?*\u00040i9c\u00063:m~\u0004v:(d\u000fv=?k\u001bvim*Kv\u0012|Wavim*K{-(f\n/im6\u000f3%,sF; !f\u0002%wm*Qv-(f\n/iec\u0005v$$f\u0007?:d*\t3=:o\u000e8i9x\n&:m*K\r'\"d\u000e\u000bCG") + b() + "\n" + "\n" + a("imY%\u001b\u0019;9K\u0019\u0019\u0019C$\u0018\u001aG") + "\n" + c() + "\n" + "\n");
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 73;
               break;
            case 1:
               var10003 = 77;
               break;
            case 2:
               var10003 = 10;
               break;
            case 3:
               var10003 = 107;
               break;
            default:
               var10003 = 86;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
