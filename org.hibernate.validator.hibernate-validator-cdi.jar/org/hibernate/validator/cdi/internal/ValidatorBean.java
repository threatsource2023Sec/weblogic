package org.hibernate.validator.cdi.internal;

import java.util.Collections;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.PassivationCapable;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.classhierarchy.ClassHierarchyHelper;
import org.hibernate.validator.internal.util.classhierarchy.Filter;

public class ValidatorBean implements Bean, PassivationCapable {
   private final BeanManager beanManager;
   private final ValidationProviderHelper validationProviderHelper;
   private final Set types;
   private final Bean validatorFactoryBean;

   public ValidatorBean(BeanManager beanManager, Bean validatorFactoryBean, ValidationProviderHelper validationProviderHelper) {
      this.beanManager = beanManager;
      this.validatorFactoryBean = validatorFactoryBean;
      this.validationProviderHelper = validationProviderHelper;
      this.types = Collections.unmodifiableSet(CollectionHelper.newHashSet(ClassHierarchyHelper.getHierarchy(validationProviderHelper.getValidatorBeanClass(), new Filter[0])));
   }

   public Class getBeanClass() {
      return this.validationProviderHelper.getValidatorBeanClass();
   }

   public Set getInjectionPoints() {
      return Collections.emptySet();
   }

   public String getName() {
      return null;
   }

   public Set getQualifiers() {
      return this.validationProviderHelper.getQualifiers();
   }

   public Class getScope() {
      return ApplicationScoped.class;
   }

   public Set getStereotypes() {
      return Collections.emptySet();
   }

   public Set getTypes() {
      return this.types;
   }

   public boolean isAlternative() {
      return false;
   }

   public boolean isNullable() {
      return false;
   }

   public Validator create(CreationalContext ctx) {
      ValidatorFactory validatorFactory = this.createValidatorFactory();
      return validatorFactory.getValidator();
   }

   private ValidatorFactory createValidatorFactory() {
      CreationalContext context = this.beanManager.createCreationalContext(this.validatorFactoryBean);
      return (ValidatorFactory)this.beanManager.getReference(this.validatorFactoryBean, ValidatorFactory.class, context);
   }

   public void destroy(Validator instance, CreationalContext ctx) {
   }

   public String getId() {
      return ValidatorBean.class.getName() + "_" + (this.validationProviderHelper.isDefaultProvider() ? "default" : "hv");
   }

   public String toString() {
      return "ValidatorBean [id=" + this.getId() + "]";
   }
}
