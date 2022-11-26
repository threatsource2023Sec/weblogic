package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "EJB Database Access",
   description = "Timed event corresponding to a database operation for an entity bean",
   path = "wls/EJB/EJB_Database_Access",
   thread = true
)
public class EJBDatabaseAccessEvent extends EJBBaseTimedEvent {
}
