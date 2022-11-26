package monfox.toolkit.snmp.agent;

public abstract class SnmpAccessControlModel {
   public abstract String getModelName();

   public abstract boolean supportsVersion(int var1);

   public abstract SnmpAccessPolicy getAccessPolicy(SnmpPendingIndication var1);
}
