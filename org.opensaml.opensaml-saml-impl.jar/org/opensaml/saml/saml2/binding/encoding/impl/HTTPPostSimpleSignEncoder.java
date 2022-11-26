package org.opensaml.saml.saml2.binding.encoding.impl;

import com.google.common.base.Strings;
import java.io.UnsupportedEncodingException;
import net.shibboleth.utilities.java.support.codec.Base64Support;
import net.shibboleth.utilities.java.support.xml.SerializeSupport;
import org.apache.velocity.VelocityContext;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Marshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.encoder.MessageEncodingException;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.opensaml.saml.common.messaging.SAMLMessageSecuritySupport;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialSupport;
import org.opensaml.xmlsec.SignatureSigningParameters;
import org.opensaml.xmlsec.crypto.XMLSigningUtil;
import org.opensaml.xmlsec.keyinfo.KeyInfoGenerator;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTPPostSimpleSignEncoder extends HTTPPostEncoder {
   public static final String DEFAULT_TEMPLATE_ID = "/templates/saml2-post-simplesign-binding.vm";
   private final Logger log = LoggerFactory.getLogger(HTTPPostSimpleSignEncoder.class);

   public HTTPPostSimpleSignEncoder() {
      this.setVelocityTemplateId("/templates/saml2-post-simplesign-binding.vm");
   }

   public String getBindingURI() {
      return "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST-SimpleSign";
   }

   protected void populateVelocityContext(VelocityContext velocityContext, MessageContext messageContext, String endpointURL) throws MessageEncodingException {
      super.populateVelocityContext(velocityContext, messageContext, endpointURL);
      SignatureSigningParameters signingParameters = SAMLMessageSecuritySupport.getContextSigningParameters(messageContext);
      if (signingParameters != null && signingParameters.getSigningCredential() != null) {
         String sigAlgURI = this.getSignatureAlgorithmURI(signingParameters);
         velocityContext.put("SigAlg", sigAlgURI);
         String formControlData = this.buildFormDataToSign(velocityContext, messageContext, sigAlgURI);
         velocityContext.put("Signature", this.generateSignature(signingParameters.getSigningCredential(), sigAlgURI, formControlData));
         KeyInfoGenerator kiGenerator = signingParameters.getKeyInfoGenerator();
         if (kiGenerator != null) {
            String kiBase64 = this.buildKeyInfo(signingParameters.getSigningCredential(), kiGenerator);
            if (!Strings.isNullOrEmpty(kiBase64)) {
               velocityContext.put("KeyInfo", kiBase64);
            }
         }

      } else {
         this.log.debug("No signing credential was supplied, skipping HTTP-Post simple signing");
      }
   }

   protected String buildKeyInfo(Credential signingCredential, KeyInfoGenerator kiGenerator) throws MessageEncodingException {
      try {
         KeyInfo keyInfo = kiGenerator.generate(signingCredential);
         if (keyInfo != null) {
            Marshaller marshaller = XMLObjectProviderRegistrySupport.getMarshallerFactory().getMarshaller(keyInfo);
            if (marshaller == null) {
               this.log.error("No KeyInfo marshaller available from configuration");
               throw new MessageEncodingException("No KeyInfo marshaller was configured");
            } else {
               String kiXML = SerializeSupport.nodeToString(marshaller.marshall(keyInfo));
               String kiBase64 = Base64Support.encode(kiXML.getBytes(), false);
               return kiBase64;
            }
         } else {
            return null;
         }
      } catch (SecurityException var7) {
         this.log.error("Error generating KeyInfo from signing credential", var7);
         throw new MessageEncodingException("Error generating KeyInfo from signing credential", var7);
      } catch (MarshallingException var8) {
         this.log.error("Error marshalling KeyInfo based on signing credential", var8);
         throw new MessageEncodingException("Error marshalling KeyInfo based on signing credential", var8);
      }
   }

   protected String buildFormDataToSign(VelocityContext velocityContext, MessageContext messageContext, String sigAlgURI) {
      StringBuilder builder = new StringBuilder();
      boolean isRequest = false;
      if (velocityContext.get("SAMLRequest") != null) {
         isRequest = true;
      }

      String msgB64;
      if (isRequest) {
         msgB64 = (String)velocityContext.get("SAMLRequest");
      } else {
         msgB64 = (String)velocityContext.get("SAMLResponse");
      }

      String msg = null;

      try {
         msg = new String(Base64Support.decode(msgB64), "UTF-8");
      } catch (UnsupportedEncodingException var9) {
      }

      if (isRequest) {
         builder.append("SAMLRequest=" + msg);
      } else {
         builder.append("SAMLResponse=" + msg);
      }

      String relayState = SAMLBindingSupport.getRelayState(messageContext);
      if (relayState != null) {
         builder.append("&RelayState=" + relayState);
      }

      builder.append("&SigAlg=" + sigAlgURI);
      return builder.toString();
   }

   protected String getSignatureAlgorithmURI(SignatureSigningParameters signingParameters) throws MessageEncodingException {
      if (signingParameters.getSignatureAlgorithm() != null) {
         return signingParameters.getSignatureAlgorithm();
      } else {
         throw new MessageEncodingException("The signing algorithm URI could not be determined");
      }
   }

   protected String generateSignature(Credential signingCredential, String algorithmURI, String formData) throws MessageEncodingException {
      this.log.debug(String.format("Generating signature with key type '%s', algorithm URI '%s' over form control string '%s'", CredentialSupport.extractSigningKey(signingCredential).getAlgorithm(), algorithmURI, formData));
      String b64Signature = null;

      try {
         byte[] rawSignature = XMLSigningUtil.signWithURI(signingCredential, algorithmURI, formData.getBytes("UTF-8"));
         b64Signature = Base64Support.encode(rawSignature, false);
         this.log.debug("Generated digital signature value (base64-encoded) {}", b64Signature);
      } catch (SecurityException var6) {
         this.log.error("Error during URL signing process", var6);
         throw new MessageEncodingException("Unable to sign form control string", var6);
      } catch (UnsupportedEncodingException var7) {
      }

      return b64Signature;
   }
}
