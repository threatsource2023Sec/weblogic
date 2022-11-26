package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("JDBC Transaction Prepare")
@Name("com.oracle.weblogic.jdbc.JDBCTransactionPrepareEvent")
@Description("This covers calls to prepare for a datasource or resource")
@Category({"WebLogic Server", "JDBC"})
public class JDBCTransactionPrepareEvent extends JDBCTransactionBaseEvent {
}
