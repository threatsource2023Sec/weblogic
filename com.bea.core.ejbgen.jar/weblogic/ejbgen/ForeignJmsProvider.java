package weblogic.ejbgen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface ForeignJmsProvider {
   String connectionFactoryJndiName() default "UNSPECIFIED";

   String connectionFactoryResourceLink() default "UNSPECIFIED";

   String providerUrl() default "UNSPECIFIED";

   String initialContextFactory() default "UNSPECIFIED";
}
