package monfox.toolkit.snmp;

import monfox.toolkit.snmp.agent.SnmpAgent;
import monfox.toolkit.snmp.agent.replicator.SnmpAgentReplicator;
import monfox.toolkit.snmp.appl.SnmpBulkWalk;
import monfox.toolkit.snmp.appl.SnmpExplore;
import monfox.toolkit.snmp.appl.SnmpGet;
import monfox.toolkit.snmp.appl.SnmpGetAll;
import monfox.toolkit.snmp.appl.SnmpGetBulk;
import monfox.toolkit.snmp.appl.SnmpGetNext;
import monfox.toolkit.snmp.appl.SnmpGroupMonitor;
import monfox.toolkit.snmp.appl.SnmpInform;
import monfox.toolkit.snmp.appl.SnmpSet;
import monfox.toolkit.snmp.appl.SnmpTableMonitor;
import monfox.toolkit.snmp.appl.SnmpTrap;
import monfox.toolkit.snmp.appl.SnmpTrapLogger;
import monfox.toolkit.snmp.appl.SnmpTrapMonitor;
import monfox.toolkit.snmp.appl.SnmpUsm;
import monfox.toolkit.snmp.appl.SnmpVacm;
import monfox.toolkit.snmp.appl.SnmpWalk;
import monfox.toolkit.snmp.metadata.gen.SnmpMibGen;
import monfox.toolkit.snmp.metadata.output.XmlToMib;

public class Main {
   public static void main(String[] var0) {
      boolean var4 = SnmpValue.b;
      if (var0.length == 0) {
         a();
         System.exit(1);
      }

      String var1 = var0[0];
      String[] var2 = new String[var0.length - 1];
      int var3 = 0;

      while(true) {
         if (var3 < var2.length) {
            var2[var3] = var0[var3 + 1];
            ++var3;
            if (var4) {
               break;
            }

            if (!var4) {
               continue;
            }
         }

         if (var1.equals(a("~_'u^DS\r`}"))) {
            SnmpMibGen.main(var2);
            if (!var4) {
               return;
            }
         }
         break;
      }

      if (var1.equals(a("~_'uTHE"))) {
         SnmpGet.main(var2);
         if (!var4) {
            return;
         }
      }

      if (var1.equals(a("~_'uTHE\u000bi\u007f"))) {
         SnmpGetAll.main(var2);
         if (!var4) {
            return;
         }
      }

      if (var1.equals(a("~_'uTHE\u0004`kY"))) {
         SnmpGetNext.main(var2);
         if (!var4) {
            return;
         }
      }

      if (var1.equals(a("~_'uTHE\bp\u007fF"))) {
         SnmpGetBulk.main(var2);
         if (!var4) {
            return;
         }
      }

      if (var1.equals(a("~_'u@HE"))) {
         SnmpSet.main(var2);
         if (!var4) {
            return;
         }
      }

      if (var1.equals(a("~_'uVUA&jaH"))) {
         SnmpExplore.main(var2);
         if (!var4) {
            return;
         }
      }

      if (var1.equals(a("~_'uT_^?u^B_#q|_"))) {
         SnmpGroupMonitor.main(var2);
         if (!var4) {
            return;
         }
      }

      if (var1.equals(a("~_'uGLS&`^B_#q|_"))) {
         SnmpTableMonitor.main(var2);
         if (!var4) {
            return;
         }
      }

      if (var1.equals(a("~_'uDL]!"))) {
         SnmpWalk.main(var2);
         if (!var4) {
            return;
         }
      }

      if (var1.equals(a("~_'uQX]!RrAZ"))) {
         SnmpBulkWalk.main(var2);
         if (!var4) {
            return;
         }
      }

      if (var1.equals(a("~_'uG_P:H|CX>ja"))) {
         SnmpTrapMonitor.main(var2);
         if (!var4) {
            return;
         }
      }

      if (var1.equals(a("~_'uG_P:"))) {
         SnmpTrap.main(var2);
         if (!var4) {
            return;
         }
      }

      if (var1.equals(a("~_'uRJT$q"))) {
         SnmpAgent.main(var2);
         if (!var4) {
            return;
         }
      }

      if (var1.equals(a("~_'uZCW%w~"))) {
         SnmpInform.main(var2);
         if (!var4) {
            return;
         }
      }

      if (var1.equals(a("~_'uF^\\"))) {
         SnmpUsm.main(var2);
         if (!var4) {
            return;
         }
      }

      if (var1.equals(a("~_'uELR'"))) {
         SnmpVacm.main(var2);
         if (!var4) {
            return;
         }
      }

      if (var1.equals(a("u\\&Q|`X("))) {
         XmlToMib.main(var2);
         if (!var4) {
            return;
         }
      }

      if (var1.equals(a("~_'uRJT$qAHA&lpLE%w"))) {
         SnmpAgentReplicator.main(var2);
         if (!var4) {
            return;
         }
      }

      if (var1.equals(a("~_'uG_P:I|JV/w"))) {
         SnmpTrapLogger.main(var2);
         if (!var4) {
            return;
         }
      }

      if (var1.equals(a("{T8vzB_"))) {
         Version.main(var2);
         if (!var4) {
            return;
         }
      }

      if (var1.equals(a("\u0000G/w`D^$"))) {
         Version.main(var2);
         if (!var4) {
            return;
         }
      }

      if (var1.equals(a("\u0000]#fvCB/"))) {
         License.main(var2);
         if (!var4) {
            return;
         }
      }

      a();
   }

