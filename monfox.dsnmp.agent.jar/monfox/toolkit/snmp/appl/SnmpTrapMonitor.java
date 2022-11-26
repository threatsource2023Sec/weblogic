package monfox.toolkit.snmp.appl;

import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.mgr.SnmpInformListener;
import monfox.toolkit.snmp.mgr.SnmpParameters;
import monfox.toolkit.snmp.mgr.SnmpPendingInform;
import monfox.toolkit.snmp.mgr.SnmpSession;
import monfox.toolkit.snmp.mgr.SnmpSessionConfig;
import monfox.toolkit.snmp.mgr.SnmpTrapListener;
import monfox.toolkit.snmp.mgr.usm.UsmNotInTimeWindowListener;
import monfox.toolkit.snmp.mgr.usm.UsmTimelinessParameters;
import monfox.toolkit.snmp.v3.usm.USMUserTable;

public class SnmpTrapMonitor extends a implements SnmpTrapListener, SnmpInformListener {
   private static final String a = "$Id: SnmpTrapMonitor.java,v 1.16 2011/02/09 02:28:36 sking Exp $";

   public static void main(String[] var0) {
      try {
         SnmpTrapMonitor var1 = new SnmpTrapMonitor(var0);
         var1.g();
      } catch (Throwable var2) {
         h();
         System.out.println("\n");
         System.out.println(a("bF*<_\u0010)XC-") + var2.getMessage());
         System.out.println("\n");
      }

   }

   SnmpTrapMonitor(String[] var1) throws Throwable {
      super(var1);
   }

   void g() {
      try {
         if (this.d.hasFlag("?")) {
            h();
            System.exit(1);
         }

         this.e();
         this.C = this.d.getIntOption(a("2\\z\u0016\u007f6"), 162);
         SnmpParameters var1 = this.a(true);
         SnmpSessionConfig var2 = SnmpSession.newConfig();
         var2.setTransportType(this.E);
         var2.setMetadata(SnmpFramework.getMetadata());
         var2.setLocalPort(this.C);
         SnmpSession var3 = null;
         if (this.x != null) {
            var2.setEngineID(this.x);
            var3 = new SnmpSession(var2);
            if (this.y >= 0 && this.z >= 0) {
               var3.getSnmpEngine().setEngineBoots(this.y);
               var3.getSnmpEngine().setEngineTime(this.z);
            }
         } else {
            var3 = new SnmpSession(var2);
         }

         if (var1.getUserTable() != null) {
            var3.getUsm().addDefaultUserTable((USMUserTable)var1.getUserTable());
         }

         var3.getUsm().setNotInTimeWindowListener(new UsmNotInTimeWindowListener() {
            public boolean handleNotInTimeWindow(UsmTimelinessParameters var1) {
               System.out.println(a("\u0010.Vr\u001a_v$\u001dh \u00048=NSJ\";W\u007fs\u001f<^uSV4Uh\u0004\u0013<]sJ\u0013h\u001a") + var1.getEngineID() + a("\u0010."));
               return true;
            }

            private static String a(String var0) {
               char[] var1 = var0.toCharArray();
               int var2 = var1.length;

               for(int var3 = 0; var3 < var2; ++var3) {
                  char var10002 = var1[var3];
                  byte var10003;
                  switch (var3 % 5) {
                     case 0:
                        var10003 = 26;
                        break;
                     case 1:
                        var10003 = 36;
                        break;
                     case 2:
                        var10003 = 118;
                        break;
                     case 3:
                        var10003 = 82;
                        break;
                     default:
                        var10003 = 58;
                  }

                  var1[var3] = (char)(var10002 ^ var10003);
               }

               return new String(var1);
            }
         });
         var3.addTrapListener(this);
         var3.addInformListener(this);
         System.out.println(a("\u000e\u000fy\rh,\u000fd\u001e--\b*\tb0\u00120") + this.C);

         while(true) {
            Thread.sleep(1000L);
         }
      } catch (Exception var4) {
         var4.printStackTrace();
      }
   }

