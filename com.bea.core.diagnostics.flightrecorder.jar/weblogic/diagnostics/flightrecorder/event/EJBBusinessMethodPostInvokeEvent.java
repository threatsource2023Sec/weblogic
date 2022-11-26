package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "EJB Business Method Post Invoke",
   description = "Instant event after invoking an EJB business method",
   path = "wls/EJB/EJB_Business_Method_Post_Invoke",
   thread = true
)
public class EJBBusinessMethodPostInvokeEvent extends EJBBaseInstantEvent {
}
