package weblogic.security.SSL.jsseadapter;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLKeyException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLEngineResult.Status;
import weblogic.security.SSL.WeblogicSSLEngine;
import weblogic.security.utils.SSLHostnameVerifier;
import weblogic.security.utils.SSLTrustValidator;
import weblogic.security.utils.SSLTruster;

final class JaSSLEngine extends WeblogicSSLEngine {
   private final SSLEngine engine;
   private final JaSSLContext jaSSLContext;
   private volatile SSLSocket associatedSSLSocket;
   private final int HANDSHAKECOMPLETEDLISTENERS_INITIALCAPACITY = 16;
   private final Vector handshakeCompletedListeners = new Vector(16);

   public String getPeerHost() {
      return this.engine.getPeerHost();
   }

   public int getPeerPort() {
      return this.engine.getPeerPort();
   }

   public SSLEngineResult wrap(final ByteBuffer src, final ByteBuffer dst) throws SSLException {
      return this.doAction(new SSLEngineResultSSLExceptionAction() {
         public SSLEngineResult run() throws SSLException {
            return JaSSLEngine.this.engine.wrap(src, dst);
         }
      }, "SSLEngine.wrap(ByteBuffer,ByteBuffer)");
   }

   public SSLEngineResult wrap(final ByteBuffer[] srcs, final ByteBuffer dst) throws SSLException {
      return this.doAction(new SSLEngineResultSSLExceptionAction() {
         public SSLEngineResult run() throws SSLException {
            return JaSSLEngine.this.engine.wrap(srcs, dst);
         }
      }, "SSLEngine.wrap(ByteBuffer[],ByteBuffer)");
   }

   public SSLEngineResult wrap(final ByteBuffer[] srcs, final int offset, final int length, final ByteBuffer dst) throws SSLException {
      return this.doAction(new SSLEngineResultSSLExceptionAction() {
         public SSLEngineResult run() throws SSLException {
            return JaSSLEngine.this.engine.wrap(srcs, offset, length, dst);
         }
      }, "SSLEngine.wrap(ByteBuffer[],int,int,ByteBuffer)");
   }

   public SSLEngineResult unwrap(final ByteBuffer src, final ByteBuffer dst) throws SSLException {
      return this.doAction(new SSLEngineResultSSLExceptionAction() {
         public SSLEngineResult run() throws SSLException {
            return JaSSLEngine.this.engine.unwrap(src, dst);
         }
      }, "SSLEngine.unwrap(ByteBuffer,ByteBuffer)");
   }

   public SSLEngineResult unwrap(final ByteBuffer src, final ByteBuffer[] dsts) throws SSLException {
      return this.doAction(new SSLEngineResultSSLExceptionAction() {
         public SSLEngineResult run() throws SSLException {
            return JaSSLEngine.this.engine.unwrap(src, dsts);
         }
      }, "SSLEngine.unwrap(ByteBuffer,ByteBuffer[])");
   }

   public SSLEngineResult unwrap(final ByteBuffer src, final ByteBuffer[] dsts, final int offset, final int length) throws SSLException {
      return this.doAction(new SSLEngineResultSSLExceptionAction() {
         public SSLEngineResult run() throws SSLException {
            return JaSSLEngine.this.engine.unwrap(src, dsts, offset, length);
         }
      }, "SSLEngine.unwrap(ByteBuffer,ByteBuffer[],int,int)");
   }

   public Runnable getDelegatedTask() {
      return this.engine.getDelegatedTask();
   }

   public void closeInbound() throws SSLException {
      this.doAction(new SSLEngineResultSSLExceptionAction() {
         public SSLEngineResult run() throws SSLException {
            JaSSLEngine.this.engine.closeInbound();
            return null;
         }
      }, "SSLEngine.closeInbound()");
   }

   public boolean isInboundDone() {
      return this.engine.isInboundDone();
   }

   public void closeOutbound() {
      this.doAction(new SetValueAction() {
         public void run() {
            JaSSLEngine.this.engine.closeOutbound();
         }

         public String getSetValue() {
            return "closed";
         }
      }, "SSLEngine.closeOutbound()");
   }

   public boolean isOutboundDone() {
      return this.engine.isOutboundDone();
   }

   public String[] getSupportedCipherSuites() {
      return JaCipherSuiteNameMap.fromJsse(this.engine.getSupportedCipherSuites());
   }

   public String[] getEnabledCipherSuites() {
      return JaCipherSuiteNameMap.fromJsse(this.engine.getEnabledCipherSuites());
   }

