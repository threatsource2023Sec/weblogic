package com.bea.util.jam.internal.elements;

import com.bea.util.jam.JamClassLoader;
import com.bea.util.jam.annotation.DefaultAnnotationProxy;
import com.bea.util.jam.provider.JamLogger;

public interface ElementContext {
   JamLogger getLogger();

   JamClassLoader getClassLoader();

   DefaultAnnotationProxy createAnnotationProxy(String var1);
}
