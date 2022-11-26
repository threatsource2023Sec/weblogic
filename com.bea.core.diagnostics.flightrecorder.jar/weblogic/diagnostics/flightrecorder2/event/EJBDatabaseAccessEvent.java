package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("EJB Database Access")
@Name("com.oracle.weblogic.ejb.EJBDatabaseAccessEvent")
@Description("Timed event corresponding to a database operation for an entity bean")
@Category({"WebLogic Server", "EJB"})
public class EJBDatabaseAccessEvent extends EJBBaseEvent {
   public boolean isEventTimed() {
      return true;
   }
}
