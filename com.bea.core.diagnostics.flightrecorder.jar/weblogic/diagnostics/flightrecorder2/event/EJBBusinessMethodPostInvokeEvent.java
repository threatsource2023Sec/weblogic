package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("EJB Business Method Post Invoke")
@Name("com.oracle.weblogic.ejb.EJBBusinessMethodPostInvokeEvent")
@Description("Instant event after invoking an EJB business method")
@Category({"WebLogic Server", "EJB"})
public class EJBBusinessMethodPostInvokeEvent extends EJBBaseEvent {
}
