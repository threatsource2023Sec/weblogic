package monfox.toolkit.snmp.appl;

import monfox.toolkit.snmp.NoSuchObjectException;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.ext.SnmpError;
import monfox.toolkit.snmp.ext.SnmpPollGroup;
import monfox.toolkit.snmp.ext.SnmpPollingEngine;
import monfox.toolkit.snmp.ext.SnmpRow;
import monfox.toolkit.snmp.ext.SnmpTable;
import monfox.toolkit.snmp.ext.SnmpTableListener;
import monfox.toolkit.snmp.mgr.SnmpErrorException;
import monfox.toolkit.snmp.mgr.SnmpSession;

public class SnmpTableMonitor extends a implements SnmpTableListener {
   private static final String[] a = new String[0];
   private static final String[] c = new String[]{a("@\u0019\u0004k3T"), a("r\u0011"), a("S\u0014\u0013a7"), a("r\u0011\u0017zqB\u0019\u0006g(Y\b\u001fm2C")};
   private SnmpPollingEngine f = null;
   private static final String g = "$Id: SnmpTableMonitor.java,v 1.24 2008/06/25 00:57:02 sking Exp $";

   public static void main(String[] var0) {
      try {
         SnmpTableMonitor var1 = new SnmpTableMonitor(var0);
         var1.g();

         while(true) {
            Thread.sleep(10000L);
         }
      } catch (Throwable var2) {
         h();
         System.out.println("\n");
         System.out.println(a("\u0010\\VG\u000eb3$8|") + var2.getMessage());
         System.out.println("\n");
      }
   }

   SnmpTableMonitor(String[] var1) throws Throwable {
      super(var1, "", a, "", c);
   }

   void g() {
      boolean var8 = SnmpTrapLogger.i;

      try {
         if (this.d.hasFlag("?") || this.e.length == 0) {
            h();
            System.exit(1);
         }

         int var1 = this.d.getIntOption(a("@\u0019\u0004k3T"), 5);
         this.e();
         int var2 = this.d.getIntOption(a("r\u0011L@1Q\u0004\u0004g,U\b\u001fv5_\u0012\u0005"), -1);
         SnmpSession var3 = this.f();
         this.f = new SnmpPollingEngine(var3);
         String[] var4 = this.e;
         if (var2 > 0 && this.h == 0) {
            System.out.println(a("u.$M\u000e\n\\%l1@\\ 3|^\u0013\u0002\"*Q\u0010\u001ff|V\u0013\u0004\">E\u0010\u001d\"+Q\u0010\u001d"));
            System.out.println("");
            System.out.println(a("\u0010\\V\"|\u0010\\;w/D\\\u0003q9\u0010Q\u00000\u0007S!Vm.\u0010Q\u00001|V\u0013\u0004\">E\u0010\u001d\"+Q\u0010\u001d"));
            System.out.println("");
            System.exit(1);
         }

         SnmpPollGroup var5 = new SnmpPollGroup(a("d\u001d\u0014n9C"));
         int var6 = 0;

         SnmpTableMonitor var10000;
         label39: {
            while(var6 < var4.length) {
               SnmpTable var7 = new SnmpTable(this.b, var4[var6]);
               var10000 = this;
               if (var8) {
                  break label39;
               }

               if (this.d.hasFlag(a("S\u0014\u0013a7"))) {
                  var7.isCheckAlignment(true);
               }

               var7.setMaxRepetitions(var2);
               var7.performUpdate(var3);
               System.out.println(a("k09C\u0018u8VV\u001dr038|") + var4[var6] + "]");
               System.out.println(var7);
               var7.addTableListener(this);
               var5.add(var7);
               ++var6;
               if (var8) {
                  break;
               }
            }

            var10000 = this;
         }

         var10000.f.add(var5, var1);
      } catch (SnmpErrorException var9) {
         this.a(var9);
      } catch (Exception var10) {
         System.out.println(a("u.$M\u000e\n\\") + var10);
         System.exit(1);
      }

   }

   public void handleCreated(SnmpTable var1, SnmpRow var2, int var3) {
      System.out.println(a("b3!/\u001fb97V\u0019t'") + var1.getName() + a("mF|") + var2);
   }

   public void handleDeleted(SnmpTable var1, SnmpRow var2, int var3) {
      System.out.println(a("b3!/\u0018u03V\u0019t'") + var1.getName() + a("mF|") + var2);
   }

   public void handleUpdated(SnmpTable var1, SnmpRow var2, int[] var3, int var4) {
      if (var3 != null) {
         System.out.println(a("b3!/\u001fx=8E\u0019t'") + var1.getName() + a("mF|"));
         System.out.print(a("K\\"));
         int var5 = 0;

         while(var3 != null && var5 < var3.length) {
            try {
               SnmpVarBind var6 = var2.getVarBind(var3[var5]);
               System.out.print(var6.getOid() + " ");
            } catch (NoSuchObjectException var7) {
               System.out.println(var7);
            }

            ++var5;
            if (SnmpTrapLogger.i) {
               break;
            }
         }

         System.out.println("}");
         System.out.println(var2);
      }
   }

