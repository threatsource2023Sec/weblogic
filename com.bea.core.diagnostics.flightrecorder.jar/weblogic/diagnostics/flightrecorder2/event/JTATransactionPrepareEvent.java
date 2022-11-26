package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("JTA Transaction Prepare")
@Name("com.oracle.weblogic.jta.JTATransactionPrepareEvent")
@Description("The JTA Transaction Prepare Event")
@Category({"WebLogic Server", "JTA"})
public class JTATransactionPrepareEvent extends JTABaseEvent {
}
