package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "EJB Pool Manager Post Invoke",
   description = "Pool manager event after invoking an EJB business method",
   path = "wls/EJB/EJB_Pool_Manager_Post_Invoke",
   thread = true
)
public class EJBPoolManagerPostInvokeEvent extends EJBBaseInstantEvent {
}
