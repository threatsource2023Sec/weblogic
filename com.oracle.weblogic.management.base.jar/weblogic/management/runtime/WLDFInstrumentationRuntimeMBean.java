package weblogic.management.runtime;

import java.util.Map;
import weblogic.management.ManagementException;

public interface WLDFInstrumentationRuntimeMBean extends RuntimeMBean {
   int getInspectedClassesCount();

   int getModifiedClassesCount();

   long getMinWeavingTime();

   long getMaxWeavingTime();

   long getTotalWeavingTime();

   int getExecutionJoinpointCount();

   int getCallJoinpointCount();

   int getClassweaveAbortCount();

   Map getMethodInvocationStatistics();

   Object getMethodInvocationStatisticsData(String var1) throws ManagementException;

   void resetMethodInvocationStatisticsData(String var1) throws ManagementException;

   Map getMethodMemoryAllocationStatistics();

   Object getMethodMemoryAllocationStatisticsData(String var1) throws ManagementException;

   void resetMethodMemoryAllocationStatisticsData(String var1) throws ManagementException;
}
