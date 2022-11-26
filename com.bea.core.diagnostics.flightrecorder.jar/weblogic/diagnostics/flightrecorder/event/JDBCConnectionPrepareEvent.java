package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "JDBC Connection Prepare",
   description = "This covers calls to prepareStatement and prepareCall, including the elapsed time",
   path = "wls/JDBC/JDBC_Connection_Prepare",
   thread = true
)
public class JDBCConnectionPrepareEvent extends JDBCBaseTimedEvent {
}
