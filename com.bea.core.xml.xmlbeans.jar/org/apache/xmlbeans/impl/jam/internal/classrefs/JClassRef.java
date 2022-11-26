package org.apache.xmlbeans.impl.jam.internal.classrefs;

import org.apache.xmlbeans.impl.jam.JClass;

public interface JClassRef {
   JClass getRefClass();

   String getQualifiedName();
}
