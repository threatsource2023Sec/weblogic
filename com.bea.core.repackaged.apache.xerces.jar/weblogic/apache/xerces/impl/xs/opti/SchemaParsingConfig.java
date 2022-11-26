package weblogic.apache.xerces.impl.xs.opti;

import java.io.IOException;
import java.util.Locale;
import weblogic.apache.xerces.impl.XML11DTDScannerImpl;
import weblogic.apache.xerces.impl.XML11NSDocumentScannerImpl;
import weblogic.apache.xerces.impl.XMLDTDScannerImpl;
import weblogic.apache.xerces.impl.XMLEntityHandler;
import weblogic.apache.xerces.impl.XMLEntityManager;
import weblogic.apache.xerces.impl.XMLErrorReporter;
import weblogic.apache.xerces.impl.XMLNSDocumentScannerImpl;
import weblogic.apache.xerces.impl.XMLVersionDetector;
import weblogic.apache.xerces.impl.dv.DTDDVFactory;
import weblogic.apache.xerces.impl.msg.XMLMessageFormatter;
import weblogic.apache.xerces.impl.validation.ValidationManager;
import weblogic.apache.xerces.impl.xs.XSMessageFormatter;
import weblogic.apache.xerces.parsers.BasicParserConfiguration;
import weblogic.apache.xerces.util.SymbolTable;
import weblogic.apache.xerces.xni.XMLLocator;
import weblogic.apache.xerces.xni.XNIException;
import weblogic.apache.xerces.xni.grammars.XMLGrammarPool;
import weblogic.apache.xerces.xni.parser.XMLComponent;
import weblogic.apache.xerces.xni.parser.XMLComponentManager;
import weblogic.apache.xerces.xni.parser.XMLConfigurationException;
import weblogic.apache.xerces.xni.parser.XMLDTDScanner;
import weblogic.apache.xerces.xni.parser.XMLDocumentScanner;
import weblogic.apache.xerces.xni.parser.XMLInputSource;
import weblogic.apache.xerces.xni.parser.XMLPullParserConfiguration;

public class SchemaParsingConfig extends BasicParserConfiguration implements XMLPullParserConfiguration {
   protected static final String XML11_DATATYPE_VALIDATOR_FACTORY = "weblogic.apache.xerces.impl.dv.dtd.XML11DTDDVFactoryImpl";
   protected static final String WARN_ON_DUPLICATE_ATTDEF = "http://apache.org/xml/features/validation/warn-on-duplicate-attdef";
   protected static final String WARN_ON_UNDECLARED_ELEMDEF = "http://apache.org/xml/features/validation/warn-on-undeclared-elemdef";
   protected static final String ALLOW_JAVA_ENCODINGS = "http://apache.org/xml/features/allow-java-encodings";
   protected static final String CONTINUE_AFTER_FATAL_ERROR = "http://apache.org/xml/features/continue-after-fatal-error";
   protected static final String LOAD_EXTERNAL_DTD = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
   protected static final String NOTIFY_BUILTIN_REFS = "http://apache.org/xml/features/scanner/notify-builtin-refs";
   protected static final String NOTIFY_CHAR_REFS = "http://apache.org/xml/features/scanner/notify-char-refs";
   protected static final String NORMALIZE_DATA = "http://apache.org/xml/features/validation/schema/normalized-value";
   protected static final String SCHEMA_ELEMENT_DEFAULT = "http://apache.org/xml/features/validation/schema/element-default";
   protected static final String GENERATE_SYNTHETIC_ANNOTATIONS = "http://apache.org/xml/features/generate-synthetic-annotations";
   protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
   protected static final String ENTITY_MANAGER = "http://apache.org/xml/properties/internal/entity-manager";
   protected static final String DOCUMENT_SCANNER = "http://apache.org/xml/properties/internal/document-scanner";
   protected static final String DTD_SCANNER = "http://apache.org/xml/properties/internal/dtd-scanner";
   protected static final String XMLGRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
   protected static final String DTD_VALIDATOR = "http://apache.org/xml/properties/internal/validator/dtd";
   protected static final String NAMESPACE_BINDER = "http://apache.org/xml/properties/internal/namespace-binder";
   protected static final String DATATYPE_VALIDATOR_FACTORY = "http://apache.org/xml/properties/internal/datatype-validator-factory";
   protected static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
   protected static final String SCHEMA_VALIDATOR = "http://apache.org/xml/properties/internal/validator/schema";
   protected static final String LOCALE = "http://apache.org/xml/properties/locale";
   private static final boolean PRINT_EXCEPTION_STACK_TRACE = false;
   protected final DTDDVFactory fDatatypeValidatorFactory;
   protected final XMLNSDocumentScannerImpl fNamespaceScanner;
   protected final XMLDTDScannerImpl fDTDScanner;
   protected DTDDVFactory fXML11DatatypeFactory;
   protected XML11NSDocumentScannerImpl fXML11NSDocScanner;
   protected XML11DTDScannerImpl fXML11DTDScanner;
   protected DTDDVFactory fCurrentDVFactory;
   protected XMLDocumentScanner fCurrentScanner;
   protected XMLDTDScanner fCurrentDTDScanner;
   protected XMLGrammarPool fGrammarPool;
   protected final XMLVersionDetector fVersionDetector;
   protected final XMLErrorReporter fErrorReporter;
   protected final XMLEntityManager fEntityManager;
   protected XMLInputSource fInputSource;
   protected final ValidationManager fValidationManager;
   protected XMLLocator fLocator;
   protected boolean fParseInProgress;
   protected boolean fConfigUpdated;
   private boolean f11Initialized;

