package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.UseConstantPool;
import com.oracle.jrockit.jfr.ValueDefinition;

@EventDefinition(
   name = "Connector Base Event",
   description = "Connector related event information",
   path = "wls/Connector/Connector_Base",
   thread = true
)
public class ConnectorBaseEvent extends BaseInstantEvent {
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Subsystem",
      description = "The subsystem ID",
      relationKey = "http://www.oracle.com/wls/Connector"
   )
   protected String subsystem = "Connector";

   public String getSubsystem() {
      return this.subsystem;
   }

   public void setSubsystem(String subsystem) {
      this.subsystem = subsystem;
   }
}
