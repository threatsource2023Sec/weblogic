package monfox.toolkit.snmp.v3.usm.ext;

public interface UsmSecurityAuditTrailLogger {
   int FAILURE = 0;
   int SUCCESS = 1;
   int DECRYPT = 1;
   int ENCRYPT = 2;
   int INCOMING = 1;
   int OUTGOING = 2;
   int DES = 2;
   int AES128 = 4;
   int AES192 = 5;
   int AES256 = 6;

   void logPrivOperation(String var1, int var2, int var3, int var4, int var5);

   void logAuthOperation(String var1, int var2, int var3, int var4, int var5);
}
