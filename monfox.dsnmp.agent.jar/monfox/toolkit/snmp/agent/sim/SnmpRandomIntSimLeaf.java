package monfox.toolkit.snmp.agent.sim;

import java.util.StringTokenizer;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibLeaf;
import monfox.toolkit.snmp.agent.SnmpMibLeafFactory;
import monfox.toolkit.snmp.agent.SnmpMibTable;

public class SnmpRandomIntSimLeaf extends SnmpSimLeaf {
   private Logger a = null;
   private long b = 0L;
   private long c = 2147483647L;

   public SnmpRandomIntSimLeaf(SnmpOid var1) throws SnmpMibException, SnmpValueException {
      super(var1);
      this.a = Logger.getInstance(a("Q\u0006PIQc\u0006YVnK\u0006Ijjo$XXe"));
   }

   public SnmpRandomIntSimLeaf(SnmpOid var1, SnmpOid var2) throws SnmpMibException, SnmpValueException {
      super(var1, var2);
      this.a = Logger.getInstance(a("Q\u0006PIQc\u0006YVnK\u0006Ijjo$XXe"));
   }

   public void initializeFunction(String var1) throws SnmpValueException {
      boolean var5 = SnmpSimLeaf.c;
      StringTokenizer var2 = new StringTokenizer(var1, a("*A\u0007\u0015#"), false);
      String[] var3 = new String[]{a("p\tS]lo"), "0", a("3X\r\t")};
      int var4 = 0;

      int var10000;
      while(true) {
         if (var4 < var3.length) {
            var10000 = var2.hasMoreTokens();
            if (var5) {
               break;
            }

            if (var10000 != 0) {
               var3[var4] = var2.nextToken().toLowerCase();
            }

            ++var4;
            if (!var5) {
               continue;
            }
         }

         try {
            this.b = Long.parseLong(var3[1]);
         } catch (NumberFormatException var7) {
            throw new SnmpValueException(a("k\u0006KXok\f\u001dTjlHKXo*") + var3[1] + ")");
         }

         try {
            this.c = Long.parseLong(var3[2]);
         } catch (NumberFormatException var6) {
            throw new SnmpValueException(a("k\u0006KXok\f\u001dTbzHKXo*") + var3[2] + ")");
         }

         var10000 = this.getType();
         break;
      }

      SnmpValue.getInstance(var10000, this.c);
   }

   public SnmpValue getValue() {
      long var1 = (long)(Math.random() * (double)(this.c - this.b)) + this.b;

      try {
         return SnmpValue.getInstance(this.getType(), var1);
      } catch (Exception var4) {
         return null;
      }
   }

   public static SnmpMibLeafFactory getFactory(String var0) {
      return new Factory(var0);
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
               var10003 = 104;
               break;
            case 2:
               var10003 = 61;
               break;
            case 3:
               var10003 = 57;
               break;
            default:
               var10003 = 3;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   private static class Factory implements SnmpMibLeafFactory {
      private String a = null;

      public Factory(String var1) {
         this.a = var1;
      }

      public SnmpMibLeaf getInstance(SnmpMibTable var1, SnmpOid var2, SnmpOid var3) throws SnmpValueException, SnmpMibException {
         SnmpRandomIntSimLeaf var4 = new SnmpRandomIntSimLeaf(var2, var3);
         if (this.a != null) {
            var4.initializeFunction(this.a);
         }

         return var4;
      }
   }
}
