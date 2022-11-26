package com.bea.xml_.impl.jam.mutable;

import com.bea.xml_.impl.jam.JAnnotatedElement;

public interface MAnnotatedElement extends MElement, JAnnotatedElement {
   MAnnotation findOrCreateAnnotation(String var1);

   MAnnotation[] getMutableAnnotations();

   MAnnotation getMutableAnnotation(String var1);

   MAnnotation addLiteralAnnotation(String var1);

   MComment getMutableComment();

   MComment createComment();

   void removeComment();
}
