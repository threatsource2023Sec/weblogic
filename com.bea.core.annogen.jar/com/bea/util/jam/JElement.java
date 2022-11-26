package com.bea.util.jam;

import com.bea.util.jam.visitor.JVisitor;

public interface JElement {
   JElement getParent();

   String getSimpleName();

   String getQualifiedName();

   JSourcePosition getSourcePosition();

   void accept(JVisitor var1);

   Object getArtifact();

   boolean isSourceAvailable();

   String toString();
}
