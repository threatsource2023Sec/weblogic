package org.python.apache.xerces.impl;

import java.io.IOException;
import org.python.apache.xerces.util.SymbolTable;
import org.python.apache.xerces.util.XMLChar;
import org.python.apache.xerces.util.XMLStringBuffer;
import org.python.apache.xerces.util.XMLSymbols;
import org.python.apache.xerces.xni.Augmentations;
import org.python.apache.xerces.xni.XMLDTDContentModelHandler;
import org.python.apache.xerces.xni.XMLDTDHandler;
import org.python.apache.xerces.xni.XMLLocator;
import org.python.apache.xerces.xni.XMLResourceIdentifier;
import org.python.apache.xerces.xni.XMLString;
import org.python.apache.xerces.xni.XNIException;
import org.python.apache.xerces.xni.parser.XMLComponent;
import org.python.apache.xerces.xni.parser.XMLComponentManager;
import org.python.apache.xerces.xni.parser.XMLConfigurationException;
import org.python.apache.xerces.xni.parser.XMLDTDScanner;
import org.python.apache.xerces.xni.parser.XMLInputSource;

public class XMLDTDScannerImpl extends XMLScanner implements XMLDTDScanner, XMLComponent, XMLEntityHandler {
   protected static final int SCANNER_STATE_END_OF_INPUT = 0;
   protected static final int SCANNER_STATE_TEXT_DECL = 1;
   protected static final int SCANNER_STATE_MARKUP_DECL = 2;
   private static final String[] RECOGNIZED_FEATURES = new String[]{"http://xml.org/sax/features/validation", "http://apache.org/xml/features/scanner/notify-char-refs"};
   private static final Boolean[] FEATURE_DEFAULTS;
   private static final String[] RECOGNIZED_PROPERTIES;
   private static final Object[] PROPERTY_DEFAULTS;
   private static final boolean DEBUG_SCANNER_STATE = false;
   protected XMLDTDHandler fDTDHandler;
   protected XMLDTDContentModelHandler fDTDContentModelHandler;
   protected int fScannerState;
   protected boolean fStandalone;
   protected boolean fSeenExternalDTD;
   protected boolean fSeenPEReferences;
   private boolean fStartDTDCalled;
   private int[] fContentStack = new int[5];
   private int fContentDepth;
   private int[] fPEStack = new int[5];
   private boolean[] fPEReport = new boolean[5];
   private int fPEDepth;
   private int fMarkUpDepth;
   private int fExtEntityDepth;
   private int fIncludeSectDepth;
   private final String[] fStrings = new String[3];
   private final XMLString fString = new XMLString();
   private final XMLStringBuffer fStringBuffer = new XMLStringBuffer();
   private final XMLStringBuffer fStringBuffer2 = new XMLStringBuffer();
   private final XMLString fLiteral = new XMLString();
   private final XMLString fLiteral2 = new XMLString();
   private String[] fEnumeration = new String[5];
   private int fEnumerationCount;
   private final XMLStringBuffer fIgnoreConditionalBuffer = new XMLStringBuffer(128);

   public XMLDTDScannerImpl() {
   }

   public XMLDTDScannerImpl(SymbolTable var1, XMLErrorReporter var2, XMLEntityManager var3) {
      this.fSymbolTable = var1;
      this.fErrorReporter = var2;
      this.fEntityManager = var3;
      var3.setProperty("http://apache.org/xml/properties/internal/symbol-table", this.fSymbolTable);
   }

   public void setInputSource(XMLInputSource var1) throws IOException {
      if (var1 == null) {
         if (this.fDTDHandler != null) {
            this.fDTDHandler.startDTD((XMLLocator)null, (Augmentations)null);
            this.fDTDHandler.endDTD((Augmentations)null);
         }

      } else {
         this.fEntityManager.setEntityHandler(this);
         this.fEntityManager.startDTDEntity(var1);
      }
   }

   public boolean scanDTDExternalSubset(boolean var1) throws IOException, XNIException {
      this.fEntityManager.setEntityHandler(this);
      if (this.fScannerState == 1) {
         this.fSeenExternalDTD = true;
         boolean var2 = this.scanTextDecl();
         if (this.fScannerState == 0) {
            return false;
         }

         this.setScannerState(2);
         if (var2 && !var1) {
            return true;
         }
      }

      while(this.scanDecls(var1)) {
         if (!var1) {
            return true;
         }
      }

      return false;
   }

   public boolean scanDTDInternalSubset(boolean var1, boolean var2, boolean var3) throws IOException, XNIException {
      this.fEntityScanner = this.fEntityManager.getEntityScanner();
      this.fEntityManager.setEntityHandler(this);
      this.fStandalone = var2;
      if (this.fScannerState == 1) {
         if (this.fDTDHandler != null) {
            this.fDTDHandler.startDTD(this.fEntityScanner, (Augmentations)null);
            this.fStartDTDCalled = true;
         }

         this.setScannerState(2);
      }

      while(this.scanDecls(var1)) {
         if (!var1) {
            return true;
         }
      }

      if (this.fDTDHandler != null && !var3) {
         this.fDTDHandler.endDTD((Augmentations)null);
      }

      this.setScannerState(1);
      return false;
   }

   public void reset(XMLComponentManager var1) throws XMLConfigurationException {
      super.reset(var1);
      this.init();
   }

   public void reset() {
      super.reset();
      this.init();
   }

   public String[] getRecognizedFeatures() {
      return (String[])RECOGNIZED_FEATURES.clone();
   }

   public String[] getRecognizedProperties() {
      return (String[])RECOGNIZED_PROPERTIES.clone();
   }

   public Boolean getFeatureDefault(String var1) {
      for(int var2 = 0; var2 < RECOGNIZED_FEATURES.length; ++var2) {
         if (RECOGNIZED_FEATURES[var2].equals(var1)) {
            return FEATURE_DEFAULTS[var2];
         }
      }

      return null;
   }

   public Object getPropertyDefault(String var1) {
      for(int var2 = 0; var2 < RECOGNIZED_PROPERTIES.length; ++var2) {
         if (RECOGNIZED_PROPERTIES[var2].equals(var1)) {
            return PROPERTY_DEFAULTS[var2];
         }
      }

      return null;
   }

   public void setDTDHandler(XMLDTDHandler var1) {
      this.fDTDHandler = var1;
   }

   public XMLDTDHandler getDTDHandler() {
      return this.fDTDHandler;
   }

