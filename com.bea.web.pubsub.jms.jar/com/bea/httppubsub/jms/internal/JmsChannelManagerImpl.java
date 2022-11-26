package com.bea.httppubsub.jms.internal;

import com.bea.httppubsub.descriptor.ChannelBean;
import com.bea.httppubsub.descriptor.JmsHandlerBean;
import com.bea.httppubsub.descriptor.WeblogicPubsubBean;
import com.bea.httppubsub.internal.ChannelEventPublisher;
import com.bea.httppubsub.internal.ChannelManagerImpl;
import com.bea.httppubsub.jms.provider.JmsProvider;
import com.bea.httppubsub.jms.provider.JmsProviderFactory;
import com.bea.httppubsub.jms.provider.JmsProviderFactoryFinder;
import com.bea.httppubsub.security.ChannelAuthorizationManager;
import com.bea.httppubsub.util.ConfigUtils;
import com.bea.httppubsub.util.StringUtils;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.jms.ConnectionFactory;
import javax.jms.Topic;

public class JmsChannelManagerImpl extends ChannelManagerImpl {
   private final Set jmsTopicListeners = new HashSet();

   public JmsChannelManagerImpl(String pubsubServerName, ChannelEventPublisher channelEventPublisher, ChannelAuthorizationManager channelAuthorizationManager, WeblogicPubsubBean configuration) {
      super(pubsubServerName, channelEventPublisher, channelAuthorizationManager, configuration);
      this.initJmsTopicListeners(configuration);
   }

   public void destroy() {
      super.destroy();
      Iterator var1 = this.jmsTopicListeners.iterator();

      while(var1.hasNext()) {
         TopicListener topicListener = (TopicListener)var1.next();
         topicListener.destroy();
      }

   }

   private void initJmsTopicListeners(WeblogicPubsubBean configuration) {
      Map mappings = ConfigUtils.getJmsHandlerMappings(configuration);
      ChannelBean[] channelMappingBeans = configuration.getChannels();
      if (channelMappingBeans != null && channelMappingBeans.length != 0) {
         JmsProviderFactory factory = JmsProviderFactoryFinder.getFactory();
         ChannelBean[] var5 = channelMappingBeans;
         int var6 = channelMappingBeans.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            ChannelBean channelMappingBean = var5[var7];
            String jmsHandlerName = channelMappingBean.getJmsHandlerName();
            if (StringUtils.isNotEmpty(jmsHandlerName)) {
               JmsHandlerBean jmsHandlerBean = (JmsHandlerBean)mappings.get(jmsHandlerName);
               String channelPattern = channelMappingBean.getChannelPattern();
               TopicListener topicListener = new TopicListener(channelPattern, this);
               JmsProvider jmsProvider = factory.createJmsProvider(jmsHandlerBean);
               ConnectionFactory connectionFactory = jmsProvider.getConnectionFactory();
               Topic topic = jmsProvider.getTopic();
               topicListener.initialize(connectionFactory, topic);
               this.jmsTopicListeners.add(topicListener);
            }
         }

      }
   }
}
