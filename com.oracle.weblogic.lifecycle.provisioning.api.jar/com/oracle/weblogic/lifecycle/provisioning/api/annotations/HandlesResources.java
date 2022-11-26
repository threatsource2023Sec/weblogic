package com.oracle.weblogic.lifecycle.provisioning.api.annotations;

import com.oracle.weblogic.lifecycle.provisioning.api.URIEndsWithSelector;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface HandlesResources {
   String[] value();

   boolean enabled() default true;

   Class selectorClass() default URIEndsWithSelector.class;
}
