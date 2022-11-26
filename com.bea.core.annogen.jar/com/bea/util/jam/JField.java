package com.bea.util.jam;

public interface JField extends JMember {
   JClass getType();

   boolean isFinal();

   boolean isStatic();

   boolean isVolatile();

   boolean isTransient();

   String getQualifiedName();

   Object getConstantValue();

   String getConstantValueExpression();
}
