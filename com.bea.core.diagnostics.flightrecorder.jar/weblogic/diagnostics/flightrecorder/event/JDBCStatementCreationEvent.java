package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "JDBC Statement Creation",
   description = "This events covers the makePreparedStatement and makeCallableStatement calls, including the elapsed time",
   path = "wls/JDBC/JDBC_Statement_Creation",
   thread = true
)
public class JDBCStatementCreationEvent extends JDBCBaseTimedEvent {
}
