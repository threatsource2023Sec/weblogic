package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "JTA Transaction End",
   description = "The JTA Transaction End Event",
   path = "wls/JTA/JTA_Transaction_End",
   thread = true
)
public class JTATransactionEndEvent extends JTABaseInstantEvent {
}
