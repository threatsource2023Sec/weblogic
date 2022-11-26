package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;
import weblogic.diagnostics.flightrecorder.event.EJBEventInfo;
import weblogic.diagnostics.flightrecorder.event.EJBEventInfoHelper;
import weblogic.diagnostics.flightrecorder.event.StackTraced;
import weblogic.diagnostics.flightrecorder2.impl.RelationKey;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@Label("EJB Base Instant")
@Name("com.oracle.weblogic.ejb.EJBBaseEvent")
@Description("Base EJB information")
@Category({"WebLogic Server", "EJB"})
public abstract class EJBBaseEvent extends BaseEvent implements EJBEventInfo, StackTraced {
   @Label("Subsystem")
   @Description("The subsystem ID")
   @RelationKey("http://www.oracle.com/wls/EJB")
   protected String subsystem = "EJB";
   @Label("Application Name")
   @Description("The application name")
   @RelationKey("http://www.oracle.com/wls/EJB/applicationName")
   protected String applicationName = null;
   @Label("Component Name")
   @Description("The component name")
   @RelationKey("http://www.oracle.com/wls/EJB/componentName")
   protected String componentName = null;
   @Label("EJB Name")
   @Description("The EJB name")
   @RelationKey("http://www.oracle.com/wls/EJB/ejbName")
   protected String ejbName = null;
   @Label("EJB Method Name")
   @Description("The EJB method name")
   @RelationKey("http://www.oracle.com/wls/EJB/ejbMethodName")
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
