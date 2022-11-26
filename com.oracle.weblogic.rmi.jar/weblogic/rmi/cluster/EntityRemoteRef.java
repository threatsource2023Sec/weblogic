package weblogic.rmi.cluster;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import javax.transaction.Transaction;
import weblogic.jndi.Environment;
import weblogic.jndi.internal.ThreadEnvironment;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.BasicRemoteRef;
import weblogic.rmi.internal.Enrollable;
import weblogic.rmi.internal.dgc.DGCClientHelper;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.spi.InboundResponse;
import weblogic.rmi.spi.OutboundRequest;
import weblogic.rmi.spi.RMIRuntime;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionManager;

public final class EntityRemoteRef extends BasicRemoteRef {
   private static final long serialVersionUID = -3542562121173822208L;
   private static final boolean DEBUG = false;
   private ReplicaHandler replicaHandler;
   private String jndiName;
   private Object pk;
   private Environment environment;
   private Enrollable enrollable;

   protected void finalize() throws Throwable {
      if (this.enrollable != null) {
         this.enrollable.unenroll();
      }

   }

   public EntityRemoteRef(int oid, HostID hostID, String jndiName, Object pk) {
      super(oid, hostID);
      this.jndiName = jndiName;
      this.pk = pk;
   }

   public boolean equals(Object obj) {
      if (obj instanceof EntityRemoteRef) {
         EntityRemoteRef other = (EntityRemoteRef)obj;
         return !other.pk.equals(this.pk) ? false : other.jndiName.equals(this.jndiName);
      } else {
         return false;
      }
   }

   public String toString() {
      return super.toString() + " - hostID: '" + this.hostID + "', oid: '" + this.oid + "' PK " + this.pk;
   }

   public final Object invoke(Remote stub, RuntimeMethodDescriptor md, Object[] args, Method m) throws Throwable {
      if (this.environment != null) {
         ThreadEnvironment.push(this.environment);
      }

      String partitionURL = this.getPartitionURL(stub);

      try {
         RetryHandler retryHandler = new RetryHandler();
         int retryCount = 0;

         while(true) {
            try {
               retryHandler.setRetryCount(retryCount);
               Object var8 = this.privateInvoke(md, args, partitionURL);
               return var8;
            } catch (RemoteException var13) {
               RemoteReference ref = this.replicaHandler.failOver((RemoteReference)null, md, m, args, var13, retryHandler);
               this.oid = ref.getObjectID();
               this.hostID = ref.getHostID();
               ++retryCount;
            }
         }
      } finally {
         if (this.environment != null) {
            ThreadEnvironment.pop();
         }

      }
   }

   private Object privateInvoke(RuntimeMethodDescriptor md, Object[] args, String partitionURL) throws Throwable {
      Transaction tx = null;
      TransactionManager tm = (TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();
      if (!md.isTransactional()) {
         tx = tm.forceSuspend();
      }

      OutboundRequest request = this.getOutboundRequest(md, partitionURL);
      InboundResponse response = null;

      Object var8;
      try {
         request.marshalArgs(args);
         response = request.sendReceive();
         var8 = response.unmarshalReturn();
      } finally {
         try {
            if (response != null) {
               response.close();
            }
         } catch (IOException var15) {
            throw new UnmarshalException("failed to close response stream", var15);
         }

         if (!md.isTransactional()) {
            tm.forceResume(tx);
         }

      }

      return var8;
   }

   public EntityRemoteRef() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      super.writeExternal(out);
      out.writeObject(this.jndiName);
      out.writeObject(this.pk);
      if (this.enrollable != null) {
         this.enrollable.renewLease();
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
      this.jndiName = (String)in.readObject();
      this.pk = in.readObject();
      HostID id = this.getHostID();
      this.environment = new Environment();
      if (!id.isLocal()) {
         this.environment.setProviderUrl(RMIRuntime.findEndPoint(id).getClusterURL(in));
      }

      this.replicaHandler = new EntityBeanReplicaHandler(this.pk, this.jndiName, this.environment);
      this.enrollable = DGCClientHelper.findAndEnroll(this);
   }
}
