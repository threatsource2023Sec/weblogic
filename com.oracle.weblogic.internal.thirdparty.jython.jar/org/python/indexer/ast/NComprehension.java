package org.python.indexer.ast;

import java.util.Iterator;
import java.util.List;
import org.python.indexer.Scope;
import org.python.indexer.types.NType;
import org.python.indexer.types.NUnknownType;

public class NComprehension extends NNode {
   static final long serialVersionUID = -598250664243757218L;
   public NNode target;
   public NNode iter;
   public List ifs;

   public NComprehension(NNode target, NNode iter, List ifs) {
      this(target, iter, ifs, 0, 1);
   }

   public NComprehension(NNode target, NNode iter, List ifs, int start, int end) {
      super(start, end);
      this.target = target;
      this.iter = iter;
      this.ifs = ifs;
      this.addChildren(new NNode[]{target, iter});
      this.addChildren(ifs);
   }

   public boolean bindsName() {
      return true;
   }

   protected void bindNames(Scope s) throws Exception {
      this.bindNames(s, this.target, NameBinder.make());
   }

   private void bindNames(Scope s, NNode target, NameBinder binder) throws Exception {
      if (target instanceof NName) {
         binder.bind(s, (NNode)((NName)target), new NUnknownType());
      } else {
         if (target instanceof NSequence) {
            Iterator var4 = ((NSequence)target).getElements().iterator();

            while(var4.hasNext()) {
               NNode n = (NNode)var4.next();
               this.bindNames(s, n, binder);
            }
         }

      }
   }

   public NType resolve(Scope s) throws Exception {
      NameBinder.make().bindIter(s, this.target, this.iter);
      this.resolveList(this.ifs, s);
      return this.setType(this.target.getType());
   }

   public String toString() {
      return "<Comprehension:" + this.start() + ":" + this.target + ":" + this.iter + ":" + this.ifs + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.target, v);
         this.visitNode(this.iter, v);
         this.visitNodeList(this.ifs, v);
      }

   }
}
