package org.python.indexer.ast;

import java.util.Iterator;
import java.util.List;
import org.python.indexer.Indexer;
import org.python.indexer.NBinding;
import org.python.indexer.Scope;
import org.python.indexer.types.NTupleType;
import org.python.indexer.types.NType;
import org.python.indexer.types.NUnknownType;

public class NLambda extends NFunctionDef {
   static final long serialVersionUID = 7737836525970653522L;
   private NName fname;

   public NLambda(List args, NNode body, List defaults, NName varargs, NName kwargs) {
      this(args, body, defaults, varargs, kwargs, 0, 1);
   }

   public NLambda(List args, NNode body, List defaults, NName varargs, NName kwargs, int start, int end) {
      super((NName)null, args, (NBlock)null, defaults, varargs, kwargs, start, end);
      this.body = (NNode)(body instanceof NBlock ? new NBody((NBlock)body) : body);
      this.addChildren(new NNode[]{this.body});
   }

   public boolean isLambda() {
      return true;
   }

   protected String getBindingName(Scope s) {
      if (this.fname != null) {
         return this.fname.id;
      } else {
         String fn = s.newLambdaName();
         this.fname = new NName(fn, this.start(), this.start() + "lambda".length());
         this.fname.setParent(this);
         return fn;
      }
   }

   protected void bindFunctionName(Scope owner) throws Exception {
      NameBinder.make(NBinding.Kind.FUNCTION).bindName(owner, this.fname, this.getType());
   }

   protected void bindMethodAttrs(Scope owner) throws Exception {
   }

   public NType resolve(Scope s) throws Exception {
      if (!this.getType().isFuncType()) {
         Indexer.idx.reportFailedAssertion("Bad type on " + this + ": type=" + this.getType() + " in file " + this.getFile() + " at " + this.start());
      }

      NTupleType fromType = new NTupleType();
      NameBinder param = NameBinder.make(NBinding.Kind.PARAMETER);
      this.resolveList(this.defaults, s);
      Scope funcTable = this.getTable();
      int argnum = 0;
      Iterator var6 = this.args.iterator();

      while(var6.hasNext()) {
         NNode a = (NNode)var6.next();
         NType argtype = NFunctionDef.getArgType(this.args, this.defaults, argnum++);
         param.bind(funcTable, a, argtype);
         fromType.add(argtype);
      }

      NUnknownType u;
      if (this.varargs != null) {
         u = new NUnknownType();
         param.bind(funcTable, (NNode)this.varargs, u);
         fromType.add(u);
      }

      if (this.kwargs != null) {
         u = new NUnknownType();
         param.bind(funcTable, (NNode)this.kwargs, u);
         fromType.add(u);
      }

      try {
         funcTable.setNameBindingPhase(true);
         this.body.visit(new BindingFinder(funcTable));
      } finally {
         funcTable.setNameBindingPhase(false);
      }

      NType toType = resolveExpr(this.body, funcTable);
      if (this.getType().isFuncType()) {
         this.getType().asFuncType().setReturnType(toType);
      }

      return this.getType();
   }

   public String toString() {
      return "<Lambda:" + this.start() + ":" + this.args + ":" + this.body + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNodeList(this.args, v);
         this.visitNodeList(this.defaults, v);
         this.visitNode(this.varargs, v);
         this.visitNode(this.kwargs, v);
         this.visitNode(this.body, v);
      }

   }
}
