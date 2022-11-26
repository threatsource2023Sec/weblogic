package monfox.toolkit.snmp.ext;

public interface SnmpObjectSetListener {
   void handleUpdated(SnmpObjectSet var1, int[] var2);

   void handleError(SnmpObjectSet var1, SnmpError var2);
}
