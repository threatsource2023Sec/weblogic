package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "JDBC Transaction Commit",
   description = "This covers calls to commit for a datasource or resource",
   path = "wls/JDBC/JDBC_Transaction_Commit",
   thread = true
)
public class JDBCTransactionCommitEvent extends JDBCTransactionBaseEvent {
}
