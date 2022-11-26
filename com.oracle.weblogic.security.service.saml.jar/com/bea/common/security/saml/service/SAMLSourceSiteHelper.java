package com.bea.common.security.saml.service;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.service.ServiceLogger;
import com.bea.common.security.legacy.spi.SAMLSingleSignOnServiceConfigInfoSpi;
import com.bea.common.security.saml.manager.SAMLKeyManager;
import com.bea.common.security.saml.manager.SAMLRPConfigManager;
import com.bea.common.security.saml.manager.SAMLTrustManager;
import com.bea.common.security.saml.registry.SAMLRelyingPartyConfig;
import com.bea.common.security.saml.utils.SAMLContextHandler;
import com.bea.common.security.saml.utils.SAMLUtil;
import com.bea.common.security.service.CredentialMappingService;
import com.bea.common.security.service.Identity;
import com.bea.common.security.service.SAMLKeyService;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.opensaml.SAMLAssertion;
import org.opensaml.SAMLException;
import org.opensaml.SAMLRequest;
import org.opensaml.SAMLResponse;
import org.opensaml.SAMLSOAPBinding;
import org.w3c.dom.Element;
import weblogic.security.providers.saml.SAMLAssertionStore;
import weblogic.security.providers.saml.SAMLAssertionStoreV2;
import weblogic.security.providers.utils.Utils;
import weblogic.security.service.AdminResource;
import weblogic.security.service.ContextElement;

public class SAMLSourceSiteHelper {
   private static final String DEFAULT_ASSERTION_STORE = "com.bea.common.security.saml.utils.SAMLAssertionStoreMemImpl";
   private static String SignatureMethod_RSA_SHA1_URI = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
   private static final String postURI = "/post";
   private static final String artifactURI = "/artifact";
   private SAMLSingleSignOnServiceConfigInfoSpi ssoServiceConfig;
   private CredentialMappingService credMapper;
   private LoggerSpi log;
   private SAMLKeyManager keyManager = null;
   private SAMLTrustManager trustManager = null;
   private SAMLRPConfigManager partnerManager = null;
   private SAMLAssertionStore assertionStore = null;
   private SAMLAssertionStoreV2 assertionStoreV2 = null;
   private AdminResource rs = null;
   private static String allowExpiredCerts = "com.bea.common.security.saml.allowExpiredCerts";

   private final void logDebug(String msg) {
      if (this.log.isDebugEnabled()) {
         this.log.debug("SAMLSourceSite: " + msg);
      }

   }

   private final boolean isDebugEnabled() {
      return this.log.isDebugEnabled();
   }

   public SAMLSourceSiteHelper(SAMLSingleSignOnServiceConfigInfoSpi ssoServiceConfig, CredentialMappingService credMapper, LoggerSpi log, SAMLKeyService samlKeyService) throws Exception {
      this.ssoServiceConfig = ssoServiceConfig;
      this.credMapper = credMapper;
      this.log = log;
      this.rs = new AdminResource("Credential Mapping", "realm", "SAML");
      this.keyManager = SAMLKeyManager.getManager(samlKeyService);
      this.trustManager = SAMLTrustManager.getManager();
      this.partnerManager = SAMLRPConfigManager.getManager();
      String assertionStoreClassName = ssoServiceConfig.getAssertionStoreClassName();
      Properties assertionStoreProperties = ssoServiceConfig.getAssertionStoreProperties();
      if (assertionStoreClassName == null || assertionStoreClassName.equals("")) {
         assertionStoreClassName = "com.bea.common.security.saml.utils.SAMLAssertionStoreMemImpl";
         assertionStoreProperties = null;
      }

      this.assertionStore = (SAMLAssertionStore)SAMLUtil.instantiatePlugin(assertionStoreClassName, SAMLAssertionStore.class.getName());
      this.assertionStore.initStore(assertionStoreProperties);
      if (this.assertionStore != null) {
         if (this.assertionStore instanceof SAMLAssertionStoreV2) {
            this.assertionStoreV2 = (SAMLAssertionStoreV2)this.assertionStore;
            this.assertionStore = null;
            this.logDebug("init(): Assertion store is version 2, will verify destination sites");
         } else {
            this.logDebug("init(): Assertion store is version 1, unable to verify destination sites");
         }
      }

      String alias = ssoServiceConfig.getSigningKeyAlias();
      String passphrase = ssoServiceConfig.getSigningKeyPassPhrase();
      if (alias != null && !alias.equals("")) {
         this.logDebug("init(): Setting SigningKey: " + alias);
         if (passphrase == null) {
            passphrase = "";
         }

         this.keyManager.setProtocolKeyAliasInfo(alias, passphrase);
      }

   }

