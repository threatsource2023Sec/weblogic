package weblogic.connector.monitoring.outbound;

import java.io.Serializable;
import javax.management.j2ee.statistics.JCAConnectionPoolStats;
import javax.management.j2ee.statistics.JCAConnectionStats;
import javax.management.j2ee.statistics.JCAStats;
import javax.management.j2ee.statistics.Statistic;
import weblogic.management.runtime.ConnectorComponentRuntimeMBean;
import weblogic.management.runtime.ConnectorConnectionPoolRuntimeMBean;

public class JCAStatsImpl implements JCAStats, Serializable {
   JCAConnectionStats[] jcaConnectionStatsArray;
   JCAConnectionPoolStats[] jcaConnectionPoolStatsArray;

   public JCAStatsImpl(ConnectorComponentRuntimeMBean aConnectorComponentRuntimeMBean) {
      this.jcaConnectionStatsArray = this.initConnections(aConnectorComponentRuntimeMBean);
      this.jcaConnectionPoolStatsArray = this.initConnectionPools(aConnectorComponentRuntimeMBean);
   }

   public JCAConnectionStats[] getConnections() {
      return this.jcaConnectionStatsArray;
   }

   public JCAConnectionPoolStats[] getConnectionPools() {
      return this.jcaConnectionPoolStatsArray;
   }

   private JCAConnectionStats[] initConnections(ConnectorComponentRuntimeMBean connectorComponentRuntimeMBean) {
      return new JCAConnectionStats[0];
   }

   private JCAConnectionPoolStats[] initConnectionPools(ConnectorComponentRuntimeMBean connectorComponentRuntimeMBean) {
      ConnectorConnectionPoolRuntimeMBean[] conPoolRuntimeArray = connectorComponentRuntimeMBean.getConnectionPools();
      JCAConnectionPoolStats[] returnArray = new JCAConnectionPoolStats[0];
      if (conPoolRuntimeArray != null && conPoolRuntimeArray.length > 0) {
         returnArray = new JCAConnectionPoolStats[conPoolRuntimeArray.length];

         for(int i = 0; i < conPoolRuntimeArray.length; ++i) {
            returnArray[i] = new JCAConnectionPoolStatsImpl(conPoolRuntimeArray[i]);
         }
      }

      return returnArray;
   }

   public Statistic getStatistic(String s) {
      return null;
   }

   public String[] getStatisticNames() {
      String[] supportedStatistics = new String[0];
      return supportedStatistics;
   }

   public Statistic[] getStatistics() {
      Statistic[] stats = new Statistic[0];
      return stats;
   }
}
