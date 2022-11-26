package monfox.toolkit.snmp.ext;

public interface SnmpTableListener {
   void handleCreated(SnmpTable var1, SnmpRow var2, int var3);

   void handleDeleted(SnmpTable var1, SnmpRow var2, int var3);

   void handleUpdated(SnmpTable var1, SnmpRow var2, int[] var3, int var4);

   void handleError(SnmpTable var1, SnmpError var2);
}
