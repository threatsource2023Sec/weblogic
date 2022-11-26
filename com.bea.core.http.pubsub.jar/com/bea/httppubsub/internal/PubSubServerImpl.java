package com.bea.httppubsub.internal;

import com.bea.httppubsub.BayeuxMessage;
import com.bea.httppubsub.Channel;
import com.bea.httppubsub.Client;
import com.bea.httppubsub.ClientManager;
import com.bea.httppubsub.EventMessage;
import com.bea.httppubsub.LocalClient;
import com.bea.httppubsub.MessageFactory;
import com.bea.httppubsub.MessageFilter;
import com.bea.httppubsub.PubSubContext;
import com.bea.httppubsub.PubSubLogger;
import com.bea.httppubsub.PubSubSecurityException;
import com.bea.httppubsub.PubSubServerException;
import com.bea.httppubsub.Transport;
import com.bea.httppubsub.bayeux.BayeuxConstants;
import com.bea.httppubsub.bayeux.errors.ErrorFactory;
import com.bea.httppubsub.bayeux.messages.AbstractBayeuxMessage;
import com.bea.httppubsub.bayeux.messages.EventMessageImpl;
import com.bea.httppubsub.descriptor.ChannelBean;
import com.bea.httppubsub.descriptor.MessageFilterBean;
import com.bea.httppubsub.descriptor.ServerConfigBean;
import com.bea.httppubsub.descriptor.SupportedTransportBean;
import com.bea.httppubsub.descriptor.WeblogicPubsubBean;
import com.bea.httppubsub.security.ChannelAuthorizationManager;
import com.bea.httppubsub.util.ConfigUtils;
import com.bea.httppubsub.util.StringUtils;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;

public class PubSubServerImpl implements RegistrablePubSubServer {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugPubSubServer");
   private static final int DEFAULT_CLIENT_IDLE_TIMEOUT = 40000;
   private static final int DEFAULT_CLIENT_TIMEOUT = 60000;
   private static final int DEFAULT_PERSISTENT_CLIENT_TIMEOUT = 600000;
   private static final int DEFAULT_INTERVAL = 500;
   private static final int DEFAULT_MULTIFRAME_INTERVAL = 3000;
   private static final String[] DEFAULT_SUPPORTED_CONNECTION_TYPES = new String[]{"long-polling", "callback-polling"};
   public static final String DEFAULT_COOKIE_PATH = "/";
   private final WeblogicPubsubBean configuration;
   private final String name;
   private final boolean allowPublishDirectly;
   private int connectionIdleTimeout = 40000;
   private int clientTimeout = 60000;
   private int persistentClientTimeout = 600000;
   private int interval = 500;
   private int multiFrameInterval = 3000;
   private String cookiePath = "/";
   private String[] supportedConnectionTypes;
   private Map messageFilterChainMapping;
   private ErrorFactory errorFactory;
   private MessageFactory messageFactory;
   private ChannelAuthorizationManager channelAuthorizationManager;
   private ChannelManager channelManager;
   private ClientManager clientManager;
   private BayeuxHandlerFactory handlerFactory;
   private ErrorSender errorSender;
   private final PubSubContext context;
   private ClientPersistenceManager clientPersistenceManager;

   public PubSubServerImpl(WeblogicPubsubBean configuration, String name) {
      this.supportedConnectionTypes = DEFAULT_SUPPORTED_CONNECTION_TYPES;
      if (configuration == null) {
         throw new IllegalArgumentException("WeblogicPubsubBean cannot be null.");
      } else if (StringUtils.isEmpty(name)) {
         throw new IllegalArgumentException("PubSub server name cannot be empty");
      } else {
         this.configuration = configuration;
         this.name = name;
         this.context = new PubSubContext(this, configuration);
         this.allowPublishDirectly = ConfigUtils.isPublishWithoutConnectAllowed(configuration);
      }
   }

   public void init() {
      if (this.channelManager == null) {
         throw new IllegalArgumentException("ChannelManager must be set.");
      } else if (this.errorFactory == null) {
         throw new IllegalArgumentException("ErrorFactory must be set.");
      } else if (this.channelAuthorizationManager == null) {
         throw new IllegalArgumentException("ChannelAuthroizationManager must be set.");
      } else {
         this.errorSender = new ErrorSenderImpl(this.errorFactory);
         this.createPreconfiguredChannels(this.configuration);
         this.initTransportSetting(this.configuration);
         this.initMessageFilterChain(this.configuration);
         ChannelPersistenceManagerBuilder cpmBuilder = this.context.getChannelPersistManagerBuilder();
         if (cpmBuilder != null && cpmBuilder.hasPersistenceChannel()) {
            try {
               this.clientPersistenceManager = new DefaultClientPersistenceManager(this);
            } catch (PubSubServerException var3) {
               var3.printStackTrace();
            }
         }

      }
   }

