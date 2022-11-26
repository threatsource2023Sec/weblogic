package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@EventDefinition(
   name = "Servlet Check Access",
   description = "This event covers the servlet access check, including the elapsed time",
   path = "wls/Servlet/Servlet_Check_Access",
   thread = true
)
public class ServletCheckAccessEvent extends ServletBaseTimedEvent {
   public void populateExtensions(Object retVal, Object[] args, DynamicJoinPoint djp, boolean isAfter) {
      if (!isAfter) {
         super.populateExtensions((Object)null, args, djp, isAfter);
      } else if (retVal == null || !(retVal instanceof Boolean) || (Boolean)retVal) {
         this.setThrottled(true);
      }
   }
}
