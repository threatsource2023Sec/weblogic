package weblogic.wtc.wls;

import com.bea.core.jatmi.internal.TuxedoXid;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

public class WTCXAResource implements XAResource {
   private XAResource xares;

   private Xid getImportedXid(Xid tuxedoXid) {
      Xid rmXid = null;
      if (tuxedoXid instanceof TuxedoXid) {
         rmXid = ((TuxedoXid)tuxedoXid).getImportedXid();
         if (rmXid == null) {
            rmXid = tuxedoXid;
         }
      } else {
         rmXid = tuxedoXid;
      }

      return rmXid;
   }

   public WTCXAResource(XAResource xares) {
      this.xares = xares;
   }

   public void setXAResource(XAResource xares) {
      this.xares = xares;
   }

   public void commit(Xid tuxedoXid, boolean onePhase) throws XAException {
      this.xares.commit(this.getImportedXid(tuxedoXid), onePhase);
   }

   public void end(Xid tuxedoXid, int flags) throws XAException {
      this.xares.end(this.getImportedXid(tuxedoXid), flags);
   }

   public void forget(Xid tuxedoXid) throws XAException {
      this.xares.forget(this.getImportedXid(tuxedoXid));
   }

   public int getTransactionTimeout() throws XAException {
      return this.xares.getTransactionTimeout();
   }

   public boolean isSameRM(XAResource xares) throws XAException {
      return this.xares.isSameRM(xares);
   }

   public int prepare(Xid tuxedoXid) throws XAException {
      return this.xares.prepare(this.getImportedXid(tuxedoXid));
   }

   public Xid[] recover(int flag) throws XAException {
      return this.xares.recover(flag);
   }

   public void rollback(Xid tuxedoXid) throws XAException {
      this.xares.rollback(this.getImportedXid(tuxedoXid));
   }

   public boolean setTransactionTimeout(int seconds) throws XAException {
      return this.xares.setTransactionTimeout(seconds);
   }

   public void start(Xid tuxedoXid, int flags) throws XAException {
      this.xares.start(this.getImportedXid(tuxedoXid), flags);
   }

   public String toString() {
      return this.xares.toString();
   }

   public boolean equals(Object obj) {
      return this.xares.equals(obj);
   }

   public int hashCode() {
      return this.xares.hashCode();
   }
}
