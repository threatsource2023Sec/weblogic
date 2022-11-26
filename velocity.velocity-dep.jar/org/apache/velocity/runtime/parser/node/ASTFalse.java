package org.apache.velocity.runtime.parser.node;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.runtime.parser.Parser;

public class ASTFalse extends SimpleNode {
   private static Boolean value;

   public ASTFalse(int id) {
      super(id);
   }

   public ASTFalse(Parser p, int id) {
      super(p, id);
   }

   public Object jjtAccept(ParserVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public boolean evaluate(InternalContextAdapter context) {
      return false;
   }

   public Object value(InternalContextAdapter context) {
      return value;
   }

   static {
      value = Boolean.FALSE;
   }
}
