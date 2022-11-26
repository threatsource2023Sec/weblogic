package org.python.core;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

public class PyException extends RuntimeException implements Traverseproc {
   public PyObject type;
   public PyObject value;
   public PyTraceback traceback;
   private boolean isReRaise;
   private boolean normalized;
   private boolean printingStackTrace;

   public PyException() {
      this(Py.None, Py.None);
   }

   public PyException(PyObject type) {
      this(type, Py.None);
   }

   public PyException(PyObject type, PyObject value) {
      this(type, value, (PyTraceback)null);
   }

   public PyException(PyObject type, PyObject value, PyTraceback traceback) {
      this.value = Py.None;
      this.isReRaise = false;
      this.normalized = false;
      this.printingStackTrace = false;
      this.type = type;
      this.value = value;
      if (traceback != null) {
         this.traceback = traceback;
         this.isReRaise = true;
      } else {
         PyFrame frame = Py.getFrame();
         if (frame != null && frame.tracefunc != null) {
            frame.tracefunc = frame.tracefunc.traceException(frame, this);
         }
      }

   }

   public PyException(PyObject type, String value) {
      this(type, (PyObject)Py.newStringOrUnicode(value));
   }

   public void printStackTrace() {
      Py.printException(this);
   }

   public Throwable fillInStackTrace() {
      return (Throwable)(Options.includeJavaStackInExceptions ? super.fillInStackTrace() : this);
   }

   public synchronized void printStackTrace(PrintStream s) {
      if (this.printingStackTrace) {
         super.printStackTrace(s);
      } else {
         try {
            PyFile err = new PyFile(s);
            err.setEncoding("ascii", "backslashreplace");
            this.printingStackTrace = true;
            Py.displayException(this.type, this.value, this.traceback, err);
         } finally {
            this.printingStackTrace = false;
         }
      }

   }

   public synchronized void super__printStackTrace(PrintWriter w) {
      try {
         this.printingStackTrace = true;
         super.printStackTrace(w);
      } finally {
         this.printingStackTrace = false;
      }

   }

   public synchronized String toString() {
      return Py.exceptionToString(this.type, this.value, this.traceback);
   }

   public void normalize() {
      if (!this.normalized) {
         PyObject inClass = null;
         if (isExceptionInstance(this.value)) {
            inClass = this.value.fastGetClass();
         }

         if (isExceptionClass(this.type)) {
            if (inClass != null && Py.isSubClass(inClass, this.type)) {
               if (inClass != this.type) {
                  this.type = inClass;
               }
            } else {
               PyObject[] args;
               if (this.value == Py.None) {
                  args = Py.EmptyObjects;
               } else if (this.value instanceof PyTuple && this.type != Py.KeyError) {
                  args = ((PyTuple)this.value).getArray();
               } else {
                  args = new PyObject[]{this.value};
               }

               this.value = this.type.__call__(args);
            }
         }

         this.normalized = true;
      }
   }

   public void tracebackHere(PyFrame here) {
      this.tracebackHere(here, false);
   }

   public void tracebackHere(PyFrame here, boolean isFinally) {
      if (!this.isReRaise && here != null) {
         this.traceback = new PyTraceback(this.traceback, here);
      }

      this.isReRaise = isFinally;
   }

   public static PyException doRaise(PyObject type, PyObject value, PyObject traceback) {
      if (type == null) {
         ThreadState state = Py.getThreadState();
         type = state.exception.type;
         value = state.exception.value;
         traceback = state.exception.traceback;
      }

      if (traceback == Py.None) {
         traceback = null;
      } else if (traceback != null && !(traceback instanceof PyTraceback)) {
         throw Py.TypeError("raise: arg 3 must be a traceback or None");
      }

      if (value == null) {
         value = Py.None;
      }

      while(type instanceof PyTuple && ((PyTuple)type).size() > 0) {
         type = type.__getitem__(0);
      }

      if (isExceptionClass(type)) {
         PyException pye = new PyException(type, value, (PyTraceback)traceback);
         pye.normalize();
         if (!isExceptionInstance(pye.value)) {
            throw Py.TypeError(String.format("calling %s() should have returned an instance of BaseException, not '%s'", pye.type, pye.value));
         } else {
            return pye;
         }
      } else if (isExceptionInstance(type)) {
         if (value != Py.None) {
            throw Py.TypeError("instance exception may not have a separate value");
         } else {
            value = type;
            type = type.fastGetClass();
            if (Options.py3k_warning && type instanceof PyClass) {
               Py.DeprecationWarning("exceptions must derive from BaseException in 3.x");
            }

            return new PyException(type, value, (PyTraceback)traceback);
         }
      } else {
         throw Py.TypeError("exceptions must be old-style classes or derived from BaseException, not " + type.getType().fastGetName());
      }
   }

   public boolean match(PyObject exc) {
      if (exc instanceof PyTuple) {
         PyObject[] var2 = ((PyTuple)exc).getArray();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            PyObject item = var2[var4];
            if (this.match(item)) {
               return true;
            }
         }

         return false;
      } else {
         if (exc instanceof PyString) {
            Py.DeprecationWarning("catching of string exceptions is deprecated");
         } else if (Options.py3k_warning && !isPy3kExceptionClass(exc)) {
            Py.DeprecationWarning("catching classes that don't inherit from BaseException is not allowed in 3.x");
         }

         this.normalize();
         if (exc == Py.IOError && __builtin__.isinstance(this.value, PyType.fromClass(IOException.class))) {
            return true;
         } else if (exc == Py.MemoryError && __builtin__.isinstance(this.value, PyType.fromClass(OutOfMemoryError.class))) {
            return true;
         } else if (isExceptionClass(this.type) && isExceptionClass(exc)) {
            try {
               return Py.isSubClass(this.type, exc);
            } catch (PyException var6) {
               Py.writeUnraisable(var6, this.type);
               return false;
            }
         } else {
            return this.type == exc;
         }
      }
   }

   public static boolean isExceptionClass(PyObject obj) {
      return obj instanceof PyClass ? true : isPy3kExceptionClass(obj);
   }

   private static boolean isPy3kExceptionClass(PyObject obj) {
      if (!(obj instanceof PyType)) {
         return false;
      } else {
         PyType type = (PyType)obj;
         if (type.isSubType(PyBaseException.TYPE)) {
            return true;
         } else {
            return type.getProxyType() != null && Throwable.class.isAssignableFrom(type.getProxyType());
         }
      }
   }

   public static boolean isExceptionInstance(PyObject obj) {
      return obj instanceof PyInstance || obj instanceof PyBaseException || obj.getJavaProxy() instanceof Throwable;
   }

   public static String exceptionClassName(PyObject obj) {
      return obj instanceof PyClass ? ((PyClass)obj).__name__ : ((PyType)obj).fastGetName();
   }

   public int traverse(Visitproc visit, Object arg) {
      int retValue;
      if (this.type != null) {
         retValue = visit.visit(this.type, arg);
         if (retValue != 0) {
            return retValue;
         }
      }

      if (this.value != null) {
         retValue = visit.visit(this.value, arg);
         if (retValue != 0) {
            return retValue;
         }
      }

      if (this.traceback != null) {
         retValue = visit.visit(this.traceback, arg);
         if (retValue != 0) {
            return retValue;
         }
      }

      return 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (this.type == ob || this.value == ob || this.traceback == ob);
   }
}
