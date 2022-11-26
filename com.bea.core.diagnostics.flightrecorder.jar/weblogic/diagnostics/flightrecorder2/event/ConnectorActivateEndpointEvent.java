package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Connector_Activate_Endpoint")
@Description("Connector endpoint activation")
@Category({"WebLogic Server", "Connector"})
@Name("com.oracle.weblogic.connector.ConnectorActivateEndpointEvent")
public class ConnectorActivateEndpointEvent extends ConnectorEndpointBaseEvent {
}
