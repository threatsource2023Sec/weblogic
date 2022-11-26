package weblogic.apache.xerces.impl;

import java.io.CharConversionException;
import java.io.EOFException;
import java.io.IOException;
import weblogic.apache.xerces.impl.dtd.XMLDTDDescription;
import weblogic.apache.xerces.impl.io.MalformedByteSequenceException;
import weblogic.apache.xerces.impl.validation.ValidationManager;
import weblogic.apache.xerces.util.NamespaceSupport;
import weblogic.apache.xerces.util.XMLChar;
import weblogic.apache.xerces.util.XMLStringBuffer;
import weblogic.apache.xerces.xni.Augmentations;
import weblogic.apache.xerces.xni.NamespaceContext;
import weblogic.apache.xerces.xni.XMLResourceIdentifier;
import weblogic.apache.xerces.xni.XMLString;
import weblogic.apache.xerces.xni.XNIException;
import weblogic.apache.xerces.xni.parser.XMLComponentManager;
import weblogic.apache.xerces.xni.parser.XMLConfigurationException;
import weblogic.apache.xerces.xni.parser.XMLDTDScanner;
import weblogic.apache.xerces.xni.parser.XMLInputSource;

public class XMLDocumentScannerImpl extends XMLDocumentFragmentScannerImpl {
   protected static final int SCANNER_STATE_XML_DECL = 0;
   protected static final int SCANNER_STATE_PROLOG = 5;
   protected static final int SCANNER_STATE_TRAILING_MISC = 12;
   protected static final int SCANNER_STATE_DTD_INTERNAL_DECLS = 17;
   protected static final int SCANNER_STATE_DTD_EXTERNAL = 18;
   protected static final int SCANNER_STATE_DTD_EXTERNAL_DECLS = 19;
   protected static final String LOAD_EXTERNAL_DTD = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
   protected static final String DISALLOW_DOCTYPE_DECL_FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
   protected static final String DTD_SCANNER = "http://apache.org/xml/properties/internal/dtd-scanner";
   protected static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
   protected static final String NAMESPACE_CONTEXT = "http://apache.org/xml/properties/internal/namespace-context";
   private static final String[] RECOGNIZED_FEATURES = new String[]{"http://apache.org/xml/features/nonvalidating/load-external-dtd", "http://apache.org/xml/features/disallow-doctype-decl"};
   private static final Boolean[] FEATURE_DEFAULTS;
   private static final String[] RECOGNIZED_PROPERTIES;
   private static final Object[] PROPERTY_DEFAULTS;
   protected XMLDTDScanner fDTDScanner;
   protected ValidationManager fValidationManager;
   protected boolean fScanningDTD;
   protected String fDoctypeName;
   protected String fDoctypePublicId;
   protected String fDoctypeSystemId;
   protected NamespaceContext fNamespaceContext = new NamespaceSupport();
   protected boolean fLoadExternalDTD = true;
   protected boolean fDisallowDoctype = false;
   protected boolean fSeenDoctypeDecl;
   protected final XMLDocumentFragmentScannerImpl.Dispatcher fXMLDeclDispatcher = new XMLDeclDispatcher();
   protected final XMLDocumentFragmentScannerImpl.Dispatcher fPrologDispatcher = new PrologDispatcher();
   protected final XMLDocumentFragmentScannerImpl.Dispatcher fDTDDispatcher = new DTDDispatcher();
   protected final XMLDocumentFragmentScannerImpl.Dispatcher fTrailingMiscDispatcher = new TrailingMiscDispatcher();
   private final String[] fStrings = new String[3];
   private final XMLString fString = new XMLString();
   private final XMLStringBuffer fStringBuffer = new XMLStringBuffer();
   private XMLInputSource fExternalSubsetSource = null;
   private final XMLDTDDescription fDTDDescription = new XMLDTDDescription((String)null, (String)null, (String)null, (String)null, (String)null);

   public void setInputSource(XMLInputSource var1) throws IOException {
      this.fEntityManager.setEntityHandler(this);
      this.fEntityManager.startDocumentEntity(var1);
   }

