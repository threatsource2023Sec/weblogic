package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;
import weblogic.diagnostics.flightrecorder.event.ServletEventInfo;
import weblogic.diagnostics.flightrecorder.event.ServletEventInfoHelper;
import weblogic.diagnostics.flightrecorder2.impl.RelationKey;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@Label("Servlet Base")
@Name("com.oracle.weblogic.servlet.ServletBaseEvent")
@Description("This defines the values common to all Servlet events")
@Category({"WebLogic Server", "Servlet"})
public abstract class ServletBaseEvent extends BaseEvent implements ServletEventInfo {
   @Label("Subsystem")
   @Description("The subsystem ID")
   @RelationKey("http://www.oracle.com/wls/Servlet")
   String subsystem = "Servlet";
   @Label("URI")
   @Description("The URI")
   @RelationKey("http://www.oracle.com/wls/Servlet/uri")
   String uri = null;
   @Label("Servlet Name")
   @Description("Servlet name")
   @RelationKey("http://www.oracle.com/wls/Servlet/servletName")
   String servletName = null;
   private boolean hasServletName = false;

   public String getSubsystem() {
      return this.subsystem;
   }

   public void setSubsystem(String subsystem) {
      this.subsystem = subsystem;
   }

   public void populateExtensions(Object retVal, Object[] args, DynamicJoinPoint djp, boolean isAfter) {
      ServletEventInfoHelper.populateExtensions((Object)null, args, this);
   }

   public String getUri() {
      return this.uri;
   }

   public void setUri(String uri) {
      this.uri = uri;
   }

   public String getServletName() {
      return this.servletName;
   }

   public void setServletName(String servletName) {
      this.servletName = servletName;
      this.hasServletName = true;
   }

   public boolean hasServletName() {
      return this.hasServletName;
   }

   protected ServletBaseEvent() {
   }

   protected ServletBaseEvent(ServletBaseEvent timedEvent) {
      super(timedEvent);
      this.uri = timedEvent.uri;
      this.hasServletName = timedEvent.hasServletName();
      if (this.hasServletName) {
         this.servletName = timedEvent.servletName;
      }

   }

   public boolean isEventTimed() {
      return true;
   }
}
