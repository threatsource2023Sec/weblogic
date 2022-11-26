package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;
import weblogic.diagnostics.flightrecorder.event.JDBCEventInfo;
import weblogic.diagnostics.flightrecorder.event.JDBCEventInfoHelper;
import weblogic.diagnostics.flightrecorder.event.StackTraced;
import weblogic.diagnostics.flightrecorder2.impl.RelationKey;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@Label("JDBC Base")
@Name("com.oracle.weblogic.jdbc.JDBCBaseEvent")
@Description("This defines the values common to JDBC events that are not timed")
@Category({"WebLogic Server", "JDBC"})
public abstract class JDBCBaseEvent extends BaseEvent implements JDBCEventInfo, StackTraced {
   private boolean infectedSet = false;
   @Label("Subsystem")
   @Description("The subsystem ID")
   @RelationKey("http://www.oracle.com/wls/JDBC")
   protected String subsystem = "JDBC";
   @Label("SQL")
   @Description("The sql statement")
   @RelationKey("http://www.oracle.com/wls/JDBC/sql")
   protected String sql = null;
   @Label("Pool")
   @Description("The pool name")
   @RelationKey("http://www.oracle.com/wls/JDBC/pool")
   protected String pool = null;
   @Label("Infected")
   @Description("Indicates whether the underlying vendor connection is considered to be infected")
   @RelationKey("http://www.oracle.com/wls/JDBC/infected")
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

   protected JDBCBaseEvent() {
   }

   protected JDBCBaseEvent(JDBCBaseEvent timedEvent) {
      super(timedEvent);
      this.sql = timedEvent.sql;
      this.infected = timedEvent.infected;
      this.pool = timedEvent.pool;
   }

   public boolean isEventTimed() {
      return true;
   }
}
