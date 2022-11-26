package org.opensaml.xmlsec.signature.impl;

import java.util.Iterator;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.apache.xml.security.Init;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.signature.XMLSignature;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Marshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.algorithm.AlgorithmSupport;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.ContentReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SignatureMarshaller implements Marshaller {
   private final Logger log = LoggerFactory.getLogger(SignatureMarshaller.class);

   public SignatureMarshaller() {
      if (!Init.isInitialized()) {
         this.log.debug("Initializing XML security library");
         Init.init();
      }

   }

   public Element marshall(XMLObject xmlObject) throws MarshallingException {
      try {
         Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
         return this.marshall(xmlObject, document);
      } catch (ParserConfigurationException var3) {
         throw new MarshallingException("Unable to create Document to place marshalled elements in", var3);
      }
   }

   public Element marshall(XMLObject xmlObject, Element parentElement) throws MarshallingException {
      Element signatureElement = this.createSignatureElement((SignatureImpl)xmlObject, parentElement.getOwnerDocument());
      ElementSupport.appendChildElement(parentElement, signatureElement);
      return signatureElement;
   }

   public Element marshall(XMLObject xmlObject, Document document) throws MarshallingException {
      Element signatureElement = this.createSignatureElement((SignatureImpl)xmlObject, document);
      Element documentRoot = document.getDocumentElement();
      if (documentRoot != null) {
         document.replaceChild(signatureElement, documentRoot);
      } else {
         document.appendChild(signatureElement);
      }

      return signatureElement;
   }

   private Element createSignatureElement(Signature signature, Document document) throws MarshallingException {
      this.log.debug("Starting to marshall {}", signature.getElementQName());

      try {
         this.log.debug("Creating XMLSignature object");
         XMLSignature dsig = null;
         if (signature.getHMACOutputLength() != null && AlgorithmSupport.isHMAC(signature.getSignatureAlgorithm())) {
            dsig = new XMLSignature(document, "", signature.getSignatureAlgorithm(), signature.getHMACOutputLength(), signature.getCanonicalizationAlgorithm());
         } else {
            dsig = new XMLSignature(document, "", signature.getSignatureAlgorithm(), signature.getCanonicalizationAlgorithm());
         }

         this.log.debug("Adding content to XMLSignature.");
         Iterator var7 = signature.getContentReferences().iterator();

         while(var7.hasNext()) {
            ContentReference contentReference = (ContentReference)var7.next();
            contentReference.createReference(dsig);
         }

         this.log.debug("Creating Signature DOM element");
         Element signatureElement = dsig.getElement();
         if (signature.getKeyInfo() != null) {
            Marshaller keyInfoMarshaller = XMLObjectProviderRegistrySupport.getMarshallerFactory().getMarshaller(KeyInfo.DEFAULT_ELEMENT_NAME);
            keyInfoMarshaller.marshall(signature.getKeyInfo(), signatureElement);
         }

         ((SignatureImpl)signature).setXMLSignature(dsig);
         signature.setDOM(signatureElement);
         signature.releaseParentDOM(true);
         return signatureElement;
      } catch (XMLSecurityException var6) {
         String msg = "Unable to construct signature Element " + signature.getElementQName();
         this.log.error(msg, var6);
         throw new MarshallingException(msg, var6);
      }
   }
}
