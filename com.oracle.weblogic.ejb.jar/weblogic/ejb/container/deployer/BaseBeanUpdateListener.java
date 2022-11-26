package weblogic.ejb.container.deployer;

import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.internal.TransactionService;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.j2ee.descriptor.wl.TransactionDescriptorBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.JTAMBean;
import weblogic.management.configuration.JTAPartitionMBean;

public abstract class BaseBeanUpdateListener implements BeanUpdateListener {
   protected static final DebugLogger DEBUG_LOGGER;
   private final BeanInfo bi;

   protected BaseBeanUpdateListener(BeanInfo bi) {
      this.bi = bi;
   }

   protected void addBeanUpdateListener(WeblogicEnterpriseBeanBean wlBean, EjbDescriptorBean ejbDescBean) {
      DescriptorBean db = (DescriptorBean)wlBean.getTransactionDescriptor();
      db.addBeanUpdateListener(this);
      TransactionService.getJTAConfigMBean(this.bi.getCIC()).addBeanUpdateListener(this);
   }

   protected void removeBeanUpdateListener(WeblogicEnterpriseBeanBean wlBean, EjbDescriptorBean ejbDescBean) {
      DescriptorBean db = (DescriptorBean)wlBean.getTransactionDescriptor();
      db.removeBeanUpdateListener(this);
      TransactionService.getJTAConfigMBean(this.bi.getCIC()).removeBeanUpdateListener(this);
   }

   public final void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("activateUpdate: " + event);
      }

      BeanUpdateEvent.PropertyUpdate[] list = event.getUpdateList();
      DescriptorBean newBean = event.getProposedBean();
      BeanUpdateEvent.PropertyUpdate[] var4 = list;
      int var5 = list.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         BeanUpdateEvent.PropertyUpdate prop = var4[var6];
         switch (prop.getUpdateType()) {
            case 1:
               String propertyName = prop.getPropertyName();
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  debug("Changing property of type: " + propertyName);
               }

               if (propertyName.equals("TransTimeoutSeconds")) {
                  TransactionDescriptorBean td = (TransactionDescriptorBean)newBean;
                  if (td.getTransTimeoutSeconds() == 0) {
                     this.bi.setTransactionTimeoutSeconds(TransactionService.getJTAConfigTimeout(this.bi.getCIC()), true);
                  } else {
                     this.bi.setTransactionTimeoutSeconds(td.getTransTimeoutSeconds(), false);
                  }
               } else if (!(event.getSourceBean() instanceof JTAMBean) && !(event.getSourceBean() instanceof JTAPartitionMBean)) {
                  this.handleProperyChange(propertyName, newBean);
               } else if (propertyName.equals("TimeoutSeconds") && this.bi.isUsingJTAConfigTimeout()) {
                  this.bi.setTransactionTimeoutSeconds(TransactionService.getJTAConfigTimeout(this.bi.getCIC()), true);
               }
               break;
            case 2:
            case 3:
               if (!(event.getSourceBean() instanceof ConfigurationMBean)) {
                  throw new IllegalStateException("Unexpected BeanUpdateEvent: " + event);
               }
         }
      }

   }

   public void prepareUpdate(BeanUpdateEvent event) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("prepareUpdate: " + event);
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("rollbackUpdate: " + event);
      }

   }

   protected abstract void handleProperyChange(String var1, DescriptorBean var2) throws BeanUpdateFailedException;

   private static void debug(String s) {
      DEBUG_LOGGER.debug("[BaseBeanUpdateListener] " + s);
   }

   static {
      DEBUG_LOGGER = EJBDebugService.deploymentLogger;
   }
}
