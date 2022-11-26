package weblogic.connector.monitoring.outbound;

import java.io.Serializable;
import javax.management.j2ee.statistics.JCAConnectionStats;
import javax.management.j2ee.statistics.Statistic;
import javax.management.j2ee.statistics.TimeStatistic;
import weblogic.management.runtime.ConnectorConnectionRuntimeMBean;

public class JCAConnectionStatsImpl implements JCAConnectionStats, Serializable {
   String connectionFactory;
   String managedConnectionFactory;

   public JCAConnectionStatsImpl(ConnectorConnectionRuntimeMBean connectorConnectionRuntimeMBean) {
      this.connectionFactory = connectorConnectionRuntimeMBean.getConnectionFactoryClassName();
      this.managedConnectionFactory = connectorConnectionRuntimeMBean.getManagedConnectionFactoryClassName();
   }

   public String getConnectionFactory() {
      return this.connectionFactory;
   }

   public String getManagedConnectionFactory() {
      return this.managedConnectionFactory;
   }

   public TimeStatistic getWaitTime() {
      return null;
   }

   public TimeStatistic getUseTime() {
      return null;
   }

   public Statistic getStatistic(String stat) {
      Statistic returnValue = null;
      return (Statistic)returnValue;
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
