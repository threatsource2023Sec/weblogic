package org.python.core;

class JavaCode extends PyCode implements Traverseproc {
   private PyObject func;

   public JavaCode(PyObject func) {
      this.func = func;
      if (func instanceof PyReflectedFunction) {
         this.co_name = ((PyReflectedFunction)func).__name__;
      }

   }

   public PyObject call(ThreadState state, PyFrame frame, PyObject closure) {
      Py.warning(Py.RuntimeWarning, "JavaCode doesn't support call with signature (ThreadState state, PyFrame frame, PyObject closure).");
      return Py.None;
   }

   public PyObject call(ThreadState state, PyObject[] args, String[] keywords, PyObject globals, PyObject[] defaults, PyObject closure) {
      return this.func.__call__(args, keywords);
   }

   public PyObject call(ThreadState state, PyObject self, PyObject[] args, String[] keywords, PyObject globals, PyObject[] defaults, PyObject closure) {
      return this.func.__call__(self, args, keywords);
   }

   public PyObject call(ThreadState state, PyObject globals, PyObject[] defaults, PyObject closure) {
      return this.func.__call__();
   }

   public PyObject call(ThreadState state, PyObject arg1, PyObject globals, PyObject[] defaults, PyObject closure) {
      return this.func.__call__(arg1);
   }

   public PyObject call(ThreadState state, PyObject arg1, PyObject arg2, PyObject globals, PyObject[] defaults, PyObject closure) {
      return this.func.__call__(arg1, arg2);
   }

   public PyObject call(ThreadState state, PyObject arg1, PyObject arg2, PyObject arg3, PyObject globals, PyObject[] defaults, PyObject closure) {
      return this.func.__call__(arg1, arg2, arg3);
   }

   public PyObject call(ThreadState state, PyObject arg1, PyObject arg2, PyObject arg3, PyObject arg4, PyObject globals, PyObject[] defaults, PyObject closure) {
      return this.func.__call__(arg1, arg2, arg3, arg4);
   }

   public int traverse(Visitproc visit, Object arg) {
      return this.func != null ? visit.visit(this.func, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && ob == this.func;
   }
}
