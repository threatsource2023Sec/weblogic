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

public class SnmpCounterSimLeaf extends SnmpSimLeaf {
   private Object a = new Object();
   private Logger b = null;
   private long c = 0L;
   private long d = 2147483647L;
   private long e = 0L;
   private long f = 1L;
   private long g = 1000L;
   private long h = 0L;

   public SnmpCounterSimLeaf(SnmpOid var1) throws SnmpMibException, SnmpValueException {
      super(var1);
      this.b = Logger.getInstance(a("\u0006oVN5:tUJ\u0013'RRS:0`]"));
   }

   public SnmpCounterSimLeaf(SnmpOid var1, SnmpOid var2) throws SnmpMibException, SnmpValueException {
      super(var1, var2);
      this.b = Logger.getInstance(a("\u0006oVN5:tUJ\u0013'RRS:0`]"));
   }

   public void initializeFunction(String var1) throws SnmpValueException {
      boolean var5 = SnmpSimLeaf.c;
      StringTokenizer var2 = new StringTokenizer(var1, a("}(\u0001\u0012V"), false);
      String[] var3 = new String[]{a("6nNP\u00020s"), "0", a("g0\u000f\tBm2\r\nA"), "0", a("d1"), a("dr^]")};
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

            SnmpException.b = !SnmpException.b;
         }

         this.c = monfox.toolkit.snmp.agent.sim.a.getLongFromString(var3[1], a("&uZL\u0002"));
         this.d = monfox.toolkit.snmp.agent.sim.a.getLongFromString(var3[2], a("8`C"));
         this.e = monfox.toolkit.snmp.agent.sim.a.getLongFromString(var3[3], a("8hU\u0013\u0005!dK"));
         this.f = monfox.toolkit.snmp.agent.sim.a.getLongFromString(var3[4], a("8`C\u0013\u0005!dK"));
         long var6;
         var10000 = (var6 = this.d - 0L) == 0L ? 0 : (var6 < 0L ? -1 : 1);
         break;
      }

      if (var10000 <= 0) {
         throw new SnmpValueException(a("<oM_\u001a<e\u001bS\u0017-!M_\u001a}") + this.d + ")");
      } else if (this.f < this.e) {
         throw new SnmpValueException(a("<oM_\u001a<e\u001bS\u0017-,HJ\u0013%!M_\u001a}") + this.f + ")");
      } else {
         this.g = monfox.toolkit.snmp.agent.sim.a.getMillisFromString(var3[5], a("<oO[\u0004#`W"));
         this.h = System.currentTimeMillis();
         SnmpValue.getInstance(this.getType(), this.d);
      }
   }

   public SnmpValue getValue() {
      // $FF: Couldn't be decompiled
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
               var10003 = 85;
               break;
            case 1:
               var10003 = 1;
               break;
            case 2:
               var10003 = 59;
               break;
            case 3:
               var10003 = 62;
               break;
            default:
               var10003 = 118;
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
         SnmpCounterSimLeaf var4 = new SnmpCounterSimLeaf(var2, var3);
         if (this.a != null) {
            var4.initializeFunction(this.a);
         }

         return var4;
      }
   }
}
