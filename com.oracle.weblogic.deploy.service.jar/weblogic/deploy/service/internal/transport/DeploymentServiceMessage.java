package weblogic.deploy.service.internal.transport;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.deploy.service.Deployment;
import weblogic.deploy.service.internal.DeploymentService;
import weblogic.deploy.service.internal.DomainVersion;
import weblogic.deploy.service.internal.adminserver.AdminRequestImpl;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.utils.ArrayUtils;

public final class DeploymentServiceMessage implements Externalizable, Cloneable {
   private static final long serialVersionUID = -566288665135473593L;
   public static final byte HEARTBEAT = 0;
   public static final byte REQUEST_PREPARE = 1;
   public static final byte REQUEST_COMMIT = 2;
   public static final byte REQUEST_CANCEL = 3;
   public static final byte GET_DEPLOYMENTS = 4;
   public static final byte GET_DEPLOYMENTS_RESPONSE = 5;
   public static final byte PREPARE_ACK = 6;
   public static final byte PREPARE_NAK = 7;
   public static final byte COMMIT_SUCCEEDED = 8;
   public static final byte COMMIT_FAILED = 9;
   public static final byte CANCEL_SUCCEEDED = 10;
   public static final byte CANCEL_FAILED = 11;
   public static final byte REQUEST_STATUS = 12;
   public static final byte BLOCKING_GET_DEPLOYMENTS = 13;
   public static final byte CONFIG_PREPARE_ACK = 14;
   public static final byte CONFIG_PREPARE_NAK = 15;
   public static final byte REQUEST_APP_STATE = 16;
   public static final byte TRANSMIT_APP_STATE = 17;
   public static final byte REQUEST_MULTIVERSION_STATE = 18;
   public static final byte TRANSMIT_MULTIVERSION_STATE = 19;
   public static final byte GET_DEPLOYMENTS_FOR_PARTITION = 20;
   private byte deploymentServiceVersion;
   private final byte currentDeploymentServiceVersion;
   private byte messageType;
   private long deploymentId;
   private long timeoutValue = -1L;
   private boolean callConfigurationProviderLast;
   private String messageSrc;
   private transient Set targets = null;
   private ArrayList items = null;
   private DomainVersion fromVersion = null;
   private DomainVersion toVersion = null;
   private AuthenticatedSubject initiator = null;
   private String deploymentType = null;
   private boolean needsVersionUpdate = true;
   private String partitionName = null;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public DeploymentServiceMessage() {
      DeploymentService.getDeploymentService();
      this.currentDeploymentServiceVersion = DeploymentService.getVersionByte();
   }

   public DeploymentServiceMessage(byte deploymentServiceVersion, byte messageType, AdminRequestImpl request, String targetServer) {
      DeploymentService.getDeploymentService();
      this.currentDeploymentServiceVersion = DeploymentService.getVersionByte();
      this.deploymentServiceVersion = deploymentServiceVersion;
      this.messageType = messageType;
      this.messageSrc = ManagementService.getRuntimeAccess(kernelId).getServerName();
      if (request != null) {
         this.deploymentId = request.getId();
         this.timeoutValue = request.getTimeoutInterval();
         this.callConfigurationProviderLast = request.isConfigurationProviderCalledLast();
         this.initiator = request.getInitiator();
         this.targets = new HashSet();
         Iterator iterator = request.getTargets();
         if (this.targets != null) {
            while(iterator.hasNext()) {
               this.targets.add((String)iterator.next());
            }
         }

         if (this.items == null) {
            this.items = new ArrayList();
         }

         Iterator deployments = request.getDeployments();

         while(deployments.hasNext()) {
            Deployment deployment = (Deployment)deployments.next();
            String[] servers = deployment.getTargets();
            boolean addDeployment = false;
            if (!"Configuration".equals(deployment.getCallbackHandlerId()) && !"Application".equals(deployment.getCallbackHandlerId())) {
               if (targetServer == null || servers == null || servers.length == 0 || ArrayUtils.contains(servers, targetServer)) {
                  addDeployment = true;
               }
            } else {
               addDeployment = true;
            }

            if (addDeployment) {
               this.items.add(deployment);
            }
         }

         this.needsVersionUpdate = !request.isControlRequest();
      }

   }

