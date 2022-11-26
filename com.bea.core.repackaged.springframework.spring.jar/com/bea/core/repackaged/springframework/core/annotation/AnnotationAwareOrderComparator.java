package com.bea.core.repackaged.springframework.core.annotation;

import com.bea.core.repackaged.springframework.core.DecoratingProxy;
import com.bea.core.repackaged.springframework.core.OrderComparator;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class AnnotationAwareOrderComparator extends OrderComparator {
   public static final AnnotationAwareOrderComparator INSTANCE = new AnnotationAwareOrderComparator();

   @Nullable
   protected Integer findOrder(Object obj) {
      Integer order = super.findOrder(obj);
      if (order != null) {
         return order;
      } else if (obj instanceof Class) {
         return OrderUtils.getOrder((Class)obj);
      } else {
         Order ann;
         if (obj instanceof Method) {
            ann = (Order)AnnotationUtils.findAnnotation((Method)obj, Order.class);
            if (ann != null) {
               return ann.value();
            }
         } else if (obj instanceof AnnotatedElement) {
            ann = (Order)AnnotationUtils.getAnnotation((AnnotatedElement)obj, Order.class);
            if (ann != null) {
               return ann.value();
            }
         } else {
            order = OrderUtils.getOrder(obj.getClass());
            if (order == null && obj instanceof DecoratingProxy) {
               order = OrderUtils.getOrder(((DecoratingProxy)obj).getDecoratedClass());
            }
         }

         return order;
      }
   }

   @Nullable
   public Integer getPriority(Object obj) {
      if (obj instanceof Class) {
         return OrderUtils.getPriority((Class)obj);
      } else {
         Integer priority = OrderUtils.getPriority(obj.getClass());
         if (priority == null && obj instanceof DecoratingProxy) {
            priority = OrderUtils.getPriority(((DecoratingProxy)obj).getDecoratedClass());
         }

         return priority;
      }
   }

   public static void sort(List list) {
      if (list.size() > 1) {
         list.sort(INSTANCE);
      }

   }

   public static void sort(Object[] array) {
      if (array.length > 1) {
         Arrays.sort(array, INSTANCE);
      }

   }

   public static void sortIfNecessary(Object value) {
      if (value instanceof Object[]) {
         sort((Object[])((Object[])value));
      } else if (value instanceof List) {
         sort((List)value);
      }

   }
}
