package weblogic.apache.xerces.jaxp.validation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import weblogic.apache.xerces.impl.XMLEntityManager;
import weblogic.apache.xerces.impl.XMLErrorReporter;
import weblogic.apache.xerces.impl.validation.ValidationManager;
import weblogic.apache.xerces.impl.xs.XMLSchemaValidator;
import weblogic.apache.xerces.impl.xs.XSMessageFormatter;
import weblogic.apache.xerces.util.DOMEntityResolverWrapper;
import weblogic.apache.xerces.util.ErrorHandlerWrapper;
import weblogic.apache.xerces.util.NamespaceSupport;
import weblogic.apache.xerces.util.ParserConfigurationSettings;
import weblogic.apache.xerces.util.SecurityManager;
import weblogic.apache.xerces.util.SymbolTable;
import weblogic.apache.xerces.xni.NamespaceContext;
import weblogic.apache.xerces.xni.XNIException;
import weblogic.apache.xerces.xni.parser.XMLComponent;
import weblogic.apache.xerces.xni.parser.XMLComponentManager;
import weblogic.apache.xerces.xni.parser.XMLConfigurationException;

final class XMLSchemaValidatorComponentManager extends ParserConfigurationSettings implements XMLComponentManager {
   private static final String SCHEMA_VALIDATION = "http://apache.org/xml/features/validation/schema";
   private static final String VALIDATION = "http://xml.org/sax/features/validation";
   private static final String USE_GRAMMAR_POOL_ONLY = "http://apache.org/xml/features/internal/validation/schema/use-grammar-pool-only";
   private static final String IGNORE_XSI_TYPE = "http://apache.org/xml/features/validation/schema/ignore-xsi-type-until-elemdecl";
   private static final String ID_IDREF_CHECKING = "http://apache.org/xml/features/validation/id-idref-checking";
   private static final String UNPARSED_ENTITY_CHECKING = "http://apache.org/xml/features/validation/unparsed-entity-checking";
   private static final String IDENTITY_CONSTRAINT_CHECKING = "http://apache.org/xml/features/validation/identity-constraint-checking";
   private static final String DISALLOW_DOCTYPE_DECL_FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
   private static final String NORMALIZE_DATA = "http://apache.org/xml/features/validation/schema/normalized-value";
   private static final String SCHEMA_ELEMENT_DEFAULT = "http://apache.org/xml/features/validation/schema/element-default";
   private static final String SCHEMA_AUGMENT_PSVI = "http://apache.org/xml/features/validation/schema/augment-psvi";
   private static final String ENTITY_MANAGER = "http://apache.org/xml/properties/internal/entity-manager";
   private static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
   private static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
   private static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
   private static final String NAMESPACE_CONTEXT = "http://apache.org/xml/properties/internal/namespace-context";
   private static final String SCHEMA_VALIDATOR = "http://apache.org/xml/properties/internal/validator/schema";
   private static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
   private static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
   private static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
   private static final String XMLGRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
   private static final String LOCALE = "http://apache.org/xml/properties/locale";
   private boolean fConfigUpdated = true;
   private boolean fUseGrammarPoolOnly;
   private final HashMap fComponents = new HashMap();
   private final XMLEntityManager fEntityManager = new XMLEntityManager();
   private final XMLErrorReporter fErrorReporter;
   private final NamespaceContext fNamespaceContext;
   private final XMLSchemaValidator fSchemaValidator;
   private final ValidationManager fValidationManager;
   private final HashMap fInitFeatures = new HashMap();
   private final HashMap fInitProperties = new HashMap();
   private final SecurityManager fInitSecurityManager;
   private ErrorHandler fErrorHandler = null;
   private LSResourceResolver fResourceResolver = null;
   private Locale fLocale = null;

