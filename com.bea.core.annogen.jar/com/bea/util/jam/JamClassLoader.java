package com.bea.util.jam;

import org.apache.tools.ant.taskdefs.Javac;

public interface JamClassLoader {
   JClass loadClass(String var1);

   JClass loadClass(Javac var1, String var2);

   JPackage getPackage(String var1);
}
