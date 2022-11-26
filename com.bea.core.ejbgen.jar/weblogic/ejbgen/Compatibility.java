package weblogic.ejbgen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface Compatibility {
   Constants.Bool serializeCharArrayToBytes() default Constants.Bool.UNSPECIFIED;

   Constants.Bool disableStringTrimming() default Constants.Bool.UNSPECIFIED;

   Constants.Bool serializeByteArrayToOracleBlob() default Constants.Bool.UNSPECIFIED;

   Constants.Bool allowReadonlyCreateAndRemove() default Constants.Bool.UNSPECIFIED;

   Constants.Bool findersReturnNulls() default Constants.Bool.UNSPECIFIED;
}
