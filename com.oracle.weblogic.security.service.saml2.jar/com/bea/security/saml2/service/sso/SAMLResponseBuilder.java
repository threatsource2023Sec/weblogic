package com.bea.security.saml2.service.sso;

import com.bea.security.saml2.Saml2Logger;
import com.bea.security.saml2.service.SAML2DetailedException;
import com.bea.security.saml2.util.SAML2Utils;
import com.bea.security.saml2.util.SAMLObjectBuilder;
import java.security.PrivateKey;
import java.util.List;
import org.joda.time.DateTime;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.EncryptedAssertion;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.core.StatusCode;
import org.opensaml.saml.saml2.core.StatusDetail;
import org.opensaml.saml.saml2.core.StatusMessage;
import org.opensaml.xmlsec.signature.support.SignatureException;

class SAMLResponseBuilder {
   public Response buildResponse(Assertion assertion, EncryptedAssertion encryptedAssertion, String bindingLocation, String requestId, String issuerURI, PrivateKey key, List certificate) throws SAML2DetailedException {
      Response resp = this.buildResponseSkeleton(bindingLocation, requestId, issuerURI);
      if (assertion != null) {
         resp.getAssertions().add(assertion);
      }

      if (encryptedAssertion != null) {
         resp.getEncryptedAssertions().add(encryptedAssertion);
      }

      resp.setStatus(SAMLObjectBuilder.buildSuccessStatus());
      return this.sign(resp, key, certificate);
   }

   public Response buildErrorResponse(String bindingLocation, String requestId, String issuerURI, PrivateKey key, List certificate, StatusCode code, String message) throws SAML2DetailedException {
      Response resp = this.buildResponseSkeleton(bindingLocation, requestId, issuerURI);
      StatusMessage msg = null;
      if (message != null) {
         msg = SAMLObjectBuilder.buildStatusMessage(message);
      }

      resp.setStatus(SAMLObjectBuilder.buildStatus((StatusCode)code, (StatusMessage)msg, (StatusDetail)null));
      return this.sign(resp, key, certificate);
   }

   private Response buildResponseSkeleton(String bindingLocation, String requestId, String issuerURI) {
      Response resp = (Response)XMLObjectSupport.buildXMLObject(Response.DEFAULT_ELEMENT_NAME);
      resp.setDestination(bindingLocation);
      resp.setID(SAML2Utils.getXMLSafeSecureUUID());
      if (requestId != null) {
         resp.setInResponseTo(requestId);
      }

      resp.setIssuer(SAMLObjectBuilder.buildIssuer(issuerURI));
      resp.setIssueInstant(new DateTime());
      resp.setVersion(SAMLVersion.VERSION_20);
      return resp;
   }

   private Response sign(Response resp, PrivateKey key, List certificate) throws SAML2DetailedException {
      if (key == null) {
         return resp;
      } else {
         try {
            return (Response)SAML2Utils.signSamlObject(key, resp, certificate);
         } catch (SignatureException | MarshallingException var5) {
            throw (new SAML2DetailedException(Saml2Logger.getSAML2SigningErrors("<samlp:Response>"), var5, 500)).setStatusCode("urn:oasis:names:tc:SAML:2.0:status:Responder", (String)null);
         }
      }
   }
}
