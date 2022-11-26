package weblogic.servlet.httppubsub.runtime;

import com.bea.httppubsub.runtime.MBeanManager;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ChannelRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WebPubSubRuntimeMBean;

public class WebPubSubRuntimeMBeanImpl extends RuntimeMBeanDelegate implements WebPubSubRuntimeMBean {
   private MBeanManagerImpl mBeanManager;

   public WebPubSubRuntimeMBeanImpl(String name, RuntimeMBean parent, MBeanManager mBeanManager) throws ManagementException {
      super(name, parent);
      this.mBeanManager = (MBeanManagerImpl)mBeanManager;
   }

   public ChannelRuntimeMBean getChannel(String name) {
      return this.mBeanManager.findChannelRuntimeMBean(name);
   }

   public ChannelRuntimeMBean getRootChannel() {
      return this.mBeanManager.findChannelRuntimeMBean("/");
   }
}
