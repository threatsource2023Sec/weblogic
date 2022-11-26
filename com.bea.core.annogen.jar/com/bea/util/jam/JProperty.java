package com.bea.util.jam;

public interface JProperty extends JAnnotatedElement {
   JClass getType();

   JMethod getSetter();

   JMethod getGetter();
}
