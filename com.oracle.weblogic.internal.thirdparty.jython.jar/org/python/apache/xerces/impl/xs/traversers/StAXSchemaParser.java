package org.python.apache.xerces.impl.xs.traversers;

import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.Location;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.ProcessingInstruction;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.python.apache.xerces.impl.xs.opti.SchemaDOMParser;
import org.python.apache.xerces.util.JAXPNamespaceContextWrapper;
import org.python.apache.xerces.util.StAXLocationWrapper;
import org.python.apache.xerces.util.SymbolTable;
import org.python.apache.xerces.util.XMLAttributesImpl;
import org.python.apache.xerces.util.XMLStringBuffer;
import org.python.apache.xerces.util.XMLSymbols;
import org.python.apache.xerces.xni.Augmentations;
import org.python.apache.xerces.xni.QName;
import org.python.apache.xerces.xni.XMLString;
import org.python.apache.xerces.xni.XNIException;
import org.w3c.dom.Document;

final class StAXSchemaParser {
   private static final int CHUNK_SIZE = 1024;
   private static final int CHUNK_MASK = 1023;
   private final char[] fCharBuffer = new char[1024];
   private SymbolTable fSymbolTable;
   private SchemaDOMParser fSchemaDOMParser;
   private final StAXLocationWrapper fLocationWrapper = new StAXLocationWrapper();
   private final JAXPNamespaceContextWrapper fNamespaceContext;
   private final QName fElementQName;
   private final QName fAttributeQName;
   private final XMLAttributesImpl fAttributes;
   private final XMLString fTempString;
   private final ArrayList fDeclaredPrefixes;
   private final XMLStringBuffer fStringBuffer;
   private int fDepth;

   public StAXSchemaParser() {
      this.fNamespaceContext = new JAXPNamespaceContextWrapper(this.fSymbolTable);
      this.fElementQName = new QName();
      this.fAttributeQName = new QName();
      this.fAttributes = new XMLAttributesImpl();
      this.fTempString = new XMLString();
      this.fDeclaredPrefixes = new ArrayList();
      this.fStringBuffer = new XMLStringBuffer();
      this.fNamespaceContext.setDeclaredPrefixes(this.fDeclaredPrefixes);
   }

   public void reset(SchemaDOMParser var1, SymbolTable var2) {
      this.fSchemaDOMParser = var1;
      this.fSymbolTable = var2;
      this.fNamespaceContext.setSymbolTable(this.fSymbolTable);
      this.fNamespaceContext.reset();
   }

   public Document getDocument() {
      return this.fSchemaDOMParser.getDocument();
   }

   public void parse(XMLEventReader var1) throws XMLStreamException, XNIException {
      XMLEvent var2 = var1.peek();
      if (var2 != null) {
         int var3 = var2.getEventType();
         if (var3 != 7 && var3 != 1) {
            throw new XMLStreamException();
         }

         this.fLocationWrapper.setLocation(var2.getLocation());
         this.fSchemaDOMParser.startDocument(this.fLocationWrapper, (String)null, this.fNamespaceContext, (Augmentations)null);

         label34:
         while(var1.hasNext()) {
            var2 = var1.nextEvent();
            var3 = var2.getEventType();
            switch (var3) {
               case 1:
                  ++this.fDepth;
                  StartElement var4 = var2.asStartElement();
                  this.fillQName(this.fElementQName, var4.getName());
                  this.fLocationWrapper.setLocation(var4.getLocation());
                  this.fNamespaceContext.setNamespaceContext(var4.getNamespaceContext());
                  this.fillXMLAttributes(var4);
                  this.fillDeclaredPrefixes(var4);
                  this.addNamespaceDeclarations();
                  this.fNamespaceContext.pushContext();
                  this.fSchemaDOMParser.startElement(this.fElementQName, this.fAttributes, (Augmentations)null);
                  break;
               case 2:
                  EndElement var5 = var2.asEndElement();
                  this.fillQName(this.fElementQName, var5.getName());
                  this.fillDeclaredPrefixes(var5);
                  this.fLocationWrapper.setLocation(var5.getLocation());
                  this.fSchemaDOMParser.endElement(this.fElementQName, (Augmentations)null);
                  this.fNamespaceContext.popContext();
                  --this.fDepth;
                  if (this.fDepth <= 0) {
                     break label34;
                  }
                  break;
               case 3:
                  ProcessingInstruction var6 = (ProcessingInstruction)var2;
                  this.fillProcessingInstruction(var6.getData());
                  this.fSchemaDOMParser.processingInstruction(var6.getTarget(), this.fTempString, (Augmentations)null);
                  break;
               case 4:
                  this.sendCharactersToSchemaParser(var2.asCharacters().getData(), false);
               case 5:
               case 8:
               case 9:
               case 10:
               case 11:
               default:
                  break;
               case 6:
                  this.sendCharactersToSchemaParser(var2.asCharacters().getData(), true);
                  break;
               case 7:
                  ++this.fDepth;
                  break;
               case 12:
                  this.fSchemaDOMParser.startCDATA((Augmentations)null);
                  this.sendCharactersToSchemaParser(var2.asCharacters().getData(), false);
                  this.fSchemaDOMParser.endCDATA((Augmentations)null);
            }
         }

         this.fLocationWrapper.setLocation((Location)null);
         this.fNamespaceContext.setNamespaceContext((NamespaceContext)null);
         this.fSchemaDOMParser.endDocument((Augmentations)null);
      }

   }

