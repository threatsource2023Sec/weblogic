package weblogic.jms.foreign;

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import weblogic.transaction.internal.IgnoreXAResource;

class IgnoreXAResourceImpl implements XAResource, IgnoreXAResource {
   public void commit(Xid xid, boolean onePhase) throws XAException {
      throw new XAException("method commit should not be called on weblogic.transaction.internal.IgnoreXAResource");
   }

   public void end(Xid xid, int flags) throws XAException {
      throw new XAException("method end should not be called on weblogic.transaction.internal.IgnoreXAResource");
   }

   public void forget(Xid xid) throws XAException {
      throw new XAException("method forget should not be called on weblogic.transaction.internal.IgnoreXAResource");
   }

   public int getTransactionTimeout() throws XAException {
      throw new XAException("method getTransactioinTimeout should not be called on weblogic.transaction.internal.IgnoreXAResource");
   }

   public boolean isSameRM(XAResource xares) throws XAException {
      throw new XAException("method isSameRM should not be called on weblogic.transaction.internal.IgnoreXAResource");
   }

   public int prepare(Xid xid) throws XAException {
      throw new XAException("method prepare should not be called on weblogic.transaction.internal.IgnoreXAResource");
   }

   public Xid[] recover(int flag) throws XAException {
      throw new XAException("method recover should not be called on weblogic.transaction.internal.IgnoreXAResource");
   }

   public void rollback(Xid xid) throws XAException {
      throw new XAException("method rollback should not be called on weblogic.transaction.internal.IgnoreXAResource");
   }

   public boolean setTransactionTimeout(int seconds) throws XAException {
      throw new XAException("method setTransactionTimeout should not be called on weblogic.transaction.internal.IgnoreXAResource");
   }

   public void start(Xid xid, int flags) throws XAException {
      throw new XAException("method start should not be called on weblogic.transaction.internal.IgnoreXAResource");
   }
}
