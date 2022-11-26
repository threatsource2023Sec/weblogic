package com.bea.httppubsub.internal;

import com.bea.httppubsub.PubSubLogger;
import com.bea.httppubsub.PubSubServer;
import com.bea.httppubsub.PubSubServerFactory;
import com.bea.httppubsub.bayeux.errors.ErrorFactory;
import com.bea.httppubsub.bayeux.errors.ErrorFactoryImpl;
import com.bea.httppubsub.descriptor.ServerConfigBean;
import com.bea.httppubsub.descriptor.WeblogicPubsubBean;
import com.bea.httppubsub.security.ChannelAuthorizationManager;
import com.bea.httppubsub.security.ChannelAuthorizationManagerFactory;
import com.bea.httppubsub.security.DelegateChannelAuthManager;
import com.bea.httppubsub.security.NullChannelAuthorizationManagerImpl;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletContext;
import weblogic.diagnostics.debug.DebugLogger;

public class PubSubServerFactoryImpl implements PubSubServerFactory {
   private final ConcurrentHashMap servers = new ConcurrentHashMap();

   public PubSubServer createPubSubServer(String name, WeblogicPubsubBean bean) {
      ChannelAuthorizationManagerFactory authManagerFactory = getChannelAuthorizationManagerFactory(bean);
      return this.createPubSubServer(name, (String)null, bean, authManagerFactory, (ServletContext)null);
   }

   public PubSubServer createPubSubServer(Hashtable env) {
      String name = (String)env.get("com.bea.httppubsub.ServerName");
      WeblogicPubsubBean bean = (WeblogicPubsubBean)env.get("com.bea.httppubsub.PubSubBean");
      ChannelAuthorizationManagerFactory authManagerFactory = (ChannelAuthorizationManagerFactory)env.get("com.bea.httppubsub.security.ChannelAuthorizationManagerFactory");
      ServletContext context = (ServletContext)env.get("com.bea.httppubsub.ServletContext");
      if (authManagerFactory == null) {
         authManagerFactory = getChannelAuthorizationManagerFactory(bean);
      }

      String contextPath = (String)env.get("com.bea.httppubsub.ServletContextPath");
      return this.createPubSubServer(name, contextPath, bean, authManagerFactory, context);
   }

   private PubSubServer createPubSubServer(String name, String contextPath, WeblogicPubsubBean bean, ChannelAuthorizationManagerFactory atzFactory, ServletContext servletContext) {
      if (bean == null) {
         throw new IllegalArgumentException("bean is null");
      } else if (atzFactory == null) {
         throw new IllegalArgumentException("atzFactory is null");
      } else {
         ServerConfigBean scb = bean.getServerConfig();
         if (scb == null && name == null || scb != null && scb.getName() == null && name == null) {
            throw new IllegalArgumentException();
         } else {
            if (scb != null && scb.getName() != null) {
               name = scb.getName();
            }

            if (this.servers.containsKey(name)) {
               PubSubLogger.logDuplicateInitServer(name);
            }

            PubSubServerImpl server = new PubSubServerImpl(bean, name);
            server.getContext().setServletContext(servletContext);
            server.getContext().setServletContextPath(contextPath);
            ErrorFactory errorFactory = new ErrorFactoryImpl();
            server.setErrorFactory(errorFactory);
            server.setMessageFactory(new MessageFactoryImpl());
            ChannelAuthorizationManager authManager = atzFactory.getChannelAuthorizationManager();
            PubSubLogger.logChannelAutorizationManagerFactory(authManager.toString());
            ChannelAuthorizationManager authManager = new DelegateChannelAuthManager(authManager);

            try {
               authManager.initialize(server.getContext());
            } catch (IllegalStateException var15) {
               PubSubLogger.logErrorInitChannelAuthorizationManager(var15);
               throw var15;
            }

            server.setChannelAuthorizationManager(authManager);
            String workManagerName = scb == null ? null : scb.getWorkManager();
            ChannelManagerFactory channelManagerFactory = new ChannelManagerFactoryImpl(name, new ChannelEventPublisherImpl(name, workManagerName), bean, authManager);
            ChannelManager channelManager = channelManagerFactory.getChannelManager();
            server.setChannelManager(channelManager);
            server.init();
            this.servers.put(name, server);
            ClientManagerImpl clientManager = new ClientManagerImpl();
            clientManager.setPubSubServer(server);
            clientManager.setStartTimeListener(true);
            clientManager.init();
            BayeuxHandlerFactoryBuilder handlerFactoryBuilder = new BayeuxHandlerFactoryBuilderImpl(server, errorFactory, bean);
            handlerFactoryBuilder.build();
            PubSubLogger.logInitServer(name);
            return server;
         }
      }
   }

   public PubSubServer lookupPubSubServer(String name) {
      return (PubSubServer)this.servers.get(name);
   }

   public void removePubSubServer(PubSubServer server) {
      if (server != null) {
         PubSubServerImpl pubsub = (PubSubServerImpl)this.servers.remove(server.getName());
         pubsub.destroy();
      }
   }

   public void removeAllPubSubServers() {
      for(Iterator it = this.servers.keySet().iterator(); it.hasNext(); it.remove()) {
         String serverName = (String)it.next();
         if (serverName != null) {
            this.removePubSubServer((PubSubServer)this.servers.get(serverName));
         }
      }

   }

   private static ChannelAuthorizationManagerFactory getChannelAuthorizationManagerFactory(WeblogicPubsubBean bean) {
      DebugLogger logger = DebugLogger.getDebugLogger("DebugPubSubSecurity");
      if (Boolean.getBoolean("com.bea.httppubsub.security.Disabled")) {
         PubSubLogger.logSecurityAuthorizationDisabled();
         return new ChannelAuthorizationManagerFactory() {
            public ChannelAuthorizationManager getChannelAuthorizationManager() {
               return new NullChannelAuthorizationManagerImpl();
            }
         };
      } else {
         String factoryClassName = System.getProperty("com.bea.httppubsub.security.ChannelAuthorizationManagerFactory");
         if (logger.isDebugEnabled()) {
            logger.debug("Loaded property name:com.bea.httppubsub.security.ChannelAuthorizationManagerFactory value:" + factoryClassName);
         }

         if (factoryClassName == null) {
            factoryClassName = "com.bea.httppubsub.security.wls.WlsChannelAuthorizationManagerFactory";
         }

         if (factoryClassName != null) {
            try {
               ClassLoader cl = Thread.currentThread().getContextClassLoader();
               Class factoryClass = cl != null ? cl.loadClass(factoryClassName) : Class.forName(factoryClassName);
               Object obj = factoryClass.newInstance();
               if (logger.isDebugEnabled()) {
                  logger.debug("Loaded ATZ Factory: " + factoryClass);
               }

               return (ChannelAuthorizationManagerFactory)obj;
            } catch (Throwable var6) {
               if (logger.isDebugEnabled()) {
                  logger.debug("Error loading com.bea.httppubsub.security.ChannelAuthorizationManagerFactory factoryClassName:" + factoryClassName, var6);
               }

               throw new IllegalStateException("ChannelAuthorizationManagerFactory cannot be loaded.", var6);
            }
         } else {
            throw new IllegalStateException("No channel authorization manager factory");
         }
      }
   }
}
