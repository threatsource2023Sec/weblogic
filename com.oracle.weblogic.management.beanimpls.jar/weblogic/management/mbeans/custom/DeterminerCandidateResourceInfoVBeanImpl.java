package weblogic.management.mbeans.custom;

import weblogic.management.configuration.DeterminerCandidateResourceInfoVBean;

public class DeterminerCandidateResourceInfoVBeanImpl implements DeterminerCandidateResourceInfoVBean {
   private boolean isDeterminer;
   private String displayName;
   private String internalName;
   private String resourceType;

   public DeterminerCandidateResourceInfoVBeanImpl() {
   }

   public DeterminerCandidateResourceInfoVBeanImpl(String displayName, String internalName, String resourceType, boolean isDeterminer) {
      this.displayName = displayName;
      this.internalName = internalName;
      this.resourceType = resourceType;
      this.isDeterminer = isDeterminer;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public void setDisplayName(String displayName) {
      this.displayName = displayName;
   }

   public String getInternalName() {
      return this.internalName;
   }

   public void setInternalName(String internalName) {
      this.internalName = internalName;
   }

   public String getResourceType() {
      return this.resourceType;
   }

   public void setResourceType(String resourceType) {
      this.resourceType = resourceType;
   }

   public boolean isDeterminer() {
      return this.isDeterminer;
   }

   public void setIsDeterminer(boolean isDeterminer) {
      this.isDeterminer = isDeterminer;
   }
}
