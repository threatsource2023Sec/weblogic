package org.apache.xml.security.stax.impl.processor.input;

import java.util.ArrayDeque;
import javax.xml.stream.XMLStreamException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.AbstractInputProcessor;
import org.apache.xml.security.stax.ext.InputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.stax.ext.stax.XMLSecEndElement;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;

public class XMLSecurityInputProcessor extends AbstractInputProcessor {
   private int startIndexForProcessor;
   private InternalBufferProcessor internalBufferProcessor;
   private boolean signatureElementFound = false;
   private boolean encryptedDataElementFound = false;
   private boolean decryptOnly = false;

   public XMLSecurityInputProcessor(XMLSecurityProperties securityProperties) {
      super(securityProperties);
      this.setPhase(XMLSecurityConstants.Phase.POSTPROCESSING);
      this.decryptOnly = securityProperties.getActions().size() == 1 && securityProperties.getActions().contains(XMLSecurityConstants.ENCRYPT);
   }

   public XMLSecEvent processNextHeaderEvent(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
      return null;
   }

   public XMLSecEvent processNextEvent(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
      if (!this.decryptOnly && this.internalBufferProcessor == null) {
         this.internalBufferProcessor = new InternalBufferProcessor(this.getSecurityProperties());
         inputProcessorChain.addProcessor(this.internalBufferProcessor);
      }

      XMLSecEvent xmlSecEvent = inputProcessorChain.processEvent();
      switch (xmlSecEvent.getEventType()) {
         case 1:
            final XMLSecStartElement xmlSecStartElement = xmlSecEvent.asStartElement();
            if (!this.decryptOnly && xmlSecStartElement.getName().equals(XMLSecurityConstants.TAG_dsig_Signature)) {
               if (this.signatureElementFound) {
                  throw new XMLSecurityException("stax.multipleSignaturesNotSupported");
               }

               this.signatureElementFound = true;
               this.startIndexForProcessor = this.internalBufferProcessor.getXmlSecEventList().size() - 1;
            } else if (xmlSecStartElement.getName().equals(XMLSecurityConstants.TAG_xenc_EncryptedData)) {
               this.encryptedDataElementFound = true;
               XMLDecryptInputProcessor decryptInputProcessor = new XMLDecryptInputProcessor(this.getSecurityProperties());
               decryptInputProcessor.setPhase(XMLSecurityConstants.Phase.PREPROCESSING);
               decryptInputProcessor.addAfterProcessor(XMLEventReaderInputProcessor.class.getName());
               decryptInputProcessor.addBeforeProcessor(XMLSecurityInputProcessor.class.getName());
               decryptInputProcessor.addBeforeProcessor(InternalBufferProcessor.class.getName());
               inputProcessorChain.addProcessor(decryptInputProcessor);
               if (!this.decryptOnly) {
                  ArrayDeque xmlSecEventList = this.internalBufferProcessor.getXmlSecEventList();
                  xmlSecEventList.pollFirst();
               }

               AbstractInputProcessor abstractInputProcessor = new AbstractInputProcessor(this.getSecurityProperties()) {
                  public XMLSecEvent processNextHeaderEvent(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
                     return this.processNextEvent(inputProcessorChain);
                  }

                  public XMLSecEvent processNextEvent(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
                     inputProcessorChain.removeProcessor(this);
                     return xmlSecStartElement;
                  }
               };
               abstractInputProcessor.setPhase(XMLSecurityConstants.Phase.PREPROCESSING);
               abstractInputProcessor.addBeforeProcessor(decryptInputProcessor);
               inputProcessorChain.addProcessor(abstractInputProcessor);
               inputProcessorChain.reset();
               xmlSecEvent = inputProcessorChain.processEvent();
               if (!this.decryptOnly && xmlSecEvent.isStartElement() && xmlSecEvent.asStartElement().getName().equals(XMLSecurityConstants.TAG_dsig_Signature) && !this.signatureElementFound) {
                  throw new XMLSecurityException("Internal error");
               }
            }
            break;
         case 2:
            XMLSecEndElement xmlSecEndElement = xmlSecEvent.asEndElement();
            if (this.signatureElementFound && xmlSecEndElement.getName().equals(XMLSecurityConstants.TAG_dsig_Signature)) {
               XMLSignatureInputHandler inputHandler = new XMLSignatureInputHandler();
               ArrayDeque xmlSecEventList = this.internalBufferProcessor.getXmlSecEventList();
               inputHandler.handle(inputProcessorChain, this.getSecurityProperties(), xmlSecEventList, this.startIndexForProcessor);
               inputProcessorChain.removeProcessor(this.internalBufferProcessor);
               InternalReplayProcessor internalReplayProcessor = new InternalReplayProcessor(this.getSecurityProperties(), xmlSecEventList);
               internalReplayProcessor.addBeforeProcessor(XMLSignatureReferenceVerifyInputProcessor.class.getName());
               inputProcessorChain.addProcessor(internalReplayProcessor);
               InputProcessorChain subInputProcessorChain = inputProcessorChain.createSubChain(this, false);

               while(!xmlSecEventList.isEmpty()) {
                  subInputProcessorChain.reset();
                  subInputProcessorChain.processEvent();
               }

               inputProcessorChain.getProcessors().clear();
               inputProcessorChain.getProcessors().addAll(subInputProcessorChain.getProcessors());
            }
      }

      return xmlSecEvent;
   }

   public void doFinal(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
      if (!this.signatureElementFound && !this.encryptedDataElementFound) {
         throw new XMLSecurityException("stax.unsecuredMessage");
      } else {
         super.doFinal(inputProcessorChain);
      }
   }

   public static class InternalReplayProcessor extends AbstractInputProcessor {
      private final ArrayDeque xmlSecEventList;

      public InternalReplayProcessor(XMLSecurityProperties securityProperties, ArrayDeque xmlSecEventList) {
         super(securityProperties);
         this.xmlSecEventList = xmlSecEventList;
      }

      public XMLSecEvent processNextHeaderEvent(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
         return null;
      }

      public XMLSecEvent processNextEvent(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
         if (!this.xmlSecEventList.isEmpty()) {
            return (XMLSecEvent)this.xmlSecEventList.pollLast();
         } else {
            inputProcessorChain.removeProcessor(this);
            return inputProcessorChain.processEvent();
         }
      }
   }

   public class InternalBufferProcessor extends AbstractInputProcessor {
      private final ArrayDeque xmlSecEventList = new ArrayDeque();

      InternalBufferProcessor(XMLSecurityProperties securityProperties) {
         super(securityProperties);
         this.setPhase(XMLSecurityConstants.Phase.POSTPROCESSING);
         this.addBeforeProcessor(XMLSecurityInputProcessor.class.getName());
      }

      public ArrayDeque getXmlSecEventList() {
         return this.xmlSecEventList;
      }

      public XMLSecEvent processNextHeaderEvent(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
         return null;
      }

      public XMLSecEvent processNextEvent(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
         XMLSecEvent xmlSecEvent = inputProcessorChain.processEvent();
         this.xmlSecEventList.push(xmlSecEvent);
         return xmlSecEvent;
      }
   }
}
