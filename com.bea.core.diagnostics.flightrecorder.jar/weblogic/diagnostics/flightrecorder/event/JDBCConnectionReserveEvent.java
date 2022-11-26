package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "JDBC Connection Reserve",
   description = "This covers the reserve of a connection from the pool, including the elapsed time",
   path = "wls/JDBC/JDBC_Connection_Reserve",
   thread = true
)
public class JDBCConnectionReserveEvent extends JDBCBaseTimedEvent {
}
