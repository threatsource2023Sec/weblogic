package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "JDBC Connection Rollback",
   description = "This covers the connection rollback, including the elapsed time",
   path = "wls/JDBC/JDBC_Connection_Rollback",
   thread = true
)
public class JDBCConnectionRollbackEvent extends JDBCBaseTimedEvent {
}
