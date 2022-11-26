package com.bea.core.repackaged.aspectj.weaver.tools;

public interface ContextBasedMatcher {
   /** @deprecated */
   boolean couldMatchJoinPointsInType(Class var1);

   boolean couldMatchJoinPointsInType(Class var1, MatchingContext var2);

   boolean mayNeedDynamicTest();

   FuzzyBoolean matchesStatically(MatchingContext var1);

   boolean matchesDynamically(MatchingContext var1);
}
