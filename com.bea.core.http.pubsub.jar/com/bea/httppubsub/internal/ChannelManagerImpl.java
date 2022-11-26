package com.bea.httppubsub.internal;

import com.bea.httppubsub.Channel;
import com.bea.httppubsub.Client;
import com.bea.httppubsub.PubSubLogger;
import com.bea.httppubsub.descriptor.ChannelBean;
import com.bea.httppubsub.descriptor.ChannelPersistenceBean;
import com.bea.httppubsub.descriptor.WeblogicPubsubBean;
import com.bea.httppubsub.runtime.MBeanManager;
import com.bea.httppubsub.runtime.MBeanManagerHelper;
import com.bea.httppubsub.security.ChannelAuthorizationManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.management.MBeanException;

public class ChannelManagerImpl implements ChannelManager {
   protected final String pubsubServerName;
   private final ChannelEventPublisher channelEventPublisher;
   private final ChannelAuthorizationManager channelAuthorizationManager;
   private final List channelPersistenceConfigurations;
   private Channel rootChannel = null;

   public ChannelManagerImpl(String pubsubServerName, ChannelEventPublisher channelEventPublisher, ChannelAuthorizationManager channelAuthorizationManager, WeblogicPubsubBean configuration) {
      this.pubsubServerName = pubsubServerName;
      this.channelEventPublisher = channelEventPublisher;
      this.channelAuthorizationManager = channelAuthorizationManager;
      this.channelPersistenceConfigurations = this.initializeChannelPersistenceConfigurations(configuration);
   }

   public Channel getRootChannel() {
      if (this.rootChannel != null) {
         return this.rootChannel;
      } else {
         synchronized(this) {
            if (this.rootChannel != null) {
               return this.rootChannel;
            } else {
               this.rootChannel = this.initializeRootChannel();
               return this.rootChannel;
            }
         }
      }
   }

   public Channel findChannel(String url) {
      return this.getChannel(ChannelId.newInstance(url), false);
   }

   public Channel findOrCreateChannel(String url) {
      return this.getChannel(ChannelId.newInstance(url), true);
   }

   public Channel createChannel(String url) {
      return this.getChannel(ChannelId.newInstance(url), true);
   }

   public void deleteChannel(String url, Client client) {
      Channel channel = this.findChannel(url);
      if (channel != null) {
         this.deleteChannel(channel, client);
      }

   }

   private void deleteChannel(Channel channel, Client client) {
      if (channel != null) {
         Iterator var3 = channel.getSubChannels().iterator();

         while(var3.hasNext()) {
            Channel subChannel = (Channel)var3.next();
            this.deleteChannel(subChannel, client);
         }

         this.unregisterChannelRuntimeMBean(channel);
         channel.destroy(client);
      }
   }

   protected Channel buildChannelInstance(ChannelEventPublisher eventPublisher, ChannelId cid) {
      return new ChannelImpl(eventPublisher, cid.getChannelName());
   }

   protected Channel buildChannelInstance(ChannelEventPublisher eventPublisher, Channel parent, ChannelId cid) {
      return new ChannelImpl(eventPublisher, parent, cid.getChannelName());
   }

   protected ChannelPersistenceBean getChannelPersistenceBeanByChannelId(ChannelId cid) {
      Iterator var2 = this.channelPersistenceConfigurations.iterator();

      ChannelPersistenceConfiguration config;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         config = (ChannelPersistenceConfiguration)var2.next();
      } while(!config.getChannelId().contains(cid));

