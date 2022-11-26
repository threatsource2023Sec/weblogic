package weblogic.diagnostics.instrumentation.gathering;

import weblogic.diagnostics.instrumentation.ValueRenderer;

public class JMSEventTypeStringRenderer implements ValueRenderer {
   private static final int LOG_CONSUMERCREATE = 1;
   private static final int LOG_CONSUMERDESTROY = 2;
   private static final String CONSUMERCREATE = "CREATE";
   private static final String CONSUMERDESTROY = "DESTROY";

   public Object render(Object inputObject) {
      if (inputObject != null && inputObject instanceof Integer) {
         int eventType = (Integer)inputObject;
         if (eventType == 1) {
            return "CREATE";
         } else {
            return eventType == 2 ? "DESTROY" : null;
         }
      } else {
         return null;
      }
   }
}
