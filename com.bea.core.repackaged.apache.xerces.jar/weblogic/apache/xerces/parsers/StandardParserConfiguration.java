package weblogic.apache.xerces.parsers;

import weblogic.apache.xerces.impl.xs.XMLSchemaValidator;
import weblogic.apache.xerces.impl.xs.XSMessageFormatter;
import weblogic.apache.xerces.util.SymbolTable;
import weblogic.apache.xerces.xni.grammars.XMLGrammarPool;
import weblogic.apache.xerces.xni.parser.XMLComponentManager;
import weblogic.apache.xerces.xni.parser.XMLConfigurationException;

public class StandardParserConfiguration extends DTDConfiguration {
   protected static final String NORMALIZE_DATA = "http://apache.org/xml/features/validation/schema/normalized-value";
   protected static final String SCHEMA_ELEMENT_DEFAULT = "http://apache.org/xml/features/validation/schema/element-default";
   protected static final String SCHEMA_AUGMENT_PSVI = "http://apache.org/xml/features/validation/schema/augment-psvi";
   protected static final String XMLSCHEMA_VALIDATION = "http://apache.org/xml/features/validation/schema";
   protected static final String XMLSCHEMA_FULL_CHECKING = "http://apache.org/xml/features/validation/schema-full-checking";
   protected static final String GENERATE_SYNTHETIC_ANNOTATIONS = "http://apache.org/xml/features/generate-synthetic-annotations";
   protected static final String VALIDATE_ANNOTATIONS = "http://apache.org/xml/features/validate-annotations";
   protected static final String HONOUR_ALL_SCHEMALOCATIONS = "http://apache.org/xml/features/honour-all-schemaLocations";
   protected static final String NAMESPACE_GROWTH = "http://apache.org/xml/features/namespace-growth";
   protected static final String TOLERATE_DUPLICATES = "http://apache.org/xml/features/internal/tolerate-duplicates";
   protected static final String IGNORE_XSI_TYPE = "http://apache.org/xml/features/validation/schema/ignore-xsi-type-until-elemdecl";
   protected static final String ID_IDREF_CHECKING = "http://apache.org/xml/features/validation/id-idref-checking";
   protected static final String UNPARSED_ENTITY_CHECKING = "http://apache.org/xml/features/validation/unparsed-entity-checking";
   protected static final String IDENTITY_CONSTRAINT_CHECKING = "http://apache.org/xml/features/validation/identity-constraint-checking";
   protected static final String SCHEMA_VALIDATOR = "http://apache.org/xml/properties/internal/validator/schema";
   protected static final String SCHEMA_LOCATION = "http://apache.org/xml/properties/schema/external-schemaLocation";
   protected static final String SCHEMA_NONS_LOCATION = "http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation";
   protected static final String ROOT_TYPE_DEF = "http://apache.org/xml/properties/validation/schema/root-type-definition";
   protected static final String ROOT_ELEMENT_DECL = "http://apache.org/xml/properties/validation/schema/root-element-declaration";
   protected static final String SCHEMA_DV_FACTORY = "http://apache.org/xml/properties/internal/validation/schema/dv-factory";
   protected XMLSchemaValidator fSchemaValidator;

   public StandardParserConfiguration() {
      this((SymbolTable)null, (XMLGrammarPool)null, (XMLComponentManager)null);
   }

   public StandardParserConfiguration(SymbolTable var1) {
      this(var1, (XMLGrammarPool)null, (XMLComponentManager)null);
   }

   public StandardParserConfiguration(SymbolTable var1, XMLGrammarPool var2) {
      this(var1, var2, (XMLComponentManager)null);
   }

