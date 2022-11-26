package weblogic.apache.xerces.impl.dtd;

import java.io.EOFException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Locale;
import weblogic.apache.xerces.impl.XMLDTDScannerImpl;
import weblogic.apache.xerces.impl.XMLEntityManager;
import weblogic.apache.xerces.impl.XMLErrorReporter;
import weblogic.apache.xerces.impl.msg.XMLMessageFormatter;
import weblogic.apache.xerces.util.DefaultErrorHandler;
import weblogic.apache.xerces.util.SymbolTable;
import weblogic.apache.xerces.xni.XNIException;
import weblogic.apache.xerces.xni.grammars.Grammar;
import weblogic.apache.xerces.xni.grammars.XMLGrammarLoader;
import weblogic.apache.xerces.xni.grammars.XMLGrammarPool;
import weblogic.apache.xerces.xni.parser.XMLConfigurationException;
import weblogic.apache.xerces.xni.parser.XMLEntityResolver;
import weblogic.apache.xerces.xni.parser.XMLErrorHandler;
import weblogic.apache.xerces.xni.parser.XMLInputSource;

public class XMLDTDLoader extends XMLDTDProcessor implements XMLGrammarLoader {
   protected static final String STANDARD_URI_CONFORMANT_FEATURE = "http://apache.org/xml/features/standard-uri-conformant";
   protected static final String BALANCE_SYNTAX_TREES = "http://apache.org/xml/features/validation/balance-syntax-trees";
   private static final String[] LOADER_RECOGNIZED_FEATURES = new String[]{"http://xml.org/sax/features/validation", "http://apache.org/xml/features/validation/warn-on-duplicate-attdef", "http://apache.org/xml/features/validation/warn-on-undeclared-elemdef", "http://apache.org/xml/features/scanner/notify-char-refs", "http://apache.org/xml/features/standard-uri-conformant", "http://apache.org/xml/features/validation/balance-syntax-trees"};
   protected static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
   protected static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
   protected static final String LOCALE = "http://apache.org/xml/properties/locale";
   private static final String[] LOADER_RECOGNIZED_PROPERTIES = new String[]{"http://apache.org/xml/properties/internal/symbol-table", "http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/error-handler", "http://apache.org/xml/properties/internal/entity-resolver", "http://apache.org/xml/properties/internal/grammar-pool", "http://apache.org/xml/properties/internal/validator/dtd", "http://apache.org/xml/properties/locale"};
   private boolean fStrictURI;
   private boolean fBalanceSyntaxTrees;
   protected XMLEntityResolver fEntityResolver;
   protected XMLDTDScannerImpl fDTDScanner;
   protected XMLEntityManager fEntityManager;
   protected Locale fLocale;

   public XMLDTDLoader() {
      this(new SymbolTable());
   }

   public XMLDTDLoader(SymbolTable var1) {
      this(var1, (XMLGrammarPool)null);
   }

   public XMLDTDLoader(SymbolTable var1, XMLGrammarPool var2) {
      this(var1, var2, (XMLErrorReporter)null, new XMLEntityManager());
   }

   XMLDTDLoader(SymbolTable var1, XMLGrammarPool var2, XMLErrorReporter var3, XMLEntityResolver var4) {
      this.fStrictURI = false;
      this.fBalanceSyntaxTrees = false;
      this.fSymbolTable = var1;
      this.fGrammarPool = var2;
      if (var3 == null) {
         var3 = new XMLErrorReporter();
         var3.setProperty("http://apache.org/xml/properties/internal/error-handler", new DefaultErrorHandler());
      }

      this.fErrorReporter = var3;
      if (this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210") == null) {
         XMLMessageFormatter var5 = new XMLMessageFormatter();
         this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210", var5);
         this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1999/REC-xml-names-19990114", var5);
      }

      this.fEntityResolver = var4;
      if (this.fEntityResolver instanceof XMLEntityManager) {
         this.fEntityManager = (XMLEntityManager)this.fEntityResolver;
      } else {
         this.fEntityManager = new XMLEntityManager();
      }

      this.fEntityManager.setProperty("http://apache.org/xml/properties/internal/error-reporter", var3);
      this.fDTDScanner = this.createDTDScanner(this.fSymbolTable, this.fErrorReporter, this.fEntityManager);
      this.fDTDScanner.setDTDHandler(this);
      this.fDTDScanner.setDTDContentModelHandler(this);
      this.reset();
   }

