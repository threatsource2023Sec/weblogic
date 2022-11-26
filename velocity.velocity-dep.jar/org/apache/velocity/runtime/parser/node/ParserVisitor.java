package org.apache.velocity.runtime.parser.node;

public interface ParserVisitor {
   Object visit(SimpleNode var1, Object var2);

   Object visit(ASTprocess var1, Object var2);

   Object visit(ASTComment var1, Object var2);

   Object visit(ASTNumberLiteral var1, Object var2);

   Object visit(ASTStringLiteral var1, Object var2);

   Object visit(ASTIdentifier var1, Object var2);

   Object visit(ASTWord var1, Object var2);

   Object visit(ASTDirective var1, Object var2);

   Object visit(ASTBlock var1, Object var2);

   Object visit(ASTObjectArray var1, Object var2);

   Object visit(ASTMethod var1, Object var2);

   Object visit(ASTReference var1, Object var2);

   Object visit(ASTTrue var1, Object var2);

   Object visit(ASTFalse var1, Object var2);

   Object visit(ASTText var1, Object var2);

   Object visit(ASTIfStatement var1, Object var2);

   Object visit(ASTElseStatement var1, Object var2);

   Object visit(ASTElseIfStatement var1, Object var2);

   Object visit(ASTSetDirective var1, Object var2);

   Object visit(ASTExpression var1, Object var2);

   Object visit(ASTAssignment var1, Object var2);

   Object visit(ASTOrNode var1, Object var2);

   Object visit(ASTAndNode var1, Object var2);

   Object visit(ASTEQNode var1, Object var2);

   Object visit(ASTNENode var1, Object var2);

   Object visit(ASTLTNode var1, Object var2);

   Object visit(ASTGTNode var1, Object var2);

   Object visit(ASTLENode var1, Object var2);

   Object visit(ASTGENode var1, Object var2);

   Object visit(ASTAddNode var1, Object var2);

   Object visit(ASTSubtractNode var1, Object var2);

   Object visit(ASTMulNode var1, Object var2);

   Object visit(ASTDivNode var1, Object var2);

   Object visit(ASTModNode var1, Object var2);

   Object visit(ASTNotNode var1, Object var2);
}
