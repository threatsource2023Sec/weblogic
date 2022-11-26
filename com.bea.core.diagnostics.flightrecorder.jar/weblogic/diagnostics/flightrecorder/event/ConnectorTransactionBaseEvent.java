package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.UseConstantPool;
import com.oracle.jrockit.jfr.ValueDefinition;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@EventDefinition(
   name = "Connector Base Event",
   description = "Connector related event information",
   path = "wls/Connector/Connector_Transaction_Base",
   thread = true
)
public class ConnectorTransactionBaseEvent extends ConnectorBaseEvent implements ConnectorEventInfo {
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Pool",
      description = "The pool name",
      relationKey = "http://www.oracle.com/wls/Connector/pool"
   )
   protected String pool = null;

   public void setPool(String pool) {
      this.pool = pool;
   }

   public String getPool() {
      return this.pool;
   }

   public void populateExtensions(Object retVal, Object[] args, DynamicJoinPoint djp, boolean isAfter) {
      ConnectorEventInfoHelper.populateExtensions(retVal, args, djp, this);
   }
}
