package weblogic.application.internal.flow;

import java.io.File;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.utils.ManagementUtils;
import weblogic.application.utils.PathUtils;
import weblogic.utils.FileUtils;

public final class DescriptorCacheDirFlow extends BaseFlow {
   public DescriptorCacheDirFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void prepare() {
      String appSourceDir = null;
      if (this.appCtx.getAppDeploymentMBean() != null && this.appCtx.getAppDeploymentMBean().isCacheInAppDirectory()) {
         appSourceDir = (new File(this.appCtx.getAppDeploymentMBean().getSourcePath())).getParent();
      }

      this.appCtx.setDescriptorCacheDir(PathUtils.generateDescriptorCacheDir(ManagementUtils.getServerName(), this.appCtx.getApplicationId(), this.appCtx.isInternalApp(), appSourceDir));
   }

   public void remove() {
      File dir = this.appCtx.getDescriptorCacheDir();
      if (dir != null) {
         FileUtils.remove(dir);
      }

   }
}
