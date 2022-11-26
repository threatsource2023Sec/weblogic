package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "JTA Transaction Start",
   description = "The JTA Transaction Start Event",
   path = "wls/JTA/JTA_Transaction_Start",
   thread = true
)
public class JTATransactionStartEvent extends JTABaseInstantEvent {
}
