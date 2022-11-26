package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "JTA Transaction Prepared",
   description = "The JTA Transaction Prepared Event",
   path = "wls/JTA/JTA_Transaction_Prepared",
   thread = true
)
public class JTATransactionPreparedEvent extends JTABaseInstantEvent {
}
