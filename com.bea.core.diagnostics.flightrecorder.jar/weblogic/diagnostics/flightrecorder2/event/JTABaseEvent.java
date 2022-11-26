package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;
import weblogic.diagnostics.flightrecorder.event.StackTraced;
import weblogic.diagnostics.flightrecorder2.impl.RelationKey;

@Label("JTA Base")
@Name("com.oracle.weblogic.jta.JTABaseEvent")
@Description("JTA information")
@Category({"WebLogic Server", "JTA"})
public abstract class JTABaseEvent extends BaseEvent implements StackTraced {
   @Label("Subsystem")
   @Description("The subsystem ID")
   @RelationKey("http://www.oracle.com/wls/JTA")
   protected String subsystem = "JTA";

   public String getSubsystem() {
      return this.subsystem;
   }

   public void setSubsystem(String subsystem) {
      this.subsystem = subsystem;
   }
}
