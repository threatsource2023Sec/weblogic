package com.bea.common.security.saml.service;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.legacy.spi.SAMLSingleSignOnServiceConfigInfoSpi;
import com.bea.common.security.saml.manager.SAMLAPConfigManager;
import com.bea.common.security.saml.manager.SAMLKeyManager;
import com.bea.common.security.saml.manager.SAMLTrustManager;
import com.bea.common.security.saml.registry.SAMLAssertingPartyConfig;
import com.bea.common.security.saml.utils.SAMLContextHandler;
import com.bea.common.security.saml.utils.SAMLSourceId;
import com.bea.common.security.saml.utils.SAMLUtil;
import com.bea.common.security.saml.utils.ServletIdentityHelper;
import com.bea.common.security.saml.utils.ServletIdentityHelperImpl;
import com.bea.common.security.service.Identity;
import com.bea.common.security.service.IdentityAssertionService;
import com.bea.common.security.service.SAMLKeyService;
import com.bea.common.security.service.SessionService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.opensaml.SAMLAssertion;
import org.opensaml.SAMLException;
import org.opensaml.SAMLRequest;
import org.opensaml.SAMLResponse;
import org.opensaml.SAMLSOAPBinding;
import weblogic.security.providers.saml.SAMLUsedAssertionCache;
import weblogic.security.providers.utils.XSSSanitizer;
import weblogic.security.service.ContextElement;
import weblogic.utils.StringUtils;

public class SAMLDestinationSiteHelper {
   private static boolean QUERY_PARAMS_WITH_TARGET = Boolean.getBoolean("weblogic.security.saml.filter.enableQueryParamsWithTarget");
   private static final String DEFAULT_ASSERTION_CACHE = "com.bea.common.security.saml.utils.SAMLUsedAssertionCacheMemImpl";
   private SAMLSingleSignOnServiceConfigInfoSpi ssoServiceConfig;
   private Set consumerURIs = null;
   private SAMLKeyManager keyManager = null;
   private SAMLTrustManager trustManager = null;
   private SAMLAPConfigManager partnerManager = null;
   private SAMLUsedAssertionCache assertionCache = null;
   private IdentityAssertionService identityAsserter = null;
   private SessionService sessionService = null;
   private LoggerSpi log = null;
   private ServletIdentityHelper idHelper;

   private final void logDebug(String msg) {
      if (this.log.isDebugEnabled()) {
         this.log.debug("SAMLDestinationSiteHelper: " + msg);
      }

   }

   private final void handleError(String msg) throws ServletException {
      String errmsg = "SAMLDestinationSiteHelper: " + msg;
      if (this.log.isDebugEnabled()) {
         this.log.debug(errmsg);
      }

      throw new ServletException(msg);
   }

