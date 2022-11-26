package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "JDBC Statement Execute Begin",
   description = "This covers execute* calls, and shows the start time. This is generated in conjunction with a timed event, but this event can show long running in-flight calls that are in progress when a JFR snaphot is captured",
   path = "wls/JDBC/JDBC_Statement_Execute_Begin",
   thread = true
)
public class JDBCStatementExecuteBeginEvent extends JDBCBaseInstantEvent {
   public JDBCStatementExecuteBeginEvent(JDBCStatementExecuteEvent timedEvent) {
      super(timedEvent);
   }
}
