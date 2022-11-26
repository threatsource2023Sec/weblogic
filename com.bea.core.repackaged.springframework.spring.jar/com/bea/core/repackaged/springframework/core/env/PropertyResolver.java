package com.bea.core.repackaged.springframework.core.env;

import com.bea.core.repackaged.springframework.lang.Nullable;

public interface PropertyResolver {
   boolean containsProperty(String var1);

   @Nullable
   String getProperty(String var1);

   String getProperty(String var1, String var2);

   @Nullable
   Object getProperty(String var1, Class var2);

   Object getProperty(String var1, Class var2, Object var3);

   String getRequiredProperty(String var1) throws IllegalStateException;

   Object getRequiredProperty(String var1, Class var2) throws IllegalStateException;

   String resolvePlaceholders(String var1);

   String resolveRequiredPlaceholders(String var1) throws IllegalArgumentException;
}
