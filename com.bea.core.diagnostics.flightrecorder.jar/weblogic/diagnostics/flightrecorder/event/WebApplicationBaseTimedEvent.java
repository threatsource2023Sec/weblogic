package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.UseConstantPool;
import com.oracle.jrockit.jfr.ValueDefinition;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@EventDefinition(
   name = "Web Application Base Timed",
   description = "This defines the values common to all Web Application events",
   path = "wls/Servlet/WebApp_Base_Timed",
   thread = true
)
public class WebApplicationBaseTimedEvent extends BaseTimedEvent implements WebApplicationEventInfo {
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Subsystem",
      description = "The subsystem ID",
      relationKey = "http://www.oracle.com/wls/Servlet"
   )
   protected String subsystem = "Servlet";
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Module Name",
      description = "The module name",
      relationKey = "http://www.oracle.com/wls/Servlet/moduleName"
   )
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
}
