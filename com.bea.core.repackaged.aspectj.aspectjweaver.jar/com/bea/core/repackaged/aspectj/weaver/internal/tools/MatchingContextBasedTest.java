package com.bea.core.repackaged.aspectj.weaver.internal.tools;

import com.bea.core.repackaged.aspectj.weaver.ast.ITestVisitor;
import com.bea.core.repackaged.aspectj.weaver.ast.Test;
import com.bea.core.repackaged.aspectj.weaver.tools.ContextBasedMatcher;
import com.bea.core.repackaged.aspectj.weaver.tools.MatchingContext;

public class MatchingContextBasedTest extends Test {
   private final ContextBasedMatcher matcher;

   public MatchingContextBasedTest(ContextBasedMatcher pc) {
      this.matcher = pc;
   }

   public void accept(ITestVisitor v) {
      v.visit(this);
   }

   public boolean matches(MatchingContext context) {
      return this.matcher.matchesDynamically(context);
   }
}