   public String[] getRecognizedFeatures() {
      return (String[])LOADER_RECOGNIZED_FEATURES.clone();
   }

   public void setFeature(String var1, boolean var2) throws XMLConfigurationException {
      if (var1.equals("http://xml.org/sax/features/validation")) {
         this.fValidation = var2;
      } else if (var1.equals("http://apache.org/xml/features/validation/warn-on-duplicate-attdef")) {
         this.fWarnDuplicateAttdef = var2;
      } else if (var1.equals("http://apache.org/xml/features/validation/warn-on-undeclared-elemdef")) {
         this.fWarnOnUndeclaredElemdef = var2;
      } else if (var1.equals("http://apache.org/xml/features/scanner/notify-char-refs")) {
         this.fDTDScanner.setFeature(var1, var2);
      } else if (var1.equals("http://apache.org/xml/features/standard-uri-conformant")) {
         this.fStrictURI = var2;
      } else {
         if (!var1.equals("http://apache.org/xml/features/validation/balance-syntax-trees")) {
            throw new XMLConfigurationException((short)0, var1);
         }

         this.fBalanceSyntaxTrees = var2;
      }

   }

   public String[] getRecognizedProperties() {
      return (String[])LOADER_RECOGNIZED_PROPERTIES.clone();
   }

   public Object getProperty(String var1) throws XMLConfigurationException {
      if (var1.equals("http://apache.org/xml/properties/internal/symbol-table")) {
         return this.fSymbolTable;
      } else if (var1.equals("http://apache.org/xml/properties/internal/error-reporter")) {
         return this.fErrorReporter;
      } else if (var1.equals("http://apache.org/xml/properties/internal/error-handler")) {
         return this.fErrorReporter.getErrorHandler();
      } else if (var1.equals("http://apache.org/xml/properties/internal/entity-resolver")) {
         return this.fEntityResolver;
      } else if (var1.equals("http://apache.org/xml/properties/locale")) {
         return this.getLocale();
      } else if (var1.equals("http://apache.org/xml/properties/internal/grammar-pool")) {
         return this.fGrammarPool;
      } else if (var1.equals("http://apache.org/xml/properties/internal/validator/dtd")) {
         return this.fValidator;
      } else {
         throw new XMLConfigurationException((short)0, var1);
      }
   }

   public void setProperty(String var1, Object var2) throws XMLConfigurationException {
      if (var1.equals("http://apache.org/xml/properties/internal/symbol-table")) {
         this.fSymbolTable = (SymbolTable)var2;
         this.fDTDScanner.setProperty(var1, var2);
         this.fEntityManager.setProperty(var1, var2);
      } else if (var1.equals("http://apache.org/xml/properties/internal/error-reporter")) {
         this.fErrorReporter = (XMLErrorReporter)var2;
         if (this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210") == null) {
            XMLMessageFormatter var3 = new XMLMessageFormatter();
            this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210", var3);
            this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1999/REC-xml-names-19990114", var3);
         }

         this.fDTDScanner.setProperty(var1, var2);
         this.fEntityManager.setProperty(var1, var2);
      } else if (var1.equals("http://apache.org/xml/properties/internal/error-handler")) {
         this.fErrorReporter.setProperty(var1, var2);
      } else if (var1.equals("http://apache.org/xml/properties/internal/entity-resolver")) {
         this.fEntityResolver = (XMLEntityResolver)var2;
         this.fEntityManager.setProperty(var1, var2);
      } else if (var1.equals("http://apache.org/xml/properties/locale")) {
         this.setLocale((Locale)var2);
      } else {
         if (!var1.equals("http://apache.org/xml/properties/internal/grammar-pool")) {
            throw new XMLConfigurationException((short)0, var1);
         }

         this.fGrammarPool = (XMLGrammarPool)var2;
      }

   }

   public boolean getFeature(String var1) throws XMLConfigurationException {
      if (var1.equals("http://xml.org/sax/features/validation")) {
         return this.fValidation;
      } else if (var1.equals("http://apache.org/xml/features/validation/warn-on-duplicate-attdef")) {
         return this.fWarnDuplicateAttdef;
      } else if (var1.equals("http://apache.org/xml/features/validation/warn-on-undeclared-elemdef")) {
         return this.fWarnOnUndeclaredElemdef;
      } else if (var1.equals("http://apache.org/xml/features/scanner/notify-char-refs")) {
         return this.fDTDScanner.getFeature(var1);
      } else if (var1.equals("http://apache.org/xml/features/standard-uri-conformant")) {
         return this.fStrictURI;
      } else if (var1.equals("http://apache.org/xml/features/validation/balance-syntax-trees")) {
         return this.fBalanceSyntaxTrees;
      } else {
         throw new XMLConfigurationException((short)0, var1);
      }
   }

