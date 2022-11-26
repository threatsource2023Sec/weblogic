package weblogic.diagnostics.lifecycle;

import weblogic.diagnostics.archive.DataRetirementScheduler;

public class DataRetirementLifecycleImpl implements DiagnosticComponentLifecycle {
   private static DataRetirementLifecycleImpl singleton = new DataRetirementLifecycleImpl();
   private DataRetirementScheduler dataRetirementScheduler;

   public static final DiagnosticComponentLifecycle getInstance() {
      return singleton;
   }

   public int getStatus() {
      return 4;
   }

   public void initialize() throws DiagnosticComponentLifecycleException {
      this.dataRetirementScheduler = DataRetirementScheduler.getInstance();
      this.dataRetirementScheduler.start();
   }

   public void enable() throws DiagnosticComponentLifecycleException {
      this.dataRetirementScheduler.start();
   }

   public void disable() throws DiagnosticComponentLifecycleException {
      this.dataRetirementScheduler.stop();
   }
}
