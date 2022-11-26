package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Servlet Async Action",
   description = "This event covers the execution of the async servlet actions (doRequest, doResponse, doTimeout), including the elapsed time",
   path = "wls/Servlet/Servlet_Async_Action",
   thread = true
)
public class ServletAsyncActionEvent extends BaseTimedEvent {
}
