package org.python.indexer.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.python.indexer.Builtins;
import org.python.indexer.Indexer;
import org.python.indexer.NBinding;
import org.python.indexer.Scope;
import org.python.indexer.types.NDictType;
import org.python.indexer.types.NFuncType;
import org.python.indexer.types.NListType;
import org.python.indexer.types.NTupleType;
import org.python.indexer.types.NType;
import org.python.indexer.types.NUnknownType;

public class NFunctionDef extends NNode {
   static final long serialVersionUID = 5495886181960463846L;
   public NName name;
   public List args;
   public List defaults;
   public NName varargs;
   public NName kwargs;
   public NNode body;
   private List decoratorList;

   public NFunctionDef(NName name, List args, NBlock body, List defaults, NName varargs, NName kwargs) {
      this(name, args, body, defaults, kwargs, varargs, 0, 1);
   }

   public NFunctionDef(NName name, List args, NBlock body, List defaults, NName varargs, NName kwargs, int start, int end) {
      super(start, end);
      this.name = name;
      this.args = args;
      this.body = (NNode)(body != null ? new NBody(body) : new NBlock((List)null));
      this.defaults = defaults;
      this.varargs = varargs;
      this.kwargs = kwargs;
      this.addChildren(new NNode[]{name});
      this.addChildren(args);
      this.addChildren(defaults);
      this.addChildren(new NNode[]{varargs, kwargs, this.body});
   }

   public void setDecoratorList(List decoratorList) {
      this.decoratorList = decoratorList;
      this.addChildren(decoratorList);
   }

   public List getDecoratorList() {
      if (this.decoratorList == null) {
         this.decoratorList = new ArrayList();
      }

      return this.decoratorList;
   }

   public boolean isFunctionDef() {
      return true;
   }

   public boolean bindsName() {
      return true;
   }

   protected String getBindingName(Scope s) {
      return this.name.id;
   }

   protected void bindNames(Scope s) throws Exception {
      Scope owner = s.getScopeSymtab();
      this.setType(new NFuncType());
      Scope funcTable = new Scope(s.getEnclosingLexicalScope(), Scope.Type.FUNCTION);
      this.getType().setTable(funcTable);
      funcTable.setPath(owner.extendPath(this.getBindingName(owner)));
      NType existing = owner.lookupType(this.getBindingName(owner), true);
      if (existing == null || !existing.isFuncType()) {
         this.bindFunctionName(owner);
         this.bindFunctionParams(funcTable);
         this.bindFunctionDefaults(s);
         this.bindMethodAttrs(owner);
      }
   }

   protected void bindFunctionName(Scope owner) throws Exception {
      NBinding.Kind funkind = NBinding.Kind.FUNCTION;
      if (owner.getScopeType() == Scope.Type.CLASS) {
         if ("__init__".equals(this.name.id)) {
            funkind = NBinding.Kind.CONSTRUCTOR;
         } else {
            funkind = NBinding.Kind.METHOD;
         }
      }

      NameBinder.make(funkind).bindName(owner, this.name, this.getType());
   }

   protected void bindFunctionParams(Scope funcTable) throws Exception {
      NameBinder param = NameBinder.make(NBinding.Kind.PARAMETER);
      Iterator var3 = this.args.iterator();

      while(var3.hasNext()) {
         NNode a = (NNode)var3.next();
         param.bind(funcTable, (NNode)a, new NUnknownType());
      }

      if (this.varargs != null) {
         param.bind(funcTable, (NNode)this.varargs, new NListType());
      }

      if (this.kwargs != null) {
         param.bind(funcTable, (NNode)this.kwargs, new NDictType());
      }

   }

   protected void bindFunctionDefaults(Scope s) throws Exception {
      Iterator var2 = this.defaults.iterator();

      while(var2.hasNext()) {
         NNode n = (NNode)var2.next();
         if (n.bindsName()) {
            n.bindNames(s);
         }
      }

   }

   protected void bindMethodAttrs(Scope owner) throws Exception {
      NType cls = Indexer.idx.lookupQnameType(owner.getPath());
      if (cls != null && cls.isClassType()) {
         this.addReadOnlyAttr("im_class", cls, NBinding.Kind.CLASS);
         this.addReadOnlyAttr("__class__", cls, NBinding.Kind.CLASS);
         this.addReadOnlyAttr("im_self", cls, NBinding.Kind.ATTRIBUTE);
         this.addReadOnlyAttr("__self__", cls, NBinding.Kind.ATTRIBUTE);
      }
   }

   protected NBinding addSpecialAttr(String name, NType atype, NBinding.Kind kind) {
      NBinding b = this.getTable().update(name, (NNode)Builtins.newDataModelUrl("the-standard-type-hierarchy"), atype, kind);
      b.markSynthetic();
      b.markStatic();
      return b;
   }

   protected NBinding addReadOnlyAttr(String name, NType type, NBinding.Kind kind) {
      NBinding b = this.addSpecialAttr(name, type, kind);
      b.markReadOnly();
      return b;
   }

   public NType resolve(Scope outer) throws Exception {
      this.resolveList(this.defaults, outer);
      this.resolveList(this.decoratorList, outer);
      Scope funcTable = this.getTable();
      NBinding selfBinding = funcTable.lookup("__self__");
      if (selfBinding != null && !selfBinding.getType().isClassType()) {
         selfBinding = null;
      }

      if (selfBinding != null) {
         if (this.args.size() < 1) {
            this.addWarning(this.name, "method should have at least one argument (self)");
         } else if (!(this.args.get(0) instanceof NName)) {
            this.addError(this.name, "self parameter must be an identifier");
         }
      }

      NTupleType fromType = new NTupleType();
      this.bindParamsToDefaults(selfBinding, fromType);
      NBinding b;
      if (this.varargs != null) {
         b = funcTable.lookupLocal(this.varargs.id);
         if (b != null) {
            fromType.add(b.getType());
         }
      }

      if (this.kwargs != null) {
         b = funcTable.lookupLocal(this.kwargs.id);
         if (b != null) {
            fromType.add(b.getType());
         }
      }

      NType toType = resolveExpr(this.body, funcTable);
      this.getType().asFuncType().setReturnType(toType);
      return this.getType();
   }

   private void bindParamsToDefaults(NBinding selfBinding, NTupleType fromType) throws Exception {
      NameBinder param = NameBinder.make(NBinding.Kind.PARAMETER);
      Scope funcTable = this.getTable();

      for(int i = 0; i < this.args.size(); ++i) {
         NNode arg = (NNode)this.args.get(i);
         NType argtype = i == 0 && selfBinding != null ? selfBinding.getType() : getArgType(this.args, this.defaults, i);
         param.bind(funcTable, arg, argtype);
         fromType.add(argtype);
      }

   }

   static NType getArgType(List args, List defaults, int argnum) {
      if (defaults == null) {
         return new NUnknownType();
      } else {
         int firstDefault = args.size() - defaults.size();
         return (NType)(firstDefault >= 0 && argnum >= firstDefault ? ((NNode)defaults.get(argnum - firstDefault)).getType() : new NUnknownType());
      }
   }

   public String toString() {
      return "<Function:" + this.start() + ":" + this.name + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.name, v);
         this.visitNodeList(this.args, v);
         this.visitNodeList(this.defaults, v);
         this.visitNode(this.kwargs, v);
         this.visitNode(this.varargs, v);
         this.visitNode(this.body, v);
      }

   }
}