   public DeploymentServiceMessage(byte deploymentServiceVersion, byte messageType, long deploymentId, ArrayList items) {
      DeploymentService.getDeploymentService();
      this.currentDeploymentServiceVersion = DeploymentService.getVersionByte();
      this.deploymentServiceVersion = deploymentServiceVersion;
      this.messageType = messageType;
      this.deploymentId = deploymentId;
      this.items = items;
      if (ManagementService.getPropertyService(kernelId).serverNameIsSet()) {
         this.messageSrc = ManagementService.getPropertyService(kernelId).getServerName();
      } else {
         this.messageSrc = null;
      }

   }

   public final long getDeploymentId() {
      return this.deploymentId;
   }

   public final long getTimeoutInterval() {
      return this.timeoutValue;
   }

   public final boolean isConfigurationProviderCalledLast() {
      return this.callConfigurationProviderLast;
   }

   private void setTargets(Set targets) {
      this.targets = targets;
   }

   public final Set getTargets() {
      return this.targets;
   }

   public final byte getVersion() {
      return this.deploymentServiceVersion;
   }

   public final byte getMessageType() {
      return this.messageType;
   }

   public final ArrayList getItems() {
      return this.items;
   }

   public final String getMessageSrc() {
      return this.messageSrc;
   }

   public final AuthenticatedSubject getInitiator() {
      return this.initiator;
   }

   private static String getMessageTypeString(byte messageType) {
      switch (messageType) {
         case 0:
            return "HEARTBEAT";
         case 1:
            return "REQUEST_PREPARE";
         case 2:
            return "REQUEST_COMMIT";
         case 3:
            return "REQUEST_CANCEL";
         case 4:
            return "GET_DEPLOYMENTS";
         case 5:
            return "GET_DEPLOYMENTS_RESPONSE";
         case 6:
            return "PREPARE_ACK";
         case 7:
            return "PREPARE_NAK";
         case 8:
            return "COMMIT_SUCCEEDED";
         case 9:
            return "COMMIT_FAILED";
         case 10:
            return "CANCEL_SUCCEEDED";
         case 11:
            return "CANCEL_FAILED";
         case 12:
            return "REQUEST_STATUS";
         case 13:
            return "BLOCKING_GET_DEPLOYMENTS";
         case 14:
            return "CONFIG_PREPARE_ACK";
         case 15:
            return "CONFIG_PREPARE_NAK";
         case 16:
            return "REQUEST_APP_STATE";
         case 17:
            return "TRANSMIT_APP_STATE";
         case 18:
         case 19:
         default:
            return "ILLEGAL";
         case 20:
            return "GET_DEPLOYMENTS_FOR_PARTITION";
      }
   }

   public final Object clone() {
      DeploymentServiceMessage clonedMessage = new DeploymentServiceMessage(this.deploymentServiceVersion, this.messageType, this.deploymentId, (ArrayList)this.items.clone());
      clonedMessage.setTargets(this.targets);
      return clonedMessage;
   }

   public final void setFromVersion(DomainVersion version) {
      this.fromVersion = version;
   }

   public final DomainVersion getFromVersion() {
      return this.fromVersion;
   }

   public final void setToVersion(DomainVersion version) {
      this.toVersion = version;
   }

   public final DomainVersion getToVersion() {
      return this.toVersion;
   }

   public final boolean needsVersionUpdate() {
      return this.needsVersionUpdate;
   }

   public final void writeExternal(ObjectOutput oo) throws IOException {
      oo.writeByte(this.deploymentServiceVersion);
      this.writeMessage(this.deploymentServiceVersion, oo);
   }

   private void writeMessage(byte deploymentServiceVersion, ObjectOutput oo) throws IOException {
      if (deploymentServiceVersion == this.currentDeploymentServiceVersion) {
         oo.writeByte(this.messageType);
         oo.writeLong(this.deploymentId);
         oo.writeLong(this.timeoutValue);
         oo.writeBoolean(this.callConfigurationProviderLast);
         ((WLObjectOutput)oo).writeString(this.messageSrc);
         boolean itemsIncluded = this.items != null;
         oo.writeBoolean(itemsIncluded);
         if (itemsIncluded) {
            ((WLObjectOutput)oo).writeArrayList(this.items);
         }

         boolean fromVersionIncluded = this.fromVersion != null;
         oo.writeBoolean(fromVersionIncluded);
         if (fromVersionIncluded) {
            oo.writeObject(this.fromVersion);
         }

         boolean toVersionIncluded = this.toVersion != null;
         oo.writeBoolean(toVersionIncluded);
         if (toVersionIncluded) {
            oo.writeObject(this.toVersion);
         }

         boolean initiatorIncluded = this.initiator != null;
         oo.writeBoolean(initiatorIncluded);
         if (initiatorIncluded) {
            oo.writeObject(SecurityServiceManager.sendASToWire(this.initiator));
         }

         boolean deploymentTypeIncluded = this.deploymentType != null;
         oo.writeBoolean(deploymentTypeIncluded);
         if (deploymentTypeIncluded) {
            oo.writeObject(this.deploymentType);
         }

         oo.writeBoolean(this.needsVersionUpdate);
         boolean partitionNameIncluded = this.partitionName != null;
         oo.writeBoolean(partitionNameIncluded);
         if (partitionNameIncluded) {
            oo.writeObject(this.partitionName);
         }
      }

   }

