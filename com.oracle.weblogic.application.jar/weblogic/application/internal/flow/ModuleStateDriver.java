package weblogic.application.internal.flow;

import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.UpdateListener;
import weblogic.application.internal.FlowContext;
import weblogic.application.utils.ExceptionUtils;
import weblogic.application.utils.StateChange;
import weblogic.application.utils.StateChangeException;
import weblogic.application.utils.StateMachineDriver;
import weblogic.utils.StackTraceUtils;

final class ModuleStateDriver {
   private final StateMachineDriver driver = new StateMachineDriver();
   private final FlowContext appCtx;
   private final StateChange prepareStateChange = new StateChange() {
      public String toString() {
         return "module_prepare";
      }

      public void next(Module m) throws Exception {
         m.prepare();
      }

      public void previous(Module m) throws Exception {
         m.unprepare();
      }

      public void logRollbackError(StateChangeException e) {
         ModuleStateDriver.this.log("Ignoring unprepare errors " + StackTraceUtils.throwable2StackTrace(e.getCause()));
      }
   };
   private final StateChange activateStateChange = new StateChange() {
      public String toString() {
         return "module_activate";
      }

      public void next(Module m) throws Exception {
         m.activate();
      }

      public void previous(Module m) throws Exception {
         m.deactivate();
      }

      public void logRollbackError(StateChangeException e) {
         ModuleStateDriver.this.log("Ignoring deactivate errors " + StackTraceUtils.throwable2StackTrace(e.getCause()));
      }
   };
   private final StateChange startStateChange = new StateChange() {
      public String toString() {
         return "module_start";
      }

      public void next(Module m) throws Exception {
         m.start();
      }

      public void previous(Module m) {
      }

      public void logRollbackError(StateChangeException e) {
      }
   };
   private final StateChange removeStateChange = new StateChange() {
      public String toString() {
         return "module_remove";
      }

      public void next(Module m) throws Exception {
         throw new AssertionError("someone is transitioning up to remove!");
      }

      public void previous(Module m) throws Exception {
         m.remove();
      }

      public void logRollbackError(StateChangeException e) {
         ModuleStateDriver.this.log("Ignoring remove errors " + StackTraceUtils.throwable2StackTrace(e.getCause()));
      }
   };
   private final StateChange adminToProductionChange = new StateChange() {
      public String toString() {
         return "module_adminToProd";
      }

      public void next(Module m) throws Exception {
         m.adminToProduction();
      }

      public void previous(Module m) throws Exception {
         m.forceProductionToAdmin();
      }

      public void logRollbackError(StateChangeException e) {
         ModuleStateDriver.this.log("Ignoring errors while forcing to admin mode " + StackTraceUtils.throwable2StackTrace(e.getCause()));
      }
   };

   ModuleStateDriver(FlowContext appCtx) {
      this.appCtx = appCtx;
   }

   public void prepare(Module[] modules) throws ModuleException {
      try {
         this.driver.nextState(this.prepareStateChange, modules);
      } catch (StateChangeException var3) {
         this.throwException(var3.getCause());
      }

   }

   public void parallelPrepare(Module[] modules) throws ModuleException {
      try {
         this.driver.nextStateInParallel(this.prepareStateChange, modules);
      } catch (StateChangeException var3) {
         this.throwException(var3.getCause());
      }

   }

   public void activate(Module[] modules) throws ModuleException {
      try {
         this.driver.nextState(this.activateStateChange, modules);
      } catch (StateChangeException var3) {
         this.throwException(var3.getCause());
      }

   }

   public void parallelActivate(Module[] modules) throws ModuleException {
      try {
         this.driver.nextStateInParallel(this.activateStateChange, modules);
      } catch (StateChangeException var3) {
         this.throwException(var3.getCause());
      }

   }

   public void start(Module[] modules) throws ModuleException {
      try {
         this.driver.nextState(this.startStateChange, modules);
      } catch (StateChangeException var3) {
         this.throwException(var3.getCause());
      }

   }

   public void parallelStart(Module[] modules) throws ModuleException {
      try {
         this.driver.nextStateInParallel(this.startStateChange, modules);
      } catch (StateChangeException var3) {
         this.throwException(var3.getCause());
      }

   }

   public void deactivate(Module[] modules) throws ModuleException {
      try {
         this.driver.previousState(this.activateStateChange, modules);
      } catch (StateChangeException var3) {
         this.throwException(var3.getCause());
      }

   }

   public void unprepare(Module[] modules) throws ModuleException {
      try {
         this.driver.previousState(this.prepareStateChange, modules);
      } catch (StateChangeException var3) {
         this.throwException(var3.getCause());
      }

   }

   public void remove(Module[] modules) throws ModuleException {
      try {
         this.driver.previousState(this.removeStateChange, modules);
      } catch (StateChangeException var3) {
         this.throwException(var3.getCause());
      }

   }

   public void destroy(Module[] modules) throws ModuleException {
      try {
         this.driver.previousState(new DestroyStateChange(this.appCtx), modules);
      } catch (StateChangeException var3) {
         this.throwException(var3.getCause());
      }

   }

   public void adminToProduction(Module[] modules) throws ModuleException {
      try {
         this.driver.nextState(this.adminToProductionChange, modules);
      } catch (StateChangeException var3) {
         this.throwException(var3.getCause());
      }

   }

   public void forceProductionToAdmin(Module[] modules) throws ModuleException {
      try {
         this.driver.previousState(this.adminToProductionChange, modules);
      } catch (StateChangeException var3) {
         this.throwException(var3.getCause());
      }

   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier, Module[] m) throws ModuleException {
      try {
         this.driver.previousState(new GracefulProductionToAdminChange(barrier), m);
      } catch (StateChangeException var4) {
         this.throwException(var4.getCause());
      }

   }

   private void log(String s) {
      this.appCtx.debug(s);
   }

   private void throwException(Throwable th) throws ModuleException {
      ExceptionUtils.throwModuleException(th);
   }

   private final class GracefulProductionToAdminChange implements StateChange {
      private final AdminModeCompletionBarrier barrier;

      public String toString() {
         return "module_gracefulProdToAdmin";
      }

      GracefulProductionToAdminChange(AdminModeCompletionBarrier barrier) {
         this.barrier = barrier;
      }

      public void next(Module m) throws Exception {
         throw new AssertionError("GracefulProductionToAdminChange.next");
      }

      public void previous(Module m) throws Exception {
         m.gracefulProductionToAdmin(this.barrier);
      }

      public void logRollbackError(StateChangeException e) {
         ModuleStateDriver.this.log("Ignoring errors while bringing to admin mode " + StackTraceUtils.throwable2StackTrace(e.getCause()));
      }
   }

   private final class DestroyStateChange implements StateChange {
      private final UpdateListener.Registration reg;

      DestroyStateChange(UpdateListener.Registration reg) {
         this.reg = reg;
      }

      public String toString() {
         return "module_destroy";
      }

      public void next(Module m) throws Exception {
         throw new AssertionError("someone is transitioning up to destroy!");
      }

      public void previous(Module m) throws Exception {
         m.destroy(this.reg);
      }

      public void logRollbackError(StateChangeException e) {
         ModuleStateDriver.this.log("Ignoring destroy errors " + StackTraceUtils.throwable2StackTrace(e.getCause()));
      }
   }
}
