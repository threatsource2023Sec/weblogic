package org.python.indexer.ast;

import org.python.indexer.Indexer;
import org.python.indexer.Scope;
import org.python.indexer.types.NType;
import org.python.indexer.types.NUnionType;
import org.python.indexer.types.NUnknownType;

public class NBinOp extends NNode {
   static final long serialVersionUID = -8961832251782335108L;
   public NNode left;
   public NNode right;
   public String op;

   public NBinOp(NNode target, NNode value, String op) {
      this(target, value, op, 0, 1);
   }

   public NBinOp(NNode target, NNode value, String op, int start, int end) {
      super(start, end);
      this.left = target;
      this.right = value;
      this.op = op;
      this.addChildren(new NNode[]{target, value});
   }

   public NType resolve(Scope s) throws Exception {
      NType ltype = null;
      NType rtype = null;
      if (this.left != null) {
         ltype = resolveExpr(this.left, s).follow();
      }

      if (this.right != null) {
         rtype = resolveExpr(this.right, s).follow();
      }

      if (ltype != Indexer.idx.builtins.BaseStr && rtype != Indexer.idx.builtins.BaseStr) {
         if (ltype != Indexer.idx.builtins.BaseNum && rtype != Indexer.idx.builtins.BaseNum) {
            if (ltype == null) {
               return this.setType((NType)(rtype == null ? new NUnknownType() : rtype));
            } else {
               return rtype == null ? this.setType((NType)(ltype == null ? new NUnknownType() : ltype)) : this.setType(NUnionType.union(ltype, rtype));
            }
         } else {
            return this.setType(Indexer.idx.builtins.BaseNum);
         }
      } else {
         return this.setType(Indexer.idx.builtins.BaseStr);
      }
   }

   public String toString() {
      return "<BinOp:" + this.left + " " + this.op + " " + this.right + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.left, v);
         this.visitNode(this.right, v);
      }

   }
}
