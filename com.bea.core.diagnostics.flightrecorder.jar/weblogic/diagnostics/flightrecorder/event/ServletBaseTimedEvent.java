package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.UseConstantPool;
import com.oracle.jrockit.jfr.ValueDefinition;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@EventDefinition(
   name = "Servlet Base Timed",
   description = "This defines the values common to all Servlet events",
   path = "wls/Servlet/Servlet_Base_Timed",
   thread = true
)
public class ServletBaseTimedEvent extends BaseTimedEvent implements ServletEventInfo {
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
      name = "URI",
      description = "The URI",
      relationKey = "http://www.oracle.com/wls/Servlet/uri"
   )
   protected String uri = null;
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Servlet Name",
      description = "Servlet name",
      relationKey = "http://www.oracle.com/wls/Servlet/servletName"
   )
   protected String servletName = null;
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
}
