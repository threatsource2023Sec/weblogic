package weblogic.connector.monitoring.outbound;

import java.io.Serializable;
import javax.management.j2ee.statistics.BoundedRangeStatistic;
import javax.management.j2ee.statistics.CountStatistic;
import javax.management.j2ee.statistics.JCAConnectionPoolStats;
import javax.management.j2ee.statistics.RangeStatistic;
import javax.management.j2ee.statistics.Statistic;
import javax.management.j2ee.statistics.TimeStatistic;
import weblogic.connector.common.Debug;
import weblogic.management.j2ee.statistics.BoundedRangeStatisticImpl;
import weblogic.management.j2ee.statistics.CountStatisticImpl;
import weblogic.management.j2ee.statistics.RangeStatisticImpl;
import weblogic.management.j2ee.statistics.StatException;
import weblogic.management.runtime.ConnectorConnectionPoolRuntimeMBean;

public class JCAConnectionPoolStatsImpl implements JCAConnectionPoolStats, Serializable {
   private String key;
   private long closeCount;
   private String connectionFactory;
   private long createCount;
   private String managedConnectionFactory;
   private long freeConnectionsCurrentCount;
   private long freePoolSizeHighWaterMark;
   private long freePoolSizeLowWaterMark;
   private long maxCapacity;
   private long poolSize;
   private long numWaiters;
   private long highestNumWaiters;
   protected static final String STAT_CLOSE_COUNT = Debug.getStringCloseCount();
   protected static final String STAT_CREATE_COUNT = Debug.getStringCreateCount();
   protected static final String STAT_FREE_POOL_SIZE = Debug.getStringFreePoolSize();
   protected static final String STAT_POOL_SIZE = Debug.getStringPoolSize();
   protected static final String STAT_WAITING_THREAD_COUNT = Debug.getStringWaitingThreadCount();
   protected static final String STAT_CLOSE_COUNT_DESCRIPTION = Debug.getStringCloseCountDescription();
   protected static final String STAT_CREATE_COUNT_DESCRIPTION = Debug.getStringCreateCountDescription();
   protected static final String STAT_FREE_POOL_SIZE_DESCRIPTION = Debug.getStringFreePoolSizeDescription();
   protected static final String STAT_POOL_SIZE_DESCRIPTION = Debug.getStringPoolSizeDescription();
   protected static final String STAT_WAITING_THREAD_COUNT_DESCRIPTION = Debug.getStringWaitingThreadCountDescription();

   public JCAConnectionPoolStatsImpl(ConnectorConnectionPoolRuntimeMBean connectorConnectionPoolRuntimeMBean) {
      this.key = connectorConnectionPoolRuntimeMBean.getKey();
      this.closeCount = connectorConnectionPoolRuntimeMBean.getCloseCount();
      this.connectionFactory = connectorConnectionPoolRuntimeMBean.getConnectionFactoryClassName();
      connectorConnectionPoolRuntimeMBean.getManagedConnectionFactoryClassName();
      this.managedConnectionFactory = connectorConnectionPoolRuntimeMBean.getManagedConnectionFactoryClassName();
      this.createCount = (long)connectorConnectionPoolRuntimeMBean.getConnectionsCreatedTotalCount();
      this.freeConnectionsCurrentCount = (long)connectorConnectionPoolRuntimeMBean.getFreeConnectionsCurrentCount();
      this.freePoolSizeHighWaterMark = connectorConnectionPoolRuntimeMBean.getFreePoolSizeHighWaterMark();
      this.freePoolSizeLowWaterMark = connectorConnectionPoolRuntimeMBean.getFreePoolSizeLowWaterMark();
      this.maxCapacity = (long)connectorConnectionPoolRuntimeMBean.getMaxCapacity();
      this.poolSize = connectorConnectionPoolRuntimeMBean.getCurrentCapacity();
      this.numWaiters = connectorConnectionPoolRuntimeMBean.getNumWaiters();
      this.highestNumWaiters = connectorConnectionPoolRuntimeMBean.getHighestNumWaiters();
   }

