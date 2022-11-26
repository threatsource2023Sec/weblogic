package org.python.modules._threading;

import org.python.core.ContextManager;
import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyDataDescr;
import org.python.core.PyException;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyTuple;
import org.python.core.PyType;
import org.python.core.ThreadState;
import org.python.core.Untraversable;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@Untraversable
@ExposedType(
   name = "_threading.RLock"
)
public class RLock extends PyObject implements ContextManager, ConditionSupportingLock {
   public static final PyType TYPE;
   private final RLockImplementation _lock = new RLockImplementation();

   public java.util.concurrent.locks.Lock getLock() {
      return this._lock;
   }

   @ExposedNew
   static final PyObject RLock___new__(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      int nargs = args.length;
      return new RLock();
   }

   final boolean RLock_acquire(boolean blocking) {
      if (blocking) {
         this._lock.lock();
         return true;
      } else {
         return this._lock.tryLock();
      }
   }

   public boolean acquire() {
      return this.RLock_acquire(true);
   }

   public boolean acquire(boolean blocking) {
      return this.RLock_acquire(blocking);
   }

   final PyObject RLock___enter__() {
      this._lock.lock();
      return this;
   }

   public PyObject __enter__(ThreadState ts) {
      this._lock.lock();
      return this;
   }

   final void RLock_release() {
      if (this._lock.isHeldByCurrentThread() && this._lock.getHoldCount() > 0) {
         this._lock.unlock();
      } else {
         throw Py.RuntimeError("cannot release un-acquired lock");
      }
   }

   public void release() {
      this.RLock_release();
   }

   final boolean RLock___exit__(PyObject type, PyObject value, PyObject traceback) {
      this._lock.unlock();
      return false;
   }

   public boolean __exit__(ThreadState ts, PyException exception) {
      this._lock.unlock();
      return false;
   }

   final boolean RLock_locked() {
      return this._lock.isLocked();
   }

   public boolean locked() {
      return this.RLock_locked();
   }

   final boolean RLock__is_owned() {
      return this._lock.isHeldByCurrentThread();
   }

   public boolean _is_owned() {
      return this.RLock__is_owned();
   }

   public int getWaitQueueLength(java.util.concurrent.locks.Condition condition) {
      return this._lock.getWaitQueueLength(condition);
   }

   public String toString() {
      String owner = this._lock.getOwnerName();
      return Py.newString("<_threading.RLock owner=%r count=%d>").__mod__(new PyTuple(new PyObject[]{(PyObject)(owner != null ? Py.newStringOrUnicode(owner) : Py.None), Py.newInteger(this._lock.getHoldCount())})).toString();
   }

   static {
      PyType.addBuilder(RLock.class, new PyExposer());
      TYPE = PyType.fromClass(RLock.class);
   }

   private static class RLock_acquire_exposer extends PyBuiltinMethodNarrow {
      public RLock_acquire_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "";
      }

      public RLock_acquire_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new RLock_acquire_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((RLock)this.self).RLock_acquire(Py.py2boolean(var1)));
      }

      public PyObject __call__() {
         return Py.newBoolean(((RLock)this.self).RLock_acquire((boolean)1));
      }
   }

   private static class RLock___enter___exposer extends PyBuiltinMethodNarrow {
      public RLock___enter___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public RLock___enter___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new RLock___enter___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((RLock)this.self).RLock___enter__();
      }
   }

   private static class RLock_release_exposer extends PyBuiltinMethodNarrow {
      public RLock_release_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public RLock_release_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new RLock_release_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         ((RLock)this.self).RLock_release();
         return Py.None;
      }
   }

   private static class RLock___exit___exposer extends PyBuiltinMethodNarrow {
      public RLock___exit___exposer(String var1) {
         super(var1, 4, 4);
         super.doc = "";
      }

      public RLock___exit___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new RLock___exit___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newBoolean(((RLock)this.self).RLock___exit__(var1, var2, var3));
      }
   }

   private static class RLock_locked_exposer extends PyBuiltinMethodNarrow {
      public RLock_locked_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public RLock_locked_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new RLock_locked_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((RLock)this.self).RLock_locked());
      }
   }

   private static class RLock__is_owned_exposer extends PyBuiltinMethodNarrow {
      public RLock__is_owned_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public RLock__is_owned_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new RLock__is_owned_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((RLock)this.self).RLock__is_owned());
      }
   }

   private static class toString_exposer extends PyBuiltinMethodNarrow {
      public toString_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public toString_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new toString_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((RLock)this.self).toString();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return RLock.RLock___new__(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new RLock_acquire_exposer("acquire"), new RLock___enter___exposer("__enter__"), new RLock_release_exposer("release"), new RLock___exit___exposer("__exit__"), new RLock_locked_exposer("locked"), new RLock__is_owned_exposer("_is_owned"), new toString_exposer("toString")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("_threading.RLock", RLock.class, Object.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