   public void dispatchPOSTRequest(SAMLRelyingPartyConfig partner, Identity user, String targetURL, HttpServletRequest req, HttpServletResponse resp, ServletContext sc) throws ServletException, IOException {
      if (!this.ssoServiceConfig.isITSPostEnabled()) {
         resp.sendError(404);
      } else {
         SAMLContextHandler ctx = new SAMLContextHandler();
         Element assertion = this.getAssertion(partner.getPartnerId(), user, targetURL, ctx);
         if (assertion == null) {
            resp.sendError(403);
         } else {
            String consumerURL = partner.getAssertionConsumerURL();
            String postTemplate = partner.getPostForm();
            if (consumerURL == null) {
               throw new ServletException(ServiceLogger.getSAMLCouldNotGenerate("assertion", "null URL"));
            } else {
               String response = null;

               try {
                  byte[] responseBytes = this.constructPOSTResponse(assertion, consumerURL);
                  if (responseBytes != null) {
                     response = new String(responseBytes);
                  } else {
                     this.logDebug("Failed to construct the POST response");
                  }
               } catch (SAMLException var23) {
                  throw new ServletException(ServiceLogger.getSAMLCouldNotGenerate("response", var23.getMessage()));
               } catch (IOException var24) {
                  throw new ServletException(ServiceLogger.getSAMLCouldNotGenerate("response", var24.getMessage()));
               }

               if (response == null) {
                  throw new ServletException(ServiceLogger.getSAMLCouldNotGenerate("response", "unknown error"));
               } else {
                  Map encodedRequestParams = SAMLUtil.paramStringToMap(req.getQueryString());
                  encodedRequestParams.remove("TARGET");
                  encodedRequestParams.remove("RPID");
                  HashMap requestParams = new HashMap();
                  Set entries = encodedRequestParams.entrySet();
                  Iterator it = entries.iterator();

                  String escapedTarget;
                  while(it.hasNext()) {
                     Map.Entry entry = (Map.Entry)it.next();
                     escapedTarget = URLDecoder.decode((String)entry.getKey(), "UTF-8");
                     String val = URLDecoder.decode((String)entry.getValue(), "UTF-8");
                     if (escapedTarget.length() > 0) {
                        requestParams.put(URLEncoder.encode(escapedTarget, "UTF-8"), URLEncoder.encode(val, "UTF-8"));
                     }
                  }

                  Map acsParams = SAMLUtil.paramArrayToMap(partner.getAssertionConsumerParams());
                  if (this.isDebugEnabled()) {
                     String[] params = partner.getAssertionConsumerParams();

                     for(int i = 0; params != null && i < params.length; ++i) {
                        this.logDebug("ACSParams[" + i + "]: " + params[i]);
                     }
                  }

                  if (this.isDebugEnabled()) {
                     this.logDebug("Dispatch POST Request Target URL is '" + targetURL + "'");
                  }

                  escapedTarget = Utils.escapeFormValue(targetURL);
                  if (this.isDebugEnabled()) {
                     this.logDebug("POST FORM escaped TARGET is '" + escapedTarget + "'");
                  }

                  if (postTemplate != null) {
                     ServletContext templateContext = sc.getContext(postTemplate);
                     if (templateContext == null) {
                        this.logDebug("can't get servlet context for custom post form: " + postTemplate);
                        throw new ServletException(ServiceLogger.getSAMLInvalidPostFormConfig());
                     }

                     String contextPath = null;
                     RequestDispatcher templateDispatcher = null;

                     try {
                        ServletContext.class.getMethod("getContextPath", (Class[])null);
                        contextPath = templateContext.getContextPath();
                        if (contextPath == null || contextPath.length() == 0) {
                           contextPath = "/";
                        }

                        templateDispatcher = templateContext.getRequestDispatcher(postTemplate.substring(contextPath.length()));
                     } catch (SecurityException var25) {
                        this.logDebug("can't call getContextPath(): " + var25.getMessage());
                     } catch (NoSuchMethodException var26) {
                        this.logDebug("can't call getContextPath(): " + var26.getMessage());
                     }

                     if (templateDispatcher == null) {
                        int secondSlashPosition = postTemplate.indexOf("/", 1);
                        String dispatcherPath = secondSlashPosition == -1 ? postTemplate : postTemplate.substring(secondSlashPosition);
                        this.logDebug("dispatcherPath: " + dispatcherPath);
                        templateDispatcher = templateContext.getRequestDispatcher(dispatcherPath);
                     }

                     if (templateDispatcher == null) {
                        throw new ServletException(ServiceLogger.getSAMLInvalidPostFormConfig());
                     }

                     req.setAttribute("TARGET", escapedTarget);
                     req.setAttribute("SAML_AssertionConsumerURL", consumerURL);
                     req.setAttribute("SAML_AssertionConsumerParams", acsParams);
                     req.setAttribute("SAML_ITSRequestParams", requestParams);
                     req.setAttribute("SAMLResponse", response);
                     templateDispatcher.forward(req, resp);
                  } else {
                     resp.setContentType("text/html");
                     PrintWriter out = resp.getWriter();
                     out.println("<HTML>");
                     out.println("<HEAD>");
                     out.println("<TITLE>SAML Post Profile Intersite Transfer Service</TITLE>");
                     out.println("</HEAD>");
                     out.println("<BODY onLoad=\"document.forms[0].submit();\">");
                     out.println("<FORM METHOD=\"POST\" ACTION=\"" + consumerURL + "\">");
                     this.outputFormParameter(out, "TARGET", escapedTarget);
                     if (this.ssoServiceConfig.isV2Config()) {
                        this.outputFormParameterMap(out, acsParams);
                        this.outputFormParameterMap(out, requestParams);
                     }

                     this.outputFormParameter(out, "SAMLResponse", new String(response));
                     out.println("</FORM>");
                     out.println("</BODY>");
                     out.println("</HTML>");
                  }

               }
            }
         }
      }
   }

