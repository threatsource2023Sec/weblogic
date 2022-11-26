package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "JDBC Statement Execute",
   description = "This covers execute* calls, including the elapsed time",
   path = "wls/JDBC/JDBC_Statement_Execute",
   thread = true
)
public class JDBCStatementExecuteEvent extends JDBCBaseTimedEvent {
   public void generateInFlight() {
      (new JDBCStatementExecuteBeginEvent(this)).commit();
   }

   public boolean willGenerateInFlight() {
      return true;
   }
}
