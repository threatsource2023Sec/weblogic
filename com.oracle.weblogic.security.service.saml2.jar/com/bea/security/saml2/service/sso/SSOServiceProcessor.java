package com.bea.security.saml2.service.sso;

import com.bea.common.security.saml2.utils.SAMLContextHandler;
import com.bea.common.security.service.CredentialMappingService;
import com.bea.common.security.service.Identity;
import com.bea.common.security.service.LoginSession;
import com.bea.common.security.utils.SAML2ClassLoader;
import com.bea.security.saml2.Saml2Logger;
import com.bea.security.saml2.binding.BindingHandlerException;
import com.bea.security.saml2.binding.BindingReceiver;
import com.bea.security.saml2.binding.BindingSender;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import com.bea.security.saml2.providers.registry.IndexedEndpoint;
import com.bea.security.saml2.providers.registry.IndexedEndpointImpl;
import com.bea.security.saml2.providers.registry.WebSSOSPPartner;
import com.bea.security.saml2.registry.PartnerManagerException;
import com.bea.security.saml2.service.AbstractService;
import com.bea.security.saml2.service.SAML2DetailedException;
import com.bea.security.saml2.service.SAML2Exception;
import com.bea.security.saml2.util.SAML2Utils;
import com.bea.security.saml2.util.key.SAML2KeyManager;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.opensaml.core.xml.io.Unmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.core.EncryptedAssertion;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.x509.BasicX509Credential;
import org.opensaml.xmlsec.encryption.support.EncryptionException;
import org.w3c.dom.Element;
import weblogic.security.providers.utils.XSSSanitizer;
import weblogic.security.service.AdminResource;
import weblogic.security.service.ContextElement;

class SSOServiceProcessor extends AbstractService {
   private static final String AUTH_REQUEST_ATTRIBUTE = "com.bea.security.saml2.attr.AuthnRequest";
   private static final String RELAY_STATE_ATTRIBUTE = "com.bea.security.saml2.attr.RelayState";
   private static final AdminResource DUMMY_RESOURCE = new AdminResource("Credential Mapping", "realm", "SAML2");
   private final SAMLResponseBuilder respBuilder;
   private final AuthnRequestValidator validator;
   private HttpServletRequest request;
   private HttpServletResponse response;
   private String relayState;
   private WebSSOSPPartner partner;
   private String issuerURI;
   private IndexedEndpoint targetEndpoint;
   private WrappedAuthnRequest wrappedReq = new WrappedAuthnRequest((AuthnRequest)null);

   public SSOServiceProcessor(SAML2ConfigSpi config) {
      super(config);
      this.validator = new AuthnRequestValidator(config);
      this.respBuilder = new SAMLResponseBuilder();
      this.issuerURI = config.getLocalConfiguration().getEntityID();
   }

   public boolean process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      this.request = request;
      this.response = response;
      String requestURI = request.getRequestURI();

      try {
         if (!requestURI.endsWith("/login-return") && !requestURI.endsWith("/idp/login")) {
            if (requestURI.endsWith("/initiator")) {
               this.doInitiator();
            } else {
               this.processAuthnRequest();
            }
         } else {
            this.loginReturn();
         }
      } catch (SAML2Exception var5) {
         this.logAndSendError(response, var5.getHttpStatusCode(), var5);
      } catch (Exception var6) {
         this.logAndSendError(response, 500, var6);
      }

