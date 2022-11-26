package monfox.toolkit.snmp.agent;

import monfox.toolkit.snmp.SnmpVarBindList;

public abstract class SnmpSecurityModel {
   public abstract String getModelName();

   public abstract SnmpVarBindList getReportVarBindList(int var1);
}