   public void reset(XMLComponentManager var1) throws XMLConfigurationException {
      super.reset(var1);
      this.fDoctypeName = null;
      this.fDoctypePublicId = null;
      this.fDoctypeSystemId = null;
      this.fSeenDoctypeDecl = false;
      this.fScanningDTD = false;
      this.fExternalSubsetSource = null;
      if (!this.fParserSettings) {
         this.fNamespaceContext.reset();
         this.setScannerState(0);
         this.setDispatcher(this.fXMLDeclDispatcher);
      } else {
         try {
            this.fLoadExternalDTD = var1.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd");
         } catch (XMLConfigurationException var6) {
            this.fLoadExternalDTD = true;
         }

         try {
            this.fDisallowDoctype = var1.getFeature("http://apache.org/xml/features/disallow-doctype-decl");
         } catch (XMLConfigurationException var5) {
            this.fDisallowDoctype = false;
         }

         this.fDTDScanner = (XMLDTDScanner)var1.getProperty("http://apache.org/xml/properties/internal/dtd-scanner");

         try {
            this.fValidationManager = (ValidationManager)var1.getProperty("http://apache.org/xml/properties/internal/validation-manager");
         } catch (XMLConfigurationException var4) {
            this.fValidationManager = null;
         }

         try {
            this.fNamespaceContext = (NamespaceContext)var1.getProperty("http://apache.org/xml/properties/internal/namespace-context");
         } catch (XMLConfigurationException var3) {
         }

         if (this.fNamespaceContext == null) {
            this.fNamespaceContext = new NamespaceSupport();
         }

         this.fNamespaceContext.reset();
         this.setScannerState(0);
         this.setDispatcher(this.fXMLDeclDispatcher);
      }
   }

   public String[] getRecognizedFeatures() {
      String[] var1 = super.getRecognizedFeatures();
      int var2 = var1 != null ? var1.length : 0;
      String[] var3 = new String[var2 + RECOGNIZED_FEATURES.length];
      if (var1 != null) {
         System.arraycopy(var1, 0, var3, 0, var1.length);
      }

      System.arraycopy(RECOGNIZED_FEATURES, 0, var3, var2, RECOGNIZED_FEATURES.length);
      return var3;
   }

   public void setFeature(String var1, boolean var2) throws XMLConfigurationException {
      super.setFeature(var1, var2);
      if (var1.startsWith("http://apache.org/xml/features/")) {
         int var3 = var1.length() - "http://apache.org/xml/features/".length();
         if (var3 == "nonvalidating/load-external-dtd".length() && var1.endsWith("nonvalidating/load-external-dtd")) {
            this.fLoadExternalDTD = var2;
            return;
         }

         if (var3 == "disallow-doctype-decl".length() && var1.endsWith("disallow-doctype-decl")) {
            this.fDisallowDoctype = var2;
            return;
         }
      }

   }

   public String[] getRecognizedProperties() {
      String[] var1 = super.getRecognizedProperties();
      int var2 = var1 != null ? var1.length : 0;
      String[] var3 = new String[var2 + RECOGNIZED_PROPERTIES.length];
      if (var1 != null) {
         System.arraycopy(var1, 0, var3, 0, var1.length);
      }

      System.arraycopy(RECOGNIZED_PROPERTIES, 0, var3, var2, RECOGNIZED_PROPERTIES.length);
      return var3;
   }

   public void setProperty(String var1, Object var2) throws XMLConfigurationException {
      super.setProperty(var1, var2);
      if (var1.startsWith("http://apache.org/xml/properties/")) {
         int var3 = var1.length() - "http://apache.org/xml/properties/".length();
         if (var3 == "internal/dtd-scanner".length() && var1.endsWith("internal/dtd-scanner")) {
            this.fDTDScanner = (XMLDTDScanner)var2;
         }

         if (var3 == "internal/namespace-context".length() && var1.endsWith("internal/namespace-context") && var2 != null) {
            this.fNamespaceContext = (NamespaceContext)var2;
         }

      }
   }

   public Boolean getFeatureDefault(String var1) {
      for(int var2 = 0; var2 < RECOGNIZED_FEATURES.length; ++var2) {
         if (RECOGNIZED_FEATURES[var2].equals(var1)) {
            return FEATURE_DEFAULTS[var2];
         }
      }

      return super.getFeatureDefault(var1);
   }

   public Object getPropertyDefault(String var1) {
      for(int var2 = 0; var2 < RECOGNIZED_PROPERTIES.length; ++var2) {
         if (RECOGNIZED_PROPERTIES[var2].equals(var1)) {
            return PROPERTY_DEFAULTS[var2];
         }
      }

      return super.getPropertyDefault(var1);
   }

   public void startEntity(String var1, XMLResourceIdentifier var2, String var3, Augmentations var4) throws XNIException {
      super.startEntity(var1, var2, var3, var4);
      if (!var1.equals("[xml]") && this.fEntityScanner.isExternal()) {
         this.setScannerState(16);
      }

      if (this.fDocumentHandler != null && var1.equals("[xml]")) {
         this.fDocumentHandler.startDocument(this.fEntityScanner, var3, this.fNamespaceContext, (Augmentations)null);
      }

   }

