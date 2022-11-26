package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;
import weblogic.diagnostics.flightrecorder.event.ConnectorEndpointEventInfo;
import weblogic.diagnostics.flightrecorder.event.ConnectorEndpointEventInfoHelper;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@Label("Connector Endpoint Base Event")
@Description("Connector endpoint information")
@Category({"WebLogic Server", "Connector"})
@Name("com.oracle.weblogic.connector.ConnectorEndpointBaseEvent")
public abstract class ConnectorEndpointBaseEvent extends ConnectorBaseEvent implements ConnectorEndpointEventInfo {
   @Label("EJB Name")
   protected String ejbName;
   @Label("JNDI Name")
   protected String jndiName;
   @Label("Message Listener")
   protected String messageListener;

   public String getEjbName() {
      return this.ejbName;
   }

   public void setEjbName(String ejbName) {
      this.ejbName = ejbName;
   }

   public String getJndiName() {
      return this.jndiName;
   }

   public void setJndiName(String jndiName) {
      this.jndiName = jndiName;
   }

   public String getMessageListener() {
      return this.messageListener;
   }

   public void setMessageListener(String messageListener) {
      this.messageListener = messageListener;
   }

   public void populateExtensions(Object retVal, Object[] args, DynamicJoinPoint djp, boolean isAfter) {
      ConnectorEndpointEventInfoHelper.populateExtensions(retVal, args, djp, this);
   }
}
