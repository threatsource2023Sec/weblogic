package weblogic.servlet.logging;

import weblogic.diagnostics.context.Correlation;
import weblogic.diagnostics.context.CorrelationFactory;

public final class ContextLogField implements LogField {
   private int type;

   ContextLogField(String pfx, String id) {
      if ("ecid".equals(id)) {
         this.type = 13;
      } else if ("rid".equals(id)) {
         this.type = 14;
      } else {
         this.type = 0;
      }

   }

   public void logField(HttpAccountingInfo metrics, FormatStringBuffer buff) {
      Correlation ctx = null;
      switch (this.type) {
         case 0:
            buff.appendValueOrDash((String)null);
            break;
         case 13:
            ctx = CorrelationFactory.findOrCreateCorrelation();
            String ecid = ctx != null ? ctx.getECID() : null;
            buff.appendValueOrDash(ecid);
            break;
         case 14:
            ctx = CorrelationFactory.findOrCreateCorrelation();
            String rid = ctx != null ? ctx.getRID() : null;
            buff.appendValueOrDash(rid);
      }

   }
}
