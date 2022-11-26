package weblogic.diagnostics.instrumentation;

import weblogic.diagnostics.context.Correlation;
import weblogic.diagnostics.context.CorrelationFactory;
import weblogic.utils.PropertyHelper;

public abstract class AbstractDiagnosticAction implements DiagnosticAction {
   static final long serialVersionUID = 4064514881509087899L;
   private static final boolean noEventGen = PropertyHelper.getBoolean("weblogic.diagnostics.internal.noEventGen");
   private DiagnosticMonitor monitor;
   private String type;

   public final String getType() {
      return this.type;
   }

   public final void setType(String type) {
      this.type = type;
   }

   public boolean requiresArgumentsCapture() {
      return false;
   }

   public void setDiagnosticMonitor(DiagnosticMonitor monitor) {
      this.monitor = monitor;
   }

   public DiagnosticMonitor getDiagnosticMonitor() {
      return this.monitor;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj instanceof DiagnosticAction) {
         String type = ((DiagnosticAction)obj).getType();
         return this.getType().equals(type);
      } else {
         return false;
      }
   }

   protected InstrumentationEvent createInstrumentationEvent(JoinPoint jp, boolean showArgs) {
      if (noEventGen) {
         return null;
      } else {
         Correlation context = CorrelationFactory.findOrCreateCorrelation();
         InstrumentationEvent event = new InstrumentationEvent(this, jp, showArgs);
         if (context != null) {
            event.setContextId(context.getECID());
         }

         return event;
      }
   }
}