   public SAMLDestinationSiteHelper(SAMLSingleSignOnServiceConfigInfoSpi ssoServiceConfig, IdentityAssertionService identityAsserter, SessionService sessionService, LoggerSpi log, SAMLKeyService samlKeyService) throws Exception {
      this.ssoServiceConfig = ssoServiceConfig;
      this.identityAsserter = identityAsserter;
      this.sessionService = sessionService;
      this.log = log;
      this.idHelper = (ServletIdentityHelper)Delegator.getProxy(ServletIdentityHelper.class, new ServletIdentityHelperImpl());
      this.keyManager = SAMLKeyManager.getManager(samlKeyService);
      this.trustManager = SAMLTrustManager.getManager();
      this.partnerManager = SAMLAPConfigManager.getManager();
      String assertionCacheClassName = ssoServiceConfig.getUsedAssertionCacheClassName();
      Properties assertionCacheProperties = ssoServiceConfig.getUsedAssertionCacheProperties();
      if (assertionCacheClassName == null || assertionCacheClassName.equals("")) {
         assertionCacheClassName = "com.bea.common.security.saml.utils.SAMLUsedAssertionCacheMemImpl";
         assertionCacheProperties = null;
      }

      this.assertionCache = (SAMLUsedAssertionCache)SAMLUtil.instantiatePlugin(assertionCacheClassName, SAMLUsedAssertionCache.class.getName());
      this.assertionCache.initCache(assertionCacheProperties);
      String[] acsURIs = ssoServiceConfig.getAssertionConsumerURIs();
      this.consumerURIs = new HashSet();
      this.logDebug("init(): building consumer URI map, there are " + (acsURIs != null && acsURIs.length != 0 ? Integer.toString(acsURIs.length) : "no") + " URIs");

      for(int i = 0; acsURIs != null && i < acsURIs.length; ++i) {
         this.logDebug("init(): found ACS URI '" + acsURIs[i] + "'");
         if (acsURIs[i] != null && acsURIs[i].length() > 0) {
            this.consumerURIs.add(acsURIs[i]);
         }
      }

      String alias = ssoServiceConfig.getSSLClientIdentityAlias();
      String passphrase = ssoServiceConfig.getSSLClientIdentityPassPhrase();
      if (alias != null && !alias.equals("")) {
         this.logDebug("init(): Setting SSLClientKey: " + alias);
         if (passphrase == null) {
            passphrase = "";
         }

         this.keyManager.setSSLClientKeyAliasInfo(alias, passphrase);
      }

   }

   public boolean isConsumerURI(String uri) {
      boolean found = this.consumerURIs.contains(uri);
      this.logDebug("isConsumerURI(): URI '" + uri + "' is " + (found ? "a" : "not a") + " consumer URI");
      return found;
   }

   public boolean doSourceSiteRedirect(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      if (this.ssoServiceConfig.isDestinationSiteEnabled()) {
         Map redirectMap = this.partnerManager.getRedirectMap();
         if (redirectMap != null) {
            String redirectURI = req.getRequestURI();
            String itsURL = (String)redirectMap.get(redirectURI);
            if (itsURL != null) {
               this.logDebug("doSourceSiteRedirect(): Processing source site redirect, redirect URI is '" + redirectURI + "'");
               this.logDebug("doSourceSiteRedirect(): Processing source site redirect, ITS URL is '" + itsURL + "'");
               String queryString = req.getQueryString();
               this.logDebug("doSourceSiteRedirect(): Request query string is: '" + queryString + "'");
               if (queryString != null) {
                  queryString = (!QUERY_PARAMS_WITH_TARGET ? "&" : "?") + SAMLUtil.queryStringStripParam(queryString, "TARGET");
                  this.logDebug("doSourceSiteRedirect(): Prepped query string is: '" + queryString + "'");
               } else {
                  queryString = "";
               }

               String encodedTarget = URLEncoder.encode(req.getRequestURL() + (!QUERY_PARAMS_WITH_TARGET ? "" : queryString), "UTF-8");
               String targetParam = "TARGET=" + encodedTarget;
               this.logDebug("doSourceSiteRedirect(): TARGET param is '" + targetParam + "'");
               String redirectURL = itsURL + targetParam + (!QUERY_PARAMS_WITH_TARGET ? queryString : "");
               this.logDebug("doSourceSiteRedirect(): Assembled redirect URL: '" + redirectURL + "'");
               String url = SAMLUtil.ENABLE_URL_REWRITING ? resp.encodeRedirectURL(redirectURL) : redirectURL;
               if (url == null || url.length() == 0) {
                  this.handleError("doSourceSiteRedirect(): null or zero length redirect URL");
               }

               this.logDebug("doSourceSiteRedirect(): Redirect URL: '" + url + "'");
               String validURL = XSSSanitizer.getValidInput(url);
               this.logDebug("doSourceSiteRedirect(): Redirect Valid URL: '" + validURL + "'");
               resp.sendRedirect(validURL);
               return true;
            }

            this.logDebug("doSourceSiteRedirect(): no redirect configured for URI '" + redirectURI + "'");
         } else {
            this.logDebug("doSourceSiteRedirect(): redirect map is null");
         }
      } else {
         this.logDebug("doSourceSiteRedirect(): destination site not enabled");
      }

      return false;
   }

