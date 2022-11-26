package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("JDBC Data Source Get Connection")
@Name("com.oracle.weblogic.jdbc.JDBCDataSourceGetConnectionEvent")
@Description("This covers the Data Source level getConnection, including the elapsed time")
@Category({"WebLogic Server", "JDBC"})
public class JDBCDataSourceGetConnectionEvent extends JDBCBaseEvent {
}
