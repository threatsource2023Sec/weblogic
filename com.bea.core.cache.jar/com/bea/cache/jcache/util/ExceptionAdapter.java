package com.bea.cache.jcache.util;

import com.bea.cache.jcache.CacheException;
import weblogic.cache.CacheRuntimeException;
import weblogic.cache.util.ExceptionTranslator;

public class ExceptionAdapter implements ExceptionTranslator {
   public static final ExceptionAdapter ADAPTER = new ExceptionAdapter();

   public static ExceptionAdapter getInstance() {
      return ADAPTER;
   }

   private ExceptionAdapter() {
   }

   public RuntimeException fromInternal(CacheRuntimeException ce) {
      Throwable cause = ce.getCause() == null ? ce : ce.getCause();
      return new CacheException(ce.getMessage(), (Throwable)cause);
   }

   public RuntimeException toInternal(CacheException ce) {
      Throwable cause = ce.getCause() == null ? ce : ce.getCause();
      return new CacheRuntimeException(ce.getMessage(), (Throwable)cause);
   }
}
