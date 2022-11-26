package com.bea.core.repackaged.aspectj.weaver.ast;

import com.bea.core.repackaged.aspectj.weaver.internal.tools.MatchingContextBasedTest;

public interface ITestVisitor {
   void visit(And var1);

   void visit(Instanceof var1);

   void visit(Not var1);

   void visit(Or var1);

   void visit(Literal var1);

   void visit(Call var1);

   void visit(FieldGetCall var1);

   void visit(HasAnnotation var1);

   void visit(MatchingContextBasedTest var1);
}
