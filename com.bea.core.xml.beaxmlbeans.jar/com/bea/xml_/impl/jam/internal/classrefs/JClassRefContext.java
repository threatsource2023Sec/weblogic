package com.bea.xml_.impl.jam.internal.classrefs;

import com.bea.xml_.impl.jam.JamClassLoader;

public interface JClassRefContext {
   String getPackageName();

   String[] getImportSpecs();

   JamClassLoader getClassLoader();
}
