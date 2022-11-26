package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "JDBC Connection Release",
   description = "This covers releasing the connection back to the pool, including the elapsed time",
   path = "wls/JDBC/JDBC_Connection_Release",
   thread = true
)
public class JDBCConnectionReleaseEvent extends JDBCBaseTimedEvent {
}
