package weblogic.application.internal.flow;

import java.util.Iterator;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.internal.FlowContext;
import weblogic.application.naming.ModuleRegistry;
import weblogic.application.naming.ReferenceResolutionException;
import weblogic.application.naming.ReferenceResolver;
import weblogic.management.DeploymentException;

public class ReferenceResolutionFlow extends BaseFlow {
   public ReferenceResolutionFlow(FlowContext appCtx) {
      super(appCtx);
   }

   public void prepare() throws DeploymentException {
      ModuleRegistry registry = (ModuleRegistry)this.appCtx.getUserObject(ModuleRegistry.class.getName());
      if (registry != null) {
         this.resolveReferences(registry.getReferenceResolvers());
      }

      Module[] var2 = this.appCtx.getApplicationModules();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Module m = var2[var4];
         registry = this.appCtx.getModuleContext(m.getId()).getRegistry();
         if (registry != null) {
            this.resolveReferences(registry.getReferenceResolvers());
         }
      }

   }

   private void resolveReferences(Iterable resolvers) throws DeploymentException {
      Iterator var2 = resolvers.iterator();

      while(var2.hasNext()) {
         ReferenceResolver resolver = (ReferenceResolver)var2.next();

         try {
            resolver.resolve((ApplicationContextInternal)this.appCtx);
         } catch (ReferenceResolutionException var5) {
            throw new DeploymentException(var5);
         }
      }

   }
}
