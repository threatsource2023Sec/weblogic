package org.python.modules;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import org.python.core.JyAttribute;
import org.python.core.Py;
import org.python.core.PyInstance;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PySequenceList;
import org.python.core.PyString;
import org.python.core.Traverseproc;
import org.python.core.TraverseprocDerived;
import org.python.core.Untraversable;
import org.python.core.Visitproc;
import org.python.core.finalization.FinalizeTrigger;
import org.python.modules._weakref.GlobalRef;
import org.python.modules._weakref.ReferenceBackend;

public class gc {
   public static final int UNKNOWN_COUNT = -2;
   public static final short MONITOR_GLOBAL = 1;
   public static final short DONT_FINALIZE_CYCLIC_GARBAGE = 2;
   public static final short PRESERVE_WEAKREFS_ON_RESURRECTION = 4;
   public static final short DONT_FINALIZE_RESURRECTED_OBJECTS = 8;
   public static final short FORCE_DELAYED_FINALIZATION = 16;
   public static final short FORCE_DELAYED_WEAKREF_CALLBACKS = 32;
   public static final short DONT_TRAVERSE_BY_REFLECTION = 64;
   public static final short SUPPRESS_TRAVERSE_BY_REFLECTION_WARNING = 128;
   public static final short INSTANCE_TRAVERSE_BY_REFLECTION_WARNING = 256;
   public static final short USE_PY_WRITE_DEBUG = 512;
   public static final short VERBOSE_COLLECT = 1024;
   public static final short VERBOSE_WEAKREF = 2048;
   public static final short VERBOSE_DELAYED = 4096;
   public static final short VERBOSE_FINALIZE = 8192;
   public static final short VERBOSE = 15360;
   public static final int DEBUG_STATS = 1;
   public static final int DEBUG_COLLECTABLE = 2;
   public static final int DEBUG_UNCOLLECTABLE = 4;
   public static final int DEBUG_INSTANCES = 8;
   public static final int DEBUG_OBJECTS = 16;
   public static final int DEBUG_SAVEALL = 32;
   public static final int DEBUG_LEAK = 62;
   private static short gcFlags = 0;
   private static int debugFlags = 0;
   private static boolean monitorNonTraversable = false;
   private static boolean waitingForFinalizers = false;
   private static AtomicBoolean gcRunning = new AtomicBoolean(false);
   private static HashSet monitoredObjects;
   private static HashSet reflectionWarnedClasses;
   private static ReferenceQueue gcTrash;
   private static int finalizeWaitCount = 0;
   private static int initWaitTime = 10;
   private static int defaultWaitFactor = 2;
   private static long lastRemoveTimeStamp = -1L;
   private static long maxWaitTime;
   private static int gcMonitoredRunCount;
   public static long gcRecallTime;
   public static PyList garbage;
   private static List preFinalizationProcess;
   private static List postFinalizationProcess;
   private static List preFinalizationProcessRemove;
   private static List postFinalizationProcessRemove;
   private static Thread postFinalizationProcessor;
   public static long postFinalizationTimeOut;
   private static long postFinalizationTimestamp;
   private static int openFinalizeCount;
   private static boolean postFinalizationPending;
   private static boolean lockPostFinalization;
   private static IdentityHashMap delayedFinalizables;
   private static IdentityHashMap resurrectionCriticals;
   private static int abortedCyclicFinalizers;
   private static final byte DO_NOTHING_SPECIAL = 0;
   private static final byte MARK_REACHABLE_CRITICALS = 1;
   private static final byte NOTIFY_FOR_RERUN = 2;
   private static byte delayedFinalizationMode;
   private static boolean notifyRerun;
   public static final String __doc__ = "This module provides access to the garbage collector for reference cycles.\n\nenable() -- Enable automatic garbage collection (does nothing in Jython).\ndisable() -- Disable automatic garbage collection (raises NotImplementedError in Jython).\nisenabled() -- Returns True because Java garbage collection cannot be disabled.\ncollect() -- Do a full collection right now (potentially expensive).\nget_count() -- Return the current collection counts (raises NotImplementedError in Jython).\nset_debug() -- Set debugging flags.\nget_debug() -- Get debugging flags.\nset_threshold() -- Set the collection thresholds (raise NotImplementedError in Jython).\nget_threshold() -- Return the current the collection thresholds (raise NotImplementedError in Jython).\nget_objects() -- Return a list of all objects tracked by the collector (raises NotImplementedError in Jython).\nis_tracked() -- Returns true if a given object is tracked (i.e. monitored in Jython).\nget_referrers() -- Return the list of objects that refer to an object (only finds monitored referrers in Jython).\nget_referents() -- Return the list of objects that an object refers to.\n";
   public static final String __name__ = "gc";
   public static final PyString __doc__enable;
   public static final PyString __doc__disable;
   public static final PyString __doc__isenabled;
   public static final PyString __doc__collect;
   public static final PyString __doc__get_count;
   public static final PyString __doc__set_debug;
   public static final PyString __doc__get_debug;
   public static final PyString __doc__set_thresh;
   public static final PyString __doc__get_thresh;
   public static final PyString __doc__get_objects;
   public static final PyString __doc__is_tracked;
   public static final PyString __doc__get_referrers;
   public static final PyString __doc__get_referents;

   public static void writeDebug(String type, String msg) {
      if ((gcFlags & 512) != 0) {
         Py.writeDebug(type, msg);
      } else {
         System.err.println(type + ": " + msg);
      }

   }

   public static void restoreFinalizer(PyObject obj) {
      FinalizeTrigger ft = (FinalizeTrigger)JyAttribute.getAttr(obj, (byte)127);
      boolean notify = false;
      if (ft != null) {
         FinalizeTrigger.ensureFinalizer(obj);
         ft.clear();
         ((FinalizeTrigger)JyAttribute.getAttr(obj, (byte)127)).flags = ft.flags;
         notify = (ft.flags & 1) != 0;
      }

      if ((gcFlags & 4096) != 0 || (gcFlags & 8192) != 0) {
         writeDebug("gc", "restore finalizer of " + obj);
      }

      CycleMarkAttr cm = (CycleMarkAttr)JyAttribute.getAttr(obj, (byte)5);
      if (cm != null && cm.monitored) {
         monitorObject(obj, true);
      }

      if (notify) {
         boolean cyclic;
         if (cm != null && cm.isUncollectable()) {
            cyclic = true;
         } else {
            markCyclicObjects(obj, true);
            cm = (CycleMarkAttr)JyAttribute.getAttr(obj, (byte)5);
            cyclic = cm != null && cm.isUncollectable();
         }

         if ((gcFlags & 4096) != 0 || (gcFlags & 8192) != 0) {
            writeDebug("gc", "notify finalizer abort;  cyclic? " + cyclic);
         }

         notifyAbortFinalize(obj, cyclic);
      }

   }

   public static void restoreWeakReferences(PyObject rst) {
      ReferenceBackend toRestore = (ReferenceBackend)JyAttribute.getAttr(rst, (byte)0);
      if (toRestore != null) {
         toRestore.restore(rst);
      }

   }

   public static boolean delayedWeakrefCallbacksEnabled() {
      return (gcFlags & 36) != 0;
   }

   public static boolean delayedFinalizationEnabled() {
      return (gcFlags & 28) != 0;
   }

   private static void updateDelayedFinalizationState() {
      if (!delayedFinalizationEnabled() && !delayedWeakrefCallbacksEnabled()) {
         if (indexOfPostFinalizationProcess(gc.DelayedFinalizationProcess.defaultInstance) != -1) {
            suspendDelayedFinalization();
         }
      } else {
         resumeDelayedFinalization();
      }

      if (!delayedWeakrefCallbacksEnabled() && GlobalRef.hasDelayedCallbacks()) {
         Thread dlcProcess = new Thread() {
            public void run() {
               GlobalRef.processDelayedCallbacks();
            }
         };
         dlcProcess.start();
      }

   }

   private static void resumeDelayedFinalization() {
      if (delayedFinalizables == null) {
         delayedFinalizables = new IdentityHashMap();
      }

      if (resurrectionCriticals == null) {
         resurrectionCriticals = new IdentityHashMap();
      }

      try {
         synchronized(postFinalizationProcessRemove) {
            postFinalizationProcessRemove.remove(gc.DelayedFinalizationProcess.defaultInstance);
            if (postFinalizationProcessRemove.isEmpty()) {
               postFinalizationProcessRemove = null;
            }
         }
      } catch (NullPointerException var3) {
      }

      if (indexOfPostFinalizationProcess(gc.DelayedFinalizationProcess.defaultInstance) == -1) {
         registerPostFinalizationProcess(gc.DelayedFinalizationProcess.defaultInstance);
      }

   }

   private static void suspendDelayedFinalization() {
      unregisterPostFinalizationProcessAfterNextRun(gc.DelayedFinalizationProcess.defaultInstance);
   }

   private static boolean isResurrectionCritical(PyObject ob) {
      return isTraversable(ob) && FinalizeTrigger.hasActiveTrigger(ob);
   }

   public static void registerForDelayedFinalization(PyObject ob) {
      if (isResurrectionCritical(ob)) {
         resurrectionCriticals.put(ob, ob);
      } else {
         delayedFinalizables.put(ob, ob);
      }

   }

