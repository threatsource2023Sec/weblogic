package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.UseConstantPool;
import com.oracle.jrockit.jfr.ValueDefinition;

@EventDefinition(
   name = "Servlet Stale Resource",
   description = "This event is generated when the stale resource check determines the resource is stale. Including the elapsed time to make the determination",
   path = "wls/Servlet/Servlet_Stale_Resource",
   thread = true
)
public class ServletStaleResourceEvent extends BaseTimedEvent implements ServletStaleResourceEventInfo {
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "resource",
      description = "The resource",
      relationKey = "http://www.oracle.com/wls/Servlet/resource"
   )
   protected String resource = null;

   public String getResource() {
      return this.resource;
   }

   public void setResource(String resource) {
      this.resource = resource;
   }
}
