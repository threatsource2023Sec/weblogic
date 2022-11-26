package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("JDBC Transaction Is Same RM")
@Name("com.oracle.weblogic.jdbc.JDBCTransactionIsSameRMEvent")
@Description("This covers calls to isSameRM for a datasource or resource")
@Category({"WebLogic Server", "JDBC"})
public class JDBCTransactionIsSameRMEvent extends JDBCTransactionBaseEvent {
}