   public void handleTrap(monfox.toolkit.snmp.mgr.SnmpTrap var1) {
      boolean var2 = SnmpTrapLogger.i;
      System.out.println(a("oK'Y^,\u000bzYY0\u0007zY_'\u0005o\u0010{'\u0002*T o"));
      System.out.println(a("bF*Y['\u0014y\u0010b,F*Y-bF*Y7b") + Snmp.versionToString(var1.getVersion()));
      System.out.println(a("bF*Y^-\u0013x\u001ahbF*Y-bF*Y7b") + var1.getSource());
      if (var1.getCommunity() != null) {
         System.out.println(a("bF*YN-\u000bg\fc+\u0012sY-bF*Y7b") + SnmpFramework.byteArrayToString(var1.getCommunity(), (SnmpOid)null));
      }

      if (var1.getContext() != null) {
         System.out.println(a("bF*YN-\b~\u001cu6F*Y-bF*Y7b") + var1.getContext());
      }

      System.out.println(a("bF*YH,\u0012o\u000b}0\u000fy\u001c-bF*Y7b") + var1.getEnterprise());
      if (var1.getAgentAddr() != null) {
         System.out.println(a("bF*YL%\u0003d\rL&\u0002xY-bF*Y7b") + var1.getAgentAddr());
      }

      System.out.println(a("bF*YY0\u0007z6D\u0006F*Y-bF*Y7b") + var1.getTrapOid());
      System.out.println(a("bF*Y_#\u0011^\u000bl2)C=-bF*Y7b") + var1.getTrapOid().toNumericString());
      System.out.println(a("bF*YY0\u0007zYB \fo\u001ay1F*Y7b") + var1.getObjectValues());
      System.out.println(a("bF*Y_#\u0011*/l0$c\u0017i1F*Y7b") + var1.getVarBindList());
      if (SnmpException.b) {
         SnmpTrapLogger.i = !var2;
      }

   }

   public void handleInform(SnmpPendingInform var1) {
      System.out.println(a("oK'Y^,\u000bzYD,\u0000e\u000b`b4o\u001ah+\u0010o\u001d-oK'"));
      System.out.println(a("bF*Y['\u0014y\u0010b,F*Y-bF*Y7b") + Snmp.versionToString(var1.getVersion()));
      System.out.println(a("bF*Y^-\u0013x\u001ahbF*Y-bF*Y7b") + var1.getSource());
      if (var1.getCommunity() != null) {
         System.out.println(a("bF*YN-\u000bg\fc+\u0012sY-bF*Y7b") + SnmpFramework.byteArrayToString(var1.getCommunity(), (SnmpOid)null));
      }

      if (var1.getContext() != null) {
         System.out.println(a("bF*YN-\b~\u001cu6F*Y-bF*Y7b") + var1.getContext());
      }

      System.out.println(a("bF*YY0\u0007z6D\u0006F*Y-bF*Y7b") + var1.getTrapOid());
      System.out.println(a("bF*YD,\u0000e\u000b`b)h\u0013h!\u0012yY7b") + var1.getRequestObjectValues());
      System.out.println(a("bF*Y_#\u0011*/l0\u0004c\u0017i1F*Y7b") + var1.getRequestVarBindList());
      var1.sendResponse();
   }

   private String a(int var1) {
      switch (var1) {
         case 0:
            return a("4W");
         case 1:
            return a("4T");
         case 2:
         default:
            return a("7\ba\u0017b5\b");
         case 3:
            return a("4U");
      }
   }

