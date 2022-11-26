package kodo.ee;

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

public class XAResourceImpl implements XAResource {
   public void commit(Xid xid, boolean onePhase) throws XAException {
      throw new XAException("Unimplemented");
   }

   public void end(Xid xid, int flags) throws XAException {
      throw new XAException("Unimplemented");
   }

   public void forget(Xid xid) throws XAException {
      throw new XAException("Unimplemented");
   }

   public int getTransactionTimeout() throws XAException {
      throw new XAException("Unimplemented");
   }

   public boolean isSameRM(XAResource xares) throws XAException {
      throw new XAException("Unimplemented");
   }

   public int prepare(Xid xid) throws XAException {
      throw new XAException("Unimplemented");
   }

   public Xid[] recover(int flag) throws XAException {
      throw new XAException("Unimplemented");
   }

   public void rollback(Xid xid) throws XAException {
      throw new XAException("Unimplemented");
   }

   public boolean setTransactionTimeout(int seconds) throws XAException {
      throw new XAException("Unimplemented");
   }

   public void start(Xid xid, int flags) throws XAException {
      throw new XAException("Unimplemented");
   }
}
