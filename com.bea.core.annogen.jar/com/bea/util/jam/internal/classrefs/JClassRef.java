package com.bea.util.jam.internal.classrefs;

import com.bea.util.jam.JClass;

public interface JClassRef {
   JClass getRefClass();

   String getQualifiedName();
}
