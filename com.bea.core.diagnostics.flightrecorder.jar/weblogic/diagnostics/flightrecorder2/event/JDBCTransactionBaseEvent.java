package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;
import weblogic.diagnostics.flightrecorder.event.StackTraced;
import weblogic.diagnostics.flightrecorder2.impl.RelationKey;

@Label("JDBC Transaction Base")
@Name("com.oracle.weblogic.jdbc.JDBCTransactionBaseEvent")
@Description("Base for JDBC Transaction eventse")
@Category({"WebLogic Server", "JDBC"})
public abstract class JDBCTransactionBaseEvent extends BaseEvent implements StackTraced {
   @Label("Subsystem")
   @Description("The subsystem ID")
   @RelationKey("http://www.oracle.com/wls/JDBC")
   protected String subsystem = "JDBC";

   public String getSubsystem() {
      return this.subsystem;
   }

   public void setSubsystem(String subsystem) {
      this.subsystem = subsystem;
   }

   public boolean isEventTimed() {
      return false;
   }
}