   public void setDTDContentModelHandler(XMLDTDContentModelHandler var1) {
      this.fDTDContentModelHandler = var1;
   }

   public XMLDTDContentModelHandler getDTDContentModelHandler() {
      return this.fDTDContentModelHandler;
   }

   public void startEntity(String var1, XMLResourceIdentifier var2, String var3, Augmentations var4) throws XNIException {
      super.startEntity(var1, var2, var3, var4);
      boolean var5 = var1.equals("[dtd]");
      if (var5) {
         if (this.fDTDHandler != null && !this.fStartDTDCalled) {
            this.fDTDHandler.startDTD(this.fEntityScanner, (Augmentations)null);
         }

         if (this.fDTDHandler != null) {
            this.fDTDHandler.startExternalSubset(var2, (Augmentations)null);
         }

         this.fEntityManager.startExternalSubset();
         ++this.fExtEntityDepth;
      } else if (var1.charAt(0) == '%') {
         this.pushPEStack(this.fMarkUpDepth, this.fReportEntity);
         if (this.fEntityScanner.isExternal()) {
            ++this.fExtEntityDepth;
         }
      }

      if (this.fDTDHandler != null && !var5 && this.fReportEntity) {
         this.fDTDHandler.startParameterEntity(var1, var2, var3, var4);
      }

   }

