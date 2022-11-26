package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("EJB Business Method Post Invoke Cleanup")
@Name("com.oracle.weblogic.ejb.EJBBusinessMethodPostInvokeCleanupEvent")
@Description("Instant event for cleanup after invoking an EJB business method")
@Category({"WebLogic Server", "EJB"})
public class EJBBusinessMethodPostInvokeCleanupEvent extends EJBBaseEvent {
   public boolean isEventTimed() {
      return true;
   }
}
