package monfox.toolkit.snmp.agent.tc;

import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpInt;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibLeaf;
import monfox.toolkit.snmp.agent.SnmpMibLeafFactory;
import monfox.toolkit.snmp.agent.SnmpMibTable;
import monfox.toolkit.snmp.agent.SnmpPendingIndication;

public class TestAndIncr extends SnmpMibLeaf {
   public static final long MAX = 2147483647L;
   public static final double MAX_DOUBLE = 2.147483647E9;

   public TestAndIncr(SnmpOid var1) throws SnmpMibException, SnmpValueException {
      super(var1);
      long var2 = (long)(Math.random() * 2.147483647E9);
      this.setValue(var2);
   }

   public TestAndIncr(SnmpOid var1, long var2) throws SnmpMibException, SnmpValueException {
      super(var1);
      this.setValue(var2);
   }

   public TestAndIncr(SnmpOid var1, SnmpOid var2) throws SnmpMibException, SnmpValueException {
      boolean var5 = StorageType.b;
      super(var1, var2);
      long var3 = (long)(Math.random() * 2.147483647E9);
      this.setValue(var3);
      if (var5) {
         SnmpException.b = !SnmpException.b;
      }

   }

   public TestAndIncr(SnmpOid var1, SnmpOid var2, long var3) throws SnmpMibException, SnmpValueException {
      super(var1, var2);
      this.setValue(var3);
   }

   public long longValue() {
      return this.hasValue() ? this.getValue().longValue() : 0L;
   }

   public int valueOk(SnmpValue var1) {
      long var2 = var1.longValue();
      return var2 != this.longValue() ? 12 : 0;
   }

   public void setValue(long var1) throws SnmpValueException {
      this.setValue(new SnmpInt(var1));
   }

   public boolean commitSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      try {
         long var4 = this.longValue();
         ++var4;
         if (var4 > 2147483647L) {
            var4 = 0L;
         }

         this.setValue(var4);
         return true;
      } catch (SnmpValueException var6) {
         return false;
      }
   }

   public static SnmpMibLeafFactory getFactory() {
      return new Factory();
   }

   public static SnmpMibLeafFactory getFactory(long var0) {
      return new Factory(var0);
   }

   private static class Factory implements SnmpMibLeafFactory {
      private Long a;

      public Factory() {
         this.a = null;
      }

      public Factory(long var1) {
         boolean var3 = StorageType.b;
         super();
         this.a = null;
         this.a = new Long(var1);
         if (SnmpException.b) {
            StorageType.b = !var3;
         }

      }

      public SnmpMibLeaf getInstance(SnmpMibTable var1, SnmpOid var2, SnmpOid var3) throws SnmpValueException, SnmpMibException {
         return this.a == null ? new TestAndIncr(var2, var3) : new TestAndIncr(var2, var3, this.a);
      }
   }
}
