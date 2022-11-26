package com.bea.xml_.impl.jam;

public interface JProperty extends JAnnotatedElement {
   JClass getType();

   JMethod getSetter();

   JMethod getGetter();
}
