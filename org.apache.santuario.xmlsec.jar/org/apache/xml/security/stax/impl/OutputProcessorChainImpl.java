package org.apache.xml.security.stax.impl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.DocumentContext;
import org.apache.xml.security.stax.ext.OutboundSecurityContext;
import org.apache.xml.security.stax.ext.OutputProcessor;
import org.apache.xml.security.stax.ext.OutputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutputProcessorChainImpl implements OutputProcessorChain {
   protected static final transient Logger LOG = LoggerFactory.getLogger(OutputProcessorChainImpl.class);
   private List outputProcessors;
   private int startPos;
   private int curPos;
   private XMLSecStartElement parentXmlSecStartElement;
   private final OutboundSecurityContext outboundSecurityContext;
   private final DocumentContextImpl documentContext;

   public OutputProcessorChainImpl(OutboundSecurityContext outboundSecurityContext) {
      this(outboundSecurityContext, 0);
   }

   public OutputProcessorChainImpl(OutboundSecurityContext outboundSecurityContext, int startPos) {
      this(outboundSecurityContext, new DocumentContextImpl(), startPos, new ArrayList(20));
   }

   public OutputProcessorChainImpl(OutboundSecurityContext outboundSecurityContext, DocumentContextImpl documentContext) {
      this(outboundSecurityContext, documentContext, 0, new ArrayList(20));
   }

   protected OutputProcessorChainImpl(OutboundSecurityContext outboundSecurityContext, DocumentContextImpl documentContextImpl, int startPos, List outputProcessors) {
      this.outboundSecurityContext = outboundSecurityContext;
      this.curPos = this.startPos = startPos;
      this.documentContext = documentContextImpl;
      this.outputProcessors = outputProcessors;
   }

   public void reset() {
      this.curPos = this.startPos;
   }

   public OutboundSecurityContext getSecurityContext() {
      return this.outboundSecurityContext;
   }

   public DocumentContext getDocumentContext() {
      return this.documentContext;
   }

   public void addProcessor(OutputProcessor newOutputProcessor) {
      int startPhaseIdx = 0;
      int endPhaseIdx = this.outputProcessors.size();
      int idxToInsert = endPhaseIdx;
      XMLSecurityConstants.Phase targetPhase = newOutputProcessor.getPhase();

      int i;
      OutputProcessor outputProcessor;
      for(i = this.outputProcessors.size() - 1; i >= 0; --i) {
         outputProcessor = (OutputProcessor)this.outputProcessors.get(i);
         if (outputProcessor.getPhase().ordinal() < targetPhase.ordinal()) {
            startPhaseIdx = i + 1;
            break;
         }
      }

      for(i = startPhaseIdx; i < this.outputProcessors.size(); ++i) {
         outputProcessor = (OutputProcessor)this.outputProcessors.get(i);
         if (outputProcessor.getPhase().ordinal() > targetPhase.ordinal()) {
            endPhaseIdx = i;
            break;
         }
      }

      if (newOutputProcessor.getBeforeProcessors().isEmpty() && newOutputProcessor.getAfterProcessors().isEmpty()) {
         this.outputProcessors.add(endPhaseIdx, newOutputProcessor);
      } else if (newOutputProcessor.getBeforeProcessors().isEmpty()) {
         idxToInsert = endPhaseIdx;

         for(i = endPhaseIdx - 1; i >= startPhaseIdx; --i) {
            outputProcessor = (OutputProcessor)this.outputProcessors.get(i);
            if (newOutputProcessor.getAfterProcessors().contains(outputProcessor) || newOutputProcessor.getAfterProcessors().contains(outputProcessor.getClass().getName())) {
               idxToInsert = i + 1;
               break;
            }
         }

         this.outputProcessors.add(idxToInsert, newOutputProcessor);
      } else if (newOutputProcessor.getAfterProcessors().isEmpty()) {
         idxToInsert = startPhaseIdx;

         for(i = startPhaseIdx; i < endPhaseIdx; ++i) {
            outputProcessor = (OutputProcessor)this.outputProcessors.get(i);
            if (newOutputProcessor.getBeforeProcessors().contains(outputProcessor) || newOutputProcessor.getBeforeProcessors().contains(outputProcessor.getClass().getName())) {
               idxToInsert = i;
               break;
            }
         }

         this.outputProcessors.add(idxToInsert, newOutputProcessor);
      } else {
         boolean found = false;
         idxToInsert = endPhaseIdx;

         OutputProcessor outputProcessor;
         int i;
         for(i = startPhaseIdx; i < endPhaseIdx; ++i) {
            outputProcessor = (OutputProcessor)this.outputProcessors.get(i);
            if (newOutputProcessor.getBeforeProcessors().contains(outputProcessor) || newOutputProcessor.getBeforeProcessors().contains(outputProcessor.getClass().getName())) {
               idxToInsert = i;
               found = true;
               break;
            }
         }

         if (found) {
            this.outputProcessors.add(idxToInsert, newOutputProcessor);
         } else {
            for(i = endPhaseIdx - 1; i >= startPhaseIdx; --i) {
               outputProcessor = (OutputProcessor)this.outputProcessors.get(i);
               if (newOutputProcessor.getAfterProcessors().contains(outputProcessor) || newOutputProcessor.getAfterProcessors().contains(outputProcessor.getClass().getName())) {
                  idxToInsert = i + 1;
                  break;
               }
            }

            this.outputProcessors.add(idxToInsert, newOutputProcessor);
         }
      }

      if (idxToInsert < this.curPos) {
         ++this.curPos;
      }

      if (LOG.isDebugEnabled()) {
         LOG.debug("Added {} to output chain: ", newOutputProcessor.getClass().getName());

         for(i = 0; i < this.outputProcessors.size(); ++i) {
            outputProcessor = (OutputProcessor)this.outputProcessors.get(i);
            LOG.debug("Name: {} phase: {}", outputProcessor.getClass().getName(), outputProcessor.getPhase());
         }
      }

   }

   public void removeProcessor(OutputProcessor outputProcessor) {
      LOG.debug("Removing processor {} from output chain", outputProcessor.getClass().getName());
      if (this.outputProcessors.indexOf(outputProcessor) <= this.curPos) {
         --this.curPos;
      }

      this.outputProcessors.remove(outputProcessor);
   }

   public List getProcessors() {
      return this.outputProcessors;
   }

   private void setParentXmlSecStartElement(XMLSecStartElement xmlSecStartElement) {
      this.parentXmlSecStartElement = xmlSecStartElement;
   }

   public void processEvent(XMLSecEvent xmlSecEvent) throws XMLStreamException, XMLSecurityException {
      boolean reparent = false;
      if (this.curPos == this.startPos) {
         switch (xmlSecEvent.getEventType()) {
            case 1:
               if (xmlSecEvent == this.parentXmlSecStartElement) {
                  this.parentXmlSecStartElement = null;
               }

               xmlSecEvent.setParentXMLSecStartElement(this.parentXmlSecStartElement);
               this.parentXmlSecStartElement = xmlSecEvent.asStartElement();
               break;
            case 2:
               xmlSecEvent.setParentXMLSecStartElement(this.parentXmlSecStartElement);
               reparent = true;
               break;
            default:
               xmlSecEvent.setParentXMLSecStartElement(this.parentXmlSecStartElement);
         }
      }

      ((OutputProcessor)this.outputProcessors.get(this.curPos++)).processNextEvent(xmlSecEvent, this);
      if (reparent && this.parentXmlSecStartElement != null) {
         this.parentXmlSecStartElement = this.parentXmlSecStartElement.getParentXMLSecStartElement();
      }

   }

   public void doFinal() throws XMLStreamException, XMLSecurityException {
      ((OutputProcessor)this.outputProcessors.get(this.curPos++)).doFinal(this);
   }

   public OutputProcessorChain createSubChain(OutputProcessor outputProcessor) throws XMLStreamException, XMLSecurityException {
      return this.createSubChain(outputProcessor, (XMLSecStartElement)null);
   }

   public OutputProcessorChain createSubChain(OutputProcessor outputProcessor, XMLSecStartElement parentXMLSecStartElement) throws XMLStreamException, XMLSecurityException {
      OutputProcessorChainImpl outputProcessorChain;
      try {
         outputProcessorChain = new OutputProcessorChainImpl(this.outboundSecurityContext, this.documentContext.clone(), this.outputProcessors.indexOf(outputProcessor) + 1, this.outputProcessors);
      } catch (CloneNotSupportedException var5) {
         throw new XMLSecurityException(var5);
      }

      if (parentXMLSecStartElement != null) {
         outputProcessorChain.setParentXmlSecStartElement(parentXMLSecStartElement);
      } else {
         outputProcessorChain.setParentXmlSecStartElement(this.parentXmlSecStartElement);
      }

      return outputProcessorChain;
   }
}
