package weblogic.diagnostics.instrumentation;

public final class DisplayArgsReturnsActions extends AbstractDiagnosticAction implements AroundDiagnosticAction {
   public DisplayArgsReturnsActions() {
      this.setType("DisplayArgsReturnsActions");
   }

   public String[] getAttributeNames() {
      return null;
   }

   public DiagnosticActionState createState() {
      return new ArgsState();
   }

   public boolean requiresArgumentsCapture() {
      return true;
   }

   public void preProcess(JoinPoint jp, DiagnosticActionState actionState) {
      ArgsState state = (ArgsState)actionState;
      state.setArguments(((DynamicJoinPoint)jp).getArguments());
   }

   public void postProcess(JoinPoint jp, DiagnosticActionState actionState) {
      ArgsState state = (ArgsState)actionState;
      InstrumentationEvent event = this.createInstrumentationEvent(jp, true);
      if (event != null) {
         EventQueue.getInstance().enqueue(event);
      }
   }

   class ArgsState implements DiagnosticActionState {
      private Object[] args;

      void setArguments(Object[] args) {
         this.args = args;
      }

      Object[] getArguments() {
         return this.args;
      }
   }
}
