package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "JDBC Transaction Get XA Resource",
   description = "This covers calls to getXAResource for a datasource or resource",
   path = "wls/JDBC/JDBC_Transaction_Get_XA_Resource",
   thread = true
)
public class JDBCTransactionGetXAResourceEvent extends JDBCTransactionBaseEvent {
}
