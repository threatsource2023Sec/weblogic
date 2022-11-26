package weblogic.apache.org.apache.velocity.runtime.parser.node;

import weblogic.apache.org.apache.velocity.runtime.parser.Parser;

public class ASTprocess extends SimpleNode {
   public ASTprocess(int id) {
      super(id);
   }

   public ASTprocess(Parser p, int id) {
      super(p, id);
   }

   public Object jjtAccept(ParserVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }
}
