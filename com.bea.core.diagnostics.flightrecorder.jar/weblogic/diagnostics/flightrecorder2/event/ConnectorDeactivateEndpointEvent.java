package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Connector Deactivate Endpoint")
@Description("Connector endpoint deactivation")
@Category({"WebLogic Server", "Connector"})
@Name("com.oracle.weblogic.connector.ConnectorDeactivateEndpointEvent")
public class ConnectorDeactivateEndpointEvent extends ConnectorEndpointBaseEvent {
}
