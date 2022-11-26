package weblogic.application.compiler.flow;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.compiler.AppcUtils;
import weblogic.application.compiler.CompilerCtx;
import weblogic.application.compiler.ModuleState;
import weblogic.application.compiler.ToolsContext;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.compiler.ToolsModuleExtension;
import weblogic.application.naming.ModuleRegistry;
import weblogic.application.naming.ReferenceResolutionException;
import weblogic.application.naming.ReferenceResolver;
import weblogic.application.utils.ClassLoaderUtils;
import weblogic.application.utils.EarUtils;
import weblogic.j2ee.J2EELogger;
import weblogic.j2ee.validation.EARValidator;
import weblogic.j2ee.validation.ModuleValidationInfo;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.compiler.ToolFailureException;
import weblogic.utils.compiler.jdt.JDTJavaCompilerFactory;

public final class CompileModuleFlow extends CompilerFlow {
   private ToolsModule[] modules = null;
   private EARValidator earValidator = null;
   private GenericClassLoader cl;

   public CompileModuleFlow(CompilerCtx ctx) {
      super(ctx);
   }

   public void compile() throws ToolFailureException {
      this.modules = this.ctx.getModules();
      this.earValidator = new EARValidator(this.ctx.getApplicationDD(), this.ctx.getWLApplicationDD());
      this.cl = this.getApplicationClassLoader();
      ClassLoaderUtils.initFilterPatterns(this.ctx.getWLApplicationDD() != null ? this.ctx.getWLApplicationDD().getPreferApplicationPackages() : null, this.ctx.getWLApplicationDD() != null ? this.ctx.getWLApplicationDD().getPreferApplicationResources() : null, this.cl);
      this.compileModules(this.cl);
      this.resolveReferences();

      try {
         this.earValidator.validate();
      } catch (ErrorCollectionException var2) {
         throw new ToolFailureException(J2EELogger.logAppcErrorsValidatingEarLoggable(var2.toString()).getMessage(), var2);
      }
   }

   private GenericClassLoader getApplicationClassLoader() {
      MultiClassFinder finder = new MultiClassFinder();
      finder.addFinder(this.ctx.getEar().getClassFinder());

      for(int i = 0; i < this.modules.length; ++i) {
         ClassFinder moduleClassFinder = this.ctx.getModuleState(this.modules[i]).getClassFinder();
         if (moduleClassFinder != null) {
            finder.addFinder(moduleClassFinder);
         }
      }

      finder.addFinder(this.ctx.getClassFinder());
      return AppcUtils.getClassLoaderForApplication(finder, this.ctx, this.ctx.getApplicationId());
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

   private void compileModules(GenericClassLoader cl) throws ToolFailureException {
      boolean quiet = this.ctx.getOpts().hasOption("quiet");
      String moduleUri = this.ctx.getOpts().getOption("moduleUri");
      boolean validModuleUri = false;

      for(int i = 0; i < this.modules.length; ++i) {
         ToolsModule module = this.modules[i];
         if (moduleUri != null) {
            if (!moduleUri.equals(module.getURI())) {
               continue;
            }

            if (module.getModuleType() != ModuleType.WAR) {
               throw new ToolFailureException(J2EELogger.logNotWebModuleLoggable(moduleUri).getMessage());
            }
         }

         validModuleUri = true;
         ModuleValidationInfo info = this.ctx.getModuleState(module).getValidationInfo();
         this.earValidator.addModuleValidationInfo(info);
         if ((this.ctx.isVerbose() || !quiet) && !this.ctx.getModuleState(module).isLibrary()) {
            J2EELogger.logCompilingEarModule(module.getURI());
         }

         ModuleState state = this.ctx.getModuleState(module);
         if (debugLogger.isDebugEnabled() && !state.isLibrary()) {
            debugLogger.debug(EarUtils.addClassName("Compiling: " + module.getURI() + " from: " + this.ctx.getModuleState(module).getOutputFileName()));
         }

         Map moduleDescriptors = module.compile(cl);
         Map extensibleModuleDescriptors = moduleDescriptors == null ? new HashMap() : new HashMap(moduleDescriptors);
         state.initExtensions();
         Iterator var11 = state.getExtensions().iterator();

         while(var11.hasNext()) {
            ToolsModuleExtension modExtension = (ToolsModuleExtension)var11.next();
            modExtension.compile(cl, extensibleModuleDescriptors);
         }
      }

      if (!validModuleUri) {
         throw new ToolFailureException(J2EELogger.logModuleUriDoesNotExistLoggable(moduleUri).getMessage());
      }
   }

   public void cleanup() {
      if (this.cl != null) {
         this.cl.close();
         JDTJavaCompilerFactory.getInstance().resetCache(this.cl);
      }

   }
}
