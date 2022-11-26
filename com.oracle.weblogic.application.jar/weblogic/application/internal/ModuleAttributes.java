package weblogic.application.internal;

import java.util.Collection;
import weblogic.application.AppEnvSharingModule;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ConcurrentModule;
import weblogic.application.Module;
import weblogic.application.ModuleLocationInfo;
import weblogic.application.ModuleWrapper;
import weblogic.application.internal.flow.NonTargetedModuleInvoker;
import weblogic.application.naming.ModuleRegistryImpl;

public class ModuleAttributes {
   private Module wrappedModule;
   private Module unwrappedModule;
   private DeploymentModuleContext ctx;
   private final String URI;
   private boolean isUntargeted = false;
   private ExtensibleModuleWrapper extWrapper = null;
   private boolean searchedForExtWrapper = false;

   public ModuleAttributes(Module module, ApplicationContextInternal appCtx) {
      this.wrappedModule = module;
      if (this.wrappedModule == null) {
         this.unwrappedModule = null;
      } else {
         if (this.wrappedModule instanceof NonTargetedModuleInvoker) {
            this.isUntargeted = true;
         }

         Module m = this.wrappedModule;

         while(m instanceof ModuleWrapper) {
            m = ((ModuleWrapper)m).getDelegate();
            if (m instanceof NonTargetedModuleInvoker) {
               this.isUntargeted = true;
            }
         }

         this.unwrappedModule = m;
      }

      this.URI = this.unwrappedModule == null ? null : this.getModuleUri(this.unwrappedModule);
      if (this.unwrappedModule != null && appCtx != null) {
         this.ctx = new DeploymentModuleContext(this.URI, this.unwrappedModule.getId(), appCtx.getApplicationId(), appCtx.getApplicationName(), this.unwrappedModule.getType(), appCtx, new ModuleRegistryImpl(), this.unwrappedModule);
      } else {
         this.ctx = null;
      }

      if (this.unwrappedModule == null) {
         this.searchedForExtWrapper = true;
         this.extWrapper = null;
      }

   }

   private String getModuleUri(Module m) {
      if (m instanceof ModuleWrapper) {
         m = ((ModuleWrapper)m).unwrap();
      }

      return m instanceof ModuleLocationInfo ? ((ModuleLocationInfo)m).getModuleURI() : m.getId();
   }

   public Module getModule() {
      return this.wrappedModule;
   }

   public Module getUnwrappedModule() {
      return this.unwrappedModule;
   }

   public Object getUnwrappedModule(Class clz) {
      return !this.isUntargeted && this.unwrappedModule != null && clz.isAssignableFrom(this.unwrappedModule.getClass()) ? clz.cast(this.unwrappedModule) : null;
   }

   public boolean isConcurrent() {
      ConcurrentModule m = (ConcurrentModule)this.getUnwrappedModule(ConcurrentModule.class);
      return m == null ? false : m.isParallelEnabled();
   }

   public String getURI() {
      return this.URI;
   }

   public DeploymentModuleContext getModuleContext() {
      return this.ctx;
   }

   public Collection getExtensions() {
      if (!this.searchedForExtWrapper) {
         Module m;
         for(m = this.wrappedModule; m instanceof ModuleWrapper && !(m instanceof ExtensibleModuleWrapper); m = ((ModuleWrapper)m).getDelegate()) {
         }

         if (m instanceof ExtensibleModuleWrapper) {
            this.extWrapper = (ExtensibleModuleWrapper)m;
         }

         this.searchedForExtWrapper = true;
      }

      return this.extWrapper != null ? this.extWrapper.getExtensions() : null;
   }

   public boolean needsAppEnvContextCopy() {
      return this.unwrappedModule instanceof AppEnvSharingModule && ((AppEnvSharingModule)this.unwrappedModule).needsAppEnvContextCopy();
   }

   public void clear() {
      this.wrappedModule = null;
      this.unwrappedModule = null;
      if (this.ctx != null) {
         this.ctx.getRegistry().clear();
         this.ctx.cleanup();
         this.ctx = null;
      }

   }
}
