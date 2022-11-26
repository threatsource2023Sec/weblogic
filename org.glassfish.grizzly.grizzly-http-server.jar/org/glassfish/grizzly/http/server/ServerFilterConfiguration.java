package org.glassfish.grizzly.http.server;

import java.nio.charset.Charset;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.utils.JdkVersion;

public class ServerFilterConfiguration {
   public static final int MAX_REQUEST_PARAMETERS = 10000;
   public static final String USE_SEND_FILE = "org.glassfish.grizzly.http.USE_SEND_FILE";
   private String httpServerName;
   private String httpServerVersion;
   private boolean sendFileEnabled;
   private boolean traceEnabled;
   private boolean passTraceRequest;
   private int maxRequestParameters;
   private long maxPostSize;
   private int maxFormPostSize;
   private int maxBufferedPostSize;
   private int sessionTimeoutSeconds;
   private Charset defaultQueryEncoding;
   private ErrorPageGenerator defaultErrorPageGenerator;
   private BackendConfiguration backendConfiguration;
   private SessionManager sessionManager;
   private boolean isGracefulShutdownSupported;

   public ServerFilterConfiguration() {
      this("Grizzly", Grizzly.getDotedVersion());
   }

   public ServerFilterConfiguration(String serverName, String serverVersion) {
      this.maxRequestParameters = 10000;
      this.maxPostSize = -1L;
      this.maxFormPostSize = 2097152;
      this.maxBufferedPostSize = 2097152;
      this.sessionTimeoutSeconds = -1;
      this.isGracefulShutdownSupported = true;
      this.httpServerName = serverName;
      this.httpServerVersion = serverVersion;
      this.configureSendFileSupport();
      this.defaultErrorPageGenerator = new DefaultErrorPageGenerator();
   }

   public ServerFilterConfiguration(ServerFilterConfiguration configuration) {
      this.maxRequestParameters = 10000;
      this.maxPostSize = -1L;
      this.maxFormPostSize = 2097152;
      this.maxBufferedPostSize = 2097152;
      this.sessionTimeoutSeconds = -1;
      this.isGracefulShutdownSupported = true;
      this.httpServerName = configuration.httpServerName;
      this.httpServerVersion = configuration.httpServerVersion;
      this.sendFileEnabled = configuration.sendFileEnabled;
      this.backendConfiguration = configuration.backendConfiguration;
      this.traceEnabled = configuration.traceEnabled;
      this.passTraceRequest = configuration.passTraceRequest;
      this.maxRequestParameters = configuration.maxRequestParameters;
      this.maxFormPostSize = configuration.maxFormPostSize;
      this.maxBufferedPostSize = configuration.maxBufferedPostSize;
      this.defaultQueryEncoding = configuration.defaultQueryEncoding;
      this.defaultErrorPageGenerator = configuration.defaultErrorPageGenerator;
      this.isGracefulShutdownSupported = configuration.isGracefulShutdownSupported;
      this.maxPostSize = configuration.maxPostSize;
      this.sessionTimeoutSeconds = configuration.sessionTimeoutSeconds;
      this.sessionManager = configuration.sessionManager;
   }

   public String getHttpServerName() {
      return this.httpServerName;
   }

   public void setHttpServerName(String httpServerName) {
      this.httpServerName = httpServerName;
   }

   public String getHttpServerVersion() {
      return this.httpServerVersion;
   }

   public void setHttpServerVersion(String httpServerVersion) {
      this.httpServerVersion = httpServerVersion;
   }

   public boolean isSendFileEnabled() {
      return this.sendFileEnabled;
   }

   public void setSendFileEnabled(boolean sendFileEnabled) {
      this.sendFileEnabled = sendFileEnabled;
   }

   public String getScheme() {
      BackendConfiguration config = this.backendConfiguration;
      return config != null ? config.getScheme() : null;
   }

   public void setScheme(String scheme) {
      BackendConfiguration config = this.backendConfiguration;
      if (config == null) {
         config = new BackendConfiguration();
      }

      config.setScheme(scheme);
      this.backendConfiguration = config;
   }

