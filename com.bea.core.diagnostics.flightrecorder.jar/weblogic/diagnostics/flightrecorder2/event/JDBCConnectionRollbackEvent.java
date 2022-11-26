package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("JDBC Connection Rollback")
@Name("com.oracle.weblogic.jdbc.JDBCConnectionRollbackEvent")
@Description("This covers the connection rollback, including the elapsed time")
@Category({"WebLogic Server", "JDBC"})
public class JDBCConnectionRollbackEvent extends JDBCBaseEvent {
}
