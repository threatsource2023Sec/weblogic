package weblogic.jaxrs.monitoring.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import org.glassfish.jersey.server.monitoring.ExceptionMapperStatistics;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JaxRsExceptionMapperStatisticsRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

final class JaxRsExceptionMapperStatisticsMBeanImpl extends RuntimeMBeanDelegate implements JaxRsExceptionMapperStatisticsRuntimeMBean {
   private volatile ExceptionMapperStatistics stats;
   private volatile Map execs = new TreeMap();

   public JaxRsExceptionMapperStatisticsMBeanImpl(String name, RuntimeMBean parent, ExceptionMapperStatistics stats) throws ManagementException {
      super(name, parent);
      this.update(stats);
   }

   public void update(ExceptionMapperStatistics stats) {
      this.stats = stats;
      Iterator var2 = stats.getExceptionMapperExecutions().entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         this.execs.put(((Class)entry.getKey()).getName(), entry.getValue());
      }

   }

   public Map getExceptionMapperCount() {
      return this.execs;
   }

   public long getSuccessfulMappings() {
      return this.stats.getSuccessfulMappings();
   }

   public long getUnsuccessfulMappings() {
      return this.stats.getUnsuccessfulMappings();
   }

   public long getTotalMappings() {
      return this.stats.getTotalMappings();
   }
}
