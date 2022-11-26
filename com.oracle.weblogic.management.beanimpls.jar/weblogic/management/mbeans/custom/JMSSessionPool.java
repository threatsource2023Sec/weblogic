package weblogic.management.mbeans.custom;

import weblogic.management.configuration.JMSConnectionConsumerMBean;
import weblogic.management.configuration.JMSSessionPoolMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;

public class JMSSessionPool extends ConfigurationMBeanCustomizer {
   public JMSSessionPool(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public JMSConnectionConsumerMBean[] getConnectionConsumers() {
      JMSSessionPoolMBean bean = (JMSSessionPoolMBean)this.getMbean();
      return bean.getJMSConnectionConsumers();
   }

   public JMSConnectionConsumerMBean createJMSConnectionConsumer(String name, JMSConnectionConsumerMBean toClone) {
      return (JMSConnectionConsumerMBean)this.getMbean().createChildCopyIncludingObsolete("JMSConnectionConsumer", toClone);
   }

   public void destroyJMSConnectionConsumer(String name, JMSConnectionConsumerMBean toDelete) {
      ((JMSSessionPoolMBean)this.getMbean()).destroyJMSConnectionConsumer(toDelete);
   }
}
