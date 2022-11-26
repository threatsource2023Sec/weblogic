package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "JDBC Connection Close",
   description = "This covers the connection close, including the elapsed time",
   path = "wls/JDBC/JDBC_Connection_Close",
   thread = true
)
public class JDBCConnectionCloseEvent extends JDBCBaseTimedEvent {
}
