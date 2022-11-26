package weblogic.cluster;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import javax.inject.Singleton;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.jvnet.hk2.annotations.Service;
import weblogic.jndi.Environment;
import weblogic.rmi.extensions.server.RemoteDomainSecurityHelper;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.subject.SubjectManager;

@Service
@Singleton
public final class RemoteClusterHealthCheckerImpl implements RemoteClusterHealthChecker, ClusterMembersChangeListener {
   static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private long currentVersion;
   private final HashSet set = new HashSet();

   public void start() {
      try {
         ClusterServices services = ClusterService.getClusterServiceInternal();
         this.updateMembershipInfo(services.getLocalMember(), true);
         Iterator iterator = services.getRemoteMembers().iterator();

         while(iterator.hasNext()) {
            ClusterMemberInfo info = (ClusterMemberInfo)iterator.next();
            this.updateMembershipInfo(info, true);
         }

         services.addClusterMembersListener(this);
         Environment env = new Environment();
         env.setReplicateBindings(true);
         env.setProperty("weblogic.jndi.createUnderSharable", "true");
         env.getInitialContext().bind("weblogic/cluster/RemoteClusterHealthChecker", this);
      } catch (NamingException var4) {
         throw new AssertionError("Unexpected exception" + var4.toString());
      }
   }

   public void stop() {
      ClusterService.getClusterServiceInternal().removeClusterMembersListener(this);

      try {
         (new InitialContext()).unbind("weblogic/cluster/RemoteClusterHealthChecker");
         ServerHelper.unexportObject(this, false);
      } catch (NamingException var2) {
         throw new AssertionError("Unexpected exception" + var2.toString());
      } catch (NoSuchObjectException var3) {
      }

   }

   public ArrayList checkClusterMembership(long version) throws RemoteException {
      Collection collection = null;
      this.verifyCaller();
      synchronized(this) {
         if (version == this.currentVersion) {
            return null;
         }

         collection = (HashSet)this.set.clone();
      }

      ArrayList result = new ArrayList(collection);
      return result;
   }

   private void verifyCaller() {
      try {
         HostID id = ServerHelper.getClientEndPoint().getHostID();
         AuthenticatedSubject subject = (AuthenticatedSubject)SubjectManager.getSubjectManager().getCurrentSubject(KERNEL_ID);
         if (subject == null) {
            throw new SecurityException("Null user is not permitted to perform MAN remote cluster membership operations");
         } else {
            int result = RemoteDomainSecurityHelper.acceptRemoteDomainCall(id, subject);
            if (result == 1) {
               throw new SecurityException("user " + subject.getName() + " is not permitted to perform MAN cluster membership operations");
            }
         }
      } catch (ServerNotActiveException var4) {
         throw new SecurityException("operation not permitted");
      }
   }

   public void clusterMembersChanged(ClusterMembersChangeEvent cmce) {
      ClusterMemberInfo info = cmce.getClusterMemberInfo();
      int action = cmce.getAction();
      if (action == 1) {
         this.updateMembershipInfo(info, false);
      } else if (action == 0) {
         this.updateMembershipInfo(info, true);
      }

   }

   private synchronized void updateMembershipInfo(ClusterMemberInfo info, boolean add) {
      if (add) {
         this.set.add(info);
      } else {
         this.set.remove(info);
      }

      this.currentVersion = 0L;

      ClusterMemberInfo iinfo;
      for(Iterator i = this.set.iterator(); i.hasNext(); this.currentVersion += (long)iinfo.identity().hashCode()) {
         iinfo = (ClusterMemberInfo)i.next();
      }

   }
}
