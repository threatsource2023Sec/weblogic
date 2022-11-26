package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "JTA Transaction Commit",
   description = "The JTA Transaction Commit Event",
   path = "wls/JTA/JTA_Transaction_Commit",
   thread = true
)
public class JTATransactionCommitEvent extends JTABaseInstantEvent {
}
