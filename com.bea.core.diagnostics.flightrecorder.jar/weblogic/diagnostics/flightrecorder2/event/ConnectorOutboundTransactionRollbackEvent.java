package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Connector Outbound Transaction Rollback")
@Description("Generated on a connector outbound transaction rollback")
@Category({"WebLogic Server", "Connector"})
@Name("com.oracle.weblogic.connector.ConnectorOutboundTransactionRollbackEvent")
public class ConnectorOutboundTransactionRollbackEvent extends ConnectorTransactionBaseEvent {
}
