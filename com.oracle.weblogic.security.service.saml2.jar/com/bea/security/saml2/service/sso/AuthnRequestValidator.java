package com.bea.security.saml2.service.sso;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.security.saml2.Saml2Logger;
import com.bea.security.saml2.binding.BindingReceiver;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import com.bea.security.saml2.providers.registry.WebSSOSPPartner;
import com.bea.security.saml2.service.SAML2DetailedException;
import com.bea.security.saml2.util.SAML2Utils;
import java.security.KeyException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.NameIDPolicy;
import org.opensaml.saml.saml2.core.Subject;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.SignatureException;

class AuthnRequestValidator {
   private SAML2ConfigSpi config;
   private LoggerSpi logger;

   public AuthnRequestValidator(SAML2ConfigSpi config) {
      this.config = config;
      this.logger = config.getLogger();
   }

   void verifySignature(WebSSOSPPartner sp, BindingReceiver receiver, AuthnRequest authnReq) throws SAML2DetailedException {
      if (authnReq == null) {
         throw new IllegalArgumentException("authnReq is null");
      } else {
         authnReq.getDOM().setIdAttributeNS((String)null, "ID", true);
         Signature sig = authnReq.getSignature();
         boolean needVerify = sp.isWantAuthnRequestsSigned() || this.config.getLocalConfiguration().isWantAuthnRequestsSigned() || sig != null;
         if (needVerify) {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("Verifying the signature of <samlp:AuthnRequest> message.");
            }

            try {
               PublicKey verifyKey = SAML2Utils.getVerifyKey(sp);
               if (!receiver.verifySignature(verifyKey)) {
                  SAML2Utils.verifySamlObjectSignature(verifyKey, sig);
               }
            } catch (SignatureException var7) {
               this.logger.error(var7.getMessage(), var7);
               throw (new SAML2DetailedException(Saml2Logger.getSAML2VerifySignatureFail(), 403)).setStatusCode("urn:oasis:names:tc:SAML:2.0:status:Requester");
            } catch (CertificateException var8) {
               this.logger.error(var8.getMessage(), var8);
               throw new SAML2DetailedException(Saml2Logger.getNoVerifyingCert("<samlp:AuthnRequest>", sp.getName()), 403);
            } catch (KeyException var9) {
               this.logger.error(var9.getMessage(), var9);
               throw new SAML2DetailedException(Saml2Logger.getSAML2NoVerifyKeyFor("<samlp:AuthnRequest>"), 403);
            }
         }

      }
   }

   public void validate(WebSSOSPPartner sp, AuthnRequest req, String requestURI) throws SAML2DetailedException {
      if (!SAMLVersion.VERSION_20.toString().equals(req.getVersion().toString())) {
         throw (new SAML2DetailedException(Saml2Logger.getInvalidVersion("<samlp:AuthnRequest>", req.getVersion().toString()), 404)).setStatusCode("urn:oasis:names:tc:SAML:2.0:status:VersionMismatch");
      } else {
         if (this.config.getLocalConfiguration().isRecipientCheckEnabled()) {
            String localSite = SAML2Utils.getLocalSiteFromPublishedURL(this.config.getLocalConfiguration().getPublishedSiteURL());
            String dest = localSite + requestURI;
            if (req.getDestination() == null || !req.getDestination().equals(dest)) {
               throw (new SAML2DetailedException(Saml2Logger.getAuthnRequestDestinationNotMatch(dest, req.getDestination()), 404)).setStatusCode("urn:oasis:names:tc:SAML:2.0:status:Requester");
            }
         }

         Issuer issuer = req.getIssuer();
         if (issuer.getFormat() != null && !issuer.getFormat().equals("urn:oasis:names:tc:SAML:2.0:nameid-format:entity")) {
            throw (new SAML2DetailedException(Saml2Logger.getInvalidIssuerFormat(issuer.getFormat()), 404)).setStatusCode("urn:oasis:names:tc:SAML:2.0:status:Requester");
         } else if (issuer.getNameQualifier() == null && issuer.getSPNameQualifier() == null && issuer.getSPProvidedID() == null) {
            Subject subject = req.getSubject();
            if (subject != null && subject.getSubjectConfirmations() != null) {
               throw (new SAML2DetailedException(Saml2Logger.getSubjectConfirmationMustNotExist(), 404)).setStatusCode("urn:oasis:names:tc:SAML:2.0:status:Requester");
            } else {
               NameIDPolicy policy = req.getNameIDPolicy();
               if (policy != null) {
                  if (policy.getFormat() != null && !"urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified".equals(policy.getFormat())) {
                     throw (new SAML2DetailedException(Saml2Logger.getOnlySupportUnspecifiedNamedId(), 404)).setStatusCode("urn:oasis:names:tc:SAML:2.0:status:Requester", "urn:oasis:names:tc:SAML:2.0:status:InvalidNameIDPolicy");
                  }

                  if (policy.getSPNameQualifier() != null) {
                     throw (new SAML2DetailedException(Saml2Logger.getSPNameQualifierNotSupported(), 404)).setStatusCode("urn:oasis:names:tc:SAML:2.0:status:Requester", "urn:oasis:names:tc:SAML:2.0:status:InvalidNameIDPolicy");
                  }
               }

               if (req.getRequestedAuthnContext() != null) {
                  throw (new SAML2DetailedException(Saml2Logger.getRequestedAuthnContextNotSupported(), 404)).setStatusCode("urn:oasis:names:tc:SAML:2.0:status:Requester", "urn:oasis:names:tc:SAML:2.0:status:InvalidNameIDPolicy");
               }
            }
         } else {
            throw (new SAML2DetailedException(Saml2Logger.getInvalidQualifiersInIssuer(), 404)).setStatusCode("urn:oasis:names:tc:SAML:2.0:status:Requester");
         }
      }
   }
}
