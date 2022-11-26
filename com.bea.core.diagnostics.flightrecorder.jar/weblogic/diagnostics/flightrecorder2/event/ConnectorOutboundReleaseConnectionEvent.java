package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Connector Outbound Release Connection")
@Description("Connector connection release event")
@Category({"WebLogic Server", "Connector"})
@Name("com.oracle.weblogic.connector.ConnectorOutboundReleaseConnectionEvent")
public class ConnectorOutboundReleaseConnectionEvent extends ConnectorTransactionBaseEvent {
}