   public void destroy() {
      if (this.clientManager != null) {
         this.clientManager.destroy();
      }

      if (this.channelAuthorizationManager != null) {
         this.channelAuthorizationManager.destroy();
      }

      if (this.clientPersistenceManager != null) {
         this.clientPersistenceManager.destroy();
      }

      if (this.channelManager != null) {
         this.channelManager.destroy();
      }

      if (this.handlerFactory != null) {
         this.handlerFactory.destroy();
      }

      ChannelPersistenceManagerBuilder builder = this.context.getChannelPersistManagerBuilder();
      if (builder != null) {
         builder.destroyAllPersistenceManagers();
      }

   }

   public WeblogicPubsubBean getConfiguration() {
      return this.configuration;
   }

   public void setErrorFactory(ErrorFactory errorFactory) {
      this.errorFactory = errorFactory;
   }

   public void setMessageFactory(MessageFactory messageFactory) {
      this.messageFactory = messageFactory;
   }

   public ChannelAuthorizationManager getChannelAuthorizationManager() {
      return this.channelAuthorizationManager;
   }

   public void setChannelAuthorizationManager(ChannelAuthorizationManager channelAuthorizationManager) {
      this.channelAuthorizationManager = channelAuthorizationManager;
   }

   public ChannelManager getChannelManager() {
      return this.channelManager;
   }

   public void setChannelManager(ChannelManager channelManager) {
      this.channelManager = channelManager;
   }

   public void registerClientManager(ClientManager clientManager) {
      this.clientManager = clientManager;
   }

   public void registerBayeuxHandlerFactory(BayeuxHandlerFactory handlerFactory) {
      this.handlerFactory = handlerFactory;
   }

   public String getName() {
      return this.name;
   }

   public Channel findChannel(String url) {
      return this.channelManager.findChannel(url);
   }

   public Channel findOrCreateChannel(Client client, String url) throws PubSubSecurityException {
      Channel channel = this.findChannel(url);
      if (channel != null) {
         return channel;
      } else {
         ChannelManager channelManagerForUse = this.channelManager;
         synchronized(channelManagerForUse) {
            channel = this.findChannel(url);
            if (channel != null) {
               return channel;
            } else if (!this.channelAuthorizationManager.hasPermission(client, url, ChannelAuthorizationManager.Action.CREATE)) {
               String userName = client.getAuthenticatedUser() == null ? "null" : client.getAuthenticatedUser().getUserName();
               PubSubLogger.logNoPermissionCreateChannel(userName, url);
               throw new PubSubSecurityException(PubSubLogger.logNoPermissionCreateChannelLoggable(userName, url).getMessageText());
            } else {
               ChannelImpl result = (ChannelImpl)channelManagerForUse.createChannel(url);
               ChannelPersistenceManagerBuilder cpmBuilder = this.context.getChannelPersistManagerBuilder();
               if (cpmBuilder != null && cpmBuilder.hasPersistenceChannel()) {
                  result.setChannelPersistenceManager(cpmBuilder.getChannelPersistenceManager(url));
                  result.setChannelPersistManBuilder(cpmBuilder);
               }

               return result;
            }
         }
      }
   }

   public void deleteChannel(Client client, String url) throws PubSubSecurityException {
      if (!this.channelAuthorizationManager.hasPermission(client, url, ChannelAuthorizationManager.Action.DELETE)) {
         String userName = client.getAuthenticatedUser() == null ? "null" : client.getAuthenticatedUser().getUserName();
         PubSubLogger.logNoPermissionDeleteChannel(userName, url);
         throw new PubSubSecurityException(PubSubLogger.logNoPermissionCreateChannelLoggable(userName, url).getMessageText());
      } else {
         this.channelManager.deleteChannel(url, client);
      }
   }

