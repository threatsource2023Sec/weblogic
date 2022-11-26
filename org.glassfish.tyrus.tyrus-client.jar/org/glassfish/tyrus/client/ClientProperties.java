package org.glassfish.tyrus.client;

public final class ClientProperties {
   public static final String HANDSHAKE_TIMEOUT = "org.glassfish.tyrus.client.ClientManager.ContainerTimeout";
   public static final String RECONNECT_HANDLER = "org.glassfish.tyrus.client.ClientManager.ReconnectHandler";
   public static final String PROXY_URI = "org.glassfish.tyrus.client.proxy";
   public static final String PROXY_HEADERS = "org.glassfish.tyrus.client.proxy.headers";
   public static final String SSL_ENGINE_CONFIGURATOR = "org.glassfish.tyrus.client.sslEngineConfigurator";
   public static final String INCOMING_BUFFER_SIZE = "org.glassfish.tyrus.incomingBufferSize";
   public static final String SHARED_CONTAINER = "org.glassfish.tyrus.client.sharedContainer";
   public static final String SHARED_CONTAINER_IDLE_TIMEOUT = "org.glassfish.tyrus.client.sharedContainerIdleTimeout";
   public static final String WORKER_THREAD_POOL_CONFIG = "org.glassfish.tyrus.client.workerThreadPoolConfig";
   public static final String AUTH_CONFIG = "org.glassfish.tyrus.client.http.auth.AuthConfig";
   public static final String CREDENTIALS = "org.glassfish.tyrus.client.http.auth.Credentials";
   public static final String REDIRECT_ENABLED = "org.glassfish.tyrus.client.http.redirect";
   public static final String REDIRECT_THRESHOLD = "org.glassfish.tyrus.client.http.redirect.threshold";
   public static final String RETRY_AFTER_SERVICE_UNAVAILABLE = "org.glassfish.tyrus.client.http.retryAfter";
   public static final String LOG_HTTP_UPGRADE = "org.glassfish.tyrus.client.http.logUpgrade";
   public static final String MASKING_KEY_GENERATOR = "org.glassfish.tyrus.client.maskingKeyGenerator";
}
