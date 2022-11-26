package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("JDBC Connection Get Vendor Connection")
@Name("com.oracle.weblogic.jdbc.JDBCConnectionGetVendorConnectionEvent")
@Description("This covers calls to getVendorConnection and getVendorConnectionSafe")
@Category({"WebLogic Server", "JDBC"})
public class JDBCConnectionGetVendorConnectionEvent extends JDBCBaseEvent {
   public boolean isEventTimed() {
      return false;
   }
}
