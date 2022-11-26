package com.bea.util.jam;

public interface JImport extends JElement {
   boolean isStaticImport();

   boolean isStarEnd();

   String getQualifiedName();
}
