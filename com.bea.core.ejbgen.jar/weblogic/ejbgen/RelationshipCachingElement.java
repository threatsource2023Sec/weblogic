package weblogic.ejbgen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface RelationshipCachingElement {
   String cachingName();

   String cmrField();

   String groupName() default "UNSPECIFIED";

   String parentId() default "UNSPECIFIED";

   String id() default "UNSPECIFIED";
}