      return true;
   }

   private void processAuthnRequest() throws IOException, ServletException, SAML2Exception {
      String bindingType = this.getBindingTypeFromURI(this.request, this.response);
      this.checkBindingTypeEnabled(bindingType);
      BindingReceiver receiver = this.receive(bindingType);
      String issuer = this.wrappedReq.getIssuer();

      try {
         this.partner = (WebSSOSPPartner)this.config.getPartnerManager().findServiceProviderByIssuerURI(issuer);
      } catch (PartnerManagerException var7) {
         throw new SAML2Exception(Saml2Logger.getFindSPPartnerByIssuerURIError(issuer), 500);
      }

      if (this.partner == null) {
         throw new SAML2Exception(Saml2Logger.getNoSPPartnerWithIssuerURI(issuer), 404);
      } else if (!this.partner.isEnabled()) {
         throw new SAML2DetailedException(Saml2Logger.getSPPartnerIsDisabled(this.partner.getName()), 404);
      } else {
         try {
            this.validator.verifySignature(this.partner, receiver, this.wrappedReq.getAuthnRequest());
            this.validator.validate(this.partner, this.wrappedReq.getAuthnRequest(), this.request.getRequestURI());
            if (this.wrappedReq.isForceAuthn() && this.wrappedReq.isPassive()) {
               throw new SAML2DetailedException(Saml2Logger.getCantForceAuthnAndInPassiveBothTrue(), 400);
            }

            if (this.wrappedReq.isForceAuthn()) {
               this.logoutUser();
            }

            if (!this.isUserAuthenticated()) {
               if (this.wrappedReq.isPassive()) {
                  throw new SAML2DetailedException(Saml2Logger.getCantAuthnUserInPassiveMode(), 403);
               }

               this.fowardToLoginPage();
            } else {
               this.sendResponse();
            }
         } catch (SAML2DetailedException var5) {
            this.sendErrorResponse(var5, this.wrappedReq.getID());
         } catch (SAML2Exception var6) {
            this.logAndSendError(this.response, var6.getHttpStatusCode(), var6);
         }

      }
   }

   private void checkBindingTypeEnabled(String bindingType) throws SAML2Exception {
      if (!this.config.getLocalConfiguration().isIdentityProviderArtifactBindingEnabled() && "HTTP/Artifact".equals(bindingType)) {
         throw new SAML2Exception(Saml2Logger.getBindingUnenabled("HTTP/Artifact"));
      } else if (!this.config.getLocalConfiguration().isIdentityProviderPOSTBindingEnabled() && "HTTP/POST".equals(bindingType)) {
         throw new SAML2Exception(Saml2Logger.getBindingUnenabled("HTTP/POST"));
      } else if (!this.config.getLocalConfiguration().isIdentityProviderRedirectBindingEnabled() && "HTTP/Redirect".equals(bindingType)) {
         throw new SAML2Exception(Saml2Logger.getBindingUnenabled("HTTP/Redirect"));
      }
   }

   private void loginReturn() throws IOException, SAML2Exception {
      if (!this.isUserAuthenticated()) {
         throw new SAML2Exception(Saml2Logger.getUnauthenticatedUserAccessingLoginReturn(), 403);
      } else {
         HttpSession session = null;
         Thread t = Thread.currentThread();
         ClassLoader tcl = t.getContextClassLoader();

         try {
            if (tcl instanceof SAML2ClassLoader) {
               t.setContextClassLoader(((SAML2ClassLoader)tcl).getThreadConextClassLoader());
            }

            session = this.request.getSession();
         } finally {
            t.setContextClassLoader(tcl);
         }

         AuthnRequestWrapper authnReq = (AuthnRequestWrapper)session.getAttribute("com.bea.security.saml2.attr.AuthnRequest");
         this.wrappedReq = new WrappedAuthnRequest(authnReq);
         if (authnReq == null) {
            throw new SAML2Exception(Saml2Logger.getNoAuthnRequestInSession(), 404);
         } else {
            this.relayState = (String)session.getAttribute("com.bea.security.saml2.attr.RelayState");

            try {
               if (tcl instanceof SAML2ClassLoader) {
                  t.setContextClassLoader(((SAML2ClassLoader)tcl).getThreadConextClassLoader());
               }

               session.removeAttribute("com.bea.security.saml2.attr.AuthnRequest");
               session.removeAttribute("com.bea.security.saml2.attr.RelayState");
            } finally {
               t.setContextClassLoader(tcl);
            }

            String issuer = this.wrappedReq.getIssuer();

            try {
               this.partner = (WebSSOSPPartner)this.config.getPartnerManager().findServiceProviderByIssuerURI(issuer);
            } catch (PartnerManagerException var13) {
               throw new SAML2Exception(Saml2Logger.getFindSPPartnerByIssuerURIError(issuer), 500);
            }

            if (this.partner == null) {
               throw new SAML2Exception(Saml2Logger.getNoSPPartnerWithIssuerURI(issuer), 404);
            } else if (!this.partner.isEnabled()) {
               throw new SAML2DetailedException(Saml2Logger.getSPPartnerIsDisabled(this.partner.getName()), 404);
            } else {
               this.sendResponse();
            }
         }
      }
   }

   private void doInitiator() throws IOException, SAML2Exception {
      if (!this.isUserAuthenticated()) {
         throw new SAML2Exception(Saml2Logger.getUnauthenticatedUserAccessingIdpInitiator(), 403);
      } else {
         String name = this.request.getParameter("SPName");
         if (name == null) {
            throw new SAML2Exception(Saml2Logger.getNoRequiredParamForIdpInitator("SPName"));
         } else {
            try {
               this.partner = (WebSSOSPPartner)this.config.getPartnerManager().getSPPartner(name);
            } catch (PartnerManagerException var7) {
               throw new SAML2Exception(Saml2Logger.getFindSPPartnerByNameError(name), 500);
            }

            if (this.partner == null) {
               throw new SAML2Exception(Saml2Logger.getCantFindPartnerFromName(name), 404);
            } else if (!this.partner.isEnabled()) {
               throw new SAML2DetailedException(Saml2Logger.getSPPartnerIsDisabled(name), 404);
            } else {
               String targetUrl = this.request.getParameter("RequestURL");
               Set paramNames = this.request.getParameterMap().keySet();
               if (paramNames.size() > 2) {
                  StringBuffer buf = new StringBuffer();
                  Iterator it = paramNames.iterator();

                  while(it.hasNext()) {
                     String paramName = (String)it.next();
                     if (!"SPName".equals(paramName) && !"RequestURL".equals(paramName)) {
                        buf.append('&').append(paramName).append('=').append(this.request.getParameter(paramName));
                     }
                  }

                  if (buf.length() > 0) {
                     buf.setCharAt(0, '?');
                  }

                  targetUrl = targetUrl + buf.toString();
               }

               if (targetUrl != null) {
                  if (targetUrl.length() >= 80) {
                     throw new SAML2Exception(Saml2Logger.getRelayStateTooLong());
                  }

                  this.relayState = targetUrl;
               }

               this.sendResponse();
            }
         }
      }
   }

   private BindingReceiver receive(String bindingType) throws SAML2Exception {
      try {
         BindingReceiver receiver = this.config.getBindingHandlerFactory().newBindingReceiver(bindingType, this.request, this.response);
         this.relayState = receiver.getRelayState();
         RequestAbstractType req = receiver.receiveRequest();
         if (!(req instanceof AuthnRequest)) {
            throw new SAML2Exception(Saml2Logger.getReceivedNonAuthnRequestDoc(req.getClass().getName()));
         } else {
            this.wrappedReq = new WrappedAuthnRequest((AuthnRequest)req);
            return receiver;
         }
      } catch (BindingHandlerException var4) {
         throw new SAML2Exception(Saml2Logger.getFailedToReceiveDocument("AuthnRequest"), var4, var4.getHttpStatusCode());
      }
   }

   private void fowardToLoginPage() throws IOException, ServletException, SAML2Exception {
      Thread t = Thread.currentThread();
      ClassLoader tcl = t.getContextClassLoader();

      try {
         if (tcl instanceof SAML2ClassLoader) {
            t.setContextClassLoader(((SAML2ClassLoader)tcl).getThreadConextClassLoader());
         }

         HttpSession session = this.request.getSession();
         session.setAttribute("com.bea.security.saml2.attr.AuthnRequest", this.wrappedReq.getAuthnRequestWrapper());
         session.setAttribute("com.bea.security.saml2.attr.RelayState", this.relayState);
      } finally {
         t.setContextClassLoader(tcl);
      }

      String loginURL = this.config.getLocalConfiguration().getLoginURL();
      String paramName = this.config.getLocalConfiguration().getLoginReturnQueryParameter();
      String loginReturnURL = this.config.getLocalConfiguration().getPublishedSiteURL() + "/idp/sso/login-return";
      if (loginURL != null && loginURL.trim().length() != 0) {
         if (paramName != null && paramName.trim().length() > 0) {
            loginURL = loginURL + "?" + paramName + "=" + loginReturnURL;
         }

         String redirectURL = SAML2Utils.ENABLE_URL_REWRITING ? this.response.encodeRedirectURL(loginURL) : loginURL;
         String validURL = XSSSanitizer.getValidInput(redirectURL);
         if (this.log.isDebugEnabled()) {
            this.log.debug("validURL: " + validURL);
         }

         this.response.sendRedirect(validURL);
      } else {
         throw new SAML2Exception(Saml2Logger.getNoLoginURLIsConfigured(), 404);
      }
   }

   private void sendResponse() throws IOException {
      Identity user = this.getCurrentIdentity();
      String requestId = this.wrappedReq.getID();

      try {
         IndexedEndpoint acs = this.getACSEndpoint();
         SAML2Utils.checkBindingType(acs.getBinding(), (SAML2ConfigSpi)null, true);
         Assertion assertion = this.getAssertionForUser(user, this.wrappedReq, acs.getLocation());
         this.checkSSOCertificate();
         Response samlResponse = null;
         if (!this.config.getLocalConfiguration().isAssertionEncryptionEnabled()) {
            if (this.partner.getAssertionEncryptionCert() != null && this.log.isDebugEnabled()) {
               this.log.warn("The Service Partner provided an encryption certificate but the local Assertion Encryption configuration is disabled.");
            }

            samlResponse = this.respBuilder.buildResponse(assertion, (EncryptedAssertion)null, acs.getLocation(), requestId, this.issuerURI, this.getPrivateKey(), this.getCertificate());
         } else {
            X509Certificate encryptionCert = this.partner.getAssertionEncryptionCert();
            if (encryptionCert == null) {
               if (this.log.isDebugEnabled()) {
                  this.log.debug("The assertion will not be encrypted as the Service Partner provided no encryption certificate.");
               }

               samlResponse = this.respBuilder.buildResponse(assertion, (EncryptedAssertion)null, acs.getLocation(), requestId, this.issuerURI, this.getPrivateKey(), this.getCertificate());
            } else {
               try {
                  encryptionCert.checkValidity();
               } catch (CertificateExpiredException var13) {
                  if (!SAML2Utils.ALLOW_EXPIRE_CERTS) {
                     throw new SAML2Exception("Expired encryption certificates are not allowed.", var13, 500);
                  }

                  if (this.log.isDebugEnabled()) {
                     this.log.debug("Using an expired certificate to encrypt a SAML assertion: " + encryptionCert.getSubjectDN());
                  }
               } catch (CertificateNotYetValidException var14) {
                  if (!SAML2Utils.ALLOW_EXPIRE_CERTS) {
                     throw new SAML2Exception("Not yet valid certificates are not allowed.", var14, 500);
                  }

                  if (this.log.isDebugEnabled()) {
                     this.log.debug("Using a not yet valid certificate to encrypt a SAML assertion: " + encryptionCert.getSubjectDN());
                  }
               }

               Credential encryptionCredential = new BasicX509Credential(this.partner.getAssertionEncryptionCert());
               String dataEncryptionAlgorithm = SAML2Utils.resolveDataEncryptionAlgorithm(this.config.getLocalConfiguration().getDataEncryptionAlgorithm(), this.partner.getEncryptionAlgorithms());
               String keyEncryptionAlgorithm = SAML2Utils.resolveKeyEncryptionAlgorithm(this.config.getLocalConfiguration().getKeyEncryptionAlgorithm(), this.partner.getEncryptionAlgorithms());
               EncryptedAssertion encryptedAssertion = null;

               try {
                  if (this.log.isDebugEnabled()) {
                     this.log.debug("Encrypting assertion using " + dataEncryptionAlgorithm + " data encryption algorithm ID and " + keyEncryptionAlgorithm + " key transport algorithm ID.");
                  }

                  encryptedAssertion = SAML2Utils.encryptAssertion(assertion, dataEncryptionAlgorithm, keyEncryptionAlgorithm, encryptionCredential, this.partner.getEntityID());
               } catch (NoSuchAlgorithmException | EncryptionException var12) {
                  throw (new SAML2DetailedException(Saml2Logger.getSAML2EncryptionErrors("<saml:Assertion>"), var12, 500)).setStatusCode("urn:oasis:names:tc:SAML:2.0:status:Responder", (String)null);
               }

               samlResponse = this.respBuilder.buildResponse((Assertion)null, encryptedAssertion, acs.getLocation(), requestId, this.issuerURI, this.getPrivateKey(), this.getCertificate());
            }
         }

         BindingSender sender = this.getSender(this.request, this.response, acs.getBinding());
         sender.sendResponse(samlResponse, acs, this.partner, this.relayState, this.getPrivateKey());
      } catch (BindingHandlerException var15) {
         this.logAndSendError(this.response, var15.getHttpStatusCode(), var15);
      } catch (SAML2DetailedException var16) {
         this.sendErrorResponse(var16, requestId);
      } catch (SAML2Exception var17) {
         this.logAndSendError(this.response, var17.getHttpStatusCode(), var17);
      }

   }

   private PrivateKey getPrivateKey() {
      SAML2KeyManager.KeyInfo keyInfo = this.config.getSAML2KeyManager().getSSOKeyInfo();
      if (keyInfo != null) {
         return keyInfo.getKey();
      } else {
         this.log.warn(Saml2Logger.getNoSignKeyFor("<Response>"));
         return null;
      }
   }

   private List getCertificate() {
      SAML2KeyManager.KeyInfo keyInfo = this.config.getSAML2KeyManager().getSSOKeyInfo();
      if (keyInfo != null) {
         return keyInfo.getCertAsList();
      } else {
         this.log.warn(Saml2Logger.getNoSignCertificateFor("<Response>"));
         return null;
      }
   }

   private Assertion getAssertionForUser(Identity user, WrappedAuthnRequest wrappedReq, String bindingLocation) throws SAML2DetailedException {
      SAMLContextHandler ctx = new SAMLContextHandler();
      if (wrappedReq.getID() != null) {
         ctx.addElement(new ContextElement("com.bea.contextelement.saml2.InResponseTo", wrappedReq.getID()));
      }

      ctx.addElement(new ContextElement("com.bea.contextelement.saml2.RecipientEndpoint", bindingLocation));
      ctx.addElement(new ContextElement("com.bea.contextelement.saml2.PartnerName", this.partner.getName()));
      ctx.addElement(new ContextElement("com.bea.contextelement.saml2.EntityID", this.config.getLocalConfiguration().getEntityID()));
      CredentialMappingService cmService = this.config.getCredentialMappingService();
      Object[] assertion = cmService.getCredentials(user, user, DUMMY_RESOURCE, ctx, "SAML2.Assertion.DOM");
      if (assertion != null && assertion.length != 0 && assertion[0] instanceof Element) {
         Element assertionElement = (Element)assertion[0];
         Unmarshaller unmarshaller = XMLObjectSupport.getUnmarshaller(assertionElement);

         try {
            return (Assertion)unmarshaller.unmarshall(assertionElement);
         } catch (UnmarshallingException var10) {
            throw new SAML2DetailedException(Saml2Logger.getFailedToUnmashallAssertion(), var10, 500);
         }
      } else {
         throw new SAML2DetailedException(Saml2Logger.getCantGenerateAssertion(), 404);
      }
   }

   private void sendErrorResponse(SAML2DetailedException detailed, String requestId) throws IOException {
      if (this.log.isDebugEnabled()) {
         this.log.debug(detailed.getMessage(), detailed);
      }

      try {
         IndexedEndpoint acs = this.getACSEndpoint();
         this.checkSSOCertificate();
         Response samlResponse = this.respBuilder.buildErrorResponse(acs.getLocation(), requestId, this.issuerURI, this.getPrivateKey(), this.getCertificate(), detailed.getStatus(), detailed.getMessage());
         BindingSender sender = this.getSender(this.request, this.response, acs.getBinding());
         sender.sendResponse(samlResponse, acs, this.partner, this.relayState, this.getPrivateKey());
      } catch (SAML2Exception var6) {
         this.logAndSendError(this.response, var6.getHttpStatusCode(), var6);
      }

   }

   private IndexedEndpoint getACSEndpoint() throws SAML2Exception {
      if (this.targetEndpoint != null) {
         return this.targetEndpoint;
      } else {
         IndexedEndpoint[] acs = this.partner.getAssertionConsumerService();
         if (acs != null && acs.length != 0) {
            int var4;
            if (this.wrappedReq != null) {
               Integer index = this.wrappedReq.getAssertionConsumerServiceIndex();
               if (index != null) {
                  IndexedEndpoint[] var3 = acs;
                  var4 = acs.length;

                  for(int var5 = 0; var5 < var4; ++var5) {
                     IndexedEndpoint ep = var3[var5];
                     if (ep.getIndex() == index) {
                        this.targetEndpoint = ep;
                        return ep;
                     }
                  }
               }

               String binding = convertSAMLBinding(this.wrappedReq.getProtocolBinding());
               String url = this.wrappedReq.getAssertionConsumerServiceURL();
               if (binding != null && url != null) {
                  this.targetEndpoint = new IndexedEndpointImpl();
                  this.targetEndpoint.setLocation(url);
                  this.targetEndpoint.setBinding(binding);
                  return this.targetEndpoint;
               }
            }

            IndexedEndpoint[] var7 = acs;
            int var9 = acs.length;

            for(var4 = 0; var4 < var9; ++var4) {
               IndexedEndpoint ep = var7[var4];
               if (ep.isDefaultSet() && ep.isDefault()) {
                  this.targetEndpoint = ep;
                  return ep;
               }
            }

            this.targetEndpoint = acs[0];
            return this.targetEndpoint;
         } else {
            throw new SAML2Exception(Saml2Logger.getNoACSServiceInPartner(this.partner.getName()), 404);
         }
      }
   }

   private boolean isUserAuthenticated() {
      return this.getCurrentIdentity() != null;
   }

   private Identity getCurrentIdentity() {
      Thread t = Thread.currentThread();
      ClassLoader tcl = t.getContextClassLoader();

      Identity var4;
      try {
         if (tcl instanceof SAML2ClassLoader) {
            t.setContextClassLoader(((SAML2ClassLoader)tcl).getThreadConextClassLoader());
         }

         Identity id = this.config.getIdentityService().getCurrentIdentity();
         if (id == null || id.isAnonymous()) {
            var4 = null;
            return var4;
         }

         var4 = id;
      } finally {
         t.setContextClassLoader(tcl);
      }

      return var4;
   }

   private void logoutUser() {
      Thread t = Thread.currentThread();
      ClassLoader tcl = t.getContextClassLoader();

      try {
         if (tcl instanceof SAML2ClassLoader) {
            t.setContextClassLoader(((SAML2ClassLoader)tcl).getThreadConextClassLoader());
         }

         Identity id = this.config.getIdentityService().getCurrentIdentity();
         if (id != null && !id.isAnonymous()) {
            LoginSession session = this.config.getSessionService().getSession(id);
            if (session != null) {
               this.config.getSessionService().logout(session);
            }
         }
      } finally {
         t.setContextClassLoader(tcl);
      }

   }

   private static String convertSAMLBinding(String protocolBinding) {
      if ("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Artifact".equals(protocolBinding)) {
         return "HTTP/Artifact";
      } else if ("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect".equals(protocolBinding)) {
         return "HTTP/Redirect";
      } else if ("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST".equals(protocolBinding)) {
         return "HTTP/POST";
      } else {
         return "urn:oasis:names:tc:SAML:2.0:bindings:SOAP".equals(protocolBinding) ? "SOAP" : null;
      }
   }

   private class WrappedAuthnRequest {
      private AuthnRequest request;
      private AuthnRequestWrapper wrapper;

      WrappedAuthnRequest(AuthnRequestWrapper wrapper) {
         this.request = null;
         this.wrapper = wrapper;
      }

      WrappedAuthnRequest(AuthnRequest request) {
         String ID = null;
         String protocolBinding = null;
         String assertionConsumerServiceURL = null;
         Integer assertionConsumerServiceIndex = null;
         String issuer = null;
         boolean forceAuth = false;
         boolean passive = false;
         this.request = request;
         if (request != null) {
            ID = request.getID();
            protocolBinding = request.getProtocolBinding();
            assertionConsumerServiceURL = request.getAssertionConsumerServiceURL();
            assertionConsumerServiceIndex = request.getAssertionConsumerServiceIndex();
            if (request.getIssuer() != null) {
               issuer = request.getIssuer().getValue();
            }

            if (request.isForceAuthn() != null) {
               forceAuth = request.isForceAuthn();
            }

            if (request.isPassive() != null) {
               passive = request.isPassive();
            }
         }

         this.wrapper = new AuthnRequestWrapper(ID, protocolBinding, assertionConsumerServiceURL, assertionConsumerServiceIndex, issuer, forceAuth, passive);
      }

      public AuthnRequest getAuthnRequest() {
         return this.request;
      }

      public AuthnRequestWrapper getAuthnRequestWrapper() {
         return this.wrapper;
      }

      public String getIssuer() throws SAML2Exception {
         String value = this.wrapper.getIssuer();
         if (value == null) {
            throw new SAML2Exception(Saml2Logger.getInvalidIssuer("<samlp:AuthnRequest>", (String)null));
         } else {
            return value;
         }
      }

      public boolean isForceAuthn() {
         return this.wrapper.isForceAuthn();
      }

      public boolean isPassive() {
         return this.wrapper.isPassive();
      }

      public String getID() {
         return this.wrapper.getID();
      }

      public String getProtocolBinding() {
         return this.wrapper.getProtocolBinding();
      }

      public String getAssertionConsumerServiceURL() {
         return this.wrapper.getAssertionConsumerServiceURL();
      }

      public Integer getAssertionConsumerServiceIndex() {
         return this.wrapper.getAssertionConsumerServiceIndex();
      }
   }
}
