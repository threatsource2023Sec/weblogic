package weblogic.diagnostics.instrumentation.gathering;

import weblogic.diagnostics.instrumentation.ValueRenderer;

public class DeploymentStatusRenderer implements ValueRenderer {
   public Object render(Object inputObject) {
      if (inputObject != null && inputObject instanceof Integer) {
         int status = (Integer)inputObject;
         if (status == 0) {
            return "STATE_INIT";
         } else if (status == 1) {
            return "STATE_IN_PROGRESS";
         } else if (status == 2) {
            return "STATE_FAILED";
         } else if (status == 3) {
            return "STATE_SUCCESS";
         } else {
            return status == 4 ? "STATE_UNAVAILABLE" : null;
         }
      } else {
         return null;
      }
   }
}
