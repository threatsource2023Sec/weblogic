package weblogic.apache.xerces.jaxp.validation;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.Comment;
import javax.xml.stream.events.DTD;
import javax.xml.stream.events.EndDocument;
import javax.xml.stream.events.EntityReference;
import javax.xml.stream.events.ProcessingInstruction;
import javax.xml.stream.events.StartDocument;
import javax.xml.transform.stax.StAXResult;
import weblogic.apache.xerces.util.JAXPNamespaceContextWrapper;
import weblogic.apache.xerces.xni.Augmentations;
import weblogic.apache.xerces.xni.NamespaceContext;
import weblogic.apache.xerces.xni.QName;
import weblogic.apache.xerces.xni.XMLAttributes;
import weblogic.apache.xerces.xni.XMLLocator;
import weblogic.apache.xerces.xni.XMLResourceIdentifier;
import weblogic.apache.xerces.xni.XMLString;
import weblogic.apache.xerces.xni.XNIException;
import weblogic.apache.xerces.xni.parser.XMLDocumentSource;

final class StAXStreamResultBuilder implements StAXDocumentHandler {
   private XMLStreamWriter fStreamWriter;
   private final JAXPNamespaceContextWrapper fNamespaceContext;
   private boolean fIgnoreChars;
   private boolean fInCDATA;
   private final QName fAttrName = new QName();

   public StAXStreamResultBuilder(JAXPNamespaceContextWrapper var1) {
      this.fNamespaceContext = var1;
   }

   public void setStAXResult(StAXResult var1) {
      this.fIgnoreChars = false;
      this.fInCDATA = false;
      this.fAttrName.clear();
      this.fStreamWriter = var1 != null ? var1.getXMLStreamWriter() : null;
   }

   public void startDocument(XMLStreamReader var1) throws XMLStreamException {
      String var2 = var1.getVersion();
      String var3 = var1.getCharacterEncodingScheme();
      this.fStreamWriter.writeStartDocument(var3 != null ? var3 : "UTF-8", var2 != null ? var2 : "1.0");
   }

   public void endDocument(XMLStreamReader var1) throws XMLStreamException {
      this.fStreamWriter.writeEndDocument();
      this.fStreamWriter.flush();
   }

   public void comment(XMLStreamReader var1) throws XMLStreamException {
      this.fStreamWriter.writeComment(var1.getText());
   }

   public void processingInstruction(XMLStreamReader var1) throws XMLStreamException {
      String var2 = var1.getPIData();
      if (var2 != null && var2.length() > 0) {
         this.fStreamWriter.writeProcessingInstruction(var1.getPITarget(), var2);
      } else {
         this.fStreamWriter.writeProcessingInstruction(var1.getPITarget());
      }

   }

   public void entityReference(XMLStreamReader var1) throws XMLStreamException {
      this.fStreamWriter.writeEntityRef(var1.getLocalName());
   }

   public void startDocument(StartDocument var1) throws XMLStreamException {
      String var2 = var1.getVersion();
      String var3 = var1.getCharacterEncodingScheme();
      this.fStreamWriter.writeStartDocument(var3 != null ? var3 : "UTF-8", var2 != null ? var2 : "1.0");
   }

   public void endDocument(EndDocument var1) throws XMLStreamException {
      this.fStreamWriter.writeEndDocument();
      this.fStreamWriter.flush();
   }

   public void doctypeDecl(DTD var1) throws XMLStreamException {
      this.fStreamWriter.writeDTD(var1.getDocumentTypeDeclaration());
   }

   public void characters(Characters var1) throws XMLStreamException {
      this.fStreamWriter.writeCharacters(var1.getData());
   }

   public void cdata(Characters var1) throws XMLStreamException {
      this.fStreamWriter.writeCData(var1.getData());
   }

   public void comment(Comment var1) throws XMLStreamException {
      this.fStreamWriter.writeComment(var1.getText());
   }

   public void processingInstruction(ProcessingInstruction var1) throws XMLStreamException {
      String var2 = var1.getData();
      if (var2 != null && var2.length() > 0) {
         this.fStreamWriter.writeProcessingInstruction(var1.getTarget(), var2);
      } else {
         this.fStreamWriter.writeProcessingInstruction(var1.getTarget());
      }

   }

   public void entityReference(EntityReference var1) throws XMLStreamException {
      this.fStreamWriter.writeEntityRef(var1.getName());
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
         if (var1.prefix.length() > 0) {
            this.fStreamWriter.writeStartElement(var1.prefix, var1.localpart, var1.uri != null ? var1.uri : "");
         } else if (var1.uri != null) {
            this.fStreamWriter.writeStartElement(var1.uri, var1.localpart);
         } else {
            this.fStreamWriter.writeStartElement(var1.localpart);
         }

         int var4 = this.fNamespaceContext.getDeclaredPrefixCount();
         javax.xml.namespace.NamespaceContext var5 = this.fNamespaceContext.getNamespaceContext();

         for(int var6 = 0; var6 < var4; ++var6) {
            String var7 = this.fNamespaceContext.getDeclaredPrefixAt(var6);
            String var8 = var5.getNamespaceURI(var7);
            if (var7.length() == 0) {
               this.fStreamWriter.writeDefaultNamespace(var8 != null ? var8 : "");
            } else {
               this.fStreamWriter.writeNamespace(var7, var8 != null ? var8 : "");
            }
         }

         var4 = var2.getLength();

         for(int var10 = 0; var10 < var4; ++var10) {
            var2.getName(var10, this.fAttrName);
            if (this.fAttrName.prefix.length() > 0) {
               this.fStreamWriter.writeAttribute(this.fAttrName.prefix, this.fAttrName.uri != null ? this.fAttrName.uri : "", this.fAttrName.localpart, var2.getValue(var10));
            } else if (this.fAttrName.uri != null) {
               this.fStreamWriter.writeAttribute(this.fAttrName.uri, this.fAttrName.localpart, var2.getValue(var10));
            } else {
               this.fStreamWriter.writeAttribute(this.fAttrName.localpart, var2.getValue(var10));
            }
         }

      } catch (XMLStreamException var9) {
         throw new XNIException(var9);
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
               this.fStreamWriter.writeCharacters(var1.ch, var1.offset, var1.length);
            } else {
               this.fStreamWriter.writeCData(var1.toString());
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
         this.fStreamWriter.writeEndElement();
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
}