   public SchemaParsingConfig() {
      this((SymbolTable)null, (XMLGrammarPool)null, (XMLComponentManager)null);
   }

   public SchemaParsingConfig(SymbolTable var1) {
      this(var1, (XMLGrammarPool)null, (XMLComponentManager)null);
   }

   public SchemaParsingConfig(SymbolTable var1, XMLGrammarPool var2) {
      this(var1, var2, (XMLComponentManager)null);
   }

   public SchemaParsingConfig(SymbolTable var1, XMLGrammarPool var2, XMLComponentManager var3) {
      super(var1, var3);
      this.fXML11DatatypeFactory = null;
      this.fXML11NSDocScanner = null;
      this.fXML11DTDScanner = null;
      this.fParseInProgress = false;
      this.fConfigUpdated = false;
      this.f11Initialized = false;
      String[] var4 = new String[]{"http://apache.org/xml/features/internal/parser-settings", "http://apache.org/xml/features/validation/warn-on-duplicate-attdef", "http://apache.org/xml/features/validation/warn-on-undeclared-elemdef", "http://apache.org/xml/features/allow-java-encodings", "http://apache.org/xml/features/continue-after-fatal-error", "http://apache.org/xml/features/nonvalidating/load-external-dtd", "http://apache.org/xml/features/scanner/notify-builtin-refs", "http://apache.org/xml/features/scanner/notify-char-refs", "http://apache.org/xml/features/generate-synthetic-annotations"};
      this.addRecognizedFeatures(var4);
      this.fFeatures.put("http://apache.org/xml/features/internal/parser-settings", Boolean.TRUE);
      this.fFeatures.put("http://apache.org/xml/features/validation/warn-on-duplicate-attdef", Boolean.FALSE);
      this.fFeatures.put("http://apache.org/xml/features/validation/warn-on-undeclared-elemdef", Boolean.FALSE);
      this.fFeatures.put("http://apache.org/xml/features/allow-java-encodings", Boolean.FALSE);
      this.fFeatures.put("http://apache.org/xml/features/continue-after-fatal-error", Boolean.FALSE);
      this.fFeatures.put("http://apache.org/xml/features/nonvalidating/load-external-dtd", Boolean.TRUE);
      this.fFeatures.put("http://apache.org/xml/features/scanner/notify-builtin-refs", Boolean.FALSE);
      this.fFeatures.put("http://apache.org/xml/features/scanner/notify-char-refs", Boolean.FALSE);
      this.fFeatures.put("http://apache.org/xml/features/generate-synthetic-annotations", Boolean.FALSE);
      String[] var5 = new String[]{"http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/entity-manager", "http://apache.org/xml/properties/internal/document-scanner", "http://apache.org/xml/properties/internal/dtd-scanner", "http://apache.org/xml/properties/internal/validator/dtd", "http://apache.org/xml/properties/internal/namespace-binder", "http://apache.org/xml/properties/internal/grammar-pool", "http://apache.org/xml/properties/internal/datatype-validator-factory", "http://apache.org/xml/properties/internal/validation-manager", "http://apache.org/xml/features/generate-synthetic-annotations", "http://apache.org/xml/properties/locale"};
      this.addRecognizedProperties(var5);
      this.fGrammarPool = var2;
      if (this.fGrammarPool != null) {
         this.setProperty("http://apache.org/xml/properties/internal/grammar-pool", this.fGrammarPool);
      }

      this.fEntityManager = new XMLEntityManager();
      this.fProperties.put("http://apache.org/xml/properties/internal/entity-manager", this.fEntityManager);
      this.addComponent(this.fEntityManager);
      this.fErrorReporter = new XMLErrorReporter();
      this.fErrorReporter.setDocumentLocator(this.fEntityManager.getEntityScanner());
      this.fProperties.put("http://apache.org/xml/properties/internal/error-reporter", this.fErrorReporter);
      this.addComponent(this.fErrorReporter);
      this.fNamespaceScanner = new XMLNSDocumentScannerImpl();
      this.fProperties.put("http://apache.org/xml/properties/internal/document-scanner", this.fNamespaceScanner);
      this.addRecognizedParamsAndSetDefaults(this.fNamespaceScanner);
      this.fDTDScanner = new XMLDTDScannerImpl();
      this.fProperties.put("http://apache.org/xml/properties/internal/dtd-scanner", this.fDTDScanner);
      this.addRecognizedParamsAndSetDefaults(this.fDTDScanner);
      this.fDatatypeValidatorFactory = DTDDVFactory.getInstance();
      this.fProperties.put("http://apache.org/xml/properties/internal/datatype-validator-factory", this.fDatatypeValidatorFactory);
      this.fValidationManager = new ValidationManager();
      this.fProperties.put("http://apache.org/xml/properties/internal/validation-manager", this.fValidationManager);
      this.fVersionDetector = new XMLVersionDetector();
      if (this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210") == null) {
         XMLMessageFormatter var6 = new XMLMessageFormatter();
         this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210", var6);
         this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1999/REC-xml-names-19990114", var6);
      }

      if (this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/xml-schema-1") == null) {
         XSMessageFormatter var8 = new XSMessageFormatter();
         this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/xml-schema-1", var8);
      }

      try {
         this.setLocale(Locale.getDefault());
      } catch (XNIException var7) {
      }

   }

