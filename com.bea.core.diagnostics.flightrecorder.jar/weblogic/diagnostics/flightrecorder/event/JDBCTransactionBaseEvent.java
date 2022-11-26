package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.UseConstantPool;
import com.oracle.jrockit.jfr.ValueDefinition;

@EventDefinition(
   name = "JDBC Transaction Base",
   description = "Base for JDBC Transaction events",
   path = "wls/JDBC/JDBC_Transaction_Base",
   thread = true
)
public class JDBCTransactionBaseEvent extends BaseInstantEvent implements StackTraced {
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Subsystem",
      description = "The subsystem ID",
      relationKey = "http://www.oracle.com/wls/JDBC"
   )
   protected String subsystem = "JDBC";

   public String getSubsystem() {
      return this.subsystem;
   }

   public void setSubsystem(String subsystem) {
      this.subsystem = subsystem;
   }
}
