package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;
import weblogic.diagnostics.flightrecorder2.impl.RelationKey;

@Label("EJB Timer Manager")
@Name("com.oracle.weblogic.ejb.EJBTimerManagerEvent")
@Description("Timed event corresponding to the EJB timer manager operation")
@Category({"WebLogic Server", "EJB"})
public class EJBTimerManagerEvent extends BaseEvent {
   @Label("subsystem")
   @Description("The subsystem ID")
   @RelationKey("http://www.oracle.com/wls/EJB")
   protected String subsystem = "EJB";

   public String getSubsystem() {
      return this.subsystem;
   }

   public void setSubsystem(String subsystem) {
      this.subsystem = subsystem;
   }

   public boolean isEventTimed() {
      return true;
   }
}
