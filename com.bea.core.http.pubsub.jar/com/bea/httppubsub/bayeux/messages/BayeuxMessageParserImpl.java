package com.bea.httppubsub.bayeux.messages;

import com.bea.httppubsub.BayeuxMessage;
import com.bea.httppubsub.PubSubLogger;
import com.bea.httppubsub.bayeux.BayeuxConstants;
import com.bea.httppubsub.json.JSONArray;
import com.bea.httppubsub.json.JSONException;
import com.bea.httppubsub.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import weblogic.diagnostics.debug.DebugLogger;

public class BayeuxMessageParserImpl implements BayeuxMessageParser {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugPubSubBayeux");
   private static final ChannelUrlValidator channelUrlValidator = initializeChannelUrlValidator();

   private static ChannelUrlValidator initializeChannelUrlValidator() {
      String className = "com.bea.httppubsub.internal.ChannelUrlValidatorImpl";

      try {
         ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
         Class clz = classLoader.loadClass(className);
         return (ChannelUrlValidator)clz.newInstance();
      } catch (Exception var3) {
         throw new RuntimeException("Cannot create ChannelUrlValidator [" + className + "].", var3);
      }
   }

   public List parse(String message) throws BayeuxParseException {
      JSONArray array;
      try {
         array = new JSONArray(message);
      } catch (JSONException var10) {
         try {
            JSONObject json = new JSONObject(message);
            array = new JSONArray();
            array.put((Object)json);
         } catch (JSONException var9) {
            if (logger.isDebugEnabled()) {
               logger.debug("Invalid JSON format of bayeux message, message: " + message, var9);
            }

            PubSubLogger.logJsonMessageParseError();
            throw new BayeuxParseException(var9.getMessage());
         }
      }

      try {
         List messages = new ArrayList(array.length());

         for(int i = 0; i < array.length(); ++i) {
            JSONObject json = (JSONObject)array.get(i);
            String channel = json.getString("channel");
            if (channel == null) {
               if (logger.isDebugEnabled()) {
                  logger.debug("Incoming bayeux message doesn't contain 'channel' field, message: " + message);
               }

               PubSubLogger.logNoChannelInBayeuxMessage();
               throw new BayeuxParseException(PubSubLogger.logNoChannelInBayeuxMessageLoggable().getMessage());
            }

            try {
               channelUrlValidator.validate(channel);
            } catch (InvalidChannelUrlFoundException var8) {
               PubSubLogger.logInvalidChannelFoundInBayeuxMessage(channel, var8.getMessage());
               throw new BayeuxParseException(PubSubLogger.logInvalidChannelFoundInBayeuxMessageLoggable(channel, var8.getMessage()).getMessageText());
            }

            if (channel.startsWith("/meta/")) {
               messages.add((AbstractBayeuxMessage)this.createMetaMessage(channel.intern(), json));
            } else {
               messages.add((AbstractBayeuxMessage)this.createEventMessage(channel, json));
            }
         }

         return messages;
      } catch (JSONException var11) {
         if (logger.isDebugEnabled()) {
            logger.debug("Invalid JSON format of bayeux message, message: " + message, var11);
         }

         PubSubLogger.logJsonMessageParseError();
         throw new BayeuxParseException(var11.getMessage());
      }
   }

   private BayeuxMessage createEventMessage(String channel, JSONObject json) {
      EventMessageImpl message = channel.startsWith("/service/") ? new ServiceMessageImpl() : new EventMessageImpl();
      String data = json.getString("data");
      ((EventMessageImpl)message).setChannel(channel);
      ((EventMessageImpl)message).setPayLoad(data);
      if (json.has("clientId")) {
         String clientId = json.getString("clientId");
         if (clientId != null) {
            ((EventMessageImpl)message).setClientId(clientId);
         }
      }

      if (json.has("id")) {
         ((EventMessageImpl)message).setId(json.getString("id"));
      }

      return (BayeuxMessage)message;
   }

