package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("JDBC Transaction End")
@Name("com.oracle.weblogic.jdbc.JDBCTransactionEndEvent")
@Description("This covers calls to end for a datasource or resource")
@Category({"WebLogic Server", "JDBC"})
public class JDBCTransactionEndEvent extends JDBCTransactionBaseEvent {
}
