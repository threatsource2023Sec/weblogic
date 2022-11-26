package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.weaver.ISourceContext;

public interface ITokenSource {
   IToken next();

   IToken peek();

   IToken peek(int var1);

   int getIndex();

   void setIndex(int var1);

   ISourceContext getSourceContext();
}
