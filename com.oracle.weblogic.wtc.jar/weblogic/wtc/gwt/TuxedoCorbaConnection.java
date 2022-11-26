package weblogic.wtc.gwt;

import java.util.HashMap;
import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;
import weblogic.wtc.jatmi.CallDescriptor;
import weblogic.wtc.jatmi.Objinfo;
import weblogic.wtc.jatmi.Reply;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TPReplyException;
import weblogic.wtc.jatmi.TypedBuffer;

public interface TuxedoCorbaConnection extends TuxedoConnection, TxEnd {
   CallDescriptor tpMethodReq(TypedBuffer var1, Objinfo var2, MethodParameters var3, int var4) throws TPException;

   Reply tpgetrply(CallDescriptor var1, int var2) throws TPException, TPReplyException;

   boolean getRollbackOnly() throws TPException;

   void end(Xid var1, int var2) throws XAException;

   HashMap getTxInfoMap();
}
