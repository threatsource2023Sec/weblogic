package monfox.toolkit.snmp.appl;

import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.mgr.SnmpErrorException;
import monfox.toolkit.snmp.mgr.SnmpReportException;
import monfox.toolkit.snmp.mgr.SnmpTimeoutException;

public class SnmpGet extends a {
   private static final String[] a = new String[0];
   private static final String[] f = new String[]{a("\u0002\u00009(|\u0016")};
   private static final String g = "$Id: SnmpGet.java,v 1.30 2007/03/23 04:12:00 sking Exp $";

   public static void main(String[] var0) {
      try {
         SnmpGet var1 = new SnmpGet(var0);
         var1.g();
      } catch (Throwable var2) {
         h();
         System.out.println("\n");
         System.out.println(a("REk\u0004A *\u0019{3") + var2);
         System.out.println("\n");
      }

   }

   SnmpGet(String[] var1) throws Throwable {
      super(var1, "", a, "", f);
   }

   void g() {
      SnmpVarBindList var1 = null;

      Exception var10000;
      label49: {
         boolean var10001;
         int var2;
         try {
            if (this.d.hasFlag("?") || this.e.length == 0) {
               h();
               System.exit(1);
            }

            var2 = this.d.getIntOption(a("\u0002\u00009(|\u0016"), 0);
            this.e();
            this.c = this.f();
            var1 = new SnmpVarBindList(this.e);
         } catch (Exception var9) {
            var10000 = var9;
            var10001 = false;
            break label49;
         }

         label46:
         while(true) {
            try {
               try {
                  this.b.reset();
                  SnmpVarBindList var3 = this.c.performGet(this.b, var1);
                  this.a(var3);
               } catch (SnmpTimeoutException var4) {
                  System.out.println(a("7\u00179.aHE\u001f(~\u0017\n>5"));
               } catch (SnmpErrorException var5) {
                  this.a(var5, var1);
               } catch (SnmpReportException var6) {
                  System.out.println(a(" \u0000;.a\u0006_k") + var6);
               }

               if (var2 > 0) {
                  Thread.sleep((long)(var2 * 1000));
               }
            } catch (Exception var8) {
               var10000 = var8;
               var10001 = false;
               break;
            }

            do {
               try {
                  if (var2 > 0) {
                     continue label46;
                  }
               } catch (Exception var7) {
                  var10000 = var7;
                  var10001 = false;
                  break label46;
               }
            } while(SnmpTrapLogger.i);

            return;
         }
      }

      Exception var10 = var10000;
      var10.printStackTrace();
      System.out.println(a("77\u0019\u000eAHE") + var10);
   }

   static void h() {
      System.out.println(a("xEk\u0014@3\"\u000eK\u0019REka3\u0018\u0004= 3!\u000b&1T\u0017\u0011k\u001a>M\u0019$1g\u001b\n%2NRY$#y\u0017\u0006?\bWLOAK3R!\u000e\u0012P ,\u001b\u0015Z=+AK3REkaC\u0017\u0017-.a\u001f\u0016k 3\u0010\u00048(pR6%,c5\u0000?a|\u0002\u00009 g\u001b\n%a|\u001cE?)vR\u0016;$p\u001b\u0003\"$wR(\u0002\u0003\u0019REka3\u0004\u00049(r\u0010\t.2=R<$43\u001f\u00042a`\u0002\u0000((u\u000bE*ac\u0017\u0017\".wR\f%a`\u0017\u0006$/w\u0001E<)z\u0011\rk6z\u001e\tkK3REkap\u0013\u00108$3\u0006\r.ar\u0002\u0015'(p\u0013\u0011\".}R\t$.cR\u0004%%3\u0000\u0000?3z\u0017\u0013.arR\u000b.63\u0004\u0004'4vR\u0000=$a\u000bEAa3REk}c\u0017\u0017\".wLE8$p\u001d\u000b/2=xokaR\"5\u0007\bP31\u0002\u000e]R*\u001b\u0015Z=+\u0018K\u0019REka3_\u0015.3z\u001d\u0001ka3REk}`\u0017\u0006$/w\u0001[ka)R\u0015$-\u007f\u001b\u000b,ac\u0017\u0017\".wR\u0003$33\u0006\r.at\u0017\u0011ka3R>%.}\u00178AK3R*\u001b\u0015Z=+\u0018K\u0019") + b() + "\n" + "\n" + a("RE\u0018\u000f^\"\u0013xa\\\"1\u0002\u000e]!o") + "\n" + c() + "\n" + "\n");
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 114;
               break;
            case 1:
               var10003 = 101;
               break;
            case 2:
               var10003 = 75;
               break;
            case 3:
               var10003 = 65;
               break;
            default:
               var10003 = 19;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
