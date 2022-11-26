package weblogic.diagnostics.instrumentation.gathering;

import weblogic.diagnostics.instrumentation.ValueRenderer;
import weblogic.servlet.internal.WebAppModule;
import weblogic.servlet.internal.WebAppServletContext;

public final class WebAppModuleNameRenderer implements ValueRenderer {
   private static final String noModule = "<internal>";

   public Object render(Object inputObject) {
      String moduleName = null;
      if (inputObject == null) {
         moduleName = "<internal>";
      } else if (inputObject instanceof WebAppModule) {
         WebAppModule module = (WebAppModule)inputObject;
         moduleName = module.getName();
      } else if (inputObject instanceof WebAppServletContext) {
         WebAppServletContext ctx = (WebAppServletContext)inputObject;
         WebAppModule module = ctx.getWebAppModule();
         if (module != null) {
            moduleName = module.getName();
         }
      }

      return new WebApplicationEventInfoImpl(moduleName);
   }
}