   public void setEnabledCipherSuites(final String[] strings) {
      this.doAction(new SetValueAction() {
         public void run() {
            JaSSLEngine.this.engine.setEnabledCipherSuites(JaCipherSuiteNameMap.toJsse(strings));
         }

         public String getSetValue() {
            return JaSSLEngine.toString(strings);
         }
      }, "SSLEngine.setEnabledCipherSuites(String[])");
   }

   public String[] getSupportedProtocols() {
      return this.engine.getSupportedProtocols();
   }

   public String[] getEnabledProtocols() {
      return this.engine.getEnabledProtocols();
   }

   public void setEnabledProtocols(final String[] strings) {
      this.doAction(new SetValueAction() {
         public void run() {
            JaSSLEngine.this.engine.setEnabledProtocols(strings);
         }

         public String getSetValue() {
            return JaSSLEngine.toString(strings);
         }
      }, "SSLEngine.setEnabledProtocols(String[])");
   }

   public SSLSession getSession() {
      return this.engine.getSession();
   }

   public void beginHandshake() throws SSLException {
      this.doAction(new SSLEngineResultSSLExceptionAction() {
         public SSLEngineResult run() throws SSLException {
            JaSSLEngine.this.engine.beginHandshake();
            return null;
         }
      }, "SSLEngine.beginHandshake()");
   }

   public SSLEngineResult.HandshakeStatus getHandshakeStatus() {
      return this.engine.getHandshakeStatus();
   }

   public void setUseClientMode(final boolean b) {
      this.doAction(new SetValueAction() {
         public void run() {
            JaSSLEngine.this.engine.setUseClientMode(b);
         }

         public String getSetValue() {
            return Boolean.toString(b);
         }
      }, "SSLEngine.setUseClientMode(boolean)");
   }

   public boolean getUseClientMode() {
      return this.engine.getUseClientMode();
   }

   public void setNeedClientAuth(final boolean b) {
      this.doAction(new SetValueAction() {
         public void run() {
            JaSSLEngine.this.engine.setNeedClientAuth(b);
         }

         public String getSetValue() {
            return Boolean.toString(b);
         }
      }, "SSLEngine.setNeedClientAuth(boolean)");
   }

   public boolean getNeedClientAuth() {
      return this.engine.getNeedClientAuth();
   }

   public void setWantClientAuth(final boolean b) {
      this.doAction(new SetValueAction() {
         public void run() {
            JaSSLEngine.this.engine.setWantClientAuth(b);
         }

         public String getSetValue() {
            return Boolean.toString(b);
         }
      }, "SSLEngine.setWantClientAuth(boolean)");
   }

   public boolean getWantClientAuth() {
      return this.engine.getWantClientAuth();
   }

   public void setEnableSessionCreation(final boolean b) {
      this.doAction(new SetValueAction() {
         public void run() {
            JaSSLEngine.this.engine.setEnableSessionCreation(b);
         }

         public String getSetValue() {
            return Boolean.toString(b);
         }
      }, "SSLEngine.setEnableSessionCreation(boolean)");
   }

   public boolean getEnableSessionCreation() {
      return this.engine.getEnableSessionCreation();
   }

   public void setAssociatedSSLSocket(SSLSocket sslSocket) {
      this.associatedSSLSocket = sslSocket;
   }

   public SSLSocket getAssociatedSSLSocket() {
      return this.associatedSSLSocket;
   }

   public void addHandshakeCompletedListener(HandshakeCompletedListener listener) {
      if (null == listener) {
         throw new IllegalArgumentException("Non-null HandshakeCompletedListener expected.");
      } else {
         boolean added = false;
         synchronized(this.handshakeCompletedListeners) {
            if (!this.handshakeCompletedListeners.contains(listener)) {
               this.handshakeCompletedListeners.add(listener);
               added = true;
            }
         }

         if (added) {
            if (JaLogger.isLoggable(Level.FINEST)) {
               JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "Added HandshakeCompletedListener: class={0}, instance={1}.", listener.getClass().getName(), listener);
            }
         } else if (JaLogger.isLoggable(Level.FINEST)) {
            JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "HandshakeCompletedListener previously added: class={0}, instance={1}.", listener.getClass().getName(), listener);
         }

