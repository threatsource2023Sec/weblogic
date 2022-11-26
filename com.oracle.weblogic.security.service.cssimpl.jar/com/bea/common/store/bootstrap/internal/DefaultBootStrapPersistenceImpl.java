package com.bea.common.store.bootstrap.internal;

import com.bea.common.security.store.data.TopId;
import com.bea.common.store.bootstrap.BootStrapPersistence;
import com.bea.common.store.service.StoreService;
import java.lang.reflect.Constructor;
import java.util.Collection;
import javax.jdo.PersistenceManager;

public class DefaultBootStrapPersistenceImpl implements BootStrapPersistence {
   protected final PersistenceManager pm;
   protected final String storeId;

   public DefaultBootStrapPersistenceImpl(StoreService storeService) {
      this.pm = storeService.getPersistenceManager();
      this.storeId = storeService.getStoreId();
   }

   public boolean has(Object obj) throws Exception {
      try {
         Constructor oidConstructor = Class.forName(obj.getClass().getName() + "Id").getConstructor(obj.getClass());
         if (oidConstructor != null) {
            Object oid = oidConstructor.newInstance(obj);
            if (oid instanceof TopId) {
               return ((TopId)oid).getObject(this.pm) != null;
            }
         }
      } catch (Exception var4) {
      }

      return false;
   }

   public void makePersistentAll(Collection objects) throws Exception {
      try {
         this.pm.currentTransaction().begin();
         this.pm.makePersistentAll(objects);
         this.pm.currentTransaction().commit();
      } catch (Exception var3) {
         if (this.pm.currentTransaction().isActive()) {
            this.pm.currentTransaction().rollback();
         }

         throw var3;
      }
   }

   public void close() {
      if (this.pm != null) {
         this.pm.close();
      }

   }

   public String getStoreId() {
      return this.storeId;
   }

   public Collection postProcessObjects(Collection objs) {
      return objs;
   }
}
