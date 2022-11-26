package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.UseConstantPool;
import com.oracle.jrockit.jfr.ValueDefinition;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@EventDefinition(
   name = "EJB Base Timed",
   description = "EJB information with timing",
   path = "wls/EJB/EJB_Base_Timed",
   thread = true
)
public class EJBBaseTimedEvent extends BaseTimedEvent implements EJBEventInfo, StackTraced {
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Subsystem",
      description = "The subsystem ID",
      relationKey = "http://www.oracle.com/wls/EJB"
   )
   protected String subsystem = "EJB";
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Application Name",
      description = "The application name",
      relationKey = "http://www.oracle.com/wls/EJB/applicationName"
   )
   protected String applicationName = null;
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Component Name",
      description = "The component name",
      relationKey = "http://www.oracle.com/wls/EJB/componentName"
   )
   protected String componentName = null;
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "EJB Name",
      description = "The EJB name",
      relationKey = "http://www.oracle.com/wls/EJB/ejbName"
   )
   protected String ejbName = null;
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "EJB Method Name",
      description = "The EJB method name",
      relationKey = "http://www.oracle.com/wls/EJB/ejbMethodName"
   )
   protected String ejbMethodName = null;

   public String getSubsystem() {
      return this.subsystem;
   }

   public void setSubsystem(String subsystem) {
      this.subsystem = subsystem;
   }

   public void populateExtensions(Object retVal, Object[] args, DynamicJoinPoint djp, boolean isAfter) {
      EJBEventInfoHelper.populateExtensions(retVal, args, this);
   }

   public String getApplicationName() {
      return this.applicationName;
   }

   public void setApplicationName(String applicationName) {
      this.applicationName = applicationName;
   }

   public String getComponentName() {
      return this.componentName;
   }

   public void setComponentName(String componentName) {
      this.componentName = componentName;
   }

   public String getEjbName() {
      return this.ejbName;
   }

   public void setEjbName(String ejbName) {
      this.ejbName = ejbName;
   }

   public String getEjbMethodName() {
      return this.ejbMethodName;
   }

   public void setEjbMethodName(String ejbMethodName) {
      this.ejbMethodName = ejbMethodName;
   }
}
