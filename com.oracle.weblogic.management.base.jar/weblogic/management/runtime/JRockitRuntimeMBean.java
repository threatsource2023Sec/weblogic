package weblogic.management.runtime;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import weblogic.management.utils.NotFoundException;

/** @deprecated */
@Deprecated
public interface JRockitRuntimeMBean extends JVMRuntimeMBean {
   long getTotalPhysicalMemory();

   long getFreePhysicalMemory();

   long getUsedPhysicalMemory();

   long getMaxHeapSize();

   long getTotalHeap();

   long getFreeHeap();

   long getUsedHeap();

   long getTotalNurserySize();

   String getGcAlgorithm();

   long getTotalGarbageCollectionCount();

   long getLastGCStart() throws NotFoundException;

   long getLastGCEnd() throws NotFoundException;

   long getTotalGarbageCollectionTime() throws NotFoundException;

   boolean isGCHandlesCompaction() throws NotFoundException;

   boolean isConcurrent() throws NotFoundException;

   boolean isGenerational() throws NotFoundException;

   boolean isIncremental() throws NotFoundException;

   boolean isParallel() throws NotFoundException;

   void setPauseTimeTarget(long var1) throws NotFoundException;

   long getPauseTimeTarget() throws NotFoundException;

   /** @deprecated */
   @Deprecated
   boolean isMethodTimingEnabled(Method var1) throws NotFoundException;

   /** @deprecated */
   @Deprecated
   long getMethodTiming(Method var1) throws NotFoundException;

   /** @deprecated */
   @Deprecated
   boolean isMethodInvocationCountEnabled(Method var1) throws NotFoundException;

   /** @deprecated */
   @Deprecated
   long getMethodInvocationCount(Method var1) throws NotFoundException;

   /** @deprecated */
   @Deprecated
   boolean isConstructorTimingEnabled(Constructor var1) throws NotFoundException;

   /** @deprecated */
   @Deprecated
   long getConstructorTiming(Constructor var1) throws NotFoundException;

   /** @deprecated */
   @Deprecated
   boolean isConstructorInvocationCountEnabled(Constructor var1) throws NotFoundException;

   /** @deprecated */
   @Deprecated
   long getConstructorInvocationCount(Constructor var1) throws NotFoundException;

   /** @deprecated */
   @Deprecated
   boolean isExceptionCountEnabled(Class var1) throws ClassCastException;

   /** @deprecated */
   @Deprecated
   long getExceptionCount(Class var1);

   int getTotalNumberOfThreads();

   int getNumberOfDaemonThreads();

   Collection getThreadSnapShots();

   String getJVMDescription();

   String getVendor();

   String getVersion();

   String getName();

   int getNumberOfProcessors();

   double getAllProcessorsAverageLoad();

   double getJvmProcessorLoad();

   List getCPUs() throws NotFoundException;

   Collection getHardwareComponents() throws NotFoundException;

   Collection getNICs() throws NotFoundException;
}
