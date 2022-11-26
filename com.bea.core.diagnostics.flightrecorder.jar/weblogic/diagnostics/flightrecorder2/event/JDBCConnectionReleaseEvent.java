package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("JDBC Connection Release")
@Name("com.oracle.weblogic.jdbc.JDBCConnectionReleaseEvent")
@Description("This covers releasing the connection back to the pool, including the elapsed time")
@Category({"WebLogic Server", "JDBC"})
public class JDBCConnectionReleaseEvent extends JDBCBaseEvent {
}
