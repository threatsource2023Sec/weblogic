package weblogic.metadata.management;

import weblogic.application.CustomModuleContext;
import weblogic.application.CustomModuleFactory;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.config.DefaultEARModule;
import weblogic.j2ee.descriptor.wl.CustomModuleBean;
import weblogic.j2ee.descriptor.wl.ModuleProviderBean;
import weblogic.utils.compiler.ToolFailureException;

public class AnnotationOverridesModuleFactory extends CustomModuleFactory {
   private ModuleProviderBean config;
   private CustomModuleContext ctx;

   public void init(CustomModuleContext ctx) {
      this.config = ctx.getModuleProviderBean();
      this.ctx = ctx;
   }

   public Module createModule(CustomModuleBean cmb) throws ModuleException {
      return new AnnotationOverridesModule(this.config, cmb);
   }

   public ToolsModule createToolsModule(CustomModuleBean cmb) throws ToolFailureException {
      return new DefaultEARModule(this.ctx, cmb, true);
   }
}
