package weblogic.application.internal.flow;

import java.util.Iterator;
import java.util.Map;
import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.ApplicationContext;
import weblogic.application.DeploymentManager;
import weblogic.application.MergedDescriptorModule;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.ModuleListener;
import weblogic.application.ModuleListenerCtx;
import weblogic.application.ModuleListenerCtxImpl;
import weblogic.application.ModuleWrapper;
import weblogic.application.UpdateListener;
import weblogic.application.utils.ExceptionUtils;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;

public final class ModuleListenerInvoker extends ModuleWrapper implements Module, MergedDescriptorModule {
   private final Module delegate;
   private final TargetMBean target;
   private ModuleListener.State state;
   private ModuleListenerCtx ctx;
   private final DeploymentManager deploymentManager;

   ModuleListenerInvoker(Module delegate, TargetMBean target) {
      this.state = ModuleListener.STATE_NEW;
      this.deploymentManager = DeploymentManager.getDeploymentManager();
      this.delegate = delegate;
      this.target = target;
   }

   public String toString() {
      return this.getClass().getName() + "(" + this.delegate.getClass().getName() + ")(" + this.ctx.toString() + ")";
   }

   public Module getDelegate() {
      return this.delegate;
   }

   private void throwModuleException(Throwable th) throws ModuleException {
      ExceptionUtils.throwModuleException(th);
   }

   public String getId() {
      return this.delegate.getId();
   }

   public String getType() {
      return this.delegate.getType();
   }

   public ComponentRuntimeMBean[] getComponentRuntimeMBeans() {
      return this.delegate.getComponentRuntimeMBeans();
   }

   public DescriptorBean[] getDescriptors() {
      return this.delegate.getDescriptors();
   }

   public GenericClassLoader init(ApplicationContext appCtx, GenericClassLoader parent, UpdateListener.Registration reg) throws ModuleException {
      this.ctx = new ModuleListenerCtxImpl(appCtx.getApplicationId(), this.getId(), this.target, this.getType());
      return this.delegate.init(appCtx, parent, reg);
   }

   public void initUsingLoader(ApplicationContext appCtx, GenericClassLoader gcl, UpdateListener.Registration reg) throws ModuleException {
      this.ctx = new ModuleListenerCtxImpl(appCtx.getApplicationId(), this.getId(), this.target, this.getType());
      this.delegate.initUsingLoader(appCtx, gcl, reg);
   }

   public void prepare() throws ModuleException {
      try {
         this.begin(ModuleListener.STATE_PREPARED);
         this.delegate.prepare();
         this.end(ModuleListener.STATE_PREPARED);
         this.state = ModuleListener.STATE_PREPARED;
      } catch (Throwable var2) {
         this.failed(ModuleListener.STATE_PREPARED);
         this.throwModuleException(var2);
      }

   }

   public void activate() throws ModuleException {
      try {
         this.begin(ModuleListener.STATE_ADMIN);
         this.delegate.activate();
         this.end(ModuleListener.STATE_ADMIN);
         this.state = ModuleListener.STATE_ADMIN;
      } catch (Throwable var2) {
         this.failed(ModuleListener.STATE_ADMIN);
         this.throwModuleException(var2);
      }

   }

   public void start() throws ModuleException {
      this.delegate.start();
   }

   public void deactivate() throws ModuleException {
      try {
         this.begin(ModuleListener.STATE_PREPARED);
         this.delegate.deactivate();
         this.end(ModuleListener.STATE_PREPARED);
         this.state = ModuleListener.STATE_PREPARED;
      } catch (Throwable var2) {
         this.failed(ModuleListener.STATE_PREPARED);
         this.throwModuleException(var2);
      }

   }

   public void unprepare() throws ModuleException {
      try {
         this.begin(ModuleListener.STATE_NEW);
         this.delegate.unprepare();
         this.end(ModuleListener.STATE_NEW);
         this.state = ModuleListener.STATE_NEW;
      } catch (Throwable var2) {
         this.failed(ModuleListener.STATE_NEW);
         this.throwModuleException(var2);
      }

   }

   public void destroy(UpdateListener.Registration reg) throws ModuleException {
      this.delegate.destroy(reg);
   }

   public void remove() throws ModuleException {
      this.delegate.remove();
   }

   public void adminToProduction() {
      try {
         this.begin(ModuleListener.STATE_ACTIVE);
         this.delegate.adminToProduction();
         this.end(ModuleListener.STATE_ACTIVE);
         this.state = ModuleListener.STATE_ACTIVE;
      } catch (RuntimeException var2) {
         this.failed(ModuleListener.STATE_ACTIVE);
         throw var2;
      } catch (Error var3) {
         this.failed(ModuleListener.STATE_ACTIVE);
         throw var3;
      }
   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier) throws ModuleException {
      try {
         this.begin(ModuleListener.STATE_ADMIN);
         this.delegate.gracefulProductionToAdmin(barrier);
         this.end(ModuleListener.STATE_ADMIN);
         this.state = ModuleListener.STATE_ADMIN;
      } catch (Throwable var3) {
         this.failed(ModuleListener.STATE_ADMIN);
         this.throwModuleException(var3);
      }

   }

   public void forceProductionToAdmin() throws ModuleException {
      try {
         this.begin(ModuleListener.STATE_ADMIN);
         this.delegate.forceProductionToAdmin();
         this.end(ModuleListener.STATE_ADMIN);
         this.state = ModuleListener.STATE_ADMIN;
      } catch (Throwable var2) {
         this.failed(ModuleListener.STATE_ADMIN);
         this.throwModuleException(var2);
      }

   }

   private void begin(ModuleListener.State newState) {
      Iterator it = this.deploymentManager.getModuleListeners();

      while(it.hasNext()) {
         ((ModuleListener)it.next()).beginTransition(this.ctx, this.state, newState);
      }

   }

   private void end(ModuleListener.State newState) {
      Iterator it = this.deploymentManager.getModuleListeners();

      while(it.hasNext()) {
         ((ModuleListener)it.next()).endTransition(this.ctx, this.state, newState);
      }

   }

   private void failed(ModuleListener.State newState) {
      Iterator it = this.deploymentManager.getModuleListeners();

      while(it.hasNext()) {
         ((ModuleListener)it.next()).failedTransition(this.ctx, this.state, newState);
      }

   }

   public Map getDescriptorMappings() {
      return this.delegate instanceof MergedDescriptorModule ? ((MergedDescriptorModule)this.delegate).getDescriptorMappings() : null;
   }

   public void handleMergedFinder(ClassFinder finder) {
      if (this.delegate instanceof MergedDescriptorModule) {
         ((MergedDescriptorModule)this.delegate).handleMergedFinder(finder);
      }

   }
}
