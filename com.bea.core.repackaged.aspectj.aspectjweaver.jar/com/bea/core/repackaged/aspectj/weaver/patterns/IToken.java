package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.weaver.IHasPosition;

public interface IToken extends IHasPosition {
   IToken EOF = BasicToken.makeOperator("<eof>", 0, 0);

   String getString();

   boolean isIdentifier();

   String getLiteralKind();

   Pointcut maybeGetParsedPointcut();
}
