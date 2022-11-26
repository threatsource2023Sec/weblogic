package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("JDBC Transaction Commit")
@Name("com.oracle.weblogic.jdbc.JDBCTransactionCommitEvent")
@Description("This covers calls to commit for a datasource or resource")
@Category({"WebLogic Server", "JDBC"})
public class JDBCTransactionCommitEvent extends JDBCTransactionBaseEvent {
}
