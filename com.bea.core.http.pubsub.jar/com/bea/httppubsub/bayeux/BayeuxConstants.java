package com.bea.httppubsub.bayeux;

import java.util.Arrays;
import java.util.List;

public final class BayeuxConstants {
   public static final String KEY_CLIENT = "client";
   public static final String KEY_MESSAGE = "message";
   public static final String META = "/meta/";
   public static final String META_CONNECT = "/meta/connect".intern();
   public static final String META_DISCONNECT = "/meta/disconnect".intern();
   public static final String META_HANDSHAKE = "/meta/handshake".intern();
   public static final String META_PING = "/meta/ping".intern();
   public static final String META_RECONNECT = "/meta/reconnect".intern();
   public static final String META_STATUS = "/meta/status".intern();
   public static final String META_SUBSCRIBE = "/meta/subscribe".intern();
   public static final String META_UNSUBSCRIBE = "/meta/unsubscribe".intern();
   public static final String SERVICE = "/service/";
   public static final String FIELD_CHANNEL = "channel";
   public static final String FIELD_VERSION = "version";
   public static final String FIELD_MINIMUM_VERSION = "minimumVersion";
   public static final String FIELD_SUPPORTED_CONNECTION_TYPES = "supportedConnectionTypes";
   public static final String FIELD_AUTH_SCHEMA = "authScheme";
   public static final String FIELD_AUTH_USER = "authUser";
   public static final String FIELD_AUTH_TOKEN = "authToken";
   public static final String FIELD_CLIENT_ID = "clientId";
   public static final String FIELD_AUTH_SUCCESSFUL = "authSuccessful";
   public static final String FIELD_ADVICE = "advice";
   public static final String FIELD_RECONNECT = "reconnect";
   public static final String FIELD_TRANSPORT = "transport";
   public static final String FIELD_IFRAME = "iframe";
   public static final String FIELD_FLASH = "flash";
   public static final String FIELD_HTTP_POLLING = "http-polling";
   public static final String FIELD_INTERVAL = "interval";
   public static final String FIELD_CONNECTION_TYPE = "connectionType";
   public static final String FIELD_SUCCESSFUL = "successful";
   public static final String FIELD_ERROR = "error";
   public static final String FIELD_CONNECTION_ID = "connectionId";
   public static final String FIELD_TIMESTAMP = "timestamp";
   public static final String FIELD_DATA = "data";
   public static final String FIELD_ID = "id";
   public static final String FIELD_SUBSCRIPTION = "subscription";
   public static final String FIELD_RETRY = "retry";
   public static final String FIELD_EXT = "ext";
   public static final String FIELD_MULTIPLE_CLIENTS = "multiple-clients";
   public static final String FIELD_JSON_COMMENT_FILTERED = "json-comment-filtered";
   public static final String SUPPORTED_CONNECTION_TYPE_LONG_POLLING = "long-polling";
   public static final String SUPPORTED_CONNECTION_TYPE_CALLBACK_POLLING = "callback-polling";
   public static final String SUPPORTED_CONNECTION_TYPE_IFRAME = "iframe";
   public static final String SUPPORTED_CONNECTION_TYPE_FLASH = "flash";
   public static final String DOUBLE_WILD = "/**";
   public static final String SINGLE_WILD = "/*";
   public static final String BAYEUX_HTTP_ID = "Bayeux_HTTP_ID";
   public static final String CLIENTS_FROM_SAME_BROWSER = "Clients_From_Same_Browser";
   public static final String CLIENT_IN_HTTP_SESSION = "Client_In_Http_Session";
   private static final List validSupportedConnectionTypes = Arrays.asList("long-polling", "callback-polling", "iframe", "flash");

   private BayeuxConstants() {
   }

   public static boolean isValidSupportedConnectionType(String connectionType) {
      return validSupportedConnectionTypes.contains(connectionType);
   }
}
