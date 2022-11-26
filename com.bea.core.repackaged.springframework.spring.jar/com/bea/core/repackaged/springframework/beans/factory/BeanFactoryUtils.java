package com.bea.core.repackaged.springframework.beans.factory;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BeanFactoryUtils {
   public static final String GENERATED_BEAN_NAME_SEPARATOR = "#";
   private static final Map transformedBeanNameCache = new ConcurrentHashMap();

   public static boolean isFactoryDereference(@Nullable String name) {
      return name != null && name.startsWith("&");
   }

   public static String transformedBeanName(String name) {
      Assert.notNull(name, (String)"'name' must not be null");
      return !name.startsWith("&") ? name : (String)transformedBeanNameCache.computeIfAbsent(name, (beanName) -> {
         do {
            beanName = beanName.substring("&".length());
         } while(beanName.startsWith("&"));

         return beanName;
      });
   }

   public static boolean isGeneratedBeanName(@Nullable String name) {
      return name != null && name.contains("#");
   }

   public static String originalBeanName(String name) {
      Assert.notNull(name, (String)"'name' must not be null");
      int separatorIndex = name.indexOf("#");
      return separatorIndex != -1 ? name.substring(0, separatorIndex) : name;
   }

   public static int countBeansIncludingAncestors(ListableBeanFactory lbf) {
      return beanNamesIncludingAncestors(lbf).length;
   }

   public static String[] beanNamesIncludingAncestors(ListableBeanFactory lbf) {
      return beanNamesForTypeIncludingAncestors(lbf, Object.class);
   }

   public static String[] beanNamesForTypeIncludingAncestors(ListableBeanFactory lbf, ResolvableType type) {
      Assert.notNull(lbf, (String)"ListableBeanFactory must not be null");
      String[] result = lbf.getBeanNamesForType(type);
      if (lbf instanceof HierarchicalBeanFactory) {
         HierarchicalBeanFactory hbf = (HierarchicalBeanFactory)lbf;
         if (hbf.getParentBeanFactory() instanceof ListableBeanFactory) {
            String[] parentResult = beanNamesForTypeIncludingAncestors((ListableBeanFactory)hbf.getParentBeanFactory(), type);
            result = mergeNamesWithParent(result, parentResult, hbf);
         }
      }

      return result;
   }

   public static String[] beanNamesForTypeIncludingAncestors(ListableBeanFactory lbf, Class type) {
      Assert.notNull(lbf, (String)"ListableBeanFactory must not be null");
      String[] result = lbf.getBeanNamesForType(type);
      if (lbf instanceof HierarchicalBeanFactory) {
         HierarchicalBeanFactory hbf = (HierarchicalBeanFactory)lbf;
         if (hbf.getParentBeanFactory() instanceof ListableBeanFactory) {
            String[] parentResult = beanNamesForTypeIncludingAncestors((ListableBeanFactory)hbf.getParentBeanFactory(), type);
            result = mergeNamesWithParent(result, parentResult, hbf);
         }
      }

      return result;
   }

   public static String[] beanNamesForTypeIncludingAncestors(ListableBeanFactory lbf, Class type, boolean includeNonSingletons, boolean allowEagerInit) {
      Assert.notNull(lbf, (String)"ListableBeanFactory must not be null");
      String[] result = lbf.getBeanNamesForType(type, includeNonSingletons, allowEagerInit);
      if (lbf instanceof HierarchicalBeanFactory) {
         HierarchicalBeanFactory hbf = (HierarchicalBeanFactory)lbf;
         if (hbf.getParentBeanFactory() instanceof ListableBeanFactory) {
            String[] parentResult = beanNamesForTypeIncludingAncestors((ListableBeanFactory)hbf.getParentBeanFactory(), type, includeNonSingletons, allowEagerInit);
            result = mergeNamesWithParent(result, parentResult, hbf);
         }
      }

      return result;
   }

   public static String[] beanNamesForAnnotationIncludingAncestors(ListableBeanFactory lbf, Class annotationType) {
      Assert.notNull(lbf, (String)"ListableBeanFactory must not be null");
      String[] result = lbf.getBeanNamesForAnnotation(annotationType);
      if (lbf instanceof HierarchicalBeanFactory) {
         HierarchicalBeanFactory hbf = (HierarchicalBeanFactory)lbf;
         if (hbf.getParentBeanFactory() instanceof ListableBeanFactory) {
            String[] parentResult = beanNamesForAnnotationIncludingAncestors((ListableBeanFactory)hbf.getParentBeanFactory(), annotationType);
            result = mergeNamesWithParent(result, parentResult, hbf);
         }
      }

      return result;
   }

   public static Map beansOfTypeIncludingAncestors(ListableBeanFactory lbf, Class type) throws BeansException {
      Assert.notNull(lbf, (String)"ListableBeanFactory must not be null");
      Map result = new LinkedHashMap(4);
      result.putAll(lbf.getBeansOfType(type));
      if (lbf instanceof HierarchicalBeanFactory) {
         HierarchicalBeanFactory hbf = (HierarchicalBeanFactory)lbf;
         if (hbf.getParentBeanFactory() instanceof ListableBeanFactory) {
            Map parentResult = beansOfTypeIncludingAncestors((ListableBeanFactory)hbf.getParentBeanFactory(), type);
            parentResult.forEach((beanName, beanInstance) -> {
               if (!result.containsKey(beanName) && !hbf.containsLocalBean(beanName)) {
                  result.put(beanName, beanInstance);
               }

            });
         }
      }

      return result;
   }

   public static Map beansOfTypeIncludingAncestors(ListableBeanFactory lbf, Class type, boolean includeNonSingletons, boolean allowEagerInit) throws BeansException {
      Assert.notNull(lbf, (String)"ListableBeanFactory must not be null");
      Map result = new LinkedHashMap(4);
      result.putAll(lbf.getBeansOfType(type, includeNonSingletons, allowEagerInit));
      if (lbf instanceof HierarchicalBeanFactory) {
         HierarchicalBeanFactory hbf = (HierarchicalBeanFactory)lbf;
         if (hbf.getParentBeanFactory() instanceof ListableBeanFactory) {
            Map parentResult = beansOfTypeIncludingAncestors((ListableBeanFactory)hbf.getParentBeanFactory(), type, includeNonSingletons, allowEagerInit);
            parentResult.forEach((beanName, beanInstance) -> {
               if (!result.containsKey(beanName) && !hbf.containsLocalBean(beanName)) {
                  result.put(beanName, beanInstance);
               }

            });
         }
      }

      return result;
   }

   public static Object beanOfTypeIncludingAncestors(ListableBeanFactory lbf, Class type) throws BeansException {
      Map beansOfType = beansOfTypeIncludingAncestors(lbf, type);
      return uniqueBean(type, beansOfType);
   }

   public static Object beanOfTypeIncludingAncestors(ListableBeanFactory lbf, Class type, boolean includeNonSingletons, boolean allowEagerInit) throws BeansException {
      Map beansOfType = beansOfTypeIncludingAncestors(lbf, type, includeNonSingletons, allowEagerInit);
      return uniqueBean(type, beansOfType);
   }

   public static Object beanOfType(ListableBeanFactory lbf, Class type) throws BeansException {
      Assert.notNull(lbf, (String)"ListableBeanFactory must not be null");
      Map beansOfType = lbf.getBeansOfType(type);
      return uniqueBean(type, beansOfType);
   }

   public static Object beanOfType(ListableBeanFactory lbf, Class type, boolean includeNonSingletons, boolean allowEagerInit) throws BeansException {
      Assert.notNull(lbf, (String)"ListableBeanFactory must not be null");
      Map beansOfType = lbf.getBeansOfType(type, includeNonSingletons, allowEagerInit);
      return uniqueBean(type, beansOfType);
   }

   private static String[] mergeNamesWithParent(String[] result, String[] parentResult, HierarchicalBeanFactory hbf) {
      if (parentResult.length == 0) {
         return result;
      } else {
         List merged = new ArrayList(result.length + parentResult.length);
         merged.addAll(Arrays.asList(result));
         String[] var4 = parentResult;
         int var5 = parentResult.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String beanName = var4[var6];
            if (!merged.contains(beanName) && !hbf.containsLocalBean(beanName)) {
               merged.add(beanName);
            }
         }

         return StringUtils.toStringArray((Collection)merged);
      }
   }

   private static Object uniqueBean(Class type, Map matchingBeans) {
      int count = matchingBeans.size();
      if (count == 1) {
         return matchingBeans.values().iterator().next();
      } else if (count > 1) {
         throw new NoUniqueBeanDefinitionException(type, matchingBeans.keySet());
      } else {
         throw new NoSuchBeanDefinitionException(type);
      }
   }
}
