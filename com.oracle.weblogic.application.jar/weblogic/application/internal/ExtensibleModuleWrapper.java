package weblogic.application.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.ApplicationContext;
import weblogic.application.ApplicationFactoryManager;
import weblogic.application.Extensible;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.ModuleExtension;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.ModuleExtensionFactory;
import weblogic.application.ModuleWrapper;
import weblogic.application.UpdateListener;
import weblogic.application.utils.StateChange;
import weblogic.application.utils.StateChangeException;
import weblogic.application.utils.StateMachineDriver;
import weblogic.descriptor.DescriptorBean;
import weblogic.i18n.logging.Loggable;
import weblogic.j2ee.J2EELogger;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.utils.classloaders.GenericClassLoader;

public class ExtensibleModuleWrapper extends ModuleWrapper {
   private DrivenObject[] drivenObjects = new DrivenObject[0];
   private final Module extensibleModule;
   private final Module delegate;
   private final FlowContext ctx;
   private Collection moduleExtensions;
   private final StateMachineDriver driver = new StateMachineDriver();
   private final PrepareStateChange prepareChange;
   private static ActivateStateChange activateChange = new ActivateStateChange();
   private static AdminToProductionStateChange adminToProductionChange = new AdminToProductionStateChange();
   private static StartStateChange startChange = new StartStateChange();
   private static RemoveStateChange removeChange = new RemoveStateChange();

   public ExtensibleModuleWrapper(Module delegate, FlowContext ctx) {
      this.delegate = delegate;
      if (delegate instanceof ModuleWrapper) {
         this.extensibleModule = ((ModuleWrapper)delegate).unwrap();
      } else {
         this.extensibleModule = delegate;
      }

      if (!(this.extensibleModule instanceof Extensible)) {
         throw new AssertionError("Extensible Module Wrapper must only be created for modules that have been marked Extensible");
      } else {
         this.prepareChange = new PrepareStateChange(ctx);
         this.ctx = ctx;
      }
   }

   public Module getDelegate() {
      return this.delegate;
   }

   public Collection getExtensions() {
      return this.moduleExtensions;
   }

   public ComponentRuntimeMBean[] getComponentRuntimeMBeans() {
      return this.delegate.getComponentRuntimeMBeans();
   }

   public DescriptorBean[] getDescriptors() {
      return this.delegate.getDescriptors();
   }

   public String getId() {
      return this.delegate.getId();
   }

   public String getType() {
      return this.delegate.getType();
   }

   public GenericClassLoader init(ApplicationContext appCtx, GenericClassLoader parent, UpdateListener.Registration reg) throws ModuleException {
      return this.delegate.init(appCtx, parent, reg);
   }

   public void initUsingLoader(ApplicationContext appCtx, GenericClassLoader gcl, UpdateListener.Registration reg) throws ModuleException {
      this.delegate.initUsingLoader(appCtx, gcl, reg);
   }

   public void prepare() throws ModuleException {
      this.initDrivenObjectArray();

      try {
         this.driver.nextState(this.prepareChange, this.drivenObjects);
      } catch (StateChangeException var2) {
         if (Loggable.isAppendStackTraceEnabled()) {
            throw new ModuleException(var2.getMessage(), var2.getCause());
         } else {
            throw new ModuleException(var2);
         }
      }
   }

   public void activate() throws ModuleException {
      try {
         this.driver.nextState(activateChange, this.drivenObjects);
      } catch (StateChangeException var2) {
         throw new ModuleException(var2);
      }
   }

   public void adminToProduction() {
      try {
         this.driver.nextState(adminToProductionChange, this.drivenObjects);
      } catch (StateChangeException var2) {
         throw new AssertionError("No checked exceptions are thrown by ModuleExtension.productionToAdmin()");
      }
   }

   public void start() throws ModuleException {
      try {
         this.driver.nextState(startChange, this.drivenObjects);
      } catch (StateChangeException var2) {
         throw new ModuleException(var2);
      }
   }

   public void forceProductionToAdmin() throws ModuleException {
      try {
         this.driver.previousState(adminToProductionChange, this.drivenObjects);
      } catch (StateChangeException var2) {
         throw new ModuleException(var2);
      }
   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier) throws ModuleException {
      try {
         this.driver.previousState(new GracefulProductionToAdmin(barrier), this.drivenObjects);
      } catch (StateChangeException var3) {
         throw new ModuleException(var3);
      }
   }

   public void deactivate() throws ModuleException {
      try {
         this.driver.previousState(activateChange, this.drivenObjects);
      } catch (StateChangeException var2) {
         throw new ModuleException(var2);
      }
   }

