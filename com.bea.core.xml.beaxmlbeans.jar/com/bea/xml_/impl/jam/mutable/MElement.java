package com.bea.xml_.impl.jam.mutable;

import com.bea.xml_.impl.jam.JElement;
import com.bea.xml_.impl.jam.JamClassLoader;
import com.bea.xml_.impl.jam.visitor.MVisitor;

public interface MElement extends JElement {
   JamClassLoader getClassLoader();

   void setSimpleName(String var1);

   MSourcePosition createSourcePosition();

   void removeSourcePosition();

   MSourcePosition getMutableSourcePosition();

   void accept(MVisitor var1);

   void setArtifact(Object var1);
}