   public boolean getFeature(String var1) throws XMLConfigurationException {
      return var1.equals("http://apache.org/xml/features/internal/parser-settings") ? this.fConfigUpdated : super.getFeature(var1);
   }

   public void setFeature(String var1, boolean var2) throws XMLConfigurationException {
      this.fConfigUpdated = true;
      this.fNamespaceScanner.setFeature(var1, var2);
      this.fDTDScanner.setFeature(var1, var2);
      if (this.f11Initialized) {
         try {
            this.fXML11DTDScanner.setFeature(var1, var2);
         } catch (Exception var5) {
         }

         try {
            this.fXML11NSDocScanner.setFeature(var1, var2);
         } catch (Exception var4) {
         }
      }

      super.setFeature(var1, var2);
   }

   public Object getProperty(String var1) throws XMLConfigurationException {
      return "http://apache.org/xml/properties/locale".equals(var1) ? this.getLocale() : super.getProperty(var1);
   }

   public void setProperty(String var1, Object var2) throws XMLConfigurationException {
      this.fConfigUpdated = true;
      if ("http://apache.org/xml/properties/locale".equals(var1)) {
         this.setLocale((Locale)var2);
      }

      this.fNamespaceScanner.setProperty(var1, var2);
      this.fDTDScanner.setProperty(var1, var2);
      if (this.f11Initialized) {
         try {
            this.fXML11DTDScanner.setProperty(var1, var2);
         } catch (Exception var5) {
         }

         try {
            this.fXML11NSDocScanner.setProperty(var1, var2);
         } catch (Exception var4) {
         }
      }

      super.setProperty(var1, var2);
   }

   public void setLocale(Locale var1) throws XNIException {
      super.setLocale(var1);
      this.fErrorReporter.setLocale(var1);
   }

   public void setInputSource(XMLInputSource var1) throws XMLConfigurationException, IOException {
      this.fInputSource = var1;
   }