   public static void abortDelayedFinalization(PyObject ob) {
      resurrectionCriticals.remove(ob);
      delayedFinalizables.remove(ob);
      if ((gcFlags & 4096) != 0 || (gcFlags & 8192) != 0) {
         writeDebug("gc", "abort delayed finalization of " + ob);
      }

      restoreFinalizer(ob);
   }

   public static void registerPreFinalizationProcess(Runnable process) {
      registerPreFinalizationProcess(process, -1);
   }

   public static void registerPreFinalizationProcess(Runnable process, int index) {
      while(true) {
         try {
            synchronized(preFinalizationProcess) {
               preFinalizationProcess.add(index < 0 ? index + preFinalizationProcess.size() + 1 : index, process);
               return;
            }
         } catch (NullPointerException var5) {
            preFinalizationProcess = new ArrayList(1);
         }
      }
   }

   public static int indexOfPreFinalizationProcess(Runnable process) {
      try {
         synchronized(preFinalizationProcess) {
            return preFinalizationProcess.indexOf(process);
         }
      } catch (NullPointerException var4) {
         return -1;
      }
   }

   public static boolean unregisterPreFinalizationProcess(Runnable process) {
      try {
         synchronized(preFinalizationProcess) {
            boolean result = preFinalizationProcess.remove(process);
            if (result && preFinalizationProcess.isEmpty()) {
               preFinalizationProcess = null;
            }

            return result;
         }
      } catch (NullPointerException var5) {
         return false;
      }
   }

   public static void unregisterPreFinalizationProcessAfterNextRun(Runnable process) {
      while(true) {
         try {
            synchronized(preFinalizationProcessRemove) {
               preFinalizationProcessRemove.add(process);
               return;
            }
         } catch (NullPointerException var4) {
            preFinalizationProcessRemove = new ArrayList(1);
         }
      }
   }

   public static void registerPostFinalizationProcess(Runnable process) {
      registerPostFinalizationProcess(process, -1);
   }

   public static void registerPostFinalizationProcess(Runnable process, int index) {
      while(true) {
         try {
            synchronized(postFinalizationProcess) {
               postFinalizationProcess.add(index < 0 ? index + postFinalizationProcess.size() + 1 : index, process);
               return;
            }
         } catch (NullPointerException var5) {
            postFinalizationProcess = new ArrayList(1);
         }
      }
   }

   public static int indexOfPostFinalizationProcess(Runnable process) {
      try {
         synchronized(postFinalizationProcess) {
            return postFinalizationProcess.indexOf(process);
         }
      } catch (NullPointerException var4) {
         return -1;
      }
   }

   public static boolean unregisterPostFinalizationProcess(Runnable process) {
      try {
         synchronized(postFinalizationProcess) {
            boolean result = postFinalizationProcess.remove(process);
            if (result && postFinalizationProcess.isEmpty()) {
               postFinalizationProcess = null;
            }

            return result;
         }
      } catch (NullPointerException var5) {
         return false;
      }
   }

   public static void unregisterPostFinalizationProcessAfterNextRun(Runnable process) {
      while(true) {
         try {
            synchronized(postFinalizationProcessRemove) {
               postFinalizationProcessRemove.add(process);
               return;
            }
         } catch (NullPointerException var4) {
            postFinalizationProcessRemove = new ArrayList(1);
         }
      }
   }

   public static void notifyPreFinalization() {
      long callTime = System.currentTimeMillis();
      ++openFinalizeCount;
      if (callTime - postFinalizationTimestamp >= postFinalizationTimeOut) {
         try {
            synchronized(preFinalizationProcess) {
               Iterator var3 = preFinalizationProcess.iterator();

               while(var3.hasNext()) {
                  Runnable r = (Runnable)var3.next();

                  try {
                     r.run();
                  } catch (Exception var13) {
                     Py.writeError("gc", "Finalization preprocess " + r + " caused error: " + var13);
                  }
               }

               try {
                  synchronized(preFinalizationProcessRemove) {
                     preFinalizationProcess.removeAll(preFinalizationProcessRemove);
                     preFinalizationProcessRemove = null;
                  }

                  if (preFinalizationProcess.isEmpty()) {
                     preFinalizationProcess = null;
                  }
               } catch (NullPointerException var12) {
               }
            }
         } catch (NullPointerException var15) {
            preFinalizationProcessRemove = null;
         }

         try {
            synchronized(postFinalizationProcess) {
               if (!postFinalizationProcess.isEmpty() && postFinalizationProcessor == null) {
                  postFinalizationPending = true;
                  postFinalizationProcessor = new Thread(gc.PostFinalizationProcessor.defaultInstance);
                  postFinalizationProcessor.start();
               }
            }
         } catch (NullPointerException var10) {
         }

      }
   }

   public static void notifyPostFinalization() {
      postFinalizationTimestamp = System.currentTimeMillis();
      --openFinalizeCount;
      if (openFinalizeCount == 0 && postFinalizationProcessor != null) {
         postFinalizationProcessor.interrupt();
      }

   }

   protected static void postFinalizationProcess() {
      try {
         synchronized(postFinalizationProcess) {
            Iterator var1 = postFinalizationProcess.iterator();

            while(var1.hasNext()) {
               Runnable r = (Runnable)var1.next();

               try {
                  r.run();
               } catch (Exception var8) {
                  System.err.println("Finalization postprocess " + r + " caused error:");
                  System.err.println(var8);
               }
            }

            try {
               synchronized(postFinalizationProcessRemove) {
                  postFinalizationProcess.removeAll(postFinalizationProcessRemove);
                  postFinalizationProcessRemove = null;
               }

               if (postFinalizationProcess.isEmpty()) {
                  postFinalizationProcess = null;
               }
            } catch (NullPointerException var7) {
            }
         }
      } catch (NullPointerException var10) {
         postFinalizationProcessRemove = null;
      }

   }

   public static void monitorObject(PyObject ob) {
      monitorObject(ob, false);
   }

   public static void monitorObject(PyObject ob, boolean initString) {
      if (ob != null && ob != garbage) {
         if (monitorNonTraversable || ob instanceof Traverseproc || ob instanceof TraverseprocDerived || !ob.getClass().isAnnotationPresent(Untraversable.class) || JyAttribute.hasAttr(ob, (byte)127)) {
            if (gcTrash == null) {
               gcTrash = new ReferenceQueue();
            }

            while(true) {
               try {
                  synchronized(monitoredObjects) {
                     if (!isMonitored(ob)) {
                        CycleMarkAttr cm = new CycleMarkAttr();
                        JyAttribute.setAttr(ob, (byte)5, cm);
                        WeakReferenceGC refPut = new WeakReferenceGC(ob, gcTrash);
                        if (initString) {
                           refPut.initStr(ob);
                        }

                        monitoredObjects.add(refPut);
                        cm.monitored = true;
                     }

                     return;
                  }
               } catch (NullPointerException var7) {
                  monitoredObjects = new HashSet();
               }
            }
         }
      }
   }

   public static WeakReferenceGC getMonitorReference(PyObject ob) {
      try {
         synchronized(monitoredObjects) {
            Iterator var2 = monitoredObjects.iterator();

            while(var2.hasNext()) {
               WeakReferenceGC ref = (WeakReferenceGC)var2.next();
               if (ref.equals(ob)) {
                  return ref;
               }
            }
         }
      } catch (NullPointerException var6) {
      }

      return null;
   }

   public static boolean isMonitoring() {
      try {
         synchronized(monitoredObjects) {
            return !monitoredObjects.isEmpty();
         }
      } catch (NullPointerException var3) {
         return false;
      }
   }

   public static boolean isMonitored(PyObject ob) {
      try {
         synchronized(monitoredObjects) {
            gc.WeakrefGCCompareDummy.defaultInstance.setCompare(ob);
            boolean result = monitoredObjects.contains(gc.WeakrefGCCompareDummy.defaultInstance);
            gc.WeakrefGCCompareDummy.defaultInstance.clearCompare();
            return result;
         }
      } catch (NullPointerException var5) {
         return false;
      }
   }

   public static boolean unmonitorObject(PyObject ob) {
      try {
         synchronized(monitoredObjects) {
            gc.WeakrefGCCompareDummy.defaultInstance.setCompare(ob);
            WeakReferenceGC rem = getMonitorReference(ob);
            if (rem != null) {
               rem.clear();
            }

            boolean result = monitoredObjects.remove(gc.WeakrefGCCompareDummy.defaultInstance);
            gc.WeakrefGCCompareDummy.defaultInstance.clearCompare();
            JyAttribute.delAttr(ob, (byte)5);
            FinalizeTrigger ft = (FinalizeTrigger)JyAttribute.getAttr(ob, (byte)127);
            if (ft != null) {
               ft.flags &= -2;
            }

            return result;
         }
      } catch (NullPointerException var7) {
         return false;
      }
   }

