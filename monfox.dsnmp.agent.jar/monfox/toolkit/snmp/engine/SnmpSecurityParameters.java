package monfox.toolkit.snmp.engine;

public interface SnmpSecurityParameters {
   int getSecurityModel();

   int getSecurityLevel();

   byte[] getSecurityName();
}
