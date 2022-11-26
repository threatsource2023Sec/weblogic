package weblogic.management.provider.internal;

public interface InternalApplication {
   String getLocation();

   boolean isClustered();

   String getSourceFileName();

   String getName();

   String getSuffix();

   boolean isLib();

   boolean isBackground();

   String[] getOnDemandContextPaths();

   boolean isOnDemandDisplayRefresh();

   boolean isCritical();

   boolean isAdminOnly();

   boolean isMtEnabled();

   String getOptionalFeatureName();

   boolean isOptionalFeatureEnabled();
}
