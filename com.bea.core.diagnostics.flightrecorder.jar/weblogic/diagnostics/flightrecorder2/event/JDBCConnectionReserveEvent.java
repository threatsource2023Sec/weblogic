package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("JDBC Connection Reserve")
@Name("com.oracle.weblogic.jdbc.JDBCConnectionReserveEvent")
@Description("This covers the reserve of a connection from the pool, including the elapsed time")
@Category({"WebLogic Server", "JDBC"})
public class JDBCConnectionReserveEvent extends JDBCBaseEvent {
}
