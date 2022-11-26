package com.bea.util.jam.mutable;

import com.bea.util.jam.JAnnotation;
import com.bea.util.jam.JClass;

public interface MAnnotation extends JAnnotation, MElement {
   void setAnnotationInstance(Object var1);

   void setSimpleValue(String var1, Object var2, JClass var3);

   MAnnotation createNestedValue(String var1, String var2);

   MAnnotation[] createNestedValueArray(String var1, String var2, int var3);
}
