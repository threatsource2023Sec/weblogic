package weblogic.rmi.cluster;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.naming.ConfigurationException;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingException;
import org.omg.PortableServer.Servant;
import weblogic.cluster.ClusterAnnouncementsDebugLogger;
import weblogic.jndi.Aggregatable;
import weblogic.jndi.AggregatableInternal;
import weblogic.jndi.OpaqueReference;
import weblogic.jndi.internal.NamingNode;
import weblogic.protocol.LocalServerIdentity;
import weblogic.rmi.extensions.server.DescriptorHelper;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RemoteWrapper;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.internal.CBVWrapper;
import weblogic.rmi.internal.RemoteType;
import weblogic.rmi.internal.RuntimeDescriptor;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.spi.HostID;
import weblogic.utils.AssertionError;

public class ClusterableRemoteObject extends ReplicaAwareRemoteObject implements AggregatableInternal, OpaqueReference, Externalizable {
   private static final long serialVersionUID = 588136583649318138L;
   private RemoteType remoteType = null;
   protected transient ClusterableRemoteRef clusterableRef;
   private transient BasicReplicaList serverRefs = new BasicReplicaList();

   static boolean isClusterable(Object object) {
      Object remote = null;
      if (object instanceof CBVWrapper) {
         remote = ((CBVWrapper)object).getDelegate();
      } else if (object instanceof Remote) {
         remote = object;
      } else if (object instanceof RemoteWrapper) {
         remote = ((RemoteWrapper)object).getRemoteDelegate();
      }

      if (remote == null) {
         return false;
      } else {
         try {
            if (remote instanceof StubInfoIntf) {
               StubInfo info = ((StubInfoIntf)remote).getStubInfo();
               RemoteReference ref = info.getRemoteRef();
               return ref instanceof ClusterableRemoteRef;
            } else {
               RuntimeDescriptor rtd = DescriptorHelper.getDescriptor(remote.getClass());
               return rtd.isClusterable();
            }
         } catch (RemoteException var4) {
            return false;
         }
      }
   }

   static boolean isIDLObject(Object object) {
      return object instanceof org.omg.CORBA.Object || object instanceof Servant;
   }

   public ClusterableRemoteObject(Remote primaryRepresentative) throws RemoteException {
      super(primaryRepresentative);
      this.initialize();
   }

   public ClusterableRemoteObject(RemoteWrapper primaryRepresentative) throws RemoteException {
      super(primaryRepresentative);
      this.initialize();
   }

   private void initialize() {
      try {
         if (this.remoteType == null) {
            this.remoteType = ServerHelper.getDescriptor(this.getPrimaryRemote()).getRemoteType();
         }

         this.clusterableRef = this.getRef();
      } catch (RemoteException var2) {
         throw new AssertionError("Attempt to create RemoteObject using unexported Remote", var2);
      }
   }

   public Object getReferent(Name name, Context ctx) throws NamingException {
      Object obj = this.getPrimaryRepresentative();
      if (obj == null) {
         throw new NamingException("unable to find primary representative");
      } else {
         return obj;
      }
   }

   public String toString() {
      return super.toString() + "\t" + this.remoteType.toString();
   }

   protected boolean isSameType(ClusterableRemoteObject other) {
      RemoteType otherType = other.remoteType;
      return this.remoteType != null && otherType != null ? this.remoteType.isAssignableFrom(otherType) : false;
   }

