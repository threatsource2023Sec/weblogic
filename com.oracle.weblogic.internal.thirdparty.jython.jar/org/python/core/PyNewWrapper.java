package org.python.core;

public abstract class PyNewWrapper extends PyBuiltinMethod implements Traverseproc {
   public PyType for_type;

   public PyNewWrapper() {
      this((PyType)((PyType)null), "__new__", -1, -1);
   }

   public PyNewWrapper(Class c, String name, int minargs, int maxargs) {
      this(PyType.fromClass(c), name, minargs, maxargs);
   }

   public PyNewWrapper(PyType type, String name, int minargs, int maxargs) {
      super(type, new PyBuiltinCallable.DefaultInfo(name, minargs, maxargs));
      this.for_type = (PyType)this.getSelf();
      this.doc = "T.__new__(S, ...) -> a new object with type S, a subtype of T";
   }

   public abstract PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4);

   public PyBuiltinCallable bind(PyObject self) {
      throw Py.SystemError("__new__ wrappers are already bound");
   }

   public PyType getWrappedType() {
      return this.for_type;
   }

   public void setWrappedType(PyType type) {
      this.self = type;
      this.for_type = type;
   }

   public PyObject __call__(PyObject[] args) {
      return this.__call__(args, Py.NoKeywords);
   }

   public PyObject __call__(PyObject[] args, String[] keywords) {
      int nargs = args.length;
      if (nargs >= 1 && nargs != keywords.length) {
         PyObject arg0 = args[0];
         if (!(arg0 instanceof PyType)) {
            throw Py.TypeError(this.for_type.fastGetName() + ".__new__(X): X is not a type object (" + arg0.getType().fastGetName() + ")");
         } else {
            PyType subtype = (PyType)arg0;
            if (!subtype.isSubType(this.for_type)) {
               throw Py.TypeError(this.for_type.fastGetName() + ".__new__(" + subtype.fastGetName() + "): " + subtype.fastGetName() + " is not a subtype of " + this.for_type.fastGetName());
            } else if (subtype.getStatic() != this.for_type) {
               throw Py.TypeError(this.for_type.fastGetName() + ".__new__(" + subtype.fastGetName() + ") is not safe, use " + subtype.fastGetName() + ".__new__()");
            } else {
               PyObject[] rest = new PyObject[nargs - 1];
               System.arraycopy(args, 1, rest, 0, nargs - 1);
               return this.new_impl(false, subtype, rest, keywords);
            }
         }
      } else {
         throw Py.TypeError(this.for_type.fastGetName() + ".__new__(): not enough arguments");
      }
   }

   public int traverse(Visitproc visit, Object arg) {
      return this.for_type == null ? 0 : visit.visit(this.for_type, arg);
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && ob == this.for_type;
   }
}
