package javax.batch.runtime;

import java.util.Date;
import java.util.Properties;

public interface JobExecution {
   long getExecutionId();

   String getJobName();

   BatchStatus getBatchStatus();

   Date getStartTime();

   Date getEndTime();

   String getExitStatus();

   Date getCreateTime();

   Date getLastUpdatedTime();

   Properties getJobParameters();
}