   public final void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
      this.deploymentServiceVersion = oi.readByte();
      this.readMessage(this.deploymentServiceVersion, oi);
   }

   private void readMessage(byte deploymentServiceVersion, ObjectInput oi) throws IOException, ClassNotFoundException {
      if (deploymentServiceVersion == this.currentDeploymentServiceVersion) {
         this.messageType = oi.readByte();
         this.deploymentId = oi.readLong();
         this.timeoutValue = oi.readLong();
         this.callConfigurationProviderLast = oi.readBoolean();
         this.messageSrc = ((WLObjectInput)oi).readString();
         boolean itemsIncluded = oi.readBoolean();
         if (itemsIncluded) {
            this.items = ((WLObjectInput)oi).readArrayList();
         }

         boolean fromVersionIncluded = oi.readBoolean();
         if (fromVersionIncluded) {
            this.fromVersion = (DomainVersion)oi.readObject();
         }

         boolean toVersionIncluded = oi.readBoolean();
         if (toVersionIncluded) {
            this.toVersion = (DomainVersion)oi.readObject();
         }

         boolean initiatorIncluded = oi.readBoolean();
         if (initiatorIncluded) {
            this.initiator = SecurityServiceManager.getASFromWire((AuthenticatedSubject)oi.readObject());
         }

         boolean deploymentTypeIncluded = oi.readBoolean();
         if (deploymentTypeIncluded) {
            this.setDeploymentType((String)oi.readObject());
         }

         this.needsVersionUpdate = oi.readBoolean();
         boolean partitionNameIncluded = oi.readBoolean();
         if (partitionNameIncluded) {
            this.setPartitionName((String)oi.readObject());
         }
      }

   }

   public final String toString() {
      StringBuffer stringBuffer = new StringBuffer();
      stringBuffer.append("DeploymentService message ");
      stringBuffer.append(getMessageTypeString(this.messageType));
      stringBuffer.append(" and deployment id: ");
      stringBuffer.append(this.deploymentId);
      stringBuffer.append(" from : ");
      stringBuffer.append(this.messageSrc);
      stringBuffer.append(" with : ");
      if (this.items != null && !this.items.isEmpty()) {
         Iterator iterator = this.items.iterator();
         stringBuffer.append(this.items.size());
         stringBuffer.append(" item(s) : ");

         while(iterator.hasNext()) {
            Object item = iterator.next();
            if (item != null) {
               stringBuffer.append("[");
               stringBuffer.append(item.toString());
               stringBuffer.append("] ");
            }
         }
      } else {
         stringBuffer.append(0);
         stringBuffer.append(" items ");
      }

      if (this.fromVersion != null) {
         stringBuffer.append(" fromVersion: ");
         stringBuffer.append(this.fromVersion.toString());
      }

      if (this.toVersion != null) {
         stringBuffer.append(" toVersion: ");
         stringBuffer.append(this.toVersion.toString());
      }

      return stringBuffer.toString();
   }

   public final String getDeploymentType() {
      return this.deploymentType;
   }

   public final void setDeploymentType(String given) {
      if (this.messageType != 13 && this.messageType != 20) {
         throw new IllegalArgumentException("setDeploymentType is invalid for this message type " + this.messageType);
      } else {
         this.deploymentType = given;
      }
   }

   public final String getPartitionName() {
      return this.partitionName;
   }

   public final void setPartitionName(String partitionName) {
      if (this.messageType != 20) {
         throw new IllegalArgumentException("setPartitionName is invalid for message type " + this.messageType);
      } else {
         this.partitionName = partitionName;
      }
   }
}
