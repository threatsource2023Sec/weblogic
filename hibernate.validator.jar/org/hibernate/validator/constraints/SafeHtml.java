package org.hibernate.validator.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;

@Documented
@Constraint(
   validatedBy = {}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(List.class)
public @interface SafeHtml {
   String message() default "{org.hibernate.validator.constraints.SafeHtml.message}";

   Class[] groups() default {};

   Class[] payload() default {};

   WhiteListType whitelistType() default SafeHtml.WhiteListType.RELAXED;

   String[] additionalTags() default {};

   Tag[] additionalTagsWithAttributes() default {};

   String baseURI() default "";

   public static enum WhiteListType {
      NONE,
      SIMPLE_TEXT,
      BASIC,
      BASIC_WITH_IMAGES,
      RELAXED;
   }

   @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
   @Retention(RetentionPolicy.RUNTIME)
   @Documented
   public @interface List {
      SafeHtml[] value();
   }

   @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
   @Retention(RetentionPolicy.RUNTIME)
   @Documented
   public @interface Attribute {
      String name();

      String[] protocols();
   }

   @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
   @Retention(RetentionPolicy.RUNTIME)
   @Documented
   public @interface Tag {
      String name();

      String[] attributes() default {};

      Attribute[] attributesWithProtocols() default {};
   }
}
