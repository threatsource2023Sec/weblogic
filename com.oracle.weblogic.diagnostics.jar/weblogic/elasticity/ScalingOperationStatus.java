package weblogic.elasticity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Properties;

public class ScalingOperationStatus implements Serializable {
   private boolean isScaleUp;
   private String clusterName;
   private int clusterSize;
   private int clusterMinSize;
   private int clusterMaxSize;
   private int requestedScalingSize;
   private int allowedScalingSize;
   private int additionalServersNeeded;
   private ArrayList candidateMemberNames = new ArrayList();
   private ArrayList scaledMemberNames = new ArrayList();
   private Properties instanceMetadata = new Properties();
   private boolean completed;
   private boolean success;

   public ScalingOperationStatus(boolean isScaleUp, String clusterName, int requestedScalingSize, int clusterSize, int clusterMinSize, int clusterMaxSize) {
      this.isScaleUp = isScaleUp;
      this.clusterName = clusterName;
      this.requestedScalingSize = requestedScalingSize;
      this.clusterSize = clusterSize;
      this.clusterMinSize = clusterMinSize;
      this.clusterMaxSize = clusterMaxSize;
   }

   public boolean isScaleUp() {
      return this.isScaleUp;
   }

   public String getClusterName() {
      return this.clusterName;
   }

   public int getClusterSize() {
      return this.clusterSize;
   }

   public int getClusterMinSize() {
      return this.clusterMinSize;
   }

   public int getClusterMaxSize() {
      return this.clusterMaxSize;
   }

   public int getRequestedScalingSize() {
      return this.requestedScalingSize;
   }

   public int getAllowedScalingSize() {
      return this.allowedScalingSize;
   }

   public void setAllowedScalingSize(int allowedScalingSize) {
      this.allowedScalingSize = allowedScalingSize;
   }

   public int getAdditionalServersNeeded() {
      return this.additionalServersNeeded;
   }

   public void setAdditionalServersNeeded(int additionalServersNeeded) {
      this.additionalServersNeeded = additionalServersNeeded;
   }

   public ArrayList getCandidateMemberNames() {
      return this.candidateMemberNames;
   }

   public void setCandidateMemberNames(ArrayList candidateMemberNames) {
      this.candidateMemberNames = candidateMemberNames;
   }

   public ArrayList getScaledMemberNames() {
      return this.scaledMemberNames;
   }

   public void setScaledMemberNames(ArrayList scaledMemberNames) {
      this.scaledMemberNames = scaledMemberNames;
   }

   public Properties getInstanceMetadata() {
      return this.instanceMetadata;
   }

   public void setInstanceMetadata(Properties instanceMetadata) {
      this.instanceMetadata = instanceMetadata;
   }

   public boolean isCompleted() {
      return this.completed;
   }

   public void setCompleted(boolean completed) {
      this.completed = completed;
   }

   public boolean isSuccess() {
      return this.success;
   }

   public void setSuccess(boolean success) {
      this.success = success;
   }

   public String toString() {
      return "ScalingOperationStatus{isScaleUp=" + this.isScaleUp + ", clusterName='" + this.clusterName + '\'' + ", clusterMinSize=" + this.clusterMinSize + ", clusterMaxSize=" + this.clusterMaxSize + ", requestedScalingSize=" + this.requestedScalingSize + ", allowedScalingSize=" + this.allowedScalingSize + ", additionalServersNeeded=" + this.additionalServersNeeded + ", candidateMemberNames=" + this.candidateMemberNames + ", scaledMemberNames=" + this.scaledMemberNames + ", completed=" + this.completed + ", success=" + this.success + '}';
   }
}
