package org.apache.xmlbeans.impl.jam.internal.elements;

import org.apache.xmlbeans.impl.jam.JamClassLoader;
import org.apache.xmlbeans.impl.jam.annotation.AnnotationProxy;
import org.apache.xmlbeans.impl.jam.provider.JamLogger;

public interface ElementContext extends JamLogger {
   JamLogger getLogger();

   JamClassLoader getClassLoader();

   AnnotationProxy createAnnotationProxy(String var1);
}
