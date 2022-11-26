package weblogic.cache.util;

import weblogic.cache.CacheRuntimeException;

public interface ExceptionTranslator {
   RuntimeException fromInternal(CacheRuntimeException var1);
}
