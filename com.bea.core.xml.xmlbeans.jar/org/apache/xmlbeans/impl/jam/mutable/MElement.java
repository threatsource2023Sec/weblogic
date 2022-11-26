package org.apache.xmlbeans.impl.jam.mutable;

import org.apache.xmlbeans.impl.jam.JElement;
import org.apache.xmlbeans.impl.jam.JamClassLoader;
import org.apache.xmlbeans.impl.jam.visitor.MVisitor;

public interface MElement extends JElement {
   JamClassLoader getClassLoader();

   void setSimpleName(String var1);

   MSourcePosition createSourcePosition();

   void removeSourcePosition();

   MSourcePosition getMutableSourcePosition();

   void accept(MVisitor var1);

   void setArtifact(Object var1);
}
