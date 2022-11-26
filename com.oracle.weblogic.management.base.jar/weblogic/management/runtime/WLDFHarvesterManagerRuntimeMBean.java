package weblogic.management.runtime;

import java.util.Map;
import javax.management.JMException;

public interface WLDFHarvesterManagerRuntimeMBean extends RuntimeMBean {
   long getAverageSamplingTime();

   long getCurrentDataSampleCount();

   long getCurrentSnapshotElapsedTime();

   long getCurrentSnapshotStartTime();

   long getMaximumSamplingTime();

   long getMinimumSamplingTime();

   long getTotalDataSampleCount();

   long getTotalSamplingCycles();

   long getTotalSamplingTime();

   Map retrieveSnapshot() throws JMException;
}