   static void h() {
      System.out.println(a("HF*,^\u0003!Os\u0007Hl*Y-bF`\u0018{#FY\u0017`22x\u0018}\u000f\td\u0010y-\u0014*\" }\u001ae\ty+\td\nPHl*YI\u00075I+D\u00122C6CHl*Y-bFF\u0010~6\u0003dYk-\u0014*\r\u007f#\u0016yYl,\u0002*\u0010c$\tx\u0014~b\tdYy*\u0003*\n}'\u0005c\u001fd'\u0002*\tb0\u0012$s\u0007bFE)Y\u000b)D*\u0007HF*Y-bKz\"b0\u0012WY-bF*Y12\tx\r3bF*Y-xFf\u0016n#\n*\r\u007f#\u0016*\u0015d1\u0012o\u0017-2\tx\r-bF*Y-bF*Y-b=;O?\u001fl\u0000Y-bF*T`'\u0012k\u001dl6\u0007*Y-bZl\u0010a'\bk\u0014h|F0Y`'\u0012k\u001dl6\u0007*\u001fd.\u0003*\rbb\ne\u0018ibF*Y-bF*Y-bF*\"`+\u0004'KPHF*Y-bKG\u0005`+\u0004n\u0010\u007f1F*Y1&\u000fxT}#\u0012bG-xFn\u0010\u007f'\u0005~\u0016\u007f+\u0003yYb$Fz\u000bh!\tg\td.\u0003nY@\u000b$yY-b=n\u001ck#\u0013f\rPHF*Y-bKg\"d \u0015WY-bF*Y1/\u000fhTa+\u0015~G-xFf\u0010~6Fe\u001f-\u000f/H\n-6\t*\u0015b#\u0002*\u001f\u007f-\u000b*\u0014d \u0002c\u000b~b=g\u0010ooTWs-bF*Y-bF*Y-bF*Y-bF*Y-bF*Y-bF*Y-j\u0002o\u001f7b5D4]4T'4D\u0000\\C? \u000f/HCY\u00016'4D\u0000O\u0000s-bF*Y .\u000fy\r-bF*Y-bF*Y-bF*Y-bF*C-.\u000fy\r-#\u0010k\u0010a#\u0004f\u001c-\u000f/H\n-bF*Y-bF*Y-bF*YV$\u0007f\nh\u001fl\u0000Y-bF*Ta-\u0001*Y-bF*Y-bZf\u0016j$\u000ff\u001c3bF0Ya-\u0001l\u0010a'F~\u0016-1\u0012e\u000bhb\u0002o\u001bx%Fe\fy2\u0013~Y-bF*\"c-\bo$\u0007bF*Y-o\u0002Q\f`2;*Y-bF*Y-bF*Y-bF*Y7b\u0002\u007f\u0014}b\u0002o\u001bx%Fc\u0017k-F~\u0016-1\u0012n\u0016x6F*Y-bF*Y-\u0019\tl\u001fPHF*Y-bF*Y-bF*Y-bF*Y-bF*Y-bF*Y-bF\"\u0017b6\u00030Yz+\nfYc-\u0012*\u000eb0\r*\u000ed6\u000e*Ta-\u0001#s\u0007bF*Y-o)*\u0016x6\tz\r~bF*Y-bF*Y-bF*Y7b\u0002c\n}.\u0007sYb7\u0012z\fyb\tz\rd-\byY-bF*Y-bF*Y-\u0019\u000fWs-bF*Y-bF*Y-bF*Y-bF*Y-bF*Y-bF*Y-,\\*\t\u007f+\b~YB\u000b\"yYd,Fd\f`'\u0014c\u001a-$\tx\u0014l6l*Y-bF*Y-bF*Y-bF*Y-bF*Y-bF*Y-bF*\u00157b\u0016x\u0010c6FE0I1F}\u0010y*Fx\u001c~-\n|\u001cib\nk\u001bh.\u0015\u0000Y-bF*Y-bF*Y-bF*Y-bF*Y-bF*Y-bF*YdxFz\u000bd,\u0012*6D\u0006\u0015*\u000ed6\u000e*\u001fb0\u000bk\ry'\u0002*\u0010c&\u0003r\u001c~HF**C\u000f6|J-\r6^0B\f5\u0000s") + c() + "\n" + "\n" + a("bF*Y-\f)^<7b09Yh,\u0001c\u0017hb\u0014o\u0015l6\u0003nY}#\u0014k\u0014h6\u0003x\n-jKoU \u0018O*\u0018}2\nsYy-F~\u0011hb\ne\u001al.F\u0000") + a("bF*Y-bF*Y-b\u0007\u007f\re-\u0014c\rl6\u000f|\u001c-'\bm\u0010c'Fl\u0016\u007fb\u0014o\u001ah+\u0010c\u0017jb/d\u001fb0\u000b*\u0014h1\u0015k\u001eh1H*,^\u000fl") + a("bF*Y-bF*Y-b\u0013y\u001c\u007fb\u000fd\u001fb0\u000bk\rd-\b*\u0018}2\nc\u001c~b\u0012eYh+\u0012b\u001c\u007fb\u0012b\u001c-.\ti\u0018ab\txY\u007f'\u000be\rhH") + a("bF*Y-bF*Y-b3Y4-7\u0015o\u000b~ll"));
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 66;
               break;
            case 1:
               var10003 = 102;
               break;
            case 2:
               var10003 = 10;
               break;
            case 3:
               var10003 = 121;
               break;
            default:
               var10003 = 13;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