   public void parse(XMLStreamReader var1) throws XMLStreamException, XNIException {
      if (var1.hasNext()) {
         int var2 = var1.getEventType();
         if (var2 != 7 && var2 != 1) {
            throw new XMLStreamException();
         }

         this.fLocationWrapper.setLocation(var1.getLocation());
         this.fSchemaDOMParser.startDocument(this.fLocationWrapper, (String)null, this.fNamespaceContext, (Augmentations)null);
         boolean var3 = true;

         label39:
         while(var1.hasNext()) {
            if (!var3) {
               var2 = var1.next();
            } else {
               var3 = false;
            }

            switch (var2) {
               case 1:
                  ++this.fDepth;
                  this.fLocationWrapper.setLocation(var1.getLocation());
                  this.fNamespaceContext.setNamespaceContext(var1.getNamespaceContext());
                  this.fillQName(this.fElementQName, var1.getNamespaceURI(), var1.getLocalName(), var1.getPrefix());
                  this.fillXMLAttributes(var1);
                  this.fillDeclaredPrefixes(var1);
                  this.addNamespaceDeclarations();
                  this.fNamespaceContext.pushContext();
                  this.fSchemaDOMParser.startElement(this.fElementQName, this.fAttributes, (Augmentations)null);
                  break;
               case 2:
                  this.fLocationWrapper.setLocation(var1.getLocation());
                  this.fNamespaceContext.setNamespaceContext(var1.getNamespaceContext());
                  this.fillQName(this.fElementQName, var1.getNamespaceURI(), var1.getLocalName(), var1.getPrefix());
                  this.fillDeclaredPrefixes(var1);
                  this.fSchemaDOMParser.endElement(this.fElementQName, (Augmentations)null);
                  this.fNamespaceContext.popContext();
                  --this.fDepth;
                  if (this.fDepth <= 0) {
                     break label39;
                  }
                  break;
               case 3:
                  this.fillProcessingInstruction(var1.getPIData());
                  this.fSchemaDOMParser.processingInstruction(var1.getPITarget(), this.fTempString, (Augmentations)null);
                  break;
               case 4:
                  this.fTempString.setValues(var1.getTextCharacters(), var1.getTextStart(), var1.getTextLength());
                  this.fSchemaDOMParser.characters(this.fTempString, (Augmentations)null);
               case 5:
               case 8:
               case 9:
               case 10:
               case 11:
               default:
                  break;
               case 6:
                  this.fTempString.setValues(var1.getTextCharacters(), var1.getTextStart(), var1.getTextLength());
                  this.fSchemaDOMParser.ignorableWhitespace(this.fTempString, (Augmentations)null);
                  break;
               case 7:
                  ++this.fDepth;
                  break;
               case 12:
                  this.fSchemaDOMParser.startCDATA((Augmentations)null);
                  this.fTempString.setValues(var1.getTextCharacters(), var1.getTextStart(), var1.getTextLength());
                  this.fSchemaDOMParser.characters(this.fTempString, (Augmentations)null);
                  this.fSchemaDOMParser.endCDATA((Augmentations)null);
            }
         }

         this.fLocationWrapper.setLocation((Location)null);
         this.fNamespaceContext.setNamespaceContext((NamespaceContext)null);
         this.fSchemaDOMParser.endDocument((Augmentations)null);
      }

   }

   private void sendCharactersToSchemaParser(String var1, boolean var2) {
      if (var1 != null) {
         int var3 = var1.length();
         int var4 = var3 & 1023;
         if (var4 > 0) {
            var1.getChars(0, var4, this.fCharBuffer, 0);
            this.fTempString.setValues(this.fCharBuffer, 0, var4);
            if (var2) {
               this.fSchemaDOMParser.ignorableWhitespace(this.fTempString, (Augmentations)null);
            } else {
               this.fSchemaDOMParser.characters(this.fTempString, (Augmentations)null);
            }
         }

         int var5 = var4;

         while(var5 < var3) {
            int var10001 = var5;
            var5 += 1024;
            var1.getChars(var10001, var5, this.fCharBuffer, 0);
            this.fTempString.setValues(this.fCharBuffer, 0, 1024);
            if (var2) {
               this.fSchemaDOMParser.ignorableWhitespace(this.fTempString, (Augmentations)null);
            } else {
               this.fSchemaDOMParser.characters(this.fTempString, (Augmentations)null);
            }
         }
      }

   }

