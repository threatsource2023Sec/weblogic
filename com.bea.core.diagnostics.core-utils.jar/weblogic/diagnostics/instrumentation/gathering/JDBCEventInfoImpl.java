package weblogic.diagnostics.instrumentation.gathering;

import weblogic.diagnostics.flightrecorder.event.JDBCEventInfo;

public class JDBCEventInfoImpl implements JDBCEventInfo {
   private String sql = null;
   private String pool = null;
   private boolean infectedSet = false;
   public boolean infected = false;

   public JDBCEventInfoImpl(String sql, String pool) {
      this.sql = sql;
      this.pool = pool;
   }

   public JDBCEventInfoImpl(String sql, String pool, boolean infected) {
      this.sql = sql;
      this.pool = pool;
      this.infected = infected;
      this.infectedSet = true;
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

   public boolean getInfectedSet() {
      return this.infectedSet;
   }

   public boolean getInfected() {
      return this.infected;
   }

   public void setInfected(boolean infected) {
      this.infected = infected;
      this.infectedSet = true;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      if (this.pool != null) {
         sb.append("pool=");
         sb.append(this.pool);
         sb.append(",");
      }

      if (this.infectedSet) {
         sb.append("infected=");
         sb.append(this.infected);
         sb.append(",");
      }

      sb.append("sql=");
      sb.append(this.sql);
      return sb.toString();
   }
}
