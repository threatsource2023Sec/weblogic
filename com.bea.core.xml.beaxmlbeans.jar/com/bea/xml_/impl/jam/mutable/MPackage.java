package com.bea.xml_.impl.jam.mutable;

import com.bea.xml_.impl.jam.JPackage;

public interface MPackage extends JPackage, MAnnotatedElement {
   MClass[] getMutableClasses();
}
