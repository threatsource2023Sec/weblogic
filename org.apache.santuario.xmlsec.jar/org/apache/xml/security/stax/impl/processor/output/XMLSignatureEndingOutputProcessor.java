package org.apache.xml.security.stax.impl.processor.output;

import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.OutputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.XMLSecurityUtils;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;
import org.apache.xml.security.stax.impl.SignaturePartDef;
import org.apache.xml.security.stax.impl.algorithms.SignatureAlgorithm;
import org.apache.xml.security.stax.securityEvent.SignatureValueSecurityEvent;
import org.apache.xml.security.stax.securityToken.OutboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;

public class XMLSignatureEndingOutputProcessor extends AbstractSignatureEndingOutputProcessor {
   private AbstractSignatureEndingOutputProcessor.SignedInfoProcessor signedInfoProcessor;

   public XMLSignatureEndingOutputProcessor(XMLSignatureOutputProcessor signatureOutputProcessor) throws XMLSecurityException {
      super(signatureOutputProcessor);
      this.addAfterProcessor(XMLSignatureOutputProcessor.class.getName());
   }

   protected AbstractSignatureEndingOutputProcessor.SignedInfoProcessor newSignedInfoProcessor(SignatureAlgorithm signatureAlgorithm, String signatureId, XMLSecStartElement xmlSecStartElement, OutputProcessorChain outputProcessorChain) throws XMLSecurityException {
      this.signedInfoProcessor = new AbstractSignatureEndingOutputProcessor.SignedInfoProcessor(signatureAlgorithm, signatureId, xmlSecStartElement);
      this.signedInfoProcessor.setXMLSecurityProperties(this.getSecurityProperties());
      this.signedInfoProcessor.setAction(this.getAction());
      this.signedInfoProcessor.addAfterProcessor(XMLSignatureEndingOutputProcessor.class.getName());
      this.signedInfoProcessor.init(outputProcessorChain);
      return this.signedInfoProcessor;
   }

   public void processHeaderEvent(OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
      super.processHeaderEvent(outputProcessorChain);
      SignatureValueSecurityEvent signatureValueSecurityEvent = new SignatureValueSecurityEvent();
      signatureValueSecurityEvent.setSignatureValue(this.signedInfoProcessor.getSignatureValue());
      signatureValueSecurityEvent.setCorrelationID(this.signedInfoProcessor.getSignatureId());
      outputProcessorChain.getSecurityContext().registerSecurityEvent(signatureValueSecurityEvent);
   }

   protected void flushBufferAndCallbackAfterHeader(OutputProcessorChain outputProcessorChain, Deque xmlSecEventDeque) throws XMLStreamException, XMLSecurityException {
      XMLSecEvent xmlSecEvent;
      for(xmlSecEvent = (XMLSecEvent)xmlSecEventDeque.pop(); !xmlSecEvent.isStartElement(); xmlSecEvent = (XMLSecEvent)xmlSecEventDeque.pop()) {
         outputProcessorChain.reset();
         outputProcessorChain.processEvent(xmlSecEvent);
      }

      outputProcessorChain.reset();
      outputProcessorChain.processEvent(xmlSecEvent);
      int depth = 0;
      QName signaturePositionQName = this.getSecurityProperties().getSignaturePositionQName();
      boolean start = this.getSecurityProperties().isSignaturePositionStart();
      if (signaturePositionQName != null) {
         while(!xmlSecEventDeque.isEmpty() && (!start || !xmlSecEvent.isStartElement() || !xmlSecEvent.asStartElement().getName().equals(signaturePositionQName)) && (start || !xmlSecEvent.isEndElement() || !xmlSecEvent.asEndElement().getName().equals(signaturePositionQName))) {
            xmlSecEvent = (XMLSecEvent)xmlSecEventDeque.pop();
            if (xmlSecEvent.isStartElement()) {
               ++depth;
            } else if (xmlSecEvent.isEndElement()) {
               --depth;
               if (depth < 0) {
                  xmlSecEventDeque.push(xmlSecEvent);
                  break;
               }
            }

            outputProcessorChain.reset();
            outputProcessorChain.processEvent(xmlSecEvent);
         }
      } else {
         int signaturePosition = this.getSecurityProperties().getSignaturePosition();
         if (signaturePosition < 0) {
            signaturePosition = 0;
         }

         int position = 0;

         while(position != signaturePosition) {
            xmlSecEvent = (XMLSecEvent)xmlSecEventDeque.pop();
            if (xmlSecEvent.isStartElement()) {
               ++depth;
            } else if (xmlSecEvent.isEndElement()) {
               --depth;
               if (depth == 0) {
                  ++position;
               } else if (depth < 0) {
                  xmlSecEventDeque.push(xmlSecEvent);
                  break;
               }
            }

            outputProcessorChain.reset();
            outputProcessorChain.processEvent(xmlSecEvent);
         }
      }

      super.flushBufferAndCallbackAfterHeader(outputProcessorChain, xmlSecEventDeque);
   }