   public BackendConfiguration getBackendConfiguration() {
      return this.backendConfiguration;
   }

   public void setBackendConfiguration(BackendConfiguration backendConfiguration) {
      this.backendConfiguration = backendConfiguration;
   }

   public boolean isPassTraceRequest() {
      return this.passTraceRequest;
   }

   public void setPassTraceRequest(boolean passTraceRequest) {
      this.passTraceRequest = passTraceRequest;
   }

   public boolean isTraceEnabled() {
      return this.traceEnabled;
   }

   public void setTraceEnabled(boolean enabled) {
      this.traceEnabled = enabled;
   }

   public int getMaxRequestParameters() {
      return this.maxRequestParameters;
   }

   public void setMaxRequestParameters(int maxRequestParameters) {
      if (maxRequestParameters < 0) {
         this.maxRequestParameters = -1;
      } else {
         this.maxRequestParameters = maxRequestParameters;
      }

   }

   /** @deprecated */
   public boolean isReuseSessionID() {
      return false;
   }

   /** @deprecated */
   public void setReuseSessionID(boolean isReuseSessionID) {
   }

   public long getMaxPostSize() {
      return this.maxPostSize;
   }

   public void setMaxPostSize(long maxPostSize) {
      this.maxPostSize = maxPostSize < 0L ? -1L : maxPostSize;
   }

   public int getMaxFormPostSize() {
      return this.maxFormPostSize;
   }

   public void setMaxFormPostSize(int maxFormPostSize) {
      this.maxFormPostSize = maxFormPostSize < 0 ? -1 : maxFormPostSize;
   }

   public int getMaxBufferedPostSize() {
      return this.maxBufferedPostSize;
   }

   public void setMaxBufferedPostSize(int maxBufferedPostSize) {
      this.maxBufferedPostSize = maxBufferedPostSize < 0 ? -1 : maxBufferedPostSize;
   }

   public Charset getDefaultQueryEncoding() {
      return this.defaultQueryEncoding;
   }

   public void setDefaultQueryEncoding(Charset defaultQueryEncoding) {
      this.defaultQueryEncoding = defaultQueryEncoding;
   }

   public ErrorPageGenerator getDefaultErrorPageGenerator() {
      return this.defaultErrorPageGenerator;
   }

   public void setDefaultErrorPageGenerator(ErrorPageGenerator defaultErrorPageGenerator) {
      this.defaultErrorPageGenerator = defaultErrorPageGenerator;
   }

   public boolean isGracefulShutdownSupported() {
      return this.isGracefulShutdownSupported;
   }

   public void setGracefulShutdownSupported(boolean isGracefulShutdownSupported) {
      this.isGracefulShutdownSupported = isGracefulShutdownSupported;
   }

   public int getSessionTimeoutSeconds() {
      return this.sessionTimeoutSeconds;
   }

   public void setSessionTimeoutSeconds(int sessionTimeoutSeconds) {
      this.sessionTimeoutSeconds = sessionTimeoutSeconds;
   }

   public SessionManager getSessionManager() {
      return this.sessionManager;
   }

   public void setSessionManager(SessionManager sessionManager) {
      this.sessionManager = sessionManager;
   }

   private void configureSendFileSupport() {
      if (System.getProperty("os.name").equalsIgnoreCase("linux") && !linuxSendFileSupported() || System.getProperty("os.name").equalsIgnoreCase("HP-UX")) {
         this.sendFileEnabled = false;
      }

      if (System.getProperty("org.glassfish.grizzly.http.USE_SEND_FILE") != null) {
         this.sendFileEnabled = Boolean.valueOf(System.getProperty("org.glassfish.grizzly.http.USE_SEND_FILE"));
      }

   }

   private static boolean linuxSendFileSupported() {
      JdkVersion jdkVersion = JdkVersion.getJdkVersion();
      JdkVersion minimumVersion = JdkVersion.parseVersion("1.6.0_18");
      return minimumVersion.compareTo(jdkVersion) <= 0;
   }
}
