package com.bea.core.repackaged.springframework.beans.factory;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.annotation.Annotation;
import java.util.Map;

public interface ListableBeanFactory extends BeanFactory {
   boolean containsBeanDefinition(String var1);

   int getBeanDefinitionCount();

   String[] getBeanDefinitionNames();

   String[] getBeanNamesForType(ResolvableType var1);

   String[] getBeanNamesForType(@Nullable Class var1);

   String[] getBeanNamesForType(@Nullable Class var1, boolean var2, boolean var3);

   Map getBeansOfType(@Nullable Class var1) throws BeansException;

   Map getBeansOfType(@Nullable Class var1, boolean var2, boolean var3) throws BeansException;

   String[] getBeanNamesForAnnotation(Class var1);

   Map getBeansWithAnnotation(Class var1) throws BeansException;

   @Nullable
   Annotation findAnnotationOnBean(String var1, Class var2) throws NoSuchBeanDefinitionException;
}
