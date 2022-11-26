package weblogic.application.internal.flow;

import weblogic.application.ApplicationContextInternal;
import weblogic.application.internal.ApplicationRuntimeMBeanImpl;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.t3.srvr.ClassLoaderRuntime;

public final class ApplicationRuntimeMBeanFlow extends BaseFlow {
   public ApplicationRuntimeMBeanFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   private ApplicationRuntimeMBeanImpl createRuntime() throws DeploymentException {
      String name = ApplicationVersionUtils.replaceDelimiter(this.appCtx.getApplicationId(), '_');

      try {
         AppDeploymentMBean deployableMBean = this.appCtx.getAppDeploymentMBean();
         ApplicationRuntimeMBeanImpl runtime;
         if (deployableMBean != null) {
            runtime = new ApplicationRuntimeMBeanImpl(name, deployableMBean);
         } else {
            SystemResourceMBean srmb = this.appCtx.getSystemResourceMBean();
            if (srmb == null) {
               throw new AssertionError("neither app or System resource?");
            }

            runtime = new ApplicationRuntimeMBeanImpl(name, srmb);
         }

         runtime.setClassLoaderRuntime(new ClassLoaderRuntime(runtime.getName(), this.appCtx.getAppClassLoader(), runtime));
         return runtime;
      } catch (ManagementException var5) {
         throw new DeploymentException(var5);
      }
   }

   public void prepare() throws DeploymentException {
      this.appCtx.setRuntime(this.createRuntime());
   }

   public void unprepare() throws DeploymentException {
      ApplicationRuntimeMBeanImpl appRuntime = this.appCtx.getRuntime();

      try {
         appRuntime.unregister();
      } catch (Exception var3) {
         throw new DeploymentException(var3);
      }
   }
}
