package weblogic.ejb.container.deployer;

import weblogic.descriptor.DescriptorBean;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.container.manager.StatelessManager;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.j2ee.descriptor.wl.PoolBean;
import weblogic.j2ee.descriptor.wl.StatelessSessionDescriptorBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBean;

public class StatelessSessionBeanUpdateListener extends BaseBeanUpdateListener {
   private final SessionBeanInfo sbi;

   protected StatelessSessionBeanUpdateListener(SessionBeanInfo sbi) {
      super(sbi);
      this.sbi = sbi;
   }

   protected void addBeanUpdateListener(WeblogicEnterpriseBeanBean wlBean, EjbDescriptorBean ejbDesc) {
      super.addBeanUpdateListener(wlBean, ejbDesc);
      StatelessSessionDescriptorBean slsd = wlBean.getStatelessSessionDescriptor();
      ((DescriptorBean)slsd.getPool()).addBeanUpdateListener(this);
   }

   protected void removeBeanUpdateListener(WeblogicEnterpriseBeanBean wlBean, EjbDescriptorBean ejbDesc) {
      super.removeBeanUpdateListener(wlBean, ejbDesc);
      StatelessSessionDescriptorBean slsd = wlBean.getStatelessSessionDescriptor();
      ((DescriptorBean)slsd.getPool()).removeBeanUpdateListener(this);
   }

   protected void handleProperyChange(String propertyName, DescriptorBean newBean) {
      PoolBean pool;
      if (propertyName.equals("MaxBeansInFreePool")) {
         pool = (PoolBean)newBean;
         this.updateMaxBeansInFreePool(pool.getMaxBeansInFreePool());
      } else {
         if (!propertyName.equals("IdleTimeoutSeconds")) {
            throw new AssertionError("Unexpected propertyName: " + propertyName);
         }

         pool = (PoolBean)newBean;
         this.updatePoolIdleTimeoutSeconds(pool.getIdleTimeoutSeconds());
      }

   }

   private void updateMaxBeansInFreePool(int max) {
      StatelessManager sm = (StatelessManager)this.sbi.getBeanManager();
      sm.getPool().updateMaxBeansInFreePool(max);
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("updated MaxBeansInFreePool to " + max + " for EJB " + this.sbi.getDisplayName());
      }

   }

   private void updatePoolIdleTimeoutSeconds(int seconds) {
      StatelessManager sm = (StatelessManager)this.sbi.getBeanManager();
      sm.getPool().updateIdleTimeoutSeconds(seconds);
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("updated Pool IdleTimeoutSeconds to " + seconds + " for EJB " + this.sbi.getDisplayName());
      }

   }

   private static void debug(String s) {
      DEBUG_LOGGER.debug("[SessionBeanUpdateListener] " + s);
   }
}
