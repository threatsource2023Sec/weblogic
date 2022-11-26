package org.jboss.weld.bean.proxy;

import org.jboss.classfilewriter.ClassMethod;

public interface BytecodeMethodResolver {
   void getDeclaredMethod(ClassMethod var1, String var2, String var3, String[] var4, ClassMethod var5);
}
