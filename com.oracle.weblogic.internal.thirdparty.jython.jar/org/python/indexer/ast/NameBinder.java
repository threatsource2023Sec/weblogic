package org.python.indexer.ast;

import java.util.List;
import org.python.indexer.Indexer;
import org.python.indexer.NBinding;
import org.python.indexer.Scope;
import org.python.indexer.types.NType;
import org.python.indexer.types.NUnknownType;

public class NameBinder {
   private static final NameBinder DEFAULT_BINDER = new NameBinder();
   private static final NameBinder ATTRIBUTE_BINDER;
   private static final NameBinder CLASS_BINDER;
   private static final NameBinder CONSTRUCTOR_BINDER;
   private static final NameBinder FUNCTION_BINDER;
   private static final NameBinder METHOD_BINDER;
   private static final NameBinder MODULE_BINDER;
   private static final NameBinder PARAMETER_BINDER;
   private static final NameBinder VARIABLE_BINDER;
   private NBinding.Kind kind;

   public static NameBinder make() {
      return DEFAULT_BINDER;
   }

   public static NameBinder make(NBinding.Kind kind) {
      switch (kind) {
         case ATTRIBUTE:
            return ATTRIBUTE_BINDER;
         case CLASS:
            return CLASS_BINDER;
         case CONSTRUCTOR:
            return CONSTRUCTOR_BINDER;
         case FUNCTION:
            return FUNCTION_BINDER;
         case METHOD:
            return METHOD_BINDER;
         case MODULE:
            return MODULE_BINDER;
         case PARAMETER:
            return PARAMETER_BINDER;
         case VARIABLE:
            return VARIABLE_BINDER;
         default:
            return DEFAULT_BINDER;
      }
   }

   private NameBinder() {
   }

   private NameBinder(NBinding.Kind kind) {
      this.kind = kind;
   }

   public void bind(Scope s, NNode target, NType rvalue) throws Exception {
      if (target instanceof NName) {
         this.bindName(s, (NName)target, rvalue);
      } else if (target instanceof NTuple) {
         this.bind(s, ((NTuple)target).elts, rvalue);
      } else if (target instanceof NList) {
         this.bind(s, ((NList)target).elts, rvalue);
      } else if (target instanceof NAttribute) {
         if (!s.isNameBindingPhase()) {
            ((NAttribute)target).setAttr(s, rvalue);
         }

      } else if (target instanceof NSubscript) {
         if (!s.isNameBindingPhase()) {
            NNode.resolveExpr(target, s);
         }

      } else {
         Indexer.idx.putProblem(target, "invalid location for assignment");
      }
   }

   public void bind(Scope s, List xs, NType rvalue) throws Exception {
      if (rvalue.isTupleType()) {
         List vs = rvalue.asTupleType().getElementTypes();
         if (xs.size() != vs.size()) {
            this.reportUnpackMismatch(xs, vs.size());
         } else {
            for(int i = 0; i < xs.size(); ++i) {
               this.bind(s, (NNode)xs.get(i), (NType)vs.get(i));
            }
         }

      } else if (rvalue.isListType()) {
         this.bind(s, (List)xs, rvalue.asListType().toTupleType(xs.size()));
      } else if (rvalue.isDictType()) {
         this.bind(s, (List)xs, rvalue.asDictType().toTupleType(xs.size()));
      } else {
         if (!rvalue.isUnknownType()) {
            Indexer.idx.putProblem(((NNode)xs.get(0)).getFile(), ((NNode)xs.get(0)).start(), ((NNode)xs.get(xs.size() - 1)).end(), "unpacking non-iterable: " + rvalue);
         }

         for(int i = 0; i < xs.size(); ++i) {
            this.bind(s, (NNode)((NNode)xs.get(i)), new NUnknownType());
         }

      }
   }

   public NBinding bindName(Scope s, NName name, NType rvalue) throws Exception {
      NBinding b;
      if (s.isGlobalName(name.id)) {
         b = s.getGlobalTable().put(name.id, name, rvalue, this.kindOr(NBinding.Kind.SCOPE));
         Indexer.idx.putLocation((NNode)name, b);
      } else {
         Scope bindingScope = s.getScopeSymtab();
         b = bindingScope.put(name.id, name, rvalue, this.kindOr(bindingScope.isFunctionScope() ? NBinding.Kind.VARIABLE : NBinding.Kind.SCOPE));
      }

      name.setType(b.followType());
      NType nameType = name.getType();
      if (!nameType.isModuleType() && !nameType.isClassType()) {
         nameType.getTable().setPath(b.getQname());
      }

      return b;
   }

   public void bindIter(Scope s, NNode target, NNode iter) throws Exception {
      NType iterType = NNode.resolveExpr(iter, s);
      if (iterType.isListType()) {
         this.bind(s, target, iterType.asListType().getElementType());
      } else if (iterType.isTupleType()) {
         this.bind(s, target, iterType.asTupleType().toListType().getElementType());
      } else {
         NBinding ent = iterType.getTable().lookupAttr("__iter__");
         if (ent != null && ent.getType().isFuncType()) {
            this.bind(s, target, ent.getType().asFuncType().getReturnType());
         } else {
            if (!iterType.isUnknownType()) {
               iter.addWarning("not an iterable type: " + iterType);
            }

            this.bind(s, (NNode)target, new NUnknownType());
         }
      }

   }

   private void reportUnpackMismatch(List xs, int vsize) {
      int xsize = xs.size();
      int beg = ((NNode)xs.get(0)).start();
      int end = ((NNode)xs.get(xs.size() - 1)).end();
      int diff = xsize - vsize;
      String msg;
      if (diff > 0) {
         msg = "ValueError: need more than " + vsize + " values to unpack";
      } else {
         msg = "ValueError: too many values to unpack";
      }

      Indexer.idx.putProblem(((NNode)xs.get(0)).getFile(), beg, end, msg);
   }

   private NBinding.Kind kindOr(NBinding.Kind k) {
      return this.kind != null ? this.kind : k;
   }

   static {
      ATTRIBUTE_BINDER = new NameBinder(NBinding.Kind.ATTRIBUTE);
      CLASS_BINDER = new NameBinder(NBinding.Kind.CLASS);
      CONSTRUCTOR_BINDER = new NameBinder(NBinding.Kind.CONSTRUCTOR);
      FUNCTION_BINDER = new NameBinder(NBinding.Kind.FUNCTION);
      METHOD_BINDER = new NameBinder(NBinding.Kind.METHOD);
      MODULE_BINDER = new NameBinder(NBinding.Kind.MODULE);
      PARAMETER_BINDER = new NameBinder(NBinding.Kind.PARAMETER);
      VARIABLE_BINDER = new NameBinder(NBinding.Kind.VARIABLE);
   }
}
