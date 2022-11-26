package org.apache.xmlbeans.impl.jam;

public interface JMember extends JAnnotatedElement {
   JClass getContainingClass();

   int getModifiers();

   boolean isPackagePrivate();

   boolean isPrivate();

   boolean isProtected();

   boolean isPublic();
}
