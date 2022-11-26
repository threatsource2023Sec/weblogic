package com.bea.xml_.impl.jam.internal.elements;

import com.bea.xml_.impl.jam.JamClassLoader;
import com.bea.xml_.impl.jam.annotation.AnnotationProxy;
import com.bea.xml_.impl.jam.provider.JamLogger;

public interface ElementContext extends JamLogger {
   JamLogger getLogger();

   JamClassLoader getClassLoader();

   AnnotationProxy createAnnotationProxy(String var1);
}