   private BayeuxMessage createMetaMessage(String channel, JSONObject json) throws BayeuxParseException {
      if (BayeuxConstants.META_HANDSHAKE == channel) {
         return this.createHandshakeMessage(json);
      } else if (BayeuxConstants.META_CONNECT == channel) {
         return this.createConnectMessage(json);
      } else if (BayeuxConstants.META_RECONNECT == channel) {
         return this.createReconnectMessage(json);
      } else if (BayeuxConstants.META_DISCONNECT == channel) {
         return this.createDisconnectMessage(json);
      } else if (BayeuxConstants.META_SUBSCRIBE == channel) {
         return this.createSubscribeMessage(json);
      } else if (BayeuxConstants.META_UNSUBSCRIBE == channel) {
         return this.createUnsubscribeMessage(json);
      } else {
         PubSubLogger.logUnknownMetaChannelFromBayeuxMessage(channel);
         throw new BayeuxParseException(PubSubLogger.logUnknownMetaChannelFromBayeuxMessageLoggable(channel).getMessage());
      }
   }

   private BayeuxMessage createHandshakeMessage(JSONObject json) {
      String version = json.getString("version");
      HandshakeMessage message = new HandshakeMessage();
      message.setVersion(version);
      if (json.has("supportedConnectionTypes")) {
         JSONArray types = json.getJSONArray("supportedConnectionTypes");
         if (types != null) {
            String[] connTypes = new String[types.length()];

            for(int i = 0; i < types.length(); ++i) {
               connTypes[i] = types.getString(i);
            }

            message.setSupportedConnectionTypes(connTypes);
         }
      }

      if (json.has("minimumVersion")) {
         message.setMinimumVersion(json.getString("minimumVersion"));
      }

      if (json.has("ext")) {
         JSONObject extJson = json.getJSONObject("ext");
         if (extJson != null && extJson.has("json-comment-filtered")) {
            Ext ext = new Ext();
            ext.setJsonCommentFiltered(extJson.getBoolean("json-comment-filtered"));
            message.setExt(ext);
         }
      }

      return message;
   }

   private BayeuxMessage createConnectMessage(JSONObject json) {
      String clientId = json.getString("clientId");
      String connectionType = json.getString("connectionType");
      ConnectMessage message = new ConnectMessage();
      message.setClientId(clientId);
      message.setConnectionType(connectionType);
      return message;
   }

   private BayeuxMessage createReconnectMessage(JSONObject json) {
      String clientId = json.getString("clientId");
      ReconnectMessage message = new ReconnectMessage();
      message.setClientId(clientId);
      if (json.has("connectionType")) {
         message.setConnectionType(json.getString("connectionType"));
      }

      return message;
   }

   private BayeuxMessage createDisconnectMessage(JSONObject json) {
      String clientId = json.getString("clientId");
      DisconnectMessage message = new DisconnectMessage();
      message.setClientId(clientId);
      return message;
   }

   private BayeuxMessage createSubscribeMessage(JSONObject json) {
      String clientId = json.getString("clientId");
      String subscriptrion = json.getString("subscription");

      try {
         channelUrlValidator.validate(subscriptrion);
      } catch (InvalidChannelUrlFoundException var5) {
         PubSubLogger.logInvalidChannelFoundInBayeuxMessage(subscriptrion, var5.getMessage());
         throw new BayeuxParseException(PubSubLogger.logInvalidChannelFoundInBayeuxMessageLoggable(subscriptrion, var5.getMessage()).getMessageText());
      }

      SubscribeMessage message = new SubscribeMessage();
      message.setClientId(clientId);
      message.setSubscription(subscriptrion);
      return message;
   }

   private BayeuxMessage createUnsubscribeMessage(JSONObject json) {
      String clientId = json.getString("clientId");
      String subscriptrion = json.getString("subscription");

      try {
         channelUrlValidator.validate(subscriptrion);
      } catch (InvalidChannelUrlFoundException var5) {
         PubSubLogger.logInvalidChannelFoundInBayeuxMessage(subscriptrion, var5.getMessage());
         throw new BayeuxParseException(PubSubLogger.logInvalidChannelFoundInBayeuxMessageLoggable(subscriptrion, var5.getMessage()).getMessageText());
      }

      UnsubscribeMessage message = new UnsubscribeMessage();
      message.setClientId(clientId);
      message.setSubscription(subscriptrion);
      return message;
   }
}
