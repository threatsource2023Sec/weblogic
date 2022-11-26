package monfox.toolkit.snmp.agent.tc;

import monfox.toolkit.snmp.SnmpInt;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibLeaf;
import monfox.toolkit.snmp.agent.SnmpMibLeafFactory;
import monfox.toolkit.snmp.agent.SnmpMibTable;

public class StorageType extends SnmpMibLeaf {
   public static final int OTHER = 1;
   public static final int VOLATILE = 2;
   public static final int NON_VOLATILE = 3;
   public static final int PERMANENT = 4;
   public static final int READ_ONLY = 5;
   public static boolean b;

   public StorageType(SnmpOid var1, SnmpOid var2) throws SnmpMibException, SnmpValueException {
      super(var1, var2);
   }

   public StorageType(SnmpOid var1, SnmpOid var2, int var3) throws SnmpMibException, SnmpValueException {
      super(var1, var2);
      this.setValue(var3);
   }

   public int intValue() {
      return this.hasValue() ? this.getValue().intValue() : 0;
   }

   public int valueOk(SnmpValue var1) {
      int var2 = var1.intValue();
      if (var2 >= 1 && 5 >= var2) {
         if (!this.hasValue()) {
            return 0;
         } else {
            int var3 = this.intValue();
            if (var3 == 2 && var2 != 3) {
               return 12;
            } else if (var3 == 3 && var2 != 2) {
               return 12;
            } else {
               return var3 != 5 && var3 != 4 ? 0 : 12;
            }
         }
      } else {
         return 10;
      }
   }

   public void setValue(int var1) throws SnmpValueException {
      this.setValue(new SnmpInt(var1));
   }

   public static SnmpMibLeafFactory getFactory() {
      return new Factory(0);
   }

   public static SnmpMibLeafFactory getFactory(int var0) {
      return new Factory(var0);
   }

   private static class Factory implements SnmpMibLeafFactory {
      private int a = 0;

      public Factory(int var1) {
         this.a = var1;
      }

      public SnmpMibLeaf getInstance(SnmpMibTable var1, SnmpOid var2, SnmpOid var3) throws SnmpValueException, SnmpMibException {
         StorageType var4 = new StorageType(var2, var3);
         if (this.a != 0) {
            var4.setValue(this.a);
         }

         return var4;
      }
   }
}