   public void onBind(NamingNode node, String name, Aggregatable other) throws NamingException {
      if (other == null) {
         if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
            ClusterAnnouncementsDebugLogger.debug("Adding JNDI binding " + name + " Ref=" + this.clusterableRef);
         }

         try {
            this.initializeRef(node.getNameInNamespace(name));
            if (this.clusterableRef.getHostID() == null) {
               if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
                  ClusterAnnouncementsDebugLogger.debug("clusterableRef.getHostID is null for clusterableRef: " + this.clusterableRef);
               }

               throw new AssertionError("Invalid Remote Ref: " + this.clusterableRef);
            }

            this.serverRefs.add(this.clusterableRef);
         } catch (RemoteException var9) {
            throw new AssertionError("impossible exception", var9);
         }
      } else {
         if (!(other instanceof ClusterableRemoteObject)) {
            throw new NameAlreadyBoundException();
         }

         ClusterableRemoteObject otherClusterable = (ClusterableRemoteObject)other;
         ClusterableRemoteRef otherRef = otherClusterable.clusterableRef;
         HostID otherRefHostID = otherRef.getHostID();
         if (!this.isSameType(otherClusterable)) {
            throw new NameAlreadyBoundException("Failed to bind remote object (" + otherClusterable + ") to replica aware stub at " + name + " (" + this + ") because it does not implement the same remote interfaces");
         }

         if (otherRefHostID == null) {
            if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
               this.logIgnoreAggregation(otherRef, name, "HostID is null");
            }

            return;
         }

         if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
            ClusterAnnouncementsDebugLogger.debug("Attempting to aggregate refs for binding " + name + " Existing Ref=" + this.clusterableRef + " New Ref=" + otherRef);
         }

         if (!this.clusterableRef.getHostID().equals(otherRefHostID)) {
            try {
               String bindingName = node.getNameInNamespace(name);
               otherClusterable.initializeRef(bindingName);
               if (otherRef.isInitialized() && otherRef.getReplicaList().size() == 0) {
                  if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
                     this.logIgnoreAggregation(otherRef, bindingName, "The ref is initialized, but has no primary");
                  }

                  return;
               }

               this.clusterableRef.add(otherRef);
               this.serverRefs.add(otherRef);
               if (otherRefHostID.isLocal()) {
                  this.clusterableRef = otherRef;
                  this.remoteRef = otherRef;
               }
            } catch (RemoteException var8) {
               throw new AssertionError("impossible exception", var8);
            }
         } else {
            if (this.clusterableRef.getObjectID() != otherRef.getObjectID()) {
               throw new NameAlreadyBoundException("Failed to bind remote object (" + otherClusterable + ") to replica aware stub at " + name + "(" + this + ")");
            }

            this.clusterableRef.getReplicaList().add(otherRef.getReplicaList().getPrimary());
            this.serverRefs.add(otherRef.getReplicaList().getPrimary());
         }
      }

   }

   public boolean isBindable(AggregatableInternal object) {
      if (!(object instanceof ClusterableRemoteObject)) {
         return false;
      } else {
         ClusterableRemoteObject otherClusterable = (ClusterableRemoteObject)object;
         ClusterableRemoteRef otherRef = otherClusterable.clusterableRef;
         HostID otherRefHostID = otherRef.getHostID();
         if (!this.isSameType(otherClusterable)) {
            return false;
         } else if (otherRefHostID == null) {
            if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
               this.logIgnoreAggregation(otherRef, "", "HostID is null");
            }

            return false;
         } else {
            return !this.clusterableRef.getHostID().equals(otherRefHostID) || this.clusterableRef.getObjectID() == otherRef.getObjectID();
         }
      }
   }

   public void onRebind(NamingNode node, String name, Aggregatable other) throws NamingException {
      boolean compatible = false;
      if (other instanceof ClusterableRemoteObject) {
         ClusterableRemoteObject otherClusterable = (ClusterableRemoteObject)other;
         if (this.isSameType(otherClusterable)) {
            try {
               otherClusterable.initializeRef(node.getNameInNamespace(name));
               this.clusterableRef.replace(otherClusterable.clusterableRef);
               this.setPrimaryRepresentative(otherClusterable.getPrimaryRepresentative());
               compatible = true;
            } catch (RemoteException var7) {
               throw new AssertionError("impossible exception", var7);
            }
         }
      }

      if (!compatible) {
         throw new NameAlreadyBoundException("Can't rebind anything but a replica-aware stub to a name that is currently bound to a replica-aware stub");
      }
   }

   public boolean onUnbind(NamingNode node, String name, Aggregatable other) throws NamingException {
      if (other == null) {
         this.serverRefs.removeOne(LocalServerIdentity.getIdentity());
         this.clusterableRef.removeOne(LocalServerIdentity.getIdentity());
         this.setPrimaryRepresentativeToNull();
      } else if (other instanceof ClusterableRemoteObject) {
         try {
            ClusterableRemoteObject otherClusterable = (ClusterableRemoteObject)other;
            if (otherClusterable.getRef().getPrimaryRef() == null && ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
               ClusterAnnouncementsDebugLogger.debug("ClusterableRemoteObject.onUnbind otherClusterable: " + otherClusterable + ", serverRefs: " + this.serverRefs + " clusterableRef: " + this.clusterableRef);
            }

            if (this.isSameType(otherClusterable) && otherClusterable.getRef().getPrimaryRef() != null) {
               this.serverRefs.removeOne(otherClusterable.getRef().getPrimaryRef().getHostID());
               this.clusterableRef.remove(otherClusterable.getRef());
            }
         } catch (RemoteException var6) {
            NamingException ne = new ConfigurationException("failed to unbind due to unexpected exception");
            ne.setRootCause(var6);
            throw ne;
         }
      }

      return this.serverRefs.size() == 0;
   }

   protected void initializeRef(String fullName) throws ConfigurationException {
      if (!this.clusterableRef.isInitialized()) {
         try {
            ReplicaAwareInfo info = this.getInfo();
            info.setJNDIName(fullName);
            this.clusterableRef.initialize(info);
         } catch (NoSuchObjectException var4) {
            ConfigurationException ne = new ConfigurationException("failed to rebind due to unexpected exception");
            ne.setRootCause(var4);
            throw ne;
         }
      }

   }

   void logIgnoreAggregation(ClusterableRemoteRef ignoredRef, String bindingName, String reason) {
      ClusterAnnouncementsDebugLogger.debug("Ignoring aggregation of ref=" + ignoredRef + " to JNDI binding " + bindingName + ". Reason: " + reason);
   }

   public ClusterableRemoteObject() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.remoteType);
      super.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.remoteType = (RemoteType)in.readObject();
      super.readExternal(in);
      this.initialize();
   }
}
