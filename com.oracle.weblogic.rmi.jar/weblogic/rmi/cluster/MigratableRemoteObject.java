package weblogic.rmi.cluster;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.annotation.Annotation;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Arrays;
import javax.naming.NamingException;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.cluster.migration.MigrationManager;
import weblogic.jndi.Aggregatable;
import weblogic.jndi.internal.NamingNode;
import weblogic.rmi.extensions.server.DescriptorHelper;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RemoteWrapper;
import weblogic.rmi.internal.CBVWrapper;
import weblogic.rmi.internal.RuntimeDescriptor;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.spi.HostID;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.Debug;

public final class MigratableRemoteObject extends ClusterableRemoteObject {
   private static final long serialVersionUID = -8639826242081467352L;
   private static final boolean DEBUG = true;

   public static boolean isEOS(Object object) {
      Remote remote = null;
      if (object instanceof CBVWrapper) {
         remote = ((CBVWrapper)object).getDelegate();
      } else if (object instanceof Remote) {
         remote = (Remote)object;
      } else if (object instanceof RemoteWrapper) {
         remote = ((RemoteWrapper)object).getRemoteDelegate();
      }

      if (remote == null) {
         return false;
      } else {
         try {
            boolean isEOS = false;
            if (remote instanceof StubInfoIntf) {
               StubInfo info = ((StubInfoIntf)remote).getStubInfo();
               RemoteReference ref = info.getRemoteRef();
               if (ref instanceof ClusterableRemoteRef) {
                  ClusterableRemoteRef raref = (ClusterableRemoteRef)ref;
                  isEOS = raref.getReplicaHandler() instanceof MigratableReplicaHandler;
               }
            } else {
               RuntimeDescriptor desc = DescriptorHelper.getDescriptor(remote.getClass());
               String replicaHandler = desc.getReplicaHandlerClassName();
               isEOS = replicaHandler != null && replicaHandler.equals(MigratableReplicaHandler.class.getName());
            }

            return isEOS;
         } catch (RemoteException var6) {
            return false;
         }
      }
   }

   private void resetList(String name) {
      ServiceLocator sl = GlobalServiceLocator.getServiceLocator();
      MigrationManager mm = (MigrationManager)sl.getService(MigrationManager.class, new Annotation[0]);
      HostID[] list = mm.getMigratableHostList(name);
      ReplicaList oldList = this.clusterableRef.getReplicaList();
      if (list != null) {
         Debug.say(name + " OLD LIST " + oldList);
         Debug.say(name + " MIG MAN LIST " + Arrays.asList((Object[])list));
         if (list != null && list.length != 0) {
            BasicReplicaList newList = null;

            int i;
            for(i = 0; i < list.length; ++i) {
               if (list[i] != null) {
                  RemoteReference ref = oldList.findReplicaHostedBy(list[i]);
                  if (ref != null) {
                     if (newList == null) {
                        newList = new BasicReplicaList(ref);
                     } else {
                        newList.add(ref);
                     }
                  }
               }
            }

            for(i = 0; i < oldList.size(); ++i) {
               if (newList == null) {
                  newList = new BasicReplicaList(oldList.get(i));
               }

               if (newList.findReplicaHostedBy(oldList.get(i).getHostID()) == null) {
                  newList.add(oldList.get(i));
               }
            }

            if (newList != null && newList.size() != 0) {
               Debug.say(name + " NEW LIST " + newList);
               this.clusterableRef.getReplicaList().resetWithoutShuffle(newList);
               this.clusterableRef.setCurRef(newList.get(0));
            }
         } else {
            Debug.say("WARNING For service " + name + " the migration man. returned a list of 0");
         }
      }
   }

   public void onBind(NamingNode node, String name, Aggregatable other) throws NamingException {
      super.onBind(node, name, other);
      this.resetList(name);
   }

   public void onRebind(NamingNode node, String name, Aggregatable other) throws NamingException {
      super.onRebind(node, name, other);
      this.resetList(name);
   }

   public boolean onUnbind(NamingNode node, String name, Aggregatable other) throws NamingException {
      boolean b = super.onUnbind(node, name, other);
      this.resetList(name);
      return b;
   }

   public MigratableRemoteObject(Remote primaryRepresentative) throws RemoteException {
      super(primaryRepresentative);
   }

   public MigratableRemoteObject(RemoteWrapper primaryRepresentative) throws RemoteException {
      super(primaryRepresentative);
   }

   public MigratableRemoteObject() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      super.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
   }
}
