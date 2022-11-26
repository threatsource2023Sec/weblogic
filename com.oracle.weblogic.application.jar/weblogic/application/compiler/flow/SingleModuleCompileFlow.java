package weblogic.application.compiler.flow;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.application.compiler.AppcUtils;
import weblogic.application.compiler.CompilerCtx;
import weblogic.application.compiler.ToolsContext;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.compiler.ToolsModuleExtension;
import weblogic.application.naming.ModuleRegistry;
import weblogic.application.naming.ReferenceResolutionException;
import weblogic.application.naming.ReferenceResolver;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.compiler.ToolFailureException;

public class SingleModuleCompileFlow extends SingleModuleFlow {
   public SingleModuleCompileFlow(CompilerCtx ctx) {
      super(ctx);
   }

   protected void proecessModule(ToolsModule module) throws ToolFailureException {
      GenericClassLoader cl = AppcUtils.getClassLoaderForModule(this.ctx.getModuleState(module).getClassFinder(), this.ctx, this.ctx.getApplicationId(), module.getURI());
      Map moduleDescriptors = module.compile(cl);
      Map extensibleModuleDescriptors = moduleDescriptors == null ? new HashMap() : new HashMap(moduleDescriptors);
      this.ctx.getModuleState(module).initExtensions();
      Iterator var5 = this.ctx.getModuleState(module).getExtensions().iterator();

      while(var5.hasNext()) {
         ToolsModuleExtension modExtension = (ToolsModuleExtension)var5.next();
         modExtension.compile(cl, extensibleModuleDescriptors);
      }

      this.resolveReferences();
   }

   private void resolveReferences() throws ToolFailureException {
      ToolsModule[] var1 = this.ctx.getModules();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         ToolsModule m = var1[var3];
         ModuleRegistry registry = this.ctx.getModuleContext(m.getURI()).getRegistry();
         if (registry != null) {
            Iterator var6 = registry.getReferenceResolvers().iterator();

            while(var6.hasNext()) {
               ReferenceResolver resolver = (ReferenceResolver)var6.next();

               try {
                  resolver.resolve((ToolsContext)this.ctx);
               } catch (ReferenceResolutionException var9) {
                  throw new ToolFailureException("Unable to resolve references", var9);
               }
            }
         }
      }

   }
}
