package org.python.indexer.ast;

import java.util.List;
import org.python.indexer.Scope;
import org.python.indexer.types.NDictType;
import org.python.indexer.types.NType;

public class NDict extends NNode {
   static final long serialVersionUID = 318144953740238374L;
   public List keys;
   public List values;

   public NDict(List keys, List values) {
      this(keys, values, 0, 1);
   }

   public NDict(List keys, List values, int start, int end) {
      super(start, end);
      this.keys = keys;
      this.values = values;
      this.addChildren(keys);
      this.addChildren(values);
   }

   public NType resolve(Scope s) throws Exception {
      NType keyType = this.resolveListAsUnion(this.keys, s);
      NType valType = this.resolveListAsUnion(this.values, s);
      return this.setType(new NDictType(keyType, valType));
   }

   public String toString() {
      return "<Dict>";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNodeList(this.keys, v);
         this.visitNodeList(this.values, v);
      }

   }
}
