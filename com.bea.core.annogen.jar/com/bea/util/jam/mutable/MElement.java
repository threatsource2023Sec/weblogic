package com.bea.util.jam.mutable;

import com.bea.util.jam.JElement;
import com.bea.util.jam.JamClassLoader;
import com.bea.util.jam.visitor.MVisitor;

public interface MElement extends JElement {
   JamClassLoader getClassLoader();

   void setSimpleName(String var1);

   MSourcePosition createSourcePosition();

   void removeSourcePosition();

   MSourcePosition getMutableSourcePosition();

   void accept(MVisitor var1);

   void setArtifact(Object var1);
}
