package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("JTA Transaction Commit")
@Name("com.oracle.weblogic.jta.JTATransactionCommitEvent")
@Description("The JTA Transaction Commit Event")
@Category({"WebLogic Server", "JTA"})
public class JTATransactionCommitEvent extends JTABaseEvent {
}
