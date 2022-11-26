package com.bea.security.saml2.service.acs;

import com.bea.common.security.saml.utils.SAMLUtil;
import com.bea.common.security.saml2.SingleSignOnServicesConfigSpi;
import com.bea.common.security.saml2.utils.SAMLContextHandler;
import com.bea.common.security.service.Identity;
import com.bea.common.security.service.IdentityAssertionService;
import com.bea.common.security.service.LoginSession;
import com.bea.common.security.utils.SAML2ClassLoader;
import com.bea.security.saml2.Saml2Logger;
import com.bea.security.saml2.binding.BindingReceiver;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import com.bea.security.saml2.providers.registry.WebSSOIdPPartner;
import com.bea.security.saml2.service.AbstractService;
import com.bea.security.saml2.service.SAML2Exception;
import com.bea.security.saml2.util.SAML2Utils;
import com.bea.security.saml2.util.cache.SAML2Cache;
import com.bea.security.saml2.util.cache.SAML2CacheException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.SAMLException;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.core.SubjectConfirmation;
import org.opensaml.saml.saml2.core.SubjectConfirmationData;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.encryption.support.DecryptionException;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.SignatureException;
import weblogic.security.providers.utils.XSSSanitizer;
import weblogic.security.service.ContextElement;
import weblogic.utils.StringUtils;

public class AssertionConsumerServiceImpl extends AbstractService {
   private SAML2Cache authnReqCache = null;

   public AssertionConsumerServiceImpl(SAML2ConfigSpi config) {
      super(config);
   }

   public boolean process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      if (this.log.isDebugEnabled()) {
         this.log.debug("Assertion consumer service: processing");
      }

      BindingReceiver receiver = null;
      Response assertRes = null;

