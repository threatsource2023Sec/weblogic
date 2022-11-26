package org.python.apache.xerces.jaxp.validation;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.TypeInfoProvider;
import javax.xml.validation.ValidatorHandler;
import org.python.apache.xerces.impl.XMLEntityManager;
import org.python.apache.xerces.impl.XMLErrorReporter;
import org.python.apache.xerces.impl.dv.XSSimpleType;
import org.python.apache.xerces.impl.validation.EntityState;
import org.python.apache.xerces.impl.validation.ValidationManager;
import org.python.apache.xerces.impl.xs.XMLSchemaValidator;
import org.python.apache.xerces.parsers.SAXParser;
import org.python.apache.xerces.util.AttributesProxy;
import org.python.apache.xerces.util.SAXLocatorWrapper;
import org.python.apache.xerces.util.SAXMessageFormatter;
import org.python.apache.xerces.util.SymbolTable;
import org.python.apache.xerces.util.URI;
import org.python.apache.xerces.util.XMLAttributesImpl;
import org.python.apache.xerces.util.XMLSymbols;
import org.python.apache.xerces.xni.Augmentations;
import org.python.apache.xerces.xni.NamespaceContext;
import org.python.apache.xerces.xni.QName;
import org.python.apache.xerces.xni.XMLAttributes;
import org.python.apache.xerces.xni.XMLDocumentHandler;
import org.python.apache.xerces.xni.XMLLocator;
import org.python.apache.xerces.xni.XMLResourceIdentifier;
import org.python.apache.xerces.xni.XMLString;
import org.python.apache.xerces.xni.XNIException;
import org.python.apache.xerces.xni.parser.XMLConfigurationException;
import org.python.apache.xerces.xni.parser.XMLDocumentSource;
import org.python.apache.xerces.xni.parser.XMLParseException;
import org.python.apache.xerces.xs.AttributePSVI;
import org.python.apache.xerces.xs.ElementPSVI;
import org.python.apache.xerces.xs.ItemPSVI;
import org.python.apache.xerces.xs.PSVIProvider;
import org.python.apache.xerces.xs.XSSimpleTypeDefinition;
import org.python.apache.xerces.xs.XSTypeDefinition;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.Attributes2;
import org.xml.sax.ext.EntityResolver2;
import org.xml.sax.ext.LexicalHandler;

final class ValidatorHandlerImpl extends ValidatorHandler implements DTDHandler, EntityState, PSVIProvider, ValidatorHelper, XMLDocumentHandler {
   private static final String NAMESPACE_PREFIXES = "http://xml.org/sax/features/namespace-prefixes";
   private static final String STRING_INTERNING = "http://xml.org/sax/features/string-interning";
   private static final String STRINGS_INTERNED = "http://apache.org/xml/features/internal/strings-interned";
   private static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
   private static final String LEXICAL_HANDLER = "http://xml.org/sax/properties/lexical-handler";
   private static final String NAMESPACE_CONTEXT = "http://apache.org/xml/properties/internal/namespace-context";
   private static final String SCHEMA_VALIDATOR = "http://apache.org/xml/properties/internal/validator/schema";
   private static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
   private static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
   private static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
   private final XMLErrorReporter fErrorReporter;
   private final NamespaceContext fNamespaceContext;
   private final XMLSchemaValidator fSchemaValidator;
   private final SymbolTable fSymbolTable;
   private final ValidationManager fValidationManager;
   private final XMLSchemaValidatorComponentManager fComponentManager;
   private final SAXLocatorWrapper fSAXLocatorWrapper;
   private boolean fNeedPushNSContext;
   private HashMap fUnparsedEntities;
   private boolean fStringsInternalized;
   private final QName fElementQName;
   private final QName fAttributeQName;
   private final XMLAttributesImpl fAttributes;
   private final AttributesProxy fAttrAdapter;
   private final XMLString fTempString;
   private ContentHandler fContentHandler;
   private final XMLSchemaTypeInfoProvider fTypeInfoProvider;
   private final ResolutionForwarder fResolutionForwarder;

   public ValidatorHandlerImpl(XSGrammarPoolContainer var1) {
      this(new XMLSchemaValidatorComponentManager(var1));
      this.fComponentManager.addRecognizedFeatures(new String[]{"http://xml.org/sax/features/namespace-prefixes"});
      this.fComponentManager.setFeature("http://xml.org/sax/features/namespace-prefixes", false);
      this.setErrorHandler((ErrorHandler)null);
      this.setResourceResolver((LSResourceResolver)null);
   }

