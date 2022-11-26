package org.apache.xml.security.stax.ext;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;

public abstract class AbstractInputProcessor implements InputProcessor {
   private final XMLSecurityProperties securityProperties;
   private XMLSecurityConstants.Phase phase;
   private Set beforeProcessors;
   private Set afterProcessors;

   public AbstractInputProcessor(XMLSecurityProperties securityProperties) {
      this.phase = XMLSecurityConstants.Phase.PROCESSING;
      this.securityProperties = securityProperties;
   }

   public XMLSecurityConstants.Phase getPhase() {
      return this.phase;
   }

   public void setPhase(XMLSecurityConstants.Phase phase) {
      this.phase = phase;
   }

   public void addBeforeProcessor(Object processor) {
      this.beforeProcessors = new HashSet();
      this.beforeProcessors.add(processor);
   }

   public Set getBeforeProcessors() {
      return this.beforeProcessors == null ? Collections.emptySet() : this.beforeProcessors;
   }

   public void addAfterProcessor(Object processor) {
      this.afterProcessors = new HashSet();
      this.afterProcessors.add(processor);
   }

   public Set getAfterProcessors() {
      return this.afterProcessors == null ? Collections.emptySet() : this.afterProcessors;
   }

   public abstract XMLSecEvent processNextHeaderEvent(InputProcessorChain var1) throws XMLStreamException, XMLSecurityException;

   public abstract XMLSecEvent processNextEvent(InputProcessorChain var1) throws XMLStreamException, XMLSecurityException;

   public void doFinal(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
      inputProcessorChain.doFinal();
   }

   public XMLSecurityProperties getSecurityProperties() {
      return this.securityProperties;
   }

   public Attribute getReferenceIDAttribute(XMLSecStartElement xmlSecStartElement) {
      return xmlSecStartElement.getAttributeByName(this.securityProperties.getIdAttributeNS());
   }
}
