package weblogic.servlet.httppubsub.runtime;

import com.bea.httppubsub.Channel;
import com.bea.httppubsub.Client;
import com.bea.httppubsub.Channel.ChannelPattern;
import com.bea.httppubsub.runtime.MBeanManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ChannelRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class ChannelRuntimeMBeanImpl extends RuntimeMBeanDelegate implements ChannelRuntimeMBean {
   private Channel channel;
   private MBeanManagerImpl mBeanManager;

   public ChannelRuntimeMBeanImpl(String name, RuntimeMBean parent, MBeanManager mBeanManager, Channel channel) throws ManagementException {
      super(name, parent);
      this.mBeanManager = (MBeanManagerImpl)mBeanManager;
      this.channel = channel;
   }

   public long getPublishedMessageCount() {
      return this.channel.getPublishedMessageCount();
   }

   public ChannelRuntimeMBean[] getSubChannels() {
      return this.mBeanManager.findSubChannelRuntimeMBeans(this.channel);
   }

   public int getSubscriberCount() {
      return this.channel.getClients(ChannelPattern.ITSELF).size();
   }

   public List getSubscribers() {
      List clients = this.channel.getClients(ChannelPattern.ITSELF);
      List clientIds = new ArrayList(clients.size());
      Iterator var3 = clients.iterator();

      while(var3.hasNext()) {
         Client client = (Client)var3.next();
         clientIds.add(client.getId());
      }

      return clientIds;
   }
}
