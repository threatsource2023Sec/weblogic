package weblogic.work.concurrent.future;

import java.util.Date;
import javax.enterprise.concurrent.LastExecution;

public class LastExecutionImpl implements LastExecution {
   private Object result;
   private final Date scheduledStart;
   private Date runStart;
   private Date runEnd;
   private final String name;

   public LastExecutionImpl(String taskName, Date scheduledStart) {
      this.name = taskName;
      this.scheduledStart = scheduledStart;
   }

   public void setResult(Object result) {
      this.result = result;
   }

   public void setRunStart(Date runStart) {
      this.runStart = runStart;
   }

   public void setRunEnd(Date runEnd) {
      this.runEnd = runEnd;
   }

   public String getIdentityName() {
      return this.name;
   }

   public Object getResult() {
      return this.result;
   }

   public Date getScheduledStart() {
      return this.scheduledStart;
   }

   public Date getRunStart() {
      return this.runStart;
   }

   public Date getRunEnd() {
      return this.runEnd;
   }
}
