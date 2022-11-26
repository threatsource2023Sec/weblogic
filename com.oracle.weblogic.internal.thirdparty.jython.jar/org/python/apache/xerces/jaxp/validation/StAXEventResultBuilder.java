package org.python.apache.xerces.jaxp.validation;

import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.Comment;
import javax.xml.stream.events.DTD;
import javax.xml.stream.events.EndDocument;
import javax.xml.stream.events.EntityReference;
import javax.xml.stream.events.ProcessingInstruction;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stax.StAXResult;
import org.python.apache.xerces.util.JAXPNamespaceContextWrapper;
import org.python.apache.xerces.xni.Augmentations;
import org.python.apache.xerces.xni.NamespaceContext;
import org.python.apache.xerces.xni.QName;
import org.python.apache.xerces.xni.XMLAttributes;
import org.python.apache.xerces.xni.XMLLocator;
import org.python.apache.xerces.xni.XMLResourceIdentifier;
import org.python.apache.xerces.xni.XMLString;
import org.python.apache.xerces.xni.XNIException;
import org.python.apache.xerces.xni.parser.XMLDocumentSource;

final class StAXEventResultBuilder implements StAXDocumentHandler {
   private XMLEventWriter fEventWriter;
   private final XMLEventFactory fEventFactory;
   private final StAXValidatorHelper fStAXValidatorHelper;
   private final JAXPNamespaceContextWrapper fNamespaceContext;
   private boolean fIgnoreChars;
   private boolean fInCDATA;
   private final QName fAttrName = new QName();
   private static final Iterator EMPTY_COLLECTION_ITERATOR = new Iterator() {
      public boolean hasNext() {
         return false;
      }

      public Object next() {
         throw new NoSuchElementException();
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   };

   public StAXEventResultBuilder(StAXValidatorHelper var1, JAXPNamespaceContextWrapper var2) {
      this.fStAXValidatorHelper = var1;
      this.fNamespaceContext = var2;
      this.fEventFactory = XMLEventFactory.newInstance();
   }

   public void setStAXResult(StAXResult var1) {
      this.fIgnoreChars = false;
      this.fInCDATA = false;
      this.fEventWriter = var1 != null ? var1.getXMLEventWriter() : null;
   }

   public void startDocument(XMLStreamReader var1) throws XMLStreamException {
      String var2 = var1.getVersion();
      String var3 = var1.getCharacterEncodingScheme();
      boolean var4 = var1.standaloneSet();
      this.fEventWriter.add(this.fEventFactory.createStartDocument(var3 != null ? var3 : "UTF-8", var2 != null ? var2 : "1.0", var4));
   }

   public void endDocument(XMLStreamReader var1) throws XMLStreamException {
      this.fEventWriter.add(this.fEventFactory.createEndDocument());
      this.fEventWriter.flush();
   }

   public void comment(XMLStreamReader var1) throws XMLStreamException {
      this.fEventWriter.add(this.fEventFactory.createComment(var1.getText()));
   }

   public void processingInstruction(XMLStreamReader var1) throws XMLStreamException {
      String var2 = var1.getPIData();
      this.fEventWriter.add(this.fEventFactory.createProcessingInstruction(var1.getPITarget(), var2 != null ? var2 : ""));
   }

   public void entityReference(XMLStreamReader var1) throws XMLStreamException {
      String var2 = var1.getLocalName();
      this.fEventWriter.add(this.fEventFactory.createEntityReference(var2, this.fStAXValidatorHelper.getEntityDeclaration(var2)));
   }

   public void startDocument(StartDocument var1) throws XMLStreamException {
      this.fEventWriter.add(var1);
   }

   public void endDocument(EndDocument var1) throws XMLStreamException {
      this.fEventWriter.add(var1);
      this.fEventWriter.flush();
   }

   public void doctypeDecl(DTD var1) throws XMLStreamException {
      this.fEventWriter.add(var1);
   }

   public void characters(Characters var1) throws XMLStreamException {
      this.fEventWriter.add(var1);
   }

   public void cdata(Characters var1) throws XMLStreamException {
      this.fEventWriter.add(var1);
   }

   public void comment(Comment var1) throws XMLStreamException {
      this.fEventWriter.add(var1);
   }

   public void processingInstruction(ProcessingInstruction var1) throws XMLStreamException {
      this.fEventWriter.add(var1);
   }

   public void entityReference(EntityReference var1) throws XMLStreamException {
      this.fEventWriter.add(var1);
   }

   public void setIgnoringCharacters(boolean var1) {
      this.fIgnoreChars = var1;
   }

   public void startDocument(XMLLocator var1, String var2, NamespaceContext var3, Augmentations var4) throws XNIException {
   }

   public void xmlDecl(String var1, String var2, String var3, Augmentations var4) throws XNIException {
   }

   public void doctypeDecl(String var1, String var2, String var3, Augmentations var4) throws XNIException {
   }

   public void comment(XMLString var1, Augmentations var2) throws XNIException {
   }

   public void processingInstruction(String var1, XMLString var2, Augmentations var3) throws XNIException {
   }

   public void startElement(QName var1, XMLAttributes var2, Augmentations var3) throws XNIException {
      try {
         int var4 = var2.getLength();
         if (var4 == 0) {
            XMLEvent var5 = this.fStAXValidatorHelper.getCurrentEvent();
            if (var5 != null) {
               this.fEventWriter.add(var5);
               return;
            }
         }

         this.fEventWriter.add(this.fEventFactory.createStartElement(var1.prefix, var1.uri != null ? var1.uri : "", var1.localpart, this.getAttributeIterator(var2, var4), this.getNamespaceIterator(), this.fNamespaceContext.getNamespaceContext()));
      } catch (XMLStreamException var6) {
         throw new XNIException(var6);
      }
   }

   public void emptyElement(QName var1, XMLAttributes var2, Augmentations var3) throws XNIException {
      this.startElement(var1, var2, var3);
      this.endElement(var1, var3);
   }

   public void startGeneralEntity(String var1, XMLResourceIdentifier var2, String var3, Augmentations var4) throws XNIException {
   }

   public void textDecl(String var1, String var2, Augmentations var3) throws XNIException {
   }

   public void endGeneralEntity(String var1, Augmentations var2) throws XNIException {
   }

   public void characters(XMLString var1, Augmentations var2) throws XNIException {
      if (!this.fIgnoreChars) {
         try {
            if (!this.fInCDATA) {
               this.fEventWriter.add(this.fEventFactory.createCharacters(var1.toString()));
            } else {
               this.fEventWriter.add(this.fEventFactory.createCData(var1.toString()));
            }
         } catch (XMLStreamException var4) {
            throw new XNIException(var4);
         }
      }

   }

   public void ignorableWhitespace(XMLString var1, Augmentations var2) throws XNIException {
      this.characters(var1, var2);
   }

   public void endElement(QName var1, Augmentations var2) throws XNIException {
      try {
         XMLEvent var3 = this.fStAXValidatorHelper.getCurrentEvent();
         if (var3 != null) {
            this.fEventWriter.add(var3);
         } else {
            this.fEventWriter.add(this.fEventFactory.createEndElement(var1.prefix, var1.uri, var1.localpart, this.getNamespaceIterator()));
         }

      } catch (XMLStreamException var4) {
         throw new XNIException(var4);
      }
   }

   public void startCDATA(Augmentations var1) throws XNIException {
      this.fInCDATA = true;
   }

   public void endCDATA(Augmentations var1) throws XNIException {
      this.fInCDATA = false;
   }

   public void endDocument(Augmentations var1) throws XNIException {
   }

   public void setDocumentSource(XMLDocumentSource var1) {
   }

   public XMLDocumentSource getDocumentSource() {
      return null;
   }

   private Iterator getAttributeIterator(XMLAttributes var1, int var2) {
      return (Iterator)(var2 > 0 ? new AttributeIterator(var1, var2) : EMPTY_COLLECTION_ITERATOR);
   }

   private Iterator getNamespaceIterator() {
      int var1 = this.fNamespaceContext.getDeclaredPrefixCount();
      return (Iterator)(var1 > 0 ? new NamespaceIterator(var1) : EMPTY_COLLECTION_ITERATOR);
   }

   final class NamespaceIterator implements Iterator {
      javax.xml.namespace.NamespaceContext fNC;
      int fIndex;
      int fEnd;

      NamespaceIterator(int var2) {
         this.fNC = StAXEventResultBuilder.this.fNamespaceContext.getNamespaceContext();
         this.fIndex = 0;
         this.fEnd = var2;
      }

      public boolean hasNext() {
         if (this.fIndex < this.fEnd) {
            return true;
         } else {
            this.fNC = null;
            return false;
         }
      }

      public Object next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            String var1 = StAXEventResultBuilder.this.fNamespaceContext.getDeclaredPrefixAt(this.fIndex++);
            String var2 = this.fNC.getNamespaceURI(var1);
            return var1.length() == 0 ? StAXEventResultBuilder.this.fEventFactory.createNamespace(var2 != null ? var2 : "") : StAXEventResultBuilder.this.fEventFactory.createNamespace(var1, var2 != null ? var2 : "");
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

   final class AttributeIterator implements Iterator {
      XMLAttributes fAttributes;
      int fIndex;
      int fEnd;

      AttributeIterator(XMLAttributes var2, int var3) {
         this.fAttributes = var2;
         this.fIndex = 0;
         this.fEnd = var3;
      }

      public boolean hasNext() {
         if (this.fIndex < this.fEnd) {
            return true;
         } else {
            this.fAttributes = null;
            return false;
         }
      }

      public Object next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            this.fAttributes.getName(this.fIndex, StAXEventResultBuilder.this.fAttrName);
            return StAXEventResultBuilder.this.fEventFactory.createAttribute(StAXEventResultBuilder.this.fAttrName.prefix, StAXEventResultBuilder.this.fAttrName.uri != null ? StAXEventResultBuilder.this.fAttrName.uri : "", StAXEventResultBuilder.this.fAttrName.localpart, this.fAttributes.getValue(this.fIndex++));
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
}
