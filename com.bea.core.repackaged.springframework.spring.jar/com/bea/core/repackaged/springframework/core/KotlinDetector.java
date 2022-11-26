package com.bea.core.repackaged.springframework.core;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;

public abstract class KotlinDetector {
   private static final Log logger = LogFactory.getLog(KotlinDetector.class);
   @Nullable
   private static final Class kotlinMetadata;
   private static final boolean kotlinReflectPresent;

   public static boolean isKotlinPresent() {
      return kotlinMetadata != null;
   }

   public static boolean isKotlinReflectPresent() {
      return kotlinReflectPresent;
   }

   public static boolean isKotlinType(Class clazz) {
      return kotlinMetadata != null && clazz.getDeclaredAnnotation(kotlinMetadata) != null;
   }

   static {
      ClassLoader classLoader = KotlinDetector.class.getClassLoader();

      Class metadata;
      try {
         metadata = ClassUtils.forName("kotlin.Metadata", classLoader);
      } catch (ClassNotFoundException var3) {
         metadata = null;
      }

      kotlinMetadata = metadata;
      kotlinReflectPresent = ClassUtils.isPresent("kotlin.reflect.full.KClasses", classLoader);
      if (kotlinMetadata != null && !kotlinReflectPresent) {
         logger.info("Kotlin reflection implementation not found at runtime, related features won't be available.");
      }

   }
}
