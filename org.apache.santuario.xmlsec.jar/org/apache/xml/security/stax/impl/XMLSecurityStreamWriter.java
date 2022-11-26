package org.apache.xml.security.stax.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.OutputProcessorChain;
import org.apache.xml.security.stax.ext.SecurePart;
import org.apache.xml.security.stax.ext.stax.XMLSecAttribute;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecEventFactory;
import org.apache.xml.security.stax.ext.stax.XMLSecNamespace;

public class XMLSecurityStreamWriter implements XMLStreamWriter {
   private final OutputProcessorChain outputProcessorChain;
   private Element elementStack;
   private Element openStartElement;
   private NSContext namespaceContext = new NSContext((NamespaceContext)null);
   private boolean endDocumentWritten = false;
   private boolean haveToWriteEndElement = false;
   private SecurePart signEntireRequestPart;
   private SecurePart encryptEntireRequestPart;

   public XMLSecurityStreamWriter(OutputProcessorChain outputProcessorChain) {
      this.outputProcessorChain = outputProcessorChain;
   }

   private void chainProcessEvent(XMLSecEvent xmlSecEvent) throws XMLStreamException {
      try {
         this.outputProcessorChain.reset();
         this.outputProcessorChain.processEvent(xmlSecEvent);
      } catch (XMLSecurityException var4) {
         throw new XMLStreamException(var4);
      } catch (XMLStreamException var5) {
         String msg = var5.getMessage();
         if (msg != null && msg.contains("Trying to declare prefix xmlns (illegal as per NS 1.1 #4)")) {
            throw new XMLStreamException("If you hit this exception this most probably meansyou are using the javax.xml.transform.stax.StAXResult. Don't use it. It is buggy as hell.", var5);
         } else {
            throw var5;
         }
      }
   }

   private void outputOpenStartElement() throws XMLStreamException {
      if (this.openStartElement != null) {
         this.chainProcessEvent(XMLSecEventFactory.createXmlSecStartElement(this.openStartElement.getQName(), this.openStartElement.getAttributes(), this.openStartElement.getNamespaces()));
         this.openStartElement = null;
      }

      if (this.haveToWriteEndElement) {
         this.haveToWriteEndElement = false;
         this.writeEndElement();
      }

   }

   private String getNamespacePrefix(String namespaceURI) {
      return this.elementStack == null ? this.namespaceContext.getPrefix(namespaceURI) : this.elementStack.getNamespaceContext().getPrefix(namespaceURI);
   }

   public void writeStartElement(String localName) throws XMLStreamException {
      this.writeStartElement("", localName, "");
   }

