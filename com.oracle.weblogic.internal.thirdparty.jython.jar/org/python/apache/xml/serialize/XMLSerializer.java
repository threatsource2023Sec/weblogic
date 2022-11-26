package org.python.apache.xml.serialize;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;
import org.python.apache.xerces.dom.DOMMessageFormatter;
import org.python.apache.xerces.util.NamespaceSupport;
import org.python.apache.xerces.util.SymbolTable;
import org.python.apache.xerces.util.XMLChar;
import org.python.apache.xerces.util.XMLSymbols;
import org.python.apache.xerces.xni.NamespaceContext;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.AttributeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/** @deprecated */
public class XMLSerializer extends BaseMarkupSerializer {
   protected static final boolean DEBUG = false;
   protected NamespaceSupport fNSBinder;
   protected NamespaceSupport fLocalNSBinder;
   protected SymbolTable fSymbolTable;
   protected static final String PREFIX = "NS";
   protected boolean fNamespaces = false;
   protected boolean fNamespacePrefixes = true;
   private boolean fPreserveSpace;

   public XMLSerializer() {
      super(new OutputFormat("xml", (String)null, false));
   }

   public XMLSerializer(OutputFormat var1) {
      super(var1 != null ? var1 : new OutputFormat("xml", (String)null, false));
      this._format.setMethod("xml");
   }

   public XMLSerializer(Writer var1, OutputFormat var2) {
      super(var2 != null ? var2 : new OutputFormat("xml", (String)null, false));
      this._format.setMethod("xml");
      this.setOutputCharStream(var1);
   }

   public XMLSerializer(OutputStream var1, OutputFormat var2) {
      super(var2 != null ? var2 : new OutputFormat("xml", (String)null, false));
      this._format.setMethod("xml");
      this.setOutputByteStream(var1);
   }

   public void setOutputFormat(OutputFormat var1) {
      super.setOutputFormat(var1 != null ? var1 : new OutputFormat("xml", (String)null, false));
   }

   public void setNamespaces(boolean var1) {
      this.fNamespaces = var1;
      if (this.fNSBinder == null) {
         this.fNSBinder = new NamespaceSupport();
         this.fLocalNSBinder = new NamespaceSupport();
         this.fSymbolTable = new SymbolTable();
      }

   }

