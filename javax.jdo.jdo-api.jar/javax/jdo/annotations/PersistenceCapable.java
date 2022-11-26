package javax.jdo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PersistenceCapable {
   Persistent[] members() default {};

   String table() default "";

   String catalog() default "";

   String schema() default "";

   String requiresExtent() default "";

   String embeddedOnly() default "";

   String detachable() default "";

   IdentityType identityType() default IdentityType.UNSPECIFIED;

   Class objectIdClass() default void.class;

   String cacheable() default "true";

   String serializeRead() default "false";

   Extension[] extensions() default {};
}