   public void handleError(SnmpTable var1, SnmpError var2) {
      System.out.println(a("u.$M\u000ek") + var1.getName() + a("mFV*") + var2 + ")");
   }

   static void h() {
      System.out.println(a(":\\VW\u000fq;3\bV\u0010\\V\"|Z\u001d\u0000c|c\u0012\u001br\bQ\u001e\u001ag\u0011_\u0012\u001fv3B\\-/cL\u0013\u0006v5_\u0012\u0005_|\f\b\u0017`0U3\u0014h9S\b?Fb\u001bv|\"|t9%A\u000ey,\"K\u0013~v|\"|\u0010\\VO3^\u0015\u0002m.\u0010\u001dVO\u0015r\\\u0002c>\\\u0019Va3^\b\u0013l(CS\u0015m0E\u0011\u0018q|V\u0013\u0004\"*Q\u0010\u0003g|S\u0014\u0017l;U\u000fVc2T\\\u0012k/@\u0010\u0017{|:\\V\"|\u0010\b\u001eg|S\u0014\u0017l;U\u000fVc/\u0010\b\u001eg%\u0010\u001d\u0004g|T\u0015\u0005a3F\u0019\u0004g8\u001e\\\"j5C\\\u0017r,\\\u0015\u0015c(Y\u0013\u0018\")C\u0019\u0005\",U\u000e\u001fm8Y\u001f|\"|\u0010\\Vr3\\\u0010\u001fl;\u0010\u0015\u0018\"3B\u0018\u0013p|D\u0013Vw,T\u001d\u0002g|D\u0014\u0013\"*Q\u0010\u0003g/\u0010\u001d\u0018f|\\\u0013\u0019i|V\u0013\u0004\"?X\u001d\u0018e9CR|\"|\u0010\\VV4U\\Jv=R\u0010\u0013M>Z\u0019\u0015v\u0015tBVq4_\t\u001af|Y\u0018\u0013l(Y\u001a\u000f\"=\u0010\b\u0017`0U\\9@\u0016u?\",|:vV\"|\u0010\\\"j9\u0010\b\u0019m0\u0010\u000b\u001fn0\u0010\t\u0005g|w\u0019\u0002/\u0012U\u0004\u0002\".U\r\u0003g/D\u000fV`%\u0010\u0018\u0013d=E\u0010\u0002\"5^\\\u0019p8U\u000eVv3\u0010\u000e\u0013v.Y\u0019\u0000gV\u0010\\V\"|D\u001d\u0014n9\u0010\u0018\u0017v=\u0010\u0014\u0019u9F\u0019\u0004\"5V\\\u0017\"{]\u001d\u000e/.U\f\u0013v5D\u0015\u0019l/\u0010T[@1\u0019[Vt=\\\t\u0013\"5C\\\u0006p3F\u0015\u0012g8:\\V\"|\u0010\b\u001eg2\u0010\b\u001eg|D\u0013\u0019n|G\u0015\u001an|E\u000f\u0013\"=\u0010\u000f\u0013p5U\u000fVm:\u0010\u001b\u0013vqR\t\u001ai|_\f\u0013p=D\u0015\u0019l/\u0010\u0015\u0018\"3B\u0018\u0013p|D\u0013|\"|\u0010\\Vg$D\u000e\u0017a(\u0010\b\u001eg|D\u001d\u0014n9\u0010\u0018\u0017v=\u001ev|\"|}38K\b\u007f.VM\fd59L\u000f:vV\"|\u0010\\[@1k\u001d\u000e/.U\f\u0013v5D\u0015\u0019l/m\\J!b\u0010FV`)\\\u0017Vo=HQ\u0004g,U\b\u001fv5_\u0012\u0005\"|\u0010\\V\"|\u0010\\V\"|\u0010\\-/mmvV\"|\u0010\\[r9B\u0015\u0019f|\f\u000f\u0013a3^\u0018\u0005<|\u0010\\V\"|\u0010FVv4U\\\u0006m0\\\u0015\u0018e|@\u0019\u0004k3T\\V\"|\u0010\\V\"|\u0010\\V\"|\u0010\\-7\u0001:vV\"\u0013`(?M\u0012cv|") + b() + "\n" + "\n" + a("\u0010\\%L\u0011`\nE\"\u0013`(?M\u0012cv") + "\n" + c() + "\n" + "\n");
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 48;
               break;
            case 1:
               var10003 = 124;
               break;
            case 2:
               var10003 = 118;
               break;
            case 3:
               var10003 = 2;
               break;
            default:
               var10003 = 92;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
