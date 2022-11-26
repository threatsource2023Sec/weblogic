package com.bea.core.repackaged.aspectj.lang;

public interface Signature {
   String toString();

   String toShortString();

   String toLongString();

   String getName();

   int getModifiers();

   Class getDeclaringType();

   String getDeclaringTypeName();
}
