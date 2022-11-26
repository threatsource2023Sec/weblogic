package com.bea.security.saml2.artifact.impl;

import com.bea.common.security.utils.CSSPlatformProxy;
import com.bea.security.saml2.binding.BindingHandlerException;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import com.bea.security.saml2.providers.registry.IndexedEndpoint;
import com.bea.security.saml2.providers.registry.WebSSOPartner;
import com.bea.security.saml2.util.SAML2Utils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class ArtifactResolverWLSImpl extends AbstractArtifactResolver {
   public ArtifactResolverWLSImpl(SAML2ConfigSpi config) {
      super(config);
   }

   public HttpURLConnection openConnection(WebSSOPartner remotepart, IndexedEndpoint remoteEp) throws BindingHandlerException {
      if (this.logdebug) {
         this.log.debug("open connection to send samlp:ArtifactResolve. partner id:" + remotepart.getEntityID() + ", endpoint url:" + remoteEp.getLocation());
      }

      HttpURLConnection conn = null;

      try {
         URL url = new URL(remoteEp.getLocation());
         String protocol = url.getProtocol().toLowerCase();
         if (protocol.startsWith("https")) {
            if (this.logdebug) {
               this.log.debug("remote ARS need secure http connection.");
            }

            conn = CSSPlatformProxy.getInstance().getHttpsURLConnection(url);
            if (this.sslClientKey != null && this.sslClientCert != null && this.sslClientCert.length > 0) {
               if (this.logdebug) {
                  this.log.debug("have certs and key, loading SSL identity.");
               }

               CSSPlatformProxy.getInstance().loadLocalIdentity(conn, this.sslClientCert, this.sslClientKey);
            }
         } else {
            conn = CSSPlatformProxy.getInstance().getHttpURLConnection(url);
         }

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

         this.log.debug("connect to remote ARS.");
         conn.connect();
         return conn;
      } catch (UnsupportedEncodingException var8) {
         if (this.logdebug) {
            this.log.debug("can't get BASE64 encoded basic authentication:UnsupportedEncoding:UTF-8.");
         }

         throw new BindingHandlerException(var8.getMessage(), 500);
      } catch (MalformedURLException var9) {
         if (this.logdebug) {
            this.log.debug("can't open connection:MalformedURL:" + remoteEp.getLocation());
         }

         throw new BindingHandlerException(var9.getMessage(), 500);
      } catch (SocketTimeoutException var10) {
         if (this.logdebug) {
            this.log.debug("can't connect to remote server.", var10);
         }

         throw new BindingHandlerException(var10.getMessage(), 500);
      } catch (IOException var11) {
         if (this.logdebug) {
            this.log.debug("can't open connection.");
         }

         throw new BindingHandlerException(var11.getMessage(), 500);
      }
   }
}
