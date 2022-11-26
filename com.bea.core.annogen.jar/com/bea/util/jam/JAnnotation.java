package com.bea.util.jam;

public interface JAnnotation extends JElement {
   String SINGLE_VALUE_NAME = "value";

   String getSimpleName();

   JAnnotationValue[] getValues();

   JAnnotationValue getValue(String var1);

   String getJavadocTagText();

   Object getAnnotationInstance();
}
