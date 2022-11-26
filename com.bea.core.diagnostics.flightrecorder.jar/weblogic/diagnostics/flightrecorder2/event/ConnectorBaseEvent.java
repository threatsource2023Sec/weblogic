package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;
import weblogic.diagnostics.flightrecorder2.impl.RelationKey;

@Label("Connector Base Event")
@Description("Connector related event information")
@Category({"WebLogic Server", "Connector"})
@Name("com.oracle.weblogic.connector.ConnectorBaseEvent")
public abstract class ConnectorBaseEvent extends BaseEvent {
   @Label("Subsystem")
   @Description("The subsystem ID")
   @RelationKey("http://www.oracle.com/wls/Connector")
   protected String subsystem = "Connector";

   public String getSubsystem() {
      return this.subsystem;
   }

   public void setSubsystem(String subsystem) {
      this.subsystem = subsystem;
   }
}