   public boolean doLogin(SAMLAssertingPartyConfig partner, String assertionXML, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String targetURL = req.getParameter("TARGET");
      if (targetURL != null && targetURL.length() != 0) {
         SAMLContextHandler ctx = new SAMLContextHandler();
         if (partner != null) {
            ctx.addElement(new ContextElement("com.bea.contextelement.saml.PartnerId", partner.getPartnerId()));
         }

         ctx.addElement(new ContextElement("com.bea.contextelement.saml.TargetResource", targetURL));
         Identity subject = null;

         try {
            this.logDebug("doLogin: Calling identity asserter");
            subject = this.identityAsserter.assertIdentity("SAML.Assertion", assertionXML, ctx);
            this.logDebug("doLogin: Logged in subject: " + subject.toString());
         } catch (LoginException var9) {
            this.logDebug("doLogin: LoginException while asserting identity, returning SC_FORBIDDEN: " + var9.toString());
            resp.sendError(403);
            return false;
         }

         if (subject != null && !subject.isAnonymous()) {
            this.logDebug("doLogin: calling runAs()");
            if (!this.idHelper.runAs(subject.getSubject(), req)) {
               this.logDebug("doLogin runAs failed, try session service");
               this.sessionService.setIdentity(req.getSession(), subject);
            } else {
               this.logDebug("doLogin runAs success, good to go");
            }

            return true;
         } else {
            this.logDebug("doLogin: subject is null or anonymous, returning SC_FORBIDDEN");
            resp.sendError(403);
            return false;
         }
      } else {
         this.logDebug("doLogin: No TARGET, sending SC_INTERNAL_SERVER_ERROR");
         resp.sendError(500);
         return false;
      }
   }

   public void doTargetRedirect(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String targetURL = req.getParameter("TARGET");
      if (targetURL != null && targetURL.length() != 0) {
         String[] allowedTargetHosts = this.ssoServiceConfig.getAllowedTargetHosts();
         if (allowedTargetHosts != null && allowedTargetHosts.length > 0 && (targetURL.startsWith("http://") || targetURL.startsWith("https://")) && !SAMLUtil.isTargetRedirectHostAllowed(targetURL, allowedTargetHosts)) {
            resp.sendError(500);
            this.log.error("The host in " + targetURL + " is not allowed for target redirection.  Only these host(s) are trusted and allowed: [" + StringUtils.join(allowedTargetHosts, ",") + "]");
         } else {
            this.logDebug("doTargetRedirect: TARGET URL is '" + targetURL + "'");
            String queryString = null;
            Map params = req.getParameterMap();
            if (params != null && params.size() > 0) {
               Set entries = params.entrySet();
               Iterator it = entries.iterator();

               while(it.hasNext()) {
                  Map.Entry entry = (Map.Entry)it.next();
                  String key = URLDecoder.decode((String)entry.getKey(), "UTF-8");
                  if (key != null && key.length() > 0 && !key.equals("TARGET") && !key.equals("SAMLart") && !key.equals("SAMLResponse") && !key.equals("APID")) {
                     String value = URLDecoder.decode(((String[])((String[])entry.getValue()))[0], "UTF-8");
                     String param = URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8");
                     queryString = (queryString == null ? "" : queryString + "&") + param;
                  }
               }
            }

            this.logDebug("doTargetRedirect: Query string is '" + queryString + "'");
            String redirectURL;
            if (queryString == null) {
               redirectURL = targetURL;
            } else {
               redirectURL = targetURL + (targetURL.indexOf(63) != -1 ? "&" : "?") + queryString;
            }

            String url = SAMLUtil.ENABLE_URL_REWRITING ? resp.encodeRedirectURL(redirectURL) : redirectURL;
            if (url != null && url.length() > 0) {
               this.logDebug("doTargetRedirect: Redirecting to TARGET, URL is '" + url + "'");
               String validURL = XSSSanitizer.getValidInput(url);
               this.logDebug("doTargetRedirect: Redirecting to TARGET, Valid URL is '" + url + "'");
               resp.sendRedirect(validURL);
            } else {
               this.logDebug("doTargetRedirect: Empty redirect URL, returning BAD_REQUEST");
               resp.sendError(400);
            }

         }
      } else {
         this.logDebug("doLogin: No TARGET, sending SC_INTERNAL_SERVER_ERROR");
         resp.sendError(500);
      }
   }

