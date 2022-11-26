package weblogic.application.internal.flow;

import java.util.Map;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.MergedDescriptorModule;
import weblogic.application.Module;
import weblogic.application.io.MergedDescriptorFinder;
import weblogic.management.DeploymentException;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;

public class DescriptorFinderFlow extends BaseFlow {
   public DescriptorFinderFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void prepare() throws DeploymentException {
      Module[] modules = this.appCtx.getApplicationModules();

      for(int i = 0; i < modules.length; ++i) {
         if (modules[i] instanceof MergedDescriptorModule) {
            MergedDescriptorModule mdm = (MergedDescriptorModule)modules[i];
            Map map = mdm.getDescriptorMappings();
            if (map != null && map.size() > 0) {
               GenericClassLoader moduleLoader = ApplicationAccess.getApplicationAccess().findModuleLoader(this.appCtx.getApplicationId(), modules[i].getId());
               ClassFinder finder = new MergedDescriptorFinder(map);
               moduleLoader.addClassFinderFirst(finder);
               mdm.handleMergedFinder(finder);
            }
         }
      }

   }
}
