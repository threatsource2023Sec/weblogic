package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.UseConstantPool;
import com.oracle.jrockit.jfr.ValueDefinition;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@EventDefinition(
   name = "Connector Endpoint Base Event",
   description = "Connector endpoint information",
   path = "wls/Connector/Connector_Endpoint_Base",
   thread = true
)
public class ConnectorEndpointBaseEvent extends ConnectorBaseEvent implements ConnectorEndpointEventInfo {
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "EJB Name"
   )
   protected String ejbName;
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "JNDI Name"
   )
   protected String jndiName;
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Message Listener"
   )
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
