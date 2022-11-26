package com.bea.xml_.impl.jam;

import com.bea.xml_.impl.jam.visitor.JVisitor;

public interface JElement {
   JElement getParent();

   String getSimpleName();

   String getQualifiedName();

   JSourcePosition getSourcePosition();

   void accept(JVisitor var1);

   Object getArtifact();

   String toString();
}
