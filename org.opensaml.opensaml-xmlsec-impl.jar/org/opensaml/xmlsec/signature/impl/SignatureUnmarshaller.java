package org.opensaml.xmlsec.signature.impl;

import java.util.List;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.apache.xml.security.Init;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.KeyInfo;
import org.apache.xml.security.signature.SignedInfo;
import org.apache.xml.security.signature.XMLSignature;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Unmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.signature.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class SignatureUnmarshaller implements Unmarshaller {
   private final Logger log = LoggerFactory.getLogger(SignatureUnmarshaller.class);

   public SignatureUnmarshaller() {
      if (!Init.isInitialized()) {
         this.log.debug("Initializing XML security library");
         Init.init();
      }

   }

   public Signature unmarshall(Element signatureElement) throws UnmarshallingException {
      this.log.debug("Starting to unmarshall Apache XML-Security-based SignatureImpl element");
      SignatureImpl signature = new SignatureImpl(signatureElement.getNamespaceURI(), signatureElement.getLocalName(), signatureElement.getPrefix());

      try {
         this.log.debug("Constructing Apache XMLSignature object");
         XMLSignature xmlSignature = new XMLSignature(signatureElement, "");
         SignedInfo signedInfo = xmlSignature.getSignedInfo();
         this.log.debug("Adding canonicalization and signing algorithms, and HMAC output length to Signature");
         signature.setCanonicalizationAlgorithm(signedInfo.getCanonicalizationMethodURI());
         signature.setSignatureAlgorithm(signedInfo.getSignatureMethodURI());
         signature.setHMACOutputLength(this.getHMACOutputLengthValue(signedInfo.getSignatureMethodElement()));
         KeyInfo xmlSecKeyInfo = xmlSignature.getKeyInfo();
         if (xmlSecKeyInfo != null) {
            this.log.debug("Adding KeyInfo to Signature");
            Unmarshaller unmarshaller = XMLObjectProviderRegistrySupport.getUnmarshallerFactory().getUnmarshaller(xmlSecKeyInfo.getElement());
            org.opensaml.xmlsec.signature.KeyInfo keyInfo = (org.opensaml.xmlsec.signature.KeyInfo)unmarshaller.unmarshall(xmlSecKeyInfo.getElement());
            signature.setKeyInfo(keyInfo);
         }

         signature.setXMLSignature(xmlSignature);
         signature.setDOM(signatureElement);
         return signature;
      } catch (XMLSecurityException var8) {
         this.log.error("Error constructing Apache XMLSignature instance from Signature element: {}", var8.getMessage());
         throw new UnmarshallingException("Unable to unmarshall Signature with Apache XMLSignature", var8);
      }
   }

   private Integer getHMACOutputLengthValue(Element signatureMethodElement) {
      if (signatureMethodElement == null) {
         return null;
      } else {
         List children = ElementSupport.getChildElementsByTagNameNS(signatureMethodElement, "http://www.w3.org/2000/09/xmldsig#", "HMACOutputLength");
         if (!children.isEmpty()) {
            Element hmacElement = (Element)children.get(0);
            String value = StringSupport.trimOrNull(hmacElement.getTextContent());
            if (value != null) {
               return new Integer(value);
            }
         }

         return null;
      }
   }
}
