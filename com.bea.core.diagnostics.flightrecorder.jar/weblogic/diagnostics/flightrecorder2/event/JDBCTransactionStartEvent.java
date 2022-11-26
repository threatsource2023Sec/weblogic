package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("JDBC Transaction Start")
@Name("com.oracle.weblogic.jdbc.JDBCTransactionStartEvent")
@Description("This covers calls to start for a datasource or resource")
@Category({"WebLogic Server", "JDBC"})
public class JDBCTransactionStartEvent extends JDBCTransactionBaseEvent {
}
