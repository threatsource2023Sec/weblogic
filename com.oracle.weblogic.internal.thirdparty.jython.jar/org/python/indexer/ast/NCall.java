package org.python.indexer.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.python.indexer.Scope;
import org.python.indexer.types.NFuncType;
import org.python.indexer.types.NType;
import org.python.indexer.types.NUnionType;
import org.python.indexer.types.NUnknownType;

public class NCall extends NNode {
   static final long serialVersionUID = 5212954751978100639L;
   public NNode func;
   public List args;
   public List keywords;
   public NNode kwargs;
   public NNode starargs;

   public NCall(NNode func, List args, List keywords, NNode kwargs, NNode starargs) {
      this(func, args, keywords, kwargs, starargs, 0, 1);
   }

   public NCall(NNode func, List args, List keywords, NNode kwargs, NNode starargs, int start, int end) {
      super(start, end);
      this.func = func;
      this.args = args;
      this.keywords = keywords;
      this.kwargs = kwargs;
      this.starargs = starargs;
      this.addChildren(new NNode[]{func, kwargs, starargs});
      this.addChildren(args);
      this.addChildren(keywords);
   }

   public NType resolve(Scope s) throws Exception {
      NType ft = resolveExpr(this.func, s);
      List argTypes = new ArrayList();
      Iterator var4 = this.args.iterator();

      while(var4.hasNext()) {
         NNode a = (NNode)var4.next();
         argTypes.add(resolveExpr(a, s));
      }

      this.resolveList(this.keywords, s);
      resolveExpr(this.starargs, s);
      resolveExpr(this.kwargs, s);
      if (ft.isClassType()) {
         return this.setType(ft);
      } else if (ft.isFuncType()) {
         return this.setType(ft.asFuncType().getReturnType().follow());
      } else if (ft.isUnknownType()) {
         NUnknownType to = new NUnknownType();
         NFuncType at = new NFuncType(to);
         NUnionType.union(ft, at);
         return this.setType(to);
      } else {
         this.addWarning("calling non-function " + ft);
         return this.setType(new NUnknownType());
      }
   }

   public String toString() {
      return "<Call:" + this.func + ":" + this.args + ":" + this.start() + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.func, v);
         this.visitNodeList(this.args, v);
         this.visitNodeList(this.keywords, v);
         this.visitNode(this.kwargs, v);
         this.visitNode(this.starargs, v);
      }

   }
}
