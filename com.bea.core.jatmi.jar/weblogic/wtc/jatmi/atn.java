package weblogic.wtc.jatmi;

public interface atn {
   int GSS_NAME_OBJID_SIZE = 12;
   int ATNTDOM_TOKEN_TYPE_1 = 1;
   int ATNTDOM_TOKEN_TYPE_2 = 2;
   int ATNTDOM_TOKEN_TYPE_3 = 3;
   int ATNTDOM_TOKEN_TYPE_4 = 4;
   int ATNTDOM_VERSION = 1;
   int MAXNAME_LEN = 512;
   int MAXCHAN_LEN = 50;
   int MAXDOMAIN_LEN = 30;
   int GSS_C_AF_INET = 2;

   atncred gssAcquireCred(String var1) throws EngineSecError;

   atncred gssAcquireCred(String var1, String var2) throws EngineSecError;

   atncred gssAcquireCred(String var1, byte[] var2) throws EngineSecError;

   atncontext gssGetContext(atncred var1, String var2) throws EngineSecError;

   int gssInitSecContext(atncontext var1, byte[] var2, int var3, byte[] var4) throws EngineSecError;

   int gssAcceptSecContext(atncontext var1, byte[] var2, int var3, byte[] var4) throws EngineSecError;

   int getActualPDUSendSize();

   int getEstimatedPDUSendSize(atncontext var1);

   int getEstimatedPDURecvSize(atncontext var1);

   int setSecurityType(int var1);

   void setSrcName(String var1);

   void setDesiredName(String var1);

   void setTargetName(String var1);

   void setApplicationPasswd(String var1);

   void setLocalPasswd(String var1);

   void setRemotePasswd(String var1);

   int setInitiatorAddr(byte[] var1);

   int setAcceptorAddr(byte[] var1);

   int setApplicationData(byte[] var1);

   void setMachineType(String var1);
}
