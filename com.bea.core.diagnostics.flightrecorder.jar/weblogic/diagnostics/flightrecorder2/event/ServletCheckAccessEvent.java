package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@Label("Servlet Check Access")
@Name("com.oracle.weblogic.servlet.ServletCheckAccessEvent")
@Description("This event covers the servlet access check, including the elapsed time")
@Category({"WebLogic Server", "Servlet"})
public class ServletCheckAccessEvent extends ServletBaseEvent {
   public void populateExtensions(Object retVal, Object[] args, DynamicJoinPoint djp, boolean isAfter) {
      if (!isAfter) {
         super.populateExtensions((Object)null, args, djp, isAfter);
      } else if (retVal == null || !(retVal instanceof Boolean) || (Boolean)retVal) {
         this.setThrottled(true);
      }
   }
}