   public void endEntity(String var1, Augmentations var2) throws XNIException {
      super.endEntity(var1, var2);
      if (this.fScannerState != 0) {
         boolean var3 = this.fReportEntity;
         if (var1.startsWith("%")) {
            var3 = this.peekReportEntity();
            int var4 = this.popPEStack();
            if (var4 == 0 && var4 < this.fMarkUpDepth) {
               this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "ILL_FORMED_PARAMETER_ENTITY_WHEN_USED_IN_DECL", new Object[]{this.fEntityManager.fCurrentEntity.name}, (short)2);
            }

            if (var4 != this.fMarkUpDepth) {
               var3 = false;
               if (this.fValidation) {
                  this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "ImproperDeclarationNesting", new Object[]{var1}, (short)1);
               }
            }

            if (this.fEntityScanner.isExternal()) {
               --this.fExtEntityDepth;
            }

            if (this.fDTDHandler != null && var3) {
               this.fDTDHandler.endParameterEntity(var1, var2);
            }
         } else if (var1.equals("[dtd]")) {
            if (this.fIncludeSectDepth != 0) {
               this.reportFatalError("IncludeSectUnterminated", (Object[])null);
            }

            this.fScannerState = 0;
            this.fEntityManager.endExternalSubset();
            if (this.fDTDHandler != null) {
               this.fDTDHandler.endExternalSubset((Augmentations)null);
               this.fDTDHandler.endDTD((Augmentations)null);
            }

            --this.fExtEntityDepth;
         }

      }
   }

   protected final void setScannerState(int var1) {
      this.fScannerState = var1;
   }

   private static String getScannerStateName(int var0) {
      return "??? (" + var0 + ')';
   }

   protected final boolean scanningInternalSubset() {
      return this.fExtEntityDepth == 0;
   }

   protected void startPE(String var1, boolean var2) throws IOException, XNIException {
      int var3 = this.fPEDepth;
      String var4 = "%" + var1;
      if (!this.fSeenPEReferences) {
         this.fSeenPEReferences = true;
         this.fEntityManager.notifyHasPEReferences();
      }

      if (this.fValidation && !this.fEntityManager.isDeclaredEntity(var4)) {
         this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "EntityNotDeclared", new Object[]{var1}, (short)1);
      }

      this.fEntityManager.startEntity(this.fSymbolTable.addSymbol(var4), var2);
      if (var3 != this.fPEDepth && this.fEntityScanner.isExternal()) {
         this.scanTextDecl();
      }

   }

   protected final boolean scanTextDecl() throws IOException, XNIException {
      boolean var1 = false;
      if (this.fEntityScanner.skipString("<?xml")) {
         ++this.fMarkUpDepth;
         String var2;
         if (this.isValidNameChar(this.fEntityScanner.peekChar())) {
            this.fStringBuffer.clear();
            this.fStringBuffer.append("xml");
            if (this.fNamespaces) {
               while(this.isValidNCName(this.fEntityScanner.peekChar())) {
                  this.fStringBuffer.append((char)this.fEntityScanner.scanChar());
               }
            } else {
               while(this.isValidNameChar(this.fEntityScanner.peekChar())) {
                  this.fStringBuffer.append((char)this.fEntityScanner.scanChar());
               }
            }

            var2 = this.fSymbolTable.addSymbol(this.fStringBuffer.ch, this.fStringBuffer.offset, this.fStringBuffer.length);
            this.scanPIData(var2, this.fString);
         } else {
            var2 = null;
            String var3 = null;
            this.scanXMLDeclOrTextDecl(true, this.fStrings);
            var1 = true;
            --this.fMarkUpDepth;
            var2 = this.fStrings[0];
            var3 = this.fStrings[1];
            this.fEntityScanner.setXMLVersion(var2);
            if (!this.fEntityScanner.fCurrentEntity.isEncodingExternallySpecified()) {
               this.fEntityScanner.setEncoding(var3);
            }

            if (this.fDTDHandler != null) {
               this.fDTDHandler.textDecl(var2, var3, (Augmentations)null);
            }
         }
      }

      this.fEntityManager.fCurrentEntity.mayReadChunks = true;
      return var1;
   }

   protected final void scanPIData(String var1, XMLString var2) throws IOException, XNIException {
      super.scanPIData(var1, var2);
      --this.fMarkUpDepth;
      if (this.fDTDHandler != null) {
         this.fDTDHandler.processingInstruction(var1, var2, (Augmentations)null);
      }

   }

   protected final void scanComment() throws IOException, XNIException {
      this.fReportEntity = false;
      this.scanComment(this.fStringBuffer);
      --this.fMarkUpDepth;
      if (this.fDTDHandler != null) {
         this.fDTDHandler.comment(this.fStringBuffer, (Augmentations)null);
      }

      this.fReportEntity = true;
   }

   protected final void scanElementDecl() throws IOException, XNIException {
      this.fReportEntity = false;
      if (!this.skipSeparator(true, !this.scanningInternalSubset())) {
         this.reportFatalError("MSG_SPACE_REQUIRED_BEFORE_ELEMENT_TYPE_IN_ELEMENTDECL", (Object[])null);
      }

      String var1 = this.fEntityScanner.scanName();
      if (var1 == null) {
         this.reportFatalError("MSG_ELEMENT_TYPE_REQUIRED_IN_ELEMENTDECL", (Object[])null);
      }

      if (!this.skipSeparator(true, !this.scanningInternalSubset())) {
         this.reportFatalError("MSG_SPACE_REQUIRED_BEFORE_CONTENTSPEC_IN_ELEMENTDECL", new Object[]{var1});
      }

      if (this.fDTDContentModelHandler != null) {
         this.fDTDContentModelHandler.startContentModel(var1, (Augmentations)null);
      }

      String var2 = null;
      this.fReportEntity = true;
      if (this.fEntityScanner.skipString("EMPTY")) {
         var2 = "EMPTY";
         if (this.fDTDContentModelHandler != null) {
            this.fDTDContentModelHandler.empty((Augmentations)null);
         }
      } else if (this.fEntityScanner.skipString("ANY")) {
         var2 = "ANY";
         if (this.fDTDContentModelHandler != null) {
            this.fDTDContentModelHandler.any((Augmentations)null);
         }
      } else {
         if (!this.fEntityScanner.skipChar(40)) {
            this.reportFatalError("MSG_OPEN_PAREN_OR_ELEMENT_TYPE_REQUIRED_IN_CHILDREN", new Object[]{var1});
         }

         if (this.fDTDContentModelHandler != null) {
            this.fDTDContentModelHandler.startGroup((Augmentations)null);
         }

         this.fStringBuffer.clear();
         this.fStringBuffer.append('(');
         ++this.fMarkUpDepth;
         this.skipSeparator(false, !this.scanningInternalSubset());
         if (this.fEntityScanner.skipString("#PCDATA")) {
            this.scanMixed(var1);
         } else {
            this.scanChildren(var1);
         }

         var2 = this.fStringBuffer.toString();
      }

      if (this.fDTDContentModelHandler != null) {
         this.fDTDContentModelHandler.endContentModel((Augmentations)null);
      }

      this.fReportEntity = false;
      this.skipSeparator(false, !this.scanningInternalSubset());
      if (!this.fEntityScanner.skipChar(62)) {
         this.reportFatalError("ElementDeclUnterminated", new Object[]{var1});
      }

      this.fReportEntity = true;
      --this.fMarkUpDepth;
      if (this.fDTDHandler != null) {
         this.fDTDHandler.elementDecl(var1, var2, (Augmentations)null);
      }

   }

   private final void scanMixed(String var1) throws IOException, XNIException {
      String var2 = null;
      this.fStringBuffer.append("#PCDATA");
      if (this.fDTDContentModelHandler != null) {
         this.fDTDContentModelHandler.pcdata((Augmentations)null);
      }

      this.skipSeparator(false, !this.scanningInternalSubset());

      for(; this.fEntityScanner.skipChar(124); this.skipSeparator(false, !this.scanningInternalSubset())) {
         this.fStringBuffer.append('|');
         if (this.fDTDContentModelHandler != null) {
            this.fDTDContentModelHandler.separator((short)0, (Augmentations)null);
         }

         this.skipSeparator(false, !this.scanningInternalSubset());
         var2 = this.fEntityScanner.scanName();
         if (var2 == null) {
            this.reportFatalError("MSG_ELEMENT_TYPE_REQUIRED_IN_MIXED_CONTENT", new Object[]{var1});
         }

         this.fStringBuffer.append(var2);
         if (this.fDTDContentModelHandler != null) {
            this.fDTDContentModelHandler.element(var2, (Augmentations)null);
         }
      }

      if (this.fEntityScanner.skipString(")*")) {
         this.fStringBuffer.append(")*");
         if (this.fDTDContentModelHandler != null) {
            this.fDTDContentModelHandler.endGroup((Augmentations)null);
            this.fDTDContentModelHandler.occurrence((short)3, (Augmentations)null);
         }
      } else if (var2 != null) {
         this.reportFatalError("MixedContentUnterminated", new Object[]{var1});
      } else if (this.fEntityScanner.skipChar(41)) {
         this.fStringBuffer.append(')');
         if (this.fDTDContentModelHandler != null) {
            this.fDTDContentModelHandler.endGroup((Augmentations)null);
         }
      } else {
         this.reportFatalError("MSG_CLOSE_PAREN_REQUIRED_IN_CHILDREN", new Object[]{var1});
      }

      --this.fMarkUpDepth;
   }

   private final void scanChildren(String var1) throws IOException, XNIException {
      this.fContentDepth = 0;
      this.pushContentStack(0);
      int var2 = 0;

      while(true) {
         label116:
         for(; !this.fEntityScanner.skipChar(40); this.skipSeparator(false, !this.scanningInternalSubset())) {
            this.skipSeparator(false, !this.scanningInternalSubset());
            String var3 = this.fEntityScanner.scanName();
            if (var3 == null) {
               this.reportFatalError("MSG_OPEN_PAREN_OR_ELEMENT_TYPE_REQUIRED_IN_CHILDREN", new Object[]{var1});
               return;
            }

            if (this.fDTDContentModelHandler != null) {
               this.fDTDContentModelHandler.element(var3, (Augmentations)null);
            }

            this.fStringBuffer.append(var3);
            int var4 = this.fEntityScanner.peekChar();
            byte var5;
            if (var4 == 63 || var4 == 42 || var4 == 43) {
               if (this.fDTDContentModelHandler != null) {
                  if (var4 == 63) {
                     var5 = 2;
                  } else if (var4 == 42) {
                     var5 = 3;
                  } else {
                     var5 = 4;
                  }

                  this.fDTDContentModelHandler.occurrence(var5, (Augmentations)null);
               }

               this.fEntityScanner.scanChar();
               this.fStringBuffer.append((char)var4);
            }

            do {
               this.skipSeparator(false, !this.scanningInternalSubset());
               var4 = this.fEntityScanner.peekChar();
               if (var4 == 44 && var2 != 124) {
                  var2 = var4;
                  if (this.fDTDContentModelHandler != null) {
                     this.fDTDContentModelHandler.separator((short)1, (Augmentations)null);
                  }

                  this.fEntityScanner.scanChar();
                  this.fStringBuffer.append(',');
                  continue label116;
               }

               if (var4 == 124 && var2 != 44) {
                  var2 = var4;
                  if (this.fDTDContentModelHandler != null) {
                     this.fDTDContentModelHandler.separator((short)0, (Augmentations)null);
                  }

                  this.fEntityScanner.scanChar();
                  this.fStringBuffer.append('|');
                  continue label116;
               }

               if (var4 != 41) {
                  this.reportFatalError("MSG_CLOSE_PAREN_REQUIRED_IN_CHILDREN", new Object[]{var1});
               }

               if (this.fDTDContentModelHandler != null) {
                  this.fDTDContentModelHandler.endGroup((Augmentations)null);
               }

               var2 = this.popContentStack();
               if (this.fEntityScanner.skipString(")?")) {
                  this.fStringBuffer.append(")?");
                  if (this.fDTDContentModelHandler != null) {
                     var5 = 2;
                     this.fDTDContentModelHandler.occurrence(var5, (Augmentations)null);
                  }
               } else if (this.fEntityScanner.skipString(")+")) {
                  this.fStringBuffer.append(")+");
                  if (this.fDTDContentModelHandler != null) {
                     var5 = 4;
                     this.fDTDContentModelHandler.occurrence(var5, (Augmentations)null);
                  }
               } else if (this.fEntityScanner.skipString(")*")) {
                  this.fStringBuffer.append(")*");
                  if (this.fDTDContentModelHandler != null) {
                     var5 = 3;
                     this.fDTDContentModelHandler.occurrence(var5, (Augmentations)null);
                  }
               } else {
                  this.fEntityScanner.scanChar();
                  this.fStringBuffer.append(')');
               }

               --this.fMarkUpDepth;
            } while(this.fContentDepth != 0);

            return;
         }

         ++this.fMarkUpDepth;
         this.fStringBuffer.append('(');
         if (this.fDTDContentModelHandler != null) {
            this.fDTDContentModelHandler.startGroup((Augmentations)null);
         }

         this.pushContentStack(var2);
         var2 = 0;
         this.skipSeparator(false, !this.scanningInternalSubset());
      }
   }

   protected final void scanAttlistDecl() throws IOException, XNIException {
      this.fReportEntity = false;
      if (!this.skipSeparator(true, !this.scanningInternalSubset())) {
         this.reportFatalError("MSG_SPACE_REQUIRED_BEFORE_ELEMENT_TYPE_IN_ATTLISTDECL", (Object[])null);
      }

      String var1 = this.fEntityScanner.scanName();
      if (var1 == null) {
         this.reportFatalError("MSG_ELEMENT_TYPE_REQUIRED_IN_ATTLISTDECL", (Object[])null);
      }

      if (this.fDTDHandler != null) {
         this.fDTDHandler.startAttlist(var1, (Augmentations)null);
      }

      if (!this.skipSeparator(true, !this.scanningInternalSubset())) {
         if (this.fEntityScanner.skipChar(62)) {
            if (this.fDTDHandler != null) {
               this.fDTDHandler.endAttlist((Augmentations)null);
            }

            --this.fMarkUpDepth;
            return;
         }

         this.reportFatalError("MSG_SPACE_REQUIRED_BEFORE_ATTRIBUTE_NAME_IN_ATTDEF", new Object[]{var1});
      }

      for(; !this.fEntityScanner.skipChar(62); this.skipSeparator(false, !this.scanningInternalSubset())) {
         String var2 = this.fEntityScanner.scanName();
         if (var2 == null) {
            this.reportFatalError("AttNameRequiredInAttDef", new Object[]{var1});
         }

         if (!this.skipSeparator(true, !this.scanningInternalSubset())) {
            this.reportFatalError("MSG_SPACE_REQUIRED_BEFORE_ATTTYPE_IN_ATTDEF", new Object[]{var1, var2});
         }

         String var3 = this.scanAttType(var1, var2);
         if (!this.skipSeparator(true, !this.scanningInternalSubset())) {
            this.reportFatalError("MSG_SPACE_REQUIRED_BEFORE_DEFAULTDECL_IN_ATTDEF", new Object[]{var1, var2});
         }

         String var4 = this.scanAttDefaultDecl(var1, var2, var3, this.fLiteral, this.fLiteral2);
         if (this.fDTDHandler != null) {
            String[] var5 = null;
            if (this.fEnumerationCount != 0) {
               var5 = new String[this.fEnumerationCount];
               System.arraycopy(this.fEnumeration, 0, var5, 0, this.fEnumerationCount);
            }

            if (var4 == null || !var4.equals("#REQUIRED") && !var4.equals("#IMPLIED")) {
               this.fDTDHandler.attributeDecl(var1, var2, var3, var5, var4, this.fLiteral, this.fLiteral2, (Augmentations)null);
            } else {
               this.fDTDHandler.attributeDecl(var1, var2, var3, var5, var4, (XMLString)null, (XMLString)null, (Augmentations)null);
            }
         }
      }

      if (this.fDTDHandler != null) {
         this.fDTDHandler.endAttlist((Augmentations)null);
      }

      --this.fMarkUpDepth;
      this.fReportEntity = true;
   }

   private final String scanAttType(String var1, String var2) throws IOException, XNIException {
      String var3 = null;
      this.fEnumerationCount = 0;
      if (this.fEntityScanner.skipString("CDATA")) {
         var3 = "CDATA";
      } else if (this.fEntityScanner.skipString("IDREFS")) {
         var3 = "IDREFS";
      } else if (this.fEntityScanner.skipString("IDREF")) {
         var3 = "IDREF";
      } else if (this.fEntityScanner.skipString("ID")) {
         var3 = "ID";
      } else if (this.fEntityScanner.skipString("ENTITY")) {
         var3 = "ENTITY";
      } else if (this.fEntityScanner.skipString("ENTITIES")) {
         var3 = "ENTITIES";
      } else if (this.fEntityScanner.skipString("NMTOKENS")) {
         var3 = "NMTOKENS";
      } else if (this.fEntityScanner.skipString("NMTOKEN")) {
         var3 = "NMTOKEN";
      } else {
         int var4;
         String var5;
         if (this.fEntityScanner.skipString("NOTATION")) {
            var3 = "NOTATION";
            if (!this.skipSeparator(true, !this.scanningInternalSubset())) {
               this.reportFatalError("MSG_SPACE_REQUIRED_AFTER_NOTATION_IN_NOTATIONTYPE", new Object[]{var1, var2});
            }

            var4 = this.fEntityScanner.scanChar();
            if (var4 != 40) {
               this.reportFatalError("MSG_OPEN_PAREN_REQUIRED_IN_NOTATIONTYPE", new Object[]{var1, var2});
            }

            ++this.fMarkUpDepth;

            do {
               this.skipSeparator(false, !this.scanningInternalSubset());
               var5 = this.fEntityScanner.scanName();
               if (var5 == null) {
                  this.reportFatalError("MSG_NAME_REQUIRED_IN_NOTATIONTYPE", new Object[]{var1, var2});
                  var4 = this.skipInvalidEnumerationValue();
                  if (var4 != 124) {
                     break;
                  }
               } else {
                  this.ensureEnumerationSize(this.fEnumerationCount + 1);
                  this.fEnumeration[this.fEnumerationCount++] = var5;
                  this.skipSeparator(false, !this.scanningInternalSubset());
                  var4 = this.fEntityScanner.scanChar();
               }
            } while(var4 == 124);

            if (var4 != 41) {
               this.reportFatalError("NotationTypeUnterminated", new Object[]{var1, var2});
            }

            --this.fMarkUpDepth;
         } else {
            var3 = "ENUMERATION";
            var4 = this.fEntityScanner.scanChar();
            if (var4 != 40) {
               this.reportFatalError("AttTypeRequiredInAttDef", new Object[]{var1, var2});
            }

            ++this.fMarkUpDepth;

            do {
               this.skipSeparator(false, !this.scanningInternalSubset());
               var5 = this.fEntityScanner.scanNmtoken();
               if (var5 == null) {
                  this.reportFatalError("MSG_NMTOKEN_REQUIRED_IN_ENUMERATION", new Object[]{var1, var2});
                  var4 = this.skipInvalidEnumerationValue();
                  if (var4 != 124) {
                     break;
                  }
               } else {
                  this.ensureEnumerationSize(this.fEnumerationCount + 1);
                  this.fEnumeration[this.fEnumerationCount++] = var5;
                  this.skipSeparator(false, !this.scanningInternalSubset());
                  var4 = this.fEntityScanner.scanChar();
               }
            } while(var4 == 124);

            if (var4 != 41) {
               this.reportFatalError("EnumerationUnterminated", new Object[]{var1, var2});
            }

            --this.fMarkUpDepth;
         }
      }

      return var3;
   }

   protected final String scanAttDefaultDecl(String var1, String var2, String var3, XMLString var4, XMLString var5) throws IOException, XNIException {
      String var6 = null;
      this.fString.clear();
      var4.clear();
      if (this.fEntityScanner.skipString("#REQUIRED")) {
         var6 = "#REQUIRED";
      } else if (this.fEntityScanner.skipString("#IMPLIED")) {
         var6 = "#IMPLIED";
      } else {
         if (this.fEntityScanner.skipString("#FIXED")) {
            var6 = "#FIXED";
            if (!this.skipSeparator(true, !this.scanningInternalSubset())) {
               this.reportFatalError("MSG_SPACE_REQUIRED_AFTER_FIXED_IN_DEFAULTDECL", new Object[]{var1, var2});
            }
         }

         boolean var7 = !this.fStandalone && (this.fSeenExternalDTD || this.fSeenPEReferences);
         this.scanAttributeValue(var4, var5, var2, var7, var1);
      }

      return var6;
   }

   private final void scanEntityDecl() throws IOException, XNIException {
      boolean var1 = false;
      boolean var2 = false;
      this.fReportEntity = false;
      if (this.fEntityScanner.skipSpaces()) {
         if (!this.fEntityScanner.skipChar(37)) {
            var1 = false;
         } else if (this.skipSeparator(true, !this.scanningInternalSubset())) {
            var1 = true;
         } else if (this.scanningInternalSubset()) {
            this.reportFatalError("MSG_SPACE_REQUIRED_BEFORE_ENTITY_NAME_IN_PEDECL", (Object[])null);
            var1 = true;
         } else if (this.fEntityScanner.peekChar() == 37) {
            this.skipSeparator(false, !this.scanningInternalSubset());
            var1 = true;
         } else {
            var2 = true;
         }
      } else if (!this.scanningInternalSubset() && this.fEntityScanner.skipChar(37)) {
         if (this.fEntityScanner.skipSpaces()) {
            this.reportFatalError("MSG_SPACE_REQUIRED_BEFORE_PERCENT_IN_PEDECL", (Object[])null);
            var1 = false;
         } else {
            var2 = true;
         }
      } else {
         this.reportFatalError("MSG_SPACE_REQUIRED_BEFORE_ENTITY_NAME_IN_ENTITYDECL", (Object[])null);
         var1 = false;
      }

      String var3;
      if (var2) {
         while(true) {
            var3 = this.fEntityScanner.scanName();
            if (var3 == null) {
               this.reportFatalError("NameRequiredInPEReference", (Object[])null);
            } else if (!this.fEntityScanner.skipChar(59)) {
               this.reportFatalError("SemicolonRequiredInPEReference", new Object[]{var3});
            } else {
               this.startPE(var3, false);
            }

            this.fEntityScanner.skipSpaces();
            if (!this.fEntityScanner.skipChar(37)) {
               break;
            }

            if (!var1) {
               if (this.skipSeparator(true, !this.scanningInternalSubset())) {
                  var1 = true;
                  break;
               }

               var1 = this.fEntityScanner.skipChar(37);
            }
         }
      }

      var3 = null;
      if (this.fNamespaces) {
         var3 = this.fEntityScanner.scanNCName();
      } else {
         var3 = this.fEntityScanner.scanName();
      }

      if (var3 == null) {
         this.reportFatalError("MSG_ENTITY_NAME_REQUIRED_IN_ENTITYDECL", (Object[])null);
      }

      String var5;
      if (!this.skipSeparator(true, !this.scanningInternalSubset())) {
         if (this.fNamespaces && this.fEntityScanner.peekChar() == 58) {
            this.fEntityScanner.scanChar();
            XMLStringBuffer var4 = new XMLStringBuffer(var3);
            var4.append(':');
            var5 = this.fEntityScanner.scanName();
            if (var5 != null) {
               var4.append(var5);
            }

            this.reportFatalError("ColonNotLegalWithNS", new Object[]{var4.toString()});
            if (!this.skipSeparator(true, !this.scanningInternalSubset())) {
               this.reportFatalError("MSG_SPACE_REQUIRED_AFTER_ENTITY_NAME_IN_ENTITYDECL", new Object[]{var3});
            }
         } else {
            this.reportFatalError("MSG_SPACE_REQUIRED_AFTER_ENTITY_NAME_IN_ENTITYDECL", new Object[]{var3});
         }
      }

      this.scanExternalID(this.fStrings, false);
      String var9 = this.fStrings[0];
      var5 = this.fStrings[1];
      String var6 = null;
      boolean var7 = this.skipSeparator(true, !this.scanningInternalSubset());
      if (!var1 && this.fEntityScanner.skipString("NDATA")) {
         if (!var7) {
            this.reportFatalError("MSG_SPACE_REQUIRED_BEFORE_NDATA_IN_UNPARSED_ENTITYDECL", new Object[]{var3});
         }

         if (!this.skipSeparator(true, !this.scanningInternalSubset())) {
            this.reportFatalError("MSG_SPACE_REQUIRED_BEFORE_NOTATION_NAME_IN_UNPARSED_ENTITYDECL", new Object[]{var3});
         }

         var6 = this.fEntityScanner.scanName();
         if (var6 == null) {
            this.reportFatalError("MSG_NOTATION_NAME_REQUIRED_FOR_UNPARSED_ENTITYDECL", new Object[]{var3});
         }
      }

      if (var9 == null) {
         this.scanEntityValue(this.fLiteral, this.fLiteral2);
         this.fStringBuffer.clear();
         this.fStringBuffer2.clear();
         this.fStringBuffer.append(this.fLiteral.ch, this.fLiteral.offset, this.fLiteral.length);
         this.fStringBuffer2.append(this.fLiteral2.ch, this.fLiteral2.offset, this.fLiteral2.length);
      }

      this.skipSeparator(false, !this.scanningInternalSubset());
      if (!this.fEntityScanner.skipChar(62)) {
         this.reportFatalError("EntityDeclUnterminated", new Object[]{var3});
      }

      --this.fMarkUpDepth;
      if (var1) {
         var3 = "%" + var3;
      }

      if (var9 != null) {
         String var8 = this.fEntityScanner.getBaseSystemId();
         if (var6 != null) {
            this.fEntityManager.addUnparsedEntity(var3, var5, var9, var8, var6);
         } else {
            this.fEntityManager.addExternalEntity(var3, var5, var9, var8);
         }

         if (this.fDTDHandler != null) {
            this.fResourceIdentifier.setValues(var5, var9, var8, XMLEntityManager.expandSystemId(var9, var8, false));
            if (var6 != null) {
               this.fDTDHandler.unparsedEntityDecl(var3, this.fResourceIdentifier, var6, (Augmentations)null);
            } else {
               this.fDTDHandler.externalEntityDecl(var3, this.fResourceIdentifier, (Augmentations)null);
            }
         }
      } else {
         this.fEntityManager.addInternalEntity(var3, this.fStringBuffer.toString());
         if (this.fDTDHandler != null) {
            this.fDTDHandler.internalEntityDecl(var3, this.fStringBuffer, this.fStringBuffer2, (Augmentations)null);
         }
      }

      this.fReportEntity = true;
   }

   protected final void scanEntityValue(XMLString var1, XMLString var2) throws IOException, XNIException {
      int var3 = this.fEntityScanner.scanChar();
      if (var3 != 39 && var3 != 34) {
         this.reportFatalError("OpenQuoteMissingInDecl", (Object[])null);
      }

      int var4 = this.fEntityDepth;
      Object var5 = this.fString;
      Object var6 = this.fString;
      if (this.fEntityScanner.scanLiteral(var3, this.fString) != var3) {
         this.fStringBuffer.clear();
         this.fStringBuffer2.clear();

         do {
            this.fStringBuffer.append(this.fString);
            this.fStringBuffer2.append(this.fString);
            String var8;
            if (this.fEntityScanner.skipChar(38)) {
               if (this.fEntityScanner.skipChar(35)) {
                  this.fStringBuffer2.append("&#");
                  this.scanCharReferenceValue(this.fStringBuffer, this.fStringBuffer2);
               } else {
                  this.fStringBuffer.append('&');
                  this.fStringBuffer2.append('&');
                  var8 = this.fEntityScanner.scanName();
                  if (var8 == null) {
                     this.reportFatalError("NameRequiredInReference", (Object[])null);
                  } else {
                     this.fStringBuffer.append(var8);
                     this.fStringBuffer2.append(var8);
                  }

                  if (!this.fEntityScanner.skipChar(59)) {
                     this.reportFatalError("SemicolonRequiredInReference", new Object[]{var8});
                  } else {
                     this.fStringBuffer.append(';');
                     this.fStringBuffer2.append(';');
                  }
               }
            } else if (this.fEntityScanner.skipChar(37)) {
               do {
                  this.fStringBuffer2.append('%');
                  var8 = this.fEntityScanner.scanName();
                  if (var8 == null) {
                     this.reportFatalError("NameRequiredInPEReference", (Object[])null);
                  } else if (!this.fEntityScanner.skipChar(59)) {
                     this.reportFatalError("SemicolonRequiredInPEReference", new Object[]{var8});
                  } else {
                     if (this.scanningInternalSubset()) {
                        this.reportFatalError("PEReferenceWithinMarkup", new Object[]{var8});
                     }

                     this.fStringBuffer2.append(var8);
                     this.fStringBuffer2.append(';');
                  }

                  this.startPE(var8, true);
                  this.fEntityScanner.skipSpaces();
               } while(this.fEntityScanner.skipChar(37));
            } else {
               int var7 = this.fEntityScanner.peekChar();
               if (XMLChar.isHighSurrogate(var7)) {
                  this.scanSurrogates(this.fStringBuffer2);
               } else if (this.isInvalidLiteral(var7)) {
                  this.reportFatalError("InvalidCharInLiteral", new Object[]{Integer.toHexString(var7)});
                  this.fEntityScanner.scanChar();
               } else if (var7 != var3 || var4 != this.fEntityDepth) {
                  this.fStringBuffer.append((char)var7);
                  this.fStringBuffer2.append((char)var7);
                  this.fEntityScanner.scanChar();
               }
            }
         } while(this.fEntityScanner.scanLiteral(var3, this.fString) != var3);

         this.fStringBuffer.append(this.fString);
         this.fStringBuffer2.append(this.fString);
         var5 = this.fStringBuffer;
         var6 = this.fStringBuffer2;
      }

      var1.setValues((XMLString)var5);
      var2.setValues((XMLString)var6);
      if (!this.fEntityScanner.skipChar(var3)) {
         this.reportFatalError("CloseQuoteMissingInDecl", (Object[])null);
      }

   }

   private final void scanNotationDecl() throws IOException, XNIException {
      this.fReportEntity = false;
      if (!this.skipSeparator(true, !this.scanningInternalSubset())) {
         this.reportFatalError("MSG_SPACE_REQUIRED_BEFORE_NOTATION_NAME_IN_NOTATIONDECL", (Object[])null);
      }

      String var1 = null;
      if (this.fNamespaces) {
         var1 = this.fEntityScanner.scanNCName();
      } else {
         var1 = this.fEntityScanner.scanName();
      }

      if (var1 == null) {
         this.reportFatalError("MSG_NOTATION_NAME_REQUIRED_IN_NOTATIONDECL", (Object[])null);
      }

      if (!this.skipSeparator(true, !this.scanningInternalSubset())) {
         if (this.fNamespaces && this.fEntityScanner.peekChar() == 58) {
            this.fEntityScanner.scanChar();
            XMLStringBuffer var2 = new XMLStringBuffer(var1);
            var2.append(':');
            var2.append(this.fEntityScanner.scanName());
            this.reportFatalError("ColonNotLegalWithNS", new Object[]{var2.toString()});
            this.skipSeparator(true, !this.scanningInternalSubset());
         } else {
            this.reportFatalError("MSG_SPACE_REQUIRED_AFTER_NOTATION_NAME_IN_NOTATIONDECL", new Object[]{var1});
         }
      }

      this.scanExternalID(this.fStrings, true);
      String var5 = this.fStrings[0];
      String var3 = this.fStrings[1];
      String var4 = this.fEntityScanner.getBaseSystemId();
      if (var5 == null && var3 == null) {
         this.reportFatalError("ExternalIDorPublicIDRequired", new Object[]{var1});
      }

      this.skipSeparator(false, !this.scanningInternalSubset());
      if (!this.fEntityScanner.skipChar(62)) {
         this.reportFatalError("NotationDeclUnterminated", new Object[]{var1});
      }

      --this.fMarkUpDepth;
      if (this.fDTDHandler != null) {
         this.fResourceIdentifier.setValues(var3, var5, var4, XMLEntityManager.expandSystemId(var5, var4, false));
         this.fDTDHandler.notationDecl(var1, this.fResourceIdentifier, (Augmentations)null);
      }

      this.fReportEntity = true;
   }

   private final void scanConditionalSect(int var1) throws IOException, XNIException {
      this.fReportEntity = false;
      this.skipSeparator(false, !this.scanningInternalSubset());
      if (this.fEntityScanner.skipString("INCLUDE")) {
         this.skipSeparator(false, !this.scanningInternalSubset());
         if (var1 != this.fPEDepth && this.fValidation) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "INVALID_PE_IN_CONDITIONAL", new Object[]{this.fEntityManager.fCurrentEntity.name}, (short)1);
         }

         if (!this.fEntityScanner.skipChar(91)) {
            this.reportFatalError("MSG_MARKUP_NOT_RECOGNIZED_IN_DTD", (Object[])null);
         }

         if (this.fDTDHandler != null) {
            this.fDTDHandler.startConditional((short)0, (Augmentations)null);
         }

         ++this.fIncludeSectDepth;
         this.fReportEntity = true;
      } else {
         if (this.fEntityScanner.skipString("IGNORE")) {
            this.skipSeparator(false, !this.scanningInternalSubset());
            if (var1 != this.fPEDepth && this.fValidation) {
               this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "INVALID_PE_IN_CONDITIONAL", new Object[]{this.fEntityManager.fCurrentEntity.name}, (short)1);
            }

            if (this.fDTDHandler != null) {
               this.fDTDHandler.startConditional((short)1, (Augmentations)null);
            }

            if (!this.fEntityScanner.skipChar(91)) {
               this.reportFatalError("MSG_MARKUP_NOT_RECOGNIZED_IN_DTD", (Object[])null);
            }

            this.fReportEntity = true;
            int var2 = ++this.fIncludeSectDepth;
            if (this.fDTDHandler != null) {
               this.fIgnoreConditionalBuffer.clear();
            }

            while(true) {
               while(!this.fEntityScanner.skipChar(60)) {
                  if (!this.fEntityScanner.skipChar(93)) {
                     int var3 = this.fEntityScanner.scanChar();
                     if (this.fScannerState == 0) {
                        this.reportFatalError("IgnoreSectUnterminated", (Object[])null);
                        return;
                     }

                     if (this.fDTDHandler != null) {
                        this.fIgnoreConditionalBuffer.append((char)var3);
                     }
                  } else {
                     if (this.fDTDHandler != null) {
                        this.fIgnoreConditionalBuffer.append(']');
                     }

                     if (this.fEntityScanner.skipChar(93)) {
                        if (this.fDTDHandler != null) {
                           this.fIgnoreConditionalBuffer.append(']');
                        }

                        while(this.fEntityScanner.skipChar(93)) {
                           if (this.fDTDHandler != null) {
                              this.fIgnoreConditionalBuffer.append(']');
                           }
                        }

                        if (this.fEntityScanner.skipChar(62)) {
                           if (this.fIncludeSectDepth-- == var2) {
                              --this.fMarkUpDepth;
                              if (this.fDTDHandler != null) {
                                 this.fLiteral.setValues(this.fIgnoreConditionalBuffer.ch, 0, this.fIgnoreConditionalBuffer.length - 2);
                                 this.fDTDHandler.ignoredCharacters(this.fLiteral, (Augmentations)null);
                                 this.fDTDHandler.endConditional((Augmentations)null);
                              }

                              return;
                           }

                           if (this.fDTDHandler != null) {
                              this.fIgnoreConditionalBuffer.append('>');
                           }
                        }
                     }
                  }
               }

               if (this.fDTDHandler != null) {
                  this.fIgnoreConditionalBuffer.append('<');
               }

               if (this.fEntityScanner.skipChar(33)) {
                  if (this.fEntityScanner.skipChar(91)) {
                     if (this.fDTDHandler != null) {
                        this.fIgnoreConditionalBuffer.append("![");
                     }

                     ++this.fIncludeSectDepth;
                  } else if (this.fDTDHandler != null) {
                     this.fIgnoreConditionalBuffer.append("!");
                  }
               }
            }
         }

         this.reportFatalError("MSG_MARKUP_NOT_RECOGNIZED_IN_DTD", (Object[])null);
      }

   }

   protected final boolean scanDecls(boolean var1) throws IOException, XNIException {
      this.skipSeparator(false, true);

      for(boolean var2 = true; var2 && this.fScannerState == 2; this.skipSeparator(false, true)) {
         var2 = var1;
         if (this.fEntityScanner.skipChar(60)) {
            ++this.fMarkUpDepth;
            if (this.fEntityScanner.skipChar(63)) {
               this.scanPI();
            } else if (this.fEntityScanner.skipChar(33)) {
               if (this.fEntityScanner.skipChar(45)) {
                  if (!this.fEntityScanner.skipChar(45)) {
                     this.reportFatalError("MSG_MARKUP_NOT_RECOGNIZED_IN_DTD", (Object[])null);
                  } else {
                     this.scanComment();
                  }
               } else if (this.fEntityScanner.skipString("ELEMENT")) {
                  this.scanElementDecl();
               } else if (this.fEntityScanner.skipString("ATTLIST")) {
                  this.scanAttlistDecl();
               } else if (this.fEntityScanner.skipString("ENTITY")) {
                  this.scanEntityDecl();
               } else if (this.fEntityScanner.skipString("NOTATION")) {
                  this.scanNotationDecl();
               } else if (this.fEntityScanner.skipChar(91) && !this.scanningInternalSubset()) {
                  this.scanConditionalSect(this.fPEDepth);
               } else {
                  --this.fMarkUpDepth;
                  this.reportFatalError("MSG_MARKUP_NOT_RECOGNIZED_IN_DTD", (Object[])null);
               }
            } else {
               --this.fMarkUpDepth;
               this.reportFatalError("MSG_MARKUP_NOT_RECOGNIZED_IN_DTD", (Object[])null);
            }
         } else if (this.fIncludeSectDepth > 0 && this.fEntityScanner.skipChar(93)) {
            if (!this.fEntityScanner.skipChar(93) || !this.fEntityScanner.skipChar(62)) {
               this.reportFatalError("IncludeSectUnterminated", (Object[])null);
            }

            if (this.fDTDHandler != null) {
               this.fDTDHandler.endConditional((Augmentations)null);
            }

            --this.fIncludeSectDepth;
            --this.fMarkUpDepth;
         } else {
            if (this.scanningInternalSubset() && this.fEntityScanner.peekChar() == 93) {
               return false;
            }

            if (!this.fEntityScanner.skipSpaces()) {
               this.reportFatalError("MSG_MARKUP_NOT_RECOGNIZED_IN_DTD", (Object[])null);

               int var3;
               do {
                  this.fEntityScanner.scanChar();
                  this.skipSeparator(false, true);
                  var3 = this.fEntityScanner.peekChar();
               } while(var3 != 60 && var3 != 93 && !XMLChar.isSpace(var3));
            }
         }
      }

      return this.fScannerState != 0;
   }

   private boolean skipSeparator(boolean var1, boolean var2) throws IOException, XNIException {
      int var3 = this.fPEDepth;
      boolean var4 = this.fEntityScanner.skipSpaces();
      if (var2 && this.fEntityScanner.skipChar(37)) {
         do {
            String var5 = this.fEntityScanner.scanName();
            if (var5 == null) {
               this.reportFatalError("NameRequiredInPEReference", (Object[])null);
            } else if (!this.fEntityScanner.skipChar(59)) {
               this.reportFatalError("SemicolonRequiredInPEReference", new Object[]{var5});
            }

            this.startPE(var5, false);
            this.fEntityScanner.skipSpaces();
         } while(this.fEntityScanner.skipChar(37));

         return true;
      } else {
         return !var1 || var4 || var3 != this.fPEDepth;
      }
   }

   private final void pushContentStack(int var1) {
      if (this.fContentStack.length == this.fContentDepth) {
         int[] var2 = new int[this.fContentDepth * 2];
         System.arraycopy(this.fContentStack, 0, var2, 0, this.fContentDepth);
         this.fContentStack = var2;
      }

      this.fContentStack[this.fContentDepth++] = var1;
   }

   private final int popContentStack() {
      return this.fContentStack[--this.fContentDepth];
   }

   private final void pushPEStack(int var1, boolean var2) {
      if (this.fPEStack.length == this.fPEDepth) {
         int[] var3 = new int[this.fPEDepth * 2];
         System.arraycopy(this.fPEStack, 0, var3, 0, this.fPEDepth);
         this.fPEStack = var3;
         boolean[] var4 = new boolean[this.fPEDepth * 2];
         System.arraycopy(this.fPEReport, 0, var4, 0, this.fPEDepth);
         this.fPEReport = var4;
      }

      this.fPEReport[this.fPEDepth] = var2;
      this.fPEStack[this.fPEDepth++] = var1;
   }

   private final int popPEStack() {
      return this.fPEStack[--this.fPEDepth];
   }

   private final boolean peekReportEntity() {
      return this.fPEReport[this.fPEDepth - 1];
   }

   private final void ensureEnumerationSize(int var1) {
      if (this.fEnumeration.length == var1) {
         String[] var2 = new String[var1 * 2];
         System.arraycopy(this.fEnumeration, 0, var2, 0, var1);
         this.fEnumeration = var2;
      }

   }

   private void init() {
      this.fStartDTDCalled = false;
      this.fExtEntityDepth = 0;
      this.fIncludeSectDepth = 0;
      this.fMarkUpDepth = 0;
      this.fPEDepth = 0;
      this.fStandalone = false;
      this.fSeenExternalDTD = false;
      this.fSeenPEReferences = false;
      this.setScannerState(1);
   }

   private int skipInvalidEnumerationValue() throws IOException {
      int var1;
      do {
         var1 = this.fEntityScanner.scanChar();
      } while(var1 != 124 && var1 != 41);

      this.ensureEnumerationSize(this.fEnumerationCount + 1);
      this.fEnumeration[this.fEnumerationCount++] = XMLSymbols.EMPTY_STRING;
      return var1;
   }

   static {
      FEATURE_DEFAULTS = new Boolean[]{null, Boolean.FALSE};
      RECOGNIZED_PROPERTIES = new String[]{"http://apache.org/xml/properties/internal/symbol-table", "http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/entity-manager"};
      PROPERTY_DEFAULTS = new Object[]{null, null, null};
   }
}
