package weblogic.management.j2ee.internal;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.QueryExp;
import weblogic.management.j2ee.EJBModuleMBean;

public final class EJBModuleMBeanImpl extends J2EEModuleMBeanImpl implements EJBModuleMBean {
   public EJBModuleMBeanImpl(String name, String server, String jvm, ApplicationInfo info) {
      super(name, server, jvm, info);
   }

   public String[] getejbs() {
      QueryExp query = new QueryExp() {
         public boolean apply(ObjectName objectName) {
            return JMOTypesHelper.ejbList.contains(objectName.getKeyProperty("j2eeType")) && objectName.getKeyProperty("EJBModule").equals(EJBModuleMBeanImpl.this.getEJBModuleName());
         }

         public void setMBeanServer(MBeanServer server) {
         }
      };
      return this.queryNames(query);
   }

   private String getEJBModuleName() {
      try {
         return (new ObjectName(this.name)).getKeyProperty("name");
      } catch (MalformedObjectNameException var2) {
         throw new AssertionError("MalformedObjectName" + var2);
      }
   }
}
