package com.bea.xml_.impl.jam;

public interface JAnnotatedElement extends JElement {
   JAnnotation[] getAnnotations();

   JAnnotation getAnnotation(Class var1);

   /** @deprecated */
   Object getAnnotationProxy(Class var1);

   JAnnotation getAnnotation(String var1);

   JAnnotationValue getAnnotationValue(String var1);

   JComment getComment();

   /** @deprecated */
   JAnnotation[] getAllJavadocTags();
}