   private void outputFormParameter(PrintWriter out, String name, String value) {
      out.println("<INPUT TYPE=\"HIDDEN\" NAME=\"" + name + "\" VALUE=\"" + value + "\">");
   }

   private void outputFormParameterMap(PrintWriter out, Map params) {
      Set entries = params.entrySet();

      Map.Entry entry;
      for(Iterator it = entries.iterator(); it.hasNext(); this.outputFormParameter(out, (String)entry.getKey(), (String)entry.getValue())) {
         entry = (Map.Entry)it.next();
         if (this.isDebugEnabled()) {
            this.logDebug("Outputting param: " + (String)entry.getKey() + "=" + (String)entry.getValue());
         }
      }

   }

   public void dispatchArtifactRequest(SAMLRelyingPartyConfig partner, Identity user, String targetURL, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      if (!this.ssoServiceConfig.isITSArtifactEnabled()) {
         resp.sendError(404);
      } else {
         SAMLContextHandler ctx = new SAMLContextHandler();
         Element assertion = this.getAssertion(partner.getPartnerId(), user, targetURL, ctx);
         if (assertion == null) {
            resp.sendError(403);
         } else {
            String assertionID = (String)ctx.getValue("com.bea.contextelement.saml.AssertionID");
            Long expireTime = (Long)ctx.getValue("com.bea.contextelement.saml.AssertionExpireTime");
            if (assertionID != null && expireTime != null) {
               String artifact = this.constructArtifact(this.ssoServiceConfig.getSourceIdBytes(), assertionID);
               if (artifact == null) {
                  throw new ServletException(ServiceLogger.getSAMLCouldNotGenerate("assertion", "null artifact"));
               } else {
                  if (this.assertionStoreV2 != null) {
                     if (!this.assertionStoreV2.storeAssertionInfo(artifact, partner.getPartnerId(), expireTime, assertion)) {
                        throw new ServletException(ServiceLogger.getSAMLCouldNotGenerate("assertion", "store assertion fail"));
                     }
                  } else {
                     if (this.assertionStore == null) {
                        throw new ServletException(ServiceLogger.getSAMLCouldNotGenerate("assertion", "unknown error"));
                     }

                     if (!this.assertionStore.storeAssertion(artifact, expireTime, assertion)) {
                        throw new ServletException(ServiceLogger.getSAMLCouldNotGenerate("assertion", "store assertion fail"));
                     }
                  }

                  String consumerURL = SAMLUtil.buildURLWithParams(partner.getAssertionConsumerURL(), partner.getAssertionConsumerParams());
                  this.logDebug("SAMLSourceSite: Consumer URL with params is '" + consumerURL + "'");
                  String requestParams = req.getQueryString();
                  if (requestParams != null && requestParams.length() > 0) {
                     this.logDebug("Original request query params: '" + requestParams + "'");
                     requestParams = "&" + SAMLUtil.queryStringStripParam(requestParams, "RPID");
                     this.logDebug("Modified request query params: '" + requestParams + "'");
                  }

                  this.logDebug("SAMLSourceSite: Artifact is '" + artifact + "'");
                  String artifactParam = "SAMLart=" + URLEncoder.encode(artifact, "UTF-8");
                  this.logDebug("SAMLSourceSite: URL-encoded artifact param is '" + artifactParam + "'");
                  String redirectURL = consumerURL + artifactParam + requestParams;
                  this.logDebug("Redirect URL is '" + redirectURL + "'");
                  String url = SAMLUtil.ENABLE_URL_REWRITING ? resp.encodeRedirectURL(redirectURL) : redirectURL;
                  if (SAMLUtil.ENABLE_URL_REWRITING) {
                     this.logDebug("Encoded redirect URL is '" + url + "'");
                  }

                  resp.sendRedirect(url);
               }
            } else {
               throw new ServletException(ServiceLogger.getSAMLCouldNotGenerate("assertion", "null id or expire time"));
            }
         }
      }
   }

