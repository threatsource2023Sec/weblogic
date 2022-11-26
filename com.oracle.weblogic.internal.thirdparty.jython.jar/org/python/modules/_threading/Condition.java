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
import org.python.core.Traverseproc;
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "_threading.Condition"
)
public class Condition extends PyObject implements ContextManager, Traverseproc {
   public static final PyType TYPE;
   private final ConditionSupportingLock _lock;
   private final java.util.concurrent.locks.Condition _condition;

   public Condition() {
      this(new RLock());
   }

   public Condition(ConditionSupportingLock lock) {
      this._lock = lock;
      this._condition = lock.getLock().newCondition();
   }

   @ExposedNew
   static final PyObject Condition___new__(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      int nargs = args.length;
      return nargs == 1 ? new Condition((ConditionSupportingLock)args[0]) : new Condition();
   }

   public boolean acquire() {
      return this.Condition_acquire(true);
   }

   public boolean acquire(boolean blocking) {
      return this.Condition_acquire(blocking);
   }

   final boolean Condition_acquire(boolean blocking) {
      return this._lock.acquire(blocking);
   }

   public PyObject __enter__(ThreadState ts) {
      this._lock.acquire();
      return this;
   }

   final PyObject Condition___enter__() {
      this.Condition_acquire(true);
      return this;
   }

   public void release() {
      this.Condition_release();
   }

   final void Condition_release() {
      this._lock.release();
   }

   public boolean __exit__(ThreadState ts, PyException exception) {
      this._lock.release();
      return false;
   }

   final boolean Condition___exit__(PyObject type, PyObject value, PyObject traceback) {
      this.Condition_release();
      return false;
   }

   public void wait$(PyObject timeout) throws InterruptedException {
      this.Condition_wait(timeout);
   }

   final void Condition_wait(PyObject timeout) throws InterruptedException {
      try {
         if (timeout == Py.None) {
            this._condition.await();
         } else {
            long nanos = (long)(timeout.asDouble() * 1.0E9);
            this._condition.awaitNanos(nanos);
         }

      } catch (IllegalMonitorStateException var5) {
         throw Py.RuntimeError("cannot wait on un-acquired lock");
      }
   }

   public void notify$() {
      this.Condition_notify(1);
   }

   final void Condition_notify(int count) {
      try {
         for(int i = 0; i < count; ++i) {
            this._condition.signal();
         }

      } catch (IllegalMonitorStateException var3) {
         throw Py.RuntimeError("cannot notify on un-acquired lock");
      }
   }

   public void notifyAll$() {
      this.Condition_notifyAll();
   }

   final void Condition_notifyAll() {
      try {
         this._condition.signalAll();
      } catch (IllegalMonitorStateException var2) {
         throw Py.RuntimeError("cannot notify on un-acquired lock");
      }
   }

   final void Condition_notify_all() {
      this._condition.signalAll();
   }

   public boolean _is_owned() {
      return this.Condition__is_owned();
   }

   final boolean Condition__is_owned() {
      return this._lock._is_owned();
   }

   public String toString() {
      int count = 0;

      try {
         count = this._lock.getWaitQueueLength(this._condition);
      } catch (IllegalMonitorStateException var3) {
      }

      return Py.newString("<_threading.Condition(%s, %d)>").__mod__(new PyTuple(new PyObject[]{Py.newString(this._lock.toString()), Py.newInteger(count)})).toString();
   }

   public int traverse(Visitproc visit, Object arg) {
      return this._lock != null ? visit.visit((PyObject)this._lock, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && this._lock == ob;
   }

   static {
      PyType.addBuilder(Condition.class, new PyExposer());
      TYPE = PyType.fromClass(Condition.class);
   }

   private static class Condition_acquire_exposer extends PyBuiltinMethodNarrow {
      public Condition_acquire_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "";
      }

      public Condition_acquire_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Condition_acquire_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((Condition)this.self).Condition_acquire(Py.py2boolean(var1)));
      }

      public PyObject __call__() {
         return Py.newBoolean(((Condition)this.self).Condition_acquire((boolean)1));
      }
   }

   private static class Condition___enter___exposer extends PyBuiltinMethodNarrow {
      public Condition___enter___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public Condition___enter___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Condition___enter___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((Condition)this.self).Condition___enter__();
      }
   }

   private static class Condition_release_exposer extends PyBuiltinMethodNarrow {
      public Condition_release_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public Condition_release_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Condition_release_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         ((Condition)this.self).Condition_release();
         return Py.None;
      }
   }

   private static class Condition___exit___exposer extends PyBuiltinMethodNarrow {
      public Condition___exit___exposer(String var1) {
         super(var1, 4, 4);
         super.doc = "";
      }

      public Condition___exit___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Condition___exit___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newBoolean(((Condition)this.self).Condition___exit__(var1, var2, var3));
      }
   }

   private static class Condition_wait_exposer extends PyBuiltinMethodNarrow {
      public Condition_wait_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "";
      }

      public Condition_wait_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Condition_wait_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((Condition)this.self).Condition_wait(var1);
         return Py.None;
      }

      public PyObject __call__() {
         ((Condition)this.self).Condition_wait(Py.None);
         return Py.None;
      }
   }

   private static class Condition_notify_exposer extends PyBuiltinMethodNarrow {
      public Condition_notify_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "";
      }

      public Condition_notify_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Condition_notify_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((Condition)this.self).Condition_notify(Py.py2int(var1));
         return Py.None;
      }

      public PyObject __call__() {
         ((Condition)this.self).Condition_notify(1);
         return Py.None;
      }
   }

   private static class Condition_notifyAll_exposer extends PyBuiltinMethodNarrow {
      public Condition_notifyAll_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public Condition_notifyAll_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Condition_notifyAll_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         ((Condition)this.self).Condition_notifyAll();
         return Py.None;
      }
   }

   private static class Condition_notify_all_exposer extends PyBuiltinMethodNarrow {
      public Condition_notify_all_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public Condition_notify_all_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Condition_notify_all_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         ((Condition)this.self).Condition_notify_all();
         return Py.None;
      }
   }

   private static class Condition__is_owned_exposer extends PyBuiltinMethodNarrow {
      public Condition__is_owned_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public Condition__is_owned_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Condition__is_owned_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((Condition)this.self).Condition__is_owned());
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return Condition.Condition___new__(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new Condition_acquire_exposer("acquire"), new Condition___enter___exposer("__enter__"), new Condition_release_exposer("release"), new Condition___exit___exposer("__exit__"), new Condition_wait_exposer("wait"), new Condition_notify_exposer("notify"), new Condition_notifyAll_exposer("notifyAll"), new Condition_notify_all_exposer("notify_all"), new Condition__is_owned_exposer("_is_owned")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("_threading.Condition", Condition.class, Object.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
