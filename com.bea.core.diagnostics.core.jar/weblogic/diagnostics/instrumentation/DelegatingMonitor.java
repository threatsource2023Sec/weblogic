package weblogic.diagnostics.instrumentation;

public interface DelegatingMonitor extends StandardMonitor {
   DiagnosticAction[] getActions();

   String[] getCompatibleActionTypes();

   void addAction(DiagnosticAction var1) throws DuplicateActionException, IncompatibleActionException;

   void removeAction(DiagnosticAction var1) throws ActionNotFoundException;
}
