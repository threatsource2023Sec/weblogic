package com.bea.core.repackaged.springframework.util.xml;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

abstract class AbstractStaxHandler implements ContentHandler, LexicalHandler {
   private final List namespaceMappings = new ArrayList();
   private boolean inCData;

   public final void startDocument() throws SAXException {
      this.removeAllNamespaceMappings();
      this.newNamespaceMapping();

      try {
         this.startDocumentInternal();
      } catch (XMLStreamException var2) {
         throw new SAXException("Could not handle startDocument: " + var2.getMessage(), var2);
      }
   }

   public final void endDocument() throws SAXException {
      this.removeAllNamespaceMappings();

      try {
         this.endDocumentInternal();
      } catch (XMLStreamException var2) {
         throw new SAXException("Could not handle endDocument: " + var2.getMessage(), var2);
      }
   }

   public final void startPrefixMapping(String prefix, String uri) {
      this.currentNamespaceMapping().put(prefix, uri);
   }

   public final void endPrefixMapping(String prefix) {
   }

   public final void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
      try {
         this.startElementInternal(this.toQName(uri, qName), atts, this.currentNamespaceMapping());
         this.newNamespaceMapping();
      } catch (XMLStreamException var6) {
         throw new SAXException("Could not handle startElement: " + var6.getMessage(), var6);
      }
   }

   public final void endElement(String uri, String localName, String qName) throws SAXException {
      try {
         this.endElementInternal(this.toQName(uri, qName), this.currentNamespaceMapping());
         this.removeNamespaceMapping();
      } catch (XMLStreamException var5) {
         throw new SAXException("Could not handle endElement: " + var5.getMessage(), var5);
      }
   }

   public final void characters(char[] ch, int start, int length) throws SAXException {
      try {
         String data = new String(ch, start, length);
         if (!this.inCData) {
            this.charactersInternal(data);
         } else {
            this.cDataInternal(data);
         }

      } catch (XMLStreamException var5) {
         throw new SAXException("Could not handle characters: " + var5.getMessage(), var5);
      }
   }

   public final void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
      try {
         this.ignorableWhitespaceInternal(new String(ch, start, length));
      } catch (XMLStreamException var5) {
         throw new SAXException("Could not handle ignorableWhitespace:" + var5.getMessage(), var5);
      }
   }

   public final void processingInstruction(String target, String data) throws SAXException {
      try {
         this.processingInstructionInternal(target, data);
      } catch (XMLStreamException var4) {
         throw new SAXException("Could not handle processingInstruction: " + var4.getMessage(), var4);
      }
   }

   public final void skippedEntity(String name) throws SAXException {
      try {
         this.skippedEntityInternal(name);
      } catch (XMLStreamException var3) {
         throw new SAXException("Could not handle skippedEntity: " + var3.getMessage(), var3);
      }
   }

   public final void startDTD(String name, @Nullable String publicId, String systemId) throws SAXException {
      try {
         StringBuilder builder = new StringBuilder("<!DOCTYPE ");
         builder.append(name);
         if (publicId != null) {
            builder.append(" PUBLIC \"");
            builder.append(publicId);
            builder.append("\" \"");
         } else {
            builder.append(" SYSTEM \"");
         }

         builder.append(systemId);
         builder.append("\">");
         this.dtdInternal(builder.toString());
      } catch (XMLStreamException var5) {
         throw new SAXException("Could not handle startDTD: " + var5.getMessage(), var5);
      }
   }

   public final void endDTD() throws SAXException {
   }

   public final void startCDATA() throws SAXException {
      this.inCData = true;
   }

   public final void endCDATA() throws SAXException {
      this.inCData = false;
   }

   public final void comment(char[] ch, int start, int length) throws SAXException {
      try {
         this.commentInternal(new String(ch, start, length));
      } catch (XMLStreamException var5) {
         throw new SAXException("Could not handle comment: " + var5.getMessage(), var5);
      }
   }

   public void startEntity(String name) throws SAXException {
   }

   public void endEntity(String name) throws SAXException {
   }

   protected QName toQName(String namespaceUri, String qualifiedName) {
      int idx = qualifiedName.indexOf(58);
      if (idx == -1) {
         return new QName(namespaceUri, qualifiedName);
      } else {
         String prefix = qualifiedName.substring(0, idx);
         String localPart = qualifiedName.substring(idx + 1);
         return new QName(namespaceUri, localPart, prefix);
      }
   }

   protected boolean isNamespaceDeclaration(QName qName) {
      String prefix = qName.getPrefix();
      String localPart = qName.getLocalPart();
      return "xmlns".equals(localPart) && prefix.isEmpty() || "xmlns".equals(prefix) && !localPart.isEmpty();
   }

   private Map currentNamespaceMapping() {
      return (Map)this.namespaceMappings.get(this.namespaceMappings.size() - 1);
   }

   private void newNamespaceMapping() {
      this.namespaceMappings.add(new HashMap());
   }

   private void removeNamespaceMapping() {
      this.namespaceMappings.remove(this.namespaceMappings.size() - 1);
   }

   private void removeAllNamespaceMappings() {
      this.namespaceMappings.clear();
   }

   protected abstract void startDocumentInternal() throws XMLStreamException;

   protected abstract void endDocumentInternal() throws XMLStreamException;

   protected abstract void startElementInternal(QName var1, Attributes var2, Map var3) throws XMLStreamException;

   protected abstract void endElementInternal(QName var1, Map var2) throws XMLStreamException;

   protected abstract void charactersInternal(String var1) throws XMLStreamException;

   protected abstract void cDataInternal(String var1) throws XMLStreamException;

   protected abstract void ignorableWhitespaceInternal(String var1) throws XMLStreamException;

   protected abstract void processingInstructionInternal(String var1, String var2) throws XMLStreamException;

   protected abstract void skippedEntityInternal(String var1) throws XMLStreamException;

   protected abstract void dtdInternal(String var1) throws XMLStreamException;

   protected abstract void commentInternal(String var1) throws XMLStreamException;
}
