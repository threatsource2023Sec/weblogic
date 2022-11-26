package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Servlet Filter",
   description = "This event covers the servlet filter doFilter, including the elapsed time",
   path = "wls/Servlet/Servlet_Filter",
   thread = true
)
public class ServletFilterEvent extends ServletBaseTimedEvent {
}