   public boolean parse(boolean var1) throws XNIException, IOException {
      if (this.fInputSource != null) {
         try {
            this.fValidationManager.reset();
            this.fVersionDetector.reset(this);
            this.reset();
            short var2 = this.fVersionDetector.determineDocVersion(this.fInputSource);
            if (var2 == 1) {
               this.configurePipeline();
               this.resetXML10();
            } else {
               if (var2 != 2) {
                  return false;
               }

               this.initXML11Components();
               this.configureXML11Pipeline();
               this.resetXML11();
            }

            this.fConfigUpdated = false;
            this.fVersionDetector.startDocumentParsing((XMLEntityHandler)this.fCurrentScanner, var2);
            this.fInputSource = null;
         } catch (XNIException var10) {
            throw var10;
         } catch (IOException var11) {
            throw var11;
         } catch (RuntimeException var12) {
            throw var12;
         } catch (Exception var13) {
            throw new XNIException(var13);
         }
      }

      try {
         return this.fCurrentScanner.scanDocument(var1);
      } catch (XNIException var6) {
         throw var6;
      } catch (IOException var7) {
         throw var7;
      } catch (RuntimeException var8) {
         throw var8;
      } catch (Exception var9) {
         throw new XNIException(var9);
      }
   }

   public void cleanup() {
      this.fEntityManager.closeReaders();
   }

   public void parse(XMLInputSource var1) throws XNIException, IOException {
      if (this.fParseInProgress) {
         throw new XNIException("FWK005 parse may not be called while parsing.");
      } else {
         this.fParseInProgress = true;

         try {
            this.setInputSource(var1);
            this.parse(true);
         } catch (XNIException var13) {
            throw var13;
         } catch (IOException var14) {
            throw var14;
         } catch (RuntimeException var15) {
            throw var15;
         } catch (Exception var16) {
            throw new XNIException(var16);
         } finally {
            this.fParseInProgress = false;
            this.cleanup();
         }

      }
   }

   public void reset() throws XNIException {
      super.reset();
   }

   protected void configurePipeline() {
      if (this.fCurrentDVFactory != this.fDatatypeValidatorFactory) {
         this.fCurrentDVFactory = this.fDatatypeValidatorFactory;
         this.setProperty("http://apache.org/xml/properties/internal/datatype-validator-factory", this.fCurrentDVFactory);
      }

      if (this.fCurrentScanner != this.fNamespaceScanner) {
         this.fCurrentScanner = this.fNamespaceScanner;
         this.setProperty("http://apache.org/xml/properties/internal/document-scanner", this.fCurrentScanner);
      }

      this.fNamespaceScanner.setDocumentHandler(this.fDocumentHandler);
      if (this.fDocumentHandler != null) {
         this.fDocumentHandler.setDocumentSource(this.fNamespaceScanner);
      }

      this.fLastComponent = this.fNamespaceScanner;
      if (this.fCurrentDTDScanner != this.fDTDScanner) {
         this.fCurrentDTDScanner = this.fDTDScanner;
         this.setProperty("http://apache.org/xml/properties/internal/dtd-scanner", this.fCurrentDTDScanner);
      }

      this.fDTDScanner.setDTDHandler(this.fDTDHandler);
      if (this.fDTDHandler != null) {
         this.fDTDHandler.setDTDSource(this.fDTDScanner);
      }

      this.fDTDScanner.setDTDContentModelHandler(this.fDTDContentModelHandler);
      if (this.fDTDContentModelHandler != null) {
         this.fDTDContentModelHandler.setDTDContentModelSource(this.fDTDScanner);
      }

   }

   protected void configureXML11Pipeline() {
      if (this.fCurrentDVFactory != this.fXML11DatatypeFactory) {
         this.fCurrentDVFactory = this.fXML11DatatypeFactory;
         this.setProperty("http://apache.org/xml/properties/internal/datatype-validator-factory", this.fCurrentDVFactory);
      }

      if (this.fCurrentScanner != this.fXML11NSDocScanner) {
         this.fCurrentScanner = this.fXML11NSDocScanner;
         this.setProperty("http://apache.org/xml/properties/internal/document-scanner", this.fCurrentScanner);
      }

      this.fXML11NSDocScanner.setDocumentHandler(this.fDocumentHandler);
      if (this.fDocumentHandler != null) {
         this.fDocumentHandler.setDocumentSource(this.fXML11NSDocScanner);
      }

      this.fLastComponent = this.fXML11NSDocScanner;
      if (this.fCurrentDTDScanner != this.fXML11DTDScanner) {
         this.fCurrentDTDScanner = this.fXML11DTDScanner;
         this.setProperty("http://apache.org/xml/properties/internal/dtd-scanner", this.fCurrentDTDScanner);
      }

      this.fXML11DTDScanner.setDTDHandler(this.fDTDHandler);
      if (this.fDTDHandler != null) {
         this.fDTDHandler.setDTDSource(this.fXML11DTDScanner);
      }

      this.fXML11DTDScanner.setDTDContentModelHandler(this.fDTDContentModelHandler);
      if (this.fDTDContentModelHandler != null) {
         this.fDTDContentModelHandler.setDTDContentModelSource(this.fXML11DTDScanner);
      }

   }