      try {
         if (!this.config.getLocalConfiguration().isServiceProviderEnabled()) {
            if (this.log.isDebugEnabled()) {
               this.log.debug("Service provider is not enabled.");
            }

            return false;
         } else {
            String bindingType = this.getBindingTypeFromURI(request, response);
            SAML2Utils.checkBindingType(bindingType, this.config, false);
            receiver = this.config.getBindingHandlerFactory().newBindingReceiver(bindingType, request, response);
            assertRes = (Response)receiver.receiveResponse();
            String statusCode = assertRes.getStatus().getStatusCode().getValue();
            if (!statusCode.equals("urn:oasis:names:tc:SAML:2.0:status:Success")) {
               throw new SAML2Exception(Saml2Logger.getIllegalResponseCode(statusCode), 403);
            } else {
               this.verifyAttrAndEle(assertRes, request);
               WebSSOIdPPartner idp = (WebSSOIdPPartner)this.config.getPartnerManager().findIdentityProviderByIssuerURI(assertRes.getIssuer().getValue(), "WEBSSOIDPPARTNER");
               if (idp == null) {
                  throw new SAML2Exception(Saml2Logger.getNoIdPForIssuerURI(assertRes.getIssuer().getValue()), 404);
               } else if (!idp.isEnabled()) {
                  throw new SAML2Exception(Saml2Logger.getIdPNotEnabled(idp.getName()), 404);
               } else {
                  assertRes.getDOM().setIdAttributeNS((String)null, "ID", true);
                  Signature sig = assertRes.getSignature();
                  if (sig != null) {
                     if (this.log.isDebugEnabled()) {
                        this.log.debug("<samlp:Response> is signed.");
                     }

                     PublicKey verifyKey = SAML2Utils.getVerifyKey(idp);
                     SAML2Utils.verifySamlObjectSignature(verifyKey, sig);
                  }

                  HashMap data = new HashMap();
                  Identity id = this.assertIdentity(assertRes, idp, request, data);
                  LoginSession session = null;
                  Thread t = Thread.currentThread();
                  ClassLoader tcl = t.getContextClassLoader();

                  try {
                     if (tcl instanceof SAML2ClassLoader) {
                        t.setContextClassLoader(((SAML2ClassLoader)tcl).getThreadConextClassLoader());
                     }

                     if (data.isEmpty()) {
                        session = this.config.getSessionService().create(id, new Date(), request);
                     } else {
                        HashMap context = new HashMap();
                        context.put("HttpServletRequest", request);
                        context.put("AssociatedData", data);
                        session = this.config.getSessionService().create(id, new Date(), context);
                     }
                  } finally {
                     t.setContextClassLoader(tcl);
                  }

                  if (session == null) {
                     throw new SAML2Exception(Saml2Logger.getCreateSessionError(id.toString()), 403);
                  } else {
                     String url = this.getRedirectURL(assertRes, receiver.getRelayState(), this.config.getLocalConfiguration().getDefaultURL());
                     String redirectURL = SAML2Utils.ENABLE_URL_REWRITING ? response.encodeRedirectURL(url) : url;
                     String validURL = XSSSanitizer.getValidInput(redirectURL);
                     if (this.log.isDebugEnabled()) {
                        this.log.debug("validURL: " + validURL);
                     }

                     response.sendRedirect(validURL);
                     return true;
                  }
               }
            }
         }
      } catch (SAML2Exception var25) {
         return this.logAndSendError(response, var25.getHttpStatusCode(), var25);
      } catch (SignatureException var26) {
         return this.logAndSendError(response, 403, var26);
      } catch (CertificateException var27) {
         return this.logAndSendError(response, 403, var27);
      } catch (KeyException var28) {
         return this.logAndSendError(response, 403, var28);
      } catch (LoginException var29) {
         return this.logAndSendError(response, 403, var29);
      } catch (Exception var30) {
         return this.logAndSendError(response, 500, var30);
      }
   }

   private String getRedirectURL(Response resp, String relayState, String defaultURL) throws SAML2Exception, MalformedURLException {
      String inResponseTo = resp.getInResponseTo();
      String redirectURL = null;
      if (inResponseTo != null) {
         try {
            redirectURL = (String)this.authnReqCache.remove(resp.getInResponseTo());
         } catch (SAML2CacheException var7) {
            throw new SAML2Exception(var7);
         }

         if (this.log.isDebugEnabled()) {
            this.log.debug("Using redirect URL from request cache: '" + redirectURL + "'");
         }
      } else if (relayState != null) {
         redirectURL = relayState;
         String[] allowedTargetHosts = this.config.getLocalConfiguration().getAllowedTargetHosts();
         if (allowedTargetHosts != null && allowedTargetHosts.length > 0 && (relayState.startsWith("http://") || relayState.startsWith("https://")) && !SAMLUtil.isTargetRedirectHostAllowed(relayState, allowedTargetHosts)) {
            throw new SAML2Exception(Saml2Logger.getTargetRedirectHostNotAllowed(relayState, StringUtils.join(allowedTargetHosts, ",")));
         }

         if (this.log.isDebugEnabled()) {
            this.log.debug("Using redirect URL from RelayState: '" + relayState + "'");
         }
      }

      if (!isValidRedirectURL(redirectURL)) {
         if (this.log.isDebugEnabled()) {
            this.log.debug("Null or invalid redirect URL, defaulting");
         }

         if (isValidRedirectURL(defaultURL)) {
            redirectURL = defaultURL;
            if (this.log.isDebugEnabled()) {
               this.log.debug("Default URL is: '" + defaultURL + "'");
            }
         } else {
            redirectURL = "/";
            if (this.log.isDebugEnabled()) {
               this.log.debug("Invalid default URL, using '/'");
            }
         }
      }

      if (this.log.isDebugEnabled()) {
         this.log.debug("Redirecting to URL: " + redirectURL);
      }

      return redirectURL;
   }

   private static boolean isValidRedirectURL(String url) {
      return url != null && (url.startsWith("/") || url.startsWith("http://") || url.startsWith("https://"));
   }

   private Identity assertIdentity(Response assertRes, WebSSOIdPPartner idp, HttpServletRequest request, HashMap data) throws LoginException, MarshallingException, UnmarshallingException, DecryptionException {
      List encryptedAssertions = assertRes.getEncryptedAssertions();
      Assertion token = null;
      if (encryptedAssertions != null && encryptedAssertions.size() > 0) {
         PrivateKey privateKey = this.config.getSAML2KeyManager().getAssertionEncryptionDecryptionKeyInfo().getKey();
         if (privateKey == null) {
            throw new DecryptionException("Assertion cannot be decrypted with a null private key.");
         }

         Credential credential = new SAML2Utils.SAMLPrivateKeyCredential(privateKey);
         token = SAML2Utils.decryptAssertion((Response)assertRes, credential, this.config.getLocalConfiguration().getEntityID());
         if (this.log.isDebugEnabled()) {
            this.log.debug("SAML assertion decrypted.");
         }
      } else {
         List assertions = assertRes.getAssertions();
         if (assertions != null && assertions.size() > 0) {
            token = (Assertion)assertions.get(0);
         }
      }

      IdentityAssertionService iaService = this.config.getIdentityAssertionService();
      SAMLContextHandler context = new SAMLContextHandler();
      SingleSignOnServicesConfigSpi ssoServicesConfigSpi = this.config.getLocalConfiguration();
      context.addElement(new ContextElement("com.bea.contextelement.saml2.EntityID", ssoServicesConfigSpi.getEntityID()));
      context.addElement(new ContextElement("com.bea.contextelement.saml2.PartnerName", idp.getName()));
      context.addElement(new ContextElement("com.bea.contextelement.saml.AttributePrincipals", new ArrayList()));
      Boolean oneUsePolicyApplies = request.getRequestURI().endsWith("/post") && ssoServicesConfigSpi.isPOSTOneUseCheckEnabled() ? Boolean.TRUE : Boolean.FALSE;
      context.addElement(new ContextElement("com.bea.contextelement.saml2.OneUsePolicyApplies", oneUsePolicyApplies));
      if (ssoServicesConfigSpi.isWantAssertionsSigned()) {
         context.addElement(new ContextElement("com.bea.contextelement.saml2.WantAssertionSigned", Boolean.TRUE));
      }

      context.addElement(new ContextElement("com.bea.contextelement.saml2.websso.data", data));
      return iaService.assertIdentity("SAML2.Assertion.DOM", token.getDOM(), context);
   }

   private void verifyAttrAndEle(Response response, HttpServletRequest request) throws SAMLException {
      (new ResponseValidator(request)).validate(response);
   }

   public void setAuthnReqCache(SAML2Cache cache) {
      this.authnReqCache = cache;
   }

   private class ResponseValidator {
      private HttpServletRequest request = null;

      public ResponseValidator(HttpServletRequest request) {
         this.request = request;
      }

      public void validate(Response response) throws SAMLException {
         if (response.getStatus() == null) {
            throw new SAMLException("Response Status is required.");
         } else if (StringUtils.isEmptyString(response.getID())) {
            throw new SAMLException("Response ID must not be empty.");
         } else if (response.getVersion() == null) {
            throw new SAMLException("Response version must not be null.");
         } else if (!SAMLVersion.VERSION_20.toString().equals(response.getVersion().toString())) {
            throw new SAMLException("Wrong SAML Version: " + response.getVersion());
         } else if (response.getIssueInstant() == null) {
            throw new SAMLException("IssueInstant attribute must not be null.");
         } else {
            this.validateDestination(response);
            this.validateInResponseTo(response);
            this.validateIssuer(response);
            this.ValidateAssertions(response);
         }
      }

      protected void validateDestination(Response response) throws SAMLException {
         if (AssertionConsumerServiceImpl.this.config.getLocalConfiguration().isRecipientCheckEnabled()) {
            String localSite = SAML2Utils.getLocalSiteFromPublishedURL(AssertionConsumerServiceImpl.this.config.getLocalConfiguration().getPublishedSiteURL());
            String dest = localSite + this.request.getRequestURI();
            if (StringUtils.isEmptyString(response.getDestination()) || !response.getDestination().equals(dest)) {
               throw new SAMLException(Saml2Logger.getDestinationNotMatch(response.getDestination(), dest));
            }
         }
      }

      protected void validateConsent(Response response) throws SAMLException {
      }

      protected void validateInResponseTo(Response response) throws SAMLException {
         if (!StringUtils.isEmptyString(response.getInResponseTo())) {
            try {
               if (AssertionConsumerServiceImpl.this.authnReqCache.get(response.getInResponseTo()) == null) {
                  throw new SAMLException(Saml2Logger.getNoRequestFound(response.getInResponseTo()));
               }
            } catch (SAML2CacheException var3) {
               throw new SAMLException(var3);
            }
         }
      }

      protected void validateIssuer(Response response) throws SAMLException {
         Issuer issuer = response.getIssuer();
         if (issuer == null) {
            throw new SAMLException(Saml2Logger.getEmptyAttribute("Response:Issuer"));
         } else if (StringUtils.isEmptyString(issuer.getValue())) {
            throw new SAMLException("Issuer name is required");
         }
      }

      protected void ValidateAssertions(Response response) throws SAMLException {
         List assertions = response.getAssertions();

         for(int i = 0; i < assertions.size(); ++i) {
            this.validateSubjectConfirmationData((Assertion)assertions.get(i), response);
         }

      }

      private void validateSubjectConfirmationData(Assertion assertion, Response response) throws SAMLException {
         if (assertion.getSubject() == null) {
            throw new SAMLException(Saml2Logger.getEmptyAttribute("Assertion:Subject"));
         } else {
            List confirmations = assertion.getSubject().getSubjectConfirmations();
            if (confirmations != null && confirmations.size() != 0) {
               for(int i = 0; i < confirmations.size(); ++i) {
                  SubjectConfirmation confirmation = (SubjectConfirmation)confirmations.get(i);
                  if (!"urn:oasis:names:tc:SAML:2.0:cm:bearer".equals(confirmation.getMethod())) {
                     throw new SAMLException(Saml2Logger.getIllegalConfirmationMethod(confirmation.getMethod(), "urn:oasis:names:tc:SAML:2.0:cm:bearer"));
                  }

                  this.validateConfirmationData(confirmation.getSubjectConfirmationData(), response);
               }

            } else {
               throw new SAMLException(Saml2Logger.getEmptyAttribute("Subject:SubjectConfirmations"));
            }
         }
      }

      private void validateConfirmationData(SubjectConfirmationData data, Response response) throws SAMLException {
         if (!AssertionConsumerServiceImpl.this.config.getLocalConfiguration().isRecipientCheckEnabled() || data != null && !StringUtils.isEmptyString(data.getRecipient()) && data.getRecipient().equals(response.getDestination())) {
            if (data != null && !StringUtils.isEmptyString(data.getInResponseTo())) {
               if (!data.getInResponseTo().equals(response.getInResponseTo())) {
                  throw new SAMLException(Saml2Logger.getIllegalInResponseTo(data.getInResponseTo(), response.getInResponseTo()));
               }
            }
         } else {
            throw new SAMLException(Saml2Logger.getIllegalRecipient(data != null ? data.getRecipient() : "", response.getDestination()));
         }
      }
   }
}