   public void endEntity(String var1, Augmentations var2) throws XNIException {
      super.endEntity(var1, var2);
      if (this.fDocumentHandler != null && var1.equals("[xml]")) {
         this.fDocumentHandler.endDocument((Augmentations)null);
      }

   }

   protected XMLDocumentFragmentScannerImpl.Dispatcher createContentDispatcher() {
      return new ContentDispatcher();
   }

   protected boolean scanDoctypeDecl() throws IOException, XNIException {
      if (!this.fEntityScanner.skipSpaces()) {
         this.reportFatalError("MSG_SPACE_REQUIRED_BEFORE_ROOT_ELEMENT_TYPE_IN_DOCTYPEDECL", (Object[])null);
      }

      this.fDoctypeName = this.fEntityScanner.scanName();
      if (this.fDoctypeName == null) {
         this.reportFatalError("MSG_ROOT_ELEMENT_TYPE_REQUIRED", (Object[])null);
      }

      if (this.fEntityScanner.skipSpaces()) {
         this.scanExternalID(this.fStrings, false);
         this.fDoctypeSystemId = this.fStrings[0];
         this.fDoctypePublicId = this.fStrings[1];
         this.fEntityScanner.skipSpaces();
      }

      this.fHasExternalDTD = this.fDoctypeSystemId != null;
      if (!this.fHasExternalDTD && this.fExternalSubsetResolver != null) {
         this.fDTDDescription.setValues((String)null, (String)null, this.fEntityManager.getCurrentResourceIdentifier().getExpandedSystemId(), (String)null);
         this.fDTDDescription.setRootName(this.fDoctypeName);
         this.fExternalSubsetSource = this.fExternalSubsetResolver.getExternalSubset(this.fDTDDescription);
         this.fHasExternalDTD = this.fExternalSubsetSource != null;
      }

      if (this.fDocumentHandler != null) {
         if (this.fExternalSubsetSource == null) {
            this.fDocumentHandler.doctypeDecl(this.fDoctypeName, this.fDoctypePublicId, this.fDoctypeSystemId, (Augmentations)null);
         } else {
            this.fDocumentHandler.doctypeDecl(this.fDoctypeName, this.fExternalSubsetSource.getPublicId(), this.fExternalSubsetSource.getSystemId(), (Augmentations)null);
         }
      }

      boolean var1 = true;
      if (!this.fEntityScanner.skipChar(91)) {
         var1 = false;
         this.fEntityScanner.skipSpaces();
         if (!this.fEntityScanner.skipChar(62)) {
            this.reportFatalError("DoctypedeclUnterminated", new Object[]{this.fDoctypeName});
         }

         --this.fMarkupDepth;
      }

      return var1;
   }

   protected String getScannerStateName(int var1) {
      switch (var1) {
         case 0:
            return "SCANNER_STATE_XML_DECL";
         case 1:
         case 2:
         case 3:
         case 4:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 13:
         case 14:
         case 15:
         case 16:
         default:
            return super.getScannerStateName(var1);
         case 5:
            return "SCANNER_STATE_PROLOG";
         case 12:
            return "SCANNER_STATE_TRAILING_MISC";
         case 17:
            return "SCANNER_STATE_DTD_INTERNAL_DECLS";
         case 18:
            return "SCANNER_STATE_DTD_EXTERNAL";
         case 19:
            return "SCANNER_STATE_DTD_EXTERNAL_DECLS";
      }
   }

   static {
      FEATURE_DEFAULTS = new Boolean[]{Boolean.TRUE, Boolean.FALSE};
      RECOGNIZED_PROPERTIES = new String[]{"http://apache.org/xml/properties/internal/dtd-scanner", "http://apache.org/xml/properties/internal/validation-manager", "http://apache.org/xml/properties/internal/namespace-context"};
      PROPERTY_DEFAULTS = new Object[]{null, null, null};
   }