   protected void checkFeature(String var1) throws XMLConfigurationException {
      if (var1.startsWith("http://apache.org/xml/features/")) {
         int var2 = var1.length() - "http://apache.org/xml/features/".length();
         if (var2 == "validation/dynamic".length() && var1.endsWith("validation/dynamic")) {
            return;
         }

         byte var3;
         if (var2 == "validation/default-attribute-values".length() && var1.endsWith("validation/default-attribute-values")) {
            var3 = 1;
            throw new XMLConfigurationException(var3, var1);
         }

         if (var2 == "validation/validate-content-models".length() && var1.endsWith("validation/validate-content-models")) {
            var3 = 1;
            throw new XMLConfigurationException(var3, var1);
         }

         if (var2 == "nonvalidating/load-dtd-grammar".length() && var1.endsWith("nonvalidating/load-dtd-grammar")) {
            return;
         }

         if (var2 == "nonvalidating/load-external-dtd".length() && var1.endsWith("nonvalidating/load-external-dtd")) {
            return;
         }

         if (var2 == "validation/validate-datatypes".length() && var1.endsWith("validation/validate-datatypes")) {
            var3 = 1;
            throw new XMLConfigurationException(var3, var1);
         }
      }

      super.checkFeature(var1);
   }

   protected void checkProperty(String var1) throws XMLConfigurationException {
      int var2;
      if (var1.startsWith("http://apache.org/xml/properties/")) {
         var2 = var1.length() - "http://apache.org/xml/properties/".length();
         if (var2 == "internal/dtd-scanner".length() && var1.endsWith("internal/dtd-scanner")) {
            return;
         }
      }

      if (var1.startsWith("http://java.sun.com/xml/jaxp/properties/")) {
         var2 = var1.length() - "http://java.sun.com/xml/jaxp/properties/".length();
         if (var2 == "schemaSource".length() && var1.endsWith("schemaSource")) {
            return;
         }
      }

      super.checkProperty(var1);
   }

   private void addRecognizedParamsAndSetDefaults(XMLComponent var1) {
      String[] var2 = var1.getRecognizedFeatures();
      this.addRecognizedFeatures(var2);
      String[] var3 = var1.getRecognizedProperties();
      this.addRecognizedProperties(var3);
      int var4;
      String var5;
      if (var2 != null) {
         for(var4 = 0; var4 < var2.length; ++var4) {
            var5 = var2[var4];
            Boolean var6 = var1.getFeatureDefault(var5);
            if (var6 != null && !this.fFeatures.containsKey(var5)) {
               this.fFeatures.put(var5, var6);
               this.fConfigUpdated = true;
            }
         }
      }

      if (var3 != null) {
         for(var4 = 0; var4 < var3.length; ++var4) {
            var5 = var3[var4];
            Object var7 = var1.getPropertyDefault(var5);
            if (var7 != null && !this.fProperties.containsKey(var5)) {
               this.fProperties.put(var5, var7);
               this.fConfigUpdated = true;
            }
         }
      }

   }

   protected final void resetXML10() throws XNIException {
      this.fNamespaceScanner.reset(this);
      this.fDTDScanner.reset(this);
   }

   protected final void resetXML11() throws XNIException {
      this.fXML11NSDocScanner.reset(this);
      this.fXML11DTDScanner.reset(this);
   }

   public void resetNodePool() {
   }

   private void initXML11Components() {
      if (!this.f11Initialized) {
         this.fXML11DatatypeFactory = DTDDVFactory.getInstance("weblogic.apache.xerces.impl.dv.dtd.XML11DTDDVFactoryImpl");
         this.fXML11DTDScanner = new XML11DTDScannerImpl();
         this.addRecognizedParamsAndSetDefaults(this.fXML11DTDScanner);
         this.fXML11NSDocScanner = new XML11NSDocumentScannerImpl();
         this.addRecognizedParamsAndSetDefaults(this.fXML11NSDocScanner);
         this.f11Initialized = true;
      }

   }
}
