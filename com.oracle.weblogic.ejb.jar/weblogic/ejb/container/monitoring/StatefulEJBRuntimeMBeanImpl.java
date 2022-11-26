package weblogic.ejb.container.monitoring;

import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.management.ManagementException;
import weblogic.management.runtime.EJBCacheRuntimeMBean;
import weblogic.management.runtime.EJBLockingRuntimeMBean;
import weblogic.management.runtime.StatefulEJBRuntimeMBean;

public final class StatefulEJBRuntimeMBeanImpl extends EJBRuntimeMBeanImpl implements StatefulEJBRuntimeMBean {
   private static final long serialVersionUID = 2679142992650106674L;
   private final EJBCacheRuntimeMBean cacheRtMBean;
   private final EJBLockingRuntimeMBean lockingRtMBean;

   public StatefulEJBRuntimeMBeanImpl(BeanInfo bi, EJBRuntimeHolder component) throws ManagementException {
      super(bi.getEJBName(), bi.getEJBName(), component);
      this.cacheRtMBean = new EJBCacheRuntimeMBeanImpl(this.name, bi, this);
      this.lockingRtMBean = new EJBLockingRuntimeMBeanImpl(this.name, this);
   }

   public EJBCacheRuntimeMBean getCacheRuntime() {
      return this.cacheRtMBean;
   }

   public EJBLockingRuntimeMBean getLockingRuntime() {
      return this.lockingRtMBean;
   }
}
