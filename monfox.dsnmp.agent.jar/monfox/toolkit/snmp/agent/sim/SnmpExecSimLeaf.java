package monfox.toolkit.snmp.agent.sim;

import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibLeaf;
import monfox.toolkit.snmp.agent.SnmpMibLeafFactory;
import monfox.toolkit.snmp.agent.SnmpMibTable;

public class SnmpExecSimLeaf extends SnmpSimLeaf {
   private Object a = new Object();
   private Logger b = null;
   private String c = "";

   public SnmpExecSimLeaf(SnmpOid var1) throws SnmpMibException, SnmpValueException {
      super(var1);
      this.b = Logger.getInstance(a("A\u0012 )@j\u0019.\nl\u007f0(8c"));
   }

   public SnmpExecSimLeaf(SnmpOid var1, SnmpOid var2) throws SnmpMibException, SnmpValueException {
      super(var1, var2);
      this.b = Logger.getInstance(a("A\u0012 )@j\u0019.\nl\u007f0(8c"));
   }

   public void initializeFunction(String var1) throws SnmpValueException {
      int var2 = var1.indexOf(40);
      int var3 = var1.lastIndexOf(41);
      if (var2 >= 0 && var3 >= 0) {
         String var4 = var1.substring(var2 + 1, var3);
         this.c = var4;
         this.b.debug(a("?Qm:j\u007f\u0011,7a(\\") + this.c);
      } else {
         throw new SnmpValueException(a("p\u001d)ycg\u001290j|\\+6w\u007f\u001d9y\"") + var1 + a("5Rmqvz\u001385a2\u001e(y\"w\u0004(:-b\u000e\">ws\u0011d~"));
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
               var10003 = 18;
               break;
            case 1:
               var10003 = 124;
               break;
            case 2:
               var10003 = 77;
               break;
            case 3:
               var10003 = 89;
               break;
            default:
               var10003 = 5;
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
         SnmpExecSimLeaf var4 = new SnmpExecSimLeaf(var2, var3);
         if (this.a != null) {
            var4.initializeFunction(this.a);
         }

         return var4;
      }
   }
}
