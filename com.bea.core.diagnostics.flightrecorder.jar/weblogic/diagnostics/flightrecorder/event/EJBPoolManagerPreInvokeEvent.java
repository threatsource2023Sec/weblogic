package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "EJB Pool Manager Pre Invoke",
   description = "Pool manager event prior to invoking an EJB business method",
   path = "wls/EJB/EJB_Pool_Manager_Pre_Invoke",
   thread = true
)
public class EJBPoolManagerPreInvokeEvent extends EJBBaseInstantEvent {
}