   public void setLocale(Locale var1) {
      this.fLocale = var1;
      this.fErrorReporter.setLocale(var1);
   }

   public Locale getLocale() {
      return this.fLocale;
   }

   public void setErrorHandler(XMLErrorHandler var1) {
      this.fErrorReporter.setProperty("http://apache.org/xml/properties/internal/error-handler", var1);
   }

   public XMLErrorHandler getErrorHandler() {
      return this.fErrorReporter.getErrorHandler();
   }

   public void setEntityResolver(XMLEntityResolver var1) {
      this.fEntityResolver = var1;
      this.fEntityManager.setProperty("http://apache.org/xml/properties/internal/entity-resolver", var1);
   }

   public XMLEntityResolver getEntityResolver() {
      return this.fEntityResolver;
   }

   public Grammar loadGrammar(XMLInputSource var1) throws IOException, XNIException {
      this.reset();
      String var2 = XMLEntityManager.expandSystemId(var1.getSystemId(), var1.getBaseSystemId(), this.fStrictURI);
      XMLDTDDescription var3 = new XMLDTDDescription(var1.getPublicId(), var1.getSystemId(), var1.getBaseSystemId(), var2, (String)null);
      if (!this.fBalanceSyntaxTrees) {
         this.fDTDGrammar = new DTDGrammar(this.fSymbolTable, var3);
      } else {
         this.fDTDGrammar = new BalancedDTDGrammar(this.fSymbolTable, var3);
      }

      this.fGrammarBucket = new DTDGrammarBucket();
      this.fGrammarBucket.setStandalone(false);
      this.fGrammarBucket.setActiveGrammar(this.fDTDGrammar);

      try {
         this.fDTDScanner.setInputSource(var1);
         this.fDTDScanner.scanDTDExternalSubset(true);
      } catch (EOFException var9) {
      } finally {
         this.fEntityManager.closeReaders();
      }

      if (this.fDTDGrammar != null && this.fGrammarPool != null) {
         this.fGrammarPool.cacheGrammars("http://www.w3.org/TR/REC-xml", new Grammar[]{this.fDTDGrammar});
      }

      return this.fDTDGrammar;
   }

   public void loadGrammarWithContext(XMLDTDValidator var1, String var2, String var3, String var4, String var5, String var6) throws IOException, XNIException {
      DTDGrammarBucket var7 = var1.getGrammarBucket();
      DTDGrammar var8 = var7.getActiveGrammar();
      if (var8 != null && !var8.isImmutable()) {
         this.fGrammarBucket = var7;
         this.fEntityManager.setScannerVersion(this.getScannerVersion());
         this.reset();

         try {
            XMLInputSource var10;
            if (var6 != null) {
               StringBuffer var9 = new StringBuffer(var6.length() + 2);
               var9.append(var6).append("]>");
               var10 = new XMLInputSource((String)null, var5, (String)null, new StringReader(var9.toString()), (String)null);
               this.fEntityManager.startDocumentEntity(var10);
               this.fDTDScanner.scanDTDInternalSubset(true, false, var4 != null);
            }

            if (var4 != null) {
               XMLDTDDescription var17 = new XMLDTDDescription(var3, var4, var5, (String)null, var2);
               var10 = this.fEntityManager.resolveEntity(var17);
               this.fDTDScanner.setInputSource(var10);
               this.fDTDScanner.scanDTDExternalSubset(true);
            }
         } catch (EOFException var15) {
         } finally {
            this.fEntityManager.closeReaders();
         }
      }

   }

   protected void reset() {
      super.reset();
      this.fDTDScanner.reset();
      this.fEntityManager.reset();
      this.fErrorReporter.setDocumentLocator(this.fEntityManager.getEntityScanner());
   }

   protected XMLDTDScannerImpl createDTDScanner(SymbolTable var1, XMLErrorReporter var2, XMLEntityManager var3) {
      return new XMLDTDScannerImpl(var1, var2, var3);
   }

   protected short getScannerVersion() {
      return 1;
   }
}
