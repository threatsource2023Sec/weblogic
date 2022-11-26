package weblogic.management.runtime;

public interface JobSchedulerRuntimeMBean extends RuntimeMBean {
   JobRuntimeMBean getJob(String var1);

   JobRuntimeMBean[] getExecutedJobs();
}
