package weblogic.descriptor.annotation;

public class MethodAnnotations extends GlobalAnnotations {
   public static final AnnotationDefinition OPERATION = new AnnotationDefinition("operation", "wld:operation");
   public static final AnnotationDefinition IMPACT = new AnnotationDefinition("impact");
   public static final AnnotationDefinition ALLOW_SECURITY_OPERATIONS = new AnnotationDefinition("allowSecurityOperations");
}
