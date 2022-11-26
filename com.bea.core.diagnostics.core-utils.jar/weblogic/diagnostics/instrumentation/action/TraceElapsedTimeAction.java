package weblogic.diagnostics.instrumentation.action;

import weblogic.diagnostics.instrumentation.AbstractDiagnosticAction;
import weblogic.diagnostics.instrumentation.AroundDiagnosticAction;
import weblogic.diagnostics.instrumentation.DiagnosticActionState;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.EventQueue;
import weblogic.diagnostics.instrumentation.InstrumentationEvent;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.utils.time.Timer;

public final class TraceElapsedTimeAction extends AbstractDiagnosticAction implements AroundDiagnosticAction {
   public TraceElapsedTimeAction() {
      this.setType("TraceElapsedTimeAction");
   }

   public String[] getAttributeNames() {
      return null;
   }

   public DiagnosticActionState createState() {
      return new TraceElapsedTimeActionState();
   }

   public void preProcess(JoinPoint jp, DiagnosticActionState actionState) {
      TraceElapsedTimeActionState state = (TraceElapsedTimeActionState)actionState;
      Timer timer = Timer.createTimer();
      state.setValue(timer.timestamp());
      DiagnosticMonitor mon = this.getDiagnosticMonitor();
      if (mon != null) {
         InstrumentationEvent event = this.createInstrumentationEvent(jp, false);
         if (event == null) {
            return;
         }

         event.setEventType(this.getType() + "-Before-" + state.getId());
         EventQueue.getInstance().enqueue(event);
      }

   }

   public void postProcess(JoinPoint jp, DiagnosticActionState actionState) {
      InstrumentationEvent event = this.createInstrumentationEvent(jp, false);
      if (event != null) {
         TraceElapsedTimeActionState state = (TraceElapsedTimeActionState)actionState;
         Timer timer = Timer.createTimer();
         event.setPayload(new Long(timer.timestamp() - state.getValue()));
         event.setEventType(this.getType() + "-After-" + state.getId());
         EventQueue.getInstance().enqueue(event);
      }
   }

   private static class TraceElapsedTimeActionState implements DiagnosticActionState {
      private static int seqNum;
      private int id = genId();
      private long value;

      TraceElapsedTimeActionState() {
      }

      int getId() {
         return this.id;
      }

      long getValue() {
         return this.value;
      }

      void setValue(long value) {
         this.value = value;
      }

      private static synchronized int genId() {
         ++seqNum;
         return seqNum;
      }
   }
}
