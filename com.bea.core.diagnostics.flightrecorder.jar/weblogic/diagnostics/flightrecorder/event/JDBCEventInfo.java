package weblogic.diagnostics.flightrecorder.event;

public interface JDBCEventInfo {
   String getSql();

   void setSql(String var1);

   String getPool();

   void setPool(String var1);

   boolean getInfectedSet();

   boolean getInfected();

   void setInfected(boolean var1);
}
