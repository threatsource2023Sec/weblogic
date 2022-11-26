package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("JTA Transaction Start")
@Name("com.oracle.weblogic.jta.JTATransactionStartEvent")
@Description("The JTA Transaction Start Event")
@Category({"WebLogic Server", "JTA"})
public class JTATransactionStartEvent extends JTABaseEvent {
}
