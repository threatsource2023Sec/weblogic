package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Connector Inbound Transaction Commit")
@Description("Connector inbound transaction commit event")
@Category({"WebLogic Server", "Connector"})
@Name("com.oracle.weblogic.connector.ConnectorInboundTransactionCommitEvent")
public class ConnectorInboundTransactionCommitEvent extends ConnectorTransactionBaseEvent {
}
