package com.bea.core.repackaged.springframework.core.io;

import com.bea.core.repackaged.springframework.lang.Nullable;

public interface ResourceLoader {
   String CLASSPATH_URL_PREFIX = "classpath:";

   Resource getResource(String var1);

   @Nullable
   ClassLoader getClassLoader();
}
