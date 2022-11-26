package weblogic.wtc.jatmi;

import java.io.Serializable;
import javax.transaction.xa.Xid;
import weblogic.wtc.gwt.TuxedoConnection;

public interface gwatmi extends ApplicationToMonitorInterface {
   void send_success_return(Serializable var1, tfmh var2, int var3, int var4, int var5) throws TPException;

   void send_transaction_reply(tfmh var1) throws TPException;

   void send_failure_return(Serializable var1, TPException var2, int var3);

   CallDescriptor tprplycall(TuxRply var1, String var2, TypedBuffer var3, int var4, Xid var5, int var6, GatewayTpacallAsyncReply var7, TuxedoConnection var8) throws TPException;

   Txid tpprepare(TuxXidRply var1, Xid var2, int var3) throws TPException;

   Txid tpcommit(TuxXidRply var1, Xid var2, int var3, boolean var4) throws TPException;

   Reply tpcall(String var1, TypedBuffer var2, int var3, Xid var4, int var5, TuxedoConnection var6) throws TPException, TPReplyException;

   CallDescriptor tpacall(String var1, TypedBuffer var2, int var3, Xid var4, int var5, GatewayTpacallAsyncReply var6, TuxedoConnection var7) throws TPException;

   byte[] tpenqueue(String var1, String var2, EnqueueRequest var3, TypedBuffer var4, int var5, Xid var6, int var7, TuxedoConnection var8) throws TPException;

   DequeueReply tpdequeue(String var1, String var2, byte[] var3, byte[] var4, boolean var5, boolean var6, int var7, Xid var8, int var9, TuxedoConnection var10) throws TPException;

   Conversation tpconnect(String var1, TypedBuffer var2, int var3, Xid var4, int var5, TuxedoConnection var6) throws TPException;

   boolean getIsTerminated();

   void setIsTerminated();

   void doLocalTerminate();

   void setTerminationHandler(OnTerm var1);

   int getCompressionThreshold();

   int getUid();

   void restoreTfmhToCache(tfmh var1);

   int getSessionFeatures();

   void collect_stat_end(CallDescriptor var1, long var2, boolean var4, boolean var5);
}
