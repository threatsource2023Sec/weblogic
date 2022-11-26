package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "EJB Business Method Post Invoke Cleanup",
   description = "Instant event for cleanup after invoking an EJB business method",
   path = "wls/EJB/EJB_Business_Method_Post_Invoke_Cleanup",
   thread = true
)
public class EJBBusinessMethodPostInvokeCleanupEvent extends EJBBaseTimedEvent {
}
