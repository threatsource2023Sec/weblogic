package com.bea.core.repackaged.springframework.beans.factory.xml;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.bea.core.repackaged.springframework.core.io.Resource;

/** @deprecated */
@Deprecated
public class XmlBeanFactory extends DefaultListableBeanFactory {
   private final XmlBeanDefinitionReader reader;

   public XmlBeanFactory(Resource resource) throws BeansException {
      this(resource, (BeanFactory)null);
   }

   public XmlBeanFactory(Resource resource, BeanFactory parentBeanFactory) throws BeansException {
      super(parentBeanFactory);
      this.reader = new XmlBeanDefinitionReader(this);
      this.reader.loadBeanDefinitions(resource);
   }
}