   public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
      this.writeStartElement(this.getNamespacePrefix(namespaceURI), localName, namespaceURI);
   }

   public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
      this.outputOpenStartElement();
      Element element;
      if (this.elementStack == null) {
         element = new Element(this.elementStack, this.namespaceContext, namespaceURI, localName, prefix);
         if (this.signEntireRequestPart != null) {
            this.signEntireRequestPart.setName(new QName(namespaceURI, localName, prefix));
            this.outputProcessorChain.getSecurityContext().putAsMap("signatureParts", this.signEntireRequestPart.getName(), this.signEntireRequestPart);
         }

         if (this.encryptEntireRequestPart != null) {
            this.encryptEntireRequestPart.setName(new QName(namespaceURI, localName, prefix));
            this.outputProcessorChain.getSecurityContext().putAsMap("encryptionParts", this.encryptEntireRequestPart.getName(), this.encryptEntireRequestPart);
         }
      } else {
         element = new Element(this.elementStack, namespaceURI, localName, prefix);
      }

      this.elementStack = element;
      this.openStartElement = element;
   }

   public void writeEmptyElement(String localName) throws XMLStreamException {
      this.writeEmptyElement("", localName, "");
   }

   public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
      this.writeEmptyElement(this.getNamespacePrefix(namespaceURI), localName, namespaceURI);
   }

   public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
      this.writeStartElement(prefix, localName, namespaceURI);
      this.openStartElement.setEmptyElement(true);
      this.haveToWriteEndElement = true;
   }

   public void writeEndElement() throws XMLStreamException {
      this.outputOpenStartElement();
      Element element = this.elementStack;
      this.elementStack = this.elementStack.getParentElement();
      this.chainProcessEvent(XMLSecEventFactory.createXmlSecEndElement(element.getQName()));
   }

   public void writeEndDocument() throws XMLStreamException {
      if (!this.endDocumentWritten) {
         this.outputOpenStartElement();

         while(this.elementStack != null) {
            Element element = this.elementStack;
            this.elementStack = element.getParentElement();
            this.chainProcessEvent(XMLSecEventFactory.createXmlSecEndElement(element.getQName()));
         }

         this.chainProcessEvent(XMLSecEventFactory.createXMLSecEndDocument());
         this.endDocumentWritten = true;
      }

   }

   public void close() throws XMLStreamException {
      try {
         this.writeEndDocument();
         this.outputProcessorChain.reset();
         this.outputProcessorChain.doFinal();
      } catch (XMLSecurityException var2) {
         throw new XMLStreamException(var2);
      }
   }

   public void flush() throws XMLStreamException {
   }

   public void writeAttribute(String localName, String value) throws XMLStreamException {
      this.writeAttribute("", "", localName, value);
   }

   public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
      this.writeAttribute(this.getNamespacePrefix(namespaceURI), namespaceURI, localName, value);
   }

   public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
      if (this.openStartElement == null) {
         throw new XMLStreamException("No open start element.");
      } else {
         this.openStartElement.addAttribute(XMLSecEventFactory.createXMLSecAttribute(new QName(namespaceURI, localName, prefix), value));
      }
   }

   public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
      if (this.openStartElement == null) {
         throw new XMLStreamException("No open start element.");
      } else {
         this.openStartElement.addNamespace(XMLSecEventFactory.createXMLSecNamespace(prefix, namespaceURI));
      }
   }

   public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
      if (this.openStartElement == null) {
         throw new XMLStreamException("No open start element.");
      } else {
         if (this.openStartElement.getElementPrefix().equals("")) {
            this.openStartElement.setElementNamespace(namespaceURI);
            this.openStartElement.setElementPrefix("");
         }

         this.openStartElement.addNamespace(XMLSecEventFactory.createXMLSecNamespace("", namespaceURI));
      }
   }

   public void writeComment(String data) throws XMLStreamException {
      this.outputOpenStartElement();
      this.chainProcessEvent(XMLSecEventFactory.createXMLSecComment(data));
   }

   public void writeProcessingInstruction(String target) throws XMLStreamException {
      this.writeProcessingInstruction(target, "");
   }

   public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
      this.outputOpenStartElement();
      this.chainProcessEvent(XMLSecEventFactory.createXMLSecProcessingInstruction(target, data));
   }

   public void writeCData(String data) throws XMLStreamException {
      this.outputOpenStartElement();
      this.chainProcessEvent(XMLSecEventFactory.createXMLSecCData(data));
   }

   public void writeDTD(String dtd) throws XMLStreamException {
      if (this.elementStack != null) {
         throw new XMLStreamException("Not in proLOG");
      } else {
         this.chainProcessEvent(XMLSecEventFactory.createXMLSecDTD(dtd));
      }
   }

   public void writeEntityRef(String name) throws XMLStreamException {
      this.outputOpenStartElement();
      this.chainProcessEvent(XMLSecEventFactory.createXMLSecEntityReference(name, XMLSecEventFactory.createXmlSecEntityDeclaration(name)));
   }

   public void writeStartDocument() throws XMLStreamException {
      this.writeStartDocument((String)null, (String)null);
   }

   public void writeStartDocument(String version) throws XMLStreamException {
      this.writeStartDocument((String)null, version);
   }

   public void writeStartDocument(String encoding, String version) throws XMLStreamException {
      this.chainProcessEvent(XMLSecEventFactory.createXmlSecStartDocument((String)null, encoding, (Boolean)null, version));
   }

   public void writeCharacters(String text) throws XMLStreamException {
      this.outputOpenStartElement();
      this.chainProcessEvent(XMLSecEventFactory.createXmlSecCharacters(text));
   }

   public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
      this.outputOpenStartElement();
      this.chainProcessEvent(XMLSecEventFactory.createXmlSecCharacters(text, start, len));
   }

   public String getPrefix(String uri) throws XMLStreamException {
      return this.getNamespacePrefix(uri);
   }

   public void setPrefix(String prefix, String uri) throws XMLStreamException {
      if (this.elementStack == null) {
         this.namespaceContext.add(prefix, uri);
      } else {
         this.elementStack.getNamespaceContext().add(prefix, uri);
      }

   }

   public void setDefaultNamespace(String uri) throws XMLStreamException {
      if (this.elementStack == null) {
         this.namespaceContext.add("", uri);
      } else {
         this.elementStack.getNamespaceContext().add("", uri);
      }

   }

   public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
      if (context == null) {
         throw new NullPointerException("context must not be null");
      } else {
         this.namespaceContext = new NSContext(context);
      }
   }

   public NamespaceContext getNamespaceContext() {
      return this.elementStack == null ? this.namespaceContext : this.elementStack.getNamespaceContext();
   }

   public Object getProperty(String name) throws IllegalArgumentException {
      throw new IllegalArgumentException("Properties not supported");
   }

   public SecurePart getSignEntireRequestPart() {
      return this.signEntireRequestPart;
   }

   public void setSignEntireRequestPart(SecurePart signEntireRequestPart) {
      this.signEntireRequestPart = signEntireRequestPart;
   }

   public SecurePart getEncryptEntireRequestPart() {
      return this.encryptEntireRequestPart;
   }

   public void setEncryptEntireRequestPart(SecurePart encryptEntireRequestPart) {
      this.encryptEntireRequestPart = encryptEntireRequestPart;
   }

   private static class NSContext implements NamespaceContext {
      private NamespaceContext parentNamespaceContext;
      private List prefixNsList;

      private NSContext(NamespaceContext parentNamespaceContext) {
         this.prefixNsList = Collections.emptyList();
         this.parentNamespaceContext = parentNamespaceContext;
      }

      public String getNamespaceURI(String prefix) {
         for(int i = 0; i < this.prefixNsList.size(); i += 2) {
            String s = (String)this.prefixNsList.get(i);
            if (s.equals(prefix)) {
               return (String)this.prefixNsList.get(i + 1);
            }
         }

         if (this.parentNamespaceContext != null) {
            return this.parentNamespaceContext.getNamespaceURI(prefix);
         } else {
            return null;
         }
      }

      public String getPrefix(String namespaceURI) {
         for(int i = 1; i < this.prefixNsList.size(); i += 2) {
            String s = (String)this.prefixNsList.get(i);
            if (s.equals(namespaceURI)) {
               return (String)this.prefixNsList.get(i - 1);
            }
         }

         if (this.parentNamespaceContext != null) {
            return this.parentNamespaceContext.getPrefix(namespaceURI);
         } else {
            return null;
         }
      }

      public Iterator getPrefixes(String namespaceURI) {
         List prefixes = new ArrayList(1);

         for(int i = 1; i < this.prefixNsList.size(); i += 2) {
            String s = (String)this.prefixNsList.get(i);
            if (s.equals(namespaceURI)) {
               prefixes.add((String)this.prefixNsList.get(i - 1));
            }
         }

         if (this.parentNamespaceContext != null) {
            Iterator parentPrefixes = this.parentNamespaceContext.getPrefixes(namespaceURI);

            while(parentPrefixes.hasNext()) {
               prefixes.add((String)parentPrefixes.next());
            }
         }

         return prefixes.iterator();
      }

      private void add(String prefix, String namespace) {
         if (this.prefixNsList == Collections.emptyList()) {
            this.prefixNsList = new ArrayList(1);
         }

         this.prefixNsList.add(prefix);
         this.prefixNsList.add(namespace);
      }

      // $FF: synthetic method
      NSContext(NamespaceContext x0, Object x1) {
         this(x0);
      }
   }

   private static class Element {
      private Element parentElement;
      private QName qName;
      private String elementName;
      private String elementNamespace;
      private String elementPrefix;
      private boolean emptyElement;
      private List namespaces;
      private List attributes;
      private NSContext namespaceContext;

      public Element(Element parentElement, String elementNamespace, String elementName, String elementPrefix) {
         this(parentElement, (NSContext)null, elementNamespace, elementName, elementPrefix);
      }

      public Element(Element parentElement, NSContext namespaceContext, String elementNamespace, String elementName, String elementPrefix) {
         this.namespaces = Collections.emptyList();
         this.attributes = Collections.emptyList();
         this.parentElement = parentElement;
         this.namespaceContext = namespaceContext;
         this.elementName = elementName;
         this.setElementNamespace(elementNamespace);
         this.setElementPrefix(elementPrefix);
      }

      private Element getParentElement() {
         return this.parentElement;
      }

      private void setEmptyElement(boolean emptyElement) {
         this.emptyElement = emptyElement;
      }

      private String getElementName() {
         return this.elementName;
      }

      private String getElementNamespace() {
         return this.elementNamespace;
      }

      private void setElementNamespace(String elementNamespace) {
         if (elementNamespace == null) {
            this.elementNamespace = "";
         } else {
            this.elementNamespace = elementNamespace;
         }

         this.qName = null;
      }

      private String getElementPrefix() {
         return this.elementPrefix;
      }

      private void setElementPrefix(String elementPrefix) {
         if (elementPrefix == null) {
            this.elementPrefix = "";
         } else {
            this.elementPrefix = elementPrefix;
         }

         this.qName = null;
      }

      private List getNamespaces() {
         return this.namespaces;
      }

      private void addNamespace(XMLSecNamespace namespace) {
         if (this.namespaces == Collections.emptyList()) {
            this.namespaces = new ArrayList(1);
         }

         this.namespaces.add(namespace);
         this.getNamespaceContext().add(namespace.getPrefix(), namespace.getNamespaceURI());
      }

      private List getAttributes() {
         return this.attributes;
      }

      private void addAttribute(XMLSecAttribute attribute) {
         if (this.attributes == Collections.emptyList()) {
            this.attributes = new ArrayList(1);
         }

         this.attributes.add(attribute);
      }

      private NSContext getNamespaceContext() {
         if (this.namespaceContext == null) {
            if (this.emptyElement) {
               this.namespaceContext = this.parentElement.getNamespaceContext();
            } else if (this.parentElement != null) {
               this.namespaceContext = new NSContext(this.parentElement.getNamespaceContext());
            } else {
               this.namespaceContext = new NSContext((NamespaceContext)null);
            }
         }

         return this.namespaceContext;
      }

      private QName getQName() {
         if (this.qName == null) {
            this.qName = new QName(this.getElementNamespace(), this.getElementName(), this.getElementPrefix());
         }

         return this.qName;
      }
   }
}
