package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "JDBC Transaction Is Same RM",
   description = "This covers calls to isSameRM for a datasource or resource",
   path = "wls/JDBC/JDBC_Transaction_Is_Same_RM",
   thread = true
)
public class JDBCTransactionIsSameRMEvent extends JDBCTransactionBaseEvent {
}
