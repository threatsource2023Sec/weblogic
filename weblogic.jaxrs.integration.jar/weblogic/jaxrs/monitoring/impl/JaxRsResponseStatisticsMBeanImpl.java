package weblogic.jaxrs.monitoring.impl;

import java.util.Map;
import org.glassfish.jersey.server.monitoring.ResponseStatistics;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JaxRsResponseStatisticsRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

final class JaxRsResponseStatisticsMBeanImpl extends RuntimeMBeanDelegate implements JaxRsResponseStatisticsRuntimeMBean {
   private volatile ResponseStatistics stats;

   public JaxRsResponseStatisticsMBeanImpl(String name, RuntimeMBean parent, ResponseStatistics stats) throws ManagementException {
      super(name, parent);
      this.update(stats);
   }

   public void update(ResponseStatistics stats) {
      this.stats = stats;
   }

   public Integer getLastResponseCode() {
      return this.stats.getLastResponseCode();
   }

   public Map getResponseCodes() {
      return this.stats.getResponseCodes();
   }
}
