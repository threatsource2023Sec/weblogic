package org.python.core.finalization;

import org.python.core.JyAttribute;
import org.python.core.PyObject;
import org.python.modules.gc;

public class FinalizeTrigger {
   public static final byte NOTIFY_GC_FLAG = 1;
   public static final byte NOT_FINALIZABLE_FLAG = 8;
   public static final byte ONLY_BUILTIN_FLAG = 16;
   public static final byte FINALIZED_FLAG = 32;
   public static FinalizeTriggerFactory factory;
   protected PyObject toFinalize;
   public byte flags = 0;

   public static FinalizeTrigger makeTrigger(PyObject toFinalize) {
      return factory != null ? factory.makeTrigger(toFinalize) : new FinalizeTrigger(toFinalize);
   }

   public static boolean hasActiveTrigger(PyObject obj) {
      Object fn = JyAttribute.getAttr(obj, (byte)127);
      return fn != null && ((FinalizeTrigger)fn).isActive();
   }

   public static boolean isFinalizable(PyObject obj) {
      return obj instanceof FinalizablePyObject || obj instanceof FinalizableBuiltin || obj instanceof FinalizablePyObjectDerived;
   }

   public static void ensureFinalizer(PyObject resurrect) {
      JyAttribute.setAttr(resurrect, (byte)127, makeTrigger(resurrect));
   }

   public static void runFinalizer(PyObject toFinalize) {
      runFinalizer(toFinalize, false);
   }

   public static void runFinalizer(PyObject toFinalize, boolean runBuiltinOnly) {
      if (!runBuiltinOnly) {
         if (toFinalize instanceof FinalizablePyObjectDerived) {
            try {
               ((FinalizablePyObjectDerived)toFinalize).__del_derived__();
            } catch (Exception var5) {
            }
         } else if (toFinalize instanceof FinalizablePyObject) {
            try {
               ((FinalizablePyObject)toFinalize).__del__();
            } catch (Exception var4) {
            }
         }
      }

      if (toFinalize instanceof FinalizableBuiltin) {
         try {
            ((FinalizableBuiltin)toFinalize).__del_builtin__();
         } catch (Exception var3) {
         }
      }

   }

   public static void appendFinalizeTriggerForBuiltin(PyObject obj) {
      if (obj instanceof FinalizableBuiltin) {
         FinalizeTrigger ft = makeTrigger(obj);
         ft.flags = 16;
         JyAttribute.setAttr(obj, (byte)127, ft);
      } else {
         JyAttribute.delAttr(obj, (byte)127);
      }

   }

   public void clear() {
      this.toFinalize = null;
   }

   public void trigger(PyObject toFinalize) {
      this.toFinalize = toFinalize;
   }

   public boolean isActive() {
      return this.toFinalize != null;
   }

   protected FinalizeTrigger(PyObject toFinalize) {
      this.toFinalize = toFinalize;
   }

   protected boolean isCyclic() {
      gc.CycleMarkAttr cm = (gc.CycleMarkAttr)JyAttribute.getAttr(this.toFinalize, (byte)5);
      if (cm != null && cm.isCyclic()) {
         return true;
      } else {
         gc.markCyclicObjects(this.toFinalize, (this.flags & 8) == 0);
         cm = (gc.CycleMarkAttr)JyAttribute.getAttr(this.toFinalize, (byte)5);
         return cm != null && cm.isCyclic();
      }
   }

   protected boolean isUncollectable() {
      gc.CycleMarkAttr cm = (gc.CycleMarkAttr)JyAttribute.getAttr(this.toFinalize, (byte)5);
      if (cm != null && cm.isUncollectable()) {
         return true;
      } else {
         gc.markCyclicObjects(this.toFinalize, (this.flags & 8) == 0);
         cm = (gc.CycleMarkAttr)JyAttribute.getAttr(this.toFinalize, (byte)5);
         return cm != null && cm.isUncollectable();
      }
   }

   public void performFinalization() {
      if (this.toFinalize != null) {
         byte saveGarbage = 0;
         if ((gc.getJythonGCFlags() & 2) != 0) {
            if (this.isUncollectable()) {
               saveGarbage = 1;
            } else if (!this.isCyclic()) {
               saveGarbage = -1;
               runFinalizer(this.toFinalize, (this.flags & 16) != 0);
            }
         } else if ((this.flags & 8) == 0) {
            runFinalizer(this.toFinalize, (this.flags & 16) != 0);
         }

         if ((gc.getJythonGCFlags() & 8192) != 0) {
            gc.writeDebug("gc", "finalization of " + this.toFinalize);
         }

         if (saveGarbage == 1 || saveGarbage == 0 && (gc.get_debug() & 32) != 0 && this.isCyclic()) {
            if ((this.flags & 8) == 0) {
               appendFinalizeTriggerForBuiltin(this.toFinalize);
            }

            gc.garbage.add(this.toFinalize);
            if ((gc.getJythonGCFlags() & 8192) != 0) {
               gc.writeDebug("gc", this.toFinalize + " added to garbage.");
            }
         }
      }

      if ((this.flags & 1) != 0) {
         if ((gc.getJythonGCFlags() & 8192) != 0) {
            gc.writeDebug("gc", "notify finalization of " + this.toFinalize);
         }

         gc.notifyFinalize(this.toFinalize);
         this.flags &= -2;
      }

   }

   protected void finalize() throws Throwable {
      this.flags = (byte)(this.flags | 32);
      gc.notifyPreFinalization();
      if (gc.delayedFinalizationEnabled() && this.toFinalize != null) {
         if ((gc.getJythonGCFlags() & 8192) != 0) {
            gc.writeDebug("gc", "delayed finalization for " + this.toFinalize);
         }

         gc.registerForDelayedFinalization(this.toFinalize);
      } else {
         this.performFinalization();
      }

      gc.notifyPostFinalization();
   }

   public boolean isFinalized() {
      return (this.flags & 32) != 0;
   }
}
