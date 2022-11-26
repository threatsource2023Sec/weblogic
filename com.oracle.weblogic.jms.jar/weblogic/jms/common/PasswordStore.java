package weblogic.jms.common;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;

public final class PasswordStore {
   private long currentHandle;
   private HashMap store;
   private char[] key;
   private static final char[] DEFAULT_KEY = new char[]{'B', 'E', 'A', '0', '1'};

   public PasswordStore() {
      this(DEFAULT_KEY);
   }

   public PasswordStore(char[] paramKey) {
      this.currentHandle = Long.MIN_VALUE;
      this.store = new HashMap();
      if (paramKey == null || paramKey.length <= 0) {
         paramKey = DEFAULT_KEY;
      }

      this.key = new char[paramKey.length];
      System.arraycopy(paramKey, 0, this.key, 0, paramKey.length);
   }

   public Object storePassword(Object password) throws GeneralSecurityException {
      StoreData data;
      if (password == null) {
         data = new StoreData(2, password);
      } else {
         String encryptedPassword;
         if (password instanceof String) {
            encryptedPassword = SecHelper.encryptString(this.key, (String)password);
            data = new StoreData(0, encryptedPassword);
         } else if (password instanceof char[]) {
            encryptedPassword = SecHelper.encryptPassword(this.key, (char[])((char[])password));
            data = new StoreData(1, encryptedPassword);
         } else {
            data = new StoreData(2, password);
         }
      }

      synchronized(this.store) {
         Long handle = new Long((long)(this.currentHandle++));
         this.store.put(handle, data);
         return handle;
      }
   }

   public Object retrievePassword(Object handle) throws GeneralSecurityException, IOException {
      if (handle == null) {
         return null;
      } else if (!(handle instanceof Long)) {
         throw new GeneralSecurityException("Invalid handle type: " + handle.getClass().getName());
      } else {
         Long trueHandle = (Long)handle;
         StoreData data;
         synchronized(this.store) {
            if (!this.store.containsKey(trueHandle)) {
               return null;
            }

            data = (StoreData)this.store.get(trueHandle);
         }

         if (data.getType() == 1) {
            return SecHelper.decryptString(this.key, (String)data.getData());
         } else {
            return data.getType() == 0 ? new String(SecHelper.decryptString(this.key, (String)data.getData())) : data.getData();
         }
      }
   }

   public void removePassword(Object handle) {
      if (handle != null) {
         if (handle instanceof Long) {
            synchronized(this.store) {
               this.store.remove(handle);
            }
         }
      }
   }

   private static class StoreData {
      private static final int TYPE_STRING = 0;
      private static final int TYPE_CHARARRAY = 1;
      private static final int TYPE_OTHER = 2;
      private int type;
      private Object data;

      private StoreData(int paramType, Object paramData) {
         this.type = paramType;
         this.data = paramData;
      }

      private int getType() {
         return this.type;
      }

      private Object getData() {
         return this.data;
      }

      // $FF: synthetic method
      StoreData(int x0, Object x1, Object x2) {
         this(x0, x1);
      }
   }
}
