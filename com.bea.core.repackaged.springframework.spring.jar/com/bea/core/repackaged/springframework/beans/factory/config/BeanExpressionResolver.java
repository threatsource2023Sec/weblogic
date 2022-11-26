package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.lang.Nullable;

public interface BeanExpressionResolver {
   @Nullable
   Object evaluate(@Nullable String var1, BeanExpressionContext var2) throws BeansException;
}
