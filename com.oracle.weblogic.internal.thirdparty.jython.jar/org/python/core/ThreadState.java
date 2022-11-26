package org.python.core;

public class ThreadState {
   public PyFrame frame;
   public PyException exception;
   public int call_depth;
   public boolean tracing;
   public PyList reprStack;
   public int compareStateNesting;
   public TraceFunction tracefunc;
   public TraceFunction profilefunc;
   private PyDictionary compareStateDict;
   private PySystemStateRef systemStateRef;

   public ThreadState(PySystemState systemState) {
      this.setSystemState(systemState);
   }

   public void setSystemState(PySystemState systemState) {
      if (systemState == null) {
         systemState = Py.defaultSystemState;
      }

      if (this.systemStateRef == null || this.systemStateRef.get() != systemState) {
         this.systemStateRef = new PySystemStateRef(systemState, this);
      }

   }

   public PySystemState getSystemState() {
      PySystemState systemState = this.systemStateRef == null ? null : (PySystemState)this.systemStateRef.get();
      return systemState == null ? Py.defaultSystemState : systemState;
   }

   public boolean enterRepr(PyObject obj) {
      if (this.reprStack == null) {
         this.reprStack = new PyList(new PyObject[]{obj});
         return true;
      } else {
         for(int i = this.reprStack.size() - 1; i >= 0; --i) {
            if (obj == this.reprStack.pyget(i)) {
               return false;
            }
         }

         this.reprStack.append(obj);
         return true;
      }
   }

   public void exitRepr(PyObject obj) {
      if (this.reprStack != null) {
         for(int i = this.reprStack.size() - 1; i >= 0; --i) {
            if (this.reprStack.pyget(i) == obj) {
               this.reprStack.delRange(i, this.reprStack.size());
            }
         }

      }
   }

   public PyDictionary getCompareStateDict() {
      if (this.compareStateDict == null) {
         this.compareStateDict = new PyDictionary();
      }

      return this.compareStateDict;
   }
}
