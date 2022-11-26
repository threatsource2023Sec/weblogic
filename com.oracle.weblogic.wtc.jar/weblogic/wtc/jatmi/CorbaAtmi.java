package weblogic.wtc.jatmi;

import javax.transaction.xa.Xid;
import weblogic.wtc.gwt.MethodParameters;
import weblogic.wtc.gwt.TuxedoConnection;
import weblogic.wtc.gwt.TuxedoCorbaConnection;

public interface CorbaAtmi extends gwatmi {
   CallDescriptor tpMethodReq(TypedBuffer var1, Objinfo var2, MethodParameters var3, TuxedoCorbaConnection var4, int var5, TuxRply var6, Xid var7, int var8, TuxedoConnection var9) throws TPException;
}
