package com.bea.core.repackaged.springframework.dao.support;

import com.bea.core.repackaged.springframework.dao.DataAccessException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChainedPersistenceExceptionTranslator implements PersistenceExceptionTranslator {
   private final List delegates = new ArrayList(4);

   public final void addDelegate(PersistenceExceptionTranslator pet) {
      Assert.notNull(pet, (String)"PersistenceExceptionTranslator must not be null");
      this.delegates.add(pet);
   }

   public final PersistenceExceptionTranslator[] getDelegates() {
      return (PersistenceExceptionTranslator[])this.delegates.toArray(new PersistenceExceptionTranslator[0]);
   }

   @Nullable
   public DataAccessException translateExceptionIfPossible(RuntimeException ex) {
      Iterator var2 = this.delegates.iterator();

      DataAccessException translatedDex;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         PersistenceExceptionTranslator pet = (PersistenceExceptionTranslator)var2.next();
         translatedDex = pet.translateExceptionIfPossible(ex);
      } while(translatedDex == null);

      return translatedDex;
   }
}
