package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "EJB Home Remove",
   description = "Timed event corresponding to a remove operation on an EJB Home class",
   path = "wls/EJB/EJB_Home_Remove",
   thread = true
)
public class EJBHomeRemoveEvent extends EJBBaseTimedEvent {
}
