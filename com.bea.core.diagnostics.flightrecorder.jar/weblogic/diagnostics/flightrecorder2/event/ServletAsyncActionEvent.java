package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Servlet Async Action")
@Name("com.oracle.weblogic.servlet.ServletAsyncActionEvent")
@Description("This event covers the execution of the async servlet actions (doRequest, doResponse, doTimeout), including the elapsed time")
@Category({"WebLogic Server", "Servlet"})
public class ServletAsyncActionEvent extends BaseEvent {
}
