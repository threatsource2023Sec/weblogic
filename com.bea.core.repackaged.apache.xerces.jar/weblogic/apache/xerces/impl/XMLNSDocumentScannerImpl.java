package weblogic.apache.xerces.impl;

import java.io.IOException;
import weblogic.apache.xerces.impl.dtd.XMLDTDValidatorFilter;
import weblogic.apache.xerces.util.XMLAttributesImpl;
import weblogic.apache.xerces.util.XMLSymbols;
import weblogic.apache.xerces.xni.Augmentations;
import weblogic.apache.xerces.xni.NamespaceContext;
import weblogic.apache.xerces.xni.QName;
import weblogic.apache.xerces.xni.XMLDocumentHandler;
import weblogic.apache.xerces.xni.XNIException;
import weblogic.apache.xerces.xni.parser.XMLComponentManager;
import weblogic.apache.xerces.xni.parser.XMLConfigurationException;
import weblogic.apache.xerces.xni.parser.XMLDocumentSource;

public class XMLNSDocumentScannerImpl extends XMLDocumentScannerImpl {
   protected boolean fBindNamespaces;
   protected boolean fPerformValidation;
   private XMLDTDValidatorFilter fDTDValidator;
   private boolean fSawSpace;

   public void setDTDValidator(XMLDTDValidatorFilter var1) {
      this.fDTDValidator = var1;
   }

