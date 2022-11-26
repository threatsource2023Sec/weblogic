package org.opensaml.security.httpclient;

import java.util.Collections;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.net.ssl.SSLPeerUnverifiedException;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.criteria.UsageCriterion;
import org.opensaml.security.x509.TrustedNamesCriterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HttpClientSecuritySupport {
   private static final Logger LOG = LoggerFactory.getLogger(HttpClientSecuritySupport.class);

   private HttpClientSecuritySupport() {
   }

   public static void addDefaultTLSTrustEngineCriteria(@Nonnull HttpClientContext context, @Nonnull HttpUriRequest request) {
      if ("https".equalsIgnoreCase(request.getURI().getScheme()) && context.getAttribute("opensaml.TrustEngine") != null) {
         CriteriaSet criteria = (CriteriaSet)context.getAttribute("opensaml.CriteriaSet");
         if (criteria == null) {
            criteria = new CriteriaSet();
            context.setAttribute("opensaml.CriteriaSet", criteria);
         }

         if (!criteria.contains(UsageCriterion.class)) {
            criteria.add(new UsageCriterion(UsageType.SIGNING));
         }

         if (!criteria.contains(TrustedNamesCriterion.class)) {
            criteria.add(new TrustedNamesCriterion(Collections.singleton(request.getURI().getHost())));
         }
      }

   }

   public static void checkTLSCredentialEvaluated(@Nonnull HttpClientContext context, @Nonnull String scheme) throws SSLPeerUnverifiedException {
      if (context.getAttribute("opensaml.TrustEngine") != null && "https".equalsIgnoreCase(scheme) && context.getAttribute("opensaml.ServerTLSCredentialTrusted") == null) {
         LOG.warn("Configured TLS trust engine was not used to verify server TLS credential, the appropriate socket factory was likely not configured");
         throw new SSLPeerUnverifiedException("Evaluation of server TLS credential with configured TrustEngine was not performed");
      }
   }

   public static void marshalSecurityParameters(@Nonnull HttpClientContext context, @Nullable HttpClientSecurityParameters securityParameters) {
      marshalSecurityParameters(context, securityParameters, false);
   }

   public static void marshalSecurityParameters(@Nonnull HttpClientContext context, @Nullable HttpClientSecurityParameters securityParameters, boolean replace) {
      if (securityParameters != null) {
         Constraint.isNotNull(context, "HttpClientContext was null");
         if (securityParameters.getCredentialsProvider() != null && (replace || context.getCredentialsProvider() == null)) {
            context.setCredentialsProvider(securityParameters.getCredentialsProvider());
         }

         setContextValue(context, "opensaml.TrustEngine", securityParameters.getTLSTrustEngine(), replace);
         setContextValue(context, "opensaml.CriteriaSet", securityParameters.getTLSCriteriaSet(), replace);
         setContextValue(context, "javasupport.TLSProtocols", securityParameters.getTLSProtocols(), replace);
         setContextValue(context, "javasupport.TLSCipherSuites", securityParameters.getTLSCipherSuites(), replace);
         setContextValue(context, "javasupport.HostnameVerifier", securityParameters.getHostnameVerifier(), replace);
         setContextValue(context, "opensaml.ClientTLSCredential", securityParameters.getClientTLSCredential(), replace);
      }
   }

   public static void setContextValue(@Nonnull HttpClientContext context, @Nonnull String attributeName, @Nullable Object attributeValue, boolean replace) {
      if (attributeValue != null) {
         Constraint.isNotNull(context, "HttpClientContext was null");
         Constraint.isNotNull(attributeName, "Context attribute name was null");
         if (replace || context.getAttribute(attributeName) == null) {
            context.setAttribute(attributeName, attributeValue);
         }

      }
   }
}
