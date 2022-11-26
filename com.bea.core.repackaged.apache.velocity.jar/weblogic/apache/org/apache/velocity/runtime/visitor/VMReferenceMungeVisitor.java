package weblogic.apache.org.apache.velocity.runtime.visitor;

import java.util.Map;
import weblogic.apache.org.apache.velocity.runtime.parser.node.ASTReference;

public class VMReferenceMungeVisitor extends BaseVisitor {
   private Map argmap = null;

   public VMReferenceMungeVisitor(Map map) {
      this.argmap = map;
   }

   public Object visit(ASTReference node, Object data) {
      String override = (String)this.argmap.get(node.literal().substring(1));
      if (override != null) {
         node.setLiteral(override);
      }

      data = node.childrenAccept(this, data);
      return data;
   }
}
