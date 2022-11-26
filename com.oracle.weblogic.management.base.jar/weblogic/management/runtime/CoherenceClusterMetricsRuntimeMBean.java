package weblogic.management.runtime;

import java.util.List;
import java.util.Properties;
import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularType;
import weblogic.management.configuration.CoherenceClusterSystemResourceMBean;
import weblogic.management.configuration.CoherenceManagementClusterMBean;

public interface CoherenceClusterMetricsRuntimeMBean extends RuntimeMBean {
   CoherenceClusterSystemResourceMBean getCoherenceClusterSystemResource();

   CoherenceManagementClusterMBean getCoherenceManagementCluster();

   String getReportGroupFile();

   List getNameServiceAddresses();

   String[] getInstances();

   TabularType[] getSchema(String[] var1, String[] var2, Properties var3);

   TabularData[] getMetrics(String[] var1, String[] var2, Properties var3);
}