   public void startElement(String var1, String var2, String var3, Attributes var4) throws SAXException {
      try {
         String var5;
         if (this._printer == null) {
            var5 = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "NoWriterSupplied", (Object[])null);
            throw new IllegalStateException(var5);
         } else {
            ElementState var6 = this.getElementState();
            if (this.isDocumentState()) {
               if (!this._started) {
                  this.startDocument(var2 != null && var2.length() != 0 ? var2 : var3);
               }
            } else {
               if (var6.empty) {
                  this._printer.printText('>');
               }

               if (var6.inCData) {
                  this._printer.printText("]]>");
                  var6.inCData = false;
               }

               if (this._indenting && !var6.preserveSpace && (var6.empty || var6.afterElement || var6.afterComment)) {
                  this._printer.breakLine();
               }
            }

            boolean var7 = var6.preserveSpace;
            var4 = this.extractNamespaces(var4);
            if (var3 == null || var3.length() == 0) {
               if (var2 == null) {
                  var5 = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "NoName", (Object[])null);
                  throw new SAXException(var5);
               }

               if (var1 != null && !var1.equals("")) {
                  var5 = this.getPrefix(var1);
                  if (var5 != null && var5.length() > 0) {
                     var3 = var5 + ":" + var2;
                  } else {
                     var3 = var2;
                  }
               } else {
                  var3 = var2;
               }
            }

            this._printer.printText('<');
            this._printer.printText(var3);
            this._printer.indent();
            String var9;
            String var11;
            if (var4 != null) {
               for(int var8 = 0; var8 < var4.getLength(); ++var8) {
                  this._printer.printSpace();
                  var9 = var4.getQName(var8);
                  if (var9 != null && var9.length() == 0) {
                     var9 = var4.getLocalName(var8);
                     String var10 = var4.getURI(var8);
                     if (var10 != null && var10.length() != 0 && (var1 == null || var1.length() == 0 || !var10.equals(var1))) {
                        var5 = this.getPrefix(var10);
                        if (var5 != null && var5.length() > 0) {
                           var9 = var5 + ":" + var9;
                        }
                     }
                  }

                  var11 = var4.getValue(var8);
                  if (var11 == null) {
                     var11 = "";
                  }

                  this._printer.printText(var9);
                  this._printer.printText("=\"");
                  this.printEscaped(var11);
                  this._printer.printText('"');
                  if (var9.equals("xml:space")) {
                     if (var11.equals("preserve")) {
                        var7 = true;
                     } else {
                        var7 = this._format.getPreserveSpace();
                     }
                  }
               }
            }

            if (this._prefixes != null) {
               Iterator var13 = this._prefixes.entrySet().iterator();

               while(var13.hasNext()) {
                  this._printer.printSpace();
                  Map.Entry var14 = (Map.Entry)var13.next();
                  var11 = (String)var14.getKey();
                  var9 = (String)var14.getValue();
                  if (var9.length() == 0) {
                     this._printer.printText("xmlns=\"");
                     this.printEscaped(var11);
                     this._printer.printText('"');
                  } else {
                     this._printer.printText("xmlns:");
                     this._printer.printText(var9);
                     this._printer.printText("=\"");
                     this.printEscaped(var11);
                     this._printer.printText('"');
                  }
               }
            }

            var6 = this.enterElementState(var1, var2, var3, var7);
            var9 = var2 != null && var2.length() != 0 ? var1 + "^" + var2 : var3;
            var6.doCData = this._format.isCDataElement(var9);
            var6.unescaped = this._format.isNonEscapingElement(var9);
         }
      } catch (IOException var12) {
         throw new SAXException(var12);
      }
   }

   public void endElement(String var1, String var2, String var3) throws SAXException {
      try {
         this.endElementIO(var1, var2, var3);
      } catch (IOException var5) {
         throw new SAXException(var5);
      }
   }

   public void endElementIO(String var1, String var2, String var3) throws IOException {
      this._printer.unindent();
      ElementState var4 = this.getElementState();
      if (var4.empty) {
         this._printer.printText("/>");
      } else {
         if (var4.inCData) {
            this._printer.printText("]]>");
         }

         if (this._indenting && !var4.preserveSpace && (var4.afterElement || var4.afterComment)) {
            this._printer.breakLine();
         }

         this._printer.printText("</");
         this._printer.printText(var4.rawName);
         this._printer.printText('>');
      }

      var4 = this.leaveElementState();
      var4.afterElement = true;
      var4.afterComment = false;
      var4.empty = false;
      if (this.isDocumentState()) {
         this._printer.flush();
      }

   }

   public void startElement(String var1, AttributeList var2) throws SAXException {
      try {
         if (this._printer == null) {
            String var3 = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "NoWriterSupplied", (Object[])null);
            throw new IllegalStateException(var3);
         } else {
            ElementState var4 = this.getElementState();
            if (this.isDocumentState()) {
               if (!this._started) {
                  this.startDocument(var1);
               }
            } else {
               if (var4.empty) {
                  this._printer.printText('>');
               }

               if (var4.inCData) {
                  this._printer.printText("]]>");
                  var4.inCData = false;
               }

               if (this._indenting && !var4.preserveSpace && (var4.empty || var4.afterElement || var4.afterComment)) {
                  this._printer.breakLine();
               }
            }

            boolean var5 = var4.preserveSpace;
            this._printer.printText('<');
            this._printer.printText(var1);
            this._printer.indent();
            if (var2 != null) {
               for(int var6 = 0; var6 < var2.getLength(); ++var6) {
                  this._printer.printSpace();
                  String var7 = var2.getName(var6);
                  String var8 = var2.getValue(var6);
                  if (var8 != null) {
                     this._printer.printText(var7);
                     this._printer.printText("=\"");
                     this.printEscaped(var8);
                     this._printer.printText('"');
                  }

                  if (var7.equals("xml:space")) {
                     if (var8.equals("preserve")) {
                        var5 = true;
                     } else {
                        var5 = this._format.getPreserveSpace();
                     }
                  }
               }
            }

            var4 = this.enterElementState((String)null, (String)null, var1, var5);
            var4.doCData = this._format.isCDataElement(var1);
            var4.unescaped = this._format.isNonEscapingElement(var1);
         }
      } catch (IOException var9) {
         throw new SAXException(var9);
      }
   }

   public void endElement(String var1) throws SAXException {
      this.endElement((String)null, (String)null, var1);
   }

   protected void startDocument(String var1) throws IOException {
      String var2 = this._printer.leaveDTD();
      if (!this._started) {
         if (!this._format.getOmitXMLDeclaration()) {
            StringBuffer var3 = new StringBuffer("<?xml version=\"");
            if (this._format.getVersion() != null) {
               var3.append(this._format.getVersion());
            } else {
               var3.append("1.0");
            }

            var3.append('"');
            String var4 = this._format.getEncoding();
            if (var4 != null) {
               var3.append(" encoding=\"");
               var3.append(var4);
               var3.append('"');
            }

            if (this._format.getStandalone() && this._docTypeSystemId == null && this._docTypePublicId == null) {
               var3.append(" standalone=\"yes\"");
            }

            var3.append("?>");
            this._printer.printText(var3);
            this._printer.breakLine();
         }

         if (!this._format.getOmitDocumentType()) {
            if (this._docTypeSystemId != null) {
               this._printer.printText("<!DOCTYPE ");
               this._printer.printText(var1);
               if (this._docTypePublicId == null) {
                  this._printer.printText(" SYSTEM ");
                  this.printDoctypeURL(this._docTypeSystemId);
               } else {
                  this._printer.printText(" PUBLIC ");
                  this.printDoctypeURL(this._docTypePublicId);
                  if (this._indenting) {
                     this._printer.breakLine();

                     for(int var5 = 0; var5 < 18 + var1.length(); ++var5) {
                        this._printer.printText(" ");
                     }
                  } else {
                     this._printer.printText(" ");
                  }

                  this.printDoctypeURL(this._docTypeSystemId);
               }

               if (var2 != null && var2.length() > 0) {
                  this._printer.printText(" [");
                  this.printText(var2, true, true);
                  this._printer.printText(']');
               }

               this._printer.printText(">");
               this._printer.breakLine();
            } else if (var2 != null && var2.length() > 0) {
               this._printer.printText("<!DOCTYPE ");
               this._printer.printText(var1);
               this._printer.printText(" [");
               this.printText(var2, true, true);
               this._printer.printText("]>");
               this._printer.breakLine();
            }
         }
      }

      this._started = true;
      this.serializePreRoot();
   }

   protected void serializeElement(Element var1) throws IOException {
      if (this.fNamespaces) {
         this.fLocalNSBinder.reset();
         this.fNSBinder.pushContext();
      }

      String var2 = var1.getTagName();
      ElementState var3 = this.getElementState();
      if (this.isDocumentState()) {
         if (!this._started) {
            this.startDocument(var2);
         }
      } else {
         if (var3.empty) {
            this._printer.printText('>');
         }

         if (var3.inCData) {
            this._printer.printText("]]>");
            var3.inCData = false;
         }

         if (this._indenting && !var3.preserveSpace && (var3.empty || var3.afterElement || var3.afterComment)) {
            this._printer.breakLine();
         }
      }

      this.fPreserveSpace = var3.preserveSpace;
      int var4 = 0;
      NamedNodeMap var5 = null;
      if (var1.hasAttributes()) {
         var5 = var1.getAttributes();
         var4 = var5.getLength();
      }

      int var6;
      Attr var7;
      String var8;
      String var9;
      if (!this.fNamespaces) {
         this._printer.printText('<');
         this._printer.printText(var2);
         this._printer.indent();

         for(var6 = 0; var6 < var4; ++var6) {
            var7 = (Attr)var5.item(var6);
            var8 = var7.getName();
            var9 = var7.getValue();
            if (var9 == null) {
               var9 = "";
            }

            this.printAttribute(var8, var9, var7.getSpecified(), var7);
         }
      } else {
         String var10;
         String var11;
         boolean var12;
         String var13;
         for(var6 = 0; var6 < var4; ++var6) {
            var7 = (Attr)var5.item(var6);
            var10 = var7.getNamespaceURI();
            if (var10 != null && var10.equals(NamespaceContext.XMLNS_URI)) {
               var9 = var7.getNodeValue();
               if (var9 == null) {
                  var9 = XMLSymbols.EMPTY_STRING;
               }

               if (var9.equals(NamespaceContext.XMLNS_URI)) {
                  if (this.fDOMErrorHandler != null) {
                     var11 = DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "CantBindXMLNS", (Object[])null);
                     this.modifyDOMError(var11, (short)2, (String)null, var7);
                     var12 = this.fDOMErrorHandler.handleError(this.fDOMError);
                     if (!var12) {
                        throw new RuntimeException(DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "SerializationStopped", (Object[])null));
                     }
                  }
               } else {
                  var13 = var7.getPrefix();
                  var13 = var13 != null && var13.length() != 0 ? this.fSymbolTable.addSymbol(var13) : XMLSymbols.EMPTY_STRING;
                  var11 = this.fSymbolTable.addSymbol(var7.getLocalName());
                  if (var13 == XMLSymbols.PREFIX_XMLNS) {
                     var9 = this.fSymbolTable.addSymbol(var9);
                     if (var9.length() != 0) {
                        this.fNSBinder.declarePrefix(var11, var9);
                     }
                  } else {
                     var9 = this.fSymbolTable.addSymbol(var9);
                     this.fNSBinder.declarePrefix(XMLSymbols.EMPTY_STRING, var9);
                  }
               }
            }
         }

         var10 = var1.getNamespaceURI();
         var13 = var1.getPrefix();
         if (var10 != null && var13 != null && var10.length() == 0 && var13.length() != 0) {
            var13 = null;
            this._printer.printText('<');
            this._printer.printText(var1.getLocalName());
            this._printer.indent();
         } else {
            this._printer.printText('<');
            this._printer.printText(var2);
            this._printer.indent();
         }

         if (var10 == null) {
            if (var1.getLocalName() == null) {
               if (this.fDOMErrorHandler != null) {
                  var11 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NullLocalElementName", new Object[]{var1.getNodeName()});
                  this.modifyDOMError(var11, (short)2, (String)null, var1);
                  var12 = this.fDOMErrorHandler.handleError(this.fDOMError);
                  if (!var12) {
                     throw new RuntimeException(DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "SerializationStopped", (Object[])null));
                  }
               }
            } else {
               var10 = this.fNSBinder.getURI(XMLSymbols.EMPTY_STRING);
               if (var10 != null && var10.length() > 0) {
                  if (this.fNamespacePrefixes) {
                     this.printNamespaceAttr(XMLSymbols.EMPTY_STRING, XMLSymbols.EMPTY_STRING);
                  }

                  this.fLocalNSBinder.declarePrefix(XMLSymbols.EMPTY_STRING, XMLSymbols.EMPTY_STRING);
                  this.fNSBinder.declarePrefix(XMLSymbols.EMPTY_STRING, XMLSymbols.EMPTY_STRING);
               }
            }
         } else {
            var10 = this.fSymbolTable.addSymbol(var10);
            var13 = var13 != null && var13.length() != 0 ? this.fSymbolTable.addSymbol(var13) : XMLSymbols.EMPTY_STRING;
            if (this.fNSBinder.getURI(var13) != var10) {
               if (this.fNamespacePrefixes) {
                  this.printNamespaceAttr(var13, var10);
               }

               this.fLocalNSBinder.declarePrefix(var13, var10);
               this.fNSBinder.declarePrefix(var13, var10);
            }
         }

         for(var6 = 0; var6 < var4; ++var6) {
            var7 = (Attr)var5.item(var6);
            var9 = var7.getValue();
            var8 = var7.getNodeName();
            var10 = var7.getNamespaceURI();
            if (var10 != null && var10.length() == 0) {
               var10 = null;
               var8 = var7.getLocalName();
            }

            if (var9 == null) {
               var9 = XMLSymbols.EMPTY_STRING;
            }

            if (var10 != null) {
               var13 = var7.getPrefix();
               var13 = var13 == null ? XMLSymbols.EMPTY_STRING : this.fSymbolTable.addSymbol(var13);
               var11 = this.fSymbolTable.addSymbol(var7.getLocalName());
               if (var10 != null && var10.equals(NamespaceContext.XMLNS_URI)) {
                  var13 = var7.getPrefix();
                  var13 = var13 != null && var13.length() != 0 ? this.fSymbolTable.addSymbol(var13) : XMLSymbols.EMPTY_STRING;
                  var11 = this.fSymbolTable.addSymbol(var7.getLocalName());
                  String var14;
                  if (var13 == XMLSymbols.PREFIX_XMLNS) {
                     var14 = this.fLocalNSBinder.getURI(var11);
                     var9 = this.fSymbolTable.addSymbol(var9);
                     if (var9.length() != 0 && var14 == null) {
                        if (this.fNamespacePrefixes) {
                           this.printNamespaceAttr(var11, var9);
                        }

                        this.fLocalNSBinder.declarePrefix(var11, var9);
                     }
                  } else {
                     var10 = this.fNSBinder.getURI(XMLSymbols.EMPTY_STRING);
                     var14 = this.fLocalNSBinder.getURI(XMLSymbols.EMPTY_STRING);
                     var9 = this.fSymbolTable.addSymbol(var9);
                     if (var14 == null && this.fNamespacePrefixes) {
                        this.printNamespaceAttr(XMLSymbols.EMPTY_STRING, var9);
                     }
                  }
               } else {
                  var10 = this.fSymbolTable.addSymbol(var10);
                  String var18 = this.fNSBinder.getURI(var13);
                  if (var13 == XMLSymbols.EMPTY_STRING || var18 != var10) {
                     var8 = var7.getNodeName();
                     String var15 = this.fNSBinder.getPrefix(var10);
                     if (var15 != null && var15 != XMLSymbols.EMPTY_STRING) {
                        var8 = var15 + ":" + var11;
                     } else {
                        if (var13 == XMLSymbols.EMPTY_STRING || this.fLocalNSBinder.getURI(var13) != null) {
                           int var16 = 1;

                           for(var13 = this.fSymbolTable.addSymbol("NS" + var16++); this.fLocalNSBinder.getURI(var13) != null; var13 = this.fSymbolTable.addSymbol("NS" + var16++)) {
                           }

                           var8 = var13 + ":" + var11;
                        }

                        if (this.fNamespacePrefixes) {
                           this.printNamespaceAttr(var13, var10);
                        }

                        var9 = this.fSymbolTable.addSymbol(var9);
                        this.fLocalNSBinder.declarePrefix(var13, var9);
                        this.fNSBinder.declarePrefix(var13, var10);
                     }
                  }

                  this.printAttribute(var8, var9 == null ? XMLSymbols.EMPTY_STRING : var9, var7.getSpecified(), var7);
               }
            } else if (var7.getLocalName() == null) {
               if (this.fDOMErrorHandler != null) {
                  var11 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NullLocalAttrName", new Object[]{var7.getNodeName()});
                  this.modifyDOMError(var11, (short)2, (String)null, var7);
                  var12 = this.fDOMErrorHandler.handleError(this.fDOMError);
                  if (!var12) {
                     throw new RuntimeException(DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "SerializationStopped", (Object[])null));
                  }
               }

               this.printAttribute(var8, var9, var7.getSpecified(), var7);
            } else {
               this.printAttribute(var8, var9, var7.getSpecified(), var7);
            }
         }
      }

      if (var1.hasChildNodes()) {
         var3 = this.enterElementState((String)null, (String)null, var2, this.fPreserveSpace);
         var3.doCData = this._format.isCDataElement(var2);
         var3.unescaped = this._format.isNonEscapingElement(var2);

         for(Node var17 = var1.getFirstChild(); var17 != null; var17 = var17.getNextSibling()) {
            this.serializeNode(var17);
         }

         if (this.fNamespaces) {
            this.fNSBinder.popContext();
         }

         this.endElementIO((String)null, (String)null, var2);
      } else {
         if (this.fNamespaces) {
            this.fNSBinder.popContext();
         }

         this._printer.unindent();
         this._printer.printText("/>");
         var3.afterElement = true;
         var3.afterComment = false;
         var3.empty = false;
         if (this.isDocumentState()) {
            this._printer.flush();
         }
      }

   }

   private void printNamespaceAttr(String var1, String var2) throws IOException {
      this._printer.printSpace();
      if (var1 == XMLSymbols.EMPTY_STRING) {
         this._printer.printText(XMLSymbols.PREFIX_XMLNS);
      } else {
         this._printer.printText("xmlns:" + var1);
      }

      this._printer.printText("=\"");
      this.printEscaped(var2);
      this._printer.printText('"');
   }

   private void printAttribute(String var1, String var2, boolean var3, Attr var4) throws IOException {
      if (var3 || (this.features & 64) == 0) {
         if (this.fDOMFilter != null && (this.fDOMFilter.getWhatToShow() & 2) != 0) {
            short var5 = this.fDOMFilter.acceptNode(var4);
            switch (var5) {
               case 2:
               case 3:
                  return;
            }
         }

         this._printer.printSpace();
         this._printer.printText(var1);
         this._printer.printText("=\"");
         this.printEscaped(var2);
         this._printer.printText('"');
      }

      if (var1.equals("xml:space")) {
         if (var2.equals("preserve")) {
            this.fPreserveSpace = true;
         } else {
            this.fPreserveSpace = this._format.getPreserveSpace();
         }
      }

   }

   protected String getEntityRef(int var1) {
      switch (var1) {
         case 34:
            return "quot";
         case 38:
            return "amp";
         case 39:
            return "apos";
         case 60:
            return "lt";
         case 62:
            return "gt";
         default:
            return null;
      }
   }

   private Attributes extractNamespaces(Attributes var1) throws SAXException {
      if (var1 == null) {
         return null;
      } else {
         int var2 = var1.getLength();
         AttributesImpl var3 = new AttributesImpl(var1);

         for(int var4 = var2 - 1; var4 >= 0; --var4) {
            String var5 = var3.getQName(var4);
            if (var5.startsWith("xmlns")) {
               if (var5.length() == 5) {
                  this.startPrefixMapping("", var1.getValue(var4));
                  var3.removeAttribute(var4);
               } else if (var5.charAt(5) == ':') {
                  this.startPrefixMapping(var5.substring(6), var1.getValue(var4));
                  var3.removeAttribute(var4);
               }
            }
         }

         return var3;
      }
   }

   protected void printEscaped(String var1) throws IOException {
      int var2 = var1.length();

      for(int var3 = 0; var3 < var2; ++var3) {
         char var4 = var1.charAt(var3);
         if (!XMLChar.isValid(var4)) {
            ++var3;
            if (var3 < var2) {
               this.surrogates(var4, var1.charAt(var3), false);
            } else {
               this.fatalError("The character '" + (char)var4 + "' is an invalid XML character");
            }
         } else if (var4 != '\n' && var4 != '\r' && var4 != '\t') {
            if (var4 == '<') {
               this._printer.printText("&lt;");
            } else if (var4 == '&') {
               this._printer.printText("&amp;");
            } else if (var4 == '"') {
               this._printer.printText("&quot;");
            } else if (var4 >= ' ' && this._encodingInfo.isPrintable((char)var4)) {
               this._printer.printText((char)var4);
            } else {
               this.printHex(var4);
            }
         } else {
            this.printHex(var4);
         }
      }

   }

   protected void printXMLChar(int var1) throws IOException {
      if (var1 == 13) {
         this.printHex(var1);
      } else if (var1 == 60) {
         this._printer.printText("&lt;");
      } else if (var1 == 38) {
         this._printer.printText("&amp;");
      } else if (var1 == 62) {
         this._printer.printText("&gt;");
      } else if (var1 != 10 && var1 != 9 && (var1 < 32 || !this._encodingInfo.isPrintable((char)var1))) {
         this.printHex(var1);
      } else {
         this._printer.printText((char)var1);
      }

   }

   protected void printText(String var1, boolean var2, boolean var3) throws IOException {
      int var4 = var1.length();
      int var5;
      char var6;
      if (var2) {
         for(var5 = 0; var5 < var4; ++var5) {
            var6 = var1.charAt(var5);
            if (!XMLChar.isValid(var6)) {
               ++var5;
               if (var5 < var4) {
                  this.surrogates(var6, var1.charAt(var5), true);
               } else {
                  this.fatalError("The character '" + var6 + "' is an invalid XML character");
               }
            } else if (var3) {
               this._printer.printText(var6);
            } else {
               this.printXMLChar(var6);
            }
         }
      } else {
         for(var5 = 0; var5 < var4; ++var5) {
            var6 = var1.charAt(var5);
            if (!XMLChar.isValid(var6)) {
               ++var5;
               if (var5 < var4) {
                  this.surrogates(var6, var1.charAt(var5), true);
               } else {
                  this.fatalError("The character '" + var6 + "' is an invalid XML character");
               }
            } else if (var3) {
               this._printer.printText(var6);
            } else {
               this.printXMLChar(var6);
            }
         }
      }

   }

   protected void printText(char[] var1, int var2, int var3, boolean var4, boolean var5) throws IOException {
      char var6;
      if (var4) {
         while(var3-- > 0) {
            var6 = var1[var2++];
            if (!XMLChar.isValid(var6)) {
               if (var3-- > 0) {
                  this.surrogates(var6, var1[var2++], true);
               } else {
                  this.fatalError("The character '" + var6 + "' is an invalid XML character");
               }
            } else if (var5) {
               this._printer.printText(var6);
            } else {
               this.printXMLChar(var6);
            }
         }
      } else {
         while(var3-- > 0) {
            var6 = var1[var2++];
            if (!XMLChar.isValid(var6)) {
               if (var3-- > 0) {
                  this.surrogates(var6, var1[var2++], true);
               } else {
                  this.fatalError("The character '" + var6 + "' is an invalid XML character");
               }
            } else if (var5) {
               this._printer.printText(var6);
            } else {
               this.printXMLChar(var6);
            }
         }
      }

   }

   protected void checkUnboundNamespacePrefixedNode(Node var1) throws IOException {
      Node var3;
      if (this.fNamespaces) {
         for(Node var2 = var1.getFirstChild(); var2 != null; var2 = var3) {
            var3 = var2.getNextSibling();
            String var4 = var2.getPrefix();
            var4 = var4 != null && var4.length() != 0 ? this.fSymbolTable.addSymbol(var4) : XMLSymbols.EMPTY_STRING;
            if (this.fNSBinder.getURI(var4) == null && var4 != null) {
               this.fatalError("The replacement text of the entity node '" + var1.getNodeName() + "' contains an element node '" + var2.getNodeName() + "' with an undeclared prefix '" + var4 + "'.");
            }

            if (var2.getNodeType() == 1) {
               NamedNodeMap var5 = var2.getAttributes();

               for(int var6 = 0; var6 < var5.getLength(); ++var6) {
                  String var7 = var5.item(var6).getPrefix();
                  var7 = var7 != null && var7.length() != 0 ? this.fSymbolTable.addSymbol(var7) : XMLSymbols.EMPTY_STRING;
                  if (this.fNSBinder.getURI(var7) == null && var7 != null) {
                     this.fatalError("The replacement text of the entity node '" + var1.getNodeName() + "' contains an element node '" + var2.getNodeName() + "' with an attribute '" + var5.item(var6).getNodeName() + "' an undeclared prefix '" + var7 + "'.");
                  }
               }
            }

            if (var2.hasChildNodes()) {
               this.checkUnboundNamespacePrefixedNode(var2);
            }
         }
      }

   }

   public boolean reset() {
      super.reset();
      if (this.fNSBinder != null) {
         this.fNSBinder.reset();
         this.fNSBinder.declarePrefix(XMLSymbols.EMPTY_STRING, XMLSymbols.EMPTY_STRING);
      }

      return true;
   }
}
