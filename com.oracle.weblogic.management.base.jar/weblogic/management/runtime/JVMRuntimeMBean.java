package weblogic.management.runtime;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface JVMRuntimeMBean extends RuntimeMBean {
   void shutdown();

   void runGC();

   long getHeapFreeCurrent();

   long getHeapSizeCurrent();

   long getHeapSizeMax();

   int getHeapFreePercent();

   String getJavaVersion();

   String getJavaVendor();

   String getJavaVMVendor();

   String getOSName();

   String getOSVersion();

   String getThreadStackDump();

   long getUptime();
}
