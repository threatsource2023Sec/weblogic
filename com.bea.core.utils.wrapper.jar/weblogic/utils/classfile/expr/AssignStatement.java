package weblogic.utils.classfile.expr;

import weblogic.utils.Debug;
import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classloaders.AugmentableClassLoaderManager;

public class AssignStatement implements Statement {
   LHSExpression lhsExpression;
   Expression rhsExpression;

   public AssignStatement(LHSExpression lhs, Expression rhs) {
      if (lhs.getType() != rhs.getType()) {
         Debug.say(lhs.getType() + " LHS Identity Hashcode " + System.identityHashCode(lhs.getType()));
         Debug.say(rhs.getType() + " RHS Identity Hashcode " + System.identityHashCode(rhs.getType()));
         Debug.say("LHS CLASSLOADER " + lhs.getType().getClass().getClassLoader());
         Debug.say("rHS CLASSLOADER " + rhs.getType().getClass().getClassLoader());
         Debug.say("AUG " + AugmentableClassLoaderManager.getAugmentableSystemClassLoader());
         throw new AssertionError("invalid assignment from " + rhs.getType() + " to " + lhs.getType() + " lhs.getType().getClass().getClassLoader()" + lhs.getType().getClass().getClassLoader() + " rhs.getType().getClass().getClassLoader()" + rhs.getType().getClass().getClassLoader());
      } else {
         this.lhsExpression = lhs;
         this.rhsExpression = rhs;
      }
   }

   public void code(CodeAttribute ca, Bytecodes code) {
      this.lhsExpression.codeAssign(ca, code, this.rhsExpression);
   }

   public int getMaxStack() {
      return this.lhsExpression.getMaxStack() + this.rhsExpression.getMaxStack();
   }
}
