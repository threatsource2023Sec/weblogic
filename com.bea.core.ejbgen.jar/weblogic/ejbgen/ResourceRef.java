package weblogic.ejbgen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface ResourceRef {
   String jndiName();

   String type();

   Auth auth();

   String name();

   SharingScope sharingScope() default ResourceRef.SharingScope.UNSPECIFIED;

   String id() default "UNSPECIFIED";

   public static enum SharingScope {
      UNSPECIFIED,
      SHAREABLE,
      UNSHAREABLE;
   }

   public static enum Auth {
      UNSPECIFIED,
      APPLICATION,
      CONTAINER;
   }
}
