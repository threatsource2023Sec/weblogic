package org.apache.xml.security.stax.impl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.DocumentContext;
import org.apache.xml.security.stax.ext.InboundSecurityContext;
import org.apache.xml.security.stax.ext.InputProcessor;
import org.apache.xml.security.stax.ext.InputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputProcessorChainImpl implements InputProcessorChain {
   protected static final transient Logger LOG = LoggerFactory.getLogger(InputProcessorChainImpl.class);
   private List inputProcessors;
   private int startPos;
   private int curPos;
   private final InboundSecurityContext inboundSecurityContext;
   private final DocumentContextImpl documentContext;

   public InputProcessorChainImpl(InboundSecurityContext inboundSecurityContext) {
      this(inboundSecurityContext, 0);
   }

   public InputProcessorChainImpl(InboundSecurityContext inboundSecurityContext, int startPos) {
      this(inboundSecurityContext, new DocumentContextImpl(), startPos, new ArrayList(20));
   }

   public InputProcessorChainImpl(InboundSecurityContext inboundSecurityContext, DocumentContextImpl documentContext) {
      this(inboundSecurityContext, documentContext, 0, new ArrayList(20));
   }

   protected InputProcessorChainImpl(InboundSecurityContext inboundSecurityContext, DocumentContextImpl documentContextImpl, int startPos, List inputProcessors) {
      this.inboundSecurityContext = inboundSecurityContext;
      this.curPos = this.startPos = startPos;
      this.documentContext = documentContextImpl;
      this.inputProcessors = inputProcessors;
   }

   public void reset() {
      this.curPos = this.startPos;
   }

   public InboundSecurityContext getSecurityContext() {
      return this.inboundSecurityContext;
   }

   public DocumentContext getDocumentContext() {
      return this.documentContext;
   }

   public synchronized void addProcessor(InputProcessor newInputProcessor) {
      int startPhaseIdx = 0;
      int endPhaseIdx = this.inputProcessors.size();
      XMLSecurityConstants.Phase targetPhase = newInputProcessor.getPhase();

      int idxToInsert;
      InputProcessor inputProcessor;
      for(idxToInsert = this.inputProcessors.size() - 1; idxToInsert >= 0; --idxToInsert) {
         inputProcessor = (InputProcessor)this.inputProcessors.get(idxToInsert);
         if (inputProcessor.getPhase().ordinal() > targetPhase.ordinal()) {
            startPhaseIdx = idxToInsert + 1;
            break;
         }
      }

      for(idxToInsert = startPhaseIdx; idxToInsert < this.inputProcessors.size(); ++idxToInsert) {
         inputProcessor = (InputProcessor)this.inputProcessors.get(idxToInsert);
         if (inputProcessor.getPhase().ordinal() < targetPhase.ordinal()) {
            endPhaseIdx = idxToInsert;
            break;
         }
      }

      if (newInputProcessor.getBeforeProcessors().isEmpty() && newInputProcessor.getAfterProcessors().isEmpty()) {
         this.inputProcessors.add(startPhaseIdx, newInputProcessor);
      } else {
         InputProcessor inputProcessor;
         int idxToInsert;
         if (newInputProcessor.getBeforeProcessors().isEmpty()) {
            idxToInsert = startPhaseIdx;

            for(idxToInsert = endPhaseIdx - 1; idxToInsert >= startPhaseIdx; --idxToInsert) {
               inputProcessor = (InputProcessor)this.inputProcessors.get(idxToInsert);
               if (newInputProcessor.getAfterProcessors().contains(inputProcessor) || newInputProcessor.getAfterProcessors().contains(inputProcessor.getClass().getName())) {
                  idxToInsert = idxToInsert;
                  break;
               }
            }

            this.inputProcessors.add(idxToInsert, newInputProcessor);
         } else if (newInputProcessor.getAfterProcessors().isEmpty()) {
            idxToInsert = endPhaseIdx;

            for(idxToInsert = startPhaseIdx; idxToInsert < endPhaseIdx; ++idxToInsert) {
               inputProcessor = (InputProcessor)this.inputProcessors.get(idxToInsert);
               if (newInputProcessor.getBeforeProcessors().contains(inputProcessor) || newInputProcessor.getBeforeProcessors().contains(inputProcessor.getClass().getName())) {
                  idxToInsert = idxToInsert + 1;
                  break;
               }
            }

            this.inputProcessors.add(idxToInsert, newInputProcessor);
         } else {
            boolean found = false;
            idxToInsert = startPhaseIdx;

            InputProcessor inputProcessor;
            int i;
            for(i = endPhaseIdx - 1; i >= startPhaseIdx; --i) {
               inputProcessor = (InputProcessor)this.inputProcessors.get(i);
               if (newInputProcessor.getAfterProcessors().contains(inputProcessor) || newInputProcessor.getAfterProcessors().contains(inputProcessor.getClass().getName())) {
                  idxToInsert = i;
                  found = true;
                  break;
               }
            }

            if (found) {
               this.inputProcessors.add(idxToInsert, newInputProcessor);
            } else {
               for(i = startPhaseIdx; i < endPhaseIdx; ++i) {
                  inputProcessor = (InputProcessor)this.inputProcessors.get(i);
                  if (newInputProcessor.getBeforeProcessors().contains(inputProcessor) || newInputProcessor.getBeforeProcessors().contains(inputProcessor.getClass().getName())) {
                     idxToInsert = i + 1;
                     break;
                  }
               }

               this.inputProcessors.add(idxToInsert, newInputProcessor);
            }
         }
      }

      if (LOG.isDebugEnabled()) {
         LOG.debug("Added {} to input chain: ", newInputProcessor.getClass().getName());

         for(idxToInsert = 0; idxToInsert < this.inputProcessors.size(); ++idxToInsert) {
            inputProcessor = (InputProcessor)this.inputProcessors.get(idxToInsert);
            LOG.debug("Name: {} phase: {}", inputProcessor.getClass().getName(), inputProcessor.getPhase());
         }
      }

   }

   public synchronized void removeProcessor(InputProcessor inputProcessor) {
      LOG.debug("Removing processor {} from input chain", inputProcessor.getClass().getName());
      if (this.inputProcessors.indexOf(inputProcessor) <= this.curPos) {
         --this.curPos;
      }

      this.inputProcessors.remove(inputProcessor);
   }

   public List getProcessors() {
      return this.inputProcessors;
   }

   public XMLSecEvent processHeaderEvent() throws XMLStreamException, XMLSecurityException {
      return ((InputProcessor)this.inputProcessors.get(this.curPos++)).processNextHeaderEvent(this);
   }

   public XMLSecEvent processEvent() throws XMLStreamException, XMLSecurityException {
      return ((InputProcessor)this.inputProcessors.get(this.curPos++)).processNextEvent(this);
   }

   public void doFinal() throws XMLStreamException, XMLSecurityException {
      ((InputProcessor)this.inputProcessors.get(this.curPos++)).doFinal(this);
   }

   public InputProcessorChain createSubChain(InputProcessor inputProcessor) throws XMLStreamException, XMLSecurityException {
      return this.createSubChain(inputProcessor, true);
   }

   public InputProcessorChain createSubChain(InputProcessor inputProcessor, boolean clone) throws XMLStreamException, XMLSecurityException {
      try {
         DocumentContextImpl docContext = clone ? this.documentContext.clone() : this.documentContext;
         InputProcessorChainImpl inputProcessorChain = new InputProcessorChainImpl(this.inboundSecurityContext, docContext, this.inputProcessors.indexOf(inputProcessor) + 1, new ArrayList(this.inputProcessors));
         return inputProcessorChain;
      } catch (CloneNotSupportedException var5) {
         throw new XMLSecurityException(var5);
      }
   }
}