   public void unprepare() throws ModuleException {
      try {
         this.driver.previousState(this.prepareChange, this.drivenObjects);
      } catch (StateChangeException var2) {
         throw new ModuleException(var2);
      }
   }

   public void destroy(UpdateListener.Registration reg) throws ModuleException {
      this.delegate.destroy(reg);
   }

   public void remove() throws ModuleException {
      try {
         this.driver.previousState(removeChange, this.drivenObjects);
      } catch (StateChangeException var2) {
         throw new ModuleException(var2);
      }
   }

   private void initDrivenObjectArray() throws ModuleException {
      Collection extensions = this.createModuleExtensions();
      if (extensions != null) {
         this.drivenObjects = new DrivenObject[extensions.size() * 2 + 1];
         int index = 0;

         Iterator var3;
         ModuleExtension extension;
         for(var3 = extensions.iterator(); var3.hasNext(); this.drivenObjects[index++] = new DrivenObject(extension, ExtensibleModuleWrapper.DrivenObjectType.PRE_EXTENSION)) {
            extension = (ModuleExtension)var3.next();
         }

         this.drivenObjects[index++] = new DrivenObject(this.extensibleModule);

         for(var3 = extensions.iterator(); var3.hasNext(); this.drivenObjects[index++] = new DrivenObject(extension, ExtensibleModuleWrapper.DrivenObjectType.POST_EXTENSION)) {
            extension = (ModuleExtension)var3.next();
         }
      } else {
         this.drivenObjects = new DrivenObject[]{new DrivenObject(this.extensibleModule)};
      }

   }

   private Collection createModuleExtensions() throws ModuleException {
      Extensible extensible = (Extensible)this.extensibleModule;
      ModuleExtensionContext moduleExtensionContext = extensible.getModuleExtensionContext();
      if (moduleExtensionContext != null) {
         Iterator moduleExtensionFactories = ApplicationFactoryManager.getApplicationFactoryManager().getModuleExtensionFactories(extensible.getType());
         if (moduleExtensionFactories != null) {
            while(moduleExtensionFactories.hasNext()) {
               ModuleExtensionFactory factory = (ModuleExtensionFactory)moduleExtensionFactories.next();
               ModuleExtension extension = factory.create(moduleExtensionContext, this.ctx, this.extensibleModule, extensible.getStandardDescriptor());
               if (extension != null) {
                  if (this.moduleExtensions == null) {
                     this.moduleExtensions = new ArrayList();
                  }

                  this.moduleExtensions.add(extension);
               }
            }
         }
      }

      return this.moduleExtensions;
   }

   private static class RemoveStateChange extends Change {
      private RemoveStateChange() {
         super(null);
      }

      public void next(DrivenObject machine) throws Exception {
      }

      public void previous(DrivenObject machine) throws Exception {
         if (machine.getType() == ExtensibleModuleWrapper.DrivenObjectType.MODULE) {
            machine.getModule().remove();
         } else if (machine.getType() == ExtensibleModuleWrapper.DrivenObjectType.PRE_EXTENSION) {
            machine.getModuleExtension().remove();
         }

      }

      // $FF: synthetic method
      RemoveStateChange(Object x0) {
         this();
      }
   }

   private static class GracefulProductionToAdmin extends Change {
      private final AdminModeCompletionBarrier barrier;

      public GracefulProductionToAdmin(AdminModeCompletionBarrier barrier) {
         super(null);
         this.barrier = barrier;
      }

      public void next(DrivenObject machine) throws Exception {
      }

      public void previous(DrivenObject machine) throws Exception {
         if (machine.getType() == ExtensibleModuleWrapper.DrivenObjectType.MODULE) {
            machine.getModule().gracefulProductionToAdmin(this.barrier);
         } else if (machine.getType() == ExtensibleModuleWrapper.DrivenObjectType.POST_EXTENSION) {
            machine.getModuleExtension().preGracefulProductionToAdmin(this.barrier);
         }

      }
   }

   private static class StartStateChange extends Change {
      private StartStateChange() {
         super(null);
      }

      public void next(DrivenObject machine) throws Exception {
         if (machine.getType() == ExtensibleModuleWrapper.DrivenObjectType.MODULE) {
            machine.getModule().start();
         } else if (machine.getType() == ExtensibleModuleWrapper.DrivenObjectType.POST_EXTENSION) {
            machine.getModuleExtension().start();
         }

      }

      public void previous(DrivenObject machine) throws Exception {
      }

      // $FF: synthetic method
      StartStateChange(Object x0) {
         this();
      }
   }

