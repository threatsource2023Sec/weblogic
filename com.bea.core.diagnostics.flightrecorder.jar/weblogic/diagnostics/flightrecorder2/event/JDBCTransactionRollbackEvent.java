package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("JDBC Transaction Rollback")
@Name("com.oracle.weblogic.jdbc.JDBCTransactionRollbackEvent")
@Description("This covers calls to rollback for a datasource or resource")
@Category({"WebLogic Server", "JDBC"})
public class JDBCTransactionRollbackEvent extends JDBCTransactionBaseEvent {
}
