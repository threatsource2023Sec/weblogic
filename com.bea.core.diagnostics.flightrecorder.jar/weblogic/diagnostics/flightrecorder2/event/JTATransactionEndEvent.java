package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("JTA Transaction End")
@Name("com.oracle.weblogic.jta.JTATransactionEndEvent")
@Description("The JTA Transaction End Event")
@Category({"WebLogic Server", "JTA"})
public class JTATransactionEndEvent extends JTABaseEvent {
}
