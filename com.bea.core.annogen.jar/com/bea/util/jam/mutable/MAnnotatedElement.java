package com.bea.util.jam.mutable;

import com.bea.util.jam.JAnnotatedElement;

public interface MAnnotatedElement extends MElement, JAnnotatedElement {
   MAnnotation findOrCreateAnnotation(String var1);

   MAnnotation[] getMutableAnnotations();

   MAnnotation getMutableAnnotation(String var1);

   MAnnotation addLiteralAnnotation(String var1);

   MComment getMutableComment();

   MComment createComment();

   void removeComment();
}
