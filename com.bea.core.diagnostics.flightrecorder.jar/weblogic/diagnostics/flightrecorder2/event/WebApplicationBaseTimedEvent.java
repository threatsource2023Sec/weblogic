package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;
import weblogic.diagnostics.flightrecorder.event.WebApplicationEventInfo;
import weblogic.diagnostics.flightrecorder.event.WebApplicationEventInfoHelper;
import weblogic.diagnostics.flightrecorder2.impl.RelationKey;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@Label("Web Application Base Timed")
@Name("com.oracle.weblogic.servlet.WebApplicationBaseTimedEvent")
@Description("This defines the values common to all Web Application events")
@Category({"WebLogic Server", "Servlet"})
public abstract class WebApplicationBaseTimedEvent extends BaseEvent implements WebApplicationEventInfo {
   @Label("Subsystem")
   @Description("The subsystem ID")
   @RelationKey("http://www.oracle.com/wls/Servlet")
   protected String subsystem = "Servlet";
   @Label("Module Name")
   @Description("The module name")
   @RelationKey("http://www.oracle.com/wls/Servlet/moduleName")
   protected String moduleName = null;

   public String getSubsystem() {
      return this.subsystem;
   }

   public void setSubsystem(String subsystem) {
      this.subsystem = subsystem;
   }

   public void populateExtensions(Object retVal, Object[] args, DynamicJoinPoint djp, boolean isAfter) {
      WebApplicationEventInfoHelper.populateExtensions((Object)null, args, this);
   }

   public String getModuleName() {
      return this.moduleName;
   }

   public void setModuleName(String moduleName) {
      this.moduleName = moduleName;
   }

   public boolean isEventTimed() {
      return true;
   }
}
