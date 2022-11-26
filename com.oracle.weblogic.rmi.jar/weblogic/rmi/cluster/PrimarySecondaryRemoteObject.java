package weblogic.rmi.cluster;

import java.io.Externalizable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.internal.StubInfoIntf;

public final class PrimarySecondaryRemoteObject extends ReplicaAwareRemoteObject implements Externalizable {
   private static final long serialVersionUID = 8733059777893264840L;
   private transient Remote secondary;

   public PrimarySecondaryRemoteObject(Remote primary, Remote secondary, ReplicaID pk) throws RemoteException {
      this(primary, secondary, pk, 0);
   }

   public PrimarySecondaryRemoteObject(Remote primary, Remote secondary, ReplicaID pk, int replicaVersion) throws RemoteException {
      super(primary);

      try {
         ClusterableRemoteRef ref = this.getRef();
         ref.initialize(this.getInfo());
         this.changeSecondary(secondary, pk, replicaVersion);
      } catch (RemoteException var6) {
         throw new AssertionError("impossible exception", var6);
      }
   }

   public RemoteReference getPrimaryRef() throws RemoteException {
      return this.getRef().getPrimaryRef();
   }

   public RemoteReference getSecondaryRef() throws RemoteException {
      if (this.secondary instanceof StubInfoIntf) {
         StubInfoIntf si = (StubInfoIntf)this.secondary;
         ClusterableRemoteRef crr = (ClusterableRemoteRef)si.getStubInfo().getRemoteRef();
         return crr.getPrimaryRef();
      } else {
         return null;
      }
   }

   public void updateReplicaVersion(int replicaVersion) {
      ClusterableRemoteRef ref;
      try {
         ref = this.getRef();
      } catch (RemoteException var4) {
         throw new AssertionError("impossible exception", var4);
      }

      ReplicaVersion v = ref.getReplicaList().getReplicaVersion();
      if (v == null) {
         ref.getReplicaList().setReplicaVersion(new ReplicaVersion(replicaVersion));
      } else {
         v.setVersion(replicaVersion);
      }

   }

   public void changeSecondary(Remote newSecondary, ReplicaID pk) {
      this.changeSecondary(newSecondary, pk, -1);
   }

   public void changeSecondary(Remote newSecondary, ReplicaID pk, int replicaVersion) {
      ClusterableRemoteRef ref;
      try {
         ref = this.getRef();
      } catch (RemoteException var10) {
         throw new AssertionError("impossible exception", var10);
      }

      RemoteReference primaryRef = ref.getPrimaryRef();
      RemoteReference secondaryRef = null;
      if (newSecondary instanceof StubInfoIntf) {
         this.secondary = newSecondary;
         ClusterableRemoteRef raRef = (ClusterableRemoteRef)((StubInfoIntf)newSecondary).getStubInfo().getRemoteRef();
         secondaryRef = raRef.getPrimaryRef();
      } else if (newSecondary != null) {
         throw new AssertionError("if not null secondary must always be a Stub");
      }

      ReplicaList newList;
      try {
         newList = (ReplicaList)ref.getReplicaList().clone();
      } catch (CloneNotSupportedException var9) {
         throw new AssertionError("couldn't clone replica list", var9);
      }

      newList.clear();
      newList.add(primaryRef);
      if (secondaryRef != null) {
         newList.add(secondaryRef);
      }

      ref.resetReplicaList(newList);
      if (newList instanceof BasicReplicaList) {
         ((BasicReplicaList)newList).setReplicaID(pk);
         if (replicaVersion != -1) {
            ReplicaVersion v = newList.getReplicaVersion();
            if (v == null) {
               newList.setReplicaVersion(new ReplicaVersion(replicaVersion));
            } else {
               v.setVersion(replicaVersion);
            }
         }
      }

   }

   public Remote getSecondary() {
      return this.secondary;
   }

   public Remote getPrimary() {
      return this.getPrimaryRemote();
   }

   public PrimarySecondaryRemoteObject() {
   }
}
