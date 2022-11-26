package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("JDBC Statement Execute")
@Name("com.oracle.weblogic.jdbc.JDBCStatementExecuteEvent")
@Description("This covers execute* calls, including the elapsed time")
@Category({"WebLogic Server", "JDBC"})
public class JDBCStatementExecuteEvent extends JDBCBaseEvent {
   public void generateInFlight() {
      (new JDBCStatementExecuteBeginEvent(this)).commit();
   }

   public boolean willGenerateInFlight() {
      return true;
   }
}
