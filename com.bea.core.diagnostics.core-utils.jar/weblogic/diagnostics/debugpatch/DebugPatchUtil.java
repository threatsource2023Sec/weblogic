package weblogic.diagnostics.debugpatch;

import java.util.ArrayList;
import java.util.List;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.diagnostics.i18n.DiagnosticsTextTextFormatter;

@Service
public class DebugPatchUtil implements FindClassLoaders {
   private static final DiagnosticsTextTextFormatter DTF = DiagnosticsTextTextFormatter.getInstance();

   public List findAppClassLoaders(String partition, String appName, String moduleName) {
      List list = new ArrayList();
      String errMsg = null;
      String applicationName = appName;
      ApplicationAccess appAccess = ApplicationAccess.getApplicationAccess();
      appName = ApplicationVersionUtils.getApplicationId(appName, (String)null, partition);
      ApplicationContextInternal appCtx = appAccess.getApplicationContext(appName);
      if (appCtx != null) {
         if (moduleName == null) {
            list.add(appCtx.getAppClassLoader());
         }

         Module[] modules = appCtx.getApplicationModules();
         if (modules != null) {
            Module[] var10 = modules;
            int var11 = modules.length;

            for(int var12 = 0; var12 < var11; ++var12) {
               Module mod = var10[var12];
               String moduleId = mod.getId();
               if (moduleName == null || moduleName.equals(moduleId)) {
                  ClassLoader loader = appAccess.findModuleLoader(appName, moduleId);
                  if (loader != null) {
                     list.add(loader);
                     if (moduleId.equals(moduleName)) {
                        break;
                     }
                  }
               }
            }
         }

         if (moduleName != null && list.size() == 0) {
            if (partition == null) {
               errMsg = DTF.getModuleInApplicationNotFoundDuringPatchActivation(applicationName, moduleName);
            } else {
               errMsg = DTF.getModuleInApplicationInPartitionNotFoundDuringPatchActivation(applicationName, moduleName, partition);
            }
         }
      } else if (partition == null) {
         errMsg = DTF.getApplicationNotFoundDuringPatchActivation(applicationName);
      } else {
         errMsg = DTF.getApplicationInPartitionNotFoundDuringPatchActivation(applicationName, partition);
      }

      if (errMsg != null) {
         throw new IllegalArgumentException(errMsg);
      } else {
         return list;
      }
   }
}
