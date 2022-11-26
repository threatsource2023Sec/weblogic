package com.bea.core.repackaged.aspectj.weaver.tools;

public interface ShadowMatch {
   boolean alwaysMatches();

   boolean maybeMatches();

   boolean neverMatches();

   JoinPointMatch matchesJoinPoint(Object var1, Object var2, Object[] var3);

   void setMatchingContext(MatchingContext var1);
}
