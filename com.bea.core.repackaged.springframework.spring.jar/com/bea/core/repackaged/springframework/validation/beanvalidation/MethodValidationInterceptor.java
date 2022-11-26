package com.bea.core.repackaged.springframework.validation.beanvalidation;

import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.SmartFactoryBean;
import com.bea.core.repackaged.springframework.core.BridgeMethodResolver;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationUtils;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.validation.annotation.Validated;
import java.lang.reflect.Method;
import java.util.Set;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;

public class MethodValidationInterceptor implements MethodInterceptor {
   private final Validator validator;

   public MethodValidationInterceptor() {
      this(Validation.buildDefaultValidatorFactory());
   }

   public MethodValidationInterceptor(ValidatorFactory validatorFactory) {
      this(validatorFactory.getValidator());
   }

   public MethodValidationInterceptor(Validator validator) {
      this.validator = validator;
   }

   public Object invoke(MethodInvocation invocation) throws Throwable {
      if (this.isFactoryBeanMetadataMethod(invocation.getMethod())) {
         return invocation.proceed();
      } else {
         Class[] groups = this.determineValidationGroups(invocation);
         ExecutableValidator execVal = this.validator.forExecutables();
         Method methodToValidate = invocation.getMethod();

         Set result;
         try {
            result = execVal.validateParameters(invocation.getThis(), methodToValidate, invocation.getArguments(), groups);
         } catch (IllegalArgumentException var7) {
            methodToValidate = BridgeMethodResolver.findBridgedMethod(ClassUtils.getMostSpecificMethod(invocation.getMethod(), invocation.getThis().getClass()));
            result = execVal.validateParameters(invocation.getThis(), methodToValidate, invocation.getArguments(), groups);
         }

         if (!result.isEmpty()) {
            throw new ConstraintViolationException(result);
         } else {
            Object returnValue = invocation.proceed();
            result = execVal.validateReturnValue(invocation.getThis(), methodToValidate, returnValue, groups);
            if (!result.isEmpty()) {
               throw new ConstraintViolationException(result);
            } else {
               return returnValue;
            }
         }
      }
   }

   private boolean isFactoryBeanMetadataMethod(Method method) {
      Class clazz = method.getDeclaringClass();
      if (clazz.isInterface()) {
         return (clazz == FactoryBean.class || clazz == SmartFactoryBean.class) && !method.getName().equals("getObject");
      } else {
         Class factoryBeanType = null;
         if (SmartFactoryBean.class.isAssignableFrom(clazz)) {
            factoryBeanType = SmartFactoryBean.class;
         } else if (FactoryBean.class.isAssignableFrom(clazz)) {
            factoryBeanType = FactoryBean.class;
         }

         return factoryBeanType != null && !method.getName().equals("getObject") && ClassUtils.hasMethod(factoryBeanType, method.getName(), method.getParameterTypes());
      }
   }

   protected Class[] determineValidationGroups(MethodInvocation invocation) {
      Validated validatedAnn = (Validated)AnnotationUtils.findAnnotation(invocation.getMethod(), Validated.class);
      if (validatedAnn == null) {
         validatedAnn = (Validated)AnnotationUtils.findAnnotation(invocation.getThis().getClass(), Validated.class);
      }

      return validatedAnn != null ? validatedAnn.value() : new Class[0];
   }
}
