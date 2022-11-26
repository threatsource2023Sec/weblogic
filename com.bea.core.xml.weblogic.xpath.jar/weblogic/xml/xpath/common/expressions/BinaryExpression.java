package weblogic.xml.xpath.common.expressions;

import java.util.Collection;
import weblogic.xml.xpath.common.Expression;

public abstract class BinaryExpression extends BooleanExpression {
   protected static final int NODESET_NODESET = 1;
   protected static final int NODESET_NUMBER = 2;
   protected static final int NODESET_STRING = 3;
   protected static final int NODESET_BOOLEAN = 4;
   protected static final int BOOLEAN_OTHER = 5;
   protected static final int NUMBER_OTHER = 6;
   protected static final int STRING_OTHER = 7;
   protected static final int UNKNOWN = -1;
   protected Expression mLeft;
   protected Expression mRight;

   public BinaryExpression(Expression left, Expression right) {
      if (left == null) {
         throw new IllegalArgumentException("left is null");
      } else if (right == null) {
         throw new IllegalArgumentException("right is null");
      } else {
         this.mLeft = left;
         this.mRight = right;
      }
   }

   protected final int getEvaluationType() {
      int leftType = this.mLeft.getType();
      int rightType = this.mRight.getType();
      switch (leftType) {
         case 1:
            switch (rightType) {
               case 1:
                  return 1;
               case 2:
                  return 4;
               case 3:
                  return 2;
               case 4:
                  return 3;
            }
         case 2:
            switch (rightType) {
               case 1:
                  this.swap();
                  return 2;
               default:
                  return 5;
            }
         case 3:
            switch (rightType) {
               case 1:
                  this.swap();
                  return 2;
               case 2:
                  return 5;
               default:
                  return 6;
            }
         case 4:
            switch (rightType) {
               case 1:
                  this.swap();
                  return 3;
               case 2:
                  return 5;
               case 3:
                  return 6;
               default:
                  return 7;
            }
         default:
            return -1;
      }
   }

   public void getSubExpressions(Collection out) {
      out.add(this.mLeft);
      out.add(this.mRight);
      this.mLeft.getSubExpressions(out);
      this.mRight.getSubExpressions(out);
   }

   private final void swap() {
      Expression leftHolder = this.mLeft;
      this.mLeft = this.mRight;
      this.mRight = leftHolder;
   }
}