   public static void unmonitorAll() {
      try {
         synchronized(monitoredObjects) {
            WeakReferenceGC mo;
            for(Iterator var1 = monitoredObjects.iterator(); var1.hasNext(); mo.clear()) {
               mo = (WeakReferenceGC)var1.next();
               PyObject rfrt = (PyObject)mo.get();
               if (rfrt != null) {
                  JyAttribute.delAttr(rfrt, (byte)5);
                  FinalizeTrigger ft = (FinalizeTrigger)JyAttribute.getAttr(rfrt, (byte)127);
                  if (ft != null) {
                     ft.flags &= -2;
                  }
               }
            }

            monitoredObjects.clear();
         }
      } catch (NullPointerException var7) {
      }

   }

   public static void stopMonitoring() {
      setMonitorGlobal(false);
      if (monitoredObjects != null) {
         unmonitorAll();
         monitoredObjects = null;
      }

   }

   public static boolean getMonitorGlobal() {
      return PyObject.gcMonitorGlobal;
   }

   public static void setMonitorGlobal(boolean flag) {
      if (flag) {
         gcFlags = (short)(gcFlags | 1);
      } else {
         gcFlags = (short)(gcFlags & -2);
      }

      PyObject.gcMonitorGlobal = flag;
   }

   public static short getJythonGCFlags() {
      if ((gcFlags & 1) != 0 != PyObject.gcMonitorGlobal) {
         if (PyObject.gcMonitorGlobal) {
            gcFlags = (short)(gcFlags | 1);
         } else {
            gcFlags = (short)(gcFlags & -2);
         }
      }

      return gcFlags;
   }

   public static void setJythonGCFlags(short flags) {
      gcFlags = flags;
      PyObject.gcMonitorGlobal = (gcFlags & 1) != 0;
      updateDelayedFinalizationState();
   }

   public static void addJythonGCFlags(short flags) {
      gcFlags |= flags;
      PyObject.gcMonitorGlobal = (gcFlags & 1) != 0;
      updateDelayedFinalizationState();
   }

   public static void removeJythonGCFlags(short flags) {
      gcFlags = (short)(gcFlags & ~flags);
      PyObject.gcMonitorGlobal = (gcFlags & 1) != 0;
      updateDelayedFinalizationState();
   }

   public static void notifyFinalize(PyObject finalized) {
      if (--finalizeWaitCount == 0 && waitingForFinalizers) {
         Class var1 = GCSentinel.class;
         synchronized(GCSentinel.class) {
            GCSentinel.class.notify();
         }
      }

   }

   private static void notifyAbortFinalize(PyObject abort, boolean cyclic) {
      if (cyclic) {
         ++abortedCyclicFinalizers;
      }

      notifyFinalize(abort);
   }

   public static void enable() {
   }

   public static void disable() {
      throw Py.NotImplementedError("can't disable Java GC");
   }

   public static boolean isenabled() {
      return true;
   }

   public static int collect(int generation) {
      return collect();
   }

   private static boolean needsTrashPrinting() {
      return ((debugFlags & 2) != 0 || (debugFlags & 4) != 0) && ((debugFlags & 8) != 0 || (debugFlags & 16) != 0);
   }

   private static boolean needsCollectBuffer() {
      return (debugFlags & 1) != 0 || needsTrashPrinting();
   }

   public static int collect() {
      try {
         return collect_intern();
      } catch (ConcurrentModificationException var1) {
         var1.printStackTrace();
      } catch (NullPointerException var2) {
         var2.printStackTrace();
      }

      return -1;
   }

   private static int collect_intern() {
      long t1 = 0L;
      if ((debugFlags & 1) != 0) {
         t1 = System.currentTimeMillis();
      }

      int result;
      if (!isMonitoring()) {
         if ((debugFlags & 1) != 0) {
            writeDebug("gc", "collecting generation x...");
            writeDebug("gc", "objects in each generation: unknown");
         }

         if ((gcFlags & 1024) != 0) {
            writeDebug("gc", "no monitoring; perform ordinary async System.gc...");
         }

         System.gc();
         result = -2;
      } else {
         if (!gcRunning.compareAndSet(false, true)) {
            if ((gcFlags & 1024) != 0) {
               writeDebug("gc", "collect already running...");
            }

            return -1;
         }

         if ((gcFlags & 1024) != 0) {
            writeDebug("gc", "perform monitored sync gc run...");
         }

         if (needsTrashPrinting() || (gcFlags & 15360) != 0) {
            List lst = new ArrayList();
            Iterator var4 = monitoredObjects.iterator();

            WeakReferenceGC ol;
            while(var4.hasNext()) {
               ol = (WeakReferenceGC)var4.next();
               if (ol.str == null) {
                  lst.add(ol);
               }
            }

            var4 = lst.iterator();

            while(var4.hasNext()) {
               ol = (WeakReferenceGC)var4.next();
               ol.initStr((PyObject)null);
            }

            lst.clear();
         }

         ++gcMonitoredRunCount;
         delayedFinalizationMode = 1;
         notifyRerun = false;
         int[] stat = new int[]{0, 0};
         syncCollect(stat, (debugFlags & 1) != 0);
         delayedFinalizationMode = 2;
         if (!notifyRerun) {
            if ((gcFlags & 1024) != 0) {
               writeDebug("gc", "sync collect done.");
            }
         } else {
            if ((gcFlags & 1024) != 0) {
               writeDebug("gc", "initial sync collect done.");
            }

            for(; notifyRerun; syncCollect(stat, false)) {
               notifyRerun = false;
               if ((gcFlags & 1024) != 0) {
                  writeDebug("gc", "rerun gc...");
               }
            }

            if ((gcFlags & 1024) != 0) {
               writeDebug("gc", "all sync collect runs done.");
            }
         }

         delayedFinalizationMode = 0;
         gcRunning.set(false);
         result = stat[0];
         if ((debugFlags & 1) != 0 && result != -2) {
            StringBuilder sb = new StringBuilder("done, ");
            sb.append(stat[0]);
            sb.append(" unreachable, ");
            sb.append(stat[1]);
            sb.append(" uncollectable");
            if (t1 != 0L) {
               sb.append(", ");
               sb.append((double)(System.currentTimeMillis() - t1) / 1000.0);
               sb.append("s elapsed");
            }

            sb.append(".");
            writeDebug("gc", sb.toString());
         }
      }

      if ((debugFlags & 1) != 0 && result == -2) {
         StringBuilder sb = new StringBuilder("done");
         if (t1 != 0L) {
            sb.append(", ");
            sb.append((double)(System.currentTimeMillis() - t1) / 1000.0);
            sb.append("s elapsed");
         }

         sb.append(".");
         writeDebug("gc", sb.toString());
      }

      return result;
   }

