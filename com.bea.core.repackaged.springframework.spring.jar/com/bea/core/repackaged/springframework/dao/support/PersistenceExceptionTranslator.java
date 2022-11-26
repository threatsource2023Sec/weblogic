package com.bea.core.repackaged.springframework.dao.support;

import com.bea.core.repackaged.springframework.dao.DataAccessException;
import com.bea.core.repackaged.springframework.lang.Nullable;

@FunctionalInterface
public interface PersistenceExceptionTranslator {
   @Nullable
   DataAccessException translateExceptionIfPossible(RuntimeException var1);
}
