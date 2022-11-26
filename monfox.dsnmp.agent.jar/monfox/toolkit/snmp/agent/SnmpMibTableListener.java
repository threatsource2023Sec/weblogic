package monfox.toolkit.snmp.agent;

public interface SnmpMibTableListener {
   void rowInit(SnmpMibTableRow var1);

   void rowAdded(SnmpMibTableRow var1);

   void rowActivated(SnmpMibTableRow var1);

   void rowDeactivated(SnmpMibTableRow var1);

   void rowRemoved(SnmpMibTableRow var1);
}
