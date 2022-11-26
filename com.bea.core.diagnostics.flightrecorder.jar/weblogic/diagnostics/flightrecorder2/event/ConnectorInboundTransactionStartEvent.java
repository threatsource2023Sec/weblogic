package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Connector Inbound Transaction Start")
@Description("Connector information")
@Category({"WebLogic Server", "Connector"})
@Name("com.oracle.weblogic.diagnostics.ConnectorInboundTransactionStartEvent")
public class ConnectorInboundTransactionStartEvent extends ConnectorTransactionBaseEvent {
}
