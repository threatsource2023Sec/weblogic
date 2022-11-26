package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.jmx.support.RegistrationPolicy;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({MBeanExportConfiguration.class})
public @interface EnableMBeanExport {
   String defaultDomain() default "";

   String server() default "";

   RegistrationPolicy registration() default RegistrationPolicy.FAIL_ON_EXISTING;
}
