package org.opensaml.security.httpclient.impl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import org.apache.http.HttpHost;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.protocol.HttpContext;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.criteria.UsageCriterion;
import org.opensaml.security.trust.TrustEngine;
import org.opensaml.security.x509.BasicX509Credential;
import org.opensaml.security.x509.X509Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @deprecated */
@Deprecated
public class TrustEngineTLSSocketFactory implements LayeredConnectionSocketFactory {
   private final Logger log = LoggerFactory.getLogger(TrustEngineTLSSocketFactory.class);
   @Nonnull
   private LayeredConnectionSocketFactory wrappedFactory;
   @Nullable
   private X509HostnameVerifier hostnameVerifier;

   public TrustEngineTLSSocketFactory(LayeredConnectionSocketFactory factory, X509HostnameVerifier verifier) {
      this.wrappedFactory = (LayeredConnectionSocketFactory)Constraint.isNotNull(factory, "Socket factory was null");
      this.hostnameVerifier = verifier;
   }

   public Socket createSocket(HttpContext context) throws IOException {
      this.log.trace("In createSocket");
      return this.wrappedFactory.createSocket(context);
   }

   public Socket connectSocket(int connectTimeout, Socket sock, HttpHost host, InetSocketAddress remoteAddress, InetSocketAddress localAddress, HttpContext context) throws IOException {
      this.log.trace("In connectSocket");
      Socket socket = this.wrappedFactory.connectSocket(connectTimeout, sock, host, remoteAddress, localAddress, context);
      this.performTrustEval(socket, context);
      this.performHostnameVerification(socket, host.getHostName(), context);
      return socket;
   }

   public Socket createLayeredSocket(Socket socket, String target, int port, HttpContext context) throws IOException {
      this.log.trace("In createLayeredSocket");
      Socket layeredSocket = this.wrappedFactory.createLayeredSocket(socket, target, port, context);
      this.performTrustEval(layeredSocket, context);
      this.performHostnameVerification(layeredSocket, target, context);
      return layeredSocket;
   }

   protected void performTrustEval(@Nonnull Socket socket, @Nonnull HttpContext context) throws IOException {
      if (!(socket instanceof SSLSocket)) {
         this.log.debug("Socket was not an instance of SSLSocket, skipping trust eval");
      } else {
         SSLSocket sslSocket = (SSLSocket)socket;
         this.log.debug("Attempting to evaluate server TLS credential against supplied TrustEngine and CriteriaSet");
         TrustEngine trustEngine = (TrustEngine)context.getAttribute("opensaml.TrustEngine");
         if (trustEngine == null) {
            this.log.debug("No trust engine supplied by caller, skipping trust eval");
         } else {
            this.log.trace("Saw trust engine of type: {}", trustEngine.getClass().getName());
            CriteriaSet criteriaSet = (CriteriaSet)context.getAttribute("opensaml.CriteriaSet");
            if (criteriaSet == null) {
               this.log.debug("No criteria set supplied by caller, building new criteria set with signing criteria");
               criteriaSet = new CriteriaSet(new Criterion[]{new UsageCriterion(UsageType.SIGNING)});
            } else {
               this.log.trace("Saw CriteriaSet: {}", criteriaSet);
            }

            X509Credential credential = this.extractCredential(sslSocket);

            try {
               if (trustEngine.validate(credential, criteriaSet)) {
                  this.log.debug("Credential evaluated as trusted");
                  context.setAttribute("opensaml.ServerTLSCredentialTrusted", Boolean.TRUE);
               } else {
                  this.log.debug("Credential evaluated as untrusted");
                  context.setAttribute("opensaml.ServerTLSCredentialTrusted", Boolean.FALSE);
                  throw new SSLPeerUnverifiedException("Trust engine could not establish trust of server TLS credential");
               }
            } catch (SecurityException var8) {
               this.log.error("Trust engine error evaluating credential", var8);
               throw new IOException("Trust engine error evaluating credential", var8);
            }
         }
      }
   }

   @Nonnull
   protected X509Credential extractCredential(@Nonnull SSLSocket sslSocket) throws IOException {
      SSLSession session = sslSocket.getSession();
      Certificate[] peerCertificates = session.getPeerCertificates();
      if (peerCertificates != null && peerCertificates.length >= 1) {
         ArrayList certChain = new ArrayList();
         Certificate[] var5 = peerCertificates;
         int var6 = peerCertificates.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Certificate cert = var5[var7];
            certChain.add((X509Certificate)cert);
         }

         X509Certificate entityCert = (X509Certificate)certChain.get(0);
         BasicX509Credential credential = new BasicX509Credential(entityCert);
         credential.setEntityCertificateChain(certChain);
         return credential;
      } else {
         throw new SSLPeerUnverifiedException("SSLSession peer certificates array was null or empty");
      }
   }

   protected void performHostnameVerification(Socket socket, String hostname, HttpContext context) throws IOException {
      if (this.hostnameVerifier != null && socket instanceof SSLSocket) {
         this.hostnameVerifier.verify(hostname, (SSLSocket)socket);
      }

   }
}
