package weblogic.diagnostics.instrumentation;

import java.io.Serializable;

public interface DiagnosticAction extends Serializable {
   String getType();

   void setType(String var1);

   boolean requiresArgumentsCapture();

   DiagnosticMonitor getDiagnosticMonitor();

   void setDiagnosticMonitor(DiagnosticMonitor var1);
}
