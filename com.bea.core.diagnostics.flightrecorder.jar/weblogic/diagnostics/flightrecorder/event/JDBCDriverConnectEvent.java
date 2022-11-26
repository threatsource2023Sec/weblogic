package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "JDBC Driver Connect",
   description = "This covers the driver connect, including the elapsed time",
   path = "wls/JDBC/JDBC_Driver_Connect",
   thread = true
)
public class JDBCDriverConnectEvent extends JDBCBaseTimedEvent {
}
