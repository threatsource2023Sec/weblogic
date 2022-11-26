package weblogic.descriptor.annotation;

import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.Map;

public class PropertyAnnotations extends GlobalAnnotations {
   private static Map allAnotations = null;
   public static final AnnotationDefinition AGGREGATE = new AnnotationDefinition("aggregate", "wld:property@aggregate");
   public static final AnnotationDefinition COMPONENT_NAME = new AnnotationDefinition("componentName", "wld:property@componentName");
   public static final AnnotationDefinition DEFAULT = new AnnotationDefinition("default", "wld:property@default");
   public static final AnnotationDefinition DERIVED_DEFAULT = new AnnotationDefinition("derivedDefault", "wld:property@derivedDefault");
   public static final AnnotationDefinition PRODUCTION_DEFAULT = new AnnotationDefinition("productionDefault", "wld:property@productionDefault", "production-mode-default");
   public static final AnnotationDefinition INITIALIZER = new AnnotationDefinition("initializer", "wld:property@initializer");
   public static final AnnotationDefinition VALIDATOR = new AnnotationDefinition("validator", "wld:property@validator");
   public static final AnnotationDefinition DEFERRED_VALIDATOR = new AnnotationDefinition("deferredValidator", "wld:property@deferredValidator");
   public static final AnnotationDefinition LEGAL_MAX = new AnnotationDefinition("legalMax", "wld:property@max");
   public static final AnnotationDefinition LEGAL_MIN = new AnnotationDefinition("legalMin", "wld:property@min");
   public static final AnnotationDefinition LEGAL_ZERO_LENGTH = new AnnotationDefinition.LegalZeroLengthStringAnnotation(new String[]{"legalZeroLength", "wld:property@zeroLength"}, new String[]{"StringPropertyType"});
   public static final AnnotationDefinition NULLABLE = new AnnotationDefinition.LegalNullStringAnnotation(new String[]{"legalNull", "wld:property@nullable"}, new String[]{"StringPropertyType"});
   public static final AnnotationDefinition ENUMERATION = new AnnotationDefinition.EnumerationAnnotation(new String[]{"enumeration", "wld:property@enumeration", "legalValues"}, new String[]{"IntPropertyType", "StringPropertyType"});
   public static final AnnotationDefinition PRESERVE_WHITE_SPACE = new AnnotationDefinition("preserveWhiteSpace", "wld:property@preserveWhiteSpace");
   public static final AnnotationDefinition REQUIRED = new AnnotationDefinition("required", "wld:property@required");
   public static final AnnotationDefinition KEY = new AnnotationDefinition("key", "wld:property@key");
   public static final AnnotationDefinition KEY_CHOICE = new AnnotationDefinition("keyChoice", "wld:property@keyChoice");
   public static final AnnotationDefinition KEY_COMPONENT = new AnnotationDefinition("keyComponent", "wld:property@keyComponent");
   public static final AnnotationDefinition REFERENCE = new AnnotationDefinition("reference", "wld:property@reference");
   public static final AnnotationDefinition TRANSIENT = new AnnotationDefinition(new String[]{"transient", "wld:property@transient", "non-configurable"});
   public static final AnnotationDefinition ENCRYPTED = new AnnotationDefinition(new String[]{"encrypted", "wld:property@encrypted"}, new String[]{"StringPropertyType"});
   /** @deprecated */
   @Deprecated
   public static final AnnotationDefinition ALLOWS_SUBTYPES = new AnnotationDefinition("allowsSubTypes", "wld:property@allowsSubTypes");
   public static final AnnotationDefinition SECURE_VALUE = new AnnotationDefinition("secureValue", "wld:property@secureValue");
   public static final AnnotationDefinition SECURE_VALUE_DOC_ONLY = new AnnotationDefinition("secureValueDocOnly", "wld:property@secureValueDocOnly");
   public static final AnnotationDefinition XML_ELEMENT_NAME = new AnnotationDefinition("xmlElementName", "elementName", "wld:property@xmlElementName");
   /** @deprecated */
   @Deprecated
   public static final AnnotationDefinition NON_DYNAMIC = new AnnotationDefinition("non-dynamic");
   public static final AnnotationDefinition SINGLETON = new AnnotationDefinition("mergeAsSingleton", "singleton", "wld:property@mergeAsSingleton");
   public static final AnnotationDefinition PREPEND = new AnnotationDefinition("mergePrepends", "prepend", "wld:property@mergePrepends");
   public static final AnnotationDefinition IGNORE = new AnnotationDefinition("mergeIgnores", "ignore", "wld:property@mergeIgnores");
   public static final AnnotationDefinition ATTRIBUTE = new AnnotationDefinition("xmlAttribute", "attribute", "wld:property@xmlAttribute");
   public static final AnnotationDefinition REMOVE_VALIDATOR = new AnnotationDefinition(new String[]{"removeValidator", "wld:property@removeValidator"}, new String[]{"ChildPropertyType", "ReferencePropertyType"});
   /** @deprecated */
   @Deprecated
   public static final AnnotationDefinition VALIDATE_PROPERTY_DECLARATION = new AnnotationDefinition(new String[]{"validatePropertyDeclaration", "wld:property@validatePropertyDeclaration"});
   public static final AnnotationDefinition UN_SET_VALUE = new AnnotationDefinition.UnSetValueAnnotation(new String[]{"unSetValue", "wld:property@unSetValue"}, new String[]{"StringPropertyType", "IntPropertyType"});
   public static final AnnotationDefinition PREPROCESSOR = new AnnotationDefinition(new String[]{"preprocessor", "wld:property@preprocessor"}, new String[]{"StringPropertyType", "IntPropertyType"});
   public static final AnnotationDefinition MERGERULE = new AnnotationDefinition(new String[]{"mergeRule"}, new String[]{"StringPropertyType", "ChildPropertyType"});
   public static final AnnotationDefinition ARRAYORDERSENSITIVE = new AnnotationDefinition("arrayOrderSensitive", "wld:property@arrayOrderSensitive");
   /** @deprecated */
   @Deprecated
   public static final AnnotationDefinition META_DATA = new AnnotationDefinition(new String[]{"MetaData"});

   public static synchronized AnnotationDefinition getAnnotationDefinition(String alias) {
      if (allAnotations == null) {
         allAnotations = new Hashtable();
         Field[] annotations = PropertyAnnotations.class.getFields();

         for(int count = 0; count < annotations.length; ++count) {
            if (annotations[count].getType().equals(AnnotationDefinition.class)) {
               try {
                  AnnotationDefinition annotationDef = (AnnotationDefinition)annotations[count].get(PropertyAnnotations.class);
                  String[] aliases = annotationDef.getAliases();

                  for(int i = 0; i < aliases.length; ++i) {
                     allAnotations.put(aliases[i], annotationDef);
                  }
               } catch (IllegalAccessException var6) {
               }
            }
         }
      }

      return (AnnotationDefinition)allAnotations.get(alias);
   }
}
