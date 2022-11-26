package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Connector Outbound Transaction Commit")
@Description("Generated for a successful outbound transaction commit event")
@Category({"WebLogic Server", "Connector"})
@Name("com.oracle.weblogic.connector.ConnectorOutboundTransactionCommitEvent")
public class ConnectorOutboundTransactionCommitEvent extends ConnectorTransactionBaseEvent {
}