   private void fillProcessingInstruction(String var1) {
      int var2 = var1.length();
      char[] var3 = this.fCharBuffer;
      if (var3.length < var2) {
         var3 = var1.toCharArray();
      } else {
         var1.getChars(0, var2, var3, 0);
      }

      this.fTempString.setValues(var3, 0, var2);
   }

   private void fillXMLAttributes(StartElement var1) {
      this.fAttributes.removeAllAttributes();
      Iterator var2 = var1.getAttributes();

      while(var2.hasNext()) {
         Attribute var3 = (Attribute)var2.next();
         this.fillQName(this.fAttributeQName, var3.getName());
         String var4 = var3.getDTDType();
         int var5 = this.fAttributes.getLength();
         this.fAttributes.addAttributeNS(this.fAttributeQName, var4 != null ? var4 : XMLSymbols.fCDATASymbol, var3.getValue());
         this.fAttributes.setSpecified(var5, var3.isSpecified());
      }

   }

   private void fillXMLAttributes(XMLStreamReader var1) {
      this.fAttributes.removeAllAttributes();
      int var2 = var1.getAttributeCount();

      for(int var3 = 0; var3 < var2; ++var3) {
         this.fillQName(this.fAttributeQName, var1.getAttributeNamespace(var3), var1.getAttributeLocalName(var3), var1.getAttributePrefix(var3));
         String var4 = var1.getAttributeType(var3);
         this.fAttributes.addAttributeNS(this.fAttributeQName, var4 != null ? var4 : XMLSymbols.fCDATASymbol, var1.getAttributeValue(var3));
         this.fAttributes.setSpecified(var3, var1.isAttributeSpecified(var3));
      }

   }

   private void addNamespaceDeclarations() {
      String var1 = null;
      String var2 = null;
      String var3 = null;
      String var4 = null;
      String var5 = null;
      Iterator var6 = this.fDeclaredPrefixes.iterator();

      while(var6.hasNext()) {
         var4 = (String)var6.next();
         var5 = this.fNamespaceContext.getURI(var4);
         if (var4.length() > 0) {
            var1 = XMLSymbols.PREFIX_XMLNS;
            var2 = var4;
            this.fStringBuffer.clear();
            this.fStringBuffer.append(var1);
            this.fStringBuffer.append(':');
            this.fStringBuffer.append(var4);
            var3 = this.fSymbolTable.addSymbol(this.fStringBuffer.ch, this.fStringBuffer.offset, this.fStringBuffer.length);
         } else {
            var1 = XMLSymbols.EMPTY_STRING;
            var2 = XMLSymbols.PREFIX_XMLNS;
            var3 = XMLSymbols.PREFIX_XMLNS;
         }

         this.fAttributeQName.setValues(var1, var2, var3, org.python.apache.xerces.xni.NamespaceContext.XMLNS_URI);
         this.fAttributes.addAttribute(this.fAttributeQName, XMLSymbols.fCDATASymbol, var5 != null ? var5 : XMLSymbols.EMPTY_STRING);
      }

   }

   private void fillDeclaredPrefixes(StartElement var1) {
      this.fillDeclaredPrefixes(var1.getNamespaces());
   }

   private void fillDeclaredPrefixes(EndElement var1) {
      this.fillDeclaredPrefixes(var1.getNamespaces());
   }

   private void fillDeclaredPrefixes(Iterator var1) {
      this.fDeclaredPrefixes.clear();

      while(var1.hasNext()) {
         Namespace var2 = (Namespace)var1.next();
         String var3 = var2.getPrefix();
         this.fDeclaredPrefixes.add(var3 != null ? var3 : "");
      }

   }

   private void fillDeclaredPrefixes(XMLStreamReader var1) {
      this.fDeclaredPrefixes.clear();
      int var2 = var1.getNamespaceCount();

      for(int var3 = 0; var3 < var2; ++var3) {
         String var4 = var1.getNamespacePrefix(var3);
         this.fDeclaredPrefixes.add(var4 != null ? var4 : "");
      }

   }

   private void fillQName(QName var1, javax.xml.namespace.QName var2) {
      this.fillQName(var1, var2.getNamespaceURI(), var2.getLocalPart(), var2.getPrefix());
   }

   final void fillQName(QName var1, String var2, String var3, String var4) {
      var2 = var2 != null && var2.length() > 0 ? this.fSymbolTable.addSymbol(var2) : null;
      var3 = var3 != null ? this.fSymbolTable.addSymbol(var3) : XMLSymbols.EMPTY_STRING;
      var4 = var4 != null && var4.length() > 0 ? this.fSymbolTable.addSymbol(var4) : XMLSymbols.EMPTY_STRING;
      String var5 = var3;
      if (var4 != XMLSymbols.EMPTY_STRING) {
         this.fStringBuffer.clear();
         this.fStringBuffer.append(var4);
         this.fStringBuffer.append(':');
         this.fStringBuffer.append(var3);
         var5 = this.fSymbolTable.addSymbol(this.fStringBuffer.ch, this.fStringBuffer.offset, this.fStringBuffer.length);
      }

      var1.setValues(var4, var3, var5, var2);
   }
}
