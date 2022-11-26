package monfox.toolkit.snmp.agent.sim;

import java.util.StringTokenizer;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibLeaf;
import monfox.toolkit.snmp.agent.SnmpMibLeafFactory;
import monfox.toolkit.snmp.agent.SnmpMibTable;

public class SnmpTimerSimLeaf extends SnmpSimLeaf {
   private Object a = new Object();
   private Logger b = null;
   private long c = 0L;
   private long d = 0L;
   private long e = 0L;

   public SnmpTimerSimLeaf(SnmpOid var1) throws SnmpMibException, SnmpValueException {
      super(var1);
      this.b = Logger.getInstance(a("\u000e[+r<4X#p;4X\ng\t;"));
   }

   public SnmpTimerSimLeaf(SnmpOid var1, SnmpOid var2) throws SnmpMibException, SnmpValueException {
      super(var1, var2);
      this.b = Logger.getInstance(a("\u000e[+r<4X#p;4X\ng\t;"));
   }

   public void initializeFunction(String var1) throws SnmpValueException {
      boolean var5 = SnmpSimLeaf.c;
      StringTokenizer var2 = new StringTokenizer(var1, a("u\u001c|.H"), false);
      String[] var3 = new String[]{a(")\\+g\u001a"), "0", a("o\u0004r5\\e\u0006p6_")};
      int var4 = 0;

      int var10000;
      while(true) {
         if (var4 < var3.length) {
            var10000 = var2.hasMoreTokens();
            if (var5) {
               break;
            }

            if (var10000 != 0) {
               var3[var4] = var2.nextToken().toLowerCase().trim();
            }

            ++var4;
            if (!var5) {
               continue;
            }
         }

         this.c = monfox.toolkit.snmp.agent.sim.a.getLongFromString(var3[1], a(".A'p\u001c"));
         this.e = monfox.toolkit.snmp.agent.sim.a.getLongFromString(var3[2], a("0T>"));
         this.d = System.currentTimeMillis();
         var10000 = this.getType();
         break;
      }

      SnmpValue.getInstance(var10000, 0L);
      if (SnmpException.b) {
         SnmpSimLeaf.c = !var5;
      }

   }

   public SnmpValue getValue() {
      long var1 = (System.currentTimeMillis() - this.d) / 10L + this.c;
      var1 %= this.e;

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
               var10003 = 93;
               break;
            case 1:
               var10003 = 53;
               break;
            case 2:
               var10003 = 70;
               break;
            case 3:
               var10003 = 2;
               break;
            default:
               var10003 = 104;
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
         SnmpTimerSimLeaf var4 = new SnmpTimerSimLeaf(var2, var3);
         if (this.a != null) {
            var4.initializeFunction(this.a);
         }

         return var4;
      }
   }
}
