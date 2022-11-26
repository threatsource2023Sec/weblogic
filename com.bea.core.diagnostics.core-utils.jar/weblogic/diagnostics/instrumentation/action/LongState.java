package weblogic.diagnostics.instrumentation.action;

import weblogic.diagnostics.instrumentation.DiagnosticActionState;

public final class LongState implements DiagnosticActionState {
   private long value;

   public void setValue(long value) {
      this.value = value;
   }

   public long getValue() {
      return this.value;
   }
}