   private static class AdminToProductionStateChange extends Change {
      private AdminToProductionStateChange() {
         super(null);
      }

      public void next(DrivenObject machine) throws Exception {
         if (machine.getType() == ExtensibleModuleWrapper.DrivenObjectType.MODULE) {
            machine.getModule().adminToProduction();
         } else if (machine.getType() == ExtensibleModuleWrapper.DrivenObjectType.POST_EXTENSION) {
            machine.getModuleExtension().postAdminToProduction();
         }

      }

      public void previous(DrivenObject machine) throws Exception {
         if (machine.getType() == ExtensibleModuleWrapper.DrivenObjectType.MODULE) {
            machine.getModule().forceProductionToAdmin();
         } else if (machine.getType() == ExtensibleModuleWrapper.DrivenObjectType.POST_EXTENSION) {
            machine.getModuleExtension().preForceProductionToAdmin();
         }

      }

      // $FF: synthetic method
      AdminToProductionStateChange(Object x0) {
         this();
      }
   }

   private static class ActivateStateChange extends Change {
      private ActivateStateChange() {
         super(null);
      }

      public void next(DrivenObject machine) throws Exception {
         if (machine.getType() == ExtensibleModuleWrapper.DrivenObjectType.MODULE) {
            machine.getModule().activate();
         } else if (machine.getType() == ExtensibleModuleWrapper.DrivenObjectType.PRE_EXTENSION) {
            machine.getModuleExtension().preActivate();
         } else {
            machine.getModuleExtension().postActivate();
         }

      }

      public void previous(DrivenObject machine) throws Exception {
         if (machine.getType() == ExtensibleModuleWrapper.DrivenObjectType.MODULE) {
            machine.getModule().deactivate();
         } else if (machine.getType() == ExtensibleModuleWrapper.DrivenObjectType.PRE_EXTENSION) {
            machine.getModuleExtension().postDeactivate();
         } else {
            machine.getModuleExtension().preDeactivate();
         }

      }

      // $FF: synthetic method
      ActivateStateChange(Object x0) {
         this();
      }
   }

   private static class PrepareStateChange extends Change {
      private final UpdateListener.Registration reg;

      public PrepareStateChange(UpdateListener.Registration reg) {
         super(null);
         this.reg = reg;
      }

      public void next(DrivenObject machine) throws Exception {
         if (machine.getType() == ExtensibleModuleWrapper.DrivenObjectType.PRE_EXTENSION) {
            machine.getModuleExtension().prePrepare();
         } else if (machine.getType() == ExtensibleModuleWrapper.DrivenObjectType.MODULE) {
            machine.getModule().prepare();
         } else {
            machine.getModuleExtension().postPrepare(this.reg);
         }

      }

      public void previous(DrivenObject machine) throws Exception {
         if (machine.getType() == ExtensibleModuleWrapper.DrivenObjectType.PRE_EXTENSION) {
            machine.getModuleExtension().postUnprepare();
         } else if (machine.getType() == ExtensibleModuleWrapper.DrivenObjectType.MODULE) {
            machine.getModule().unprepare();
         } else {
            machine.getModuleExtension().preUnprepare(this.reg);
         }

      }
   }

   private abstract static class Change implements StateChange {
      private Change() {
      }

      public void logRollbackError(StateChangeException e) {
         J2EELogger.logIgnoringUndeploymentError(e.getCause());
      }

      // $FF: synthetic method
      Change(Object x0) {
         this();
      }
   }

   private static class DrivenObject {
      private final Module module;
      private final ModuleExtension extension;
      private final DrivenObjectType type;

      private DrivenObject(Module m) {
         this.module = m;
         this.type = ExtensibleModuleWrapper.DrivenObjectType.MODULE;
         this.extension = null;
      }

      private DrivenObject(ModuleExtension extension, DrivenObjectType type) {
         this.module = null;
         this.type = type;
         this.extension = extension;
      }

      DrivenObjectType getType() {
         return this.type;
      }

      Module getModule() {
         return this.module;
      }

      ModuleExtension getModuleExtension() {
         return this.extension;
      }

      public String toString() {
         return this.type == ExtensibleModuleWrapper.DrivenObjectType.MODULE ? this.module.toString() : this.extension.toString();
      }

      // $FF: synthetic method
      DrivenObject(ModuleExtension x0, DrivenObjectType x1, Object x2) {
         this(x0, x1);
      }

      // $FF: synthetic method
      DrivenObject(Module x0, Object x1) {
         this(x0);
      }
   }

   private static enum DrivenObjectType {
      PRE_EXTENSION,
      MODULE,
      POST_EXTENSION;
   }
}
