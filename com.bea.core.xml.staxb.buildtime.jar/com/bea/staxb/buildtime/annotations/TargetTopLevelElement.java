package com.bea.staxb.buildtime.annotations;

import java.lang.annotation.Target;

@Target({})
public @interface TargetTopLevelElement {
   String localName();

   String namespaceUri();
}
