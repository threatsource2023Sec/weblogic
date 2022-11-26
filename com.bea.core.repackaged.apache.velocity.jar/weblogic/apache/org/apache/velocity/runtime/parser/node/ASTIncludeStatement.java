package weblogic.apache.org.apache.velocity.runtime.parser.node;

import weblogic.apache.org.apache.velocity.runtime.parser.Parser;

public class ASTIncludeStatement extends SimpleNode {
   public ASTIncludeStatement(int id) {
      super(id);
   }

   public ASTIncludeStatement(Parser p, int id) {
      super(p, id);
   }

   public Object jjtAccept(ParserVisitor visitor, Object data) {
      return visitor.visit((SimpleNode)this, data);
   }
}
