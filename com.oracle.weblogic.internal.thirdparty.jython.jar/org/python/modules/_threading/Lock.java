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
   name = "_threading.Lock"
)
public class Lock extends PyObject implements ContextManager, ConditionSupportingLock {
   public static final PyType TYPE;
   private final Mutex _lock = new Mutex();

   public java.util.concurrent.locks.Lock getLock() {
      return this._lock;
   }

   @ExposedNew
   static final PyObject Lock___new__(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      int nargs = args.length;
      return new Lock();
   }

   final boolean Lock_acquire(boolean blocking) {
      if (blocking) {
         this._lock.lock();
         return true;
      } else {
         return this._lock.tryLock();
      }
   }

   public boolean acquire() {
      return this.Lock_acquire(true);
   }

   public boolean acquire(boolean blocking) {
      return this.Lock_acquire(blocking);
   }

   final PyObject Lock___enter__() {
      this._lock.lock();
      return this;
   }

   public PyObject __enter__(ThreadState ts) {
      this._lock.lock();
      return this;
   }

   final void Lock_release() {
      this._lock.unlock();
   }

   public void release() {
      this.Lock_release();
   }

   final boolean Lock___exit__(PyObject type, PyObject value, PyObject traceback) {
      this._lock.unlock();
      return false;
   }

   public boolean __exit__(ThreadState ts, PyException exception) {
      this._lock.unlock();
      return false;
   }

   final boolean Lock_locked() {
      return this._lock.isLocked();
   }

   public boolean locked() {
      return this.Lock_locked();
   }

   final boolean Lock__is_owned() {
      return this._lock.isLocked();
   }

   public boolean _is_owned() {
      return this.Lock__is_owned();
   }

   public int getWaitQueueLength(java.util.concurrent.locks.Condition condition) {
      return this._lock.getWaitQueueLength(condition);
   }

   public String toString() {
      String owner = this._lock.getOwnerName();
      return Py.newString("<_threading.Lock owner=%r locked=%s>").__mod__(new PyTuple(new PyObject[]{(PyObject)(owner != null ? Py.newStringOrUnicode(owner) : Py.None), Py.newBoolean(this._lock.isLocked())})).toString();
   }

   static {
      PyType.addBuilder(Lock.class, new PyExposer());
      TYPE = PyType.fromClass(Lock.class);
   }

   private static class Lock_acquire_exposer extends PyBuiltinMethodNarrow {
      public Lock_acquire_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "";
      }

      public Lock_acquire_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Lock_acquire_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((Lock)this.self).Lock_acquire(Py.py2boolean(var1)));
      }

      public PyObject __call__() {
         return Py.newBoolean(((Lock)this.self).Lock_acquire((boolean)1));
      }
   }

   private static class Lock___enter___exposer extends PyBuiltinMethodNarrow {
      public Lock___enter___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public Lock___enter___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Lock___enter___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((Lock)this.self).Lock___enter__();
      }
   }

   private static class Lock_release_exposer extends PyBuiltinMethodNarrow {
      public Lock_release_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public Lock_release_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Lock_release_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         ((Lock)this.self).Lock_release();
         return Py.None;
      }
   }

   private static class Lock___exit___exposer extends PyBuiltinMethodNarrow {
      public Lock___exit___exposer(String var1) {
         super(var1, 4, 4);
         super.doc = "";
      }

      public Lock___exit___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Lock___exit___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newBoolean(((Lock)this.self).Lock___exit__(var1, var2, var3));
      }
   }

   private static class Lock_locked_exposer extends PyBuiltinMethodNarrow {
      public Lock_locked_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public Lock_locked_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Lock_locked_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((Lock)this.self).Lock_locked());
      }
   }

   private static class Lock__is_owned_exposer extends PyBuiltinMethodNarrow {
      public Lock__is_owned_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public Lock__is_owned_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Lock__is_owned_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((Lock)this.self).Lock__is_owned());
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
         String var10000 = ((Lock)this.self).toString();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return Lock.Lock___new__(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new Lock_acquire_exposer("acquire"), new Lock___enter___exposer("__enter__"), new Lock_release_exposer("release"), new Lock___exit___exposer("__exit__"), new Lock_locked_exposer("locked"), new Lock__is_owned_exposer("_is_owned"), new toString_exposer("toString")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("_threading.Lock", Lock.class, Object.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