   public void dispatchAssertionRequest(HttpServletRequest req, HttpServletResponse resp, X509Certificate clientCert, String basicAuth) throws ServletException, IOException {
      if (!this.ssoServiceConfig.isITSArtifactEnabled()) {
         resp.sendError(404);
      } else {
         SAMLSOAPBinding binding = new SAMLSOAPBinding();
         SAMLRequest request = null;
         String contentType = "text/xml;charset=UTF-8";
         this.logDebug("dispatchAssertionRequest: Setting response content type to '" + contentType + "'");
         resp.setContentType(contentType);

         try {
            request = binding.receive(req);
            this.logDebug("dispatchAssertionRequest: Got SAML Request from SOAP message");
         } catch (SAMLException var12) {
            this.logDebug("dispatchAssertionRequest: Exception while processing request, returning SOAP fault: " + var12.toString());
            binding.respond(resp, (SAMLResponse)null, var12);
            return;
         }

         List assertions = null;
         SAMLResponse response = null;
         Iterator it = request.getArtifacts();

         try {
            this.logDebug("dispatchAssertionRequest: fetching assertions");
            assertions = this.lookupStoredAssertions(it, clientCert, basicAuth);
            if (assertions == null) {
               this.logDebug("dispatchAssertionRequest: destination site auth failure, returning FORBIDDEN");
               resp.sendError(403);
               return;
            }

            this.logDebug("dispatchAssertionRequest: building response");
            response = this.constructAssertionResponse(request.getId(), assertions);
         } catch (SAMLException var13) {
            this.logDebug("dispatchAssertionRequest: Exception while building response: " + var13.toString());
            binding.respond(resp, (SAMLResponse)null, var13);
            return;
         }

         this.logDebug("dispatchAssertionRequest: Sending response to requester");
         binding.respond(resp, response, (SAMLException)null);
      }
   }

   private Element getAssertion(String partnerId, Identity user, String targetURL, SAMLContextHandler ctx) {
      ctx.addElement(new ContextElement("com.bea.contextelement.saml.PartnerId", partnerId));
      ctx.addElement(new ContextElement("com.bea.contextelement.saml.TargetResource", targetURL));
      Object[] retval = this.credMapper.getCredentials(user, user, this.rs, ctx, "SAML.Assertion.DOM");
      return retval != null && retval.length != 0 ? (Element)retval[0] : null;
   }

