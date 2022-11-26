package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.UseConstantPool;
import com.oracle.jrockit.jfr.ValueDefinition;

@EventDefinition(
   name = "EJB Timer Manager",
   description = "Timed event corresponding to the EJB timer manager operation",
   path = "wls/EJB/EJB_Timer_Manager",
   thread = true
)
public class EJBTimerManagerEvent extends BaseTimedEvent {
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
