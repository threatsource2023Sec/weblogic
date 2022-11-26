package weblogic.application.utils;

import java.util.List;
import weblogic.application.ApplicationAccess;
import weblogic.application.compiler.CompilerCtx;
import weblogic.application.compiler.Factory;
import weblogic.application.compiler.Merger;

public final class LightWeightDeploymentViewFactory implements Factory {
   public Boolean claim(CompilerCtx m) {
      String lightWeightApp = m.getLightWeightAppName();
      if (lightWeightApp == null) {
         return null;
      } else {
         return null == ApplicationAccess.getApplicationAccess().getApplicationContext(m.getLightWeightAppName()) ? null : true;
      }
   }

   public Boolean claim(CompilerCtx m, List allClaimants) {
      return this.claim(m);
   }

   public Merger create(CompilerCtx m) {
      return new LightWeightMerger(m);
   }
}
