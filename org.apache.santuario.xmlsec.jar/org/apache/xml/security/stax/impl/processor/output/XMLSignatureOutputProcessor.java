package org.apache.xml.security.stax.impl.processor.output;

import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.OutputProcessorChain;
import org.apache.xml.security.stax.ext.SecurePart;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;
import org.apache.xml.security.stax.impl.SignaturePartDef;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMLSignatureOutputProcessor extends AbstractSignatureOutputProcessor {
   private static final transient Logger LOG = LoggerFactory.getLogger(XMLSignatureOutputProcessor.class);

   public XMLSignatureOutputProcessor() throws XMLSecurityException {
   }

   public void init(OutputProcessorChain outputProcessorChain) throws XMLSecurityException {
      super.init(outputProcessorChain);
      XMLSignatureEndingOutputProcessor signatureEndingOutputProcessor = new XMLSignatureEndingOutputProcessor(this);
      signatureEndingOutputProcessor.setXMLSecurityProperties(this.getSecurityProperties());
      signatureEndingOutputProcessor.setAction(this.getAction());
      signatureEndingOutputProcessor.init(outputProcessorChain);
   }

   public void processEvent(XMLSecEvent xmlSecEvent, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
      if (((XMLSecEvent)xmlSecEvent).getEventType() == 1) {
         XMLSecStartElement xmlSecStartElement = ((XMLSecEvent)xmlSecEvent).asStartElement();
         if (this.getActiveInternalSignatureOutputProcessor() == null) {
            SecurePart securePart = this.securePartMatches(xmlSecStartElement, outputProcessorChain, "signatureParts");
            if (securePart != null) {
               LOG.debug("Matched securePart for signature");
               AbstractSignatureOutputProcessor.InternalSignatureOutputProcessor internalSignatureOutputProcessor = null;
               SignaturePartDef signaturePartDef = new SignaturePartDef();
               signaturePartDef.setSecurePart(securePart);
               signaturePartDef.setTransforms(securePart.getTransforms());
               if (signaturePartDef.getTransforms() == null) {
                  signaturePartDef.setTransforms(new String[]{"http://www.w3.org/2001/10/xml-exc-c14n#"});
               }

               signaturePartDef.setExcludeVisibleC14Nprefixes(true);
               signaturePartDef.setDigestAlgo(securePart.getDigestMethod());
               if (signaturePartDef.getDigestAlgo() == null) {
                  signaturePartDef.setDigestAlgo(this.getSecurityProperties().getSignatureDigestAlgorithm());
               }

               if (this.securityProperties.isSignatureGenerateIds()) {
                  if (securePart.getIdToSign() == null) {
                     signaturePartDef.setGenerateXPointer(securePart.isGenerateXPointer());
                     signaturePartDef.setSigRefId(IDGenerator.generateID((String)null));
                     Attribute attribute = xmlSecStartElement.getAttributeByName(this.securityProperties.getIdAttributeNS());
                     if (attribute != null) {
                        signaturePartDef.setSigRefId(attribute.getValue());
                     } else {
                        List attributeList = new ArrayList(1);
                        attributeList.add(this.createAttribute(this.securityProperties.getIdAttributeNS(), signaturePartDef.getSigRefId()));
                        xmlSecEvent = this.addAttributes(xmlSecStartElement, attributeList);
                     }
                  } else {
                     signaturePartDef.setSigRefId(securePart.getIdToSign());
                  }
               }

               this.getSignaturePartDefList().add(signaturePartDef);
               internalSignatureOutputProcessor = new AbstractSignatureOutputProcessor.InternalSignatureOutputProcessor(signaturePartDef, xmlSecStartElement);
               internalSignatureOutputProcessor.setXMLSecurityProperties(this.getSecurityProperties());
               internalSignatureOutputProcessor.setAction(this.getAction());
               internalSignatureOutputProcessor.addAfterProcessor(XMLSignatureOutputProcessor.class.getName());
               internalSignatureOutputProcessor.addBeforeProcessor(XMLSignatureEndingOutputProcessor.class.getName());
               internalSignatureOutputProcessor.init(outputProcessorChain);
               this.setActiveInternalSignatureOutputProcessor(internalSignatureOutputProcessor);
            }
         }
      }

      outputProcessorChain.processEvent((XMLSecEvent)xmlSecEvent);
   }
}
