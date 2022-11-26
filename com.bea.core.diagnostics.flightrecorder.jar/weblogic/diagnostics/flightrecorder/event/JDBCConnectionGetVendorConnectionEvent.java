package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "JDBC Connection Get Vendor Connection",
   description = "This covers calls to getVendorConnection and getVendorConnectionSafe",
   path = "wls/JDBC/JDBC_Connection_Get_Vendor_Connection",
   thread = true
)
public class JDBCConnectionGetVendorConnectionEvent extends JDBCBaseInstantEvent {
}
