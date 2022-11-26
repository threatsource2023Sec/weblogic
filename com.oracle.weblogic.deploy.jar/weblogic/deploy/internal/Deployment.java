package weblogic.deploy.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import weblogic.deploy.common.Debug;
import weblogic.deploy.internal.adminserver.EditAccessHelper;
import weblogic.deploy.service.Version;
import weblogic.deploy.service.internal.BaseDeploymentImpl;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.deploy.DeploymentTaskRuntime;
import weblogic.security.acl.internal.AuthenticatedSubject;

public final class Deployment extends BaseDeploymentImpl {
   private static final long serialVersionUID = 6457624201160781380L;
   private InternalDeploymentData internalDeploymentData;
   private String taskRuntimeId;
   private boolean isBeforeDeploymentHandler;
   private boolean isDeploy;
   private boolean requiresRestart;
   private boolean isSyncWithAdmin;
   private Map syncWithAdminState = new HashMap();
   private DeploymentVersion proposedDeploymentVersion;
   private long deploymentRequestId;
   private transient DeploymentTaskRuntime taskRuntime;
   private transient AuthenticatedSubject initiator;
   private transient boolean isCallerLockOwner;
   private transient boolean isStaged;
   private transient boolean isAControlOperation;
   private transient boolean isAnAppDeployment;
   private transient int operation;
   private transient EditAccessHelper editAccessHelper;

   public Deployment() {
   }

   public Deployment(String callbackHandlerId, DeploymentTaskRuntime taskRuntime, DeploymentData data, AuthenticatedSubject initiator, boolean callerIsLockOwner, boolean isAControlOperation, boolean restartRequired) {
      super(taskRuntime != null ? taskRuntime.getDeploymentMBean().getName() : null, callbackHandlerId, (Version)null, (List)null, (List)null, (String)null, (List)null);
      this.taskRuntime = taskRuntime;
      this.internalDeploymentData = new InternalDeploymentData();
      this.internalDeploymentData.setExternalDeploymentData(data);
      if (taskRuntime != null) {
         this.internalDeploymentData.setNotificationLevel(taskRuntime.getNotificationLevel());
         this.internalDeploymentData.setDeploymentOperation(taskRuntime.getTask());
         this.internalDeploymentData.setDeploymentName(taskRuntime.getApplicationId());
         this.taskRuntimeId = taskRuntime.getId();
         this.operation = taskRuntime.getTask();
         if (taskRuntime.getAppDeploymentMBean() != null) {
            this.isAnAppDeployment = true;
         }
      }

      this.initiator = initiator;
      this.isCallerLockOwner = callerIsLockOwner;
      this.isAControlOperation = isAControlOperation;
      this.requiresRestart = restartRequired;
   }

   public void setNotificationLevel(int notifLevel) {
      this.internalDeploymentData.setNotificationLevel(notifLevel);
   }

   public final int getOperation() {
      return this.operation;
   }

   public final void setOperation(int op) {
      this.operation = op;
   }

   public final DeploymentVersion getProposedDeploymentVersion() {
      return this.proposedDeploymentVersion;
   }

   public final void setProposedDeploymentVersion(DeploymentVersion version) {
      this.proposedDeploymentVersion = version;
   }

   public final boolean isCallerLockOwner() {
      return this.isCallerLockOwner;
   }

   public final void setCallerLockOwner(boolean isOwner) {
      this.isCallerLockOwner = isOwner;
   }

   public final AuthenticatedSubject getInitiator() {
      return this.initiator;
   }

   public final boolean isAControlOperation() {
      return this.isAControlOperation;
   }

   public final boolean requiresRestart() {
      return this.requiresRestart;
   }

   public final DeploymentTaskRuntime getDeploymentTaskRuntime() {
      return this.taskRuntime;
   }

   public final String getDeploymentTaskRuntimeId() {
      return this.taskRuntimeId;
   }

   public final boolean isBeforeDeploymentHandler() {
      return this.isBeforeDeploymentHandler;
   }

   public final void setBeforeDeploymentHandler() {
      this.isBeforeDeploymentHandler = true;
   }

   public final boolean isDeploy() {
      return this.isDeploy;
   }

   public final void setIsDeploy() {
      this.isDeploy = true;
   }

   public final InternalDeploymentData getInternalDeploymentData() {
      return this.internalDeploymentData;
   }

   public final void setInternalDeploymentData(InternalDeploymentData newData) {
      this.internalDeploymentData = newData;
   }

   public final void setDeploymentRequestIdentifier(long id) {
      this.deploymentRequestId = id;
   }

   public final long getDeploymentRequestId() {
      return this.deploymentRequestId;
   }

   public final boolean isAnAppDeployment() {
      return this.isAnAppDeployment;
   }

   public final void setStaged(String stagingMode) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("Setting staged mode of '" + this.getIdentity() + "' to '" + stagingMode + "'");
      }

      if (stagingMode == null || "stage".equals(stagingMode)) {
         this.isStaged = true;
      }

   }

   public final boolean isStaged() {
      return this.isStaged;
   }

   public final boolean isSyncWithAdmin() {
      return this.isSyncWithAdmin;
   }

   public final void enableSyncWithAdmin(Map syncState) {
      this.isSyncWithAdmin = true;
      this.syncWithAdminState = syncState;
   }

   public final Map getSyncWithAdminState() {
      return this.syncWithAdminState;
   }

   protected String toPrettyString() {
      StringBuffer sb = new StringBuffer();
      sb.append(super.toPrettyString());
      sb.append(", isBeforeDeploymentHandler: ");
      sb.append(this.isBeforeDeploymentHandler);
      return sb.toString();
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      super.writeExternal(out);
      out.writeObject(this.internalDeploymentData);
      out.writeObject(this.taskRuntimeId);
      out.writeBoolean(this.isBeforeDeploymentHandler);
      out.writeBoolean(this.isDeploy);
      out.writeBoolean(this.requiresRestart);
      out.writeBoolean(this.isSyncWithAdmin);
      out.writeObject(this.syncWithAdminState);
      out.writeObject(this.proposedDeploymentVersion);
      out.writeLong(this.deploymentRequestId);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
      this.internalDeploymentData = (InternalDeploymentData)in.readObject();
      this.taskRuntimeId = (String)in.readObject();
      this.isBeforeDeploymentHandler = in.readBoolean();
      this.isDeploy = in.readBoolean();
      this.requiresRestart = in.readBoolean();
      this.isSyncWithAdmin = in.readBoolean();
      this.syncWithAdminState = (Map)in.readObject();
      this.proposedDeploymentVersion = (DeploymentVersion)in.readObject();
      this.deploymentRequestId = in.readLong();
   }

   public EditAccessHelper getEditAccessHelper() {
      return this.editAccessHelper;
   }

   public void setEditAccessHelper(EditAccessHelper editAccessHelper) {
      this.editAccessHelper = editAccessHelper;
      this.resetEditSessionInfo();
   }

   private void resetEditSessionInfo() {
      if (this.editAccessHelper == null) {
         this.setEditSessionName("default");
         this.setPartitionName("DOMAIN");
      } else {
         this.setEditSessionName(this.editAccessHelper.getEditSessionName());
         this.setPartitionName(this.editAccessHelper.getPartitionName());
      }

   }

   public void updateDeploymentTaskStatus(int status) {
      this.taskRuntime.setState(status);
   }
}
