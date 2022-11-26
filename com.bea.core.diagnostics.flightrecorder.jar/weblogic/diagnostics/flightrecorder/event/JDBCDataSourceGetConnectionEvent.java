package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "JDBC Data Source Get Connection",
   description = "This covers the Data Source level getConnection, including the elapsed time",
   path = "wls/JDBC/JDBC_Data_Source_Get_Connection",
   thread = true
)
public class JDBCDataSourceGetConnectionEvent extends JDBCBaseTimedEvent {
}
