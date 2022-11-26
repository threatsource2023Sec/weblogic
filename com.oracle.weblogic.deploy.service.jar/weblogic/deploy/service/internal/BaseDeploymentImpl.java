package weblogic.deploy.service.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.ChangeDescriptor;
import weblogic.deploy.service.Deployment;
import weblogic.deploy.service.Version;
import weblogic.deploy.service.Deployment.DeploymentType;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public abstract class BaseDeploymentImpl implements Deployment {
   private String identity;
   private String callbackHandlerId;
   private Version proposedVersion;
   private ArrayList targets;
   private ArrayList serversToBeStarted;
   private Set partitionsToBeStarted;
   private Map partitionSystemResourcesToBeStarted = new HashMap();
   private Map serverSystemResourcesToBeStarted = new HashMap();
   private String dataTransferHandlerType;
   private ArrayList changeDescriptors;
   private String partitionName = "DOMAIN";
   private String editSessionName = "default";
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   protected BaseDeploymentImpl() {
   }

   protected BaseDeploymentImpl(String givenIdentity, String givenCallbackHandlerId, Version givenProposedVersion, List givenTargets, List givenServersToBeStarted, String givenDataTransferHandlerType, List givenChangeDescriptors) {
      this.setIdentity(givenIdentity);
      this.setCallbackHandlerId(givenCallbackHandlerId);
      this.setProposedVersion(givenProposedVersion);
      this.setTargets(givenTargets);
      this.setServersToBeRestarted(givenServersToBeStarted);
      this.setDataTransferHandlerType(givenDataTransferHandlerType);
      this.setChangeDescriptors(givenChangeDescriptors);
   }

   public final String getIdentity() {
      return this.identity;
   }

   public final void setIdentity(String given) {
      this.identity = given;
   }

   public final String getCallbackHandlerId() {
      return this.callbackHandlerId;
   }

   public final void setCallbackHandlerId(String given) {
      this.callbackHandlerId = given;
   }

   public final Version getProposedVersion() {
      return this.proposedVersion;
   }

   public final void setProposedVersion(Version given) {
      this.proposedVersion = given;
   }

   public final String[] getTargets() {
      if (this.targets == null) {
         this.targets = new ArrayList();
      }

      String[] result = new String[this.targets.size()];
      result = (String[])((String[])this.targets.toArray(result));
      return result;
   }

   public final void setTargets(List given) {
      this.targets = new ArrayList();
      if (given != null) {
         this.targets.addAll(given);
      }

   }

   public final void addTarget(String target) {
      if (this.targets == null) {
         this.targets = new ArrayList();
      }

      if (!this.targets.contains(target)) {
         this.targets.add(target);
      }

   }

   public final void removeTarget(String target) {
      if (this.targets != null) {
         this.targets.remove(target);
      }

   }

   public final String[] getServersToBeRestarted() {
      if (this.serversToBeStarted == null) {
         this.serversToBeStarted = new ArrayList();
      }

      String[] result = new String[this.serversToBeStarted.size()];
      result = (String[])((String[])this.serversToBeStarted.toArray(result));
      return result;
   }

   public final void setServersToBeRestarted(List given) {
      this.serversToBeStarted = new ArrayList();
      if (given != null) {
         this.serversToBeStarted.addAll(given);
      }

   }

   public final void addServerToBeRestarted(String server) {
      if (this.serversToBeStarted == null) {
         this.serversToBeStarted = new ArrayList();
      }

      this.serversToBeStarted.add(server);
   }

   public final void removeServerToBeRestarted(String server) {
      if (this.serversToBeStarted != null) {
         this.serversToBeStarted.remove(server);
      }

   }

   public final void removePartitionToBeRestarted(String partition) {
      if (this.partitionsToBeStarted != null) {
         this.partitionsToBeStarted.remove(partition);
      }

   }

   public final String[] getPartitionsToBeRestarted() {
      if (this.partitionsToBeStarted == null) {
         this.partitionsToBeStarted = new HashSet();
      }

      String[] result = new String[this.partitionsToBeStarted.size()];
      result = (String[])this.partitionsToBeStarted.toArray(result);
      return result;
   }

   public final void setPartitionsToBeRestarted(Collection given) {
      this.partitionsToBeStarted = new HashSet();
      if (given != null) {
         this.partitionsToBeStarted.addAll(given);
      }

   }

   public final void addPartitionsToBeRestarted(String[] partitions) {
      if (this.partitionsToBeStarted == null) {
         this.partitionsToBeStarted = new HashSet();
      }

      List partitionsAlreadyMarkedForRestart = Arrays.asList(this.getServerBean().getPendingRestartPartitions());
      if (partitions != null) {
         String[] var3 = partitions;
         int var4 = partitions.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String partition = var3[var5];
            if (!partitionsAlreadyMarkedForRestart.contains(partition)) {
               this.partitionsToBeStarted.add(partition);
            }
         }
      }

   }

   public final void addPartitionSystemResourcesToBeRestarted(String partitionName, String systemResource) {
      PartitionRuntimeMBean partitionRuntime = this.getServerBean().lookupPartitionRuntime(partitionName);
      if (partitionRuntime != null) {
         List systemResourcesAlreadyMarkedForRestart = Arrays.asList(partitionRuntime.getPendingRestartSystemResources());
         if (!systemResourcesAlreadyMarkedForRestart.contains(systemResource)) {
            if (this.partitionSystemResourcesToBeStarted == null) {
               this.partitionSystemResourcesToBeStarted = new HashMap();
            }

            this.addSystemResources(partitionName, this.partitionSystemResourcesToBeStarted, systemResource);
         }
      }

   }

   public final void addServerSystemResourcesToBeRestarted(String serverName, String systemResource) {
      ServerRuntimeMBean serverBean = this.getServerBean();
      if (serverName.equals(serverBean.getName())) {
         List serverSystemResourcesAlreadyMarkedForRestart = Arrays.asList(serverBean.getPendingRestartSystemResources());
         if (!serverSystemResourcesAlreadyMarkedForRestart.contains(systemResource)) {
            if (this.serverSystemResourcesToBeStarted == null) {
               this.serverSystemResourcesToBeStarted = new HashMap();
            }

            this.addSystemResources(serverName, this.serverSystemResourcesToBeStarted, systemResource);
         }
      }

   }

   private void addSystemResources(String resourceContainerName, Map resourcesToBeRestarted, String systemResource) {
      Set resources = (Set)resourcesToBeRestarted.get(resourceContainerName);
      if (resources == null) {
         resources = new HashSet();
         resourcesToBeRestarted.put(resourceContainerName, resources);
      }

      ((Set)resources).add(systemResource);
   }

   public final void setPartitionSystemResourcesToBeRestarted(Map given) {
      this.partitionSystemResourcesToBeStarted = new HashMap();
      if (given != null) {
         this.partitionSystemResourcesToBeStarted.putAll(given);
      }

   }

   public final void setServerSystemResourcesToBeRestarted(Map given) {
      this.serverSystemResourcesToBeStarted = new HashMap();
      if (given != null) {
         this.serverSystemResourcesToBeStarted.putAll(given);
      }

   }

   public final Collection getPartitionSystemResourcesToBeRestarted(String partitionName) {
      if (this.partitionSystemResourcesToBeStarted == null) {
         this.partitionSystemResourcesToBeStarted = new HashMap();
      }

      return this.getSystemResources(partitionName, this.partitionSystemResourcesToBeStarted);
   }

   private Collection getSystemResources(String resourceContainerName, Map resourcesToBeRestarted) {
      Set resources = (Set)resourcesToBeRestarted.get(resourceContainerName);
      if (resources == null) {
         resources = new HashSet();
         resourcesToBeRestarted.put(resourceContainerName, resources);
      }

      return Collections.unmodifiableCollection((Collection)resources);
   }

   public final Collection getServerSystemResourcesToBeRestarted(String serverName) {
      if (this.serverSystemResourcesToBeStarted == null) {
         this.serverSystemResourcesToBeStarted = new HashMap();
      }

      return this.getSystemResources(serverName, this.serverSystemResourcesToBeStarted);
   }

   private final ServerRuntimeMBean getServerBean() {
      ServerRuntimeMBean serverBean = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
      return serverBean;
   }

   public final String getDataTransferHandlerType() {
      return this.dataTransferHandlerType;
   }

   public final void setDataTransferHandlerType(String given) {
      this.dataTransferHandlerType = given;
   }

   public final List getChangeDescriptors() {
      if (this.changeDescriptors == null) {
         this.changeDescriptors = new ArrayList();
      }

      return this.changeDescriptors;
   }

   public final void setChangeDescriptors(List given) {
      this.changeDescriptors = new ArrayList();
      if (given != null) {
         this.changeDescriptors.addAll(given);
      }

   }

   public final void addChangeDescriptor(ChangeDescriptor newChange) {
      if (this.changeDescriptors == null) {
         this.changeDescriptors = new ArrayList();
      }

      this.changeDescriptors.add(newChange);
   }

   public final void removeChangeDescriptor(ChangeDescriptor newChange) {
      if (this.changeDescriptors != null) {
         this.changeDescriptors.remove(newChange);
      }

   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.identity);
      out.writeObject(this.callbackHandlerId);
      out.writeObject(this.proposedVersion);
      ArrayList targetsToBeWritten = this.targets != null && !this.targets.isEmpty() ? this.targets : null;
      out.writeObject(targetsToBeWritten);
      ArrayList restartServersToBeWritten = this.serversToBeStarted != null && !this.serversToBeStarted.isEmpty() ? this.serversToBeStarted : null;
      out.writeObject(restartServersToBeWritten);
      out.writeObject(this.dataTransferHandlerType);
      ArrayList changesToBeWritten = this.changeDescriptors != null && !this.changeDescriptors.isEmpty() ? this.changeDescriptors : null;
      out.writeObject(changesToBeWritten);
      if (out instanceof PeerInfoable) {
         PeerInfo peerInfo = ((PeerInfoable)out).getPeerInfo();
         if (peerInfo != null && peerInfo.is1221Peer()) {
            out.writeObject(this.partitionName);
            out.writeObject(this.editSessionName);
            Set restartPartitionsToBeWritten = this.partitionsToBeStarted != null && !this.partitionsToBeStarted.isEmpty() ? this.partitionsToBeStarted : null;
            out.writeObject(restartPartitionsToBeWritten);
            Map restartPartitionSystemResources = this.partitionSystemResourcesToBeStarted != null && !this.partitionSystemResourcesToBeStarted.isEmpty() ? this.partitionSystemResourcesToBeStarted : null;
            out.writeObject(restartPartitionSystemResources);
            Map restartServerSystemResources = this.serverSystemResourcesToBeStarted != null && !this.serverSystemResourcesToBeStarted.isEmpty() ? this.serverSystemResourcesToBeStarted : null;
            out.writeObject(restartServerSystemResources);
         }
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.setIdentity((String)in.readObject());
      this.setCallbackHandlerId((String)in.readObject());
      this.setProposedVersion((Version)in.readObject());
      this.setTargets((List)in.readObject());
      this.setServersToBeRestarted((List)in.readObject());
      this.setDataTransferHandlerType((String)in.readObject());
      this.setChangeDescriptors((List)in.readObject());
      if (in instanceof PeerInfoable) {
         PeerInfo peerInfo = ((PeerInfoable)in).getPeerInfo();
         if (peerInfo != null && peerInfo.is1221Peer()) {
            this.setPartitionName((String)in.readObject());
            this.setEditSessionName((String)in.readObject());
            this.setPartitionsToBeRestarted((Set)in.readObject());
            this.setPartitionSystemResourcesToBeRestarted((Map)in.readObject());
            this.setServerSystemResourcesToBeRestarted((Map)in.readObject());
         }
      }

   }

   public final String toString() {
      StringBuffer sb = new StringBuffer();
      if (!Debug.isDeploymentDebugConciseEnabled()) {
         sb.append(super.toString()).append("[");
         sb.append(this.toPrettyString()).append("]");
      } else {
         sb.append(this.toPrettyString());
      }

      return sb.toString();
   }

   protected String toPrettyString() {
      StringBuffer sb = new StringBuffer();
      sb.append("id: ");
      sb.append(this.getIdentity());
      sb.append(", callback id: ");
      sb.append(this.getCallbackHandlerId());
      String[] theTargets = this.getTargets();
      if (theTargets != null) {
         for(int i = 0; i < theTargets.length; ++i) {
            sb.append(", target[");
            sb.append(i);
            sb.append("] = ");
            sb.append(theTargets[i]);
         }
      }

      if (this.getProposedVersion() != null) {
         sb.append(", proposedVersion: ");
         sb.append(this.getProposedVersion().toString());
      }

      return sb.toString();
   }

   public String getEditSessionName() {
      return this.editSessionName;
   }

   public void setEditSessionName(String editSessionName) {
      this.editSessionName = editSessionName;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public void setPartitionName(String partitionName) {
      if (partitionName == null) {
         throw new IllegalArgumentException("BaseDeploymentImpl.setPartitionName(null)");
      } else {
         this.partitionName = partitionName;
      }
   }

   public String getConstrainedToPartitionName() {
      return null;
   }

   public void setConstrainedToPartitionName(String constraintedToPartitionName) {
      throw new IllegalArgumentException("ConstrainedToPartitionName can only be set on a configuration deployment, not on a " + this.getClass().getName());
   }

   public void updateDeploymentTaskStatus(int status) {
   }

   public Deployment.DeploymentType getDeploymentType() {
      return DeploymentType.NORMAL;
   }
}
