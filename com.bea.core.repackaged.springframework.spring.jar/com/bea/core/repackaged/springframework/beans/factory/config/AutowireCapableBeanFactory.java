package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.TypeConverter;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Set;

public interface AutowireCapableBeanFactory extends BeanFactory {
   int AUTOWIRE_NO = 0;
   int AUTOWIRE_BY_NAME = 1;
   int AUTOWIRE_BY_TYPE = 2;
   int AUTOWIRE_CONSTRUCTOR = 3;
   /** @deprecated */
   @Deprecated
   int AUTOWIRE_AUTODETECT = 4;
   String ORIGINAL_INSTANCE_SUFFIX = ".ORIGINAL";

   Object createBean(Class var1) throws BeansException;

   void autowireBean(Object var1) throws BeansException;

   Object configureBean(Object var1, String var2) throws BeansException;

   Object createBean(Class var1, int var2, boolean var3) throws BeansException;

   Object autowire(Class var1, int var2, boolean var3) throws BeansException;

   void autowireBeanProperties(Object var1, int var2, boolean var3) throws BeansException;

   void applyBeanPropertyValues(Object var1, String var2) throws BeansException;

   Object initializeBean(Object var1, String var2) throws BeansException;

   Object applyBeanPostProcessorsBeforeInitialization(Object var1, String var2) throws BeansException;

   Object applyBeanPostProcessorsAfterInitialization(Object var1, String var2) throws BeansException;

   void destroyBean(Object var1);

   NamedBeanHolder resolveNamedBean(Class var1) throws BeansException;

   Object resolveBeanByName(String var1, DependencyDescriptor var2) throws BeansException;

   @Nullable
   Object resolveDependency(DependencyDescriptor var1, @Nullable String var2) throws BeansException;

   @Nullable
   Object resolveDependency(DependencyDescriptor var1, @Nullable String var2, @Nullable Set var3, @Nullable TypeConverter var4) throws BeansException;
}
