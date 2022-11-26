package com.bea.httppubsub.internal;

import com.bea.httppubsub.descriptor.WeblogicPubsubBean;
import com.bea.httppubsub.security.ChannelAuthorizationManager;
import com.bea.httppubsub.util.ConfigUtils;
import com.bea.httppubsub.util.StringUtils;
import java.lang.reflect.Constructor;

public class ChannelManagerFactoryImpl implements ChannelManagerFactory {
   private static String DEFAULT_JMS_CHANNEL_MANAGER_CLASS = "com.bea.httppubsub.jms.internal.JmsChannelManagerImpl";
   private final ChannelManager channelManager;

   public ChannelManagerFactoryImpl(String serverName, ChannelEventPublisher channelEventPublisher, WeblogicPubsubBean configuration, ChannelAuthorizationManager authManager) {
      if (ConfigUtils.isJmsHandlerEnable(configuration)) {
         this.channelManager = this.createJmsChannelManager(this.getJmsChannelManagerClassName(), serverName, channelEventPublisher, authManager, configuration);
      } else {
         this.channelManager = new ChannelManagerImpl(serverName, channelEventPublisher, authManager, configuration);
      }

   }

   public ChannelManager getChannelManager() {
      return this.channelManager;
   }

   private ChannelManager createJmsChannelManager(String className, String pubsubServerName, ChannelEventPublisher channelEventPublisher, ChannelAuthorizationManager authManager, WeblogicPubsubBean configuration) {
      try {
         Class cls = Thread.currentThread().getContextClassLoader().loadClass(className);
         Constructor constructor = cls.getConstructor(String.class, ChannelEventPublisher.class, ChannelAuthorizationManager.class, WeblogicPubsubBean.class);
         return (ChannelManager)constructor.newInstance(pubsubServerName, channelEventPublisher, authManager, configuration);
      } catch (Exception var8) {
         throw new RuntimeException("Cannot create JMS ChannelManager [" + className + "].", var8);
      }
   }

   private String getJmsChannelManagerClassName() {
      String result = System.getProperty("jms.channel.manager.class");
      return StringUtils.isEmpty(result) ? DEFAULT_JMS_CHANNEL_MANAGER_CLASS : result;
   }
}
