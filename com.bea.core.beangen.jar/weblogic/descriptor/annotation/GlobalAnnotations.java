package weblogic.descriptor.annotation;

public class GlobalAnnotations {
   public static final AnnotationDefinition EXCLUDE = new AnnotationDefinition(new String[]{"exclude"}, 1);
   public static final AnnotationDefinition INTERNAL = new AnnotationDefinition(new String[]{"internal"}, 1);
   public static final AnnotationDefinition DYNAMIC = new AnnotationDefinition(new String[]{"dynamic"}, 3);
   public static final AnnotationDefinition CONFIGURABLE = new AnnotationDefinition(new String[]{"configurable"}, 3);
   public static final AnnotationDefinition OBSOLETE = new AnnotationDefinition(new String[]{"obsolete"}, 3);
   public static final AnnotationDefinition ROLE_ALLOWED = new AnnotationDefinition(new String[]{"roleAllowed"}, 3);
   public static final AnnotationDefinition ROLE_EXCLUDED = new AnnotationDefinition(new String[]{"roleExcluded"}, 3);
   public static final AnnotationDefinition ROLE_PERMIT_ALL = new AnnotationDefinition(new String[]{"rolePermitAll"}, 3);
   public static final AnnotationDefinition HARVESTABLE = new AnnotationDefinition("harvestable");
   public static final AnnotationDefinition VISIBLE_TO_PARTITION = new AnnotationDefinition(new String[]{"VisibleToPartitions"}, 3);
   public static final AnnotationDefinition OWNER = new AnnotationDefinition(new String[]{"owner"}, 3);
   public static final AnnotationDefinition RESTARTS = new AnnotationDefinition(new String[]{"restarts"}, 3);
}
