package weblogic.application.internal.flow;

import java.util.HashMap;
import java.util.Map;
import weblogic.application.ApplicationContextInternal;
import weblogic.j2ee.descriptor.wl.ApplicationParamBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.management.DeploymentException;

public final class ApplicationParamFlow extends BaseFlow {
   public ApplicationParamFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void prepare() throws DeploymentException {
      WeblogicApplicationBean wldd = this.appCtx.getWLApplicationDD();
      if (wldd != null) {
         ApplicationParamBean[] params = wldd.getApplicationParams();
         if (params != null && params.length != 0) {
            Map paramMap = new HashMap(params.length);

            for(int i = 0; i < params.length; ++i) {
               paramMap.put(params[i].getParamName(), params[i].getParamValue());
            }

            this.appCtx.setApplicationParameters(paramMap);
         }
      }
   }
}
