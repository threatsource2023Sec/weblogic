package weblogic.corba.cos.transactions;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.LinkedList;
import javax.rmi.PortableRemoteObject;
import javax.transaction.SystemException;
import javax.transaction.xa.Xid;
import org.omg.CORBA.INVALID_TRANSACTION;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CosTransactions.NotPrepared;
import org.omg.CosTransactions.RecoveryCoordinator;
import org.omg.CosTransactions.Resource;
import org.omg.CosTransactions.Status;
import weblogic.corba.idl.ObjectImpl;
import weblogic.iiop.IIOPLogger;
import weblogic.rmi.extensions.activation.Activatable;
import weblogic.rmi.extensions.activation.Activator;
import weblogic.transaction.Transaction;
import weblogic.transaction.TxHelper;
import weblogic.utils.collections.ConcurrentHashMap;

public final class RecoveryCoordinatorImpl extends ObjectImpl implements RecoveryCoordinator, Activatable, Releasable {
   private RecoveryActivationID aid;
   private static boolean throwONE = Boolean.getBoolean("weblogic.iiop.recoverycoordinator.ThrowONE");

   public RecoveryCoordinatorImpl(Xid xid, Resource res) {
      this.aid = new RecoveryActivationID(xid, res);
   }

   RecoveryCoordinatorImpl(Object aid) {
      this.aid = (RecoveryActivationID)aid;
   }

   public static RecoveryCoordinator getRecoveryCoordinator(Xid xid, Resource res) {
      return (RecoveryCoordinator)((RecoveryFactory)RecoveryFactory.getActivator()).activate(new RecoveryActivationID(xid, res));
   }

   public void release() {
      ((RecoveryFactory)RecoveryFactory.getActivator()).deactivate(this);
   }

   public Object getActivationID() {
      return this.aid;
   }

   public Activator getActivator() {
      return RecoveryFactory.getActivator();
   }

   public Status replay_completion(Resource res) throws NotPrepared {
      Transaction tx = this.getTx();
      if (tx == null) {
         if (throwONE) {
            throw new OBJECT_NOT_EXIST("no transaction was found");
         } else {
            return Status.StatusRolledBack;
         }
      } else {
         try {
            ConcurrentHashMap h = (ConcurrentHashMap)tx.getLocalProperty("weblogic.transaction.ots.resources");
            if (h == null || h.get(res) == null) {
               LinkedList omr = (LinkedList)tx.getLocalProperty("weblogic.transaction.ots.failedResources");
               if (omr == null) {
                  omr = new LinkedList();
                  tx.setLocalProperty("weblogic.transaction.ots.failedResources", omr);
               }

               omr.add(res);
            }

            Status status = OTSHelper.jta2otsStatus(tx.getStatus());
            switch (status.value()) {
               case 0:
               case 7:
                  throw new NotPrepared();
               default:
                  return status;
            }
         } catch (SystemException var5) {
            IIOPLogger.logOTSError("replay_completion() failed unexpectedly", var5);
            throw new INVALID_TRANSACTION(var5.getMessage());
         }
      }
   }

   private final Transaction getTx() {
      return (Transaction)TxHelper.getTransactionManager().getTransaction(this.aid.xid);
   }

   private static class RecoveryActivationID implements Externalizable {
      private static final long serialVersionUID = -3321373552041680942L;
      private Xid xid;
      private Resource resource;

      public RecoveryActivationID() {
      }

      private RecoveryActivationID(Xid xid, Resource resource) {
         this.xid = xid;
         this.resource = resource;
      }

      public int hashCode() {
         return this.resource.hashCode() ^ (this.xid == null ? 1 : this.xid.hashCode());
      }

      public boolean equals(Object other) {
         try {
            if (other == null) {
               return false;
            }

            if (this.xid == null && ((RecoveryActivationID)other).xid == null || ((RecoveryActivationID)other).xid.equals(this.xid)) {
               return ((RecoveryActivationID)other).resource.equals(this.resource);
            }
         } catch (ClassCastException var3) {
         }

         return false;
      }

      public void readExternal(ObjectInput decoder) throws IOException, ClassNotFoundException {
         this.xid = OTSHelper.readXid(decoder);
         Object obj = decoder.readObject();
         this.resource = (Resource)PortableRemoteObject.narrow(obj, Resource.class);
      }

      public void writeExternal(ObjectOutput encoder) throws IOException {
         OTSHelper.writeXid(encoder, this.xid);
         encoder.writeObject(this.resource);
      }

      // $FF: synthetic method
      RecoveryActivationID(Xid x0, Resource x1, Object x2) {
         this(x0, x1);
      }
   }
}
