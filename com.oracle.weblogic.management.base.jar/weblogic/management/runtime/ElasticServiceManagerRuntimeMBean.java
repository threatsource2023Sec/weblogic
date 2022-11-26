package weblogic.management.runtime;

public interface ElasticServiceManagerRuntimeMBean extends RuntimeMBean {
   ScalingTaskRuntimeMBean[] getScalingTasks();

   ScalingTaskRuntimeMBean[] getScalingTasks(String var1, int var2);

   ScalingTaskRuntimeMBean lookupScalingTask(String var1);

   ScalingTaskRuntimeMBean scaleUp(String var1, int var2);

   ScalingTaskRuntimeMBean scaleDown(String var1, int var2);

   int purgeScalingTasks(String var1, int var2);
}
