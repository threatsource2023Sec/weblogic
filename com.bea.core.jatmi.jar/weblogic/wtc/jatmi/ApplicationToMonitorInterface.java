package weblogic.wtc.jatmi;

public interface ApplicationToMonitorInterface {
   int TPNOBLOCK = 1;
   int TPSIGRSTRT = 2;
   int TPNOREPLY = 4;
   int TPNOTRAN = 8;
   int TPTRAN = 16;
   int TPNOTIME = 32;
   int TPABSOLUTE = 64;
   int TPGETANY = 128;
   int TPNOCHANGE = 256;
   int TPCONV = 1024;
   int TPSENDONLY = 2048;
   int TPRECVONLY = 4096;
   int TPACK = 8192;
   int TPRMICALL = 16384;
   int TPUNKAUTH = -1;
   int TPNOAUTH = 0;
   int TPSYSAUTH = 1;
   int TPAPPAUTH = 2;

   CallDescriptor tpacall(String var1, TypedBuffer var2, int var3) throws TPException;

   CallDescriptor tpacall(String var1, TypedBuffer var2, int var3, TpacallAsyncReply var4) throws TPException;

   void tpcancel(CallDescriptor var1, int var2) throws TPException;

   Reply tpgetrply(CallDescriptor var1, int var2) throws TPException, TPReplyException;

   Reply tpcall(String var1, TypedBuffer var2, int var3) throws TPException, TPReplyException;

   byte[] tpenqueue(String var1, String var2, EnqueueRequest var3, TypedBuffer var4, int var5) throws TPException;

   DequeueReply tpdequeue(String var1, String var2, byte[] var3, byte[] var4, boolean var5, boolean var6, int var7) throws TPException;

   DequeueReply tpdequeue(String var1, String var2, int var3) throws TPException;

   void tpterm() throws TPException;

   Conversation tpconnect(String var1, TypedBuffer var2, int var3) throws TPException;

   void tpsprio(int var1, int var2) throws TPException;
}
