package weblogic.ejb.container.monitoring;

import java.util.HashSet;
import java.util.Set;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.management.ManagementException;
import weblogic.management.runtime.EJBRuntimeMBean;
import weblogic.management.runtime.EJBTransactionRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public abstract class EJBRuntimeMBeanImpl extends RuntimeMBeanDelegate implements EJBRuntimeMBean {
   private final EJBTransactionRuntimeMBean txRtMBean;
   private final String ejbName;
   private final Set resources = new HashSet();

   public EJBRuntimeMBeanImpl(String name, String ejbName, EJBRuntimeHolder component) throws ManagementException {
      super(name, component, true, "EJBRuntimes");
      this.ejbName = ejbName;
      this.txRtMBean = new EJBTransactionRuntimeMBeanImpl(name, this);
   }

   public String getEJBName() {
      return this.ejbName;
   }

   public EJBTransactionRuntimeMBean getTransactionRuntime() {
      return this.txRtMBean;
   }

   public RuntimeMBean[] getResources() {
      return (RuntimeMBean[])this.resources.toArray(new RuntimeMBean[this.resources.size()]);
   }

   public void addResource(RuntimeMBean resource) {
      this.resources.add(resource);
   }
}
