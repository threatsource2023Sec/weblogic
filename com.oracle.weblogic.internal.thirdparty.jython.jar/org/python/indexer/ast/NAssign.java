package org.python.indexer.ast;

import java.util.Iterator;
import java.util.List;
import org.python.indexer.Scope;
import org.python.indexer.types.NType;
import org.python.indexer.types.NUnknownType;

public class NAssign extends NNode {
   static final long serialVersionUID = 928890389856851537L;
   public List targets;
   public NNode rvalue;

   public NAssign(List targets, NNode rvalue) {
      this(targets, rvalue, 0, 1);
   }

   public NAssign(List targets, NNode rvalue, int start, int end) {
      super(start, end);
      this.targets = targets;
      this.rvalue = rvalue;
      this.addChildren(targets);
      this.addChildren(new NNode[]{rvalue});
   }

   public boolean bindsName() {
      return true;
   }

   protected void bindNames(Scope s) throws Exception {
      NameBinder binder = NameBinder.make();
      Iterator var3 = this.targets.iterator();

      while(var3.hasNext()) {
         NNode target = (NNode)var3.next();
         binder.bind(s, (NNode)target, new NUnknownType());
      }

   }

   public NType resolve(Scope s) throws Exception {
      NType valueType = resolveExpr(this.rvalue, s);
      switch (this.targets.size()) {
         case 0:
            break;
         case 1:
            NameBinder.make().bind(s, (NNode)this.targets.get(0), valueType);
            break;
         default:
            NameBinder.make().bind(s, this.targets, valueType);
      }

      return this.setType(valueType);
   }

   public String toString() {
      return "<Assign:" + this.targets + "=" + this.rvalue + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNodeList(this.targets, v);
         this.visitNode(this.rvalue, v);
      }

   }
}
