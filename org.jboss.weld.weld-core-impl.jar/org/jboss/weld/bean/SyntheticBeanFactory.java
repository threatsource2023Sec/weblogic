package org.jboss.weld.bean;

import javax.decorator.Decorator;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.InjectionTargetFactory;
import javax.enterprise.inject.spi.PassivationCapable;
import javax.enterprise.inject.spi.ProducerFactory;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.reflection.Reflections;

public class SyntheticBeanFactory {
   private SyntheticBeanFactory() {
   }

   public static AbstractSyntheticBean create(BeanAttributes attributes, Class beanClass, InjectionTargetFactory factory, BeanManagerImpl manager) {
      return attributes.getStereotypes().contains(Decorator.class) ? createDecorator(attributes, beanClass, factory, manager) : createClassBean(attributes, beanClass, factory, manager);
   }

   public static AbstractSyntheticBean create(BeanAttributes attributes, Class beanClass, ProducerFactory factory, BeanManagerImpl manager) {
      return createProducerBean(attributes, beanClass, factory, manager);
   }

   private static AbstractSyntheticBean createClassBean(BeanAttributes attributes, Class beanClass, InjectionTargetFactory factory, BeanManagerImpl manager) {
      return (AbstractSyntheticBean)(Reflections.isSerializable(beanClass) ? new PassivationCapableSyntheticClassBean(attributes, beanClass, factory, manager) : new SyntheticClassBean(attributes, beanClass, factory, manager));
   }

   private static AbstractSyntheticBean createDecorator(BeanAttributes attributes, Class beanClass, InjectionTargetFactory factory, BeanManagerImpl manager) {
      return (AbstractSyntheticBean)(Reflections.isSerializable(beanClass) ? new PassivationCapableSyntheticDecorator(attributes, beanClass, factory, manager) : new SyntheticDecorator(attributes, beanClass, factory, manager));
   }

   private static AbstractSyntheticBean createProducerBean(BeanAttributes attributes, Class beanClass, ProducerFactory factory, BeanManagerImpl manager) {
      return new SyntheticProducerBean(attributes, beanClass, factory, manager);
   }

   private static class PassivationCapableSyntheticDecorator extends SyntheticDecorator implements PassivationCapable {
      protected PassivationCapableSyntheticDecorator(BeanAttributes attributes, Class beanClass, InjectionTargetFactory factory, BeanManagerImpl manager) {
         super(attributes, beanClass, factory, manager);
      }
   }

   private static class PassivationCapableSyntheticClassBean extends SyntheticClassBean implements PassivationCapable {
      protected PassivationCapableSyntheticClassBean(BeanAttributes attributes, Class beanClass, InjectionTargetFactory factory, BeanManagerImpl manager) {
         super(attributes, beanClass, factory, manager);
      }
   }
}
