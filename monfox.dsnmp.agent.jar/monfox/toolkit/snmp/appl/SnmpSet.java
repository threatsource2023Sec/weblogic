package monfox.toolkit.snmp.appl;

import java.util.StringTokenizer;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.mgr.SnmpErrorException;
import monfox.toolkit.snmp.mgr.SnmpSession;
import monfox.toolkit.snmp.mgr.SnmpTimeoutException;

public class SnmpSet extends a {
   private static final String a = "$Id: SnmpSet.java,v 1.26 2007/03/23 04:12:00 sking Exp $";

   public static void main(String[] var0) {
      try {
         SnmpSet var1 = new SnmpSet(var0);
         var1.g();
      } catch (Throwable var2) {
         h();
         System.out.println("\n");
         System.out.println(a("\u001cN=\u0016\u000bn!Oiy") + var2.getMessage());
         System.out.println("\n");
      }

   }

   SnmpSet(String[] var1) throws Throwable {
      super(var1);
   }

   void g() {
      boolean var10 = SnmpTrapLogger.i;
      SnmpVarBindList var1 = null;

      try {
         if (this.d.hasFlag("?") || this.e.length == 0) {
            h();
            System.exit(1);
         }

         this.e();
         SnmpSession var2 = this.f();
         var1 = new SnmpVarBindList();
         String[] var3 = this.e;
         int var4 = 0;

         int var10000;
         label57: {
            while(var4 < var3.length) {
               StringTokenizer var5 = new StringTokenizer(var3[var4], a("\u0001T"), false);
               var10000 = var5.countTokens();
               if (var10) {
                  break label57;
               }

               label53: {
                  if (var10000 < 2) {
                     System.out.println(a("y<O\u001c\u000b\u0006N_2=\u001c/o4,Q\u000bs'y") + var3[var4]);
                     if (!var10) {
                        break label53;
                     }
                  }

                  try {
                     label48: {
                        String var6 = var5.nextToken();
                        String var7 = null;
                        String var8 = var5.nextToken();
                        if (!var5.hasMoreTokens()) {
                           var1.add(var6, var8);
                           if (!var10) {
                              break label48;
                           }
                        }

                        var7 = var8.toUpperCase();
                        var8 = var5.nextToken("\u0001").trim();
                        SnmpValue var9 = SnmpValue.getInstance(var7, var8);
                        var1.add(var6, var9);
                     }
                  } catch (SnmpException var11) {
                     System.out.println(a("y<O\u001c\u000b\u0006N_2=\u001c-r>)S\u0000x=-\u0006N") + var3[var4]);
                     System.out.println(a("y<O\u001c\u000b\u0006NT=?ST=") + var11.getMessage());
                  } catch (NumberFormatException var12) {
                     System.out.println(a("y<O\u001c\u000b\u0006N_2=\u001c h>;Y\u001c=t") + var12.getMessage() + a("\u001bN{!6QN=t") + var3[var4] + a("\u001b@"));
                  } catch (Exception var13) {
                     System.out.println(a("y<O\u001c\u000b\u0006N_2=\u001c8|!\u001bU\u0000yiy") + var3[var4]);
                     System.out.println(a("y<O\u001c\u000b\u0006NT=?ST=") + var13.getMessage());
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
            System.out.println(a("y<O\u001c\u000b\u0006Ns<yJ\u000fq&<ONi<yO\u000biY"));
            System.exit(1);
         }

         SnmpVarBindList var17 = var2.performSet(var1);
         this.a(var17);
      } catch (SnmpTimeoutException var14) {
         System.out.println(a("y<O\u001c\u000b\u0006NI:4Y\u0001h'"));
      } catch (SnmpErrorException var15) {
         this.a(var15, var1);
      } catch (Exception var16) {
         System.out.println(a("y<O\u001c\u000b\u0006N") + var16);
      }

   }

   static void h() {
      System.out.println(a("6N=\u0006\n})XYS\u001cN=syV\u000fk2yo\u0000p#\nY\u001a=\bt\u0003\u0012r#-U\u0001s \u0004\u001cRr13Y\ri\u001a\u001d\u0002SFo-E\u001exmcaRk25I\u000b#sw\u0012@=YS\u001cNY\u0016\n\u007f<T\u0003\ru!SYS\u001cN=syl\u000bo56N\u0003ns8\u001c\f| <\u001c=s>)o\u000bis6L\u000bo2-U\u0001ss6RNi;<\u001c\u001dm6:U\bt6=\u001c#T\u0011S\u001cN=syJ\u000fo:8^\u0002x w6d=s\t}<\\\u001e\u001ch+O\u0000S6N=sy\u001cRr13Y\ri\u001a\u001d\u0002N's\u0014u,=%8N\u0007|15YNR\u001a\u001d6d=sy\u001cN!' L\u000b#sy\u001cN=iyU5s'<[\u000bo\u000euO5i!0R\t@\u007f6g\u0007y\u000eu6N=sy\u001cN=sy\u001cN=sy\u001cN=s0L5|7=N\u000bn \u0004\u0010\tF2,[\u000b@\u007f:g\u0001h=-Y\u001c@\bj\u000e3\u0017sy\u001cN=sy\u001cN=sy\u001cN=sy\u001c\rF<,R\u001ax!\u0004\nZ1s-g\u0007p6-U\rv \u0004\u0010Nr#\u0002]\u001fh6\u00046d=sy\u001cN=sy\u001cN=sy\u001cN=sy\u0016NS<-YNi;8HN!' L\u000b#s?U\u000bq7yU\u001d=<)H\u0007r=8P@=\u001a?\u001c\u001au6S\u001cN=sy\u001cN=sy\u001cN=sy\u001cN=syq'_s:S\u0000i20R\u0007s4yH\u0006xs6^\u0004x0-\u001c\u0007ns5S\u000fy6=\u0010Ni;<RNi;<6N=sy\u001cN=sy\u001cN=sy\u001cN=sy\u001cRi*)YP=:*\u001c\u0000r'yR\u000bx7<X@\u0017Yy\u001cN=seJ\u000fq&<\u0002N=sy\u0006Nk25I\u000b=<?\u001c\u001au6yq'_s/]\u001ct2;P\u000b='6\u001c\u001dx'S6N=\u001c\th'R\u001d\n6d") + b() + "\n" + "\n" + a("\u001cNN\u001d\u0014l\u0018.s\u0016l:T\u001c\u0017od") + "\n" + c() + "\n" + "\n");
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 60;
               break;
            case 1:
               var10003 = 110;
               break;
            case 2:
               var10003 = 29;
               break;
            case 3:
               var10003 = 83;
               break;
            default:
               var10003 = 89;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
