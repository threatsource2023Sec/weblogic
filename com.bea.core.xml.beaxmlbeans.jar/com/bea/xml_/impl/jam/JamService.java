package com.bea.xml_.impl.jam;

public interface JamService {
   JamClassLoader getClassLoader();

   String[] getClassNames();

   JamClassIterator getClasses();

   JClass[] getAllClasses();
}
