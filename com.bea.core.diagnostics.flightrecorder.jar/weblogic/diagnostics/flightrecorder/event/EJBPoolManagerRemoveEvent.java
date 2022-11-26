package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "EJB Pool Manager Remove",
   description = "Timed event corresponding to a remove operation on an EJB pool manager",
   path = "wls/EJB/EJB_Pool_Manager_Remove",
   thread = true
)
public class EJBPoolManagerRemoveEvent extends EJBBaseTimedEvent {
}
