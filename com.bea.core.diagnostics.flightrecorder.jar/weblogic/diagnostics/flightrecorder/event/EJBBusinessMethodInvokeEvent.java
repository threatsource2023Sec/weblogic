package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "EJB Business Method Invoke",
   description = "Timed event corresponding to invoking an EJB business method",
   path = "wls/EJB/EJB_Business_Method_Invoke",
   thread = true
)
public class EJBBusinessMethodInvokeEvent extends EJBBaseTimedEvent {
}
