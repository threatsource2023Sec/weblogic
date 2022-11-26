package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("JDBC Connection Create Statement")
@Name("com.oracle.weblogic.jdbc.JDBCConnectionCreateStatementEvent")
@Description("This covers createStatement calls on the connection, including the elapsed time")
@Category({"WebLogic Server", "JDBC"})
public class JDBCConnectionCreateStatementEvent extends JDBCBaseEvent {
}
