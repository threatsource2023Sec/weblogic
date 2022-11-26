package weblogic.ejbgen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface FileGeneration {
   Constants.Bool pkClass() default Constants.Bool.TRUE;

   String remoteClassName() default "UNSPECIFIED";

   String remotePackage() default "UNSPECIFIED";

   Constants.Bool valueClass() default Constants.Bool.UNSPECIFIED;

   String valueClassName() default "UNSPECIFIED";

   String localClassName() default "UNSPECIFIED";

   String localPackage() default "UNSPECIFIED";

   Constants.Bool remoteHome() default Constants.Bool.UNSPECIFIED;

   String localHomePackage() default "UNSPECIFIED";

   String remoteHomePackage() default "UNSPECIFIED";

   Constants.Bool remoteClass() default Constants.Bool.UNSPECIFIED;

   String localHomeName() default "UNSPECIFIED";

   Constants.Bool localHome() default Constants.Bool.UNSPECIFIED;

   String remoteHomeName() default "UNSPECIFIED";

   Constants.Bool localClass() default Constants.Bool.UNSPECIFIED;
}
