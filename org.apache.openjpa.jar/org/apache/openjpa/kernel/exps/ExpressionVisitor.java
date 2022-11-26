package org.apache.openjpa.kernel.exps;

public interface ExpressionVisitor {
   void enter(Expression var1);

   void exit(Expression var1);

   void enter(Value var1);

   void exit(Value var1);
}
