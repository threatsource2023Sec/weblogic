package weblogic.t3.srvr;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JRockitRuntimeMBean;
import weblogic.management.utils.NotFoundException;

/** @deprecated */
@Deprecated
public class JRockitRuntime extends JVMRuntime implements JRockitRuntimeMBean {
   public JRockitRuntime() throws ManagementException {
   }

   public long getTotalPhysicalMemory() {
      return 0L;
   }

   public long getFreePhysicalMemory() {
      return 0L;
   }

   public long getUsedPhysicalMemory() {
      return 0L;
   }

   public long getMaxHeapSize() {
      return 0L;
   }

   public long getTotalHeap() {
      return 0L;
   }

   public long getFreeHeap() {
      return 0L;
   }

   public long getUsedHeap() {
      return 0L;
   }

   public long getTotalNurserySize() {
      return 0L;
   }

   public String getGcAlgorithm() {
      return null;
   }

   public long getTotalGarbageCollectionCount() {
      return 0L;
   }

   public long getLastGCStart() throws NotFoundException {
      return 0L;
   }

   public long getLastGCEnd() throws NotFoundException {
      return 0L;
   }

   public long getTotalGarbageCollectionTime() throws NotFoundException {
      return 0L;
   }

   public boolean isGCHandlesCompaction() throws NotFoundException {
      return false;
   }

   public boolean isConcurrent() throws NotFoundException {
      return false;
   }

   public boolean isGenerational() throws NotFoundException {
      return false;
   }

   public boolean isIncremental() throws NotFoundException {
      return false;
   }

   public boolean isParallel() throws NotFoundException {
      return false;
   }

   public void setPauseTimeTarget(long pauseTime) throws NotFoundException {
   }

   public long getPauseTimeTarget() throws NotFoundException {
      return 0L;
   }

   public boolean isMethodTimingEnabled(Method method) throws NotFoundException {
      return false;
   }

   public long getMethodTiming(Method method) throws NotFoundException {
      return 0L;
   }

   public boolean isMethodInvocationCountEnabled(Method method) throws NotFoundException {
      return false;
   }

   public long getMethodInvocationCount(Method method) throws NotFoundException {
      return 0L;
   }

   public boolean isConstructorTimingEnabled(Constructor constructor) throws NotFoundException {
      return false;
   }

   public long getConstructorTiming(Constructor constructor) throws NotFoundException {
      return 0L;
   }

   public boolean isConstructorInvocationCountEnabled(Constructor cons) throws NotFoundException {
      return false;
   }

   public long getConstructorInvocationCount(Constructor constructor) throws NotFoundException {
      return 0L;
   }

   public boolean isExceptionCountEnabled(Class throwableClass) throws ClassCastException {
      return false;
   }

   public long getExceptionCount(Class throwableClass) {
      return 0L;
   }

   public int getTotalNumberOfThreads() {
      return 0;
   }

   public int getNumberOfDaemonThreads() {
      return 0;
   }

   public Collection getThreadSnapShots() {
      return null;
   }

   public String getJVMDescription() {
      return null;
   }

   public String getVendor() {
      return null;
   }

   public String getVersion() {
      return null;
   }

   public int getNumberOfProcessors() {
      return 0;
   }

   public double getAllProcessorsAverageLoad() {
      return 0.0;
   }

   public double getJvmProcessorLoad() {
      return 0.0;
   }

   public List getCPUs() throws NotFoundException {
      return null;
   }

   public Collection getHardwareComponents() throws NotFoundException {
      return null;
   }

   public Collection getNICs() throws NotFoundException {
      return null;
   }
}
