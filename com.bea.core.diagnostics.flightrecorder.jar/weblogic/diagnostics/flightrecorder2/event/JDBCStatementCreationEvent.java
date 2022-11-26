package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("JDBC Statement Creation")
@Name("com.oracle.weblogic.jdbc.JDBCStatementCreationEvent")
@Description("This events covers the makePreparedStatement and makeCallableStatement calls, including the elapsed time")
@Category({"WebLogic Server", "JDBC"})
public class JDBCStatementCreationEvent extends JDBCBaseEvent {
}
