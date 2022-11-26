package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "EJB Business Method Pre Invoke",
   description = "Instant event prior to invoking an EJB business method",
   path = "wls/EJB/EJB_Business_Method_Pre_Invoke",
   thread = true
)
public class EJBBusinessMethodPreInvokeEvent extends EJBBaseInstantEvent {
}
