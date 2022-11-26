package monfox.toolkit.snmp.metadata.gen;

public interface SnmpMibGenListener {
   void handleInfo(InfoMessage var1, Object var2);

   void handleError(ErrorMessage var1, Object var2);
}
