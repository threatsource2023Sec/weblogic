package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;
import weblogic.diagnostics.flightrecorder.event.WebservicesJAXWSEventInfo;
import weblogic.diagnostics.flightrecorder.event.WebservicesJAXWSEventInfoHelper;
import weblogic.diagnostics.flightrecorder2.impl.RelationKey;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@Label("Webservices JAXWS Base Timed")
@Name("com.oracle.weblogic.webservices.WebservicesJAXWSBaseTimedEvent")
@Description("Webservices JAX-WS information with timing")
@Category({"WebLogic Server", "Webservices", "JAXWS"})
public abstract class WebservicesJAXWSBaseTimedEvent extends BaseEvent implements WebservicesJAXWSEventInfo {
   @Label("Subsystem")
   @Description("The subsystem ID")
   @RelationKey("http://www.oracle.com/wls/Webservices")
   protected String subsystem = "Webservices";
   @Label("URI")
   @Description("The URI")
   @RelationKey("http://www.oracle.com/wls/Servlet/uri")
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

   public boolean isEventTimed() {
      return true;
   }
}