   public StandardParserConfiguration(SymbolTable var1, XMLGrammarPool var2, XMLComponentManager var3) {
      super(var1, var2, var3);
      String[] var4 = new String[]{"http://apache.org/xml/features/validation/schema/normalized-value", "http://apache.org/xml/features/validation/schema/element-default", "http://apache.org/xml/features/validation/schema/augment-psvi", "http://apache.org/xml/features/generate-synthetic-annotations", "http://apache.org/xml/features/validate-annotations", "http://apache.org/xml/features/honour-all-schemaLocations", "http://apache.org/xml/features/namespace-growth", "http://apache.org/xml/features/internal/tolerate-duplicates", "http://apache.org/xml/features/validation/schema", "http://apache.org/xml/features/validation/schema-full-checking", "http://apache.org/xml/features/validation/schema/ignore-xsi-type-until-elemdecl", "http://apache.org/xml/features/validation/id-idref-checking", "http://apache.org/xml/features/validation/identity-constraint-checking", "http://apache.org/xml/features/validation/unparsed-entity-checking"};
      this.addRecognizedFeatures(var4);
      this.setFeature("http://apache.org/xml/features/validation/schema/element-default", true);
      this.setFeature("http://apache.org/xml/features/validation/schema/normalized-value", true);
      this.setFeature("http://apache.org/xml/features/validation/schema/augment-psvi", true);
      this.setFeature("http://apache.org/xml/features/generate-synthetic-annotations", false);
      this.setFeature("http://apache.org/xml/features/validate-annotations", false);
      this.setFeature("http://apache.org/xml/features/honour-all-schemaLocations", false);
      this.setFeature("http://apache.org/xml/features/namespace-growth", false);
      this.setFeature("http://apache.org/xml/features/internal/tolerate-duplicates", false);
      this.setFeature("http://apache.org/xml/features/validation/schema/ignore-xsi-type-until-elemdecl", false);
      this.setFeature("http://apache.org/xml/features/validation/id-idref-checking", true);
      this.setFeature("http://apache.org/xml/features/validation/identity-constraint-checking", true);
      this.setFeature("http://apache.org/xml/features/validation/unparsed-entity-checking", true);
      String[] var5 = new String[]{"http://apache.org/xml/properties/schema/external-schemaLocation", "http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation", "http://apache.org/xml/properties/validation/schema/root-type-definition", "http://apache.org/xml/properties/validation/schema/root-element-declaration", "http://apache.org/xml/properties/internal/validation/schema/dv-factory"};
      this.addRecognizedProperties(var5);
   }

   protected void configurePipeline() {
      super.configurePipeline();
      if (this.getFeature("http://apache.org/xml/features/validation/schema")) {
         if (this.fSchemaValidator == null) {
            this.fSchemaValidator = new XMLSchemaValidator();
            this.fProperties.put("http://apache.org/xml/properties/internal/validator/schema", this.fSchemaValidator);
            this.addComponent(this.fSchemaValidator);
            if (this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/xml-schema-1") == null) {
               XSMessageFormatter var1 = new XSMessageFormatter();
               this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/xml-schema-1", var1);
            }
         }

         this.fLastComponent = this.fSchemaValidator;
         this.fNamespaceBinder.setDocumentHandler(this.fSchemaValidator);
         this.fSchemaValidator.setDocumentHandler(this.fDocumentHandler);
         this.fSchemaValidator.setDocumentSource(this.fNamespaceBinder);
      }

   }

   protected void checkFeature(String var1) throws XMLConfigurationException {
      if (var1.startsWith("http://apache.org/xml/features/")) {
         int var2 = var1.length() - "http://apache.org/xml/features/".length();
         if (var2 == "validation/schema".length() && var1.endsWith("validation/schema")) {
            return;
         }

         if (var2 == "validation/schema-full-checking".length() && var1.endsWith("validation/schema-full-checking")) {
            return;
         }

         if (var2 == "validation/schema/normalized-value".length() && var1.endsWith("validation/schema/normalized-value")) {
            return;
         }

         if (var2 == "validation/schema/element-default".length() && var1.endsWith("validation/schema/element-default")) {
            return;
         }
      }

      super.checkFeature(var1);
   }

   protected void checkProperty(String var1) throws XMLConfigurationException {
      int var2;
      if (var1.startsWith("http://apache.org/xml/properties/")) {
         var2 = var1.length() - "http://apache.org/xml/properties/".length();
         if (var2 == "schema/external-schemaLocation".length() && var1.endsWith("schema/external-schemaLocation")) {
            return;
         }

         if (var2 == "schema/external-noNamespaceSchemaLocation".length() && var1.endsWith("schema/external-noNamespaceSchemaLocation")) {
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
}
