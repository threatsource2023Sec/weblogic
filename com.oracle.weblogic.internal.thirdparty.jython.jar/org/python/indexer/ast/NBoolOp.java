package org.python.indexer.ast;

import java.util.Iterator;
import java.util.List;
import org.python.indexer.Scope;
import org.python.indexer.types.NType;
import org.python.indexer.types.NUnknownType;

public class NBoolOp extends NNode {
   static final long serialVersionUID = -5261954056600388069L;
   OpType op;
   public List values;

   public NBoolOp(OpType op, List values) {
      this(op, values, 0, 1);
   }

   public NBoolOp(OpType op, List values, int start, int end) {
      super(start, end);
      this.op = op;
      this.values = values;
      this.addChildren(values);
   }

   public NType resolve(Scope s) throws Exception {
      if (this.op != NBoolOp.OpType.AND) {
         return this.setType(this.resolveListAsUnion(this.values, s));
      } else {
         NType last = null;

         NNode e;
         for(Iterator var3 = this.values.iterator(); var3.hasNext(); last = resolveExpr(e, s)) {
            e = (NNode)var3.next();
         }

         return this.setType((NType)(last == null ? new NUnknownType() : last));
      }
   }

   public String toString() {
      return "<BoolOp:" + this.op + ":" + this.values + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNodeList(this.values, v);
      }

   }

   public static enum OpType {
      AND,
      OR,
      UNDEFINED;
   }
}
