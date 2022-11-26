package javax.enterprise.concurrent;

import java.util.Date;

public interface Trigger {
   Date getNextRunTime(LastExecution var1, Date var2);

   boolean skipRun(LastExecution var1, Date var2);
}
