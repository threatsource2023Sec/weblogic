package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Connector Outbound Transaction Start")
@Description("This event indicates the start of a connector outbound transaction")
@Category({"WebLogic Server", "Connector"})
@Name("com.oracle.weblogic.connector.ConnectorOutboundTransactionStartEvent")
public class ConnectorOutboundTransactionStartEvent extends ConnectorTransactionBaseEvent {
}
