package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;
import weblogic.utils.PropertyHelper;

@Label("Servlet Request Run")
@Name("com.oracle.weblogic.servlet.ServletRequestRunEvent")
@Description("This event covers the servlet request work thread run, including the elapsed time")
@Category({"WebLogic Server", "Servlet"})
public class ServletRequestRunEvent extends ServletBaseEvent {
   private static final boolean INFLIGHT_ENABLED = !PropertyHelper.getBoolean("weblogic.diagnostics.flightrecorder.ServletInflightDisabled");

   public void generateInFlight() {
      (new ServletRequestRunBeginEvent(this)).commit();
   }

   public boolean willGenerateInFlight() {
      return INFLIGHT_ENABLED;
   }
}