   protected void createKeyInfoStructureForSignature(OutputProcessorChain outputProcessorChain, OutboundSecurityToken securityToken, boolean useSingleCertificate) throws XMLStreamException, XMLSecurityException {
      X509Certificate[] x509Certificates = securityToken.getX509Certificates();
      if (x509Certificates != null) {
         if (this.getSecurityProperties().getSignatureKeyIdentifiers().isEmpty()) {
            XMLSecurityUtils.createX509IssuerSerialStructure(this, outputProcessorChain, x509Certificates);
         } else {
            List keyIdentifiers = this.getSecurityProperties().getSignatureKeyIdentifiers();
            if (keyIdentifiers.remove(SecurityTokenConstants.KeyIdentifier_KeyName)) {
               String keyName = this.getSecurityProperties().getSignatureKeyName();
               XMLSecurityUtils.createKeyNameTokenStructure(this, outputProcessorChain, keyName);
            }

            if (keyIdentifiers.remove(SecurityTokenConstants.KeyIdentifier_KeyValue)) {
               XMLSecurityUtils.createKeyValueTokenStructure(this, outputProcessorChain, (X509Certificate[])x509Certificates);
            }

            if (!keyIdentifiers.isEmpty()) {
               this.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_X509Data, true, (List)null);
               Iterator var8 = keyIdentifiers.iterator();

               while(var8.hasNext()) {
                  SecurityTokenConstants.KeyIdentifier keyIdentifier = (SecurityTokenConstants.KeyIdentifier)var8.next();
                  if (SecurityTokenConstants.KeyIdentifier_IssuerSerial.equals(keyIdentifier)) {
                     XMLSecurityUtils.createX509IssuerSerialStructure(this, outputProcessorChain, x509Certificates, false);
                  } else if (SecurityTokenConstants.KeyIdentifier_SkiKeyIdentifier.equals(keyIdentifier)) {
                     XMLSecurityUtils.createX509SubjectKeyIdentifierStructure(this, outputProcessorChain, x509Certificates, false);
                  } else if (SecurityTokenConstants.KeyIdentifier_X509KeyIdentifier.equals(keyIdentifier)) {
                     XMLSecurityUtils.createX509CertificateStructure(this, outputProcessorChain, x509Certificates, false);
                  } else if (SecurityTokenConstants.KeyIdentifier_X509SubjectName.equals(keyIdentifier)) {
                     XMLSecurityUtils.createX509SubjectNameStructure(this, outputProcessorChain, x509Certificates, false);
                  } else if (!SecurityTokenConstants.KeyIdentifier_KeyName.equals(keyIdentifier) && !SecurityTokenConstants.KeyIdentifier_KeyValue.equals(keyIdentifier)) {
                     throw new XMLSecurityException("stax.unsupportedToken", new Object[]{keyIdentifier});
                  }
               }

               this.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_X509Data);
            }
         }
      } else if (securityToken.getPublicKey() != null) {
         XMLSecurityUtils.createKeyValueTokenStructure(this, outputProcessorChain, (PublicKey)securityToken.getPublicKey());
      }

   }

   protected void createTransformsStructureForSignature(OutputProcessorChain subOutputProcessorChain, SignaturePartDef signaturePartDef) throws XMLStreamException, XMLSecurityException {
      if (signaturePartDef.getTransforms() != null) {
         this.createStartElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_dsig_Transforms, false, (List)null);
         String[] transforms = signaturePartDef.getTransforms();

         for(int i = 0; i < transforms.length; ++i) {
            String transform = transforms[i];
            if (this.shouldIncludeTransform(transform)) {
               List attributes = new ArrayList(1);
               attributes.add(this.createAttribute(XMLSecurityConstants.ATT_NULL_Algorithm, transform));
               this.createStartElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_dsig_Transform, false, attributes);
               if (this.getSecurityProperties().isAddExcC14NInclusivePrefixes()) {
                  attributes = new ArrayList(1);
                  attributes.add(this.createAttribute(XMLSecurityConstants.ATT_NULL_PrefixList, signaturePartDef.getInclusiveNamespacesPrefixes()));
                  this.createStartElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_c14nExcl_InclusiveNamespaces, true, attributes);
                  this.createEndElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_c14nExcl_InclusiveNamespaces);
               }

               this.createEndElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_dsig_Transform);
            }
         }

         this.createEndElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_dsig_Transforms);
      }

   }

   private boolean shouldIncludeTransform(String transform) {
      boolean include = true;
      if (!this.securityProperties.isSignatureIncludeDigestTransform() && !transform.equals("http://www.w3.org/2000/09/xmldsig#enveloped-signature")) {
         include = false;
      }

      return include;
   }
}
