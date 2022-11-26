package weblogic.diagnostics.image.descriptor;

public interface ServerRuntimeStatisticsBean {
   int getTotalRegisteredMBeansCount();

   void setTotalRegisteredMBeansCount(int var1);

   JMXDomainStatisticsBean[] getMBeanServerStatistics();

   JMXDomainStatisticsBean createJMXDomainStatistic();
}
