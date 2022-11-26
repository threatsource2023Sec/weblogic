package weblogic.diagnostics.instrumentation;

public interface AroundDiagnosticAction extends DiagnosticAction {
   DiagnosticActionState createState();

   void preProcess(JoinPoint var1, DiagnosticActionState var2);

   void postProcess(JoinPoint var1, DiagnosticActionState var2);
}
