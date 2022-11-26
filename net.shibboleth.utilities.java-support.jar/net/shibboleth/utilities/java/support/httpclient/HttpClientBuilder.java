package net.shibboleth.utilities.java.support.httpclient;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.Duration;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.collection.IterableSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.util.CharsetUtils;

public class HttpClientBuilder {
   private InetAddress socketLocalAddress;
   @Duration
   private int socketTimeout;
   private int socketBufferSize;
   @Duration
   private int connectionTimeout;
   @Duration
   private int connectionRequestTimeout;
   private int maxConnectionsTotal;
   private int maxConnectionsPerRoute;
   private boolean connectionDisregardTLSCertificate;
   @Nullable
   private LayeredConnectionSocketFactory tlsSocketFactory;
   private boolean connectionCloseAfterResponse;
   private boolean connectionStaleCheck;
   @Nullable
   private String connectionProxyHost;
   @Nullable
   private String userAgent;
   private int connectionProxyPort;
   @Nullable
   private String connectionProxyUsername;
   @Nullable
   private String connectionProxyPassword;
   private boolean httpFollowRedirects;
   @Nullable
   private String httpContentCharSet;
   @Nullable
   private HttpRequestRetryHandler retryHandler;
   @Nullable
   private ServiceUnavailableRetryStrategy serviceUnavailStrategy;
   private boolean disableAuthCaching;
   private boolean disableAutomaticRetries;
   private boolean disableConnectionState;
   private boolean disableContentCompression;
   private boolean disableCookieManagement;
   private boolean disableRedirectHandling;
   private boolean useSystemProperties;
   private List requestInterceptorsFirst;
   private List requestInterceptorsLast;
   private List responseInterceptorsFirst;
   private List responseInterceptorsLast;
   @Nonnull
   @NonnullElements
   private List staticContextHandlers;
   private org.apache.http.impl.client.HttpClientBuilder apacheBuilder;

   public HttpClientBuilder() {
      this(org.apache.http.impl.client.HttpClientBuilder.create());
   }

   public HttpClientBuilder(@Nonnull org.apache.http.impl.client.HttpClientBuilder builder) {
      Constraint.isNotNull(builder, "Apache HttpClientBuilder may not be null");
      this.apacheBuilder = builder;
      this.resetDefaults();
   }

   public void resetDefaults() {
      this.maxConnectionsTotal = -1;
      this.maxConnectionsPerRoute = -1;
      this.socketLocalAddress = null;
      this.socketBufferSize = 8192;
      this.socketTimeout = 60000;
      this.connectionTimeout = 60000;
      this.connectionRequestTimeout = 60000;
      this.connectionDisregardTLSCertificate = false;
      this.connectionCloseAfterResponse = true;
      this.connectionStaleCheck = false;
      this.connectionProxyHost = null;
      this.connectionProxyPort = 8080;
      this.connectionProxyUsername = null;
      this.connectionProxyPassword = null;
      this.httpFollowRedirects = true;
      this.httpContentCharSet = "UTF-8";
      this.userAgent = null;
      this.requestInterceptorsFirst = Collections.emptyList();
      this.requestInterceptorsLast = Collections.emptyList();
      this.responseInterceptorsFirst = Collections.emptyList();
      this.responseInterceptorsLast = Collections.emptyList();
      this.staticContextHandlers = Collections.emptyList();
   }

   public int getMaxConnectionsTotal() {
      return this.maxConnectionsTotal;
   }

   public void setMaxConnectionsTotal(int max) {
      this.maxConnectionsTotal = max;
   }

   public int getMaxConnectionsPerRoute() {
      return this.maxConnectionsPerRoute;
   }

   public void setMaxConnectionsPerRoute(int max) {
      this.maxConnectionsPerRoute = max;
   }

   public InetAddress getSocketLocalAddress() {
      return this.socketLocalAddress;
   }

   public void setSocketLocalAddress(InetAddress address) {
      this.socketLocalAddress = address;
   }

   public void setSocketLocalAddress(String ipOrHost) throws UnknownHostException {
      this.socketLocalAddress = InetAddress.getByName((String)Constraint.isNotNull(ipOrHost, "IP or hostname may not be null"));
   }

   @Duration
   public int getSocketTimeout() {
      return this.socketTimeout;
   }

