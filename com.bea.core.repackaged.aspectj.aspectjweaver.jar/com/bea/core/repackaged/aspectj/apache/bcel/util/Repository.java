package com.bea.core.repackaged.aspectj.apache.bcel.util;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.JavaClass;

public interface Repository {
   void storeClass(JavaClass var1);

   void removeClass(JavaClass var1);

   JavaClass findClass(String var1);

   JavaClass loadClass(String var1) throws ClassNotFoundException;

   JavaClass loadClass(Class var1) throws ClassNotFoundException;

   void clear();
}
