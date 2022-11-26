package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;
import weblogic.diagnostics.flightrecorder.event.ConnectorEventInfo;
import weblogic.diagnostics.flightrecorder.event.ConnectorEventInfoHelper;
import weblogic.diagnostics.flightrecorder2.impl.RelationKey;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@Label("Connector Base Event")
@Description("Connector related event information")
@Category({"WebLogic Server", "Connector"})
@Name("com.oracle.weblogic.connector.ConnectorTransactionBaseEvent")
public abstract class ConnectorTransactionBaseEvent extends ConnectorBaseEvent implements ConnectorEventInfo {
   @Label("Pool")
   @Description("The pool name")
   @RelationKey("http://www.oracle.com/wls/Connector/pool")
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

   public boolean isBaseEvent() {
      return false;
   }
}
