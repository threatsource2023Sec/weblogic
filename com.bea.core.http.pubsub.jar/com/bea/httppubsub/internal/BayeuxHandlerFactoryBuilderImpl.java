package com.bea.httppubsub.internal;

import com.bea.httppubsub.PubSubServer;
import com.bea.httppubsub.bayeux.errors.ErrorFactory;
import com.bea.httppubsub.descriptor.WeblogicPubsubBean;
import com.bea.httppubsub.util.ConfigUtils;
import com.bea.httppubsub.util.StringUtils;
import java.lang.reflect.Constructor;
import weblogic.diagnostics.debug.DebugLogger;

public class BayeuxHandlerFactoryBuilderImpl implements BayeuxHandlerFactoryBuilder {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugPubSubServer");
   private static final String DEFAULT_JMS_BAYEUX_HANDLER_FACTORY_CLASS = "com.bea.httppubsub.jms.internal.JmsBayeuxHandlerFactoryImpl";
   private final RegistrablePubSubServer server;
   private final ErrorFactory errorFactory;
   private final WeblogicPubsubBean configuration;

   public BayeuxHandlerFactoryBuilderImpl(RegistrablePubSubServer server, ErrorFactory errorFactory, WeblogicPubsubBean configuration) {
      if (server == null) {
         throw new IllegalArgumentException("Server cannot be null");
      } else {
         this.server = server;
         this.errorFactory = errorFactory;
         this.configuration = configuration;
      }
   }

   public BayeuxHandlerFactory build() {
      BayeuxHandlerFactory result;
      if (ConfigUtils.isJmsHandlerEnable(this.configuration)) {
         result = this.createJmsRequestHandlerFactory(this.getJmsBayeuxHandlerFactoryClassName(), this.server, this.errorFactory, this.configuration);
      } else {
         result = this.createGenericRequestHandlerFactory(this.server, this.errorFactory, this.configuration);
      }

      this.server.registerBayeuxHandlerFactory(result);
      return result;
   }

   private BayeuxHandlerFactory createGenericRequestHandlerFactory(PubSubServer server, ErrorFactory errorFactory, WeblogicPubsubBean config) {
      String className = "com.bea.httppubsub.bayeux.handlers.BayeuxHandlerFactoryImpl";
      if (logger.isDebugEnabled()) {
         logger.debug("----- Init BayeuxHandlerFactory: [" + className + "]");
      }

      try {
         Class cls = Thread.currentThread().getContextClassLoader().loadClass(className);
         Constructor constructor = cls.getConstructor(PubSubServer.class, ErrorFactory.class, WeblogicPubsubBean.class);
         return (BayeuxHandlerFactory)constructor.newInstance(server, errorFactory, config);
      } catch (Exception var7) {
         throw new RuntimeException("Cannot create BayeuxHandlerFactory [" + className + "].", var7);
      }
   }

   private BayeuxHandlerFactory createJmsRequestHandlerFactory(String className, PubSubServer pubSubServer, ErrorFactory errorFactory, WeblogicPubsubBean configuration) {
      if (logger.isDebugEnabled()) {
         logger.debug("----- Init BayeuxHandlerFactory: [" + className + "]");
      }

      try {
         Class cls = Thread.currentThread().getContextClassLoader().loadClass(className);
         Constructor constructor = cls.getConstructor(PubSubServer.class, ErrorFactory.class, WeblogicPubsubBean.class);
         return (BayeuxHandlerFactory)constructor.newInstance(pubSubServer, errorFactory, configuration);
      } catch (Exception var7) {
         throw new RuntimeException("Cannot create JMS BayeuxHandlerFactory [" + className + "].", var7);
      }
   }

   private String getJmsBayeuxHandlerFactoryClassName() {
      String result = System.getProperty("jms.bayeux.handler.factory.class");
      return StringUtils.isEmpty(result) ? "com.bea.httppubsub.jms.internal.JmsBayeuxHandlerFactoryImpl" : result;
   }
}
