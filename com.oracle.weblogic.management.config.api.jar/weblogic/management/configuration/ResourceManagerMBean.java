package weblogic.management.configuration;

public interface ResourceManagerMBean extends ConfigurationMBean {
   FileOpenMBean getFileOpen();

   FileOpenMBean createFileOpen(String var1);

   void destroyFileOpen(FileOpenMBean var1);

   HeapRetainedMBean getHeapRetained();

   HeapRetainedMBean createHeapRetained(String var1);

   void destroyHeapRetained(HeapRetainedMBean var1);

   CpuUtilizationMBean getCpuUtilization();

   CpuUtilizationMBean createCpuUtilization(String var1);

   void destroyCpuUtilization(CpuUtilizationMBean var1);

   RestartLoopProtectionMBean getRestartLoopProtection();
}
