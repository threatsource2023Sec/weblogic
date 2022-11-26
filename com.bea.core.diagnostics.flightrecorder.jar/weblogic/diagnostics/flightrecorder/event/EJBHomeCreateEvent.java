package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "EJB Home Create",
   description = "Timed event corresponding to a create operation on an EJB Home class",
   path = "wls/EJB/EJB_Home_Create",
   thread = true
)
public class EJBHomeCreateEvent extends EJBBaseTimedEvent {
}
