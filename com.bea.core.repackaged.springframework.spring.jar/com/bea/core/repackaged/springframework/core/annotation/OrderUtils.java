package com.bea.core.repackaged.springframework.core.annotation;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ConcurrentReferenceHashMap;
import java.lang.annotation.Annotation;
import java.util.Map;

public abstract class OrderUtils {
   private static final Object NOT_ANNOTATED = new Object();
   @Nullable
   private static Class priorityAnnotationType;
   private static final Map orderCache;
   private static final Map priorityCache;

   public static int getOrder(Class type, int defaultOrder) {
      Integer order = getOrder(type);
      return order != null ? order : defaultOrder;
   }

   @Nullable
   public static Integer getOrder(Class type, @Nullable Integer defaultOrder) {
      Integer order = getOrder(type);
      return order != null ? order : defaultOrder;
   }

   @Nullable
   public static Integer getOrder(Class type) {
      Object cached = orderCache.get(type);
      if (cached != null) {
         return cached instanceof Integer ? (Integer)cached : null;
      } else {
         Order order = (Order)AnnotationUtils.findAnnotation(type, Order.class);
         Integer result;
         if (order != null) {
            result = order.value();
         } else {
            result = getPriority(type);
         }

         orderCache.put(type, result != null ? result : NOT_ANNOTATED);
         return result;
      }
   }

   @Nullable
   public static Integer getPriority(Class type) {
      if (priorityAnnotationType == null) {
         return null;
      } else {
         Object cached = priorityCache.get(type);
         if (cached != null) {
            return cached instanceof Integer ? (Integer)cached : null;
         } else {
            Annotation priority = AnnotationUtils.findAnnotation(type, priorityAnnotationType);
            Integer result = null;
            if (priority != null) {
               result = (Integer)AnnotationUtils.getValue(priority);
            }

            priorityCache.put(type, result != null ? result : NOT_ANNOTATED);
            return result;
         }
      }
   }

   static {
      try {
         priorityAnnotationType = ClassUtils.forName("javax.annotation.Priority", OrderUtils.class.getClassLoader());
      } catch (Throwable var1) {
         priorityAnnotationType = null;
      }

      orderCache = new ConcurrentReferenceHashMap(64);
      priorityCache = new ConcurrentReferenceHashMap();
   }
}
