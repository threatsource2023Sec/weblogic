package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("EJB Home Create")
@Name("com.oracle.weblogic.ejb.EJBHomeCreateEvent")
@Description("Timed event corresponding to a create operation on an EJB Home class")
@Category({"WebLogic Server", "EJB"})
public class EJBHomeCreateEvent extends EJBBaseEvent {
   public boolean isEventTimed() {
      return true;
   }
}
