package weblogic.wtc.gwt;

import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;

public interface TxEnd {
   void end(Xid var1, int var2) throws XAException;
}
