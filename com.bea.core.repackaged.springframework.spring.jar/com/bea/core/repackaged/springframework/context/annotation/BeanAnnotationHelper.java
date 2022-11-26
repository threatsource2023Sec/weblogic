package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.core.annotation.AnnotatedElementUtils;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAttributes;
import com.bea.core.repackaged.springframework.util.ConcurrentReferenceHashMap;
import java.lang.reflect.Method;
import java.util.Map;

abstract class BeanAnnotationHelper {
   private static final Map beanNameCache = new ConcurrentReferenceHashMap();
   private static final Map scopedProxyCache = new ConcurrentReferenceHashMap();

   public static boolean isBeanAnnotated(Method method) {
      return AnnotatedElementUtils.hasAnnotation(method, Bean.class);
   }

   public static String determineBeanNameFor(Method beanMethod) {
      String beanName = (String)beanNameCache.get(beanMethod);
      if (beanName == null) {
         beanName = beanMethod.getName();
         AnnotationAttributes bean = AnnotatedElementUtils.findMergedAnnotationAttributes(beanMethod, (Class)Bean.class, false, false);
         if (bean != null) {
            String[] names = bean.getStringArray("name");
            if (names.length > 0) {
               beanName = names[0];
            }
         }

         beanNameCache.put(beanMethod, beanName);
      }

      return beanName;
   }

   public static boolean isScopedProxy(Method beanMethod) {
      Boolean scopedProxy = (Boolean)scopedProxyCache.get(beanMethod);
      if (scopedProxy == null) {
         AnnotationAttributes scope = AnnotatedElementUtils.findMergedAnnotationAttributes(beanMethod, (Class)Scope.class, false, false);
         scopedProxy = scope != null && scope.getEnum("proxyMode") != ScopedProxyMode.NO;
         scopedProxyCache.put(beanMethod, scopedProxy);
      }

      return scopedProxy;
   }
}
