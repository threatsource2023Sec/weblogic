package weblogic.management.j2ee.internal;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.QueryExp;

public class J2EEManagedObjectMBeanImpl {
   protected final String name;

   public J2EEManagedObjectMBeanImpl(String objectname) {
      this.name = objectname;
   }

   public boolean iseventProvider() {
      return false;
   }

   public boolean isstatisticsProvider() {
      return false;
   }

   public boolean isstateManageable() {
      return false;
   }

   public String getobjectName() {
      return this.name;
   }

   protected String[] queryNames(ObjectName pattern) {
      return JMOService.getJMOService().getQueriedNames(pattern);
   }

   protected String[] queryNames(QueryExp query) {
      return JMOService.getJMOService().getQueriedNames(query);
   }

   protected String getName(ObjectName pattern) throws InstanceNotFoundException {
      MBeanServer mbeanServer = JMOService.getJMOService().getJMOMBeanServer();
      return mbeanServer.getObjectInstance(pattern).getObjectName().getCanonicalName();
   }
}
