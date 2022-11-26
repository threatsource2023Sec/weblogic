package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("JTA Transaction Prepared")
@Name("com.oracle.weblogic.jta.JTATransactionPreparedEvent")
@Description("The JTA Transaction Prepared Event")
@Category({"WebLogic Server", "JTA"})
public class JTATransactionPreparedEvent extends JTABaseEvent {
}
