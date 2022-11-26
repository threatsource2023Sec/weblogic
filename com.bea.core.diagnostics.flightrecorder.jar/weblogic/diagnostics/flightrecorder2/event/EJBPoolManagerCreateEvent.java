package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("EJB Pool Manager Create")
@Name("com.oracle.weblogic.ejb.EJBPoolManagerCreateEvent")
@Description("Timed event corresponding to a create operation on an EJB pool manager")
@Category({"WebLogic Server", "EJB"})
public class EJBPoolManagerCreateEvent extends EJBBaseEvent {
   public boolean isEventTimed() {
      return true;
   }
}
