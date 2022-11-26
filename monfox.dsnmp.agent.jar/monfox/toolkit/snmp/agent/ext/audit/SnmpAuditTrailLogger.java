package monfox.toolkit.snmp.agent.ext.audit;

import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.v3.usm.ext.UsmSecurityAuditTrailLogger;

public interface SnmpAuditTrailLogger extends UsmSecurityAuditTrailLogger {
   int VERSION_1 = 0;
   int VERSION_2 = 1;
   int VERSION_3 = 3;
   int SNMP_GET = 160;
   int SNMP_GET_NEXT = 161;
   int SNMP_GET_BULK = 165;
   int SNMP_SET = 163;

   void logObjectAccess(String var1, int var2, int var3, String var4, SnmpOid var5, String var6);

   void logErrorResponse(String var1, int var2, int var3, SnmpVarBindList var4, int var5, int var6);

   void logErrorReport(String var1, SnmpVarBindList var2);
}