   public String getAssertion(SAMLAssertingPartyConfig partner, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String targetURL = req.getParameter("TARGET");
      if (targetURL != null && targetURL.length() != 0) {
         String assertion = null;
         String artifact;
         if (req.getMethod().compareToIgnoreCase("POST") == 0) {
            if (!this.ssoServiceConfig.isACSPostEnabled()) {
               this.logDebug("POST request while POST profile not enabled -- returning SC_FORBIDDEN");
               resp.sendError(403);
               return null;
            }

            artifact = req.getParameter("SAMLResponse");
            if (artifact == null || artifact.length() == 0) {
               this.logDebug("Missing SAMLResponse parameter -- returning SC_BAD_REQUEST");
               resp.sendError(400);
               return null;
            }

            String requestURL = req.getRequestURL().toString();
            SAMLAssertion samlAssertion = this.validateResponse(partner, artifact, this.ssoServiceConfig.isPOSTRecipientCheckEnabled() ? requestURL : null);
            if (samlAssertion == null) {
               this.logDebug("Unable to validate response -- returning SC_FORBIDDEN");
               resp.sendError(403);
               return null;
            }

            if (this.ssoServiceConfig.isPOSTOneUseCheckEnabled()) {
               if (!this.assertionCache.cacheAssertion(samlAssertion.getId(), samlAssertion.getIssuer(), samlAssertion.getNotOnOrAfter().getTime())) {
                  this.logDebug("Assertion (id: " + samlAssertion.getId() + ", issuer: " + samlAssertion.getIssuer() + ") already in cache -- returning SC_FORBIDDEN");
                  resp.sendError(403);
                  return null;
               }

               this.logDebug("Cached used assertion (id: " + samlAssertion.getId() + ", issuer: " + samlAssertion.getIssuer() + ")");
            }

            assertion = samlAssertion.toString();
         } else {
            label74: {
               if (req.getMethod().compareToIgnoreCase("GET") == 0) {
                  if (!this.ssoServiceConfig.isACSArtifactEnabled()) {
                     this.logDebug("GET request while Artifact profile not enabled -- returning SC_FORBIDDEN");
                     resp.sendError(403);
                     return null;
                  }

                  artifact = req.getParameter("SAMLart");
                  if (artifact != null && artifact.length() != 0) {
                     assertion = this.dereferenceArtifact(partner, artifact);
                     if (assertion == null) {
                        this.logDebug("Unable to dereference artifact -- returning SC_FORBIDDEN");
                        resp.sendError(403);
                        return null;
                     }
                     break label74;
                  }

                  this.logDebug("Missing SAMLart parameter -- returning SC_BAD_REQUEST");
                  resp.sendError(400);
                  return null;
               }

               this.logDebug("Got request with method '" + req.getMethod() + "' -- returning SC_FORBIDDEN");
               resp.sendError(403);
               return null;
            }
         }

         if (assertion == null) {
            this.handleError("Processing error -- assertion unexpectedly null");
         }

         return assertion;
      } else {
         this.logDebug("Missing TARGET parameter -- returning SC_BAD_REQUEST");
         resp.sendError(400);
         return null;
      }
   }

