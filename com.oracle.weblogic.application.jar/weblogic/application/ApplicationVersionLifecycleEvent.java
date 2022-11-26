package weblogic.application;

import weblogic.application.utils.ApplicationVersionUtils;

public class ApplicationVersionLifecycleEvent {
   private final String ownAppId;
   private final String appId;
   private final boolean isActiveVersion;
   private final boolean isOwnVersionActive;

   public ApplicationVersionLifecycleEvent(String ownAppId, boolean isOwnVersionActive, String appId, boolean isActiveVersion) {
      this.ownAppId = ownAppId;
      this.appId = appId;
      this.isOwnVersionActive = isOwnVersionActive;
      this.isActiveVersion = isActiveVersion;
   }

   public String getApplicationId() {
      return this.appId;
   }

   public String getApplicationName() {
      return ApplicationVersionUtils.getApplicationName(this.appId);
   }

   public String getVersionId() {
      return ApplicationVersionUtils.getVersionId(this.appId);
   }

   public String getArchiveVersion() {
      return ApplicationVersionUtils.getArchiveVersion(this.getVersionId());
   }

   public String getPlanVersion() {
      return ApplicationVersionUtils.getPlanVersion(this.getVersionId());
   }

   public String getLibSpecVersion() {
      return ApplicationVersionUtils.getLibSpecVersion(this.getArchiveVersion());
   }

   public String getLibImplVersion() {
      return ApplicationVersionUtils.getLibImplVersion(this.getArchiveVersion());
   }

   public boolean isAdminMode() {
      return ApplicationVersionUtils.getAdminModeAppCtxParam(ApplicationAccess.getApplicationAccess().getCurrentApplicationContext());
   }

   public boolean isActiveVersion() {
      return this.isActiveVersion;
   }

   public boolean isOwnVersion() {
      return this.appId != null && this.ownAppId.equals(this.appId);
   }

   public boolean isOwnVersionActive() {
      return this.isOwnVersionActive;
   }

   public String toString() {
      return "ApplicationVersionLifecycleEvent(" + ApplicationVersionUtils.getDisplayName(this.appId) + ",isAdmin=" + this.isAdminMode() + ",isActive=" + this.isActiveVersion() + ",isOwnVersion=" + this.isOwnVersion() + ",isOwnActive=" + this.isOwnVersionActive() + ")";
   }
}
