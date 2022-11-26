package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;
import weblogic.diagnostics.flightrecorder2.impl.RelationKey;

@Label("EJB Replicated Session Manager")
@Name("com.oracle.weblogic.ejb.EJBReplicatedSessionManagerEvent")
@Description("Timed event corresponding to the replicated session manager operation")
@Category({"WebLogic Server", "EJB"})
public class EJBReplicatedSessionManagerEvent extends BaseEvent {
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
