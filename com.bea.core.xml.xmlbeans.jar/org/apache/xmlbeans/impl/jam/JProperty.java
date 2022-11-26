package org.apache.xmlbeans.impl.jam;

public interface JProperty extends JAnnotatedElement {
   JClass getType();

   JMethod getSetter();

   JMethod getGetter();
}
