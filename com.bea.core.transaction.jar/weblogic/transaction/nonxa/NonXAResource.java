package weblogic.transaction.nonxa;

import javax.transaction.xa.Xid;

public interface NonXAResource {
   void commit(Xid var1, boolean var2) throws NonXAException;

   void rollback(Xid var1) throws NonXAException;

   boolean isSameRM(NonXAResource var1) throws NonXAException;
}
