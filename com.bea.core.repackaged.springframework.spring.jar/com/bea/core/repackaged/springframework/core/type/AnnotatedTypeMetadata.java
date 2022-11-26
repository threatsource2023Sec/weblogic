package com.bea.core.repackaged.springframework.core.type;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.MultiValueMap;
import java.util.Map;

public interface AnnotatedTypeMetadata {
   boolean isAnnotated(String var1);

   @Nullable
   Map getAnnotationAttributes(String var1);

   @Nullable
   Map getAnnotationAttributes(String var1, boolean var2);

   @Nullable
   MultiValueMap getAllAnnotationAttributes(String var1);

   @Nullable
   MultiValueMap getAllAnnotationAttributes(String var1, boolean var2);
}
