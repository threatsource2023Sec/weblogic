package com.bea.xml_.impl.jam;

public interface JAnnotation extends JElement {
   String SINGLE_VALUE_NAME = "value";

   String getSimpleName();

   Object getProxy();

   JAnnotationValue[] getValues();

   JAnnotationValue getValue(String var1);

   Object getAnnotationInstance();
}
