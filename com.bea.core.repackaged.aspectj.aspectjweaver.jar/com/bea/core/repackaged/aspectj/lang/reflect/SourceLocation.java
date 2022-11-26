package com.bea.core.repackaged.aspectj.lang.reflect;

public interface SourceLocation {
   Class getWithinType();

   String getFileName();

   int getLine();

   /** @deprecated */
   int getColumn();
}