   private SAMLAssertion validateResponse(SAMLAssertingPartyConfig partner, String responseString, String requestURL) throws ServletException {
      SAMLResponse response = null;

      try {
         byte[] decodedResponse = SAMLUtil.base64Decode(responseString);
         if (this.log.isDebugEnabled()) {
            this.logDebug("Decoded response is: '" + new String(decodedResponse) + "'");
         }

         response = new SAMLResponse(new ByteArrayInputStream(decodedResponse));
      } catch (SAMLException var14) {
         this.logDebug("Could not parse SAML response: " + var14.toString());
         return null;
      } catch (IOException var15) {
         this.logDebug("Could not decode SAML response: " + var15.toString());
         return null;
      }

      if (response == null) {
         this.handleError("Unexpected error while parsing SAMLResponse");
      }

      try {
         response.checkValidity();
         this.logDebug("Response passed basic validity check");
      } catch (Exception var13) {
         this.logDebug("Response validity check failed with exception: " + var13.toString());
         return null;
      }

      if (!response.isSigned()) {
         this.logDebug("Invalid response -- not signed");
         return null;
      } else {
         this.logDebug("Verifying response signature");
         X509Certificate signCert = null;
         if (partner != null) {
            String alias = partner.getProtocolSigningCertAlias();
            if (alias == null || alias.trim().length() == 0) {
               this.logDebug("No signing cert alias configured for partner '" + partner.getPartnerId() + "', failing response validation");
               return null;
            }

            signCert = this.trustManager.getCertificate(alias);
            if (signCert == null) {
               this.logDebug("Signing cert for partner '" + partner.getPartnerId() + "' not found, failing response validation");
               return null;
            }
         }

         try {
            if (signCert != null) {
               response.verify(signCert);
            } else {
               response.verify();
            }

            this.logDebug("Signature verification SUCCESS");
         } catch (SAMLException var12) {
            this.logDebug("Signature verification failed with exception: " + var12.toString());
            return null;
         }

         X509Certificate keyinfoCert = SAMLUtil.getEndCertFromSignedObject(this.log, response);
         if (keyinfoCert == null) {
            this.logDebug("Invalid response -- no keyinfo found");
            return null;
         } else {
            this.logDebug("Got keyinfo cert from response: " + keyinfoCert.getSubjectDN().getName());
            if (signCert != null && !signCert.equals(keyinfoCert)) {
               this.logDebug("Keyinfo certificate does not match configured signing certificate, rejecting response");
               return null;
            } else if (signCert == null && !this.trustManager.isCertificateTrusted(keyinfoCert)) {
               this.logDebug("Signing certificate is not trusted, rejecting response");
               return null;
            } else {
               this.logDebug("Signing certificate is trusted");
               String recipient = response.getRecipient();
               if (recipient != null && recipient.length() != 0) {
                  if (requestURL != null) {
                     if (!recipient.equals(requestURL)) {
                        this.logDebug("Invalid response -- recipient does not match request URL");
                        return null;
                     }

                     this.logDebug("Response recipient matches request URL");
                  }

                  SAMLAssertion assertion = null;
                  Iterator assertions = response.getAssertions();

                  while(assertions.hasNext()) {
                     SAMLAssertion current = (SAMLAssertion)assertions.next();
                     String confirmationMethod = SAMLUtil.getConfirmationMethod(this.log, current);
                     if (confirmationMethod != null && confirmationMethod.equals("urn:oasis:names:tc:SAML:1.0:cm:bearer")) {
                        assertion = current;
                        this.logDebug("Found assertion with 'bearer' AuthenticationStatement");
                        break;
                     }
                  }

                  return assertion;
               } else {
                  this.logDebug("Invalid response -- no recipient attribute");
                  return null;
               }
            }
         }
      }
   }

