package com.bea.httppubsub.jms.internal;

import com.bea.httppubsub.PubSubServer;
import com.bea.httppubsub.PubSubServerException;
import com.bea.httppubsub.bayeux.errors.ErrorFactory;
import com.bea.httppubsub.bayeux.handlers.BayeuxHandlerFactoryImpl;
import com.bea.httppubsub.descriptor.ChannelBean;
import com.bea.httppubsub.descriptor.JmsHandlerBean;
import com.bea.httppubsub.descriptor.WeblogicPubsubBean;
import com.bea.httppubsub.internal.BayeuxHandler;
import com.bea.httppubsub.internal.ChannelId;
import com.bea.httppubsub.jms.PubSubJmsLogger;
import com.bea.httppubsub.jms.provider.JmsProvider;
import com.bea.httppubsub.jms.provider.JmsProviderFactory;
import com.bea.httppubsub.jms.provider.JmsProviderFactoryFinder;
import com.bea.httppubsub.jms.utils.ToStringUtils;
import com.bea.httppubsub.util.ConfigUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JmsBayeuxHandlerFactoryImpl extends BayeuxHandlerFactoryImpl {
   private final List handlerConfigurations;

   public JmsBayeuxHandlerFactoryImpl(PubSubServer pubSubServer, ErrorFactory errorFactory, WeblogicPubsubBean configuration) throws PubSubServerException {
      super(pubSubServer, errorFactory, configuration);
      this.handlerConfigurations = this.initializeHandlerConfigurations(configuration, pubSubServer, errorFactory);
   }

   protected BayeuxHandler doGetPublishRequestHandler(ChannelId cid) {
      Iterator var2 = this.handlerConfigurations.iterator();

      HandlerConfiguration config;
      do {
         if (!var2.hasNext()) {
            return super.doGetPublishRequestHandler(cid);
         }

         config = (HandlerConfiguration)var2.next();
      } while(!config.getChannelId().contains(cid));

      return config.getBayeuxHandler();
   }

   public void destroy() {
      super.destroy();
      Iterator var1 = this.handlerConfigurations.iterator();

      while(var1.hasNext()) {
         HandlerConfiguration handlerConfiguration = (HandlerConfiguration)var1.next();
         handlerConfiguration.getBayeuxHandler().destroy();
      }

      this.handlerConfigurations.clear();
   }

   private List initializeHandlerConfigurations(WeblogicPubsubBean configuration, PubSubServer pubSubServer, ErrorFactory errorFactory) {
      List result = new ArrayList();
      ChannelBean[] channelBeans = configuration.getChannels();
      if (channelBeans != null && channelBeans.length != 0) {
         Map mappings = ConfigUtils.getJmsHandlerMappings(configuration);
         PubSubJmsLogger.logJmsHandlerMappingCount(mappings.size());
         Iterator var7 = mappings.entrySet().iterator();

         while(var7.hasNext()) {
            Map.Entry entry = (Map.Entry)var7.next();
            PubSubJmsLogger.logJmsHandlerMapping((String)entry.getKey(), ToStringUtils.jmsHandlerBeanToString((JmsHandlerBean)entry.getValue()));
         }

         ChannelBean[] var17 = channelBeans;
         int var19 = channelBeans.length;

         for(int var9 = 0; var9 < var19; ++var9) {
            ChannelBean each = var17[var9];
            String channelUrl = each.getChannelPattern();
            String jmsHandlerName = each.getJmsHandlerName();
            JmsHandlerBean jmsBean = (JmsHandlerBean)mappings.get(jmsHandlerName);
            if (jmsBean != null) {
               JmsPublishRequestHandler handler = new JmsPublishRequestHandler();
               handler.setPubSubServer(pubSubServer);
               handler.setErrorFactory(errorFactory);
               JmsProviderFactory factory = JmsProviderFactoryFinder.getFactory();
               JmsProvider provider = factory.createJmsProvider(jmsBean);
               handler.setConnectionFactory(provider.getConnectionFactory());
               handler.setTopic(provider.getTopic());
               handler.setChannelPattern(channelUrl);
               handler.initialize();
               result.add(new HandlerConfiguration(ChannelId.newInstance(channelUrl), handler));
            } else {
               result.add(new HandlerConfiguration(ChannelId.newInstance(channelUrl), super.doGetPublishRequestHandler((ChannelId)null)));
            }
         }

         HandlerConfiguration[] configs = (HandlerConfiguration[])result.toArray(new HandlerConfiguration[result.size()]);
         Arrays.sort(configs);
         result.clear();
         result.addAll(Arrays.asList(configs));
         Iterator var20 = result.iterator();

         while(var20.hasNext()) {
            HandlerConfiguration entry = (HandlerConfiguration)var20.next();
            PubSubJmsLogger.logChannelHandlerMapping(entry.getChannelId().toUrl(), entry.getBayeuxHandler().getClass().getName());
         }

         return result;
      } else {
         PubSubJmsLogger.logNoChannelBeanConfigured();
         return result;
      }
   }

   private static class HandlerConfiguration implements Comparable {
      private final ChannelId channelId;
      private final BayeuxHandler bayeuxHandler;

      private HandlerConfiguration(ChannelId channelId, BayeuxHandler bayeuxHandler) {
         this.channelId = channelId;
         this.bayeuxHandler = bayeuxHandler;
      }

      public ChannelId getChannelId() {
         return this.channelId;
      }

      public BayeuxHandler getBayeuxHandler() {
         return this.bayeuxHandler;
      }

      public int compareTo(HandlerConfiguration o) {
         return this.getChannelId().compareTo(o.getChannelId());
      }

      // $FF: synthetic method
      HandlerConfiguration(ChannelId x0, BayeuxHandler x1, Object x2) {
         this(x0, x1);
      }
   }
}
