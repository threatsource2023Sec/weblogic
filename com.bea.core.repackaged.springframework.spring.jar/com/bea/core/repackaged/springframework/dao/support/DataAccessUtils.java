package com.bea.core.repackaged.springframework.dao.support;

import com.bea.core.repackaged.springframework.dao.DataAccessException;
import com.bea.core.repackaged.springframework.dao.EmptyResultDataAccessException;
import com.bea.core.repackaged.springframework.dao.IncorrectResultSizeDataAccessException;
import com.bea.core.repackaged.springframework.dao.TypeMismatchDataAccessException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.CollectionUtils;
import com.bea.core.repackaged.springframework.util.NumberUtils;
import java.util.Collection;

public abstract class DataAccessUtils {
   @Nullable
   public static Object singleResult(@Nullable Collection results) throws IncorrectResultSizeDataAccessException {
      if (CollectionUtils.isEmpty(results)) {
         return null;
      } else if (results.size() > 1) {
         throw new IncorrectResultSizeDataAccessException(1, results.size());
      } else {
         return results.iterator().next();
      }
   }

   public static Object requiredSingleResult(@Nullable Collection results) throws IncorrectResultSizeDataAccessException {
      if (CollectionUtils.isEmpty(results)) {
         throw new EmptyResultDataAccessException(1);
      } else if (results.size() > 1) {
         throw new IncorrectResultSizeDataAccessException(1, results.size());
      } else {
         return results.iterator().next();
      }
   }

   @Nullable
   public static Object nullableSingleResult(@Nullable Collection results) throws IncorrectResultSizeDataAccessException {
      if (CollectionUtils.isEmpty(results)) {
         throw new EmptyResultDataAccessException(1);
      } else if (results.size() > 1) {
         throw new IncorrectResultSizeDataAccessException(1, results.size());
      } else {
         return results.iterator().next();
      }
   }

   @Nullable
   public static Object uniqueResult(@Nullable Collection results) throws IncorrectResultSizeDataAccessException {
      if (CollectionUtils.isEmpty(results)) {
         return null;
      } else if (!CollectionUtils.hasUniqueObject(results)) {
         throw new IncorrectResultSizeDataAccessException(1, results.size());
      } else {
         return results.iterator().next();
      }
   }

   public static Object requiredUniqueResult(@Nullable Collection results) throws IncorrectResultSizeDataAccessException {
      if (CollectionUtils.isEmpty(results)) {
         throw new EmptyResultDataAccessException(1);
      } else if (!CollectionUtils.hasUniqueObject(results)) {
         throw new IncorrectResultSizeDataAccessException(1, results.size());
      } else {
         return results.iterator().next();
      }
   }

   public static Object objectResult(@Nullable Collection results, @Nullable Class requiredType) throws IncorrectResultSizeDataAccessException, TypeMismatchDataAccessException {
      Object result = requiredUniqueResult(results);
      if (requiredType != null && !requiredType.isInstance(result)) {
         if (String.class == requiredType) {
            result = result.toString();
         } else {
            if (!Number.class.isAssignableFrom(requiredType) || !Number.class.isInstance(result)) {
               throw new TypeMismatchDataAccessException("Result object is of type [" + result.getClass().getName() + "] and could not be converted to required type [" + requiredType.getName() + "]");
            }

            try {
               result = NumberUtils.convertNumberToTargetClass((Number)result, requiredType);
            } catch (IllegalArgumentException var4) {
               throw new TypeMismatchDataAccessException(var4.getMessage());
            }
         }
      }

      return result;
   }

   public static int intResult(@Nullable Collection results) throws IncorrectResultSizeDataAccessException, TypeMismatchDataAccessException {
      return ((Number)objectResult(results, Number.class)).intValue();
   }

   public static long longResult(@Nullable Collection results) throws IncorrectResultSizeDataAccessException, TypeMismatchDataAccessException {
      return ((Number)objectResult(results, Number.class)).longValue();
   }

   public static RuntimeException translateIfNecessary(RuntimeException rawException, PersistenceExceptionTranslator pet) {
      Assert.notNull(pet, (String)"PersistenceExceptionTranslator must not be null");
      DataAccessException dae = pet.translateExceptionIfPossible(rawException);
      return (RuntimeException)(dae != null ? dae : rawException);
   }
}
