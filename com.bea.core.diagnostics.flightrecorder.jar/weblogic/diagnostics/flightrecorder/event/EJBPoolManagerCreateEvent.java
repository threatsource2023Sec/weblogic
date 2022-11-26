package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "EJB PoolManager Create",
   description = "Timed event corresponding to a create operation on an EJB pool manager",
   path = "wls/EJB/EJB_Pool_Manager_Create",
   thread = true
)
public class EJBPoolManagerCreateEvent extends EJBBaseTimedEvent {
}