   public XMLSchemaValidatorComponentManager(XSGrammarPoolContainer var1) {
      this.fComponents.put("http://apache.org/xml/properties/internal/entity-manager", this.fEntityManager);
      this.fErrorReporter = new XMLErrorReporter();
      this.fComponents.put("http://apache.org/xml/properties/internal/error-reporter", this.fErrorReporter);
      this.fNamespaceContext = new NamespaceSupport();
      this.fComponents.put("http://apache.org/xml/properties/internal/namespace-context", this.fNamespaceContext);
      this.fSchemaValidator = new XMLSchemaValidator();
      this.fComponents.put("http://apache.org/xml/properties/internal/validator/schema", this.fSchemaValidator);
      this.fValidationManager = new ValidationManager();
      this.fComponents.put("http://apache.org/xml/properties/internal/validation-manager", this.fValidationManager);
      this.fComponents.put("http://apache.org/xml/properties/internal/entity-resolver", (Object)null);
      this.fComponents.put("http://apache.org/xml/properties/internal/error-handler", (Object)null);
      this.fComponents.put("http://apache.org/xml/properties/security-manager", (Object)null);
      this.fComponents.put("http://apache.org/xml/properties/internal/symbol-table", new SymbolTable());
      this.fComponents.put("http://apache.org/xml/properties/internal/grammar-pool", var1.getGrammarPool());
      this.fUseGrammarPoolOnly = var1.isFullyComposed();
      this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/xml-schema-1", new XSMessageFormatter());
      String[] var2 = new String[]{"http://apache.org/xml/features/disallow-doctype-decl", "http://apache.org/xml/features/validation/schema/normalized-value", "http://apache.org/xml/features/validation/schema/element-default", "http://apache.org/xml/features/validation/schema/augment-psvi"};
      this.addRecognizedFeatures(var2);
      this.fFeatures.put("http://apache.org/xml/features/disallow-doctype-decl", Boolean.FALSE);
      this.fFeatures.put("http://apache.org/xml/features/validation/schema/normalized-value", Boolean.FALSE);
      this.fFeatures.put("http://apache.org/xml/features/validation/schema/element-default", Boolean.FALSE);
      this.fFeatures.put("http://apache.org/xml/features/validation/schema/augment-psvi", Boolean.TRUE);
      this.addRecognizedParamsAndSetDefaults(this.fEntityManager, var1);
      this.addRecognizedParamsAndSetDefaults(this.fErrorReporter, var1);
      this.addRecognizedParamsAndSetDefaults(this.fSchemaValidator, var1);
      Boolean var3 = var1.getFeature("http://javax.xml.XMLConstants/feature/secure-processing");
      if (Boolean.TRUE.equals(var3)) {
         this.fInitSecurityManager = new SecurityManager();
      } else {
         this.fInitSecurityManager = null;
      }

      this.fComponents.put("http://apache.org/xml/properties/security-manager", this.fInitSecurityManager);
      this.fFeatures.put("http://apache.org/xml/features/validation/schema/ignore-xsi-type-until-elemdecl", Boolean.FALSE);
      this.fFeatures.put("http://apache.org/xml/features/validation/id-idref-checking", Boolean.TRUE);
      this.fFeatures.put("http://apache.org/xml/features/validation/identity-constraint-checking", Boolean.TRUE);
      this.fFeatures.put("http://apache.org/xml/features/validation/unparsed-entity-checking", Boolean.TRUE);
   }

   public boolean getFeature(String var1) throws XMLConfigurationException {
      if ("http://apache.org/xml/features/internal/parser-settings".equals(var1)) {
         return this.fConfigUpdated;
      } else if (!"http://xml.org/sax/features/validation".equals(var1) && !"http://apache.org/xml/features/validation/schema".equals(var1)) {
         if ("http://apache.org/xml/features/internal/validation/schema/use-grammar-pool-only".equals(var1)) {
            return this.fUseGrammarPoolOnly;
         } else if ("http://javax.xml.XMLConstants/feature/secure-processing".equals(var1)) {
            return this.getProperty("http://apache.org/xml/properties/security-manager") != null;
         } else {
            return super.getFeature(var1);
         }
      } else {
         return true;
      }
   }

   public void setFeature(String var1, boolean var2) throws XMLConfigurationException {
      if ("http://apache.org/xml/features/internal/parser-settings".equals(var1)) {
         throw new XMLConfigurationException((short)1, var1);
      } else if (!var2 && ("http://xml.org/sax/features/validation".equals(var1) || "http://apache.org/xml/features/validation/schema".equals(var1))) {
         throw new XMLConfigurationException((short)1, var1);
      } else if ("http://apache.org/xml/features/internal/validation/schema/use-grammar-pool-only".equals(var1) && var2 != this.fUseGrammarPoolOnly) {
         throw new XMLConfigurationException((short)1, var1);
      } else if ("http://javax.xml.XMLConstants/feature/secure-processing".equals(var1)) {
         this.setProperty("http://apache.org/xml/properties/security-manager", var2 ? new SecurityManager() : null);
      } else {
         this.fConfigUpdated = true;
         this.fEntityManager.setFeature(var1, var2);
         this.fErrorReporter.setFeature(var1, var2);
         this.fSchemaValidator.setFeature(var1, var2);
         if (!this.fInitFeatures.containsKey(var1)) {
            boolean var3 = super.getFeature(var1);
            this.fInitFeatures.put(var1, var3 ? Boolean.TRUE : Boolean.FALSE);
         }

         super.setFeature(var1, var2);
      }
   }

   public Object getProperty(String var1) throws XMLConfigurationException {
      if ("http://apache.org/xml/properties/locale".equals(var1)) {
         return this.getLocale();
      } else {
         Object var2 = this.fComponents.get(var1);
         if (var2 != null) {
            return var2;
         } else {
            return this.fComponents.containsKey(var1) ? null : super.getProperty(var1);
         }
      }
   }

