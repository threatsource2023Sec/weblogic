package com.bea.core.repackaged.springframework.beans.factory.parsing;

import com.bea.core.repackaged.springframework.beans.BeanMetadataElement;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanReference;

public interface ComponentDefinition extends BeanMetadataElement {
   String getName();

   String getDescription();

   BeanDefinition[] getBeanDefinitions();

   BeanDefinition[] getInnerBeanDefinitions();

   BeanReference[] getBeanReferences();
}
