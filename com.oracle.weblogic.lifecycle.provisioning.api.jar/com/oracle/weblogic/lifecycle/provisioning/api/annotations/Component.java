package com.oracle.weblogic.lifecycle.provisioning.api.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;
import org.glassfish.hk2.api.Metadata;

@Documented
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Component {
   @Metadata("componentName")
   String value();

   @Metadata("affiliates")
   String[] affiliates() default {};
}