      return config.getChannelPersistenceBean();
   }

   public void destroy() {
      this.deleteChannel((String)"/", new LocalClientImpl("local_client"));
   }

   private Channel buildMetaChannelInstance(ChannelEventPublisher eventPublisher, Channel parent, ChannelId cid) {
      return new ChannelImpl(eventPublisher, parent, cid.getChannelName());
   }

   private Channel initializeRootChannel() {
      Channel rootChannel = this.buildChannelInstance(this.channelEventPublisher, ChannelId.newInstance("/"));
      if (rootChannel instanceof InternalChannel) {
         ((InternalChannel)rootChannel).setAuthorizationManager(this.channelAuthorizationManager);
      }

      this.registerChannelRuntimeMBean(rootChannel);
      return rootChannel;
   }

   private Channel getChannel(ChannelId cid, boolean create) {
      ChannelId id = cid.isWild() ? ChannelId.newInstance(cid.getChannelName()) : cid;
      Channel curr = this.getRootChannel();
      StringBuilder currUrl = new StringBuilder("/");

      for(Iterator var6 = id.getSegments().iterator(); var6.hasNext(); currUrl.append("/")) {
         String segment = (String)var6.next();
         currUrl.append(segment);
         boolean found = false;
         Iterator var9 = curr.getSubChannels().iterator();

         while(var9.hasNext()) {
            Channel channel = (Channel)var9.next();
            String channelName = channel.getName();
            String currUrlName = currUrl.toString();
            if (channelName.equals(currUrlName)) {
               curr = channel;
               found = true;
               break;
            }
         }

         if (!found) {
            if (!create) {
               return null;
            }

            ChannelId cidForCreate = ChannelId.newInstance(currUrl.toString());
            if (cidForCreate.getChannelName().startsWith("/meta/")) {
               curr = this.buildMetaChannelInstance(this.channelEventPublisher, curr, cidForCreate);
            } else {
               curr = this.buildChannelInstance(this.channelEventPublisher, curr, cidForCreate);
            }

            if (curr instanceof InternalChannel) {
               ((InternalChannel)curr).setAuthorizationManager(this.channelAuthorizationManager);
            }

            this.registerChannelRuntimeMBean(curr);
         }
      }

      if (curr instanceof InternalChannel) {
         ((InternalChannel)curr).setAuthorizationManager(this.channelAuthorizationManager);
      }

      return curr;
   }

   private void registerChannelRuntimeMBean(Channel channel) {
      if (!channel.isMetaChannel()) {
         try {
            MBeanManager manager = MBeanManagerHelper.getMBeanManager(this.pubsubServerName);
            if (manager != null) {
               manager.registerChannelRuntimeMBean(channel);
            }
         } catch (MBeanException var3) {
            PubSubLogger.logFailRegisterMBean(channel.getName(), var3);
         }

      }
   }

   private void unregisterChannelRuntimeMBean(Channel channel) {
      if (!channel.isMetaChannel()) {
         try {
            MBeanManager manager = MBeanManagerHelper.getMBeanManager(this.pubsubServerName);
            if (manager != null) {
               manager.unregisterChannelRuntimeMBean(channel);
            }
         } catch (MBeanException var3) {
            PubSubLogger.logFailUnregisterMBean(channel.getName(), var3);
         }

      }
   }

   private List initializeChannelPersistenceConfigurations(WeblogicPubsubBean configuration) {
      List result = new ArrayList();
      if (configuration == null) {
         return result;
      } else {
         ChannelBean[] channelBeans = configuration.getChannels();
         if (channelBeans != null && channelBeans.length != 0) {
            ChannelBean[] var4 = channelBeans;
            int var5 = channelBeans.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               ChannelBean channelBean = var4[var6];
               String channelPattern = channelBean.getChannelPattern();
               ChannelPersistenceBean channelPersistenceBean = channelBean.getChannelPersistence();
               ChannelId channelId = ChannelId.newInstance(channelPattern);
               result.add(new ChannelPersistenceConfiguration(channelId, channelPersistenceBean));
            }

            ChannelPersistenceConfiguration[] configs = (ChannelPersistenceConfiguration[])result.toArray(new ChannelPersistenceConfiguration[result.size()]);
            Arrays.sort(configs);
            result.clear();
            result.addAll(Arrays.asList(configs));
            return result;
         } else {
            return result;
         }
      }
   }

   private static class ChannelPersistenceConfiguration implements Comparable {
      private final ChannelId channelId;
      private final ChannelPersistenceBean channelPersistenceBean;

      private ChannelPersistenceConfiguration(ChannelId channelId, ChannelPersistenceBean channelPersistenceBean) {
         this.channelId = channelId;
         this.channelPersistenceBean = channelPersistenceBean;
      }

      public ChannelId getChannelId() {
         return this.channelId;
      }

      public ChannelPersistenceBean getChannelPersistenceBean() {
         return this.channelPersistenceBean;
      }

      public int compareTo(ChannelPersistenceConfiguration o) {
         return this.getChannelId().compareTo(o.getChannelId());
      }

      // $FF: synthetic method
      ChannelPersistenceConfiguration(ChannelId x0, ChannelPersistenceBean x1, Object x2) {
         this(x0, x1);
      }
   }
}
