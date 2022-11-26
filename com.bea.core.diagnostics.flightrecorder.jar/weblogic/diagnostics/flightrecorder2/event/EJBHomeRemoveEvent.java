package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("EJB Home Remove")
@Name("com.oracle.weblogic.ejb.EJBHomeRemoveEvent")
@Description("Timed event corresponding to a remove operation on an EJB Home class")
@Category({"WebLogic Server", "EJB"})
public class EJBHomeRemoveEvent extends EJBBaseEvent {
   public boolean isEventTimed() {
      return true;
   }
}
