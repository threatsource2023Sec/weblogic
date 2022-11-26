package monfox.toolkit.snmp.metadata.output;

import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.xml.SnmpMetadataLoader;
import monfox.toolkit.snmp.util.Commandline;

public class XmlToMib {
   public static void main(String[] var0) {
      XmlToMib var1 = new XmlToMib();
      var1.a(var0);
   }

   void a(String[] var1) {
      boolean var12 = MibOutputter.i;
      Commandline var2 = null;

      String var5;
      try {
         String var3 = a("m'");
         String[] var4 = new String[]{a("=\"[f\u0010 =Jq"), a(":1Rd"), a("$e"), a("$f")};
         var5 = a("=0");
         String[] var6 = new String[]{a("=!Jd\u0012&"), a("6=L"), a("7,J")};
         var2 = new Commandline(var1, var3, var5, var4, var6);
      } catch (Throwable var14) {
         System.out.println(var14.getMessage());
         a();
         System.exit(1);
      }

      if (var2.hasFlag(a("mnVq\u000b\"")) || var2.params.length == 0) {
         a();
         System.exit(1);
      }

      if (var2.params.length == 0) {
         a();
         System.exit(1);
      }

      boolean var17 = var2.hasFlag(a("$e"));
      boolean var18 = var2.hasFlag(a("=\"[f\u0010 =Jq"));
      var5 = var2.getOption(a("=nQa\u0013\"!J"), (String)null);
      String var19 = var2.getOption(a("7,J"), a("?=\\"));
      String var7 = var2.getOption(a("6nZ}\u0015"), (String)null);
      SnmpMetadata var8 = new SnmpMetadata();
      SnmpMetadataLoader var9 = new SnmpMetadataLoader();

      try {
         int var10 = 0;

         while(var10 < var2.params.length) {
            System.out.println(a("\u001e;_p\u000e<3\u00044") + var2.params[var10]);
            var9.load(var2.params[var10], var8, false);
            ++var10;
            if (var12) {
               break;
            }
         }

         MibOutputter var20 = new MibOutputter(var17 ? 0 : 1);
         var20.setVerbose(true);
         var20.setResolveParent(true);
         if (var5 != null) {
            try {
               var20.output(var8, var5);
               return;
            } catch (Exception var15) {
               System.out.println(var15);
               if (!var12) {
                  return;
               }
            }
         }

         try {
            var20.outputModuleFiles(var8, var7, var19, var18);
         } catch (Exception var13) {
            System.out.println(var13);
         }
      } catch (Exception var16) {
         System.out.println(var16);
      }

   }

   private static void a() {
      System.out.println(a("X\u0001mU \u0017^\u001e4G\n9R@\b\u001f=\\4<=$J}\b<'c4[*9R9\n;6\u0013r\u000e>1\u00004m\u0016\u0011mW5\u001b\u0004j](\u001c^\u001e4G\u0006<WgG3$Nx\u000e15J}\b<tI}\u000b>t]{\t$1L`G\u0016-Pu\n;7mZ*\u0002tfY+r\u0019[`\u000665JuG4=Rq\u0014Xt\u001e4\u000e< Q44\u001c\u0019n4*\u001b\u0016\u001er\u000e>1M4\u0001=&\u001eG*\u001b\"\f:G\u0010-\u001ep\u000245Kx\u0013~tW`G%=RxGXt\u001e4\u00007:[f\u0006&1Z4\u0006r'Wz\u0000>1\u001er\u000e>1\u001ed\u0002 ts]%r9Qp\u0012>1\u001ea\u0014;:Y4\u0013:1\u001ey\b6!RqG<5Sqmrt\u001eu\u0014r VqG05MqG4=RqG<5SqG3:Z4@|9Wv@r5M4\u0013:1\u001eq\u001f&1Pg\u000e=:\u0010\u001em\u0013\u0004nX.\u0011\u0015j](\u001ctp[3\u0017\u000744Gr\u0015J4\u0017 1Mq\t&x\u001e`\u000f7tfy\u000b`\u0019WvG1;Pb\u0002 'W{\tr!J}\u000b; G4\u0003=1M4\t= \u001eg\u0012\"$Qf\u0013Xt\u001e4\u0013:1\u001eY(\u0016\u0001rQJ\u0011\u001bsD+\u001b\u0015pW\"~tqV-\u0017\u0017j9.\u0016\u0011p@.\u0006\r\u001e{\u0015r\u0015yQ)\u0006y}U7\u0013\u0016wX.\u0006\u001d{Gmrt\u001eY.\u0010t]{\t! La\u0004&'\u001043:1MqG%=RxG01\u001eu\u000361Z4\u000e<t_4\u0001' Kf\u0002r&[x\u00023'[:mX\u001bn@.\u001d\u001am\u001eGrt\u0013+\u001b:1RdGrt\u001e4Grt\u001e4]r$L}\t&tJ|\u000e!tSq\u0014!5Yqmrt\u001e9\b\t!Jd\u0012&\t\u001er\t39[4GhtJ|\u0002r\u0019wVG=!Jd\u0012&tX}\u000b7z\u001e@\u000f;'\u001er\u000e>1\u001ec\u000e>844Grt\u001e4Grt\u001e4Grt\u001e4Grt\u001e4G1;P`\u0006;:\u001eu\u000b>tQrG&<[4*\u001b\u0016\u001ey\b6!Rq\u0014|twrG&<Wgmrt\u001e4Grt\u001e4Grt\u001e4Grt\u001e4GrtQd\u0013;;P4\u000e!tQy\u000e& [pKr;PqG4=RqG\"1L4*\u001b\u0016\u001ey\b6!Rqmrt\u001e4Grt\u001e4Grt\u001e4Grt\u001e4GrtI}\u000b>t\\qG51Pq\u00153 [pG3!J{\n3 Ww\u0006>8G:Grt\u001e4Gr\u000fP{\t7\t44GryQb\u0002 #L}\u00137t\u001e4Grt\u001e.G=\"[f\u0010 =JqG7,Wg\u0013;:Y4*\u001b\u0016\u001er\u000e>1M4\u000e4tPq\u000261Z:mrt\u001e9\u0003\t=LIG6=Lq\u0004&;LmGhtJ|\u0002r0Wf\u00021 Qf\u001er2QfG3!J{G51Pq\u00153 [pG\u001f\u001d|4\u0001;8[gG\tzc\u001eGrt\u0013q\u001f&t[l\u00137:M}\b<t\u001e4]r VqG4=RqG7,Jq\t! W{\tr2QfG\u001f\u001d|4\u0001;8[gIrt\u001e4G\t9Wv:X"));
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 82;
               break;
            case 1:
               var10003 = 84;
               break;
            case 2:
               var10003 = 62;
               break;
            case 3:
               var10003 = 20;
               break;
            default:
               var10003 = 103;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
