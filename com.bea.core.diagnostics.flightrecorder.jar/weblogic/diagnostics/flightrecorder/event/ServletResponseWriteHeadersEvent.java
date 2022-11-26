package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Servlet Response Write Headers",
   description = "This event covers the execution of the servlet response writeHeaders call, including the elapsed time",
   path = "wls/Servlet/Servlet_Response_Write_Headers",
   thread = true
)
public class ServletResponseWriteHeadersEvent extends ServletBaseTimedEvent {
}
