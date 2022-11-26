package org.apache.xmlbeans.impl.jam;

public interface JamService {
   JamClassLoader getClassLoader();

   String[] getClassNames();

   JamClassIterator getClasses();

   JClass[] getAllClasses();
}
