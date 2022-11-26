package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "JDBC Transaction Prepare",
   description = "This covers calls to prepare for a datasource or resource",
   path = "wls/JDBC/JDBC_Transaction_Prepare",
   thread = true
)
public class JDBCTransactionPrepareEvent extends JDBCTransactionBaseEvent {
}
