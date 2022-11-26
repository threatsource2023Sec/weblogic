package weblogic.ejbgen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD})
public @interface Select {
   String cachingName() default "UNSPECIFIED";

   String groupName() default "UNSPECIFIED";

   String maxElements() default "UNSPECIFIED";

   Constants.Bool sqlSelectDistinct() default Constants.Bool.UNSPECIFIED;

   Constants.Bool includeUpdates() default Constants.Bool.UNSPECIFIED;

   String orderingNumber() default "UNSPECIFIED";

   String ejbQl() default "UNSPECIFIED";

   String weblogicEjbQl() default "UNSPECIFIED";

   ResultTypeMapping resultTypeMapping() default Select.ResultTypeMapping.UNSPECIFIED;

   public static enum ResultTypeMapping {
      UNSPECIFIED,
      LOCAL,
      REMOTE;
   }
}
