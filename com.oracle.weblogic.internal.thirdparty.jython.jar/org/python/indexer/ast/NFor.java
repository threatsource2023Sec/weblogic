package org.python.indexer.ast;

import java.util.Iterator;
import org.python.indexer.Scope;
import org.python.indexer.types.NType;
import org.python.indexer.types.NUnknownType;

public class NFor extends NNode {
   static final long serialVersionUID = 3228529969554646406L;
   public NNode target;
   public NNode iter;
   public NBlock body;
   public NBlock orelse;

   public NFor(NNode target, NNode iter, NBlock body, NBlock orelse) {
      this(target, iter, body, orelse, 0, 1);
   }

   public NFor(NNode target, NNode iter, NBlock body, NBlock orelse, int start, int end) {
      super(start, end);
      this.target = target;
      this.iter = iter;
      this.body = body;
      this.orelse = orelse;
      this.addChildren(new NNode[]{target, iter, body, orelse});
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
      if (this.body == null) {
         this.setType(new NUnknownType());
      } else {
         this.setType(resolveExpr(this.body, s));
      }

      if (this.orelse != null) {
         this.addType(resolveExpr(this.orelse, s));
      }

      return this.getType();
   }

   public String toString() {
      return "<For:" + this.target + ":" + this.iter + ":" + this.body + ":" + this.orelse + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.target, v);
         this.visitNode(this.iter, v);
         this.visitNode(this.body, v);
         this.visitNode(this.orelse, v);
      }

   }
}
