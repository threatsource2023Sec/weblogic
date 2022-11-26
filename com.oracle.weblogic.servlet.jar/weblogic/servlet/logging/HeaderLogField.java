package weblogic.servlet.logging;

final class HeaderLogField implements LogField {
   private int prefix;
   private String header;

   HeaderLogField(String pfx, String header) {
      this.header = header;
      if (pfx.startsWith("c")) {
         this.prefix = 3;
      } else if (pfx.startsWith("s")) {
         this.prefix = 4;
      } else {
         this.prefix = 0;
      }

   }

   public void logField(HttpAccountingInfo metrics, FormatStringBuffer buff) {
      String header_val = null;
      if (this.header != null) {
         switch (this.prefix) {
            case 0:
            case 1:
            case 2:
            default:
               break;
            case 3:
               header_val = metrics.getHeader(this.header);
               break;
            case 4:
               header_val = metrics.getResponseHeader(this.header);
         }
      }

      buff.appendQuotedValueOrDash(header_val);
   }
}
