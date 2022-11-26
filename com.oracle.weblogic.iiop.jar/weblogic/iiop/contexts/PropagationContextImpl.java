package weblogic.iiop.contexts;

import javax.transaction.xa.Xid;
import org.omg.CORBA.Any;
import org.omg.CosTransactions.Coordinator;
import org.omg.CosTransactions.PropagationContext;
import org.omg.CosTransactions.PropagationContextHelper;
import org.omg.CosTransactions.Terminator;
import org.omg.CosTransactions.TransIdentity;
import org.omg.CosTransactions.otid_t;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.transaction.Transaction;
import weblogic.transaction.TxHelper;

public class PropagationContextImpl extends ServiceContext {
   private PropagationContext ctx;
   private Xid xid;
   private boolean registered;
   private Throwable registrationError;
   public static final PropagationContextImpl NULL_CTX = new PropagationContextImpl();
   private static final byte[] NULL_BRANCH = new byte[]{11, 14, 10, 3};

   private PropagationContextImpl() {
      this(new otid_t(0, 0, new byte[0]));
   }

   private PropagationContextImpl(otid_t otid) {
      super(0);
      this.registered = true;
      TransIdentity id = new TransIdentity((Coordinator)null, (Terminator)null, otid);
      this.ctx = new PropagationContext(0, id, new TransIdentity[0], (Any)null);
   }

   public PropagationContextImpl(Transaction tx, Coordinator coordinator) {
      super(0);
      this.registered = true;
      this.xid = tx.getXID();
      byte[] gtrid = tx.getXID().getGlobalTransactionId();
      byte[] bqual = tx.getXID().getBranchQualifier();
      if ((bqual == null || bqual.length == 0) && tx.getXID().getFormatId() == 48801) {
         bqual = NULL_BRANCH;
      }

      int bquallen = bqual == null ? 0 : bqual.length;
      byte[] xid = new byte[gtrid.length + bquallen];
      System.arraycopy(gtrid, 0, xid, 0, gtrid.length);
      if (bquallen > 0) {
         System.arraycopy(bqual, 0, xid, gtrid.length, bqual.length);
      }

      otid_t otid = new otid_t(tx.getXID().getFormatId(), bquallen, xid);
      TransIdentity id = new TransIdentity(coordinator, (Terminator)null, otid);
      this.ctx = new PropagationContext(getTimeout(tx), id, new TransIdentity[0], (Any)null);
   }

   private static int getTimeout(Transaction tx) {
      Integer timeout = (Integer)tx.getProperty("weblogic.transaction.timeoutSeconds");
      return timeout == null ? 0 : timeout;
   }

   public PropagationContextImpl(PropagationContext ctx) {
      super(0);
      this.registered = true;
      this.ctx = ctx;
   }

   public final PropagationContext getPropagationContext() {
      return this.ctx;
   }

   public final Xid getXid() {
      return this.xid;
   }

   public final boolean isNull() {
      return this.ctx == null || this.ctx.current.coord == null && this.ctx.current.term == null;
   }

   public final PropagationContextImpl getNullContext() {
      return this.ctx != null && this.ctx.current.otid != null && this.ctx.current.otid.tid.length != 0 ? new PropagationContextImpl(this.ctx.current.otid) : NULL_CTX;
   }

   public final boolean isForeign() {
      return this.ctx == null || this.ctx.current.otid.formatID != 48801;
   }

   public PropagationContextImpl(CorbaInputStream in) {
      super(0);
      this.registered = true;
      this.readEncapsulatedContext(in);
   }

   public final synchronized Throwable waitForRegistration() {
      while(!this.registered) {
         try {
            this.wait();
         } catch (InterruptedException var2) {
         }
      }

      return this.registrationError;
   }

   public final synchronized void notifyRegistration() {
      this.registrationError = null;
      this.registered = true;
      this.notify();
   }

   public final synchronized void notifyRegistration(Throwable t) {
      this.registrationError = t;
      this.registered = true;
      this.notify();
   }

   public final void requiresRegistration() {
      if (this.registered) {
         synchronized(this) {
            this.registered = false;
         }
      }
   }

   public final Throwable getRegistrationError() {
      return this.registrationError;
   }

   public void write(CorbaOutputStream out) {
      this.writeEncapsulatedContext(out);
   }

   protected void writeEncapsulation(CorbaOutputStream out) {
      PropagationContextHelper.write(out, this.ctx);
   }

   protected void readEncapsulation(CorbaInputStream in) {
      this.ctx = PropagationContextHelper.read(in);
      if (!this.isNull() || !this.isForeign()) {
         int gtridlen = this.ctx.current.otid.tid.length - this.ctx.current.otid.bqual_length;
         byte[] gtrid = new byte[gtridlen];
         System.arraycopy(this.ctx.current.otid.tid, 0, gtrid, 0, gtridlen);
         if (this.ctx.current.otid.bqual_length > 0) {
            byte[] bqual = new byte[this.ctx.current.otid.bqual_length];
            System.arraycopy(this.ctx.current.otid.tid, gtridlen, bqual, 0, this.ctx.current.otid.bqual_length);
            if (bqual.length == NULL_BRANCH.length && this.ctx.current.otid.formatID == 48801 && bqual[0] == NULL_BRANCH[0] && bqual[1] == NULL_BRANCH[1] && bqual[2] == NULL_BRANCH[2] && bqual[3] == NULL_BRANCH[3]) {
               this.xid = TxHelper.createXid(this.ctx.current.otid.formatID, gtrid, (byte[])null);
            } else {
               this.xid = TxHelper.createXid(this.ctx.current.otid.formatID, gtrid, bqual);
            }
         } else {
            this.xid = TxHelper.createXid(this.ctx.current.otid.formatID, gtrid, (byte[])null);
         }

      }
   }

   public String toString() {
      return "PropagationContextImpl: context (" + this.ctx + ")";
   }

   protected static final void p(String msg) {
      System.err.println("<PropagationContextImpl> " + msg);
   }
}
