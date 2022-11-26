package com.bea.xml_.impl.jam;

public interface JMethod extends JInvokable {
   JClass getReturnType();

   boolean isFinal();

   boolean isStatic();

   boolean isAbstract();

   boolean isNative();

   boolean isSynchronized();

   String getQualifiedName();
}
