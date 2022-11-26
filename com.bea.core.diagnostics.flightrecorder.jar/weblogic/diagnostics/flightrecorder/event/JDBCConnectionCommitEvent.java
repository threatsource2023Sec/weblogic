package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "JDBC Connection Commit",
   description = "This covers the connection commit, including the elapsed time",
   path = "wls/JDBC/JDBC_Connection_Commit",
   thread = true
)
public class JDBCConnectionCommitEvent extends JDBCBaseTimedEvent {
}
