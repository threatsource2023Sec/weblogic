package weblogic.jms.utils.tracing;

public class DataLogRecord {
   public int point;
   public long time;
   public long data;

   public DataLogRecord(int point, long time, long data) {
      this.point = point;
      this.time = time;
      this.data = data;
   }
}
