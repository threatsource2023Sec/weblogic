package javax.resource.spi;

import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;

public interface XATerminator {
   void commit(Xid var1, boolean var2) throws XAException;

   void forget(Xid var1) throws XAException;

   int prepare(Xid var1) throws XAException;

   Xid[] recover(int var1) throws XAException;

   void rollback(Xid var1) throws XAException;
}