   private String dereferenceArtifact(SAMLAssertingPartyConfig partner, String artifact) {
      SAMLKeyManager km = this.keyManager;
      PrivateKey sslKey = null;
      Certificate[] sslCertChain = null;
      if (km != null) {
         SAMLKeyManager.KeyInfo keyInfo = km.getSSLClientIdentityKeyInfo();
         if (keyInfo != null) {
            sslKey = keyInfo.getKey();
            sslCertChain = keyInfo.getChain();
         } else {
            this.logDebug("Unable to get SSL Client Identity Key Info");
         }
      }

      String sourceId = this.getSourceIdFromArtifact(artifact);
      if (sourceId == null) {
         this.logDebug("Unable to decode artifact");
         return null;
      } else {
         if (partner == null) {
            partner = this.partnerManager.findAssertingPartyBySourceId(sourceId);
            if (partner == null) {
               this.logDebug("Can't find partner for source ID '" + sourceId + "'");
               return null;
            }
         } else if (!sourceId.equals(partner.getSourceIdHex())) {
            this.logDebug("Source ID from artifact does not match source ID configured for partner '" + partner.getPartnerId() + "'");
            return null;
         }

         String retrievalURL = partner.getAssertionRetrievalURL();
         if (retrievalURL == null) {
            this.logDebug("Partner '" + partner.getPartnerId() + "' does not have an assertion retrieval URL configured");
            return null;
         } else {
            String basicAuth = this.getBasicAuthCredentials(partner);
            this.logDebug("dereferenceArtifact(): artifact is '" + artifact + "'");
            this.logDebug("dereferenceArtifact(): sourceID is '" + sourceId + "'");
            this.logDebug("dereferenceArtifact(): retrieval URL is '" + retrievalURL + "'");
            this.logDebug("dereferenceArtifact(): basic auth credentials " + (basicAuth != null ? "are" : "are not") + " configured");
            SAMLRequest request = new SAMLRequest();
            request.addArtifact(artifact);
            this.logDebug("Created SAMLRequest");
            SAMLResponse response = null;

            SAMLSOAPBinding assertion;
            try {
               this.logDebug("Sending request to source site");
               assertion = new SAMLSOAPBinding();
               response = assertion.send(request, retrievalURL, sslKey, sslCertChain, basicAuth);
               this.logDebug("Got response from source site");
            } catch (SAMLException var13) {
               this.logDebug("Exception while sending/receiving request/response: " + var13.toString());
               return null;
            }

            assertion = null;
            Iterator it = response.getAssertions();
            if (it.hasNext()) {
               SAMLAssertion assertion = (SAMLAssertion)it.next();
               this.logDebug("Got assertion from response");
               return assertion.toString();
            } else {
               this.logDebug("Response has no assertions");
               return null;
            }
         }
      }
   }

   private String getSourceIdFromArtifact(String art) {
      if (art != null && art.length() != 0) {
         byte[] artifact = null;

         byte[] artifact;
         try {
            artifact = SAMLUtil.base64Decode(art);
         } catch (IOException var5) {
            return null;
         }

         if (artifact != null && artifact.length == 42) {
            byte[] sourceId = new byte[20];

            for(int i = 0; i < 20; ++i) {
               sourceId[i] = artifact[i + 2];
            }

            SAMLSourceId id = new SAMLSourceId(sourceId);
            return id.getSourceIdHex();
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private String getBasicAuthCredentials(SAMLAssertingPartyConfig partner) {
      String username = partner.getARSUsername();
      String password = partner.getARSPassword();
      if ((username == null || username.length() <= 0) && (password == null || password.length() <= 0)) {
         return null;
      } else {
         String cred = username + ":" + password;
         return SAMLUtil.base64Encode(cred.getBytes());
      }
   }

   public SAMLAssertingPartyConfig lookupPartner(String apid) {
      return this.partnerManager.findAssertingParty(apid);
   }
}
