package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("EJB Pool Manager Post Invoke")
@Name("com.oracle.weblogic.ejb.EJBPoolManagerPostInvokeEvent")
@Description("Pool manager event after invoking an EJB business method")
@Category({"WebLogic Server", "EJB"})
public class EJBPoolManagerPostInvokeEvent extends EJBBaseEvent {
}
