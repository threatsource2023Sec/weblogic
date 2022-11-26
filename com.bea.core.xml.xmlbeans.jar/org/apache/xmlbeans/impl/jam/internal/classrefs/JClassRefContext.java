package org.apache.xmlbeans.impl.jam.internal.classrefs;

import org.apache.xmlbeans.impl.jam.JamClassLoader;

public interface JClassRefContext {
   String getPackageName();

   String[] getImportSpecs();

   JamClassLoader getClassLoader();
}
