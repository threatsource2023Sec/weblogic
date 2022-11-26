package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "JDBC Transaction Rollback",
   description = "This covers calls to rollback for a datasource or resource",
   path = "wls/JDBC/JDBC_Transaction_Rollback",
   thread = true
)
public class JDBCTransactionRollbackEvent extends JDBCTransactionBaseEvent {
}