   public CountStatistic getCloseCount() {
      CountStatisticImpl returnValue = null;

      try {
         returnValue = new CountStatisticImpl(STAT_CLOSE_COUNT_DESCRIPTION, STAT_CLOSE_COUNT, this.key);
         returnValue.setCount(this.closeCount);
      } catch (StatException var3) {
      }

      return returnValue;
   }

   public CountStatistic getCreateCount() {
      CountStatisticImpl returnValue = null;

      try {
         returnValue = new CountStatisticImpl(STAT_CREATE_COUNT_DESCRIPTION, STAT_CREATE_COUNT, this.key);
         returnValue.setCount(this.createCount);
      } catch (StatException var3) {
      }

      return returnValue;
   }

   public BoundedRangeStatistic getFreePoolSize() {
      BoundedRangeStatisticImpl returnValue = null;

      try {
         returnValue = new BoundedRangeStatisticImpl(STAT_FREE_POOL_SIZE_DESCRIPTION, STAT_FREE_POOL_SIZE, this.key);
         returnValue.setCurrent(this.freeConnectionsCurrentCount);
         returnValue.setHighWaterMark(this.freePoolSizeHighWaterMark);
         returnValue.setLowWaterMark(this.freePoolSizeLowWaterMark);
         returnValue.setUpperBound(this.maxCapacity);
         returnValue.setLowerBound(0L);
      } catch (StatException var3) {
      }

      return returnValue;
   }

   public BoundedRangeStatistic getPoolSize() {
      BoundedRangeStatisticImpl returnValue = null;

      try {
         returnValue = new BoundedRangeStatisticImpl(STAT_POOL_SIZE_DESCRIPTION, STAT_POOL_SIZE, this.key);
         returnValue.setCurrent(this.poolSize);
         returnValue.setHighWaterMark(this.freePoolSizeHighWaterMark);
         returnValue.setLowWaterMark(this.freePoolSizeLowWaterMark);
         returnValue.setUpperBound(this.maxCapacity);
         returnValue.setLowerBound(0L);
      } catch (StatException var3) {
      }

      return returnValue;
   }

   public RangeStatistic getWaitingThreadCount() {
      RangeStatisticImpl returnValue = null;

      try {
         returnValue = new RangeStatisticImpl(STAT_WAITING_THREAD_COUNT_DESCRIPTION, STAT_WAITING_THREAD_COUNT, this.key);
         returnValue.setCurrent(this.numWaiters);
         returnValue.setHighWaterMark(this.highestNumWaiters);
         returnValue.setLowWaterMark(0L);
      } catch (StatException var3) {
      }

      return returnValue;
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
      if (stat != null && stat.length() > 0) {
         if (stat.equals(STAT_CLOSE_COUNT)) {
            returnValue = this.getCloseCount();
         } else if (stat.equals(STAT_CREATE_COUNT)) {
            returnValue = this.getCreateCount();
         } else if (stat.equals(STAT_FREE_POOL_SIZE)) {
            returnValue = this.getFreePoolSize();
         } else if (stat.equals(STAT_POOL_SIZE)) {
            returnValue = this.getPoolSize();
         } else if (stat.equals(STAT_WAITING_THREAD_COUNT)) {
            returnValue = this.getWaitingThreadCount();
         }
      }

      return (Statistic)returnValue;
   }

   public String[] getStatisticNames() {
      String[] supportedStatistics = new String[]{STAT_CLOSE_COUNT, STAT_CREATE_COUNT, STAT_FREE_POOL_SIZE, STAT_POOL_SIZE, STAT_WAITING_THREAD_COUNT};
      return supportedStatistics;
   }

   public Statistic[] getStatistics() {
      Statistic[] stats = new Statistic[]{this.getCloseCount(), this.getCreateCount(), this.getFreePoolSize(), this.getPoolSize(), this.getWaitingThreadCount()};
      return stats;
   }
}