   public ValidatorHandlerImpl(XMLSchemaValidatorComponentManager var1) {
      this.fSAXLocatorWrapper = new SAXLocatorWrapper();
      this.fNeedPushNSContext = true;
      this.fUnparsedEntities = null;
      this.fStringsInternalized = false;
      this.fElementQName = new QName();
      this.fAttributeQName = new QName();
      this.fAttributes = new XMLAttributesImpl();
      this.fAttrAdapter = new AttributesProxy(this.fAttributes);
      this.fTempString = new XMLString();
      this.fContentHandler = null;
      this.fTypeInfoProvider = new XMLSchemaTypeInfoProvider();
      this.fResolutionForwarder = new ResolutionForwarder((LSResourceResolver)null);
      this.fComponentManager = var1;
      this.fErrorReporter = (XMLErrorReporter)this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
      this.fNamespaceContext = (NamespaceContext)this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/namespace-context");
      this.fSchemaValidator = (XMLSchemaValidator)this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/validator/schema");
      this.fSymbolTable = (SymbolTable)this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table");
      this.fValidationManager = (ValidationManager)this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/validation-manager");
   }

   public void setContentHandler(ContentHandler var1) {
      this.fContentHandler = var1;
   }

   public ContentHandler getContentHandler() {
      return this.fContentHandler;
   }

   public void setErrorHandler(ErrorHandler var1) {
      this.fComponentManager.setErrorHandler(var1);
   }

   public ErrorHandler getErrorHandler() {
      return this.fComponentManager.getErrorHandler();
   }

   public void setResourceResolver(LSResourceResolver var1) {
      this.fComponentManager.setResourceResolver(var1);
   }

   public LSResourceResolver getResourceResolver() {
      return this.fComponentManager.getResourceResolver();
   }

   public TypeInfoProvider getTypeInfoProvider() {
      return this.fTypeInfoProvider;
   }

