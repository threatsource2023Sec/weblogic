package com.bea.util.jam.mutable;

import com.bea.util.jam.JPackage;

public interface MPackage extends JPackage, MAnnotatedElement {
   MClass[] getMutableClasses();
}
