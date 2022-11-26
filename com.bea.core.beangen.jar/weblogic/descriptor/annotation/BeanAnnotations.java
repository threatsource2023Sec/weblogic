package weblogic.descriptor.annotation;

public class BeanAnnotations extends GlobalAnnotations {
   public static final AnnotationDefinition BEAN = new AnnotationDefinition("bean", "wld:bean");
   public static final AnnotationDefinition ABSTRACT = new AnnotationDefinition("abstract", "wld:bean@abstract");
   public static final AnnotationDefinition CUSTOMIZER_FACTORY = new AnnotationDefinition("customizerFactory", "wld:bean@customizerFactory");
   public static final AnnotationDefinition CUSTOMIZER = new AnnotationDefinition("customizer", "wld:bean@customizer");
   public static final AnnotationDefinition ALLOW_DIFF_WITH_SIBLING_CLASS = new AnnotationDefinition("allowDiffWithSiblingClass", "wld:bean@allowDiffWithSiblingClass");
   public static final AnnotationDefinition VALIDATOR = new AnnotationDefinition("validator", "wld:bean@validator");
   public static final AnnotationDefinition ROOT = new AnnotationDefinition("root", "wld:bean@root");
   public static final AnnotationDefinition BASE_INTERFACE = new AnnotationDefinition("baseInterface", "wld:bean@baseInterface");
   public static final AnnotationDefinition REFERENCEABLE = new AnnotationDefinition(new String[]{"referenceable", "wld:bean@referenceable"}, 2);
   public static final AnnotationDefinition XML_TYPE_NAME = new AnnotationDefinition(new String[]{"xmlTypeName", "wld:bean@xmlTypeName"});
   public static final AnnotationDefinition SCHEMA_LOCATION = new AnnotationDefinition("schemaLocation", "wld:bean@schemaLocation");
   public static final AnnotationDefinition TARGET_NAMESPACE = new AnnotationDefinition("targetNamespace", "wld:bean@targetNamespace");
   public static final AnnotationDefinition DELEGATE_BEAN = new AnnotationDefinition("delegateBean", "wld:bean@delegateBean");
   public static final AnnotationDefinition INHERIT_SUPER_CUSTOMIZER = new AnnotationDefinition("inheritSuperCustomizer", "wld:bean@inheritSuperCustomizer");
}
