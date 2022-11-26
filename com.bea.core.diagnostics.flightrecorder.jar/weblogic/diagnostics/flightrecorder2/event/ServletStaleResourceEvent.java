package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;
import weblogic.diagnostics.flightrecorder.event.ServletStaleResourceEventInfo;
import weblogic.diagnostics.flightrecorder2.impl.RelationKey;

@Label("Servlet Stale Resource")
@Name("com.oracle.weblogic.servlet.ServletStaleResourceEvent")
@Description("This event is generated when the stale resource check determines the resource is stale. Including the elapsed time to make the determination")
@Category({"WebLogic Server", "Servlet"})
public class ServletStaleResourceEvent extends BaseEvent implements ServletStaleResourceEventInfo {
   @Label("resource")
   @Description("The resource")
   @RelationKey("http://www.oracle.com/wls/Servlet/resource")
   protected String resource = null;

   public String getResource() {
      return this.resource;
   }

   public void setResource(String resource) {
      this.resource = resource;
   }

   public boolean isEventTimed() {
      return true;
   }
}
