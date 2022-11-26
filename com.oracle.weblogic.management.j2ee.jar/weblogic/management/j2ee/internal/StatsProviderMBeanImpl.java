package weblogic.management.j2ee.internal;

import javax.management.j2ee.statistics.Stats;
import weblogic.management.j2ee.StatsProviderMBean;

public class StatsProviderMBeanImpl extends J2EEResourceMBeanImpl implements StatsProviderMBean {
   public StatsProviderMBeanImpl(String objectName) {
      super(objectName);
   }

   public Stats getstats() {
      return null;
   }
}
