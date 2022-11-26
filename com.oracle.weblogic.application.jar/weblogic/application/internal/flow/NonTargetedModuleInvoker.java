package weblogic.application.internal.flow;

import java.util.HashMap;
import java.util.Map;
import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.ApplicationContext;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.ModuleWrapper;
import weblogic.application.UpdateListener;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.utils.classloaders.GenericClassLoader;

public final class NonTargetedModuleInvoker extends ModuleWrapper implements Module, UpdateListener.Registration {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainer");
   private final Map updateListeners = new HashMap(1);
   private final Module delegate;
   private UpdateListener.Registration updateListenerRegistration = null;
   private final boolean partialDelegation;
   private static DescriptorBean[] noDescriptorBeans = new DescriptorBean[0];
   private static ComponentRuntimeMBean[] noRuntimeMBeans = new ComponentRuntimeMBean[0];

   public NonTargetedModuleInvoker(Module delegate, boolean partialDelegation) {
      this.delegate = delegate;
      this.partialDelegation = partialDelegation;
   }

   public Module getDelegate() {
      return this.delegate;
   }

   public String getId() {
      return this.delegate.getId();
   }

   public String getType() {
      return this.delegate.getType();
   }

   public ComponentRuntimeMBean[] getComponentRuntimeMBeans() {
      return this.partialDelegation ? this.delegate.getComponentRuntimeMBeans() : noRuntimeMBeans;
   }

   public DescriptorBean[] getDescriptors() {
      return this.partialDelegation ? this.delegate.getDescriptors() : noDescriptorBeans;
   }

   public GenericClassLoader init(ApplicationContext appCtx, GenericClassLoader parent, UpdateListener.Registration reg) throws ModuleException {
      this.updateListenerRegistration = reg;
      return this.partialDelegation ? this.delegate.init(appCtx, parent, this) : parent;
   }

   public void initUsingLoader(ApplicationContext appCtx, GenericClassLoader gcl, UpdateListener.Registration reg) throws ModuleException {
      if (this.partialDelegation) {
         this.delegate.initUsingLoader(appCtx, gcl, reg);
      }

   }

   public void destroy(UpdateListener.Registration reg) throws ModuleException {
      if (this.partialDelegation) {
         this.delegate.destroy(this);
      }

   }

   public void remove() throws ModuleException {
      if (this.partialDelegation) {
         this.delegate.remove();
      }

   }

   public void prepare() {
   }

   public void activate() {
   }

   public void start() {
   }

   public void deactivate() {
   }

   public void unprepare() {
   }

   public void adminToProduction() {
   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier) {
   }

   public void forceProductionToAdmin() {
   }

   public void addUpdateListener(UpdateListener updateListener) {
      UpdateListener ul = new OnlyAcceptURIUpdateListener(updateListener);
      this.updateListeners.put(updateListener, ul);
      if (debugLogger.isDebugEnabled()) {
         String s = ul == null ? "null" : ul.getClass().getName();
         debugLogger.debug("Registering wrapped update listener " + s + " for module " + this.delegate.getId());
      }

      this.updateListenerRegistration.addUpdateListener(ul);
   }

   public void removeUpdateListener(UpdateListener updateListener) {
      UpdateListener ul = (UpdateListener)this.updateListeners.remove(updateListener);
      this.updateListenerRegistration.removeUpdateListener(ul);
      if (debugLogger.isDebugEnabled()) {
         String s = ul == null ? "null" : ul.getClass().getName();
         debugLogger.debug("Removed wrapped update listener " + s + " for module " + this.delegate.getId());
      }

   }

   private static class OnlyAcceptURIUpdateListener implements UpdateListener {
      private final UpdateListener delegate;

      OnlyAcceptURIUpdateListener(UpdateListener delegate) {
         this.delegate = delegate;
      }

      public boolean acceptURI(String uri) {
         return this.delegate.acceptURI(uri);
      }

      public void prepareUpdate(String uri) {
      }

      public void activateUpdate(String uri) {
      }

      public void rollbackUpdate(String uri) {
      }
   }
}
