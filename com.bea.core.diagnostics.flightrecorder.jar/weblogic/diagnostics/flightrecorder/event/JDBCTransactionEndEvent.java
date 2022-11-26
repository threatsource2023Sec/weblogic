package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "JDBC Transaction End",
   description = "This covers calls to end for a datasource or resource",
   path = "wls/JDBC/JDBC_Transaction_End",
   thread = true
)
public class JDBCTransactionEndEvent extends JDBCTransactionBaseEvent {
}