   public boolean routeMessages(List messages, Transport transport) throws PubSubServerException, IOException {
      assert transport != null;

      assert messages != null && messages.size() > 0;

      if (logger.isDebugEnabled()) {
         Iterator var3 = messages.iterator();

         while(var3.hasNext()) {
            BayeuxMessage message = (BayeuxMessage)var3.next();
            logger.debug(">>>>> " + message.toJSONRequestString());
         }
      }

      List metaMessages = new ArrayList();
      Iterator var9 = messages.iterator();

      AbstractBayeuxMessage metaMessage;
      while(var9.hasNext()) {
         metaMessage = (AbstractBayeuxMessage)var9.next();
         if (metaMessage.isMetaMessage()) {
            metaMessages.add(metaMessage);
         }
      }

      if (metaMessages.size() > 1) {
         this.errorSender.send(transport, (List)metaMessages, 405);
         return false;
      } else if (metaMessages.size() == 1 && ((AbstractBayeuxMessage)metaMessages.get(0)).getType() == BayeuxMessage.TYPE.HANDSHAKE && messages.size() > 1) {
         this.errorSender.send(transport, (AbstractBayeuxMessage)((AbstractBayeuxMessage)metaMessages.get(0)), 410);
         return false;
      } else {
         boolean isConnectOrReconnect = false;
         metaMessage = null;
         if (metaMessages.size() == 1) {
            metaMessage = (AbstractBayeuxMessage)metaMessages.get(0);
            if (metaMessage.getType() != BayeuxMessage.TYPE.CONNECT && metaMessage.getType() != BayeuxMessage.TYPE.RECONNECT) {
               this.handleMessage(metaMessage, transport);
            } else {
               isConnectOrReconnect = true;
            }
         }

         if (metaMessage == null || metaMessage.isSuccessful() || isConnectOrReconnect) {
            Iterator var6 = messages.iterator();

            while(var6.hasNext()) {
               AbstractBayeuxMessage message = (AbstractBayeuxMessage)var6.next();
               if (!message.isMetaMessage()) {
                  this.handleMessage(message, transport);
               }
            }
         }

         if (isConnectOrReconnect) {
            this.handleMessage(metaMessage, transport);
         }

         InternalClient client;
         if (metaMessage != null) {
            client = (InternalClient)metaMessage.getClient();
         } else {
            client = (InternalClient)((AbstractBayeuxMessage)messages.get(0)).getClient();
         }

         return client.send(transport, messages);
      }
   }

   public ClientManager getClientManager() {
      return this.clientManager;
   }

   public MessageFilterChain getMessageFilterChain(String wilUrlPattern) {
      if (this.messageFilterChainMapping != null) {
         Iterator var2 = this.messageFilterChainMapping.keySet().iterator();

         while(var2.hasNext()) {
            String pattern = (String)var2.next();
            boolean match = this.matches(pattern, wilUrlPattern);
            if (match) {
               List filters = (List)this.messageFilterChainMapping.get(pattern);
               return new MessageFilterChain(filters);
            }
         }
      }

      return null;
   }

   private boolean matches(String superPattern, String patternToMatch) {
      ChannelId scid = ChannelId.newInstance(superPattern);
      ChannelId tcid = ChannelId.newInstance(patternToMatch);
      if (scid.isDoubleWild() && tcid.isWild()) {
         if (scid.depth() > tcid.depth()) {
            return false;
         } else {
            int i = scid.depth() - 1;

            do {
               --i;
               if (i < 0) {
                  return i == -1;
               }
            } while(scid.getSegment(i).equals(tcid.getSegment(i)));

            return false;
         }
      } else {
         return scid.matches(tcid);
      }
   }

   public int getConnectionIdleTimeout() {
      return this.connectionIdleTimeout;
   }

   public int getClientTimeout() {
      return this.clientTimeout;
   }

   public int getPersistentClientTimeout() {
      return this.persistentClientTimeout;
   }

   public int getReconnectInterval(boolean isMultiFrame) {
      return isMultiFrame ? this.multiFrameInterval : this.interval;
   }

   public String getCookiePath() {
      return this.cookiePath;
   }

   public boolean isMultiFrameSupported() {
      return this.multiFrameInterval != -1;
   }

   public String[] getSupportedConnectionTypes() {
      return this.supportedConnectionTypes;
   }

   public MessageFactory getMessageFactory() {
      return this.messageFactory;
   }

   public void publishToChannel(LocalClient client, String channel, String payLoad) throws PubSubSecurityException {
      MessageFactory messageFactory = this.getMessageFactory();
      EventMessage eventMessage = messageFactory.createEventMessage(client, channel, payLoad);

      try {
         this.handleMessage((EventMessageImpl)eventMessage, new NullTransport());
      } catch (PubSubServerException var7) {
         throw new RuntimeException(var7);
      }
   }

