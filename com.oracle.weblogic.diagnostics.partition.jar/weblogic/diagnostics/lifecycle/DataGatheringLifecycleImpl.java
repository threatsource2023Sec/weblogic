package weblogic.diagnostics.lifecycle;

public class DataGatheringLifecycleImpl implements DiagnosticComponentLifecycle {
   private static DataGatheringLifecycleImpl singleton = new DataGatheringLifecycleImpl();

   public static final DiagnosticComponentLifecycle getInstance() {
      return singleton;
   }

   public int getStatus() {
      return 4;
   }

   public void initialize() throws DiagnosticComponentLifecycleException {
   }

   public void enable() throws DiagnosticComponentLifecycleException {
   }

   public void disable() throws DiagnosticComponentLifecycleException {
   }
}
