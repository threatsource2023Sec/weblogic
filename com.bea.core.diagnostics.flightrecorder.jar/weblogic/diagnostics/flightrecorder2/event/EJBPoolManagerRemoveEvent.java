package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("EJB Pool Manager Remove")
@Name("com.oracle.weblogic.ejb.EJBPoolManagerRemoveEvent")
@Description("Timed event corresponding to a remove operation on an EJB pool manager")
@Category({"WebLogic Server", "EJB"})
public class EJBPoolManagerRemoveEvent extends EJBBaseEvent {
   public boolean isEventTimed() {
      return true;
   }
}