   public void subscribeToChannel(LocalClient client, String channel) throws PubSubSecurityException {
      Channel targetChannel = this.findOrCreateChannel(client, channel);
      targetChannel.subscribe(client, Channel.ChannelPattern.getPattern(channel));
   }

   public void unsubscribeToChannel(LocalClient client, String channel) {
      Channel targetChannel = this.findChannel(channel);
      targetChannel.unsubscribe(client, Channel.ChannelPattern.getPattern(channel));
   }

   public boolean isAllowPublishDirectly() {
      return this.allowPublishDirectly;
   }

   public PubSubContext getContext() {
      return this.context;
   }

   public ClientPersistenceManager getClientPersistenceManager() {
      return this.clientPersistenceManager;
   }

   private void handleMessage(AbstractBayeuxMessage message, Transport transport) throws PubSubServerException {
      BayeuxHandler handler = this.handlerFactory.getMessageHandler(message);

      try {
         handler.handle(message, transport);
      } catch (PubSubSecurityException var5) {
         this.setErrorInMessage(message, 406, var5.getMessage());
      }

   }

   private void setErrorInMessage(AbstractBayeuxMessage message, int errorCode, String errorMsg) {
      String errorMessage = errorMsg == null ? this.errorFactory.getError(errorCode) : this.errorFactory.getError(errorCode, errorMsg);
      message.setSuccessful(false);
      message.setError(errorMessage);
      message.setTimestamp(System.currentTimeMillis());
   }