   private static void syncCollect(int[] stat, boolean debugStat) {
      abortedCyclicFinalizers = 0;
      lockPostFinalization = true;

      Reference trash;
      try {
         trash = gcTrash.remove((long)initWaitTime);
         if (trash != null && (gcFlags & 1024) != 0) {
            writeDebug("gc", "monitored objects from previous gc-run found.");
         }
      } catch (InterruptedException var16) {
         trash = null;
      }

      HashSet cyclic;
      IdentityHashMap cyclicLookup;
      synchronized(monitoredObjects) {
         if (trash != null) {
            while(trash != null) {
               monitoredObjects.remove(trash);

               try {
                  trash = gcTrash.remove((long)initWaitTime);
               } catch (InterruptedException var15) {
                  trash = null;
               }
            }

            if ((gcFlags & 1024) != 0) {
               writeDebug("gc", "cleaned up previous trash.");
            }
         }

         Iterator var6 = monitoredObjects.iterator();

         while(true) {
            FinalizeTrigger ft;
            WeakReferenceGC wrg;
            if (!var6.hasNext()) {
               cyclicLookup = removeNonCyclicWeakRefs(monitoredObjects);
               cyclic = new HashSet(cyclicLookup.values());
               if (debugStat) {
                  writeDebug("gc", "collecting generation x...");
                  writeDebug("gc", "objects in each generation: " + cyclic.size());
               }

               if ((debugFlags & 32) != 0 || (gcFlags & 8) != 0) {
                  cyclic.retainAll(monitoredObjects);
                  var6 = cyclic.iterator();

                  while(var6.hasNext()) {
                     wrg = (WeakReferenceGC)var6.next();
                     if (!wrg.hasFinalizer) {
                        PyObject obj = (PyObject)wrg.get();
                        FinalizeTrigger.ensureFinalizer(obj);
                        wrg.updateHasFinalizer();
                        ft = (FinalizeTrigger)JyAttribute.getAttr(obj, (byte)127);
                        ft.flags = (byte)(ft.flags | 8);
                        ft.flags = (byte)(ft.flags | 1);
                     }
                  }
               }
               break;
            }

            wrg = (WeakReferenceGC)var6.next();
            wrg.updateHasFinalizer();
            if (wrg.hasFinalizer) {
               ft = (FinalizeTrigger)JyAttribute.getAttr((PyObject)wrg.get(), (byte)127);
               ft.flags = (byte)(ft.flags | 1);
            }
         }
      }

      maxWaitTime = (long)initWaitTime;
      lastRemoveTimeStamp = System.currentTimeMillis();
      if (finalizeWaitCount != 0) {
         System.err.println("Finalize wait count should be initially 0!");
         finalizeWaitCount = 0;
      }

      try {
         trash = gcTrash.remove((long)initWaitTime);
         if (trash != null && (gcFlags & 1024) != 0) {
            writeDebug("gc", "monitored objects from interferring gc-run found.");
         }
      } catch (InterruptedException var14) {
         trash = null;
      }

      if (trash != null) {
         while(true) {
            if (trash == null) {
               if ((gcFlags & 1024) != 0) {
                  writeDebug("gc", "cleaned up interferring trash.");
               }
               break;
            }

            monitoredObjects.remove(trash);
            if (cyclic.remove(trash) && (gcFlags & 1024) != 0) {
               writeDebug("gc", "cyclic interferring trash: " + trash);
            } else if ((gcFlags & 1024) != 0) {
               writeDebug("gc", "interferring trash: " + trash);
            }

            try {
               trash = gcTrash.remove((long)initWaitTime);
            } catch (InterruptedException var13) {
               trash = null;
            }
         }
      }

      if ((gcFlags & 1024) != 0) {
         writeDebug("gc", "call System.gc.");
      }

      cyclicLookup = null;
      List collectBuffer = collectSyncViaSentinel(stat, cyclic);
      lockPostFinalization = false;
      if (postFinalizationProcessor != null) {
         postFinalizationProcessor.interrupt();
      }

      waitingForFinalizers = true;
      waitForFinalizers();
      waitingForFinalizers = false;
      if (postFinalizationPending) {
         if ((gcFlags & 1024) != 0) {
            writeDebug("gc", "waiting for pending post-finalization process.");
         }

         Class var19 = PostFinalizationProcessor.class;
         synchronized(PostFinalizationProcessor.class) {
            while(postFinalizationPending) {
               try {
                  PostFinalizationProcessor.class.wait();
               } catch (InterruptedException var12) {
               }
            }
         }

         if ((gcFlags & 1024) != 0) {
            writeDebug("gc", "post-finalization finished.");
         }
      }

      if (collectBuffer != null) {
         int var10002;
         Iterator var20;
         WeakReferenceGC wrg;
         if ((debugFlags & 2) == 0 || (debugFlags & 16) == 0 && (debugFlags & 8) == 0) {
            if ((debugFlags & 1) != 0) {
               var20 = collectBuffer.iterator();

               while(var20.hasNext()) {
                  wrg = (WeakReferenceGC)var20.next();
                  if (wrg.cycleMark.isUncollectable()) {
                     var10002 = stat[1]++;
                  }
               }
            }
         } else {
            var20 = collectBuffer.iterator();

            while(var20.hasNext()) {
               wrg = (WeakReferenceGC)var20.next();
               if (!wrg.cycleMark.isUncollectable()) {
                  if (wrg.isInstance) {
                     writeDebug("gc", "collectable " + ((debugFlags & 8) != 0 ? wrg.inst_str : wrg.str));
                  } else if ((debugFlags & 16) != 0) {
                     writeDebug("gc", "collectable " + wrg.str);
                  }
               } else {
                  var10002 = stat[1]++;
               }
            }
         }

         if ((debugFlags & 4) != 0 && ((debugFlags & 16) != 0 || (debugFlags & 8) != 0)) {
            var20 = collectBuffer.iterator();

            while(var20.hasNext()) {
               wrg = (WeakReferenceGC)var20.next();
               if (wrg.cycleMark.isUncollectable()) {
                  if (wrg.isInstance) {
                     writeDebug("gc", "uncollectable " + ((debugFlags & 8) != 0 ? wrg.inst_str : wrg.str));
                  } else if ((debugFlags & 16) != 0) {
                     writeDebug("gc", "uncollectable " + wrg.str);
                  }
               }
            }
         }
      }

      if ((gcFlags & 1024) != 0) {
         writeDebug("gc", abortedCyclicFinalizers + " finalizers aborted.");
      }

      stat[0] -= abortedCyclicFinalizers;
      stat[1] -= abortedCyclicFinalizers;
   }

   private static List collectSyncViaSentinel(int[] stat, Set cyclic) {
      WeakReference sentRef = new WeakReference(new GCSentinel(Thread.currentThread()), gcTrash);
      System.gc();
      List collectBuffer = null;
      if (needsCollectBuffer()) {
         collectBuffer = new ArrayList();
      }

      try {
         while(true) {
            long removeTime = System.currentTimeMillis() - lastRemoveTimeStamp;
            if (removeTime > maxWaitTime) {
               maxWaitTime = removeTime;
            }

            lastRemoveTimeStamp = System.currentTimeMillis();
            Reference trash = gcTrash.remove(Math.max(gcRecallTime, maxWaitTime * (long)defaultWaitFactor));
            if (trash != null) {
               if (trash instanceof WeakReferenceGC) {
                  synchronized(monitoredObjects) {
                     monitoredObjects.remove(trash);
                  }

                  if (cyclic.contains(trash) && !((WeakReferenceGC)trash).cls.contains("Java")) {
                     int var10002 = stat[0]++;
                     if (collectBuffer != null) {
                        collectBuffer.add((WeakReferenceGC)trash);
                     }

                     if ((gcFlags & 1024) != 0) {
                        writeDebug("gc", "Collected cyclic object: " + trash);
                     }
                  }

                  if (((WeakReferenceGC)trash).hasFinalizer) {
                     ++finalizeWaitCount;
                     if ((gcFlags & 8192) != 0) {
                        writeDebug("gc", "Collected finalizable object: " + trash);
                        writeDebug("gc", "New finalizeWaitCount: " + finalizeWaitCount);
                     }
                  }
               } else if (trash == sentRef && (gcFlags & 1024) != 0) {
                  writeDebug("gc", "Sentinel collected.");
               }
            } else {
               System.gc();
            }
         }
      } catch (InterruptedException var10) {
         return collectBuffer;
      }
   }

   private static void waitForFinalizers() {
      if (finalizeWaitCount != 0) {
         if ((gcFlags & 1024) != 0) {
            writeDebug("gc", "waiting for " + finalizeWaitCount + " pending finalizers.");
            if (finalizeWaitCount < 0) {
               Py.writeError("gc", "There should never be less than zero pending finalizers!");
            }
         }

         Class var0 = GCSentinel.class;
         synchronized(GCSentinel.class) {
            while(finalizeWaitCount != 0) {
               try {
                  GCSentinel.class.wait();
               } catch (InterruptedException var4) {
               }
            }
         }

         if ((gcFlags & 1024) != 0) {
            writeDebug("gc", "no more finalizers pending.");
         }
      }

      while(openFinalizeCount > 0 || System.currentTimeMillis() - postFinalizationTimestamp < postFinalizationTimeOut) {
         try {
            Thread.sleep(postFinalizationTimeOut);
         } catch (InterruptedException var3) {
         }
      }

   }

   public static PyObject get_count() {
      throw Py.NotImplementedError("not applicable to Java GC");
   }

   public static void set_debug(int flags) {
      debugFlags = flags;
   }

   public static int get_debug() {
      return debugFlags;
   }

   public static void set_threshold(PyObject[] args, String[] kwargs) {
      throw Py.NotImplementedError("not applicable to Java GC");
   }

   public static PyObject get_threshold() {
      throw Py.NotImplementedError("not applicable to Java GC");
   }

   public static PyObject get_objects() {
      if (!isMonitoring()) {
         throw Py.NotImplementedError("not applicable in Jython if gc module is not monitoring PyObjects");
      } else {
         ArrayList resultList = new ArrayList(monitoredObjects.size());
         synchronized(monitoredObjects) {
            Iterator var2 = monitoredObjects.iterator();

            while(true) {
               if (!var2.hasNext()) {
                  break;
               }

               WeakReferenceGC src = (WeakReferenceGC)var2.next();
               PyObject obj = (PyObject)src.get();
               if (isTraversable(obj)) {
                  resultList.add(obj);
               }
            }
         }

         resultList.trimToSize();
         return new PyList(resultList);
      }
   }

