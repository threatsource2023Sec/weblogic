package org.python.indexer.ast;

import org.python.indexer.Scope;
import org.python.indexer.types.NType;

public class NAlias extends NNode {
   static final long serialVersionUID = 4127878954298987559L;
   public NQname qname;
   public String name;
   public NName aname;

   public NAlias(String name, NQname qname, NName aname) {
      this(name, qname, aname, 0, 1);
   }

   public NAlias(String name, NQname qname, NName aname, int start, int end) {
      super(start, end);
      this.qname = qname;
      this.name = name;
      this.aname = aname;
      this.addChildren(new NNode[]{qname, aname});
   }

   public String getBoundName() {
      return this.aname != null ? this.aname.id : this.name;
   }

   public NType resolve(Scope s) throws Exception {
      this.setType(resolveExpr(this.qname, s));
      if (this.aname != null && this.qname != null) {
         this.setType(this.qname.getBottom().getType());
         this.aname.setType(this.getType());
      }

      return this.getType();
   }

   public String toString() {
      return "<Alias:" + this.name + ":" + this.aname + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.qname, v);
         this.visitNode(this.aname, v);
      }

   }
}
