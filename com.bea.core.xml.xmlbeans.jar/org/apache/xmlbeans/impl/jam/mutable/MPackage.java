package org.apache.xmlbeans.impl.jam.mutable;

import org.apache.xmlbeans.impl.jam.JPackage;

public interface MPackage extends JPackage, MAnnotatedElement {
   MClass[] getMutableClasses();
}
