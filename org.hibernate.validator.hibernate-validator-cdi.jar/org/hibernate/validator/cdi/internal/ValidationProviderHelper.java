package org.hibernate.validator.cdi.internal;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.util.AnnotationLiteral;
import javax.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidatorFactory;
import org.hibernate.validator.internal.engine.ValidatorFactoryImpl;
import org.hibernate.validator.internal.engine.ValidatorImpl;
import org.hibernate.validator.internal.util.CollectionHelper;

public class ValidationProviderHelper {
   private final boolean isDefaultProvider;
   private final boolean isHibernateValidator;
   private final Class validatorFactoryClass;
   private final Class validatorClass;
   private final Set qualifiers;

   public static ValidationProviderHelper forDefaultProvider(ValidatorFactory validatorFactory) {
      boolean isHibernateValidator = validatorFactory instanceof HibernateValidatorFactory;
      return new ValidationProviderHelper(true, isHibernateValidator, validatorFactory.getClass(), validatorFactory.getValidator().getClass(), determineRequiredQualifiers(true, isHibernateValidator));
   }

   public static ValidationProviderHelper forHibernateValidator() {
      return new ValidationProviderHelper(false, true, ValidatorFactoryImpl.class, ValidatorImpl.class, determineRequiredQualifiers(false, true));
   }

   private ValidationProviderHelper(boolean isDefaultProvider, boolean isHibernateValidator, Class validatorFactoryClass, Class validatorClass, Set qualifiers) {
      this.isDefaultProvider = isDefaultProvider;
      this.isHibernateValidator = isHibernateValidator;
      this.validatorFactoryClass = validatorFactoryClass;
      this.validatorClass = validatorClass;
      this.qualifiers = Collections.unmodifiableSet(qualifiers);
   }

   public boolean isDefaultProvider() {
      return this.isDefaultProvider;
   }

   public boolean isHibernateValidator() {
      return this.isHibernateValidator;
   }

   Class getValidatorFactoryBeanClass() {
      return this.validatorFactoryClass;
   }

   Class getValidatorBeanClass() {
      return this.validatorClass;
   }

   Set getQualifiers() {
      return this.qualifiers;
   }

   private static Set determineRequiredQualifiers(boolean isDefaultProvider, boolean isHibernateValidator) {
      HashSet qualifiers = CollectionHelper.newHashSet(3);
      if (isDefaultProvider) {
         qualifiers.add(new AnnotationLiteral() {
         });
      }

      if (isHibernateValidator) {
         qualifiers.add(new AnnotationLiteral() {
         });
      }

      qualifiers.add(new AnnotationLiteral() {
      });
      return qualifiers;
   }

   public String toString() {
      return "ValidationProviderHelper [isDefaultProvider=" + this.isDefaultProvider + ", isHibernateValidator=" + this.isHibernateValidator + ", validatorFactoryClass=" + this.validatorFactoryClass + ", validatorClass=" + this.validatorClass + ", qualifiers=" + this.qualifiers + "]";
   }
}
