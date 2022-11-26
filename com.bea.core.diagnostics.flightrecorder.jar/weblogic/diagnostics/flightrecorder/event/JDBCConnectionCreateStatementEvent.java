package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "JDBC Connection Create Statement",
   description = "This covers createStatement calls on the connection, including the elapsed time",
   path = "wls/JDBC/JDBC_Connection_Create_Statement",
   thread = true
)
public class JDBCConnectionCreateStatementEvent extends JDBCBaseTimedEvent {
}