   public void setProperty(String var1, Object var2) throws XMLConfigurationException {
      if (!"http://apache.org/xml/properties/internal/entity-manager".equals(var1) && !"http://apache.org/xml/properties/internal/error-reporter".equals(var1) && !"http://apache.org/xml/properties/internal/namespace-context".equals(var1) && !"http://apache.org/xml/properties/internal/validator/schema".equals(var1) && !"http://apache.org/xml/properties/internal/symbol-table".equals(var1) && !"http://apache.org/xml/properties/internal/validation-manager".equals(var1) && !"http://apache.org/xml/properties/internal/grammar-pool".equals(var1)) {
         this.fConfigUpdated = true;
         this.fEntityManager.setProperty(var1, var2);
         this.fErrorReporter.setProperty(var1, var2);
         this.fSchemaValidator.setProperty(var1, var2);
         if (!"http://apache.org/xml/properties/internal/entity-resolver".equals(var1) && !"http://apache.org/xml/properties/internal/error-handler".equals(var1) && !"http://apache.org/xml/properties/security-manager".equals(var1)) {
            if ("http://apache.org/xml/properties/locale".equals(var1)) {
               this.setLocale((Locale)var2);
               this.fComponents.put(var1, var2);
            } else {
               if (!this.fInitProperties.containsKey(var1)) {
                  this.fInitProperties.put(var1, super.getProperty(var1));
               }

               super.setProperty(var1, var2);
            }
         } else {
            this.fComponents.put(var1, var2);
         }
      } else {
         throw new XMLConfigurationException((short)1, var1);
      }
   }

   public void addRecognizedParamsAndSetDefaults(XMLComponent var1, XSGrammarPoolContainer var2) {
      String[] var3 = var1.getRecognizedFeatures();
      this.addRecognizedFeatures(var3);
      String[] var4 = var1.getRecognizedProperties();
      this.addRecognizedProperties(var4);
      this.setFeatureDefaults(var1, var3, var2);
      this.setPropertyDefaults(var1, var4);
   }

   public void reset() throws XNIException {
      this.fNamespaceContext.reset();
      this.fValidationManager.reset();
      this.fEntityManager.reset(this);
      this.fErrorReporter.reset(this);
      this.fSchemaValidator.reset(this);
      this.fConfigUpdated = false;
   }

   void setErrorHandler(ErrorHandler var1) {
      this.fErrorHandler = var1;
      this.setProperty("http://apache.org/xml/properties/internal/error-handler", var1 != null ? new ErrorHandlerWrapper(var1) : new ErrorHandlerWrapper(DraconianErrorHandler.getInstance()));
   }

   ErrorHandler getErrorHandler() {
      return this.fErrorHandler;
   }

   void setResourceResolver(LSResourceResolver var1) {
      this.fResourceResolver = var1;
      this.setProperty("http://apache.org/xml/properties/internal/entity-resolver", new DOMEntityResolverWrapper(var1));
   }

   LSResourceResolver getResourceResolver() {
      return this.fResourceResolver;
   }

   void setLocale(Locale var1) {
      this.fLocale = var1;
      this.fErrorReporter.setLocale(var1);
   }

   Locale getLocale() {
      return this.fLocale;
   }

   void restoreInitialState() {
      this.fConfigUpdated = true;
      this.fComponents.put("http://apache.org/xml/properties/internal/entity-resolver", (Object)null);
      this.fComponents.put("http://apache.org/xml/properties/internal/error-handler", (Object)null);
      this.fComponents.put("http://apache.org/xml/properties/security-manager", this.fInitSecurityManager);
      this.setLocale((Locale)null);
      this.fComponents.put("http://apache.org/xml/properties/locale", (Object)null);
      Iterator var1;
      Map.Entry var2;
      String var3;
      if (!this.fInitFeatures.isEmpty()) {
         var1 = this.fInitFeatures.entrySet().iterator();

         while(var1.hasNext()) {
            var2 = (Map.Entry)var1.next();
            var3 = (String)var2.getKey();
            boolean var4 = (Boolean)var2.getValue();
            super.setFeature(var3, var4);
         }

         this.fInitFeatures.clear();
      }

      if (!this.fInitProperties.isEmpty()) {
         var1 = this.fInitProperties.entrySet().iterator();

         while(var1.hasNext()) {
            var2 = (Map.Entry)var1.next();
            var3 = (String)var2.getKey();
            Object var5 = var2.getValue();
            super.setProperty(var3, var5);
         }

         this.fInitProperties.clear();
      }

   }

   private void setFeatureDefaults(XMLComponent var1, String[] var2, XSGrammarPoolContainer var3) {
      if (var2 != null) {
         for(int var4 = 0; var4 < var2.length; ++var4) {
            String var5 = var2[var4];
            Boolean var6 = var3.getFeature(var5);
            if (var6 == null) {
               var6 = var1.getFeatureDefault(var5);
            }

            if (var6 != null && !this.fFeatures.containsKey(var5)) {
               this.fFeatures.put(var5, var6);
               this.fConfigUpdated = true;
            }
         }
      }

   }

   private void setPropertyDefaults(XMLComponent var1, String[] var2) {
      if (var2 != null) {
         for(int var3 = 0; var3 < var2.length; ++var3) {
            String var4 = var2[var3];
            Object var5 = var1.getPropertyDefault(var4);
            if (var5 != null && !this.fProperties.containsKey(var4)) {
               this.fProperties.put(var4, var5);
               this.fConfigUpdated = true;
            }
         }
      }

   }
}
