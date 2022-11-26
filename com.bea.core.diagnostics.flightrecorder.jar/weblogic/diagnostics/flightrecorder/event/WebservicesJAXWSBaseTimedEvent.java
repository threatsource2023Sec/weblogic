package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.UseConstantPool;
import com.oracle.jrockit.jfr.ValueDefinition;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@EventDefinition(
   name = "Webservices JAXWS Base Timed",
   description = "Webservices JAX-WS information with timing",
   path = "wls/Webservices/JAXWS/Webservices_JAXWS_Base_Timed",
   thread = true
)
public class WebservicesJAXWSBaseTimedEvent extends BaseTimedEvent implements WebservicesJAXWSEventInfo {
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Subsystem",
      description = "The subsystem ID",
      relationKey = "http://www.oracle.com/wls/Webservices"
   )
   protected String subsystem = "Webservices";
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "URI",
      description = "The URI",
      relationKey = "http://www.oracle.com/wls/Servlet/uri"
   )
   protected String uri = null;

   public String getSubsystem() {
      return this.subsystem;
   }

   public void setSubsystem(String subsystem) {
      this.subsystem = subsystem;
   }

   public void populateExtensions(Object retVal, Object[] args, DynamicJoinPoint djp, boolean isAfter) {
      WebservicesJAXWSEventInfoHelper.populateExtensions(retVal, args, this);
   }

   public String getUri() {
      return this.uri;
   }

   public void setUri(String uri) {
      this.uri = uri;
   }
}
