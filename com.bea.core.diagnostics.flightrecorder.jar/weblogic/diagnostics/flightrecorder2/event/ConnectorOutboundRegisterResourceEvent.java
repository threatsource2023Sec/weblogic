package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Connector Outbound Register Resource")
@Description("Connector resource registration event")
@Category({"WebLogic Server", "Connector"})
@Name("com.oracle.weblogic.connector.ConnectorOutboundRegisterResourceEvent")
public class ConnectorOutboundRegisterResourceEvent extends ConnectorTransactionBaseEvent {
}
