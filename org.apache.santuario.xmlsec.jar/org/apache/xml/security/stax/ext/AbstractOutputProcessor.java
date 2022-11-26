package org.apache.xml.security.stax.ext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.stax.XMLSecAttribute;
import org.apache.xml.security.stax.ext.stax.XMLSecCharacters;
import org.apache.xml.security.stax.ext.stax.XMLSecEndElement;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecEventFactory;
import org.apache.xml.security.stax.ext.stax.XMLSecNamespace;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public abstract class AbstractOutputProcessor implements OutputProcessor {
   protected XMLSecurityProperties securityProperties;
   protected XMLSecurityConstants.Action action;
   private XMLSecurityConstants.Phase phase;
   private Set beforeProcessors;
   private Set afterProcessors;

   protected AbstractOutputProcessor() throws XMLSecurityException {
      this.phase = XMLSecurityConstants.Phase.PROCESSING;
   }

   public void setXMLSecurityProperties(XMLSecurityProperties xmlSecurityProperties) {
      this.securityProperties = xmlSecurityProperties;
   }

   public void setAction(XMLSecurityConstants.Action action) {
      this.action = action;
   }

   public void init(OutputProcessorChain outputProcessorChain) throws XMLSecurityException {
      outputProcessorChain.addProcessor(this);
   }

   public XMLSecurityConstants.Phase getPhase() {
      return this.phase;
   }

   public void setPhase(XMLSecurityConstants.Phase phase) {
      this.phase = phase;
   }

   public void addBeforeProcessor(Object processor) {
      if (this.beforeProcessors == null) {
         this.beforeProcessors = new HashSet();
      }

      this.beforeProcessors.add(processor);
   }

   public Set getBeforeProcessors() {
      return this.beforeProcessors == null ? Collections.emptySet() : this.beforeProcessors;
   }

   public void addAfterProcessor(Object processor) {
      if (this.afterProcessors == null) {
         this.afterProcessors = new HashSet();
      }

      this.afterProcessors.add(processor);
   }

   public Set getAfterProcessors() {
      return this.afterProcessors == null ? Collections.emptySet() : this.afterProcessors;
   }

   public XMLSecurityProperties getSecurityProperties() {
      return this.securityProperties;
   }

   public XMLSecurityConstants.Action getAction() {
      return this.action;
   }

   public abstract void processEvent(XMLSecEvent var1, OutputProcessorChain var2) throws XMLStreamException, XMLSecurityException;

   public void processNextEvent(XMLSecEvent xmlSecEvent, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
      this.processEvent(xmlSecEvent, outputProcessorChain);
   }

   public void doFinal(OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
      outputProcessorChain.doFinal();
   }

   public XMLSecStartElement addAttributes(XMLSecStartElement xmlSecStartElement, List attributeList) throws XMLStreamException {
      List declaredNamespaces = xmlSecStartElement.getOnElementDeclaredNamespaces();

      for(int i = 0; i < attributeList.size(); ++i) {
         XMLSecAttribute xmlSecAttribute = (XMLSecAttribute)attributeList.get(i);
         xmlSecStartElement.addAttribute(xmlSecAttribute);
         QName attributeName = xmlSecAttribute.getName();
         if (attributeName.getNamespaceURI() != null && !"".equals(attributeName.getNamespaceURI()) && !declaredNamespaces.contains(xmlSecAttribute.getAttributeNamespace())) {
            xmlSecStartElement.addNamespace(xmlSecAttribute.getAttributeNamespace());
         }
      }

      return xmlSecStartElement;
   }

   public void createStartElementAndOutputAsEvent(OutputProcessorChain outputProcessorChain, QName element, List namespaces, List attributes) throws XMLStreamException, XMLSecurityException {
      XMLSecStartElement xmlSecStartElement = XMLSecEventFactory.createXmlSecStartElement(element, attributes, namespaces);
      this.outputAsEvent(outputProcessorChain, xmlSecStartElement);
   }

   public XMLSecStartElement createStartElementAndOutputAsEvent(OutputProcessorChain outputProcessorChain, QName element, boolean outputLocalNs, List attributes) throws XMLStreamException, XMLSecurityException {
      List comparableNamespaces = Collections.emptyList();
      if (outputLocalNs) {
         comparableNamespaces = new ArrayList(2);
         ((List)comparableNamespaces).add(XMLSecEventFactory.createXMLSecNamespace(element.getPrefix(), element.getNamespaceURI()));
      }

      if (attributes != null) {
         for(int i = 0; i < attributes.size(); ++i) {
            XMLSecAttribute xmlSecAttribute = (XMLSecAttribute)attributes.get(i);
            QName attributeName = xmlSecAttribute.getName();
            String attributeNamePrefix = attributeName.getPrefix();
            if ((attributeNamePrefix == null || !attributeNamePrefix.isEmpty()) && !((List)comparableNamespaces).contains(xmlSecAttribute.getAttributeNamespace())) {
               if (comparableNamespaces == Collections.emptyList()) {
                  comparableNamespaces = new ArrayList(1);
               }

               ((List)comparableNamespaces).add(xmlSecAttribute.getAttributeNamespace());
            }
         }
      }

      XMLSecStartElement xmlSecStartElement = XMLSecEventFactory.createXmlSecStartElement(element, (List)attributes, (List)comparableNamespaces);
      this.outputAsEvent(outputProcessorChain, xmlSecStartElement);
      return xmlSecStartElement;
   }

   public XMLSecEndElement createEndElement(QName element) {
      return XMLSecEventFactory.createXmlSecEndElement(element);
   }

   public void createEndElementAndOutputAsEvent(OutputProcessorChain outputProcessorChain, QName element) throws XMLStreamException, XMLSecurityException {
      this.outputAsEvent(outputProcessorChain, this.createEndElement(element));
   }

   public void createCharactersAndOutputAsEvent(OutputProcessorChain outputProcessorChain, String characters) throws XMLStreamException, XMLSecurityException {
      this.outputAsEvent(outputProcessorChain, this.createCharacters(characters));
   }

   public void createCharactersAndOutputAsEvent(OutputProcessorChain outputProcessorChain, char[] text) throws XMLStreamException, XMLSecurityException {
      this.outputAsEvent(outputProcessorChain, this.createCharacters(text));
   }

   public XMLSecCharacters createCharacters(String characters) {
      return XMLSecEventFactory.createXmlSecCharacters(characters);
   }

   public XMLSecCharacters createCharacters(char[] text) {
      return XMLSecEventFactory.createXmlSecCharacters(text);
   }

   public XMLSecAttribute createAttribute(QName attribute, String attributeValue) {
      return XMLSecEventFactory.createXMLSecAttribute(attribute, attributeValue);
   }

   public XMLSecNamespace createNamespace(String prefix, String uri) {
      return XMLSecEventFactory.createXMLSecNamespace(prefix, uri);
   }

   protected void outputAsEvent(OutputProcessorChain outputProcessorChain, XMLSecEvent xmlSecEvent) throws XMLStreamException, XMLSecurityException {
      outputProcessorChain.reset();
      outputProcessorChain.processEvent(xmlSecEvent);
   }

   protected SecurePart securePartMatches(XMLSecStartElement xmlSecStartElement, OutputProcessorChain outputProcessorChain, String dynamicParts) {
      Map dynamicSecureParts = outputProcessorChain.getSecurityContext().getAsMap(dynamicParts);
      return this.securePartMatches(xmlSecStartElement, dynamicSecureParts);
   }

   protected SecurePart securePartMatches(XMLSecStartElement xmlSecStartElement, Map secureParts) {
      SecurePart securePart = null;
      if (secureParts != null) {
         securePart = (SecurePart)secureParts.get(xmlSecStartElement.getName());
         if (securePart == null) {
            Attribute attribute = xmlSecStartElement.getAttributeByName(this.securityProperties.getIdAttributeNS());
            if (attribute != null) {
               securePart = (SecurePart)secureParts.get(attribute.getValue());
            }
         }
      }

      return securePart;
   }

   protected void outputDOMElement(Element element, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
      NamedNodeMap namedNodeMap = element.getAttributes();
      List attributes = new ArrayList(namedNodeMap.getLength());
      List namespaces = new ArrayList(namedNodeMap.getLength());

      for(int i = 0; i < namedNodeMap.getLength(); ++i) {
         Attr attribute = (Attr)namedNodeMap.item(i);
         if (attribute.getPrefix() == null) {
            attributes.add(this.createAttribute(new QName(attribute.getNamespaceURI(), attribute.getLocalName()), attribute.getValue()));
         } else if (!"xmlns".equals(attribute.getPrefix()) && !"xmlns".equals(attribute.getLocalName())) {
            attributes.add(this.createAttribute(new QName(attribute.getNamespaceURI(), attribute.getLocalName(), attribute.getPrefix()), attribute.getValue()));
         } else {
            namespaces.add(this.createNamespace(attribute.getLocalName(), attribute.getValue()));
         }
      }

      QName elementName = new QName(element.getNamespaceURI(), element.getLocalName(), element.getPrefix());
      this.createStartElementAndOutputAsEvent(outputProcessorChain, elementName, namespaces, attributes);

      for(Node childNode = element.getFirstChild(); childNode != null; childNode = childNode.getNextSibling()) {
         switch (childNode.getNodeType()) {
            case 1:
               this.outputDOMElement((Element)childNode, outputProcessorChain);
               break;
            case 3:
               this.createCharactersAndOutputAsEvent(outputProcessorChain, ((Text)childNode).getData());
         }
      }

      this.createEndElementAndOutputAsEvent(outputProcessorChain, elementName);
   }
}