   public static PyObject get_referrers(PyObject[] args, String[] kwargs) {
      if (!isMonitoring()) {
         throw Py.NotImplementedError("not applicable in Jython if gc module is not monitoring PyObjects");
      } else if (args == null) {
         return Py.None;
      } else {
         PyObject result = new PyList();
         PyObject[] coll = new PyObject[]{null, result};
         synchronized(monitoredObjects) {
            PyObject[] var5 = args;
            int var6 = args.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               PyObject ob = var5[var7];
               Iterator var9 = monitoredObjects.iterator();

               while(var9.hasNext()) {
                  WeakReferenceGC src0 = (WeakReferenceGC)var9.next();
                  PyObject src = (PyObject)src0.get();
                  if (src instanceof Traverseproc) {
                     try {
                        if (((Traverseproc)src).refersDirectlyTo(ob)) {
                           result.__add__(src);
                        }
                     } catch (UnsupportedOperationException var14) {
                        coll[0] = ob;
                        traverse(ob, gc.ReferrerFinder.defaultInstance, coll);
                     }
                  } else if (isTraversable(src)) {
                     coll[0] = ob;
                     traverse(ob, gc.ReferrerFinder.defaultInstance, coll);
                  }
               }
            }

            return result;
         }
      }
   }

   public static PyObject get_referents(PyObject[] args, String[] kwargs) {
      if (args == null) {
         return Py.None;
      } else {
         PyObject result = new PyList();
         PyObject[] var3 = args;
         int var4 = args.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            PyObject ob = var3[var5];
            traverse(ob, gc.ReferentsFinder.defaultInstance, result);
         }

         return result;
      }
   }

   public static PyObject is_tracked(PyObject[] args, String[] kwargs) {
      return !isTraversable(args[0]) || monitoredObjects != null && !isMonitored(args[0]) ? Py.False : Py.True;
   }

   private static IdentityHashMap removeNonCyclicWeakRefs(Iterable pool) {
      IdentityHashMap[] pools = new IdentityHashMap[]{new IdentityHashMap(), new IdentityHashMap()};
      Iterator var2;
      WeakReferenceGC ref;
      PyObject referent;
      if (monitorNonTraversable) {
         var2 = pool.iterator();

         while(var2.hasNext()) {
            ref = (WeakReferenceGC)var2.next();
            referent = (PyObject)ref.get();
            if (referent != null && isTraversable(referent)) {
               pools[0].put(referent, ref);
            }
         }
      } else {
         var2 = pool.iterator();

         while(var2.hasNext()) {
            ref = (WeakReferenceGC)var2.next();
            referent = (PyObject)ref.get();
            if (referent != null) {
               pools[0].put(referent, ref);
            }
         }
      }

      IdentityHashMap toProcess = new IdentityHashMap();
      Iterator var5 = pools[0].values().iterator();

      WeakReferenceGC ref;
      while(var5.hasNext()) {
         ref = (WeakReferenceGC)var5.next();
         traverse((PyObject)ref.get(), gc.ReachableFinderWeakRefs.defaultInstance, pools);
      }

      IdentityHashMap tmp;
      while(!pools[1].isEmpty()) {
         tmp = pools[1];
         pools[1] = toProcess;
         toProcess = tmp;
         pools[0].putAll(tmp);
         var5 = tmp.values().iterator();

         while(var5.hasNext()) {
            ref = (WeakReferenceGC)var5.next();
            traverse((PyObject)ref.get(), gc.ReachableFinderWeakRefs.defaultInstance, pools);
         }

         tmp.clear();
      }

      for(boolean done = false; !done; pools[1] = tmp) {
         done = true;
         Iterator var11 = pools[0].values().iterator();

         while(var11.hasNext()) {
            WeakReferenceGC ref = (WeakReferenceGC)var11.next();
            gc.RefInListFinder.defaultInstance.found = false;
            referent = (PyObject)ref.get();
            traverse(referent, gc.RefInListFinder.defaultInstance, pools);
            if (!gc.RefInListFinder.defaultInstance.found) {
               toProcess.put(referent, ref);
               done = false;
            }
         }

         var11 = toProcess.keySet().iterator();

         while(var11.hasNext()) {
            PyObject ref = (PyObject)var11.next();
            pools[1].remove(ref);
         }

         toProcess.clear();
         done = done && pools[0].size() == pools[1].size();
         tmp = pools[0];
         tmp.clear();
         pools[0] = pools[1];
      }

      return pools[0];
   }

   private static Set findReachables(Iterable pool) {
      IdentityHashMap[] pools = new IdentityHashMap[]{new IdentityHashMap(), new IdentityHashMap()};
      IdentityHashMap toProcess = new IdentityHashMap();
      Iterator var3 = pool.iterator();

      PyObject obj;
      while(var3.hasNext()) {
         obj = (PyObject)var3.next();
         if (isTraversable(obj)) {
            traverse(obj, gc.ReachableFinder.defaultInstance, pools);
         }
      }

      while(!pools[1].isEmpty()) {
         IdentityHashMap tmp = pools[1];
         pools[1] = toProcess;
         toProcess = tmp;
         pools[0].putAll(tmp);
         var3 = tmp.keySet().iterator();

         while(var3.hasNext()) {
            obj = (PyObject)var3.next();
            traverse(obj, gc.ReachableFinder.defaultInstance, pools);
         }

         tmp.clear();
      }

      return pools[0].keySet();
   }

   private static Set removeNonCyclic(Iterable pool) {
      IdentityHashMap[] pools = new IdentityHashMap[]{new IdentityHashMap(), new IdentityHashMap()};
      Iterator var2;
      PyObject obj;
      if (monitorNonTraversable) {
         var2 = pool.iterator();

         while(var2.hasNext()) {
            obj = (PyObject)var2.next();
            if (isTraversable(obj)) {
               pools[0].put(obj, obj);
            }
         }
      } else {
         var2 = pool.iterator();

         while(var2.hasNext()) {
            obj = (PyObject)var2.next();
            pools[0].put(obj, obj);
         }
      }

      IdentityHashMap toProcess = new IdentityHashMap();
      Iterator var4 = pools[0].keySet().iterator();

      PyObject obj;
      while(var4.hasNext()) {
         obj = (PyObject)var4.next();
         traverse(obj, gc.ReachableFinder.defaultInstance, pools);
      }

      IdentityHashMap tmp;
      while(!pools[1].isEmpty()) {
         tmp = pools[1];
         pools[1] = toProcess;
         toProcess = tmp;
         pools[0].putAll(tmp);
         var4 = tmp.keySet().iterator();

         while(var4.hasNext()) {
            obj = (PyObject)var4.next();
            traverse(obj, gc.ReachableFinder.defaultInstance, pools);
         }

         tmp.clear();
      }

      for(boolean done = false; !done; pools[1] = tmp) {
         done = true;
         Iterator var10 = pools[0].keySet().iterator();

         PyObject obj;
         while(var10.hasNext()) {
            obj = (PyObject)var10.next();
            gc.ObjectInListFinder.defaultInstance.found = false;
            traverse(obj, gc.ObjectInListFinder.defaultInstance, pools);
            if (!gc.ObjectInListFinder.defaultInstance.found) {
               toProcess.put(obj, obj);
               done = false;
            }
         }

         var10 = toProcess.keySet().iterator();

         while(var10.hasNext()) {
            obj = (PyObject)var10.next();
            pools[1].remove(obj);
         }

         toProcess.clear();
         done = done && pools[0].size() == pools[1].size();
         tmp = pools[0];
         tmp.clear();
         pools[0] = pools[1];
      }

      return pools[0].keySet();
   }

   public static void markCyclicObjects(PyObject start, boolean uncollectable) {
      Set search = findCyclicObjects(start);
      if (search != null) {
         Iterator var3 = search.iterator();

         while(var3.hasNext()) {
            PyObject obj = (PyObject)var3.next();
            CycleMarkAttr cm = (CycleMarkAttr)JyAttribute.getAttr(obj, (byte)5);
            if (cm == null) {
               cm = new CycleMarkAttr(true, uncollectable);
               JyAttribute.setAttr(obj, (byte)5, cm);
            } else {
               cm.setFlags(true, uncollectable);
            }
         }

      }
   }

   public static Set findCyclicObjects(PyObject start) {
      IdentityHashMap map = findCyclicObjectsIntern(start);
      return map == null ? null : map.keySet();
   }

   private static IdentityHashMap findCyclicObjectsIntern(PyObject start) {
      if (!isTraversable(start)) {
         return null;
      } else {
         IdentityHashMap[] reachSearch = (IdentityHashMap[])(new IdentityHashMap[2]);
         reachSearch[0] = new IdentityHashMap();
         reachSearch[1] = new IdentityHashMap();
         IdentityHashMap search = new IdentityHashMap();
         traverse(start, gc.ReachableFinder.defaultInstance, reachSearch);
         IdentityHashMap tmp = search;
         search = reachSearch[1];
         tmp.clear();

         for(reachSearch[1] = tmp; !search.isEmpty(); reachSearch[1] = tmp) {
            reachSearch[0].putAll(search);
            Iterator var4 = search.keySet().iterator();

            while(var4.hasNext()) {
               PyObject obj = (PyObject)var4.next();
               traverse(obj, gc.ReachableFinder.defaultInstance, reachSearch);
            }

            tmp = search;
            search = reachSearch[1];
            tmp.clear();
         }

         if (!reachSearch[0].containsKey(start)) {
            return null;
         } else {
            search.clear();
            search.put(start, start);
            boolean changed = true;

            while(changed) {
               changed = false;
               Iterator var8 = reachSearch[0].keySet().iterator();

               PyObject key;
               while(var8.hasNext()) {
                  key = (PyObject)var8.next();
                  if (traverse(key, gc.RefersToSetFinder.defaultInstance, search.keySet()) == 1) {
                     changed = true;
                     tmp.put(key, key);
                  }
               }

               search.putAll(tmp);
               var8 = tmp.keySet().iterator();

               while(var8.hasNext()) {
                  key = (PyObject)var8.next();
                  reachSearch[0].remove(key);
               }

               tmp.clear();
            }

            return search;
         }
      }
   }

   public static int traverse(PyObject ob, Visitproc visit, Object arg) {
      boolean traversed = false;
      int retVal;
      if (ob instanceof Traverseproc) {
         retVal = ((Traverseproc)ob).traverse(visit, arg);
         traversed = true;
         if (retVal != 0) {
            return retVal;
         }
      }

      if (ob instanceof TraverseprocDerived) {
         retVal = ((TraverseprocDerived)ob).traverseDerived(visit, arg);
         traversed = true;
         if (retVal != 0) {
            return retVal;
         }
      }

      boolean justAddedWarning = false;
      if ((gcFlags & 128) == 0 && !(ob instanceof Traverseproc) && !(ob instanceof TraverseprocDerived) && ob.getClass() != PyObject.class && !ob.getClass().isAnnotationPresent(Untraversable.class) && ((gcFlags & 256) != 0 || reflectionWarnedClasses == null || !reflectionWarnedClasses.contains(ob.getClass()))) {
         if ((gcFlags & 256) == 0) {
            if (reflectionWarnedClasses == null) {
               reflectionWarnedClasses = new HashSet();
            }

            reflectionWarnedClasses.add(ob.getClass());
            justAddedWarning = true;
         }

         Py.writeWarning("gc", "The PyObject-subclass " + ob.getClass().getName() + "\n" + "should either implement Traverseproc or be marked with the\n" + "@Untraversable annotation. See instructions in\n" + "javadoc of org.python.core.Traverseproc.java.");
      }

      if ((gcFlags & 64) != 0) {
         return 0;
      } else {
         Class cls = ob.getClass();
         if (!traversed && cls != PyObject.class && !cls.isAnnotationPresent(Untraversable.class)) {
            if ((gcFlags & 128) == 0 && ((gcFlags & 256) != 0 || justAddedWarning || reflectionWarnedClasses == null || !reflectionWarnedClasses.contains(ob.getClass()))) {
               if ((gcFlags & 256) == 0 && !justAddedWarning) {
                  if (reflectionWarnedClasses == null) {
                     reflectionWarnedClasses = new HashSet();
                  }

                  reflectionWarnedClasses.add(ob.getClass());
               }

               Py.writeWarning("gc", "Traverse by reflection: " + ob.getClass().getName() + "\n" + "This is an inefficient procedure. It is recommended to\n" + "implement the traverseproc mechanism properly.");
            }

            return traverseByReflection(ob, visit, arg);
         } else {
            return 0;
         }
      }
   }

   public static int traverseByReflection(Object ob, Visitproc visit, Object arg) {
      IdentityHashMap alreadyTraversed = new IdentityHashMap();
      alreadyTraversed.put(ob, ob);
      return traverseByReflectionIntern(ob, alreadyTraversed, visit, arg);
   }

   private static int traverseByReflectionIntern(Object ob, IdentityHashMap alreadyTraversed, Visitproc visit, Object arg) {
      Class cls = ob.getClass();
      int result = 0;
      Object element;
      if (cls.isArray() && canLinkToPyObject(cls.getComponentType(), false)) {
         for(int i = 0; i < Array.getLength(ob); ++i) {
            element = Array.get(ob, i);
            if (element != null) {
               if (element instanceof PyObject) {
                  result = visit.visit((PyObject)element, arg);
               } else if (!alreadyTraversed.containsKey(element)) {
                  alreadyTraversed.put(element, element);
                  result = traverseByReflectionIntern(element, alreadyTraversed, visit, arg);
               }

               if (result != 0) {
                  return result;
               }
            }
         }
      } else {
         while(cls != Object.class && cls != PyObject.class) {
            Field[] declFields = cls.getDeclaredFields();

            for(int i = 0; i < declFields.length; ++i) {
               if (!Modifier.isStatic(declFields[i].getModifiers()) && !declFields[i].getType().isPrimitive()) {
                  if (!declFields[i].isAccessible()) {
                     declFields[i].setAccessible(true);
                  }

                  if (canLinkToPyObject(declFields[i].getType(), false)) {
                     try {
                        element = declFields[i].get(ob);
                        if (element != null) {
                           if (element instanceof PyObject) {
                              result = visit.visit((PyObject)element, arg);
                           } else if (!alreadyTraversed.containsKey(element)) {
                              alreadyTraversed.put(element, element);
                              result = traverseByReflectionIntern(element, alreadyTraversed, visit, arg);
                           }

                           if (result != 0) {
                              return result;
                           }
                        }
                     } catch (Exception var10) {
                     }
                  }
               }
            }

            cls = cls.getSuperclass();
         }
      }

      return 0;
   }

   public static boolean canLinkToPyObject(Class cls, boolean actual) {
      if (quickCheckCannotLinkToPyObject(cls)) {
         return false;
      } else if (!actual && !Modifier.isFinal(cls.getModifiers())) {
         return true;
      } else if (quickCheckCanLinkToPyObject(cls)) {
         return true;
      } else if (!cls.isInterface() && !Modifier.isAbstract(cls.getModifiers())) {
         if (cls.isArray()) {
            return canLinkToPyObject(cls.getComponentType(), false);
         } else {
            Class cls2 = cls;

            int fieldCount;
            for(fieldCount = cls.getDeclaredFields().length; fieldCount == 0 && cls2 != Object.class; fieldCount += cls.getDeclaredFields().length) {
               cls2 = cls2.getSuperclass();
            }

            if (fieldCount == 0) {
               return false;
            } else {
               IdentityHashMap alreadyChecked = new IdentityHashMap();
               alreadyChecked.put(cls, cls);

               for(cls2 = cls; cls2 != Object.class; cls2 = cls2.getSuperclass()) {
                  Field[] var5 = cls2.getDeclaredFields();
                  int var6 = var5.length;

                  for(int var7 = 0; var7 < var6; ++var7) {
                     Field f = var5[var7];
                     if (!Modifier.isStatic(f.getModifiers())) {
                        Class ft = f.getType();
                        if (!ft.isPrimitive() && !alreadyChecked.containsKey(ft)) {
                           alreadyChecked.put(ft, ft);
                           if (canLinkToPyObjectIntern(ft, alreadyChecked)) {
                              return true;
                           }
                        }
                     }
                  }
               }

               return false;
            }
         }
      } else {
         return true;
      }
   }

   private static boolean quickCheckCanLinkToPyObject(Class cls) {
      if (!Modifier.isFinal(cls.getModifiers())) {
         return true;
      } else if (cls.isAssignableFrom(PyObject.class)) {
         return true;
      } else if (PyObject.class.isAssignableFrom(cls)) {
         return true;
      } else {
         return cls.isArray() ? quickCheckCanLinkToPyObject(cls.getComponentType()) : false;
      }
   }

   private static boolean quickCheckCannotLinkToPyObject(Class cls) {
      if (cls.isPrimitive()) {
         return true;
      } else if (cls != String.class && cls != Class.class && cls != Field.class && cls != Method.class) {
         return cls.isArray() ? quickCheckCannotLinkToPyObject(cls.getComponentType()) : false;
      } else {
         return true;
      }
   }

   private static boolean canLinkToPyObjectIntern(Class cls, IdentityHashMap alreadyChecked) {
      if (quickCheckCanLinkToPyObject(cls)) {
         return true;
      } else if (quickCheckCannotLinkToPyObject(cls)) {
         return false;
      } else if (cls.isArray()) {
         return canLinkToPyObjectIntern(cls.getComponentType(), alreadyChecked);
      } else {
         for(Class cls2 = cls; cls2 != Object.class; cls2 = cls2.getSuperclass()) {
            Field[] var3 = cls2.getDeclaredFields();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               Field f = var3[var5];
               if (!Modifier.isStatic(f.getModifiers())) {
                  Class ft = f.getType();
                  if (!ft.isPrimitive() && !alreadyChecked.containsKey(ft)) {
                     alreadyChecked.put(ft, ft);
                     if (canLinkToPyObjectIntern(ft, alreadyChecked)) {
                        return true;
                     }
                  }
               }
            }
         }

         return false;
      }
   }

   public static boolean isTraversable(PyObject ob) {
      if (ob == null) {
         return false;
      } else if (!(ob instanceof Traverseproc) && !(ob instanceof TraverseprocDerived)) {
         if ((gcFlags & 64) != 0) {
            return false;
         } else {
            Class cls = ob.getClass();
            return cls != PyObject.class && !cls.isAnnotationPresent(Untraversable.class);
         }
      } else {
         return true;
      }
   }

   static {
      maxWaitTime = (long)initWaitTime;
      gcMonitoredRunCount = 0;
      gcRecallTime = 4000L;
      garbage = new PyList();
      postFinalizationTimeOut = 100L;
      postFinalizationTimestamp = System.currentTimeMillis() - 2L * postFinalizationTimeOut;
      openFinalizeCount = 0;
      postFinalizationPending = false;
      lockPostFinalization = false;
      abortedCyclicFinalizers = 0;
      delayedFinalizationMode = 0;
      notifyRerun = false;
      __doc__enable = new PyString("enable() -> None\n\nEnable automatic garbage collection.\n(does nothing in Jython)\n");
      __doc__disable = new PyString("disable() -> None\n\nDisable automatic garbage collection.\n(raises NotImplementedError in Jython)\n");
      __doc__isenabled = new PyString("isenabled() -> status\n\nReturns true if automatic garbage collection is enabled.\n");
      __doc__collect = new PyString("collect([generation]) -> n\n\nWith no arguments, run a full collection.  The optional argument\nmay be an integer specifying which generation to collect.  A ValueError\nis raised if the generation number is invalid.\n\nThe number of unreachable objects is returned.\n(Jython emulates CPython cyclic trash counting if objects are monitored.\nIf no objects are monitored, returns -2\n");
      __doc__get_count = new PyString("get_count() -> (count0, count1, count2)\n\nReturn the current collection counts\n(raises NotImplementedError in Jython)\n");
      __doc__set_debug = new PyString("set_debug(flags) -> None\n\nSet the garbage collection debugging flags. Debugging information is\nwritten to sys.stderr.\n\nflags is an integer and can have the following bits turned on:\n\n  DEBUG_STATS - Print statistics during collection.\n  DEBUG_COLLECTABLE - Print collectable objects found.\n  DEBUG_UNCOLLECTABLE - Print unreachable but uncollectable objects found.\n  DEBUG_INSTANCES - Print instance objects.\n  DEBUG_OBJECTS - Print objects other than instances.\n  DEBUG_SAVEALL - Save objects to gc.garbage rather than freeing them.\n  DEBUG_LEAK - Debug leaking programs (everything but STATS).\n");
      __doc__get_debug = new PyString("get_debug() -> flags\n\nGet the garbage collection debugging flags.\n");
      __doc__set_thresh = new PyString("set_threshold(threshold0, [threshold1, threshold2]) -> None\n\nSets the collection thresholds.  Setting threshold0 to zero disables\ncollection.\n(raises NotImplementedError in Jython)\n");
      __doc__get_thresh = new PyString("get_threshold() -> (threshold0, threshold1, threshold2)\n\nReturn the current collection thresholds\n(raises NotImplementedError in Jython)\n");
      __doc__get_objects = new PyString("get_objects() -> [...]\n\nReturn a list of objects tracked by the collector (excluding the list\nreturned).\n(raises NotImplementedError in Jython)\n");
      __doc__is_tracked = new PyString("is_tracked(obj) -> bool\n\nReturns true if the object is tracked by the garbage collector.\n(i.e. monitored in Jython)\n");
      __doc__get_referrers = new PyString("get_referrers(*objs) -> list\nReturn the list of objects that directly refer to any of objs.\n(only finds monitored referrers in Jython)");
      __doc__get_referents = new PyString("get_referents(*objs) -> list\nReturn the list of objects that are directly referred to by objs.");
   }

   private static class ObjectInListFinder implements Visitproc {
      public static ObjectInListFinder defaultInstance = new ObjectInListFinder();
      public boolean found = false;

      public int visit(PyObject object, Object arg) {
         IdentityHashMap[] pools = (IdentityHashMap[])((IdentityHashMap[])arg);
         if (pools[0].containsKey(object)) {
            pools[1].put(object, object);
            this.found = true;
         }

         return 0;
      }
   }

   private static class RefInListFinder implements Visitproc {
      public static RefInListFinder defaultInstance = new RefInListFinder();
      public boolean found = false;

      public int visit(PyObject object, Object arg) {
         IdentityHashMap[] pools = (IdentityHashMap[])((IdentityHashMap[])arg);
         WeakReferenceGC ref = (WeakReferenceGC)pools[0].get(object);
         if (ref != null) {
            pools[1].put(object, ref);
            this.found = true;
         }

         return 0;
      }
   }

   private static class RefersToSetFinder implements Visitproc {
      public static RefersToSetFinder defaultInstance = new RefersToSetFinder();

      public int visit(PyObject object, Object arg) {
         return ((Set)arg).contains(object) ? 1 : 0;
      }
   }

   private static class ReferrerFinder implements Visitproc {
      public static ReferrerFinder defaultInstance = new ReferrerFinder();

      public int visit(PyObject object, Object arg) {
         if (((PyObject[])((PyObject[])arg))[0].__eq__(object).__nonzero__()) {
            ((PySequenceList)((PyObject[])((PyObject[])arg))[1]).pyadd(object);
         }

         return 0;
      }
   }

   private static class ReachableFinderWeakRefs implements Visitproc {
      public static ReachableFinderWeakRefs defaultInstance = new ReachableFinderWeakRefs();

      public int visit(PyObject object, Object arg) {
         if (gc.isTraversable(object)) {
            IdentityHashMap[] pools = (IdentityHashMap[])((IdentityHashMap[])arg);
            WeakReferenceGC ref = (WeakReferenceGC)pools[0].get(object);
            if (ref == null) {
               ref = new WeakReferenceGC(object);
               pools[1].put(object, ref);
            }
         }

         return 0;
      }
   }

   private static class ReachableFinder implements Visitproc {
      public static ReachableFinder defaultInstance = new ReachableFinder();

      public int visit(PyObject object, Object arg) {
         IdentityHashMap[] reachSearch = (IdentityHashMap[])((IdentityHashMap[])arg);
         if (gc.isTraversable(object) && !reachSearch[0].containsKey(object)) {
            reachSearch[1].put(object, object);
         }

         return 0;
      }
   }

   private static class ReferentsFinder implements Visitproc {
      public static ReferentsFinder defaultInstance = new ReferentsFinder();

      public int visit(PyObject object, Object arg) {
         ((PySequenceList)arg).pyadd(object);
         return 0;
      }
   }

   private static class PostFinalizationProcessor implements Runnable {
      protected static PostFinalizationProcessor defaultInstance = new PostFinalizationProcessor();

      public void run() {
         for(long current = System.currentTimeMillis(); gc.lockPostFinalization || gc.openFinalizeCount != 0 || current - gc.postFinalizationTimestamp <= gc.postFinalizationTimeOut; current = System.currentTimeMillis()) {
            try {
               long time = gc.postFinalizationTimeOut - current + gc.postFinalizationTimestamp;
               if (gc.openFinalizeCount != 0 || gc.lockPostFinalization || time < 0L) {
                  time = gc.gcRecallTime;
               }

               Thread.sleep(time);
            } catch (InterruptedException var8) {
            }
         }

         gc.postFinalizationProcessor = null;
         gc.postFinalizationProcess();
         Class var5 = PostFinalizationProcessor.class;
         synchronized(PostFinalizationProcessor.class) {
            gc.postFinalizationPending = false;
            PostFinalizationProcessor.class.notify();
         }
      }
   }

   private static class DelayedFinalizationProcess implements Runnable {
      static DelayedFinalizationProcess defaultInstance = new DelayedFinalizationProcess();

      private static void performFinalization(PyObject del) {
         if ((gc.gcFlags & 4096) != 0) {
            gc.writeDebug("gc", "delayed finalize of " + del);
         }

         FinalizeTrigger ft = (FinalizeTrigger)JyAttribute.getAttr(del, (byte)127);
         if (ft != null) {
            ft.performFinalization();
         } else if ((gc.gcFlags & 4096) != 0) {
            gc.writeDebug("gc", "no FinalizeTrigger");
         }

      }

      public void run() {
         if ((gc.gcFlags & 4096) != 0) {
            gc.writeDebug("gc", "run delayed finalization. Index: " + gc.gcMonitoredRunCount);
         }

         Set criticals = gc.resurrectionCriticals.keySet();
         if (gc.delayedFinalizationMode == 0 && (gc.gcFlags & 12) == 0) {
            if ((gc.gcFlags & 32) != 0) {
               if ((gc.gcFlags & 4096) != 0) {
                  gc.writeDebug("gc", "process delayed callbacks (force-branch)");
               }

               GlobalRef.processDelayedCallbacks();
            }

            if ((gc.gcFlags & 16) != 0) {
               if ((gc.gcFlags & 4096) != 0) {
                  gc.writeDebug("gc", "process delayed finalizers (force-branch)");
               }

               Iterator var9 = gc.delayedFinalizables.keySet().iterator();

               PyObject cr;
               while(var9.hasNext()) {
                  cr = (PyObject)var9.next();
                  performFinalization(cr);
               }

               var9 = criticals.iterator();

               while(var9.hasNext()) {
                  cr = (PyObject)var9.next();
                  performFinalization(cr);
               }

               gc.delayedFinalizables.clear();
               gc.resurrectionCriticals.clear();
            }

            if ((gc.gcFlags & 4096) != 0) {
               gc.writeDebug("gc", "forced delayed finalization run done");
            }

         } else {
            Set cyclicCriticals = gc.removeNonCyclic(criticals);
            cyclicCriticals.retainAll(criticals);
            criticals.removeAll(cyclicCriticals);
            Set criticalReachablePool = gc.findReachables(criticals);
            ArrayList criticalReachables = new ArrayList();
            Iterator var5;
            PyObject del;
            FinalizeTrigger fn;
            if (gc.delayedFinalizationMode == 1) {
               var5 = criticalReachablePool.iterator();

               while(var5.hasNext()) {
                  del = (PyObject)var5.next();
                  fn = (FinalizeTrigger)JyAttribute.getAttr(del, (byte)127);
                  if (fn != null && fn.isActive() && fn.isFinalized()) {
                     criticalReachables.add(del);
                     JyAttribute.setAttr(del, (byte)6, gc.gcMonitoredRunCount);
                  }
               }
            } else {
               var5 = criticalReachablePool.iterator();

               while(var5.hasNext()) {
                  del = (PyObject)var5.next();
                  fn = (FinalizeTrigger)JyAttribute.getAttr(del, (byte)127);
                  if (fn != null && fn.isActive() && fn.isFinalized()) {
                     criticalReachables.add(del);
                  }
               }
            }

            criticals.removeAll(criticalReachables);
            if ((gc.gcFlags & 4) != 0) {
               if ((gc.gcFlags & 4096) != 0) {
                  gc.writeDebug("gc", "restore potentially resurrected weak references...");
               }

               var5 = criticalReachablePool.iterator();

               while(var5.hasNext()) {
                  del = (PyObject)var5.next();
                  gc.restoreWeakReferences(del);
               }

               GlobalRef.processDelayedCallbacks();
            }

            criticalReachablePool.clear();
            if ((gc.gcFlags & 8) != 0) {
               if ((gc.gcFlags & 4096) != 0) {
                  gc.writeDebug("gc", "restore " + criticalReachables.size() + " potentially resurrected finalizers...");
               }

               var5 = criticalReachables.iterator();

               while(var5.hasNext()) {
                  del = (PyObject)var5.next();
                  gc.restoreFinalizer(del);
               }
            } else {
               if ((gc.gcFlags & 4096) != 0) {
                  gc.writeDebug("gc", "delayed finalization of " + criticalReachables.size() + " potentially resurrected finalizers...");
               }

               var5 = criticalReachables.iterator();

               while(var5.hasNext()) {
                  del = (PyObject)var5.next();
                  performFinalization(del);
               }
            }

            cyclicCriticals.removeAll(criticalReachables);
            if ((gc.gcFlags & 4096) != 0 && !gc.delayedFinalizables.isEmpty()) {
               gc.writeDebug("gc", "process " + gc.delayedFinalizables.size() + " delayed finalizers...");
            }

            var5 = gc.delayedFinalizables.keySet().iterator();

            while(var5.hasNext()) {
               del = (PyObject)var5.next();
               performFinalization(del);
            }

            if ((gc.gcFlags & 4096) != 0 && !cyclicCriticals.isEmpty()) {
               gc.writeDebug("gc", "process " + cyclicCriticals.size() + " cyclic delayed finalizers...");
            }

            var5 = cyclicCriticals.iterator();

            while(var5.hasNext()) {
               del = (PyObject)var5.next();
               performFinalization(del);
            }

            if ((gc.gcFlags & 4096) != 0 && !criticals.isEmpty()) {
               gc.writeDebug("gc", "calling " + criticals.size() + " critical finalizers not reachable by other critical finalizers...");
            }

            if (gc.delayedFinalizationMode == 1 && !criticals.isEmpty() && !criticalReachables.isEmpty()) {
               gc.notifyRerun = true;
            }

            if (gc.delayedFinalizationMode == 2 && !gc.notifyRerun) {
               for(var5 = criticals.iterator(); var5.hasNext(); performFinalization(del)) {
                  del = (PyObject)var5.next();
                  if (!gc.notifyRerun) {
                     Object m = JyAttribute.getAttr(del, (byte)6);
                     if (m != null && (Integer)m == gc.gcMonitoredRunCount) {
                        gc.notifyRerun = true;
                     }
                  }
               }
            } else {
               var5 = criticals.iterator();

               while(var5.hasNext()) {
                  del = (PyObject)var5.next();
                  performFinalization(del);
               }
            }

            gc.delayedFinalizables.clear();
            gc.resurrectionCriticals.clear();
            if ((gc.gcFlags & 4096) != 0) {
               gc.writeDebug("gc", "delayed finalization run done");
            }

         }
      }
   }

   private static class GCSentinel {
      Thread waiting;

      public GCSentinel(Thread notifyOnFinalize) {
         this.waiting = notifyOnFinalize;
      }

      protected void finalize() throws Throwable {
         gc.notifyPreFinalization();
         if ((gc.gcFlags & 1024) != 0) {
            gc.writeDebug("gc", "Sentinel finalizer called...");
         }

         if (gc.lastRemoveTimeStamp != -1L) {
            for(long diff = gc.maxWaitTime * (long)gc.defaultWaitFactor - System.currentTimeMillis() + gc.lastRemoveTimeStamp; diff > 0L; diff = gc.maxWaitTime * (long)gc.defaultWaitFactor - System.currentTimeMillis() + gc.lastRemoveTimeStamp) {
               try {
                  Thread.sleep(diff);
               } catch (InterruptedException var4) {
               }
            }
         }

         if (this.waiting != null) {
            this.waiting.interrupt();
         }

         if ((gc.gcFlags & 1024) != 0) {
            gc.writeDebug("gc", "Sentinel finalizer done");
         }

         gc.notifyPostFinalization();
      }
   }

   private static class WeakrefGCCompareDummy {
      public static WeakrefGCCompareDummy defaultInstance = new WeakrefGCCompareDummy();
      protected PyObject compare;
      int hashCode = 0;

      public void setCompare(PyObject compare) {
         this.compare = compare;
         this.hashCode = System.identityHashCode(compare);
      }

      public void clearCompare() {
         this.compare = null;
         this.hashCode = 0;
      }

      public int hashCode() {
         return this.hashCode;
      }

      public boolean equals(Object ob) {
         if (ob instanceof Reference) {
            return this.compare.equals(((Reference)ob).get());
         } else {
            return ob instanceof WeakrefGCCompareDummy ? this.compare.equals(((WeakrefGCCompareDummy)ob).compare) : this.compare.equals(ob);
         }
      }
   }

   private static class WeakReferenceGC extends WeakReference {
      int hashCode = 0;
      public String str = null;
      public String inst_str = null;
      public String cls;
      boolean isInstance;
      boolean hasFinalizer = false;
      CycleMarkAttr cycleMark;

      WeakReferenceGC(PyObject referent) {
         super(referent);
         this.isInstance = referent instanceof PyInstance;
         this.cycleMark = (CycleMarkAttr)JyAttribute.getAttr(referent, (byte)5);
         this.hashCode = System.identityHashCode(referent);
         this.cls = referent.getClass().getName();
         this.updateHasFinalizer();
      }

      WeakReferenceGC(PyObject referent, ReferenceQueue q) {
         super(referent, q);
         this.isInstance = referent instanceof PyInstance;
         this.cycleMark = (CycleMarkAttr)JyAttribute.getAttr(referent, (byte)5);
         this.hashCode = System.identityHashCode(referent);
         this.cls = referent.getClass().getName();
         this.updateHasFinalizer();
      }

      public void updateHasFinalizer() {
         PyObject gt = (PyObject)this.get();
         Object fn = JyAttribute.getAttr(gt, (byte)127);
         this.hasFinalizer = fn != null && ((FinalizeTrigger)fn).isActive();
      }

      public void initStr(PyObject referent) {
         PyObject ref = referent;
         if (referent == null) {
            ref = (PyObject)this.get();
         }

         try {
            if (ref instanceof PyInstance) {
               String name = ((PyInstance)ref).fastGetClass().__name__;
               if (name == null) {
                  name = "?";
               }

               this.inst_str = String.format("<%.100s instance at %s>", name, Py.idstr(ref));
            }

            this.str = String.format("<%.100s %s>", ref.getType().getName(), Py.idstr(ref));
         } catch (Exception var4) {
            this.str = "<" + ref.getClass().getSimpleName() + " " + System.identityHashCode(ref) + ">";
         }

      }

      public String toString() {
         return this.str;
      }

      public int hashCode() {
         return this.hashCode;
      }

      public boolean equals(Object ob) {
         Object ownReferent = this.get();
         if (ob instanceof WeakReferenceGC) {
            Object otherReferent = ((WeakReferenceGC)ob).get();
            if (ownReferent != null && otherReferent != null) {
               return otherReferent.equals(ownReferent) && ((WeakReferenceGC)ob).hashCode == this.hashCode;
            } else {
               return ownReferent == otherReferent && this.hashCode == ((WeakReferenceGC)ob).hashCode;
            }
         } else if (!(ob instanceof WeakrefGCCompareDummy)) {
            return false;
         } else if (ownReferent != null && ((WeakrefGCCompareDummy)ob).compare != null) {
            return ownReferent.equals(((WeakrefGCCompareDummy)ob).compare) && this.hashCode == ((WeakrefGCCompareDummy)ob).hashCode;
         } else {
            return ownReferent == ((WeakrefGCCompareDummy)ob).compare && this.hashCode == ((WeakrefGCCompareDummy)ob).hashCode;
         }
      }
   }

   public static class CycleMarkAttr {
      private boolean cyclic = false;
      private boolean uncollectable = false;
      public boolean monitored = false;

      CycleMarkAttr() {
      }

      CycleMarkAttr(boolean cyclic, boolean uncollectable) {
         this.cyclic = cyclic;
         this.uncollectable = uncollectable;
      }

      public boolean isCyclic() {
         return this.cyclic || this.uncollectable;
      }

      public boolean isUncollectable() {
         return this.uncollectable;
      }

      public void setFlags(boolean cyclic, boolean uncollectable) {
         this.cyclic = cyclic;
         this.uncollectable = uncollectable;
      }
   }
}
