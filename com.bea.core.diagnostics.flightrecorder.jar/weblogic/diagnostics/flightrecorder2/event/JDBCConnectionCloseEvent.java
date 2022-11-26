package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("JDBC Connection Close")
@Name("com.oracle.weblogic.jdbc.JDBCConnectionCloseEvent")
@Description("This covers the connection close, including the elapsed time")
@Category({"WebLogic Server", "JDBC"})
public class JDBCConnectionCloseEvent extends JDBCBaseEvent {
}
