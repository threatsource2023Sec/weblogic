package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.UseConstantPool;
import com.oracle.jrockit.jfr.ValueDefinition;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@EventDefinition(
   name = "JDBC Base Instant",
   description = "This defines the values common to JDBC events that are not timed",
   path = "wls/JDBC/JDBC_Base_Instant",
   thread = true
)
public class JDBCBaseInstantEvent extends BaseInstantEvent implements JDBCEventInfo, StackTraced {
   private boolean infectedSet = false;
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Subsystem",
      description = "The subsystem ID",
      relationKey = "http://www.oracle.com/wls/JDBC"
   )
   protected String subsystem = "JDBC";
   @UseConstantPool(
      name = "LocalPool"
   )
   @ValueDefinition(
      name = "SQL",
      description = "The sql statement",
      relationKey = "http://www.oracle.com/wls/JDBC/sql"
   )
   protected String sql = null;
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Pool",
      description = "The pool name",
      relationKey = "http://www.oracle.com/wls/JDBC/pool"
   )
   protected String pool = null;
   @ValueDefinition(
      name = "Infected",
      description = "Indicates whether the underlying vendor connection is considered to be infected",
      relationKey = "http://www.oracle.com/wls/JDBC/infected"
   )
   protected boolean infected = false;

   public boolean getInfectedSet() {
      return this.infectedSet;
   }

   public String getSubsystem() {
      return this.subsystem;
   }

   public void setSubsystem(String subsystem) {
      this.subsystem = subsystem;
   }

   public void populateExtensions(Object retVal, Object[] args, DynamicJoinPoint djp, boolean isAfter) {
      JDBCEventInfoHelper.populateExtensions(retVal, args, this);
   }

   public String getSql() {
      return this.sql;
   }

   public void setSql(String sql) {
      this.sql = sql;
   }

   public String getPool() {
      return this.pool;
   }

   public void setPool(String pool) {
      this.pool = pool;
   }

   public boolean getInfected() {
      return this.infected;
   }

   public void setInfected(boolean infected) {
      this.infected = infected;
      this.infectedSet = true;
   }

   protected JDBCBaseInstantEvent() {
   }

   protected JDBCBaseInstantEvent(JDBCBaseTimedEvent timedEvent) {
      super(timedEvent);
      this.sql = timedEvent.sql;
      this.infected = timedEvent.infected;
      this.pool = timedEvent.pool;
   }
}
