package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public interface InstantiationStrategy {
   Object instantiate(RootBeanDefinition var1, @Nullable String var2, BeanFactory var3) throws BeansException;

   Object instantiate(RootBeanDefinition var1, @Nullable String var2, BeanFactory var3, Constructor var4, Object... var5) throws BeansException;

   Object instantiate(RootBeanDefinition var1, @Nullable String var2, BeanFactory var3, @Nullable Object var4, Method var5, Object... var6) throws BeansException;
}
