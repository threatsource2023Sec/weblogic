package com.bea.security.saml2.artifact.impl;

import com.bea.security.saml2.binding.BindingHandlerException;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import com.bea.security.saml2.providers.registry.IndexedEndpoint;
import com.bea.security.saml2.providers.registry.WebSSOPartner;
import com.bea.security.saml2.util.SAML2Utils;
import com.bea.security.utils.ssl.SSLContextProtocolSelector;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

public class ArtifactResolverJSSEImpl extends AbstractArtifactResolver {
   private static final String SSLCONTEXT_PROTOCOL = SSLContextProtocolSelector.getSSLContextProtocol();

   public ArtifactResolverJSSEImpl(SAML2ConfigSpi config) {
      super(config);
   }

   public HttpURLConnection openConnection(WebSSOPartner remotepart, IndexedEndpoint remoteEp) throws BindingHandlerException {
      if (this.logdebug) {
         this.log.debug("open connection to send samlp:ArtifactResolve. partner id:" + remotepart.getEntityID() + ", endpoint url:" + remoteEp.getLocation());
      }

      HttpURLConnection conn = null;

      try {
         conn = (HttpURLConnection)(new URL(remoteEp.getLocation())).openConnection();
         conn.setDoOutput(true);
         conn.setAllowUserInteraction(false);
         conn.setInstanceFollowRedirects(false);
         conn.setRequestMethod("POST");
         conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
         String basicAuthn = this.getBasicAuthn(remotepart);
         if (basicAuthn != null && !basicAuthn.equals("")) {
            String base64BasicAuthn = SAML2Utils.base64Encode(basicAuthn.getBytes("UTF-8"));
            conn.setRequestProperty("Authorization", "Basic " + base64BasicAuthn);
         }
      } catch (UnsupportedEncodingException var7) {
         if (this.logdebug) {
            this.log.debug("can't get BASE64 encoded basic authentication:UnsupportedEncoding:UTF-8.");
         }

         throw new BindingHandlerException(var7.getMessage(), 500);
      } catch (MalformedURLException var8) {
         if (this.logdebug) {
            this.log.debug("can't open connection:MalformedURL:" + remoteEp.getLocation());
         }

         throw new BindingHandlerException(var8.getMessage(), 500);
      } catch (IOException var9) {
         if (this.logdebug) {
            this.log.debug("can't open connection.");
         }

         throw new BindingHandlerException(var9.getMessage(), 500);
      }

      if (conn instanceof HttpsURLConnection) {
         if (this.logdebug) {
            this.log.debug("remote ARS need secure http connection.");
         }

         try {
            SSLClientKeyManager keymanager = null;
            if (this.sslClientKey != null && this.sslClientCert != null && this.sslClientCert.length > 0) {
               keymanager = new SSLClientKeyManager(this.sslClientKey, this.sslClientCert, this.sslClientKeyAlias);
            }

            KeyManager[] kmlist = keymanager == null ? null : new KeyManager[]{keymanager};
            if (this.logdebug) {
               this.log.debug("Expected SSLContext service protocol: " + SSLCONTEXT_PROTOCOL);
            }

            SSLContext context = SSLContext.getInstance(SSLCONTEXT_PROTOCOL);
            if (this.logdebug && null != context) {
               this.log.debug("Actual SSLContext service protocol: " + context.getProtocol());
            }

            context.init(kmlist, (TrustManager[])null, (SecureRandom)null);
            ((HttpsURLConnection)conn).setSSLSocketFactory(context.getSocketFactory());
            conn.connect();
         } catch (NoSuchAlgorithmException var10) {
            if (this.logdebug) {
               this.log.debug("can't get ssl context: NoSuchAlgorithm: " + SSLCONTEXT_PROTOCOL + ".");
            }

            throw new BindingHandlerException(var10.getMessage(), 500);
         } catch (KeyManagementException var11) {
            if (this.logdebug) {
               this.log.debug("can't initialize ssl context.", var11);
            }

            throw new BindingHandlerException(var11.getMessage(), 500);
         } catch (SocketTimeoutException var12) {
            if (this.logdebug) {
               this.log.debug("can't connect to remote server.", var12);
            }

            throw new BindingHandlerException(var12.getMessage(), 500);
         } catch (IOException var13) {
            if (this.logdebug) {
               this.log.debug("can't connect to remote server.", var13);
            }

            throw new BindingHandlerException(var13.getMessage(), 500);
         }
      }

      return conn;
   }
}