   private String constructArtifact(byte[] sourceID, String assertionID) {
      if (assertionID != null && assertionID.length() != 0) {
         byte[] digest = null;

         byte[] digest;
         try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            digest = md.digest(assertionID.getBytes());
         } catch (NoSuchAlgorithmException var6) {
            return null;
         }

         byte[] artifact = new byte[42];
         artifact[0] = 0;
         artifact[1] = 1;

         int i;
         for(i = 0; i < 20; ++i) {
            artifact[i + 2] = sourceID[i];
         }

         for(i = 0; i < 20; ++i) {
            artifact[i + 22] = digest[i];
         }

         return SAMLUtil.base64Encode(artifact);
      } else {
         return null;
      }
   }

   private byte[] constructPOSTResponse(Element assertionDOM, String consumerURL) throws SAMLException, IOException {
      if (assertionDOM != null && consumerURL != null) {
         SAMLKeyManager km = this.keyManager;
         if (km == null) {
            this.logDebug("Unable to locate SAML Key Manager");
            return null;
         } else {
            SAMLAssertion assertion = new SAMLAssertion(assertionDOM);
            SAMLResponse response = new SAMLResponse();
            response.setRecipient(consumerURL);
            response.addAssertion(assertion);
            SAMLKeyManager.KeyInfo keyInfo = km.getProtocolSigningKeyInfo();
            if (keyInfo == null) {
               this.logDebug("Unable to retrieve protocol signing key info");
               return null;
            } else {
               boolean allowExpired = Boolean.getBoolean(allowExpiredCerts);

               try {
                  ((X509Certificate)keyInfo.getCert()).checkValidity();
               } catch (CertificateExpiredException var9) {
                  this.logDebug("constructPOSTResponse: Using expired certificate to sign response");
                  if (!allowExpired) {
                     throw new IOException("Using expired certificate to sign response: " + var9.getMessage());
                  }
               } catch (CertificateNotYetValidException var10) {
                  this.logDebug("constructPOSTResponse:Certificate being used to sign response is not yet valid");
                  if (!allowExpired) {
                     throw new IOException("Certificate being used to sign response is not yet valid: " + var10.getMessage());
                  }
               }

               response.sign(SignatureMethod_RSA_SHA1_URI, keyInfo.getKey(), keyInfo.getCertAsList());
               return response.toBase64();
            }
         }
      } else {
         return null;
      }
   }

   private List lookupStoredAssertions(Iterator it, X509Certificate clientCert, String basicAuth) throws SAMLException {
      this.logDebug("lookupStoredAssertions: fetching assertions");
      if (!it.hasNext()) {
         this.logDebug("lookupStoredAssertions: no assertions were requested");
      }

      ArrayList assertions = new ArrayList();
      boolean foundAllAssertions = true;
      boolean partnerChecked = false;
      boolean partnerAuthorized = true;
      String partnerId = null;

      while(it.hasNext()) {
         String artifact = (String)it.next();
         this.logDebug("lookupStoredAssertions: fetching assertion for artifact '" + artifact + "'");
         if (this.assertionStoreV2 != null) {
            SAMLAssertionStoreV2.AssertionInfo aInfo = this.assertionStoreV2.retrieveAssertionInfo(artifact);
            if (aInfo == null) {
               this.logDebug("lookupStoredAssertions: assertion not found for artifact '" + artifact + "', will return no assertions");
               foundAllAssertions = false;
            } else {
               SAMLRelyingPartyConfig partner = this.partnerManager.findRelyingParty(aInfo.getPartnerId());
               if (partner == null) {
                  this.logDebug("lookupStoredAssertions: auth failure: partner '" + aInfo.getPartnerId() + "' not found");
                  partnerAuthorized = false;
               } else {
                  if (!partnerChecked) {
                     if (!this.verifyDestinationSite(partner, clientCert, basicAuth)) {
                        this.logDebug("lookupStoredASsertions: auth failure: missing/invalid credentials for partner '" + partner.getPartnerId() + "'");
                        partnerAuthorized = false;
                     }

                     partnerChecked = true;
                     partnerId = partner.getPartnerId();
                  } else if (!partnerId.equals(partner.getPartnerId())) {
                     this.logDebug("lookupStoredAssertions: auth failure: multiple partner IDs");
                     partnerAuthorized = false;
                  }

                  if (foundAllAssertions && partnerAuthorized) {
                     assertions.add(new SAMLAssertion(aInfo.getAssertion()));
                  }
               }
            }
         } else {
            if (this.assertionStore == null) {
               this.logDebug("lookupStoredAssertions: no assertion store!");
               foundAllAssertions = false;
               break;
            }

            if (!partnerChecked) {
               if (clientCert != null && !this.trustManager.isCertificateTrusted(clientCert)) {
                  this.logDebug("lookupStoredASsertions: auth failure: invalid credentials: certificate not trusted");
                  partnerAuthorized = false;
               }

               if (basicAuth != null) {
                  this.logDebug("lookupStoredASsertions: auth failure: invalid credentials: basic auth not enabled");
                  partnerAuthorized = false;
               }

               partnerChecked = true;
            }

            Element domAssertion = this.assertionStore.retrieveAssertion(artifact);
            if (domAssertion == null) {
               this.logDebug("lookupStoredAssertions: assertion not found for artifact '" + artifact + "', will return no assertions");
               foundAllAssertions = false;
            } else if (foundAllAssertions && partnerAuthorized) {
               assertions.add(new SAMLAssertion(domAssertion));
            }
         }
      }

      if (!partnerAuthorized) {
         return null;
      } else {
         if (!foundAllAssertions) {
            assertions.clear();
         }

         return assertions;
      }
   }

   private SAMLResponse constructAssertionResponse(String inResponseTo, List assertions) throws SAMLException {
      SAMLResponse response = new SAMLResponse();
      response.setInResponseTo(inResponseTo);
      response.setAssertions(assertions);
      return response;
   }

   private boolean verifyDestinationSite(SAMLRelyingPartyConfig partner, X509Certificate clientCert, String basicAuth) {
      String certAlias = partner.getSSLClientCertAlias();
      if (certAlias != null) {
         if (clientCert == null) {
            this.logDebug("verifyDestinationSite: auth failure for partner '" + partner.getPartnerId() + "', client cert required but not provided");
            return false;
         }

         if (!this.trustManager.isCertificateTrustedAlias(clientCert, certAlias)) {
            this.logDebug("verifyDestinationSite: auth failure for partner '" + partner.getPartnerId() + "', supplied client cert not trusted");
            return false;
         }
      }

      String username = partner.getARSUsername();
      String password = partner.getARSPassword();
      if ((username == null || username.length() <= 0) && (password == null || password.length() <= 0)) {
         if (basicAuth != null) {
            this.logDebug("verifyDestinationSite: auth failure for partner '" + partner.getPartnerId() + "', Basic auth not required but username/password was provided");
            return false;
         }
      } else {
         if (basicAuth == null) {
            this.logDebug("verifyDestinationSite: auth failure for partner '" + partner.getPartnerId() + "', Basic auth required but username/password not provided");
            return false;
         }

         byte[] decodedAuthBytes = null;

         byte[] decodedAuthBytes;
         try {
            decodedAuthBytes = SAMLUtil.base64Decode(basicAuth);
         } catch (IOException var9) {
            decodedAuthBytes = null;
         }

         if (decodedAuthBytes == null) {
            this.logDebug("verifyDestinationSite: auth failure for partner '" + partner.getPartnerId() + "', could not decode Basic auth credential");
            return false;
         }

         String decodedAuth = new String(decodedAuthBytes);
         if (!decodedAuth.equals(username + ":" + password)) {
            this.logDebug("verifyDestinationSite: auth failure for partner '" + partner.getPartnerId() + "', Basic auth credentials invalid");
            return false;
         }
      }

      this.logDebug("verifyDestinationSite: authentication/verification succeeded for partner '" + partner.getPartnerId() + "'");
      return true;
   }

   public SAMLRelyingPartyConfig lookupPartner(String rpid, String targetURL, String requestURI) {
      if (rpid != null) {
         return this.partnerManager.findRelyingParty(rpid);
      } else {
         String confMethod = null;
         if (requestURI.endsWith("/post")) {
            confMethod = "bearer";
         } else {
            if (!requestURI.endsWith("/artifact")) {
               this.logDebug("lookupPartner: No RPID provided and request is not on '/post' or '/artifact' URI");
               return null;
            }

            confMethod = "artifact";
         }

         return this.partnerManager.findRelyingPartyByRequestParams(confMethod, targetURL);
      }
   }

   public String validateRequestURI(SAMLRelyingPartyConfig partner, String servletURI) {
      if (partner.getProfileConfMethodName().equals("bearer") && servletURI.endsWith("/artifact")) {
         return "Artifact";
      } else {
         return partner.getProfileConfMethodName().equals("artifact") && servletURI.endsWith("/post") ? "POST" : null;
      }
   }
}
