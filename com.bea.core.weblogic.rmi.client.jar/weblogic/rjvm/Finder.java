package weblogic.rjvm;

import weblogic.utils.KeyTable;

final class Finder implements InvokableFinder {
   private final KeyTable table = new KeyTable();

   private static RemoteInvokable unwarp(Object o) {
      return o instanceof HashWrapper ? (RemoteInvokable)((HashWrapper)o).value : (RemoteInvokable)o;
   }

   public synchronized RemoteInvokable lookupRemoteInvokable(int id) {
      return unwarp(this.table.get(id));
   }

   public synchronized void put(int id, RemoteInvokable ri) {
      Object o = this.table.get(id);
      if (o != null) {
         RJVMLogger.logBadInstall(id, o.toString(), ri.toString());
      }

      if (ri.hashCode() == id) {
         this.table.put(ri);
      } else {
         this.table.put(new HashWrapper(id, ri));
      }

   }

   public synchronized void remove(int id) {
      this.table.remove(id);
   }

   private static final class HashWrapper {
      private final int key;
      private final Object value;

      HashWrapper(int key, Object value) {
         this.key = key;
         this.value = value;
      }

      public int hashCode() {
         return this.key;
      }

      public boolean equals(Object other) {
         if (other == null) {
            return false;
         } else {
            return this.key == other.hashCode();
         }
      }
   }
}
