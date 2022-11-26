package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("JDBC Connection Prepare")
@Name("com.oracle.weblogic.jdbc.JDBCConnectionPrepareEvent")
@Description("This covers calls to prepareStatement and prepareCall, including the elapsed time")
@Category({"WebLogic Server", "JDBC"})
public class JDBCConnectionPrepareEvent extends JDBCBaseEvent {
}
