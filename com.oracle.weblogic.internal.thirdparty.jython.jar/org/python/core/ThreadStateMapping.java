package org.python.core;

import java.util.Map;
import org.python.google.common.collect.MapMaker;

class ThreadStateMapping {
   private static final Map globalThreadStates = (new MapMaker()).weakKeys().makeMap();
   private static final Map inverseGlobalThreadStates = (new MapMaker()).weakValues().makeMap();
   private static final ThreadLocal inCallThreadState = new ThreadLocal() {
      protected Object[] initialValue() {
         return new Object[2];
      }
   };
   private static Map.Entry[] entriesPrototype = new Map.Entry[0];

   public ThreadState getThreadState(PySystemState newSystemState) {
      Object[] scoped = (Object[])inCallThreadState.get();
      if (scoped[0] != null) {
         return (ThreadState)scoped[0];
      } else {
         Thread currentThread = Thread.currentThread();
         ThreadState ts = (ThreadState)globalThreadStates.get(currentThread);
         if (ts != null) {
            return ts;
         } else {
            if (newSystemState == null) {
               Py.writeDebug("threadstate", "no current system state");
               if (Py.defaultSystemState == null) {
                  PySystemState.initialize();
               }

               newSystemState = Py.defaultSystemState;
            }

            for(PySystemStateRef freedRef = (PySystemStateRef)PySystemStateRef.referenceQueue.poll(); freedRef != null; freedRef = (PySystemStateRef)PySystemStateRef.referenceQueue.poll()) {
               globalThreadStates.remove(inverseGlobalThreadStates.remove(freedRef.getThreadState()));
            }

            ts = new ThreadState(newSystemState);
            globalThreadStates.put(currentThread, ts);
            inverseGlobalThreadStates.put(ts, currentThread);
            return ts;
         }
      }
   }

   public static void enterCall(ThreadState ts) {
      if (ts.call_depth == 0) {
         Object[] scoped = (Object[])inCallThreadState.get();
         scoped[0] = ts;
         scoped[1] = ts.getSystemState();
      } else if (ts.call_depth > ts.getSystemState().getrecursionlimit()) {
         throw Py.RuntimeError("maximum recursion depth exceeded");
      }

      ++ts.call_depth;
   }

   public static void exitCall(ThreadState ts) {
      --ts.call_depth;
      if (ts.call_depth == 0) {
         Object[] scoped = (Object[])inCallThreadState.get();
         scoped[0] = null;
         scoped[1] = null;
      }

   }

   public static PyDictionary _current_frames() {
      Map.Entry[] entries = (Map.Entry[])globalThreadStates.entrySet().toArray(entriesPrototype);
      int i = 0;
      Map.Entry[] var2 = entries;
      int var3 = entries.length;

      int var4;
      for(var4 = 0; var4 < var3; ++var4) {
         Map.Entry entry = var2[var4];
         if (((ThreadState)entry.getValue()).frame != null) {
            ++i;
         }
      }

      PyObject[] elements = new PyObject[i * 2];
      i = 0;
      Map.Entry[] var8 = entries;
      var4 = entries.length;

      for(int var9 = 0; var9 < var4; ++var9) {
         Map.Entry entry = var8[var9];
         if (((ThreadState)entry.getValue()).frame != null) {
            elements[i++] = Py.newInteger(((Thread)entry.getKey()).getId());
            elements[i++] = ((ThreadState)entry.getValue()).frame;
         }
      }

      return new PyDictionary(elements);
   }
}
