package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("JDBC Driver Connect")
@Name("com.oracle.weblogic.jdbc.JDBCDriverConnectEvent")
@Description("This covers the driver connect, including the elapsed time")
@Category({"WebLogic Server", "JDBC"})
public class JDBCDriverConnectEvent extends JDBCBaseEvent {
}
