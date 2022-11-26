package weblogic.application;

public abstract class ModuleExtension {
   protected final ModuleExtensionContext extensionCtx;
   protected final ApplicationContextInternal appCtx;
   protected final ModuleContext modCtx;
   protected final Module extensibleModule;

   public ModuleExtension(ModuleExtensionContext modCtx, ApplicationContextInternal appCtx, Module extensibleModule) {
      this.extensionCtx = modCtx;
      this.appCtx = appCtx;
      this.extensibleModule = extensibleModule;
      this.modCtx = appCtx.getModuleContext(extensibleModule.getId());
   }

   public void prePrepare() throws ModuleException {
   }

   public void postPrepare(UpdateListener.Registration reg) throws ModuleException {
   }

   public void preUnprepare(UpdateListener.Registration reg) throws ModuleException {
   }

   public void postUnprepare() throws ModuleException {
   }

   public void preActivate() throws ModuleException {
   }

   public void postActivate() throws ModuleException {
   }

   public void preDeactivate() throws ModuleException {
   }

   public void postDeactivate() throws ModuleException {
   }

   public void start() throws ModuleException {
   }

   public void remove() throws ModuleException {
   }

   public void preRefreshClassLoader() throws ModuleException {
   }

   public void postRefreshClassLoader() throws ModuleException {
   }

   public void postAdminToProduction() {
   }

   public void preGracefulProductionToAdmin(AdminModeCompletionBarrier barrier) throws ModuleException {
   }

   public void preForceProductionToAdmin() throws ModuleException {
   }
}
