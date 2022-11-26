package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("JDBC Connection Commit")
@Name("com.oracle.weblogic.jdbc.JDBCConnectionCommitEvent")
@Description("This covers the connection commit, including the elapsed time")
@Category({"WebLogic Server", "JDBC"})
public class JDBCConnectionCommitEvent extends JDBCBaseEvent {
}
