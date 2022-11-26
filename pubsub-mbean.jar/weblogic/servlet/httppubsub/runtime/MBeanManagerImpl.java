package weblogic.servlet.httppubsub.runtime;

import com.bea.httppubsub.Channel;
import com.bea.httppubsub.descriptor.WeblogicPubsubBean;
import com.bea.httppubsub.runtime.MBeanManager;
import com.bea.httppubsub.util.ConfigUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.management.MBeanException;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ChannelRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WebAppComponentRuntimeMBean;
import weblogic.management.runtime.WebPubSubRuntimeMBean;
import weblogic.servlet.internal.WebAppServletContext;

public class MBeanManagerImpl implements MBeanManager {
   private String pubSubServerName;
   private WebAppServletContext servletContext;
   private ConcurrentMap channelRuntimeMap = new ConcurrentHashMap();

   MBeanManagerImpl(WeblogicPubsubBean bean, WebAppServletContext servletContext) {
      this.pubSubServerName = ConfigUtils.getPubSubServerName(bean, servletContext);
      this.servletContext = servletContext;
   }

   public ChannelRuntimeMBean findChannelRuntimeMBean(String channelName) {
      return (ChannelRuntimeMBean)this.channelRuntimeMap.get(channelName);
   }

   public ChannelRuntimeMBean[] findSubChannelRuntimeMBeans(Channel parent) {
      List channels = parent.getSubChannels();
      List result = new ArrayList();
      Iterator var4 = channels.iterator();

      while(var4.hasNext()) {
         Channel channel = (Channel)var4.next();
         result.add(this.channelRuntimeMap.get(channel.getName()));
      }

      return (ChannelRuntimeMBean[])result.toArray(new ChannelRuntimeMBean[result.size()]);
   }

   public String getPubSubServerName() {
      return this.pubSubServerName;
   }

   public void registerChannelRuntimeMBean(Channel channel) throws MBeanException {
      if (channel.getParentChannel() == null) {
         this.registerRootChannelRuntimeMBean(channel);
      } else {
         this.registerSubChannelRuntimeMBean(channel);
      }

   }

   public void registerWebPubSubRuntimeMBean() throws MBeanException {
      WebAppComponentRuntimeMBean webAppRuntime = this.servletContext.getRuntimeMBean();

      try {
         WebPubSubRuntimeMBean webPubSubRuntime = new WebPubSubRuntimeMBeanImpl(this.pubSubServerName, webAppRuntime, this);
         webAppRuntime.setWebPubSubRuntime(webPubSubRuntime);
      } catch (ManagementException var3) {
         throw new MBeanException(var3);
      }
   }

   public void unregisterChannelRuntimeMBean(Channel channel) throws MBeanException {
      this.unregister(this.findChannelRuntimeMBean(channel.getName()));
      this.channelRuntimeMap.remove(channel.getName());
   }

   public void unregisterWebPubSubRuntimeMBean() throws MBeanException {
      this.unregister(this.servletContext.getRuntimeMBean().getWebPubSubRuntime());
   }

   private void unregister(RuntimeMBean runtime) throws MBeanException {
      try {
         ((RuntimeMBeanDelegate)runtime).unregister();
      } catch (ManagementException var3) {
         throw new MBeanException(var3);
      }
   }

   private void registerRootChannelRuntimeMBean(Channel channel) throws MBeanException {
      WebPubSubRuntimeMBean webPubSubRuntime = this.servletContext.getRuntimeMBean().getWebPubSubRuntime();

      try {
         ChannelRuntimeMBeanImpl channelRuntime = new ChannelRuntimeMBeanImpl(channel.getName(), webPubSubRuntime, this, channel);
         this.channelRuntimeMap.put("/", channelRuntime);
      } catch (ManagementException var4) {
         throw new MBeanException(var4);
      }
   }

   private void registerSubChannelRuntimeMBean(Channel channel) throws MBeanException {
      ChannelRuntimeMBeanImpl parent = (ChannelRuntimeMBeanImpl)this.channelRuntimeMap.get(channel.getParentChannel().getName());

      try {
         ChannelRuntimeMBeanImpl channelRuntime = new ChannelRuntimeMBeanImpl(channel.getName(), parent, this, channel);
         this.channelRuntimeMap.put(channel.getName(), channelRuntime);
      } catch (ManagementException var4) {
         throw new MBeanException(var4);
      }
   }
}