   public void setSocketTimeout(@Duration long timeout) {
      if (timeout > 2147483647L) {
         throw new IllegalArgumentException("Timeout was too large");
      } else {
         this.socketTimeout = (int)timeout;
      }
   }

   public int getSocketBufferSize() {
      return this.socketBufferSize;
   }

   public void setSocketBufferSize(int size) {
      this.socketBufferSize = (int)Constraint.isGreaterThan(0L, (long)size, "Socket buffer size must be greater than 0");
   }

   @Duration
   public int getConnectionTimeout() {
      return this.connectionTimeout;
   }

   public void setConnectionTimeout(@Duration long timeout) {
      if (timeout > 2147483647L) {
         throw new IllegalArgumentException("Timeout was too large");
      } else {
         this.connectionTimeout = (int)timeout;
      }
   }

   @Duration
   public int getConnectionRequestTimeout() {
      return this.connectionRequestTimeout;
   }

   public void setConnectionRequestTimeout(@Duration long timeout) {
      if (timeout > 2147483647L) {
         throw new IllegalArgumentException("Timeout was too large");
      } else {
         this.connectionRequestTimeout = (int)timeout;
      }
   }

   public boolean isConnectionDisregardTLSCertificate() {
      return this.connectionDisregardTLSCertificate;
   }

   public void setConnectionDisregardTLSCertificate(boolean disregard) {
      this.connectionDisregardTLSCertificate = disregard;
   }

   @Nullable
   public LayeredConnectionSocketFactory getTLSSocketFactory() {
      return this.tlsSocketFactory;
   }

   public void setTLSSocketFactory(@Nullable LayeredConnectionSocketFactory factory) {
      this.tlsSocketFactory = factory;
   }

   public boolean isConnectionCloseAfterResponse() {
      return this.connectionCloseAfterResponse;
   }

   public void setConnectionCloseAfterResponse(boolean close) {
      this.connectionCloseAfterResponse = close;
   }

   /** @deprecated */
   public boolean isConnectionStalecheck() {
      return this.isConnectionStaleCheck();
   }

   /** @deprecated */
   public void setConnectionStalecheck(boolean check) {
      this.setConnectionStaleCheck(check);
   }

   public boolean isConnectionStaleCheck() {
      return this.connectionStaleCheck;
   }

   public void setConnectionStaleCheck(boolean check) {
      this.connectionStaleCheck = check;
   }

   @Nullable
   public String getConnectionProxyHost() {
      return this.connectionProxyHost;
   }

   public void setConnectionProxyHost(@Nullable String host) {
      this.connectionProxyHost = StringSupport.trimOrNull(host);
   }

   public int getConnectionProxyPort() {
      return this.connectionProxyPort;
   }

   public void setConnectionProxyPort(int port) {
      this.connectionProxyPort = (int)Constraint.numberInRangeExclusive(0L, 65536L, (long)port, "Proxy port must be between 0 and 65536, exclusive");
   }

   @Nullable
   public String getConnectionProxyUsername() {
      return this.connectionProxyUsername;
   }

   public void setConnectionProxyUsername(@Nullable String usename) {
      this.connectionProxyUsername = usename;
   }

   @Nullable
   public String getConnectionProxyPassword() {
      return this.connectionProxyPassword;
   }

   public void setConnectionProxyPassword(@Nullable String password) {
      this.connectionProxyPassword = password;
   }

   public boolean isHttpFollowRedirects() {
      return this.httpFollowRedirects;
   }

   public void setHttpFollowRedirects(boolean followRedirects) {
      this.httpFollowRedirects = followRedirects;
   }

   @Nullable
   public String getHttpContentCharSet() {
      return this.httpContentCharSet;
   }

   public void setHttpContentCharSet(@Nullable String charSet) {
      this.httpContentCharSet = charSet;
   }

   @Nullable
   public String getUserAgent() {
      return this.userAgent;
   }

   public void setUserAgent(@Nullable String what) {
      this.userAgent = what;
   }

   @Nullable
   public HttpRequestRetryHandler getHttpRequestRetryHandler() {
      return this.retryHandler;
   }

   public void setHttpRequestRetryHandler(@Nullable HttpRequestRetryHandler handler) {
      this.retryHandler = handler;
   }

   @Nullable
   public ServiceUnavailableRetryStrategy getServiceUnavailableRetryHandler() {
      return this.serviceUnavailStrategy;
   }

