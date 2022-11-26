package com.bea.staxb.buildtime.annotations;

import java.lang.annotation.Target;

@Target({})
public @interface TargetSchemaProperty {
   String localName() default "";

   String namespaceUri() default "";

   boolean isAttribute() default false;

   boolean isRequired() default false;

   boolean isNillable() default false;

   boolean isFormQualified() default false;

   String defaultValue() default "";

   TargetFacet[] facets() default {};
}
