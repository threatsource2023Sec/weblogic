package com.bea.core.repackaged.springframework.context;

import com.bea.core.repackaged.springframework.beans.factory.HierarchicalBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.ListableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.bea.core.repackaged.springframework.core.env.EnvironmentCapable;
import com.bea.core.repackaged.springframework.core.io.support.ResourcePatternResolver;
import com.bea.core.repackaged.springframework.lang.Nullable;

public interface ApplicationContext extends EnvironmentCapable, ListableBeanFactory, HierarchicalBeanFactory, MessageSource, ApplicationEventPublisher, ResourcePatternResolver {
   @Nullable
   String getId();

   String getApplicationName();

   String getDisplayName();

   long getStartupDate();

   @Nullable
   ApplicationContext getParent();

   AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException;
}
