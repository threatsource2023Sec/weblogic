package weblogic.rmi.cluster;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;
import weblogic.common.internal.InteropWriteReplaceable;
import weblogic.common.internal.PeerInfo;
import weblogic.jndi.Environment;
import weblogic.jndi.internal.ThreadEnvironment;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.RMIEnvironment;
import weblogic.rmi.internal.activation.ActivatableRemoteRef;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.spi.RMIRuntime;

public final class ClusterActivatableRemoteRef extends ActivatableRemoteRef implements InteropWriteReplaceable {
   private static final long serialVersionUID = -9116119681422760510L;
   private String jndiName;
   private ReplicaHandler replicaHandler;
   private Environment environment;

   public ClusterActivatableRemoteRef() {
   }

   public ClusterActivatableRemoteRef(int oid, HostID hostID, Object aid, String jndiName) {
      super(oid, hostID, aid);
      this.jndiName = jndiName;
   }

   public Object invoke(Remote stub, RuntimeMethodDescriptor md, Object[] args, Method m) throws Throwable {
      if (this.environment != null) {
         ThreadEnvironment.push(this.environment);
      }

      try {
         RetryHandler retryHandler = new RetryHandler();
         int retryCount = 0;

         while(true) {
            try {
               retryHandler.setRetryCount(retryCount);
               Object var7 = super.invoke(stub, md, args, m);
               return var7;
            } catch (RemoteException var12) {
               RemoteReference ref = this.replicaHandler.failOver((RemoteReference)null, md, m, args, var12, retryHandler);
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

   public int hashCode() {
      return super.hashCode() ^ this.jndiName.hashCode();
   }

   public String toString() {
      return super.toString() + ", jndiName: '" + this.jndiName + "'";
   }

   public Object interopWriteReplace(PeerInfo info) throws RemoteException {
      return RMIEnvironment.getEnvironment().doInteropWriteReplace(this, info, this.getObjectID(), this.getActivationID());
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      super.writeExternal(out);
      out.writeObject(this.jndiName);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
      this.jndiName = (String)in.readObject();
      HostID id = this.getHostID();
      this.environment = new Environment();
      if (!id.isLocal()) {
         this.environment.setProviderUrl(RMIRuntime.findOrCreateEndPoint(id).getClusterURL(in));
      }

      this.replicaHandler = new EntityBeanReplicaHandler(this.getActivationID(), this.jndiName, this.environment);
   }
}
