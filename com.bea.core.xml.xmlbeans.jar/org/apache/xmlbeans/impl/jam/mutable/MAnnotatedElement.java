package org.apache.xmlbeans.impl.jam.mutable;

import org.apache.xmlbeans.impl.jam.JAnnotatedElement;

public interface MAnnotatedElement extends MElement, JAnnotatedElement {
   MAnnotation findOrCreateAnnotation(String var1);

   MAnnotation[] getMutableAnnotations();

   MAnnotation getMutableAnnotation(String var1);

   MAnnotation addLiteralAnnotation(String var1);

   MComment getMutableComment();

   MComment createComment();

   void removeComment();
}
