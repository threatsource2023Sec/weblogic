package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "JTA Transaction Prepare",
   description = "The JTA Transaction Prepare Event",
   path = "wls/JTA/JTA_Transaction_Prepare",
   thread = true
)
public class JTATransactionPrepareEvent extends JTABaseInstantEvent {
}
