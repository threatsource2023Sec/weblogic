package monfox.toolkit.snmp.agent;

import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValueException;

public class SnmpRowStatusFactory implements SnmpMibLeafFactory {
   public SnmpMibLeaf getInstance(SnmpMibTable var1, SnmpOid var2, SnmpOid var3) throws SnmpMibException, SnmpValueException {
      return new SnmpRowStatus(var2, var3);
   }
}
