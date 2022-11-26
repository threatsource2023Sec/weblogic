package org.apache.velocity.runtime.parser.node;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.runtime.parser.Parser;

public class ASTNumberLiteral extends SimpleNode {
   private Integer value = null;

   public ASTNumberLiteral(int id) {
      super(id);
   }

   public ASTNumberLiteral(Parser p, int id) {
      super(p, id);
   }

   public Object jjtAccept(ParserVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public Object init(InternalContextAdapter context, Object data) throws Exception {
      super.init(context, data);
      this.value = new Integer(this.getFirstToken().image);
      return data;
   }

   public Object value(InternalContextAdapter context) {
      return this.value;
   }
}
