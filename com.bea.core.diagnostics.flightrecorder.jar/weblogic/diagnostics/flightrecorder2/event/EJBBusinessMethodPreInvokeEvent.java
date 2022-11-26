package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("EJB Business Method Pre Invoke")
@Name("com.oracle.weblogic.ejb.EJBBusinessMethodPreInvokeEvent")
@Description("Instant event prior to invoking an EJB business method")
@Category({"WebLogic Server", "EJB"})
public class EJBBusinessMethodPreInvokeEvent extends EJBBaseEvent {
}