   private static void a() {
      System.out.println(a("';j%F~p\r@)';@%3\r\u0011jC|_\\j4:\r\u0011jor[Pj(yLCja`C\\:(;LV/kgQ\\-w:\u0003[+w3\u0011R%h~L_.;3v\u001cuX\u0019\r\u0011j%3\r^8\u000f3\r\u0011j%UBC'%!\u0004\u0011j%yLG+%~B_,jk\u0003E%j\u007fFX>+`C\\:+^LX$%/N^'hrCUt%H\u0000\u000e\u0017\u000f\u0019\r\u0011\u000e@@nc\u0003UGd~\u0004?\u0019'\u0011j%3\rd'gaH]&d3LA:izNP>l|C\u0011,ja\r]+p}NY#kt\r^>mv_\u0011\u000e|}L\\#f@c|\u001a-A\u0004;j%3\r\u0011+ucAX)dgD^$v=';j%\\}e\u0003J]~\u000b@\u000f3\r\u0011j%CLC+hvYT8%/N^'hrCUt%~LHjgv\r^$`3BWp%\u0019'\u0011j%3\r\u0011j(eHC9l|C\u0011j%3\r\u0011j%3\r\u0011j?3jT>%gETjwvAT+vv\rG/w`D^$\u000f3\r\u0011j%3\r\u001c&lpH_9`3\r\u0011j%3\r\u0011j%3\r\u000bjBvY\u0011>mv\r]#fvCB/%zCW%w~LE#j}';j%3\r\u0011j%@C\\:HzOv/k3\r\u0011j%3\r\u0011j%)\rr%hcD]/%@c|\u001a%^dsjczAT9%zCE%%~HE+arYP@%3\r\u0011j%3u\\&Q|`X(%3\r\u0011j%3\r\u0011j%3\u0017\u0011\tj}[T8q3`^$c|U\u0011\u0012H_\r|\u0003G`\rX$q|\rb\u0004HC\r|\u0003G`';j%3\r\u0011j%@C\\:BvY\u0011j%3\r\u0011j%3\r\u0011j%)\ra/wuBC'%rC\u0011\u0019k~]v/q\u0019\r\u0011j%3\r\u0011\u0019k~]v/qRA]j%3\r\u0011j%3\r\u0011p%CHC,ja@\u0011+k3jT>%|K\u0011+i\u007f\rE+g\u007fH\u0011)j\u007fX\\$v\u0019\r\u0011j%3\r\u0011\u0019k~]v/q]HI>%3\r\u0011j%3\r\u0011p%CHC,ja@\u0011+k3~_'uTHE\u0004`kY;j%3\r\u0011j%@C\\:BvYs?ix\r\u0011j%3\r\u0011j%)\ra/wuBC'%rC\u0011\u0019k~]v/qQX]!\u000f3\r\u0011j%3\rb$hc~T>%3\r\u0011j%3\r\u0011j%3\r\u000bjUv_W%w~\rP$%@C\\:VvY;j%3\r\u0011j%@C\\:@k]]%wv\r\u0011j%3\r\u0011j%)\ra/wuBC'%rC\u0011\u000f}cA^8`3BA/wrYX%k\u0019\r\u0011j%3\r\u0011\u0019k~]v8jf]|%kzY^8%3\r\u0011p%^B_#q|_\u0011+%t_^?u3BWjjqGT)q`'\u0011j%3\r\u0011jV}@A\u001edqAT\u0007j}DE%w3\r\u0011j?3`^$lgBCjd3`x\b%gLS&`\u0019\r\u0011j%3\r\u0011\u0019k~]f+ix\r\u0011j%3\r\u0011j%3\r\u0011p%CHC,ja@\u0011+%^dsjrrAZj%3\r\u0011j-tHEgkvUEc\u000f3\r\u0011j%3\rb$hcoD&nDL]!%3\r\u0011j%3\r\u000bjUv_W%w~\rPjHZo\u0011(p\u007fF\u0011=d\u007fF\u0011bbvY\u001c(p\u007fF\u0018@%3\r\u0011j%3~_'uG_P:H|CX>ja\r\u0011j%3\u0017\u0011\u0006l`YT$%uBCjqaLA9\u000f3\r\u0011j%3\rb$hcyC+u3\r\u0011j%3\r\u0011j%3\r\u000bjVvCUjqaLA9\u000f3\r\u0011j%3\rb$hcyC+u_BV-`a\r\u0011j%3\r\u000bjI|J\u0011\u0019K^}\u0011>wr]Bjq|\rPji|J\u0011,l\u007fH;j%3\r\u0011j%@C\\:L}K^8h3\r\u0011j%3\r\u0011j%)\rb/kw\rP$%@C\\:L}K^8h3_T;pv^E@%3\r\u0011j%3~_'uF^\\j%3\r\u0011j%3\r\u0011j%3\u0017\u0011\u001a`aK^8h3xb\u0007%rI\\#kz^E8dgD^$%\u0019\r\u0011j%3\r\u0011\u0019k~]g+f~\r\u0011j%3\r\u0011j%3\r\u0011p%CHC,ja@\u0011\u001cDP`\u0011+a~D_#vg_P>l|C;@%3\r\u0011j%3~_'uRJT$q3\r\u0011j%3\r\u0011j%3\u0017\u0011\u0018p}\rE\"`3IT,dfAEjV}@A\u000bbvCE@%3\r\u0011j%3~_'uRJT$qAHA&lpLE%w3\u0017\u0011\u0018p}\rE\"`3IT,dfAEjdtH_>%aHA&lpLE%w\u0019'\u0011j(>\u0000\u001cg(>\u0000\u001cg(>\u0000\u001cg(>\u0000\u001cg(>\u0000\u001cg(>\u0000\u001cg(>\u0000\u001cg(>\u0000\u001cg(>\u0000\u001cg(>\u0000\u001cg(>\u0000\u001cg\u000f3\r\u001cjL}\r^8av_\u0011>j3JT>%gETjp`LV/%uBCjd}T\u0011%c3YY/vv\rR%h~L_.v3\u0000;j%>\rB#hcAHjuaBG#av\rE\"`3N^'hrCUjkr@TjrzYYjq{H\u0011g:3K]+b=\r\u0011j(\u0019\r\u0011g(>\u0000\u001cg(>\u0000\u001cg(>\u0000\u001cg(>\u0000\u001cg(>\u0000\u001cg(>\u0000\u001cg(>\u0000\u001cg(>\u0000\u001cg(>\u0000\u001cg(>\u0000\u001cg(>\u0000\u001c@"));
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 45;
               break;
            case 1:
               var10003 = 49;
               break;
            case 2:
               var10003 = 74;
               break;
            case 3:
               var10003 = 5;
               break;
            default:
               var10003 = 19;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
