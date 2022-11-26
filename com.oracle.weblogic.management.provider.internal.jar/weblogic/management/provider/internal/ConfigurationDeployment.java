package weblogic.management.provider.internal;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.annotation.Annotation;
import java.rmi.UnknownHostException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.deploy.service.ChangeDescriptor;
import weblogic.deploy.service.Deployment;
import weblogic.deploy.service.Version;
import weblogic.deploy.service.Deployment.DeploymentType;
import weblogic.deploy.service.internal.BaseDeploymentImpl;
import weblogic.management.DomainDir;
import weblogic.management.ManagementLogger;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.URLManagerService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;

public class ConfigurationDeployment extends BaseDeploymentImpl {
   private static final long serialVersionUID = 4191427923503258738L;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private String constrainedToPartitionName = null;

   public ConfigurationDeployment() {
   }

   ConfigurationDeployment(String callbackId) {
      super(callbackId, callbackId, (Version)null, (List)null, (List)null, (String)null, (List)null);
   }

   public void addServersToBeRestarted(String[] serverNames) {
      if (serverNames != null) {
         for(int i = 0; i < serverNames.length; ++i) {
            if (isServerRunning(serverNames[i])) {
               ManagementLogger.logServerNeedsReboot(serverNames[i]);
            }

            this.addServerToBeRestarted(serverNames[i]);
         }

      }
   }

   private static boolean isServerRunning(String serverName) {
      String thisServer = ManagementService.getRuntimeAccess(KERNEL_ID).getServerName();
      if (thisServer.equals(serverName)) {
         return true;
      } else {
         try {
            return getURLManagerService().findAdministrationURL(serverName) != null;
         } catch (UnknownHostException var3) {
            return false;
         }
      }
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      super.writeExternal(out);
      if (out instanceof PeerInfoable) {
         PeerInfo peerInfo = ((PeerInfoable)out).getPeerInfo();
         if (peerInfo != null && peerInfo.is1221Peer()) {
            out.writeObject(this.constrainedToPartitionName);
         }
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
      if (in instanceof PeerInfoable) {
         PeerInfo peerInfo = ((PeerInfoable)in).getPeerInfo();
         if (peerInfo != null && peerInfo.is1221Peer()) {
            this.constrainedToPartitionName = (String)in.readObject();
         }
      }

   }

   void narrowToManagedServers(Set serverNames) {
      List currentChanges = this.getChangeDescriptors();
      if (currentChanges != null) {
         List changes = new ArrayList();
         changes.addAll(currentChanges);
         Iterator var4 = changes.iterator();

         while(true) {
            while(true) {
               ChangeDescriptor change;
               do {
                  if (!var4.hasNext()) {
                     return;
                  }

                  Object c = var4.next();
                  change = (ChangeDescriptor)ChangeDescriptor.class.cast(c);
               } while(!"non-wls".equals(change.getIdentity()));

               String changeTarget = change.getChangeTarget();
               File absoluteChangeTarget = new File(DomainDir.getPathRelativeRootDir(changeTarget));
               if (DomainDir.isFileRelativeToCAMConfigDir(absoluteChangeTarget)) {
                  this.removeChangeDescriptor(change);
               } else if (DomainDir.isFileRelativeToFMWServersConfigDir(absoluteChangeTarget)) {
                  boolean include = false;
                  if (serverNames != null) {
                     Iterator var10 = serverNames.iterator();

                     while(var10.hasNext()) {
                        String serverName = (String)var10.next();
                        include |= DomainDir.isFileRelativeToFMWServerConfigDir(absoluteChangeTarget, serverName);
                        if (include) {
                           break;
                        }
                     }
                  }

                  if (!include) {
                     this.removeChangeDescriptor(change);
                  }
               }
            }
         }
      }
   }

   public String getConstrainedToPartitionName() {
      return this.constrainedToPartitionName;
   }

   public void setConstrainedToPartitionName(String constrainedToPartitionName) {
      this.constrainedToPartitionName = constrainedToPartitionName;
   }

   public void updateDeploymentTaskStatus(int status) {
      throw new UnsupportedOperationException("updateDeploymentTaskStatus is not relevant to configuration deployment!");
   }

   public Deployment.DeploymentType getDeploymentType() {
      return DeploymentType.CONFIGURATION;
   }
}
