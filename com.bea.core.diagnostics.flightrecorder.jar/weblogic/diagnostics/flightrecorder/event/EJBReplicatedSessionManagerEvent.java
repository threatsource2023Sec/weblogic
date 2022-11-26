package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.UseConstantPool;
import com.oracle.jrockit.jfr.ValueDefinition;

@EventDefinition(
   name = "EJB Replicated Session Manager",
   description = "Timed event corresponding to the replicated session manager operation",
   path = "wls/EJB/EJB_Replicated_Session_Manager",
   thread = true
)
public class EJBReplicatedSessionManagerEvent extends BaseTimedEvent {
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "subsystem",
      description = "The subsystem ID",
      relationKey = "http://www.oracle.com/wls/EJB"
   )
   protected String subsystem = "EJB";

   public String getSubsystem() {
      return this.subsystem;
   }

   public void setSubsystem(String subsystem) {
      this.subsystem = subsystem;
   }
}
