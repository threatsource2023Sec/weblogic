package weblogic.wtc.gwt;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.transaction.xa.Xid;
import weblogic.wtc.jatmi.ApplicationToMonitorInterface;
import weblogic.wtc.jatmi.CallDescriptor;
import weblogic.wtc.jatmi.Conversation;
import weblogic.wtc.jatmi.DequeueReply;
import weblogic.wtc.jatmi.EnqueueRequest;
import weblogic.wtc.jatmi.Reply;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TPReplyException;
import weblogic.wtc.jatmi.TpacallAsyncReply;
import weblogic.wtc.jatmi.TypedBuffer;
import weblogic.wtc.jatmi.UserRec;

public interface TuxedoConnection extends ApplicationToMonitorInterface {
   List getProviderRoute(String var1, TypedBuffer var2, Xid var3, int var4) throws TPException;

   CallDescriptor tpacall(String var1, TypedBuffer var2, int var3) throws TPException;

   CallDescriptor tpacall(String var1, TypedBuffer var2, int var3, TpacallAsyncReply var4) throws TPException;

   void tpcancel(CallDescriptor var1, int var2) throws TPException;

   Reply tpgetrply(CallDescriptor var1, int var2) throws TPException, TPReplyException;

   byte[] tpenqueue(String var1, String var2, EnqueueRequest var3, TypedBuffer var4, int var5) throws TPException;

   DequeueReply tpdequeue(String var1, String var2, byte[] var3, byte[] var4, boolean var5, boolean var6, int var7) throws TPException;

   DequeueReply tpdequeue(String var1, String var2, int var3) throws TPException;

   Reply tpcall(String var1, TypedBuffer var2, int var3) throws TPException, TPReplyException;

   void tpterm();

   Conversation tpconnect(String var1, TypedBuffer var2, int var3) throws TPException;

   boolean isTerminated();

   boolean getRollbackOnly() throws TPException;

   void setUserRecord(UserRec var1);

   UserRec getUserRecord();

   void updateViewMap(String var1, Class var2, int var3) throws TPException;

   void tpsprio(int var1, int var2) throws TPException;

   HashMap getCurrImpSvc();

   ConcurrentHashMap getCurrImpSvc2();
}
