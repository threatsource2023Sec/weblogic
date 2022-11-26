package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("JDBC Transaction Get XA Resource")
@Name("com.oracle.weblogic.jdbc.JDBCTransactionGetXAResourceEvent")
@Description("This covers calls to getXAResource for a datasource or resource")
@Category({"WebLogic Server", "JDBC"})
public class JDBCTransactionGetXAResourceEvent extends JDBCTransactionBaseEvent {
}