         if (null == this.getAssociatedSSLSocket() && JaLogger.isLoggable(Level.FINER)) {
            JaLogger.log(Level.FINER, JaLogger.Component.SSLENGINE, "No associated SSLSocket when adding HandshakeCompletedListener: class={0}, instance={1}. An associated SSLSocket is required.", listener.getClass().getName(), listener);
         }

      }
   }

   public void removeHandshakeCompletedListener(HandshakeCompletedListener listener) {
      if (null == listener) {
         throw new IllegalArgumentException("Non-null HandshakeCompletedListener expected.");
      } else if (!this.handshakeCompletedListeners.remove(listener)) {
         String msg = MessageFormat.format("Attempting to remove unregistered HandshakeCompletedListener: class={0}, instance={1}.", listener.getClass().getName(), listener);
         if (JaLogger.isLoggable(Level.FINE)) {
            JaLogger.log(Level.FINE, JaLogger.Component.SSLENGINE, msg);
         }

         throw new IllegalArgumentException(msg);
      } else {
         if (JaLogger.isLoggable(Level.FINEST)) {
            JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "Removed HandshakeCompletedListener: class={0}, instance={1}.", listener.getClass().getName(), listener);
         }

      }
   }

   public String getALPNProtocol() {
      return ALPNRuntimeSupport.getApplicationProtocol(this.engine);
   }

   JaSSLEngine(JaSSLContext jaSSLContext, SSLEngine engine) {
      if (null == jaSSLContext) {
         throw new IllegalArgumentException("Expected non-null JaSSLContext.");
      } else if (null == engine) {
         throw new IllegalArgumentException("Expected non-null SSLEngine.");
      } else {
         this.engine = engine;
         this.jaSSLContext = jaSSLContext;
      }
   }

   final SSLEngine getDelegate() {
      return this.engine;
   }

   static void validateErrToException(int validateErr) throws IOException {
      if (0 == validateErr) {
         if (JaLogger.isLoggable(Level.FINEST)) {
            JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "No trust failure, validateErr={0}.", validateErr);
         }

      } else {
         StringBuilder sb = new StringBuilder(128);
         sb.append("Trust failure (");
         sb.append(validateErr);
         sb.append("): ");
         if ((1 & validateErr) != 0) {
            sb.append(" CERT_CHAIN_INVALID");
         }

         if ((2 & validateErr) != 0) {
            sb.append(" CERT_EXPIRED");
         }

         if ((4 & validateErr) != 0) {
            sb.append(" CERT_CHAIN_INCOMPLETE");
         }

         if ((8 & validateErr) != 0) {
            sb.append(" SIGNATURE_INVALID");
         }

         if ((16 & validateErr) != 0) {
            sb.append(" CERT_CHAIN_UNTRUSTED");
         }

         if ((32 & validateErr) != 0) {
            sb.append(" VALIDATION_FAILED");
         }

         String msg = sb.toString();
         if (JaLogger.isLoggable(Level.FINE)) {
            JaLogger.log(Level.FINE, JaLogger.Component.SSLENGINE, msg);
         }

         throw new SSLKeyException(msg);
      }
   }

   void doPostHandshake() throws IOException {
      SSLSocket sslSocket = this.getAssociatedSSLSocket();
      if (null == sslSocket) {
         if (JaLogger.isLoggable(Level.FINE)) {
            JaLogger.log(Level.FINE, JaLogger.Component.SSLENGINE, "No associated SSLSocket for WeblogicSSLEngine (class={0}, instance={1}), unable to perform post-handshake processing.", this.getClass().getName(), this);
         }

      } else {
         SSLSession session = this.getSession();
         SSLTruster truster = this.jaSSLContext.getTrustManager();
         String urlHost;
         if (truster != null) {
            X509Certificate[] chain = null;

            try {
               chain = (X509Certificate[])((X509Certificate[])session.getPeerCertificates());
            } catch (SSLPeerUnverifiedException var10) {
               if (JaLogger.isLoggable(Level.FINER)) {
                  JaLogger.log(Level.FINER, JaLogger.Component.SSLENGINE, "Trying to get peer certificates from SSLSession, SSLPeerUnverifiedException: {0}.", var10.getMessage());
               }
            }

            int validateErr = false;
            X509Certificate[] trustedCAs = this.jaSSLContext.getTrustedCAs();
            String negotiatedCS = session.getCipherSuite();
            if (JaLogger.isLoggable(Level.FINEST)) {
               JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "negotiatedCipherSuite: {0}", negotiatedCS);
               JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "SSLEngine.getNeedClientAuth(): {0}", this.getNeedClientAuth());
               JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "Peer certificate chain: {0}", chain);
               if (truster instanceof SSLTrustValidator) {
                  JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "weblogic.security.utils.SSLTrustValidator.isPeerCertsRequired(): {0}", ((SSLTrustValidator)truster).isPeerCertsRequired());
               }
            }

            if (negotiatedCS != null) {
               urlHost = negotiatedCS.toUpperCase();
               if (!urlHost.contains("_ANON_") && !urlHost.contains("NULL_WITH_NULL_NULL")) {
                  validateErrToException(truster.validationCallback(chain, 0, sslSocket, trustedCAs));
               }
            }
         }

         if (this.getUseClientMode()) {
            SSLHostnameVerifier hostnameVerifier = this.jaSSLContext.getHostnameVerifier();
            if (hostnameVerifier != null) {
               InetAddress socketAddress = sslSocket.getInetAddress();
               String hostname;
               if (null == socketAddress) {
                  hostname = "";
               } else {
                  hostname = socketAddress.getHostName();
               }

               if (!hostnameVerifier.hostnameValidationCallback(hostname, sslSocket)) {
                  SSLSession sslSession = sslSocket.getSession();
                  urlHost = null;
                  if (sslSession != null) {
                     urlHost = (String)sslSession.getValue("wls_hostname_verifier_url_host");
                  }

                  String msg = MessageFormat.format("Hostname verification failed: HostnameVerifier={0}, hostname={1}.", hostnameVerifier.getClass().getName(), urlHost != null ? urlHost : hostname);
                  if (urlHost != null) {
                     sslSession.removeValue("wls_hostname_verifier_url_host");
                  }

                  throw new SSLKeyException(msg);
               }
            }
         }

         HandshakeCompletedEvent event = new HandshakeCompletedEvent(sslSocket, session);
         Iterator var14 = this.handshakeCompletedListeners.iterator();

         while(var14.hasNext()) {
            HandshakeCompletedListener listener = (HandshakeCompletedListener)var14.next();
            listener.handshakeCompleted(event);
         }

      }
   }

   SSLEngineResult doAction(SSLEngineResultSSLExceptionAction action, String actionName) throws SSLException {
      if (null == action) {
         throw new IllegalArgumentException("Expected non-null SSLEngineResultSSLExceptionAction object.");
      } else if (null == actionName) {
         throw new IllegalArgumentException("Expected non-null actionName object.");
      } else {
         try {
            SSLEngineResult engineResult = action.run();
            if (JaLogger.isLoggable(Level.FINEST)) {
               JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "{0} called: result={1}.", actionName, engineResult);
            }

            if (null == engineResult) {
               return engineResult;
            } else {
               SSLEngineResult.Status status = engineResult.getStatus();
               SSLEngineResult.HandshakeStatus hStatus = engineResult.getHandshakeStatus();
               if (Status.OK == status && HandshakeStatus.FINISHED == hStatus) {
                  this.doPostHandshake();
                  if (JaLogger.isLoggable(Level.FINEST)) {
                     JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "Successfully completed post-handshake processing.");
                  }
               }

               return engineResult;
            }
         } catch (Exception var6) {
            if (JaLogger.isLoggable(Level.FINE)) {
               JaLogger.log(Level.FINE, JaLogger.Component.SSLENGINE, var6, "Exception occurred during {0}.", actionName);
            }

            if (var6 instanceof RuntimeException) {
               throw (RuntimeException)var6;
            } else if (var6 instanceof SSLException) {
               throw (SSLException)var6;
            } else {
               throw new SSLException("Occurred during " + actionName + ".", var6);
            }
         }
      }
   }

   private void doAction(SetValueAction action, String actionName) {
      if (null == action) {
         throw new IllegalArgumentException("Expected non-null SetValueAction object.");
      } else if (null == actionName) {
         throw new IllegalArgumentException("Expected non-null actionName object.");
      } else {
         try {
            action.run();
            if (JaLogger.isLoggable(Level.FINEST)) {
               JaLogger.log(Level.FINEST, JaLogger.Component.SSLENGINE, "{0}: value={1}.", actionName, action.getSetValue());
            }

         } catch (RuntimeException var4) {
            if (JaLogger.isLoggable(Level.FINE)) {
               JaLogger.log(Level.FINE, JaLogger.Component.SSLENGINE, var4, "Exception occurred during {0}: value={1}.", actionName, action.getSetValue());
            }

            throw var4;
         }
      }
   }

   static String toString(String[] input) {
      if (null == input) {
         return "<null>";
      } else {
         StringBuilder output = new StringBuilder(256);
         String[] var2 = input;
         int var3 = input.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String elem = var2[var4];
            if (null != elem) {
               if (output.length() > 0) {
                  output.append(",");
               }

               output.append(elem);
            }
         }

         return output.toString();
      }
   }

   private interface SetValueAction {
      void run();

      String getSetValue();
   }

   interface SSLEngineResultSSLExceptionAction {
      SSLEngineResult run() throws SSLException;
   }
}