   protected boolean scanStartElement() throws IOException, XNIException {
      this.fEntityScanner.scanQName(this.fElementQName);
      String var1 = this.fElementQName.rawname;
      if (this.fBindNamespaces) {
         this.fNamespaceContext.pushContext();
         if (this.fScannerState == 6 && this.fPerformValidation) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_GRAMMAR_NOT_FOUND", new Object[]{var1}, (short)1);
            if (this.fDoctypeName == null || !this.fDoctypeName.equals(var1)) {
               this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "RootElementTypeMustMatchDoctypedecl", new Object[]{this.fDoctypeName, var1}, (short)1);
            }
         }
      }

      this.fCurrentElement = this.fElementStack.pushElement(this.fElementQName);
      boolean var2 = false;
      this.fAttributes.removeAllAttributes();

      int var4;
      while(true) {
         boolean var3 = this.fEntityScanner.skipSpaces();
         var4 = this.fEntityScanner.peekChar();
         if (var4 == 62) {
            this.fEntityScanner.scanChar();
            break;
         }

         if (var4 == 47) {
            this.fEntityScanner.scanChar();
            if (!this.fEntityScanner.skipChar(62)) {
               this.reportFatalError("ElementUnterminated", new Object[]{var1});
            }

            var2 = true;
            break;
         }

         if (!this.isValidNameStartChar(var4) || !var3) {
            this.reportFatalError("ElementUnterminated", new Object[]{var1});
         }

         this.scanAttribute(this.fAttributes);
      }

      if (this.fBindNamespaces) {
         if (this.fElementQName.prefix == XMLSymbols.PREFIX_XMLNS) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "ElementXMLNSPrefix", new Object[]{this.fElementQName.rawname}, (short)2);
         }

         String var8 = this.fElementQName.prefix != null ? this.fElementQName.prefix : XMLSymbols.EMPTY_STRING;
         this.fElementQName.uri = this.fNamespaceContext.getURI(var8);
         this.fCurrentElement.uri = this.fElementQName.uri;
         if (this.fElementQName.prefix == null && this.fElementQName.uri != null) {
            this.fElementQName.prefix = XMLSymbols.EMPTY_STRING;
            this.fCurrentElement.prefix = XMLSymbols.EMPTY_STRING;
         }

         if (this.fElementQName.prefix != null && this.fElementQName.uri == null) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "ElementPrefixUnbound", new Object[]{this.fElementQName.prefix, this.fElementQName.rawname}, (short)2);
         }

         var4 = this.fAttributes.getLength();

         for(int var5 = 0; var5 < var4; ++var5) {
            this.fAttributes.getName(var5, this.fAttributeQName);
            String var6 = this.fAttributeQName.prefix != null ? this.fAttributeQName.prefix : XMLSymbols.EMPTY_STRING;
            String var7 = this.fNamespaceContext.getURI(var6);
            if ((this.fAttributeQName.uri == null || this.fAttributeQName.uri != var7) && var6 != XMLSymbols.EMPTY_STRING) {
               this.fAttributeQName.uri = var7;
               if (var7 == null) {
                  this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "AttributePrefixUnbound", new Object[]{this.fElementQName.rawname, this.fAttributeQName.rawname, var6}, (short)2);
               }

               this.fAttributes.setURI(var5, var7);
            }
         }

         if (var4 > 1) {
            QName var9 = this.fAttributes.checkDuplicatesNS();
            if (var9 != null) {
               if (var9.uri != null) {
                  this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "AttributeNSNotUnique", new Object[]{this.fElementQName.rawname, var9.localpart, var9.uri}, (short)2);
               } else {
                  this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "AttributeNotUnique", new Object[]{this.fElementQName.rawname, var9.rawname}, (short)2);
               }
            }
         }
      }

      if (this.fDocumentHandler != null) {
         if (var2) {
            --this.fMarkupDepth;
            if (this.fMarkupDepth < this.fEntityStack[this.fEntityDepth - 1]) {
               this.reportFatalError("ElementEntityMismatch", new Object[]{this.fCurrentElement.rawname});
            }

            this.fDocumentHandler.emptyElement(this.fElementQName, this.fAttributes, (Augmentations)null);
            if (this.fBindNamespaces) {
               this.fNamespaceContext.popContext();
            }

            this.fElementStack.popElement(this.fElementQName);
         } else {
            this.fDocumentHandler.startElement(this.fElementQName, this.fAttributes, (Augmentations)null);
         }
      }

      return var2;
   }

   protected void scanStartElementName() throws IOException, XNIException {
      this.fEntityScanner.scanQName(this.fElementQName);
      this.fSawSpace = this.fEntityScanner.skipSpaces();
   }

   protected boolean scanStartElementAfterName() throws IOException, XNIException {
      String var1 = this.fElementQName.rawname;
      if (this.fBindNamespaces) {
         this.fNamespaceContext.pushContext();
         if (this.fScannerState == 6 && this.fPerformValidation) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_GRAMMAR_NOT_FOUND", new Object[]{var1}, (short)1);
            if (this.fDoctypeName == null || !this.fDoctypeName.equals(var1)) {
               this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "RootElementTypeMustMatchDoctypedecl", new Object[]{this.fDoctypeName, var1}, (short)1);
            }
         }
      }

      this.fCurrentElement = this.fElementStack.pushElement(this.fElementQName);
      boolean var2 = false;
      this.fAttributes.removeAllAttributes();

      while(true) {
         int var3 = this.fEntityScanner.peekChar();
         if (var3 == 62) {
            this.fEntityScanner.scanChar();
            break;
         }

         if (var3 == 47) {
            this.fEntityScanner.scanChar();
            if (!this.fEntityScanner.skipChar(62)) {
               this.reportFatalError("ElementUnterminated", new Object[]{var1});
            }

            var2 = true;
            break;
         }

         if (!this.isValidNameStartChar(var3) || !this.fSawSpace) {
            this.reportFatalError("ElementUnterminated", new Object[]{var1});
         }

         this.scanAttribute(this.fAttributes);
         this.fSawSpace = this.fEntityScanner.skipSpaces();
      }

      if (this.fBindNamespaces) {
         if (this.fElementQName.prefix == XMLSymbols.PREFIX_XMLNS) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "ElementXMLNSPrefix", new Object[]{this.fElementQName.rawname}, (short)2);
         }

         String var8 = this.fElementQName.prefix != null ? this.fElementQName.prefix : XMLSymbols.EMPTY_STRING;
         this.fElementQName.uri = this.fNamespaceContext.getURI(var8);
         this.fCurrentElement.uri = this.fElementQName.uri;
         if (this.fElementQName.prefix == null && this.fElementQName.uri != null) {
            this.fElementQName.prefix = XMLSymbols.EMPTY_STRING;
            this.fCurrentElement.prefix = XMLSymbols.EMPTY_STRING;
         }

         if (this.fElementQName.prefix != null && this.fElementQName.uri == null) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "ElementPrefixUnbound", new Object[]{this.fElementQName.prefix, this.fElementQName.rawname}, (short)2);
         }

         int var4 = this.fAttributes.getLength();

         for(int var5 = 0; var5 < var4; ++var5) {
            this.fAttributes.getName(var5, this.fAttributeQName);
            String var6 = this.fAttributeQName.prefix != null ? this.fAttributeQName.prefix : XMLSymbols.EMPTY_STRING;
            String var7 = this.fNamespaceContext.getURI(var6);
            if ((this.fAttributeQName.uri == null || this.fAttributeQName.uri != var7) && var6 != XMLSymbols.EMPTY_STRING) {
               this.fAttributeQName.uri = var7;
               if (var7 == null) {
                  this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "AttributePrefixUnbound", new Object[]{this.fElementQName.rawname, this.fAttributeQName.rawname, var6}, (short)2);
               }

               this.fAttributes.setURI(var5, var7);
            }
         }

         if (var4 > 1) {
            QName var9 = this.fAttributes.checkDuplicatesNS();
            if (var9 != null) {
               if (var9.uri != null) {
                  this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "AttributeNSNotUnique", new Object[]{this.fElementQName.rawname, var9.localpart, var9.uri}, (short)2);
               } else {
                  this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "AttributeNotUnique", new Object[]{this.fElementQName.rawname, var9.rawname}, (short)2);
               }
            }
         }
      }

      if (this.fDocumentHandler != null) {
         if (var2) {
            --this.fMarkupDepth;
            if (this.fMarkupDepth < this.fEntityStack[this.fEntityDepth - 1]) {
               this.reportFatalError("ElementEntityMismatch", new Object[]{this.fCurrentElement.rawname});
            }

            this.fDocumentHandler.emptyElement(this.fElementQName, this.fAttributes, (Augmentations)null);
            if (this.fBindNamespaces) {
               this.fNamespaceContext.popContext();
            }

            this.fElementStack.popElement(this.fElementQName);
         } else {
            this.fDocumentHandler.startElement(this.fElementQName, this.fAttributes, (Augmentations)null);
         }
      }

      return var2;
   }

   protected void scanAttribute(XMLAttributesImpl var1) throws IOException, XNIException {
      this.fEntityScanner.scanQName(this.fAttributeQName);
      this.fEntityScanner.skipSpaces();
      if (!this.fEntityScanner.skipChar(61)) {
         this.reportFatalError("EqRequiredInAttribute", new Object[]{this.fCurrentElement.rawname, this.fAttributeQName.rawname});
      }

      this.fEntityScanner.skipSpaces();
      int var2;
      if (this.fBindNamespaces) {
         var2 = var1.getLength();
         var1.addAttributeNS(this.fAttributeQName, XMLSymbols.fCDATASymbol, (String)null);
      } else {
         int var3 = var1.getLength();
         var2 = var1.addAttribute(this.fAttributeQName, XMLSymbols.fCDATASymbol, (String)null);
         if (var3 == var1.getLength()) {
            this.reportFatalError("AttributeNotUnique", new Object[]{this.fCurrentElement.rawname, this.fAttributeQName.rawname});
         }
      }

      boolean var8 = this.scanAttributeValue(this.fTempString, this.fTempString2, this.fAttributeQName.rawname, this.fIsEntityDeclaredVC, this.fCurrentElement.rawname);
      String var4 = this.fTempString.toString();
      var1.setValue(var2, var4);
      if (!var8) {
         var1.setNonNormalizedValue(var2, this.fTempString2.toString());
      }

      var1.setSpecified(var2, true);
      if (this.fBindNamespaces) {
         String var5 = this.fAttributeQName.localpart;
         String var6 = this.fAttributeQName.prefix != null ? this.fAttributeQName.prefix : XMLSymbols.EMPTY_STRING;
         if (var6 != XMLSymbols.PREFIX_XMLNS && (var6 != XMLSymbols.EMPTY_STRING || var5 != XMLSymbols.PREFIX_XMLNS)) {
            if (this.fAttributeQName.prefix != null) {
               var1.setURI(var2, this.fNamespaceContext.getURI(this.fAttributeQName.prefix));
            }
         } else {
            String var7 = this.fSymbolTable.addSymbol(var4);
            if (var6 == XMLSymbols.PREFIX_XMLNS && var5 == XMLSymbols.PREFIX_XMLNS) {
               this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "CantBindXMLNS", new Object[]{this.fAttributeQName}, (short)2);
            }

            if (var7 == NamespaceContext.XMLNS_URI) {
               this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "CantBindXMLNS", new Object[]{this.fAttributeQName}, (short)2);
            }

            if (var5 == XMLSymbols.PREFIX_XML) {
               if (var7 != NamespaceContext.XML_URI) {
                  this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "CantBindXML", new Object[]{this.fAttributeQName}, (short)2);
               }
            } else if (var7 == NamespaceContext.XML_URI) {
               this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "CantBindXML", new Object[]{this.fAttributeQName}, (short)2);
            }

            var6 = var5 != XMLSymbols.PREFIX_XMLNS ? var5 : XMLSymbols.EMPTY_STRING;
            if (var7 == XMLSymbols.EMPTY_STRING && var5 != XMLSymbols.PREFIX_XMLNS) {
               this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "EmptyPrefixedAttName", new Object[]{this.fAttributeQName}, (short)2);
            }

            this.fNamespaceContext.declarePrefix(var6, var7.length() != 0 ? var7 : null);
            var1.setURI(var2, this.fNamespaceContext.getURI(XMLSymbols.PREFIX_XMLNS));
         }
      }

   }

   protected int scanEndElement() throws IOException, XNIException {
      this.fElementStack.popElement(this.fElementQName);
      if (!this.fEntityScanner.skipString(this.fElementQName.rawname)) {
         this.reportFatalError("ETagRequired", new Object[]{this.fElementQName.rawname});
      }

      this.fEntityScanner.skipSpaces();
      if (!this.fEntityScanner.skipChar(62)) {
         this.reportFatalError("ETagUnterminated", new Object[]{this.fElementQName.rawname});
      }

      --this.fMarkupDepth;
      --this.fMarkupDepth;
      if (this.fMarkupDepth < this.fEntityStack[this.fEntityDepth - 1]) {
         this.reportFatalError("ElementEntityMismatch", new Object[]{this.fCurrentElement.rawname});
      }

      if (this.fDocumentHandler != null) {
         this.fDocumentHandler.endElement(this.fElementQName, (Augmentations)null);
         if (this.fBindNamespaces) {
            this.fNamespaceContext.popContext();
         }
      }

      return this.fMarkupDepth;
   }

   public void reset(XMLComponentManager var1) throws XMLConfigurationException {
      super.reset(var1);
      this.fPerformValidation = false;
      this.fBindNamespaces = false;
   }

   protected XMLDocumentFragmentScannerImpl.Dispatcher createContentDispatcher() {
      return new NSContentDispatcher();
   }

   protected final class NSContentDispatcher extends XMLDocumentScannerImpl.ContentDispatcher {
      protected NSContentDispatcher() {
         super();
      }

      protected boolean scanRootElementHook() throws IOException, XNIException {
         if (XMLNSDocumentScannerImpl.this.fExternalSubsetResolver != null && !XMLNSDocumentScannerImpl.this.fSeenDoctypeDecl && !XMLNSDocumentScannerImpl.this.fDisallowDoctype && (XMLNSDocumentScannerImpl.this.fValidation || XMLNSDocumentScannerImpl.this.fLoadExternalDTD)) {
            XMLNSDocumentScannerImpl.this.scanStartElementName();
            this.resolveExternalSubsetAndRead();
            this.reconfigurePipeline();
            if (XMLNSDocumentScannerImpl.this.scanStartElementAfterName()) {
               XMLNSDocumentScannerImpl.this.setScannerState(12);
               XMLNSDocumentScannerImpl.this.setDispatcher(XMLNSDocumentScannerImpl.this.fTrailingMiscDispatcher);
               return true;
            }
         } else {
            this.reconfigurePipeline();
            if (XMLNSDocumentScannerImpl.this.scanStartElement()) {
               XMLNSDocumentScannerImpl.this.setScannerState(12);
               XMLNSDocumentScannerImpl.this.setDispatcher(XMLNSDocumentScannerImpl.this.fTrailingMiscDispatcher);
               return true;
            }
         }

         return false;
      }

      private void reconfigurePipeline() {
         if (XMLNSDocumentScannerImpl.this.fDTDValidator == null) {
            XMLNSDocumentScannerImpl.this.fBindNamespaces = true;
         } else if (!XMLNSDocumentScannerImpl.this.fDTDValidator.hasGrammar()) {
            XMLNSDocumentScannerImpl.this.fBindNamespaces = true;
            XMLNSDocumentScannerImpl.this.fPerformValidation = XMLNSDocumentScannerImpl.this.fDTDValidator.validate();
            XMLDocumentSource var1 = XMLNSDocumentScannerImpl.this.fDTDValidator.getDocumentSource();
            XMLDocumentHandler var2 = XMLNSDocumentScannerImpl.this.fDTDValidator.getDocumentHandler();
            var1.setDocumentHandler(var2);
            if (var2 != null) {
               var2.setDocumentSource(var1);
            }

            XMLNSDocumentScannerImpl.this.fDTDValidator.setDocumentSource((XMLDocumentSource)null);
            XMLNSDocumentScannerImpl.this.fDTDValidator.setDocumentHandler((XMLDocumentHandler)null);
         }

      }
   }
}
