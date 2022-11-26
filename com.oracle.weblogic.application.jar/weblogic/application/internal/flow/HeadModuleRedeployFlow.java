package weblogic.application.internal.flow;

import java.util.ArrayList;
import java.util.List;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;

public final class HeadModuleRedeployFlow extends BaseFlow {
   public HeadModuleRedeployFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void stop(String[] uris) {
      Module[] stoppingModules = this.appCtx.getStoppingModules();
      this.appCtx.setStoppingModules(new Module[0]);
      Module[] currentModules = this.appCtx.getApplicationModules();
      List moduleList = new ArrayList(currentModules.length - stoppingModules.length);
      int si = 0;

      for(int i = 0; i < currentModules.length; ++i) {
         if (si < stoppingModules.length && stoppingModules[si].getId().equals(currentModules[i].getId())) {
            ++si;
         } else {
            moduleList.add(currentModules[i]);
         }
      }

      this.appCtx.setApplicationModules((Module[])((Module[])moduleList.toArray(new Module[moduleList.size()])));
   }
}
