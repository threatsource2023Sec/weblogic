package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;

public interface ISourceContext {
   ISourceLocation makeSourceLocation(IHasPosition var1);

   ISourceLocation makeSourceLocation(int var1, int var2);

   int getOffset();

   void tidy();
}
