package monfox.toolkit.snmp.appl;

import java.util.StringTokenizer;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.mgr.SnmpErrorException;
import monfox.toolkit.snmp.mgr.SnmpSession;
import monfox.toolkit.snmp.mgr.SnmpTimeoutException;

public class SnmpInform extends a {
   private static final SnmpOid a = new SnmpOid(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 1L, 1L, 4L, 1L, 0L});
   private static final SnmpOid b = new SnmpOid(new long[]{1L, 3L, 6L, 1L, 2L, 1L, 1L, 3L, 0L});
   private static final String c = "$Id: SnmpInform.java,v 1.6 2007/03/23 04:12:00 sking Exp $";

   public static void main(String[] var0) {
      try {
         SnmpInform var1 = new SnmpInform(var0);
         var1.g();
      } catch (Throwable var2) {
         h();
         System.out.println("\n");
         System.out.println(a("jZ>!u\u00185L^\u0007") + var2.getMessage());
         System.out.println("\n");
      }

   }

   SnmpInform(String[] var1) throws Throwable {
      super(var1);
   }

   void g() {
      boolean var10 = SnmpTrapLogger.i;
      SnmpVarBindList var1 = null;

      try {
         if (this.d.hasFlag("?") || this.e.length < 2) {
            h();
            System.exit(1);
         }

         this.e();
         SnmpSession var2 = this.f();
         var1 = new SnmpVarBindList();

         try {
            int var3 = Integer.parseInt(this.e[0]);
            var1.addTimeTicks(b, (long)var3);
         } catch (NumberFormatException var12) {
            System.out.println(a("\u000f(L+upZ|\u0005CjFk\u0014S#\u0017{Z\u001dj]") + this.e[0] + "'");
            System.out.println(a("jW3D|") + var12.getMessage() + a("\u0017Z3I"));
            System.exit(1);
         }

         try {
            var1.addOid(a, this.e[1]);
         } catch (Exception var11) {
            System.out.println(a("\u000f(L+upZ|\u0005CjFj\u0016F:Wq\rCt@>C") + this.e[1] + "'");
            System.out.println(a("jW3D|") + var11.getMessage() + a("\u0017Z3I"));
            System.exit(1);
         }

         String[] var19 = this.e;
         int var4 = 2;

         int var10000;
         label71: {
            while(var4 < var19.length) {
               StringTokenizer var5 = new StringTokenizer(var19[var4], a("w@"), false);
               var10000 = var5.countTokens();
               if (var10) {
                  break label71;
               }

               label67: {
                  if (var10000 < 2) {
                     System.out.println(a("\u000f(L+upZ\\\u0005Cj;l\u0003R'\u001fp\u0010\u0007") + var19[var4]);
                     if (!var10) {
                        break label67;
                     }
                  }

                  try {
                     label62: {
                        String var6 = var5.nextToken();
                        String var7 = null;
                        String var8 = var5.nextToken();
                        if (!var5.hasMoreTokens()) {
                           var1.add(var6, var8);
                           if (!var10) {
                              break label62;
                           }
                        }

                        var7 = var8.toUpperCase();
                        var8 = var5.nextToken("\u0001").trim();
                        SnmpValue var9 = SnmpValue.getInstance(var7, var8);
                        var1.add(var6, var9);
                     }
                  } catch (SnmpException var13) {
                     System.out.println(a("\u000f(L+upZ\\\u0005Cj9q\tW%\u0014{\nSpZ") + var19[var4]);
                     System.out.println(a("\u000f(L+upZW\nA%@>") + var13.getMessage());
                  } catch (NumberFormatException var14) {
                     System.out.println(a("\u000f(L+upZ\\\u0005Cj4k\tE/\b>C") + var14.getMessage() + a("mZx\u0016H'Z>C") + var19[var4] + a("mT"));
                  } catch (Exception var15) {
                     System.out.println(a("\u000f(L+upZ\\\u0005Cj,\u007f\u0016e#\u0014z^\u0007") + var19[var4]);
                     System.out.println(a("\u000f(L+upZW\nA%@>") + var15.getMessage());
                  }
               }

               ++var4;
               if (var10) {
                  break;
               }
            }

            var10000 = var1.size();
         }

         if (var10000 <= 0) {
            System.out.println(a("\u000f(L+upZp\u000b\u0007<\u001br\u0011B9Zj\u000b\u0007#\u0014}\bR.\u001f\u0014"));
            System.exit(1);
         }

         var2.performInform(var1);
      } catch (SnmpTimeoutException var16) {
         System.out.println(a("\u000f(L+upZJ\rJ/\u0015k\u0010"));
      } catch (SnmpErrorException var17) {
         this.a(var17, var1);
      } catch (Exception var18) {
         var18.printStackTrace();
         System.out.println(a("\u000f(L+upZ") + var18);
      }

   }

   static void h() {
      System.out.println(a("@Z>1t\u000b=[n-jZ>D\u0007 \u001bh\u0005\u0007\u0019\u0014s\u0014n$\u001cq\u0016Jj!3[[%\nj\rH$\tCD\u001b?\nj\rJ/D>XS8\u001bnIH#\u001e D\u001b%\u0018t\u0001D>3ZZ\u001a\u0011Fj\u001dW/D$9\u001b<\u001br\u0011BtZ0J\tjp\u0014D\u0007\u000e?M'u\u0003*J-h\u0004p\u0014D\u0007jZ>4B8\u001cq\u0016J9Z\u007f\n\u0007\u00194S4\u0007\u0003\u0014x\u000bU'Zq\u0014B8\u001bj\rH$Zq\n\u0007>\u0012{DT:\u001f}\rA#\u001fzDj\u00038\u0014D\u0007jZ>\u0012F8\u0013\u007f\u0006K/\t0n-jZN%u\u000b7[0b\u0018)\u0014n\u0007jZ>D\u001b%\u0018t\u0001D>3ZZ\u0007pZS-ej\f\u007f\u0016N+\u0018r\u0001\u0007\u00053Zn-jZ>D\u0007v\u000eg\u0014BtZ>D\u0007j@>\r|$\u000e{\u0003B8'2\u0017|>\bw\n@\u0017Vq?N.'2n\u0007jZ>D\u0007jZ>D\u0007jZ>D\u0007jZw\u0014|+\u001ez\u0016B9\tCH@\u0011\u001bk\u0003B\u0017V}?H?\u0014j\u0001U\u0017!-Vz@Z>D\u0007jZ>D\u0007jZ>D\u0007jZ>DD\u0011\u0015k\nS/\bCR\u0013fZj?N'\u001fj\rD!\tCH\u0007%\nE\u0005V?\u001fCn-jZ>D\u0007jZ>D\u0007jZ>D\u0007jZ>N\u0007\u0004\u0015j\u0001\u0007>\u0012\u007f\u0010\u0007v\u000eg\u0014BtZx\rB&\u001e>\rTj\u0015n\u0010N%\u0014\u007f\b\tj3xDS\"\u001f\u0014D\u0007jZ>D\u0007jZ>D\u0007jZ>D\u0007jZ>)n\bZ}\u000bI>\u001bw\nN$\u001d>\u0010O/Zq\u0006M/\u0019jDN9Zr\u000bF.\u001fzH\u0007>\u0012{\n\u0007>\u0012{n\u0007jZ>D\u0007jZ>D\u0007jZ>D\u0007jZ>D\u001b>\u0003n\u0001\u0019j\u0013mDI%\u000e>\nB/\u001e{\u0000\t@p>D\u0007jZ\"\u0012F&\u000f{Z\u0007jZ>^\u0007<\u001br\u0011Bj\u0015xDS\"\u001f>)n\bZh\u0005U#\u001b|\bBj\u000eqDN$\u0019r\u0011C/p\u0014D\u0007\u0005*J-h\u0004)\u0014n") + b() + "\n" + "\n" + a("jZM*j\u001a\f-Dh\u001a.W+i\u0019p") + "\n" + c() + "\n" + "\n");
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 74;
               break;
            case 1:
               var10003 = 122;
               break;
            case 2:
               var10003 = 30;
               break;
            case 3:
               var10003 = 100;
               break;
            default:
               var10003 = 39;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
