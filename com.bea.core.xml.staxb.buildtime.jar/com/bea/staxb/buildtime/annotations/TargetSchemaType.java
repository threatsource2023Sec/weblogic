package com.bea.staxb.buildtime.annotations;

import java.lang.annotation.Target;

@Target({})
public @interface TargetSchemaType {
   String localName();

   String namespaceUri();

   TargetTopLevelElement[] topLevelElements();
}