   private void initTransportSetting(WeblogicPubsubBean configuration) {
      ServerConfigBean serverConfig = configuration.getServerConfig();
      SupportedTransportBean supportedTransportBean = null;
      if (serverConfig != null) {
         supportedTransportBean = serverConfig.getSupportedTransport();
         this.connectionIdleTimeout = serverConfig.getConnectionIdleTimeoutSecs() * 1000;
         if (this.connectionIdleTimeout <= 0) {
            PubSubLogger.logInvalidConnectionTimeout(this.connectionIdleTimeout);
            throw new IllegalArgumentException(PubSubLogger.logInvalidConnectionTimeoutLoggable(this.connectionIdleTimeout).getMessage());
         }

         this.clientTimeout = serverConfig.getClientTimeoutSecs() * 1000;
         if (this.clientTimeout <= 0) {
            PubSubLogger.logInvalidClientTimeout(this.clientTimeout);
            throw new IllegalArgumentException(PubSubLogger.logInvalidClientTimeoutLoggable(this.clientTimeout).getMessage());
         }

         this.interval = serverConfig.getIntervalMillisecs();
         if (this.interval < 0) {
            PubSubLogger.logInvalidInterval(this.interval);
            throw new IllegalArgumentException(PubSubLogger.logInvalidIntervalLoggable(this.interval).getMessage());
         }

         this.multiFrameInterval = serverConfig.getMultiFrameIntervalMillisecs();
         if (this.multiFrameInterval <= 0 && this.multiFrameInterval != -1) {
            PubSubLogger.logInvalidMultiFrameInterval(this.multiFrameInterval);
            throw new IllegalArgumentException(PubSubLogger.logInvalidMultiFrameIntervalLoggable(this.multiFrameInterval).getMessage());
         }

         this.persistentClientTimeout = serverConfig.getPersistentClientTimeoutSecs() * 1000;
         if (this.persistentClientTimeout <= 0) {
            PubSubLogger.logInvalidPersistentTimeout(this.persistentClientTimeout);
            throw new IllegalArgumentException(PubSubLogger.logInvalidPersistentTimeoutLoggable(this.persistentClientTimeout).getMessage());
         }

         if (this.persistentClientTimeout < this.clientTimeout) {
            PubSubLogger.logInvalidPersistentClientTimeout(this.persistentClientTimeout, this.clientTimeout);
            throw new IllegalArgumentException(PubSubLogger.logInvalidPersistentClientTimeoutLoggable(this.persistentClientTimeout, this.clientTimeout).getMessage());
         }

         this.cookiePath = serverConfig.isCookiePathSet() ? serverConfig.getCookiePath() : "/";
         if (logger.isDebugEnabled()) {
            logger.debug("Transport clientTimeout -> [" + this.clientTimeout + "]");
            logger.debug("Transport supported connection types -> [" + StringUtils.arrayToString(this.supportedConnectionTypes) + "]");
            logger.debug("Transport connect/reconnect interval when multiple frame is detected -> [" + this.multiFrameInterval + "]");
         }
      }

      if (supportedTransportBean != null) {
         this.supportedConnectionTypes = supportedTransportBean.getTypes();
         if (logger.isDebugEnabled()) {
            logger.debug("Transport supported connection types -> [" + StringUtils.arrayToString(this.supportedConnectionTypes) + "]");
         }

         String[] var4 = this.supportedConnectionTypes;
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String eachConnectionType = var4[var6];
            if (!BayeuxConstants.isValidSupportedConnectionType(eachConnectionType)) {
               PubSubLogger.logInvalidTransportType(eachConnectionType);
               throw new IllegalArgumentException(PubSubLogger.logInvalidTransportTypeLoggable(eachConnectionType).getMessage());
            }
         }
      }

   }

   private void createPreconfiguredChannels(WeblogicPubsubBean configuration) {
      ChannelBean[] channels = configuration.getChannels();
      ChannelBean[] channelsForCreate = channels == null ? new ChannelBean[0] : channels;
      if (logger.isDebugEnabled()) {
         logger.debug("Total [" + channelsForCreate.length + "] channel(s) found should be created.");
      }

      ChannelPersistenceManagerBuilder cpmBuilder = this.context.getChannelPersistManagerBuilder();
      boolean hasPersistenceChannel = false;
      if (cpmBuilder != null && cpmBuilder.hasPersistenceChannel()) {
         hasPersistenceChannel = true;
      }

      ChannelBean[] var6 = channelsForCreate;
      int var7 = channelsForCreate.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         ChannelBean bean = var6[var8];
         String url = bean.getChannelPattern();
         Channel channel = this.channelManager.findOrCreateChannel(url);
         if (hasPersistenceChannel) {
            ChannelImpl ch = (ChannelImpl)channel;
            ch.setChannelPersistenceManager(cpmBuilder.getChannelPersistenceManager(url));
            ch.setChannelPersistManBuilder(cpmBuilder);

            while(ch.getParentChannel() != null) {
               ch = (ChannelImpl)ch.getParentChannel();
               ch.setChannelPersistManBuilder(cpmBuilder);
            }
         }

         logger.debug("Channel [" + url + "] created.");
      }

   }

   private void initMessageFilterChain(WeblogicPubsubBean configuration) {
      ChannelBean[] channels = configuration.getChannels();
      if (channels != null) {
         this.messageFilterChainMapping = new LinkedHashMap();
         ChannelBean[] var3 = channels;
         int var4 = channels.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ChannelBean cmb = var3[var5];
            String channelPattern = cmb.getChannelPattern();
            String[] messageFilterNames = cmb.getMessageFilters();
            if (messageFilterNames != null) {
               List filters = new ArrayList();
               this.messageFilterChainMapping.put(channelPattern, filters);
               String[] var10 = messageFilterNames;
               int var11 = messageFilterNames.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  String name = var10[var12];
                  MessageFilter filter = this.initMessageFilter(configuration, name);
                  filters.add(filter);
               }
            }
         }

      }
   }

   private MessageFilter initMessageFilter(WeblogicPubsubBean configuration, String filterName) {
      MessageFilterBean[] filterBeans = configuration.getMessageFilters();
      if (filterBeans == null) {
         PubSubLogger.logNoMessageFilterConfigured(filterName);
         throw new RuntimeException(PubSubLogger.logNoMessageFilterConfiguredLoggable(filterName).getMessage());
      } else {
         MessageFilterBean[] var4 = filterBeans;
         int var5 = filterBeans.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            MessageFilterBean filterBean = var4[var6];
            if (filterName.equals(filterBean.getMessageFilterName())) {
               String filterClass = filterBean.getMessageFilterClass();

               try {
                  Class cls = Thread.currentThread().getContextClassLoader().loadClass(filterClass);
                  Constructor constructor = cls.getConstructor();
                  return (MessageFilter)constructor.newInstance();
               } catch (Exception var11) {
                  PubSubLogger.logInvalidMessageFilterConfiguration(filterName, filterClass, var11);
                  throw new RuntimeException(PubSubLogger.logInvalidMessageFilterConfigurationLoggable(filterName, filterClass, var11).getMessage());
               }
            }
         }

         PubSubLogger.logMismatchMessageFilterDefined(filterName);
         throw new RuntimeException(PubSubLogger.logMismatchMessageFilterDefinedLoggable(filterName).getMessage());
      }
   }
}