   protected final class TrailingMiscDispatcher implements XMLDocumentFragmentScannerImpl.Dispatcher {
      public boolean dispatch(boolean var1) throws IOException, XNIException {
         try {
            boolean var2;
            do {
               var2 = false;
               switch (XMLDocumentScannerImpl.this.fScannerState) {
                  case 1:
                     ++XMLDocumentScannerImpl.this.fMarkupDepth;
                     if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(63)) {
                        XMLDocumentScannerImpl.this.setScannerState(3);
                        var2 = true;
                     } else if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(33)) {
                        XMLDocumentScannerImpl.this.setScannerState(2);
                        var2 = true;
                     } else if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(47)) {
                        XMLDocumentScannerImpl.this.reportFatalError("MarkupNotRecognizedInMisc", (Object[])null);
                        var2 = true;
                     } else if (XMLDocumentScannerImpl.this.isValidNameStartChar(XMLDocumentScannerImpl.this.fEntityScanner.peekChar())) {
                        XMLDocumentScannerImpl.this.reportFatalError("MarkupNotRecognizedInMisc", (Object[])null);
                        XMLDocumentScannerImpl.this.scanStartElement();
                        XMLDocumentScannerImpl.this.setScannerState(7);
                     } else if (XMLDocumentScannerImpl.this.isValidNameStartHighSurrogate(XMLDocumentScannerImpl.this.fEntityScanner.peekChar())) {
                        XMLDocumentScannerImpl.this.reportFatalError("MarkupNotRecognizedInMisc", (Object[])null);
                        XMLDocumentScannerImpl.this.scanStartElement();
                        XMLDocumentScannerImpl.this.setScannerState(7);
                     } else {
                        XMLDocumentScannerImpl.this.reportFatalError("MarkupNotRecognizedInMisc", (Object[])null);
                     }
                     break;
                  case 2:
                     if (!XMLDocumentScannerImpl.this.fEntityScanner.skipString("--")) {
                        XMLDocumentScannerImpl.this.reportFatalError("InvalidCommentStart", (Object[])null);
                     }

                     XMLDocumentScannerImpl.this.scanComment();
                     XMLDocumentScannerImpl.this.setScannerState(12);
                     break;
                  case 3:
                     XMLDocumentScannerImpl.this.scanPI();
                     XMLDocumentScannerImpl.this.setScannerState(12);
                  case 4:
                  case 5:
                  case 6:
                  case 9:
                  case 10:
                  case 11:
                  case 13:
                  default:
                     break;
                  case 7:
                     int var3 = XMLDocumentScannerImpl.this.fEntityScanner.peekChar();
                     if (var3 == -1) {
                        XMLDocumentScannerImpl.this.setScannerState(14);
                        return false;
                     }

                     XMLDocumentScannerImpl.this.reportFatalError("ContentIllegalInTrailingMisc", (Object[])null);
                     XMLDocumentScannerImpl.this.fEntityScanner.scanChar();
                     XMLDocumentScannerImpl.this.setScannerState(12);
                     break;
                  case 8:
                     XMLDocumentScannerImpl.this.reportFatalError("ReferenceIllegalInTrailingMisc", (Object[])null);
                     XMLDocumentScannerImpl.this.setScannerState(12);
                     break;
                  case 12:
                     XMLDocumentScannerImpl.this.fEntityScanner.skipSpaces();
                     if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(60)) {
                        XMLDocumentScannerImpl.this.setScannerState(1);
                        var2 = true;
                     } else {
                        XMLDocumentScannerImpl.this.setScannerState(7);
                        var2 = true;
                     }
                     break;
                  case 14:
                     return false;
               }
            } while(var1 || var2);

            return true;
         } catch (MalformedByteSequenceException var5) {
            XMLDocumentScannerImpl.this.fErrorReporter.reportError(var5.getDomain(), var5.getKey(), var5.getArguments(), (short)2, var5);
            return false;
         } catch (CharConversionException var6) {
            XMLDocumentScannerImpl.this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "CharConversionFailure", (Object[])null, (short)2, var6);
            return false;
         } catch (EOFException var7) {
            if (XMLDocumentScannerImpl.this.fMarkupDepth != 0) {
               XMLDocumentScannerImpl.this.reportFatalError("PrematureEOF", (Object[])null);
               return false;
            } else {
               XMLDocumentScannerImpl.this.setScannerState(14);
               return false;
            }
         }
      }
   }

   protected class ContentDispatcher extends XMLDocumentFragmentScannerImpl.FragmentContentDispatcher {
      protected ContentDispatcher() {
         super();
      }

      protected boolean scanForDoctypeHook() throws IOException, XNIException {
         if (XMLDocumentScannerImpl.this.fEntityScanner.skipString("DOCTYPE")) {
            XMLDocumentScannerImpl.this.setScannerState(4);
            return true;
         } else {
            return false;
         }
      }

      protected boolean elementDepthIsZeroHook() throws IOException, XNIException {
         XMLDocumentScannerImpl.this.setScannerState(12);
         XMLDocumentScannerImpl.this.setDispatcher(XMLDocumentScannerImpl.this.fTrailingMiscDispatcher);
         return true;
      }

      protected boolean scanRootElementHook() throws IOException, XNIException {
         if (XMLDocumentScannerImpl.this.fExternalSubsetResolver != null && !XMLDocumentScannerImpl.this.fSeenDoctypeDecl && !XMLDocumentScannerImpl.this.fDisallowDoctype && (XMLDocumentScannerImpl.this.fValidation || XMLDocumentScannerImpl.this.fLoadExternalDTD)) {
            XMLDocumentScannerImpl.this.scanStartElementName();
            this.resolveExternalSubsetAndRead();
            if (XMLDocumentScannerImpl.this.scanStartElementAfterName()) {
               XMLDocumentScannerImpl.this.setScannerState(12);
               XMLDocumentScannerImpl.this.setDispatcher(XMLDocumentScannerImpl.this.fTrailingMiscDispatcher);
               return true;
            }
         } else if (XMLDocumentScannerImpl.this.scanStartElement()) {
            XMLDocumentScannerImpl.this.setScannerState(12);
            XMLDocumentScannerImpl.this.setDispatcher(XMLDocumentScannerImpl.this.fTrailingMiscDispatcher);
            return true;
         }

         return false;
      }

      protected void endOfFileHook(EOFException var1) throws IOException, XNIException {
         XMLDocumentScannerImpl.this.reportFatalError("PrematureEOF", (Object[])null);
      }

      protected void resolveExternalSubsetAndRead() throws IOException, XNIException {
         XMLDocumentScannerImpl.this.fDTDDescription.setValues((String)null, (String)null, XMLDocumentScannerImpl.this.fEntityManager.getCurrentResourceIdentifier().getExpandedSystemId(), (String)null);
         XMLDocumentScannerImpl.this.fDTDDescription.setRootName(XMLDocumentScannerImpl.this.fElementQName.rawname);
         XMLInputSource var1 = XMLDocumentScannerImpl.this.fExternalSubsetResolver.getExternalSubset(XMLDocumentScannerImpl.this.fDTDDescription);
         if (var1 != null) {
            XMLDocumentScannerImpl.this.fDoctypeName = XMLDocumentScannerImpl.this.fElementQName.rawname;
            XMLDocumentScannerImpl.this.fDoctypePublicId = var1.getPublicId();
            XMLDocumentScannerImpl.this.fDoctypeSystemId = var1.getSystemId();
            if (XMLDocumentScannerImpl.this.fDocumentHandler != null) {
               XMLDocumentScannerImpl.this.fDocumentHandler.doctypeDecl(XMLDocumentScannerImpl.this.fDoctypeName, XMLDocumentScannerImpl.this.fDoctypePublicId, XMLDocumentScannerImpl.this.fDoctypeSystemId, (Augmentations)null);
            }

            try {
               if (XMLDocumentScannerImpl.this.fValidationManager != null && XMLDocumentScannerImpl.this.fValidationManager.isCachedDTD()) {
                  XMLDocumentScannerImpl.this.fDTDScanner.setInputSource((XMLInputSource)null);
               } else {
                  XMLDocumentScannerImpl.this.fDTDScanner.setInputSource(var1);

                  while(XMLDocumentScannerImpl.this.fDTDScanner.scanDTDExternalSubset(true)) {
                  }
               }
            } finally {
               XMLDocumentScannerImpl.this.fEntityManager.setEntityHandler(XMLDocumentScannerImpl.this);
            }
         }

      }
   }

   protected final class DTDDispatcher implements XMLDocumentFragmentScannerImpl.Dispatcher {
      public boolean dispatch(boolean var1) throws IOException, XNIException {
         XMLDocumentScannerImpl.this.fEntityManager.setEntityHandler((XMLEntityHandler)null);

         boolean var4;
         try {
            boolean var3;
            boolean var5;
            try {
               label247:
               while(true) {
                  boolean var2 = false;
                  switch (XMLDocumentScannerImpl.this.fScannerState) {
                     case 17:
                        var3 = true;
                        var4 = (XMLDocumentScannerImpl.this.fValidation || XMLDocumentScannerImpl.this.fLoadExternalDTD) && (XMLDocumentScannerImpl.this.fValidationManager == null || !XMLDocumentScannerImpl.this.fValidationManager.isCachedDTD());
                        var5 = XMLDocumentScannerImpl.this.fDTDScanner.scanDTDInternalSubset(var3, XMLDocumentScannerImpl.this.fStandalone, XMLDocumentScannerImpl.this.fHasExternalDTD && var4);
                        if (!var5) {
                           if (!XMLDocumentScannerImpl.this.fEntityScanner.skipChar(93)) {
                              XMLDocumentScannerImpl.this.reportFatalError("EXPECTED_SQUARE_BRACKET_TO_CLOSE_INTERNAL_SUBSET", (Object[])null);
                           }

                           XMLDocumentScannerImpl.this.fEntityScanner.skipSpaces();
                           if (!XMLDocumentScannerImpl.this.fEntityScanner.skipChar(62)) {
                              XMLDocumentScannerImpl.this.reportFatalError("DoctypedeclUnterminated", new Object[]{XMLDocumentScannerImpl.this.fDoctypeName});
                           }

                           --XMLDocumentScannerImpl.this.fMarkupDepth;
                           if (XMLDocumentScannerImpl.this.fDoctypeSystemId != null) {
                              XMLDocumentScannerImpl.this.fIsEntityDeclaredVC = !XMLDocumentScannerImpl.this.fStandalone;
                              if (!var4) {
                                 break label247;
                              }

                              XMLDocumentScannerImpl.this.setScannerState(18);
                           } else {
                              if (XMLDocumentScannerImpl.this.fExternalSubsetSource == null) {
                                 XMLDocumentScannerImpl.this.fIsEntityDeclaredVC = XMLDocumentScannerImpl.this.fEntityManager.hasPEReferences() && !XMLDocumentScannerImpl.this.fStandalone;
                                 break label247;
                              }

                              XMLDocumentScannerImpl.this.fIsEntityDeclaredVC = !XMLDocumentScannerImpl.this.fStandalone;
                              if (!var4) {
                                 break label247;
                              }

                              XMLDocumentScannerImpl.this.fDTDScanner.setInputSource(XMLDocumentScannerImpl.this.fExternalSubsetSource);
                              XMLDocumentScannerImpl.this.fExternalSubsetSource = null;
                              XMLDocumentScannerImpl.this.setScannerState(19);
                           }
                        }
                        break;
                     case 18:
                        XMLDocumentScannerImpl.this.fDTDDescription.setValues(XMLDocumentScannerImpl.this.fDoctypePublicId, XMLDocumentScannerImpl.this.fDoctypeSystemId, (String)null, (String)null);
                        XMLDocumentScannerImpl.this.fDTDDescription.setRootName(XMLDocumentScannerImpl.this.fDoctypeName);
                        XMLInputSource var17 = XMLDocumentScannerImpl.this.fEntityManager.resolveEntity(XMLDocumentScannerImpl.this.fDTDDescription);
                        XMLDocumentScannerImpl.this.fDTDScanner.setInputSource(var17);
                        XMLDocumentScannerImpl.this.setScannerState(19);
                        var2 = true;
                        break;
                     case 19:
                        var3 = true;
                        var4 = XMLDocumentScannerImpl.this.fDTDScanner.scanDTDExternalSubset(var3);
                        if (!var4) {
                           XMLDocumentScannerImpl.this.setScannerState(5);
                           XMLDocumentScannerImpl.this.setDispatcher(XMLDocumentScannerImpl.this.fPrologDispatcher);
                           XMLDocumentScannerImpl.this.fEntityManager.setEntityHandler(XMLDocumentScannerImpl.this);
                           var5 = true;
                           return var5;
                        }
                        break;
                     default:
                        throw new XNIException("DTDDispatcher#dispatch: scanner state=" + XMLDocumentScannerImpl.this.fScannerState + " (" + XMLDocumentScannerImpl.this.getScannerStateName(XMLDocumentScannerImpl.this.fScannerState) + ')');
                  }

                  if (!var1 && !var2) {
                     return true;
                  }
               }

               XMLDocumentScannerImpl.this.setScannerState(5);
               XMLDocumentScannerImpl.this.setDispatcher(XMLDocumentScannerImpl.this.fPrologDispatcher);
               XMLDocumentScannerImpl.this.fEntityManager.setEntityHandler(XMLDocumentScannerImpl.this);
               boolean var6 = true;
               return var6;
            } catch (MalformedByteSequenceException var13) {
               XMLDocumentScannerImpl.this.fErrorReporter.reportError(var13.getDomain(), var13.getKey(), var13.getArguments(), (short)2, var13);
               var3 = false;
               return var3;
            } catch (CharConversionException var14) {
               XMLDocumentScannerImpl.this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "CharConversionFailure", (Object[])null, (short)2, var14);
               var4 = false;
            } catch (EOFException var15) {
               XMLDocumentScannerImpl.this.reportFatalError("PrematureEOF", (Object[])null);
               var5 = false;
               return var5;
            }
         } finally {
            XMLDocumentScannerImpl.this.fEntityManager.setEntityHandler(XMLDocumentScannerImpl.this);
         }

         return var4;
      }
   }

   protected final class PrologDispatcher implements XMLDocumentFragmentScannerImpl.Dispatcher {
      public boolean dispatch(boolean var1) throws IOException, XNIException {
         try {
            boolean var2;
            do {
               var2 = false;
               switch (XMLDocumentScannerImpl.this.fScannerState) {
                  case 1:
                     ++XMLDocumentScannerImpl.this.fMarkupDepth;
                     if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(33)) {
                        if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(45)) {
                           if (!XMLDocumentScannerImpl.this.fEntityScanner.skipChar(45)) {
                              XMLDocumentScannerImpl.this.reportFatalError("InvalidCommentStart", (Object[])null);
                           }

                           XMLDocumentScannerImpl.this.setScannerState(2);
                           var2 = true;
                        } else if (XMLDocumentScannerImpl.this.fEntityScanner.skipString("DOCTYPE")) {
                           XMLDocumentScannerImpl.this.setScannerState(4);
                           var2 = true;
                        } else {
                           XMLDocumentScannerImpl.this.reportFatalError("MarkupNotRecognizedInProlog", (Object[])null);
                        }
                     } else {
                        if (XMLDocumentScannerImpl.this.isValidNameStartChar(XMLDocumentScannerImpl.this.fEntityScanner.peekChar())) {
                           XMLDocumentScannerImpl.this.setScannerState(6);
                           XMLDocumentScannerImpl.this.setDispatcher(XMLDocumentScannerImpl.this.fContentDispatcher);
                           return true;
                        }

                        if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(63)) {
                           XMLDocumentScannerImpl.this.setScannerState(3);
                           var2 = true;
                        } else {
                           if (XMLDocumentScannerImpl.this.isValidNameStartHighSurrogate(XMLDocumentScannerImpl.this.fEntityScanner.peekChar())) {
                              XMLDocumentScannerImpl.this.setScannerState(6);
                              XMLDocumentScannerImpl.this.setDispatcher(XMLDocumentScannerImpl.this.fContentDispatcher);
                              return true;
                           }

                           XMLDocumentScannerImpl.this.reportFatalError("MarkupNotRecognizedInProlog", (Object[])null);
                        }
                     }
                     break;
                  case 2:
                     XMLDocumentScannerImpl.this.scanComment();
                     XMLDocumentScannerImpl.this.setScannerState(5);
                     break;
                  case 3:
                     XMLDocumentScannerImpl.this.scanPI();
                     XMLDocumentScannerImpl.this.setScannerState(5);
                     break;
                  case 4:
                     if (XMLDocumentScannerImpl.this.fDisallowDoctype) {
                        XMLDocumentScannerImpl.this.reportFatalError("DoctypeNotAllowed", (Object[])null);
                     }

                     if (XMLDocumentScannerImpl.this.fSeenDoctypeDecl) {
                        XMLDocumentScannerImpl.this.reportFatalError("AlreadySeenDoctype", (Object[])null);
                     }

                     XMLDocumentScannerImpl.this.fSeenDoctypeDecl = true;
                     if (XMLDocumentScannerImpl.this.scanDoctypeDecl()) {
                        XMLDocumentScannerImpl.this.setScannerState(17);
                        XMLDocumentScannerImpl.this.setDispatcher(XMLDocumentScannerImpl.this.fDTDDispatcher);
                        return true;
                     }

                     if (XMLDocumentScannerImpl.this.fDoctypeSystemId != null) {
                        XMLDocumentScannerImpl.this.fIsEntityDeclaredVC = !XMLDocumentScannerImpl.this.fStandalone;
                        if ((XMLDocumentScannerImpl.this.fValidation || XMLDocumentScannerImpl.this.fLoadExternalDTD) && (XMLDocumentScannerImpl.this.fValidationManager == null || !XMLDocumentScannerImpl.this.fValidationManager.isCachedDTD())) {
                           XMLDocumentScannerImpl.this.setScannerState(18);
                           XMLDocumentScannerImpl.this.setDispatcher(XMLDocumentScannerImpl.this.fDTDDispatcher);
                           return true;
                        }
                     } else if (XMLDocumentScannerImpl.this.fExternalSubsetSource != null) {
                        XMLDocumentScannerImpl.this.fIsEntityDeclaredVC = !XMLDocumentScannerImpl.this.fStandalone;
                        if ((XMLDocumentScannerImpl.this.fValidation || XMLDocumentScannerImpl.this.fLoadExternalDTD) && (XMLDocumentScannerImpl.this.fValidationManager == null || !XMLDocumentScannerImpl.this.fValidationManager.isCachedDTD())) {
                           XMLDocumentScannerImpl.this.fDTDScanner.setInputSource(XMLDocumentScannerImpl.this.fExternalSubsetSource);
                           XMLDocumentScannerImpl.this.fExternalSubsetSource = null;
                           XMLDocumentScannerImpl.this.setScannerState(19);
                           XMLDocumentScannerImpl.this.setDispatcher(XMLDocumentScannerImpl.this.fDTDDispatcher);
                           return true;
                        }
                     }

                     XMLDocumentScannerImpl.this.fDTDScanner.setInputSource((XMLInputSource)null);
                     XMLDocumentScannerImpl.this.setScannerState(5);
                     break;
                  case 5:
                     XMLDocumentScannerImpl.this.fEntityScanner.skipSpaces();
                     if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(60)) {
                        XMLDocumentScannerImpl.this.setScannerState(1);
                        var2 = true;
                     } else if (XMLDocumentScannerImpl.this.fEntityScanner.skipChar(38)) {
                        XMLDocumentScannerImpl.this.setScannerState(8);
                        var2 = true;
                     } else {
                        XMLDocumentScannerImpl.this.setScannerState(7);
                        var2 = true;
                     }
                  case 6:
                  default:
                     break;
                  case 7:
                     XMLDocumentScannerImpl.this.reportFatalError("ContentIllegalInProlog", (Object[])null);
                     XMLDocumentScannerImpl.this.fEntityScanner.scanChar();
                  case 8:
                     XMLDocumentScannerImpl.this.reportFatalError("ReferenceIllegalInProlog", (Object[])null);
               }
            } while(var1 || var2);

            if (var1) {
               if (XMLDocumentScannerImpl.this.fEntityScanner.scanChar() != 60) {
                  XMLDocumentScannerImpl.this.reportFatalError("RootElementRequired", (Object[])null);
               }

               XMLDocumentScannerImpl.this.setScannerState(6);
               XMLDocumentScannerImpl.this.setDispatcher(XMLDocumentScannerImpl.this.fContentDispatcher);
            }

            return true;
         } catch (MalformedByteSequenceException var5) {
            XMLDocumentScannerImpl.this.fErrorReporter.reportError(var5.getDomain(), var5.getKey(), var5.getArguments(), (short)2, var5);
            return false;
         } catch (CharConversionException var6) {
            XMLDocumentScannerImpl.this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "CharConversionFailure", (Object[])null, (short)2, var6);
            return false;
         } catch (EOFException var7) {
            XMLDocumentScannerImpl.this.reportFatalError("PrematureEOF", (Object[])null);
            return false;
         }
      }
   }

   protected final class XMLDeclDispatcher implements XMLDocumentFragmentScannerImpl.Dispatcher {
      public boolean dispatch(boolean var1) throws IOException, XNIException {
         XMLDocumentScannerImpl.this.setScannerState(5);
         XMLDocumentScannerImpl.this.setDispatcher(XMLDocumentScannerImpl.this.fPrologDispatcher);

         try {
            if (XMLDocumentScannerImpl.this.fEntityScanner.skipString("<?xml")) {
               ++XMLDocumentScannerImpl.this.fMarkupDepth;
               if (XMLChar.isName(XMLDocumentScannerImpl.this.fEntityScanner.peekChar())) {
                  XMLDocumentScannerImpl.this.fStringBuffer.clear();
                  XMLDocumentScannerImpl.this.fStringBuffer.append("xml");
                  if (XMLDocumentScannerImpl.this.fNamespaces) {
                     while(XMLChar.isNCName(XMLDocumentScannerImpl.this.fEntityScanner.peekChar())) {
                        XMLDocumentScannerImpl.this.fStringBuffer.append((char)XMLDocumentScannerImpl.this.fEntityScanner.scanChar());
                     }
                  } else {
                     while(XMLChar.isName(XMLDocumentScannerImpl.this.fEntityScanner.peekChar())) {
                        XMLDocumentScannerImpl.this.fStringBuffer.append((char)XMLDocumentScannerImpl.this.fEntityScanner.scanChar());
                     }
                  }

                  String var2 = XMLDocumentScannerImpl.this.fSymbolTable.addSymbol(XMLDocumentScannerImpl.this.fStringBuffer.ch, XMLDocumentScannerImpl.this.fStringBuffer.offset, XMLDocumentScannerImpl.this.fStringBuffer.length);
                  XMLDocumentScannerImpl.this.scanPIData(var2, XMLDocumentScannerImpl.this.fString);
               } else {
                  XMLDocumentScannerImpl.this.scanXMLDeclOrTextDecl(false);
               }
            }

            XMLDocumentScannerImpl.this.fEntityManager.fCurrentEntity.mayReadChunks = true;
            return true;
         } catch (MalformedByteSequenceException var5) {
            XMLDocumentScannerImpl.this.fErrorReporter.reportError(var5.getDomain(), var5.getKey(), var5.getArguments(), (short)2, var5);
            return false;
         } catch (CharConversionException var6) {
            XMLDocumentScannerImpl.this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "CharConversionFailure", (Object[])null, (short)2, var6);
            return false;
         } catch (EOFException var7) {
            XMLDocumentScannerImpl.this.reportFatalError("PrematureEOF", (Object[])null);
            return false;
         }
      }
   }
}
