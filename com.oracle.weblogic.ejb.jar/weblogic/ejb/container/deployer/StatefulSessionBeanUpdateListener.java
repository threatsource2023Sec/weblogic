package weblogic.ejb.container.deployer;

import weblogic.descriptor.DescriptorBean;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.CachingManager;
import weblogic.ejb.container.interfaces.StatefulSessionBeanInfo;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.j2ee.descriptor.wl.StatefulSessionCacheBean;
import weblogic.j2ee.descriptor.wl.StatefulSessionDescriptorBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBean;

public class StatefulSessionBeanUpdateListener extends BaseBeanUpdateListener {
   private final StatefulSessionBeanInfo sbi;

   protected StatefulSessionBeanUpdateListener(StatefulSessionBeanInfo sbi) {
      super(sbi);
      this.sbi = sbi;
   }

   protected void addBeanUpdateListener(WeblogicEnterpriseBeanBean wlBean, EjbDescriptorBean ejbDesc) {
      super.addBeanUpdateListener(wlBean, ejbDesc);
      StatefulSessionDescriptorBean sfsd = wlBean.getStatefulSessionDescriptor();
      ((DescriptorBean)sfsd.getStatefulSessionCache()).addBeanUpdateListener(this);
   }

   protected void removeBeanUpdateListener(WeblogicEnterpriseBeanBean wlBean, EjbDescriptorBean ejbDesc) {
      super.removeBeanUpdateListener(wlBean, ejbDesc);
      StatefulSessionDescriptorBean sfsd = wlBean.getStatefulSessionDescriptor();
      ((DescriptorBean)sfsd.getStatefulSessionCache()).removeBeanUpdateListener(this);
   }

   protected void handleProperyChange(String propertyName, DescriptorBean newBean) {
      StatefulSessionCacheBean cache;
      if (propertyName.equals("MaxBeansInCache")) {
         cache = (StatefulSessionCacheBean)newBean;
         this.updateMaxBeansInCache(cache.getMaxBeansInCache());
      } else {
         if (!propertyName.equals("IdleTimeoutSeconds")) {
            throw new AssertionError("Unexpected propertyName: " + propertyName);
         }

         cache = (StatefulSessionCacheBean)newBean;
         this.updateCacheIdleTimeoutSeconds(cache.getIdleTimeoutSeconds());
      }

   }

   private void updateMaxBeansInCache(int max) {
      BeanManager bm = this.sbi.getBeanManager();
      if (bm instanceof CachingManager) {
         ((CachingManager)bm).updateMaxBeansInCache(max);
      }

      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("updated MaxBeansInCache to " + max + " for EJB " + this.sbi.getDisplayName());
      }

   }

   private void updateCacheIdleTimeoutSeconds(int seconds) {
      if (!this.sbi.isStatefulTimeoutConfigured()) {
         BeanManager bm = this.sbi.getBeanManager();
         if (bm instanceof CachingManager) {
            CachingManager cm = (CachingManager)bm;
            cm.updateIdleTimeoutSecondsCache(seconds);
         }

         if (DEBUG_LOGGER.isDebugEnabled()) {
            debug("updated Cache IdleTimeoutSeconds to " + seconds + " for EJB " + this.sbi.getDisplayName());
         }

      }
   }

   private static void debug(String s) {
      DEBUG_LOGGER.debug("[SessionBeanUpdateListener] " + s);
   }
}
