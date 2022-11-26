package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;
import weblogic.utils.PropertyHelper;

@EventDefinition(
   name = "Servlet Request Run",
   description = "This event covers the servlet request work thread run, including the elapsed time",
   path = "wls/Servlet/Servlet_Request_Run",
   thread = true
)
public class ServletRequestRunEvent extends ServletBaseTimedEvent {
   private static final boolean INFLIGHT_ENABLED = !PropertyHelper.getBoolean("weblogic.diagnostics.flightrecorder.ServletInflightDisabled");

   public void generateInFlight() {
      (new ServletRequestRunBeginEvent(this)).commit();
   }

   public boolean willGenerateInFlight() {
      return INFLIGHT_ENABLED;
   }
}
