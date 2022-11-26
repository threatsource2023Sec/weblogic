package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "JDBC Transaction Start",
   description = "This covers calls to start for a datasource or resource",
   path = "wls/JDBC/JDBC_Transaction_Start",
   thread = true
)
public class JDBCTransactionStartEvent extends JDBCTransactionBaseEvent {
}
