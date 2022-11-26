package com.bea.core.repackaged.springframework.beans.factory.annotation;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryUtils;
import com.bea.core.repackaged.springframework.beans.factory.ListableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.NoSuchBeanDefinitionException;
import com.bea.core.repackaged.springframework.beans.factory.NoUniqueBeanDefinitionException;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.support.AbstractBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.AutowireCandidateQualifier;
import com.bea.core.repackaged.springframework.beans.factory.support.RootBeanDefinition;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class BeanFactoryAnnotationUtils {
   public static Map qualifiedBeansOfType(ListableBeanFactory beanFactory, Class beanType, String qualifier) throws BeansException {
      String[] candidateBeans = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(beanFactory, beanType);
      Map result = new LinkedHashMap(4);
      String[] var5 = candidateBeans;
      int var6 = candidateBeans.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         String beanName = var5[var7];
         if (isQualifierMatch(qualifier::equals, beanName, beanFactory)) {
            result.put(beanName, beanFactory.getBean(beanName, beanType));
         }
      }

      return result;
   }

   public static Object qualifiedBeanOfType(BeanFactory beanFactory, Class beanType, String qualifier) throws BeansException {
      Assert.notNull(beanFactory, (String)"BeanFactory must not be null");
      if (beanFactory instanceof ListableBeanFactory) {
         return qualifiedBeanOfType((ListableBeanFactory)beanFactory, beanType, qualifier);
      } else if (beanFactory.containsBean(qualifier)) {
         return beanFactory.getBean(qualifier, beanType);
      } else {
         throw new NoSuchBeanDefinitionException(qualifier, "No matching " + beanType.getSimpleName() + " bean found for bean name '" + qualifier + "'! (Note: Qualifier matching not supported because given BeanFactory does not implement ConfigurableListableBeanFactory.)");
      }
   }

   private static Object qualifiedBeanOfType(ListableBeanFactory bf, Class beanType, String qualifier) {
      String[] candidateBeans = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(bf, beanType);
      String matchingBean = null;
      String[] var5 = candidateBeans;
      int var6 = candidateBeans.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         String beanName = var5[var7];
         if (isQualifierMatch(qualifier::equals, beanName, bf)) {
            if (matchingBean != null) {
               throw new NoUniqueBeanDefinitionException(beanType, new String[]{matchingBean, beanName});
            }

            matchingBean = beanName;
         }
      }

      if (matchingBean != null) {
         return bf.getBean(matchingBean, beanType);
      } else if (bf.containsBean(qualifier)) {
         return bf.getBean(qualifier, beanType);
      } else {
         throw new NoSuchBeanDefinitionException(qualifier, "No matching " + beanType.getSimpleName() + " bean found for qualifier '" + qualifier + "' - neither qualifier match nor bean name match!");
      }
   }

   public static boolean isQualifierMatch(Predicate qualifier, String beanName, @Nullable BeanFactory beanFactory) {
      if (qualifier.test(beanName)) {
         return true;
      } else {
         if (beanFactory != null) {
            String[] var3 = beanFactory.getAliases(beanName);
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               String alias = var3[var5];
               if (qualifier.test(alias)) {
                  return true;
               }
            }

            try {
               Class beanType = beanFactory.getType(beanName);
               if (beanFactory instanceof ConfigurableBeanFactory) {
                  BeanDefinition bd = ((ConfigurableBeanFactory)beanFactory).getMergedBeanDefinition(beanName);
                  if (bd instanceof AbstractBeanDefinition) {
                     AbstractBeanDefinition abd = (AbstractBeanDefinition)bd;
                     AutowireCandidateQualifier candidate = abd.getQualifier(Qualifier.class.getName());
                     if (candidate != null) {
                        Object value = candidate.getAttribute("value");
                        if (value != null && qualifier.test(value.toString())) {
                           return true;
                        }
                     }
                  }

                  if (bd instanceof RootBeanDefinition) {
                     Method factoryMethod = ((RootBeanDefinition)bd).getResolvedFactoryMethod();
                     if (factoryMethod != null) {
                        Qualifier targetAnnotation = (Qualifier)AnnotationUtils.getAnnotation(factoryMethod, Qualifier.class);
                        if (targetAnnotation != null) {
                           return qualifier.test(targetAnnotation.value());
                        }
                     }
                  }
               }

               if (beanType != null) {
                  Qualifier targetAnnotation = (Qualifier)AnnotationUtils.getAnnotation((AnnotatedElement)beanType, (Class)Qualifier.class);
                  if (targetAnnotation != null) {
                     return qualifier.test(targetAnnotation.value());
                  }
               }
            } catch (NoSuchBeanDefinitionException var8) {
            }
         }

         return false;
      }
   }
}
