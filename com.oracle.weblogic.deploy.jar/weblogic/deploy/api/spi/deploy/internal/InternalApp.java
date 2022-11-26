package weblogic.deploy.api.spi.deploy.internal;

import java.io.File;
import weblogic.Home;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.management.internal.InternalApplication;

public final class InternalApp implements InternalApplication {
   public static final String LIB;
   public static final String WAR = ".war";
   public static final String JAR = ".jar";
   public static final String EAR = ".ear";
   public static final boolean ADMIN_ONLY = true;
   public static final boolean ALL_SERVERS = false;
   public static final boolean CRITICAL = true;
   public static final boolean NON_CRITICAL = false;
   public static final boolean BACKGROUND = true;
   public static final boolean IN_STARTUP = false;
   public static final boolean DISPLAY_REFRESH = true;
   public static final boolean NO_REFRESH = false;
   public static final boolean APP = false;
   public static final boolean LIBRARY = true;
   public static final boolean MT_ENABLED = true;
   public static final boolean MT_DISABLED = false;
   public static final boolean DEPLOY_PER_VT = true;
   public static final boolean DEPLOY_PER_PARTITION = false;
   private final String name;
   private final String suffix;
   private final boolean adminOnly;
   private final boolean critical;
   private final String location;
   private final boolean isLib;
   public boolean isBackground;
   private String[] onDemandContextPaths;
   private boolean onDemandDisplayRefresh;
   private DeploymentPlanBean deploymentPlanBean;
   private boolean clustered;
   private String sourceFileName;
   private final boolean isMtEnabled;
   private OptionalFeatureName optionalFeatureName;
   private boolean optionalFeatureEnabled;
   private String virtualHostName;
   private final boolean isDeployPerVT;

   public InternalApp(String name, String suffix, boolean adminOnly, boolean critical) {
      this(name, suffix, adminOnly, critical, false, false);
   }

   public InternalApp(String name, String suffix, boolean adminOnly, boolean critical, boolean isBackground) {
      this(name, suffix, adminOnly, critical, false, isBackground);
   }

   public InternalApp(String name, String suffix, boolean adminOnly, boolean critical, boolean isLib, boolean isBackground) {
      this(name, suffix, adminOnly, critical, isLib, isBackground, (String[])null, false);
   }

   public InternalApp(String name, String suffix, boolean adminOnly, boolean critical, boolean isLib, boolean isBackground, String[] onDemandContextPaths, boolean onDemandDisplayRefresh) {
      this(name, suffix, adminOnly, critical, isLib, isBackground, onDemandContextPaths, onDemandDisplayRefresh, LIB);
   }

   public InternalApp(String name, String suffix, boolean adminOnly, boolean critical, boolean isLib, boolean isBackground, String[] onDemandContextPaths, boolean onDemandDisplayRefresh, String location) {
      this(name, suffix, adminOnly, critical, isLib, isBackground, onDemandContextPaths, onDemandDisplayRefresh, location, false);
   }

   public InternalApp(String name, String suffix, boolean adminOnly, boolean critical, boolean isLib, boolean isBackground, String[] onDemandContextPaths, boolean onDemandDisplayRefresh, String location, boolean mtEnabled) {
      this(name, suffix, adminOnly, critical, isLib, isBackground, onDemandContextPaths, onDemandDisplayRefresh, location, mtEnabled, true);
   }

   public InternalApp(String name, String suffix, boolean adminOnly, boolean critical, boolean isLib, boolean isBackground, String[] onDemandContextPaths, boolean onDemandDisplayRefresh, String location, boolean mtEnabled, boolean deployPerVT) {
      this.sourceFileName = null;
      this.optionalFeatureName = null;
      this.optionalFeatureEnabled = false;
      this.virtualHostName = null;
      this.name = name;
      this.suffix = suffix;
      this.adminOnly = adminOnly;
      this.critical = critical;
      this.location = location;
      this.isLib = isLib;
      this.isBackground = isBackground;
      this.onDemandContextPaths = onDemandContextPaths;
      this.onDemandDisplayRefresh = onDemandDisplayRefresh;
      this.isMtEnabled = mtEnabled;
      this.isDeployPerVT = deployPerVT;
   }

   public DeploymentPlanBean getDeploymentPlanBean() {
      return this.deploymentPlanBean;
   }

   public void setDeploymentPlanBean(DeploymentPlanBean deploymentPlanBean) {
      this.deploymentPlanBean = deploymentPlanBean;
   }

   public boolean isClustered() {
      return this.clustered;
   }

   public void setClustered(boolean clustered) {
      this.clustered = clustered;
   }

   public String getSourceFileName() {
      return this.sourceFileName;
   }

   public void setSourceFileName(String sourceFileName) {
      this.sourceFileName = sourceFileName;
   }

   public String getName() {
      return this.name;
   }

   public String getSuffix() {
      return this.suffix;
   }

   public boolean isLib() {
      return this.isLib;
   }

   public boolean isBackground() {
      return this.isBackground;
   }

   public String[] getOnDemandContextPaths() {
      return this.onDemandContextPaths;
   }

   public boolean isOnDemandDisplayRefresh() {
      return this.onDemandDisplayRefresh;
   }

   public boolean isCritical() {
      return this.critical;
   }

   public boolean isAdminOnly() {
      return this.adminOnly;
   }

   public String getLocation() {
      return this.location;
   }

   public boolean isMtEnabled() {
      return this.isMtEnabled;
   }

   public boolean isDeployPerVT() {
      return this.isDeployPerVT;
   }

   public void relatesToOptionalFeature(OptionalFeatureName ofn, boolean enabled) {
      this.optionalFeatureName = ofn;
      this.optionalFeatureEnabled = enabled;
   }

   public String getOptionalFeatureName() {
      return this.optionalFeatureName == null ? null : this.optionalFeatureName.getOptionalFeatureName();
   }

   public boolean isOptionalFeatureEnabled() {
      return this.optionalFeatureEnabled;
   }

   public String getVirtualHostName() {
      return this.virtualHostName;
   }

   public void setVirtualHostName(String virtualHostName) {
      this.virtualHostName = virtualHostName;
   }

   static {
      LIB = Home.getPath() + File.separator + "lib";
   }

   public static enum OptionalFeatureName {
      READYAPP("READYAPP"),
      WSAT("WSAT"),
      JAXRPC_ASYNC_RESPONSE("JAXRPC_ASYNC_RESPONSE");

      private final String optionalFeatureName;

      private OptionalFeatureName(String optionalFeatureName) {
         this.optionalFeatureName = optionalFeatureName;
      }

      public String getOptionalFeatureName() {
         return this.optionalFeatureName;
      }

      public static boolean validateName(String name) {
         if (name == null) {
            return false;
         } else {
            OptionalFeatureName[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
               OptionalFeatureName ofn = var1[var3];
               if (name.equals(ofn.optionalFeatureName)) {
                  return true;
               }
            }

            return false;
         }
      }
   }
}
