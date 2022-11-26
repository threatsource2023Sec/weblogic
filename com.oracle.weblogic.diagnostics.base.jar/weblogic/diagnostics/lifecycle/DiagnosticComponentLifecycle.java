package weblogic.diagnostics.lifecycle;

public interface DiagnosticComponentLifecycle {
   void initialize() throws DiagnosticComponentLifecycleException;

   void enable() throws DiagnosticComponentLifecycleException;

   void disable() throws DiagnosticComponentLifecycleException;

   int getStatus();
}
