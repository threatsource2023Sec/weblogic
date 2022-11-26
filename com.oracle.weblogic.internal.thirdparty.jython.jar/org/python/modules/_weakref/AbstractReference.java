package org.python.modules._weakref;

import org.python.core.JyAttribute;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyType;
import org.python.core.Traverseproc;
import org.python.core.Visitproc;
import org.python.modules.gc;

public abstract class AbstractReference extends PyObject implements Traverseproc {
   PyObject callback;
   protected ReferenceBackend gref;

   public AbstractReference(PyType subType, ReferenceBackend gref, PyObject callback) {
      super(subType);
      this.gref = gref;
      this.callback = callback;
      gref.add(this);
   }

   void call() {
      if (this.callback != null) {
         try {
            this.callback.__call__((PyObject)this);
         } catch (Exception var2) {
            Py.writeUnraisable(var2, this.callback);
         }

      }
   }

   protected PyObject py() {
      PyObject o = this.get();
      if (o == null) {
         throw Py.ReferenceError("weakly-referenced object no longer exists");
      } else {
         return o;
      }
   }

   public boolean equals(Object ob_other) {
      return ob_other == this;
   }

   public boolean hasCallback() {
      return this.callback != null;
   }

   public int hashCode() {
      return this.gref.pythonHashCode();
   }

   public PyObject __eq__(PyObject other) {
      if (other.getClass() != this.getClass()) {
         return null;
      } else {
         PyObject pythis = this.get();
         PyObject pyother = ((AbstractReference)other).get();
         if (pythis != null && pyother != null) {
            return pythis._eq(pyother);
         } else {
            return this == other ? Py.True : Py.False;
         }
      }
   }

   public PyObject __ne__(PyObject other) {
      if (other.getClass() != this.getClass()) {
         return Py.True;
      } else {
         PyObject pythis = this.get();
         PyObject pyother = ((AbstractReference)other).get();
         if (pythis != null && pyother != null) {
            return pythis._eq(pyother).__not__();
         } else {
            return this == other ? Py.False : Py.True;
         }
      }
   }

   protected PyObject get() {
      PyObject result = this.gref.get();
      if (result == null && gc.delayedWeakrefCallbacksEnabled()) {
         if (this.gref.isCleared()) {
            return null;
         } else {
            if ((gc.getJythonGCFlags() & 2048) != 0) {
               gc.writeDebug("gc", "pending in get of abstract ref " + this + ": " + Thread.currentThread().getId());
            }

            JyAttribute.setAttr(this, (byte)3, Thread.currentThread());

            for(; !this.gref.isCleared() && result == null; result = this.gref.get()) {
               try {
                  Thread.sleep(2000L);
               } catch (InterruptedException var3) {
               }
            }

            JyAttribute.delAttr(this, (byte)3);
            if ((gc.getJythonGCFlags() & 2048) != 0) {
               gc.writeDebug("gc", "pending of " + this + " resolved: " + Thread.currentThread().getId());
               if (this.gref.isCleared()) {
                  gc.writeDebug("gc", "reference was cleared.");
               } else if (result != null) {
                  gc.writeDebug("gc", "reference was restored.");
               } else {
                  gc.writeDebug("gc", "something went very wrong.");
               }
            }

            return result;
         }
      } else {
         return result;
      }
   }

   public int traverse(Visitproc visit, Object arg) {
      return this.callback != null ? visit.visit(this.callback, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && this.callback == ob;
   }
}
