package weblogic.kernel;

import java.util.Arrays;

public class ResettableThreadLocal implements AuditableThreadLocal {
   private final ThreadLocalInitialValue initial;
   private final int index;
   private static final InheritableThreadLocal threadLocals = new InheritableThreadLocal() {
      protected Object initialValue() {
         return new ThreadStorage();
      }

      protected Object childValue(Object parentValue) {
         return ((ThreadStorage)parentValue).createChildCopy();
      }
   };

   public ResettableThreadLocal() {
      this(new ThreadLocalInitialValue());
   }

   public ResettableThreadLocal(ThreadLocalInitialValue initial) {
      this.index = ResettableThreadLocal.ThreadStorage.newSlot(this);
      this.initial = initial;
   }

   public ResettableThreadLocal(boolean inherit) {
      this(new ThreadLocalInitialValue(inherit));
   }

   public Object get() {
      return this.currentStorage().get(this.index);
   }

   public Object get(AuditableThread thread) {
      if (thread == null) {
         return null;
      } else {
         ThreadStorage ts = thread.getThreadStorage();
         return ts == null ? null : ts.get(this.index);
      }
   }

   public void set(Object value) {
      this.currentStorage().set(this.index, value);
   }

   protected Object initialValue() {
      return this.initial.initialValue();
   }

   protected Object childValue(Object parentValue) {
      return this.initial.childValue(parentValue);
   }

   protected Object resetValue(Object currentValue) {
      return this.initial.resetValue(currentValue);
   }

   private final ThreadStorage currentStorage() {
      Thread t = Thread.currentThread();
      ThreadStorage ts = null;
      if (t instanceof AuditableThread) {
         ts = ((AuditableThread)t).getThreadStorage();
         if (ts == null) {
            ts = (ThreadStorage)threadLocals.get();
            ((AuditableThread)t).setThreadStorage(ts);
         }
      } else {
         ts = (ThreadStorage)threadLocals.get();
      }

      return ts;
   }

   public static final void resetJavaThreadStorage() {
      threadLocals.remove();
   }

   public static final void restoreCurrentJavaThreadStorage() {
      Thread t = Thread.currentThread();
      if (t instanceof AuditableThread) {
         ThreadStorage ts = ((AuditableThread)t).getThreadStorage();
         if (ts != null) {
            threadLocals.set(ts);
         }
      }

   }

   static final class ThreadStorage {
      public static final Object UNINITIALIZED = new Object();
      private static ResettableThreadLocal[] varList = new ResettableThreadLocal[0];
      private Object[] storage;

      public static int newSlot(ResettableThreadLocal var) {
         Class var1 = ThreadStorage.class;
         synchronized(ThreadStorage.class) {
            int newIdx = varList.length;
            ResettableThreadLocal[] newList = new ResettableThreadLocal[newIdx + 1];
            System.arraycopy(varList, 0, newList, 0, newIdx);
            newList[newIdx] = var;
            varList = newList;
            return newIdx;
         }
      }

      public ThreadStorage() {
         this.storage = new Object[varList.length];
         Arrays.fill(this.storage, UNINITIALIZED);
      }

      public void set(int idx, Object o) {
         if (idx >= this.storage.length) {
            this.expand(idx + 1);
         }

         this.storage[idx] = o;
      }

      public Object get(int idx) {
         if (idx >= this.storage.length) {
            this.expand(idx + 1);
         }

         Object value = this.storage[idx];
         if (value == UNINITIALIZED) {
            value = varList[idx].initialValue();
            this.set(idx, value);
         }

         return value;
      }

      public ThreadStorage createChildCopy() {
         ThreadStorage ts = new ThreadStorage();
         Object[] childStorage = ts.storage;
         Object[] localStorage = this.storage;
         ResettableThreadLocal[] localVarList = varList;
         int size = localStorage.length;

         for(int i = 0; i < size; ++i) {
            Object o = localStorage[i];
            if (o != UNINITIALIZED) {
               childStorage[i] = localVarList[i].childValue(o);
            }
         }

         return ts;
      }

      final void reset() {
         Object[] localStorage = this.storage;
         ResettableThreadLocal[] localVarList = varList;
         int size = localStorage.length;

         for(int i = 0; i < size; ++i) {
            Object o = localStorage[i];
            if (o != UNINITIALIZED) {
               localStorage[i] = localVarList[i].resetValue(o);
            }
         }

      }

      private void expand(int newSize) {
         int oldSize = this.storage.length;
         Object[] newStorage = new Object[newSize];
         System.arraycopy(this.storage, 0, newStorage, 0, oldSize);

         for(int i = oldSize; i < newSize; ++i) {
            newStorage[i] = UNINITIALIZED;
         }

         this.storage = newStorage;
      }
   }
}
