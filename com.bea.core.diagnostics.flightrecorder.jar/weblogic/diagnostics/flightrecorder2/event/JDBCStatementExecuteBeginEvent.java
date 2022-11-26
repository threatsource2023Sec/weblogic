package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("JDBC Statement Execute Begin")
@Name("com.oracle.weblogic.jdbc.JDBCStatementExecuteBeginEvent")
@Description("This covers execute* calls, and shows the start time. This is generated in conjunction with a timed event, but this event can show long running in-flight calls that are in progress when a JFR snaphot is captured")
@Category({"WebLogic Server", "JDBC"})
public class JDBCStatementExecuteBeginEvent extends JDBCBaseEvent {
   public JDBCStatementExecuteBeginEvent(JDBCStatementExecuteEvent timedEvent) {
      super(timedEvent);
   }

   public boolean isEventTimed() {
      return false;
   }
}
