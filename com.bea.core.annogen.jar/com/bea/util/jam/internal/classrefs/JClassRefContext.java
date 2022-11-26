package com.bea.util.jam.internal.classrefs;

import com.bea.util.jam.JamClassLoader;

public interface JClassRefContext {
   String getPackageName();

   String[] getImportSpecs();

   JamClassLoader getClassLoader();
}
