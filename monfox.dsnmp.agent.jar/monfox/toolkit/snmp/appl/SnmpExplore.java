package monfox.toolkit.snmp.appl;

import java.util.Vector;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.mgr.SnmpErrorException;
import monfox.toolkit.snmp.mgr.SnmpExplorer;
import monfox.toolkit.snmp.mgr.SnmpExplorerListener;
import monfox.toolkit.snmp.mgr.SnmpParameters;
import monfox.toolkit.snmp.mgr.SnmpPeer;
import monfox.toolkit.snmp.mgr.SnmpSession;

public class SnmpExplore extends a implements SnmpExplorerListener {
   private String g = null;
   private String i = null;
   private Vector a = new Vector();
   private static final String b = "$Id: SnmpExplore.java,v 1.17 2003/12/30 17:47:12 sking Exp $";

   public static void main(String[] var0) {
      try {
         SnmpExplore var1 = new SnmpExplore(var0);
         var1.g();
      } catch (Throwable var2) {
         h();
         System.out.println("\n");
         System.out.println(a("\"\u001f+N#PpY1Q") + var2.getMessage());
         System.out.println("\n");
      }

   }

   SnmpExplore(String[] var1) throws Throwable {
      super(var1, "", new String[0], a("fZ"), new String[]{a("fZgj\b"), a("gG{g\u001epZ_b\u001cgP~\u007f")});
   }

   void g() {
      boolean var7 = SnmpTrapLogger.i;
      SnmpVarBindList var1 = null;

      try {
         if (this.e.length < 2) {
            h();
            System.exit(1);
         }

         SnmpExplorer var3;
         SnmpParameters var4;
         int var5;
         label38: {
            label37: {
               this.g = this.e[0];
               this.i = this.e[1];
               this.e();
               SnmpSession var2 = new SnmpSession();
               var3 = new SnmpExplorer(var2, this, (long)this.B);
               var4 = new SnmpParameters(this.f, (String)null, (String)null);
               var4.setVersion(this.h);
               var1 = new SnmpVarBindList();
               var1.cacheEncoding(true);
               if (this.e.length > 2) {
                  var5 = 2;

                  while(var5 < this.e.length) {
                     var1.add(this.e[var5]);
                     ++var5;
                     if (var7) {
                        break label38;
                     }

                     if (var7) {
                        break;
                     }
                  }

                  if (!var7) {
                     break label37;
                  }
               }

               var1.add(a("3\u00118%G,\u000e%9_3\u0011:%@,\u000f"));
            }

            var5 = this.d.getIntOption(a("g\u0005ns\u0001nPyn%kRnd\u0004v"), 0);
         }

         int var6 = this.d.getIntOption(a("fZgj\b"), 3);
         var3.setDelayPeriod(var6);
         var3.performExplore(var4, var1, this.g, this.i, this.C, (long)var5);
         Thread.sleep(10000L);
      } catch (SnmpErrorException var8) {
         this.a(var8, var1);
      } catch (Exception var9) {
         var9.printStackTrace();
         System.out.println(var9);
      }

   }

   public void handleDiscovered(SnmpExplorer var1, SnmpPeer var2, SnmpParameters var3, int var4, int var5, SnmpVarBindList var6) {
      System.out.println(a("(\u0012&&\\/\u0012&&\\/\u0012&&\\/\u0012&&\\/\u0012&&\\/\u0012&&\\/\u0012&&\\/\u0012&&\\/\u0012&&\\/\u0012&&\\/\u0012&&\\/\u0012&&\\/\u0012&&\\/\u0012&&\\/\u0012&&\\("));
      System.out.println(a("(\u001fOB\"Ap]N#G{1+") + var2.getAddress());
      if (var4 != 0) {
         System.out.println(a("(\u001fNy\u0003mM1+YgMyd\u0003?") + Snmp.errorStatusToString(var4) + a(".\u001fbe\u0015gG6") + var5 + ")");
      }

      System.out.println(a("(\u001f]j\u0003@Veo\u00028"));
      this.a(a("(\u001f++"), var6);
      System.out.println(a("(\u0012&&\\/\u0012&&\\/\u0012&&\\/\u0012&&\\/\u0012&&\\/\u0012&&\\/\u0012&&\\/\u0012&&\\/\u0012&&\\/\u0012&&\\/\u0012&&\\/\u0012&&\\/\u0012&&\\/\u0012&&\\/\u0012&&\\(5"));
      this.a.addElement(var2.getAddress());
   }

   public void handleCompleted(SnmpExplorer var1) {
      System.out.println(a("FVxh\u001etZyn\u0015\"") + this.a.size() + a("\"lEF!\"[n}\u0018aZx+\u0018l\u001fx~\u0013lZ\u007f1") + this.g + "/" + this.i + ".");
      System.exit(1);
   }

   public void handleCancelled(SnmpExplorer var1) {
      System.out.println(a("A^eh\u0014nSno_"));
      System.exit(1);
   }

   static void h() {
      System.out.println(a("\b\u001f+^\"CxN\u0001{\"\u001f++QQQf{4zOgd\u0003g\u001fP&N~P{\u007f\u0018mQxVQ>Qn\u007f\u0006mM`5Q>Qn\u007f\u001ccL`5QY\u0003}j\u0003/Qjf\u0014<b!\u0001{\"\u001fON\"AmB[%KpE\u0001{\"\u001f++QRZym\u001epRx+\u0010\"]jx\u0018a\u001fx~\u0013lZ\u007f|\u001epT+n\trSdy\u0014\"P{n\u0003cKbd\u001f\"]r+\u0018qL~b\u001fe5++Q\"\u001fXe\u001crxn\u007fQmOny\u0010vVde\u0002\"Kd+\u0014c\\c+\u0018l[b}\u0018fJjgQKo+j\u0015fMnx\u0002\"L{n\u0012kYbn\u0015\b\u001f++Q\"]r+\u0005jZ+{\u0003mIbo\u0014f\u001fen\u0005uPy`^lZ\u007ff\u0010qT+h\u001eo]be\u0010vVde_\b5++>RkBD?Q5\u0001+Q\"\u001f+&\u0015gSjrQ\"\u001f++Q\"\u001f7f\u0018nSbxO\"\u001f1+\u0015gSjrQ`Z\u007f|\u0014gQ+f\u0014qLjl\u0014q\u001f++QY\fV\u0001Q\"\u001f++\\gds{\u001dmMn_\u0018oZd~\u0005_\u001f7\u007f\u001e<\u001f+1QgG{g\u001epZ+\u007f\u0018oZd~\u0005\"Ve+\u001ckSgb\u0002\"ded\u001fgb\u0001") + b());
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 2;
               break;
            case 1:
               var10003 = 63;
               break;
            case 2:
               var10003 = 11;
               break;
            case 3:
               var10003 = 11;
               break;
            default:
               var10003 = 113;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
