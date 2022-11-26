package com.bea.util.jam;

import org.apache.tools.ant.taskdefs.Javac;

public interface JamService {
   JamClassLoader getClassLoader();

   String[] getClassNames();

   JamClassIterator getClasses();

   JClass[] getAllClasses(Javac var1);

   JClass[] getAllClasses();
}
