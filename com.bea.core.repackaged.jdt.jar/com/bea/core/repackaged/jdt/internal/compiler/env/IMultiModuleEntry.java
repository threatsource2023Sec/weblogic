package com.bea.core.repackaged.jdt.internal.compiler.env;

import java.util.Collection;

public interface IMultiModuleEntry extends IModulePathEntry {
   IModule getModule(char[] var1);

   Collection getModuleNames(Collection var1);
}