   public void setServiceUnavailableRetryHandler(@Nullable ServiceUnavailableRetryStrategy strategy) {
      this.serviceUnavailStrategy = strategy;
   }

   public boolean isDisableAuthCaching() {
      return this.disableAuthCaching;
   }

   public void setDisableAuthCaching(boolean flag) {
      this.disableAuthCaching = flag;
   }

   public boolean isDisableAutomaticRetries() {
      return this.disableAutomaticRetries;
   }

   public void setDisableAutomaticRetries(boolean flag) {
      this.disableAutomaticRetries = flag;
   }

   public boolean isDisableConnectionState() {
      return this.disableConnectionState;
   }

   public void setDisableConnectionState(boolean flag) {
      this.disableConnectionState = flag;
   }

   public boolean isDisableContentCompression() {
      return this.disableContentCompression;
   }

   public void setDisableContentCompression(boolean flag) {
      this.disableContentCompression = flag;
   }

   public boolean isDisableCookieManagement() {
      return this.disableCookieManagement;
   }

   public void setDisableCookieManagement(boolean flag) {
      this.disableCookieManagement = flag;
   }

   public boolean isDisableRedirectHandling() {
      return this.disableRedirectHandling;
   }

   public void setDisableRedirectHandling(boolean flag) {
      this.disableRedirectHandling = flag;
   }

   public boolean isUseSystemProperties() {
      return this.useSystemProperties;
   }

   public void setUseSystemProperties(boolean flag) {
      this.useSystemProperties = flag;
   }

   @Nullable
   public List getFirstRequestInterceptors() {
      return this.requestInterceptorsFirst;
   }

   public void setFirstRequestInterceptors(@Nullable List interceptors) {
      this.requestInterceptorsFirst = this.normalizeList(interceptors);
   }

   @Nullable
   public List getLastRequestInterceptors() {
      return this.requestInterceptorsLast;
   }

   public void setLastRequestInterceptors(@Nullable List interceptors) {
      this.requestInterceptorsLast = this.normalizeList(interceptors);
   }

   @Nullable
   public List getFirstResponseInterceptors() {
      return this.responseInterceptorsFirst;
   }

   public void setFirstResponseInterceptors(@Nullable List interceptors) {
      this.responseInterceptorsFirst = this.normalizeList(interceptors);
   }

   @Nullable
   public List getLastResponseInterceptors() {
      return this.responseInterceptorsLast;
   }

   public void setLastResponseInterceptors(@Nullable List interceptors) {
      this.responseInterceptorsLast = this.normalizeList(interceptors);
   }

   @Nonnull
   @NonnullElements
   @NotLive
   @Unmodifiable
   public List getStaticContextHandlers() {
      return ImmutableList.copyOf(this.staticContextHandlers);
   }

   public void setStaticContextHandlers(@Nullable List handlers) {
      this.staticContextHandlers = this.normalizeList(handlers);
   }

   @Nonnull
   @NonnullElements
   private List normalizeList(@Nullable List items) {
      return (List)(items == null ? Collections.emptyList() : new ArrayList(Collections2.filter(items, Predicates.notNull())));
   }

   public HttpClient buildClient() throws Exception {
      this.decorateApacheBuilder();
      return new ContextHandlingHttpClient(this.getApacheBuilder().build(), this.getStaticContextHandlers());
   }

