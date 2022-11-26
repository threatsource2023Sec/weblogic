package org.opensaml.saml.saml2.binding.encoding.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.shibboleth.utilities.java.support.codec.Base64Support;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.net.HttpServletSupport;
import net.shibboleth.utilities.java.support.net.URLBuilder;
import net.shibboleth.utilities.java.support.xml.SerializeSupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.encoder.MessageEncodingException;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.SignableSAMLObject;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.opensaml.saml.common.messaging.SAMLMessageSecuritySupport;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.core.StatusResponseType;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialSupport;
import org.opensaml.xmlsec.SignatureSigningParameters;
import org.opensaml.xmlsec.crypto.XMLSigningUtil;
import org.opensaml.xmlsec.signature.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTPRedirectDeflateEncoder extends BaseSAML2MessageEncoder {
   private final Logger log = LoggerFactory.getLogger(HTTPRedirectDeflateEncoder.class);

   public String getBindingURI() {
      return "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect";
   }

   protected void doEncode() throws MessageEncodingException {
      MessageContext messageContext = this.getMessageContext();
      SAMLObject outboundMessage = (SAMLObject)messageContext.getMessage();
      String endpointURL = this.getEndpointURL(messageContext).toString();
      this.removeSignature(outboundMessage);
      String encodedMessage = this.deflateAndBase64Encode(outboundMessage);
      String redirectURL = this.buildRedirectURL(messageContext, endpointURL, encodedMessage);
      HttpServletResponse response = this.getHttpServletResponse();
      HttpServletSupport.addNoCacheHeaders(response);
      HttpServletSupport.setUTF8Encoding(response);

      try {
         response.sendRedirect(redirectURL);
      } catch (IOException var8) {
         throw new MessageEncodingException("Problem sending HTTP redirect", var8);
      }
   }

   protected void removeSignature(SAMLObject message) {
      if (message instanceof SignableSAMLObject) {
         SignableSAMLObject signableMessage = (SignableSAMLObject)message;
         if (signableMessage.isSigned()) {
            this.log.debug("Removing SAML protocol message signature");
            signableMessage.setSignature((Signature)null);
         }
      }

   }

   protected String deflateAndBase64Encode(SAMLObject message) throws MessageEncodingException {
      this.log.debug("Deflating and Base64 encoding SAML message");

      try {
         String messageStr = SerializeSupport.nodeToString(this.marshallMessage(message));
         ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
         Deflater deflater = new Deflater(8, true);
         DeflaterOutputStream deflaterStream = new DeflaterOutputStream(bytesOut, deflater);
         deflaterStream.write(messageStr.getBytes("UTF-8"));
         deflaterStream.finish();
         return Base64Support.encode(bytesOut.toByteArray(), false);
      } catch (IOException var6) {
         throw new MessageEncodingException("Unable to DEFLATE and Base64 encode SAML message", var6);
      }
   }

   protected String buildRedirectURL(MessageContext messageContext, String endpoint, String message) throws MessageEncodingException {
      this.log.debug("Building URL to redirect client to");
      URLBuilder urlBuilder = null;

      try {
         urlBuilder = new URLBuilder(endpoint);
      } catch (MalformedURLException var12) {
         throw new MessageEncodingException("Endpoint URL " + endpoint + " is not a valid URL", var12);
      }

      List queryParams = urlBuilder.getQueryParams();
      queryParams.clear();
      SAMLObject outboundMessage = (SAMLObject)messageContext.getMessage();
      if (outboundMessage instanceof RequestAbstractType) {
         queryParams.add(new Pair("SAMLRequest", message));
      } else {
         if (!(outboundMessage instanceof StatusResponseType)) {
            throw new MessageEncodingException("SAML message is neither a SAML RequestAbstractType or StatusResponseType");
         }

         queryParams.add(new Pair("SAMLResponse", message));
      }

      String relayState = SAMLBindingSupport.getRelayState(messageContext);
      if (SAMLBindingSupport.checkRelayState(relayState)) {
         queryParams.add(new Pair("RelayState", relayState));
      }

      SignatureSigningParameters signingParameters = SAMLMessageSecuritySupport.getContextSigningParameters(messageContext);
      if (signingParameters != null && signingParameters.getSigningCredential() != null) {
         String sigAlgURI = this.getSignatureAlgorithmURI(signingParameters);
         Pair sigAlg = new Pair("SigAlg", sigAlgURI);
         queryParams.add(sigAlg);
         String sigMaterial = urlBuilder.buildQueryString();
         queryParams.add(new Pair("Signature", this.generateSignature(signingParameters.getSigningCredential(), sigAlgURI, sigMaterial)));
      } else {
         this.log.debug("No signing credential was supplied, skipping HTTP-Redirect DEFLATE signing");
      }

      return urlBuilder.buildURL();
   }

   protected String getSignatureAlgorithmURI(SignatureSigningParameters signingParameters) throws MessageEncodingException {
      if (signingParameters.getSignatureAlgorithm() != null) {
         return signingParameters.getSignatureAlgorithm();
      } else {
         throw new MessageEncodingException("The signing algorithm URI could not be determined");
      }
   }

   protected String generateSignature(Credential signingCredential, String algorithmURI, String queryString) throws MessageEncodingException {
      this.log.debug(String.format("Generating signature with key type '%s', algorithm URI '%s' over query string '%s'", CredentialSupport.extractSigningKey(signingCredential).getAlgorithm(), algorithmURI, queryString));
      String b64Signature = null;

      try {
         byte[] rawSignature = XMLSigningUtil.signWithURI(signingCredential, algorithmURI, queryString.getBytes("UTF-8"));
         b64Signature = Base64Support.encode(rawSignature, false);
         this.log.debug("Generated digital signature value (base64-encoded) {}", b64Signature);
      } catch (SecurityException var6) {
         this.log.error("Error during URL signing process", var6);
         throw new MessageEncodingException("Unable to sign URL query string", var6);
      } catch (UnsupportedEncodingException var7) {
      }

      return b64Signature;
   }
}
