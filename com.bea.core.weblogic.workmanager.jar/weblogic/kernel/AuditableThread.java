package weblogic.kernel;

public class AuditableThread extends WLSThread {
   private ResettableThreadLocal.ThreadStorage threadStorage;
   FinalThreadLocal.FinalThreadStorage finalThreadStorage = new FinalThreadLocal.FinalThreadStorage();

   protected AuditableThread() {
   }

   protected AuditableThread(Runnable target) {
      super(target);
   }

   protected AuditableThread(String name) {
      super(name);
   }

   protected AuditableThread(ThreadGroup group, Runnable target) {
      super(group, target);
   }

   protected AuditableThread(Runnable target, String name) {
      super(target, name);
   }

   protected AuditableThread(ThreadGroup group, String name) {
      super(group, name);
   }

   public AuditableThread(ThreadGroup group, Runnable target, String name) {
      super(group, target, name);
   }

   protected void reset() {
      if (this.threadStorage != null) {
         this.threadStorage.reset();
      }

      this.finalThreadStorage.reset();
   }

   protected void readyToRun() {
      FinalThreadLocal.resetJavaThreadStorage();
   }

   protected void restoreCurrentJavaThreadStorage() {
      FinalThreadLocal.resetJavaThreadStorage();
      ResettableThreadLocal.restoreCurrentJavaThreadStorage();
   }

   final ResettableThreadLocal.ThreadStorage getThreadStorage() {
      return this.threadStorage;
   }

   final void setThreadStorage(ResettableThreadLocal.ThreadStorage ts) {
      this.threadStorage = ts;
   }

   public ClassLoader getDefaultContextClassLoader() {
      return null;
   }

   public Object suspendThreadStorages() {
      if (this != Thread.currentThread()) {
         throw new IllegalArgumentException("Can not call suspend/restore threadStorages from another thread: this(" + this + "), current(" + Thread.currentThread() + ")");
      } else {
         Object[] storages = new Object[]{this.finalThreadStorage, this.threadStorage};
         this.finalThreadStorage = new FinalThreadLocal.FinalThreadStorage();
         this.threadStorage = null;
         ResettableThreadLocal.resetJavaThreadStorage();
         return storages;
      }
   }

   public void restoreThreadStorages(Object data) {
      if (this != Thread.currentThread()) {
         throw new IllegalArgumentException("Can not call suspend/restore threadStorages from another thread: this(" + this + "), current(" + Thread.currentThread() + ")");
      } else if (!(data instanceof Object[])) {
         throw new IllegalArgumentException("Invalid storages: " + data);
      } else {
         Object[] storages = (Object[])((Object[])data);
         if (storages.length != 2) {
            throw new IllegalArgumentException("Invalid storages: length=" + storages.length);
         } else if (!(storages[0] instanceof FinalThreadLocal.FinalThreadStorage)) {
            throw new IllegalArgumentException("Invalid finalThreadStorage: " + storages[0]);
         } else {
            this.finalThreadStorage = (FinalThreadLocal.FinalThreadStorage)storages[0];
            if (storages[1] == null) {
               this.threadStorage = null;
               ResettableThreadLocal.resetJavaThreadStorage();
            } else {
               if (!(storages[1] instanceof ResettableThreadLocal.ThreadStorage)) {
                  throw new IllegalArgumentException("Invalid threadStorage: " + storages[1]);
               }

               this.threadStorage = (ResettableThreadLocal.ThreadStorage)storages[1];
               ResettableThreadLocal.restoreCurrentJavaThreadStorage();
            }

         }
      }
   }
}
