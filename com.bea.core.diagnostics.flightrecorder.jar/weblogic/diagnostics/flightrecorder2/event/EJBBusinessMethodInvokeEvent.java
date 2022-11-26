package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("EJB Business Method Invoke")
@Name("com.oracle.weblogic.ejb.EJBBusinessMethodInvokeEvent")
@Description("Timed event corresponding to invoking an EJB business method")
@Category({"WebLogic Server", "EJB"})
public class EJBBusinessMethodInvokeEvent extends EJBBaseEvent {
   public boolean isEventTimed() {
      return true;
   }
}
