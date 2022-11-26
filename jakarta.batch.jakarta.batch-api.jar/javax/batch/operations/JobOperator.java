package javax.batch.operations;

import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.JobInstance;

public interface JobOperator {
   Set getJobNames() throws JobSecurityException;

   int getJobInstanceCount(String var1) throws NoSuchJobException, JobSecurityException;

   List getJobInstances(String var1, int var2, int var3) throws NoSuchJobException, JobSecurityException;

   List getRunningExecutions(String var1) throws NoSuchJobException, JobSecurityException;

   Properties getParameters(long var1) throws NoSuchJobExecutionException, JobSecurityException;

   long start(String var1, Properties var2) throws JobStartException, JobSecurityException;

   long restart(long var1, Properties var3) throws JobExecutionAlreadyCompleteException, NoSuchJobExecutionException, JobExecutionNotMostRecentException, JobRestartException, JobSecurityException;

   void stop(long var1) throws NoSuchJobExecutionException, JobExecutionNotRunningException, JobSecurityException;

   void abandon(long var1) throws NoSuchJobExecutionException, JobExecutionIsRunningException, JobSecurityException;

   JobInstance getJobInstance(long var1) throws NoSuchJobExecutionException, JobSecurityException;

   List getJobExecutions(JobInstance var1) throws NoSuchJobInstanceException, JobSecurityException;

   JobExecution getJobExecution(long var1) throws NoSuchJobExecutionException, JobSecurityException;

   List getStepExecutions(long var1) throws NoSuchJobExecutionException, JobSecurityException;
}
