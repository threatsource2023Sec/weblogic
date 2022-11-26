package weblogic.servlet.logging;

public final class NullLogField implements LogField {
   NullLogField() {
   }

   public void logField(HttpAccountingInfo metrics, FormatStringBuffer buff) {
      buff.appendValueOrDash((String)null);
   }
}
