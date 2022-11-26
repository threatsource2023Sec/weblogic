package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("EJB Pool Manager Pre Invoke")
@Name("com.oracle.weblogic.ejb.EJBPoolManagerPreInvokeEvent")
@Description("Pool manager event prior to invoking an EJB business method")
@Category({"WebLogic Server", "EJB"})
public class EJBPoolManagerPreInvokeEvent extends EJBBaseEvent {
}