   protected void decorateApacheBuilder() throws Exception {
      org.apache.http.impl.client.HttpClientBuilder builder = this.getApacheBuilder();
      if (this.getTLSSocketFactory() != null) {
         builder.setSSLSocketFactory(this.getTLSSocketFactory());
      } else if (this.connectionDisregardTLSCertificate) {
         builder.setSSLSocketFactory(HttpClientSupport.buildNoTrustTLSSocketFactory());
      } else {
         builder.setSSLSocketFactory(HttpClientSupport.buildStrictTLSSocketFactory());
      }

      if (this.connectionCloseAfterResponse && (this.getFirstRequestInterceptors() == null || !IterableSupport.containsInstance(this.getFirstRequestInterceptors(), RequestConnectionClose.class)) && (this.getLastRequestInterceptors() == null || !IterableSupport.containsInstance(this.getLastRequestInterceptors(), RequestConnectionClose.class))) {
         builder.addInterceptorLast(new RequestConnectionClose());
      }

      if (this.maxConnectionsTotal > 0) {
         builder.setMaxConnTotal(this.maxConnectionsTotal);
      }

      if (this.maxConnectionsPerRoute > 0) {
         builder.setMaxConnPerRoute(this.maxConnectionsPerRoute);
      }

      if (this.retryHandler != null) {
         builder.setRetryHandler(this.retryHandler);
      }

      if (this.serviceUnavailStrategy != null) {
         builder.setServiceUnavailableRetryStrategy(this.serviceUnavailStrategy);
      }

      if (this.isDisableAuthCaching()) {
         builder.disableAuthCaching();
      }

      if (this.isDisableAutomaticRetries()) {
         builder.disableAutomaticRetries();
      }

      if (this.isDisableConnectionState()) {
         builder.disableConnectionState();
      }

      if (this.isDisableContentCompression()) {
         builder.disableContentCompression();
      }

      if (this.isDisableCookieManagement()) {
         builder.disableCookieManagement();
      }

      if (this.isDisableRedirectHandling()) {
         builder.disableRedirectHandling();
      }

      if (this.isUseSystemProperties()) {
         builder.useSystemProperties();
      }

      Iterator i$;
      HttpRequestInterceptor interceptor;
      if (this.getFirstRequestInterceptors() != null) {
         i$ = this.getFirstRequestInterceptors().iterator();

         while(i$.hasNext()) {
            interceptor = (HttpRequestInterceptor)i$.next();
            builder.addInterceptorFirst(interceptor);
         }
      }

      if (this.getLastRequestInterceptors() != null) {
         i$ = this.getLastRequestInterceptors().iterator();

         while(i$.hasNext()) {
            interceptor = (HttpRequestInterceptor)i$.next();
            builder.addInterceptorLast(interceptor);
         }
      }

      HttpResponseInterceptor interceptor;
      if (this.getFirstResponseInterceptors() != null) {
         i$ = this.getFirstResponseInterceptors().iterator();

         while(i$.hasNext()) {
            interceptor = (HttpResponseInterceptor)i$.next();
            builder.addInterceptorFirst(interceptor);
         }
      }

      if (this.getLastResponseInterceptors() != null) {
         i$ = this.getLastResponseInterceptors().iterator();

         while(i$.hasNext()) {
            interceptor = (HttpResponseInterceptor)i$.next();
            builder.addInterceptorLast(interceptor);
         }
      }

      RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
      if (this.socketLocalAddress != null) {
         requestConfigBuilder.setLocalAddress(this.socketLocalAddress);
      }

      if (this.socketTimeout >= 0) {
         requestConfigBuilder.setSocketTimeout(this.socketTimeout);
      }

      if (this.connectionTimeout >= 0) {
         requestConfigBuilder.setConnectTimeout(this.connectionTimeout);
      }

      if (this.connectionRequestTimeout >= 0) {
         requestConfigBuilder.setConnectionRequestTimeout(this.connectionRequestTimeout);
      }

      requestConfigBuilder.setStaleConnectionCheckEnabled(this.connectionStaleCheck);
      requestConfigBuilder.setRedirectsEnabled(this.httpFollowRedirects);
      if (this.connectionProxyHost != null) {
         HttpHost proxyHost = new HttpHost(this.connectionProxyHost, this.connectionProxyPort);
         requestConfigBuilder.setProxy(proxyHost);
         if (this.connectionProxyUsername != null && this.connectionProxyPassword != null) {
            CredentialsProvider credProvider = new BasicCredentialsProvider();
            credProvider.setCredentials(new AuthScope(this.connectionProxyHost, this.connectionProxyPort), new UsernamePasswordCredentials(this.connectionProxyUsername, this.connectionProxyPassword));
            builder.setDefaultCredentialsProvider(credProvider);
         }
      }

      ConnectionConfig.Builder connectionConfigBuilder = ConnectionConfig.custom();
      connectionConfigBuilder.setBufferSize(this.socketBufferSize);
      if (this.httpContentCharSet != null) {
         connectionConfigBuilder.setCharset(CharsetUtils.get(this.httpContentCharSet));
      }

      builder.setDefaultRequestConfig(requestConfigBuilder.build());
      builder.setDefaultConnectionConfig(connectionConfigBuilder.build());
      if (null != this.userAgent) {
         builder.setUserAgent(this.userAgent);
      }

   }

   protected org.apache.http.impl.client.HttpClientBuilder getApacheBuilder() {
      return this.apacheBuilder;
   }
}
