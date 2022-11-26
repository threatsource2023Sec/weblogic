package weblogic.rmi.cluster;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.annotation.Annotation;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import weblogic.application.AppClassLoaderManager;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.jndi.internal.NameAlreadyUnboundException;
import weblogic.rmi.extensions.server.DescriptorHelper;
import weblogic.rmi.extensions.server.RemoteWrapper;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.internal.CBVWrapper;
import weblogic.rmi.internal.RuntimeDescriptor;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.AssertionError;
import weblogic.utils.classloaders.AugmentableClassLoaderManager;
import weblogic.utils.classloaders.GenericClassLoader;

public class ReplicaAwareRemoteObject implements Externalizable {
   private static final long serialVersionUID = -3476281508384826108L;
   private Object primaryRepresentative;
   private String primaryRemoteName;
   private String appName;
   private static final boolean DEBUG = false;
   private transient Remote primaryRemote;
   private transient ReplicaAwareInfo info;
   protected transient ClusterableRemoteRef remoteRef;
   private transient StubInfoIntf stub;
   private transient boolean isRemoteWrapper;

   protected ReplicaAwareRemoteObject(Remote primaryRepresentative) throws RemoteException {
      this.setPrimaryRepresentative(primaryRepresentative);
   }

   protected ReplicaAwareRemoteObject(RemoteWrapper primaryRepresentative) throws RemoteException {
      this.setPrimaryRepresentative(primaryRepresentative);
   }

   private void initialize() throws RemoteException {
      this.stub = (StubInfoIntf)ServerHelper.exportObject(this.primaryRemote);
      this.remoteRef = (ClusterableRemoteRef)this.stub.getStubInfo().getRemoteRef();
      this.primaryRemoteName = this.primaryRemote.getClass().getName();
      this.appName = this.stub.getStubInfo().getApplicationName();
   }

   public Object getPrimaryRepresentative() {
      if (this.primaryRepresentative == null) {
         try {
            Class c = null;
            if (this.appName != null) {
               AppClassLoaderManager mgr = (AppClassLoaderManager)GlobalServiceLocator.getServiceLocator().getService(AppClassLoaderManager.class, new Annotation[0]);
               GenericClassLoader gcl = mgr.findLoader(new weblogic.utils.classloaders.Annotation(this.appName));
               if (gcl != null) {
                  c = gcl.loadClass(this.primaryRemoteName);
               } else {
                  c = AugmentableClassLoaderManager.getAugmentableSystemClassLoader().loadClass(this.primaryRemoteName);
               }
            } else {
               c = AugmentableClassLoaderManager.getAugmentableSystemClassLoader().loadClass(this.primaryRemoteName);
            }

            RuntimeDescriptor desc = DescriptorHelper.getDescriptor(c);
            return new StubInfo(this.getRef(), desc.getClientRuntimeDescriptor(this.appName), desc.getStubClassName());
         } catch (ClassNotFoundException var4) {
            return null;
         } catch (RemoteException var5) {
            throw new AssertionError("Unexpected exception ", var5);
         }
      } else {
         return this.primaryRepresentative;
      }
   }

   protected void setPrimaryRepresentative(Object replaceOne) throws RemoteException {
      if (replaceOne instanceof CBVWrapper) {
         this.primaryRepresentative = replaceOne;
         this.primaryRemote = ((CBVWrapper)this.primaryRepresentative).getDelegate();
      } else if (replaceOne instanceof RemoteWrapper) {
         this.isRemoteWrapper = true;
         this.primaryRepresentative = replaceOne;
         this.primaryRemote = ((RemoteWrapper)this.primaryRepresentative).getRemoteDelegate();
      } else {
         this.primaryRepresentative = replaceOne;
         this.primaryRemote = (Remote)this.primaryRepresentative;
      }

      this.initialize();
      if (!ServerHelper.isCollocated(this.primaryRemote)) {
         throw new AssertionError(this.primaryRepresentative.toString() + " is not hosted locally");
      }
   }

   protected void setPrimaryRepresentativeToNull() {
      this.primaryRepresentative = null;
   }

   public Remote getPrimaryRemote() {
      return this.primaryRemote;
   }

   public ReplicaAwareInfo getInfo() throws NoSuchObjectException {
      if (this.info == null) {
         ClusterableServerRef ref = (ClusterableServerRef)ServerHelper.getServerReference(this.primaryRemote);
         this.info = ref.getInfo();
      }

      return this.info;
   }

   public ClusterableRemoteRef getRef() throws RemoteException {
      if (this.remoteRef == null) {
         if (!ServerHelper.isCollocated(this.primaryRemote)) {
            throw new AssertionError(this.primaryRemote + ": is not local");
         }

         ClusterableServerRef serverRef = (ClusterableServerRef)ServerHelper.getServerReference(this.primaryRemote);
         this.remoteRef = serverRef.getReplicaAwareRemoteRef();
      }

      return this.remoteRef;
   }

   public ReplicaAwareRemoteObject() {
   }

   public String toString() {
      return this.remoteRef.toString();
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      if (this.isRemoteWrapper) {
         if (this.primaryRepresentative == null) {
            throw new NameAlreadyUnboundException("Object already partially unbound in cluster.");
         }

         out.writeBoolean(this.isRemoteWrapper);
         out.writeObject(this.primaryRepresentative);
      } else if (out instanceof WLObjectOutput) {
         WLObjectOutput wlout = (WLObjectOutput)out;
         wlout.writeBoolean(this.isRemoteWrapper);
         wlout.writeString(this.primaryRemoteName);
         wlout.writeString(this.appName);
         wlout.writeObject(this.getRef());
      } else {
         out.writeBoolean(this.isRemoteWrapper);
         out.writeObject(this.primaryRemoteName);
         out.writeObject(this.appName);
         out.writeObject(this.getRef());
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.isRemoteWrapper = in.readBoolean();
      if (this.isRemoteWrapper) {
         this.primaryRepresentative = in.readObject();
         Remote remoteDelegate = ((RemoteWrapper)this.primaryRepresentative).getRemoteDelegate();
         if (remoteDelegate instanceof StubInfoIntf) {
            this.primaryRemoteName = remoteDelegate.getClass().getName();
            StubInfoIntf stub = (StubInfoIntf)remoteDelegate;
            this.remoteRef = (ClusterableRemoteRef)stub.getStubInfo().getRemoteRef();
         } else {
            this.primaryRemote = remoteDelegate;
            this.initialize();
         }
      } else if (in instanceof WLObjectInput) {
         WLObjectInput wlin = (WLObjectInput)in;
         this.primaryRemoteName = wlin.readString();
         this.appName = wlin.readString();
         this.remoteRef = (ClusterableRemoteRef)wlin.readObject();
      } else {
         this.primaryRemoteName = (String)in.readObject();
         this.appName = (String)in.readObject();
         this.remoteRef = (ClusterableRemoteRef)in.readObject();
      }

   }

   static void p(String s) {
      System.out.println("<ReplicaAwareRemoteObject>: " + s);
   }
}