   public boolean getFeature(String var1) throws SAXNotRecognizedException, SAXNotSupportedException {
      if (var1 == null) {
         throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "FeatureNameNull", (Object[])null));
      } else if ("http://apache.org/xml/features/internal/strings-interned".equals(var1)) {
         return this.fStringsInternalized;
      } else {
         try {
            return this.fComponentManager.getFeature(var1);
         } catch (XMLConfigurationException var4) {
            String var3 = var4.getIdentifier();
            if (var4.getType() == 0) {
               throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "feature-not-recognized", new Object[]{var3}));
            } else {
               throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "feature-not-supported", new Object[]{var3}));
            }
         }
      }
   }

   public void setFeature(String var1, boolean var2) throws SAXNotRecognizedException, SAXNotSupportedException {
      if (var1 == null) {
         throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "FeatureNameNull", (Object[])null));
      } else if ("http://apache.org/xml/features/internal/strings-interned".equals(var1)) {
         this.fStringsInternalized = var2;
      } else {
         try {
            this.fComponentManager.setFeature(var1, var2);
         } catch (XMLConfigurationException var5) {
            String var4 = var5.getIdentifier();
            if (var5.getType() == 0) {
               throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "feature-not-recognized", new Object[]{var4}));
            } else {
               throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "feature-not-supported", new Object[]{var4}));
            }
         }
      }
   }

   public Object getProperty(String var1) throws SAXNotRecognizedException, SAXNotSupportedException {
      if (var1 == null) {
         throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "ProperyNameNull", (Object[])null));
      } else {
         try {
            return this.fComponentManager.getProperty(var1);
         } catch (XMLConfigurationException var4) {
            String var3 = var4.getIdentifier();
            if (var4.getType() == 0) {
               throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "property-not-recognized", new Object[]{var3}));
            } else {
               throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "property-not-supported", new Object[]{var3}));
            }
         }
      }
   }

   public void setProperty(String var1, Object var2) throws SAXNotRecognizedException, SAXNotSupportedException {
      if (var1 == null) {
         throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "ProperyNameNull", (Object[])null));
      } else {
         try {
            this.fComponentManager.setProperty(var1, var2);
         } catch (XMLConfigurationException var5) {
            String var4 = var5.getIdentifier();
            if (var5.getType() == 0) {
               throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "property-not-recognized", new Object[]{var4}));
            } else {
               throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "property-not-supported", new Object[]{var4}));
            }
         }
      }
   }

   public boolean isEntityDeclared(String var1) {
      return false;
   }

   public boolean isEntityUnparsed(String var1) {
      return this.fUnparsedEntities != null ? this.fUnparsedEntities.containsKey(var1) : false;
   }

   public void startDocument(XMLLocator var1, String var2, NamespaceContext var3, Augmentations var4) throws XNIException {
      if (this.fContentHandler != null) {
         try {
            this.fContentHandler.startDocument();
         } catch (SAXException var6) {
            throw new XNIException(var6);
         }
      }

   }

   public void xmlDecl(String var1, String var2, String var3, Augmentations var4) throws XNIException {
   }

   public void doctypeDecl(String var1, String var2, String var3, Augmentations var4) throws XNIException {
   }

   public void comment(XMLString var1, Augmentations var2) throws XNIException {
   }

   public void processingInstruction(String var1, XMLString var2, Augmentations var3) throws XNIException {
      if (this.fContentHandler != null) {
         try {
            this.fContentHandler.processingInstruction(var1, var2.toString());
         } catch (SAXException var5) {
            throw new XNIException(var5);
         }
      }

   }

   public void startElement(QName var1, XMLAttributes var2, Augmentations var3) throws XNIException {
      if (this.fContentHandler != null) {
         try {
            this.fTypeInfoProvider.beginStartElement(var3, var2);
            this.fContentHandler.startElement(var1.uri != null ? var1.uri : XMLSymbols.EMPTY_STRING, var1.localpart, var1.rawname, this.fAttrAdapter);
         } catch (SAXException var9) {
            throw new XNIException(var9);
         } finally {
            this.fTypeInfoProvider.finishStartElement();
         }
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
      if (this.fContentHandler != null) {
         if (var1.length == 0) {
            return;
         }

         try {
            this.fContentHandler.characters(var1.ch, var1.offset, var1.length);
         } catch (SAXException var4) {
            throw new XNIException(var4);
         }
      }

   }

   public void ignorableWhitespace(XMLString var1, Augmentations var2) throws XNIException {
      if (this.fContentHandler != null) {
         try {
            this.fContentHandler.ignorableWhitespace(var1.ch, var1.offset, var1.length);
         } catch (SAXException var4) {
            throw new XNIException(var4);
         }
      }

   }

   public void endElement(QName var1, Augmentations var2) throws XNIException {
      if (this.fContentHandler != null) {
         try {
            this.fTypeInfoProvider.beginEndElement(var2);
            this.fContentHandler.endElement(var1.uri != null ? var1.uri : XMLSymbols.EMPTY_STRING, var1.localpart, var1.rawname);
         } catch (SAXException var8) {
            throw new XNIException(var8);
         } finally {
            this.fTypeInfoProvider.finishEndElement();
         }
      }

   }

   public void startCDATA(Augmentations var1) throws XNIException {
   }

   public void endCDATA(Augmentations var1) throws XNIException {
   }

   public void endDocument(Augmentations var1) throws XNIException {
      if (this.fContentHandler != null) {
         try {
            this.fContentHandler.endDocument();
         } catch (SAXException var3) {
            throw new XNIException(var3);
         }
      }

   }

   public void setDocumentSource(XMLDocumentSource var1) {
   }

   public XMLDocumentSource getDocumentSource() {
      return this.fSchemaValidator;
   }

   public void setDocumentLocator(Locator var1) {
      this.fSAXLocatorWrapper.setLocator(var1);
      if (this.fContentHandler != null) {
         this.fContentHandler.setDocumentLocator(var1);
      }

   }

   public void startDocument() throws SAXException {
      this.fComponentManager.reset();
      this.fSchemaValidator.setDocumentHandler(this);
      this.fValidationManager.setEntityState(this);
      this.fTypeInfoProvider.finishStartElement();
      this.fNeedPushNSContext = true;
      if (this.fUnparsedEntities != null && !this.fUnparsedEntities.isEmpty()) {
         this.fUnparsedEntities.clear();
      }

      this.fErrorReporter.setDocumentLocator(this.fSAXLocatorWrapper);

      try {
         this.fSchemaValidator.startDocument(this.fSAXLocatorWrapper, this.fSAXLocatorWrapper.getEncoding(), this.fNamespaceContext, (Augmentations)null);
      } catch (XMLParseException var3) {
         throw Util.toSAXParseException(var3);
      } catch (XNIException var4) {
         throw Util.toSAXException(var4);
      }
   }

   public void endDocument() throws SAXException {
      this.fSAXLocatorWrapper.setLocator((Locator)null);

      try {
         this.fSchemaValidator.endDocument((Augmentations)null);
      } catch (XMLParseException var3) {
         throw Util.toSAXParseException(var3);
      } catch (XNIException var4) {
         throw Util.toSAXException(var4);
      }
   }

   public void startPrefixMapping(String var1, String var2) throws SAXException {
      String var3;
      String var4;
      if (!this.fStringsInternalized) {
         var3 = var1 != null ? this.fSymbolTable.addSymbol(var1) : XMLSymbols.EMPTY_STRING;
         var4 = var2 != null && var2.length() > 0 ? this.fSymbolTable.addSymbol(var2) : null;
      } else {
         var3 = var1 != null ? var1 : XMLSymbols.EMPTY_STRING;
         var4 = var2 != null && var2.length() > 0 ? var2 : null;
      }

      if (this.fNeedPushNSContext) {
         this.fNeedPushNSContext = false;
         this.fNamespaceContext.pushContext();
      }

      this.fNamespaceContext.declarePrefix(var3, var4);
      if (this.fContentHandler != null) {
         this.fContentHandler.startPrefixMapping(var1, var2);
      }

   }

   public void endPrefixMapping(String var1) throws SAXException {
      if (this.fContentHandler != null) {
         this.fContentHandler.endPrefixMapping(var1);
      }

   }

   public void startElement(String var1, String var2, String var3, Attributes var4) throws SAXException {
      if (this.fNeedPushNSContext) {
         this.fNamespaceContext.pushContext();
      }

      this.fNeedPushNSContext = true;
      this.fillQName(this.fElementQName, var1, var2, var3);
      if (var4 instanceof Attributes2) {
         this.fillXMLAttributes2((Attributes2)var4);
      } else {
         this.fillXMLAttributes(var4);
      }

      try {
         this.fSchemaValidator.startElement(this.fElementQName, this.fAttributes, (Augmentations)null);
      } catch (XMLParseException var7) {
         throw Util.toSAXParseException(var7);
      } catch (XNIException var8) {
         throw Util.toSAXException(var8);
      }
   }

   public void endElement(String var1, String var2, String var3) throws SAXException {
      this.fillQName(this.fElementQName, var1, var2, var3);

      try {
         this.fSchemaValidator.endElement(this.fElementQName, (Augmentations)null);
      } catch (XMLParseException var11) {
         throw Util.toSAXParseException(var11);
      } catch (XNIException var12) {
         throw Util.toSAXException(var12);
      } finally {
         this.fNamespaceContext.popContext();
      }

   }

   public void characters(char[] var1, int var2, int var3) throws SAXException {
      try {
         this.fTempString.setValues(var1, var2, var3);
         this.fSchemaValidator.characters(this.fTempString, (Augmentations)null);
      } catch (XMLParseException var6) {
         throw Util.toSAXParseException(var6);
      } catch (XNIException var7) {
         throw Util.toSAXException(var7);
      }
   }

   public void ignorableWhitespace(char[] var1, int var2, int var3) throws SAXException {
      try {
         this.fTempString.setValues(var1, var2, var3);
         this.fSchemaValidator.ignorableWhitespace(this.fTempString, (Augmentations)null);
      } catch (XMLParseException var6) {
         throw Util.toSAXParseException(var6);
      } catch (XNIException var7) {
         throw Util.toSAXException(var7);
      }
   }

   public void processingInstruction(String var1, String var2) throws SAXException {
      if (this.fContentHandler != null) {
         this.fContentHandler.processingInstruction(var1, var2);
      }

   }

   public void skippedEntity(String var1) throws SAXException {
      if (this.fContentHandler != null) {
         this.fContentHandler.skippedEntity(var1);
      }

   }

   public void notationDecl(String var1, String var2, String var3) throws SAXException {
   }

   public void unparsedEntityDecl(String var1, String var2, String var3, String var4) throws SAXException {
      if (this.fUnparsedEntities == null) {
         this.fUnparsedEntities = new HashMap();
      }

      this.fUnparsedEntities.put(var1, var1);
   }

   public void validate(Source var1, Result var2) throws SAXException, IOException {
      if (!(var2 instanceof SAXResult) && var2 != null) {
         throw new IllegalArgumentException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "SourceResultMismatch", new Object[]{var1.getClass().getName(), var2.getClass().getName()}));
      } else {
         SAXSource var3 = (SAXSource)var1;
         SAXResult var4 = (SAXResult)var2;
         LexicalHandler var5 = null;
         if (var2 != null) {
            ContentHandler var6 = var4.getHandler();
            var5 = var4.getLexicalHandler();
            if (var5 == null && var6 instanceof LexicalHandler) {
               var5 = (LexicalHandler)var6;
            }

            this.setContentHandler(var6);
         }

         XMLReader var26 = null;

         try {
            var26 = var3.getXMLReader();
            if (var26 == null) {
               SAXParserFactory var7 = SAXParserFactory.newInstance();
               var7.setNamespaceAware(true);

               try {
                  var26 = var7.newSAXParser().getXMLReader();
                  if (var26 instanceof SAXParser) {
                     Object var8 = this.fComponentManager.getProperty("http://apache.org/xml/properties/security-manager");
                     if (var8 != null) {
                        try {
                           var26.setProperty("http://apache.org/xml/properties/security-manager", var8);
                        } catch (SAXException var23) {
                        }
                     }
                  }
               } catch (Exception var24) {
                  throw new FactoryConfigurationError(var24);
               }
            }

            try {
               this.fStringsInternalized = var26.getFeature("http://xml.org/sax/features/string-interning");
            } catch (SAXException var22) {
               this.fStringsInternalized = false;
            }

            ErrorHandler var27 = this.fComponentManager.getErrorHandler();
            var26.setErrorHandler((ErrorHandler)(var27 != null ? var27 : DraconianErrorHandler.getInstance()));
            var26.setEntityResolver(this.fResolutionForwarder);
            this.fResolutionForwarder.setEntityResolver(this.fComponentManager.getResourceResolver());
            var26.setContentHandler(this);
            var26.setDTDHandler(this);

            try {
               var26.setProperty("http://xml.org/sax/properties/lexical-handler", var5);
            } catch (SAXException var21) {
            }

            InputSource var28 = var3.getInputSource();
            var26.parse(var28);
         } finally {
            this.setContentHandler((ContentHandler)null);
            if (var26 != null) {
               try {
                  var26.setContentHandler((ContentHandler)null);
                  var26.setDTDHandler((DTDHandler)null);
                  var26.setErrorHandler((ErrorHandler)null);
                  var26.setEntityResolver((EntityResolver)null);
                  this.fResolutionForwarder.setEntityResolver((LSResourceResolver)null);
                  var26.setProperty("http://xml.org/sax/properties/lexical-handler", (Object)null);
               } catch (Exception var20) {
               }
            }

         }

      }
   }

   public ElementPSVI getElementPSVI() {
      return this.fTypeInfoProvider.getElementPSVI();
   }

   public AttributePSVI getAttributePSVI(int var1) {
      return this.fTypeInfoProvider.getAttributePSVI(var1);
   }

   public AttributePSVI getAttributePSVIByName(String var1, String var2) {
      return this.fTypeInfoProvider.getAttributePSVIByName(var1, var2);
   }

   private void fillQName(QName var1, String var2, String var3, String var4) {
      if (!this.fStringsInternalized) {
         var2 = var2 != null && var2.length() > 0 ? this.fSymbolTable.addSymbol(var2) : null;
         var3 = var3 != null ? this.fSymbolTable.addSymbol(var3) : XMLSymbols.EMPTY_STRING;
         var4 = var4 != null ? this.fSymbolTable.addSymbol(var4) : XMLSymbols.EMPTY_STRING;
      } else {
         if (var2 != null && var2.length() == 0) {
            var2 = null;
         }

         if (var3 == null) {
            var3 = XMLSymbols.EMPTY_STRING;
         }

         if (var4 == null) {
            var4 = XMLSymbols.EMPTY_STRING;
         }
      }

      String var5 = XMLSymbols.EMPTY_STRING;
      int var6 = var4.indexOf(58);
      if (var6 != -1) {
         var5 = this.fSymbolTable.addSymbol(var4.substring(0, var6));
      }

      var1.setValues(var5, var3, var4, var2);
   }

   private void fillXMLAttributes(Attributes var1) {
      this.fAttributes.removeAllAttributes();
      int var2 = var1.getLength();

      for(int var3 = 0; var3 < var2; ++var3) {
         this.fillXMLAttribute(var1, var3);
         this.fAttributes.setSpecified(var3, true);
      }

   }

   private void fillXMLAttributes2(Attributes2 var1) {
      this.fAttributes.removeAllAttributes();
      int var2 = var1.getLength();

      for(int var3 = 0; var3 < var2; ++var3) {
         this.fillXMLAttribute(var1, var3);
         this.fAttributes.setSpecified(var3, var1.isSpecified(var3));
         if (var1.isDeclared(var3)) {
            this.fAttributes.getAugmentations(var3).putItem("ATTRIBUTE_DECLARED", Boolean.TRUE);
         }
      }

   }

   private void fillXMLAttribute(Attributes var1, int var2) {
      this.fillQName(this.fAttributeQName, var1.getURI(var2), var1.getLocalName(var2), var1.getQName(var2));
      String var3 = var1.getType(var2);
      this.fAttributes.addAttributeNS(this.fAttributeQName, var3 != null ? var3 : XMLSymbols.fCDATASymbol, var1.getValue(var2));
   }

   static final class ResolutionForwarder implements EntityResolver2 {
      private static final String XML_TYPE = "http://www.w3.org/TR/REC-xml";
      protected LSResourceResolver fEntityResolver;

      public ResolutionForwarder() {
      }

      public ResolutionForwarder(LSResourceResolver var1) {
         this.setEntityResolver(var1);
      }

      public void setEntityResolver(LSResourceResolver var1) {
         this.fEntityResolver = var1;
      }

      public LSResourceResolver getEntityResolver() {
         return this.fEntityResolver;
      }

      public InputSource getExternalSubset(String var1, String var2) throws SAXException, IOException {
         return null;
      }

      public InputSource resolveEntity(String var1, String var2, String var3, String var4) throws SAXException, IOException {
         if (this.fEntityResolver != null) {
            LSInput var5 = this.fEntityResolver.resolveResource("http://www.w3.org/TR/REC-xml", (String)null, var2, var4, var3);
            if (var5 != null) {
               String var6 = var5.getPublicId();
               String var7 = var5.getSystemId();
               String var8 = var5.getBaseURI();
               Reader var9 = var5.getCharacterStream();
               InputStream var10 = var5.getByteStream();
               String var11 = var5.getStringData();
               String var12 = var5.getEncoding();
               InputSource var13 = new InputSource();
               var13.setPublicId(var6);
               var13.setSystemId(var8 != null ? this.resolveSystemId(var7, var8) : var7);
               if (var9 != null) {
                  var13.setCharacterStream(var9);
               } else if (var10 != null) {
                  var13.setByteStream(var10);
               } else if (var11 != null && var11.length() != 0) {
                  var13.setCharacterStream(new StringReader(var11));
               }

               var13.setEncoding(var12);
               return var13;
            }
         }

         return null;
      }

      public InputSource resolveEntity(String var1, String var2) throws SAXException, IOException {
         return this.resolveEntity((String)null, var1, (String)null, var2);
      }

      private String resolveSystemId(String var1, String var2) {
         try {
            return XMLEntityManager.expandSystemId(var1, var2, false);
         } catch (URI.MalformedURIException var4) {
            return var1;
         }
      }
   }

   private class XMLSchemaTypeInfoProvider extends TypeInfoProvider {
      private Augmentations fElementAugs;
      private XMLAttributes fAttributes;
      private boolean fInStartElement;
      private boolean fInEndElement;

      private XMLSchemaTypeInfoProvider() {
         this.fInStartElement = false;
         this.fInEndElement = false;
      }

      void beginStartElement(Augmentations var1, XMLAttributes var2) {
         this.fInStartElement = true;
         this.fElementAugs = var1;
         this.fAttributes = var2;
      }

      void finishStartElement() {
         this.fInStartElement = false;
         this.fElementAugs = null;
         this.fAttributes = null;
      }

      void beginEndElement(Augmentations var1) {
         this.fInEndElement = true;
         this.fElementAugs = var1;
      }

      void finishEndElement() {
         this.fInEndElement = false;
         this.fElementAugs = null;
      }

      private void checkStateAttribute() {
         if (!this.fInStartElement) {
            throw new IllegalStateException(JAXPValidationMessageFormatter.formatMessage(ValidatorHandlerImpl.this.fComponentManager.getLocale(), "TypeInfoProviderIllegalStateAttribute", (Object[])null));
         }
      }

      private void checkStateElement() {
         if (!this.fInStartElement && !this.fInEndElement) {
            throw new IllegalStateException(JAXPValidationMessageFormatter.formatMessage(ValidatorHandlerImpl.this.fComponentManager.getLocale(), "TypeInfoProviderIllegalStateElement", (Object[])null));
         }
      }

      public TypeInfo getAttributeTypeInfo(int var1) {
         this.checkStateAttribute();
         return this.getAttributeType(var1);
      }

      private TypeInfo getAttributeType(int var1) {
         this.checkStateAttribute();
         if (var1 >= 0 && this.fAttributes.getLength() > var1) {
            Augmentations var2 = this.fAttributes.getAugmentations(var1);
            if (var2 == null) {
               return null;
            } else {
               AttributePSVI var3 = (AttributePSVI)var2.getItem("ATTRIBUTE_PSVI");
               return this.getTypeInfoFromPSVI(var3);
            }
         } else {
            throw new IndexOutOfBoundsException(Integer.toString(var1));
         }
      }

      public TypeInfo getAttributeTypeInfo(String var1, String var2) {
         this.checkStateAttribute();
         return this.getAttributeTypeInfo(this.fAttributes.getIndex(var1, var2));
      }

      public TypeInfo getAttributeTypeInfo(String var1) {
         this.checkStateAttribute();
         return this.getAttributeTypeInfo(this.fAttributes.getIndex(var1));
      }

      public TypeInfo getElementTypeInfo() {
         this.checkStateElement();
         if (this.fElementAugs == null) {
            return null;
         } else {
            ElementPSVI var1 = (ElementPSVI)this.fElementAugs.getItem("ELEMENT_PSVI");
            return this.getTypeInfoFromPSVI(var1);
         }
      }

      private TypeInfo getTypeInfoFromPSVI(ItemPSVI var1) {
         if (var1 == null) {
            return null;
         } else {
            if (var1.getValidity() == 2) {
               XSSimpleTypeDefinition var2 = var1.getMemberTypeDefinition();
               if (var2 != null) {
                  return var2 instanceof TypeInfo ? (TypeInfo)var2 : null;
               }
            }

            XSTypeDefinition var3 = var1.getTypeDefinition();
            if (var3 != null) {
               return var3 instanceof TypeInfo ? (TypeInfo)var3 : null;
            } else {
               return null;
            }
         }
      }

      public boolean isIdAttribute(int var1) {
         this.checkStateAttribute();
         XSSimpleType var2 = (XSSimpleType)this.getAttributeType(var1);
         return var2 == null ? false : var2.isIDType();
      }

      public boolean isSpecified(int var1) {
         this.checkStateAttribute();
         return this.fAttributes.isSpecified(var1);
      }

      ElementPSVI getElementPSVI() {
         return this.fElementAugs != null ? (ElementPSVI)this.fElementAugs.getItem("ELEMENT_PSVI") : null;
      }

      AttributePSVI getAttributePSVI(int var1) {
         if (this.fAttributes != null) {
            Augmentations var2 = this.fAttributes.getAugmentations(var1);
            if (var2 != null) {
               return (AttributePSVI)var2.getItem("ATTRIBUTE_PSVI");
            }
         }

         return null;
      }

      AttributePSVI getAttributePSVIByName(String var1, String var2) {
         if (this.fAttributes != null) {
            Augmentations var3 = this.fAttributes.getAugmentations(var1, var2);
            if (var3 != null) {
               return (AttributePSVI)var3.getItem("ATTRIBUTE_PSVI");
            }
         }

         return null;
      }

      // $FF: synthetic method
      XMLSchemaTypeInfoProvider(Object var2) {
         this();
      }
   }
}
