package org.apache.oro.text.awk;

final class QuestionNode extends OrNode {
   static final SyntaxNode _epsilon = new EpsilonNode();

   QuestionNode(SyntaxNode var1) {
      super(var1, _epsilon);
   }

   SyntaxNode _clone(int[] var1) {
      return new QuestionNode(super._left._clone(var1));
   }

   boolean _nullable() {
      return true;
   }
}
