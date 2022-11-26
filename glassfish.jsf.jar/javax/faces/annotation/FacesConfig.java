package javax.faces.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

@Qualifier
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FacesConfig {
   @Nonbinding
   Version version() default FacesConfig.Version.JSF_2_3;

   public static enum Version {
      JSF_2_3;
   }
}
