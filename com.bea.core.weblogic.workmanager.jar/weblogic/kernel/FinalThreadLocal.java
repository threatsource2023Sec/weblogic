package weblogic.kernel;

final class FinalThreadLocal implements AuditableThreadLocal {
   private static volatile boolean finalized = false;
   private final int index = FinalThreadLocal.FinalThreadStorage.newSlot(this);
   private final ThreadLocalInitialValue initial;
   private static final String CONTINUAL_LOG_PROP = "weblogic.kernel.debug.JavaThreadAccess";
   private static final boolean keepLogging = getContinueLoggingProperty();
   private static boolean loggedWarning = true;
   private static final InheritableThreadLocal threadLocals = new InheritableThreadLocal() {
      protected Object initialValue() {
         return new FinalThreadStorage();
      }

      protected Object childValue(Object parentValue) {
         Thread thread = Thread.currentThread();
         if (thread instanceof AuditableThread) {
            return new FinalThreadStorage(((AuditableThread)thread).finalThreadStorage);
         } else {
            if (!FinalThreadLocal.loggedWarning) {
               KernelLogger.logWarnSwitchToWorkManagerAPI();
               FinalThreadLocal.loggedWarning = true;
            } else if (FinalThreadLocal.keepLogging) {
               KernelLogger.logDebugSwitchToWorkManagerAPI(thread.toString());
            }

            return parentValue != null ? new FinalThreadStorage((FinalThreadStorage)parentValue) : null;
         }
      }
   };

   private static boolean getContinueLoggingProperty() {
      return !KernelStatus.isApplet() && "true".equals(System.getProperty("weblogic.kernel.debug.JavaThreadAccess"));
   }

   FinalThreadLocal() {
      this.initial = new ThreadLocalInitialValue();
   }

   FinalThreadLocal(ThreadLocalInitialValue d) {
      this.initial = d;
   }

   public Object get() {
      Thread thread = Thread.currentThread();
      return thread instanceof AuditableThread ? ((AuditableThread)thread).finalThreadStorage.get(this.index) : this.get((ThreadStorage)threadLocals.get());
   }

   public Object get(AuditableThread thread) {
      return thread == null ? null : thread.finalThreadStorage.get(this.index);
   }

   private Object get(ThreadStorage storage) {
      return storage == null ? null : storage.get(this.index);
   }

   public void set(Object value) {
      Thread thread = Thread.currentThread();
      if (thread instanceof AuditableThread) {
         ((AuditableThread)thread).finalThreadStorage.set(this.index, value);
      } else {
         this.set((ThreadStorage)threadLocals.get(), value);
      }

   }

   private void set(ThreadStorage storage, Object value) {
      if (storage != null) {
         storage.set(this.index, value);
      }
   }

   static boolean isFinalized() {
      return finalized;
   }

   static final void resetJavaThreadStorage() {
      threadLocals.set((Object)null);
   }

   static final class FinalThreadStorage implements ThreadStorage {
      private static FinalThreadLocal[] varList = new FinalThreadLocal[0];
      private final Object[] storage;
      private final int NUM_SLOTS;

      public static int newSlot(FinalThreadLocal var) {
         if (FinalThreadLocal.finalized) {
            throw new AssertionError("A FinalThreadLocal was allocated after thread creation.");
         } else {
            Class var1 = FinalThreadStorage.class;
            synchronized(FinalThreadStorage.class) {
               int newIdx = varList.length;
               FinalThreadLocal[] newList = new FinalThreadLocal[newIdx + 1];
               System.arraycopy(varList, 0, newList, 0, newIdx);
               newList[newIdx] = var;
               varList = newList;
               return newIdx;
            }
         }
      }

      public FinalThreadStorage() {
         FinalThreadLocal.finalized = true;
         this.NUM_SLOTS = varList.length;
         this.storage = new Object[this.NUM_SLOTS];

         for(int i = 0; i < this.NUM_SLOTS; ++i) {
            this.storage[i] = varList[i].initial.initialValue();
         }

      }

      protected FinalThreadStorage(FinalThreadStorage parent) {
         this.NUM_SLOTS = varList.length;
         this.storage = new Object[this.NUM_SLOTS];

         for(int i = 0; i < this.NUM_SLOTS; ++i) {
            if (parent != null) {
               this.storage[i] = varList[i].initial.childValue(parent.storage[i]);
            } else {
               this.storage[i] = varList[i].initial.initialValue();
            }
         }

      }

      public void set(int idx, Object o) {
         this.storage[idx] = o;
      }

      public Object get(int idx) {
         return this.storage[idx];
      }

      public final void reset() {
         Object[] localStorage = this.storage;
         int size = localStorage.length;

         for(int i = 0; i < size; ++i) {
            Object o = localStorage[i];
            localStorage[i] = varList[i].initial.resetValue(o);
         }

      }
   }
}
