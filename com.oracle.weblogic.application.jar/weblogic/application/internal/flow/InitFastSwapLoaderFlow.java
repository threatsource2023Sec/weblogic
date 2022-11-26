package weblogic.application.internal.flow;

import com.bea.wls.redef.ClassRedefInitializationException;
import com.bea.wls.redef.ClassRedefinerFactory;
import com.bea.wls.redef.RedefiningClassLoader;
import com.bea.wls.redef.runtime.ClassRedefinitionRuntimeImpl;
import com.bea.wls.redef.runtime.ClassRedefinitionRuntimeMBean;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.internal.ApplicationRuntimeMBeanImpl;
import weblogic.application.utils.ManagementUtils;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.wl.FastSwapBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.utils.classloaders.GenericClassLoader;

public class InitFastSwapLoaderFlow extends BaseFlow {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugClassRedef");

   public InitFastSwapLoaderFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void prepare() throws DeploymentException {
      this.initLoader(this.appCtx.getWLApplicationDD());
   }

   public void initLoader(WeblogicApplicationBean wlBean) throws DeploymentException {
      if (!ManagementUtils.isProductionModeEnabled()) {
         if (wlBean != null) {
            FastSwapBean bean = wlBean.getFastSwap();
            if (bean != null) {
               boolean enabled = bean.isEnabled();
               int secs = bean.getRefreshInterval();
               if (logger.isDebugEnabled()) {
                  logger.debug(" Enabled : " + enabled + " Reload : " + secs);
               }

               if (enabled) {
                  GenericClassLoader loader = this.appCtx.getAppClassLoader();

                  try {
                     GenericClassLoader redefLoader = ClassRedefinerFactory.makeClassLoader(loader.getClassFinder(), loader.getParent());
                     redefLoader.setAnnotation(loader.getAnnotation());
                     ((RedefiningClassLoader)redefLoader).getRedefinitionRuntime().setRedefinitionTaskLimit(bean.getRedefinitionTaskLimit());
                     this.createRuntime(redefLoader);
                     if (logger.isDebugEnabled()) {
                        logger.debug(" Appclassloader reset to " + redefLoader);
                     }

                     this.appCtx.resetAppClassLoader(redefLoader);
                  } catch (ClassRedefInitializationException var7) {
                     throw new DeploymentException(var7);
                  }
               }

            }
         }
      }
   }

   private void createRuntime(GenericClassLoader loader) throws ClassRedefInitializationException {
      try {
         ApplicationRuntimeMBeanImpl appRuntime = this.appCtx.getRuntime();
         ClassRedefinitionRuntimeMBean rmb = new ClassRedefinitionRuntimeImpl(appRuntime, loader);
         appRuntime.setClassRedefinitionRuntime(rmb);
      } catch (ManagementException var4) {
         throw new ClassRedefInitializationException(var4.getMessage(), var4);
      } catch (ClassCastException var5) {
         throw new ClassRedefInitializationException(var5.getMessage(), var5);
      }
   }
}
